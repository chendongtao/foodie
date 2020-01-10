package com.imooc.pojo.enums;

public enum YesOrNo {
    NO(0,"否"),
    YES(1,"是");

    public final Integer value;
    public final String label;

    YesOrNo(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
