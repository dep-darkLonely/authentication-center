package com.hb.authenticationcenter.security.filter;

import com.hb.authenticationcenter.security.context.UserContextHolder;
import com.hb.authenticationcenter.security.context.UserContextHolderStrategy;
import com.hb.authenticationcenter.security.convert.AuthenticationConvert;
import com.hb.authenticationcenter.security.exception.InvalidTokenException;
import com.hb.authenticationcenter.security.httpconverter.BearerAuthenticationConverter;
import com.hb.authenticationcenter.security.token.JweAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author admin
 * @version 1.0
 * @description jwe authentication filter
 * @date 2022/12/30
 */
public class BearerAuthenticationFilter extends OncePerRequestFilter {

    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    private UserContextHolderStrategy userContextHolderStrategy = UserContextHolder.getUserContextHolderStrategy();

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AuthenticationManager authenticationManager;

    private RememberMeServices rememberMeServices = new NullRememberMeServices();

    private boolean ignoreFailure = false;

    private String credentialsCharset = "UTF-8";

    private final BearerAuthenticationConverter authenticationConverter = new BearerAuthenticationConverter();

    private SecurityContextRepository securityContextRepository = new RequestAttributeSecurityContextRepository();
    /**
     * Creates an instance which will authenticate against the supplied
     * {@code AuthenticationManager} and use the supplied {@code AuthenticationEntryPoint}
     * to handle authentication failures.
     * @param authenticationManager the bean to submit authentication requests to
     * @param authenticationEntryPoint will be invoked when authentication fails.
     * Typically an instance of {@link BasicAuthenticationEntryPoint}.
     */
    public BearerAuthenticationFilter(AuthenticationManager authenticationManager,
                                     AuthenticationEntryPoint authenticationEntryPoint) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null");
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * Sets the {@link SecurityContextRepository} to save the {@link SecurityContext} on
     * authentication success. The default action is not to save the
     * {@link SecurityContext}.
     * @param securityContextRepository the {@link SecurityContextRepository} to use.
     * Cannot be null.
     */
    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        Assert.notNull(securityContextRepository, "securityContextRepository cannot be null");
        this.securityContextRepository = securityContextRepository;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.authenticationManager, "An AuthenticationManager is required");
        if (!isIgnoreFailure()) {
            Assert.notNull(this.authenticationEntryPoint, "An AuthenticationEntryPoint is required");
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            JweAuthenticationToken authRequest = this.authenticationConverter.convert(request);
            if (authRequest == null) {
                this.logger.error("Token not exist in current request.");
                throw new InvalidTokenException("token not exist.");
            }
            Authentication authResult = this.authenticationManager.authenticate(authRequest);
            SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authResult);
            this.securityContextHolderStrategy.setContext(context);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
            }
            this.rememberMeServices.loginSuccess(request, response, authResult);
            this.securityContextRepository.saveContext(context, request, response);
            this.userContextHolderStrategy.setContext(AuthenticationConvert.convert(request, authResult));
            onSuccessfulAuthentication(request, response, authResult);
        }
        catch (AuthenticationException ex) {
            this.securityContextHolderStrategy.clearContext();
            this.logger.debug("Failed to process authentication request", ex);
            this.rememberMeServices.loginFail(request, response);
            onUnsuccessfulAuthentication(request, response, ex);
            this.authenticationEntryPoint.commence(request, response, ex);
            this.userContextHolderStrategy.clearContext();
            return;
        }
        chain.doFilter(request, response);
        this.userContextHolderStrategy.clearContext();
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              Authentication authResult) throws IOException {
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException failed) throws IOException {
    }

    protected boolean isIgnoreFailure() {
        return this.ignoreFailure;
    }

    /**
     * Sets the {@link SecurityContextHolderStrategy} to use. The default action is to use
     * the {@link SecurityContextHolderStrategy} stored in {@link SecurityContextHolder}.
     *
     * @since 5.8
     */
    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        Assert.notNull(securityContextHolderStrategy, "securityContextHolderStrategy cannot be null");
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    public void setAuthenticationDetailsSource(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationConverter.setAuthenticationDetailsSource(authenticationDetailsSource);
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        Assert.notNull(rememberMeServices, "rememberMeServices cannot be null");
        this.rememberMeServices = rememberMeServices;
    }

    public void setCredentialsCharset(String credentialsCharset) {
        Assert.hasText(credentialsCharset, "credentialsCharset cannot be null or empty");
        this.credentialsCharset = credentialsCharset;
        this.authenticationConverter.setCredentialsCharset(Charset.forName(credentialsCharset));
    }

    protected String getCredentialsCharset(HttpServletRequest httpRequest) {
        return this.credentialsCharset;
    }

    /**
     * set user context holder strategy.
     * @param userContextHolderStrategy user context holder strategy
     */
    public void setUserContextHolderStrategy(UserContextHolderStrategy userContextHolderStrategy) {
        this.userContextHolderStrategy = userContextHolderStrategy;
    }
}
