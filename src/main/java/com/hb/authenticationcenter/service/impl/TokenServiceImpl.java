package com.hb.authenticationcenter.service.impl;

import com.hb.authenticationcenter.controller.response.UserResponse;
import com.hb.authenticationcenter.entity.SecurityUserDetails;
import com.hb.authenticationcenter.entity.UserEntity;
import com.hb.authenticationcenter.security.config.LoginConfig;
import com.hb.authenticationcenter.security.context.CurrentUser;
import com.hb.authenticationcenter.security.context.UserContextHolder;
import com.hb.authenticationcenter.security.token.persistent.PersistentTokenRepository;
import com.hb.authenticationcenter.service.TokenService;
import com.hb.authenticationcenter.util.JweUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Token Service Impl:
 * responsible for token refresh.
 * @author admin
 * @version 1.0
 * @description TokenServiceImpl
 * @date 2023/1/8
 */
@Slf4j
@Service
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
public class TokenServiceImpl implements TokenService {

    private final PersistentTokenRepository persistentTokenRepository;

    private final LoginConfig config;

    public TokenServiceImpl(PersistentTokenRepository persistentTokenRepository, LoginConfig config) {
        this.persistentTokenRepository = persistentTokenRepository;
        this.config = config;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UserResponse refreshToken(String oldToken) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        SecurityUserDetails userDetails = (SecurityUserDetails) Optional.ofNullable(authentication.getDetails()).orElse(new SecurityUserDetails());
        UserEntity userEntity = Optional.ofNullable(userDetails.getUserEntity()).orElse(new UserEntity());
        long time = config.generateTokenExpireTime();
        String newToken = JweUtils.createToken(userDetails, time);
        persistentTokenRepository.updateTokenByOldToken(oldToken, newToken, new Date(time));
        CurrentUser currentUser = UserContextHolder.getUserContext();
        return UserResponse.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .clientName(userEntity.getClientName())
                .permissions(currentUser.getPermissions())
                .roles(currentUser.getRoles())
                .token(newToken)
                .expireTime(time)
                .build();
    }
}
