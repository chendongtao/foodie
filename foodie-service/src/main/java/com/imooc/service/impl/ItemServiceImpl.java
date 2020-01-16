package com.imooc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.ItemsCustomMapper;
import com.imooc.mapper.ItemsMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopCatVO;
import com.imooc.service.ItemService;
import com.imooc.utils.DesensitizationUtil;
import com.imooc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsCustomMapper itemsCustomMapper;

    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult queryItemComments(Map map, Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ItemCommentVO> list = itemsCustomMapper.queryItemComments(map);
        for (ItemCommentVO vo : list) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        PageInfo<ItemCommentVO> pageInfo =new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        PagedGridResult pagedGridResult = setPagedGridResult(list, pageInfo);
        return pagedGridResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult searchItems(Map map, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list = itemsCustomMapper.searchItems(map);
        PageInfo<SearchItemsVO> pageInfo =new PageInfo();
        PagedGridResult pagedGridResult = setPagedGridResult(list, pageInfo);
        return pagedGridResult;
    }

    @Override
    public PagedGridResult searchItemsByThirCat(Map map, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list = itemsCustomMapper.searchItemsByThirCat(map);
        PageInfo<SearchItemsVO> pageInfo =new PageInfo();
        PagedGridResult pagedGridResult = setPagedGridResult(list, pageInfo);
        return pagedGridResult;
    }

    @Override
    public List<ShopCatVO> queryItemsBySpecIds(String specIds) {
        String[] split = specIds.split(",");
        List<String> list =new ArrayList();
        Collections.addAll(list,split);
        List<ShopCatVO> shopCatVOList = itemsCustomMapper.queryItemsBySpecIds(list);
        return shopCatVOList;
    }

    private PagedGridResult setPagedGridResult(List<?> list ,PageInfo<?> pageInfo){
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(pageInfo.getPageNum());
        grid.setRows(list);
        grid.setTotal(pageInfo.getPages());
        grid.setRecords(pageInfo.getTotal());
        return grid;
    }
}
