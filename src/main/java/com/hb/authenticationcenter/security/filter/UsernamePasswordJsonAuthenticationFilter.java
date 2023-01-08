package com.hb.authenticationcenter.security.filter;

import com.hb.authenticationcenter.controller.request.LoginRequest;
import com.hb.authenticationcenter.security.checker.PreAuthenticationChecker;
import com.hb.authenticationcenter.security.context.UserContextHolder;
import com.hb.authenticationcenter.security.context.UserContextHolderStrategy;
import com.hb.authenticationcenter.security.convert.AuthenticationConvert;
import com.hb.authenticationcenter.util.JsonUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.List;

import static com.hb.authenticationcenter.security.MultiSecurityFilterChainConfig.LOGIN_URL;

/**
 * @author admin
 * @version 1.0
 * @description custom username password json format authentication filter
 * @date 2022/12/29
 */
@Slf4j
public class UsernamePasswordJsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(LOGIN_URL,
            "POST");
    private boolean postOnly = true;

    private UserContextHolderStrategy userContextHolderStrategy = UserContextHolder.getUserContextHolderStrategy();

    @Setter
    private List<PreAuthenticationChecker> preAuthenticationCheckers;

    public UsernamePasswordJsonAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public UsernamePasswordJsonAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (this.postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.getContentType())) {
            throw new AuthenticationServiceException("Authentication request content type not supported: " + request.getContentType());
        }
        LoginRequest loginRequest;
        try {
            loginRequest = JsonUtils.MAPPER.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            log.error("json serialization login request failed.");
            loginRequest = new LoginRequest();
        }
        String username = ObjectUtils.defaultIfNull(loginRequest.getUsername(), "").trim();
        String password = ObjectUtils.defaultIfNull(loginRequest.getPassword(), "");
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                password);
        setDetails(request, authRequest);
        preAuthenticationCheck(loginRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.userContextHolderStrategy.setContext(AuthenticationConvert.convert(request, authResult));
        super.successfulAuthentication(request, response, chain, authResult);
    }

    /**
     * After authentication fails, clear the content of user context.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        try {
            super.unsuccessfulAuthentication(request, response, failed);
        } finally {
            this.userContextHolderStrategy.clearContext();
        }
    }

    /**
     * pre check before authentication user.
     * @param model user login request.
     */
    private void preAuthenticationCheck(LoginRequest model) {
        if (ObjectUtils.isEmpty(this.preAuthenticationCheckers)) {
            return;
        }
        this.preAuthenticationCheckers.forEach(item -> item.check(model));
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
