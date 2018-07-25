package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/***
 * 距加班天数大于五天不让提交
 * @author yangc
 *
 */
public class JiaBanYzhXNY implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		String requestid = requestInfo.getRequestid();// 请求号
		String tablename = SAPUtil.getTablename(requestInfo);// 表名
		new BaseBean().writeLog("加班申请时间判断 "+requestid);
		
		String sql = "select tianshu from "+tablename+" where requestid="+requestid;
		rs.executeSql(sql);
		rs.next();
		String tianshu = rs.getString(1);
		if(Double.parseDouble(tianshu)<(-4)){
			new BaseBean().writeLog("距加班天数大于五天不让提交");
			requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
			requestInfo.getRequestManager().setMessagecontent("执行出错，距加班天数大于五天 不可以提交" );// 提醒信息内容
			return "0";
		}
		
		return Action.SUCCESS;
	}

}
