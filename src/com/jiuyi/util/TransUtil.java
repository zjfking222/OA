package com.jiuyi.util;

import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

import weaver.conn.RecordSet;

public class TransUtil {
	/**
	 * HR流程 分钟数（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getFz(String value){
		String name="";
		if("0".equals(value)){
			name = "00";
		}else if("1".equals(value)){
			name = "15";
		}else if("2".equals(value)){
			name = "30";
		}else if("3".equals(value)){
			name = "45";
		}
		return name;  
	}
	/**
	 * 采购合同付款流程 审批类型类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getSplx(String value){
		String name=JiuyiUtil.getSelectname(value,"9834");
		return name;
	}
	
	/**
	 * MM流程获取当前日期
	 * @param value
	 * @return name 
	 */
	public static String getDate(String value){
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		return dateNowStr;
	}
	
	/**
	 * 采购合同付款流程 流程状态类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getLczt(String value){
		String name=JiuyiUtil.getSelectname(value,"13930");
		return name;
	}
	
	
	/**
	 * 票到付款流程 审批类型类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getPdfkSplx(String value){
		String name=JiuyiUtil.getSelectname(value,"9813");
		return name;
	}
	
	/**
	 * 票到付款流程 流程状态类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getPdfkLczt(String value){
		String name=JiuyiUtil.getSelectname(value,"13932");
		return name;
	}
	
	/**
	 * 票到付款流程 业务员 转换
	 * @param value
	 * @return name
	 */
	public static String getPdfkYwy(String value){
		RecordSet rs = new RecordSet();
		String sql ="select* from hrmresource where id = "+value;
		new BaseBean().writeLog("sql"+sql);
		rs.executeSql(sql);
		rs.next();
		String name = rs.getString("lastname");
		
		return name;
	}
	
