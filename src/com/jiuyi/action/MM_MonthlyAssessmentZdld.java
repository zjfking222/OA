package com.jiuyi.action;


import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class MM_MonthlyAssessmentZdld implements Action {
	//国内月度需求计划评估流程字段更新 
	private String lcbm; 
	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		//获取主表的id 工厂编号
		String sql = "select * from "+tablename+" where requestid = "+requestid;
		rs.executeSql(sql);
		rs.next();

		String mainid = rs.getString("id");//mainid
		String WERKS1=rs.getString("WERKS1");//客户所选择的工厂的id
		new BaseBean().writeLog("mainid-----"+mainid);

		String BSART1 = rs.getString("BSART1");//月度采购申请凭证类型
		String BSART2= rs.getString("BSART2");//零星采购申请凭证类型
		String BSART = rs.getString("BSART");//采购申请凭证类型号
		String KOSTL1 = rs.getString("KOSTL1");//成本中心
		String cbzxms = rs.getString("cbzxms");//成本中心描述
		String shenqr = rs.getString("shenqr");//申请人
		String ddms = rs.getString("ddms");//项目描述ddms
		String dd = rs.getString("dd");//项目
		

		sql = "select * from hrmresource where id = "+shenqr;
		rs.execute(sql);
		rs.next();
		String shenqrxm = rs.getString("lastname");
		String mobile = rs.getString("mobile");//表体“联系方式”取申请者OA个人信息的“移动电话”值，如果移动电话号码小余11位，直接取值，如果大于等于11位，首位“1”不取，中间的“-”不取，其余值全按顺序全取，如果申请者无移动电话，不取值
		String lxrdh = "";
		int mobilelenth = mobile.length();
		
		sql = "select * from "+tablename+"_dt1 where mainid = "+mainid + " order by id";
		rs1.executeSql(sql);
		
		int i =1;
		while(rs1.next()){//更新行项目号
			String sqlwhere = " where id='"+rs1.getString("id")+"'";
			String setValue = " BNFPO='"+i+"'";
			JiuyiUtil.updatePosnrXh(tablename+"_dt1",setValue,sqlwhere);
			i++;
		}
		
		if(mobilelenth>10){//更新明细联系方式
			lxrdh=mobile.substring(mobile.length()-10, mobile.length());
		}else if(mobilelenth>0&&mobilelenth<11){
			lxrdh=mobile;
		}else {
			requestInfo.getRequestManager().setMessage("111100");//提醒消息ID
			requestInfo.getRequestManager().setMessagecontent("需要维护申请人移动电话后才能提交（人事—我的卡片—移动电话）");//提醒消息内容
			return "0";
		}
		sql = "update "+tablename+"_dt1 set BEDNR='"+lxrdh+"' where mainid = "+mainid;
		rs.executeSql(sql);
		
		
		
		
	    if("0".equals(BSART1)) {
	    	BSART="1001";
	    }else if("1".equals(BSART1)) {
	    	BSART="1002";
	    }else if("3".equals(BSART1)) {
	    	BSART="1004";
	    }else if("0".equals(BSART2)) {
	    	BSART="1005";
	    }else if("1".equals(BSART2)) {
	    	BSART="1006";
	    }else if("3".equals(BSART2)) {
	    	BSART="1008";
	    }
	    sql = "update "+tablename+" set BSART='"+BSART+"' where requestid="+requestid;
		rs.executeSql(sql);
	    
		
		
		
		
		if("xmydpg".equals(lcbm)){//国内月度评估，,IM_FLAG=1成本中心必填
		if("3".equals(BSART1)) {
			sql = "update "+tablename+"_dt1 set KOSTL = '"+KOSTL1+"', MCTXT = '"+cbzxms+"', KNTTP='K',AFNAM='"+cbzxms+shenqrxm+"' where mainid = "+mainid;
			new BaseBean().writeLog("sql1-----"+sql);
		}else {
			sql = "update "+tablename+"_dt1 set KOSTL = '' ,MCTXT = '"+cbzxms+"',KNTTP='',AFNAM='"+cbzxms+shenqrxm+"'  where mainid = "+mainid;
			new BaseBean().writeLog("sql2-----"+sql);
		}

		rs.execute(sql);
		sql = "update "+tablename+" set xqzxm='"+cbzxms+shenqrxm+"' where requestid="+requestid;
		rs.execute(sql);
		if (WERKS1.equals("3020")){
		    sql = "update "+tablename+"_dt1 set KOSTL = '"+KOSTL1+"', IM_FLAG=1  where mainid = "+mainid;
		    new BaseBean().writeLog("sql3-----"+sql);
		    rs.execute(sql);
		}
		
		
		}else if("scjylx".equals(lcbm)) {//生产经营零星材料需求
			if("3".equals(BSART2)) {
				sql = "update "+tablename+"_dt1 set KOSTL = '"+KOSTL1+"',MCTXT='"+cbzxms+"', KNTTP='K',AFNAM='"+cbzxms+shenqrxm+"',AUFNR='"+dd+"' where mainid = "+mainid;
				new BaseBean().writeLog("sql3-----"+sql);
			}else {
				sql = "update "+tablename+"_dt1 set KOSTL = '',MCTXT='"+cbzxms+"', KNTTP='',AFNAM='"+cbzxms+shenqrxm+"',AUFNR=''  where mainid = "+mainid;
				new BaseBean().writeLog("sql4-----"+sql);
			}
			rs.execute(sql);
			sql = "update "+tablename+" set xqzxm='"+cbzxms+shenqrxm+"' where requestid="+requestid;
			rs.execute(sql);
            if (WERKS1.equals("3020")){
				sql = "update "+tablename+"_dt1 set KOSTL = '"+KOSTL1+"', IM_FLAG=1  where mainid = "+mainid;
                new BaseBean().writeLog("sql3-----"+sql);
                rs.execute(sql);
            }
			
		}else if("xmlxcl".equals(lcbm)) {//项目零星材料需求
			if("3".equals(BSART2)) {
				sql = "update "+tablename+"_dt1 set KTEXT = '"+ddms+"', KNTTP='F',AFNAM='"+dd+shenqrxm+"',AUFNR='"+dd+"'  where mainid = "+mainid;
				new BaseBean().writeLog("sql5-----"+sql);
			}else {
				sql = "update "+tablename+"_dt1 set KTEXT = '', KNTTP='',AFNAM='"+dd+shenqrxm+"',AUFNR=''  where mainid = "+mainid;
				new BaseBean().writeLog("sql6-----"+sql);
			}
			rs.executeSql(sql);
			sql = "update "+tablename+" set xqzxm='"+dd+shenqrxm+"' where requestid="+requestid;
			rs.executeSql(sql);
			
			
		}else if("xmydcl".equals(lcbm)) {//项目月度材料需求
			if("3".equals(BSART1)) {
				sql = "update "+tablename+"_dt1 set KTEXT = '"+ddms+"', KNTTP='F',AFNAM='"+dd+shenqrxm+"',AUFNR='"+dd+"'  where mainid = "+mainid;
				new BaseBean().writeLog("sql7-----"+sql);
			}else {
				sql = "update "+tablename+"_dt1 set KTEXT = '', KNTTP='',AFNAM='"+dd+shenqrxm+"',AUFNR=''  where mainid = "+mainid;
				new BaseBean().writeLog("sql8-----"+sql);
			}
			rs.executeSql(sql);
			sql = "update "+tablename+" set xqzxm='"+dd+shenqrxm+"' where requestid="+requestid;
			rs.executeSql(sql);
		}
		
		return Action.SUCCESS;
	}
	public String getLcbm() {
		return lcbm;
	}
	public void setLcbm(String lcbm) {
		this.lcbm = lcbm;
	}

}
