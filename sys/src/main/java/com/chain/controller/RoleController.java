package com.chain.controller;

import com.chain.base.BaseController;
import com.chain.common.ResultEntity;
import com.chain.entity.Role;
import com.chain.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制器
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Slf4j
@RestController
@RequestMapping("/role")
@Api(value = "API", tags = {""})
public class RoleController extends BaseController<Role> {

    @Autowired
    private RoleService roleService;


    @ApiOperation("根据用户ID获取用户权限")
    @RequestMapping(value = "/getRoleByUserId/id/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultEntity getRoleByUserId(@PathVariable("userId") Long userId) {
        try {
            List<Role> roleList = roleService.getRoleByUser(userId);
            return ResultEntity.success(roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}

