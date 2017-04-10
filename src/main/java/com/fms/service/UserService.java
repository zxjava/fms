package com.fms.service;

import com.fms.mapper.UserMapper;
import com.fms.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
