package com.hb.authenticationcenter.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

/**
 * @author admin
 * @version 1.0
 * @description Role Response
 * @date 2023/1/2
 */
@Data
@Builder
public class RoleResponse {

    @JsonView(SimpleView.class)
    private String id;

    @JsonView(SimpleView.class)
    private String name;

    @JsonView(DefaultView.class)
    private boolean status;

    @JsonView(DefaultView.class)
    @JsonProperty("built_in")
    private boolean builtIn;

    @JsonView(DetailView.class)
    @JsonProperty("create_date")
    private Date createDate;

    @JsonView(DetailView.class)
    private String remark;
}
