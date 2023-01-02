package com.hb.authenticationcenter.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.authenticationcenter.dao.SysAuthorityDao;
import com.hb.authenticationcenter.entity.SysAuthorityEntity;
import com.hb.authenticationcenter.mapper.SysAuthorityMapper;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @version 1.0
 * @description sys authority dao impl.
 * @date 2023/1/2
 */
@Repository
public class SysAuthorityDaoImpl extends ServiceImpl<SysAuthorityMapper, SysAuthorityEntity> implements SysAuthorityDao {
}
