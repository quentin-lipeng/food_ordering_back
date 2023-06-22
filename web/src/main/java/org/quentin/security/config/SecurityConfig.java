package org.quentin.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * <code>SessionCreationPolicy.STATELESS</code> means ensure no session is created by spring security
     */
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
//                                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
        ;
        return http.build();
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
