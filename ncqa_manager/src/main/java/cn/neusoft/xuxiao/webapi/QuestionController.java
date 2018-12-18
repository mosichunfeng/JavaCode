package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.service.inf.IQuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Resource(name="questionServiceImpl")
    private IQuestionService questionService;


    @RequestMapping("/error")
    @ResponseBody
    public String testError(){
        questionService.testError();
        return "去你妈的!!";
    }
}
