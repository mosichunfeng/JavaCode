package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class GenerateResponse {
    private int code;
    private String remark;
    private Object result;
}
