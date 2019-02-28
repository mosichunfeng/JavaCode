package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.RequestLog;

public interface ILogService {

    public Integer insertRequestLog(RequestLog requestLog);

    public void updateRequestLog(RequestLog requestLog);

}
