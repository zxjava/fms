package com.fms.service;

import com.fms.dto.PageTO;
import com.fms.dto.ResultTO;
import com.fms.mapper.ResourceMapper;
import com.fms.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * ResourceService
 *
 * @author ZhangXinJie
 * @DATE 2017/4/11
 */
@Service
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;


    public Integer addResource(Resource resource){
        return resourceMapper.addResource(resource);
    }

    public Resource getResourceById(Integer userId,Integer resourceId){
        return resourceMapper.getResourceById(userId,resourceId);
    }

    public ResultTO getResourceList(Integer userId,String keyword,Integer pageIndex,Integer pageSize){
        ResultTO result=new ResultTO();
        PageTO page=new PageTO();
        page.setCurrentPage(pageIndex);
        page.setPageSize(pageSize);
        page.setTotal(resourceMapper.getResourceCount(userId, keyword));
        if(page.getTotal()>0){
            Integer startIndex=(pageIndex-1)*pageSize;
            result.setResult(resourceMapper.getResourceList(userId,keyword,startIndex,pageSize));
        }else{
            result.setResult(new ArrayList<>());
        }
        result.setPage(page);
        return result;
    }

    public Integer deleteResource(Integer userId,Integer resourceId){


        return resourceMapper.deleteResource(userId,resourceId);
    }

}
