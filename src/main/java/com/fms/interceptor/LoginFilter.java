package com.fms.interceptor;

import com.alibaba.fastjson.JSON;
import com.fms.dto.ResultTO;
import com.fms.job.UserSession;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LoginFilter
 *
 * @author ZhangXinJie
 * @DATE 2017/4/19
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;

        String token="fms_token";

        if(req.getServletPath().startsWith("/api")){
            res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
            res.setHeader("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS");
            res.setHeader("Access-Control-Allow-Credentials","true");
            res.setHeader("Access-Control-Allow-Headers",
                    "origin, content-type, accept, x-requested-with,fms_token");
            res.setHeader("Allow", "GET, POST, PUT, DELETE, OPTIONS");

            String loginToken=null;
            if ((null == (loginToken = req.getParameter("token"))
                    || null == UserSession.getAdmin(loginToken) )
                    && !req.getServletPath().endsWith("login") && !req.getMethod().equals("OPTIONS")) {
                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json;charset=utf-8");
                ResultTO result = new ResultTO();
                result.authException("User is not login");
                res.getWriter().write(JSON.toJSONString(result));
                res.getWriter().close();
            }else if(req.getServletPath().endsWith("login") || req.getMethod().equals("OPTIONS")){
                filterChain.doFilter(request, response);
            }else{
                UserSession.resetTime(loginToken);
                filterChain.doFilter(request, response);
            }
        }//end  login
    }

    @Override
    public void destroy() {

    }
}
