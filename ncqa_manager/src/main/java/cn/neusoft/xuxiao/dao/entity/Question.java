package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

import java.util.List;


@Data
public class Question {
    private Integer id;

    private Integer question_index;

    private Integer question_base_id;

    private Integer select_type;

    private String content;

    private List<Answer> answerList;

    private RightAnswer rightAnswer;

    private Integer question_type;

    private Integer grade;

}
