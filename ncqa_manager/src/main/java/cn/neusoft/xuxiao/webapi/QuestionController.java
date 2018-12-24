package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.dao.entity.Question;
import cn.neusoft.xuxiao.dao.entity.QuestionBase;
import cn.neusoft.xuxiao.dao.entity.QuestionCriteria;
import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.service.inf.IQuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Resource(name="questionServiceImpl")
    private IQuestionService questionService;


    @RequestMapping("/pageQuery")
    @ResponseBody
    public String pageQuery(QuestionCriteria questionCriteria, ModelMap map){
        return "question_index";
    }
}
