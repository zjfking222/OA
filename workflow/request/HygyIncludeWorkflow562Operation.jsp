<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="weaver.general.*" %>
<%@page import="com.jiuyi.util.*"%>
<%@ page language="java" import="java.util.*"%>
<%@page import="net.sf.json.*"%>
<%@page import="weaver.formmode.setup.*"%>
<%@page import="com.sap.mw.jco.JCO"%>
<%@page import="com.sap.mw.jco.IFunctionTemplate"%>
<%@ page import="weaver.hrm.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />

<%	
	String ksrq = Util.null2String(request.getParameter("ksrq"));//开始日期
	String jsrq = Util.null2String(request.getParameter("jsrq"));//结束日期
	String xqzxm = Util.null2String(request.getParameter("xqzxm"));//需求者/请求者姓名
	String wzlb = Util.null2String(request.getParameter("wzlb"));//物资类别
	String WERKS = Util.null2String(request.getParameter("werks1"));//工厂
	String KOSTL = Util.null2String(request.getParameter("kostl1"));//成本中心
	String MATNR = Util.null2String(request.getParameter("matnr1"));//物料编码
	String sql = "";
	new BaseBean().writeLog("开始日期:"+ksrq);
	new BaseBean().writeLog("结束日期:"+jsrq);
	new BaseBean().writeLog("需求者/请求者姓名:"+xqzxm);
	new BaseBean().writeLog("物资类别:"+wzlb);
	new BaseBean().writeLog("工厂:"+WERKS);
	new BaseBean().writeLog("成本中心:"+KOSTL);
	new BaseBean().writeLog("物料编码:"+MATNR);
	JSONArray dt1Arr= new JSONArray();
	JSONObject dataObj = new JSONObject();
	JSONObject dt1Obj = new JSONObject();
	
	if(MATNR.equals(null)){
		sql = "select t1.KNTTP ,t1.EPSTP ,t1.MATNR ,t1.MAKTX ,t1.MENGE ,t1.MEINS ,t1.PREIS ,t1.WAERS ,t1.xj ,t1.MATKL ,t1.WERKS ,t1.LGORT ,t1.EKGRP ,t1.AFNAM ,t1.BEDNR ,t1.KOSTL ,t1.ANLN1 ,t1.AUFNR ,t1.LFDAT ,t1.gysHzqr ,t1.cjrq ,t1.cjrq ,t1.clzt ,t1.TEXT_LINE ,t2.id from formtable_main_174_dt1 t1 inner join formtable_main_174 t2 on t1.mainid = t2.id where t2.wzlb = '"+wzlb+"' and t1.cjrq = '"+ksrq+"' and t1.LFDAT = '"+jsrq+"' and t1.AFNAM = '"+xqzxm+"' and t1.WERKS = '"+WERKS+"' and t1.KOSTL = '"+KOSTL+"'";
		new BaseBean().writeLog("sql MATNR=null---:"+sql);
		
	}else{
		sql = "select t1.KNTTP ,t1.EPSTP ,t1.MATNR ,t1.MAKTX ,t1.MENGE ,t1.MEINS ,t1.PREIS ,t1.WAERS ,t1.xj ,t1.MATKL ,t1.WERKS ,t1.LGORT ,t1.EKGRP ,t1.AFNAM ,t1.BEDNR ,t1.KOSTL ,t1.ANLN1 ,t1.AUFNR ,t1.LFDAT ,t1.gysHzqr ,t1.cjrq ,t1.cjrq ,t1.clzt ,t1.TEXT_LINE ,t2.id from formtable_main_174_dt1 t1 inner join formtable_main_174 t2 on t1.mainid = t2.id where t2.wzlb = '"+wzlb+"' and t1.cjrq = '"+ksrq+"' and t1.LFDAT = '"+jsrq+"' and t1.AFNAM = '"+xqzxm+"' and t1.WERKS = '"+WERKS+"' and t1.KOSTL = '"+KOSTL+"' and t1.MATNR = '"+MATNR+"'";
		new BaseBean().writeLog("sql MATNR!=null---:"+sql);
	}
	
	rs.executeSql(sql);
	while(rs.next()){
		dt1Obj.put("KNTTP",rs.getString(1));//科目分配类别
		dt1Obj.put("EPSTP",rs.getString(2));//项目类别
		dt1Obj.put("MATNR",rs.getString(3));//物料编码
		dt1Obj.put("MAKTX",rs.getString(4));//物料描述
		dt1Obj.put("MENGE",rs.getString(5));//数量
		dt1Obj.put("MEINS",rs.getString(6));//单位
		dt1Obj.put("PREIS",rs.getString(7));//价格
		dt1Obj.put("WAERS",rs.getString(8));//货币码 
		dt1Obj.put("xj",rs.getString(9));//小计金额
		dt1Obj.put("MATKL",rs.getString(10));//物料组 
		dt1Obj.put("WERKS",rs.getString(11));//工厂
		dt1Obj.put("LGORT",rs.getString(12));//库存地点
		dt1Obj.put("EKGRP",rs.getString(13));//采购组 
		dt1Obj.put("AFNAM",rs.getString(14));//需求者/请求者姓名 
		dt1Obj.put("BEDNR",rs.getString(15));//需求跟踪号
		dt1Obj.put("KOSTL",rs.getString(16));//成本中心
		dt1Obj.put("ANLN1",rs.getString(17));//主资产号
		dt1Obj.put("AUFNR",rs.getString(18));//订单号 
		dt1Obj.put("LFDAT",rs.getString(19));//交货日期
		dt1Obj.put("gysHzqr",rs.getString(20));//供应商或债权人的帐号
		dt1Obj.put("cjrq",rs.getString(21));//创建日期
		dt1Obj.put("kc",rs.getString(22));//库存
		dt1Obj.put("clzt",rs.getString(23));//处理状态
		dt1Obj.put("TEXT_LINE",rs.getString(24));//备注
		dt1Obj.put("ydmxId",rs.getInt(25));//月度明细id
		dt1Arr.add(dt1Obj);
	}

	dataObj.put("dt1",dt1Arr);

	out.print(dataObj.toString());



%>