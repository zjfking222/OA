package com.jiuyi.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jiuyi.util.JiuyiUtil;
import weaver.general.BaseBean;

import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;

/***
 * 国内月度需求明细处理状态变更
 * @author yangc
 *
 */
public class MonthlyDemandProcessingState extends BaseAction{
	
	@Override
	public String execute(RequestInfo requestInfo) {
		new BaseBean().writeLog("国内月度需求明细处理状态变更:"+requestInfo.getRequestid());
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = JiuyiUtil.getTablename(requestInfo);//表名
		int mainid=0;
		String sql2 = "select id from "+tablename+" where requestid = "+requestid;//获取 国内月度需求计划报批流程 主表id
		new BaseBean().writeLog("sql2___--"+sql2);
		rs2.executeSql(sql2);
		if(rs.next()){
			mainid = rs2.getInt(1);//获取 国内月度需求计划报批流程 明细表mainid
		}
		String sql = "select ydmxId from "+tablename+"_dt1 where mainid = "+mainid;//获取 国内月度需求计划报批流程 明细表ydmxId
		new BaseBean().writeLog("sql--"+sql);
		rs.executeSql(sql);
		List<Map<String, Integer>> list = new ArrayList<Map<String,Integer>>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		int i = 0;
		while(rs.next()){
			map.put("ydmxId", rs.getInt(1));
			list.add(map);
			new BaseBean().writeLog("ydmxId_"+i+"="+list.get(i).get("ydmxId"));
			String sql1 = "update "+tablename+"_dt1 set clzt = 0 where id = "+list.get(i).get("ydmxId");//修改 国内月度需求计划评估流程 中的明细表中处理状态
			new BaseBean().writeLog("sql1--"+sql1);
			rs1.executeSql(sql1);
			i++;
		}
		
		return Action.SUCCESS;
	}
	
}
