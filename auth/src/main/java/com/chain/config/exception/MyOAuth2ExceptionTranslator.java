package com.chain.config.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyOAuth2ExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    /**
     * 根据不同的异常栈返回不同的异常对象
     */
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        // 尝试从堆栈跟踪中提取一个SpringSecurityException
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        // 异常栈获取 OAuth2Exception 异常
        Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception((OAuth2Exception) ase);
        }
        // 服务器内部错误自定义 MyOAuth2ServerException 异常
        return handleOAuth2Exception(new MyOAuth2ServerException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));

    }

    /**
     * 将 OAuth2Exception异常包装成 自定义的 MyOAuth2Exception异常。
     */
    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }

        // 使用自定义扩展OAuth2Exception
        e = new MyOAuth2Exception(e.getMessage(), e.getOAuth2ErrorCode(), e);
        ResponseEntity<OAuth2Exception> response = new ResponseEntity<OAuth2Exception>(e, headers, HttpStatus.valueOf(status));
        return response;
    }
}

