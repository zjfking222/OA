package com.jiuyi.action;


import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class MM_MonthlyAssessmentbp implements Action {
	//国内月度需求计划评估流程字段更新 

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		//获取主表的id 工厂编号
		String sql = "select * from "+tablename+" where requestid = "+requestid;
		rs.executeSql(sql);
		rs.next();
		//mainid
		String mainid = rs.getString("id");
		new BaseBean().writeLog("mainid-----"+mainid);
		//客户所选择的工厂的id
		String BSART1 = rs.getString("BSART1");//月度采购申请凭证类型
		String BSART2= rs.getString("BSART2");//零星采购申请凭证类型
		String BSART = rs.getString("BSART");//采购申请凭证类型号
	
		

		
		
		
	    if("0".equals(BSART1)) {
	    	BSART="1001";
	    }else if("1".equals(BSART1)) {
	    	BSART="1002";
	    }else if("3".equals(BSART1)) {
	    	BSART="1004";
	    }else if("0".equals(BSART2)) {
	    	BSART="1005";
	    }else if("1".equals(BSART2)) {
	    	BSART="1006";
	    }else if("3".equals(BSART2)) {
	    	BSART="1008";
	    }
	    sql = "update "+tablename+" set BSART='"+BSART+"' where requestid="+requestid;
		rs.executeSql(sql);
	    
		
		
		
		
	
		
		return Action.SUCCESS;
	}
	

}
