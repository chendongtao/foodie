package com.imooc.pojo.vo;

public enum  CommentLevel {
    GOOD(1,"好评"),
    NORMAL(2,"中评"),
    BAD(3,"差评");

    public final Integer value;
    public final String label;

    CommentLevel(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
