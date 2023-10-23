package com.chain.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * 配置了一个基于 JWT 的资源服务器，它使用 JWT 令牌来实现身份验证和授权
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)//启用权限表达式
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    /**
     * 这个方法配置 HTTP 安全性，即定义哪些请求需要受保护，哪些请求允许公开访问
     * 允许所有用户访问 "/login" 路径，其他路径可能需要进行身份验证。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api", "/api/**").permitAll()
                .anyRequest().authenticated();
    }

    /**
     * 生成JWT令牌转换器，并可使用密钥对对令牌进行签名和验证操作。这样配置的授权服务器可以生成和验证 JWT 令牌，以实现基于 JWT 的身份验证和授权功能。
     * 负责将 OAuth2 令牌和 JWT 令牌进行转换。密钥对从证书文件中加载，用于 JWT 令牌的签名和验证。
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        // JwtAccessTokenConverter是Spring Security OAuth2 提供的一个实现类，用于在 JWT（JSON Web Token）令牌和 OAuth2 令牌之间进行转换。
        // 它负责将 OAuth2 令牌转换为 JWT 格式的令牌，或将 JWT 格式的令牌转换为 OAuth2 令牌。
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    /**
     * 这个方法主要用于从一个位于 classpath 下的证书文件（jwt.jks）中获取一个密钥对。
     * KeyPair是Java 加密库提供的一个类，表示一个非对称密钥对，通常用于加密和签名操作。
     */
    @Bean
    public KeyPair keyPair() {
        //指定了位于 classpath 下的证书文件jwt.jks和证书密码
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        //从证书中获取指定别名（"jwt"）和密码的密钥对
        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
    }

    /**
     * 这个方法配置资源服务器的安全性。在这里，将之前创建的 jwtTokenStore 设置为资源服务器的 TokenStore，用于处理令牌。
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(new JwtTokenStore(jwtAccessTokenConverter()));
    }
}
