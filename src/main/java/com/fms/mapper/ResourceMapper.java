package com.fms.mapper;

import com.fms.model.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */
@Repository
public interface ResourceMapper {


    Integer addResource(Resource resource);

    Resource getResourceById(@Param("userId")Integer userId,@Param("resourceId")Integer resourceId);

    List<Resource> getResourceList(@Param("userId")Integer userId,@Param("keyword")String word,
                                   @Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);

    Integer getResourceCount(@Param("userId")Integer userId,@Param("keyword")String word);

    Integer deleteResource(@Param("userId")Integer userId,@Param("resourceId")Integer resourceId);

}
