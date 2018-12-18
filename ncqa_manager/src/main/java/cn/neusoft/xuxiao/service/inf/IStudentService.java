package cn.neusoft.xuxiao.service.inf;

import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;
import cn.neusoft.xuxiao.dao.entity.StudentRegisterDO;
import cn.neusoft.xuxiao.dao.inf.IStudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IStudentService {

    public List<Student> findAll();

    List<StudentRegisterDO> pageQuery(StudentCriteria studentCriteria);
}
