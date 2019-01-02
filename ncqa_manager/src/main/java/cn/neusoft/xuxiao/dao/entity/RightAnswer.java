package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;


@Data
public class RightAnswer {

    private Integer id;

    private Integer question_id;

    private String right_answer_index;

    private String right_answer_content;


}
