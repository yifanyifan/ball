package com.chain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @Autowired
    TokenTask tokenTask;

    @GetMapping("/index.html")
    public String hello(String code, Model model) {
        model.addAttribute("msg", tokenTask.getData(code));
        return "index";
    }


//    @GetMapping("/index.html")
//    public String index() {
//        return "index";
//    }
//
//    @PostMapping("/login")
//    public String hello(String username, String password, Model model) {
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("username", username);
//        map.add("password", password);
//        map.add("client_secret", "123");
//        map.add("client_id", "southyin");
//        map.add("grant_type", "password");
//        Map<String, String> resp = restTemplate.postForObject("http://localhost:8080/oauth/token", map, Map.class);
//        String access_token = resp.get("access_token");
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + access_token);
//        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8081/admin/hello", HttpMethod.GET, httpEntity, String.class);
//        model.addAttribute("msg", entity.getBody());
//        return "index";
//    }

}

