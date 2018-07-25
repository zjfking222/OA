package com.jiuyi.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;

import com.jiuyi.entity.EquipmentReceiveParticulars;

/**
 * 插入PM设备领用明细表字段
 * @author SCP
 * 
 */
public class InsertEquipmentReceiveMXSql {
	public String inserSql(String requestid, String tablename, EquipmentReceiveParticulars[] ep){
		try{
			String sql = "select id from "+tablename+" where requestid = '"+requestid+"'";
			RecordSet rs = new RecordSet();
			rs.execute(sql);
			String MATNR;//	物料编码
			String MAKTX;//	物料描述
			String ERFMG;//	数量
			String ERFME;//	单位
			String feixzsy;//非限制使用
			String MATKL;//	物料组
			String CHARG;//	批次
			String beiz;//	备注
			String gdzc;//	固定资产
			String zcms;//	资产描述
			String zclb;//	资产类别
			String TXK50;//资产类别描述
			if(rs.next()){
				String mainid = rs.getString("id");
				for (int i =0 ;i<ep.length;i++){
					MATNR = Util.null2String(ep[i].getMATNR());//	物料编码
					MAKTX = Util.null2String(ep[i].getMAKTX());//	物料描述
					ERFMG = "1";//	数量
					ERFME = Util.null2String(ep[i].getERFME());//	单位
					feixzsy = Util.null2String(ep[i].getCLASS());//非限制使用
					MATKL = Util.null2String(ep[i].getMATKL());//	物料组
					CHARG = Util.null2String(ep[i].getCHARG());//	批次
					beiz = "";//	备注
					gdzc = Util.null2String(ep[i].getANLN1());//	固定资产
					zcms = Util.null2String(ep[i].getEQKTX());//	资产描述
					zclb = Util.null2String(ep[i].getANLKL());//	资产类别
					TXK50 = Util.null2String(ep[i].getTXK50());//	
					sql = "insert into " + tablename + "_dt1 " +
							"(mainid,MATNR,MAKTX,ERFMG,ERFME,feixzsy,MATKL,CHARG,beiz,gdzc,zclb,zcms,TXK50) " +
							"values ('"+mainid+"','"+MATNR+"','"+MAKTX+"','"+ERFMG+"','"+ERFME+"','"+feixzsy+"','"+MATKL+"','"+CHARG+"'" +
									",'"+beiz+"','"+gdzc+"','"+zclb+"','"+zcms+"','"+TXK50+"')";
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
