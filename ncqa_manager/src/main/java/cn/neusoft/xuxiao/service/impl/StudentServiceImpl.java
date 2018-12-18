package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.dao.entity.Register;
import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;
import cn.neusoft.xuxiao.dao.entity.StudentRegisterDO;
import cn.neusoft.xuxiao.dao.inf.IRegisterDao;
import cn.neusoft.xuxiao.dao.inf.IStudentDao;
import cn.neusoft.xuxiao.service.inf.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("studentServiceImpl")
public class StudentServiceImpl implements IStudentService {

    private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Resource(name="IStudentDao")
    private IStudentDao studentDao;

    @Resource(name="IRegisterDao")
    private IRegisterDao registerDao;

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public List<StudentRegisterDO> pageQuery(StudentCriteria studentCriteria) {
        /*
        Specification<Student> specification = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate equal = criteriaBuilder.equal(root.get("student_name"), "李瑾");
                return equal;
            }
        };
        List<Student> all = studentDao.findAll(specification);
        for (Student student : all) {
            System.out.println(student);
        }
        return all;
    */
        List<Student> studentList = studentDao.findAll();
        List<StudentRegisterDO> studentRegisterDOList = new ArrayList<>();
        /*
        iter增强for循环
         */
        int i  = 0;
        long start = System.currentTimeMillis();
        if(!CollectionUtils.isEmpty(studentList)) {
            for (Student student : studentList) {
                logger.info("循环{}次",i);
                i++;
                StudentRegisterDO studentRegisterDO = new StudentRegisterDO();
                studentRegisterDO.setStudent(student);
                List<Register> registerList = registerDao.findRegistersByStudentId(student.getStudent_id());
                studentRegisterDO.setRegisterList(registerList);
                studentRegisterDOList.add(studentRegisterDO);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("花费"+(end-start));
        return studentRegisterDOList;
    }
}
