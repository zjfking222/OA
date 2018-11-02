package com.jiuyi.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;

import com.jiuyi.util.CommonUtil;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

/**
 * 资产调拨流程
 * @author yangc
 *
 */
public class ZiChanTiaoBo extends BaseAction {

	@Override
	public String execute(RequestInfo requestInfo) {
		JCO.Client myConnection = null;
		int error = 0;
		String message = "";
		try{
			new BaseBean().writeLog("资产调拨流程--"+requestInfo.getRequestid());
			String functionName = "ZFI_OE022_COST_CENTER_INBOUND";//SAP函数名
			myConnection = SAPUtil.getSAPcon();//获取SAP连接
			myConnection.connect();
			JCO.Repository myRepository = new JCO.Repository("myRepository", myConnection);
			IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);
			JCO.Function bapi = ft.getFunction();// 创建连接对象
			//JCO.ParameterList parameterList = bapi.getImportParameterList();// 声明传入参数与结构对象
			ParameterList table = bapi.getTableParameterList();
			Table TAB_ZSFI116 = table.getTable("TAB_ZSFI116");//开票信息
			String tablename = JiuyiUtil.getTablename(requestInfo);
			RecordSet rs = new RecordSet();
			RecordSet rs1 = new RecordSet();
			int mainid = 0;
			//获取申请人id
			String shenqrId = "";
			//获取申请人
			String shenqr="";
			
			
			String ANLNR = "";
			String BUKRS = "";
			String KOSTL_TO = "";
			String CAUFN_FROM = "";
			String yxksrq = "";
			
			String sql = "select * from "+tablename+" where requestid="+requestInfo.getRequestid();//获取主表数据
			rs.executeSql(sql);
			new BaseBean().writeLog("sql--"+sql);
			if(rs.next()){
				mainid = rs.getInt("id");
				
				shenqrId = rs.getString("shenqr");//
				//获取申请人
				shenqr= CommonUtil.getLastNameByUserId(shenqrId);
				new BaseBean().writeLog("获取申请人--"+shenqr);
			}
			
			
			String sql1 = "select * from "+tablename+"_dt1 where mainid="+mainid;//获取明细表数据
			rs1.executeSql(sql1);
			new BaseBean().writeLog("sql1--"+sql1);
			while(rs1.next()){
				ANLNR = rs1.getString("ANLNR");//资产编号
				BUKRS = rs1.getString("BUKRS");//公司代码
				KOSTL_TO =rs1.getString("KOSTL_TO");//成本中心
				CAUFN_FROM = rs1.getString("CAUFN_TO");//内部订单
				yxksrq =rs1.getString("yxksrq");//有效开始日期
				new BaseBean().writeLog("ANLNR--"+ANLNR);
				new BaseBean().writeLog("BUKRS--"+BUKRS);
				new BaseBean().writeLog("KOSTL_TO--"+KOSTL_TO);
				new BaseBean().writeLog("CAUFN_FROM--"+CAUFN_FROM);
				new BaseBean().writeLog("yxksrq--"+yxksrq);
				
				
				TAB_ZSFI116.appendRow();
				
				TAB_ZSFI116.setValue(shenqr, "USERNAME");//申请人
				TAB_ZSFI116.setValue("0000", "ANLN2");//
				
				TAB_ZSFI116.setValue(ANLNR, "ANLN1");//资产编号
				TAB_ZSFI116.setValue(BUKRS, "BUKRS");//公司代码
				TAB_ZSFI116.setValue(KOSTL_TO, "KOSTL");//成本中心
				TAB_ZSFI116.setValue(CAUFN_FROM, "CAUFN");//内部订单
				TAB_ZSFI116.setValue(yxksrq, "ADATU_FROM");//有效开始日期
			}
			
			myConnection.execute(bapi);// 所有输入数据传输后，执行函数
			JCO.ParameterList outs = bapi.getExportParameterList();// 输出参数与机构集合
			String E_TYPE = outs.getString("FLAG");
			String E_MESSAGE = outs.getString("MESSAGE");
			new BaseBean().writeLog("e_type:"+E_TYPE+",E_MESSAGE:"+E_MESSAGE);
			if (!ANLNR.equals("")){
				if ("E".equals(E_TYPE)) {
					requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
					requestInfo.getRequestManager().setMessagecontent("执行出错，SAP返回错误信息." + E_MESSAGE);// 提醒信息内容
					return "0";
				}
			}else{return Action.SUCCESS;}
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
