package com.hb.authenticationcenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import static com.hb.authenticationcenter.security.MultiSecurityFilterChainConfig.PBKDF2_SECRET;

@SpringBootTest
class AuthenticationCenterApplicationTests {

    @Test
    void pbkdf2password() {
        long start = System.currentTimeMillis();
        Pbkdf2PasswordEncoder passwordEncoder =
                new Pbkdf2PasswordEncoder(PBKDF2_SECRET, 16, 310000,
                        Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        String encode = passwordEncoder.encode("Admin123!@#");
        boolean matches = passwordEncoder.matches("Admin123!@#", encode);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(matches);
    }

    @Test
    void bcryptPasswordEncoder() {
        long start = System.currentTimeMillis();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("Admin123!@#");
        boolean matches = bCryptPasswordEncoder.matches("Admin123!@#", encode);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(matches);
    }

}
