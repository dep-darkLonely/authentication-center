package com.hb.authenticationcenter.security.checker;

import com.hb.authenticationcenter.controller.request.LoginRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0
 * @description multi factor authentication.
 * @date 2022/12/29
 */
@Order(2)
@Component
public class UserMFAChecker implements PreAuthenticationChecker {

    @Override
    public void check(LoginRequest loginRequest) {
    }
}
