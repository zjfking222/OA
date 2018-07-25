package com.jiuyi.action;

import java.util.regex.Pattern;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import com.jiuyi.util.SAPUtil;

/**
 * 物料主数据创建  物料编号11位控制
 * @author yangc
 *
 */
public class WuLiaoZhuShuJuWLBH implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		//获取主表的id 
		String sql = "select id from "+tablename+" where requestid = "+requestid;
		new BaseBean().writeLog("sql-----"+sql);
		rs.executeSql(sql);
		rs.next();
		int mainid = rs.getInt("id");
		
		//获取物料编码1
		String sql1 = "select MATNR from "+tablename+"_dt1 where mainid = "+mainid;
		new BaseBean().writeLog("sql1-----"+sql1);
		rs1.executeSql(sql1);
		rs1.next();
		String MATNR = rs1.getString("MATNR");
		new BaseBean().writeLog("MATNR-----"+MATNR);
		
        Pattern pattern=Pattern.compile("[0-9]*");
        

		new BaseBean().writeLog("是否为纯数字-----"+pattern.matcher(MATNR).matches());
		new BaseBean().writeLog("编码长度-----"+MATNR.length());
        
		if(pattern.matcher(MATNR).matches()==true&&MATNR.length()==11){
			return Action.SUCCESS;
		}else{
			requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
			requestInfo.getRequestManager().setMessagecontent("执行出错，物料编码必须为11位数字，请检查" );// 提醒信息内容
			return "0";
		}
		
		
	}

}
