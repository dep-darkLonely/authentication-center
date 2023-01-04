package com.hb.authenticationcenter;

import com.hb.authenticationcenter.banner.AuthenticationCenterBanner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
/**
 * @author admin
 * @version 1.0
 * @description Application Start Up Class.
 * @date 2023/1/1
 */
@SpringBootApplication
@MapperScan("com.hb.authenticationcenter.mapper")
public class AuthenticationCenterApplication implements ApplicationRunner {
    /**
     * logger
     */
    private static final Log logger = LogFactory.getLog(AuthenticationCenterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationCenterApplication.class, args);
    }

    /**
     * print banner
     * @param args incoming application arguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        AuthenticationCenterBanner authenticationCenterBanner = new AuthenticationCenterBanner();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        authenticationCenterBanner.printBanner(null, AuthenticationCenterApplication.class, new PrintStream(baos));
        logger.info(baos.toString(StandardCharsets.UTF_8));
        logger.info("Authentication Center Service Startup Completed.");
    }
}
