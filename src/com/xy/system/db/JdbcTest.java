package com.xy.system.db;

/**
 * @(#)JdbcTemplate.java
 *
 *
 * @author
 * @version 1.00 2008/10/21
 */

import com.xy.util.UtilFunction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTest {
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static PreparedStatement psmt = null;
    private UtilFunction utilFun = new UtilFunction();
    private DBManager dbMgr = DBManager.getInstance();

    public JdbcTest() {

    }

    /**
     * 打开数据库连接并创建数据库连接对象
     *
     * @return boolean true:连接成功，false:连接失败
     */

    public boolean openConn(String dbName) {
        boolean isPassed = false;
        try {
            //System.out.println(dbName);
            DBManager dbMgr=DBManager.getInstance();
            conn = dbMgr.getConnection(dbName);
            //pool=ConnectionPool.getConnectionPool();
            //conn=pool.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                //utilFun.writeLog("数据库连接创建成功！当前连接数为：" + dbMgr.getClient());
                isPassed = true;
            }

        } catch (Exception e) {
            isPassed = false;
            conn = null;
            e.printStackTrace();
            //utilFun.writeLog("数据库连接失败！" + e);
        }
        return isPassed;
    }

    /**
     * 准备SQL参数
     *
     * @param pstm
     * @param params
     */
    public void PrepareCommand(PreparedStatement pstm, Object[] params) {
        if (params == null || params.length == 0) {
            return;
        }
        try {
            for (int i = 0; i < params.length; i++) {
                int parameterIndex = i + 1;
                // String
                if (params[i].getClass() == String.class) {
                    pstm.setString(parameterIndex, params[i].toString());
                }
                // Short
                else if (params[i].getClass() == short.class) {
                    pstm.setShort(parameterIndex, Short.parseShort(params[i]
                            .toString()));
                }
                // Long
                else if (params[i].getClass() == long.class) {
                    pstm.setLong(parameterIndex, Long.parseLong(params[i]
                            .toString()));
                }
                // Integer
                else if (params[i].getClass() == Integer.class) {
                    pstm.setInt(parameterIndex, Integer.parseInt(params[i]
                            .toString()));
                }
                // Date
                else if (params[i].getClass() == java.util.Date.class) {
                    java.util.Date dt = (java.util.Date) params[i];
                    pstm.setDate(parameterIndex,
                            new Date(dt.getTime()));
                }
                // Byte
                else if (params[i].getClass() == byte.class) {
                    pstm.setByte(parameterIndex, (Byte) params[i]);

                }
                // Float
                else if (params[i].getClass() == float.class) {
                    pstm.setFloat(parameterIndex, Float.parseFloat(params[i]
                            .toString()));
                }
                // Boolean
                else if (params[i].getClass() == boolean.class) {
                    pstm.setBoolean(parameterIndex, Boolean
                            .parseBoolean(params[i].toString()));
                } else {
                    //utilFun.writeLog("参数准备出错:数据类型不可见"+ params[i].getClass().toString());
                }
            }
        } catch (Exception e) {
           // utilFun.writeLog("SQL参数准备错误！");
        }

    }

    /**
     * 执行数据库的新增和修改语句，只操作一张表
     *
     * @param sql
     *            要执行的SQL语句
     * @return int 返回影响的行数
     */

    public int exeUpdate(String dbName,String sql) {
        int intRows = 0;
        try {
            if (openConn(dbName)) {
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                intRows = stmt.executeUpdate(sql);
                runCommit();
                closeAll();
                utilFun.writeLog("操作执行成功！执行SQL：" + sql);
            }
        } catch (SQLException e) {
            utilFun.writeLog("操作执行成功！执行SQL：" + sql + "错误原因: " + e);
            intRows = 0;
            runRollBack();
            closeAll();
        }
        return intRows;
    }

    /**
     * 用于获取结果集语句（eg：selete * from table）
     *
     * @param sql
     * @param params
     * @return
     */
    public List exeQuery(String dbName,String sql, Object[] params) {
        List list = new ArrayList();
        try {
            if (openConn(dbName)) {
                psmt = conn.prepareStatement(sql);
                PrepareCommand(psmt, params);
                rs = psmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int column = rsmd.getColumnCount();
                while (rs.next()) {
                    Object obj[] = new Object[column];
                    for (int i = 1; i <= column; i++) {
                        obj[i - 1] = rs.getObject(i);
                    }
                    list.add(obj);
                }

                //utilFun.writeLog("执行查询成功!返回记录：" + rs.getRow() + "条");
            }
            closeAll();
        } catch (SQLException e) {
            utilFun.writeLog("执行查询失败!" + e);
            closeAll();
        }
        return list;
    }

    /**
     * 执行数据库的新增和修改语句，只操作一张表
     *
     * @param sql
     *            要执行的SQL语句
     * @return int 返回影响的行数
     */

    /**
     * 用于获取结果集语句（eg：selete * from table）
     *
     * @param sql
     * @param
     * @return list
     */
    public List exeQuery(String dbName,String sql) {
        List list = new ArrayList();
        int nRows = 0;
        try {
            if (openConn(dbName)) {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                int column = rsmd.getColumnCount();
                while (rs.next()) {
                    Object obj[] = new Object[column];
                    nRows++;
                    for (int i = 1; i <= column; i++) {
                        obj[i - 1] = rs.getObject(i);
                    }
                    list.add(obj);
                }

                utilFun.writeLog("执行查询成功!返回记录：" + nRows + "条");
            }
            closeAll();
        } catch (SQLException e) {
            utilFun.writeLog("执行查询失败!" + e);
            closeAll();
        }
        return list;
    }

    /**
     * 用于获取结果集语句（eg：selete * from table）
     *
     * @param sql
     * @param
     * @return list
     */
    public ResultSet exeQuery(String dbName,String sql,String str){
        int nRows = 0;
        ResultSet rSet=null;
        try {
            if (openConn(dbName)) {
                stmt = conn.createStatement();
                rSet = stmt.executeQuery(sql);
                //utilFun.writeLog("执行查询成功!");
            }
            //closeAll();
        } catch (SQLException e) {
            //utilFun.writeLog("执行查询失败!" + e);
            e.printStackTrace();
            rSet=null;
            //closeAll();

        }
        return rSet;
    }

    /**
     * 用于批量操作数据库表不包括查询（Update,delete,insert）
     *
     * @param params
     * @return 影响的行数
     */
    public int[] exeBatch(String dbName,String[] params) {
        int[] nRows = new int[params.length];
        try {
            if (openConn(dbName)) {
                stmt = conn.createStatement();
                if (params != null && params.length > 0) {
                    for (int i = 0; i < params.length; i++) {
                        if (params[i] != null)
                            stmt.addBatch(params[i]);
                        else
                            continue;
                    }
                    nRows = stmt.executeBatch();
                    runCommit();
                    if (params != null && params.length > 0) {
                        for (int i = 0; i < params.length; i++) {
                            utilFun.writeLog("执行语句：" + params + " 影响行数："
                                    + nRows[i]);
                        }
                    }
                } else {
                    utilFun.writeLog("SQL参数为空!");
                }

            } else {
                utilFun.writeLog("没有连接数据库!");
            }
            closeAll();
        } catch (SQLException e) {
            runRollBack();
            utilFun.writeLog("数据执行失败!");
            e.printStackTrace();
            closeAll();
        }
        return nRows;
    }

    /**
     * 用于预编译SQL操作数据库表（Update,delete,insert）
     *
     * @param params
     * @param sql
     *            预编译SQL语句格式
     * @return 影响的行数
     */
    public int exeUpdate(String dbName,String sql, Object[] params) {
        int nRows = 0;
        try {
            if (openConn(dbName)) {
                psmt = conn.prepareStatement(sql);
                PrepareCommand(psmt, params);
                nRows = psmt.executeUpdate();
                runCommit();
                utilFun.writeLog("数据库操作成功!影响行数为：" + nRows);
            }
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
            runRollBack();
            utilFun.writeLog("数据库操作失败:" + e);
            closeAll();
        }
        return nRows;
    }
    /**
     *
     */
    public PreparedStatement getPreparedStatement(String dbName,String sql){
        try{
            if (openConn(dbName)) {
            psmt=conn.prepareStatement(sql);
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("创建预编译状态通道失败！");
            psmt=null;
        }
        return  psmt;
    }
    /**
     * 用于事务提交
     *
     */
    public void runCommit() {
        try {
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("提交事务失败!");
        }
    }

    /**
     * 用于事务失败回滚
     *
     */
    public void runRollBack() {
        try {
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("事务回滚失败！");
        }
    }

    /**
     * 关闭所有的连接,释放连接资源
     *
     */
    public void closeAll() {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            // if (conn != null)
            // conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            utilFun.writeLog("数据关闭错误：" + e);
        } finally {
            dbMgr.freeConnection("SqlServer", conn);
            //pool.release(conn);
            //pool.close();
        }
    }

    public static void main(String args[]) {
        try {
            long t = System.currentTimeMillis();
            JdbcTest jdbcDB = new JdbcTest();
            ResultSet rs=null;
            rs=jdbcDB.exeQuery("sqlserver","select * from T_Sys_User","");
            while (rs.next()){
                System.out.println(111);
            }
           /* ResultSetMetaData rsmd=rs.getMetaData();
            int iCols=rsmd.getColumnCount();
            for(int i=1;i<iCols;i++){
                System.out.print(rsmd.getColumnName(i)+" ");
            }
            */
            /*StringBuffer sb=new StringBuffer();
            sb.append("CREATE TABLE [dbo].[T_ZC_WLZCB] (");
            sb.append("\r\n");
            while (rs.next()) {
                String F_COLUMNBH=rs.getString("F_COLUMNBH").trim();
                String F_COLUMNLX=rs.getString("F_COLUMNLX").trim();
                int Len=rs.getInt("F_COLUMNLEN");
                sb.append("["+F_COLUMNBH+"] ["+F_COLUMNLX+"] ("+Len+"),");
                sb.append("\r\n");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append(")");
            System.out.println(sb.toString());*/

            System.out.println((System.currentTimeMillis() - t)/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
