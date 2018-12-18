package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.dao.inf.IRegisterDao;
import cn.neusoft.xuxiao.service.inf.IRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("registerServiceImpl")
public class RegisterServiceImpl implements IRegisterService {

    private static Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Resource(name="IRegisterDao")
    private IRegisterDao registerDao;
}
