package com.hb.authenticationcenter.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.authenticationcenter.dao.UserDao;
import com.hb.authenticationcenter.entity.UserEntity;
import com.hb.authenticationcenter.mapper.UserMapper;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @version 1.0
 * @description
 * @date 2022/12/18
 */
@Repository
public class UserDaoImpl extends ServiceImpl<UserMapper, UserEntity> implements UserDao {
}
