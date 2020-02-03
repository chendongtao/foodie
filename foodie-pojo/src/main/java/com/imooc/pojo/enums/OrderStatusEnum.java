package com.imooc.pojo.enums;

public enum OrderStatusEnum {
    WAIT_PAY(10, "待付款"),
    WAIT_DELIVER(20, "已付款，待发货"),
    WAIT_RECEIVE(30, "已发货，待收货"),
    SUCCESS(40, "交易成功"),
    CLOSE(50, "交易关闭");

    public final Integer value;
    public final String label;

    OrderStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
