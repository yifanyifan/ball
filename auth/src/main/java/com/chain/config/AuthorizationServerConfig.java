package com.chain.config;

import com.chain.common.Constant;
import com.chain.config.jwt.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加认证服务器配置，使用@EnableAuthorizationServer注解开启：
 * AuthorizationServer（认证服务器）：用于认证用户的服务器，如果客户端认证通过，发放访问资源服务器的令牌。
 * 添加认证服务相关配置Oauth2ServerConfig，需要配置加载用户信息的服务UserServiceImpl及RSA的钥匙对KeyPair；
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    //SpringSecurityConfig类中定义的BCryptPasswordEncoder()
    @Autowired
    private PasswordEncoder passwordEncoder;
    //SpringSecurityConfig类中定义的authenticationManagerBean()
    @Autowired
    private AuthenticationManager authenticationManager;
    //定义的UserDetailsService类
    @Autowired
    private UserService userService;
    //Jwt内容增强器
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;
    /**
     * 将令牌存储为 JWT 格式
     */
    //@Qualifier("redisTokenStore")
//    @Autowired
//    @Qualifier("jwtTokenStore")
//    private TokenStore tokenStore;
//    /**
//     * JWT令牌转换器
//     */
//    @Autowired
//    private JwtAccessTokenConverter jwtAccessTokenConverter;


    /**
     * 配置Oauth2【客户端】（Client Details）请求访问【认证服务器】时身份验证信息
     * 确保只有经过授权的客户端能够向认证服务器进行有效的身份验证和令牌请求
     * 注意:秘钥(如ADMIN_CLIENT_ID、ADMIN_CLIENT_PASSWORD)是用于客户端与认证服务器之间的通信，与用户登录的密码是分开的概念。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //这表示客户端详细信息将存储在内存中，而不是从数据库或其他外部源加载。
        clients.inMemory()
                //这里配置了一个客户端（client）的标识符，即client_id。在这个例子中，客户端标识符为"admin-app"。
                .withClient(Constant.ADMIN_CLIENT_ID)
                //这里设置了客户端的秘钥，用户客户端和认证服务器之间进行安全通信和身份验证
                .secret(passwordEncoder.encode(Constant.ADMIN_CLIENT_PASSWORD))
                //这里配置了客户端的权限范围（scopes）。"all"表示该客户端具有请求全部权限范围。
                .scopes("all")
                //这里配置了客户端支持的授权类型,"password"表示客户端可以使用用户名和密码进行身份验证，并获取访问令牌（access token），"refresh_token"表示客户端可以使用刷新令牌（refresh token）获取新的访问令牌。
                .authorizedGrantTypes("password", "refresh_token")
                //这里配置了访问令牌的有效期，单位为秒。在这个例子中，访问令牌的有效期为3600秒，即1小时。
                .accessTokenValiditySeconds(1000)
                //这里配置了刷新令牌的有效期（refresh token validity），单位为秒。在这个例子中，刷新令牌的有效期为86400秒，即24小时。
                .refreshTokenValiditySeconds(10000000);
    }

    /**
     * 配置了【授权服务器】的端点，包括身份验证管理器、用户信息服务、访问令牌转换器和令牌增强器。
     * 通过配置这些端点，可以实现对请求的身份验证、生成访问令牌和对访问令牌进行增强等功能。
     * 注意：在这段代码中没有包含对密钥对或密钥的配置，因此可能需要在其他地方进行相关配置，以便进行签名和验证操作。
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenEnhancer> delegates = new ArrayList<>();
        //Jwt内容增强器
        delegates.add(jwtTokenEnhancer);
        //JWT令牌和OAuth2令牌之间的转换器
        delegates.add(accessTokenConverter());

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        //将令牌增强器列表设置到TokenEnhancerChain对象中，以配置JWT的内容增强器。这将确保在生成JWT令牌时，会应用上述的令牌增强器。
        enhancerChain.setTokenEnhancers(delegates);

        endpoints.authenticationManager(authenticationManager)  //配置身份验证管理器（authenticationManager），用于处理身份验证请求。
                .userDetailsService(userService)                //配置加载用户信息的服务。
                .accessTokenConverter(accessTokenConverter())   //配置访问令牌转换器。
                .tokenEnhancer(enhancerChain);                  //配置令牌增强器，或指定 token 的存储方式。
    }

    /**
     * 生成JWT令牌转换器，并可使用密钥对对令牌进行签名和验证操作。这样配置的授权服务器可以生成和验证 JWT 令牌，以实现基于 JWT 的身份验证和授权功能。
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
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
     * 作用是允许客户端使用表单身份验证，以便授权服务器可以处理这种类型的客户端身份验证请求。这样，客户端可以使用表单提交其身份验证凭证，并与授权服务器进行交互。
     * 这个如果配置支持allowFormAuthenticationForClients的，且url中有client_id和client_secret的会走ClientCredentialsTokenEndpointFilter来保护
     * 如果没有支持allowFormAuthenticationForClients或者有支持但是url中没有client_id和client_secret的，走basic认证保护
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }
}
