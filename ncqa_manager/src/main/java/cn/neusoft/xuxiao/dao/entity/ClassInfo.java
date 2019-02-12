package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class ClassInfo {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 年级id
     */
    private Integer grade_id;

    /**
     * 辅导员id
     */
    private Integer teacher_id;

    /**
     * 是否实习
     */
    private Integer work_detail;
}
