package com.chain.auth;

import cn.hutool.core.util.StrUtil;
import com.chain.common.Constant;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * 全局过滤器AuthGlobalFilter
 * 当鉴权通过后将JWT令牌中的用户信息解析出来，然后存入请求的Header中，这样后续服务就不需要解析JWT令牌了，可以直接从请求的Header中获取到用户信息。
 * 将登录用户的JWT转化成用户信息的全局过滤器
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    private static Logger logger = LoggerFactory.getLogger(AuthGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(Constant.JWT_TOKEN_HEADER);
        if (StrUtil.isEmpty(token)) {
            //将请求传递给下一个过滤器处理
            return chain.filter(exchange);
        }
        try {
            //获取实际的令牌字符串, 从token中解析用户信息并设置到Header中去
            String realToken = token.replace(Constant.JWT_TOKEN_PREFIX, "" );
            //使用JWSObject类解析令牌字符串，获取其中的用户信息，并将用户信息设置到请求的头部中，使用名为Constant.USER_TOKEN_HEADER的头部。
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            logger.info("======================>AuthGlobalFilter.filter() user:{}" , userStr);
            ServerHttpRequest request = exchange.getRequest().mutate().header(Constant.USER_TOKEN_HEADER, userStr).build();
            //使用mutate方法创建一个新的请求，将用户信息设置到头部，并构建新的ServerWebExchange对象。
            exchange = exchange.mutate().request(request).build();
        } catch (ParseException e) {
            logger.info(e.getMessage(), e);
        }
        //通过chain.filter(exchange)将新的ServerWebExchange对象传递给下一个过滤器处理。
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
