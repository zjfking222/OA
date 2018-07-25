package com.jiuyi.action;


import java.util.Arrays;

import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class FICO_TicketPayJD1 implements Action {
	//资产主数据维护节点4字段要求 

	public String execute(RequestInfo requestInfo) {
		try {
			
		
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		RecordSet rs3 = new RecordSet();
		BaseBean log = new BaseBean();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		//获取主表的id 工厂编号
		String sql = "select * from "+tablename+" where requestid = "+requestid;
		rs.executeSql(sql);
		rs.next();
		String mainid = rs.getString("id");
		int count1 = 0;
		int count2 = 0;
		String fph1="";
		String fph2="";
		
		sql = "select * from "+tablename+"_dt1 where mainid = "+mainid+"";
		rs1.executeSql(sql);
		count1 = rs1.getCounts();
		sql = "select * from "+tablename+"_dt2 where mainid = "+mainid;
		rs2.executeSql(sql);
		count2 = rs2.getCounts();
		new BaseBean().writeLog("mainid-----"+mainid+"sql="+sql);
		//客户所选择的工厂的id
		sql = "select BELNR from "+tablename+"_dt1 where mainid = "+mainid+" group by BELNR order by BELNR";
		rs1.executeSql(sql);
		while(rs1.next()) {
			fph1=fph1+rs1.getString("BELNR");
		}
		
		sql = "select BELNR from "+tablename+"_dt2 where mainid = "+mainid+" group by BELNR order by BELNR";
		rs1.executeSql(sql);
		while(rs2.next()) {
			fph2=fph2+rs2.getString("BELNR");
		}
		
		
		if(rs1.getCounts()!=rs2.getCounts()) {
			requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
			requestInfo.getRequestManager().setMessagecontent("明细1与明细2信息不一致，请修改以后再提交！" );// 提醒信息内容
			return "0";
		}
	    
		} catch (Exception e) {
			e.printStackTrace();
			requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
			requestInfo.getRequestManager().setMessagecontent("接口执行出错！" );// 提醒信息内容
			return "0";
		}
		
		return Action.SUCCESS;
		
		
	
		
	}
	

}
