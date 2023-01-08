package com.hb.authenticationcenter.service;

import com.hb.authenticationcenter.controller.response.UserResponse;

/**
 * @author admin
 * @version 1.0
 * @description
 * @date 2023/1/8
 */
public interface TokenService {
    /**
     * refresh token
     * @param oldToken oldToken
     * @return UserResponse
     */
    UserResponse refreshToken(String oldToken);
}
