package com.imooc.service.impl;

import com.imooc.mapper.ItemsImgMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.enums.YesOrNo;
import com.imooc.service.ItemImgService;
import com.imooc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ItemImgServiceImpl implements ItemImgService {

    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example =new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    public ItemsImg selectMainImgByParam(String itemId) {
        ItemsImg itemsImg =new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.value);
        return itemsImgMapper.selectOne(itemsImg);
    }
}
