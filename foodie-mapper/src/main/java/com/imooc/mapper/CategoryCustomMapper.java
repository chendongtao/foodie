package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.MyItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryCustomMapper {
    List<Category> queryCategorySubList(Integer id);

    List<MyItemsVO> queryMyItemsList(@Param("paramsMap") Map map);

}
