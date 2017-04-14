package com.fms.controller;

import com.fms.dto.ResultTO;
import com.fms.exception.CommonException;
import com.fms.model.Resource;
import com.fms.model.User;
import com.fms.service.ResourceService;
import com.fms.util.StringUtil;
import com.fms.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * ResourceController
 *
 * @author ZhangXinJie
 * @DATE 2017/4/10
 */
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;


    private static final String FILE_FORMAT = "gif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg," +
            "asf,rm,rmvb,doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2,mp4";

    private static final String video_format = "wmv,avi,rmvb,mp4";
    private static final String img_format = "gif,jpg,jpeg,png";


    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ResultTO uploadResource(HttpServletRequest req,HttpServletResponse res,
                                   @RequestParam("file")CommonsMultipartFile file)throws CommonException,Exception{
        ResultTO result=new ResultTO();
        HttpSession session=req.getSession();
        if(null == session.getAttribute("loginUser")){
            throw new CommonException("用户未登录！");
        }

        User loginUser=(User)session.getAttribute("loginUser");

        if(null == file){
            throw new CommonException("请选择文件！");
        }
        String originalName=file.getOriginalFilename();
        String fileSuffix=originalName.split("\\.")[originalName.split("\\.").length-1];
        if(FILE_FORMAT.indexOf(fileSuffix)<0){
            throw new CommonException("文件格式不合法！");
        }

        Resource resource=new Resource();
        if(video_format.indexOf(fileSuffix)>0){
            resource.setType(1);
        }else if(img_format.indexOf(fileSuffix)>0){
            resource.setType(2);
        }else{
            resource.setType(3);
        }
        resource.setUserId(loginUser.getUserId());
        resource.setFormat(fileSuffix);
        resource.setOriginName(file.getOriginalFilename());
        resource.setResourceName(UUIDGenerator.getUUID() + "." + fileSuffix);
        resource.setResourceSize(file.getSize());
        double size=StringUtil.div(Double.valueOf(resource.getResourceSize().toString()),1024,2);
        String sizeName=size+"K";
        if(size>=1024){
            size=StringUtil.div(size,1024,2);
            sizeName=size+"M";
        }
        if(size>=1024){
            size=StringUtil.div(size,1024,2);
            sizeName=size+"G";
        }
        resource.setSizeName(sizeName);
        String filePath=req.getServletContext().getRealPath("/")+"\\resources\\";
        if(!new File(filePath).exists() || !new File(filePath).isDirectory()){
            new File(filePath).mkdir();
        }
        file.getInputStream();
        File fileData=new File(filePath+resource.getResourceName());
        while(fileData.exists()){
            resource.setResourceName(UUIDGenerator.getUUID() + "." + fileSuffix);
            fileData=new File(filePath+resource.getResourceName());
        }

        BufferedInputStream bis=new BufferedInputStream(file.getInputStream());
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(fileData));
        int b;
        while((b=bis.read())!=-1){
            bos.write(b);
            bos.flush();
        }
        bis.close();
        bos.close();
        resourceService.addResource(resource);
        return result;
    }


    @RequestMapping(value = "/resource/{resourceId}",method = RequestMethod.GET)
    public void download(HttpServletRequest req,HttpServletResponse res,
                         @PathVariable("resourceId")Integer resourceId)throws CommonException,Exception{

        HttpSession session=req.getSession();
        res.setCharacterEncoding("UTF-8");
        OutputStream out=res.getOutputStream();

        if(null == session.getAttribute("loginUser")){
            res.sendRedirect("/login");
        }
        User loginUser=(User)session.getAttribute("loginUser");
        Resource resource=resourceService.getResourceById(loginUser.getUserId(), resourceId);
        if(null == resource){
            out.write("找不到此文件！".getBytes("UTF-8"));
            return;
        }
        String filePath=req.getServletContext().getRealPath("/")+"/resources/"+resource.getResourceName();
        File file=new File(filePath);
        if(!file.exists()){
            out.write("找不到此文件！".getBytes("UTF-8"));
            return;
        }
        res.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(resource.getOriginName(), "UTF-8"));
        BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));
        int b;
        BufferedOutputStream bos=new BufferedOutputStream(out);
        while ((b=bis.read())!=-1){
            bos.write(b);
            bos.flush();
        }
    }

    @RequestMapping(value = "/delete/{resourceId}",method = RequestMethod.POST)
    public ResultTO deleteResource(HttpServletRequest req,@PathVariable("resourceId")Integer resourceId)
            throws CommonException{
        HttpSession session=req.getSession();
        if(null == session.getAttribute("loginUser")){
            throw new CommonException("请先登陆！");
        }
        User loginUser=(User)session.getAttribute("loginUser");
        Resource resource=resourceService.getResourceById(loginUser.getUserId(), resourceId);
        if(null == resource){
            throw new CommonException("找不到资源！");
        }
        String filePath=req.getServletContext().getRealPath("/")+"/resources/"+resource.getResourceName();
        File file=new File(filePath);
        if(null !=file && file.exists()){
            file.delete();
        }
        resourceService.deleteResource(loginUser.getUserId(),resourceId);
        return new ResultTO();
    }


}
