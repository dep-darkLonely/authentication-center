package com.hb.authenticationcenter.config;

import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.hb.authenticationcenter.util.AesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author admin
 * @version 1.0
 * @description DataSource
 * @date 2023/1/2
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class DataSourceConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.druid.initial-size}")
    private Integer initSize;

    @Value("${spring.datasource.druid.max-active}")
    private Integer maxActive;

    @Value("${spring.datasource.druid.max-wait}")
    private Integer maxWait;

    @Value("${spring.datasource.druid.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.druid.test-on-borrow}")
    private boolean testOnBorrow;

    @Bean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        try {
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(AesUtils.decrypt(password));
            druidDataSource.setDriverClassName(driverClass);
            druidDataSource.setInitialSize(initSize);
            druidDataSource.setDbType(DbType.mysql);
            druidDataSource.setKeepAlive(true);
            druidDataSource.setMaxActive(maxActive);
            druidDataSource.setValidationQuery(validationQuery);
            druidDataSource.setMaxWait(maxWait);
            druidDataSource.setTestOnBorrow(testOnBorrow);
            druidDataSource.init();
        } catch (SQLException e) {
            log.error("database connection exception.");
            throw new RuntimeException(e);
        }
        return druidDataSource;
    }
}
