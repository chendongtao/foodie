package com.imooc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class HelloWorld {



    @GetMapping("/hello")
    public String sayHelloWold(){
        return "hello 阿大你买再看鸟！！！";
    }
    @PostMapping("/postHello")
    public String sayHelloWold(String name){
        return "hello 阿大你买再看鸟！！！"+name;
    }
}
