package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;


@Data
public class Question {
    private int id;

    private int question_index;

    private int select_type;

    private String content;

}
