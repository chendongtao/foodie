package com.imooc.controller.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.CenterUserInfoBo;
import com.imooc.service.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户信息中心",tags = "用户信息中心")
@RestController
@RequestMapping("userInfo")
public class CenterUserInfoController {

    @Autowired
    CenterUserService centerUserService;

    @PostMapping("/update")
    @ApiOperation(value = "修改保存用户信息",notes = "修改保存用户信息",httpMethod = "POST")
    public IMOOCJSONResult update(@RequestParam String userId, @RequestBody @Valid CenterUserInfoBo centerUserInfoBo,
                                  HttpServletRequest request, HttpServletResponse response, BindingResult result){
        if (result.hasErrors()){
            Map<String, String> errorMap = getErrorMap(result);
            return IMOOCJSONResult.errorMap(errorMap);
        }
        Users users = centerUserService.update(centerUserInfoBo);
        //更新Cookie
        users = this.setNullProperties(users);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(users),    3000,true);

        return IMOOCJSONResult.ok();

    }

    private Map<String,String> getErrorMap(BindingResult result){
        Map<String,String> map =new HashMap();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            map.put(field,defaultMessage);
        }
        return map;
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
