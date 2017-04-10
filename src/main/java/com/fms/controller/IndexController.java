package com.fms.controller;

import com.fms.dto.ResultTO;
import com.fms.exception.CommonException;
import com.fms.model.User;
import com.fms.service.UserService;
import com.fms.util.EncryptUtils;
import com.fms.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/4/9.
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    public ResultTO pcLogin(HttpServletRequest req,@RequestBody User user)throws CommonException{
        ResultTO result=new ResultTO();
        if(null == user){
            throw new CommonException("参数错误！");
        }
        if(StringUtil.isEmpty(user.getUserName())){
            throw new CommonException("用户帐号不能为空！");
        }
        if(StringUtil.isEmpty(user.getPassword())){
            throw new CommonException("用户密码不能为空！");
        }
        User loginUser = userService.getUserByUserName(user.getUserName());
        if(null == loginUser){
            throw new CommonException("不存在该用户！");
        }
        if(!loginUser.getPassword().equals(EncryptUtils.encryptMD5(user.getPassword()))){
            throw new CommonException("密码错误！");
        }
        req.getSession().setAttribute("loginUser",loginUser);
        return result;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest req,HttpServletResponse res){
        return new ModelAndView("/login");
    }

    @RequestMapping(value = "/index")
    public ModelAndView getResourceList1(HttpServletRequest req,HttpServletResponse res){
        HttpSession session=req.getSession();
        if(null == session.getAttribute("loginUser")){
            return new ModelAndView("redirect:/login");
        }

        return new ModelAndView("/index");
    }

    @RequestMapping(value = {"","/"})
    public ModelAndView getResourceList(HttpServletRequest req,HttpServletResponse res){
        HttpSession session=req.getSession();
        if(null == session.getAttribute("loginUser")){
            return new ModelAndView("redirect:/login");
        }

        return new ModelAndView("/index");
    }

}
