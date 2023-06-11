package org.quentin.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.quentin.security.domain.dto.Token;
import org.quentin.security.domain.dto.User;
import org.quentin.security.domain.dto.UserAuthz;
import org.quentin.security.domain.dto.UserInfo;
import org.quentin.security.domain.enumration.Role;
import org.quentin.security.domain.enumration.TokenType;
import org.quentin.security.domain.vo.AuthenticationRequest;
import org.quentin.security.domain.vo.AuthenticationResponse;
import org.quentin.security.domain.vo.RegisterRequest;
import org.quentin.security.domain.vo.UserDetail;
import org.quentin.security.mapper.TokenMapper;
import org.quentin.security.mapper.UserAuthzMapper;
import org.quentin.security.mapper.UserInfoMapper;
import org.quentin.security.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final UserMapper userMapper;
    private final UserInfoMapper userInfoMapper;
    private final TokenMapper tokenMapper;
    private final UserAuthzMapper userAuthzMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserMapper userMapper,
                       UserInfoMapper userInfoMapper, TokenMapper tokenMapper, UserAuthzMapper userAuthzMapper, PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
        this.tokenMapper = tokenMapper;
        this.userAuthzMapper = userAuthzMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserDetail.buildUser(new User(
                request.username(),
                passwordEncoder.encode(request.password())
        ), null, request.email());

        // this will return valid item length
        int validUserLength = userMapper.insert(user);

        LOGGER.trace("valid user count = {}", validUserLength);
        User savedUser = userMapper.selectById(user.getUserId());

        UserAuthz userAuthz = new UserAuthz(user.getUserId(), Role.USER);
        int validUserAuthzLength = userAuthzMapper.insert(userAuthz);
        LOGGER.trace("valid user authz count = {}", validUserAuthzLength);

        var userInfo = new UserInfo(savedUser.getUserId(), request.email(), "");
        int validUserInfoLength = userInfoMapper.insert(userInfo);
        LOGGER.trace("valid user info count = {}", validUserInfoLength);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password())
        );
        UserDetail user = UserDetail.buildUser(
                userMapper.getUserByUserInfoEmail(request.email()), null, request.email());
        UserAuthz foundUserAuthz = userAuthzMapper.selectOne(
                new QueryWrapper<UserAuthz>().eq("user_id", user.getUserId()));
        user.setRole(foundUserAuthz.getRole());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token(jwtToken, TokenType.BEARER, Token.NON_REVOKED, Token.NON_EXPIRED, user.getUserId());
        LOGGER.info("token = {}", token);
        int validTokenLength = tokenMapper.insert(token);
        LOGGER.trace("valid item length = {}", validTokenLength);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenMapper.selectList(
                new QueryWrapper<Token>().eq("user_id", user.getUserId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(1);
            token.setRevoked(1);
            int validUpdateLength = tokenMapper.updateById(token);
            LOGGER.trace("validTokenLength length = {}", validUpdateLength);
        });
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            UserDetail user = UserDetail.buildUser(
                    userMapper.getUserByUserInfoEmail(userEmail));
            UserAuthz foundUserAuthz = userAuthzMapper.selectOne(
                    new QueryWrapper<UserAuthz>().eq("user_id", user.getUserId()));
            user.setRole(foundUserAuthz.getRole());

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new AuthenticationResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
