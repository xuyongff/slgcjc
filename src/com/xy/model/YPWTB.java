package com.xy.model;
import java.sql.*;

/**
 *文件名称: T_JL_YPWTB.java
 @创建时间：2019-09-14 10:19:18
 @创建人：XY
 @文件描述：T_JL_YPWTB 实体类
 @文件版本: V0.01
 */
public class YPWTB{
   private int F_ID;
   private String F_JLBH;
   private String F_CPBH;
   private String F_CPMC;
   private String F_CPXH;
   private String F_YPWG;
   private float F_YPSL;
   private String F_SCDW;
   private String F_YPDJ;
   private String F_SCPH;
   private String F_SCRQ;
   private String F_XYLB;
   private String F_WCSJ;
   private String F_WTDWBH;
   private String F_WTDW;
   private String F_WTDWDZ;
   private String F_WTLXFS;
   private String F_LXR;
   private String F_WTDH;
   private String F_YPBH;
   private String F_RWLY;
   private String F_WTSJ;
   private String F_YPSJDW;
   private String F_CYSJ;
   private String F_CYR;
   private String F_CYDD;
   private String F_CYJS;
   private String F_CYHJ;
   private String F_CYQT;
   private String F_CYBZ;
   private String F_JCYJBH;
   private String F_JCYJMC;

   @Override
   public String toString() {
      return "YPWTB{" +
              "F_ID=" + F_ID +
              ", F_JLBH='" + F_JLBH + '\'' +
              ", F_CPBH='" + F_CPBH + '\'' +
              ", F_CPMC='" + F_CPMC + '\'' +
              ", F_CPXH='" + F_CPXH + '\'' +
              ", F_YPWG='" + F_YPWG + '\'' +
              ", F_YPSL=" + F_YPSL +
              ", F_SCDW='" + F_SCDW + '\'' +
              ", F_YPDJ='" + F_YPDJ + '\'' +
              ", F_SCPH='" + F_SCPH + '\'' +
              ", F_SCRQ='" + F_SCRQ + '\'' +
              ", F_XYLB='" + F_XYLB + '\'' +
              ", F_WCSJ='" + F_WCSJ + '\'' +
              ", F_WTDWBH='" + F_WTDWBH + '\'' +
              ", F_WTDW='" + F_WTDW + '\'' +
              ", F_WTDWDZ='" + F_WTDWDZ + '\'' +
              ", F_WTLXFS='" + F_WTLXFS + '\'' +
              ", F_LXR='" + F_LXR + '\'' +
              ", F_WTDH='" + F_WTDH + '\'' +
              ", F_YPBH='" + F_YPBH + '\'' +
              ", F_RWLY='" + F_RWLY + '\'' +
              ", F_WTSJ='" + F_WTSJ + '\'' +
              ", F_YPSJDW='" + F_YPSJDW + '\'' +
              ", F_CYSJ='" + F_CYSJ + '\'' +
              ", F_CYR='" + F_CYR + '\'' +
              ", F_CYDD='" + F_CYDD + '\'' +
              ", F_CYJS='" + F_CYJS + '\'' +
              ", F_CYHJ='" + F_CYHJ + '\'' +
              ", F_CYQT='" + F_CYQT + '\'' +
              ", F_CYBZ='" + F_CYBZ + '\'' +
              ", F_JCYJBH='" + F_JCYJBH + '\'' +
              ", F_JCYJMC='" + F_JCYJMC + '\'' +
              ", F_JCXM='" + F_JCXM + '\'' +
              ", F_FFYD='" + F_FFYD + '\'' +
              ", F_JGYD='" + F_JGYD + '\'' +
              ", F_FBYD='" + F_FBYD + '\'' +
              ", F_YPBC='" + F_YPBC + '\'' +
              ", F_YPCZ='" + F_YPCZ + '\'' +
              ", F_QTYD='" + F_QTYD + '\'' +
              ", F_JSPS='" + F_JSPS + '\'' +
              ", F_JSPSR='" + F_JSPSR + '\'' +
              ", F_CWPS='" + F_CWPS + '\'' +
              ", F_CWSH=" + F_CWSH +
              ", F_CWPSR='" + F_CWPSR + '\'' +
              ", F_JSSH=" + F_JSSH +
              ", F_JSPSSJ='" + F_JSPSSJ + '\'' +
              ", F_CWPSSJ='" + F_CWPSSJ + '\'' +
              ", F_JLR='" + F_JLR + '\'' +
              ", F_JLSJ='" + F_JLSJ + '\'' +
              ", F_HJJE=" + F_HJJE +
              '}';
   }

