package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;


@Data
public class Register {
    private int id;

    private String student_id;

    private String student_name;

    private String student_class;

    private Integer student_class_id;

    private String start_time;

    private String image_url;

    private String address;
}
