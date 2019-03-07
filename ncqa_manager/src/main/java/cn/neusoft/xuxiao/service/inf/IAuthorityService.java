package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.Authority;
import cn.neusoft.xuxiao.dao.entity.User;
import cn.neusoft.xuxiao.webapi.entity.GetIndexByGroupResponse;
import cn.neusoft.xuxiao.webapi.entity.GetIndexByGroupRequest;
import org.apache.ibatis.annotations.Param;

public interface IAuthorityService {
    GetIndexByGroupResponse getIndexByGroup(GetIndexByGroupRequest reqMsg);

    void addUser(String username, String password);

    void modify(Authority reqMsg);

    Authority findAuthByUserAndGroup(int user_id,int group_id);
}
