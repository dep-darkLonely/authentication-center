package com.hb.authenticationcenter.entity;

import com.hb.authenticationcenter.controller.response.RoleResponse;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author admin
 * @version 1.0
 * @description role authority entity.
 * @date 2022/12/18
 */
@Data
@Accessors(chain = true)
public class RoleAuthorityEntity {
    /**
     * role id
     */
    private String id;
    /**
     * role name
     */
    private String name;
    /**
     * role status
     */
    private boolean status;
    /**
     * role built in
     */
    private boolean builtIn;
    /**
     * role authorities
     */
    private Set<AuthorityEntity> authorities;
    /**
     * remark
     */
    private String remark;

    /**
     * get user authorities set.
     * @return authorities set.
     */
    public Set<SimpleGrantedAuthority> getUserAuthorities() {
        return this.authorities == null ? Collections.emptySet() :
                            this.authorities
                                    .stream()
                                    .map(ele -> new SimpleGrantedAuthority(ele.getIdentification()))
                                    .collect(Collectors.toSet());
    }

    /**
     * RoleAuthorityEntity 2 RoleResponse
     * @param roleAuthorityEntity roleAuthorityEntity
     * @return RoleResponse roleResponse
     */
    public static RoleResponse entity2Response(RoleAuthorityEntity roleAuthorityEntity) {
        return RoleResponse.builder()
                .id(roleAuthorityEntity.getId())
                .name(roleAuthorityEntity.getName())
                .builtIn(roleAuthorityEntity.isBuiltIn())
                .status(roleAuthorityEntity.isStatus())
                .remark(roleAuthorityEntity.getRemark())
                .build();
    }
}
