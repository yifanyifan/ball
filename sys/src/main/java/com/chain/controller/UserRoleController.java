package com.chain.controller;

import com.chain.entity.UserRole;
import com.chain.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import com.chain.param.UserRolePageParam;
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
@RequestMapping("/userRole")
@Api(value = "API", tags = {""})
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加", response = ResultEntity.class)
    public ResultEntity<Boolean> addUserRole(@Validated(Add.class) @RequestBody UserRole userRole) throws Exception {
        boolean flag = userRoleService.saveUserRole(userRole);
        return ResultEntity.success(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改", response = ResultEntity.class)
    public ResultEntity<Boolean> updateUserRole(@Validated(Update.class) @RequestBody UserRole userRole) throws Exception {
        boolean flag = userRoleService.updateUserRole(userRole);
        return ResultEntity.success(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除", response = ResultEntity.class)
    public ResultEntity<Boolean> deleteUserRole(@PathVariable("id") Long id) throws Exception {
        boolean flag = userRoleService.deleteUserRole(id);
        return ResultEntity.success(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "详情", response = UserRole.class)
    public ResultEntity<UserRole> getUserRole(@PathVariable("id") Long id) throws Exception {
        UserRole userRole = userRoleService.getById(id);
        return ResultEntity.success(userRole);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "分页列表", response = UserRole.class)
    public ResultEntity<PageEntity<UserRole>> getUserRolePageList(@Validated @RequestBody UserRolePageParam userRolePageParam) throws Exception {
        PageEntity<UserRole> paging = userRoleService.getUserRolePageList(userRolePageParam);
        return ResultEntity.success(paging);
    }

    /**
     * 列表
     */
    @PostMapping("/getList")
    @ApiOperation(value = "列表", response = UserRole.class)
    @ApiOperationSupport(ignoreParameters = {"userRolePageParam.pageIndex","userRolePageParam.pageSorts","userRolePageParam.pageSize"})
    public ResultEntity<List<UserRole>> getUserRoleList(@Validated @RequestBody UserRolePageParam userRolePageParam) throws Exception {
        List<UserRole> list = userRoleService.getUserRoleList(userRolePageParam);
        return ResultEntity.success(list);
    }

}

