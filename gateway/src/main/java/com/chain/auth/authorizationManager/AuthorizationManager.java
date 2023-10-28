//package com.chain.auth.authorizationManager;
//
//import cn.hutool.core.convert.Convert;
//import com.chain.common.Constant;
//import com.chain.config.IgnoreUrlsConfig;
//import com.chain.service.RedisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.ReactiveAuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.security.web.server.authorization.AuthorizationContext;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 在WebFluxSecurity中自定义鉴权操作需要实现ReactiveAuthorizationManager接口；
// * 鉴权管理器，用于判断是否有资源的访问权限
// */
//@Component
//public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
//    @Autowired
//    private RedisService redisService;
//    @Autowired
//    private IgnoreUrlsConfig ignoreUrlsConfig;
//
//    /**
//     * 作用是对认证后的JwtAuthenticationToken对象进行处理，提取其中的权限（角色）信息，并与authoritiesFinal列表中的权限进行匹配。
//     * 如果存在匹配的角色，返回一个授权通过的决策；如果没有匹配的角色，返回一个授权未通过的决策。
//     */
//    @Override
//    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
//        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
//        String token = request.getHeaders().getFirst("Authorization");
//        URI uri = request.getURI();
//        PathMatcher pathMatcher = new AntPathMatcher();
//        //白名单路径直接放行
//        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
//        for (String ignoreUrl : ignoreUrls) {
//            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
//                return Mono.just(new AuthorizationDecision(true));
//            }
//        }
//        //对应跨域的预检请求直接放行
//        if (request.getMethod() == HttpMethod.OPTIONS) {
//            return Mono.just(new AuthorizationDecision(true));
//        }
//        //从Redis中获取当前页面路径.html可访问角色列表
//        Object obj = redisService.hGet(Constant.REDIS_RESOURCE_ROLE, uri.getPath());
//        List<String> authorities = Convert.toList(String.class, obj);
//        //每个角色添加一个固定的前缀，以便与后续的用户角色进行匹配。
//        List<String> authoritiesFinal = authorities.stream().map(i -> i = Constant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
//        //认证通过且角色匹配的用户可访问当前路径
//        return mono
//                //过滤出已经认证的
//                .filter(Authentication::isAuthenticated)
//                .filter(a -> a instanceof JwtAuthenticationToken)
//                //并将其转换为JwtAuthenticationToken类型
//                .cast(JwtAuthenticationToken.class)
//                //对象的头部信息和属性信息
//                .doOnNext(tt -> {
//                    System.out.println(tt.getToken().getHeaders());
//                    System.out.println(tt.getTokenAttributes());
//                })
//                //将Authentication对象中的权限（角色）进行遍历，检查是否存在匹配的角色。如果存在匹配的角色，返回一个授权通过的决策。
//                .flatMapIterable(Authentication::getAuthorities).map(GrantedAuthority::getAuthority).any(authority -> {
//                    System.out.println("AuthorManager:" + authority + "_" + authoritiesFinal);
//                    return authoritiesFinal.contains(authority);
//                }).map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
//    }
//
//}
