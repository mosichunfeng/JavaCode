package cn.neusoft.xuxiao.dao.inf;


import cn.neusoft.xuxiao.dao.entity.RequestLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("ILogDao")
public interface ILogDao {

    void insertRequestLog(RequestLog requestLog);

    void updateRequestLog(RequestLog requestLog);
}
