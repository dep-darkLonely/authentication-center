package com.hb.authenticationcenter.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * VIEW(sys_role_authority_view)实体类
 *
 * @author admin
 * @since 2023-01-01 13:21:13
 * @description role-authority-view
 */
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_role_authority_view")
public class SysRoleAuthorityView extends BaseEntity {
    /**
     * ID
     */
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
     * 备注
     */
    private String remark;
    /**
     * 名称
     */
    private String authority;
    /**
     * 标识
     */
    private String identification;

}
