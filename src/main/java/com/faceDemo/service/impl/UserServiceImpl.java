package com.faceDemo.service.impl;

import com.faceDemo.dao.IUserDao;
import com.faceDemo.model.User;
import com.faceDemo.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    public User selectUser(long userId) {
        return this.userDao.selectUser(userId);
    }

}
