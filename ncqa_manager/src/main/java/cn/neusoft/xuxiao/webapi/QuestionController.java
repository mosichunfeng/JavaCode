package cn.neusoft.xuxiao.webapi;

import cn.neusoft.xuxiao.constants.AuthorityConstants;
import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.constants.UserAuthorityConstants;
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
    public String pageQuery(Integer remark1,QuestionCriteria questionCriteria, ModelMap map, HttpServletRequest request,HttpServletResponse response,QuestionCriteriaFill fill) {
        User user = checkAndReturnUser(request,response);
        PaginationResult<GetQuestionIndexResponse> result = questionService.pageQuery(questionCriteria);
        result.setAuthority(queryAuthorityForThis(user, AuthorityConstants.QUESTION_AUTH));
        QuestionCriteria questionCriteria1 = new QuestionCriteria();
        questionCriteria1.setPageNo(fill.getPageNo1());
        questionCriteria1.setPageSize(fill.getPageSize1());
        questionCriteria1.setQuestion_base_id(questionCriteria.getQuestion_base_id());
        PaginationResult<GetQuestionIndexResponse> result2  = questionService.pageQueryFill(questionCriteria1);
        map.put("remark1", remark1);
        map.put("result", result);
        map.put("result2", result2);
        map.put("user", user);
        return "question_index";
    }

    @RequestMapping("/pageQueryFill")
    public String pageQueryFill(ModelMap map,HttpServletRequest request,HttpServletResponse response,QuestionCriteria reqMsg){
        User user = checkAndReturnUser(request,response);
        PaginationResult<GetQuestionIndexResponse> result = questionService.pageQueryFill(reqMsg);
        result.setAuthority(queryAuthorityForThis(user, AuthorityConstants.QUESTION_AUTH));
        map.put("result",result);
        map.put("user", user);
        return "question_index";
    }

    @RequestMapping("/pageQueryBase")
    public String pageQueryBase(QuestionBaseCriteria questionBaseCriteria, ModelMap map, HttpServletRequest request,HttpServletResponse response) {
        User user = checkAndReturnUser(request,response);
        PaginationResult<GetQuestionBaseIndexResponse> result = questionService.pageQueryBase(questionBaseCriteria);
        result.setAuthority(queryAuthorityForThis(user, AuthorityConstants.QUESTION_AUTH));
        map.put("result", result);
        map.put("user", user);
        return "question_base_index";
    }

    @RequestMapping("/insertQuestionBase")
    @ResponseBody
    public String insertQuestionBase(QuestionBase questionBase, HttpServletRequest request,HttpServletResponse response) {
        User user = checkAndReturnUser(request,response);
        checkAuthorityAndExit(user,AuthorityConstants.QUESTION_AUTH,UserAuthorityConstants.AUTH_ADD);
        questionService.insertQuestionBase(questionBase);
        return generateResponse(ServiceResponseCode.OK);
    }


    @RequestMapping("/updateQuestionBase")
    @ResponseBody
    public String updateQuestionBase(QuestionBase questionBase, HttpServletRequest request,HttpServletResponse response) {
        User user = checkAndReturnUser(request,response);
        checkAuthorityAndExit(user,AuthorityConstants.QUESTION_AUTH,UserAuthorityConstants.AUTH_MODIFY);
        questionService.updateQuestionBase(questionBase);
        return generateResponse(ServiceResponseCode.OK);
    }

    @RequestMapping("/deleteQuestionBase")
    @ResponseBody
    public String deleteQuestionBase(QuestionBase questionBase, HttpServletRequest request,HttpServletResponse response) {
        User user = checkAndReturnUser(request,response);
        checkAuthorityAndExit(user,AuthorityConstants.QUESTION_AUTH,UserAuthorityConstants.AUTH_DELETE);
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
     * 模板下载（暂时废弃）
     */
//    @RequestMapping("/exportTemplate")
 //   public void exportTemplate(HttpServletResponse response) {
  //      questionService.exportTemplate(response);
   // }


    /**
     * 成绩下载
     */
    @RequestMapping("/exportExamGrade")
    public void exportExamGrade(int question_base_id, HttpServletResponse response, HttpServletRequest request) {
        User user = checkAndReturnUser(request,response);
        questionService.exportExamGrade(response, question_base_id);
    }

    /**
     * 导出报名表
     */
    @RequestMapping("/exportJoin")
    public void exportJoin(int question_base_id,HttpServletResponse response,HttpServletRequest request){
        User user = checkAndReturnUser(request,response);
        questionService.exportJoin(response, question_base_id);
    }

    /**
     * 导入答题
     */
    @RequestMapping("/importQuestion")
    public void importQuesion(@RequestParam("proxyfile") MultipartFile file, int question_base_id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = checkAndReturnUser(request,response);
        checkAuthorityAndExit(user,AuthorityConstants.QUESTION_AUTH,UserAuthorityConstants.AUTH_ADD);
        questionService.importQuesion(file, question_base_id);
        response.sendRedirect("/question/pageQuery?question_base_id=" + question_base_id);
    }

}
