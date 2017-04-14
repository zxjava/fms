package com.fms.mapper;

import com.fms.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */
@Repository
public interface UserMapper {


    User getUserByUserName(@Param("userName")String userName);

    Integer addUser(User user);

    User getUserByUserId(@Param("userId")Integer userId);

    Integer updateUser(User user);

    Integer getUserCount(@Param("keyword")String keyword);

    List<User> getUserList(@Param("keyword")String keyword,@Param("startIndex")Integer startIndex,
                           @Param("pageSize")Integer pageSize);

}
