package com.hb.authenticationcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hb.authenticationcenter.entity.RoleAuthorityEntity;
import com.hb.authenticationcenter.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 角色表(sys_role)数据Mapper
 *
 * @author admin
 * @since 2023-01-02 12:03:54
 * @description 
*/
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {
    /**
     * search special user role info by user id.
     * @param uId user id.
     * @return Set<RoleAuthorityEntity> user role infos.
     */
    Set<RoleAuthorityEntity> queryRolesByUId(@Param("uId") String uId);
}
