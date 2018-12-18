package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.service.inf.IRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Resource(name="registerServiceImpl")
    private IRegisterService registerService;
}
