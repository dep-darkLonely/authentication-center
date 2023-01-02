package com.hb.authenticationcenter.security;

import com.hb.authenticationcenter.security.checker.PreAuthenticationChecker;
import com.hb.authenticationcenter.security.entrypoint.CustomAuthenticationEntryPoint;
import com.hb.authenticationcenter.security.filter.BearerAuthenticationFilter;
import com.hb.authenticationcenter.security.filter.UsernamePasswordJsonAuthenticationFilter;
import com.hb.authenticationcenter.security.matcher.CustomCsrfMatcher;
import com.hb.authenticationcenter.security.provider.JweAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

/**
 * @author admin
 * @version 1.0
 * @description Spring Security configuration
 * @date 2022/12/15
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MultiSecurityFilterChainConfig {
    /**
     * login url
     */
    public static final String LOGIN_URL = "/public/login";
    /**
     * pbkdf2 custom secret
     */
    public static final String PBKDF2_SECRET = "default_pbkdf2_secret";
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private List<PreAuthenticationChecker> preAuthenticationCheckers;
    private UserDetailsService customUserDetailService;

    @Autowired
    public void setCustomUserDetailService(UserDetailsService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Autowired
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Autowired
    public void setPreAuthenticationCheckers(List<PreAuthenticationChecker> preAuthenticationCheckers) {
        this.preAuthenticationCheckers = preAuthenticationCheckers;
    }

    /**
     * login filter chain
     * @return SecurityFilterChain
     */
    @Bean
    @Order(1)
    public SecurityFilterChain publicFilterChain(HttpSecurity httpSecurity,
                                                 AuthenticationManager authenticationManager)
            throws Exception {
        UsernamePasswordJsonAuthenticationFilter authenticationFilter = new UsernamePasswordJsonAuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(
                new AuthenticationEntryPointFailureHandler(customAuthenticationEntryPoint()));
        authenticationFilter.setPreAuthenticationCheckers(preAuthenticationCheckers);
        authenticationFilter.setAuthenticationManager(authenticationManager);

        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/public/**"))
                .cors()
                .and()
                .csrf()
                .requireCsrfProtectionMatcher(new CustomCsrfMatcher())
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/public/**")
                .permitAll()
                .and()
                .portMapper((portMapper) ->
                        portMapper
                                .http(8080).mapsTo(8080)
                                .http(80).mapsTo(443))
                .requiresChannel()
                .anyRequest()
                .requiresInsecure()
                .and()
                .addFilterAfter(authenticationFilter, LogoutFilter.class)
                .build();
    }

    /**
     * Api filter chain.
     * @return SecurityFilterChain
     */
    @Bean
    @Order(2)
    public SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity,
                                                 AuthenticationManager authenticationManager)
            throws Exception {
        BearerAuthenticationFilter bearerAuthenticationFilter =
                new BearerAuthenticationFilter(authenticationManager, customAuthenticationEntryPoint());
        return httpSecurity
                .securityMatcher(new AntPathRequestMatcher("/**"))
                .cors()
                .and()
                .csrf()
                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .portMapper((portMapper) ->
                        portMapper
                                .http(8080).mapsTo(8080)
                                .http(80).mapsTo(443))
                .requiresChannel()
                .anyRequest()
                .requiresInsecure()
                .and()
                .addFilterAfter(bearerAuthenticationFilter, LogoutFilter.class)
                .build();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(
                daoAuthenticationProvider(),
                new JweAuthenticationProvider(customUserDetailService),
                new AnonymousAuthenticationProvider("anonymous")));
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(true);
        return daoAuthenticationProvider;
    }

    /**
     * user password encoder，One-way encryption
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(PBKDF2_SECRET, 8, 10000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    }
}
