package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBo;
import com.imooc.service.UsersService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/passport")
@Api(value = "用户注册登录",tags = {"用于用户登录注册"})
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/usernameIsExist")
    @ApiOperation(value = "用户名是否存在",notes = "判断用户名是否存在",httpMethod = "GET")
    public IMOOCJSONResult getIsUserNameExist(@RequestParam(name="username") String name){
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

    @PostMapping("/regist")
    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    public IMOOCJSONResult createUsers(@RequestBody UserBo userBo,
                                       HttpServletRequest request,
                                       HttpServletResponse response){
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
        Users user = usersService.createUsers(userBo);
        //5、注册成功后，将用户信息存至cookie中
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(user),3000,true);
        //TODO 同步购物车数据

        return IMOOCJSONResult.ok(this.setNullProperties(user));
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    public IMOOCJSONResult login(@RequestBody UserBo userBo,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }
        userBo.setPassword(MD5Utils.getMD5Str(password));
        Users user = usersService.login(userBo);
        if (user==null){
            return IMOOCJSONResult.errorMsg("用户名或者密码不正确");
        }

        //5、登录成功后，将用户信息存至cookie中
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(user),    3000,true);

        //TODO 购物车同步
        return IMOOCJSONResult.ok(this.setNullProperties(user));
    }

    private Users setNullProperties(Users user){
        user.setPassword(null);
        user.setRealname(null);
        user.setBirthday(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        return user;
    }
}
