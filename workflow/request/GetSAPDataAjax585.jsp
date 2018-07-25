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

JCO.Client myConnection = SAPUtil.getSAPcon("1");//BPC数据源 
try{
	new BaseBean().writeLog("读取BPC物料主数据工厂 销售组织 分销渠道接口 ");
	User user = HrmUserVarify.getUser (request , response) ;
   	JSONArray dt1Arr= new JSONArray();
	JSONObject dataObj = new JSONObject();
	JSONObject dt1Obj = new JSONObject();
   	String MATNR1 = Util.null2String(request.getParameter("MATNR1"));//物料编码
   	String gcbh1 = Util.null2String(request.getParameter("gcbh1"));//工厂
   	String MAKTX1 = Util.null2String(request.getParameter("MAKTX1"));//物料描述
   	new BaseBean().writeLog("MATNR1:"+MATNR1);
   	new BaseBean().writeLog("gcbh1:"+gcbh1);
   	new BaseBean().writeLog("MAKTX1:"+MAKTX1);
	myConnection.connect();
	JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
	IFunctionTemplate ft = myRepository.getFunctionTemplate("ZMM_OE008_MATERIAL_GETINFO");
	JCO.Function bapi = ft.getFunction();
	JCO.ParameterList input = bapi.getImportParameterList();
	input.setValue(MATNR1, "IM_MATNR");//物料编码
	input.setValue(gcbh1, "IM_WERKS");//工厂
	input.setValue(MAKTX1, "IM_MAKTX");//物料描述
	myConnection.execute(bapi);
	//获取明细1数据
	JCO.Table IM_ITEM = bapi.getTableParameterList().getTable("IM_ITEM");
	for (int i=0;i<IM_ITEM.getNumRows();i++) {
		IM_ITEM.setRow(i);
		dt1Obj.put("WERKS", IM_ITEM.getString("WERKS"));//工厂
		dt1Obj.put("VKORG", IM_ITEM.getString("VKORG"));//销售组织
		dt1Obj.put("VTWEG", IM_ITEM.getString("VTWEG"));//分销渠道
		
		dt1Arr.add(dt1Obj);
	}
	
	dataObj.put("dt1", dt1Arr);
	out.print(dataObj.toString());
}catch(Exception e){
	
}finally{
	if(myConnection!=null){
		SAPUtil.releaseClient(myConnection);
	}
}
	
%>