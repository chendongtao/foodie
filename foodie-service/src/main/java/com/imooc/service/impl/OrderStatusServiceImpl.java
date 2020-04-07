package com.imooc.service.impl;

import com.imooc.mapper.OrderStatusMapper;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.enums.OrderStatusEnum;
import com.imooc.service.OrderStatusService;
import com.imooc.utils.DateUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    @Override
    public void closeOrder() {
        OrderStatus orderStatus =new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.value);
        List<OrderStatus> orderStatusList = orderStatusMapper.select(orderStatus);
        for (OrderStatus os : orderStatusList) {
            Date createdTime = os.getCreatedTime();
            int days = DateUtil.daysBetween(createdTime, new Date());
            if (days>=1){
                os.setOrderStatus(OrderStatusEnum.CLOSE.value);
                os.setCloseTime(new Date());
                orderStatusMapper.updateByPrimaryKeySelective(os);
            }
        }
    }
}
