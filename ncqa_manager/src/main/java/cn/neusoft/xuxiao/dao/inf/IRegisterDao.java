package cn.neusoft.xuxiao.dao.inf;

import cn.neusoft.xuxiao.dao.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("IRegisterDao")
public interface IRegisterDao extends JpaRepository<Register,Integer> {
    public List<Register> findRegistersByStudentId(String student_id);
}
