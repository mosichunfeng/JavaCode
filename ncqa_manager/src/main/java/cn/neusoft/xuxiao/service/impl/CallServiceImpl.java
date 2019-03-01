package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.*;
import cn.neusoft.xuxiao.dao.inf.ICallDao;
import cn.neusoft.xuxiao.dao.inf.IRegisterDao;
import cn.neusoft.xuxiao.dao.inf.IStudentDao;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.ICallService;
import cn.neusoft.xuxiao.utils.PageTemplateUtil;
import cn.neusoft.xuxiao.utils.TimeTool;
import cn.neusoft.xuxiao.utils.ValidationUtils;
import cn.neusoft.xuxiao.webapi.entity.BasePage;
import cn.neusoft.xuxiao.webapi.entity.GetCallIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service("callServiceImpl")
public class CallServiceImpl implements ICallService {

    @Resource
    private ICallDao callDao;

    @Resource
    private IRegisterDao registerDao;

    @Resource
    private IStudentDao studentDao;

    @Override
    public PaginationResult<GetCallIndexResponse> pageQuery(CallCriteria reqMsg) {

        if (reqMsg.getRowSrt() == null) {
            reqMsg.setRowSrt(new Integer(0));
        }
        reqMsg.setPageSize(10);

        PaginationResult<GetCallIndexResponse> paginationResult = new PaginationResult<>();

        Integer pageCnt = callDao.pageQuery_Count(reqMsg);
        if (pageCnt == null) {
            paginationResult.setBasePage(new BasePage());
            return paginationResult;
        }
        BasePage pageInfo = PageTemplateUtil.setBasePage(reqMsg, pageCnt);

        GetCallIndexResponse result = new GetCallIndexResponse();

        List<Call> callList = callDao.pageQuery(reqMsg);
        for (Call call : callList) {
            String classes_name = "";
            if (call.getClasses().contains(",")) {
                String[] split = call.getClasses().split(",");
                for (String s : split) {
                    ClassInfo classById = studentDao.findClassById(new Integer(s));
                    classes_name += classById.getName() + "，";
                }
            } else {
                ClassInfo classById = studentDao.findClassById(new Integer(call.getClasses()));
                classes_name += classById.getName();
            }
            if (classes_name.endsWith("，")) {
                classes_name = classes_name.substring(0, classes_name.length() - 1);
            }
            call.setClasses(classes_name);
            if (TimeTool.StrToDate(call.getEnd_time()).getTime() < new Date().getTime()) {
                call.setStatus(1);
            }
        }

        result.setCallList(callList);
        List<ClassInfo> classList = studentDao.findAllClass();
        result.setClassList(classList);

        paginationResult.setBasePage(pageInfo);
        paginationResult.setResult(result);
        return paginationResult;
    }

    @Override
    public void insertCall(Call call) {
        ValidationUtils.checkNotEmpty(call.getClasses(), "班级不能为空!");

        call.setStart_time(TimeTool.DateToString(new Date()));
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, call.getCost_time());
        call.setEnd_time(TimeTool.DateToString(c.getTime()));
        callDao.insertCall(call);
    }

    @Override
    public void exportCallExcel(HttpServletResponse response, Integer call_id) {
        List<Register> registerList = findAllRegister(call_id);
        for (Register register : registerList) {
            Student student = studentDao.findStudentByStudentId(register.getStudent_id());
            register.setStudent_class(student.getStudent_class());
        }
        Call call = callDao.findCallById(call_id);


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("签到表");
        String[] headers = {"班级", "学号", "姓名", "签到时间", "签到地点"};
        String fileName = call.getStart_time() + "起" + call.getCost_time() + "分钟签到表" + System.currentTimeMillis() + ".xls";
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

        cellFormat(registerList, sheet, rowNum);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "服务器异常，请联系管理员！");
        }
    }

    private void cellFormat(List<Register> info, HSSFSheet sheet, int rowNum) {
        for (int i = 0; i < info.size(); i++) {
            Register sr = info.get(i);
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(sr.getStudent_class());
            row1.createCell(1).setCellValue(sr.getStudent_id());
            row1.createCell(2).setCellValue(sr.getStudent_name());
            row1.createCell(3).setCellValue(sr.getStart_time());
            row1.createCell(4).setCellValue(sr.getAddress());
            rowNum++;
        }
    }

    @Override
    public void exportNoCallExcel(HttpServletResponse response, Integer call_id) {
        ValidationUtils.checkNotEmpty(call_id, "call_id不能为空!");
        Call call = callDao.findCallById(call_id);
        List<Integer> allClass = new ArrayList<>();
        String classes = call.getClasses();
        if(classes.contains(",")){
            String[] split = classes.split(",");
            for (String s : split) {
                allClass.add(new Integer(s));
            }
        }else{
            allClass.add(new Integer(classes));
        }
        List<Student> noCallStudentList = callDao.findNoCallStudentList(call,allClass);


        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("未签到名单");
        String[] headers = {"班级", "学号", "姓名"};
        String fileName = call.getStart_time() + "起" + call.getCost_time() + "分钟逃课表" + System.currentTimeMillis() + ".xls";
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

        for (int i = 0; i < noCallStudentList.size(); i++) {
            Student st = noCallStudentList.get(i);
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(st.getStudent_class());
            row1.createCell(1).setCellValue(st.getStudent_id());
            row1.createCell(2).setCellValue(st.getStudent_name());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "服务器异常，请联系管理员！");
        }
    }

    private List<Register> findAllRegister(Integer call_id) {
        ValidationUtils.checkNotEmpty(call_id, "call_id不能为空!");
        Call call = callDao.findCallById(call_id);

        RegisterCriteria registerCriteria = new RegisterCriteria();
        registerCriteria.setPageNo(null);
        registerCriteria.setPageSize(null);
        registerCriteria.setStart_time(call.getStart_time());
        registerCriteria.setEnd_time(call.getEnd_time());

        List<Register> registerList = registerDao.pageQuery(registerCriteria);
        return registerList;
    }


}
