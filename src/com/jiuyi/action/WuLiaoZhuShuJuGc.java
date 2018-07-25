package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import com.jiuyi.util.SAPUtil;

public class WuLiaoZhuShuJuGc implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		//获取主表的id 工厂编号
		String sql = "select id,factory from "+tablename+" where requestid = "+requestid;
		new BaseBean().writeLog("sql-----"+sql);
		rs.executeSql(sql);
		rs.next();
		//mainid
		int mainid = rs.getInt(1);
		new BaseBean().writeLog("mainid-----"+mainid);
		//客户所选择的工厂的id
		String factoryId = rs.getString(2);
		new BaseBean().writeLog("factoryId-----"+factoryId);
		//拿到主表的工厂编号 查询对应的code
		String sql1 = "select werks from uf_factory where id = "+factoryId;
		new BaseBean().writeLog("sql1-----"+sql1);
		rs1.executeSql(sql1);
		rs1.next();
		//所选择的工厂code
		String werks =rs1.getString(1);
		new BaseBean().writeLog("werks-----"+werks);
		//将主表的工厂code  全部更新到明细表里
		String sql2 = "update "+tablename+"_dt1 set WERKS = '"+factoryId+"' where mainid = "+mainid;
		new BaseBean().writeLog("sql2-----"+sql2);
		rs2.executeSql(sql2);
		
		
		return Action.SUCCESS;
	}

}
