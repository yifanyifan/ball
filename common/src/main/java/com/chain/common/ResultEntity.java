package com.chain.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResultEntity<T> implements Serializable {
    private static final long serialVersionUID = -5835280423988735793L;
    private Integer code = 20000;
    private String status;
    private T data;

    public static <T> ResultEntity<T> success() {
        return new ResultEntity<>(ResultEntityEnum.SUCCESS.getCode(), ResultEntityEnum.SUCCESS.getMsg(), null);
    }

    public static <T> ResultEntity<T> success(T data) {
        return new ResultEntity<>(ResultEntityEnum.SUCCESS.getCode(), ResultEntityEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ResultEntity<T> success(String message, T data) {
        return new ResultEntity<T>(ResultEntityEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> ResultEntity<T> failed() {
        return failed(ResultEntityEnum.FAILED);
    }

    public static <T> ResultEntity<T> failed(String message) {
        return new ResultEntity<T>(ResultEntityEnum.FAILED.getCode(), message, null);
    }

    public static <T> ResultEntity<T> failed(ResultEntityEnum errorCode) {
        return new ResultEntity<T>(errorCode.getCode(), errorCode.getMsg(), null);
    }

    public static <T> ResultEntity<T> failed(ResultEntityEnum errorCode, String message) {
        return new ResultEntity<T>(errorCode.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> ResultEntity<T> unauthorized(T data) {
        return new ResultEntity<T>(ResultEntityEnum.UNAUTHORIZED.getCode(), ResultEntityEnum.UNAUTHORIZED.getMsg(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> ResultEntity<T> forbidden(T data) {
        return new ResultEntity<T>(ResultEntityEnum.FORBIDDEN.getCode(), ResultEntityEnum.FORBIDDEN.getMsg(), data);
    }
}
