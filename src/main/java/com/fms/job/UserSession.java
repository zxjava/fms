package com.fms.job;

import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * UserSession
 *
 * @author ZhangXinJie
 * @DATE 2017/4/19
 */
@Component
public class UserSession {





    @Scheduled(cron = "0 0/1 * * * ?")//每分钟
    private void recycleTimeoutAdminUser(){
        Iterator<String> keys= this.getKeys();
        while(keys.hasNext()){
            this.getAdmin(keys.next());
        }
    }

    /**
     * 后台管理系统 用户会话
     */
    private static final long ACTIVE_TIME = 1000*60*60;//1小时

    private static final Map<String,JSONObject> ADMIN_USER=new HashMap<>();

    public static void  setAdmin(String token,Integer adminUserId){
        JSONObject obj=new JSONObject();
        obj.put("data",adminUserId);
        obj.put("time",new Date().getTime());
        ADMIN_USER.put(token,obj);
    }

    public static Integer getAdmin(String token){
        if(ADMIN_USER.containsKey(token)){
            JSONObject obj=ADMIN_USER.get(token);
            if((new Date().getTime()-obj.getLong("time"))<ACTIVE_TIME){
                return obj.getInteger("data");
            }else{
                Integer userId=obj.getInteger("data");
                System.out.println(userId+":>>is timeout");
                removeAdmin(token);
            }
        }
        return null;
    }

    public static Iterator<String> getKeys(){
        return ADMIN_USER.keySet().iterator();
    }

    public static void removeAdmin(String token){
        if(ADMIN_USER.containsKey(token)){
            ADMIN_USER.remove(token);
        }
    }

    public static void resetTime(String token){
        JSONObject obj=ADMIN_USER.get(token);
        obj.put("time",new Date().getTime());
        ADMIN_USER.put(token,obj);
    }
    /**
     * 后台管理系统 用户会话end
     */




}
