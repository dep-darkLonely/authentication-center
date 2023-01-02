package com.hb.authenticationcenter.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.Set;

/**
 * @author admin
 * @version 1.0
 * @description user response.
 * @date 2022/12/29
 */
@Data
@Builder
@JsonView(SimpleView.class)
public class UserResponse {

    private String id;

    private String username;

    private String name;

    @JsonProperty("client_name")
    private String clientName;

    @JsonProperty("is_rememberme")
    private boolean isRememberme;

    @JsonProperty("expire_time")
    private long expireTime;

    private String token;

    @JsonProperty("roles")
    private Set<RoleResponse> roles;

    @JsonProperty("permissions")
    private Set<String> permissions;
}
