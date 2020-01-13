package com.imooc.service.impl;

import com.imooc.mapper.ItemsMapper;
import com.imooc.mapper.ItemsParamMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsParam;
import com.imooc.service.ItemParamService;
import com.imooc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private ItemsParamMapper itemsParamMapper;


    @Override
    public ItemsParam queryItemParamById(String itemId) {
        Example example =new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsParamMapper.selectOneByExample(example);
    }
}
