package com.jiuyi.util;

import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

import weaver.conn.RecordSet;

public class TransUtil {
	/**
	 * HR���� ��������������ת��
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
	 * �ɹ���ͬ�������� �����������ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getSplx(String value){
		String name=JiuyiUtil.getSelectname(value,"9834");
		return name;
	}
	
	/**
	 * MM���̻�ȡ��ǰ����
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
	 * �ɹ���ͬ�������� ����״̬���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getLczt(String value){
		String name=JiuyiUtil.getSelectname(value,"13930");
		return name;
	}
	
	
	/**
	 * Ʊ���������� �����������ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getPdfkSplx(String value){
		String name=JiuyiUtil.getSelectname(value,"9813");
		return name;
	}
	
	/**
	 * Ʊ���������� ����״̬���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getPdfkLczt(String value){
		String name=JiuyiUtil.getSelectname(value,"13932");
		return name;
	}
	
	/**
	 * Ʊ���������� ҵ��Ա ת��
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
	 * ������ѵ�������� ���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getCcwxLx(String value){
		String name=JiuyiUtil.getSelectname(value,"7915");
		return name;
	}
	/**
	 * ��ˢ������ ���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getBskjlLx(String value){
		String name=JiuyiUtil.getSelectname(value,"7940");
		return name;
	}
	/**
	 * ��ˢ������ ��ˢ��ԭ��������ת��
	 * @param value
	 * @return name
	 */
	public static String getBskjlBuskyy(String value){
		String name=JiuyiUtil.getSelectname(value,"7941");
		return name;
	}
	/**
	 * ������� ���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getQjLx(String value){
		String name=JiuyiUtil.getSelectname(value,"7998");
		return name;
	}
	/**
	 * �������� ���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getXjLx(String value){
		String name=JiuyiUtil.getSelectname(value,"8067");
		return name;
	}
	
	/**
	 * ����(���˺�)���� ���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getXjLxWzh(String value){
		String name=JiuyiUtil.getSelectname(value,"12157");
		return name;
	}
	
	/**
	 * ������� �����㽭���ѣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getQjLxZjHy(String value){
		String name=JiuyiUtil.getSelectname(value,"11932");
		return name;
	}
	
	/**
	 * ������� ����2��������ת��
	 * @param value
	 * @return name
	 */
	public static String getQjLx2(String value){
		String name=JiuyiUtil.getSelectname(value,"8695");
		return name;
	}
	/**
	 * ������� ��������Դ��������ת��
	 * @param value
	 * @return name
	 */
	public static String getQjLxXny(String value){
		String name=JiuyiUtil.getSelectname(value,"11931");
		return name;
	}
	/**
	 * 	����ͣ�� ���ͣ�check��ת��
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
	 * �������(���˺�) ���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getQjLxWzh(String value){
		String name=JiuyiUtil.getSelectname(value,"8695");
		return name;
	}
	/**
	 * �����������뵥 ���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getQtSqdLx(String value){
		String name=JiuyiUtil.getSelectname(value,"8839");
		return name;
	}
	/**
	 * ���������� �������ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getMtart(String value){
		String name=JiuyiUtil.getSelectname(value,"9016");
		return name;
	}
	/**
	 * ���������� ���϶����飨������ת��
	 * @param value
	 * @return name
	 */
	public static String getKondm(String value){
		String name=JiuyiUtil.getSelectname(value,"9033");
		return name;
	}
	/**
	 * ���������� 	QM�����루������ת��
	 * @param value
	 * @return name
	 */
	public static String getSsqss(String value){
		String name=JiuyiUtil.getSelectname(value,"9035");
		name = name.substring(0,4);
		return name;
	}
	/**
	 * ���������� �۸���ƣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getVprsv(String value){
		String name=JiuyiUtil.getSelectname(value,"9038");
		return name;
	}
	/**
	 * ���������� �����루������ת��
	 * @param value
	 * @return name
	 */
	public static String getAwsls(String value){
		String name=JiuyiUtil.getSelectname(value,"9044");
		return name;
	}
	/**
	 * ���������� �ɹ����ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getBeskz(String value){
		String name=JiuyiUtil.getSelectname(value,"9050");
		return name;
	}
	/**
	 * ���������� �ƻ��߼��루������ת��
	 * @param value
	 * @return name
	 */
	public static String getFhori(String value){
		String name=JiuyiUtil.getSelectname(value,"9053");
		return name;
	}
	/**
	 * ���������� ��������Ա��������ת��
	 * @param value
	 * @return name
	 */
	public static String getFevor(String value){
		String name=JiuyiUtil.getSelectname(value,"9054");
		return name;
	}
	/**
	 * ���������� �������������յ��ڼ��ʶ��������ת��
	 * @param value
	 * @return name
	 */
	public static String getIprkz(String value){
		String name=JiuyiUtil.getSelectname(value,"9055");
		return name;
	}
	/**
	 * ���������� �������������յ��ڼ��ʶ��������ת��
	 * @param value
	 * @return name
	 */
	public static String getBwtty(String value){
		String name=JiuyiUtil.getSelectname(value,"9058");
		return name;
	}
	/**
	 * ���������� �������ϣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getWlzsjJbwl(String value){
		String name=JiuyiUtil.getSelectname(value,"9047");
		return name;
	}
	/**
	 * ���������� ˰���ࣨ������ת��
	 * @param value
	 * @return name
	 */
	public static String getWlzsjSfl(String value){
		String name=JiuyiUtil.getSelectname(value,"13610");//��ʽ��
		//String name=JiuyiUtil.getSelectname(value,"13345");//���Ի�
		return name;
	}
	/**
	 * ���������� �۸�ȷ����check��ת��
	 * @param value
	 * @return name
	 */
	public static String getMlast(String value){
		String name=JiuyiUtil.getSelectname(value,"9061");
		new BaseBean().writeLog("name==="+name);
		return name;
	}
	/**
	 * ��������/�¶����� ���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getLxYdXq(String value){
		String name=JiuyiUtil.getSelectname(value,"8889");
		return name;
	}
	/**
	 * ��ȡ���������
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
	 * ����֤���� ��֤Gu��֤��������ϸ��
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
	 * ����֤���� ��֤Cu��֤��������ϸ��
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
	 * ����֤���� ��֤Ni��֤��������ϸ��
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
	 * ����֤���� ��֤Ge��֤��������ϸ��
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
	 * ����֤���� ��֤Gu��֤��������ϸ��
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
	 * ����֤���� ��֤Cu��֤��������ϸ��
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
	 * ����֤���� ��֤Ni��֤��������ϸ��
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
	 * ����֤���� ��֤Ge��֤��������ϸ��
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
	 * ����֤���� ��֤Li��֤��������ϸ��
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
	 * ����֤���� ��֤Mn��֤��������ϸ��
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
	 * ����֤���� ��֤Li��֤��������ϸ��
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
	 * ����֤���� ��֤Mn��֤��������ϸ��
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
	 * �������� ���Ӹ���
	 * @param value
	 * @return name
	 */
	public static String getDfje(String value){
		String name="-"+value;
		return name;
	}
	/**
	 * �������� ̧ͷ�ı�ƾ��
	 * @param requestid
	 * @return name
	 */
	public static String getHeaderText(String requestid){
		String result = "";
		RecordSet rs = new RecordSet();
		String sql="select * from formtable_main_176 where requestid='"+requestid+"'";
		rs.execute(sql);
		if(rs.next()){
			result = "Ա��"+rs.getString("jkr")+" ���"+rs.getString("xxjkje")+" "+rs.getString("WAERS");
		}
		return result;
	}
	/**
	 * �㹩Ӧ�����������ͣ�������ת��
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
	 * �㹩Ӧ�������� ��Ӧ���˻��飨������ת��
	 * @param value
	 * @return name
	 */
	public static String getGysZsjWh(String value){
		String name=JiuyiUtil.getSelectname(value,"9277");
		return name;
	}
	/**
	 * �㹩Ӧ�������� ҵ������飨������ת��
	 * @param value
	 * @return name
	 */
	public static String getGysZsjYw(String value){
		String name=JiuyiUtil.getSelectname(value,"9246");
		return name;
	}
	/**
	 * �㹩Ӧ�������� �����루������ת��
	 * @param value
	 * @return name
	 */
	public static String getGysZsjPxm(String value){
		String name=JiuyiUtil.getSelectname(value,"10109");
		return name;
	}
	/**
	 * �㹩Ӧ�������� ��Ӧ��Ʒ���ͣ�������ת��
	 * @param value
	 * @return name
	 */
	public static String getGysZsjGysfl(String value){
		String name=JiuyiUtil.getSelectname(value,"9888");
		return name;
	}
	/** 
	 * �㹩Ӧ�������� ���й��Ҵ��루������ת��
	 * @param value
	 * @return name
	 */
	public static String getGysZsjGjDm(String value){
		String name=JiuyiUtil.getSelectname(value,"9309");
		return name;
	}
	
	/**
	 * �㹩Ӧ�������� ���д��루������ת��
	 * @param value
	 * @return name
	 */
	public static String getGysZsjYhDm(String value){
		String name=JiuyiUtil.getSelectname(value,"9310");
		return name;
	} 
	/**
	 * �ͻ������� ҵ������飨������ת��
	 * @param value
	 * @return name
	 */
	public static String getKhZsjYwhb(String value){
		String name=JiuyiUtil.getSelectname(value,"10835");
		return name;
	}
	/**
	 * ������� ���˺ţ�Сʱ��ת��
	 * @param value
	 * @return name
	 */
	public static String getQjSqWzhXS(String value){
		String name = value.substring(0, value.indexOf(":",value.indexOf(":")-1 ));
		return name;
	}
	/**
	 * ������� ���˺ţ����ӣ�ת��
	 * @param value
	 * @return name
	 */
	public static String getQjSqWzhFZ(String value){
		String name = value.substring(value.indexOf(":") + 1);
		return name;
	}
	
	
	
	/**
	 * ��ȡ���ڵ�ʱ��6λ
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
