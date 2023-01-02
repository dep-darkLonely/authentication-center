package com.hb.authenticationcenter.security.authority;

/**
 * @author admin
 * @version 1.0
 * @description Authority Constants.
 * @date 2023/1/1
 */
public class Authority {
    /**
     * common authority.
     */
    public class Common {
        /**
         * is authenticated.
         */
        public static final String AUTHENTICATED = "isAuthenticated()";
        /**
         * admin
         */
        public static final String ADMIN = "hasRole('admin')";
    }

    /**
     * user authority.
     */
    public class User {
        /**
         * sys:user:add
         */
        public static final String ADD = "hasAuthority('sys:user:add')";
        /**
         * sys:user:search
         */
        public static final String SEARCH = "hasAuthority('sys:user:search')";
        /**
         * sys:user:delete
         */
        public static final String DELETE = "hasAuthority('sys:user:delete')";
    }

    public class Role {
        /**
         * sys:user:add
         */
        public static final String ADD = "hasAuthority('sys:role:add')";
    }
}
