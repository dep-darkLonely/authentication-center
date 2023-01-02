package com.hb.authenticationcenter.security.matcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.hb.authenticationcenter.security.MultiSecurityFilterChainConfig.LOGIN_URL;

/**
 * @author admin
 * @version 1.0
 * @description custom csrf matcher, add skip request
 * @date 2022/12/18
 */
public final class CustomCsrfMatcher implements RequestMatcher {
    /**
     * skip csrf protected request
     */
    private final Set<String> skipRequests = new HashSet<>(Arrays.asList(LOGIN_URL));
    private final HashSet<String> allowedMethods = new HashSet<>(Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

    @Override
    public boolean matches(HttpServletRequest request) {
        return !(skipRequests.contains(request.getRequestURI())  || this.allowedMethods.contains(request.getMethod()));
    }

    @Override
    public String toString() {
        return "CsrfNotRequired " + this.allowedMethods;
    }
}
