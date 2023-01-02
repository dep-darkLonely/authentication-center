package com.hb.authenticationcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 权限表(sys_authority)实体类
 *
 * @author admin
 * @since 2023-01-02 12:11:50
 * @description sys authority class.
 */
@Data
@Accessors(chain = true)
@TableName("sys_authority")
public class SysAuthorityEntity extends BaseEntity {
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
     * 标识
     */
    private String identification;
    /**
     * 备注
     */
    private String remark;

}