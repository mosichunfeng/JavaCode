package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.Authority;
import cn.neusoft.xuxiao.webapi.entity.GetIndexByGroupResponse;
import cn.neusoft.xuxiao.webapi.entity.GetIndexByGroupRequest;

public interface IAuthorityService {
    GetIndexByGroupResponse getIndexByGroup(GetIndexByGroupRequest reqMsg);

    void addUser(String username, String password);

    void modify(Authority reqMsg);
}
