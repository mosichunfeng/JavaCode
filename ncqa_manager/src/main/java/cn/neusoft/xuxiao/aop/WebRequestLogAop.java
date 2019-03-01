package cn.neusoft.xuxiao.aop;

import cn.neusoft.xuxiao.dao.entity.RequestLog;
import cn.neusoft.xuxiao.dao.entity.User;
import cn.neusoft.xuxiao.service.inf.ILogService;
import cn.neusoft.xuxiao.utils.*;
import cn.neusoft.xuxiao.webapi.entity.GenerateResponse;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Aspect
@Component
public class WebRequestLogAop {
    private Logger logger = LoggerFactory.getLogger(WebRequestLogAop.class);

    @Autowired
    private RedisUtil redisUtil;

    @Resource(name="logServiceImpl")
    private ILogService logService;


    /**
     * 请求时的系统秒数
     */
    private long start;

    /**
     * requestLog的id
     */
    private Integer requestLogId = null;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void executeWhileRequest(){
    }

    /**
     * 从缓存中取出用户数据
     * @param request
     * @return
     */
    public User getUserFromCache(HttpServletRequest request){
        String token = CookieUtils.getCookieValue(request, "ncqa_token");
        String  userData = (String) redisUtil.get("SESSION:" + token);
        if(StringUtil.isEmpty(userData)){
            return new User();
        }
        return JsonTool.jsonToObject(userData, User.class);
    }


    /**
     * 前置通知
     */
    @Before("executeWhileRequest()")
    public void insertLog(){
        /**
         * 构建日志对象
         */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        RequestLog requestLog = new RequestLog();
        requestLog.setUser_id(getUserFromCache(request).getId());
        requestLog.setUser_name(getUserFromCache(request).getUsername());
        requestLog.setUrl(request.getRequestURL().toString());
        requestLog.setMethod(request.getMethod());
        requestLog.setParams(JSON.toJSONString(request.getParameterMap()));
        requestLog.setRequest_time(TimeTool.DateToString(new Date()));
        requestLog.setClient_ip(request.getRemoteAddr());

        requestLogId = logService.insertRequestLog(requestLog);
        start = System.currentTimeMillis();
    }

    /**
     * 后置通知（返回之后）
     */
    @AfterReturning(pointcut = "executeWhileRequest()",returning = "res")
    public void updateLog(String res){
        long end = System.currentTimeMillis();
        Integer spend_time = Math.toIntExact((end - start));
        RequestLog requestLog = new RequestLog();
        requestLog.setResponse_time(TimeTool.DateToString(new Date()));
        requestLog.setSpend_time(spend_time);

        logger.info("res======>"+res);
        if(requestLogId!=null) {

            requestLog.setId(requestLogId);
            if (res.contains("remark") && res.contains("code")) {
                GenerateResponse generateResponse = JsonTool.jsonToObject(res, GenerateResponse.class);
                requestLog.setResponse_code(generateResponse.getCode());
                requestLog.setResponse_remark(generateResponse.getRemark());
            }else{
                requestLog.setResponse_code(200);
                requestLog.setResponse_remark("返回到freemarker页面");
            }
            logService.updateRequestLog(requestLog);
        }
    }

}
