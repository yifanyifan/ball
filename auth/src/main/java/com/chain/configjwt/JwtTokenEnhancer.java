package com.chain.configjwt;

import com.chain.entity.SecurityUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Jwt内容增强器
 * 自定义的实现了 TokenEnhancer 接口的类，用于对 JWT 访问令牌进行增强操作，比如往JWT中添加自定义信息的话，比如说登录用户的ID
 * Created by macro on 2019/10/8.
 */
public class JwtTokenEnhancer implements TokenEnhancer {
    /**
     * Jwt内容增强器
     *
     * @param accessToken    原始的访问令牌
     * @param authentication 包含身份验证信息 对象
     * @return 返回增强后的访问令牌
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        //从 authentication 对象中获取认证的主体（principal）。
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        //把用户ID设置到JWT中
        Map<String, Object> info = new HashMap<>();
        info.put("id", securityUser.getId());
        info.put("client_id", securityUser.getClientId());
        //将 info 中的额外信息添加到访问令牌中。
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

        return accessToken;
    }
}
