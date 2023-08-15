package com.chain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 易樊
 * @since 2023-07-17
 */
@Data
@ApiModel(value = "Role对象")
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("角色名称")
    private String name;

}
