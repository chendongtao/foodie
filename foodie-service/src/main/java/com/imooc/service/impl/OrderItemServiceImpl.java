package com.imooc.service.impl;

import com.imooc.mapper.OrderItemsMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemsService {

    @Autowired
    OrderItemsMapper orderItemsMapper;

    @Override
    public void insertOrderItems(OrderItems orderItems) {
        orderItemsMapper.insertSelective(orderItems);
    }
}
