package com.jiuyi.action;


import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.jiuyi.entity.SellParticulars;
import com.jiuyi.util.JiuyiUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
/**
 * 插入销售开票、冲销明细表字段
 * @author SCP
 * 
 */
public class InsertSellMXSql {
	public String inserSql(String requestid,String tablename,SellParticulars[] sp){
		try{
			String sql = "select id from "+tablename+" where requestid = '"+requestid+"'";
			RecordSet rs = new RecordSet();
			rs.execute(sql);
			String POSNR;//行项目号
			String MATNR;//商品编码
			String MAKTX;//商品名称
			String GROES;//规格型号
			String MEINS;//计量单位
			String ZDWMS;//计量单位描述
			String FKIMG;//数量--
			String NETWR;//不含税金额--
			String ZZSSL;//税率
			String MWSBP;//税额--
			String BHSDJ;//不含税单价
			String HSDJ;//含税单价
			String AMOUNT;//含税金额--
			String ZYBF;//含税金额
			String BB;//币别
			String YLZD1;//预留字段1
			String YLZD2;//预留字段2
			String ZYKPSL;//已申请开票数量
			String ZWKPSL;//未申请开票数量
			String SORT1;//客户简称
			String ZHXMBY1;//行项目备用
			String ZHXMBY2;//行项目备用
			String ZHXMBY3;//行项目备用
			String ZHXMBY4;//行项目备用
			String ZHXMBY5;//行项目备用
			if(rs.next()){
				String mainid = rs.getString("id");
				for (int i =0 ;i<sp.length;i++){
					POSNR= Util.null2String(sp[i].getPOSNR());//行项目号
					MATNR= Util.null2String(sp[i].getMATNR());//商品编码
					MAKTX= Util.null2String(sp[i].getMAKTX());//商品名称
					GROES= Util.null2String(sp[i].getGROES());//规格型号
					MEINS= Util.null2String(sp[i].getMEINS());//计量单位
					ZDWMS= Util.null2String(sp[i].getZDWMS());//计量单位描述
					FKIMG= JiuyiUtil.getRightfs(Util.null2String(sp[i].getFKIMG()));//数量--
					NETWR= JiuyiUtil.getRightfs(Util.null2String(sp[i].getNETWR()));//不含税金额--
					ZZSSL= JiuyiUtil.getRightfs(Util.null2String(sp[i].getZZSSL()));//税率
					MWSBP= JiuyiUtil.getRightfs(Util.null2String(sp[i].getMWSBP()));//税额--
					BHSDJ= JiuyiUtil.getRightfs(Util.null2String(sp[i].getBHSDJ()));//不含税单价
					HSDJ= JiuyiUtil.getRightfs(Util.null2String(sp[i].getHSDJ()));//含税单价
					AMOUNT= JiuyiUtil.getRightfs(Util.null2String(sp[i].getAMOUNT()));//含税金额--
					ZYBF= Util.null2String(sp[i].getZYBF());//海运费
					BB= Util.null2String(sp[i].getBB());//币别
					YLZD1 = Util.null2String(sp[i].getYLZD1());//保险费
					YLZD2 = Util.null2String(sp[i].getYLZD2());//汇率
					ZYKPSL=Util.null2String(sp[i].getZYKPSL());//已申请开票数量
					ZWKPSL=Util.null2String(sp[i].getZWKPSL());//未申请开票数量

					ZHXMBY1=Util.null2String(sp[i].getZHXMBY1());//行项目备用
					ZHXMBY2=Util.null2String(sp[i].getZHXMBY2());//行项目备用
					ZHXMBY3=Util.null2String(sp[i].getZHXMBY3());//行项目备用
					ZHXMBY4=Util.null2String(sp[i].getZHXMBY4());//行项目备用
					ZHXMBY5=Util.null2String(sp[i].getZHXMBY5());//行项目备用
					sql = "insert into " + tablename + "_dt1 " +
							"(mainid,POSNR,MATNR,MAKTX,MEINS,GROES,ZDWMS,FKIMG,NETWR,ZZSSL,MWSBP,BHSDJ,HSDJ,AMOUNT,ZYBF,BB,YLZD1,YLZD2,ZHXMBY1,ZHXMBY2,ZHXMBY3,ZHXMBY4,ZHXMBY5,ZYKPSL,ZWKPSL) " +
							"values ('"+mainid+"','"+POSNR+"','"+MATNR+"','"+MAKTX+"','"+MEINS+"','"+GROES+"','"+ZDWMS+"','"+JiuyiUtil.getQfw(Double.parseDouble(FKIMG))+"'" +
									",'"+JiuyiUtil.getQfw(Double.parseDouble(NETWR))+"','"+ZZSSL+"','"+JiuyiUtil.getQfw(Double.parseDouble(MWSBP))+"','"+BHSDJ+"','"+HSDJ+"','"+JiuyiUtil.getQfw(Double.parseDouble(AMOUNT))+"','"+ZYBF+"','"+BB+"','"+YLZD1+"','"+YLZD2+"','"+ZHXMBY1+"','"+ZHXMBY2+"','"+ZHXMBY3+"','"+ZHXMBY4+"','"+ZHXMBY5+"','"+ZYKPSL+"','"+ZWKPSL+"')";
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
