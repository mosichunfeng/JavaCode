package cn.neusoft.xuxiao.service;

import cn.neusoft.xuxiao.dao.inf.IStudentDao;
import cn.neusoft.xuxiao.service.inf.IStudentService;
import org.junit.Test;

import javax.annotation.Resource;

public class StudentTest {

    @Resource(name="studentServiceImpl")
    private IStudentService studentService;


    @Test
    public void getList(){
        studentService.findAll();
    }
}
