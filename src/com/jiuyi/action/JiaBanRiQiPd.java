package com.jiuyi.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * 申报时间晚于加班时间不能提交 
 * 
 * @author yang7
 * 
 */
public class JiaBanRiQiPd implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		String requestid = requestInfo.getRequestid();// 请求号
		String tablename = SAPUtil.getTablename(requestInfo);// 表名
		new BaseBean().writeLog("加班申请时间判断 "+requestid);
		
		Date day=new Date();    
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 

		String sql = "select kaisrq,kaissjxs,kaissjfz from " + tablename + " where requestid = " + requestid;
		new BaseBean().writeLog("sql----- "+sql);
		rs.executeSql(sql);
		rs.next();

		String kaisrq = rs.getString(1);// 加班开始日期
		String kaissjxs = rs.getString(2);// 开始时间小时
		String kaissjfz = rs.getString(3);// 开始时间分钟
		
		String jbkssj =kaisrq+" "+kaissjxs+":"+kaissjfz;// 加班开始时间
		new BaseBean().writeLog("加班开始时间jbkssj "+jbkssj);
		//获取系统当前日期
		String sqsj = df2.format(day);//申请时间
		new BaseBean().writeLog("申请时间sqsj "+sqsj);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");//如2016-08-10 20:40 
		try {
			//将字符串转换为date类型 
			Date dt1 = df.parse(jbkssj);//加班开始时间 
			Date dt2 = df.parse(sqsj);//申请时间
			
			String dt11 = simpleFormat.format(simpleFormat.parse(jbkssj));//加班开始时间 
			String dt22 = simpleFormat.format(simpleFormat.parse(sqsj));//申请时间
			new BaseBean().writeLog("加班开始时间 "+dt1.getTime());
			new BaseBean().writeLog("申请时间"+dt2.getTime());
			new BaseBean().writeLog("时间比较"+(dt2.getTime()>dt1.getTime()));
			long from;
			from = simpleFormat.parse(dt11).getTime();
			long to = simpleFormat.parse(dt22).getTime();  
			int days = (int) (( from - to )/(1000 * 60 * 60 * 24)); 
			new BaseBean().writeLog("时间差"+days);
			
			if(dt2.getTime()>dt1.getTime())//申报时间大于加班时间不能提交
			{	
				new BaseBean().writeLog("申报时间大于加班时间不能提交");
				requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
				requestInfo.getRequestManager().setMessagecontent("执行出错，申报时间大于加班时间不能提交" );// 提醒信息内容
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
}
