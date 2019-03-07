package cn.neusoft.xuxiao.dao.entity;

import cn.neusoft.xuxiao.webapi.entity.BasePage;
import lombok.Data;

import java.util.List;

@Data
public class QuestionCriteria extends BasePage {
    private Integer id;

    private int question_index;

    private int select_type;

    private String content;

    private Integer question_base_id;

    private Integer question_type;
}
