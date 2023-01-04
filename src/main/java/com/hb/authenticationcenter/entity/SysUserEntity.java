package com.hb.authenticationcenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表(sys_user)实体类
 *
 * @author admin
 * @since 2022-12-18 19:49:27
 * @description 由 Mybatisplus Code Generator 创建
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_user")
public class SysUserEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID, uuid
     * 不添加@Id 也不会出错
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String password;
    /**
     * 启用状态
     */
    private boolean status;
    /**
     * 锁定状态
     */
    private boolean isLocked;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 微信号
     */
    private String wechatId;
    /**
     * 锁定时间
     */
    private Date lockedTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 客户端
     */
    private String clientName;

}
