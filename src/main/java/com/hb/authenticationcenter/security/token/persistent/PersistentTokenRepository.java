package com.hb.authenticationcenter.security.token.persistent;

import com.hb.authenticationcenter.entity.TokenWhiteListEntity;

import java.util.Date;

/**
 * @author admin
 * @version 1.0
 * @description persistent token repository.
 * @date 2023/1/4
 */
public interface PersistentTokenRepository {
    /**
     * create token
     * @param token user credentials token
     */
    void createToken(TokenWhiteListEntity token);
    /**
     * update token by oldToken
     * @param oldToken old token
     * @param tokenValue user credentials token
     * @param date token create time
     */
    void updateTokenByOldToken(String oldToken, String tokenValue, Date date);

    /**
     * get token by oldToken
     * @param oldToken old token
     * @return SysTokenWhiteListEntity
     */
    TokenWhiteListEntity getTokenEntity(String oldToken);

    /**
     * remove token by username
     * @param username username
     */
    void removeTokenByUsername(String username);

    /**
     * remove tokens by username
     * @param oldToken old token
     */
    void removeTokenByOldToken(String oldToken);

    /**
     * remove expired token
     */
    void removeExpiredToken();
}