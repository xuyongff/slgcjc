package com.xy.service;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-21
 * Time: 上午10:51
 * To change this template use File | Settings | File Templates.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class AbstractServlet extends HttpServlet
{
    public AbstractServlet() {
        super();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String methodName = request.getParameter("method");
        try
        {
            Method method = this.getClass().getDeclaredMethod(methodName, new Class[] {HttpServletRequest.class, HttpServletResponse.class });
            method.invoke(this, new Object[] { request, response });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
    {
        doGet(request, response);
    }

}
