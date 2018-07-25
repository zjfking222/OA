package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/***
 * 物料主数据创建90其他
 * @author yangc
 *
 */
public class WuLiao90QiTa  implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		
		String sql = "select id from "+tablename+" where requestid="+requestid;
		new BaseBean().writeLog("sql---"+sql);
		rs.executeSql(sql);
		rs.next();
		int mainid = rs.getInt(1);
		new BaseBean().writeLog("mainid---"+mainid);
		
		String sql1 = "update "+tablename+"_dt1 set SPART='90' where mainid="+mainid;
		new BaseBean().writeLog("sql1---"+sql1);
		rs1.executeSql(sql1);
		
		
		return Action.SUCCESS;
	}
	
}
