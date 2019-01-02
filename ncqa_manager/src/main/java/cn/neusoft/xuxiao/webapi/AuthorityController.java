package cn.neusoft.xuxiao.webapi;


import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.Authority;
import cn.neusoft.xuxiao.dao.entity.User;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.IAuthorityService;
import cn.neusoft.xuxiao.webapi.base.BaseController;
import cn.neusoft.xuxiao.webapi.entity.GetIndexByGroupResponse;
import cn.neusoft.xuxiao.webapi.entity.GetIndexByGroupRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/authority")
public class AuthorityController extends BaseController {

    @Resource(name="authorityServiceImpl")
    private IAuthorityService authorityService;

    @RequestMapping("/getIndexByGroup")
    public String getIndexByGroup(GetIndexByGroupRequest reqMsg , HttpServletRequest request, HttpServletResponse response, ModelMap map){
        User user = checkAndReturnUser(request,response);
        GetIndexByGroupResponse result = authorityService.getIndexByGroup(reqMsg);
        map.put("user", user);
        System.out.println("user====>>"+user);
        map.put("result", result);
        return "authority_index";
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(String username,String password,HttpServletRequest request,HttpServletResponse response){
        User user = checkAndReturnUser(request,response);
        authorityService.addUser(username,password);
        return generateResponse(ServiceResponseCode.OK);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public String modify(Authority reqMsg,HttpServletRequest request,HttpServletResponse response){
        User user = checkAndReturnUser(request, response);
        authorityService.modify(reqMsg);
        return generateResponse(ServiceResponseCode.OK);
    }
}
