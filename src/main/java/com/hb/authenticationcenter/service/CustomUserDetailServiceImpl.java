package com.hb.authenticationcenter.service;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.hb.authenticationcenter.dao.SysRoleDao;
import com.hb.authenticationcenter.dao.SysUserDao;
import com.hb.authenticationcenter.entity.RoleAuthorityEntity;
import com.hb.authenticationcenter.entity.SecurityUserDetails;
import com.hb.authenticationcenter.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.hb.authenticationcenter.common.Constants.LIMIT_1_SQL_SEGAMENT;

/**
 * @author admin
 * @version 1.0
 * @description custom user service
 * @date 2022/12/18
 */
@Slf4j
@Service
@Transactional(readOnly = true, rollbackFor = RuntimeException.class)
public class CustomUserDetailServiceImpl implements UserDetailsService {
    private final SysUserDao userDao;
    private final SysRoleDao sysRoleDao;

    public CustomUserDetailServiceImpl(SysUserDao userDao, SysRoleDao sysRoleDao) {
        this.userDao = userDao;
        this.sysRoleDao = sysRoleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity sysUserEntity = new QueryChainWrapper<>(userDao.getBaseMapper())
                .eq("username", username)
                .last(LIMIT_1_SQL_SEGAMENT)
                .list()
                .stream()
                .findFirst()
                .orElseThrow(() -> {throw new UsernameNotFoundException("user not exist.username=" + username);});
        Set<RoleAuthorityEntity> roles = sysRoleDao.getRolesByUserId(sysUserEntity.getId());
        SecurityUserDetails securityUserDetails = new SecurityUserDetails();
        securityUserDetails.setSysUserEntity(sysUserEntity);
        securityUserDetails.setRoles(roles);
        return securityUserDetails;
    }
}
