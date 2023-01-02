package com.hb.authenticationcenter.security.checker;

import com.hb.authenticationcenter.controller.request.LoginRequest;

/**
 * @author admin
 * @version 1.0
 * @description login request body check before perform user authentication.
 * @date 2022/12/29
 */
public interface PreAuthenticationChecker {
    /**
     * login request body check before perform user authentication
     * @param loginRequest
     */
    void check(LoginRequest loginRequest);
}
