package com.imooc.controller;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.enums.YesOrNo;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
