package cn.neusoft.xuxiao.service.impl;

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

    @Override
    public void testError() {
        System.out.println("hello");
        logger.info("我自横刀向天笑");
        logger.error("笑完我就去睡觉");
        logger.debug("去你妈的，又来黑我");
    }
}
