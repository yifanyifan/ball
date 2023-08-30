package com.chain.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用于配置JWT令牌存储和增强。
 */
@Configuration
public class JwtTokenStoreConfig {
    /**
     * Jwt内容增强器
     */
    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }

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
