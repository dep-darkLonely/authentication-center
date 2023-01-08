package com.hb.authenticationcenter.security.context;

import com.hb.authenticationcenter.controller.response.RoleResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @author admin
 * @version 1.0
 * @description current user info
 * @date 2023/1/8
 */
@Getter
@Setter
@Accessors(chain = true)
public class CurrentUser {
    /**
     * user id
     */
    private String id;
    /**
     * username/account
     */
    private String username;
    /**
     * name
     */
    private String name;
    /**
     * wechat
     */
    private String wechatId;
    /**
     * client name
     */
    private String clientName;
    /**
     * current user role
     */
    private Set<RoleResponse> roles;
    /**
     * role permission
     */
    private Set<String> permissions;
    /**
     * request ip
     */
    private String ip;
}
