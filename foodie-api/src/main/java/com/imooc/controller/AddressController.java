package com.imooc.controller;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@Api(value = "收获地址Controller",tags = "收获地址Controller")
public class AddressController {

    @Autowired
    AddressService addressService;


    @PostMapping("/list")
    @ApiOperation(value = "查询用户收货地址",notes = "查询用户收货地址",httpMethod = "POST")
    public IMOOCJSONResult list(String userId){
        if(StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg("用户id不能为空");
        }
        List<UserAddress> userAddressList = addressService.queryUserAddress(userId);
        return IMOOCJSONResult.ok(userAddressList);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增用户收获地址",notes = "新增用户收获地址",httpMethod = "POST")
    public IMOOCJSONResult add(@RequestBody AddressBO addressBO){
        IMOOCJSONResult imoocjsonResult = checkAddress(addressBO);
        if (imoocjsonResult.getStatus()!= HttpStatus.OK.value()){
            return imoocjsonResult;
        }
        addressService.addUserAddress(addressBO);
        return IMOOCJSONResult.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户收获地址",notes = "修改用户收获地址",httpMethod = "POST")
    public IMOOCJSONResult update(@RequestBody AddressBO addressBO){
        String addressId = addressBO.getAddressId();
        if (StringUtils.isBlank(addressId)){
            return IMOOCJSONResult.errorMsg("地址id不能为空");
        }
        IMOOCJSONResult imoocjsonResult = checkAddress(addressBO);
        if (imoocjsonResult.getStatus()!= HttpStatus.OK.value()){
            return imoocjsonResult;
        }
        addressService.updateUserAddress(addressBO);
        return IMOOCJSONResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除用户收获地址",notes = "删除用户收获地址",httpMethod = "POST")
    public IMOOCJSONResult delete(String userId,String addressId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return IMOOCJSONResult.errorMsg("参数不能为空");
        }
        addressService.deleteAddress(userId,addressId);
        return IMOOCJSONResult.ok();
    }

    @PostMapping("/setDefalut")
    @ApiOperation(value = "设置默认收获地址",notes = "设置默认收获地址",httpMethod = "POST")
    public IMOOCJSONResult setDefalut(String userId,String addressId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)){
            return IMOOCJSONResult.errorMsg("参数不能为空");
        }
        addressService.setDefalut(userId,addressId);
        return IMOOCJSONResult.ok();
    }



    private IMOOCJSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return IMOOCJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return IMOOCJSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return IMOOCJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return IMOOCJSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return IMOOCJSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return IMOOCJSONResult.errorMsg("收货地址信息不能为空");
        }

        return IMOOCJSONResult.ok();
    }


}
