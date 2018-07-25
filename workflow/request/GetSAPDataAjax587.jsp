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
	new BaseBean().writeLog("读取BPC供应商主数据银行信息接口 ");
	User user = HrmUserVarify.getUser (request , response) ;
   	JSONArray dt1Arr= new JSONArray();
   	JSONArray dt2Arr= new JSONArray();
	JSONObject dataObj = new JSONObject();
	JSONObject dt1Obj = new JSONObject();
	JSONObject dt2Obj = new JSONObject();
   	String LIFNR1 = Util.null2String(request.getParameter("LIFNR1"));//供应商编码
   	String EKORG1 = Util.null2String(request.getParameter("EKORG1"));//采购组织
   	String BUKRS1 = Util.null2String(request.getParameter("BUKRS1"));//公司代码*
   	new BaseBean().writeLog("LIFNR1:"+LIFNR1);
   	new BaseBean().writeLog("EKORG1:"+EKORG1);
   	new BaseBean().writeLog("BUKRS1:"+BUKRS1);
	myConnection.connect();
	JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
	IFunctionTemplate ft = myRepository.getFunctionTemplate("ZMM_OE013_VENDOR_INFO");
	JCO.Function bapi = ft.getFunction();
	JCO.ParameterList input = bapi.getImportParameterList();
	input.setValue(LIFNR1, "IM_LIFNR");//供应商编码
	input.setValue(EKORG1, "IM_EKORG");//采购组织
	input.setValue(BUKRS1, "IM_BUKRS");//公司代码*
	myConnection.execute(bapi);
	//获取明细1数据
	JCO.Table IT_BANK = bapi.getTableParameterList().getTable("IT_BANK");
	for (int i=0;i<IT_BANK.getNumRows();i++) {
		IT_BANK.setRow(i);
		dt1Obj.put("BANKA", IT_BANK.getString("KOINH"));//银行名称
		dt1Obj.put("BANKN", IT_BANK.getString("BANKN"));//银行帐户号码
		dt1Obj.put("BKREF", IT_BANK.getString("BKREF"));//参考明细
		dt1Arr.add(dt1Obj);
	}
	//获取主表数据
	JCO.Table IT_INFO = bapi.getTableParameterList().getTable("IT_INFO");
	dt2Obj.put("LFB1_LOEVM", IT_INFO.getString("LOEVM_B"));//基于公司代码的删除标记
	dt2Obj.put("LFM1_LOEVM", IT_INFO.getString("LOEVM_M"));//基于采购组织的删除标记
	dt2Arr.add(dt2Obj);
	
	dataObj.put("dt1", dt1Arr);
	dataObj.put("dt2", dt2Arr);
	out.print(dataObj.toString());
}catch(Exception e){
	
}finally{
	if(myConnection!=null){
		SAPUtil.releaseClient(myConnection);
	}
}
	
%>