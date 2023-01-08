package com.hb.authenticationcenter.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @version 1.0
 * @description user login config
 * @date 2023/1/7
 */
@Setter
@Getter
@Component
public class LoginConfig {

    @Value("${user.login.multiple-logins: false}")
    private Boolean multipleLogin;

    @Value("${user.login.token-expire-time: 3600}")
    private Integer tokenExpireTime;
}
