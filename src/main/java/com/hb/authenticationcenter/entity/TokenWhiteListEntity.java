package com.hb.authenticationcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * (sys_token_white_list)实体类
 *
 * @author admin
 * @since 2023-01-04 23:45:31
 * @description 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("sys_token_white_list")
public class TokenWhiteListEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * token
     */
    private String token;
    /**
     * token过期时间
     */
    private Date date;

}