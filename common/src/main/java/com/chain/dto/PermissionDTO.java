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
@ApiModel(value = "Permission对象")
public class PermissionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("访问路径")
    private String url;

    @ApiModelProperty("权限类型，menu:菜单;permission:权限")
    private String permissionType;

    @ApiModelProperty("父级ID")
    private Long parentId;
}
