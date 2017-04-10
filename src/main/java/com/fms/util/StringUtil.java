package com.fms.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xujiawei on 2016/4/28.
 */
public class StringUtil {
    /**
     * 检测是否有特殊字符
     * @param text
     * @return
     */
    public static boolean chkSpecialString(String text) {
        String str = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(str);
        Matcher match = pattern.matcher(text);
        if (match.find())
            return true;
        else
            return false;
    }

    /**
     * 检测SQL关键字防止SQL注入
     * @param text 文本内容
     * @return
     */
    public static String chkSQLString(String text) {
        String strResult = text;
        String strKeyword = "DELETE|UPDATE|DROP|UNION|SELECT|EXEC|XP_CMDSHELL|XP_REGREAD|CHAR(|TRUNCATE";
        strResult = strResult.replace("'", "''");
        strResult = strResult.replace(";", "");
        String[] arr_str = strKeyword.split("\\|");
        for (String str : arr_str) {
            if (strResult.indexOf(str) >= 0) {
                strResult = "";
                break;
            }
        }
        return strResult;
    }

    /**
     * Get hex string from byte array
     */
    public static String toHexString(byte[] res) {
        StringBuffer sb = new StringBuffer(res.length << 1);
        for (int i = 0; i < res.length; i++) {
            String digit = Integer.toHexString(0xFF & res[i]);
            if (digit.length() == 1) {
                digit = '0' + digit;
            }
            sb.append(digit);
        }
        return sb.toString().toUpperCase();
    }

    public static boolean isEmpty(String str){
        if(null==str || "".equals(str.trim())){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str){
        if(null!=str && !"".equals(str.trim())){
            return true;
        }
        return false;
    }

    public static boolean isInteger(String str){
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isDouble(String str){
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*[.]?[\\d]+$");
        return pattern.matcher(str).matches();
    }

    public static boolean isNumber(String str){
        return isInteger(str) || isDouble(str);
    }

    /**
     * 对double数据进行取精度.
     * @param value  double数据.
     * @param scale  精度位数(保留的小数位数).
     * @param roundingMode  精度取值方式.[BigDecimal.ROUND_DOWN向下取整;BigDecimal.ROUND_UP向上取整;BigDecimal.ROUND_HALF_UP四舍五入]
     * @return 精度计算后的数据.
     */
    public static double round(double value, int scale,int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }

    public static String roundToString(double value, int scale,int roundingMode){
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        DecimalFormat df=new DecimalFormat("#,###,###,##0.00");
        return df.format(d);
    }


    /**
     * double 相加
     * @param d1
     * @param d2
     * @return
     */
    public static double sum(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }


    /**
     * double 相减
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 乘法
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1,double d2){
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }


    /**
     * double 除法
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static double div(double d1,double d2,int scale){
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理

        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide
                (bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @return 返回半分比 50%
     */
    public static String numDivide(double d1,int scale){
        NumberFormat nFromat = NumberFormat.getPercentInstance();
        nFromat.setMinimumFractionDigits(scale);
        return nFromat.format(d1);
    }

    public static String getDateTimeStr(String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
    public static String getDateTimeStr(String format,Date date){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取cookie
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie retCookie = null;
        if (null==cookieName || "".equals(cookieName.trim())) {
            return retCookie;
        }
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    retCookie = cookie;
                    break;
                }
            }
        }
        return retCookie;
    }

    /**
     * 写Cookie
     *
     * @param request
     * @param response
     * @param cookieKey
     * @param cookieValue
     * @param maxAgeByDays Cookie保存的天数
     */
    public static void writeCookie(HttpServletRequest request, HttpServletResponse response,boolean httpOnly, String cookieKey, String cookieValue, int maxAgeByDays) {
        int day = 24 * 60 * 60;
        Cookie cookie = getCookie(request, cookieKey);
        if (cookie == null) {
            Cookie newCookie = new Cookie(cookieKey, cookieValue);
            newCookie.setMaxAge(maxAgeByDays * day);
            newCookie.setHttpOnly(true);
            newCookie.setPath("/");
            newCookie.setHttpOnly(httpOnly);
            response.addCookie(newCookie);
        } else {
            cookie.setValue(cookieValue);
            cookie.setMaxAge(maxAgeByDays * day);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setHttpOnly(httpOnly);
            response.addCookie(cookie);
        }

    }

    /**
     * 将文件读取为String
     * @param filePath
     * @return
     */
    public static String ReadFileToString(String filePath){
        String resultStr="";
        if(null == filePath || "".equals(filePath.trim())){
            return resultStr;
        }
        File file=new File(filePath);
        if(!file.exists()){
            return resultStr;
        }
        try {
            StringBuffer sb=new StringBuffer();
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                sb.append(lineTxt);
            }
            read.close();
            resultStr=sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            return resultStr;
        }
        return resultStr;
    }

    /**
     * 按行读取文件为字符串
     * @param filePath
     * @return
     */
    public static List<String> ReadFileToLineList(String filePath){
        String resultStr="";
        List<String> result=null;
        if(null == filePath || "".equals(filePath.trim())){
            return result;
        }
        File file=new File(filePath);
        if(!file.exists()){
            return result;
        }
        try {
            result=new ArrayList<>();
            StringBuffer sb=new StringBuffer();
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while (StringUtil.isNotEmpty((lineTxt = bufferedReader.readLine())) ) {
                result.add(lineTxt.trim());
            }
            read.close();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成静态HTML页面的方法
     *
     * @param request
     *            请求对象
     * @param response
     *            响应对象
     * @param servletContext
     *            Servlet上下文
     * @param fileName
     *            文件名称
     * @param fileFullPath
     *            文件完整路径
     * @param jspPath
     *            需要生成静态文件的JSP路径(相对即可)
     * @throws IOException
     * @throws ServletException
     */
    public static void createHtml(HttpServletRequest request, HttpServletResponse response,
                       ServletContext servletContext, String fileName, String fileFullPath, String jspPath)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=gb2312");// 设置HTML结果流编码(即HTML文件编码)
        RequestDispatcher rd = servletContext.getRequestDispatcher(jspPath);// 得到JSP资源
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();// 用于从ServletOutputStream中接收资源
        final ServletOutputStream servletOuputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }// 用于从HttpServletResponse中接收资源
            public void write(byte[] b, int off, int len) {
                byteArrayOutputStream.write(b, off, len);
            }

            public void write(int b) {
                byteArrayOutputStream.write(b);
            }
        };
        final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream));// 把转换字节流转换成字符流
        HttpServletResponse httpServletResponse = new HttpServletResponseWrapper(response) {// 用于从response获取结果流资源(重写了两个方法)
            public ServletOutputStream getOutputStream() {
                return servletOuputStream;
            }

            public PrintWriter getWriter() {
                return printWriter;
            }
        };
        rd.include(request, httpServletResponse);// 发送结果流
        printWriter.flush();// 刷新缓冲区，把缓冲区的数据输出
        FileOutputStream fileOutputStream = new FileOutputStream(fileFullPath);
        OutputStreamWriter osw=new OutputStreamWriter(fileOutputStream,"utf-8");
        osw.write(byteArrayOutputStream.toString());
//        byteArrayOutputStream.writeTo(fileOutputStream);// 把byteArrayOuputStream中的资源全部写入到fileOuputStream中
        byteArrayOutputStream.close();
        fileOutputStream.close();// 关闭输出流，并释放相关资源
//        response.sendRedirect(fileName);// 发送指定文件流到客户端
    }

}
