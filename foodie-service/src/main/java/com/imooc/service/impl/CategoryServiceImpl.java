package com.imooc.service.impl;

import com.imooc.mapper.CategoryMapper;
import com.imooc.mapper.CategoryCustomMapper;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.MyItemsVO;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryCustomMapper categoryCustomMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryRootCategory() {
        Category category =new Category();
        category.setType(1);
        List<Category> categoryList = categoryMapper.select(category);
        return categoryList;
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryCategorySubList(Integer id){
        return categoryCustomMapper.queryCategorySubList(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MyItemsVO> queryMyItemsList(Map map) {
       return categoryCustomMapper.queryMyItemsList(map);
    }
}
