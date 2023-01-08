package com.hb.authenticationcenter.security.provider;

import com.hb.authenticationcenter.entity.TokenWhiteListEntity;
import com.hb.authenticationcenter.security.exception.InvalidTokenException;
import com.hb.authenticationcenter.security.exception.UserNotFoundException;
import com.hb.authenticationcenter.security.token.JweAuthenticationToken;
import com.hb.authenticationcenter.security.token.persistent.PersistentTokenRepository;
import com.hb.authenticationcenter.util.JweUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Optional;

/**
 * @author admin
 * @version 1.0
 * @description Jwe Authentication Provider
 * @date 2022/12/30
 */
@Slf4j
public class JweAuthenticationProvider implements AuthenticationProvider {
    @Setter
    private UserDetailsChecker authenticationCheck = new AccountStatusUserDetailsChecker();
    @Setter
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final UserDetailsService userService;
    private final PersistentTokenRepository persistentTokenRepository;

    public JweAuthenticationProvider(UserDetailsService userService,PersistentTokenRepository persistentTokenRepository) {
        Assert.notNull(userService, "userDetailService can not blank.");
        this.userService = userService;
        this.persistentTokenRepository = persistentTokenRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        if (StringUtils.isEmpty(token)) {
            log.error("token not exist.");
            throw new InvalidTokenException("token not exist.");
        }
        // get the stored token from the database first.
        TokenWhiteListEntity tokenEntity = Optional.ofNullable(persistentTokenRepository.getTokenEntity(token))
                .orElseThrow(() -> {
                    log.error("invalid token.");
                    throw new InvalidTokenException("invalid token.");
                });
        if (tokenEntity.getDate().getTime() < System.currentTimeMillis()) {
            log.error("token already expired.");
            throw new CredentialsExpiredException("token already expired.");
        }
        // refresh user information.
        try {
            UserDetails userDetails = userService.loadUserByUsername(tokenEntity.getUsername());
            this.authenticationCheck.check(userDetails);
            return createSuccessAuthentication(userDetails);
        } catch (UsernameNotFoundException ex) {
            log.error("username={} user not found.", tokenEntity.getUsername());
            throw new UserNotFoundException("user not found.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JweAuthenticationToken.class.isAssignableFrom(authentication));
    }

    protected Authentication createSuccessAuthentication(UserDetails user) {
        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(user,
                null, this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(user);
        return result;
    }

    /**
     * resolve the token carried in the request header
     * @param token header token
     * @return TokenWhiteListEntity
     */
    private TokenWhiteListEntity resolveRequestToken(String token) {
        return Optional.ofNullable(JweUtils.resolveToken(token))
                .map(userResponse -> new TokenWhiteListEntity()
                        .setUsername(userResponse.getUsername())
                        .setToken(userResponse.getToken())
                        .setDate(new Date(userResponse.getExpireTime())))
                .orElseThrow(() -> {
                            log.error("resolve token failed.");
                            throw new InvalidTokenException("resolve token failed.");
                        }
                );
    }
}
