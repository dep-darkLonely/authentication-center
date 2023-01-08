package com.hb.authenticationcenter.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Objects;

/**
 * Spring Application Context
 * Responsible for obtaining the Bean object in the Spring IOC container.
 * @author admin
 * @version 1.0
 * @description Spring Application Context.
 * @date 2023/1/8
 */
public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Objects.requireNonNull(applicationContext);
        SpringApplicationContext.applicationContext = applicationContext;
    }

    /**
     * Get Spring IOC Bean Object by class type.
     * @param clazz class type
     * @param <T> type
     * @return The corresponding Bean in the Spring IOC container
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
