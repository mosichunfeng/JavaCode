package cn.neusoft.xuxiao.webapi.entity;

import cn.neusoft.xuxiao.dao.entity.QuestionBase;
import lombok.Data;

import java.util.List;

@Data
public class GetQuestionBaseIndexResponse extends BasePage {
    private List<QuestionBase> questionBaseList;
}
