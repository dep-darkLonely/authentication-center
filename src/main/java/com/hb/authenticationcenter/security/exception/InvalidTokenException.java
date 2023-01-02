package com.hb.authenticationcenter.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author admin
 * @version 1.0
 * @description Invalid Token Exception.
 * @date 2023/1/2
 */
public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
