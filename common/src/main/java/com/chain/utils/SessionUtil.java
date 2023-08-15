//package com.chain.utils;
//
//import cn.hutool.core.convert.Convert;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.toolkit.Assert;
//import com.chain.common.Constant;
//import com.chain.dto.UserDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Slf4j
//@Component
//public class SessionUtil {
//    private RedisTemplate<String, Object> redisTemplate;
//    private HttpServletRequest request;
//    private String sessionRedisKey;
//
//    private ThreadLocal<Object> cache = new ThreadLocal<Object>();
//
//    private SessionUtil() {
//    }
//
//    private SessionUtil(RedisTemplate<String, Object> redisTemplate, HttpServletRequest request) {
//        this.redisTemplate = redisTemplate;
//        this.request = request;
//        Assert.notNull(request, "request must not be null");
//        this.sessionRedisKey = getSessionKey();
//    }
//
//    public static SessionUtil build(RedisTemplate<String, Object> redisTemplate, HttpServletRequest request) {
//        return new SessionUtil(redisTemplate, request);
//    }
//
//    private String getSessionKey() {
//        String token = request.getHeader(Constant.USER_TOKEN_HEADER);
//        Assert.notNull(token, "trainSessionId must not be null");
//
//        JSONObject userJsonObject = JSONObject.parseObject(token);
//        String id = userJsonObject.getString("id"); // 用户id
//
//        return trainSessionId(id);
//    }
//
//    public String trainSessionId(String sessionId) {
//        Assert.notNull(sessionId, "sessionId must not be null");
//        return "sys:ums:".concat(sessionId);
//    }
//
//    public UserDTO getSessionUser() {
//        Object user = cache.get();
//        user = null == user ? redisTemplate.opsForValue().get(sessionRedisKey) : user;
//        if (null != user) {
//            String userInfo = String.valueOf(user);
//            return JSON.parseObject(userInfo, UserDTO.class);
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 清除session
//     *
//     * @return
//     */
//    public boolean clearSession() {
//        return redisTemplate.delete(sessionRedisKey);
//    }
//}
