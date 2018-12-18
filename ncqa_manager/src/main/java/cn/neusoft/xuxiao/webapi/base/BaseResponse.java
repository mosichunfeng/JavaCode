package cn.neusoft.xuxiao.webapi.base;

import lombok.Data;

@Data
public class BaseResponse {
	private int code;
	private String remark;
}