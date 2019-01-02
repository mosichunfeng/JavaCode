package cn.neusoft.xuxiao.dao.entity;


import lombok.Data;

import java.util.Set;

@Data
public class QuestionBase {

    private Integer id ;

    private String name;

    private String description;

    private String create_time;

    private String start_time;

    private String end_time;

    private Integer kind_id;

    private String kind_name;

    private Integer available;
}
