package com.jiuyi.action;

import com.jiuyi.entity.DisqualificationParticulars;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
/**
 * 插入不合格明细表字段
 * @author SCP
 * 
 */
public class InsertMXSql {
	public String inserSql(String requestid,String tablename,DisqualificationParticulars[] dfp){
		try{
			String sql = "select id from "+tablename+" where requestid = '"+requestid+"'";
			RecordSet rs = new RecordSet();
			rs.execute(sql);
			String MATNR;//物料编码
			String MAWERK;//物料工厂
			String CHARG;//SAP批次编码
			String LICHN;//供应商批次编码
			String ATWRT;//生产批次编码 
			String EBELN;//采购凭证
			String HEADKTXT;//问题描述长文本
			String EKORG;//采购组编码
			String EKOTX;//采购组
			String BKGRP;//采购员编码
			String EKNAM;//采购员
			String RKMNG;//投诉的数量
			String LIFNUM;//供应商编码
			String LIFNUME;//供应商名称
			String FERTAUFNR;//生产订单编码
			String STEXT;//工作中心描述
			if(rs.next()){
				String mainid = rs.getString("id");
				for (int i =0 ;i<dfp.length;i++){
					MATNR = Util.null2String(dfp[i].getMATNR());//物料编码
					MAWERK = Util.null2String(dfp[i].getMAWERK());//物料工厂
					CHARG = Util.null2String(dfp[i].getCHARG());//SAP批次编码
					LICHN = Util.null2String(dfp[i].getLICHN());//供应商批次编码
					ATWRT = Util.null2String(dfp[i].getATWRT());//生产批次编码
					EBELN = Util.null2String(dfp[i].getEBELN());//采购凭证
					HEADKTXT = Util.null2String(dfp[i].getHEADKTXT());//问题描述长文本
					EKORG = Util.null2String(dfp[i].getEKORG());//采购组编码
					EKOTX = Util.null2String(dfp[i].getEKOTX());//采购组
					BKGRP = Util.null2String(dfp[i].getBKGRP());//采购员编码
					EKNAM = Util.null2String(dfp[i].getEKNAM());//采购员
					RKMNG = Util.null2String(dfp[i].getRKMNG());//投诉的数量
					LIFNUM = Util.null2String(dfp[i].getLIFNUM());//供应商编码
					LIFNUME = Util.null2String(dfp[i].getLIFNUME());//供应商名称
					FERTAUFNR = Util.null2String(dfp[i].getFERTAUFNR());//生产订单编码
					STEXT = Util.null2String(dfp[i].getSTEXT());//工作中心描述
					
					sql = "insert into " + tablename + "_dt1 " +
							"(mainid,MATNR,MAWERK,CHARG,LICHN,ATWRT,EBELN,HEADKTXT,EKORG,EKOTX,BKGRP,EKNAM,RKMNG,LIFNUM,LIFNUME,FERTAUFNR,STEXT) " +
							"values ('"+mainid+"','"+MATNR+"','"+MAWERK+"','"+CHARG+"','"+LICHN+"','"+ATWRT+"','"+EBELN+"','"+HEADKTXT+"'" +
									",'"+EKORG+"','"+EKOTX+"','"+BKGRP+"','"+EKNAM+"','"+RKMNG+"','"+LIFNUM+"','"+LIFNUME+"','"+FERTAUFNR+"','"+STEXT+"')";
					new BaseBean().writeLog("插入明细sql:"+sql);
					rs.execute(sql);
				}
			}
			return "S";
		}catch(Exception e){
			e.printStackTrace();
			new BaseBean().writeLog("requestid:"+requestid+"的明细插入失败");
			return "E";
		}
	}
}
