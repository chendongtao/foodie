package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopCatVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCustomMapper {
    List<ItemCommentVO> queryItemComments(Map<String,Object> map);

    List<SearchItemsVO> searchItems(Map map);

    List<SearchItemsVO> searchItemsByThirCat(Map map);

    List<ShopCatVO> queryItemsBySpecIds(@Param("paramsList") List list);
}
