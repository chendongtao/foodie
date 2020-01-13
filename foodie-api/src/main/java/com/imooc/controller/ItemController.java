package com.imooc.controller;


import com.imooc.mapper.ItemsCommentsMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.ItemCommentCountVO;
import com.imooc.service.*;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
@Api(value = "商品页面",tags = "商品页面详情")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemSpecService itemSpecService;
    @Autowired
    private ItemImgService itemImgService;
    @Autowired
    private ItemParamService itemParamService;

    @Autowired
    private ItemCommentService itemCommentService;


    @GetMapping("info/{itemId}")
    @ApiOperation(value = "根据商品id获取商品详情信息",notes ="根据商品id获取商品详情信息",httpMethod = "GET")
    public IMOOCJSONResult info(@PathVariable String itemId){
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemImgService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecs = itemSpecService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemParamService.queryItemParamById(itemId);
        Map map =new HashMap();
        map.put("item",items);
        map.put("itemSpecList",itemsSpecs);
        map.put("itemImgList",itemsImgs);
        map.put("itemParams",itemsParam);
        return IMOOCJSONResult.ok(map);
    }

    @GetMapping("commentLevel")
    @ApiOperation(value = "根据商品id获取商品评价数量",notes ="根据商品id获取商品评价数量",httpMethod = "GET")
    public IMOOCJSONResult commentLevel(String itemId){
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        ItemCommentCountVO itemCommentCountVO = itemCommentService.queryItemCommentCount(itemId);
        return IMOOCJSONResult.ok(itemCommentCountVO);
    }

}
