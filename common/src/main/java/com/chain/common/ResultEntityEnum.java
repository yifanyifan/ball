package com.chain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEntityEnum {

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),

    DATA_EXISTED(409, "数据已存在");

        /*FORMAT_INVALID(406, "格式错误"),
        DATA_NOT_FOUND(404, "数据不存在"),

        DATA_INVALID(409, "数据错误"),
        PERMISSION_FORBIDDEN(403, "禁止访问"),
        PERMISSION_DENIED(401, "权限不足"),
        LOGIN_TIMEOUT(401, "登录超时"),
        SERVER_ERROR(500, "网络服务异常"),
        LOGIN_EXCEPTION(401, "登录错误"),
        JWT_EXCEPTION(401, "JWT Token异常"),
        AUTH_EXCEPTION(401, "授权异常"),
        PARAM_NOT_NULL(400, "参数不能为空"),
        PARAM_NOT_SCOPE(400, "参数错误，不在范围内"),
        RESULT_NOT_EXPECT(400, "返回值不在预期范围内"),*/

    private Integer code;
    private String msg;
}
