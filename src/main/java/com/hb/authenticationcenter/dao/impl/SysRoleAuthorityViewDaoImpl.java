package com.hb.authenticationcenter.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.authenticationcenter.dao.SysRoleAuthorityViewDao;
import com.hb.authenticationcenter.entity.SysRoleAuthorityView;
import com.hb.authenticationcenter.mapper.SysRoleAuthorityViewMapper;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @version 1.0
 * @description role authority view interface impl.
 * @date 2023/1/1
 */
@Repository
public class SysRoleAuthorityViewDaoImpl extends ServiceImpl<SysRoleAuthorityViewMapper, SysRoleAuthorityView>
        implements SysRoleAuthorityViewDao {
}
