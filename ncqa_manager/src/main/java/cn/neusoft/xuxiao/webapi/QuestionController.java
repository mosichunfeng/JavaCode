package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.*;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.IQuestionService;
import cn.neusoft.xuxiao.webapi.base.BaseController;
import cn.neusoft.xuxiao.webapi.entity.GetQuestionBaseIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.GetQuestionIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController {

    @Resource(name = "questionServiceImpl")
    private IQuestionService questionService;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/pageQuery")
    public String pageQuery(QuestionCriteria questionCriteria, ModelMap map, HttpServletRequest request,HttpServletResponse response) {
        User user = checkAndReturnUser(request,response);
        PaginationResult<GetQuestionIndexResponse> result = questionService.pageQuery(questionCriteria);
        map.put("result", result);
        map.put("user", user);
        return "question_index";
    }


    @RequestMapping("/pageQueryBase")
    public String pageQueryBase(QuestionBaseCriteria questionBaseCriteria, ModelMap map, HttpServletRequest request,HttpServletResponse response) {
        User user = checkAndReturnUser(request,response);
        PaginationResult<GetQuestionBaseIndexResponse> result = questionService.pageQueryBase(questionBaseCriteria);
        map.put("result", result);
        map.put("user", user);
        return "question_base_index";
    }

    @RequestMapping("/insertQuestionBase")
    @ResponseBody
    public String insertQuestionBase(QuestionBase questionBase, HttpServletRequest request,HttpServletResponse response) {
        User user = checkAndReturnUser(request,response);
        questionService.insertQuestionBase(questionBase);
        return generateResponse(ServiceResponseCode.OK);
    }


    @RequestMapping("/updateQuestionBase")
    @ResponseBody
    public String updateQuestionBase(QuestionBase questionBase, HttpServletRequest request,HttpServletResponse response) {
        User user = checkAndReturnUser(request,response);
        questionService.updateQuestionBase(questionBase);
        return generateResponse(ServiceResponseCode.OK);
    }

    @RequestMapping("/deleteQuestionBase")
    @ResponseBody
    public String deleteQuestionBase(QuestionBase questionBase, HttpServletRequest request,HttpServletResponse response) {
        User user = checkAndReturnUser(request,response);
        questionService.deleteQuestionBase(questionBase);
        return generateResponse(ServiceResponseCode.OK);
    }

    @RequestMapping("/findAllKind")
    @ResponseBody
    public String findAllKind() {
        List<KindDO> result = questionService.findAllKind();
        return createResponse(result, ServiceResponseCode.OK);
    }

    /**
     * 模板下载
     */
    @RequestMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        questionService.exportTemplate(response);
    }


    /**
     * 成绩下载
     */
    @RequestMapping("/exportExamGrade")
    public void exportExamGrade(int question_base_id, HttpServletResponse response, HttpServletRequest request) {
        User user = checkAndReturnUser(request,response);
        questionService.exportExamGrade(response, question_base_id);
    }

    /**
     * 导入答题
     */
    @RequestMapping("/importQuestion")
    public void importQuesion(@RequestParam("proxyfile") MultipartFile file, int question_base_id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = checkAndReturnUser(request,response);
        questionService.importQuesion(file, question_base_id);
        response.sendRedirect("/question/pageQuery?question_base_id=" + question_base_id);
    }

}
