package com.jiuyi.action;

import com.jiuyi.entity.SaleAndDeliveryParticulars;
import com.jiuyi.util.JiuyiUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
/**
 * 插入销售发货明细表字段
 * @author SCP
 * 
 */
public class InsertSaleAndDeliveryMXSql {
	public String inserSql(String requestid,String tablename,SaleAndDeliveryParticulars[] sp){
		try{
			String sql = "select id from "+tablename+" where requestid = '"+requestid+"'";
			RecordSet rs = new RecordSet();
			rs.execute(sql);
			String POSNR;//        行项目编号
			String MATNR;//        物料编码
			String MAKTX;//        物料描述
			String MEINS;//        单位
			String KWMENG;//       订单数量
			String LFIMG;//        提货数量--
			String PRICE;//        销售单价--
			String AMOUNT;//       金额--
			String MATKL;//        产品分类
			String ZVTEXT;//       分类描述
			String ZCOMMENT2;//    行项目备注
			String WERKS;//        发货工厂
			String ZFHGCMS;//      工厂描述
			String LGORT;//        发货仓库
			String ZFHCKMS;//      仓库名称
			String BB;//      币别
			
			if(rs.next()){
				String mainid = rs.getString("id");
				for (int i =0 ;i<sp.length;i++){
					POSNR = Util.null2String(sp[i].getPOSNR());
					MATNR = Util.null2String(sp[i].getMATNR());
					MAKTX = Util.null2String(sp[i].getMAKTX());     
					MEINS = Util.null2String(sp[i].getMEINS());          
					KWMENG = Util.null2String(sp[i].getKWMENG());     
					LFIMG = Util.null2String(sp[i].getLFIMG()); 
					PRICE = Util.null2String(sp[i].getPRICE());     
					AMOUNT = Util.null2String(sp[i].getAMOUNT());
					MATKL = Util.null2String(sp[i].getMATKL());
					ZVTEXT = Util.null2String(sp[i].getZVTEXT());     
					ZCOMMENT2 = Util.null2String(sp[i].getZCOMMENT2());       
					WERKS = Util.null2String(sp[i].getWERKS());
					ZFHGCMS = Util.null2String(sp[i].getZFHGCMS());     
					LGORT = Util.null2String(sp[i].getLGORT());
					ZFHCKMS = Util.null2String(sp[i].getZFHCKMS()); 
					BB = Util.null2String(sp[i].getBB());    
					sql = "insert into " + tablename + "_dt1 " +
							"(mainid,POSNR,MATNR,MAKTX,MEINS,KWMENG,LFIMG,PRICE,AMOUNT,MATKL,ZVTEXT,ZCOMMENT2,WERKS,ZFHGCMS,LGORT,ZFHCKMS,BB) " +
							"values ('"+mainid+"','"+POSNR+"','"+MATNR+"','"+MAKTX+"','"+MEINS+"','"+KWMENG+"','"+JiuyiUtil.getQfw(Double.parseDouble(LFIMG))+"','"+PRICE+"'" +
									",'"+JiuyiUtil.getQfw(Double.parseDouble(AMOUNT))+"','"+MATKL+"','"+ZVTEXT+"','"+ZCOMMENT2+"','"+WERKS+"','"+ZFHGCMS+"','"+LGORT+"','"+ZFHCKMS+"','"+BB+"')";
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
