package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
/**
 * 请假有账号剩余小时数判断
 * @author yangc
 *
 */
public class QingJiaYouZHSyxss implements Action{

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		
		String sql = "select shengyxss from "+tablename+" where requestid = "+requestid;
		new BaseBean().writeLog("sql------"+sql);
		rs.executeSql(sql);
		rs.next();
		Double shengyxss = rs.getDouble(1);
		new BaseBean().writeLog("剩余小时数------"+shengyxss);
		if(shengyxss<0){
			requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
			requestInfo.getRequestManager().setMessagecontent("action执行出错-剩余小时数不足 无法提交");
			return "0";
		}
		
		
		return Action.SUCCESS;
	}

}
