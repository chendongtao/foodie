package com.imooc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class HelloWorld {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorld.class);


    @GetMapping("/hello")
    public String sayHelloWold(){
        LOGGER.info("info test 测试.....");
        return "<P style=\"font-size:200px;color:orange\">阿大鲁下课鸟<P>";
    }
    @PostMapping("/postHello")
    public String sayHelloWold(String name){
        return "hello 阿大你买再看鸟！！！"+name;
    }
}
