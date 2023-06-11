package org.quentin.security.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.quentin.security.domain.dto.UserAuthz;
import org.quentin.security.domain.vo.UserDetail;
import org.quentin.security.mapper.UserAuthzMapper;
import org.quentin.security.mapper.UserMapper;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class ApplicationConfig {
    private final UserMapper userMapper;
    private final UserAuthzMapper userAuthzMapper;

    public ApplicationConfig(UserMapper userMapper, UserAuthzMapper userAuthzMapper) {
        this.userMapper = userMapper;
        this.userAuthzMapper = userAuthzMapper;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            UserDetail userDetail = UserDetail.buildUser(
                    userMapper.getUserByUserInfoEmail(email), null, email);
            UserAuthz userAuthz = userAuthzMapper.selectOne(
                    new QueryWrapper<UserAuthz>().eq("user_id", userDetail.getUserId()));
            userDetail.setRole(userAuthz.getRole());
            return Optional.of(userDetail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
