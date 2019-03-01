package cn.neusoft.xuxiao.dao.inf;


import cn.neusoft.xuxiao.dao.entity.ClassInfo;
import cn.neusoft.xuxiao.dao.entity.ClassInfoCriteria;
import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
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

    /**
     * 查询在校人数
     */
    Integer findInCount();

    /**
     * 查询实习人数
     */
    Integer findOutCount();

    /**
     * 是否实习
     */
    Integer getWorkDetail(String student_id);

    void insertStudentSet(LinkedHashSet<String> set);


    /**
     * 查询班级数
     */
    Integer pageQueryClass_Count(ClassInfoCriteria reqMsg);

    List<ClassInfo> pageQueryClass(ClassInfoCriteria reqMsg);

    void insertClass(ClassInfo classInfo);

    void updateClass(ClassInfo classInfo);

    void deleteClass(ClassInfo classInfo);

    List<ClassInfo> findAllClass();

    void insertStudentBatch(List<Student> studentList);

    ClassInfo findClassById(Integer id);

    Student findStudentByStudentId(String id);

    List<Student> findStudentListByClassId(Integer class_id);

    List<String> findStudentIdListByClassId(Integer class_id);

    int findStudentCountByClassId(Integer class_id);
}
