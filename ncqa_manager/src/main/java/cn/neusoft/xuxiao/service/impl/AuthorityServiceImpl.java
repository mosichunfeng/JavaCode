package cn.neusoft.xuxiao.service.impl;

import cn.neusoft.xuxiao.constants.AuthorityConstants;
import cn.neusoft.xuxiao.dao.entity.Authority;
import cn.neusoft.xuxiao.dao.entity.AuthorityDO;
import cn.neusoft.xuxiao.dao.entity.User;
import cn.neusoft.xuxiao.dao.inf.IAuthorityDao;
import cn.neusoft.xuxiao.dao.inf.IUserDao;
import cn.neusoft.xuxiao.service.inf.IAuthorityService;
import cn.neusoft.xuxiao.utils.ValidationUtils;
import cn.neusoft.xuxiao.webapi.entity.GetIndexByGroupResponse;
import cn.neusoft.xuxiao.webapi.entity.GetIndexByGroupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service("authorityServiceImpl")
public class AuthorityServiceImpl implements IAuthorityService {
    private static Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);

    @Resource(name = "IAuthorityDao")
    private IAuthorityDao authorityDao;

    @Resource(name = "IUserDao")
    private IUserDao userDao;

    @Override
    public GetIndexByGroupResponse getIndexByGroup(GetIndexByGroupRequest reqMsg) {
        List<User> all = userDao.findAll();
        List<Authority> authorityList = new ArrayList<>();
        List<Authority> authorityList2 = new ArrayList<>();
        List<Authority> authorityList3 = new ArrayList<>();
        for (User user : all) {
            AuthorityDO authorityDO = new AuthorityDO();
            authorityDO.setUser_id(user.getId());
            authorityDO.setGroup_id(AuthorityConstants.QUESTION_AUTH);
            Authority auth = authorityDao.findAuthorityByGroupIdAndUserId(authorityDO);
            auth.setUsername(user.getUsername());
            authorityList.add(auth);

            authorityDO.setGroup_id(AuthorityConstants.STUDENT_AUTH);
            auth = authorityDao.findAuthorityByGroupIdAndUserId(authorityDO);
            auth.setUsername(user.getUsername());
            authorityList2.add(auth);

            authorityDO.setGroup_id(AuthorityConstants.REGISTER_AUTH);
            auth = authorityDao.findAuthorityByGroupIdAndUserId(authorityDO);
            auth.setUsername(user.getUsername());
            authorityList3.add(auth);
        }


        GetIndexByGroupResponse result = new GetIndexByGroupResponse();
        result.setAuthorityList(authorityList);
        result.setAuthorityList2(authorityList2);
        result.setAuthorityList3(authorityList3);
        return result;
    }

    @Override
    @Transactional
    public void addUser(String username, String password) {
        ValidationUtils.checkNotEmpty(username, "用户名不能为空!");
        ValidationUtils.checkNotEmpty(password, "密码不能为空！");

        /**
         * 插入user表
         */
        User user_new = new User();
        user_new.setUsername(username);
        user_new.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userDao.insertUser(user_new);
        Integer uid = user_new.getId();
        /**
         * 初始化权限
         */
        Authority[] authorityArr = new Authority[4];

        for (int i = 0; i < 4; i++) {
            authorityArr[i] = new Authority();
            authorityArr[i].setUser_id(uid);
            authorityArr[i].setGroup_id(i + 1);
            authorityDao.insertAuthority(authorityArr[i]);

        }
    }

    @Override
    public void modify(Authority reqMsg) {
        ValidationUtils.checkNotEmpty(reqMsg.getId(), "id不能为空");
        authorityDao.updateAuthority(reqMsg);
    }

    @Override
    public Authority findAuthByUserAndGroup(int user_id, int group_id) {
        return authorityDao.findAuthByUserAndGroup(user_id, group_id);
    }


}
