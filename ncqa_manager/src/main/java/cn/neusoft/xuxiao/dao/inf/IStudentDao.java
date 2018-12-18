package cn.neusoft.xuxiao.dao.inf;


import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("IStudentDao")
public interface IStudentDao extends JpaRepository<Student,Integer>, JpaSpecificationExecutor<Student> {

}
