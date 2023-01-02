package com.hb.authenticationcenter.util;

import com.hb.authenticationcenter.controller.response.UserResponse;
import com.hb.authenticationcenter.entity.SecurityUserDetails;
import com.hb.authenticationcenter.entity.SysUserEntity;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWSAlgorithm;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author admin
 * @version 1.0
 * @description jwe utils
 * @date 2022/12/29
 */
public class JweUtils {
    /**
     * jwt valid time: Milliseconds per hour
     */
    public static final long JWT_EXPIRE_TIME = 60 * 60 * 1000L;
    /**
     * jwe sign secret
     */
    private static final String signingSecret = "W12kDpmOhAERpTeXaQKsCqmzTwRZazB46hBGlw7wmsXc2jINNLZs2UEonKzCDc2U4aFa6uuCxpBd074HPaabPuoX8GrnmISDx6rlsqgskvduc1COL2RBu2X2tU5a2uJukmWCHvFltRIWfWXgfCE9K7s2zRYUclFLOGOeI2Th2F77ZOZ0gRhc4XK6meIe1CdIDHihG68OS5xHTd6LXSKlnVDatsqlDkp5VBbOWtW5VPtEQvHraIHYpfX8PriGVldT";
    /**
     * jwe encrypt secret
     */
    private static final String encryptionSecret = "Yp7Tj1OWkwsZ0IMt4u9bv7VSN7LqokCX";
    /**
     * signature configuration
     */
    private static final SecretSignatureConfiguration signatureConfiguration = new SecretSignatureConfiguration(signingSecret, JWSAlgorithm.HS256);
    /**
     * encrypt configuration
     */
    private static final SecretEncryptionConfiguration encryptionConfiguration = new SecretEncryptionConfiguration(encryptionSecret,
            JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
    /**
     * jwt authenticator
     */
    private static final JwtAuthenticator JWT_AUTHENTICATOR = new JwtAuthenticator();

    static {
        JWT_AUTHENTICATOR.setSignatureConfiguration(signatureConfiguration);
        JWT_AUTHENTICATOR.setEncryptionConfiguration(encryptionConfiguration);
    }
    public static String createToken(SecurityUserDetails userDetails, long expireTime) {
        Assert.notNull(userDetails.getSysUserEntity(), "id can not blank.");
        JwtGenerator generator = new JwtGenerator();
        generator.setEncryptionConfiguration(encryptionConfiguration);
        generator.setSignatureConfiguration(signatureConfiguration);
        generator.setExpirationTime(new Date(expireTime));
        CommonProfile userProfile = new CommonProfile();
        SysUserEntity sysUserEntity = userDetails.getSysUserEntity();
        userProfile.setId(sysUserEntity.getId());
        userProfile.setClientName(sysUserEntity.getClientName());
        userProfile.addAttribute("username", sysUserEntity.getUsername());
        return generator.generate(userProfile);
    }

    public static UserResponse resolveToken(String token) {
        Assert.notNull(token, "token can not blank.");
        CommonProfile userProfile = (CommonProfile) JWT_AUTHENTICATOR.validateToken(token);
        return userProfile == null ?  null : UserResponse.builder()
                .id(userProfile.getId())
                .clientName(userProfile.getClientName())
                .username(userProfile.getUsername())
                .permissions(userProfile.getPermissions())
                .token(token)
                .expireTime(userProfile.getAttribute("exp", Date.class).getTime())
                .build();
    }
}
