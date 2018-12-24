package cn.neusoft.xuxiao.webapi.entity;

import cn.neusoft.xuxiao.dao.entity.Student;
import lombok.Data;

import java.util.List;

/**
 * 获取学生模块首页响应类
 */
@Data
public class GetStudentIndexResponse extends BasePage {
    private List<Student> studentList;
}
