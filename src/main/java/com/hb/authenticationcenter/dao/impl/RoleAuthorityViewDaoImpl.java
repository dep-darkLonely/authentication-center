package com.hb.authenticationcenter.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.authenticationcenter.dao.RoleAuthorityViewDao;
import com.hb.authenticationcenter.entity.RoleAuthorityView;
import com.hb.authenticationcenter.mapper.RoleAuthorityViewMapper;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @version 1.0
 * @description role authority view interface impl.
 * @date 2023/1/1
 */
@Repository
public class RoleAuthorityViewDaoImpl extends ServiceImpl<RoleAuthorityViewMapper, RoleAuthorityView>
        implements RoleAuthorityViewDao {
}
