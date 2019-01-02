package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.*;
import cn.neusoft.xuxiao.dao.inf.IQuestionDao;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.IQuestionService;
import cn.neusoft.xuxiao.utils.ExcelUtil;
import cn.neusoft.xuxiao.utils.PageTemplateUtil;
import cn.neusoft.xuxiao.utils.TimeTool;
import cn.neusoft.xuxiao.utils.ValidationUtils;
import cn.neusoft.xuxiao.webapi.entity.BasePage;
import cn.neusoft.xuxiao.webapi.entity.GetQuestionBaseIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.GetQuestionIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("questionServiceImpl")
public class QuestionServiceImpl implements IQuestionService {

    private static Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Resource(name="IQuestionDao")
    private IQuestionDao questionDao;

    @Override
    public PaginationResult<GetQuestionBaseIndexResponse> pageQueryBase(QuestionBaseCriteria reqMsg) {
        if(reqMsg.getRowSrt()==null){
            reqMsg.setRowSrt(new Integer(0));
        }
        reqMsg.setPageSize(6);

        PaginationResult<GetQuestionBaseIndexResponse> paginationResult = new PaginationResult<>();

        Integer pageCnt = questionDao.pageQuery_Count(reqMsg);
        if (pageCnt==null) {
            paginationResult.setBasePage(new BasePage());
            return paginationResult;
        }
        BasePage pageInfo = PageTemplateUtil.setBasePage(reqMsg, pageCnt);

        GetQuestionBaseIndexResponse result = new GetQuestionBaseIndexResponse();
        List<QuestionBase> questionBaseList = questionDao.pageQueryQuestionBase(reqMsg);
        result.setQuestionBaseList(questionBaseList);

        paginationResult.setBasePage(pageInfo);
        paginationResult.setResult(result);

        return paginationResult;
    }

    @Override
    public void insertQuestionBase(QuestionBase questionBase) {
        ValidationUtils.checkNotEmpty(questionBase.getStart_time(), "开始时间不能为空!");
        ValidationUtils.checkNotEmpty(questionBase.getEnd_time(), "结束时间不能为空!");
        ValidationUtils.checkNotEmpty(questionBase.getName(), "名称不能为空！");
        questionBase.setCreate_time(TimeTool.DateToString(new Date()));
        questionBase.setAvailable(1);
        questionDao.insertQuestionBase(questionBase);
    }

    @Override
    public void updateQuestionBase(QuestionBase questionBase) {
        ValidationUtils.checkNotEmpty(questionBase.getId(), "id不能为空！");
        ValidationUtils.checkNotEmpty(questionBase.getStart_time(), "开始时间不能为空!");
        ValidationUtils.checkNotEmpty(questionBase.getEnd_time(), "结束时间不能为空!");
        ValidationUtils.checkNotEmpty(questionBase.getName(), "名称不能为空！");
        questionDao.updateQuestionBase(questionBase);
    }

    @Override
    public void deleteQuestionBase(QuestionBase questionBase) {
        ValidationUtils.checkNotEmpty(questionBase.getId(), "id不能为空！");
        questionDao.deleteQuestionBase(questionBase);
    }

    @Override
    public List<KindDO> findAllKind() {
        return questionDao.findAllKind();
    }

    @Override
    public void exportTemplate(HttpServletResponse response) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("题目模板");
        String[] headers = {"编号", "题目", "选项类型","选项A", "选项B","选项C","选项D","正确答案"};
        String fileName = "题目上传模板" + System.currentTimeMillis() + ".xls";
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
        row1.createCell(1).setCellValue("一道残阳铺水中，半江瑟瑟半江红。诗句中的瑟瑟是什么意思?");
        row1.createCell(2).setCellValue("单选");
        row1.createCell(3).setCellValue("朱红");
        row1.createCell(4).setCellValue("碧绿");
        row1.createCell(5).setCellValue("天蓝");
        row1.createCell(6).setCellValue("棕黄");
        row1.createCell(7).setCellValue("B");

        HSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue(1);
        row2.createCell(1).setCellValue("以下哪些选手属于IG电子竞技俱乐部LOL分部成员?");
        row2.createCell(2).setCellValue("多选");
        row2.createCell(3).setCellValue("Ning");
        row2.createCell(4).setCellValue("Ming");
        row2.createCell(5).setCellValue("Clearlove");
        row2.createCell(6).setCellValue("JackeyLove");
        row2.createCell(7).setCellValue("A,D");
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
    public PaginationResult<GetQuestionIndexResponse> pageQuery(QuestionCriteria reqMsg) {
        if(reqMsg.getRowSrt()==null){
            reqMsg.setRowSrt(new Integer(0));
        }
        reqMsg.setPageSize(10);

        PaginationResult<GetQuestionIndexResponse> paginationResult = new PaginationResult<>();

        Integer pageCnt = questionDao.pageQuery_Count2(reqMsg);
        if (pageCnt==null) {
            paginationResult.setBasePage(new BasePage());
            return paginationResult;
        }
        BasePage pageInfo = PageTemplateUtil.setBasePage(reqMsg, pageCnt);

        GetQuestionIndexResponse result = new GetQuestionIndexResponse();
        List<Question> questionList = questionDao.pageQuery(reqMsg);

        result.setQuestionList(questionList);

        paginationResult.setBasePage(pageInfo);
        paginationResult.setResult(result);

        return paginationResult;
    }

