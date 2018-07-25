package com.jiuyi.action;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.jiuyi.util.BigDecimalUtil;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class FICO_ContractPayJD7 implements Action {

	//采购合同付款节点7接口 

	public String execute(RequestInfo requestInfo) {
		String requestid="";
		try {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		//获取主表的id 工厂编号
		
		String sql = "select * from "+tablename+" where requestid = "+requestid;
		rs.executeSql(sql);
		rs.next();
		double qjje=0;
		double sqfkje=0;
		double zhtje=0;
		double ljfkje=0;
		DecimalFormat df=(DecimalFormat)NumberFormat.getInstance();
		df.setMaximumFractionDigits(2);
		df.setGroupingUsed(false);
		zhtje =Double.parseDouble(Util.null2o(rs.getString("zhtje")));//合同金额
		
		ljfkje =Double.parseDouble(Util.null2o(rs.getString("yifje")));//累计付款金额
		sqfkje =Double.parseDouble(Util.null2o(rs.getString("sqfkje")));//申请付款金额
		//mainid

		new BaseBean().writeLog("合同金额=-----"+zhtje+"累计付款金额="+sqfkje+"");
		
		ljfkje =BigDecimalUtil.add(ljfkje, sqfkje) ;//归档后累计付款金额= 累计付款金额+申请付款金额
		qjje =BigDecimalUtil.sub(zhtje, ljfkje);
		
		String mainid = rs.getString("id");
		
		sql = "update  "+tablename+" set syje='"+df.format(qjje)+"',yifje='"+df.format(ljfkje)+"'where requestid = "+requestid;
		rs.executeSql(sql);
		
		new BaseBean().writeLog("采购合同付款更新数据-----requestid="+requestid+" sql="+sql);
		
		
		
	    
	    
	    
		
		return Action.SUCCESS;
		
		}catch(Exception e){
			e.printStackTrace();
			new BaseBean().writeLog("requestid:"+requestid+"的采购合同付款归档前更新数据失败");
			return "0";
		}
	
		
	}
	

}
