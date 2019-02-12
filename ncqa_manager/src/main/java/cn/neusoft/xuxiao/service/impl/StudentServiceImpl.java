package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.ClassInfo;
import cn.neusoft.xuxiao.dao.entity.Student;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;
import cn.neusoft.xuxiao.dao.inf.IRegisterDao;
import cn.neusoft.xuxiao.dao.inf.IStudentDao;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.IStudentService;
import cn.neusoft.xuxiao.utils.ExcelUtil;
import cn.neusoft.xuxiao.utils.PageTemplateUtil;
import cn.neusoft.xuxiao.webapi.entity.BasePage;
import cn.neusoft.xuxiao.webapi.entity.GetStudentIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;


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

    @Override
    public void exportTemplate(HttpServletResponse response) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("学生模板");
        String[] headers = {"编号", "学号", "姓名","班级", "性别"};
        String fileName = "学生上传模板" + System.currentTimeMillis() + ".xls";
        try {
            fileName = new String(fileName.getBytes("GB2312"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int rowNum = 1;
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        HSSFRow row1 = sheet.createRow(rowNum);
        row1.createCell(0).setCellValue(1);
        row1.createCell(1).setCellValue("15311020233");
        row1.createCell(2).setCellValue("何佩");
        row1.createCell(3).setCellValue("物联网15202");
        row1.createCell(4).setCellValue("男");

        HSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue(2);
        row2.createCell(1).setCellValue("15311020222");
        row2.createCell(2).setCellValue("郑金浩");
        row2.createCell(3).setCellValue("物联网15202");
        row2.createCell(4).setCellValue("男");

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "服务器异常，请联系管理员！");
        }
    }

    @Override
    public void importStudent(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String[] columns = { "编号", "学号", "姓名", "班级", "性别" ,"联系电话"};
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, String>> list = ExcelUtil.parseExcel(fileName, is, columns);
        List<Student> studentList = new ArrayList();
        for (Map<String, String> map : list) {
            Student student = new Student();
            student.setStudent_id((String) map.get("学号"));
            student.setStudent_name((String) map.get("姓名"));
            student.setStudent_class((String) map.get("班级"));
            student.setStudent_gender((String) map.get("性别"));
            student.setStudent_tel((String)map.get("联系电话"));
            studentList.add(student);
        }

        /**
         * 更新班级id
         */
        LinkedHashSet<String> set = new LinkedHashSet<>();
        List<ClassInfo> class_list = new ArrayList<>();
        Map<Integer,String> map = new HashMap<>();
        for (Student student : studentList) {
            set.add(student.getStudent_class());
        }

        for(String class_name : set){
            ClassInfo ci = new ClassInfo();
            ci.setName(class_name);
        }


    }
}
