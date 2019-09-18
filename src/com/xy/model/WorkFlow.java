package com.xy.model;
import java.sql.*;
import java.util.Arrays;


/**
     *文件名称: WorkFlow.java
     @创建时间：2019-09-15 18:21:32
     @创建人：XY
     @文件描述：WorkFlow 实体类
     @文件版本: V0.01
     */ 


public class WorkFlow{
	private int F_ID;
	private String F_JLBH;
	private String F_WTDH;
	private String F_WTNR;
	private String F_WTLB;
	private String F_WTDW;
	private String F_WTLXFS;
	private String F_LXR;
	private String F_WTSJ;
	private String F_JLSJ;
	private String F_WTJD;
	private String F_JDBM;
	private int F_SFTJ;
	private String F_TJR;
	private String F_TJSJ;
	private int F_JSPS;
	private String F_JSPSR;
	private String F_JSPSSJ;
	private String F_JSBM;
	private int F_CWPS;
	private String F_CWPSR;
	private Byte[] F_CWBM;
	private String F_CWPSSJ;
	private int F_HTQD;
	private String F_HTQDR;
	private String F_HTSJ;
	private String F_HTBM;
	private int F_RKZT;
	private String F_RKYJR;
	private String F_RKYJBM;
	private String F_RKYJSJ;
	private String F_RKJSR;
	private String F_RKJSBM;
	private String F_RKJSSJ;
	private String F_CKYJR;
	private String F_CKYJSJ;
	private String F_CKYJBM;
	private int F_CKZT;
	private String F_CKJSR;
	private String F_CKJSSJ;
	private String F_CKJSBM;
	private int F_RWZT;
	private String F_RWFPR;
	private String F_RWFPSJ;
	private String F_RWFPBM;
	private String F_RWJSR;
	private String F_RWJSSJ;
	private String F_RWJSBM;
	private int F_YPZT;
	private String F_YPFFBM;
	private String F_YPFFR;
	private String F_YPFFSJ;
	private String F_YPJSBM;
	private String F_YPJSR;
	private String F_YPJSSJ;
	private int F_SJBZT;
	private String F_SJSBBM;
	private String F_SJSBR;
	private String F_SJSBSJ;
	private int F_BGZT;
	private String F_BGSHBM;
	private String F_BGSHR;
	private String F_BGSHSJ;
	private String F_BGPZBM;
	private int F_BGPZZT;
	private String F_BGPZR;
	private String F_BGPZSJ;
	private int F_BGDYZT;

