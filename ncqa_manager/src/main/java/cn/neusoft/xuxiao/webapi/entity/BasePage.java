package cn.neusoft.xuxiao.webapi.entity;

import lombok.Data;

@Data
public class BasePage {
    /**
     * 分页时用到的变量
     * pageNo      : 当前页页码，当查询最后一页的数据时，前台组件相对应的该值设为-1
     * pageSize    : 每页显示的记录数
     * pages       : 总页数，只有当用户查询最后一页数据时由系统自己填入。查询其它页时，total的值应为0
     * rowSrt      : 起始行号
     * rowEnd      : 结束行号
     * counts      : 总记录数
     * sortFields  : 排序字段
     */
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private Integer pages = 0;
    private Integer rowSrt;
    private Integer rowEnd;
    private Integer counts;
    private String sortFields;
}
