package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.RegisterCriteria;
import cn.neusoft.xuxiao.webapi.entity.GetRegisterIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;

public interface IRegisterService {

    PaginationResult<GetRegisterIndexResponse> pageQuery(RegisterCriteria reqMsg);
}
