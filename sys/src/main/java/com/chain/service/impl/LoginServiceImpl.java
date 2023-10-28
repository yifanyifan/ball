package com.chain.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chain.auth.Oauth2TokenDto;
import com.chain.common.Constant;
import com.chain.common.ResultEntity;
import com.chain.common.ResultEntityEnum;
import com.chain.common.TrainException;
import com.chain.dto.UserDTO;
import com.chain.entity.User;
import com.chain.feign.AuthCilents;
import com.chain.mapper.UserMapper;
import com.chain.service.LoginService;
import com.chain.service.RedisService;
import com.chain.service.UserService;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    public User getCurrentUser(HttpServletRequest request) {
        String userStr = request.getHeader(Constant.USER_TOKEN_HEADER);
        if (StrUtil.isEmpty(userStr)) {
            throw new TrainException(ResultEntityEnum.UNAUTHORIZED);
        }
        User userDto = JSONUtil.toBean(userStr, User.class);
        User user = JSON.parseObject((String) redisService.get(Constant.REDIS_UMS + ":" + userDto.getId()), User.class);
        if (ObjectUtil.isNotEmpty(user)) {
            return user;
        } else {
            user = userMapper.selectById(userDto.getId());
            redisService.set(Constant.REDIS_UMS + ":" + user.getId(), JSON.toJSONString(user), Constant.REDIS_UMS_EXPIRE);
            return user;
        }
    }
}
