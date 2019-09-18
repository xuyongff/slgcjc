package com.xy.user.base;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-19
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo {
    private int UserID;
    private String LoginName;
    private String PassWord;
    private String UserName;
    private int GroupID;
    private int UserType;
    private int Status;
    private String UserIP;
    private String LoginTime;
    private String Position;
    private String Department;
    private String Limit;
    private String BZ;
    public UserInfo(){}

    public int getUserID() {
        return this.UserID;
    }

    public void setUserID(int id) {
        this.UserID = id;
    }

    public String getLoginName() {
        return this.LoginName;
    }

    public void setLoginName(String loginname) {
        this.LoginName = loginname;
    }

    public String getPassWord() {
        return this.PassWord;
    }

    public void setPassWord(String passWord) {
        this.PassWord = passWord;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setUserName(String username) {
        this.UserName = username;
    }

    public int getGroupID() {
        return this.GroupID;
    }

    public void setGroupID(int groupId) {
        this.GroupID = groupId;
    }

    public int getUserType() {
        return this.UserType;
    }

    public void setUserType(int type) {
        this.UserType = type;
    }
    public void setStatus(int status){
        this.Status=status;
    }
    public int getStatus(){
        return this.Status;
    }
    public void setUserIp(String ip){
        this.UserIP=ip;
    }
    public String getUserIp(){
        return this.UserIP;
    }

    public void setLoginTime(String date){
        this.LoginTime=date;
    }
    public String getLoginTime(){
        return this.LoginTime;
    }
    public void setPosition(String posit){
        this.Position=posit;
    }
    public String getPosition(){
        return this.Position;
    }
    public void setDepartment(String dept){
        this.Department=dept;
    }
    public String getDepartment(){
        return this.Department;
    }
    public void setLimit(String limit){
        this.Limit=limit;
    }
    public String getLimit(){
        return this.Limit;
    }
    public void setBZ(String BZ){
        this.BZ=BZ;
    }
    public String getBZ(){
        return this.BZ;
    }
}

