package cn.neusoft.xuxiao.webapi.entity;

import cn.neusoft.xuxiao.webapi.base.BaseResponse;
import lombok.Data;

@Data
public class PaginationResult<T> extends BaseResponse {
    private T result;
    private BasePage basePage;
}
