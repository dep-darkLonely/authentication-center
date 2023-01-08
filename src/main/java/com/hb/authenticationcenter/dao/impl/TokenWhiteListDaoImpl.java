package com.hb.authenticationcenter.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb.authenticationcenter.dao.TokenWhiteListDao;
import com.hb.authenticationcenter.entity.TokenWhiteListEntity;
import com.hb.authenticationcenter.mapper.TokenWhiteListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @version 1.0
 * @description token white list dao impl.
 * @date 2023/1/4
 */
@Slf4j
@Repository
public class TokenWhiteListDaoImpl extends ServiceImpl<TokenWhiteListMapper, TokenWhiteListEntity>
        implements TokenWhiteListDao {
}
