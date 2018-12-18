package cn.neusoft.xuxiao.webapi;


import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;
import cn.neusoft.xuxiao.dao.entity.StudentRegisterDO;
import cn.neusoft.xuxiao.service.inf.IStudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource(name="studentServiceImpl")
    private IStudentService studentService;

    @RequestMapping("/pageQuery")
    public List<StudentRegisterDO> pageQuery(StudentCriteria studentCriteria){
        List<StudentRegisterDO> studentRegisterDOList = studentService.pageQuery(studentCriteria);
        return studentRegisterDOList;
    }
}
