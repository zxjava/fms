package com.fms.mapper;

import com.fms.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/4/9.
 */
@Repository
public interface UserMapper {


    User getUserByUserName(@Param("userName")String userName);

}
