package com.chain.config.auth.exception;

import com.chain.common.ResultEntity;
import com.chain.common.ResultEntityEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足异常
 */
@Component
public class MyExtendAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger log = LoggerFactory.getLogger(MyExtendAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // 自定义返回格式内容
        ResultEntity baseResult = ResultEntity.failed(ResultEntityEnum.RESOURCE_NOAUTH, accessDeniedException.getMessage());

        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        //response.setStatus(HttpStatus.FORBIDDEN.value());// 权限不足403
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(baseResult));

    }
}

