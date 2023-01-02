package com.hb.authenticationcenter.security.entrypoint;

import com.hb.authenticationcenter.common.ResponseResult;
import com.hb.authenticationcenter.security.exception.CaptchaException;
import com.hb.authenticationcenter.security.exception.InvalidTokenException;
import com.hb.authenticationcenter.util.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.PrintWriter;

import static com.hb.authenticationcenter.common.Constants.DEFAULT_CHARSET;
import static com.hb.authenticationcenter.common.ErrorCode.*;

/**
 * @author admin
 * @version 1.0
 * @description bearer authentication entry point.
 * @date 2022/12/30
 */
public class CustomAuthenticationEntryPoint implements InitializingBean, AuthenticationEntryPoint, MessageSourceAware {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(messages, "messageSource can not null.");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult responseResult = ResponseResult.failed();
        if (authException instanceof CredentialsExpiredException) {
            responseResult.setCode(ERROR_CREDENTIALS_EXPIRED);
            responseResult.setMessage(this.messages.getMessage(ERROR_CREDENTIALS_EXPIRED));
        } else if (authException instanceof LockedException) {
            responseResult.setCode(ERROR_USER_LOCKED);
            responseResult.setMessage(this.messages.getMessage(ERROR_USER_LOCKED));
        } else if (authException instanceof DisabledException) {
            responseResult.setCode(ERROR_USER_DISABLED);
            responseResult.setMessage(this.messages.getMessage(ERROR_USER_DISABLED));
        } else if (authException instanceof CaptchaException) {
            responseResult.setCode(ERROR_CAPTCHA_ERROR);
            responseResult.setMessage(this.messages.getMessage(ERROR_CAPTCHA_ERROR));
        } else if (authException instanceof InvalidTokenException) {
            responseResult.setCode(ERROR_BAD_CREDENTIALS);
            responseResult.setMessage(this.messages.getMessage(ERROR_BAD_CREDENTIALS));
        } else {
            responseResult.setCode(ERROR_USERNAME_OR_PASSWORD_ERROR);
            responseResult.setMessage(this.messages.getMessage(ERROR_USERNAME_OR_PASSWORD_ERROR));
        }
        response.setCharacterEncoding(DEFAULT_CHARSET);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtils.object2String(responseResult));
        writer.flush();
        writer.close();
    }
}
