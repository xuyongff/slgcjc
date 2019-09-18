package com.xy.service;

/**
 * Created by Administrator on 2019/8/28.
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
public class DeptService {

    public String getDepartmentList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String dbsource = "fimisdb";
        String Result = "";
        UtilFunction Fun = new UtilFunction();
        UserInfo userInfo = new UserInfo();
        HttpSession session = ((HttpServletRequest) request).getSession();
        userInfo = (UserInfo) session.getAttribute("UserInfo");
        String YHBH = userInfo.getLoginName();
        String DeptNo=userInfo.getLimit();
        String SqlStr = "";
        String SqlSel = (String) session.getAttribute("DEPT_LIST_SYS");
        SqlSel = SqlSel == null ? "" : SqlSel;
        String sCondition = "";
        String ParentID="";
        String sID=request.getParameter("ParentID");
        if(sID==null||sID.compareTo("")==0)
        {
            sID=(String)session.getAttribute("DEPT_PID");
        }
        else{
            session.setAttribute("DEPT_PID",sID);
        }
        ParentID=sID;
        String Dept = userInfo.getDepartment();
        String SSDW = userInfo.getPosition();
        String sFlag = "";
        sFlag =ParentID;
        StringBuffer sb = new StringBuffer();
        if (sID.compareTo("") != 0)
            sb.append(" and F_DWBH like '%" + ParentID + "%' ");
        if (!sFlag.equals("")) {
            sb.delete(sb.indexOf("and"), sb.indexOf("and") + 3);
            SqlStr = sb.toString();
            session.setAttribute("DEPT_LIST_SYS", SqlStr);
            SqlSel = SqlStr;
        } else {
            if (SqlSel.equals("")) {
                SqlStr = " F_DWBH like '%" + DeptNo + "%'";//"KSSJ between '"+S_Time+"' and '"+E_Time+"' and FGS like '%"+Dept+"%' ";
                session.setAttribute("DEPT_LIST_SYS", SqlStr);
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

        Rs = JdbcDB.exeQuery("fimisdb", "select Count(*) as CNT from T_SYS_DEPT where " + SqlSel + " ");
        int totalRows = 0;
        if (Rs.next()) {
            totalRows = Rs.getInt("CNT");
        }
        Rs.close();

        String [] Param=new String[8];
        Param[0]="T_SYS_DEPT"; Param[1]="F_ID"; Param[2]="F_ID asc"; Param[3]=String.valueOf(curPage);
        Param[4]=String.valueOf(pageSize);  Param[5]="*"; Param[6]=SqlSel; Param[7]="";
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
            String DWMC,DWBH,DWJC,SFMX,DWBZ,LKR;
            DWMC=DWBH=DWJC=SFMX=DWBZ=LKR="";
            int JS=0;
            int ID=0;
            int FMX=0;
            DWMC=Rs.getString("F_DWMC").trim();
            DWBH=Rs.getString("F_DWBH")==null?"":Rs.getString("F_DWBH").trim();
            DWJC=Rs.getString("F_DWJC")==null?"":Rs.getString("F_DWJC").trim();
            DWBZ=Rs.getString("F_BMBZ")==null?"":Rs.getString("F_BMBZ").trim();
            JS=Rs.getInt("F_JS");
            FMX=Rs.getInt("F_MX");
            ID=Rs.getInt("F_ID");
            map1.put("XH", rec);
            map1.put("ID", ID);
            map1.put("DWBH", DWBH);
            map1.put("DWMC", DWMC);
            map1.put("DWJC", DWJC);
            map1.put("JS", JS);
            map1.put("FMX", FMX);
            map1.put("DWBZ", DWBZ);
            list.add(map1);
            rec++;
            iRows++;
        }
        map.put("data", list);
        Gson gson = new Gson();
        Result = gson.toJson(map);
        return Result;
    }


}
