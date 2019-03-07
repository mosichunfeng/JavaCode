package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.*;
import cn.neusoft.xuxiao.webapi.entity.GetQuestionBaseIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.GetQuestionIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IQuestionService {

    PaginationResult<GetQuestionBaseIndexResponse> pageQueryBase(QuestionBaseCriteria reqMsg);

    void insertQuestionBase(QuestionBase questionBase);

    void updateQuestionBase(QuestionBase questionBase);

    void deleteQuestionBase(QuestionBase questionBase);

    List<KindDO> findAllKind();

    void exportTemplate(HttpServletResponse response);

    PaginationResult<GetQuestionIndexResponse> pageQuery(QuestionCriteria reqMsg);

    void exportExamGrade(HttpServletResponse response,int question_base_id);

    void importQuesion(MultipartFile file, int question_base_id);

    PaginationResult<GetQuestionIndexResponse> pageQueryFill(QuestionCriteria reqMsg);
}
