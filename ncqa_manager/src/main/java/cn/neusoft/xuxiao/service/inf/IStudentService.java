package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.ClassInfo;
import cn.neusoft.xuxiao.dao.entity.ClassInfoCriteria;
import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;

import cn.neusoft.xuxiao.webapi.entity.GetClassIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.GetClassListResponse;
import cn.neusoft.xuxiao.webapi.entity.GetStudentIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 学生模块Service接口定义
 */
public interface IStudentService {
    PaginationResult<GetStudentIndexResponse> pageQuery(StudentCriteria reqMsg);

    Set<String> getAvailableClass();

    void deleteStudent(Student student);

    void updateStudent(Student student);


    void exportTemplate(HttpServletResponse response);

    void importStudent(MultipartFile file);

    PaginationResult<GetClassIndexResponse> pageQueryClass(ClassInfoCriteria reqMsg);

    void modifyClass(ClassInfo classInfo);

    void insertClass(ClassInfo classInfo);

    void deleteClass(ClassInfo classInfo);

    GetClassListResponse classList();

    void addStudent(Student student);

    void cancelBind(String id);
}
