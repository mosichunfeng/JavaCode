package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.dao.entity.Question;
import cn.neusoft.xuxiao.dao.entity.QuestionBase;
import cn.neusoft.xuxiao.dao.entity.QuestionCriteria;
import cn.neusoft.xuxiao.dao.entity.StudentCriteria;
import cn.neusoft.xuxiao.dao.inf.IQuestionDao;
import cn.neusoft.xuxiao.service.inf.IQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("questionServiceImpl")
public class QuestionServiceImpl implements IQuestionService {

    private static Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    @Resource(name="IQuestionDao")
    private IQuestionDao questionDao;
}
