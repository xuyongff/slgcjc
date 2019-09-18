package com.xy.service;

import com.xy.system.db.JdbcTest;
import com.xy.util.StringUtil;

import java.sql.*;
import java.util.*;
import java.util.Date;


public class TestDB {
	
	public static String driver = "com.mysql.jdbc.Driver";
	public static String url = "jdbc:mysql://localhost/plusoft_test?useUnicode=true&characterEncoding=utf8";
	public static String user = "root";
	public static String pwd = "xy423";

	////////////////////////////////////////////////////
    public ArrayList GetDepartments() throws Exception
    {
        String sql = "select * from t_department";
        ArrayList data = DBSelect(sql);
        return data;
    }
    public HashMap GetDepartment(String id) throws Exception
    {
    	String sql = "select * from t_department where id = '" + id + "'";
        ArrayList data = DBSelect(sql);
        return data.size() > 0 ? (HashMap)data.get(0) : null;
    }
    public ArrayList GetPositions() throws Exception
    {
    	String sql = "select * from t_position";
        ArrayList data = DBSelect(sql);
        return data;
    }
    public ArrayList GetEducationals() throws Exception
    {
    	String sql = "select * from t_educational";
        ArrayList data = DBSelect(sql);
        return data;
    }
    public ArrayList GetPositionsByDepartmenId(String departmentId) throws Exception
    {        
    	String sql = "select * from t_position where dept_id = '" + departmentId + "'";
        ArrayList dataAll = DBSelect(sql);
        return dataAll;

    }
    public HashMap GetDepartmentEmployees(String departmentId, int index, int size) throws Exception
    {
        String sql = "select * from t_employee where dept_id = '" + departmentId + "'";
        ArrayList dataAll = DBSelect(sql);
        
        ArrayList data = new ArrayList();
        int start = index * size, end = start + size;

        for (int i = 0, l = dataAll.size(); i < l; i++)
        {
            HashMap record = (HashMap)dataAll.get(i);
            if (record == null) continue;
            if (start <= i && i < end)
            {
                data.add(record);
            }
        }

        HashMap result = new HashMap();
        result.put("data", data);
        result.put("total", dataAll.size());
        
        return result;
    }

    public HashMap SearchEmployees(String key, int index, int size, String sortField, String sortOrder) throws Exception
    {
        //System.Threading.Thread.Sleep(300);
    	if(key == null) key = "";
    	
    	String sql = 
 "select a.*, b.name dept_name, c.name position_name, d.name educational_name \n"
+"from t_employee a \n"
+"left join t_department b \n"
+"on a.dept_id = b.id \n"
+"left join t_position c \n"
+"on a.position = c.id \n"
+"left join t_educational d \n"
+"on a.educational = d.id \n"
+"where a.name like '%" + key + "%' \n";

        if (StringUtil.isNullOrEmpty(sortField) == false)
        {
            if ("desc".equals(sortOrder) == false) sortOrder = "asc";
            sql += " order by " + sortField + " " + sortOrder;
        }
        else
        {
            sql += " order by createtime desc";
        }

        ArrayList dataAll = DBSelect(sql);
        
        ArrayList data = new ArrayList();
        int start = index * size, end = start + size;

        for (int i = 0, l = dataAll.size(); i < l; i++)
        {
            HashMap record = (HashMap)dataAll.get(i);
            if (record == null) continue;
            if (start <= i && i < end)
            {
                data.add(record);
            }
            record.put("createtime", new Timestamp(100,10,10,1,1,1,1));
        }

        HashMap result = new HashMap();
        result.put("data", data);
        result.put("total", dataAll.size());

        //minAge, maxAge, avgAge
        ArrayList ages = DBSelect("select min(age) as minAge, max(age) as maxAge, avg(age) as avgAge from t_employee");
        HashMap ageInfo = (HashMap)ages.get(0);
        result.put("minAge", ageInfo.get("minAge"));
        result.put("maxAge", ageInfo.get("maxAge"));
        result.put("avgAge", ageInfo.get("avgAge"));
        



        return result;
    }
    public HashMap GetEmployee(String id) throws Exception
    {
    	String sql = "select * from t_employee where id = '"+id+"'";
        ArrayList data = DBSelect(sql);
        return data.size() > 0 ? (HashMap)data.get(0) : null;
    }
    public String InsertEmployee(HashMap user) throws Exception
    {

        String id = (user.get("id") == null || user.get("id").toString().equals(""))? UUID.randomUUID().toString() : user.get("id").toString();
        try {
        user.put("id", id);
        if (user.get("name") == null) user.put("name", "");
        if (StringUtil.isNullOrEmpty(user.get("gender"))) user.put("gender", 0);
        JdbcTest jdbcTest=new JdbcTest();
        System.out.println(user.toString());
    	String sql = "insert into t_employee (id, loginname, name, age, married, gender, birthday, country, city, dept_id, position, createtime, salary, educational, school, email, remarks)"
            + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	return id;
    }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return id;
    }
    public void DeleteEmployee(String userId) throws Exception
    {
      
		Connection conn = getConn();		
		Statement stmt = conn.createStatement();
		
        String sql = "delete from t_employee where id = \""+userId+"\"";        		
        stmt.executeUpdate(sql);
                
		stmt.close();
		conn.close();
    }
    public void UpdateEmployee(HashMap user) throws Exception
    {
        HashMap db_user = GetEmployee(user.get("id").toString());
        
        Iterator iter = user.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            
            db_user.put(key, val);
        }         

