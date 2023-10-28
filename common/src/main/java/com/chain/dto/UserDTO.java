package com.chain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 易樊
 * @since 2023-07-17
 */
@Data
@ApiModel(value = "User对象")
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
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
    private List<RoleDTO> roleDTOList;

    @ApiModelProperty("权限集合")
    private List<String> permissionList;
}
