package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class Grade {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 代号
     */
    private Integer num;

    /**
     * 描述
     */
    private String desc;
}
