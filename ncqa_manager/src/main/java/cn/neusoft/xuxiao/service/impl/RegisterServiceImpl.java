package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.dao.entity.*;
import cn.neusoft.xuxiao.dao.inf.ICallDao;
import cn.neusoft.xuxiao.dao.inf.IRegisterDao;
import cn.neusoft.xuxiao.dao.inf.IStudentDao;
import cn.neusoft.xuxiao.service.inf.IRegisterService;
import cn.neusoft.xuxiao.utils.PageTemplateUtil;
import cn.neusoft.xuxiao.utils.TimeTool;
import cn.neusoft.xuxiao.utils.ValidationUtils;
import cn.neusoft.xuxiao.webapi.entity.BasePage;
import cn.neusoft.xuxiao.webapi.entity.FindRegisterListResponse;
import cn.neusoft.xuxiao.webapi.entity.GetRegisterIndexResponse;
import cn.neusoft.xuxiao.webapi.entity.PaginationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service("registerServiceImpl")
public class RegisterServiceImpl implements IRegisterService {

    private static Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Resource(name="IRegisterDao")
    private IRegisterDao registerDao;

    @Resource(name="IStudentDao")
    private IStudentDao studentDao;

    @Resource(name="ICallDao")
    private ICallDao callDao;

    @Override
    public PaginationResult<GetRegisterIndexResponse> pageQuery(RegisterCriteria reqMsg) {
        List<Register> registerList = registerDao.findAll();
        List<RegisterInfo> registerInfoList = new ArrayList<>();
        /**
         * 查询在校离校总人数
         */
        int inCount = studentDao.findInCount();
        int outCount = studentDao.findOutCount();

        Set<Integer> weekSet = new LinkedHashSet<>();

        for (Register register : registerList) {
            int week = TimeTool.getWeekOfYear(TimeTool.StrToDate(register.getStart_time()));
            weekSet.add(week);
        }

        for(Integer any : weekSet){
            RegisterInfo registerInfo = new RegisterInfo();
            registerInfo.setRegister_week(TimeTool.getNowYear()+"年第"+any+"周");
            int []count = executeCount(registerList, any);
            registerInfo.setRegister_in_count(count[0]);
            registerInfo.setRegister_out_count(count[1]);
            registerInfo.setRegister_in_last(inCount-count[0]);
            registerInfo.setRegister_out_last(outCount-count[1]);
            registerInfoList.add(registerInfo);
        }
        System.out.println(registerInfoList);
        return null;
    }

    @Override
    public PaginationResult<FindRegisterListResponse> findRegisterListByCallId(RegisterCriteria reqMsg) {
        ValidationUtils.checkNotEmpty(reqMsg.getCall_id(), "call_id不能为空！");
        Call call = callDao.findCallById(reqMsg.getCall_id());
        reqMsg.setStart_time(call.getStart_time());
        reqMsg.setEnd_time(call.getEnd_time());

        if(reqMsg.getRowSrt()==null){
            reqMsg.setRowSrt(new Integer(0));
        }
        reqMsg.setPageSize(10);

        PaginationResult<FindRegisterListResponse> paginationResult = new PaginationResult<>();

        Integer pageCnt = registerDao.pageQuery_Count(reqMsg);
        if (pageCnt==null) {
            paginationResult.setBasePage(new BasePage());
            return paginationResult;
        }
        BasePage pageInfo = PageTemplateUtil.setBasePage(reqMsg, pageCnt);

        FindRegisterListResponse result = new FindRegisterListResponse();
        List<Register> registerList = registerDao.pageQuery(reqMsg);

        for (Register register : registerList) {
            Student student = studentDao.findStudentByStudentId(register.getStudent_id());
            register.setStudent_class(studentDao.findClassById(student.getStudent_class_id()).getName());
        }
        result.setRegisterList(registerList);

        paginationResult.setBasePage(pageInfo);
        paginationResult.setResult(result);

        return paginationResult;
    }

    public  int[] executeCount(List<Register> registerList,int any){
        Set<String> inCountSet = new LinkedHashSet<>();
        Set<String> outCountSet = new LinkedHashSet<>();

        for (Register register : registerList) {
            int week = TimeTool.getWeekOfYear(TimeTool.StrToDate(register.getStart_time()));
            if(week==any){
                if(studentDao.getWorkDetail(register.getStudent_id())!=null){
                    if(studentDao.getWorkDetail(register.getStudent_id())==0){
                        inCountSet.add(register.getStudent_id());
                    }else{
                        outCountSet.add(register.getStudent_id());
                    }
                }
            }
        }
        return new int[]{inCountSet.size(),outCountSet.size()};
    }
}
