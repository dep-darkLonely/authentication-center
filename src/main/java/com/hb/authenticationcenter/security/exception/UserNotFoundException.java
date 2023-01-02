package com.hb.authenticationcenter.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author admin
 * @version 1.0
 * @description user not found exception.
 * @date 2022/12/30
 */
public class UserNotFoundException extends AuthenticationException {

    public UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
