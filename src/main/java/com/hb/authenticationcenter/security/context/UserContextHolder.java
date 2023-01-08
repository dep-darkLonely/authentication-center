package com.hb.authenticationcenter.security.context;

/**
 * UserContextHolder:
 * holder of userContext.
 * @author admin
 * @version 1.0
 * @description user context Holder
 * @date 2023/1/8
 */
public final class UserContextHolder {

    private static UserContextHolderStrategy userContextHolderStrategy;

    static {
        userContextHolderStrategy = new ThreadLocalUserContextStrategy();
    }

    /**
     * get user context holder strategy
     * @return UserContextHolderStrategy
     */
    public static UserContextHolderStrategy getUserContextHolderStrategy() {
        return userContextHolderStrategy;
    }

    /**
     * get user context
     * @return CurrentUser
     */
    public static CurrentUser getUserContext() {
        return userContextHolderStrategy.getContext();
    }
}
