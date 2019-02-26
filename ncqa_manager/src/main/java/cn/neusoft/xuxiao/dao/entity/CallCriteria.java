package cn.neusoft.xuxiao.dao.entity;

import cn.neusoft.xuxiao.webapi.entity.BasePage;
import lombok.Data;

import java.util.List;

@Data
public class CallCriteria extends BasePage {
    private Integer id;

    private String classes;

    private String start_time;

    private String end_time;

    private Integer status;
}
