package org.quentin.security.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.quentin.security.domain.dto.UserAuthz;
import org.quentin.security.domain.vo.UserDetail;
import org.quentin.security.mapper.UserAuthzMapper;
import org.quentin.security.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserMapper userMapper;
    private final UserAuthzMapper userAuthzMapper;

    public SecurityConfig(
            JwtAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider, UserMapper userMapper, UserAuthzMapper userAuthzMapper) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.userMapper = userMapper;
        this.userAuthzMapper = userAuthzMapper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(httpSecurityCsrfConfigurer -> {
//                    CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
//                    cookieCsrfTokenRepository.setCookieCustomizer((cookie) -> cookie.httpOnly(false));
//                    cookieCsrfTokenRepository.setCookieCustomizer(cookie -> cookie.maxAge(1000));
//                    httpSecurityCsrfConfigurer.csrfTokenRepository(
//                            cookieCsrfTokenRepository);
//                })
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/auth/**",
                                "/food/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(
                        sessionManagementConfigurer ->
                                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
        ;
        return http.build();
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

    //    @Bean
    public AuthenticationProvider authProvider(
            UserDetailsService userDetailsService) {
        return new CustomAuthcProvider(userDetailsService);
    }

    //        @Bean
    public AuthenticationManager authManager(
            HttpSecurity http, AuthenticationProvider authProvider) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

    //    @Bean
    public CustomUserPassAuthFilter customUserPassAuthFilter(AuthenticationManager authenticationManager) {
        CustomUserPassAuthFilter filter = new CustomUserPassAuthFilter();
        filter.setFilterProcessesUrl("/auth/verify");
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.getWriter().write("success");
            LOGGER.info("getPrincipal = {}", authentication.getPrincipal());
        });
        filter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.getWriter().write("fail");
            LOGGER.info("fail!!!");
        });
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
}
