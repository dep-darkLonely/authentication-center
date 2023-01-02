package com.hb.authenticationcenter.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author admin
 * @version 1.0
 * @description 用户详细信息类
 * @date 2022/12/18
 */
@Setter
@Getter
@Accessors(chain = true)
public class SecurityUserDetails implements UserDetails {

    private SysUserEntity sysUserEntity;

    private boolean accountExpired;

    private boolean credentialsExpired;

    private Set<RoleAuthorityEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        Optional.ofNullable(this.roles).ifPresent(
            elements -> {
                for (RoleAuthorityEntity element : elements) {
                    if (!element.isStatus()) {
                        continue;
                    }
                    authoritySet.addAll(element.getUserAuthorities());
                }
            }
        );
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return this.sysUserEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.sysUserEntity.getUsername();
    }

    /**
     * 账户永不过期
     * @return boolean true：永不过期, false:过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.sysUserEntity.isLocked();
    }

    /**
     * 密码永不过期
     * @return boolean true：永不过期，false：过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.sysUserEntity.isStatus();
    }
}
