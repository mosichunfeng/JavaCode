package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class ActivityCodeDO {
    private int id;

    private int user_id;

    private String student_class;

    private String student_name;

    private String code;

    private String question_base_id;

    private String name;

    private String time;

    private String student_gender;

    private String student_id;
}
