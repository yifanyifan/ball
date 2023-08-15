package com.chain.common;

/**
 * 自定义API异常
 * Created by macro on 2020/2/27.
 */
public class TrainException extends RuntimeException {
    private ResultEntityEnum errorCode;

    public TrainException(ResultEntityEnum errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public TrainException(String message) {
        super(message);
    }

    public TrainException(Throwable cause) {
        super(cause);
    }

    public TrainException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultEntityEnum getErrorCode() {
        return errorCode;
    }
}
