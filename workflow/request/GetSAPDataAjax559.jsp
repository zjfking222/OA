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
    /**
     * 其他领料
     * @author CYN
     *
     */
    JCO.Client myConnection = SAPUtil.getSAPcon("1");//BPC数据源
    try{
        new BaseBean().writeLog("读取其他领料信息接口 ");
        JSONArray dt1Arr= new JSONArray();
        JSONObject dataObj = new JSONObject();
        JSONObject dt1Obj = new JSONObject();

        String WERKS = Util.null2String(request.getParameter("WERKS"));//合同号
        String MATNR = Util.null2String(request.getParameter("MATNR"));//分合同号
        new BaseBean().writeLog("WERKS:"+WERKS);
        new BaseBean().writeLog("MATNR:"+MATNR);
        myConnection.connect();
        JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
        IFunctionTemplate ft = myRepository.getFunctionTemplate("ZGET_LGPBE"); //SAP函数
        JCO.Function bapi = ft.getFunction();
        JCO.ParameterList input = bapi.getImportParameterList(); //输入参数
        input.setValue(WERKS, "IM_WERKS");//工厂
        input.setValue(MATNR, "IM_MATNR");//物料编码
        myConnection.execute(bapi);
        new BaseBean().writeLog("执行完成");
        JCO.ParameterList OU_LGPBE = bapi.getExportParameterList(); //SAP端反馈参数

        new BaseBean().writeLog("OU_LGPBE==="+OU_LGPBE.getValue("OU_LGPBE"));
        dt1Obj.put("LGPBE",OU_LGPBE.getValue("OU_LGPBE"));
        dt1Arr.add(dt1Obj);
        new BaseBean().writeLog(dt1Arr.toString());
        dataObj.put("dt1", dt1Arr);
        out.print(dataObj.toString());
    }catch(Exception e){
        new BaseBean().writeLog(e.toString());
    }finally{
        if(myConnection!=null){
            SAPUtil.releaseClient(myConnection);
        }
    }

%>