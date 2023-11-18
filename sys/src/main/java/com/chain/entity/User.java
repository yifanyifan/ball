package com.chain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.chain.validator.groups.Update;

/**
 * @author 易樊
 * @since 2023-07-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@ApiModel(value = "User对象")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("认证标识：区分走admin的用户认证还是member的用户认证")
    private String clientId;

    @ApiModelProperty("是否启用")
    private Boolean isEnable;

    @ApiModelProperty("角色集合")
    @TableField(exist = false)
    private List<Role> roleList;

    @ApiModelProperty("权限集合")
    @TableField(exist = false)
    private List<String> permissionList;
}
