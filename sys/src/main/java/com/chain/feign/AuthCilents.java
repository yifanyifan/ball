package com.chain.feign;

import com.chain.auth.Oauth2TokenDto;
import com.chain.common.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "auth")
public interface AuthCilents {
    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
    ResultEntity<Oauth2TokenDto> getToken(@RequestParam Map<String, String> params);
}
