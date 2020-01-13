package com.imooc.controller;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.enums.YesOrNo;
import com.imooc.pojo.vo.MyItemsVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
@Api(value = "首页API",tags = "首页API")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/carousel")
    @ApiOperation(value = "轮播图展示",notes = "首页轮播图展示",httpMethod = "GET")
    public IMOOCJSONResult carousel(){
        List<Carousel> carousels = carouselService.queryAllCarousel(YesOrNo.YES.value);
        return IMOOCJSONResult.ok(carousels);
    }

    @GetMapping("/cats")
    @ApiOperation(value = "查询一级商品分类",notes = "查询一级商品分类",httpMethod = "GET")
    public IMOOCJSONResult cats(){
        List<Category> categoryList = categoryService.queryRootCategory();
        return IMOOCJSONResult.ok(categoryList);
    }

    @GetMapping("/subCat/{rootCatId}")
    @ApiOperation(value = "查询子类商品分类",notes = "查询子类商品分类",httpMethod = "GET")
    public IMOOCJSONResult subCat(@ApiParam(required = true ,name = "rootCatId",value = "一级分类ID") @PathVariable("rootCatId") Integer rootCatId){
        List<Category> categoryList = categoryService.queryCategorySubList(rootCatId);
        return IMOOCJSONResult.ok(categoryList);
    }

    @GetMapping("/sixNewItems/{rootCatId}")
    @ApiOperation(value = "首页滚轮向下懒加载商品分类和列表信息",notes = "首页滚轮向下懒加载商品分类和列表信息",httpMethod = "GET")
    public IMOOCJSONResult queryMyItemsList(@PathVariable("rootCatId") String rootCatId){
        Map map =new HashMap();
        map.put("rootCatId",rootCatId);
        List<MyItemsVO> myItemsVOS = categoryService.queryMyItemsList(map);
        return IMOOCJSONResult.ok(myItemsVOS);
    }
}
