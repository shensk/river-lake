package com.company.project.service.impl;

import com.company.project.dao.UserMapper;
import com.company.project.model.User;
import com.company.project.service.UserService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by CodeGenerator on 2019/05/07.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public void testInsert() {
        User user = new User();
        user.setNickName("test");
        user.setPassword("123456");
        user.setSex(1);
        user.setUsername("testU");
        user.setRegisterDate(new Date());
        userMapper.testInsert(user);
    }
}
