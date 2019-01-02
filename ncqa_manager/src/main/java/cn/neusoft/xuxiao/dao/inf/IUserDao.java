package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("IUserDao")
@Mapper
public interface IUserDao {

    public User findUserByUsername(String username);

    public Integer insertUser(User user);

    public List<User> findAll();

    public User findUserById(int id);
}
