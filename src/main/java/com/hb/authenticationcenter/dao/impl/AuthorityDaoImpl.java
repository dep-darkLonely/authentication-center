package com.hb.authenticationcenter.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.authenticationcenter.dao.AuthorityDao;
import com.hb.authenticationcenter.entity.AuthorityEntity;
import com.hb.authenticationcenter.mapper.AuthorityMapper;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @version 1.0
 * @description sys authority dao impl.
 * @date 2023/1/2
 */
@Repository
public class AuthorityDaoImpl extends ServiceImpl<AuthorityMapper, AuthorityEntity> implements AuthorityDao {
}
