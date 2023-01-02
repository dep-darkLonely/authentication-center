package com.hb.authenticationcenter.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @version 1.0
 * @description
 * @date 2022/12/29
 */
@Setter
@Getter
public class LoginRequest {

    private String username;

    private String password;

    private String captcha;

    private Boolean rememberme = false;
}
