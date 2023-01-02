package com.hb.authenticationcenter.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hb.authenticationcenter.entity.RoleAuthorityEntity;
import com.hb.authenticationcenter.entity.SysRoleEntity;

import java.util.Set;

/**
 * 角色表(sys_role)数据DAO
 *
 * @author admin
 * @since 2023-01-02 12:03:54
 * @description sys role dao interface.
 */
public interface SysRoleDao extends IService<SysRoleEntity> {
    /**
     * get user role information by user id.
     * @param uId user id
     * @return Set<RoleAuthorityEntity> role info set.
     */
    Set<RoleAuthorityEntity> getRolesByUserId(String uId);
}