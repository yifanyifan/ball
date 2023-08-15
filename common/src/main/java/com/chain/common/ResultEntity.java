package com.chain.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liang.zhang
 * @date 2020年3月25日
 * @desc TODO
 */
@Data
@AllArgsConstructor
public class ResultEntity<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5835280423988735793L;
    private Integer code = 20000;
    private String status;
    private T data;

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResultEntity<T> success(T data) {
        return new ResultEntity<>(ResultEntityEnum.SUCCESS.getCode(), ResultEntityEnum.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> ResultEntity<T> success(String message, T data) {
        return new ResultEntity<T>(ResultEntityEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> ResultEntity<T> generate(ResultEntityEnum code, String message, T data) {
        return new ResultEntity<T>(code.getCode(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> ResultEntity<T> failed(ResultEntityEnum errorCode) {
        return new ResultEntity<T>(errorCode.getCode(), errorCode.getMsg(), null);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static <T> ResultEntity<T> failed(ResultEntityEnum errorCode, String message) {
        return new ResultEntity<T>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> ResultEntity<T> failed(String message) {
        return new ResultEntity<T>(ResultEntityEnum.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResultEntity<T> failed() {
        return failed(ResultEntityEnum.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResultEntity<T> validateFailed() {
        return failed(ResultEntityEnum.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> ResultEntity<T> validateFailed(String message) {
        return new ResultEntity<T>(ResultEntityEnum.VALIDATE_FAILED.getCode(), message, null);
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
