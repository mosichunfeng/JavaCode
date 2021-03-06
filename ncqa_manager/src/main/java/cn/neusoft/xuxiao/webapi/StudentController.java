package cn.neusoft.xuxiao.webapi;


import cn.neusoft.xuxiao.constants.AuthorityConstants;
import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.constants.UserAuthorityConstants;
import cn.neusoft.xuxiao.dao.entity.*;
import cn.neusoft.xuxiao.service.inf.IStudentService;
import cn.neusoft.xuxiao.utils.StringUtil;
import cn.neusoft.xuxiao.webapi.base.BaseController;
import cn.neusoft.xuxiao.webapi.entity.GetClassIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.GetClassListResponse;
import cn.neusoft.xuxiao.webapi.entity.GetStudentIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Controller
@RequestMapping("/student")
public class StudentController extends BaseController {

    @Resource(name = "studentServiceImpl")
    private IStudentService studentService;

    /**
     * 条件分页排序分页查询
     *
     * @param
     * @return
     */
    @RequestMapping("/pageQuery")
    public String pageQuery(StudentCriteria reqMsg, Integer flag, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        User user = checkAndReturnUser(request, response);
        trimAll(reqMsg);
        PaginationResult<GetStudentIndexResponse> result = studentService.pageQuery(reqMsg);
        result.setAuthority(queryAuthorityForThis(user, AuthorityConstants.STUDENT_AUTH));
        if (reqMsg.getStudent_class_id() != null || (flag != null && flag == 1)) {
            map.put("flag", 1);
        }
        map.put("result", result);
        map.put("searchInfo", reqMsg);
        map.put("user", user);
        return "student_index";
    }


    /**
     * 获取班级列表
     */

    @RequestMapping("/getAvailableClass")
    @ResponseBody
    public String[] getAvailableClass() {
        Set<String> availableClass = studentService.getAvailableClass();
        String[] result = new String[availableClass.size()];
        return availableClass.toArray(result);
    }

    /**
     * 导入学生
     */
    @RequestMapping("/importStudent")
    @ResponseBody
    public String importStudent(@RequestParam("proxyfile") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        User user = checkAndReturnUser(request, response);
        checkAuthorityAndExit(user,AuthorityConstants.STUDENT_AUTH,UserAuthorityConstants.AUTH_ADD);
        studentService.importStudent(file);
        return generateResponse(ServiceResponseCode.OK);
    }

    /**
     * 导出模板
     */
    @RequestMapping("exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        studentService.exportTemplate(response);
    }

    /**
     * 删除学生
     */
    @RequestMapping("/deleteStudent")
    @ResponseBody
    public String deleteStudent(Student student, HttpServletRequest request, HttpServletResponse response) {
        User user = checkAndReturnUser(request, response);
        checkAuthorityAndExit(user,AuthorityConstants.STUDENT_AUTH,UserAuthorityConstants.AUTH_DELETE);
        studentService.deleteStudent(student);
        return generateResponse(ServiceResponseCode.OK);
    }

    /**
     * 添加学士
     */
    @RequestMapping("/addStudent")
    @ResponseBody
    public String addStudent(Student student, HttpServletResponse response, HttpServletRequest request) {
        User user = checkAndReturnUser(request, response);
        checkAuthorityAndExit(user,AuthorityConstants.STUDENT_AUTH,UserAuthorityConstants.AUTH_ADD);
        studentService.addStudent(student);
        return generateResponse(ServiceResponseCode.OK);
    }

    /**
     * 修改学生
     */
    @RequestMapping("/updateStudent")
    @ResponseBody
    public String updateStudents(Student student, HttpServletRequest request, HttpServletResponse response) {
        User user = checkAndReturnUser(request, response);
        checkAuthorityAndExit(user,AuthorityConstants.STUDENT_AUTH,UserAuthorityConstants.AUTH_MODIFY);
        studentService.updateStudent(student);
        return generateResponse(ServiceResponseCode.OK);
    }

