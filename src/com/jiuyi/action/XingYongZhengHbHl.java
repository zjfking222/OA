package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

import com.jiuyi.util.SAPUtil;


/**
 * 信用证货币汇率
 * @author yangc
 *
 */
public class XingYongZhengHbHl implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		
		//查询外证货币hub  开证总金额（USD） kzzjeusd 开证总金额（RMB）zjermb  开证总金额（EUR）kzzjeEUR  人民币汇率 huil 欧元汇率 oyhl      开证总金额   kzzje   汇率hl
		// 内证货币hub1 开证总金额（USD） kzhzongjeusd 开证总金额（RMB）zjermb2  开证总金额（EUR）kzzjeEUR2  人民币汇率 huil2 欧元汇率 oyhl2    开证总金额  kzzje2      汇率hl2
		//
		String sql = "select hub,hub1,kzzjeusd,kzhzongjeusd,zjermb,zjermb2,kzzjeEUR,kzzjeEUR2,huil,huil2,oyhl,oyhl2 from "+tablename+" where  requestid="+requestid;
		new BaseBean().writeLog("sql--=="+sql);
		rs.executeSql(sql);
		rs.next();
		String hub = rs.getString("hub");//查询外证货币hub
		String kzzjeusd = rs.getString("kzzjeusd");//开证总金额（USD）
		String zjermb = rs.getString("zjermb");//开证总金额（RMB）
		String kzzjeEUR = rs.getString("kzzjeEUR");//开证总金额（EUR）
		String huil = rs.getString("huil");//人民币汇率
		String oyhl = rs.getString("oyhl");//欧元汇率
		String kzzje = rs.getString("kzzje");//开证总金额
		String hl = rs.getString("hl");//汇率
		
		new BaseBean().writeLog("hub--=="+hub);
		new BaseBean().writeLog("kzzjeusd--=="+kzzjeusd);
		new BaseBean().writeLog("zjermb--=="+zjermb);
		new BaseBean().writeLog("kzzjeEUR--=="+kzzjeEUR);
		new BaseBean().writeLog("huil--=="+huil);
		new BaseBean().writeLog("oyhl--=="+oyhl);
		new BaseBean().writeLog("kzzje--=="+kzzje);
		new BaseBean().writeLog("hl--=="+hl);
		
		if(hub.equals("USD")){
			kzzje = kzzjeusd;
		}else if(hub.equals("CNY")||hub.equals("RMB")){
			kzzje = zjermb;
			hl = huil;
		}else if(hub.equals("EUR")){
			kzzje = kzzjeEUR;
			hl = oyhl;
		}
		String sql1 = "update "+tablename+" set kzzje='"+kzzje+"' , hl='"+hl+"' where requestid="+requestid;
		new BaseBean().writeLog("sql1--=="+sql1);
		rs1.executeSql(sql1);
		
		String hub1 = rs.getString("hub1");//内证货币hub1
		String kzhzongjeusd = rs.getString("kzhzongjeusd");//开证总金额（USD）
		String zjermb2 = rs.getString("zjermb2");//开证总金额（RMB）
		String kzzjeEUR2 = rs.getString("kzzjeEUR2");//开证总金额（EUR）
		String huil2 = rs.getString("huil2");//人民币汇率
		String oyhl2 = rs.getString("oyhl2");//欧元汇率
		String kzzje2 = rs.getString("kzzje2");//开证总金额
		String hl2 = rs.getString("hl2");//汇率
		
		new BaseBean().writeLog("hub1--=="+hub1);
		new BaseBean().writeLog("kzhzongjeusd--=="+kzhzongjeusd);
		new BaseBean().writeLog("zjermb2--=="+zjermb2);
		new BaseBean().writeLog("kzzjeEUR2--=="+kzzjeEUR2);
		new BaseBean().writeLog("huil2--=="+huil2);
		new BaseBean().writeLog("oyhl2--=="+oyhl2);
		new BaseBean().writeLog("kzzje2--=="+kzzje2);
		new BaseBean().writeLog("hl2--=="+hl2);
		
		if(hub1.equals("USD")){
			kzzje2 = kzhzongjeusd;
		}else if(hub1.equals("CNY")){
			kzzje2 = zjermb2;
			hl2 = huil2;
		}else if(hub1.equals("EUR")){
			kzzje2 = kzzjeEUR2;
			hl2 = oyhl2;
		}

		String sql2 = "update "+tablename+" set kzzje2='"+kzzje2+"' , hl2='"+hl2+"' where requestid="+requestid;
		new BaseBean().writeLog("sql2--=="+sql2);
		rs2.executeSql(sql2);
		
		
		
		return Action.SUCCESS;
	}

}
