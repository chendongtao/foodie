package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;
import com.imooc.service.UsersService;
import com.imooc.utils.IMOOCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/getIsUserNameExist/{username}")
    public IMOOCJSONResult getIsUserNameExist(@PathVariable(value = "username") String name){
        //1、判断用户名是否为空
        if (StringUtils.isBlank(name)){
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        //2、查询用户名是否存在
        boolean userNameExist = usersService.isUserNameExist(name);
        if (userNameExist){
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        return IMOOCJSONResult.ok();
    }

    @PostMapping("/createUsers")
    public IMOOCJSONResult createUsers(@RequestBody UserBo userBo){
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPassword = userBo.getConfirmPassword();
        //1、用户名、密码都不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        //2、查询用户名是否存在
        boolean userNameExist = usersService.isUserNameExist(username);
        if (userNameExist){
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        //3、密码长度不能小于6位
        if (password.length()<6){
            return IMOOCJSONResult.errorMsg("密码长度不能低于6位");
        }
        //4、校验密码
        if (!password.equals(confirmPassword)){
            return IMOOCJSONResult.errorMsg("两次密码不一致");
        }
        Users users = usersService.createUsers(userBo);
        return IMOOCJSONResult.ok(users);
    }
}
