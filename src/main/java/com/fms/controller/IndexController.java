package com.fms.controller;

import com.alibaba.fastjson.JSONObject;
import com.fms.dto.ResultTO;
import com.fms.exception.CommonException;
import com.fms.job.UserSession;
import com.fms.model.User;
import com.fms.service.ResourceService;
import com.fms.service.UserService;
import com.fms.util.EncryptUtils;
import com.fms.util.StringUtil;
import com.fms.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2017/4/9.
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

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
        if(loginUser.getDisable().equals(User.DISABLE)){
            throw new CommonException("用户："+loginUser.getUserName()+"已被禁用！");
        }
        req.getSession().setAttribute("loginUser",loginUser);
        return result;
    }

    @RequestMapping(value = "/api/login",method = RequestMethod.POST)
    public ResultTO apiLogin(HttpServletRequest req,@RequestBody User user)throws CommonException{
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
        if(loginUser.getDisable().equals(User.DISABLE)){
            throw new CommonException("用户："+loginUser.getUserName()+"已被禁用！");
        }
        String token= UUIDGenerator.getUUID();
        UserSession.setAdmin(token, loginUser.getUserId());
        JSONObject resdata=new JSONObject();

        resdata.put("loginUser",loginUser);
        resdata.put("token", token);
        result.setResult(resdata);
        return result;
    }

    @RequestMapping(value = "/api/loginuser",method = RequestMethod.GET)
    public ResultTO getUserByToken(String token)throws CommonException{
        ResultTO result=new ResultTO();
        Integer userId=UserSession.getAdmin(token);
        if(null == userId){
            throw new CommonException("用户会话已超时或未登陆！");
        }
        User loginUser=userService.getUserByUserId(userId);
        if(null == loginUser){
            throw new CommonException("找不到用户！");
        }
        result.setResult(loginUser);

        return result;
    }


    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest req,HttpServletResponse res){
        return new ModelAndView("/login");
    }

    @RequestMapping(value = "/index")
    public ModelAndView getResourceList1(HttpServletRequest req,HttpServletResponse res,String keyword,
                                         Integer page){
        HttpSession session=req.getSession();
        if(null == session.getAttribute("loginUser")){
            return new ModelAndView("redirect:/login");
        }
        User loginUser=(User)session.getAttribute("loginUser");
        if(null == page || page<=0){
            page=1;
        }
        req.setAttribute("result",resourceService.getResourceList(loginUser.getUserId(),keyword,page,10));
        return new ModelAndView("/index");
    }

    @RequestMapping(value = {"","/"})
    public ModelAndView getResourceList(HttpServletRequest req,HttpServletResponse res,String keyword,
                                        Integer page)throws Exception{
        HttpSession session=req.getSession();
        if(null == session.getAttribute("loginUser")){
            return new ModelAndView("redirect:/login");
        }
        User loginUser=(User)session.getAttribute("loginUser");
        if(null == page || page<=0){
            page=1;
        }
        req.setAttribute("result",resourceService.getResourceList(loginUser.getUserId(),keyword,page,10));
        return new ModelAndView("/index");
    }

    @RequestMapping(value = "/api/resource",method = RequestMethod.GET)
    public ResultTO apiResourceList(String token,String keyword,
                                        Integer page)throws CommonException{
        ResultTO result=new ResultTO();
        Integer userId=UserSession.getAdmin(token);
        if(null == userId){
            throw new CommonException("用户会话已超时或未登陆！");
        }
        User loginUser=userService.getUserByUserId(userId);
        if(null == loginUser){
            throw new CommonException("找不到用户！");
        }
        if(null == page || page<=0){
            page=1;
        }
        result.setResult(resourceService.getResourceList(loginUser.getUserId(),keyword,page,10));
        return result;
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest req){
        HttpSession session=req.getSession();
        if(null != session.getAttribute("loginUser")){
            session.removeAttribute("loginUser");
        }
        return new ModelAndView("/login");
    }

    @RequestMapping(value = "/api/logout")
    public ResultTO apilogout(String token){
        UserSession.removeAdmin(token);
        return new ResultTO();
    }

}
