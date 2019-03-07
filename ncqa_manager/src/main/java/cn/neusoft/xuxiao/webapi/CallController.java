package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.constants.AuthorityConstants;
import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.constants.UserAuthorityConstants;
import cn.neusoft.xuxiao.dao.entity.Call;
import cn.neusoft.xuxiao.dao.entity.CallCriteria;
import cn.neusoft.xuxiao.dao.entity.User;
import cn.neusoft.xuxiao.service.inf.ICallService;
import cn.neusoft.xuxiao.webapi.base.BaseController;
import cn.neusoft.xuxiao.webapi.entity.GetCallIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/call")
public class CallController extends BaseController {

    @Resource
    private ICallService callService;

    @RequestMapping("/pageQuery")
    public String pageQuery(CallCriteria reqMsg, HttpServletRequest request, ModelMap map, HttpServletResponse response){
        User user = checkAndReturnUser(request,response);
        PaginationResult<GetCallIndexResponse> result= callService.pageQuery(reqMsg);
        result.setAuthority(queryAuthorityForThis(user, AuthorityConstants.REGISTER_AUTH));
        map.put("result", result);
        map.put("user", user);
        return "call";
    }

    @RequestMapping("/insertCall")
    @ResponseBody
    public String insertCall(Call call,HttpServletRequest request,HttpServletResponse response){
        User user = checkAndReturnUser(request,response);
        checkAuthorityAndExit(user,AuthorityConstants.REGISTER_AUTH,UserAuthorityConstants.AUTH_ADD);
        callService.insertCall(call);
        return generateResponse(ServiceResponseCode.OK);
    }

    @RequestMapping("/exportCallExcel")
    public void exportCallExcel(HttpServletResponse response,HttpServletRequest request,Integer call_id){
        User user = checkAndReturnUser(request,response);
        callService.exportCallExcel(response,call_id);
    }

    @RequestMapping("/exportNoCallExcel")
    public void exportNoCallExcel(HttpServletRequest request,HttpServletResponse response,Integer call_id){
        User user = checkAndReturnUser(request,response);
        callService.exportNoCallExcel(response,call_id);
    }
}
