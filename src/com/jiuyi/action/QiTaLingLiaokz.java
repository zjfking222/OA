package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import com.jiuyi.util.SAPUtil;

/**
 * 其他领料申请单  成本中心  内部订单控制
 * @author yangc 
 *
 */
public class QiTaLingLiaokz implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		//获取主表的
		String sql = "select * from "+tablename+" where requestid = "+requestid;
		new BaseBean().writeLog("sql=="+sql);
		rs.executeSql(sql);
		rs.next();
		
		//移动类型
		String YDLX = rs.getString("YDLX");
		
		//201有关成本中心的发货
		if(YDLX.equals("1")){
			String sql1 = "update "+tablename+" set AUFNR='',KTEXT='' where requestid = "+requestid;
			new BaseBean().writeLog("sql1=="+sql1);
			rs1.executeSql(sql1);
		}
		//261有关项目的发货
		if(YDLX.equals("2")){
			String sql2 = "update "+tablename+" set KOSTL='',MCTXT='' where requestid = "+requestid;
			new BaseBean().writeLog("sql2=="+sql2);
			rs2.executeSql(sql2);
		}
		return Action.SUCCESS;
	}

}
