package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.dao.entity.User;
import cn.neusoft.xuxiao.service.inf.IUserService;
import cn.neusoft.xuxiao.utils.CookieUtils;
import cn.neusoft.xuxiao.utils.StringUtil;
import cn.neusoft.xuxiao.webapi.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController extends BaseController {

    @Resource(name="userServiceImpl")
    private IUserService userService;

    @RequestMapping("/login")
    public void login(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = userService.login(user);
        CookieUtils.setCookie(request, response, "ncqa_token", token);
        if(request.getSession().getAttribute("orgin_url")!=null){
            String orgin_url = request.getSession().getAttribute("orgin_url").toString();
            if(!StringUtil.isEmpty(orgin_url)&&!orgin_url.equals("http://127.0.0.1:4397/login.html")){
                request.getSession().removeAttribute("orgin_url");
                response.sendRedirect(orgin_url);
                return ;
            }
        }
        response.sendRedirect("/main.html");
    }

    @RequestMapping("/logout")
    public void loglout(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if(session==null){
            try {
                response.sendRedirect("/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        session.removeAttribute("orgin_url");
        try {
            response.sendRedirect("/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
