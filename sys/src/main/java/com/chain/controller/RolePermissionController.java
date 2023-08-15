package com.chain.controller;

import com.chain.entity.RolePermission;
import com.chain.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import com.chain.param.RolePermissionPageParam;
import com.chain.common.ResultEntity;
import com.chain.common.PageEntity;
import com.chain.validator.groups.Add;
import com.chain.validator.groups.Update;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  控制器
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Slf4j
@RestController
@RequestMapping("/rolePermission")
@Api(value = "API", tags = {""})
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加", response = ResultEntity.class)
    public ResultEntity<Boolean> addRolePermission(@Validated(Add.class) @RequestBody RolePermission rolePermission) throws Exception {
        boolean flag = rolePermissionService.saveRolePermission(rolePermission);
        return ResultEntity.success(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改", response = ResultEntity.class)
    public ResultEntity<Boolean> updateRolePermission(@Validated(Update.class) @RequestBody RolePermission rolePermission) throws Exception {
        boolean flag = rolePermissionService.updateRolePermission(rolePermission);
        return ResultEntity.success(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除", response = ResultEntity.class)
    public ResultEntity<Boolean> deleteRolePermission(@PathVariable("id") Long id) throws Exception {
        boolean flag = rolePermissionService.deleteRolePermission(id);
        return ResultEntity.success(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "详情", response = RolePermission.class)
    public ResultEntity<RolePermission> getRolePermission(@PathVariable("id") Long id) throws Exception {
        RolePermission rolePermission = rolePermissionService.getById(id);
        return ResultEntity.success(rolePermission);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "分页列表", response = RolePermission.class)
    public ResultEntity<PageEntity<RolePermission>> getRolePermissionPageList(@Validated @RequestBody RolePermissionPageParam rolePermissionPageParam) throws Exception {
        PageEntity<RolePermission> paging = rolePermissionService.getRolePermissionPageList(rolePermissionPageParam);
        return ResultEntity.success(paging);
    }

    /**
     * 列表
     */
    @PostMapping("/getList")
    @ApiOperation(value = "列表", response = RolePermission.class)
    @ApiOperationSupport(ignoreParameters = {"rolePermissionPageParam.pageIndex","rolePermissionPageParam.pageSorts","rolePermissionPageParam.pageSize"})
    public ResultEntity<List<RolePermission>> getRolePermissionList(@Validated @RequestBody RolePermissionPageParam rolePermissionPageParam) throws Exception {
        List<RolePermission> list = rolePermissionService.getRolePermissionList(rolePermissionPageParam);
        return ResultEntity.success(list);
    }

}

