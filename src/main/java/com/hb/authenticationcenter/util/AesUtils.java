package com.hb.authenticationcenter.util;

import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

/**
 * @author admin
 * @version 1.0
 * @description aes encrypt/decrypt tools
 * @date 2023/1/2
 */
public class AesUtils {
    /**
     * aes secret key:uuid
     */
    public static final String SECRET_KEY = "3991331d-09b3-4cd8-9360-09b423c283aa";
    /**
     * aes salt
     */
    public static final String SALT = "e65bf2a2d5cc49ad";
    /**
     * aes password encoder.
     */
    public static AesBytesEncryptor aesPasswordEncoder =  new AesBytesEncryptor(SECRET_KEY, SALT,
            KeyGenerators.secureRandom(16),
            AesBytesEncryptor.CipherAlgorithm.GCM);

    /**
     * aes decode
     * @param encryptedString encrypted string
     * @return plaintext
     */
    public static String decrypt(String encryptedString) {
        Assert.notNull(encryptedString, "encrypted string can not blank.");
        byte[] decrypt = aesPasswordEncoder.decrypt(Base64Utils.decodeFromString(encryptedString));
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    public static String encrypt(String plainText) {
        Assert.notNull(plainText, "encrypted string can not blank.");
        byte[] encrypt = aesPasswordEncoder.encrypt(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(encrypt);
    }
}
