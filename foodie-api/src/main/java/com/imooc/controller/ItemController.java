package com.imooc.controller;


import com.github.pagehelper.Page;
import com.imooc.mapper.ItemsCommentsMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.ItemCommentCountVO;
import com.imooc.service.*;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/info/{itemId}")
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

    @GetMapping("/commentLevel")
    @ApiOperation(value = "根据商品id获取商品评价数量",notes ="根据商品id获取商品评价数量",httpMethod = "GET")
    public IMOOCJSONResult commentLevel(String itemId){
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        ItemCommentCountVO itemCommentCountVO = itemCommentService.queryItemCommentCount(itemId);
        return IMOOCJSONResult.ok(itemCommentCountVO);
    }

    @GetMapping("/comments")
    @ApiOperation(value = "根据评价等级获取商品评论列表",notes ="根据评价等级获取商品评论列表",httpMethod = "GET")
    public IMOOCJSONResult comments(String itemId, @RequestParam(value = "level",required = false) String level,
                                    @RequestParam(value = "page",required = false)Integer page,
                                    @RequestParam(value = "pageSize",required = false)Integer pageSize){
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        Map map =new HashMap();
        map.put("itemId",itemId);
        map.put("commentLevel",level);
        page =page==null?1:page;
        pageSize =pageSize==null?10:pageSize;
        PagedGridResult pagedGridResult = itemService.queryItemComments(map, page,pageSize);
        return IMOOCJSONResult.ok(pagedGridResult);
    }

    @GetMapping("/search")
    @ApiOperation(value = "商品搜索",notes ="商品搜索",httpMethod = "GET")
    public IMOOCJSONResult search(String keywords,String sort,
                                  @RequestParam(value = "page",required = false)Integer page,
                                  @RequestParam(value = "pageSize",required = false)Integer pageSize){
        if (StringUtils.isBlank(keywords)){
            return IMOOCJSONResult.errorMsg(null);
        }
        page =page==null?1:page;
        pageSize =pageSize==null?10:pageSize;
        Map map =new HashMap();
        map.put("keywords",keywords);
        map.put("sort",sort);
        PagedGridResult pagedGridResult = itemService.searchItems(map, page, pageSize);
        return IMOOCJSONResult.ok(pagedGridResult);
    }

    @GetMapping("/catItems")
    @ApiOperation(value = "分类商品搜索",notes ="分类商品搜索",httpMethod = "GET")
    public IMOOCJSONResult catItems(String catId,String sort,
                                  @RequestParam(value = "page",required = false)Integer page,
                                  @RequestParam(value = "pageSize",required = false)Integer pageSize){
        if (StringUtils.isBlank(catId)){
            return IMOOCJSONResult.errorMsg(null);
        }
        page =page==null?1:page;
        pageSize =pageSize==null?10:pageSize;
        Map map =new HashMap();
        map.put("catId",catId);
        map.put("sort",sort);
        PagedGridResult pagedGridResult = itemService.searchItemsByThirCat(map, page, pageSize);
        return IMOOCJSONResult.ok(pagedGridResult);
    }

}
