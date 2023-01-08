package com.hb.authenticationcenter.exception;

/**
 * @author admin
 * @version 1.0
 * @description  Custom BadRequestException.
 * @date 2023/1/5
 */
public class BadRequestException extends RuntimeException {
    /**
     * error code
     */
    private String resource;

    public BadRequestException(String message, String resource) {
        super(message);
        this.resource = resource;
    }
}
