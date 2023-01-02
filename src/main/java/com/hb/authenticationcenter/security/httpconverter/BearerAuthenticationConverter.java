package com.hb.authenticationcenter.security.httpconverter;

import com.hb.authenticationcenter.security.exception.InvalidTokenException;
import com.hb.authenticationcenter.security.token.JweAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author admin
 * @version 1.0
 * @description Bearer Authentication Converter.
 * @date 2022/12/30
 */
public class BearerAuthenticationConverter implements AuthenticationConverter {

    public static final String AUTHENTICATION_SCHEME_BEARER = "Bearer";

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

    private Charset credentialsCharset = StandardCharsets.UTF_8;

    public BearerAuthenticationConverter() {
        this(new WebAuthenticationDetailsSource());
    }

    public BearerAuthenticationConverter(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public Charset getCredentialsCharset() {
        return this.credentialsCharset;
    }

    public void setCredentialsCharset(Charset credentialsCharset) {
        this.credentialsCharset = credentialsCharset;
    }

    public AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
        return this.authenticationDetailsSource;
    }

    public void setAuthenticationDetailsSource(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    @Override
    public JweAuthenticationToken convert(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            return null;
        }
        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BEARER)) {
            return null;
        }
        if (header.equalsIgnoreCase(AUTHENTICATION_SCHEME_BEARER)) {
            throw new InvalidTokenException("Empty bearer authentication token.");
        }
        String token = header.substring(AUTHENTICATION_SCHEME_BEARER.length()).trim();
        if (token.length() == 0) {
            throw new InvalidTokenException("Invalid bearer authentication token.");
        }
        return JweAuthenticationToken.unauthenticated(token);
    }
}
