<%@ page language="java" contentType="text/html; chaet=utf-8" %>
<%@page import="weaver.general.*" %>
<%@page import="weaver.general.Util"%>
<%@ page language="java" import="java.util.*"%>
<%@page import="net.sf.json.*"%>
<%@page import="com.sap.mw.jco.JCO"%>
<%@page import="com.sap.mw.jco.IFunctionTemplate"%>
<%@ page import="weaver.hrm.*" %>
<%@ page import="java.text.DecimalFormat" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs2" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs3" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs4" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs5" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs6" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="SAPUtil" class="com.jiuyi.util.SAPUtil" scope="page" />

<%
	new BaseBean().writeLog("开票合计  本位币 计算接口 ");
	User user = HrmUserVarify.getUser (request , response) ;
   	JSONArray dt1Arr= new JSONArray();
	JSONObject dataObj = new JSONObject();
	JSONObject dt1Obj = new JSONObject();
   	String requestid = Util.null2String(request.getParameter("requestid"));
   	new BaseBean().writeLog("requestid:"+requestid);

	//获取主表数据
	String sql = "select id from formtable_main_147 where requestid = "+requestid;
	new BaseBean().writeLog("sql=="+sql);
	rs.executeSql(sql);
	rs.next();
	int mainid = rs.getInt("id");//获取明细表mainid
	
	//获取明细表行数数据
	//String sql2 = "select count(*) from formtable_main_147_dt1 where mainid = "+mainid;
	//new BaseBean().writeLog("sql2=="+sql2);
	//rs2.executeSql(sql2);
	//rs2.next();
	//double hangshu = rs.getDouble(1);//明细行数
	
	
	
	//获取明细表8个字段各个的总和数据
	String sql2 = "select SUM(cast(HSDJ as float)) HSDJ,SUM(cast(REPLACE(FKIMG, ',', '') as float)) FKIMG,SUM(cast(BHSDJ as float)) BHSDJ,SUM(cast(REPLACE(NETWR , ',', '') as float)) NETWR,SUM(cast(REPLACE(AMOUNT, ',', '') as float)) AMOUNT,SUM(cast(REPLACE(MWSBP, ',', '') as float)) MWSBP,SUM(cast(ZYBF as float)) ZYBF,SUM(cast(YLZD1 as float)) YLZD1 from formtable_main_147_dt1 where mainid = "+mainid;
	new BaseBean().writeLog("sql2=="+sql2);
	rs2.executeSql(sql2);
	rs2.next();
	double HSDJ = rs2.getDouble("HSDJ");//HSDJ 含税单价
	double FKIMG = rs2.getDouble("FKIMG");//FKIMG 数量
	double BHSDJ = rs2.getDouble("BHSDJ");//BHSDJ不含税单价
	double NETWR = rs2.getDouble("NETWR");//NETWR不含税金额
	double AMOUNT = rs2.getDouble("AMOUNT");//AMOUNT含税金额
	double MWSBP = rs2.getDouble("MWSBP");//MWSBP税额
	double ZYBF = rs2.getDouble("ZYBF");//ZYBF海运费
	double YLZD1 = rs2.getDouble("YLZD1");//YLZD1保险费
	
	new BaseBean().writeLog("HSDJ 含税单价=="+HSDJ);
	new BaseBean().writeLog("FKIMG 数量=="+FKIMG);
	new BaseBean().writeLog("BHSDJ不含税单价=="+BHSDJ);
	new BaseBean().writeLog("NETWR不含税金额=="+NETWR);
	new BaseBean().writeLog("AMOUNT含税金额=="+AMOUNT);
	new BaseBean().writeLog("MWSBP税额=="+MWSBP);
	new BaseBean().writeLog("ZYBF海运费=="+ZYBF);
	new BaseBean().writeLog("YLZD1保险费=="+YLZD1);
	
	DecimalFormat df = new DecimalFormat("#0.00");
	DecimalFormat df1 = new DecimalFormat("#0.0000");
	
	//最终含税单价=含税金额/数量
	double ZZHSDJ = Double.parseDouble(df1.format(AMOUNT/FKIMG));
	//double ZZHSDJ = AMOUNT/FKIMG;
	new BaseBean().writeLog("ZZHSDJ最终含税单价=="+ZZHSDJ);
	//最终不含税单价=不含税金额/数量
	double ZZBHSDJ = Double.parseDouble(df1.format(NETWR/FKIMG));
	//double ZZBHSDJ = NETWR/FKIMG;
	new BaseBean().writeLog("ZZBHSDJ最终不含税单价=="+ZZBHSDJ);
	
	
	
	dt1Obj.put("ZZHSDJ",df1.format( ZZHSDJ ));//最终含税单价
	dt1Obj.put("ZZBHSDJ",df1.format( ZZBHSDJ ));//最终不含税单价
	dt1Obj.put("FKIMG",FKIMG );//数量
	dt1Obj.put("AMOUNT", df.format(AMOUNT));//含税金额
	dt1Obj.put("NETWR",df.format(NETWR ));//不含税金额
	dt1Obj.put("MWSBP", df.format(MWSBP));//税额
	dt1Obj.put("ZYBF",df.format( ZYBF));//海运费
	dt1Obj.put("YLZD1",df.format(YLZD1));//保险费
	
	
	//获取本位币计算信息  明细表   
	double YLZD2= 0.0;//汇率
	double NETWR2= 0.0;//不含税金额
	double AMOUNT2= 0.0;//含税金额
	double MWSBP2= 0.0;//税额
	double ZYBF2= 0.0;//海运费
	double YLZD12= 0.0;//保险费
	
	double ZZNETWR =0.0;//最终本位币不含税金额
	double ZZAMOUNT=0.0;//最终本位币含税金额
	double ZZMWSBP=0.0;//最终本位币税额
	double ZZZYBF=0.0;//最终本位币海运费
	double ZZYLZD1=0.0;//最终本位币保险费
	
	String sql3 = "select YLZD2,REPLACE(NETWR, ',', '') NETWR,REPLACE(AMOUNT, ',', '') AMOUNT,REPLACE(MWSBP, ',', '') MWSBP,ZYBF,YLZD1 from formtable_main_147_dt1 where mainid = "+mainid;
	new BaseBean().writeLog("sql3=="+sql3);
	rs3.executeSql(sql3);
	while(rs3.next()){
		YLZD2 = rs3.getDouble("YLZD2");//汇率
		NETWR2= rs3.getDouble("NETWR");//不含税金额
		ZZNETWR = (YLZD2*NETWR2)+ZZNETWR;//最终本位币不含税金额
		
		AMOUNT2= rs3.getDouble("AMOUNT");//含税金额
		ZZAMOUNT = (AMOUNT2*YLZD2)+ZZAMOUNT;//最终本位币含税金额
		
		MWSBP2= rs3.getDouble("MWSBP");//税额
		ZZMWSBP = (MWSBP2*YLZD2)+ZZMWSBP;//最终本位币税额
		
		
		ZYBF2= rs3.getDouble("ZYBF");//海运费
		ZZZYBF = (ZYBF2*YLZD2)+ZZZYBF;//最终本位币海运费
		
		
		YLZD12= rs3.getDouble("YLZD1");//保险费
		ZZYLZD1 = (YLZD12*YLZD2)+ZZYLZD1;//最终本位币保险费
		
	}
	
	double ZZBWBHSDJ = ZZAMOUNT/FKIMG;//最终本位币含税单价
	double ZZBWBBHSDJ = ZZNETWR/FKIMG;//最终本位币不含税单价
	
	dt1Obj.put("ZZNETWR",df.format( ZZNETWR));//最终本位币不含税金额
	dt1Obj.put("ZZAMOUNT",df.format( ZZAMOUNT));//最终本位币含税金额
	dt1Obj.put("ZZMWSBP",df.format( ZZMWSBP));//最终本位币税额
	dt1Obj.put("ZZZYBF",df.format( ZZZYBF));//最终本位币海运费
	dt1Obj.put("ZZYLZD1",df.format( ZZYLZD1));//最终本位币保险费
	
	dt1Obj.put("ZZBWBHSDJ",df1.format(ZZBWBHSDJ));//最终本位币含税单价
	dt1Obj.put("ZZBWBBHSDJ",df1.format(ZZBWBBHSDJ));//最终本位币不含税单价
	
	
	dt1Arr.add(dt1Obj);

	
	dataObj.put("dt1", dt1Arr);
	out.print(dataObj.toString());
	new BaseBean().writeLog(dataObj.toString());
%>