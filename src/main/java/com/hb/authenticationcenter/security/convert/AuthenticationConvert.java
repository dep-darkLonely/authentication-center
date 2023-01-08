package com.hb.authenticationcenter.security.convert;

import com.hb.authenticationcenter.controller.response.RoleResponse;
import com.hb.authenticationcenter.entity.RoleAuthorityEntity;
import com.hb.authenticationcenter.entity.SecurityUserDetails;
import com.hb.authenticationcenter.entity.UserEntity;
import com.hb.authenticationcenter.security.context.CurrentUser;
import com.hb.authenticationcenter.util.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;

/**
 * @author admin
 * @version 1.0
 * @description
 * @date 2023/1/8
 */
public class AuthenticationConvert {

    /**
     * convert authentication info to CurrentUser
     * @param request HttpServletRequest
     * @param authentication authentication information
     * @return CurrentUser current user info.
     */
    public static CurrentUser convert(HttpServletRequest request, Authentication authentication) {
        Assert.notNull(authentication, "authentication info can not null.");
        SecurityUserDetails principal = (SecurityUserDetails) Optional.ofNullable(authentication.getPrincipal())
                .orElse(new SecurityUserDetails());
        UserEntity user = Optional.ofNullable(principal.getUserEntity()).orElse(new UserEntity());
        Convert<GrantedAuthority, String> authorityStringConvert = principal::getAuthorities;
        Set<String> permissions = authorityStringConvert.convert(GrantedAuthority::getAuthority);
        Convert<RoleAuthorityEntity, RoleResponse> entity2RespConvert = principal::getRoles;
        Set<RoleResponse> roles = entity2RespConvert.convert(RoleAuthorityEntity::entity2Response);
        return new CurrentUser()
                .setId(user.getId())
                .setName(user.getName())
                .setUsername(principal.getUsername())
                .setRoles(roles)
                .setPermissions(permissions)
                .setClientName(user.getClientName())
                .setIp(IpUtils.resolveRequestIp(request));
    }
}
