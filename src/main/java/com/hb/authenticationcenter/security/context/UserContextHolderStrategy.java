package com.hb.authenticationcenter.security.context;

/**
 * UserContextHolderStrategy：
 * Responsible for store, obtain, and clear userContext info.
 * @author admin
 * @version 1.0
 * @description UserContextHolderStrategy
 * @date 2023/1/8
 */
public interface UserContextHolderStrategy {

    /**
     * set user context.
     * @param currentUser authentication user info.
     */
    void setContext(CurrentUser currentUser);

    /**
     * get user information in context.
     * @return CurrentUser current user info.
     */
    CurrentUser getContext();

    /**
     * clear user context
     */
    void clearContext();
}
