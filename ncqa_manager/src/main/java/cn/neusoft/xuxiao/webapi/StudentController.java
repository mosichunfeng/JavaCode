package cn.neusoft.xuxiao.webapi;


import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;
import cn.neusoft.xuxiao.service.inf.IStudentService;
import cn.neusoft.xuxiao.utils.StringUtil;
import cn.neusoft.xuxiao.webapi.base.BaseController;
import cn.neusoft.xuxiao.webapi.entity.GetStudentIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Set;

@Controller
@RequestMapping("/student")
public class StudentController extends BaseController {

    @Resource(name="studentServiceImpl")
    private IStudentService studentService;

    /**
     * 条件分页排序分页查询
     * @param
     * @return
     */
    @RequestMapping("/pageQuery")
    public String pageQuery(StudentCriteria reqMsg, ModelMap map){
        trimAll(reqMsg);
        PaginationResult<GetStudentIndexResponse> result = studentService.pageQuery(reqMsg);
        map.put("result", result);
        System.out.println(result);
        map.put("searchInfo", reqMsg);
        return "student_index";
    }


    /**
     * 获取班级列表
     */

    @RequestMapping("/getAvailableClass")
    @ResponseBody
    public String [] getAvailableClass(){
        Set<String> availableClass = studentService.getAvailableClass();
        String [] result = new String[availableClass.size()];
        return availableClass.toArray(result);
    }


    /**
     * 删除学生
     */
    @RequestMapping("/deleteStudent")
    @ResponseBody
    public String deleteStudent(Student student){
        studentService.deleteStudent(student);
        return generateResponse(ServiceResponseCode.OK);
    }

    /**
     * 修改学生
     */
    @RequestMapping("/updateStudent")
    @ResponseBody
    public String updateStudents(Student student){
        studentService.updateStudent(student);
        return generateResponse(ServiceResponseCode.OK);
    }

    public static void trimAll(StudentCriteria studentCriteria){
        if(studentCriteria!=null) {
            if(studentCriteria.getStudent_id()!=null) {
                studentCriteria.setStudent_id(studentCriteria.getStudent_id().trim());
            }
            if(studentCriteria.getStudent_name()!=null) {
                studentCriteria.setStudent_name(studentCriteria.getStudent_name().trim());
            }
            if(studentCriteria.getStudent_class()!=null) {
                studentCriteria.setStudent_class(studentCriteria.getStudent_class().trim());
            }
            if(studentCriteria.getStudent_tel()!=null) {
                studentCriteria.setStudent_tel(studentCriteria.getStudent_tel().trim());
            }

            if(StringUtil.isEmpty(studentCriteria.getStudent_id())){
                studentCriteria.setStudent_id(null);
            }
            if(StringUtil.isEmpty(studentCriteria.getStudent_name())){
                studentCriteria.setStudent_name(null);
            }
            if(StringUtil.isEmpty(studentCriteria.getStudent_class())){
                studentCriteria.setStudent_class(null);
            }
            if(StringUtil.isEmpty(studentCriteria.getStudent_gender())){
                studentCriteria.setStudent_gender(null);
            }
            if(StringUtil.isEmpty(studentCriteria.getStudent_tel())){
                studentCriteria.setStudent_tel(null);
            }
        }
    }
}

