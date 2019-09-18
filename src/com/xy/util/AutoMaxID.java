package com.xy.util;

/**
 * Created by Administrator on 2019/8/30.
 */
 import java.sql.*;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.*;
 import com.xy.system.db.*;
 import javax.sql.rowset.CachedRowSet;
 import com.sun.rowset.*;
public class AutoMaxID  {

    //private static final String AF = "AF";
//	private static int serial = 0;
    int rec=0;
    String dbsource="fimisdb";
    //private String OrderNum=AF+new SimpleDateFormat("yyyyMMdd").format(new Date());
    JdbcTemplate JdbcDB=new JdbcTemplate();
    public AutoMaxID(){

    }
    public String generateMaxID(String AF,int serial){
        StringBuilder sb = new StringBuilder();
        sb.append(serial);
        String MaxSerial="";
        while(sb.length() < 4){
            sb.insert(0, "0");
        }
        //sb.insert(0, new SimpleDateFormat("yyyyMMdd").format(new Date()));
        sb.insert(0, AF);
        MaxSerial=sb.toString();
        return MaxSerial;
    }
    public String generateMaxID(String AF,int serial,int bit){
        StringBuilder sb = new StringBuilder();
        sb.append(serial);
        String MaxSerial="";
        while(sb.length() < bit){
            sb.insert(0, "0");
        }
        //sb.insert(0, new SimpleDateFormat("yyyyMMdd").format(new Date()));
        sb.insert(0, AF);
        MaxSerial=sb.toString();
        return MaxSerial;
    }
    public String  saveMaxID(String Str){

        try{
            CachedRowSet rs=new CachedRowSetImpl();
            String MaxSerial="";
            rs=JdbcDB.exeQuery(dbsource,"select * from T_Sys_MaxID where SerialNumber like '"+Str+"%'");
            if(rs.next()){
                MaxSerial=rs.getString("SerialNumber");
                if(MaxSerial!=null){
                    String str=MaxSerial.substring(MaxSerial.length()-5,MaxSerial.length());
                    while (str.startsWith("0") && str.length() > 1) {
                        str = str.substring(1, str.length());
                    }
                    System.out.println(str);
                    int serial=Integer.parseInt(str)+1;
                    MaxSerial=generateMaxID(Str,serial);
                }

            }else{
                MaxSerial=generateMaxID(Str,1);
                if(JdbcDB.exeUpdate(dbsource,"INSERT INTO T_Sys_MaxID(SerialNumber)values('"+MaxSerial+"')"))
                {

                }
            }

            return MaxSerial;

        }catch (Exception e){
            e.printStackTrace();

            return null;
        }

    }
    public String  saveMaxID(String Str,int bit){

        try{
            CachedRowSet rs=new CachedRowSetImpl();
            String MaxSerial="";
            rs=JdbcDB.exeQuery(dbsource,"select * from T_Sys_MaxID where SerialNumber like '"+Str+"%'");
            if(rs.next()){
                MaxSerial=rs.getString("SerialNumber");
                if(MaxSerial!=null){
                    String str=MaxSerial.substring(MaxSerial.length()-5,MaxSerial.length());
                    while (str.startsWith("0") && str.length() > 1) {
                        str = str.substring(1, str.length());
                    }
                    //System.out.println(str);
                    int serial=Integer.parseInt(str)+1;
                    MaxSerial=generateMaxID(Str,serial,bit);
                }

            }else{
                MaxSerial=generateMaxID(Str,1,bit);
                if(JdbcDB.exeUpdate(dbsource,"INSERT INTO T_Sys_MaxID(SerialNumber)values('"+MaxSerial+"')"))
                {

                }
            }

            return MaxSerial;

        }catch (Exception e){
            e.printStackTrace();

            return null;
        }

    }
    public String  saveAutoMaxID(String Str){

        try{
            CachedRowSet rs=new CachedRowSetImpl();
            //System.out.println(Str);
            String MaxSerial="";
            rs=JdbcDB.exeQuery(dbsource,"select * from T_Sys_MaxID where SerialNumber like '"+Str+"%'");
            if(rs.next()){
                MaxSerial=rs.getString("SerialNumber");
                if(MaxSerial!=null){
                    String str=MaxSerial.substring(MaxSerial.length()-5,MaxSerial.length());
                    while (str.startsWith("0") && str.length() > 1) {
                        str = str.substring(1, str.length());
                    }
                    int serial=Integer.parseInt(str)+1;
                    MaxSerial=generateMaxID(Str,serial);
                    if(JdbcDB.exeUpdate(dbsource,"UPDATE T_Sys_MaxID set SerialNumber='"+MaxSerial+"' where SerialNumber like '"+Str+"%'"))
                    {
                    }
                }

            }else{
                MaxSerial=generateMaxID(Str,1);
                if(JdbcDB.exeUpdate(dbsource,"INSERT INTO T_Sys_MaxID(SerialNumber)values('"+MaxSerial+"')")){

                }
            }

            return MaxSerial;

        }catch (Exception e){
            e.printStackTrace();

            return null;
        }

    }
    public static void main(String args[]){
        AutoMaxID maxID=new AutoMaxID();
        String OrderNum="RK"+new SimpleDateFormat("yyyyMM").format(new Date());
        //System.out.println("Serial: "+maxID.generateMaxID("14010000000",100));
        System.out.println("Serial: "+maxID.generateMaxID(OrderNum,5,5));
    }

}