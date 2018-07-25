package com.jiuyi.action;

import com.eosdata.service.EosDataLocator;
import com.eosdata.service.EosDataSoap_PortType;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;
/**
 * 停用US物料主数据
 * @author 李君莹
 *
 */
public class MaterialDelete  extends BaseAction{
	 public String execute(RequestInfo requestInfo) {
		 	RecordSet rs=new RecordSet();
		 	RecordSet rs1=new RecordSet();
		 	String workflowid=requestInfo.getWorkflowid();
			int error=0;
			String message = "";
			String requestid = "";
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			 try{
				String tablename=JiuyiUtil.getTablename(requestInfo);
				requestid=requestInfo.getRequestid();
				String sql="select * from "+tablename+" where requestId="+requestid;
				rs.execute(sql);
				while(rs.next()){
					xml+="<item>";
					xml+="<!--物品编码,必填-->  ";
					xml+="<WPH>"+Util.null2String(rs.getString("MATNR"))+"</WPH> "; 
					new BaseBean().writeLog("<WPH>"+Util.null2String(rs.getString("MATNR"))+"</WPH> ");
					xml+="</item>";
				}
				new BaseBean().writeLog("xml======"+xml);
				EosDataLocator locator = new EosDataLocator();
		        EosDataSoap_PortType service = locator.getEosDataSoap();
		        message = service.materielDelete(xml);
			 }catch(Exception e){
				message=e.getMessage();
				e.printStackTrace();
				new BaseBean().writeLog("cmztj"+e);
				error=1;
			}finally{
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
