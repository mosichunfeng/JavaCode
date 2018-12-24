package cn.neusoft.xuxiao.dao.inf;


import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("IStudentDao")
@Mapper
public interface IStudentDao{
    void deleteStudent(Student student);

    Integer pageQuery_Count(StudentCriteria reqMsg);

    List<Student> pageQuery(StudentCriteria reqMsg);

    void updateStudent(Student student);

    void insertStudent(Student student);

    List<Student> findAll();

}
