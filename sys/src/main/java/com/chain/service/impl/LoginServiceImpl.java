package com.chain.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chain.auth.Oauth2TokenDto;
import com.chain.common.BallException;
import com.chain.constant.Constant;
import com.chain.common.ResultEntity;
import com.chain.auth.ResultEntityEnum;
import com.chain.dto.UserDTO;
import com.chain.entity.Permission;
import com.chain.entity.User;
import com.chain.feign.AuthCilents;
import com.chain.mapper.PermissionMapper;
import com.chain.mapper.UserMapper;
import com.chain.service.LoginService;
import com.chain.service.RedisService;
import com.chain.service.UserService;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author 易樊
 * @since 2022-04-07
 */
@Slf4j
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthCilents authCilents;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResultEntity login(String username, String password, HttpServletRequest request) throws Exception {
        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            return ResultEntity.failed("用户名或密码不能为空！");
        }
        Map<String, String> params = new HashMap<>();
        //进行认证，当前Oauth2客户端（Client Details）请求访问认证服务器时的身份验证信息, 是用于客户端与认证服务器之间的通信, 与用户登录的密码是分开的概念。
        params.put("client_id", Constant.ADMIN_CLIENT_ID);
        params.put("client_secret", Constant.ADMIN_CLIENT_PASSWORD);
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        ResultEntity<Oauth2TokenDto> resultEntity = authCilents.getToken(params);
        if (String.valueOf(ResultEntityEnum.SUCCESS.getCode()).equals(String.valueOf(resultEntity.getCode())) && resultEntity.getData() != null) {
            // user存入redis
            Oauth2TokenDto oauth2TokenDto = resultEntity.getData();
            String realToken = oauth2TokenDto.getToken().replace("Bearer ", "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            JSONObject userJsonObject = JSONObject.parseObject(userStr);

            UserDTO userDTO = userService.loadUserByUsername(userJsonObject.getString("user_name"));
            redisService.set(Constant.REDIS_UMS + ":" + userDTO.getId(), JSON.toJSONString(userDTO), Constant.REDIS_UMS_EXPIRE);
        }
        return resultEntity;
    }

    @Override
    public UserDTO getCurrentUser(HttpServletRequest request) {
        String userStr = request.getHeader(Constant.USER_TOKEN_HEADER);
        if (StrUtil.isEmpty(userStr)) {
            throw new BallException(ResultEntityEnum.CREDENTIALS_EXPIRED);
        }
        User user = JSONUtil.toBean(userStr, User.class);

        //获取用户
        String redisUser = (String) redisService.get(Constant.REDIS_UMS + ":" + user.getId());
        if (StringUtils.isEmpty(redisUser)) {
            throw new BallException(ResultEntityEnum.CREDENTIALS_EXPIRED);
        }
        UserDTO userDTO = JSON.parseObject(redisUser, UserDTO.class);
        //获取菜单
        List<Long> roleIdList = userDTO.getRoleDTOList().stream().map(i -> i.getId()).collect(Collectors.toList());
        List<Permission> permissionList = permissionMapper.getListByParentId(null);
        List<Long> permissionIdList = permissionList.stream().map(i -> i.getId()).collect(Collectors.toList());
        List<UserDTO.Menu> menuList = get(roleIdList, permissionIdList);
        //去除空菜单
        menuList = menuList.stream().filter(i -> CollectionUtil.isNotEmpty(i.getSubmenus())).collect(Collectors.toList());
        userDTO.setMenuList(menuList);

        return userDTO;
    }

    /**
     * 递归获取菜单
     */
    public List<UserDTO.Menu> get(List<Long> roleIdList, List<Long> menuIdList) {
        List<UserDTO.Menu> menuList = new ArrayList<>();
        for (Long menuId : menuIdList) {
            //包装
            Permission permission = permissionMapper.selectById(menuId);
            UserDTO.Menu menu = new UserDTO.Menu(permission.getId(), permission.getName(), permission.getUrl());

            //判断子页面有没有权限
            List<Permission> permissionList = permissionMapper.getListByParentIdAndRoleId(permission.getId(), roleIdList);
            if (CollectionUtil.isNotEmpty(permissionList)) {
                List<Long> permissionSubList = permissionList.stream().map(i -> i.getId()).collect(Collectors.toList());
                List<UserDTO.Menu> menuListSub = get(roleIdList, permissionSubList);
                menu.setSubmenus(menuListSub);
            }
            menuList.add(menu);
        }

        return menuList;

    }
}


/*
//目前JWT数据
{
"user_name": "yifan",
"scope": ["all"],
"id": 2,
"exp": 1698510835,
"authorities": ["Trading_UPDATE", "TradingDetail_READ", "Trading_CREATE", "Trading_DELETE", "TradingDetail_UPDATE", "ROLE_YIFAN", "Trading_READ", "TradingDetail_CREATE", "TradingDetail_DELETE"],
"jti": "ed6672a3-88c1-4ea7-9b26-18a1357513e0",
"client_id": "admin-app"
}

//Redis 用户数据
{
"clientId": "client-app",
"id": 2,
"isEnable": true,
"password": "$2a$10$b67pCcXbK2r/sfADJRw8/uLFDYkFJ8FEVmpO0Ku2Z65jVavu4WTpW",
"permissionList": [
"Trading_CREATE",
"Trading_UPDATE",
"Trading_READ",
"Trading_DELETE",
"TradingDetail_CREATE",
"TradingDetail_UPDATE",
"TradingDetail_READ",
"TradingDetail_DELETE"
],
"roleDTOList": [
{
"id": 2,
"name": "ROLE_YIFAN"
}
],
"username": "yifan"
}*/
