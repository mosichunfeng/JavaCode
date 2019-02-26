package cn.neusoft.xuxiao.dao.entity;

import cn.neusoft.xuxiao.webapi.entity.BasePage;
import lombok.Data;

@Data
public class StudentCriteria extends BasePage {

    private Integer id;

    private String student_id;

    private String student_name;

    private String student_class;

    private Integer student_class_id;

    private String student_gender;

    private String student_tel;

    private Integer work_detail;
}
