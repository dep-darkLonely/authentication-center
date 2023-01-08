package com.hb.authenticationcenter.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.hb.authenticationcenter.controller.response.SimpleView;
import lombok.Data;

/**
 * @author admin
 * @version 1.0
 * @description common response result
 * @date 2022/12/30
 */
@Data
@JsonView(SimpleView.class)
public class ResponseResult {
    /**
     * response result: success is true,otherwise false.
     */
    private boolean result;
    /**
     * response code
     */
    private String code;
    /**
     * response error message
     */
    private String message;
    /**
     * handle result
     */
    private Object data;

    public ResponseResult(boolean result) {
        this.result = result;
    }

    public ResponseResult(Object data) {
        this.result = true;
        this.data = data;
    }

    public ResponseResult(String code, String message) {
        this.result = false;
        this.code = code;
        this.message = message;
    }

    public ResponseResult(Integer code, boolean result, String message, Object data) {
        this.result = result;
        this.data = data;
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(data);
    }

    public static ResponseResult failed() {
        return new ResponseResult(false);
    }

    public static ResponseResult failed(String code, String message) {
        return new ResponseResult(code, message);
    }
}
