package com.jiuyi.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 加班补报一月两次判断
 * @author yang7
 *
 */
public class JiaBanBuBao implements Action{

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String requestid = requestInfo.getRequestid();// 请求号
		String tablename = SAPUtil.getTablename(requestInfo);// 表名
		new BaseBean().writeLog("加班补报一月两次判断 "+requestid);
		Calendar cale = null;
		// 获取当月第一天和最后一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		// 获取前月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		// 获取前月的最后一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime());
		new BaseBean().writeLog("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);
		
		String sql = "select yuangbh from "+tablename+" where requestid = "+requestid;
		new BaseBean().writeLog("sql--"+sql);
		rs.executeSql(sql);
		rs.next();
		//获取当前员工编号
		String yuangbh = rs.getString(1);
		new BaseBean().writeLog("当前员工编号"+yuangbh);
		
		//通过当前月第一天与最后一天判断 是否存在两条信息
		String sql1 = "select count(a.id) from "+tablename+" a left join workflow_requestbase b on a.requestid=b.requestid and b.currentnodetype>0 where tianshu!=' ' and a.yuangbh='"+yuangbh+"' and a.kaisrq between '"+firstday+"' and '"+lastday+"'";
		new BaseBean().writeLog("sql1--"+sql1);
		rs1.executeSql(sql1);
		rs1.next();
		//获取补报次数
		int bubao = rs1.getInt(1);
		new BaseBean().writeLog("获取补报次数"+bubao);
		
		if(bubao>2){
			requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
			requestInfo.getRequestManager().setMessagecontent("执行出错，您本月已有两次补报申请，不得再次申请" );// 提醒信息内容
			return "0";
		}
		return Action.SUCCESS;
	}
	
}
