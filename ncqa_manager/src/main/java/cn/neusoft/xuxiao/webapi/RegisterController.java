package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.constants.AuthorityConstants;
import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.RegisterCriteria;
import cn.neusoft.xuxiao.dao.entity.User;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.IRegisterService;
import cn.neusoft.xuxiao.webapi.base.BaseController;
import cn.neusoft.xuxiao.webapi.entity.FindRegisterListResponse;
import cn.neusoft.xuxiao.webapi.entity.GetRegisterIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {

    @Resource(name="registerServiceImpl")
    private IRegisterService registerService;


    @RequestMapping("/pageQuery")
    public String pageQuery(RegisterCriteria reqMsg, HttpServletRequest request, HttpServletResponse response, ModelMap map){
        User user = checkAndReturnUser(request,response);
        PaginationResult<GetRegisterIndexResponse> result = registerService.pageQuery(reqMsg);
        result.setAuthority(queryAuthorityForThis(user, AuthorityConstants.REGISTER_AUTH));
        map.put("user", user);
        return "register_index";
    }

    @RequestMapping("/findRegisterListByCallId")
    public String findRegisterListByCallId(ModelMap map,RegisterCriteria reqMsg,HttpServletRequest request,HttpServletResponse response){
        User user = checkAndReturnUser(request,response);
        PaginationResult<FindRegisterListResponse> result = registerService.findRegisterListByCallId(reqMsg);
        result.setAuthority(queryAuthorityForThis(user, AuthorityConstants.REGISTER_AUTH));
        map.put("user", user);
        map.put("result", result);
        map.put("call_id", reqMsg.getCall_id());
        return "register_index";
    }
}
