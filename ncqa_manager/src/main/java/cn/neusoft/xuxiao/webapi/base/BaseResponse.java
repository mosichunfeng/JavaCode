package cn.neusoft.xuxiao.webapi.base;

import cn.neusoft.xuxiao.dao.entity.Authority;
import lombok.Data;

@Data
public class BaseResponse {
	private int code;
	private String remark;
	Authority authority;
}