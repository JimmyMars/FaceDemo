package com.faceDemo.dao;

import com.faceDemo.model.User;

public interface IUserDao {

    User selectUser(long id);

    void insertUser(User user);

    User selectUserByUsernameAndPassword(User user);

    User selectUserByUsername(User user);

}
