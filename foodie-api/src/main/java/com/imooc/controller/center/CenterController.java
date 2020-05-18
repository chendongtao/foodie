package com.imooc.controller.center;

import com.imooc.pojo.Users;
import com.imooc.service.CenterUserService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Center-用户中心",tags = "用户中心展示相关接口")
@RequestMapping("center")
public class CenterController {

    @Autowired
    CenterUserService centerUserService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "获取用户信息",notes = "获取用户信息",httpMethod = "GET")
    public IMOOCJSONResult userInfo(@RequestParam String userId){
        Users users = centerUserService.userInfo(userId);
        return IMOOCJSONResult.ok(users);
    }

}
