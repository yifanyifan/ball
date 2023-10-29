package com.chain.config.exception;

import org.springframework.http.HttpStatus;

/**
 * 自定义服务异常
 */
public class MyOAuth2ServerException extends MyOAuth2Exception {
    public MyOAuth2ServerException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "server_error";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}

