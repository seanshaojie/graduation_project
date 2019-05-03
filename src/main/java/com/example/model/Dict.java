package com.example.model;

import io.swagger.annotations.ApiModelProperty;

public class Dict extends BaseEntity<Long> {
    private static final long serialVersionUID = -6348468011204281350L;
    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("数字值")
    private String k;

    @ApiModelProperty("中文值")
    private String val;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
