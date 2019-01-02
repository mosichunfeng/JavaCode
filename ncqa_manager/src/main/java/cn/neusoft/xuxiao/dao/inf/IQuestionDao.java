package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("IQuestionDao")
@Mapper
public interface IQuestionDao{
    Integer pageQuery_Count(QuestionBaseCriteria questionBaseCriteria);

    void deleteQuestionBase(QuestionBase questionBase);


    List<QuestionBase> pageQueryQuestionBase(QuestionBaseCriteria questionBaseCriteria);

    void updateQuestionBase(QuestionBase student);

    void insertQuestionBase(QuestionBase student);

    List<KindDO> findAllKind();

    /**
     * 答题模块
     */
    Integer pageQuery_Count2(QuestionCriteria questionCriteria);

    void deleteQuestion(Question question);

    List<Question> pageQuery(QuestionCriteria questionCriteria);

    void updateQuestion(Question question);

    void insertQuestion(Question question);

    List<GradeDO> findGradeListByBaseId(int base_id);

    QuestionBase getQuestionBaseById(int id);

    /**
     * 批量插入
     * @param paramList
     * @return
     */
    int insertQuestion(List<Question> paramList);

    /**
     * 批量插入
     * @param paramList
     */
    void insertAnswer(List<Answer> paramList);

    /**
     * 批量插入
     */
    void insertRightAnswer(List<RightAnswer> paramList);

}
