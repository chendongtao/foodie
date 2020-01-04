package com.imooc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/hello")
    public String sayHelloWold(){
        return "hello 阿大你买再看鸟！！！";
    }
}
