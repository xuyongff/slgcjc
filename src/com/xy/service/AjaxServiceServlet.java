package com.xy.service;

import com.google.gson.Gson;
import com.sun.rowset.CachedRowSetImpl;
import com.xy.model.YPWTB;
import com.xy.system.db.JdbcTemplate;
import com.xy.system.login.Login;
import com.xy.user.base.UserInfo;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;
import java.io.PrintWriter;
import java.util.*;
import net.sf.json.JSONArray;
/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-21
 * Time: 上午9:11
 * To change this template use File | Settings | File Templates.
 */
public class AjaxServiceServlet extends AbstractServlet {
    //权限管理
    protected void BeforeInvoke(String methodName)
    {
        //System.out.println("Name:"+methodName);
        //Hashtable user = GetUser();
        //if (user.role == "admin" && methodName == "remove") throw .
    }
    //日志管理
    protected void AfterInvoke(String methodName)
    {

    }
    //用户登陆
    public void UserLogin(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //查询条件
        String UserName = request.getParameter("UserName");
         UserName=UserName==null?"":UserName.trim();
        String PassWord = request.getParameter("PassWord");
        PassWord=PassWord==null?"":PassWord.trim();
        String IP=request.getRemoteAddr();
        Login login=new Login();
        String fimisdb="fimisdb";
        //分页
        Gson gson=new Gson();
       if(login.isLogin(fimisdb, UserName, PassWord, IP)){
           PrintWriter out=response.getWriter();
           RequestDispatcher rd=request.getRequestDispatcher("login/main.jsp");
           HttpSession session=((HttpServletRequest)request).getSession();
           response.setCharacterEncoding("UTF-8");
           UserInfo userInfo=new UserInfo();
           userInfo=login.getLoginUser();
           if(userInfo.getStatus()==0){
               session.setAttribute("UserInfo",userInfo);
               List list=new ArrayList() ;
               HashMap map=new HashMap();
               map.put("UserName",userInfo.getUserName());
               map.put("Status","0");
               out.write(gson.toJson(map));
               out.flush();
           }else{
               HashMap map=new HashMap();
               map.put("UserName",userInfo.getUserName());
               map.put("Status","1");
               out.write(gson.toJson(map));
               out.flush();
           }

       }else{
           PrintWriter out=response.getWriter();
           Map map=new HashMap();
           map.put("UserName","");
           map.put("Status", "-1");
           out.write(gson.toJson(map));
           out.flush();
           //out.println("<script language=javascript>alert('用户密码不正确!');</script>");
           //RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
          // rd.forward(request,response);
       }


    }

