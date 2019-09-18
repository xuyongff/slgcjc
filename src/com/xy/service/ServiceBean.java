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
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-3-8
 * Time: 下午9:51
 * To change this template use File | Settings | File Templates.
 */
public class ServiceBean {

    public String getWorkInfo(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String dbsource="fimisdb";
        String Result="";
        UtilFunction Fun=new UtilFunction();
        UserInfo userInfo=new UserInfo();
        HttpSession session=((HttpServletRequest)request).getSession();
        userInfo=(UserInfo)session.getAttribute("UserInfo");
        String YHBH=userInfo.getLoginName();
        String SqlStr="";
        String SqlSel=(String)session.getAttribute("WELL_LIST_QUERY_SQL");
        SqlSel=SqlSel==null?"":SqlSel;
        String sCondition = "";
        String Dept=userInfo.getDepartment();
        String SSDW=userInfo.getPosition();
        if(userInfo.getLimit().length()==2||userInfo.getLimit().indexOf("0107")>=0)
            Dept="公司";
        else
            Dept=SSDW.substring(SSDW.indexOf(",")+1,SSDW.length());
        String mJH=request.getParameter("JH");
        mJH=mJH==null?"":mJH.trim();
        String mWCQK=request.getParameter("WCQK");
        mWCQK=mWCQK==null?"":mWCQK.trim();
        String mSGXD=request.getParameter("SGDH");
        mSGXD=mSGXD==null?"":mSGXD.trim();
        String mSGDW=request.getParameter("DWMC");
        mSGDW=mSGDW==null?"":mSGDW.trim();
        String KSSJ=Fun.StrToCN(request.getParameter("KSSJ"));
        KSSJ=KSSJ==null?"":KSSJ.trim();
        String JZSJ=Fun.StrToCN(request.getParameter("JZSJ"));
        JZSJ=JZSJ==null?"":JZSJ.trim();
        String mSearch=Fun.StrToCN(request.getParameter("searchField"));
        mSearch=mSearch==null?"":mSearch.trim();
        String sFlag ="";
        sFlag=mJH+mWCQK+mSGXD+mSGDW+KSSJ+JZSJ+mSearch;
        StringBuffer sb=new StringBuffer();

        if(KSSJ.compareTo("")==0)
            KSSJ="1900-01-01";
        else
            KSSJ=KSSJ;
        KSSJ=KSSJ.substring(0,10);

        if(JZSJ.compareTo("")==0)
            JZSJ="1900-01-01";
        else
            JZSJ=JZSJ;
        JZSJ=JZSJ.substring(0,10);
        String START_TIME,END_TIME;
        if(KSSJ.compareTo("1900-01-01")!=0&&JZSJ.compareTo("1900-01-01")==0)
        {
            START_TIME=KSSJ+" 00:00:00";
            END_TIME=KSSJ+" 23:59:59";
        }
        else if(KSSJ.compareTo("1900-01-01")==0&&JZSJ.compareTo("1900-01-01")!=0)
        {
            START_TIME=JZSJ+" 00:00:00";
            END_TIME=JZSJ+" 23:59:59";
        }
        else if(KSSJ.compareTo("1900-01-01")!=0&&JZSJ.compareTo("1900-01-01")!=0)
        {
            START_TIME=KSSJ+" 00:00:00";
            END_TIME=JZSJ+" 23:59:59";
        }
        else
        {
            START_TIME=KSSJ;
            END_TIME=JZSJ;
        }
        java.util.Date Now=new java.util.Date();
        String NowTime=Fun.getFormatTime(Now,"yyyy-MM-dd");
        String S_Time=NowTime+" 00:00:00";
        String E_Time=NowTime+" 23:59:59";
        if(mJH.compareTo("")!=0)
            sb.append(" and JH like '%"+mJH+"%' ");
        if(mWCQK.compareTo("")!=0)
            sb.append(" and WCQK like '%"+mWCQK+"%' ");
        if(mSGXD.compareTo("")!=0)
            sb.append(" and DH ='"+mSGXD+"'  ");
        if(mSGDW.compareTo("")!=0){
            mSGDW=mSGDW.substring(mSGDW.indexOf(",")+1,mSGDW.length());
            sb.append(" and FGS like '%"+mSGDW+"%' ");
        }
        if(mSearch.compareTo("")!=0)
        {
            if(KSSJ.compareTo("1900-01-01")!=0&&JZSJ.compareTo("1900-01-01")==0)
                sb.append(" and "+mSearch+" between '"+START_TIME+"' and '"+END_TIME+"' ");
            if(KSSJ.compareTo("1900-01-01")==0&&JZSJ.compareTo("1900-01-01")!=0)
                sb.append(" and "+mSearch+" between '"+START_TIME+"' and '"+END_TIME+"' ");
            if(KSSJ.compareTo("1900-01-01")!=0&&JZSJ.compareTo("1900-01-01")!=0)
                sb.append(" and "+mSearch+" between '"+START_TIME+"' and '"+END_TIME+"' ");
        }

        if(!sFlag.equals("")){
            sb.append(" and FGS like '%"+Dept+"%' ");
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("WELL_LIST_QUERY_SQL",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" FGS like '%"+Dept+"%'";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("WELL_LIST_QUERY_SQL",SqlStr);
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from logdbwork where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        //String sql="select top "+pageSize+" * from (SELECT TOP "+k+" * FROM logdbwork where "+SqlSel+" order by KSSJ desc ) a  order by a.KSSJ asc";
       // String sql="select * from logdbwork where "+SqlSel+" ";
        String [] Param=new String[8];
        Param[0]="logdbwork"; Param[1]="ID"; Param[2]="Id asc"; Param[3]=String.valueOf(curPage);
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
        while(Rs.next()){
           HashMap map1=new HashMap();
            String JFDW,JH,JX,XMMC,SGXD,SGRQ,SGLX,SGDW,SGMC,JSSJ,JSZT,F_FHSJ,F_CFSJ,SSFS,WCQK,YTQK,SJRY,TSXM;
            JFDW=JH=JX=XMMC=SGXD=SGRQ=SGLX=SGDW=SGMC=JSSJ=JSZT=F_FHSJ=F_CFSJ=SSFS=WCQK=YTQK=SJRY=TSXM="";
            float YFJE,JSJE;
            float JS1,JS2,JS3,JS4,JS5,JS;
            JS1=JS2=JS3=JS4=JS5=JS=0;
            int nCS=Rs.getInt("CS");
            SGDW=Rs.getString("FGS")==null?"":Rs.getString("FGS").trim();
            JH=Rs.getString("JH")==null?"":Rs.getString("JH").trim();
            SGXD=Rs.getString("DH")==null?"":Rs.getString("DH").trim();
            SGLX=Rs.getString("SGLB")==null?"":Rs.getString("SGLB").trim();
            SGMC=Rs.getString("SGMC")==null?"":Rs.getString("SGMC").trim();
            SGRQ=Rs.getString("KSSJ")==null?"":Rs.getString("KSSJ").trim();
            JSSJ=Rs.getString("JSSJ")==null?"":Rs.getString("JSSJ").trim();
            F_FHSJ=Rs.getString("FHSJ")==null?"":Rs.getString("FHSJ").trim();
            F_CFSJ=Rs.getString("CFSJ")==null?"":Rs.getString("CFSJ").trim();
            SSFS=Rs.getString("SSFS")==null?"":Rs.getString("SSFS").trim();
            JS1=Rs.getFloat("LYJS");
            JS2=Rs.getFloat("GJJS");
            JS3=Rs.getFloat("TGJS");
            JS4=Rs.getFloat("QXJS");
            JS5=Rs.getFloat("SKJS");
            JS=JS1+JS2+JS3+JS4+JS5;
            WCQK=Rs.getString("WCQK")==null?"":Rs.getString("WCQK").trim();
            YTQK=Rs.getString("YQT")==null?"":Rs.getString("YQT").trim();
            XMMC=Rs.getString("XMMC")==null?"":Rs.getString("XMMC").trim();
            SJRY=Rs.getString("SJRY")==null?"":Rs.getString("SJRY").trim();
            if(F_FHSJ.length()>0)
                F_FHSJ=F_FHSJ.substring(0,10);
            else
                F_FHSJ="";
            if(F_CFSJ.length()>0)
                F_CFSJ=F_CFSJ.substring(0,10);
            else
                F_CFSJ="";
            if(F_CFSJ.compareTo("")==0||F_CFSJ.compareTo("1900-01-01")==0)
                F_CFSJ=SGRQ.substring(0,10);
            else
                F_CFSJ=F_CFSJ;
            if(F_FHSJ.compareTo("")==0||F_FHSJ.compareTo("1900-01-01")==0)
                F_FHSJ=SGRQ.substring(0,10);
            else
                F_FHSJ=F_FHSJ;
            if(XMMC.indexOf("中子")>0&&SGLX.indexOf("裸眼")>=0)
                SGMC="完井加中子密度";
            if(XMMC.indexOf("中子")>0&&SGLX.indexOf("固井")>=0)
                SGMC="固井加放射性";
            if(XMMC.indexOf("多极子")>0||XMMC.indexOf("多级子")>0)
                TSXM="XMAC";
            if(XMMC.indexOf("核磁")>0)
                TSXM+=",核磁";
            if(XMMC.indexOf("倾角")>0)
                TSXM+=",倾角";
            if(XMMC.indexOf("成像")>0)
                TSXM+=",成像";
           map1.put("XH",rec);
            map1.put("JH",JH);
           map1.put("FGS",SGDW);
           map1.put("SSFS",SSFS);
           map1.put("SGXD",SGXD);
           map1.put("SGLX",SGLX);
           map1.put("SGMC",SGMC);
           map1.put("SGRQ",SGRQ);
           map1.put("JSSJ",JSSJ);
           map1.put("F_FHSJ",F_FHSJ);
           map1.put("F_CFSJ",F_CFSJ);
           map1.put("JS",JS);
           map1.put("WCQK",WCQK);
           map1.put("YTQK",YTQK);
           map1.put("SJRY",SJRY);
            map1.put("CS",nCS);
           list.add(map1);
           rec++;
           iRows++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
    public String getEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String dbsource="fimisdb";
        String Result="";
        UtilFunction Fun=new UtilFunction();
        UserInfo userInfo=new UserInfo();
        HttpSession session=((HttpServletRequest)request).getSession();
        userInfo=(UserInfo)session.getAttribute("UserInfo");
        String YHBH=userInfo.getLoginName();
        String SqlStr="";
        String SqlSel=(String)session.getAttribute("EMPLOYEE_LIST_QUERY");
        SqlSel=SqlSel==null?"":SqlSel;
        String sCondition = "";
        String Dept=userInfo.getLimit();
        String sFlag ="";
        String searchField=request.getParameter("searchField");
        searchField=searchField==null?"":searchField.trim();
        String searchKey=request.getParameter("searchKey");
        searchKey=searchKey==null?"":searchKey.trim();
        String mDWBH=request.getParameter("DWBH");
        mDWBH=mDWBH==null?"":mDWBH.trim();
        StringBuffer sb=new StringBuffer();
        if(searchField.compareTo("")!=0&&searchKey.compareTo("")!=0)
            sb.append(" and "+searchField+" like '%"+searchKey+"%' ");
        if(mDWBH.compareTo("")!=0)
            sb.append(" and F_DWBH like '"+mDWBH+"%' ");
        sFlag=searchField+searchKey+mDWBH;
        //System.out.println("DWBH: "+mDWBH);
        if(!sFlag.equals("")){
            sb.append(" and F_DWBH like '"+Dept+"%' ");
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("EMPLOYEE_LIST_QUERY",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" F_DWBH like '"+Dept+"%'";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("EMPLOYEE_LIST_QUERY",SqlStr);
                SqlSel=SqlStr;
            }
        }

        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int curPage = Integer.parseInt(request.getParameter("curPage"));
        final String sortName = request.getParameter("sortName");
        final String sortOrder = request.getParameter("sortOrder");
        int startRow = pageSize * (curPage - 1) + 1;
        int endRow = startRow + pageSize - 1;
        int k=startRow+pageSize;
        JdbcTemplate JdbcDB=new JdbcTemplate();
        CachedRowSet Rs=new CachedRowSetImpl();
        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_WLYGZD where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();
        if(k>totalRows)
            pageSize=totalRows%pageSize;
        k = (k > totalRows) ? totalRows : k;
        // if pageSize == 0, then return all
        if (endRow > totalRows || pageSize == 0) {
            endRow = totalRows;
        }
        String sql="select top "+pageSize+" * from (SELECT TOP "+k+" * FROM T_WLYGZD where "+SqlSel+" order by F_DWBH desc ) a  order by a.F_DWBH asc";
        //System.out.println(sql);
        HashMap map=new HashMap();
        map.put("success",true);
        map.put("totalRows",totalRows);
        map.put("curPage",curPage);
        Rs=JdbcDB.exeQuery("fimisdb",sql);
        List list=new ArrayList();
        int rec=0;
        rec=startRow;
        while (Rs.next()){
            HashMap map1=new HashMap();
            String DWMC,DWBH,YGMC,SFZH,ZGLX,ZGZT;
            DWMC=DWBH=YGMC=SFZH=ZGLX=ZGZT="";
            int ZT=0;
            int ID=0;
            DWMC=Rs.getString("F_DWMC").trim();
            DWBH=Rs.getString("F_DWBH")==null?"":Rs.getString("F_DWBH").trim();
            YGMC=Rs.getString("F_YGMC")==null?"":Rs.getString("F_YGMC").trim();
            SFZH=Rs.getString("F_SFZH")==null?"":Rs.getString("F_SFZH").trim();
            ZGLX=Rs.getString("F_ZGLX")==null?"":Rs.getString("F_ZGLX").trim();
            ZT=Rs.getInt("F_ZGZT");
            if(ZGLX.compareTo("ZS")==0)
                ZGLX="正式工";
            else
                ZGLX="劳务工";
            if(ZT==1)
                ZGZT="<font color=#3366FF>正常</font>";
            else
                ZGZT="<font color=#FF0000>冻结</font>";
            ID=Rs.getInt("ID");
            map1.put("XH",rec);
            map1.put("YGMC",YGMC);
            map1.put("SFZH",SFZH);
            map1.put("ZGLX",ZGLX);
            map1.put("DWBH",DWBH);
            map1.put("DWMC",DWMC);
            map1.put("ZGZT",ZGZT);
            list.add(map1);
            rec++;
        }
        map.put("data",list);
        Gson gson=new Gson();
        Result=gson.toJson(map);
        return Result;
    }
}
