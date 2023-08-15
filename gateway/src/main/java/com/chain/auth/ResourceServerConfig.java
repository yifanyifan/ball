package com.chain.auth;

import cn.hutool.core.util.ArrayUtil;
import com.chain.common.Constant;
import com.chain.component.RestAuthenticationEntryPoint;
import com.chain.component.RestfulAccessDeniedHandler;
import com.chain.config.IgnoreUrlsConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 对网关服务进行配置安全配置，由于Gateway使用的是WebFlux，所以需要使用@EnableWebFluxSecurity注解开启；
 * 资源服务器配置
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
    //鉴权管理器
    @Autowired
    private AuthorizationManager authorizationManager;
    //白名单
    private final IgnoreUrlsConfig ignoreUrlsConfig;
    //处理未授权
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    //处理未认证
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /**
     * 该方法配置了Spring Security的过滤器链，包括OAuth2和JWT的集成、白名单配置、访问控制、异常处理和CSRF防护的禁用
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer().jwt()   //配置OAuth2资源服务器和JWT的集成
                .jwtAuthenticationConverter(jwtAuthenticationConverter());  //设置JWT的转换器
        http.csrf().disable()   //禁用CSRF防护
                .authorizeExchange()    //配置授权交换规则
                .pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(), String.class)).permitAll()  //指定白名单配置
                .anyExchange().access(authorizationManager)     //配置默认的访问控制规则，即对除白名单以外的所有请求进行访问控制,使用AuthorizationManager进行权限验证。
                .and().exceptionHandling()  //配置异常处理。
                .accessDeniedHandler(restfulAccessDeniedHandler)    //处理未授权,当用户没有足够的权限访问资源时，将使用restfulAccessDeniedHandler来处理。
                .authenticationEntryPoint(restAuthenticationEntryPoint) //处理未认证, 当用户未经过认证时访问受保护的资源时，将使用restAuthenticationEntryPoint来处理。
                .and().csrf().disable();    //禁用CSRF防护
        return http.build();
    }

    /**
     * 作用是自定义JWT转换器，通过设置权限前缀和权限声明名称，将JWT令牌转换为包含权限信息的AbstractAuthenticationToken对象。这个自定义转换器将在资源服务器中使用，用于从JWT中提取权限信息并构建认证对象。
     * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
     * 需要把jwt的Claim中的authorities加入
     * 方案：重新定义ReactiveAuthenticationManager权限管理器，默认转换器JwtGrantedAuthoritiesConverter
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        //创建一个JwtGrantedAuthoritiesConverter对象, 用于将JWT中的权限（角色）信息转换为授权的权限列表。
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        //设置权限的前缀
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(Constant.AUTHORITY_PREFIX);
        //设置在JWT中存储权限信息的声明名称
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(Constant.AUTHORITY_CLAIM_NAME);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
