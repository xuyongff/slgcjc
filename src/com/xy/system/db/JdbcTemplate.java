package com.xy.system.db;

import com.sun.rowset.CachedRowSetImpl;
import com.xy.util.StringUtil;
import com.xy.util.UtilFunction;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static cn.hutool.db.DbUtil.close;

public class JdbcTemplate {
    private Connection conn;
    private Statement stmt=null;
    private ResultSet rs=null;
    private PreparedStatement psmt=null;
    UtilFunction Fun=new UtilFunction();

    public JdbcTemplate() {
    }

    public static DataSource getDataSource(String dbsource){
        DataSource ds=null;
        try {
            Context initCtx=new InitialContext();
            Context envCtx=(Context)initCtx.lookup("java:comp/env");
            ds=(DataSource)envCtx.lookup(dbsource);

        }catch (Exception e){
            e.printStackTrace();
        }
        return ds;
    }

    //创建数据库连接
    private void createConnection(String dbsource){
        try {
            //System.out.print(dbsource);
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup(dbsource);//通过名字得到数据源
            conn=ds.getConnection();
            if(conn!=null){
                conn.setAutoCommit(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接失败！"+e.getMessage());
        }
    }

    private void getStatement(String dbsource){
        this.createConnection(dbsource);//得到数据库连接对象

        try{
            stmt=conn.createStatement();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("创建状态通道失败！");
        }
    }

    public PreparedStatement getPreparedStatement(String dbsource,String sql){
        this.createConnection(dbsource);
        try{
            psmt=conn.prepareStatement(sql);

        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("创建预编译状态通道失败！");
            psmt=null;
        }
        return  psmt;
    }


    public CachedRowSet exeQuery(String dbsource,String sql)throws SQLException{
        this.getStatement(dbsource);
        CachedRowSet crs=new  CachedRowSetImpl();
        try{
            //sql=Fun.CheckReplace(sql);
            rs=stmt.executeQuery(sql);
            crs.populate(rs);
            closeAll();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("查询数据出错!");
            crs=null;
        }
        return crs;
    }
    public  CachedRowSet callProcQuery(String dbsource,String [] Param)throws SQLException{
        this.createConnection(dbsource);
        CachedRowSet crs=new CachedRowSetImpl();
        try{
            String procedure="{call SP_Pagination(?,?,?,?,?,?,?,?)}";
            CallableStatement cst=conn.prepareCall(procedure);
            UtilFunction Fun=new UtilFunction();
           for(int i=0;i<Param.length;i++){
                if(i+1==4||i+1==5)
                    cst.setInt(i+1,Fun.StrToInt(Param[i]));
                else
                    cst.setString(i+1,Param[i]);

            }
            //cst.setString(1,Param[0]);cst.setString(2,Param[1]);cst.setString(3,Param[2]);cst.setInt(4,Integer.parseInt(Param[3]));
            //cst.setInt(5,Integer.parseInt(Param[4]));  cst.setString(6,"*");  cst.setString(7,Param[6]); cst.setString(8,Param[7]);
            rs=  cst.executeQuery();
            crs.populate(rs);
            closeAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
           return crs;
    }
    public boolean exeBatch(String dbsource,String[]params){
        this.getStatement(dbsource);
        boolean isCorrect=false;
        try{
            conn.setAutoCommit(false);
            if(params!=null&&params.length>0){
                for(int i=0;i<params.length;i++){
                    stmt.addBatch(params[i]);
                }
                stmt.executeBatch();
                this.runCommit();
                isCorrect=true;
            }
            closeAll();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("状态通道下批处理操作失败！");
            this.runRollBack();
        }
        this.closeAll();
        return isCorrect;
    }
    public boolean exeUpdata(String dbsource,String sql,String[]params){
        boolean isCorrect=false;
        this.getPreparedStatement(dbsource,sql);
        try{
            if(params!=null&&params.length>0){
                for(int i=0;i<params.length;i++){
                    psmt.setString(i+1,params[i]);
                }
            }
            psmt.executeUpdate();
            this.runCommit();
            isCorrect=true;
        }catch(SQLException e){
            e.printStackTrace();
            this.runRollBack();
            System.out.println("预通道下的数据操作出错！");

        }finally {
            this.closeAll();
        }
        return isCorrect;
    }
    public boolean exeUpdate(String dbsource,String sql){
        boolean isCorrect=false;
        this.getStatement(dbsource);
        try{
            if(sql!=null){
                stmt.executeUpdate(sql);
                this.runCommit();
                isCorrect=true;
            }
        }catch(SQLException e){
            e.printStackTrace();
            this.runRollBack();
            System.out.println("数据操作出错！");
        }
        this.closeAll();
        return isCorrect;
    }
    /**
     * 查询单条记录
     *
     * @param sql  查询语句
     * @param clazz 返回对象的class

     * @param <T>   泛型返回
     * @return      返回单个对象
     */
    public  <T> T queryForObject(String dbsource,String sql, Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        this.getStatement(dbsource);
        T obj =null;// 创建对象实例;
        try {
            rs=stmt.executeQuery(sql);
            ResultSetMetaData rsm = rs.getMetaData();
            Method[] methods = clazz.getDeclaredMethods();
            int column = rsm.getColumnCount();// 得到列
            while (rs.next()) {
                obj=clazz.newInstance();
                for (int i = 1; i <= column; i++) {
                    String columnName= rsm.getColumnName(i);
                    String colType=rsm.getColumnTypeName(i);
                    //System.out.println(columnName+": "+colType);
                    //String setMethodName = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    //String getMethodName = "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Class<?> type = null;
                    try {
                        type = clazz.getDeclaredField(columnName).getType();// 获取字段类型
                    } catch (NoSuchFieldException e) { // Class对象未定义该字段时,跳过
                        continue;
                    }
                    Method method = clazz.getMethod("set" + columnName, type);
                        // 根据数据库的列名（这里没考虑别名）实体的属性名来判断该调用的set方法
                        // 根据set方法的参数类型来确定从结果集里拿数据的方式
                        // (这里的类型只有3种是应为项目只需要3种所有我没有加其他的类型)
                    if(type.isAssignableFrom(int.class)){
                        method.invoke(obj,rs.getInt(i));
                    }else if(type.isAssignableFrom(float.class)){
                        method.invoke(obj,rs.getFloat(i));
                    }else if(type.isAssignableFrom(String.class)){
                        String sdate="";
                        sdate=rs.getString(i)==null?"":rs.getString(i).trim();
                        if(colType.compareTo("datetime")==0){
                            if(sdate.compareTo("")==0||sdate.substring(0,10).compareTo("1900-01-01")==0){
                                sdate="";
                            }else{
                                sdate=sdate.substring(0,10);
                            }

                        }else{
                            sdate=sdate;
                        }
                        method.invoke(obj,sdate);
                    }
                }
            }
           // System.out.println(obj);
         } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println(e.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            closeAll(); //记得关闭
        }
        return obj;

    }
    public  <T> List<T> queryListObject( CachedRowSet Rs, Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        T obj =null;// 创建对象实例;
        List<T> list=null;
       try {
            ResultSetMetaData rsm = Rs.getMetaData();
            Method[] methods = clazz.getDeclaredMethods();
            int column = rsm.getColumnCount();// 得到列
           list = new ArrayList<T>();
            while (Rs.next()) {
                obj=clazz.newInstance();
                for (int i = 1; i <= column; i++) {
                    String columnName= rsm.getColumnName(i);
                    String colType=rsm.getColumnTypeName(i);
                    //System.out.println(columnName+": "+colType);
                    //String setMethodName = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    //String getMethodName = "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Class<?> type = null;
                    try {
                        type = clazz.getDeclaredField(columnName).getType();// 获取字段类型
                    } catch (NoSuchFieldException e) { // Class对象未定义该字段时,跳过
                        continue;
                    }
                    Method method = clazz.getMethod("set" + columnName, type);
                    // 根据数据库的列名（这里没考虑别名）实体的属性名来判断该调用的set方法
                    // 根据set方法的参数类型来确定从结果集里拿数据的方式
                    // (这里的类型只有3种是应为项目只需要3种所有我没有加其他的类型)
                    if(type.isAssignableFrom(int.class)){
                        method.invoke(obj,Rs.getInt(i));
                    }else if(type.isAssignableFrom(float.class)){
                        method.invoke(obj,Rs.getFloat(i));
                    }else if(type.isAssignableFrom(String.class)){
                        String sdate="";
                        sdate=Rs.getString(i)==null?"":Rs.getString(i).trim();
                        if(colType.compareTo("datetime")==0){
                            if(sdate.compareTo("")==0||sdate.substring(0,10).compareTo("1900-01-01")==0){
                                sdate="";
                            }else{
                                sdate=sdate.substring(0,10);
                            }

                        }else{
                            sdate=sdate;
                        }
                        method.invoke(obj,sdate);
                    }
                }
                list.add((T) obj);
            }
            // System.out.println(obj);
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println(e.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            //closeAll(); //记得关闭
        }
        return list;

    }

    public void runCommit(){
        try{
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("提交事务失败!");
        }
    }

    public  void runRollBack(){
        try{
            conn.rollback();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("事务回滚失败！");
        }
    }

    public void closeAll(){
        try{
            if(rs!=null)rs.close();
            if(stmt!=null)stmt.close();
            if(conn!=null)conn.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConn(){
        return this.conn;
    }
    public void setConn(Connection conn){
        this.conn=conn;
    }
    public Statement getStmt(){
        return this.stmt;
    }
    public void setStmt(Statement stmt){
        this.stmt=stmt;
    }
    public ResultSet getRs(){
        return this.rs;
    }
    public void setRs(ResultSet rs){
        this.rs=rs;
    }
    public void setPstm(PreparedStatement pstm){
        this.psmt=psmt;

    }
    public PreparedStatement getPstm(){
        return this.psmt;
    }

    public static void main(String args[]){
        JdbcTemplate JdbcDB=new JdbcTemplate();
        String str="xuyong";
        String str1="徐勇";
        // String sql="insert into T_Sys_User(U_LoginName,U_UserName)values('"+str+"','"+str1+"')";
        String sqlstr="insert into T_Sys_User(U_LoginName,U_PassWord,U_UserName,U_GroupID,U_Type,U_Position,U_Status,U_Department,LimitID)values('xy1','123','徐勇','2','3','U_Position','4','U_Department','100')";
        //JdbcDB.exeUpdate(sqlstr);

    }

}
