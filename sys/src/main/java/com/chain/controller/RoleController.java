package com.chain.controller;

import com.chain.base.BaseController;
import com.chain.entity.Role;
import com.chain.service.RoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}

