package com.chain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.chain.validator.groups.Update;

/**
 * 
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_permission")
@ApiModel(value = "Permission对象")
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("权限资源标识")
    private String authority;

    @ApiModelProperty("访问路径")
    private String url;

    @ApiModelProperty("权限类型，menu:菜单;permission:权限")
    private String permissionType;

    @ApiModelProperty("父级ID")
    private Long parentId;

}
