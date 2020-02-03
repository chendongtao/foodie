package com.imooc.service.impl;

import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.OrdersBO;
import com.imooc.pojo.enums.OrderStatusEnum;
import com.imooc.pojo.enums.YesOrNo;
import com.imooc.pojo.vo.MerchanOrdersVO;
import com.imooc.pojo.vo.OrdersVO;
import com.imooc.service.*;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.annotation.Order;

import java.util.Date;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final Integer POST_AMOUNT =0;

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    AddressService addressService;
    @Autowired
    Sid sid;
    @Autowired
    ItemSpecService itemSpecService;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemImgService itemImgService;

    @Autowired
    OrderItemsService orderItemsService;

    @Autowired
    OrderStatusService orderStatusService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrdersVO  create(OrdersBO ordersBO) {
        String addressId = ordersBO.getAddressId();
        String itemSpecIds = ordersBO.getItemSpecIds();
        String leftMsg = ordersBO.getLeftMsg();
        Integer payMethod = ordersBO.getPayMethod();
        String userId = ordersBO.getUserId();
        //获取收获地址
        UserAddress userAddress = addressService.selectUserAddressByPrimaryKey(addressId);
        if (null==userAddress){
            throw new RuntimeException("找不到收获地址,请确认收货地址ID");
        }
        int totalAmount =0;
        int realPayAmount =0;
        //创建主订单orders
        Orders orders =new Orders();
        orders.setId(sid.nextShort());
        orders.setUserId(userId);
        orders.setReceiverName(userAddress.getReceiver());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setReceiverAddress(userAddress.getProvince()+userAddress.getCity()+userAddress.getDistrict()+userAddress.getDetail());
        orders.setPostAmount(POST_AMOUNT);
        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);
        orders.setIsComment(YesOrNo.NO.value);
        orders.setIsDelete(YesOrNo.NO.value);
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        ordersMapper.insertSelective(orders) ;
        String ordersId = orders.getId();
        //遍历商品规格id，新增订单子表
        for (String itemSpecId : itemSpecIds.split(",")){

            //TODO 从redis获取购买数量
            int buyCount = 1;

            //获取商品规格信息
            ItemsSpec itemsSpec = itemSpecService.selectItemsSpecByPrimaryKey(itemSpecId);
            if (itemsSpec==null){
                throw  new RuntimeException("找不到商品规格信息");
            }
            String itemId = itemsSpec.getItemId();
            //商品原价和优惠价
            Integer priceNormal = itemsSpec.getPriceNormal();
            Integer priceDiscount = itemsSpec.getPriceDiscount();
            //累计总价
            totalAmount+=priceNormal*buyCount;
            //累计实际支付价格
            realPayAmount+=priceDiscount*buyCount;
            //获取商品信息
            Items items = itemService.queryItemById(itemId);
            //获取商品图片主图
            ItemsImg itemsImg = itemImgService.selectMainImgByParam(itemId);
            OrderItems orderItems =new OrderItems();
            orderItems.setId(sid.nextShort());
            orderItems.setOrderId(ordersId);
            orderItems.setItemId(itemId);
            orderItems.setItemImg(itemsImg.getUrl());
            orderItems.setItemName(items.getItemName());
            orderItems.setItemSpecId(itemSpecId);
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            orderItems.setBuyCounts(buyCount);
            orderItemsService.insertOrderItems(orderItems);
        }
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
        ordersMapper.updateByPrimaryKeySelective(orders);
        //订单状态表
        OrderStatus orderStatus =new OrderStatus();
        orderStatus.setOrderId(ordersId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.value);
        orderStatus.setCreatedTime(new Date());
        orderStatusService.insert(orderStatus);


        //组装参数，调用微信统一下单接口API，这里采用调用中间的支付中心来完成
        MerchanOrdersVO merchanOrdersVO =new MerchanOrdersVO();
        merchanOrdersVO.setMerchantOrderId(ordersId);
        merchanOrdersVO.setMerchantUserId(userId);
        merchanOrdersVO.setAmount(realPayAmount+POST_AMOUNT);
        merchanOrdersVO.setPayMethod(payMethod);

        OrdersVO ordersVO =new OrdersVO();
        ordersVO.setOrderId(ordersId);
        ordersVO.setMerchanOrdersVO(merchanOrdersVO);

        return ordersVO;
    }
}
