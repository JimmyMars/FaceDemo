package com.faceDemo.controller;

import com.faceDemo.model.DataResp;
import com.faceDemo.model.User;
import com.faceDemo.service.IUserService;
import com.faceDemo.util.FaceHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("/register.do")
    @ResponseBody
    public DataResp register(User user) {
        return userService.register(user);
    }

    /**
     * 登陆
     * @param user
     * @param passwordLogin
     * @return
     */
    @RequestMapping("/login.do")
    @ResponseBody
    public DataResp login(User user, boolean passwordLogin) {
        return userService.login(user, passwordLogin);
    }

    @RequestMapping("/aliveCheck")
    @ResponseBody
    public DataResp aliveCheck(String imgBase64, String checkType, String userId) {
        return userService.aliveCheck(imgBase64, checkType, userId);
    }

}
