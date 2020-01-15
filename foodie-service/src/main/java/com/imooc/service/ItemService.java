package com.imooc.service;

import com.github.pagehelper.Page;
import com.imooc.pojo.Items;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.utils.PagedGridResult;

import java.util.List;
import java.util.Map;

public interface ItemService {

    Items queryItemById(String itemId);

    PagedGridResult queryItemComments(Map map, Integer pageNum,Integer pageSize);

    PagedGridResult searchItems(Map map,Integer page,Integer pageSize);

    PagedGridResult searchItemsByThirCat(Map map,Integer page,Integer pageSize);
}