   private String F_JCXM;
   private String F_FFYD;
   private String F_JGYD;
   private String F_FBYD;
   private String F_YPBC;
   private String F_YPCZ;
   private String F_QTYD;
   private String F_JSPS;
   private String F_JSPSR;
   private String F_CWPS;
   private int F_CWSH;
   private String F_CWPSR;
   private int F_JSSH;
   private String F_JSPSSJ;
   private String F_CWPSSJ;
   private String F_JLR;
   private String F_JLSJ;
   private float F_HJJE;
   public void setF_ID(int F_ID){
      this.F_ID=F_ID;
   }
   public int getF_ID(){
      return F_ID;
   }
   public void setF_JLBH(String F_JLBH){
      this.F_JLBH=F_JLBH;
   }
   public String getF_JLBH(){
      return F_JLBH;
   }
   public void setF_CPBH(String F_CPBH){
      this.F_CPBH=F_CPBH;
   }
   public String getF_CPBH(){
      return F_CPBH;
   }
   public void setF_CPMC(String F_CPMC){
      this.F_CPMC=F_CPMC;
   }
   public String getF_CPMC(){
      return F_CPMC;
   }
   public void setF_CPXH(String F_CPXH){
      this.F_CPXH=F_CPXH;
   }
   public String getF_CPXH(){
      return F_CPXH;
   }
   public void setF_YPWG(String F_YPWG){
      this.F_YPWG=F_YPWG;
   }
   public String getF_YPWG(){
      return F_YPWG;
   }
   public void setF_YPSL(float F_YPSL){
      this.F_YPSL=F_YPSL;
   }
   public float getF_YPSL(){
      return F_YPSL;
   }
   public void setF_SCDW(String F_SCDW){
      this.F_SCDW=F_SCDW;
   }
   public String getF_SCDW(){
      return F_SCDW;
   }
   public void setF_YPDJ(String F_YPDJ){
      this.F_YPDJ=F_YPDJ;
   }
   public String getF_YPDJ(){
      return F_YPDJ;
   }
   public void setF_SCPH(String F_SCPH){
      this.F_SCPH=F_SCPH;
   }
   public String getF_SCPH(){
      return F_SCPH;
   }
   public void setF_SCRQ(String F_SCRQ){
      this.F_SCRQ=F_SCRQ;
   }
   public String getF_SCRQ(){
      return F_SCRQ;
   }
   public void setF_XYLB(String F_XYLB){
      this.F_XYLB=F_XYLB;
   }
   public String getF_XYLB(){
      return F_XYLB;
   }
   public void setF_WCSJ(String F_WCSJ){
      this.F_WCSJ=F_WCSJ;
   }
   public String getF_WCSJ(){
      return F_WCSJ;
   }
   public void setF_WTDWBH(String F_WTDWBH){
      this.F_WTDWBH=F_WTDWBH;
   }
   public String getF_WTDWBH(){
      return F_WTDWBH;
   }
   public void setF_WTDW(String F_WTDW){
      this.F_WTDW=F_WTDW;
   }
   public String getF_WTDW(){
      return F_WTDW;
   }
   public void setF_WTDWDZ(String F_WTDWDZ){
      this.F_WTDWDZ=F_WTDWDZ;
   }
   public String getF_WTDWDZ(){
      return F_WTDWDZ;
   }
   public void setF_WTLXFS(String F_WTLXFS){
      this.F_WTLXFS=F_WTLXFS;
   }
   public String getF_WTLXFS(){
      return F_WTLXFS;
   }
   public void setF_LXR(String F_LXR){
      this.F_LXR=F_LXR;
   }
   public String getF_LXR(){
      return F_LXR;
   }
   public void setF_WTDH(String F_WTDH){
      this.F_WTDH=F_WTDH;
   }
   public String getF_WTDH(){
      return F_WTDH;
   }
   public void setF_YPBH(String F_YPBH){
      this.F_YPBH=F_YPBH;
   }
   public String getF_YPBH(){
      return F_YPBH;
   }
   public void setF_RWLY(String F_RWLY){
      this.F_RWLY=F_RWLY;
   }
   public String getF_RWLY(){
      return F_RWLY;
   }
   public void setF_WTSJ(String F_WTSJ){
      this.F_WTSJ=F_WTSJ;
   }
   public String getF_WTSJ(){
      return F_WTSJ;
   }
   public void setF_YPSJDW(String F_YPSJDW){
      this.F_YPSJDW=F_YPSJDW;
   }
   public String getF_YPSJDW(){
      return F_YPSJDW;
   }
   public void setF_CYSJ(String F_CYSJ){
      this.F_CYSJ=F_CYSJ;
   }
   public String getF_CYSJ(){
      return F_CYSJ;
   }
   public void setF_CYR(String F_CYR){
      this.F_CYR=F_CYR;
   }
   public String getF_CYR(){
      return F_CYR;
   }
   public void setF_CYDD(String F_CYDD){
      this.F_CYDD=F_CYDD;
   }
   public String getF_CYDD(){
      return F_CYDD;
   }
   public void setF_CYJS(String F_CYJS){
      this.F_CYJS=F_CYJS;
   }
   public String getF_CYJS(){
      return F_CYJS;
   }
   public void setF_CYHJ(String F_CYHJ){
      this.F_CYHJ=F_CYHJ;
   }
   public String getF_CYHJ(){
      return F_CYHJ;
   }
   public void setF_CYQT(String F_CYQT){
      this.F_CYQT=F_CYQT;
   }
   public String getF_CYQT(){
      return F_CYQT;
   }
   public void setF_CYBZ(String F_CYBZ){
      this.F_CYBZ=F_CYBZ;
   }
   public String getF_CYBZ(){
      return F_CYBZ;
   }
   public void setF_JCYJBH(String F_JCYJBH){
      this.F_JCYJBH=F_JCYJBH;
   }
   public String getF_JCYJBH(){
      return F_JCYJBH;
   }
   public void setF_JCYJMC(String F_JCYJMC){
      this.F_JCYJMC=F_JCYJMC;
   }
   public String getF_JCYJMC(){
      return F_JCYJMC;
   }
   public void setF_JCXM(String F_JCXM){
      this.F_JCXM=F_JCXM;
   }
   public String getF_JCXM(){
      return F_JCXM;
   }
   public void setF_FFYD(String F_FFYD){
      this.F_FFYD=F_FFYD;
   }
   public String getF_FFYD(){
      return F_FFYD;
   }
   public void setF_JGYD(String F_JGYD){
      this.F_JGYD=F_JGYD;
   }
   public String getF_JGYD(){
      return F_JGYD;
   }
   public void setF_FBYD(String F_FBYD){
      this.F_FBYD=F_FBYD;
   }
   public String getF_FBYD(){
      return F_FBYD;
   }
   public void setF_YPBC(String F_YPBC){
      this.F_YPBC=F_YPBC;
   }
   public String getF_YPBC(){
      return F_YPBC;
   }
   public void setF_YPCZ(String F_YPCZ){
      this.F_YPCZ=F_YPCZ;
   }
   public String getF_YPCZ(){
      return F_YPCZ;
   }
   public void setF_QTYD(String F_QTYD){
      this.F_QTYD=F_QTYD;
   }
   public String getF_QTYD(){
      return F_QTYD;
   }
   public void setF_JSPS(String F_JSPS){
      this.F_JSPS=F_JSPS;
   }
   public String getF_JSPS(){
      return F_JSPS;
   }
   public void setF_JSPSR(String F_JSPSR){
      this.F_JSPSR=F_JSPSR;
   }
   public String getF_JSPSR(){
      return F_JSPSR;
   }
   public void setF_CWPS(String F_CWPS){
      this.F_CWPS=F_CWPS;
   }
   public String getF_CWPS(){
      return F_CWPS;
   }
   public void setF_CWSH(int F_CWSH){
      this.F_CWSH=F_CWSH;
   }
   public int getF_CWSH(){
      return F_CWSH;
   }
   public void setF_CWPSR(String F_CWPSR){
      this.F_CWPSR=F_CWPSR;
   }
   public String getF_CWPSR(){
      return F_CWPSR;
   }
   public void setF_JSSH(int F_JSSH){
      this.F_JSSH=F_JSSH;
   }
   public int getF_JSSH(){
      return F_JSSH;
   }
   public void setF_JSPSSJ(String F_JSPSSJ){
      this.F_JSPSSJ=F_JSPSSJ;
   }
   public String getF_JSPSSJ(){
      return F_JSPSSJ;
   }
   public void setF_CWPSSJ(String F_CWPSSJ){
      this.F_CWPSSJ=F_CWPSSJ;
   }
   public String getF_CWPSSJ(){
      return F_CWPSSJ;
   }
   public void setF_JLR(String F_JLR){
      this.F_JLR=F_JLR;
   }
   public String getF_JLR(){
      return F_JLR;
   }
   public void setF_JLSJ(String F_JLSJ){
      this.F_JLSJ=F_JLSJ;
   }
   public String getF_JLSJ(){
      return F_JLSJ;
   }
   public void setF_HJJE(float F_HJJE){
      this.F_HJJE=F_HJJE;
   }
   public float getF_HJJE(){
      return F_HJJE;
   }
}

