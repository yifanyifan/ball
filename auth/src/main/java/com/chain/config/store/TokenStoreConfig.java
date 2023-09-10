package com.chain.config.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 配置JWT令牌存储
 */
@Configuration
public class TokenStoreConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置 token 保存在哪里
     */
    @Bean
    TokenStore tokenStore() {
        //1. 配置token 为JWT形式, access_token 实际上就不用存储了（无状态登录，服务端不需要保存信息），用户的所有信息都在 jwt 里边
        return new JwtTokenStore(tokenConverter());
        //2. 配置 token 保存在redis中
        //return new RedisTokenStore(redisConnectionFactory);
        //3. 配置 token 保存在内存中
        //return new InMemoryTokenStore();
    }

    /**
     * JWT令牌转换器: 可以实现将用户信息和 JWT 相互转换
     */
    @Bean
    JwtAccessTokenConverter tokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //在 JWT 字符串生成的时候，我们需要一个签名，这个签名需要自己保存好
        converter.setSigningKey("southyin");
        return converter;
    }


//    /**
//     * Jwt内容增强器
//     */
//    @Bean
//    public JwtTokenEnhancer jwtTokenEnhancer() {
//        return new JwtTokenEnhancer();
//    }

//    /**
//     * 用于将令牌存储为 JWT 格式
//     */
//    @Bean
//    public TokenStore jwtTokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
//
//    /**
//     * 用于将令牌转换为 JWT。
//     */
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
//        accessTokenConverter.setSigningKey("asdqweasasdqweasasdqweasasdqweasasdqweasasdqweasasdqweasasdqweas");//配置JWT使用的秘钥
//        return accessTokenConverter;
//    }
}
