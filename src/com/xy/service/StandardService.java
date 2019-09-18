package com.xy.service;

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

/**
 * Created by Administrator on 2019/8/30.
 */
public class StandardService {
    public String getStandardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String DeptNo=userInfo.getLimit();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("STANDARD_LIST");
        SqlSel = SqlSel == null ? "" : SqlSel;
        SqlSel=" F_BMBH like '"+DeptNo+"%' ";
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_FF_JCBZ where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();
        String [] Param=new String[8];
        Param[0]="T_FF_JCBZ"; Param[1]="F_ID"; Param[2]="F_ID DESC"; Param[3]=String.valueOf(curPage);
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
            String F_BZBH,F_BZMC,F_BMBH,F_BMMC,F_JLR,F_BZZT,F_JLSJ,F_ZJF;
            F_BZBH=F_BZMC=F_BMBH=F_BMMC=F_JLR=F_BZZT=F_JLSJ=F_ZJF="";
            int nID = 0;int nBMZT=0;float F_BZJE=0;
            F_BZBH = Rs.getString("F_BZBH") == null ? "" : Rs.getString("F_BZBH").trim();
            F_BZMC = Rs.getString("F_BZMC") == null ? "" : Rs.getString("F_BZMC").trim();
            F_BMBH = Rs.getString("F_BMBH") == null ? "" : Rs.getString("F_BMBH").trim();
            F_BMMC = Rs.getString("F_BMMC") == null ? "" : Rs.getString("F_BMMC").trim();
            F_ZJF = Rs.getString("F_ZJF") == null ? "" : Rs.getString("F_ZJF").trim();
            F_BZJE = Rs.getFloat("F_BZJE");
            F_JLR = Rs.getString("F_JLR") == null ? "" : Rs.getString("F_JLR").trim();
            F_JLSJ=Rs.getString("F_JLSJ")==null?"":Rs.getString("F_JLSJ").trim();
            if(F_JLSJ.compareTo("")==0||F_JLSJ.substring(0,10).compareTo("1900-01-01")==0)
                F_JLSJ="";
            else
                F_JLSJ=F_JLSJ.substring(0,10);
            nID=Rs.getInt("F_ID");
            nBMZT=Rs.getInt("F_BZZT");
            if(nBMZT==1)
                F_BZZT="<font color=0000FF>正常</font>";
            else
                F_BZZT="<font color=FF0000>冻结</font>";
            map1.put("XH", rec);
            map1.put("ID", nID);
            map1.put("F_BZBH", F_BZBH);
            map1.put("F_BZMC", F_BZMC);
            map1.put("F_BMMC", F_BMMC);
            map1.put("F_BZJE", F_BZJE);
            map1.put("F_JLR", F_JLR);
            map1.put("F_JLSJ", F_JLSJ);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_ZJF", F_ZJF);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getStandardEditList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String DeptNo=userInfo.getLimit();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("STANDARD_EDIT_LIST");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String mDWMC= request.getParameter("F_BZMC");
        mDWMC=mDWMC==null?"":mDWMC.trim();
        String mDWBH=request.getParameter("F_BZBH");
        mDWBH=mDWBH==null?"":mDWBH.trim();
        String mZJF=request.getParameter("F_ZJF");
        mZJF=mZJF==null?"":mZJF.trim();
        String sFlag ="";
        sFlag=mDWMC+mDWBH+mZJF;
        StringBuffer sb=new StringBuffer();
        if(mDWMC.compareTo("")!=0)
            sb.append(" and F_BZMC like '%"+mDWMC+"%' ");
        if(mDWBH.compareTo("")!=0)
            sb.append(" and F_BZBH like '%"+mDWBH+"%' ");
        if(mZJF.compareTo("")!=0)
            sb.append(" and F_ZJF like '%"+mZJF+"%' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("STANDARD_EDIT_LIST",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" F_BZZT=1 ";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("STANDARD_EDIT_LIST",SqlStr);
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_FF_JCBZ where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_FF_JCBZ"; Param[1]="F_ID"; Param[2]="F_ID DESC"; Param[3]=String.valueOf(curPage);
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
            String F_BZBH,F_BZMC,F_BMBH,F_BMMC,F_JLR,F_BZZT,F_JLSJ,F_ZJF;
            F_BZBH=F_BZMC=F_BMBH=F_BMMC=F_JLR=F_BZZT=F_JLSJ=F_ZJF="";
            int nID = 0;int nBMZT=0;float F_BZJE=0;
            F_BZBH = Rs.getString("F_BZBH") == null ? "" : Rs.getString("F_BZBH").trim();
            F_BZMC = Rs.getString("F_BZMC") == null ? "" : Rs.getString("F_BZMC").trim();
            F_BMBH = Rs.getString("F_BMBH") == null ? "" : Rs.getString("F_BMBH").trim();
            F_BMMC = Rs.getString("F_BMMC") == null ? "" : Rs.getString("F_BMMC").trim();
            F_ZJF = Rs.getString("F_ZJF") == null ? "" : Rs.getString("F_ZJF").trim();
            F_BZJE = Rs.getFloat("F_BZJE");
            F_JLR = Rs.getString("F_JLR") == null ? "" : Rs.getString("F_JLR").trim();
            F_JLSJ=Rs.getString("F_JLSJ")==null?"":Rs.getString("F_JLSJ").trim();
            if(F_JLSJ.compareTo("")==0||F_JLSJ.substring(0,10).compareTo("1900-01-01")==0)
                F_JLSJ="";
            else
                F_JLSJ=F_JLSJ.substring(0,10);
            nID=Rs.getInt("F_ID");
            nBMZT=Rs.getInt("F_BZZT");
            if(nBMZT==1)
                F_BZZT="<font color=0000FF>正常</font>";
            else
                F_BZZT="<font color=FF0000>冻结</font>";
            map1.put("XH", rec);
            map1.put("ID", nID);
            map1.put("F_BZBH", F_BZBH);
            map1.put("F_BZMC", F_BZMC);
            map1.put("F_BMMC", F_BMMC);
            map1.put("F_BZJE", F_BZJE);
            map1.put("F_JLR", F_JLR);
            map1.put("F_JLSJ", F_JLSJ);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_ZJF", F_ZJF);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getStandardSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String DeptNo=userInfo.getLimit();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("STANDARD_SELECT");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String mDWMC= request.getParameter("F_BZMC");
        mDWMC=mDWMC==null?"":mDWMC.trim();
        String mDWBH=request.getParameter("F_BZBH");
        mDWBH=mDWBH==null?"":mDWBH.trim();
        String mZJF=request.getParameter("F_ZJF");
        mZJF=mZJF==null?"":mZJF.trim();
        String sFlag ="";
        sFlag=mDWMC+mDWBH+mZJF;
        StringBuffer sb=new StringBuffer();
        if(mDWMC.compareTo("")!=0)
            sb.append(" and F_BZMC like '%"+mDWMC+"%' ");
        if(mDWBH.compareTo("")!=0)
            sb.append(" and F_BZBH like '%"+mDWBH+"%' ");
        if(mZJF.compareTo("")!=0)
            sb.append(" and F_ZJF like '%"+mZJF+"%' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("STANDARD_SELECT",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" F_BZZT=1 ";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("STANDARD_SELECT",SqlStr);
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_FF_JCBZ where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_FF_JCBZ"; Param[1]="F_ID"; Param[2]="F_ID DESC"; Param[3]=String.valueOf(curPage);
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
            String F_BZBH,F_BZMC,F_BMBH,F_BMMC,F_JLR,F_BZZT,F_JLSJ,F_ZJF;
            F_BZBH=F_BZMC=F_BMBH=F_BMMC=F_JLR=F_BZZT=F_JLSJ=F_ZJF="";
            int nID = 0;int nBMZT=0;float F_BZJE=0;
            F_BZBH = Rs.getString("F_BZBH") == null ? "" : Rs.getString("F_BZBH").trim();
            F_BZMC = Rs.getString("F_BZMC") == null ? "" : Rs.getString("F_BZMC").trim();
            F_BMBH = Rs.getString("F_BMBH") == null ? "" : Rs.getString("F_BMBH").trim();
            F_BMMC = Rs.getString("F_BMMC") == null ? "" : Rs.getString("F_BMMC").trim();
            F_ZJF = Rs.getString("F_ZJF") == null ? "" : Rs.getString("F_ZJF").trim();
            F_BZJE = Rs.getFloat("F_BZJE");
            F_JLR = Rs.getString("F_JLR") == null ? "" : Rs.getString("F_JLR").trim();
            F_JLSJ=Rs.getString("F_JLSJ")==null?"":Rs.getString("F_JLSJ").trim();
            if(F_JLSJ.compareTo("")==0||F_JLSJ.substring(0,10).compareTo("1900-01-01")==0)
                F_JLSJ="";
            else
                F_JLSJ=F_JLSJ.substring(0,10);
            nID=Rs.getInt("F_ID");
            nBMZT=Rs.getInt("F_BZZT");
            if(nBMZT==1)
                F_BZZT="<font color=0000FF>正常</font>";
            else
                F_BZZT="<font color=FF0000>冻结</font>";
            map1.put("XH", rec);
            map1.put("ID", nID);
            map1.put("F_BZBH", F_BZBH);
            map1.put("F_BZMC", F_BZMC);
            map1.put("F_BMMC", F_BMMC);
            map1.put("F_BZJE", F_BZJE);
            map1.put("F_JLR", F_JLR);
            map1.put("F_JLSJ", F_JLSJ);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_ZJF", F_ZJF);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getStandardProList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String DeptNo=userInfo.getLimit();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("STANDARD_PRO_LIST");
        SqlSel = SqlSel == null ? "" : SqlSel;
        SqlSel=" F_BMBH like '"+DeptNo+"%' ";
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_FF_JCXMB where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_FF_JCXMB"; Param[1]="F_ID"; Param[2]="F_ID DESC"; Param[3]=String.valueOf(curPage);
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
            String F_BZBH,F_BZMC,F_XMMC,F_BMBH,F_BMMC,F_JSYQ,F_ZJF,F_JLR,F_JLSJ,F_BZZT;
            F_BZBH=F_BZMC=F_XMMC=F_JSYQ=F_JLR=F_BZZT=F_ZJF="";
            int nZT=0;float F_XMJE=0;
            int nID = 0;int nBMZT=0;float F_BZJE=0;
            F_BZBH = Rs.getString("F_BZBH") == null ? "" : Rs.getString("F_BZBH").trim();
            F_BZMC = Rs.getString("F_BZMC") == null ? "" : Rs.getString("F_BZMC").trim();
            F_XMMC = Rs.getString("F_XMMC") == null ? "" : Rs.getString("F_XMMC").trim();
            F_JSYQ = Rs.getString("F_JSYQ") == null ? "" : Rs.getString("F_JSYQ").trim();
            F_ZJF = Rs.getString("F_ZJF") == null ? "" : Rs.getString("F_ZJF").trim();
            F_XMJE = Rs.getFloat("F_XMJE");
            F_JLR = Rs.getString("F_JLR") == null ? "" : Rs.getString("F_JLR").trim();
            F_JLSJ=Rs.getString("F_JLSJ")==null?"":Rs.getString("F_JLSJ").trim();
            if(F_JLSJ.compareTo("")==0||F_JLSJ.substring(0,10).compareTo("1900-01-01")==0)
                F_JLSJ="";
            else
                F_JLSJ=F_JLSJ.substring(0,10);
            nID=Rs.getInt("F_ID");
            nBMZT=Rs.getInt("F_BZZT");
            if(nBMZT==1)
                F_BZZT="<font color=0000FF>正常</font>";
            else
                F_BZZT="<font color=FF0000>冻结</font>";
            map1.put("XH", rec);
            map1.put("ID", nID);
            map1.put("F_BZBH", F_BZBH);
            map1.put("F_BZMC", F_BZMC);
            map1.put("F_JSYQ", F_JSYQ);
            map1.put("F_XMJE", F_XMJE);
            map1.put("F_JLR", F_JLR);
            map1.put("F_JLSJ", F_JLSJ);
            map1.put("F_XMMC", F_XMMC);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_ZJF", F_ZJF);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getStandardProEditList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String DeptNo=userInfo.getLimit();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("STANDARD_EDIT_LIST_PRO");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String mDWMC= request.getParameter("F_BZMC");
        mDWMC=mDWMC==null?"":mDWMC.trim();
        String mDWBH=request.getParameter("F_BZBH");
        mDWBH=mDWBH==null?"":mDWBH.trim();
        String mZJF=request.getParameter("F_ZJF");
        mZJF=mZJF==null?"":mZJF.trim();
        String sFlag ="";
        sFlag=mDWMC+mDWBH+mZJF;
        StringBuffer sb=new StringBuffer();
        if(mDWMC.compareTo("")!=0)
            sb.append(" and F_BZMC like '%"+mDWMC+"%' ");
        if(mDWBH.compareTo("")!=0)
            sb.append(" and F_BZBH like '%"+mDWBH+"%' ");
        if(mZJF.compareTo("")!=0)
            sb.append(" and F_ZJF like '%"+mZJF+"%' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("STANDARD_EDIT_LIST_PRO",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" F_BZZT=1 ";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("STANDARD_EDIT_LIST_PRO",SqlStr);
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_FF_JCBZ where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_FF_JCBZ"; Param[1]="F_ID"; Param[2]="F_ID DESC"; Param[3]=String.valueOf(curPage);
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
            String F_BZBH,F_BZMC,F_BMBH,F_BMMC,F_JLR,F_BZZT,F_JLSJ,F_ZJF;
            F_BZBH=F_BZMC=F_BMBH=F_BMMC=F_JLR=F_BZZT=F_JLSJ=F_ZJF="";
            int nID = 0;int nBMZT=0;float F_BZJE=0;
            F_BZBH = Rs.getString("F_BZBH") == null ? "" : Rs.getString("F_BZBH").trim();
            F_BZMC = Rs.getString("F_BZMC") == null ? "" : Rs.getString("F_BZMC").trim();
            F_BMBH = Rs.getString("F_BMBH") == null ? "" : Rs.getString("F_BMBH").trim();
            F_BMMC = Rs.getString("F_BMMC") == null ? "" : Rs.getString("F_BMMC").trim();
            F_ZJF = Rs.getString("F_ZJF") == null ? "" : Rs.getString("F_ZJF").trim();
            F_BZJE = Rs.getFloat("F_BZJE");
            F_JLR = Rs.getString("F_JLR") == null ? "" : Rs.getString("F_JLR").trim();
            F_JLSJ=Rs.getString("F_JLSJ")==null?"":Rs.getString("F_JLSJ").trim();
            if(F_JLSJ.compareTo("")==0||F_JLSJ.substring(0,10).compareTo("1900-01-01")==0)
                F_JLSJ="";
            else
                F_JLSJ=F_JLSJ.substring(0,10);
            nID=Rs.getInt("F_ID");
            nBMZT=Rs.getInt("F_BZZT");
            if(nBMZT==1)
                F_BZZT="<font color=0000FF>正常</font>";
            else
                F_BZZT="<font color=FF0000>冻结</font>";
            map1.put("XH", rec);
            map1.put("ID", nID);
            map1.put("F_BZBH", F_BZBH);
            map1.put("F_BZMC", F_BZMC);
            map1.put("F_BMMC", F_BMMC);
            map1.put("F_BZJE", F_BZJE);
            map1.put("F_JLR", F_JLR);
            map1.put("F_JLSJ", F_JLSJ);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_ZJF", F_ZJF);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getStandardProSelect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String DeptNo=userInfo.getLimit();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("STANDARD_SELECT_LIST_PRO");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String mDWMC= request.getParameter("F_BZMC");
        mDWMC=mDWMC==null?"":mDWMC.trim();
        String mDWBH=request.getParameter("F_BZBH");
        mDWBH=mDWBH==null?"":mDWBH.trim();
        String mZJF=request.getParameter("F_ZJF");
        mZJF=mZJF==null?"":mZJF.trim();
        String sFlag ="";
        sFlag=mDWMC+mDWBH+mZJF;
        StringBuffer sb=new StringBuffer();
        if(mDWMC.compareTo("")!=0)
            sb.append(" and F_BZMC like '%"+mDWMC+"%' ");
        if(mDWBH.compareTo("")!=0)
            sb.append(" and F_BZBH like '%"+mDWBH+"%' ");
        if(mZJF.compareTo("")!=0)
            sb.append(" and F_ZJF like '%"+mZJF+"%' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("STANDARD_SELECT_LIST_PRO",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" F_BZZT=1 ";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("STANDARD_SELECT_LIST_PRO",SqlStr);
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_FF_JCXMB where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_FF_JCXMB"; Param[1]="F_ID"; Param[2]="F_ID DESC"; Param[3]=String.valueOf(curPage);
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
            String F_BZBH,F_BZMC,F_XMMC,F_JSYQ,F_BZ,F_BZZT,F_XMBH;
            F_BZBH=F_BZMC=F_XMMC=F_JSYQ=F_BZ=F_BZZT=F_XMBH="";
            float F_XMJE=0;
            F_BZBH = Rs.getString("F_BZBH") == null ? "" : Rs.getString("F_BZBH").trim();
            F_BZMC = Rs.getString("F_BZMC") == null ? "" : Rs.getString("F_BZMC").trim();
            F_XMMC = Rs.getString("F_XMMC") == null ? "" : Rs.getString("F_XMMC").trim();
            F_XMBH = Rs.getString("F_XMBH") == null ? "" : Rs.getString("F_XMBH").trim();
            F_JSYQ = Rs.getString("F_JSYQ") == null ? "" : Rs.getString("F_JSYQ").trim();
            F_BZ = Rs.getString("F_BZ") == null ? "" : Rs.getString("F_BZ").trim();
            F_XMJE = Rs.getFloat("F_XMJE");
            int nID=Rs.getInt("F_ID");
            int nBMZT=Rs.getInt("F_BZZT");
            if(nBMZT==1)
                F_BZZT="<font color=0000FF>正常</font>";
            else
                F_BZZT="<font color=FF0000>冻结</font>";
            map1.put("XH", rec);
            map1.put("ID", nID);
            map1.put("F_BZBH", F_BZBH);
            map1.put("F_BZMC", F_BZMC);
            map1.put("F_XMMC", F_XMMC);
            map1.put("F_JSYQ", F_JSYQ);
            map1.put("F_BZ", F_BZ);
            map1.put("F_XMJE", F_XMJE);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_XMBH", F_XMBH);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getxmlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String DeptNo=userInfo.getLimit();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("STANDARD_PROSELECT");
        SqlSel = SqlSel == null ? "" : SqlSel;
        JdbcTemplate JdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        String mDWMC= request.getParameter("F_BZMC");
        mDWMC=mDWMC==null?"":mDWMC.trim();
        String mDWBH=request.getParameter("F_BZBH");
        mDWBH=mDWBH==null?"":mDWBH.trim();
        String mZJF=request.getParameter("F_ZJF");
        mZJF=mZJF==null?"":mZJF.trim();
        String sFlag ="";
        sFlag=mDWMC+mDWBH+mZJF;
        StringBuffer sb=new StringBuffer();
        if(mDWMC.compareTo("")!=0)
            sb.append(" and F_BZMC like '%"+mDWMC+"%' ");
        if(mDWBH.compareTo("")!=0)
            sb.append(" and F_BZBH like '%"+mDWBH+"%' ");
        if(mZJF.compareTo("")!=0)
            sb.append(" and F_ZJF like '%"+mZJF+"%' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("STANDARD_PROSELECT",SqlStr);
            SqlStr="select * from T_FF_JCXMB where "+SqlStr;
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" select * from T_FF_JCXMB where F_BZBH='"+mDWBH+"' ";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("STANDARD_PROSELECT",SqlStr);
                SqlSel=SqlStr;
            }
        }
        //SqlSel="select * from T_FF_JCXMB where "+SqlSel;
        //System.out.println("P: "+SqlSel);
        Rs=JdbcDB.exeQuery("fimisdb",SqlSel);
        List list=new ArrayList();
        int rec=0;
        while(Rs.next()) {
            HashMap map1=new HashMap();
            String F_BZBH,F_BZMC,F_XMMC,F_JSYQ,F_BZ,F_BZZT,F_XMBH;
            F_BZBH=F_BZMC=F_XMMC=F_JSYQ=F_BZ=F_BZZT=F_XMBH="";
            float F_XMJE=0;
            F_BZBH = Rs.getString("F_BZBH") == null ? "" : Rs.getString("F_BZBH").trim();
            F_BZMC = Rs.getString("F_BZMC") == null ? "" : Rs.getString("F_BZMC").trim();
            F_XMMC = Rs.getString("F_XMMC") == null ? "" : Rs.getString("F_XMMC").trim();
            F_XMBH = Rs.getString("F_XMBH") == null ? "" : Rs.getString("F_XMBH").trim();
            F_JSYQ = Rs.getString("F_JSYQ") == null ? "" : Rs.getString("F_JSYQ").trim();
            F_BZ = Rs.getString("F_BZ") == null ? "" : Rs.getString("F_BZ").trim();
            F_XMJE = Rs.getFloat("F_XMJE");
            int nID=Rs.getInt("F_ID");
            int nBMZT=Rs.getInt("F_BZZT");
            if(nBMZT==1)
                F_BZZT="<font color=0000FF>正常</font>";
            else
                F_BZZT="<font color=FF0000>冻结</font>";
            map1.put("F_BZBH", F_BZBH);
            map1.put("F_BZMC", F_BZMC);
            map1.put("F_XMMC", F_XMMC);
            map1.put("F_JSYQ", F_JSYQ);
            map1.put("F_BZ", F_BZ);
            map1.put("F_XMJE", F_XMJE);
            map1.put("F_BZZT", F_BZZT);
            map1.put("F_XMBH", F_XMBH);
            list.add(map1);
            rec++;
        }
        Gson gson=new Gson();
        Result=gson.toJson(list);
        return Result;
    }
        //System.out.println(SqlStr);
}
