package cn.neusoft.xuxiao.dao.entity;

import cn.neusoft.xuxiao.webapi.entity.BasePage;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterCriteria extends BasePage {
    private Integer call_id;

    private String start_time;

    private String end_time;
}
