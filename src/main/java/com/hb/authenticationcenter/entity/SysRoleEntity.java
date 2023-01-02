package com.hb.authenticationcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 角色表(sys_role)实体类
 *
 * @author admin
 * @since 2023-01-02 12:03:54
 * @description system role entity.
 */
@Setter
@Getter
@Accessors(chain = true)
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 是否是内置
     */
    private Integer builtIn;
    /**
     * 备注
     */
    private String remark;

}