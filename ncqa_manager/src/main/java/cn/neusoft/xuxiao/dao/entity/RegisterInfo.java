package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class RegisterInfo {
    private Integer id;

    private String register_week;

    private int register_in_count;

    private int register_out_count;

    private int register_in_last;

    private int register_out_last;

    private String register_in_date;
}