	@Override
	public String toString() {
		return "WorkFlow{" +
				"F_ID=" + F_ID +
				", F_JLBH='" + F_JLBH + '\'' +
				", F_WTDH='" + F_WTDH + '\'' +
				", F_WTNR='" + F_WTNR + '\'' +
				", F_WTLB='" + F_WTLB + '\'' +
				", F_WTDW='" + F_WTDW + '\'' +
				", F_WTLXFS='" + F_WTLXFS + '\'' +
				", F_LXR='" + F_LXR + '\'' +
				", F_WTSJ='" + F_WTSJ + '\'' +
				", F_JLSJ='" + F_JLSJ + '\'' +
				", F_WTJD='" + F_WTJD + '\'' +
				", F_JDBM='" + F_JDBM + '\'' +
				", F_SFTJ=" + F_SFTJ +
				", F_TJR='" + F_TJR + '\'' +
				", F_TJSJ='" + F_TJSJ + '\'' +
				", F_JSPS=" + F_JSPS +
				", F_JSPSR='" + F_JSPSR + '\'' +
				", F_JSPSSJ='" + F_JSPSSJ + '\'' +
				", F_JSBM='" + F_JSBM + '\'' +
				", F_CWPS=" + F_CWPS +
				", F_CWPSR='" + F_CWPSR + '\'' +
				", F_CWBM=" + Arrays.toString(F_CWBM) +
				", F_CWPSSJ='" + F_CWPSSJ + '\'' +
				", F_HTQD=" + F_HTQD +
				", F_HTQDR='" + F_HTQDR + '\'' +
				", F_HTSJ='" + F_HTSJ + '\'' +
				", F_HTBM='" + F_HTBM + '\'' +
				", F_RKZT=" + F_RKZT +
				", F_RKYJR='" + F_RKYJR + '\'' +
				", F_RKYJBM='" + F_RKYJBM + '\'' +
				", F_RKYJSJ='" + F_RKYJSJ + '\'' +
				", F_RKJSR='" + F_RKJSR + '\'' +
				", F_RKJSBM='" + F_RKJSBM + '\'' +
				", F_RKJSSJ='" + F_RKJSSJ + '\'' +
				", F_CKYJR='" + F_CKYJR + '\'' +
				", F_CKYJSJ='" + F_CKYJSJ + '\'' +
				", F_CKYJBM='" + F_CKYJBM + '\'' +
				", F_CKZT=" + F_CKZT +
				", F_CKJSR='" + F_CKJSR + '\'' +
				", F_CKJSSJ='" + F_CKJSSJ + '\'' +
				", F_CKJSBM='" + F_CKJSBM + '\'' +
				", F_RWZT=" + F_RWZT +
				", F_RWFPR='" + F_RWFPR + '\'' +
				", F_RWFPSJ='" + F_RWFPSJ + '\'' +
				", F_RWFPBM='" + F_RWFPBM + '\'' +
				", F_RWJSR='" + F_RWJSR + '\'' +
				", F_RWJSSJ='" + F_RWJSSJ + '\'' +
				", F_RWJSBM='" + F_RWJSBM + '\'' +
				", F_YPZT=" + F_YPZT +
				", F_YPFFBM='" + F_YPFFBM + '\'' +
				", F_YPFFR='" + F_YPFFR + '\'' +
				", F_YPFFSJ='" + F_YPFFSJ + '\'' +
				", F_YPJSBM='" + F_YPJSBM + '\'' +
				", F_YPJSR='" + F_YPJSR + '\'' +
				", F_YPJSSJ='" + F_YPJSSJ + '\'' +
				", F_SJBZT=" + F_SJBZT +
				", F_SJSBBM='" + F_SJSBBM + '\'' +
				", F_SJSBR='" + F_SJSBR + '\'' +
				", F_SJSBSJ='" + F_SJSBSJ + '\'' +
				", F_BGZT=" + F_BGZT +
				", F_BGSHBM='" + F_BGSHBM + '\'' +
				", F_BGSHR='" + F_BGSHR + '\'' +
				", F_BGSHSJ='" + F_BGSHSJ + '\'' +
				", F_BGPZBM='" + F_BGPZBM + '\'' +
				", F_BGPZZT=" + F_BGPZZT +
				", F_BGPZR='" + F_BGPZR + '\'' +
				", F_BGPZSJ='" + F_BGPZSJ + '\'' +
				", F_BGDYZT=" + F_BGDYZT +
				", F_BGDYSJ='" + F_BGDYSJ + '\'' +
				", F_BGDYCS=" + F_BGDYCS +
				", F_BGFFZT=" + F_BGFFZT +
				", F_BGFFBM='" + F_BGFFBM + '\'' +
				", F_BGFFR='" + F_BGFFR + '\'' +
				", F_BGFFSJ='" + F_BGFFSJ + '\'' +
				", F_BGCDZT=" + F_BGCDZT +
				", F_BGCDSJ='" + F_BGCDSJ + '\'' +
				", F_BGCDR='" + F_BGCDR + '\'' +
				", F_BGCDBM='" + F_BGCDBM + '\'' +
				'}';
	}

