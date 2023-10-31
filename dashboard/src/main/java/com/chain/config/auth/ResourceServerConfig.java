package com.chain.config.auth;

import com.chain.constant.Constant;
import com.chain.config.auth.exception.MyExtendAccessDeniedHandler;
import com.chain.config.auth.exception.MyExtendAuthenticationEntryPointHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 配置了一个基于 JWT 的资源服务器，它使用 JWT 令牌来实现身份验证和授权
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)//启用权限表达式
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${security.oauth2.authorization.check-token-access}")
    private String checkTokenAccess;
    @Autowired
    private MyExtendAuthenticationEntryPointHandler myExtendAuthenticationEntryPointHandler;
    @Autowired
    private MyExtendAccessDeniedHandler myExtendAccessDeniedHandler;

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
     * tokenServices 我们配置了一个 RemoteTokenServices 的实例，这是因为资源服务器和授权服务
     * 器是分开的，资源服务器和授权服务器是放在一起的，就不需要配置 RemoteTokenServices 了
     * RemoteTokenServices 中我们配置了 access_token 的校验地址、client_id、client_secret 这三个
     * 信息，当用户来资源服务器请求资源时，会携带上一个 access_token，通过这里的配置，就能够
     * 校验出 token 是否正确等。
     */
    @Bean
    RemoteTokenServices tokenServices() {
        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl(checkTokenAccess);
        services.setClientId(Constant.ADMIN_CLIENT_ID);
        services.setClientSecret(Constant.ADMIN_CLIENT_PASSWORD);
        return services;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("res1").tokenServices(tokenServices())
                .authenticationEntryPoint(myExtendAuthenticationEntryPointHandler)//token异常类重写
                .accessDeniedHandler(myExtendAccessDeniedHandler);//权限不足异常类重写
    }
}
