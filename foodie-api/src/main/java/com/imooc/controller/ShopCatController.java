package com.imooc.controller;

import com.imooc.pojo.bo.ShopCatBO;
import com.imooc.pojo.vo.ShopCatVO;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shopcart")
@Api(value = "购物车Controller",tags = "购物车接口API")
public class ShopCatController {

    Logger logger = Logger.getLogger(ShopCatController.class);

    @PostMapping("/add")
    @ApiOperation(value = "加入购物同步到redis中",tags = "加入购物同步到redis中",httpMethod = "POST")
    public IMOOCJSONResult add(@RequestParam String userId, @RequestBody ShopCatBO shopCatVO){
        if (StringUtils.isBlank(userId)){
            return IMOOCJSONResult.errorMsg(null);
        }

        //TODO 将购物车商品添加redis中

        logger.info(shopCatVO);

        return IMOOCJSONResult.ok();
    }

    @PostMapping("/del")
    @ApiOperation(value = "同步删除选中购物车商品信息",tags = "同步删除选中购物车商品信息",httpMethod = "POST")
    public IMOOCJSONResult del(@RequestParam String userId, String itemSpecId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)){
            return IMOOCJSONResult.errorMsg(null);
        }

        //TODO 将选定的购物车商品从redis中删除

        return IMOOCJSONResult.ok();
    }

}
