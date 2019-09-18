package com.xy.service;

/**
 * Created by Administrator on 2019/8/11.
 */

import com.google.gson.Gson;
import com.sun.rowset.CachedRowSetImpl;
import com.xy.system.db.JdbcTemplate;
import com.xy.user.base.UserInfo;
import com.xy.util.UtilFunction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserService {
    public String getUserEditList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("USER_LIST_SYS");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String sCondition = "";
        String Dept = userInfo.getDepartment();
        String SSDW = userInfo.getPosition();
        String mUser = request.getParameter("UserName");
        mUser = mUser == null ? "" : mUser.trim();
        String mDEPTMC = request.getParameter("SGDH");
        mDEPTMC = mDEPTMC == null ? "" : mDEPTMC.trim();
        String mDEPT = request.getParameter("DH");
        mDEPT = mDEPT == null ? "" : mDEPT.trim();
        String sFlag = "";
        sFlag = mUser + mDEPT;
        StringBuffer sb = new StringBuffer();
        if (mUser.compareTo("") != 0)
            sb.append(" and U_UserName like '%" + mUser + "%' ");
        if (mDEPTMC.compareTo("") != 0)
            sb.append(" and Limit like '%" + mDEPT + "%' ");
        if (!sFlag.equals("")) {
            sb.delete(sb.indexOf("and"), sb.indexOf("and") + 3);
            SqlStr = sb.toString();
            session.setAttribute("USER_LIST_SYS", SqlStr);
            SqlSel = SqlStr;
        } else {
            if (SqlSel.equals("")) {
                SqlStr = " Limit like '%" + mDEPT + "%'";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("USER_LIST_SYS", SqlStr);
                SqlSel = SqlStr;
            }
        }
        //System.out.println(SqlStr);
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        final String sortName = request.getParameter("sortName");
        final String sortOrder = request.getParameter("sortOrder");
        int startRow = pageSize * (curPage - 1) + 1;
        int endRow = startRow + pageSize - 1;
        int k = startRow + pageSize;
        JdbcTemplate JdbcDB = new JdbcTemplate();
        CachedRowSet Rs = new CachedRowSetImpl();
        // "{call SP_Pagination('logdbwork','Id','Id asc',1,300000,'*','','')}"

        Rs = JdbcDB.exeQuery("fimisdb", "select Count(*) as CNT from T_Sys_User where " + SqlSel + " ");
        int totalRows = 0;
        if (Rs.next()) {
            totalRows = Rs.getInt("CNT");
        }
        Rs.close();

        String[] Param = new String[8];
        Param[0] = "T_Sys_User";
        Param[1] = "UserID";
        Param[2] = "UserID asc";
        Param[3] = String.valueOf(curPage);
        Param[4] = String.valueOf(pageSize);
        Param[5] = "*";
        Param[6] = SqlSel;
        Param[7] = "";
        //String SP_Pagination="{call SP_Pagination("+Param[0]+","+Param[1]+","+Param[2]+","+Param[3]+","+Param[4]+","+Param[5]+","+Param[6]+","+Param[7]+")}";
        //System.out.println(SP_Pagination);
        HashMap map = new HashMap();
        map.put("success", true);
        map.put("totalRows", totalRows);
        map.put("curPage", curPage);
        Rs = JdbcDB.callProcQuery("fimisdb", Param);
        List list = new ArrayList();
        int rec = 0;
        rec = startRow;
        //Rs.absolute(endRow);
        int iRows = 0;
        while (Rs.next()) {
            HashMap map1 = new HashMap();
            String mBMMC, mYHMC, mYHZH, mYHZW,mYHZT,mYHLX;
            mBMMC = mYHMC = mYHZH = mYHZW = mYHZT=mYHLX="";
            int nUserID = 0;int nStatus=0;int nType=0;
            mBMMC = Rs.getString("U_Department") == null ? "" : Rs.getString("U_Department").trim();
            mYHMC = Rs.getString("U_UserName") == null ? "" : Rs.getString("U_UserName").trim();
            mYHZH = Rs.getString("U_LoginName") == null ? "" : Rs.getString("U_LoginName").trim();
            mYHZW = Rs.getString("U_Position") == null ? "" : Rs.getString("U_Position").trim();
            nUserID=Rs.getInt("UserID");
            nStatus=Rs.getInt("U_Status");
            nType=Rs.getInt("U_Type");
            if(nType==0)
                mYHZT="<font color=0000FF>正常</font>";
            else
                mYHZT="<font color=FF0000>冻结</font>";
            if(nType==0)
                mYHLX="<font color=0000FF>管理员</font>";
            else
                mYHLX="<font color=FF0000>一般用户</font>";
            map1.put("XH", rec);
            map1.put("ID", nUserID);
            map1.put("BMMC", mBMMC);
            map1.put("YHMC", mYHMC);
            map1.put("YHZH", mYHZH);
            map1.put("YHZW", mYHZW);
            map1.put("YHZT", mYHZT);
            map1.put("YHLX", mYHLX);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data", list);
        Gson gson = new Gson();
        Result = gson.toJson(map);
        return Result;
    }
    public String getUserChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("USER_CHANGE_SYS");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String sCondition = "";
        String Dept = userInfo.getDepartment();
        String SSDW = userInfo.getPosition();
        String mUser= request.getParameter("UserName");
        mUser=mUser==null?"":mUser.trim();
        String mDEPTMC=request.getParameter("SGDH");
        mDEPTMC=mDEPTMC==null?"":mDEPTMC.trim();
        String mDEPT=request.getParameter("DH");
        mDEPT=mDEPT==null?"":mDEPT.trim();
        String sFlag ="";
        sFlag=mUser+mDEPT;
        StringBuffer sb=new StringBuffer();
        if(mUser.compareTo("")!=0)
            sb.append(" and U_UserName like '%"+mUser+"%' ");
        if(mDEPTMC.compareTo("")!=0)
            sb.append(" and Limit like '%"+mDEPT+"%' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("USER_CHANGE_SYS",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" Limit like '%"+mDEPT+"%'";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("USER_CHANGE_SYS",SqlStr);
                SqlSel=SqlStr;
            }
        }
        //System.out.println(SqlStr);
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        final String sortName = request.getParameter("sortName");
        final String sortOrder = request.getParameter("sortOrder");
        int startRow = pageSize * (curPage - 1) + 1;
        int endRow = startRow + pageSize - 1;
        int k=startRow+pageSize;
        JdbcTemplate JdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        // "{call SP_Pagination('logdbwork','Id','Id asc',1,300000,'*','','')}"

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_Sys_User where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_Sys_User"; Param[1]="UserID"; Param[2]="UserID asc"; Param[3]=String.valueOf(curPage);
        Param[4]=String.valueOf(pageSize);  Param[5]="*"; Param[6]=SqlSel; Param[7]="";
        //String SP_Pagination="{call SP_Pagination("+Param[0]+","+Param[1]+","+Param[2]+","+Param[3]+","+Param[4]+","+Param[5]+","+Param[6]+","+Param[7]+")}";
        //System.out.println(SP_Pagination);
        HashMap map=new HashMap();
        map.put("success",true);
        map.put("totalRows",totalRows);
        map.put("curPage",curPage);
        Rs=JdbcDB.callProcQuery("fimisdb",Param);
        List list=new ArrayList();
        int rec=0;
        rec=startRow;
        //Rs.absolute(endRow);
        int iRows=0;
        while(Rs.next()) {
            HashMap map1 = new HashMap();
            String mBMMC, mYHMC, mYHZH, mYHZW,mYHZT,mYHLX;
            mBMMC = mYHMC = mYHZH = mYHZW = mYHZT=mYHLX="";
            int nUserID = 0;int nStatus=0;int nType=0;
            mBMMC = Rs.getString("U_Department") == null ? "" : Rs.getString("U_Department").trim();
            mYHMC = Rs.getString("U_UserName") == null ? "" : Rs.getString("U_UserName").trim();
            mYHZH = Rs.getString("U_LoginName") == null ? "" : Rs.getString("U_LoginName").trim();
            mYHZW = Rs.getString("U_Position") == null ? "" : Rs.getString("U_Position").trim();
            nUserID=Rs.getInt("UserID");
            nStatus=Rs.getInt("U_Status");
            nType=Rs.getInt("U_Type");
            if(nType==0)
            mYHZT="<font color=0000FF>正常</font>";
	        else
            mYHZT="<font color=FF0000>冻结</font>";
            if(nType==0)
                mYHLX="<font color=0000FF>管理员</font>";
            else
                mYHLX="<font color=FF0000>一般用户</font>";
            map1.put("XH", rec);
            map1.put("ID", nUserID);
            map1.put("BMMC", mBMMC);
            map1.put("YHMC", mYHMC);
            map1.put("YHZH", mYHZH);
            map1.put("YHZW", mYHZW);
            map1.put("YHZT", mYHZT);
            map1.put("YHLX", mYHLX);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getRightList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("USER_RIGHT_SYS");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String sCondition = "";
        String Dept = userInfo.getDepartment();
        String SSDW = userInfo.getPosition();
        String mUser= request.getParameter("UserName");
        mUser=mUser==null?"":mUser.trim();
        String mDEPTMC=request.getParameter("SGDH");
        mDEPTMC=mDEPTMC==null?"":mDEPTMC.trim();
        String mDEPT=request.getParameter("DH");
        mDEPT=mDEPT==null?"":mDEPT.trim();
        String sFlag ="";
        sFlag=mUser+mDEPT;
        StringBuffer sb=new StringBuffer();
        if(mUser.compareTo("")!=0)
            sb.append(" and U_UserName like '%"+mUser+"%' ");
        if(mDEPTMC.compareTo("")!=0)
            sb.append(" and Limit like '%"+mDEPT+"%' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("USER_RIGHT_SYS",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" Limit like '%"+mDEPT+"%'";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("USER_RIGHT_SYS",SqlStr);
                SqlSel=SqlStr;
            }
        }
        //System.out.println(SqlStr);
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        final String sortName = request.getParameter("sortName");
        final String sortOrder = request.getParameter("sortOrder");
        int startRow = pageSize * (curPage - 1) + 1;
        int endRow = startRow + pageSize - 1;
        int k=startRow+pageSize;
        JdbcTemplate JdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        // "{call SP_Pagination('logdbwork','Id','Id asc',1,300000,'*','','')}"

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_Sys_User where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_Sys_User"; Param[1]="UserID"; Param[2]="UserID asc"; Param[3]=String.valueOf(curPage);
        Param[4]=String.valueOf(pageSize);  Param[5]="*"; Param[6]=SqlSel; Param[7]="";
        //String SP_Pagination="{call SP_Pagination("+Param[0]+","+Param[1]+","+Param[2]+","+Param[3]+","+Param[4]+","+Param[5]+","+Param[6]+","+Param[7]+")}";
        //System.out.println(SP_Pagination);
        HashMap map=new HashMap();
        map.put("success",true);
        map.put("totalRows",totalRows);
        map.put("curPage",curPage);
        Rs=JdbcDB.callProcQuery("fimisdb",Param);
        List list=new ArrayList();
        int rec=0;
        rec=startRow;
        //Rs.absolute(endRow);
        int iRows=0;
        while(Rs.next()) {
            HashMap map1 = new HashMap();
            String mBMMC, mYHMC, mYHZH, mYHZW,mYHZT,mYHLX;
            mBMMC = mYHMC = mYHZH = mYHZW = mYHZT=mYHLX="";
            int nUserID = 0;int nStatus=0;int nType=0;
            mBMMC = Rs.getString("U_Department") == null ? "" : Rs.getString("U_Department").trim();
            mYHMC = Rs.getString("U_UserName") == null ? "" : Rs.getString("U_UserName").trim();
            mYHZH = Rs.getString("U_LoginName") == null ? "" : Rs.getString("U_LoginName").trim();
            mYHZW = Rs.getString("U_Position") == null ? "" : Rs.getString("U_Position").trim();
            nUserID=Rs.getInt("UserID");
            nStatus=Rs.getInt("U_Status");
            nType=Rs.getInt("U_Type");
            if(nType==0)
                mYHZT="<font color=0000FF>正常</font>";
            else
                mYHZT="<font color=FF0000>冻结</font>";
            if(nType==0)
                mYHLX="<font color=0000FF>管理员</font>";
            else
                mYHLX="<font color=FF0000>一般用户</font>";
            map1.put("XH", rec);
            map1.put("ID", nUserID);
            map1.put("BMMC", mBMMC);
            map1.put("YHMC", mYHMC);
            map1.put("YHZH", mYHZH);
            map1.put("YHZW", mYHZW);
            map1.put("YHZT", mYHZT);
            map1.put("YHLX", mYHLX);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getMenuList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("USER_MENU_LIST");
        SqlSel = SqlSel == null ? "" : SqlSel;
        SqlSel=" ID>0";
        //System.out.println(SqlStr);
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        final String sortName = request.getParameter("sortName");
        final String sortOrder = request.getParameter("sortOrder");
        int startRow = pageSize * (curPage - 1) + 1;
        int endRow = startRow + pageSize - 1;
        int k=startRow+pageSize;
        JdbcTemplate JdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        // "{call SP_Pagination('logdbwork','Id','Id asc',1,300000,'*','','')}"

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_Sys_Menu where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_Sys_Menu"; Param[1]="ID"; Param[2]="ID asc"; Param[3]=String.valueOf(curPage);
        Param[4]=String.valueOf(pageSize);  Param[5]="*"; Param[6]=SqlSel; Param[7]="";
        //String SP_Pagination="{call SP_Pagination("+Param[0]+","+Param[1]+","+Param[2]+","+Param[3]+","+Param[4]+","+Param[5]+","+Param[6]+","+Param[7]+")}";
        //System.out.println(SP_Pagination);
        HashMap map=new HashMap();
        map.put("success",true);
        map.put("totalRows",totalRows);
        map.put("curPage",curPage);
        Rs=JdbcDB.callProcQuery("fimisdb",Param);
        List list=new ArrayList();
        int rec=0;
        rec=startRow;
        //Rs.absolute(endRow);
        int iRows=0;
        while(Rs.next()) {
            HashMap map1 = new HashMap();
            String mGNBH, mGNMC, mZJF,mJLR,mJLSJ,mBMZT;
            mGNBH= mGNMC= mZJF=mJLR=mJLSJ=mBMZT="";
            int nID = 0;int nBMZT=0;
            mGNBH = Rs.getString("F_GNBH") == null ? "" : Rs.getString("F_GNBH").trim();
            mGNMC = Rs.getString("F_GNMC") == null ? "" : Rs.getString("F_GNMC").trim();
            mZJF = Rs.getString("ZJF") == null ? "" : Rs.getString("ZJF").trim();
            mJLR = Rs.getString("JLR") == null ? "" : Rs.getString("JLR").trim();
            mJLSJ=Rs.getString("JLSJ")==null?"":Rs.getString("JLSJ").trim();
            if(mJLSJ.compareTo("")==0||mJLSJ.substring(0,10).compareTo("1900-01-01")==0)
                mJLSJ="";
            else
                mJLSJ=mJLSJ.substring(0,10);
            nID=Rs.getInt("ID");
            nBMZT=Rs.getInt("BMZT");
            if(nBMZT==1)
                mBMZT="<font color=0000FF>正常</font>";
            else
                mBMZT="<font color=FF0000>冻结</font>";
            map1.put("XH", rec);
            map1.put("ID", nID);
            map1.put("GNBH", mGNBH);
            map1.put("GNMC", mGNMC);
            map1.put("ZJF", mZJF);
            map1.put("BMZT", mBMZT);
            map1.put("BMSJ", mJLSJ);
            map1.put("BMR", mJLR);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }

    public String getMenuEditList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("USER_MENU_EDIT_LIST");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String sCondition = "";
        String Dept = userInfo.getDepartment();
        String SSDW = userInfo.getPosition();
        String mGNBH= request.getParameter("GNBH");
        mGNBH=mGNBH==null?"":mGNBH.trim();
        String mGNMC=request.getParameter("GNMC");
        mGNMC=mGNMC==null?"":mGNMC.trim();
        String mZJF=request.getParameter("ZJF");
        mZJF=mZJF==null?"":mZJF.trim();
        String mBMZT=request.getParameter("BMZT");
        mBMZT=mBMZT==null?"":mBMZT.trim();
        String sFlag ="";
        sFlag=mGNBH+mGNMC+mZJF+mBMZT;
        StringBuffer sb=new StringBuffer();
        if(mGNBH.compareTo("")!=0)
            sb.append(" and F_GNBH like '%"+mGNBH+"%' ");
        if(mGNMC.compareTo("")!=0)
            sb.append(" and F_GNMC like '%"+mGNMC+"%' ");
        if(mZJF.compareTo("")!=0)
            sb.append(" and ZJF like '%"+mZJF+"%' ");
        if(mBMZT.compareTo("")!=0)
            sb.append(" and BMZT='"+Fun.StrToInt(mBMZT)+"' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("USER_MENU_EDIT_LIST",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" BMZT=1 ";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("USER_MENU_EDIT_LIST",SqlStr);
                SqlSel=SqlStr;
            }
        }
        //System.out.println(SqlSel);
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        final String sortName = request.getParameter("sortName");
        final String sortOrder = request.getParameter("sortOrder");
        int startRow = pageSize * (curPage - 1) + 1;
        int endRow = startRow + pageSize - 1;
        int k=startRow+pageSize;
        JdbcTemplate JdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        // "{call SP_Pagination('logdbwork','Id','Id asc',1,300000,'*','','')}"

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_Sys_Menu where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_Sys_Menu"; Param[1]="ID"; Param[2]="ID asc"; Param[3]=String.valueOf(curPage);
        Param[4]=String.valueOf(pageSize);  Param[5]="*"; Param[6]=SqlSel; Param[7]="";
        //String SP_Pagination="{call SP_Pagination("+Param[0]+","+Param[1]+","+Param[2]+","+Param[3]+","+Param[4]+","+Param[5]+","+Param[6]+","+Param[7]+")}";
        //System.out.println(SP_Pagination);
        HashMap map=new HashMap();
        map.put("success",true);
        map.put("totalRows",totalRows);
        map.put("curPage",curPage);
        Rs=JdbcDB.callProcQuery("fimisdb",Param);
        List list=new ArrayList();
        int rec=0;
        rec=startRow;
        //Rs.absolute(endRow);
        int iRows=0;
        while(Rs.next()) {
            HashMap map1 = new HashMap();
            String sGNBH, sGNMC, sZJF,mJLR,mJLSJ,sBMZT;
            sGNBH= sGNMC=sZJF=mJLR=mJLSJ=sBMZT="";
            int nID = 0;int nBMZT=0;
            sGNBH = Rs.getString("F_GNBH") == null ? "" : Rs.getString("F_GNBH").trim();
            sGNMC = Rs.getString("F_GNMC") == null ? "" : Rs.getString("F_GNMC").trim();
            sZJF = Rs.getString("ZJF") == null ? "" : Rs.getString("ZJF").trim();
            mJLR = Rs.getString("JLR") == null ? "" : Rs.getString("JLR").trim();
            mJLSJ=Rs.getString("JLSJ")==null?"":Rs.getString("JLSJ").trim();
            if(mJLSJ.compareTo("")==0||mJLSJ.substring(0,10).compareTo("1900-01-01")==0)
                mJLSJ="";
            else
                mJLSJ=mJLSJ.substring(0,10);
            nID=Rs.getInt("ID");
            nBMZT=Rs.getInt("BMZT");
            if(nBMZT==1)
                mBMZT="<font color=0000FF>正常</font>";
            else
                mBMZT="<font color=FF0000>冻结</font>";
            map1.put("XH", rec);
            map1.put("ID", nID);
            map1.put("GNBH", sGNBH);
            map1.put("GNMC", sGNMC);
            map1.put("ZJF", sZJF);
            map1.put("BMZT", mBMZT);
            map1.put("BMSJ", mJLSJ);
            map1.put("BMR", mJLR);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getMenuSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("USER_MENU_SELECT");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String sCondition = "";
        String Dept = userInfo.getDepartment();
        String SSDW = userInfo.getPosition();
        String mGNBH= request.getParameter("GNBH");
        mGNBH=mGNBH==null?"":mGNBH.trim();
        String mGNMC=request.getParameter("GNMC");
        mGNMC=mGNMC==null?"":mGNMC.trim();
        String mZJF=request.getParameter("ZJF");
        mZJF=mZJF==null?"":mZJF.trim();
        String mBMZT=request.getParameter("BMZT");
        mBMZT=mBMZT==null?"":mBMZT.trim();
        String sFlag ="";
        sFlag=mGNBH+mGNMC+mZJF+mBMZT;
        StringBuffer sb=new StringBuffer();
        if(mGNBH.compareTo("")!=0)
            sb.append(" and F_GNBH like '%"+mGNBH+"%' ");
        if(mGNMC.compareTo("")!=0)
            sb.append(" and F_GNMC like '%"+mGNMC+"%' ");
        if(mZJF.compareTo("")!=0)
            sb.append(" and ZJF like '%"+mZJF+"%' ");
        if(mBMZT.compareTo("")!=0)
            sb.append(" and BMZT='"+Fun.StrToInt(mBMZT)+"' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("USER_MENU_SELECT",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" BMZT=1 ";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("USER_MENU_SELECT",SqlStr);
                SqlSel=SqlStr;
            }
        }
        //System.out.println(SqlSel);
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        final String sortName = request.getParameter("sortName");
        final String sortOrder = request.getParameter("sortOrder");
        int startRow = pageSize * (curPage - 1) + 1;
        int endRow = startRow + pageSize - 1;
        int k=startRow+pageSize;
        JdbcTemplate JdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        // "{call SP_Pagination('logdbwork','Id','Id asc',1,300000,'*','','')}"

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_Sys_Menu where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_Sys_Menu"; Param[1]="ID"; Param[2]="ID asc"; Param[3]=String.valueOf(curPage);
        Param[4]=String.valueOf(pageSize);  Param[5]="*"; Param[6]=SqlSel; Param[7]="";
        //String SP_Pagination="{call SP_Pagination("+Param[0]+","+Param[1]+","+Param[2]+","+Param[3]+","+Param[4]+","+Param[5]+","+Param[6]+","+Param[7]+")}";
        //System.out.println(SP_Pagination);
        HashMap map=new HashMap();
        map.put("success",true);
        map.put("totalRows",totalRows);
        map.put("curPage",curPage);
        Rs=JdbcDB.callProcQuery("fimisdb",Param);
        List list=new ArrayList();
        int rec=0;
        rec=startRow;
        //Rs.absolute(endRow);
        int iRows=0;
        while(Rs.next()) {
            HashMap map1 = new HashMap();
            String sGNBH, sGNMC, sZJF,mJLR,mJLSJ,sBMZT;
            sGNBH= sGNMC=sZJF=mJLR=mJLSJ=sBMZT="";
            int nID = 0;int nBMZT=0;
            sGNBH = Rs.getString("F_GNBH") == null ? "" : Rs.getString("F_GNBH").trim();
            sGNMC = Rs.getString("F_GNMC") == null ? "" : Rs.getString("F_GNMC").trim();
            sZJF = Rs.getString("ZJF") == null ? "" : Rs.getString("ZJF").trim();
            mJLR = Rs.getString("JLR") == null ? "" : Rs.getString("JLR").trim();
            mJLSJ=Rs.getString("JLSJ")==null?"":Rs.getString("JLSJ").trim();
            if(mJLSJ.compareTo("")==0||mJLSJ.substring(0,10).compareTo("1900-01-01")==0)
                mJLSJ="";
            else
                mJLSJ=mJLSJ.substring(0,10);
            nID=Rs.getInt("ID");
            nBMZT=Rs.getInt("BMZT");
            if(nBMZT==1)
                mBMZT="<font color=0000FF>正常</font>";
            else
                mBMZT="<font color=FF0000>冻结</font>";
            map1.put("XH", rec);
            map1.put("ID", nID);
            map1.put("GNBH", sGNBH);
            map1.put("GNMC", sGNMC);
            map1.put("ZJF", sZJF);
            map1.put("BMZT", mBMZT);
            map1.put("BMSJ", mJLSJ);
            map1.put("BMR", mJLR);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getSubMenuList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel =""; //(String) session.getAttribute("USER_MENU_SUB");
        SqlSel = SqlSel == null ? "ID>0" : SqlSel;
        SqlSel=" ID>0";
        //System.out.println(SqlSel);
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        final String sortName = request.getParameter("sortName");
        final String sortOrder = request.getParameter("sortOrder");
        int startRow = pageSize * (curPage - 1) + 1;
        int endRow = startRow + pageSize - 1;
        int k=startRow+pageSize;
        JdbcTemplate JdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        // "{call SP_Pagination('logdbwork','Id','Id asc',1,300000,'*','','')}"
        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_Sys_SubMenu where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();
        String [] Param=new String[8];
        Param[0]="T_Sys_SubMenu"; Param[1]="ID"; Param[2]="ID asc"; Param[3]=String.valueOf(curPage);
        Param[4]=String.valueOf(pageSize);  Param[5]="*"; Param[6]=SqlSel; Param[7]="";
        //String exec SP_Pagination 'T_Sys_SubMenu','Id','Id asc',2,15,'*','',''
        //System.out.println(SP_Pagination);
        HashMap map=new HashMap();
        map.put("success",true);
        map.put("totalRows",totalRows);
        map.put("curPage",curPage);
        Rs=JdbcDB.callProcQuery("fimisdb",Param);
        List list=new ArrayList();
        int rec=0;
        rec=startRow;
        //Rs.absolute(endRow);
        int iRows=0;
        while(Rs.next()) {
            HashMap map1 = new HashMap();
            String ZYBH,ZYMC,URL,TARGET,GNBH,GNMC,ZJF,BMZT,BMSJ,BMR,YHBM;
            ZYBH=ZYMC=URL=TARGET=GNBH=GNMC=ZJF=BMZT=BMSJ=BMR=YHBH="";
            String mEnable="";
            int ID=Rs.getInt("ID");
            int nZT=0;
            //WXDH=Rs.getString("SerialID")==null?"":Rs.getString("SerialID").trim();
            GNBH=Rs.getString("F_GPBH")==null?"":Rs.getString("F_GPBH").trim();
            GNMC=Rs.getString("F_GPMC")==null?"":Rs.getString("F_GPMC").trim();
            ZYBH=Rs.getString("F_GNBH")==null?"":Rs.getString("F_GNBH").trim();
            ZYMC=Rs.getString("F_GNMC")==null?"":Rs.getString("F_GNMC").trim();
            URL=Rs.getString("F_URL")==null?"":Rs.getString("F_URL").trim();
            TARGET=Rs.getString("F_TARGET")==null?"":Rs.getString("F_TARGET").trim();
            BMSJ=Rs.getString("JLSJ")==null?"":Rs.getString("JLSJ").trim();
            BMR=Rs.getString("JLR")==null?"":Rs.getString("JLR").trim();
            YHBM=Rs.getString("YHBH")==null?"":Rs.getString("YHBH").trim();
            nZT=0;
            if(BMSJ.compareTo("")==0||BMSJ.substring(0,10).compareTo("1900-01-01")==0)
                BMSJ="";
            else
                BMSJ=BMSJ.substring(0,10);
            if(nZT==0)
            {
                BMZT="<font color=#FF0000>冻结</font>";
            }
            else{
                BMZT="<font color=#3366FF>启用</font>";
            }
            map1.put("XH", rec);
            map1.put("ID", ID);
            map1.put("GNBH", GNBH);
            map1.put("GNMC", GNMC);
            map1.put("ZYBH", ZYBH);
            map1.put("ZYMC", ZYMC);
            map1.put("URL1", URL);
            map1.put("TARGET", TARGET);
            map1.put("BMSJ", BMSJ);
            map1.put("BMR", BMR);
            map1.put("BMZT", BMZT);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getSubMenuEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("USER_MENU_SUB_LIST");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String mGNBH= request.getParameter("GNBH");
        mGNBH=mGNBH==null?"":mGNBH.trim();
        String mGNMC=request.getParameter("GNMC");
        mGNMC=mGNMC==null?"":mGNMC.trim();
        String mZYBH=request.getParameter("ZYBH");
        mZYBH=mZYBH==null?"":mZYBH.trim();
        String mZYMC=request.getParameter("ZYMC");
        mZYMC=mZYMC==null?"":mZYMC.trim();
        String mZJF=request.getParameter("ZJF");
        mZJF=mZJF==null?"":mZJF.trim();
        String sFlag ="";
        sFlag=mGNBH+mGNMC+mZJF+mZYBH+mZYMC;
        StringBuffer sb=new StringBuffer();
        if(mGNBH.compareTo("")!=0)
            sb.append(" and F_GPBH like '%"+mGNBH+"%' ");
        if(mGNMC.compareTo("")!=0)
            sb.append(" and F_GPMC like '%"+mGNMC+"%' ");
        if(mZYBH.compareTo("")!=0)
            sb.append(" and F_GNBH like '%"+mZYBH+"%' ");
        if(mZYMC.compareTo("")!=0)
            sb.append(" and F_GNMC like '%"+mZYMC+"%' ");
        if(mZJF.compareTo("")!=0)
            sb.append(" and ZJF like '%"+mZJF+"%' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("USER_MENU_SUB_LIST",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" ID>0 ";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("USER_MENU_SUB_LIST",SqlStr);
                SqlSel=SqlStr;
            }
        }
        //System.out.println(SqlStr);
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        final String sortName = request.getParameter("sortName");
        final String sortOrder = request.getParameter("sortOrder");
        int startRow = pageSize * (curPage - 1) + 1;
        int endRow = startRow + pageSize - 1;
        int k=startRow+pageSize;
        JdbcTemplate JdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        // "{call SP_Pagination('logdbwork','Id','Id asc',1,300000,'*','','')}"

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_Sys_SubMenu where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_Sys_SubMenu"; Param[1]="ID"; Param[2]="ID asc"; Param[3]=String.valueOf(curPage);
        Param[4]=String.valueOf(pageSize);  Param[5]="*"; Param[6]=SqlSel; Param[7]="";
        //String SP_Pagination="{call SP_Pagination("+Param[0]+","+Param[1]+","+Param[2]+","+Param[3]+","+Param[4]+","+Param[5]+","+Param[6]+","+Param[7]+")}";
        //System.out.println(SP_Pagination);
        HashMap map=new HashMap();
        map.put("success",true);
        map.put("totalRows",totalRows);
        map.put("curPage",curPage);
        Rs=JdbcDB.callProcQuery("fimisdb",Param);
        List list=new ArrayList();
        int rec=0;
        rec=startRow;
        //Rs.absolute(endRow);
        int iRows=0;
        while(Rs.next()) {
            HashMap map1 = new HashMap();
            String ZYBH,ZYMC,URL,TARGET,GNBH,GNMC,ZJF,BMZT,BMSJ,BMR,YHBM;
            ZYBH=ZYMC=URL=TARGET=GNBH=GNMC=ZJF=BMZT=BMSJ=BMR=YHBH="";
            String mEnable="";
            int ID=Rs.getInt("ID");
            int nZT=0;
            //WXDH=Rs.getString("SerialID")==null?"":Rs.getString("SerialID").trim();
            GNBH=Rs.getString("F_GPBH")==null?"":Rs.getString("F_GPBH").trim();
            GNMC=Rs.getString("F_GPMC")==null?"":Rs.getString("F_GPMC").trim();
            ZYBH=Rs.getString("F_GNBH")==null?"":Rs.getString("F_GNBH").trim();
            ZYMC=Rs.getString("F_GNMC")==null?"":Rs.getString("F_GNMC").trim();
            URL=Rs.getString("F_URL")==null?"":Rs.getString("F_URL").trim();
            TARGET=Rs.getString("F_TARGET")==null?"":Rs.getString("F_TARGET").trim();
            BMSJ=Rs.getString("JLSJ")==null?"":Rs.getString("JLSJ").trim();
            BMR=Rs.getString("JLR")==null?"":Rs.getString("JLR").trim();
            YHBM=Rs.getString("YHBH")==null?"":Rs.getString("YHBH").trim();
            nZT=0;
            if(BMSJ.compareTo("")==0||BMSJ.substring(0,10).compareTo("1900-01-01")==0)
                BMSJ="";
            else
                BMSJ=BMSJ.substring(0,10);
            if(nZT==0)
            {
                BMZT="<font color=#FF0000>冻结</font>";
            }
            else{
                BMZT="<font color=#3366FF>启用</font>";
            }
            map1.put("XH", rec);
            map1.put("ID", ID);
            map1.put("GNBH", GNBH);
            map1.put("GNMC", GNMC);
            map1.put("ZYBH", ZYBH);
            map1.put("ZYMC", ZYMC);
            map1.put("URL1", URL);
            map1.put("TARGET", TARGET);
            map1.put("BMSJ", BMSJ);
            map1.put("BMR", BMR);
            map1.put("BMZT", BMZT);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
}
