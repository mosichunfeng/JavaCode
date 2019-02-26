package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

import java.util.List;

@Data
public class Call {

    private Integer id;

    private String classes;

    private String classes_name;

    private String start_time;

    private String end_time;

    private Integer cost_time;

    private int status;
}