    /**
     * 查询班级
     */
    @RequestMapping("/pageQueryClass")
    public String pageQueryClass(ClassInfoCriteria reqMsg, ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        User user = checkAndReturnUser(request, response);
        PaginationResult<GetClassIndexResponse> result = studentService.pageQueryClass(reqMsg);
        result.setAuthority(queryAuthorityForThis(user, AuthorityConstants.REGISTER_AUTH));
        map.put("result", result);
        map.put("searchInfo", reqMsg);
        map.put("user", user);
        return "class";
    }

    /**
     * 修改班级
     */
    @RequestMapping("/modifyClass")
    @ResponseBody
    public String modifyClass(ClassInfo classInfo, HttpServletResponse response, HttpServletRequest request) {
        User user = checkAndReturnUser(request, response);
        checkAuthorityAndExit(user,AuthorityConstants.STUDENT_AUTH,UserAuthorityConstants.AUTH_MODIFY);
        studentService.modifyClass(classInfo);
        return generateResponse(ServiceResponseCode.OK);
    }

    /**
     * 新增班级
     */
    @RequestMapping("/insertClass")
    @ResponseBody
    public String insertClass(HttpServletRequest request, HttpServletResponse response, ClassInfo classInfo) {
        User user = checkAndReturnUser(request, response);
        checkAuthorityAndExit(user,AuthorityConstants.STUDENT_AUTH,UserAuthorityConstants.AUTH_ADD);
        studentService.insertClass(classInfo);
        return generateResponse(ServiceResponseCode.OK);
    }

    /**
     * 删除班级
     */
    @RequestMapping("/deleteClass")
    @ResponseBody
    public String deleteClass(ClassInfo classInfo, HttpServletResponse response, HttpServletRequest request) {
        User user = checkAndReturnUser(request, response);
        checkAuthorityAndExit(user,AuthorityConstants.STUDENT_AUTH,UserAuthorityConstants.AUTH_DELETE);
        studentService.deleteClass(classInfo);
        return generateResponse(ServiceResponseCode.OK);
    }

    /**
     * 班级列表
     *
     * @param
     */
    @RequestMapping("/classList")
    @ResponseBody
    public String classList(HttpServletRequest request, HttpServletResponse response) {
        User user = checkAndReturnUser(request, response);
        GetClassListResponse result = studentService.classList();
        return createResponse(result, ServiceResponseCode.OK);
    }

    /**
     * 微信解绑
     */
    @RequestMapping("/cancelBind")
    @ResponseBody
    public String cancelBind(HttpServletRequest request, HttpServletResponse response, String id) {
        User user = checkAndReturnUser(request, response);
        studentService.cancelBind(id);
        return generateResponse(ServiceResponseCode.OK);
    }

    public static void trimAll(StudentCriteria studentCriteria) {
        if (studentCriteria != null) {
            if (studentCriteria.getStudent_id() != null) {
                studentCriteria.setStudent_id(studentCriteria.getStudent_id().trim());
            }
            if (studentCriteria.getStudent_name() != null) {
                studentCriteria.setStudent_name(studentCriteria.getStudent_name().trim());
            }
            if (studentCriteria.getStudent_class() != null) {
                studentCriteria.setStudent_class(studentCriteria.getStudent_class().trim());
            }
            if (studentCriteria.getStudent_tel() != null) {
                studentCriteria.setStudent_tel(studentCriteria.getStudent_tel().trim());
            }

            if (StringUtil.isEmpty(studentCriteria.getStudent_id())) {
                studentCriteria.setStudent_id(null);
            }
            if (StringUtil.isEmpty(studentCriteria.getStudent_name())) {
                studentCriteria.setStudent_name(null);
            }
            if (StringUtil.isEmpty(studentCriteria.getStudent_class())) {
                studentCriteria.setStudent_class(null);
            }
            if (StringUtil.isEmpty(studentCriteria.getStudent_gender())) {
                studentCriteria.setStudent_gender(null);
            }
            if (StringUtil.isEmpty(studentCriteria.getStudent_tel())) {
                studentCriteria.setStudent_tel(null);
            }
        }

    }
}

