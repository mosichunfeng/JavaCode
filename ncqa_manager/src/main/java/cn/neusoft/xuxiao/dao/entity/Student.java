package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;


@Data
public class Student {

    private Integer id;

    private String student_id;

    private String student_name;

    private String student_class;

    private Integer student_class_id;

    private Integer student_grade_id;

    private String student_gender;

    private String student_tel;

    private Integer work_detail;

}
