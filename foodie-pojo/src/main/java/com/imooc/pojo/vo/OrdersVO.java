package com.imooc.pojo.vo;

public class OrdersVO {

    private String orderId;
    private MerchanOrdersVO merchanOrdersVO;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchanOrdersVO getMerchanOrdersVO() {
        return merchanOrdersVO;
    }

    public void setMerchanOrdersVO(MerchanOrdersVO merchanOrdersVO) {
        this.merchanOrdersVO = merchanOrdersVO;
    }
}
