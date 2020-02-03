package com.imooc.service.impl;

import com.imooc.mapper.ItemsSpecMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsSpec;
import com.imooc.service.ItemService;
import com.imooc.service.ItemSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ItemSpecServiceImpl implements ItemSpecService {
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example =new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    public ItemsSpec selectItemsSpecByPrimaryKey(String id) {
        return itemsSpecMapper.selectByPrimaryKey(id);
    }
}
