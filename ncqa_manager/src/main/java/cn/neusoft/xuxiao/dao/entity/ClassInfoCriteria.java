package cn.neusoft.xuxiao.dao.entity;

import cn.neusoft.xuxiao.webapi.entity.BasePage;
import lombok.Data;

@Data
public class ClassInfoCriteria extends BasePage {

    private Integer id;

    private String name;

    private String work_detail;
}
