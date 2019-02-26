package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.Call;
import cn.neusoft.xuxiao.dao.entity.CallCriteria;
import cn.neusoft.xuxiao.webapi.entity.GetCallIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;

import javax.servlet.http.HttpServletResponse;

public interface ICallService {
    PaginationResult<GetCallIndexResponse> pageQuery(CallCriteria reqMsg);

    void insertCall(Call call);

    void exportCallExcel(HttpServletResponse response,Integer call_id);

    void exportNoCallExcel(HttpServletResponse response, Integer call_id);
}
