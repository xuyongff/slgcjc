package com.xy.system.login;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-19
 * Time: 下午5:38
 * To change this template use File | Settings | File Templates.
 */

import com.sun.rowset.CachedRowSetImpl;
import com.xy.system.db.JdbcTemplate;
import com.xy.system.log.Log;
import com.xy.user.base.UserInfo;
import com.xy.util.MD5;
import com.xy.util.UtilFunction;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

public class Login
{

    private UtilFunction Fun = new UtilFunction();
    private Log LoginLog=new Log();
    private UserInfo UserLogin;
    MD5 md5 = new MD5();

    public Login()
    {

    }

//LoginCheck

    public boolean isLogin(String dbsource,String s1,String s2,String s3)throws SQLException
    {
        UserInfo userInfo=new UserInfo();
        JdbcTemplate jdbcDB = new JdbcTemplate();
        CachedRowSet rs=new  CachedRowSetImpl();
        boolean OK = true;
        String Para[]=new String[5];
        String LoginName = "";
        String User = Fun.CheckReplace(s1);
        String PassWord="";
        String Pwd = md5.getMD5ofStr(Fun.CheckReplace(s2));
        //System.out.print(Pwd);
        String Sql = "select * from T_Sys_User where U_LoginName='" + User + "'";
        try {
            rs = jdbcDB.exeQuery(dbsource,Sql);
            if (!rs.next())
            {
                Para[0]=User;
                Para[1] = "用户登录 [ 用户名不存在 ]";
                Para[2] = s3;
                Para[3] = "登录失败";
                Para[4]="密码或用存不存";
                LoginLog.AddLog(dbsource,Para);
                OK = false;
            }else{
                PassWord = rs.getString("U_PassWord").trim();

                if(Pwd.equals(PassWord))
                {
                    //System.out.println("123!");
                    int UserID=rs.getInt("UserID");
                    String UserCode=rs.getString("U_LoginName")==null?"":rs.getString("U_LoginName").trim();
                    userInfo.setLoginName(UserCode);
                    userInfo.setUserID(UserID);
                    userInfo.setDepartment(rs.getString("U_Department")==null?"":rs.getString("U_Department").trim());
                    userInfo.setGroupID(rs.getInt("U_GroupID"));
                    userInfo.setPosition(rs.getString("U_Position")==null?"":rs.getString("U_Position").trim());
                    String UserName=rs.getString("U_UserName");
                    UserName=UserName==null?"":UserName.trim();
                    userInfo.setUserName(UserName);
                    userInfo.setUserType(rs.getInt("U_Type"));
                    userInfo.setUserID(rs.getInt("UserID"));
                    userInfo.setStatus(rs.getInt("U_Status"));
                    userInfo.setPassWord(rs.getString("U_PassWord")==null?"":rs.getString("U_PassWord").trim());
                    userInfo.setLimit(rs.getString("Limit")==null?"":rs.getString("Limit").trim());
                    userInfo.setBZ(rs.getString("BZ")==null?"":rs.getString("BZ").trim());
                    setLoginUser(userInfo);
                    Para[0]=UserName;
                    Para[1] = "用户登录 [成功]";
                    Para[2] = s3;
                    Para[3] = "登录成功";
                    Para[4]="";
                    LoginLog.AddLog(dbsource,Para);
                    UpdateLogin(dbsource,UserID, s3);
                    OK = true;
                }else{
                    Para[0]=User;
                    Para[1] = "用户登录[密码错误]";
                    Para[2] = s3;
                    Para[3] = "登录失败";
                    Para[4]="密码或用存不在";
                    LoginLog.AddLog(dbsource,Para);
                    OK = false;
                }
            }
            return OK;
        }catch(SQLException e)
        {
            e.printStackTrace();
            //return e.getMessage().toString();
            Para[1] = "用户登录[程序异常]";
            Para[3] = "No";
            LoginLog.AddLog(dbsource,Para);
            return false;
        }
    }

    public boolean UpdateLogin(String dbsource,int ID,String s2)
    {
        java.util.Date Now =new java.util.Date();
        String NowTime=Fun.getFormatTime(Now,"yyyy-MM-dd HH:mm:ss");
        String Para[]=new String[2];
        Para[0]=s2;
        Para[1]=NowTime;
        String SqlUpdate="update T_Sys_User set U_IP=?,U_DateTime=? where UserID="+ID+"";
        try
        {
            JdbcTemplate jdbcDB = new JdbcTemplate();

            jdbcDB.exeUpdata(dbsource,SqlUpdate,Para);
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            //System.out.print(sql);
            return false;
        }
    }
    public UserInfo getLoginUser()
    {
        return this.UserLogin;
    }
    public void setLoginUser(UserInfo user)
    {
        this.UserLogin=user;
    }
    //测试
    public static void main(String[] args)
    {
        try{

            Login login = new Login();
            MD5 md5 = new MD5();
            JdbcTemplate db=new JdbcTemplate();
            String Sql="insert into T_Sys_User(U_LoginName,U_PassWord,U_UserName)values(?,?,?)";
            String Para[]=new String[3];
            Para[0]="XY";
            Para[1]=md5.getMD5ofStr("XY");
            Para[2]="徐勇";
            db.exeUpdata("fimisdb",Sql,Para);
            if (login.isLogin("fimisdb","Admin","Admin","192.168.1.110"))
                System.out.println("Success!"  );
            //	else
            //	System.out.println("Fail");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

