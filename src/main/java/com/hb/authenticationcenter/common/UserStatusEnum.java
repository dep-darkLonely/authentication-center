package com.hb.authenticationcenter.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author admin
 * @version 1.0
 * @description 用户状态枚举类
 * @date 2022/12/18
 */
@Getter
public enum UserStatusEnum {
    /**
     * 启用
     */
    ENABLE("enable"),
    /**
     * 禁用
     */
    DISABLE("disable");
    @EnumValue
    private final String status;

    UserStatusEnum(String status) {
        this.status = status;
    }
}
