package com.xy.system.login;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-19
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */

import com.sun.rowset.CachedRowSetImpl;
import com.xy.system.db.JdbcTemplate;
import com.xy.user.base.UserInfo;
import com.xy.util.MD5;
import com.xy.util.UtilFunction;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public class UserAction {
    private UtilFunction Fun=new UtilFunction();
    private MD5 Md5=new MD5();
    private String dbsource="fimisdb";
    public UserAction() {
    }

    public boolean addUser(UserInfo userinfo){
        boolean flag=false;
        try{
            JdbcTemplate jdbcDB =new JdbcTemplate();
            if(userinfo!=null){
                String Sql="insert into T_Sys_User(U_LoginName,U_PassWord,U_UserName,U_GroupID,U_Type,U_Position,U_Status,U_Department,Limit,BZ)values('"+userinfo.getLoginName()+"','"+Md5.getMD5ofStr(userinfo.getPassWord())+"','"+userinfo.getUserName()+"','"+userinfo.getGroupID()+"','"+userinfo.getUserType()+"','"+userinfo.getPosition()+"','"+userinfo.getStatus()+"','"+userinfo.getDepartment()+"','"+userinfo.getLimit()+"','"+userinfo.getBZ()+"')";
                String SqlSel="select U_LoginName from T_Sys_User where U_LoginName='"+userinfo.getLoginName().trim()+"'";
                CachedRowSet Rs=new  CachedRowSetImpl();
                Rs=jdbcDB.exeQuery(dbsource,SqlSel);
                if(!Rs.next()){
                    if(jdbcDB.exeUpdate(dbsource,Sql)){
                        flag=true;
                    }else{
                        flag=false;
                    }
                }else{
                    flag=false;
                }

            }else{
                flag=false;
            }
            return flag;
        }catch(SQLException e){
            e.printStackTrace();
            return false;

        }
    }

    public boolean editUser(UserInfo userinfo){
        boolean flag=false;
        try{
            JdbcTemplate jdbcDB =new JdbcTemplate();
            if(userinfo!=null){
                String Sql="update T_Sys_User set U_LoginName='"+userinfo.getLoginName()+"',U_PassWord='"+Md5.getMD5ofStr(userinfo.getPassWord())+"',U_UserName='"+userinfo.getUserName()+"',U_GroupID='"+userinfo.getGroupID()+"',U_Type='"+userinfo.getUserType()+"',U_Position='"+userinfo.getPosition()+"',U_Status='"+userinfo.getStatus()+"',U_Department='"+userinfo.getDepartment()+"',Limit='"+userinfo.getLimit()+"',BZ='"+userinfo.getBZ()+"' where UserID="+userinfo.getUserID()+"";
                String SqlSel="select U_LoginName from T_Sys_User where UserID="+userinfo.getUserID()+"";
                //System.out.println(Sql);
                CachedRowSet Rs=new  CachedRowSetImpl();
                Rs=jdbcDB.exeQuery(dbsource,SqlSel);
                if(Rs.next()){
                    if(jdbcDB.exeUpdate(dbsource,Sql)){
                        flag=true;
                    }else{
                        flag=false;
                    }
                }else{
                    flag=false;
                }
                Rs.close();
                jdbcDB.closeAll();
            }else{
                flag=false;
            }


        }catch(Exception e){
            e.printStackTrace();
            return false;

        }
        return flag;
    }

    public boolean delUser( int UserID){
        boolean flag=false;
        try{
            JdbcTemplate jdbcDB =new JdbcTemplate();
            if(UserID>0){
                String Sql="delete T_Sys_User where UserID="+UserID+"";
                if(jdbcDB.exeUpdate(dbsource,Sql)){
                    flag=true;
                }else{
                    flag=false;
                }
            }else{
                flag=false;
            }

            jdbcDB.closeAll();

        }catch(Exception e){
            e.printStackTrace();
            return false;

        }
        return flag;
    }
    public boolean editPassWord(UserInfo userinfo){
        boolean flag=false;
        try{
            JdbcTemplate jdbcDB =new JdbcTemplate();
            if(userinfo!=null){
                String Sql="update T_Sys_User set U_PassWord='"+Md5.getMD5ofStr(userinfo.getPassWord())+"' where UserID="+userinfo.getUserID()+"";
                String SqlSel="select U_LoginName from T_Sys_User where UserID="+userinfo.getUserID()+"";
                CachedRowSet Rs=new  CachedRowSetImpl();
                Rs=jdbcDB.exeQuery(dbsource,SqlSel);
                if(Rs.next()){
                    if(jdbcDB.exeUpdate(dbsource,Sql)){
                        flag=true;
                    }else{
                        flag=false;
                    }
                }else{
                    flag=false;
                }
                Rs.close();
                jdbcDB.closeAll();
            }else{
                flag=false;
            }


        }catch(Exception e){
            e.printStackTrace();
            return false;

        }
        return flag;
    }

    public UserInfo getUser(int UserID){
        UserInfo userInfo=new UserInfo();
        try{
            JdbcTemplate jdbcDB=new JdbcTemplate();
            if(UserID>0){
                String SqlSel="select * from T_Sys_User where UserID="+UserID+"";
                //System.out.println(SqlSel);
                CachedRowSet Rs=new  CachedRowSetImpl();
                Rs=jdbcDB.exeQuery(dbsource,SqlSel);
                if(Rs.next()){
                    String LonginName=Rs.getString("U_LoginName")==null?"":Rs.getString("U_LoginName").trim();
                    userInfo.setLoginName(LonginName);
                    String Department=Rs.getString("U_Department")==null?"":Rs.getString("U_Department").trim();
                    userInfo.setDepartment(Department);
                    userInfo.setGroupID(Rs.getInt("U_GroupID"));
                    String Position=Rs.getString("U_Position")==null?"":Rs.getString("U_Position").trim();
                    userInfo.setPosition(Position);
                    String UserName=Rs.getString("U_UserName")==null?"":Rs.getString("U_UserName").trim();
                    userInfo.setUserName(UserName);
                    String Limit=Rs.getString("Limit")==null?"":Rs.getString("Limit").trim();
                    userInfo.setLimit(Limit);
                    String BZ=Rs.getString("BZ")==null?"":Rs.getString("BZ").trim();
                    userInfo.setBZ(BZ);
                    userInfo.setStatus(Rs.getInt("U_Status"));
                    userInfo.setUserType(Rs.getInt("U_Type"));
                    userInfo.setUserID(Rs.getInt("UserID"));
                    String PassWord=Rs.getString("U_UserName")==null?"":Rs.getString("U_UserName").trim();
                    userInfo.setPassWord(PassWord);

                }
                Rs.close();
            }
            //userInfo
            jdbcDB.closeAll();

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return userInfo;
    }
    public static void main(String args[]){
        JdbcTemplate db=new JdbcTemplate();
        UserAction userAction=new UserAction();
        UserInfo user=new UserInfo();
        System.out.println(userAction.getUser(1).getUserName());
    }

}

