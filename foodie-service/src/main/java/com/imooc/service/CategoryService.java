package com.imooc.service;

import com.imooc.pojo.Category;

import java.util.List;

public interface CategoryService {

    List<Category> queryRootCategory();

    List<Category> queryCategorySubList(Integer id);
}
