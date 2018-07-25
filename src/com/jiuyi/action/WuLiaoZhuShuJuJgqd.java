package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
/**
 * 物料主数据价格确定
 * @author yangc
 *
 */
public class WuLiaoZhuShuJuJgqd implements Action{

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		new BaseBean().writeLog("物料主数据价格确定-----"+requestid);
		
		String sql = "select id from "+tablename+" where requestid = "+requestid;
		rs.executeSql(sql);
		rs.next();
		int mainid = rs.getInt(1);
		new BaseBean().writeLog("mainid-----"+mainid);
		
		String sql1 = "update "+tablename+"_dt1 set MLAST = '1' where VPRSV = '0' and mainid = "+mainid;
		String sql2 = "update "+tablename+"_dt1 set MLAST = '0' where VPRSV = '1' and mainid = "+mainid;
		rs1.executeSql(sql1);
		rs2.executeSql(sql2);
		new BaseBean().writeLog("sql1-----"+sql1);
		new BaseBean().writeLog("sql2-----"+sql2);
		
		
		return Action.SUCCESS;
	}

}