    @Override
    public void exportExamGrade(HttpServletResponse response,int question_base_id) {
        ValidationUtils.checkNotEmpty(question_base_id,"题库id不能为空");

        QuestionBase questionBase = questionDao.getQuestionBaseById(question_base_id);
        List<GradeDO> gradeList = questionDao.findGradeListByBaseId(question_base_id);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("成绩表");
        String[] headers = {"学号","姓名","班级","分数","答题开始时间","答题结束时间"};
        String fileName = questionBase.getName()+System.currentTimeMillis()+".xls";
        try {
            fileName = new String(fileName.getBytes("GB2312"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int rowNum = 1;
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        for (int i = 0;i<gradeList.size();i++) {
            GradeDO grade = gradeList.get(i);
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(grade.getStudent_id());
            row1.createCell(1).setCellValue(grade.getStudent_name());
            row1.createCell(2).setCellValue(grade.getStudent_class());
            row1.createCell(3).setCellValue(grade.getGrade());
            row1.createCell(4).setCellValue(grade.getStart_time());
            row1.createCell(5).setCellValue(grade.getEnd_time());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION),"服务器异常，请联系管理员！");
        }
    }

    @Override
    public void importQuesion(MultipartFile file, int question_base_id) {
        String fileName = file.getOriginalFilename();
        String[] columns = { "问题编号", "题目", "选项类型", "选项A", "选项B", "选项C", "选项D", "正确答案" };
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, String>> list = ExcelUtil.parseExcel(fileName, is, columns);

        List<Question> questionList = new ArrayList<Question>();
        List<Answer> answerList = new ArrayList<Answer>();
        List<RightAnswer> rightAnswerList = new ArrayList<RightAnswer>();
        Question question = null;
        for (Map<String, String> map : list) {
            question = new Question();
            question.setQuestion_base_id(Integer.valueOf(question_base_id));
            question.setQuestion_index(Integer.valueOf((String) map.get("问题编号")).intValue());
            question.setContent((String) map.get("题目"));
            String type = map.get("选项类型");
            if(type.equals("单选")){
                question.setSelect_type(0);
            }else{
                question.setSelect_type(1);
            }


            Answer answer = new Answer();
            answer.setQuestion_id(Integer.valueOf((String) map.get("问题编号")).intValue());
            answer.setAnswer_index("选项A");
            answer.setAnswer_content((String) map.get("选项A"));

            Answer answer1 = new Answer();
            answer1.setQuestion_id(Integer.valueOf((String) map.get("问题编号")).intValue());
            answer1.setAnswer_index("选项B");
            answer1.setAnswer_content((String) map.get("选项B"));

            Answer answer2 = new Answer();
            answer2.setQuestion_id(Integer.valueOf((String) map.get("问题编号")).intValue());
            answer2.setAnswer_index("选项C");
            answer2.setAnswer_content((String) map.get("选项C"));

            Answer answer3 = new Answer();
            answer3.setQuestion_id(Integer.valueOf((String) map.get("问题编号")).intValue());
            answer3.setAnswer_index("选项D");
            answer3.setAnswer_content((String) map.get("选项D"));

            RightAnswer rightAnswer = new RightAnswer();
            rightAnswer.setQuestion_id(Integer.valueOf((String) map.get("问题编号")).intValue());
            rightAnswer.setRight_answer_index((String) map.get("正确答案"));
            rightAnswer.setRight_answer_content((String) map.get("选项" + (String) map.get("正确答案")));

            questionList.add(question);
            answerList.add(answer);
            answerList.add(answer1);
            answerList.add(answer2);
            answerList.add(answer3);
            rightAnswerList.add(rightAnswer);
        }
        System.out.println(rightAnswerList);

        questionDao.insertQuestion(questionList);
        for (Question question1 : questionList) {
            for (Answer answer : answerList) {
                if (answer.getQuestion_id() == question1.getQuestion_index())
                    answer.setQuestion_id(question1.getId());
            }
            for(RightAnswer right : rightAnswerList){
                if(right.getQuestion_id() == question1.getQuestion_index())
                    right.setQuestion_id(question1.getId());
            }
        }
        this.questionDao.insertAnswer(answerList);
        this.questionDao.insertRightAnswer(rightAnswerList);
    }
}
