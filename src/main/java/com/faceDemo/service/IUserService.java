package com.faceDemo.service;

import com.faceDemo.model.DataResp;
import com.faceDemo.model.User;

public interface IUserService {

    public User selectUser(long userId);

    public DataResp register(User user);

    public DataResp login(User user, boolean passwordLogin);

    public DataResp aliveCheck(String imgBase64, String checkType, String userId);

}
