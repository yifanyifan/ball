package com.chain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello1() {
        return "/hello";
    }

    @GetMapping("/admin/hello")
    public String hello2() {
        return "/admin/hello";
    }
}
