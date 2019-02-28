package cn.neusoft.xuxiao.webapi.base;

import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.User;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.exception.CommonRuntimeException;
import cn.neusoft.xuxiao.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private RedisUtil redisUtil;

    protected String generateResponse(int code) {
        BaseResponse result = new BaseResponse();
        result.setCode(code);
        result.setRemark(getErrorRemark(Integer.valueOf(code)));
        return JsonTool.dataToJson(result);
    }

    protected String generateResponse(BaseResponse result, int code) {
        result.setCode(code);
        result.setRemark(getErrorRemark(Integer.valueOf(code)));
        BeanUtils.defaultProperties(result);
        return JsonTool.dataToJsonWithNull(result);
    }

    protected String generateExcludeNullResponse(BaseResponse result, int code) {
        result.setCode(code);
        result.setRemark(getErrorRemark(Integer.valueOf(code)));
        BeanUtils.defaultProperties(result);
        return JsonTool.dataToJson(result);
    }

    protected <T> String createResponse(T result, int code) {
        Map map = new HashMap();
        map.put("code", Integer.valueOf(code));
        map.put("remark", getErrorRemark(Integer.valueOf(code)));
        map.put("result", result);
        return JsonTool.dataToJsonWithNull(map);
    }

    protected <T> String createNonNullResponse(T result, int code) {
        Map map = new HashMap();
        map.put("code", Integer.valueOf(code));
        map.put("remark", getErrorRemark(Integer.valueOf(code)));
        map.put("result", result);
        return JsonTool.dataToJson(map);
    }

    protected String getErrorRemark(Integer code) {
        if (ServiceResponseCode.STATUS_CODE_MAP.containsKey(code)) {
            return (String) ServiceResponseCode.STATUS_CODE_MAP.get(code);
        }
        return String.valueOf(code);
    }

    @ResponseBody
    @ExceptionHandler
    public String handlerException(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        BaseResponse errorResponse = new BaseResponse();
        if ((ex instanceof CommonRuntimeException)) {
            CommonRuntimeException crex = (CommonRuntimeException) ex;
            errorResponse.setCode(Integer.valueOf(crex.getCode()).intValue());
            errorResponse.setRemark(crex.getMsg());
            response.addHeader("Exception-Code", crex.getCode());

            if (String.valueOf(502).equals(crex.getCode()))
                logger.error(crex.toString(), ex);
        } else {
            errorResponse.setCode(500);
            errorResponse.setRemark("应用服务器异常，请联系系统管理员！");
            response.addHeader("Exception-Code", String.valueOf(500));
            logger.error("=> 应用服务器异常，请联系系统管理员！[ERRORCODE={}] [{}]", new Object[]{Integer.valueOf(500), ex.toString() + ": " + ex.getMessage(), ex});
        }
        return JsonTool.dataToJson(errorResponse);
    }


    protected User checkAndReturnUser(HttpServletRequest request,HttpServletResponse response){
        String token = CookieUtils.getCookieValue(request, "ncqa_token");
        if (StringUtil.isEmpty(token)) {
            request.getSession().setAttribute("orgin_url", request.getRequestURL());
            try {
                response.sendRedirect("http://127.0.0.1:4397/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new BusinessException(String.valueOf(ServiceResponseCode.NOT_LOG_IN), "未登录");
        }
        String  userData = (String) redisUtil.get("SESSION:" + token);
        if(StringUtil.isEmpty(userData)){
            try {
                response.sendRedirect("http://127.0.0.1:4397/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new BusinessException(String.valueOf(ServiceResponseCode.NOT_LOG_IN), "未登录");
        }
        redisUtil.expire("SESSION:"+token, 1800);
        return JsonTool.jsonToObject(userData, User.class);
    }
}