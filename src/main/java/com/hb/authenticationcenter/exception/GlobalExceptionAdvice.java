package com.hb.authenticationcenter.exception;

import com.hb.authenticationcenter.common.ResponseResult;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.hb.authenticationcenter.common.ErrorCode.ERROR_ACCESS_DENIED_ERROR;
import static com.hb.authenticationcenter.common.ErrorCode.ERROR_SERVER_INTERNAL_ERROR;

/**
 * @author admin
 * @version 1.0
 * @description Global Exception Advice
 * @date 2023/1/4 11:55
 */
@RestControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler implements MessageSourceAware {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<ResponseResult> accessDeniedExceptionHandle() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseResult.failed(ERROR_ACCESS_DENIED_ERROR, this.messages.getMessage(ERROR_ACCESS_DENIED_ERROR)));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ResponseResult> otherExceptionHandle() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseResult.failed(ERROR_SERVER_INTERNAL_ERROR,
                        this.messages.getMessage(ERROR_SERVER_INTERNAL_ERROR)));
    }
}
