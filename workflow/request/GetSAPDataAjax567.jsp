<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@page import="weaver.general.Util"%>
<%@page import="java.util.*"%>
<%@page import="com.sap.mw.jco.JCO"%>
<%@page import="weaver.hrm.User"%>
<%@page import="weaver.hrm.HrmUserVarify"%>
<%@page import="com.jiuyi.util.CommonUtil"%>
<%@page import="com.sap.mw.jco.IFunctionTemplate"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.text.*"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSetDataSource" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs2" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="basebean" class="weaver.general.BaseBean" scope="page" />
<jsp:useBean id="CommonUtil" class="com.jiuyi.util.CommonUtil" scope="page" />
<jsp:useBean id="SAPUtil" class="com.jiuyi.util.SAPUtil" scope="page" />
<%
//��Դ�嵥
	response.setHeader("cache-control", "no-cache");
	response.setHeader("pragma", "no-cache");
	response.setHeader("expires", "Mon 1 Jan 1990 00:00:00 GMT");
	JCO.Client myConnection =null;
try{
	User user = HrmUserVarify.checkUser(request,response);
	if(user == null){
		return;
	}
	//StringBuffer json = new StringBuffer();
	JSONObject json = null;
	String operation=Util.null2String(request.getParameter("operation"));
	String requestid=Util.null2String(request.getParameter("requestid"));
	if(operation.equals("htxx")){
		String hth = Util.null2String(request.getParameter("hth"));
		String gongys = Util.null2String(request.getParameter("gongys"));
		myConnection = SAPUtil.getSAPcon();
		myConnection.connect();
		//System.out.println("operation:"+operation); 
		JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
        IFunctionTemplate ft = myRepository.getFunctionTemplate("ZMM_OE023_PUR_CONTRACT_OUTBOUN");
	    JCO.Function function = ft.getFunction();
	     JCO.ParameterList   input=function.getImportParameterList();//��������ͽṹ����
	     input.setValue(hth,"IM_ZYL001");
	     input.setValue(gongys,"IM_LIFNR");
	     myConnection.execute(function);
	     JCO.ParameterList  outpar = function.getExportParameterList();//��������ͽṹ����
		JCO.ParameterList  Table00 = function.getTableParameterList();//�����Ĵ��� 
		JCO.Table IT_ITEM=Table00.getTable("IT_ITEM");//��ͬ��Ϣ
		IT_ITEM.setRow(0);
		Map hashmap = new HashMap();
		hashmap.put("co",IT_ITEM.getString("ZYL017"));//CO����
		hashmap.put("cu",IT_ITEM.getString("ZYL020"));//CU����
		hashmap.put("ni",IT_ITEM.getString("ZYL023"));//NI����
		hashmap.put("ge",IT_ITEM.getString("ZYL026"));//GE����
		hashmap.put("li",IT_ITEM.getString("ZYLO37"));//li����
		hashmap.put("mn",IT_ITEM.getString("ZYLO40"));//Mn����
		json = JSONObject.fromObject(hashmap);
	}
    out.print(json.toString());
}catch(Exception e){
	e.printStackTrace();
}finally{
	if(null!=myConnection){
		SAPUtil.releaseClient(myConnection);
	}
}
%>