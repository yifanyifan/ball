package com.chain.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chain.base.BaseController;
import com.chain.common.ResultEntity;
import com.chain.entity.Permission;
import com.chain.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 控制器
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Slf4j
@RestController
@RequestMapping("/permission")
@Api(value = "API", tags = {""})
public class PermissionController extends BaseController<Permission> {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("根据角色ID获取菜单集合")
    @RequestMapping(value = "/getPermissionByRoleId/id/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultEntity getPermissionByRoleId(@PathVariable("roleId") Long roleId) {
        try {
            List<Permission> permissionList = permissionService.getPermissionByRoleId(roleId);
            return ResultEntity.success(permissionList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/listTree")
    @PreAuthorize("#root.this.getRequiredAuthority('ENTITY_READ') or hasAnyAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "基础功能-条件查询", httpMethod = "POST", response = ResultEntity.class)
    public ResultEntity listTree(@RequestBody Permission entity) {
        try {
            List<Permission> list = permissionService.listTree(entity);
            return ResultEntity.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}

