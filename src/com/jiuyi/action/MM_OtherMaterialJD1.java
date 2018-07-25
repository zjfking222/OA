package com.jiuyi.action;


import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class MM_OtherMaterialJD1 implements Action {
	//其他领料维护节点1字段要求 

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		//获取主表的id 工厂编号
		String sql = "select * from "+tablename+" where requestid = "+requestid;
		rs.executeSql(sql);
		rs.next();
		String sap = rs.getString("sap");
		String mainid = rs.getString("id");
		sql = "select * from "+tablename+"_dt1 where mainid = "+mainid;
		rs.executeSql(sql);
		new BaseBean().writeLog("mainid-----"+mainid+"sql="+sql);
		if(!"0".equals(sap)) {
		//客户所选择的工厂的id
		while(rs.next()) {
			Double feixzsy = rs.getDouble("feixzsy");//非限制使用库存
			Double ERFMG = rs.getDouble("ERFMG");//数量
			String id = rs.getString("id");
			
			 if(ERFMG>feixzsy) {
					requestInfo.getRequestManager().setMessage("111100");//提醒消息ID
					requestInfo.getRequestManager().setMessagecontent("明细表数量应小于非限制使用库存！");//提醒消息内容
					return "0";
			 }
		}
		}
		
		
	    
	    
	    
		
		return Action.SUCCESS;
		
		
	
		
	}
	

}
