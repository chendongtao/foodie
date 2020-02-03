package com.imooc.service;

import com.imooc.pojo.ItemsSpec;

import java.util.List;

public interface ItemSpecService {

    List<ItemsSpec> queryItemSpecList(String itemId);

    ItemsSpec selectItemsSpecByPrimaryKey(String id);
}
