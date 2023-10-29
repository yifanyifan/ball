package com.chain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEntityEnum {
    SUCCESS(200, "操作成功"),
    FAILED(501, "操作失败"),

    USERNAME_PASSWORD_ERROR(401, "用户名或密码错误!"),
    CREDENTIALS_EXPIRED(401, "该账户的登录凭证已过期，请重新登录!"),
    FORBIDDEN(401, "没有相关权限，请联系管理员!"),
    ACCOUNT_DISABLED(401, "该账户已被禁用，请联系管理员!"),
    ACCOUNT_LOCKED(401, "该账号已被锁定，请联系管理员!"),
    ACCOUNT_EXPIRED(401, "该账号已过期，请联系管理员!"),
    PERMISSION_DENIED(401, "没有访问权限，请联系管理员!"),

    RESOURCE_NOTFOUND(401, "资源ID不在resource_ids范围内!"),
    TOKEN_ERROR(401, "Token解析失败!"),
    NOTFOUND_TOKEN(401, "未携带token!"),
    OTHER_ERROR(401, "未知异常信息!"),

    RESOURCE_NOAUTH(401, "认证过的用户访问无权限资源时的异常!");


    private Integer code;
    private String msg;
}
