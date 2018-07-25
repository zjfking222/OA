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
	new BaseBean().writeLog("读取BPC客户主数据接口 ");
	User user = HrmUserVarify.getUser (request , response) ;
   	JSONArray dt1Arr= new JSONArray();
   	JSONArray dt2Arr= new JSONArray();
	JSONObject dataObj = new JSONObject();
	JSONObject dt1Obj = new JSONObject();
	JSONObject dt2Obj = new JSONObject();
   	String KUNNR1 = Util.null2String(request.getParameter("KUNNR1"));
   	new BaseBean().writeLog("KUNNR:"+KUNNR1);
	JCO.Client myConnection = SAPUtil.getSAPcon("1");//BPC数据源 
	myConnection.connect();
	JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
	IFunctionTemplate ft = myRepository.getFunctionTemplate("ZSD_OE016_CUS_INFO_INBOUND");
	JCO.Function bapi = ft.getFunction();
	JCO.ParameterList input = bapi.getImportParameterList();
	input.setValue(KUNNR1, "KUNNR");//客户编码
	myConnection.execute(bapi);

	//获取主表数据
	JCO.Structure E_CUSTOMER = bapi.getExportParameterList().getStructure("E_CUSTOMER");
	dt1Obj.put("RLTGR", E_CUSTOMER.getString("RLTGR"));//业务伙伴分组 ZHY001 ZHY002 ZHY003
	dt1Obj.put("KUNNR", E_CUSTOMER.getString("KUNNR"));//客户编码
	dt1Obj.put("NAME1", E_CUSTOMER.getString("NAME1"));//客户名称
	dt1Obj.put("SORT1", E_CUSTOMER.getString("SORT1"));//助记码
	dt1Obj.put("STREET", E_CUSTOMER.getString("STREET"));//地址
	dt1Obj.put("LAND1", E_CUSTOMER.getString("LAND1"));//国家码
	dt1Obj.put("REGIO", E_CUSTOMER.getString("REGIO"));//省份
	dt1Obj.put("STCD1", E_CUSTOMER.getString("STCD1"));//社会统一信用代码
	dt1Obj.put("ZSALES", E_CUSTOMER.getString("ZSALES"));//业务员
	dt1Obj.put("KUKLA", E_CUSTOMER.getString("KUKLA"));//行业分类
	dt1Obj.put("SPERR", E_CUSTOMER.getString("SPERR"));//冻结标示
	dt1Obj.put("EXTENSION2", E_CUSTOMER.getString("EXTENSION2"));//主客户编码 
	
	dt1Obj.put("ZZSHDZ", E_CUSTOMER.getString("ZZSHDZ"));//送货地址 
	dt1Obj.put("ZZSHLXR", E_CUSTOMER.getString("ZZSHLXR"));//送货联系人
	dt1Obj.put("ZZSHDH", E_CUSTOMER.getString("ZZSHDH"));//送货电话
	dt1Obj.put("ZZSHYB", E_CUSTOMER.getString("ZZSHYB"));//送货邮编 
	
	dt1Obj.put("ZZJPDZ", E_CUSTOMER.getString("ZZJPDZ"));//寄票地址
	dt1Obj.put("ZZJPLXR", E_CUSTOMER.getString("ZZJPLXR"));//寄票联系人
	dt1Obj.put("ZZJPDH", E_CUSTOMER.getString("ZZJPDH"));//寄票电话
	dt1Obj.put("ZZJPYB", E_CUSTOMER.getString("ZZJPYB"));//寄票邮编
	
	
	dt1Obj.put("KOINH", E_CUSTOMER.getString("KOINH"));//开户行名称
	dt1Obj.put("BANKN", E_CUSTOMER.getString("BANKN"));//开户账号
	dt1Obj.put("TELF1", E_CUSTOMER.getString("TELF1"));//开票电话
	dt1Arr.add(dt1Obj);

	//获取明细1数据
	JCO.Table IT_ITEM2 = bapi.getTableParameterList().getTable("BUKRS");
	for (int i=0;i<IT_ITEM2.getNumRows();i++) {
		IT_ITEM2.setRow(i);
		dt2Obj.put("BUKRS", IT_ITEM2.getString("BUKRS"));//公司代码
		dt2Arr.add(dt2Obj);
	
	}
	
	
	dataObj.put("dt1", dt1Arr);
	dataObj.put("dt2", dt2Arr);
	if(myConnection!=null){
			SAPUtil.releaseClient(myConnection);
	}
	out.print(dataObj.toString());
	new BaseBean().writeLog(dataObj.toString());
%>