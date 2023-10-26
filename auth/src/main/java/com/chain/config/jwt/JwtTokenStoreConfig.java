package com.chain.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JWT内容增强器
 */
@Configuration
public class JwtTokenStoreConfig {
    /**
     * JWT内容增强器
     */
    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }
}
