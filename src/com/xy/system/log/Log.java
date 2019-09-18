package com.xy.system.log;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-19
 * Time: 下午5:54
 * To change this template use File | Settings | File Templates.
 */

import com.xy.system.db.JdbcTemplate;
import com.xy.util.UtilFunction;

public class Log {
    private JdbcTemplate jdbcDB =new JdbcTemplate();
    private UtilFunction Fun=new UtilFunction();

    public Log() {
    }
    //AddLog()
    public void AddLog(String dbsource,String [] str){
        try{
            String LoginName,LogTime,LogType,Status,LoginIp;
            java.util.Date Now=new java.util.Date();
            if(str!=null&&str.length>0){
                LogTime=Fun.getFormatTime(Now,"yyyyMMdd HH:mm:ss");
                if(jdbcDB!=null){
                    String SqlInsert="insert into T_Sys_Log(LogName,LogType,LoginIp,Status,Result,LogTime)values(?,?,?,?,?,?)";
                    String Para[]=new String[6];
                    Para[0]=str[0]==null?"":str[0].trim();
                    Para[1]=str[1]==null?"":str[1].trim();
                    Para[2]=str[2]==null?"":str[2].trim();
                    Para[3]=str[3]==null?"":str[3].trim();
                    Para[4]=str[4]==null?"":str[4].trim();
                    Para[5]=LogTime;;
                    jdbcDB.exeUpdata(dbsource,SqlInsert,Para);
                }else{
                    System.out.println("数据库连接失败！");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        try{
            Log log=new Log();
            String str[]=new String[5];
            str[0]="徐勇";
            str[1]="用户登陆";
            str[2]="192.168.1.110";
            str[3]="成功";
            //log.AddLog(str);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
