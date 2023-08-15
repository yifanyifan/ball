package com.chain.controller;

import com.chain.dto.UserDTO;
import com.chain.entity.User;
import com.chain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import com.chain.param.UserPageParam;
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
import java.util.regex.Pattern;

/**
 *  控制器
 *
 * @author 易樊
 * @since 2023-07-17
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "API", tags = {""})
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加", response = ResultEntity.class)
    public ResultEntity<Boolean> addUser(@Validated(Add.class) @RequestBody User user) throws Exception {
        boolean flag = userService.saveUser(user);
        return ResultEntity.success(flag);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改", response = ResultEntity.class)
    public ResultEntity<Boolean> updateUser(@Validated(Update.class) @RequestBody User user) throws Exception {
        boolean flag = userService.updateUser(user);
        return ResultEntity.success(flag);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除", response = ResultEntity.class)
    public ResultEntity<Boolean> deleteUser(@PathVariable("id") Long id) throws Exception {
        boolean flag = userService.deleteUser(id);
        return ResultEntity.success(flag);
    }

    /**
     * 获取详情
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "详情", response = User.class)
    public ResultEntity<User> getUser(@PathVariable("id") Long id) throws Exception {
        User user = userService.getById(id);
        return ResultEntity.success(user);
    }

    /**
     * 分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "分页列表", response = User.class)
    public ResultEntity<PageEntity<User>> getUserPageList(@Validated @RequestBody UserPageParam userPageParam) throws Exception {
        PageEntity<User> paging = userService.getUserPageList(userPageParam);
        return ResultEntity.success(paging);
    }

    /**
     * 列表
     */
    @PostMapping("/getList")
    @ApiOperation(value = "列表", response = User.class)
    @ApiOperationSupport(ignoreParameters = {"userPageParam.pageIndex","userPageParam.pageSorts","userPageParam.pageSize"})
    public ResultEntity<List<User>> getUserList(@Validated @RequestBody UserPageParam userPageParam) throws Exception {
        List<User> list = userService.getUserList(userPageParam);
        return ResultEntity.success(list);
    }

    @ApiOperation("根据用户名获取通用用户信息")
    @RequestMapping(value = "/loadByUsername", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO loadUserByUsername(@RequestParam String username) {
        UserDTO user = userService.loadUserByUsername(username);
        return user;
    }
}

