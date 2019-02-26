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


    private int member;

    /**
     * 是否实习
     */
    private Integer work_detail;
}
