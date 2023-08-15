package com.chain.common;

public interface Constant {
    //后台管理client认证
    String ADMIN_CLIENT_ID = "admin-app";
    String ADMIN_CLIENT_PASSWORD = "admin-app-password";

    //Redis用户登录信息
    String REDIS_UMS = "sys:ums";
    //Redis用户登录信息有效期毫秒
    Long REDIS_UMS_EXPIRE = 18000l;
    //Redis资源角色信息
    String REDIS_RESOURCE_ROLE = "sys:resourceRolesMap";

    //JWT存储权限前缀
    String AUTHORITY_PREFIX = "ROLE_";
    //JWT存储权限属性
    String AUTHORITY_CLAIM_NAME = "authorities";

    //前端传递的认证信息，请求key
    String JWT_TOKEN_HEADER = "Authorization";
    //JWT令牌前缀
    String JWT_TOKEN_PREFIX = "Bearer ";
    //header中用户信息【key:用户信息】
    String USER_TOKEN_HEADER = "user";

    //前台商城client_id
    String PORTAL_CLIENT_ID = "portal-app";
    //后台管理接口路径匹配
    String ADMIN_URL_PATTERN = "/mall-admin/**";

    public static final String LOGIN_SUCCESS = "登录成功!";
    public static final String USERNAME_PASSWORD_ERROR = "用户名或密码错误!";
    public static final String CREDENTIALS_EXPIRED = "该账户的登录凭证已过期，请重新登录!";
    public static final String ACCOUNT_DISABLED = "该账户已被禁用，请联系管理员!";
    public static final String ACCOUNT_LOCKED = "该账号已被锁定，请联系管理员!";
    public static final String ACCOUNT_EXPIRED = "该账号已过期，请联系管理员!";
    public static final String PERMISSION_DENIED = "没有访问权限，请联系管理员!";
}
