package com.chain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ApiModelProperty("菜单集合（前端渲染用）")
    private List<Menu> menuList;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Menu {
        private Long id;
        private String name;
        private String url;
        private List<Menu> submenus;

        public Menu(Long id, String name, String url) {
            this.id = id;
            this.name = name;
            this.url = url;
        }
    }
}
