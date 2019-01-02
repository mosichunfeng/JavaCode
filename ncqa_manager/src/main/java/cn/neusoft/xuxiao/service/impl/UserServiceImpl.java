package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.constants.ServiceResponseCode;
import cn.neusoft.xuxiao.dao.entity.User;
import cn.neusoft.xuxiao.dao.inf.IUserDao;
import cn.neusoft.xuxiao.exception.BusinessException;
import cn.neusoft.xuxiao.service.inf.IUserService;
import cn.neusoft.xuxiao.utils.JsonTool;
import cn.neusoft.xuxiao.utils.RedisUtil;
import cn.neusoft.xuxiao.utils.ValidationUtils;
import jdk.nashorn.internal.parser.Token;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.UUID;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource(name="IUserDao")
    private IUserDao userDao;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public String login(User user) {
        ValidationUtils.checkNotEmpty(user.getUsername(), "用户名不能为空!");
        ValidationUtils.checkNotEmpty(user.getPassword(), "密码不能为空!");

        User orgin_user = userDao.findUserByUsername(user.getUsername());
        if(orgin_user==null){
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION), "用户名或密码错误！");
        }
        if(!DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).equals(orgin_user.getPassword())){
            throw new BusinessException(String.valueOf(ServiceResponseCode.BUSINESS_EXCEPTION),"用户名或密码错误！");
        }
        String token = UUID.randomUUID().toString();
        orgin_user.setPassword(null);
        redisUtil.set("SESSION:"+token, JsonTool.dataToJson(orgin_user));
        redisUtil.expire("SESSION:"+token, 1800);

        return token;
    }

}
