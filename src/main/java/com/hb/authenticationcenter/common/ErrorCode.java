package com.hb.authenticationcenter.common;

import org.bouncycastle.pqc.crypto.newhope.NHSecretKeyProcessor;

/**
 * @author admin
 * @version 1.0
 * @description Error Code
 * @date 2023/1/1
 */
public class ErrorCode {
    /**
     * Credentials Expired
     */
    public static final String ERROR_CREDENTIALS_EXPIRED = "ERROR_CREDENTIALS_EXPIRED";
    /**
     * Username or Password Error
     */
    public static final String ERROR_USERNAME_OR_PASSWORD_ERROR = "ERROR_USERNAME_OR_PASSWORD_ERROR";
    /**
     * Bad Credentials
     */
    public static final String ERROR_BAD_CREDENTIALS = "ERROR_BAD_CREDENTIALS";
    /**
     * User Locked
     */
    public static final String ERROR_USER_LOCKED = "ERROR_USER_LOCKED";
    /**
     * User Disabled
     */
    public static final String ERROR_USER_DISABLED = "ERROR_USER_DISABLED";
    /**
     * Captcha Error.
     */
    public static final String ERROR_CAPTCHA_ERROR = "ERROR_CAPTCHA_ERROR";
}
