package com.hb.authenticationcenter.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author admin
 * @version 1.0
 * @description Captcha Exception.
 * @date 2023/1/2
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CaptchaException(String msg) {
        super(msg);
    }
}
