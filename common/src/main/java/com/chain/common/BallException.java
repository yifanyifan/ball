package com.chain.common;

import com.chain.auth.ResultEntityEnum;

/**
 * 自定义API异常
 * Created by macro on 2020/2/27.
 */
public class BallException extends RuntimeException {
    private ResultEntityEnum errorCode;

    public BallException(ResultEntityEnum errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public BallException(String message) {
        super(message);
    }
}