     //用户权限菜单分配
    public  void UserAuthor(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        String dbsource="fimisdb";
        UserInfo userInfo=new UserInfo();
        HttpSession session=((HttpServletRequest)request).getSession();
        userInfo=(UserInfo)session.getAttribute("UserInfo");
        String YHBH=userInfo.getLoginName();
        JdbcTemplate jdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        Rs=jdbcDB.exeQuery(dbsource,"select F_GNMC,F_GNBH from T_Sys_Menu where F_GNBH in(select substring(F_GNBH,1,4) as F_GNBH from T_Sys_UserMenu where YHBH='"+YHBH+"') order by F_GNBH ASC");
        List list=new ArrayList();
        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        while (Rs.next()){
            HashMap map=new HashMap();
            map.put("F_GNMC",Rs.getString("F_GNMC")==null?"":Rs.getString("F_GNMC").trim());
            map.put("F_GNBH",Rs.getString("F_GNBH")==null?"":Rs.getString("F_GNBH").trim());
            String F_GNBH=Rs.getString("F_GNBH")==null?"":Rs.getString("F_GNBH").trim();
            CachedRowSet rs=new  CachedRowSetImpl();
            String SqlSel="select * from T_Sys_SubMenu where F_GNBH in(select F_GNBH from T_Sys_UserMenu where F_YHBH=(select U_LoginName from T_Sys_User where U_LoginName='"+YHBH+"')) and F_GPBH='"+F_GNBH+"' order by F_SORT ";
            rs=jdbcDB.exeQuery(dbsource,SqlSel);
            List list1=new ArrayList();
            //HashMap map2=new HashMap();
            while(rs.next())
            {
                HashMap map1=new HashMap();
                String F_GNMC,URL1,TARGET;
                F_GNMC=rs.getString("F_GNMC")==null?"":rs.getString("F_GNMC").trim();
                map1.put("F_GNMC",F_GNMC);
                URL1=rs.getString("F_URL")==null?"":rs.getString("F_URL").trim();
                map1.put("Path",URL1);
                TARGET=rs.getString("F_TARGET")==null?"":rs.getString("F_TARGET").trim();
                map1.put("TARGET",TARGET);
                list1.add(map1);
            }
            map.put("SubMenu",list1);
            list.add(map);
        }
        out.write(gson.toJson(list));
        out.flush();
        //System.out.println(gson.toJson(list));
    }
    public void getWellWork(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        ServiceBean serviceBean=new ServiceBean();
        PrintWriter out=response.getWriter();
        String Result=serviceBean.getWorkInfo(request, response);
        out.write(Result);
        out.flush();
    }
    public void getEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        ServiceBean serviceBean=new ServiceBean();
        PrintWriter out=response.getWriter();
        String Result=serviceBean.getEmployee(request, response);
        out.write(Result);
        out.flush();
    }
    public  void getHtmltoJSP(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        String basePath=request.getContextPath();
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        out.println("<title>部门</title>");
        out.println("<script src='"+basePath+"'/ui/bootother.js\" type=\"text/javascript\"></script>");
        out.println("<script src='"+basePath+"'/ui/layer/js/jquery-1.js\" type=\"text/javascript\"></script>");
        out.println("<script src='"+basePath+"'/ui/layer/js/layer.js\" type=\"text/javascript\"></script>");
        out.println("<script src='"+basePath+"'/ui/js/common.js\" type=\"text/javascript></script\">");
        out.println("<link href='"+basePath+"'/ui/layer/css/layer.css\" rel=\"stylesheet type=text/css\"/>");
        out.println(" </head>");
        out.println("<body style=\"min-width:200px;overflow:hidden;\">");
        out.println("</body>");
        out.println("</html>");
    }
    //返回单位树
    public void getDeptTree(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        String dbsource="fimisdb";
        UserInfo userInfo=new UserInfo();
        HttpSession session=((HttpServletRequest)request).getSession();
        userInfo=(UserInfo)session.getAttribute("UserInfo");
        String deptID=userInfo.getLimit();
        JdbcTemplate jdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        Rs=jdbcDB.exeQuery(dbsource,"select F_DWBH,F_DWJC,substring(F_DWBH,1,2*(F_JS-1))PID from T_SYS_DEPT where F_DWBH like '"+deptID+"%' order by F_DWBH");
        List list=new ArrayList();
        PrintWriter out=response.getWriter();
        Gson gson=new Gson();
        while (Rs.next())
        {
            HashMap map=new HashMap();
            String PID=Rs.getString("PID");
            if(PID.compareTo("")==0||PID==null)
                PID="0";
            else
                PID=Rs.getString("PID");
            if(deptID.length()>2&&PID.compareTo("01")==0)
            {
                PID="0";
            }
            String ID=Rs.getString("F_DWBH");
            String Department=Rs.getString("F_DWJC");
            Department=Department==null?"":Department.trim();
            map.put("ID",ID);
            map.put("PID",PID);
            map.put("Name",Department);
            list.add(map);
        }
        out.write(gson.toJson(list));
        out.flush();
    }
    //删除用户信息
    public void getUserList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        UserService userService=new UserService();
        PrintWriter out=response.getWriter();
        String Result=userService.getUserEditList(request, response);
        out.write(Result);
        out.flush();
    }
    public void getUserChange(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        UserService userService=new UserService();
        PrintWriter out=response.getWriter();
        String Result=userService.getUserChange(request, response);
        out.write(Result);
        out.flush();
    }
    public void getRightRgList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        UserService userService=new UserService();
        PrintWriter out=response.getWriter();
        String Result=userService.getRightList(request, response);
        out.write(Result);
        out.flush();
    }
    public void getMenuList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        UserService userService=new UserService();
        PrintWriter out=response.getWriter();
        String Result=userService.getMenuList(request, response);
        //System.out.println(Result);
        out.write(Result);
        out.flush();
    }
    public void getMenuEdit(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        UserService userService=new UserService();
        PrintWriter out=response.getWriter();
        String Result=userService.getMenuEditList(request, response);
        //System.out.println(Result);
        out.write(Result);
        out.flush();
    }
    public void getMenuSelect(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        UserService userService=new UserService();
        PrintWriter out=response.getWriter();
        String Result=userService.getMenuSelect(request, response);
        //System.out.println(Result);
        out.write(Result);
        out.flush();
    }
    public void getSubMenuList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        UserService userService=new UserService();
        PrintWriter out=response.getWriter();
        String Result=userService.getSubMenuList(request, response);
        //System.out.println(Result);
        out.write(Result);
        out.flush();
    }
    public void getSubMenuEdit(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        UserService userService=new UserService();
        PrintWriter out=response.getWriter();
        String Result=userService.getSubMenuEdit(request, response);
        //System.out.println(Result);
        out.write(Result);
        out.flush();
    }
    public void getDepartmentList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        DeptService deptService=new DeptService();
        PrintWriter out=response.getWriter();
        String Result=deptService.getDepartmentList(request, response);
        //System.out.println(Result);
        out.write(Result);
        out.flush();
    }
    public void getCustomerList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        CustomerService customerService=new CustomerService();
        PrintWriter out=response.getWriter();
        String Result=customerService.getCustomerList(request, response);
        //System.out.println(Result);
        out.write(Result);
        out.flush();
    }
    public void getCustomerEditList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        CustomerService customerService=new CustomerService();
        PrintWriter out=response.getWriter();
        String Result=customerService.getCustomerEditList(request, response);
        //System.out.println(Result);
        out.write(Result);
        out.flush();
    }
    public void getCustomerSelect(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        CustomerService customerService=new CustomerService();
        PrintWriter out=response.getWriter();
        String Result=customerService.getCustomerselect(request, response);
        //System.out.println(Result);
        out.write(Result);
        out.flush();
    }
    public void getStandardList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        StandardService standardService=new StandardService();
        PrintWriter out=response.getWriter();
        String Result=standardService.getStandardList(request, response);
        out.write(Result);
        out.flush();
    }
    public void getStandardEditList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        StandardService standardService=new StandardService();
        PrintWriter out=response.getWriter();
        String Result=standardService.getStandardEditList(request, response);
        //System.out.println(Result);
        out.write(Result);
        out.flush();
    }
    public void getStandardSelect(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        StandardService standardService=new StandardService();
        PrintWriter out=response.getWriter();
        String Result=standardService.getStandardSelect(request, response);
        out.write(Result);
        out.flush();
    }
    public void getStandardProList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        StandardService standardService=new StandardService();
        PrintWriter out=response.getWriter();
        String Result=standardService.getStandardProList(request, response);
        out.write(Result);
        out.flush();
    }
    public void getStandardProEditList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        StandardService standardService=new StandardService();
        PrintWriter out=response.getWriter();
        String Result=standardService.getStandardProEditList(request, response);
        out.write(Result);
        out.flush();
    }
    public void getStandardProSelect(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        StandardService standardService=new StandardService();
        PrintWriter out=response.getWriter();
        String Result=standardService.getStandardProSelect(request, response);
        out.write(Result);
        out.flush();
    }
    public void getxmlist(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        StandardService standardService=new StandardService();
        PrintWriter out=response.getWriter();
        String Result=standardService.getxmlist(request, response);
        out.write(Result);
        out.flush();
    }
    public void SuggestionServlet(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out=response.getWriter();
        String type = request.getParameter("DicType");
        type=type==null?"":type;
        JSONArray result=new JSONArray();
        JdbcTemplate jdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        String key = request.getParameter("key");
        String SqlSel="select * from T_DIC_MXB where F_ZJF like '%"+key+"%' and F_ZDLB='"+type+"' ";
        Rs=jdbcDB.exeQuery("fimisdb",SqlSel);

        while(Rs.next()){ //当输入的字符数大于10时不提示，否则由于宽度超出了会显示不正常
            String F_ZDXMMC="";
            F_ZDXMMC=Rs.getString("F_ZDXMMC").trim();
            result.add(F_ZDXMMC);

        }
        out.write(result.toString());
        out.flush();

    }
    public void getYpwtList(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        YpwtdService ypwtdService=new YpwtdService();
        PrintWriter out=response.getWriter();
        String Result=ypwtdService.getYpwtList(request, response);
        out.write(Result);
        out.flush();
       // System.out.println(Result);
    }
    public void getWorkFlow(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        WorkFlowService workFlowService=new WorkFlowService();
        PrintWriter out=response.getWriter();
        String Result=workFlowService.getWorkFlow(request, response);
        out.write(Result);
        out.flush();
        //System.out.println(Result);
    }
}
