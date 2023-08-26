package com.chain.controller;

import com.chain.common.BaseController;
import com.chain.dto.UserDTO;
import com.chain.entity.User;
import com.chain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "API", tags = {""})
public class UserController extends BaseController<User> {

    @Autowired
    private UserService userService;


    @ApiOperation("根据用户名获取通用用户信息")
    @RequestMapping(value = "/loadByUsername", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO loadUserByUsername(@RequestParam String username) {
        UserDTO user = userService.loadUserByUsername(username);
        return user;
    }
}

