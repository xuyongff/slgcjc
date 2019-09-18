package com.xy.system.filter;

import com.xy.user.base.UserInfo;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserFilter extends HttpServlet implements Filter {
    private ServletContext servletContext;
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        this.servletContext = filterConfig.getServletContext();

        System.out.println("**过滤器初始化..");
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // TODO Auto-generated method stub

        HttpSession session=((HttpServletRequest)request).getSession();
        response.setCharacterEncoding("utf-8");
        UserInfo userInfo=new UserInfo();
        userInfo=(UserInfo)session.getAttribute("UserInfo");
        if(userInfo==null){
            PrintWriter out=response.getWriter();

            String contexPath=((HttpServletRequest) request).getContextPath()+"/index.jsp";//servletContext.getContextPath()+"/index.jsp";
            //System.out.println(contexPath);
            out.print("<script language=javascript>alert('您还没有登录!');window.parent.location.href='"+contexPath+"';window.opener=null;</script>");
        }else{
            filterChain.doFilter(request, response);
        }

    }
}