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
     * 信用证修改
     * @author CYN
     *
     */
    JCO.Client myConnection = SAPUtil.getSAPcon("1");//BPC数据源
    try{
        new BaseBean().writeLog("读取BPC信用证信息接口 ");
        User user = HrmUserVarify.getUser (request , response) ;
        JSONArray dt1Arr= new JSONArray();
        JSONArray dt2Arr= new JSONArray();
        JSONObject dataObj = new JSONObject();
        JSONObject dt1Obj = new JSONObject();
        JSONObject dt2Obj = new JSONObject();

        String hth = Util.null2String(request.getParameter("hth"));//合同号
        String fhth = Util.null2String(request.getParameter("fhth"));//分合同号
        new BaseBean().writeLog("hth:"+hth);
        new BaseBean().writeLog("fhth:"+fhth);
        myConnection.connect();
        JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
        IFunctionTemplate ft = myRepository.getFunctionTemplate("ZFI_OE009_LC_GETINFO"); //SAP函数
        JCO.Function bapi = ft.getFunction();
        JCO.ParameterList input = bapi.getImportParameterList(); //输入参数
        input.setValue(hth, "IM_ZJK012");//合同号
        input.setValue(fhth, "IM_ZJK024");//分合同号
        myConnection.execute(bapi);

        JCO.Table IT_ZTFI117 = bapi.getTableParameterList().getTable("IT_ZTFI117"); //SAP端反馈表


        for (int i=0;i<IT_ZTFI117.getNumRows();i++) {
            IT_ZTFI117.setRow(i);
            //获取明细数据
            dt1Obj.put("XYZLX",IT_ZTFI117.getString("ZJK001"));
            dt1Obj.put("XYZBH",IT_ZTFI117.getString("ZJK002"));//信用证编码
            dt1Obj.put("CoSL",IT_ZTFI117.getString("ZJK023"));
            dt1Obj.put("CuSL",IT_ZTFI117.getString("ZJK014"));
            dt1Obj.put("NiSL",IT_ZTFI117.getString("ZJK021"));
            dt1Obj.put("GeSL",IT_ZTFI117.getString("ZJK022"));
            dt1Obj.put("LiSL",IT_ZTFI117.getString("ZJKN01"));
            dt1Obj.put("MnSL",IT_ZTFI117.getString("ZJKN02"));

            dt1Arr.add(dt1Obj);
            //获取主表数据
            dt2Obj.put("XYZLX",IT_ZTFI117.getString("ZJK001"));//信用证类型
            dt2Obj.put("XYZBH", IT_ZTFI117.getString("ZJK002"));//信用证编码
            dt2Obj.put("KZH", IT_ZTFI117.getString("ZJK003"));//开证行
            dt2Obj.put("KZDW", IT_ZTFI117.getString("ZJK008"));//开证单位
            dt2Obj.put("GYS", IT_ZTFI117.getString("ZJK010"));//供应商
            dt2Obj.put("HB", IT_ZTFI117.getString("ZJK005"));//货币
            dt2Obj.put("XYZXZ", IT_ZTFI117.getString("ZJK015"));//信用证性质
            dt2Obj.put("DQTS", IT_ZTFI117.getString("ZJK016"));//到期天数
            dt2Obj.put("KZRQ", IT_ZTFI117.getString("ZJK004"));//开证日期
            dt2Obj.put("DQRQ", IT_ZTFI117.getString("ZJK025"));//到期日期
            dt2Obj.put("HL", IT_ZTFI117.getString("ZJK006"));//汇率
            dt2Obj.put("KZJE", IT_ZTFI117.getString("ZJK007"));//开证金额
            dt2Obj.put("KZDWMS", IT_ZTFI117.getString("ZJK009"));//开证单位描述
            dt2Obj.put("GYSMS", IT_ZTFI117.getString("ZJK011"));//供应商描述
            dt2Obj.put("RZXS", IT_ZTFI117.getString("ZJK019"));//融资系数
            dt2Obj.put("JJY", IT_ZTFI117.getString("ZJK020"));//计价月
            dt2Obj.put("SDLF", IT_ZTFI117.getString("ZJK030"));//收代理方
            dt2Arr.add(dt2Obj);
        }
        new BaseBean().writeLog(dt1Arr.toString());

        dataObj.put("dt1", dt1Arr);
        dataObj.put("dt2", dt2Arr);
//        new BaseBean().writeLog(dt2Arr.toString());

        out.print(dataObj.toString());
    }catch(Exception e){
        new BaseBean().writeLog(e.toString());
    }finally{
        if(myConnection!=null){
            SAPUtil.releaseClient(myConnection);
        }
    }

%>