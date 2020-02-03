package com.imooc.controller;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.OrdersBO;
import com.imooc.pojo.enums.OrderStatusEnum;
import com.imooc.pojo.vo.MerchanOrdersVO;
import com.imooc.pojo.vo.OrdersVO;
import com.imooc.service.OrderStatusService;
import com.imooc.service.OrdersService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController{

    @Autowired
    OrdersService ordersService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderStatusService orderStatusService;

    @PostMapping("/testPostForObject")
    public Map testPostForObject(@RequestBody MultiValueMap map){
        if (map == null){
                return null;
        }
        return map;
    }


    @PostMapping("/create")
    @ApiOperation("创建订单")
    public IMOOCJSONResult createOrder(@RequestBody OrdersBO ordersBO){
        IMOOCJSONResult imoocjsonResult = this.checkOrdersBOProperties(ordersBO);
        if (imoocjsonResult.getStatus()!= HttpStatus.OK.value()){
            return imoocjsonResult;
        }
        OrdersVO ordersVO = ordersService.create(ordersBO);
        String orderId = ordersVO.getOrderId();

        MerchanOrdersVO merchanOrdersVO = ordersVO.getMerchanOrdersVO();
        //TODO 整合redis后，删除redsi购物车指定商品，并同步cookie

        //测试，1分钱支付
        merchanOrdersVO.setAmount(1);
        //设置回调地址
        merchanOrdersVO.setReturnUrl(RETURNURL);
        //组装参数，发送至支付中心保存
        HttpHeaders httpHeaders =new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("imoocUserId","tmp2020");
        httpHeaders.add("password","tmp2020");
        HttpEntity<MerchanOrdersVO> httpEntity =new HttpEntity<>(merchanOrdersVO,httpHeaders);
        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(PAY_PLATFORM_URL, httpEntity, IMOOCJSONResult.class);
        IMOOCJSONResult body = responseEntity.getBody();
        if (body.getStatus()!=HttpStatus.OK.value()){
            return IMOOCJSONResult.errorMsg("订单发送至支付中心失败");
        }
        return IMOOCJSONResult.ok(orderId);
    }

    @PostMapping("/NotifyWeixinPay")
    public String getNotifyWeixinPay(@RequestBody MultiValueMap<String,String> map){
        List<String> list = map.get("merchantOrderId");
        String merchantOrderId = list.get(0);
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(merchantOrderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_DELIVER.value);
        orderStatus.setPayTime(new Date());
        orderStatusService.updateOrderStatus(orderStatus);
        return "天天支付后台更新成功";
    }

    @PostMapping("/getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(String orderId){
        OrderStatus orderStatus = orderStatusService.selectOrderStatusByKey(orderId);
        return IMOOCJSONResult.ok(orderStatus);
    }


    private IMOOCJSONResult checkOrdersBOProperties(OrdersBO ordersBO){
        String addressId = ordersBO.getAddressId();
        String itemSpecIds = ordersBO.getItemSpecIds();
//        String leftMsg = ordersBO.getLeftMsg();
        Integer payMethod = ordersBO.getPayMethod();
        String userId = ordersBO.getUserId();
        if (StringUtils.isAnyBlank(addressId,itemSpecIds,userId) || null ==payMethod){
            return IMOOCJSONResult.errorMsg("参数不能为空");
        }
        return IMOOCJSONResult.ok();
    }
}