        DeleteEmployee(user.get("id").toString());
        InsertEmployee(db_user);
    }
    
    public void UpdateDepartment(HashMap d) throws Exception
    {
    	HashMap db_d = GetDepartment(d.get("id").toString());
    	
        Iterator iter = d.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            
            db_d.put(key, val);
        }    	
    	
        String sql =
        		"update t_department " 
        		+	" set "
        		+	" name = ?, "
        		+	" manager = ?, "
        		+	" manager_name = ? "
        		+" where id = ?";
        
		Connection conn = getConn();		
			
		PreparedStatement stmt = conn.prepareStatement(sql);	
		
		stmt.setString(1, ToString(db_d.get("name")));
		stmt.setString(2, ToString(db_d.get("manager")));
		stmt.setString(3, ToString(db_d.get("manager_name")));
		stmt.setString(4, ToString(db_d.get("id")));
		
		stmt.executeUpdate();		
        stmt.close();
		conn.close();      
    }    
    /////////////////////////////////////////////////////////////////
	private Connection getConn() throws Exception{		
		Class.forName(driver).newInstance();
		Connection conn = null;
		if(user == null || user.equals("")){
			conn = DriverManager.getConnection(url);
		}else{
			conn = DriverManager.getConnection(url, user, pwd);
		}
			
		return conn;
	}	    
	public ArrayList DBSelect(String sql) throws Exception{
        JdbcTest jdbcTest=new JdbcTest();
        ResultSet rst =jdbcTest.exeQuery("sqlserver",sql,"");
     	ArrayList list=ResultSetToList(rst);
        //System.out.println(list.toString());
        return list;
	}
    private static ArrayList ResultSetToList(ResultSet   rs) throws Exception{    	
    	ResultSetMetaData md = rs.getMetaData();
    	int columnCount = md.getColumnCount();
    	ArrayList list = new ArrayList();
    	Map rowData;
    	while(rs.next()){
	    	rowData = new HashMap(columnCount);
	    	for(int i = 1; i <= columnCount; i++)   {	 	    		
	    		Object v = rs.getObject(i);	    		
	    		
	    		if(v != null && (v.getClass() == Date.class || v.getClass() == java.sql.Date.class)){
	    			Timestamp ts= rs.getTimestamp(i);
	    			v = new Date(ts.getTime());
	    			//v = ts;
	    		}else if(v != null && v.getClass() == Clob.class){
	    			v = clob2String((Clob)v);
	    		}
	    		rowData.put(md.getColumnName(i),   v);
	    	}
	    	list.add(rowData);	    	
    	}
    	return list;
	} 	
    private static String clob2String(Clob clob) throws Exception {
        return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
    }  		    
    private int ToInt(Object o){
    	if(o == null) return 0;
    	double d = Double.parseDouble(o.toString());
    	int i = 0;
		i -= d;
		return -i;			
    }    
    private String ToString(Object o){
    	if(o == null) return "";
    	return o.toString();
    }    
    private Timestamp ToDate(Object o){
    	return o != null ? new Timestamp(((Date)o).getTime()) : null;
    }
}
