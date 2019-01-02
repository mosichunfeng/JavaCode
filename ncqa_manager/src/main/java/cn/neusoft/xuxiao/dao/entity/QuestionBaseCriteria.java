package cn.neusoft.xuxiao.dao.entity;

import cn.neusoft.xuxiao.webapi.entity.BasePage;
import lombok.Data;

@Data
public class QuestionBaseCriteria extends BasePage {
    private int id;

    private String name;

    private String description;

    private String create_time;

    private String start_time;

    private String end_time;

    private Integer kind_id;

    private String kind_name;

    private Integer available;
}