	private String F_BGDYSJ;
	private int F_BGDYCS;
	private int F_BGFFZT;
	private String F_BGFFBM;
	private String F_BGFFR;
	private String F_BGFFSJ;
	private int F_BGCDZT;
	private String F_BGCDSJ;
	private String F_BGCDR;
	private String F_BGCDBM;
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
	public void setF_WTDH(String F_WTDH){
	this.F_WTDH=F_WTDH;
	}
	public String getF_WTDH(){
		return F_WTDH;
	}
	public void setF_WTNR(String F_WTNR){
	this.F_WTNR=F_WTNR;
	}
	public String getF_WTNR(){
		return F_WTNR;
	}
	public void setF_WTLB(String F_WTLB){
	this.F_WTLB=F_WTLB;
	}
	public String getF_WTLB(){
		return F_WTLB;
	}
	public void setF_WTDW(String F_WTDW){
	this.F_WTDW=F_WTDW;
	}
	public String getF_WTDW(){
		return F_WTDW;
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
	public void setF_WTSJ(String F_WTSJ){
	this.F_WTSJ=F_WTSJ;
	}
	public String getF_WTSJ(){
		return F_WTSJ;
	}
	public void setF_JLSJ(String F_JLSJ){
	this.F_JLSJ=F_JLSJ;
	}
	public String getF_JLSJ(){
		return F_JLSJ;
	}
	public void setF_WTJD(String F_WTJD){
	this.F_WTJD=F_WTJD;
	}
	public String getF_WTJD(){
		return F_WTJD;
	}
	public void setF_JDBM(String F_JDBM){
	this.F_JDBM=F_JDBM;
	}
	public String getF_JDBM(){
		return F_JDBM;
	}
	public void setF_SFTJ(int F_SFTJ){
	this.F_SFTJ=F_SFTJ;
	}
	public int getF_SFTJ(){
		return F_SFTJ;
	}
	public void setF_TJR(String F_TJR){
	this.F_TJR=F_TJR;
	}
	public String getF_TJR(){
		return F_TJR;
	}
	public void setF_TJSJ(String F_TJSJ){
	this.F_TJSJ=F_TJSJ;
	}
	public String getF_TJSJ(){
		return F_TJSJ;
	}
	public void setF_JSPS(int F_JSPS){
	this.F_JSPS=F_JSPS;
	}
	public int getF_JSPS(){
		return F_JSPS;
	}
	public void setF_JSPSR(String F_JSPSR){
	this.F_JSPSR=F_JSPSR;
	}
	public String getF_JSPSR(){
		return F_JSPSR;
	}
	public void setF_JSPSSJ(String F_JSPSSJ){
	this.F_JSPSSJ=F_JSPSSJ;
	}
	public String getF_JSPSSJ(){
		return F_JSPSSJ;
	}
	public void setF_JSBM(String F_JSBM){
	this.F_JSBM=F_JSBM;
	}
	public String getF_JSBM(){
		return F_JSBM;
	}
	public void setF_CWPS(int F_CWPS){
	this.F_CWPS=F_CWPS;
	}
	public int getF_CWPS(){
		return F_CWPS;
	}
	public void setF_CWPSR(String F_CWPSR){
	this.F_CWPSR=F_CWPSR;
	}
	public String getF_CWPSR(){
		return F_CWPSR;
	}
	public void setF_CWBM(Byte[] F_CWBM){
	this.F_CWBM=F_CWBM;
	}
	public Byte[] getF_CWBM(){
		return F_CWBM;
	}
	public void setF_CWPSSJ(String F_CWPSSJ){
	this.F_CWPSSJ=F_CWPSSJ;
	}
	public String getF_CWPSSJ(){
		return F_CWPSSJ;
	}
	public void setF_HTQD(int F_HTQD){
	this.F_HTQD=F_HTQD;
	}
	public int getF_HTQD(){
		return F_HTQD;
	}
	public void setF_HTQDR(String F_HTQDR){
	this.F_HTQDR=F_HTQDR;
	}
	public String getF_HTQDR(){
		return F_HTQDR;
	}
	public void setF_HTSJ(String F_HTSJ){
	this.F_HTSJ=F_HTSJ;
	}
	public String getF_HTSJ(){
		return F_HTSJ;
	}
	public void setF_HTBM(String F_HTBM){
	this.F_HTBM=F_HTBM;
	}
	public String getF_HTBM(){
		return F_HTBM;
	}
	public void setF_RKZT(int F_RKZT){
	this.F_RKZT=F_RKZT;
	}
	public int getF_RKZT(){
		return F_RKZT;
	}
	public void setF_RKYJR(String F_RKYJR){
	this.F_RKYJR=F_RKYJR;
	}
	public String getF_RKYJR(){
		return F_RKYJR;
	}
	public void setF_RKYJBM(String F_RKYJBM){
	this.F_RKYJBM=F_RKYJBM;
	}
	public String getF_RKYJBM(){
		return F_RKYJBM;
	}
	public void setF_RKYJSJ(String F_RKYJSJ){
	this.F_RKYJSJ=F_RKYJSJ;
	}
	public String getF_RKYJSJ(){
		return F_RKYJSJ;
	}
	public void setF_RKJSR(String F_RKJSR){
	this.F_RKJSR=F_RKJSR;
	}
	public String getF_RKJSR(){
		return F_RKJSR;
	}
	public void setF_RKJSBM(String F_RKJSBM){
	this.F_RKJSBM=F_RKJSBM;
	}
	public String getF_RKJSBM(){
		return F_RKJSBM;
	}
	public void setF_RKJSSJ(String F_RKJSSJ){
	this.F_RKJSSJ=F_RKJSSJ;
	}
	public String getF_RKJSSJ(){
		return F_RKJSSJ;
	}
	public void setF_CKYJR(String F_CKYJR){
	this.F_CKYJR=F_CKYJR;
	}
	public String getF_CKYJR(){
		return F_CKYJR;
	}
	public void setF_CKYJSJ(String F_CKYJSJ){
	this.F_CKYJSJ=F_CKYJSJ;
	}
	public String getF_CKYJSJ(){
		return F_CKYJSJ;
	}
	public void setF_CKYJBM(String F_CKYJBM){
	this.F_CKYJBM=F_CKYJBM;
	}
	public String getF_CKYJBM(){
		return F_CKYJBM;
	}
	public void setF_CKZT(int F_CKZT){
	this.F_CKZT=F_CKZT;
	}
	public int getF_CKZT(){
		return F_CKZT;
	}
	public void setF_CKJSR(String F_CKJSR){
	this.F_CKJSR=F_CKJSR;
	}
	public String getF_CKJSR(){
		return F_CKJSR;
	}
	public void setF_CKJSSJ(String F_CKJSSJ){
	this.F_CKJSSJ=F_CKJSSJ;
	}
	public String getF_CKJSSJ(){
		return F_CKJSSJ;
	}
	public void setF_CKJSBM(String F_CKJSBM){
	this.F_CKJSBM=F_CKJSBM;
	}
	public String getF_CKJSBM(){
		return F_CKJSBM;
	}
	public void setF_RWZT(int F_RWZT){
	this.F_RWZT=F_RWZT;
	}
	public int getF_RWZT(){
		return F_RWZT;
	}
	public void setF_RWFPR(String F_RWFPR){
	this.F_RWFPR=F_RWFPR;
	}
	public String getF_RWFPR(){
		return F_RWFPR;
	}
	public void setF_RWFPSJ(String F_RWFPSJ){
	this.F_RWFPSJ=F_RWFPSJ;
	}
	public String getF_RWFPSJ(){
		return F_RWFPSJ;
	}
	public void setF_RWFPBM(String F_RWFPBM){
	this.F_RWFPBM=F_RWFPBM;
	}
	public String getF_RWFPBM(){
		return F_RWFPBM;
	}
	public void setF_RWJSR(String F_RWJSR){
	this.F_RWJSR=F_RWJSR;
	}
	public String getF_RWJSR(){
		return F_RWJSR;
	}
	public void setF_RWJSSJ(String F_RWJSSJ){
	this.F_RWJSSJ=F_RWJSSJ;
	}
	public String getF_RWJSSJ(){
		return F_RWJSSJ;
	}
	public void setF_RWJSBM(String F_RWJSBM){
	this.F_RWJSBM=F_RWJSBM;
	}
	public String getF_RWJSBM(){
		return F_RWJSBM;
	}
	public void setF_YPZT(int F_YPZT){
	this.F_YPZT=F_YPZT;
	}
	public int getF_YPZT(){
		return F_YPZT;
	}
	public void setF_YPFFBM(String F_YPFFBM){
	this.F_YPFFBM=F_YPFFBM;
	}
	public String getF_YPFFBM(){
		return F_YPFFBM;
	}
	public void setF_YPFFR(String F_YPFFR){
	this.F_YPFFR=F_YPFFR;
	}
	public String getF_YPFFR(){
		return F_YPFFR;
	}
	public void setF_YPFFSJ(String F_YPFFSJ){
	this.F_YPFFSJ=F_YPFFSJ;
	}
	public String getF_YPFFSJ(){
		return F_YPFFSJ;
	}
	public void setF_YPJSBM(String F_YPJSBM){
	this.F_YPJSBM=F_YPJSBM;
	}
	public String getF_YPJSBM(){
		return F_YPJSBM;
	}
	public void setF_YPJSR(String F_YPJSR){
	this.F_YPJSR=F_YPJSR;
	}
	public String getF_YPJSR(){
		return F_YPJSR;
	}
	public void setF_YPJSSJ(String F_YPJSSJ){
	this.F_YPJSSJ=F_YPJSSJ;
	}
	public String getF_YPJSSJ(){
		return F_YPJSSJ;
	}
	public void setF_SJBZT(int F_SJBZT){
	this.F_SJBZT=F_SJBZT;
	}
	public int getF_SJBZT(){
		return F_SJBZT;
	}
	public void setF_SJSBBM(String F_SJSBBM){
	this.F_SJSBBM=F_SJSBBM;
	}
	public String getF_SJSBBM(){
		return F_SJSBBM;
	}
	public void setF_SJSBR(String F_SJSBR){
	this.F_SJSBR=F_SJSBR;
	}
	public String getF_SJSBR(){
		return F_SJSBR;
	}
	public void setF_SJSBSJ(String F_SJSBSJ){
	this.F_SJSBSJ=F_SJSBSJ;
	}
	public String getF_SJSBSJ(){
		return F_SJSBSJ;
	}
	public void setF_BGZT(int F_BGZT){
	this.F_BGZT=F_BGZT;
	}
	public int getF_BGZT(){
		return F_BGZT;
	}
	public void setF_BGSHBM(String F_BGSHBM){
	this.F_BGSHBM=F_BGSHBM;
	}
	public String getF_BGSHBM(){
		return F_BGSHBM;
	}
	public void setF_BGSHR(String F_BGSHR){
	this.F_BGSHR=F_BGSHR;
	}
	public String getF_BGSHR(){
		return F_BGSHR;
	}
	public void setF_BGSHSJ(String F_BGSHSJ){
	this.F_BGSHSJ=F_BGSHSJ;
	}
	public String getF_BGSHSJ(){
		return F_BGSHSJ;
	}
	public void setF_BGPZBM(String F_BGPZBM){
	this.F_BGPZBM=F_BGPZBM;
	}
	public String getF_BGPZBM(){
		return F_BGPZBM;
	}
	public void setF_BGPZZT(int F_BGPZZT){
	this.F_BGPZZT=F_BGPZZT;
	}
	public int getF_BGPZZT(){
		return F_BGPZZT;
	}
	public void setF_BGPZR(String F_BGPZR){
	this.F_BGPZR=F_BGPZR;
	}
	public String getF_BGPZR(){
		return F_BGPZR;
	}
	public void setF_BGPZSJ(String F_BGPZSJ){
	this.F_BGPZSJ=F_BGPZSJ;
	}
	public String getF_BGPZSJ(){
		return F_BGPZSJ;
	}
	public void setF_BGDYZT(int F_BGDYZT){
	this.F_BGDYZT=F_BGDYZT;
	}
	public int getF_BGDYZT(){
		return F_BGDYZT;
	}
	public void setF_BGDYSJ(String F_BGDYSJ){
	this.F_BGDYSJ=F_BGDYSJ;
	}
	public String getF_BGDYSJ(){
		return F_BGDYSJ;
	}
	public void setF_BGDYCS(int F_BGDYCS){
	this.F_BGDYCS=F_BGDYCS;
	}
	public int getF_BGDYCS(){
		return F_BGDYCS;
	}
	public void setF_BGFFZT(int F_BGFFZT){
	this.F_BGFFZT=F_BGFFZT;
	}
	public int getF_BGFFZT(){
		return F_BGFFZT;
	}
	public void setF_BGFFBM(String F_BGFFBM){
	this.F_BGFFBM=F_BGFFBM;
	}
	public String getF_BGFFBM(){
		return F_BGFFBM;
	}
	public void setF_BGFFR(String F_BGFFR){
	this.F_BGFFR=F_BGFFR;
	}
	public String getF_BGFFR(){
		return F_BGFFR;
	}
	public void setF_BGFFSJ(String F_BGFFSJ){
	this.F_BGFFSJ=F_BGFFSJ;
	}
	public String getF_BGFFSJ(){
		return F_BGFFSJ;
	}
	public void setF_BGCDZT(int F_BGCDZT){
	this.F_BGCDZT=F_BGCDZT;
	}
	public int getF_BGCDZT(){
		return F_BGCDZT;
	}
	public void setF_BGCDSJ(String F_BGCDSJ){
	this.F_BGCDSJ=F_BGCDSJ;
	}
	public String getF_BGCDSJ(){
		return F_BGCDSJ;
	}
	public void setF_BGCDR(String F_BGCDR){
	this.F_BGCDR=F_BGCDR;
	}
	public String getF_BGCDR(){
		return F_BGCDR;
	}
	public void setF_BGCDBM(String F_BGCDBM){
	this.F_BGCDBM=F_BGCDBM;
	}
	public String getF_BGCDBM(){
		return F_BGCDBM;
	}
}

