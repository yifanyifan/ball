package com.chain.auth;/*
package com.metadata.train.gateway.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metadata.train.common.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

*/
/**
 * 描 述 : 授权拒绝处理器，覆盖默认的OAuth2AccessDeniedHandler
 *//*

@Component
public class AuthExceptionHandler extends OAuth2AccessDeniedHandler implements AuthenticationEntryPoint, AuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException {
        authException(request, response, authException, objectMapper);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        authException(request, response, authException, objectMapper);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException(request, response, authException, objectMapper);
    }

    public static void authException(HttpServletRequest request, HttpServletResponse response, Exception authException, ObjectMapper objectMapper) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ResultEntity<String> result = new ResultEntity(1001, "认证失败，禁止访问", authException);
        response.setStatus(HttpStatus.OK.value());
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}

*/
