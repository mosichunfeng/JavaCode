package cn.neusoft.xuxiao.webapi.entity;

import cn.neusoft.xuxiao.dao.entity.Question;
import lombok.Data;

import java.util.List;

@Data
public class GetQuestionIndexResponse extends BasePage {
    private List<Question> questionList;
}
