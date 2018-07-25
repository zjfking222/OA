package com.jiuyi.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;

import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

/**
 * 客户主数据维护流程
 * @author yangc
 *
 */
public class CustomerMasterDataMaintenance extends BaseAction {
	
	@Override
	public String execute(RequestInfo requestInfo) {
		JCO.Client myConnection = null;
		int error = 0;
		String message = "";
		try{
			new BaseBean().writeLog("客户主数据维护流程--"+requestInfo.getRequestid());
			String functionName = "ZSD_OE017_CUS_BP_INBOUND";//SAP函数名
			myConnection = SAPUtil.getSAPcon();//获取SAP连接
			myConnection.connect();
			JCO.Repository myRepository = new JCO.Repository("myRepository", myConnection);
			IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);
			JCO.Function bapi = ft.getFunction();// 创建连接对象
			JCO.ParameterList parameterList = bapi.getImportParameterList();// 声明传入参数与结构对象
			JCO.ParameterList input = bapi.getImportParameterList();
			JCO.Structure IM_HEADER = parameterList.getStructure("IM_HEADER");//输入结构
			ParameterList table = bapi.getTableParameterList();
			Table IT_ITEM1 = table.getTable("IT_ITEM1");//开票信息
			Table IT_ITEM2 = table.getTable("IT_ITEM2");//公司信息
			Table IT_ITEM3 = table.getTable("IT_ITEM3");//送货信息
			Table IT_ITEM4 = table.getTable("IT_ITEM4");//寄货信息
			String tablename = JiuyiUtil.getTablename(requestInfo);
			RecordSet rs = new RecordSet();
			RecordSet rs1 = new RecordSet();
			RecordSet rs4 = new RecordSet();
			RecordSet rs5 = new RecordSet();
			RecordSet rs2 = new RecordSet();
			RecordSet rs3 = new RecordSet();
			int mainid = 0;
			String RLTGR = "";
			String FLAG = "";
			String KUNNR = "";
			String KUNNR1 = "";
			String NAME1 = "";
			String SORT1 = "";
			String STREET = "";
			String LAND1 = "";
			String REGIO = "";
			String STCD1 = "";
			String ZSALES = "";
			String KUKLA = "";
			String SPERR = "";
			String EXTENSION2 = "";
			String sql = "select * from "+tablename+" where requestid="+requestInfo.getRequestid();//获取OA数据
			rs.executeSql(sql);
			if(rs.next()){
				mainid = rs.getInt("id");
				RLTGR = rs.getString("RLTGR");//业务伙伴分组 ZHY001 ZHY002 ZHY003
				KUNNR = rs.getString("KUNNR");//客户编码
				NAME1 = rs.getString("NAME1");//客户名称 OA测试123
				SORT1 = rs.getString("SORT1");//助记码  OA TEST1
				STREET = rs.getString("STREET");//地址 测试地址
				LAND1 = rs.getString("LAND1");//国家码 CN
				REGIO = rs.getString("REGIO");//省份 11
				STCD1 = rs.getString("STCD1");//社会统一信用代码 oa001
				ZSALES = rs.getString("ZSALES");//业务员
				KUKLA = rs.getString("KUKLA");//行业分类 01
				SPERR = rs.getString("SPERR");//冻结标示 
				EXTENSION2 = rs.getString("EXTENSION2");//主客户编码  TEST MAIN CUS
				
				new BaseBean().writeLog("客户编码:"+KUNNR);
				new BaseBean().writeLog("客户编码1:"+KUNNR1);
				new BaseBean().writeLog("客户名称:"+NAME1);
				new BaseBean().writeLog("助记码:"+SORT1);
				new BaseBean().writeLog("地址:"+STREET);
				new BaseBean().writeLog("国家码:"+LAND1);
				new BaseBean().writeLog("省份:"+REGIO);
				new BaseBean().writeLog("社会统一信用代码:"+STCD1);
				new BaseBean().writeLog("业务员:"+ZSALES);
				new BaseBean().writeLog("行业分类:"+KUKLA);
				new BaseBean().writeLog("冻结标示:"+SPERR);
				new BaseBean().writeLog("主客户编码:"+EXTENSION2);


				IM_HEADER.setValue(KUNNR, "KUNNR");//客户编码
				IM_HEADER.setValue(NAME1, "NAME1");//客户名称
				IM_HEADER.setValue(SORT1, "SORT1");//助记码
				IM_HEADER.setValue(STREET, "STREET");//地址
				IM_HEADER.setValue(LAND1, "LAND1");//国家码
				IM_HEADER.setValue(REGIO, "REGIO");//省份
				IM_HEADER.setValue(STCD1, "STCD1");//社会统一信用代码
				IM_HEADER.setValue(ZSALES, "ZSALES");//业务员
				IM_HEADER.setValue(KUKLA, "KUKLA");//行业分类
				IM_HEADER.setValue(SPERR, "SPERR");//冻结标示
				IM_HEADER.setValue(EXTENSION2, "EXTENSION2");//主客户编码
				
				
//				ZGET_KUKLA
			}
			//开票信息
			String sql2 = "select * from "+tablename+"_dt1 where mainid = "+mainid;
			new BaseBean().writeLog("开票信息sql:"+sql2);
			rs2.execute(sql2);
			//int i = 0;
			while(rs2.next()){
				IT_ITEM1.appendRow();
				//IT_ITEM1.setRow(i);
				IT_ITEM1.setValue(rs2.getString("KOINH"), "KOINH");//开户行名称 中国银行
				IT_ITEM1.setValue(rs2.getString("BANKN"), "BANKN");//开户账号  323131414141
				IT_ITEM1.setValue(rs2.getString("TELF1"), "TELF1");//开票电话 12345678
				//i++;
			}
			//取公司信息
			String sql3 = "select * from "+tablename+"_dt2 where mainid='"+mainid;
			new BaseBean().writeLog("取公司信息sql:"+sql3);
			rs3.execute(sql3);
			//i = 0;
			while(rs3.next()){
				IT_ITEM2.appendRow();
				//IT_ITEM2.setRow(i);
				IT_ITEM2.setValue(rs3.getString("BUKRS"), "BUKRS");//公司代码  3020  1020
				//i++;
			}
			//取送货信息
			String sql4 = "select * from "+tablename+"_dt3 where mainid='"+mainid;
			new BaseBean().writeLog("取送货信息sql:"+sql4);
			rs4.execute(sql4);
			//i = 0;
			while(rs4.next()){
				IT_ITEM3.appendRow();
				//IT_ITEM3.setRow(i);
				IT_ITEM3.setValue(rs4.getString("ZZSHDZ"), "ZZSHDZ");//送货地址 SHDZ1122
				IT_ITEM3.setValue(rs4.getString("ZZSHLXR"), "ZZSHLXR");//送货联系人 SHLXR1122
				IT_ITEM3.setValue(rs4.getString("ZZSHDH"), "ZZSHDH");//送货电话 SHDH
				IT_ITEM3.setValue(rs4.getString("ZZSHYB"), "ZZSHYB");//送货邮编 SHYB
				//i++;
			}
			//取寄货信息
			String sql5 = "select * from "+tablename+"_dt4 where mainid='"+mainid;
			new BaseBean().writeLog("取寄货信息sql:"+sql5);
			rs5.execute(sql5);
			//i = 0;
			while(rs5.next()){
				IT_ITEM4.appendRow();
				//IT_ITEM4.setRow(i);
				IT_ITEM4.setValue(rs5.getString("ZZJPDZ"), "ZZJPDZ");//寄票地址JPDZ
				IT_ITEM4.setValue(rs5.getString("ZZJPLXR"), "ZZJPLXR");//寄票联系人JPLXR
				IT_ITEM4.setValue(rs5.getString("ZZJPDH"), "ZZJPDH");//寄票电话JPDH
				IT_ITEM4.setValue(rs5.getString("ZZJPYB"), "ZZJPYB");//寄票邮编JPYB
				//i++;
			}
			myConnection.execute(bapi);// 所有输入数据传输后，执行函数
			JCO.ParameterList outs = bapi.getExportParameterList();// 输出参数与机构集合
			String E_TYPE = outs.getString("E_TYPE");
			String E_MESSAGE = outs.getString("E_MESSAGE");
			new BaseBean().writeLog("e_type:"+E_TYPE+",E_MESSAGE:"+E_MESSAGE);
			if ("E".equals(E_TYPE)) {	
				requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
				requestInfo.getRequestManager().setMessagecontent("执行出错，SAP返回错误信息." + E_MESSAGE);// 提醒信息内容
				return "0";
			}
		}catch(Exception e){
			message=e.getMessage();
		    e.printStackTrace();
		    new BaseBean().writeLog("SAP异常" + e);
		    error=1;
		}finally{
			if (myConnection != null) {
	    		SAPUtil.releaseClient(myConnection);//关闭连接池
			}
		}
		
		if(error==1){
	    	requestInfo.getRequestManager().setMessageid("111100");//提醒信息id
	    	requestInfo.getRequestManager().setMessagecontent("action执行出错-流程不允许提交到下一节点."+message);//提醒信息内容
			return "0";
		}else{
			return Action.SUCCESS;
		}
	}
}
