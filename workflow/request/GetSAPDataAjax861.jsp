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
     * 报价
     * @author CYN
     *
     */
    JCO.Client myConnection = SAPUtil.getSAPcon("1");//BPC数据源
    try{
        new BaseBean().writeLog("读取BPC信用证信息接口 ");
        JSONArray dt1Arr= new JSONArray();
        JSONObject dataObj = new JSONObject();
        JSONObject dt1Obj = new JSONObject();


        String khbm = Util.null2String(request.getParameter("khbm"));//客户编码
        new BaseBean().writeLog("khbm:"+khbm);
        myConnection.connect();
        JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
        IFunctionTemplate ft = myRepository.getFunctionTemplate("ZSD_OE020_GET_OVERDUE"); //SAP函数
        JCO.Function bapi = ft.getFunction();
        JCO.ParameterList input = bapi.getImportParameterList(); //输入参数
        new BaseBean().writeLog("khbm==="+khbm);
        input.setValue(khbm, "KUNNR");//客户编码

        myConnection.execute(bapi);

        JCO.ParameterList output = bapi.getExportParameterList(); //输出参数

        dt1Obj.put("ZYQJE",output.getValue("ZYQJE"));//逾期金额
        dt1Obj.put("ZYSJE",output.getValue("ZYSJE"));//应收金额
        dt1Arr.add(dt1Obj);

        new BaseBean().writeLog("输出output"+bapi.getExportParameterList());
        new BaseBean().writeLog("输出dt1Arr"+dt1Arr.toString());

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