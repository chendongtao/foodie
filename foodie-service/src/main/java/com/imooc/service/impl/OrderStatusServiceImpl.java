package com.imooc.service.impl;

import com.imooc.mapper.OrderStatusMapper;
import com.imooc.pojo.OrderStatus;
import com.imooc.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    OrderStatusMapper orderStatusMapper;
    @Override
    public void insert(OrderStatus orderStatus) {
        orderStatusMapper.insertSelective(orderStatus);
    }

    @Override
    public void updateOrderStatus(OrderStatus orderStatus) {
         orderStatusMapper.updateByPrimaryKeySelective(orderStatus);

    }

    @Override
    public OrderStatus selectOrderStatusByKey(String id) {
        return orderStatusMapper.selectByPrimaryKey(id);
    }
}
