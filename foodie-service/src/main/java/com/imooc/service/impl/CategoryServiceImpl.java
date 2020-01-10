package com.imooc.service.impl;

import com.imooc.mapper.CategoryMapper;
import com.imooc.mapper.CategoryCustomMapper;
import com.imooc.pojo.Category;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryCustomMapper categoryCustomMapper;

    @Override
    public List<Category> queryRootCategory() {
        Category category =new Category();
        category.setType(1);
        List<Category> categoryList = categoryMapper.select(category);
        return categoryList;
    }
    @Override
    public List<Category> queryCategorySubList(Integer id){
        return categoryCustomMapper.queryCategorySubList(id);
    }
}
