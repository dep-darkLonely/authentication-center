package com.hb.authenticationcenter.security.handler;

import com.hb.authenticationcenter.common.ResponseResult;
import com.hb.authenticationcenter.controller.response.DefaultView;
import com.hb.authenticationcenter.controller.response.RoleResponse;
import com.hb.authenticationcenter.controller.response.SimpleView;
import com.hb.authenticationcenter.controller.response.UserResponse;
import com.hb.authenticationcenter.entity.RoleAuthorityEntity;
import com.hb.authenticationcenter.entity.SecurityUserDetails;
import com.hb.authenticationcenter.entity.SysUserEntity;
import com.hb.authenticationcenter.util.JsonUtils;
import com.hb.authenticationcenter.util.JweUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hb.authenticationcenter.common.Constants.DEFAULT_CHARSET;
import static com.hb.authenticationcenter.util.JweUtils.JWT_EXPIRE_TIME;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.pac4j.core.context.HttpConstants.AUTHORIZATION_HEADER;
import static org.pac4j.core.context.HttpConstants.BEARER_HEADER_PREFIX;

/**
 * @author admin
 * @version 1.0
 * @description authentication success handler
 * @date 2022/12/29
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // get current user userDetails
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        // permission
        Set<String> permissions = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        // roles
        Set<RoleResponse> roles = userDetails.getRoles()
                .stream()
                .map(RoleAuthorityEntity::entity2Response)
                .collect(Collectors.toSet());
        SysUserEntity sysUserEntity = userDetails.getSysUserEntity();
        long time = System.currentTimeMillis() + JWT_EXPIRE_TIME;
        String token = JweUtils.createToken(userDetails, time);
        UserResponse userResponse = UserResponse.builder()
                .id(sysUserEntity.getId())
                .username(sysUserEntity.getUsername())
                .name(sysUserEntity.getName())
                .clientName(sysUserEntity.getClientName())
                .expireTime(time)
                .token(token)
                .permissions(permissions)
                .roles(roles)
                .build();
        ResponseResult responseResult = ResponseResult.success(userResponse);
        response.setCharacterEncoding(DEFAULT_CHARSET);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(AUTHORIZATION_HEADER, BEARER_HEADER_PREFIX + token);
        response.setStatus(SC_OK);
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtils.object2String(SimpleView.class, responseResult));
        writer.flush();
        writer.close();
    }
}
