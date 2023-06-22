package org.quentin.security.service.impl;

import org.quentin.security.domain.dto.User;
import org.quentin.security.domain.dto.UserInfo;
import org.quentin.security.domain.vo.UserDetail;
import org.quentin.security.domain.vo.UserResponse;
import org.quentin.security.mapper.UserInfoMapper;
import org.quentin.security.mapper.UserMapper;
import org.quentin.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserService.class);

    private final UserMapper userMapper;
    private final UserInfoMapper userInfoMapper;

    public DefaultUserService(UserMapper userMapper, UserInfoMapper userInfoMapper) {
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
    }

    //todo validate and username set and email set.
    @Override
    public UserResponse fetchUserWithInfo(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetail userDetail) {
            var email = userDetail.getEmail();
            User existUser = userMapper.getUserByUserInfoEmail(email);
            UserInfo userInfo = userInfoMapper.selectById(existUser.getUserId());
            // 64 is char @
            return new UserResponse(existUser.getUserId(), email, existUser.getUsername(), userInfo.getPhoneNumber());
        } else {
            throw new RuntimeException("maybe user is invalid");
        }
    }
}
