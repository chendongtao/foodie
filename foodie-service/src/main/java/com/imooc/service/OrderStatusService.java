package com.imooc.service;

import com.imooc.pojo.OrderStatus;

public interface OrderStatusService {

    void insert(OrderStatus orderStatus);

    void updateOrderStatus(OrderStatus orderStatus);

    OrderStatus selectOrderStatusByKey(String id);

    void closeOrder();
}