	/**
	 * 出差外训审批流程 类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getCcwxLx(String value){
		String name=JiuyiUtil.getSelectname(value,"7915");
		return name;
	}
	/**
	 * 补刷卡流程 类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getBskjlLx(String value){
		String name=JiuyiUtil.getSelectname(value,"7940");
		return name;
	}
	/**
	 * 补刷卡流程 补刷卡原因（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getBskjlBuskyy(String value){
		String name=JiuyiUtil.getSelectname(value,"7941");
		return name;
	}
	/**
	 * 请假流程 类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getQjLx(String value){
		String name=JiuyiUtil.getSelectname(value,"7998");
		return name;
	}
	/**
	 * 销假流程 类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getXjLx(String value){
		String name=JiuyiUtil.getSelectname(value,"8067");
		return name;
	}
	
	/**
	 * 销假(无账号)流程 类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getXjLxWzh(String value){
		String name=JiuyiUtil.getSelectname(value,"12157");
		return name;
	}
	
	/**
	 * 请假流程 类型浙江华友（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getQjLxZjHy(String value){
		String name=JiuyiUtil.getSelectname(value,"11932");
		return name;
	}
	
	/**
	 * 请假流程 类型2（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getQjLx2(String value){
		String name=JiuyiUtil.getSelectname(value,"8695");
		return name;
	}
	/**
	 * 请假流程 类型新能源（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getQjLxXny(String value){
		String name=JiuyiUtil.getSelectname(value,"11931");
		return name;
	}
	/**
	 * 	物料停用 类型（check框）转换
	 * @param value
	 * @return name
	 */
	public static String getWlty(String value){
		if(value.equals("1")){
			return "X";
		}else {
			return " ";
		}
	}	
	/**
	 * 请假流程(无账号) 类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getQjLxWzh(String value){
		String name=JiuyiUtil.getSelectname(value,"8695");
		return name;
	}
	/**
	 * 其他领料申请单 类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getQtSqdLx(String value){
		String name=JiuyiUtil.getSelectname(value,"8839");
		return name;
	}
	/**
	 * 物料主数据 物料类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getMtart(String value){
		String name=JiuyiUtil.getSelectname(value,"9016");
		return name;
	}
	/**
	 * 物料主数据 物料定价组（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getKondm(String value){
		String name=JiuyiUtil.getSelectname(value,"9033");
		return name;
	}
	/**
	 * 物料主数据 	QM控制码（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getSsqss(String value){
		String name=JiuyiUtil.getSelectname(value,"9035");
		name = name.substring(0,4);
		return name;
	}
	/**
	 * 物料主数据 价格控制（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getVprsv(String value){
		String name=JiuyiUtil.getSelectname(value,"9038");
		return name;
	}
	/**
	 * 物料主数据 差异码（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getAwsls(String value){
		String name=JiuyiUtil.getSelectname(value,"9044");
		return name;
	}
	/**
	 * 物料主数据 采购类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getBeskz(String value){
		String name=JiuyiUtil.getSelectname(value,"9050");
		return name;
	}
	/**
	 * 物料主数据 计划边际码（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getFhori(String value){
		String name=JiuyiUtil.getSelectname(value,"9053");
		return name;
	}
	/**
	 * 物料主数据 生产调度员（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getFevor(String value){
		String name=JiuyiUtil.getSelectname(value,"9054");
		return name;
	}
	/**
	 * 物料主数据 货架寿命到期日的期间标识（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getIprkz(String value){
		String name=JiuyiUtil.getSelectname(value,"9055");
		return name;
	}
	/**
	 * 物料主数据 货架寿命到期日的期间标识（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getBwtty(String value){
		String name=JiuyiUtil.getSelectname(value,"9058");
		return name;
	}
	/**
	 * 物料主数据 基本物料（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getWlzsjJbwl(String value){
		String name=JiuyiUtil.getSelectname(value,"9047");
		return name;
	}
	/**
	 * 物料主数据 税分类（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getWlzsjSfl(String value){
		String name=JiuyiUtil.getSelectname(value,"13610");//正式机
		//String name=JiuyiUtil.getSelectname(value,"13345");//测试机
		return name;
	}
	/**
	 * 物料主数据 价格确定（check框）转换
	 * @param value
	 * @return name
	 */
	public static String getMlast(String value){
		String name=JiuyiUtil.getSelectname(value,"9061");
		new BaseBean().writeLog("name==="+name);
		return name;
	}
	/**
	 * 零星需求/月度需求 类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getLxYdXq(String value){
		String name=JiuyiUtil.getSelectname(value,"8889");
		return name;
	}
	/**
	 * 获取相差几天的日期
	 * 
	 * @param day
	 * @return beforeday
	 */
	public static String getBeforDay(String day) {
		String today = TimeUtil.getCurrentDateString();
		String result = today;//TimeUtil.dateAdd(today, Integer.parseInt(day));
		return result;
	}
	/**
	 * 信用证创建 外证Gu开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getGoWzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt1 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='0'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}
	/**
	 * 信用证创建 外证Cu开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getCuWzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt1 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='1'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}
	/**
	 * 信用证创建 外证Ni开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getNiWzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt1 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='3'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}/**
	 * 信用证创建 外证Ge开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getGeWzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt1 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='2'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}
	/**
	 * 信用证创建 内证Gu开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getGoNzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt2 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='0'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}
	/**
	 * 信用证创建 内证Cu开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getCuNzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt2 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='1'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}
	/**
	 * 信用证创建 内证Ni开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getNiNzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt2 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='3'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}
	/**
	 * 信用证创建 内证Ge开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getGeNzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt2 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='2'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}
	/**
	 * 信用证创建 内证Li开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getLiNzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt2 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='4'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}
	/**
	 * 信用证创建 内证Mn开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getMnNzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt2 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='5'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}
	/**
	 * 信用证创建 外证Li开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getLiWzKzsl(String requestid){

		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt1 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='4'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		new BaseBean().writeLog("waizhengli:"+result);
		return result;
	}
	/**
	 * 信用证创建 外证Mn开证数量（明细表）
	 * @param requestid
	 * @return result
	 */
	public static String getMnWzKzsl(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select shlmt from formtable_main_181_dt1 where mainid = (select id from formtable_main_181 where requestid='"+requestid+"') and jijia='5'";
		rs.execute(sql);
		if(rs.next()){
			result = Util.null2String(rs.getString("shlmt"));
		}
		return result;
	}
	/**
	 * 财务流程 金额加负号
	 * @param value
	 * @return name
	 */
	public static String getDfje(String value){
		String name="-"+value;
		return name;
	}
	/**
	 * 财务流程 抬头文本凭借
	 * @param requestid
	 * @return name
	 */
	public static String getHeaderText(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select * from formtable_main_176 where requestid='"+requestid+"'";
		rs.execute(sql);
		if(rs.next()){
			result = "员工"+rs.getString("jkr")+" 借款"+rs.getString("xxjkje")+" "+rs.getString("WAERS");
		}
		return result;
	}
	/**
	 * 零供应商主数据类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getGysZsj(String value){
		if(value.equals("0")){
			return "C";
		}else if(value.equals("1")){
			return "U";
		}else{
			return "D";
		}
	}
	/**
	 * 零供应商主数据 供应商账户组（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getGysZsjWh(String value){
		String name=JiuyiUtil.getSelectname(value,"9277");
		return name;
	}
	/**
	 * 零供应商主数据 业务伙伴分组（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getGysZsjYw(String value){
		String name=JiuyiUtil.getSelectname(value,"9246");
		return name;
	}
	/**
	 * 零供应商主数据 排序码（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getGysZsjPxm(String value){
		String name=JiuyiUtil.getSelectname(value,"10109");
		return name;
	}
	/**
	 * 零供应商主数据 供应产品类型（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getGysZsjGysfl(String value){
		String name=JiuyiUtil.getSelectname(value,"9888");
		return name;
	}
	/** 
	 * 零供应商主数据 银行国家代码（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getGysZsjGjDm(String value){
		String name=JiuyiUtil.getSelectname(value,"9309");
		return name;
	}
	
	/**
	 * 零供应商主数据 银行代码（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getGysZsjYhDm(String value){
		String name=JiuyiUtil.getSelectname(value,"9310");
		return name;
	} 
	/**
	 * 客户主数据 业务伙伴分组（下拉框）转换
	 * @param value
	 * @return name
	 */
	public static String getKhZsjYwhb(String value){
		String name=JiuyiUtil.getSelectname(value,"10835");
		return name;
	}
	/**
	 * 请假申请 无账号（小时）转换
	 * @param value
	 * @return name
	 */
	public static String getQjSqWzhXS(String value){
		String name = value.substring(0, value.indexOf(":",value.indexOf(":")-1 ));
		return name;
	}
	/**
	 * 请假申请 无账号（分钟）转换
	 * @param value
	 * @return name
	 */
	public static String getQjSqWzhFZ(String value){
		String name = value.substring(value.indexOf(":") + 1);
		return name;
	}
	
	
	
	/**
	 * 获取现在的时间6位
	 * @param value
	 * @return name
	 */
	public static String getCurrentTimeString(String value){
		Date date = new Date();
		String hours = "";
		String minutes = "";
		String seconds = "";
		if(date.getHours()<10) {
			hours="0"+String.valueOf(date.getHours());
		}else {
			hours=String.valueOf(date.getHours());
		}
		
		if(date.getMinutes()<10) {
			minutes="0"+String.valueOf(date.getMinutes());
		}else {
			minutes=String.valueOf(date.getMinutes());
		}
		
		if(date.getSeconds()<10) {
			seconds="0"+String.valueOf(date.getSeconds());
		}else {
			seconds=String.valueOf(date.getSeconds());
		}
		String time =hours +minutes+seconds;
		return time;
	} 
}
