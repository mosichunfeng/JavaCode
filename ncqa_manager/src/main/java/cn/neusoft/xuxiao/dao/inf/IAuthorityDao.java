package cn.neusoft.xuxiao.dao.inf;


import cn.neusoft.xuxiao.dao.entity.Authority;
import cn.neusoft.xuxiao.dao.entity.AuthorityDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("IAuthorityDao")
public interface IAuthorityDao {
    void insertAuthority(Authority authority);

    Authority findAuthorityByGroupIdAndUserId(AuthorityDO authorityDO);

    void updateAuthority(Authority authority);

    Authority findAuthByUserAndGroup(@Param("user_id") int user_id, @Param("group_id") int group_id);
}
