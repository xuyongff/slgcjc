package com.xy.service;

/**
 * Created by Administrator on 2019/8/29.
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
public class CustomerService {
    public String getCustomerList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("CUSTOMER_LIST");
        SqlSel = SqlSel == null ? "" : SqlSel;
        SqlSel=" F_ID>0";
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_WL_WLDW where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_WL_WLDW"; Param[1]="F_ID"; Param[2]="F_ID DESC"; Param[3]=String.valueOf(curPage);
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
            String F_DWBH,F_DWMC,F_DWBM,F_DWJC,F_DWQY,F_DWHY,F_LXR,F_GDDH,F_DWQZ,F_YZBM,F_YDDH,F_Email,F_DWDZ,F_KHYH,F_YHZH,F_DWSH,F_KPDZ,F_DWXZ,F_ZT,F_KH,F_GY;
            F_DWBH=F_DWMC=F_DWBM=F_DWJC=F_DWQY=F_DWHY=F_LXR=F_GDDH=F_DWQZ=F_YZBM=F_YDDH=F_Email=F_DWDZ=F_KHYH=F_YHZH=F_DWSH=F_KPDZ=F_DWXZ=F_ZT=F_KH=F_GY="";
            int F_KHBZ,F_GYSBZ,F_DWZT;
            F_KHBZ=F_GYSBZ=F_DWZT=0;
            int nID = 0;int nBMZT=0;
            F_DWBH=Rs.getString("F_DWBH")==null?"":Rs.getString("F_DWBH").trim();
            F_DWBM=Rs.getString("F_DWBM")==null?"":Rs.getString("F_DWBM").trim();
            F_DWMC=Rs.getString("F_DWMC")==null?"":Rs.getString("F_DWMC").trim();
            F_DWJC=Rs.getString("F_DWJC")==null?"":Rs.getString("F_DWJC").trim();
            F_DWQY=Rs.getString("F_DWQY")==null?"":Rs.getString("F_DWQY").trim();
            F_DWHY=Rs.getString("F_DWHY")==null?"":Rs.getString("F_DWHY").trim();
            F_LXR=Rs.getString("F_LXR")==null?"":Rs.getString("F_LXR").trim();
            F_GDDH=Rs.getString("F_GDDH")==null?"":Rs.getString("F_GDDH").trim();
            F_DWQZ=Rs.getString("F_DWQZ")==null?"":Rs.getString("F_DWQZ").trim();
            F_YDDH=Rs.getString("F_YDDH")==null?"":Rs.getString("F_YDDH").trim();
            F_Email=Rs.getString("F_Email")==null?"":Rs.getString("F_Email").trim();
            F_DWDZ=Rs.getString("F_DWDZ")==null?"":Rs.getString("F_DWDZ").trim();
            F_KHYH=Rs.getString("F_KHYH")==null?"":Rs.getString("F_KHYH").trim();
            F_YHZH=Rs.getString("F_YHZH")==null?"":Rs.getString("F_YHZH").trim();
            F_DWSH=Rs.getString("F_DWSH")==null?"":Rs.getString("F_DWSH").trim();
            F_KPDZ=Rs.getString("F_KPDZ")==null?"":Rs.getString("F_KPDZ").trim();
            F_DWXZ=Rs.getString("F_DWXZ")==null?"":Rs.getString("F_DWXZ").trim();
            F_KHBZ=Rs.getInt("F_KHBZ");
            F_GYSBZ=Rs.getInt("F_GYSBZ");
            F_DWZT=Rs.getInt("F_DWZT");
            nID=Rs.getInt("F_ID");
            if(F_DWZT==0)
                F_ZT="<font color=0000FF>正常</font>";
            else
                F_ZT="<font color=FF0000>冻结</font>";
            if(F_KHBZ==0)
                F_KH="<font color=0000FF>是</font>";
            else
                F_KH="<font color=FF0000>否</font>";
            if(F_GYSBZ==0)
                F_GY="<font color=0000FF>是</font>";
            else
                F_GY="<font color=FF0000>否</font>";
            map1.put("XH", rec);map1.put("ID", nID);map1.put("F_DWBH", F_DWBH);map1.put("F_DWBM", F_DWBM);map1.put("F_DWMC", F_DWMC);map1.put("F_DWJC", F_DWJC);
            map1.put("F_DWQY", F_DWQY);map1.put("F_DWHY", F_DWHY);map1.put("F_LXR", F_LXR);map1.put("F_GDDH", F_GDDH);
            map1.put("F_DWQZ", F_DWQZ);map1.put("F_YDDH", F_YDDH);map1.put("F_Email", F_Email);map1.put("F_DWDZ", F_DWDZ);
            map1.put("F_KHYH", F_KHYH);map1.put("F_YHZH", F_YHZH);map1.put("F_DWSH", F_DWSH);map1.put("F_KPDZ", F_KPDZ);map1.put("F_DWXZ", F_DWXZ);
            map1.put("F_KH", F_KHBZ);map1.put("F_GYSBZ", F_GY);map1.put("F_DWZT", F_ZT);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getCustomerEditList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("CUSTOMER_EDIT_LIST");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String mDWMC= request.getParameter("F_DWMC");
        mDWMC=mDWMC==null?"":mDWMC.trim();
        String mDWBH=request.getParameter("F_DWBH");
        mDWBH=mDWBH==null?"":mDWBH.trim();
        String mZJF=request.getParameter("F_ZJF");
        mZJF=mZJF==null?"":mZJF.trim();
        String sFlag ="";
        sFlag=mDWMC+mDWBH+mZJF;
        StringBuffer sb=new StringBuffer();
        if(mDWMC.compareTo("")!=0)
            sb.append(" and F_DWMC like '%"+mDWMC+"%' ");
        if(mDWBH.compareTo("")!=0)
            sb.append(" and F_DWBH like '%"+mDWBH+"%' ");
        if(mZJF.compareTo("")!=0)
            sb.append(" and F_ZJF like '%"+mZJF+"%' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("CUSTOMER_EDIT_LIST",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" F_DWZT=0 ";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("CUSTOMER_EDIT_LIST",SqlStr);
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_WL_WLDW where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_WL_WLDW"; Param[1]="F_ID"; Param[2]="F_ID DESC"; Param[3]=String.valueOf(curPage);
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
            String F_DWBH,F_DWMC,F_DWBM,F_DWJC,F_DWQY,F_DWHY,F_LXR,F_GDDH,F_DWQZ,F_YZBM,F_YDDH,F_Email,F_DWDZ,F_KHYH,F_YHZH,F_DWSH,F_KPDZ,F_DWXZ,F_ZT,F_KH,F_GY;
            F_DWBH=F_DWMC=F_DWBM=F_DWJC=F_DWQY=F_DWHY=F_LXR=F_GDDH=F_DWQZ=F_YZBM=F_YDDH=F_Email=F_DWDZ=F_KHYH=F_YHZH=F_DWSH=F_KPDZ=F_DWXZ=F_ZT=F_KH=F_GY="";
            int F_KHBZ,F_GYSBZ,F_DWZT;
            F_KHBZ=F_GYSBZ=F_DWZT=0;
            int nID = 0;int nBMZT=0;
            F_DWBH=Rs.getString("F_DWBH")==null?"":Rs.getString("F_DWBH").trim();
            F_DWBM=Rs.getString("F_DWBM")==null?"":Rs.getString("F_DWBM").trim();
            F_DWMC=Rs.getString("F_DWMC")==null?"":Rs.getString("F_DWMC").trim();
            F_DWJC=Rs.getString("F_DWJC")==null?"":Rs.getString("F_DWJC").trim();
            F_DWQY=Rs.getString("F_DWQY")==null?"":Rs.getString("F_DWQY").trim();
            F_DWHY=Rs.getString("F_DWHY")==null?"":Rs.getString("F_DWHY").trim();
            F_LXR=Rs.getString("F_LXR")==null?"":Rs.getString("F_LXR").trim();
            F_GDDH=Rs.getString("F_GDDH")==null?"":Rs.getString("F_GDDH").trim();
            F_DWQZ=Rs.getString("F_DWQZ")==null?"":Rs.getString("F_DWQZ").trim();
            F_YDDH=Rs.getString("F_YDDH")==null?"":Rs.getString("F_YDDH").trim();
            F_Email=Rs.getString("F_Email")==null?"":Rs.getString("F_Email").trim();
            F_DWDZ=Rs.getString("F_DWDZ")==null?"":Rs.getString("F_DWDZ").trim();
            F_KHYH=Rs.getString("F_KHYH")==null?"":Rs.getString("F_KHYH").trim();
            F_YHZH=Rs.getString("F_YHZH")==null?"":Rs.getString("F_YHZH").trim();
            F_DWSH=Rs.getString("F_DWSH")==null?"":Rs.getString("F_DWSH").trim();
            F_KPDZ=Rs.getString("F_KPDZ")==null?"":Rs.getString("F_KPDZ").trim();
            F_DWXZ=Rs.getString("F_DWXZ")==null?"":Rs.getString("F_DWXZ").trim();
            F_KHBZ=Rs.getInt("F_KHBZ");
            F_GYSBZ=Rs.getInt("F_GYSBZ");
            F_DWZT=Rs.getInt("F_DWZT");
            nID=Rs.getInt("F_ID");
            if(F_DWZT==0)
                F_ZT="<font color=0000FF>正常</font>";
            else
                F_ZT="<font color=FF0000>冻结</font>";
            if(F_KHBZ==0)
                F_KH="<font color=0000FF>是</font>";
            else
                F_KH="<font color=FF0000>否</font>";
            if(F_GYSBZ==0)
                F_GY="<font color=0000FF>是</font>";
            else
                F_GY="<font color=FF0000>否</font>";
            map1.put("XH", rec);map1.put("ID", nID);map1.put("F_DWBH", F_DWBH);map1.put("F_DWBM", F_DWBM);map1.put("F_DWMC", F_DWMC);map1.put("F_DWJC", F_DWJC);
            map1.put("F_DWQY", F_DWQY);map1.put("F_DWHY", F_DWHY);map1.put("F_LXR", F_LXR);map1.put("F_GDDH", F_GDDH);
            map1.put("F_DWQZ", F_DWQZ);map1.put("F_YDDH", F_YDDH);map1.put("F_Email", F_Email);map1.put("F_DWDZ", F_DWDZ);
            map1.put("F_KHYH", F_KHYH);map1.put("F_YHZH", F_YHZH);map1.put("F_DWSH", F_DWSH);map1.put("F_KPDZ", F_KPDZ);map1.put("F_DWXZ", F_DWXZ);
            map1.put("F_KH", F_KHBZ);map1.put("F_GYSBZ", F_GY);map1.put("F_DWZT", F_ZT);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getCustomerselect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("CUSTOMER_SELECT");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String mDWMC= request.getParameter("F_DWMC");
        mDWMC=mDWMC==null?"":mDWMC.trim();
        String mDWBH=request.getParameter("F_DWBH");
        mDWBH=mDWBH==null?"":mDWBH.trim();
        String mZJF=request.getParameter("F_ZJF");
        mZJF=mZJF==null?"":mZJF.trim();
        String sFlag ="";
        sFlag=mDWMC+mDWBH+mZJF;
        StringBuffer sb=new StringBuffer();
        if(mDWMC.compareTo("")!=0)
            sb.append(" and F_DWMC like '%"+mDWMC+"%' ");
        if(mDWBH.compareTo("")!=0)
            sb.append(" and F_DWBH like '%"+mDWBH+"%' ");
        if(mZJF.compareTo("")!=0)
            sb.append(" and F_ZJF like '%"+mZJF+"%' ");
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("CUSTOMER_SELECT",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" F_DWZT=0 ";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("CUSTOMER_SELECT",SqlStr);
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_WL_WLDW where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_WL_WLDW"; Param[1]="F_ID"; Param[2]="F_ID DESC"; Param[3]=String.valueOf(curPage);
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
            String F_DWBH,F_DWMC,F_DWBM,F_DWJC,F_DWQY,F_DWHY,F_LXR,F_GDDH,F_DWQZ,F_YZBM,F_YDDH,F_Email,F_DWDZ,F_KHYH,F_YHZH,F_DWSH,F_KPDZ,F_DWXZ,F_ZT,F_KH,F_GY;
            F_DWBH=F_DWMC=F_DWBM=F_DWJC=F_DWQY=F_DWHY=F_LXR=F_GDDH=F_DWQZ=F_YZBM=F_YDDH=F_Email=F_DWDZ=F_KHYH=F_YHZH=F_DWSH=F_KPDZ=F_DWXZ=F_ZT=F_KH=F_GY="";
            int F_KHBZ,F_GYSBZ,F_DWZT;
            F_KHBZ=F_GYSBZ=F_DWZT=0;
            int nID = 0;int nBMZT=0;
            F_DWBH=Rs.getString("F_DWBH")==null?"":Rs.getString("F_DWBH").trim();
            F_DWBM=Rs.getString("F_DWBM")==null?"":Rs.getString("F_DWBM").trim();
            F_DWMC=Rs.getString("F_DWMC")==null?"":Rs.getString("F_DWMC").trim();
            F_DWJC=Rs.getString("F_DWJC")==null?"":Rs.getString("F_DWJC").trim();
            F_DWQY=Rs.getString("F_DWQY")==null?"":Rs.getString("F_DWQY").trim();
            F_DWHY=Rs.getString("F_DWHY")==null?"":Rs.getString("F_DWHY").trim();
            F_LXR=Rs.getString("F_LXR")==null?"":Rs.getString("F_LXR").trim();
            F_GDDH=Rs.getString("F_GDDH")==null?"":Rs.getString("F_GDDH").trim();
            F_DWQZ=Rs.getString("F_DWQZ")==null?"":Rs.getString("F_DWQZ").trim();
            F_YDDH=Rs.getString("F_YDDH")==null?"":Rs.getString("F_YDDH").trim();
            F_Email=Rs.getString("F_Email")==null?"":Rs.getString("F_Email").trim();
            F_DWDZ=Rs.getString("F_DWDZ")==null?"":Rs.getString("F_DWDZ").trim();
            F_KHYH=Rs.getString("F_KHYH")==null?"":Rs.getString("F_KHYH").trim();
            F_YHZH=Rs.getString("F_YHZH")==null?"":Rs.getString("F_YHZH").trim();
            F_DWSH=Rs.getString("F_DWSH")==null?"":Rs.getString("F_DWSH").trim();
            F_KPDZ=Rs.getString("F_KPDZ")==null?"":Rs.getString("F_KPDZ").trim();
            F_DWXZ=Rs.getString("F_DWXZ")==null?"":Rs.getString("F_DWXZ").trim();
            F_KHBZ=Rs.getInt("F_KHBZ");
            F_GYSBZ=Rs.getInt("F_GYSBZ");
            F_DWZT=Rs.getInt("F_DWZT");
            nID=Rs.getInt("F_ID");
            if(F_DWZT==0)
                F_ZT="<font color=0000FF>正常</font>";
            else
                F_ZT="<font color=FF0000>冻结</font>";
            if(F_KHBZ==0)
                F_KH="<font color=0000FF>是</font>";
            else
                F_KH="<font color=FF0000>否</font>";
            if(F_GYSBZ==0)
                F_GY="<font color=0000FF>是</font>";
            else
                F_GY="<font color=FF0000>否</font>";
            map1.put("XH", rec);map1.put("ID", nID);map1.put("F_DWBH", F_DWBH);map1.put("F_DWBM", F_DWBM);map1.put("F_DWMC", F_DWMC);map1.put("F_DWJC", F_DWJC);
            map1.put("F_DWQY", F_DWQY);map1.put("F_DWHY", F_DWHY);map1.put("F_LXR", F_LXR);map1.put("F_GDDH", F_GDDH);
            map1.put("F_DWQZ", F_DWQZ);map1.put("F_YDDH", F_YDDH);map1.put("F_Email", F_Email);map1.put("F_DWDZ", F_DWDZ);
            map1.put("F_KHYH", F_KHYH);map1.put("F_YHZH", F_YHZH);map1.put("F_DWSH", F_DWSH);map1.put("F_KPDZ", F_KPDZ);map1.put("F_DWXZ", F_DWXZ);
            map1.put("F_KH", F_KHBZ);map1.put("F_GYSBZ", F_GY);map1.put("F_DWZT", F_ZT);
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
