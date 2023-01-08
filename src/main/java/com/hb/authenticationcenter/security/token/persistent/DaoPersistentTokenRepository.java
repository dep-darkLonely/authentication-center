package com.hb.authenticationcenter.security.token.persistent;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.hb.authenticationcenter.dao.TokenWhiteListDao;
import com.hb.authenticationcenter.entity.TokenWhiteListEntity;
import com.hb.authenticationcenter.exception.BadRequestException;
import com.hb.authenticationcenter.security.config.LoginConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Optional;

import static com.hb.authenticationcenter.common.ErrorCode.ERROR_CREATE_TOKEN_FAILED_ERROR;

/**
 * @author admin
 * @version 1.0
 * @description Persistence Token
 * @date 2023/1/4
 */
@Slf4j
@Component
public class DaoPersistentTokenRepository implements PersistentTokenRepository, MessageSourceAware {
    private final TokenWhiteListDao tokenDao;
    private final LoginConfig loginConfig;
    private MessageSourceAccessor messages;

    public DaoPersistentTokenRepository(TokenWhiteListDao tokenDao, LoginConfig loginConfig) {
        Assert.notNull(tokenDao, "sysTokenWhiteListDao can not null.");
        this.tokenDao = tokenDao;
        this.loginConfig = loginConfig;
    }

    @Override
    public void createToken(TokenWhiteListEntity token) {
        QueryWrapper<TokenWhiteListEntity> deleteWrapper = new QueryWrapper<TokenWhiteListEntity>()
                .le("date", new Date())
                .or()
                .eq(!loginConfig.getMultipleLogin(), "username", token.getUsername());
        tokenDao.remove(deleteWrapper);
        if (!tokenDao.save(token)) {
            log.error("save username={} token failed.", token.getUsername());
            throw new BadRequestException(this.messages.getMessage(ERROR_CREATE_TOKEN_FAILED_ERROR),
                    "ERROR_CREATE_TOKEN_FAILED_ERROR");
        }
    }

    @Override
    public void updateTokenByOldToken(String oldToken, String tokenValue, Date date) {
        Optional.ofNullable(this.getTokenEntity(oldToken))
                .ifPresent(entity -> {
                    entity.setToken(tokenValue).setDate(date);
                    tokenDao.updateById(entity);
                });
    }

    @Override
    public TokenWhiteListEntity getTokenEntity(String oldToken) {
        return new QueryChainWrapper<>(tokenDao.getBaseMapper())
                .eq("token", oldToken)
                .list()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removeTokenByUsername(String username) {
        tokenDao.remove(new QueryWrapper<TokenWhiteListEntity>().eq("username", username));
    }

    @Override
    public void removeTokenByOldToken(String oldToken) {
        tokenDao.remove(new QueryWrapper<TokenWhiteListEntity>()
                .eq("token", oldToken));
    }

    @Override
    public void removeExpiredToken() {
        tokenDao.remove(new QueryWrapper<TokenWhiteListEntity>().le("date", new Date()));
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource must be set.");
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
