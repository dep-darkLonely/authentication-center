package com.hb.authenticationcenter.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author admin
 * @version 1.0
 * @description base entity.
 * @date 2022/12/18
 */
@Getter
@Setter
@Accessors(chain = true)
public class BaseEntity {
    /**
     * 创建时间
     */
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改时间
     */
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date updateTime;
    /**
     * 修改人
     */
    private String updateBy;
}
