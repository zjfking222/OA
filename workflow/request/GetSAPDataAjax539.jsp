<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="weaver.general.*" %>
<%@page import="weaver.general.Util"%>
<%@ page language="java" import="java.util.*"%>
<%@page import="net.sf.json.*"%>
<%@page import="com.sap.mw.jco.JCO"%>
<%@page import="com.sap.mw.jco.IFunctionTemplate"%>
<%@ page import="weaver.hrm.*" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="SAPUtil" class="com.jiuyi.util.SAPUtil" scope="page" />

<%
	new BaseBean().writeLog("读取BPC物料停用接口 ");
	User user = HrmUserVarify.getUser (request , response) ;
   	JSONArray dt1Arr= new JSONArray();
   	JSONArray dt2Arr= new JSONArray();
   	JSONArray dt3Arr= new JSONArray();
	JSONObject dataObj = new JSONObject();
	JSONObject dt1Obj = new JSONObject();
	JSONObject dt2Obj = new JSONObject();
	JSONObject dt3Obj = new JSONObject();
   	String MATNR = Util.null2String(request.getParameter("MATNR"));//物料编码
   	new BaseBean().writeLog("MATNR:"+MATNR);
	JCO.Client myConnection = SAPUtil.getSAPcon("4");//BPC数据源 
	myConnection.connect();
	JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
	IFunctionTemplate ft = myRepository.getFunctionTemplate("ZMM_OE011_MATERIAL_INFO_INBOUND");
	JCO.Function bapi = ft.getFunction();
	JCO.ParameterList input = bapi.getImportParameterList();
	input.setValue(MATNR, "IM_MATNR");//物料编码
	myConnection.execute(bapi);
	//获取明细1数据
	JCO.Table IT_ITEM1 = bapi.getTableParameterList().getTable("IT_ITEM1");
	//获取明细2数据
	JCO.Table IT_ITEM2 = bapi.getTableParameterList().getTable("IT_ITEM2");
	//获取明细3数据
	JCO.Table IT_ITEM3 = bapi.getTableParameterList().getTable("IT_ITEM3");
	for (int i=0;i<IT_ITEM1.getNumRows();i++) {
		IT_ITEM1.setRow(i);
		String WERKS = IT_ITEM1.getString("WERKS");//工厂
		String LVOWK = IT_ITEM1.getString("LVOWK");//工厂删除标记
		
		dt1Obj.put("WERKS", IT_ITEM1.getString("WERKS"));//工厂
		dt1Obj.put("LVOWK", IT_ITEM1.getString("LVOWK"));//工厂删除标记
		dt1Arr.add(dt1Obj);
	}
	
	for (int i=0;i<IT_ITEM2.getNumRows();i++) {
		IT_ITEM2.setRow(i);
		String BURKS = IT_ITEM1.getString("BURKS");//库存地点
		String LVOLG = IT_ITEM1.getString("LVOLG");//库存地点删除标记
	
		dt2Obj.put("LGORT", IT_ITEM2.getString("LGORT"));//库存地点
		dt2Obj.put("LVOLG", IT_ITEM2.getString("LVOLG"));//库存地点删除标记
		dt2Arr.add(dt2Obj);
	
	}
	for (int i=0;i<IT_ITEM3.getNumRows();i++) {
		IT_ITEM3.setRow(i);
		String VKORG = IT_ITEM1.getString("VKORG");//销售组织
		String VTWEG = IT_ITEM1.getString("VTWEG");//分销渠道
		String LVOVK = IT_ITEM1.getString("LVOVK");//销售删除标记
	
		dt3Obj.put("VKORG", IT_ITEM3.getString("VKORG"));//销售组织
		dt3Obj.put("VTWEG", IT_ITEM3.getString("VTWEG"));//分销渠道
		dt3Obj.put("LVOVK", IT_ITEM3.getString("LVOVK"));//销售删除标记
		dt3Arr.add(dt3Obj);
	
	
	}
	
	
	dataObj.put("dt1", dt1Arr);
	dataObj.put("dt2", dt2Arr);
	dataObj.put("dt3", dt3Arr);
	if(myConnection!=null){
			SAPUtil.releaseClient(myConnection);
	}
	out.print(dataObj.toString());
	new BaseBean().writeLog(dataObj.toString());
%>