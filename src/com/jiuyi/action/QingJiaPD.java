package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 
 * 年休假定额  调休定额判断 
 * @author yang7
 *
 */
public class QingJiaPD implements Action{

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		
		String sql = "select nianxjde,txde from "+tablename+" where requestid = "+requestid;
		new BaseBean().writeLog("sql------"+sql);
		rs.executeSql(sql);
		rs.next();
		//年休假定额
		String nianxjde = rs.getString(1);
		//调休定额
		String txde = rs.getString(2);
		
		
		if(nianxjde.equals("0")){
			requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
			requestInfo.getRequestManager().setMessagecontent("action执行出错-年休假定额不足 无法提交");
			return "0";
		}else if(txde.equals("0")){
			requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
			requestInfo.getRequestManager().setMessagecontent("action执行出错-调休定额不足 无法提交");
			return "0";
		}
		
		return Action.SUCCESS;
	}
	
}
