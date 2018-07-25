package com.jiuyi.action;


import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class FICO_AssetsDatacreatJD4 implements Action {
	//资产主数据维护节点4字段要求 

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
		
		sql = "select * from "+tablename+"_dt1 where mainid = "+mainid;
		rs.executeSql(sql);
		
		new BaseBean().writeLog("mainid-----"+mainid+"sql="+sql);
		//客户所选择的工厂的id
		while(rs.next()) {
			String NDJAR_01 = rs.getString("NDJAR_01");//计划年使用期
			String ANLKL = rs.getString("ANLKL");//资产分类
			String AFASL="";//折旧码
			String id = rs.getString("id"); 
			
			String zcfl = ANLKL.substring(0,1);
			
			 
			 if("1".equals(zcfl)) {
				 AFASL="Z001";
				 sql = "update "+tablename+"_dt1 set AFASL='"+AFASL+"' where id="+id;
				 new BaseBean().writeLog("update sql1="+sql);
				 rs.execute(sql);
			 }else if ("4".equals(zcfl)) {
				 AFASL="Z002";
				 sql = "update "+tablename+"_dt1 set AFASL='"+AFASL+"' where id="+id;
				 new BaseBean().writeLog("update sql2="+sql);
				 rs.execute(sql);
			 }else {
				 AFASL="Z000";
				 sql = "update "+tablename+"_dt1 set AFASL='"+AFASL+"' where id="+id;
				 new BaseBean().writeLog("update sql3="+sql);
				 rs.execute(sql);
			 }
			 
			 
			 
			 
			 if("1".equals(zcfl)||"4".equals(zcfl)) {
				 if("".equals(NDJAR_01)||NDJAR_01==null) {
					requestInfo.getRequestManager().setMessage("111100");//提醒消息ID
					requestInfo.getRequestManager().setMessagecontent("该资产分类下计划年使用期不能为空！");//提醒消息内容
					return "0";
				 }
			 }
		}
		
		
	    
	    
	    
		
		return Action.SUCCESS;
		
		
	
		
	}
	

}
