package com.hb.authenticationcenter.security.handler;

import com.hb.authenticationcenter.common.ResponseResult;
import com.hb.authenticationcenter.controller.response.RoleResponse;
import com.hb.authenticationcenter.controller.response.SimpleView;
import com.hb.authenticationcenter.controller.response.UserResponse;
import com.hb.authenticationcenter.entity.RoleAuthorityEntity;
import com.hb.authenticationcenter.entity.SecurityUserDetails;
import com.hb.authenticationcenter.entity.TokenWhiteListEntity;
import com.hb.authenticationcenter.entity.UserEntity;
import com.hb.authenticationcenter.security.config.LoginConfig;
import com.hb.authenticationcenter.security.convert.Convert;
import com.hb.authenticationcenter.security.token.persistent.PersistentTokenRepository;
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
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static com.hb.authenticationcenter.common.Constants.DEFAULT_CHARSET;
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

    private final PersistentTokenRepository tokenRepository;

    private final LoginConfig config;

    public CustomAuthenticationSuccessHandler(PersistentTokenRepository tokenRepository, LoginConfig config) {
        Assert.notNull(tokenRepository, "tokenRepository must be set.");
        this.tokenRepository = tokenRepository;
        this.config = config;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        long time = System.currentTimeMillis() + config.getTokenExpireTime();
        String token = JweUtils.createToken(userDetails, time);
        UserResponse userResponse = buildUserResponse(userDetails);
        userResponse.setToken(token);
        userResponse.setExpireTime(time);
        ResponseResult responseResult = ResponseResult.success(userResponse);
        TokenWhiteListEntity entity = buildTokenWhiteListEntity(userResponse.getUsername(), token, time);
        tokenRepository.createToken(entity);
        response.setCharacterEncoding(DEFAULT_CHARSET);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(AUTHORIZATION_HEADER, BEARER_HEADER_PREFIX + token);
        response.setStatus(SC_OK);
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtils.object2String(SimpleView.class, responseResult));
        writer.flush();
        writer.close();
    }

    /**
     * build tokenWhiteList object
     * @param username the account of the authenticated user
     * @param token generated token
     * @param time token expiration time
     * @return SysTokenWhiteListEntity
     */
    private TokenWhiteListEntity buildTokenWhiteListEntity(String username, String token, long time) {
        return new TokenWhiteListEntity()
                .setUsername(username)
                .setToken(token)
                .setDate(new Date(time));
    }

    /**
     * build UserResponse by UserDetails.
     * @param userDetails User information of authenticated users
     * @return UserResponse
     */
    private UserResponse buildUserResponse(SecurityUserDetails userDetails) {
        Assert.notNull(userDetails, "userDetails can not null");
        Convert<GrantedAuthority, String> authorityStringConvert = userDetails::getAuthorities;
        Set<String> permissions = authorityStringConvert.convert(GrantedAuthority::getAuthority);
        Convert<RoleAuthorityEntity, RoleResponse> entity2RespConvert = userDetails::getRoles;
        Set<RoleResponse> roles = entity2RespConvert.convert(RoleAuthorityEntity::entity2Response);
        UserEntity userEntity = userDetails.getUserEntity();
        return UserResponse.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .clientName(userEntity.getClientName())
                .permissions(permissions)
                .roles(roles)
                .build();
    }
}
