package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.Register;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("IRegisterDao")
@Mapper
public interface IRegisterDao {
    public List<Register> findRegistersByStudentId(String student_id);

    List<Register> findAll();
}
