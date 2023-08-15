package com.chain.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 添加SpringSecurity配置，允许认证相关路径的访问及表单登录, 实现定制化的安全配置。
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * PasswordEncoder 是 Spring Security 提供的接口，用于对密码进行安全的哈希编码。
     * 在这个代码中，BCryptPasswordEncoder 是 Spring Security 提供的一种具体的实现，它使用 BCrypt 哈希算法来进行密码的编码和验证。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 在 Spring Security 中，AuthenticationManager 是一个核心接口，用于处理身份验证相关的操作。
     * 通过获取 AuthenticationManager 实例，您可以在应用程序中进行用户身份验证的操作，如登录认证等。
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 定义请求的安全规则和访问控制策略。可以设置哪些请求需要身份验证、哪些请求需要特定的角色或权限、配置跨域请求、启用 CSRF 保护等。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()           // 禁用 CSRF（跨站请求伪造）防护功能。
                .authorizeRequests()    //开始对请求进行授权配置。
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()   //允许所有的端点请求匹配器。
                .antMatchers("/oauth/token", "/rsa/publicKey", "/user/getAccessToken").permitAll()  //对指定的路径进行授权配置，允许所有用户访问。
                .anyRequest().authenticated();  //对其他所有请求进行身份验证。
    }
}
