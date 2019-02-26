package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.Call;
import cn.neusoft.xuxiao.dao.entity.CallCriteria;
import cn.neusoft.xuxiao.dao.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("ICallDao")
public interface ICallDao {
    List<Call> pageQuery(CallCriteria callCriteria);

    int pageQuery_Count(CallCriteria callCriteria);

    void insertCall(Call call);

    Call findCallById(Integer id);

    List<Student> findNoCallStudentList(@Param("call") Call call, @Param("allClass") List<Integer> allClass);
}
