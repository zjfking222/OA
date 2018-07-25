package com.jiuyi.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/***
 * 申报时间距加班时间5天内 可以提交
 * @author yangc
 *
 */
public class JiaBanWuZhangHao implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String requestid = requestInfo.getRequestid();// 请求号
		String tablename = SAPUtil.getTablename(requestInfo);// 表名
		new BaseBean().writeLog("加班申请时间判断 "+requestid);

		String sql = "select id from " + tablename + " where requestid = " + requestid;
		new BaseBean().writeLog("sql----- "+sql);
		rs.executeSql(sql);
		rs.next();
		int mainid = rs.getInt(1);
		
		String sql1 = "select kaisrq,kaissj,jiesrq,jiessj from " + tablename + "_dt1  where mainid = " + mainid;
		new BaseBean().writeLog("sql1----- "+sql1);
		rs1.executeSql(sql1);
		while(rs1.next()){
			String kaisrq = rs1.getString("kaisrq");//开始日期
			String kaissj = rs1.getString("kaissj");//开始时间
			String jiesrq = rs1.getString("jiesrq");//结束日期
			String jiessj = rs1.getString("jiessj");//结束时间
			new BaseBean().writeLog("开始日期"+kaisrq);
			new BaseBean().writeLog("开始时间"+kaissj);
			new BaseBean().writeLog("结束日期"+jiesrq);
			new BaseBean().writeLog("结束时间"+jiessj);
			//判断日期是否输入错误
			new BaseBean().writeLog("判断日期是否输入错误"+(kaisrq.length()!=8||jiesrq.length()!=8));
			if(kaisrq.length()!=8||jiesrq.length()!=8){
				requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
				requestInfo.getRequestManager().setMessagecontent("执行出错，日期填写错误，请检查" );// 提醒信息内容
				return "0";
			}
			//判断时间是否输入错误
			new BaseBean().writeLog("判断时间是否输入错误"+(kaissj.length()!=5||jiessj.length()!=5));
			if(kaissj.length()!=5||jiessj.length()!=5){
				requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
				requestInfo.getRequestManager().setMessagecontent("执行出错，时间填写错误，请检查" );// 提醒信息内容
				return "0";
			}
			
			
			
			
			
			//获取当前时间点 年月日
			Calendar cal = Calendar.getInstance(); 
		    int year = cal.get(Calendar.YEAR); 
		    int month = cal.get(Calendar.MONTH) + 1; 
		    int day = cal.get(Calendar.DAY_OF_MONTH); 
		    String date = "";
		    if(month<10){
		    	date = Integer.toString(year)+"-"+"0"+Integer.toString(month)+"-"+Integer.toString(day);
		    	new BaseBean().writeLog("当前申请日期"+date);
		    }else{
		    	date = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
		    	new BaseBean().writeLog("当前申请日期"+date);
		    }
		    
		    
		    String year1 = kaisrq.substring(0, 4);
		    String month1 = kaisrq.substring(4, 6);
		    String day1 = kaisrq.substring(6, 8);
		    new BaseBean().writeLog("year1="+year1);
		    new BaseBean().writeLog("month1="+month1);
		    new BaseBean().writeLog("day1="+day1);
		    String date1 = year1+"-"+month1+"-"+day1;
		    new BaseBean().writeLog("开始加班日期="+date1);
			
			
			
			
			
			
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");//如2016-08-10 20:40 
			try {
				String fromDate = simpleFormat.format(simpleFormat.parse(date)); //当前申请日期
				String toDate = simpleFormat.format(simpleFormat.parse(date1));  //开始加班日期
				long from;
				from = simpleFormat.parse(fromDate).getTime();   //当前申请日期
				long to = simpleFormat.parse(toDate).getTime();  //开始加班日期
				int days = (int) ((from- to)/(1000 * 60 * 60 * 24)); 
				new BaseBean().writeLog("申请时间与加班开始时间相隔："+days);
				if(days>4){
					new BaseBean().writeLog("申报时间距加班时间5天内 可以提交");
					requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
					requestInfo.getRequestManager().setMessagecontent("执行出错，申报时间距加班时间大于5天 不可以提交" );// 提醒信息内容
					return "0";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		
		
		return Action.SUCCESS;
	}

}
