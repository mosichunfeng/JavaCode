package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.dao.entity.RequestLog;
import cn.neusoft.xuxiao.dao.inf.ILogDao;
import cn.neusoft.xuxiao.service.inf.ILogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("logServiceImpl")
public class ILogServiceImpl implements ILogService {

    @Resource(name = "ILogDao")
    private ILogDao logDao;

    @Override
    public Integer insertRequestLog(RequestLog requestLog) {
        logDao.insertRequestLog(requestLog);
        return requestLog.getId();
    }

    @Override
    public void updateRequestLog(RequestLog requestLog) {
        logDao.updateRequestLog(requestLog);
    }
}
