package cn.neusoft.xuxiao.webapi.entity;

import cn.neusoft.xuxiao.dao.entity.Authority;
import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.webapi.base.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class GetIndexByGroupResponse {
    /**
     * 问题权限列表
     */
    private List<Authority> authorityList;


    /**
     * 学生权限列表
     */
    private List<Authority> authorityList2;

    /**
     * 签到权限列表
     */
    private List<Authority> authorityList3;

    private Authority authority;
}
