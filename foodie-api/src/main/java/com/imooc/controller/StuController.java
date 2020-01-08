package com.imooc.controller;

import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class StuController {

    @Autowired
    private StuService stuService;

    @GetMapping("/getStu/{id}")
    public Stu getStu(@PathVariable(name = "id") int id){
        return stuService.getStu(id);
    }

    @PostMapping("/saveStu")
    public int saveStu(@RequestBody Stu stu){
        return stuService.saveStu(stu);
    }

}
