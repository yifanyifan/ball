package com.chain.config.auth.exception;

import com.chain.common.ResultEntity;
import com.chain.auth.ResultEntityEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Token类 异常
 */
@Component
public class MyExtendAuthenticationEntryPointHandler extends OAuth2AuthenticationEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(MyExtendAuthenticationEntryPointHandler.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Throwable cause = authException.getCause();

        // 自定义返回格式内容
        ResultEntity baseResult = null;
        if (cause instanceof OAuth2AccessDeniedException) {
            baseResult = ResultEntity.failed(ResultEntityEnum.RESOURCE_NOTFOUND);
        } else if (cause instanceof InvalidTokenException) {
            baseResult = ResultEntity.failed(ResultEntityEnum.TOKEN_ERROR);
        } else if (authException instanceof InsufficientAuthenticationException) {
            baseResult = ResultEntity.failed(ResultEntityEnum.NOTFOUND_TOKEN);
        } else {
            baseResult = ResultEntity.failed(ResultEntityEnum.OTHER_ERROR);
        }
        baseResult.setData(authException.getMessage());

        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.append(new ObjectMapper().writeValueAsString(baseResult));

    }

}

