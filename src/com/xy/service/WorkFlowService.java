package com.xy.service;

import com.google.gson.Gson;
import com.sun.rowset.CachedRowSetImpl;
import com.xy.model.WorkFlow;
import com.xy.system.db.JdbcTemplate;
import com.xy.user.base.UserInfo;
import com.xy.util.UtilFunction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2019/9/15.
 */
public class WorkFlowService {
    public String getWorkFlow(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String DeptNo=userInfo.getLimit();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("WORKFLOW_LIST");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String mF_WTNR= request.getParameter("F_WTNR");
        mF_WTNR=mF_WTNR==null?"":mF_WTNR.trim();
        String mF_WTBH=request.getParameter("F_WTBH");
        mF_WTBH=mF_WTBH==null?"":mF_WTBH.trim();
        String mF_WTDW=request.getParameter("F_WTDW");
        mF_WTDW=mF_WTDW==null?"":mF_WTDW.trim();
        String mSearch=Fun.StrToCN(request.getParameter("searchField"));
        mSearch=mSearch==null?"":mSearch.trim();
        String  KSSJ=Fun.StrToCN(request.getParameter("KSSJ"));
        KSSJ=KSSJ==null?"":KSSJ.trim();
        String  JZSJ=Fun.StrToCN(request.getParameter("JZSJ"));
        JZSJ=JZSJ==null?"":JZSJ.trim();
        String sFlag ="";
        sFlag=mF_WTNR+mF_WTBH+mF_WTDW+mSearch;
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
        String S_Time=NowTime.substring(0,10)+" 00:00:00";
        String E_Time=NowTime.substring(0,10)+" 23:59:59";
        StringBuffer sb=new StringBuffer();
        if(mF_WTNR.compareTo("")!=0)
            sb.append(" and F_WTNR like '%"+mF_WTNR+"%' ");
        if(mF_WTBH.compareTo("")!=0)
            sb.append(" and F_WTDH like '%"+mF_WTBH+"%' ");
        if(mF_WTDW.compareTo("")!=0)
            sb.append(" and F_WTDW like '%"+mF_WTDW+"%' ");
        if(mSearch.compareTo("")!=0)
        {
            if(KSSJ.compareTo("1900-01-01")!=0&&JZSJ.compareTo("1900-01-01")==0)
                sb.append(" and "+mSearch+" between '"+START_TIME+"' and '"+END_TIME+"' ");
            if(KSSJ.compareTo("1900-01-01")==0&&JZSJ.compareTo("1900-01-01")!=0)
                sb.append(" and "+mSearch+" between '"+START_TIME+"' and '"+END_TIME+"' ");
            if(KSSJ.compareTo("1900-01-01")!=0&&JZSJ.compareTo("1900-01-01")!=0)
                sb.append(" and "+mSearch+" between '"+START_TIME+"' and '"+END_TIME+"'  ");
        }
        if(!sFlag.equals("")){
            sb.delete(sb.indexOf("and"),sb.indexOf("and")+3);
            SqlStr=sb.toString();
            session.setAttribute("WORKFLOW_LIST",SqlStr);
            SqlSel=SqlStr;
        }else{
            if(SqlSel.equals("")){
                SqlStr=" F_JLSJ between '"+S_Time+"' and '"+E_Time+"'";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("WORKFLOW_LIST",SqlStr);
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

        Rs=JdbcDB.exeQuery("fimisdb","select Count(*) as CNT from T_JLJC_WORKFLOW where "+SqlSel+" ");
        int totalRows=0;
        if(Rs.next()){
            totalRows=Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_JLJC_WORKFLOW"; Param[1]="F_ID"; Param[2]="F_ID DESC"; Param[3]=String.valueOf(curPage);
        Param[4]=String.valueOf(pageSize);  Param[5]="*"; Param[6]=SqlSel; Param[7]="";
        //String SP_Pagination="{call SP_Pagination("+Param[0]+","+Param[1]+","+Param[2]+","+Param[3]+","+Param[4]+","+Param[5]+","+Param[6]+","+Param[7]+")}";
        //System.out.println(SP_Pagination);
        HashMap map=new HashMap();
        map.put("success",true);
        map.put("totalRows",totalRows);
        map.put("curPage",curPage);
        Rs=JdbcDB.callProcQuery("fimisdb",Param);
        CachedRowSet rs=new CachedRowSetImpl();
        rs=JdbcDB.callProcQuery("fimisdb",Param);
        List<WorkFlow> list1=JdbcDB.queryListObject(rs,WorkFlow.class);
        for (WorkFlow workFlows : list1) {
            System.out.println(workFlows);
        }

        List list=new ArrayList();
        int rec=0;
        rec=startRow;
        //Rs.absolute(endRow);
        int iRows=0;
        ResultSetMetaData rsmd = Rs.getMetaData();
        while(Rs.next()) {
            HashMap<String,Object> map1 = new HashMap<String,Object>();
            for(int i=1;i<=rsmd.getColumnCount();i++){
                String colname=rsmd.getColumnName(i);
                String Type=rsmd.getColumnTypeName(i);
                //System.out.println(Type);
                if(Type.compareTo("int")==0){

                    map1.put(colname,Rs.getInt(i));
                }
                else if(Type.compareTo("float")==0)
                    map1.put(colname,Rs.getFloat(i));
                else if(Type.compareTo("datetime")==0)
                {
                    String sdate="";
                    sdate=Rs.getString(i)==null?"":Rs.getString(i).trim();
                    if(sdate.compareTo("")==0||sdate.substring(0,10).compareTo("1900-01-01")==0){
                        sdate="";
                    }else{
                        sdate=sdate.substring(0,10);
                    }
                    map1.put(colname,sdate);
                }
                else if(Type.compareTo("varchar")==0||Type.compareTo("nchar")==0||Type.compareTo("char")==0){
                    String sdate="";
                    sdate=Rs.getString(i)==null?"":Rs.getString(i).trim();
                    map1.put(colname,sdate);
                }
            }
            /*String F_WTDH,F_WTDW,F_CPMC,F_CPXH,F_YPWG,F_SCDW,F_LXR,F_WTLXFS,F_JLSJ,F_CPBH,F_JLBH;
            F_WTDH=F_WTDW=F_CPMC=F_CPXH=F_YPWG=F_SCDW=F_LXR=F_WTLXFS=F_JLSJ=F_CPBH=F_JLBH="";
            int nID = 0;int nBMZT=0;float F_YPSL=0;
            F_WTDH = Rs.getString("F_WTDH") == null ? "" : Rs.getString("F_WTDH").trim();
            F_WTDW = Rs.getString("F_WTDW") == null ? "" : Rs.getString("F_WTDW").trim();
            F_CPMC = Rs.getString("F_CPMC") == null ? "" : Rs.getString("F_CPMC").trim();
            F_YPWG = Rs.getString("F_YPWG") == null ? "" : Rs.getString("F_YPWG").trim();
            F_CPXH = Rs.getString("F_CPXH") == null ? "" : Rs.getString("F_CPXH").trim();
            F_SCDW = Rs.getString("F_SCDW") == null ? "" : Rs.getString("F_SCDW").trim();
            F_CPBH = Rs.getString("F_CPBH") == null ? "" : Rs.getString("F_CPBH").trim();
            F_YPSL = Rs.getFloat("F_YPSL");
            F_LXR = Rs.getString("F_LXR") == null ? "" : Rs.getString("F_LXR").trim();
            F_WTLXFS = Rs.getString("F_WTLXFS") == null ? "" : Rs.getString("F_WTLXFS").trim();
            F_JLBH=Rs.getString("F_JLBH")==null?"":Rs.getString("F_JLBH").trim();
            F_JLSJ=Rs.getString("F_JLSJ")==null?"":Rs.getString("F_JLSJ").trim();
            if(F_JLSJ.compareTo("")==0||F_JLSJ.substring(0,10).compareTo("1900-01-01")==0)
                F_JLSJ="";
            else
                F_JLSJ=F_JLSJ.substring(0,10);
            nID=Rs.getInt("F_ID");
            map1.put("XH", rec);
            map1.put("ID", F_JLBH);
            map1.put("F_WTDH", F_WTDH);
            map1.put("F_WTDW", F_WTDW);
            map1.put("F_CPBH", F_CPBH);
            map1.put("F_CPMC", F_CPMC);
            map1.put("F_YPWG", F_YPWG);
            map1.put("F_CPXH", F_CPXH);
            map1.put("F_SCDW", F_SCDW);
            map1.put("F_YPSL", F_YPSL);
            map1.put("F_LXR", F_LXR);
            map1.put("F_WTLXFS", F_WTLXFS);
            map1.put("F_JLSJ", F_JLSJ);*/
            map1.put("XH", rec);
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
