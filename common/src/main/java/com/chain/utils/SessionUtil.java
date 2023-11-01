package com.chain.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chain.constant.Constant;
import com.chain.dto.UserDTO;
import com.chain.service.RedisUtils;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public static UserDTO getUser(HttpServletRequest request) {
        JSONObject userJsonObject = JSONObject.parseObject(request.getHeader(Constant.USER_TOKEN_HEADER));
        String id = userJsonObject.getString("id"); // 用户id

        Object user = RedisUtils.get(Constant.REDIS_UMS + ":" + id);

        if (null != user) {
            String userInfo = String.valueOf(user);
            return JSON.parseObject(userInfo, UserDTO.class);
        } else {
            return null;
        }
    }
}
