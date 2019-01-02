package cn.neusoft.xuxiao.dao.entity;

import lombok.Data;

@Data
public class Authority {

    private Integer id;

    private Integer user_id;

    private String username;

    private Integer group_id;

    private Integer auth_add;

    private Integer auth_modify;

    private Integer auth_delete;

    private Integer auth_download;

    private Integer auth_upload;
}
