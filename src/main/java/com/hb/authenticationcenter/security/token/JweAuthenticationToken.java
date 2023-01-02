package com.hb.authenticationcenter.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.io.Serial;

/**
 * @author admin
 * @version 1.0
 * @description jwe authentication token
 * @date 2022/12/30
 */
public class JweAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private Object credentials;

    public JweAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    /**
     * This factory method can be safely used by any code that wishes to create a
     * unauthenticated <code>JweAuthenticationToken</code>.
     * @param principal
     * @param credentials
     * @return JweAuthenticationToken with false isAuthenticated() result
     *
     * @since 5.7
     */
    public static JweAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new JweAuthenticationToken(principal, credentials);
    }
    /**
     * This factory method can be safely used by any code that wishes to create a
     * unauthenticated <code>JweAuthenticationToken</code>.
     * @param credentials
     * @return JweAuthenticationToken with false isAuthenticated() result
     *
     * @since 5.7
     */
    public static JweAuthenticationToken unauthenticated(Object credentials) {
        return new JweAuthenticationToken(null, credentials);
    }
}
