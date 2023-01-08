package com.hb.authenticationcenter.security.context;

import java.util.Objects;
import java.util.Optional;

/**
 * UserContext:
 * context is responsible for storing authenticated user information.
 * @author admin
 * @version 1.0
 * @description UserContext.
 * @date 2023/1/8
 */
public class ThreadLocalUserContextStrategy implements UserContextHolderStrategy {

    private static final ThreadLocal<CurrentUser> CONTEXT = new ThreadLocal<>();

    /**
     * set user context.
     * @param user authentication user info.
     */
    public void setContext(CurrentUser user) {
        Objects.requireNonNull(user);
        CONTEXT.set(user);
    }

    /**
     * get user information in context.
     * @return CurrentUser current user info.
     */
    public CurrentUser getContext() {
        return Optional.ofNullable(CONTEXT.get()).orElse(new CurrentUser());
    }

    /**
     * get current user id
     * @return user id
     */
    public String getCurrentUserId() {
        return getContext().getId();
    }

    /**
     * clear user context
     */
    public void clearContext() {
        CONTEXT.remove();
    }

    public static UserContextHolderStrategy getHolderStrategy() {
        return new ThreadLocalUserContextStrategy();
    }
}
