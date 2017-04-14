package com.fms.service;

import com.fms.dto.PageTO;
import com.fms.dto.ResultTO;
import com.fms.mapper.UserMapper;
import com.fms.model.Resource;
import com.fms.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/9.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public User getUserByUserName(String username){
        return userMapper.getUserByUserName(username);
    }

    public User getUserByUserId(Integer userId){
        return userMapper.getUserByUserId(userId);
    }

    public Integer updateUserInfo(User user){
        return userMapper.updateUser(user);
    }

    public ResultTO getUserList(String keyword,Integer pageIndex,Integer pageSize){
        ResultTO result=new ResultTO();
        PageTO page=new PageTO();
        page.setPageSize(pageSize);
        page.setCurrentPage(pageIndex);
        page.setTotal(userMapper.getUserCount(keyword));
        if(page.getTotal()>0){
            Integer startIndex=(pageIndex-1)*pageSize;
            result.setResult(userMapper.getUserList(keyword,startIndex,pageSize));
        }else{
            result.setResult(new ArrayList<>());
        }
        result.setPage(page);
        return result;
    }

    public Integer addUser(User user){
        return userMapper.addUser(user);
    }

}
