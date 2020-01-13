package com.imooc.service;

import com.imooc.pojo.Category;
import com.imooc.pojo.vo.MyItemsVO;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    List<Category> queryRootCategory();

    List<Category> queryCategorySubList(Integer id);

    List<MyItemsVO> queryMyItemsList(Map map);
}
