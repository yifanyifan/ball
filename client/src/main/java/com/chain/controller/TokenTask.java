package com.chain.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class TokenTask {
    @Autowired
    RestTemplate restTemplate;
    public String access_token = "";
    public String refresh_token = "";

    public String getData(String code) {
        if (StringUtils.isEmpty(access_token) && !StringUtils.isEmpty(code)) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("client_id", "southyin");
            map.add("client_secret", "123456");
            map.add("redirect_uri", "http://127.0.0.1:8080/index.html");
            map.add("grant_type", "authorization_code");
            Map<String, String> resp = restTemplate.postForObject("http://127.0.0.1:8001/oauth/token", map, Map.class);
            JSONObject jsonObject = JSONUtil.parseObj(JSONUtil.toJsonStr(resp.get("data")));
            access_token = jsonObject.getStr("token");
            refresh_token = jsonObject.getStr("refreshToken");
            return loadDataFromResServer();
        } else {
            return loadDataFromResServer();
        }
    }

    private String loadDataFromResServer() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + access_token);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> entity = restTemplate.exchange("http://127.0.0.1:8004/admin/hello", HttpMethod.GET, httpEntity, String.class);
            return entity.getBody();
        } catch (RestClientException e) {
            return "未加载";
        }
    }

    @Scheduled(cron = "0 55 0/1 * * ？")
    public void tokenTask() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "southyin");
        map.add("client_secret", "123456");
        map.add("refresh_token", refresh_token);
        map.add("grant_type", "refresh_token");
        Map<String, String> resp = restTemplate.postForObject("http://127.0.0.1:8001/oauth/token", map, Map.class);
        access_token = resp.get("access_token");
        refresh_token = resp.get("refresh_token");
    }
}

