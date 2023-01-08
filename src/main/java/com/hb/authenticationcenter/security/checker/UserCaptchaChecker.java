package com.hb.authenticationcenter.security.checker;

import com.hb.authenticationcenter.controller.request.LoginRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0
 * @description user captcha check
 * @date 2022/12/29
 */
@Order(1)
@Component
public class UserCaptchaChecker implements PreAuthenticationChecker {

    @Override
    public void check(LoginRequest loginRequest) {
    }
}
