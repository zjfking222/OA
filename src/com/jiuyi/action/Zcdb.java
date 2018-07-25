package com.jiuyi.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.RequestService;

import com.jiuyi.entity.CertificateMainTable;
import com.jiuyi.entity.CertificateParticulars;
import com.jiuyi.entity.MessageInfo;
import com.jiuyi.util.CommonUtil;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.JudgeUserId;
import com.jiuyi.util.PropUtil;
import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * 资产调拨
 * @author 李君莹
 *
 */
public class Zcdb extends BaseAction{
	 public String execute(RequestInfo requestInfo) {
		 RecordSet rs=new RecordSet();
			JCO.Client myConnection =null;
			int error=0;
			String message="";
			String requestid="";
			try{
			String tablename=JiuyiUtil.getTablename(requestInfo);
			requestid=requestInfo.getRequestid();
			String functionName="ZFI_OE022_COST_CENTER_INBOUND";//函数的名字
			
			myConnection =SAPUtil.getSAPcon();
	        myConnection.connect(); 
	        System.out.println("连接SAP成功");
	        JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
	        IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);
	        //從這個函數範本獲得該SAP函數的物件
	        JCO.Function bapi = ft.getFunction();
			//用于设置输入参数
			JCO.ParameterList  parameterList=bapi.getImportParameterList();
			JCO.ParameterList   inputtable= bapi.getTableParameterList();//输入表的处理
			JCO.Table  TAB_ZSFI116=inputtable.getTable("TAB_ZSFI116");
			//JCO.Table  IT_PURCHASE=inputtable.getTable("IT_PURCHASE");
			//JCO.Table  IT_COMPANY=inputtable.getTable("IT_COMPANY");
			//获取输入参数
			//JCO.Structure IS_HEADER = parameterList.getStructure("IS_HEADER");
			//从流程主数据表中查询出结构数据
			String sql="select * from "+tablename+" where requestId="+requestid;
			System.out.println(sql);
			rs.execute(sql);
			String id="";
			String USERNAME = "";
			if(rs.next()){
				id = rs.getString("id");
				USERNAME = CommonUtil.getLastNameByUserId(rs.getString("shenqr"));
			}
			sql="select * from "+tablename+"_dt1 where mainid="+id+" order by id ";
			rs.executeSql(sql);
			String ANLN1="";
			String BUKRS="";
			String KOSTL="";
			String CAUFN="";
			while(rs.next()){
				TAB_ZSFI116.appendRow();
				ANLN1=Util.null2String(rs.getString("ANLNR"));//
				BUKRS=Util.null2String(rs.getString("BUKRS"));//借方记账代码
				KOSTL=Util.null2String(rs.getString("KOSTL_TO"));//借方对应会计科目编号
				CAUFN=Util.null2String(rs.getString("CAUFN_TO"));//内部订单
				KOSTL=Util.null2String(rs.getString("KOSTL"));//成本中心
				//System.out.println(SGTXT+"--"+BSCHL+"--"+SAKNR+"--"+TXT50+"--"+WRBTR+"--"+ZFBDT);

				TAB_ZSFI116.setValue(ANLN1, "ANLN1");
				TAB_ZSFI116.setValue(BUKRS, "BUKRS");
				TAB_ZSFI116.setValue(CAUFN, "CAUFN");
				TAB_ZSFI116.setValue(KOSTL, "KOSTL");
				TAB_ZSFI116.setValue(USERNAME, "USERNAME");
				TAB_ZSFI116.setValue("0", "ANLN2");
			}
			
			myConnection.execute(bapi);
			JCO.ParameterList  outs = bapi.getExportParameterList();//输出参数和结构处理
			JCO.ParameterList  outtab = bapi.getTableParameterList();//输出参数和结构处理
			//String rootpath=GCONST.getRootPath()+"shangqilog\\saplogMM_main04data.html";
			//bapi.writeHTML(rootpath);
			String E_TYPE=(outs.getValue("FLAG")+"").replace("", "").replace("'", "");
			String E_MESSAGE=(outs.getValue("MESSAGE")+"").replace("", "").replace("'", "");
			//String LIFNR=(outs.getValue("LIFNR")+"").replace("", "").replace("'", "");
			
			//message+=" TESTRUN=";
			System.out.println(message);
			if("E".equals(E_TYPE)){
				requestInfo.getRequestManager().setMessage("111100");//提醒信息id
				requestInfo.getRequestManager().setMessagecontent("action执行出错，流程不允许提交到下一节点."+message);//提醒信息内容
//				sql = " update "+tablename+" set EMESSAGE='"+E_MESSAGE+"' where requestid="+requestInfo.getRequestid();
//				rs.executeSql(sql);
				return "0";
			}else{//,BLDAT='"+BLDAT+"'
//				sql = " update "+tablename+" set BELNR='"+E_BELNR+"',EMESSAGE='"+E_MESSAGE+"' where requestid="+requestInfo.getRequestid();
//				rs.executeSql(sql);
			}
			}catch(Exception e){
				message=e.getMessage();
				e.printStackTrace();
				new BaseBean().writeLog("FICO_Expensere"+e);
				error=1;
			}finally{
				if(null!=myConnection){
					SAPUtil.releaseClient(myConnection);
				}
			}
			
			if(error==1){
				requestInfo.getRequestManager().setMessage("111100");//提醒信息id
				requestInfo.getRequestManager().setMessagecontent("action执行出错-流程不允许提交到下一节点."+message);//提醒信息内容
				return "0";
			}else{
				return Action.SUCCESS;
			}
	 }
}
