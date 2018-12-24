package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;
import cn.neusoft.xuxiao.dao.inf.IRegisterDao;
import cn.neusoft.xuxiao.dao.inf.IStudentDao;
import cn.neusoft.xuxiao.service.inf.IStudentService;
import cn.neusoft.xuxiao.utils.PageTemplateUtil;
import cn.neusoft.xuxiao.webapi.entity.BasePage;
import cn.neusoft.xuxiao.webapi.entity.GetStudentIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service("studentServiceImpl")
public class StudentServiceImpl implements IStudentService {

    private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Resource(name="IStudentDao")
    private IStudentDao studentDao;

    @Resource(name="IRegisterDao")
    private IRegisterDao registerDao;


    @Override
    public PaginationResult<GetStudentIndexResponse> pageQuery(StudentCriteria reqMsg) {
        if(reqMsg.getRowSrt()==null){
            reqMsg.setRowSrt(new Integer(0));
        }
        reqMsg.setPageSize(10);

        PaginationResult<GetStudentIndexResponse> paginationResult = new PaginationResult<>();

        Integer pageCnt = studentDao.pageQuery_Count(reqMsg);
        if (pageCnt==null) {
            paginationResult.setBasePage(new BasePage());
            return paginationResult;
        }
        BasePage pageInfo = PageTemplateUtil.setBasePage(reqMsg, pageCnt);

        GetStudentIndexResponse result = new GetStudentIndexResponse();
        List<Student> studentList = studentDao.pageQuery(reqMsg);
        result.setStudentList(studentList);

        paginationResult.setBasePage(pageInfo);
        paginationResult.setResult(result);

        return paginationResult;
    }

    @Override
    public Set<String> getAvailableClass() {
        Set<String> set = new LinkedHashSet<>();
        List<Student> studentList = studentDao.findAll();
        if(!CollectionUtils.isEmpty(studentList)) {
            for (Student student : studentList) {
                if(student.getStudent_class()!=null){
                    set.add(student.getStudent_class());
                }
            }
        }
        return set;
    }

    @Override
    public void deleteStudent(Student student) {
        studentDao.deleteStudent(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentDao.updateStudent(student);
    }
}
