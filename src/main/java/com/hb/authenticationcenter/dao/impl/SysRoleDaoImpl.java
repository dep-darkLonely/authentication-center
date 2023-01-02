package com.hb.authenticationcenter.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.authenticationcenter.dao.SysRoleDao;
import com.hb.authenticationcenter.entity.RoleAuthorityEntity;
import com.hb.authenticationcenter.entity.SysRoleEntity;
import com.hb.authenticationcenter.mapper.SysRoleMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;

/**
 * @author admin
 * @version 1.0
 * @description sys role dao impl.
 * @date 2023/1/2
 */
@Repository
public class SysRoleDaoImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleDao {
    @Override
    public Set<RoleAuthorityEntity> getRolesByUserId(String uId) {
        if (StringUtils.isEmpty(uId)) {
            return Collections.emptySet();
        }
        return ObjectUtils.defaultIfNull(this.baseMapper.queryRolesByUId(uId), Collections.emptySet());
    }
}
