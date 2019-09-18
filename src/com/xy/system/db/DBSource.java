package com.xy.system.db;

import com.jolbox.bonecp.BoneCPDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-12-11
 * Time: 上午7:53
 * To change this template use File | Settings | File Templates.
 */
public class DBSource {
    public static BoneCPDataSource connectionPool = null;
    //private static DataSource dataSource= new DataSource();
    public DBSource(){}
    public static void init(String dbsource){
       /* if(connectionPool!=null) {
            try{
                connectionPool.close();
            }catch (Exception e){

            }
            connectionPool=null;
        } */
        try{
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            connectionPool = (BoneCPDataSource) envCtx.lookup(dbsource);//通过名字得到数据源
            //dataSource=(DataSource)envCtx.lookup(dbsource);
           // System.out.println(ds.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static synchronized Connection getConnection(String dbsource) throws SQLException {
        if(connectionPool==null){
            init(dbsource);
        }
        Connection con=null;
        if(connectionPool!=null){
        con= connectionPool.getConnection();
        }
        return con;
    }


}
