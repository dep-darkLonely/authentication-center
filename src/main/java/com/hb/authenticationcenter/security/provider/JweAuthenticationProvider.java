package com.hb.authenticationcenter.security.provider;

import com.hb.authenticationcenter.controller.response.UserResponse;
import com.hb.authenticationcenter.security.exception.InvalidTokenException;
import com.hb.authenticationcenter.security.exception.UserNotFoundException;
import com.hb.authenticationcenter.security.token.JweAuthenticationToken;
import com.hb.authenticationcenter.util.JweUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

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

    public JweAuthenticationProvider(UserDetailsService userService) {
        Assert.notNull(userService, "userDetailService can not blank.");
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        if (StringUtils.isEmpty(token)) {
            log.error("token not exist.");
            throw new InvalidTokenException("token not exist.");
        }
        UserResponse userResponse = Optional.ofNullable(JweUtils.resolveToken(token))
                .orElseThrow(() -> {
                            log.error("resolve token failed.");
                            throw new InvalidTokenException("resolve token failed.");
                        }
                );
        if (userResponse.getExpireTime() < System.currentTimeMillis()) {
            log.error("token already expired.");
            throw new CredentialsExpiredException("token already expired.");
        }
        // refresh user information.
        try {
            UserDetails userDetails = userService.loadUserByUsername(userResponse.getUsername());
            this.authenticationCheck.check(userDetails);
            return createSuccessAuthentication(userDetails.getUsername(), userDetails);
        } catch (UsernameNotFoundException ex) {
            log.error("username={} user not found.", userResponse.getUsername());
            throw new UserNotFoundException("user not found.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JweAuthenticationToken.class.isAssignableFrom(authentication));
    }

    protected Authentication createSuccessAuthentication(Object principal, UserDetails user) {
        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(principal,
                null, this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(null);
        return result;
    }
}
