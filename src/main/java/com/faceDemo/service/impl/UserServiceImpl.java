package com.faceDemo.service.impl;

import com.faceDemo.dao.IUserDao;
import com.faceDemo.model.DataResp;
import com.faceDemo.model.User;
import com.faceDemo.service.IUserService;
import com.faceDemo.util.FaceHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.HashMap;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    public User selectUser(long userId) {
        return this.userDao.selectUser(userId);
    }

    @Override
    public DataResp register(User user) {
        // 识别人脸
        DataResp dataResp = FaceHelper.faceDetect(user.getImgBase64());

        if (dataResp.getCode() == DataResp.Code.SUCCESS) {
            // 把 base64 存到数据库中，以便以后作对比使用
            userDao.insertUser(user);
        }

        return dataResp;
    }

    @Override
    public DataResp login(User user, boolean passwordLogin) {
        DataResp dataResp = new DataResp();
        // 判断是否是刷脸登陆
        if (!passwordLogin) {
            // 首先要对提交的人脸做一下识别，看是否是符合用来作人脸对比的图像
            dataResp = FaceHelper.faceDetect(user.getImgBase64());
            // 如果成功的话，就用这张人脸和注册时录入的人脸进行比对
            if (dataResp.getCode() == DataResp.Code.SUCCESS) {
                // 查询登陆用户
                User compareUser = userDao.selectUserByUsername(user);
                if (user != null) {
                    // 进行对比
                    dataResp = FaceHelper.faceCompare(user.getImgBase64(), compareUser.getImgBase64());
                    if (dataResp.getCode() == DataResp.Code.SUCCESS) {
                        // 把用户返回前端，用于后续操作
                        dataResp.setData(compareUser);
                    }
                } else {
                    dataResp.setCode(DataResp.Code.ERROR);
                    dataResp.setMessage("该用户不存在");
                }

            }
            // 否则，识别失败的话，提示，重新刷脸

        } else {
            // 密码登陆
            user = userDao.selectUserByUsernameAndPassword(user);
            if (user != null) {
                dataResp.setMessage("登陆成功");
                dataResp.setCode(DataResp.Code.SUCCESS);
                dataResp.setData(user);
            } else {
                dataResp.setCode(DataResp.Code.ERROR);
                dataResp.setMessage("登陆失败，用户名或密码错误");
            }
        }

        return dataResp;
    }

    @Override
    public DataResp aliveCheck(String imgBase64, String checkType, String userId) {
        System.out.println("后台收到的 checkType: "+checkType+" base64：" + imgBase64);
        return FaceHelper.aliveCheck(imgBase64, checkType, userId);
    }
}
