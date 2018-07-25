package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 客户主数据  主客户数据编码与客户编码判断
 * @author yang7
 *
 */
public class KeHuZhuShuJuBianma implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		String requestid = requestInfo.getRequestid();// 请求号
		String tablename = SAPUtil.getTablename(requestInfo);// 表名
		
		String sql = "select KUNNR,EXTENSION2 from "+tablename+" where requestid = "+requestid;
		rs.executeSql(sql);
		rs.next();
		String KUNNR = rs.getString(1);
		String EXTENSION2 = rs.getString(2);
		if(KUNNR.equals(EXTENSION2)){
			requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
			requestInfo.getRequestManager().setMessagecontent("执行出错，主客户编码与客户编码不能相同" );// 提醒信息内容
			return "0";
		}
		
		return Action.SUCCESS;
	}

}
