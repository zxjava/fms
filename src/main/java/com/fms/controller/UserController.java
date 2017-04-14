package com.fms.controller;

import com.fms.dto.ResultTO;
import com.fms.exception.CommonException;
import com.fms.model.User;
import com.fms.service.UserService;
import com.fms.util.EncryptUtils;
import com.fms.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * UserController
 *
 * @author ZhangXinJie
 * @DATE 2017/4/14
 */
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public ModelAndView getUserList(HttpServletRequest req,String keyword,Integer page)throws CommonException{
        HttpSession session=req.getSession();
        if(null == session.getAttribute("loginUser")){
            return new ModelAndView("redirect:/login");
        }
        User loginUser=(User)session.getAttribute("loginUser");
        if(loginUser.getUserType().equals(User.TYPE_USER)){
            return new ModelAndView("redirect:/index");
        }
        if(null == page || page<=0){
            page=1;
        }
        req.setAttribute("result",userService.getUserList(keyword,page,10));
        return new ModelAndView("/user");
    }


    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    public ResultTO addUser(HttpServletRequest req,HttpServletResponse res,
                            @RequestBody User user)throws CommonException,Exception{
        HttpSession session=req.getSession();
        if(null == session.getAttribute("loginUser")){
            res.sendRedirect("/login");
        }
        User loginUser=(User)session.getAttribute("loginUser");
        if(null == loginUser){
            res.sendRedirect("/login");
        }
        if(loginUser.getUserType().equals(User.TYPE_USER)){
            throw new CommonException("您不是管理员，无法添加用户！");
        }
        if(null == user){
            throw new CommonException("参数不合法！");
        }
        if(StringUtil.isEmpty(user.getUserName()) || user.getUserName().trim().length()>10){
            throw new CommonException("用户名格式错误！");
        }
        if(null !=userService.getUserByUserName(user.getUserName())){
            throw new CommonException("用户名已存在！");
        }
        if(StringUtil.isEmpty(user.getPassword()) || StringUtil.chkSpecialString(user.getPassword())
                || user.getPassword().trim().length()<6 || user.getPassword().trim().length()>18){
            throw new CommonException("密码格式错误！");
        }
        if(StringUtil.isNotEmpty(user.getMobile()) && ( !StringUtil.isInteger(user.getMobile())
                || user.getMobile().trim().length()>11)){
            throw new CommonException("手机号格式错误！");
        }
        if(user.getEmail().trim().length()>50){
            throw new CommonException("Email过长！");
        }
        if(user.getQq().trim().length()>20){
            throw new CommonException("QQ过长！");
        }
        user.setDisable(User.NO_DISABLE);
        user.setUserType(User.TYPE_USER);
        user.setPassword(EncryptUtils.encryptMD5(user.getPassword()));
        userService.addUser(user);
        return new ResultTO();
    }

    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    public ResultTO updUser(HttpServletRequest req,HttpServletResponse res,
                            @RequestBody User user)throws CommonException,Exception{
        HttpSession session=req.getSession();
        if(null == session.getAttribute("loginUser")){
            res.sendRedirect("/login");
        }
        User loginUser=(User)session.getAttribute("loginUser");
        if(null == loginUser){
            res.sendRedirect("/login");
        }
        if(loginUser.getUserType().equals(User.TYPE_USER)){
            throw new CommonException("您不是管理员，无法修改用户！");
        }
        if(null == user){
            throw new CommonException("参数不合法！");
        }
        User updUser=userService.getUserByUserId(user.getUserId());
        if(null ==updUser){
            throw new CommonException("找不到用户！");
        }
        if(StringUtil.chkSpecialString(user.getPassword())
                || (user.getPassword().trim().length()>0 &&
                (user.getPassword().trim().length()<6 || user.getPassword().trim().length()>18))){
            throw new CommonException("密码格式错误！");
        }else{
            updUser.setPassword(EncryptUtils.encryptMD5(user.getPassword()));
        }
        if(StringUtil.isNotEmpty(user.getMobile()) && (!StringUtil.isInteger(user.getMobile())
                || user.getMobile().trim().length()>11)){
            throw new CommonException("手机号格式错误！");
        }else{
            updUser.setMobile(user.getMobile());
        }
        if(user.getEmail().trim().length()>50){
            throw new CommonException("Email过长！");
        }else{
            updUser.setEmail(user.getEmail());
        }
        if(user.getQq().trim().length()>20){
            throw new CommonException("QQ过长！");
        }else{
            updUser.setQq(user.getQq());
        }
        user.setDisable(User.NO_DISABLE);
        user.setUserType(User.TYPE_USER);
        userService.updateUserInfo(updUser);
        return new ResultTO();
    }

    @RequestMapping(value = "/user/delete/{userId}",method = RequestMethod.POST)
    public ResultTO delUser(HttpServletRequest req,HttpServletResponse res,
                            @PathVariable("userId")Integer userId)throws CommonException,Exception{
        HttpSession session=req.getSession();
        if(null == session){
            res.sendRedirect("/login");
        }
        User loginUser=(User)session.getAttribute("loginUser");

        User delUser=userService.getUserByUserId(userId);
        if(null == delUser){
            throw new CommonException("找不到该用户！");
        }
        if(loginUser.getUserType().equals(User.TYPE_USER)){
            if(delUser.getDisable().equals(User.DISABLE)){
                throw new CommonException("您不是管理员，无法启用用户！");
            }else{
                throw new CommonException("您不是管理员，无法禁用用户！");
            }
        }
        if(delUser.getUserType().equals(User.TYPE_ADMIN)){
            throw new CommonException("这是管理员账号，无法操作！");
        }
        delUser.setDisable(User.DISABLE.equals(delUser.getDisable())?User.NO_DISABLE:User.DISABLE);
        userService.updateUserInfo(delUser);
        return new ResultTO();
    }

}
