package com.hb.authenticationcenter.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.authenticationcenter.dao.SysUserDao;
import com.hb.authenticationcenter.entity.SysUserEntity;
import com.hb.authenticationcenter.mapper.SysUserMapper;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @version 1.0
 * @description
 * @date 2022/12/18
 */
@Repository
public class SysUserDaoImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserDao {
}
