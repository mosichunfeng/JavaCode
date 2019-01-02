package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;


@Data
public class Answer {

    private Integer id;

    private Integer question_id;

    private String answer_index;

    private String answer_content;

}
