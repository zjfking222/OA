package com.jiuyi.action;


import com.jiuyi.entity.CertificateParticulars;
import com.jiuyi.util.JiuyiUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
/**
 * 插入物资出门证明细表字段
 * @author SCP
 * 
 */
public class InsertCertificateMXSql {
	public String inserSql(String mainid,String tablename,CertificateParticulars[] cp){
		try{
			RecordSet rs = new RecordSet();
			String POSNR;// 行号
			String MATNR;// 商品编码	ZZWFHL
			String MAKTX;// 商品名称
			String GROES;// 规格型号
			String MEINS;// 单位
			String LGORT;// 发货仓库
			String ZFHCKMS;// 仓库名称
			double LFIMG;// 发货总量
			double ZZBCFHL;// 本次发货量
			double ZZLJFHL;// 累计发货量
			double ZZWFHL;// 未发货量

			for (int i =0 ;i<cp.length;i++){
				POSNR= Util.null2String(cp[i].getPOSNR());
				MATNR= Util.null2String(cp[i].getMATNR());
				MAKTX= Util.null2String(cp[i].getMAKTX());
				GROES= Util.null2String(cp[i].getGROES());
				MEINS= Util.null2String(cp[i].getMEINS());
				LGORT= Util.null2String(cp[i].getLGORT());
				ZFHCKMS= Util.null2String(cp[i].getZFHCKMS());					
				if("".equals(Util.null2String(cp[i].getLFIMG()))){
					LFIMG= 0;
					ZZWFHL = 0;
				}else{
					LFIMG= Util.getDoubleValue(cp[i].getLFIMG());
					ZZWFHL = LFIMG;
				}
				if("".equals(Util.null2String(cp[i].getZZBCFHL()))){
					ZZBCFHL= 0;
				}else{
					ZZBCFHL= Util.getDoubleValue(cp[i].getZZBCFHL());
				}
					
				if("".equals(Util.null2String(cp[i].getZZLJFHL()))){
					ZZLJFHL= 0;
				}else{
					ZZLJFHL= Util.getDoubleValue(cp[i].getZZLJFHL());
				}
					
				String sql = "select * from " + tablename + "_dt1 where mainid='"+mainid+"' and POSNR='"+POSNR+"'";
				rs.execute(sql);
				if(rs.next()){
					sql="update " + tablename + "_dt1 set LFIMG='"+JiuyiUtil.getQfw(LFIMG)+"' where mainid='"+mainid+"' and POSNR='"+POSNR+"'";
				}else{
					sql = "insert into " + tablename + "_dt1 " +
							"(mainid,POSNR,MATNR,MAKTX,GROES,MEINS,LGORT,ZFHCKMS,LFIMG,ZZBCFHL,ZZLJFHL,ZZWFHL) " +
							"values ('"+mainid+"','"+POSNR+"','"+MATNR+"','"+MAKTX+"','"+GROES+"','"+MEINS+"','"+LGORT+"','"+ZFHCKMS+"'" +
									",'"+JiuyiUtil.getQfw(LFIMG)+"','"+JiuyiUtil.getQfw(ZZBCFHL)+"','"+JiuyiUtil.getQfw(ZZLJFHL)+"','"+ZZWFHL+"')";
				}
				new BaseBean().writeLog("插入明细sql:"+sql);
				rs.execute(sql);
			}
			return "S";
		}catch(Exception e){
			e.printStackTrace();
			new BaseBean().writeLog("id:"+mainid+"的明细插入失败");
			return "E";
		}
	}
}
