package com.jiuyi.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;


/**
 * 法定加班判断
 * @author yangc
 *
 */
public class JiaBanFaDingJr implements Action{

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		
		String sql = "select jiablx,kaisrq from "+tablename+"  where requestid = "+requestid;
		new BaseBean().writeLog("sql---"+sql);
		rs.executeSql(sql);
		rs.next();
		//加班类型
		String jiablx = rs.getString(1);
		new BaseBean().writeLog("加班类型---"+jiablx);
		//加班开始日期
		String kaisrq = rs.getString(2);
		new BaseBean().writeLog("kaisrq---"+kaisrq);
		String sql1="select count(*) from uf_fadingjr where jiarrq='"+kaisrq+"'";
		new BaseBean().writeLog("sql1---"+sql1);
		rs1.executeSql(sql1);
		rs1.next();
		String count = rs1.getString(1);
		if(jiablx.equals("2")&&count.equals("0")){
			requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
			requestInfo.getRequestManager().setMessagecontent("action执行出错-您申请的日期不在法定假日");
			return "0";
		}else if(jiablx.equals("1")){
			try {
				DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");       
				Date bdate;
				bdate = format1.parse(kaisrq);
				Calendar cal = Calendar.getInstance();
				cal.setTime(bdate);
				if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
					return Action.SUCCESS;
				}else{
					System.out.println("no");
					requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
					requestInfo.getRequestManager().setMessagecontent("action执行出错-您申请的日期不在周末");
					return "0";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Action.SUCCESS;
	}

}
