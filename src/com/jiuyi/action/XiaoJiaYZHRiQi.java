package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 
 * 销假有账号 获取请假日期时间
 * @author yang7
 *
 */
public class XiaoJiaYZHRiQi implements Action{

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		
		String sql = "select * from "+tablename+" where requestid = "+requestid;
		new BaseBean().writeLog("sql------"+sql);
		rs.executeSql(sql);
		rs.next();
		//获取主表中的请假流程ID
		String qjlc = rs.getString("qjlc");
		
		//根据请假流程ID去查询请假流程信息
		String sql1 = "select * from formtable_main_129 where requestid="+qjlc;
		new BaseBean().writeLog("sql1------"+sql1);
		rs1.executeSql(sql1);
		rs1.next();
		
		//获取请假开始日期
		String kaisrq = rs1.getString("kaisrq");
		//获取请假结束日期
		String jiesrq = rs1.getString("jiesrq");
		//获取请假开始时间小时
		String kaissjxs = rs1.getString("kaissjxs");
		//获取请假开始时间分钟
		String kaissjfz = rs1.getString("kaissjfz");
		//获取请假开结束时间小时
		String jiessjxs = rs1.getString("jiessjxs");
		//获取请假开结束时间分钟
		String jiessjfz = rs1.getString("jiessjfz");
		
		String sql2 = "update "+tablename+" set qjksrq='"+kaisrq+"',qjjsrq='"+jiesrq+"',qjkssjxs='"+kaissjxs+"',qjkssjfz='"+kaissjfz+"',qjjssjxs='"+jiessjxs+"',qjjssjfz='"+jiessjfz+"'  where requestid="+requestid;
		new BaseBean().writeLog("sql2-----"+sql2);
		rs2.executeSql(sql2);
		
		
		
		return Action.SUCCESS;
	}
	
}
