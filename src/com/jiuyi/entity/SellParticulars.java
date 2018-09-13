package com.jiuyi.entity;
/**
 * 销售开票、冲销明细
 * @author SCP
 *
 */
public class SellParticulars {
	private String POSNR;//行项目号
	private String MATNR;//商品编码
	private String MAKTX;//商品名称
	private String GROES;//规格型号
	private String MEINS;//计量单位
	private String ZDWMS;//计量单位描述
	private String FKIMG;//数量
	private String NETWR;//不含税金额
	private String ZZSSL;//税率
	private String MWSBP;//税额
	private String BHSDJ;//不含税单价
	private String HSDJ;//含税单价
	private String AMOUNT;//含税金额
	private String ZYBF;//运保费
	private String BB;//币别
	private String YLZD1;//预留字段1
	private String YLZD2;//预留字段2
	private String YLZD3;//预留字段3
	private String YLZD4;//预留字段4
	private String ZYKPSL;//已申请开票数量
	private String ZWKPSL;//未申请开票数量
	private String ZHXMBY1;//行项目备用1
	private String ZHXMBY2;
	private String ZHXMBY3;
	private String ZHXMBY4;
	private String ZHXMBY5;
	
	public String getPOSNR() {
		return POSNR;
	}
	public void setPOSNR(String pOSNR) {
		POSNR = pOSNR;
	}
	public String getMATNR() {
		return MATNR;
	}
	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}
	public String getMAKTX() {
		return MAKTX;
	}
	public void setMAKTX(String mAKTX) {
		MAKTX = mAKTX;
	}
	public String getGROES() {
		return GROES;
	}
	public void setGROES(String gROES) {
		GROES = gROES;
	}
	public String getMEINS() {
		return MEINS;
	}
	public void setMEINS(String mEINS) {
		MEINS = mEINS;
	}
	public String getZDWMS() {
		return ZDWMS;
	}
	public void setZDWMS(String zDWMS) {
		ZDWMS = zDWMS;
	}
	public String getFKIMG() {
		return FKIMG;
	}
	public void setFKIMG(String fKIMG) {
		FKIMG = fKIMG;
	}
	public String getNETWR() {
		return NETWR;
	}
	public void setNETWR(String nETWR) {
		NETWR = nETWR;
	}
	public String getZZSSL() {
		return ZZSSL;
	}
	public void setZZSSL(String zZSSL) {
		ZZSSL = zZSSL;
	}
	public String getMWSBP() {
		return MWSBP;
	}
	public void setMWSBP(String mWSBP) {
		MWSBP = mWSBP;
	}
	public String getBHSDJ() {
		return BHSDJ;
	}
	public void setBHSDJ(String bHSDJ) {
		BHSDJ = bHSDJ;
	}
	public String getHSDJ() {
		return HSDJ;
	}
	public void setHSDJ(String hSDJ) {
		HSDJ = hSDJ;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getZYBF() {
		return ZYBF;
	}
	public void setZYBF(String zYBF) {
		ZYBF = zYBF;
	}
	public String getBB() {
		return BB;
	}
	public void setBB(String bB) {
		BB = bB;
	}
	public String getYLZD1() {
		return YLZD1;
	}
	public void setYLZD1(String yLZD1) {
		YLZD1 = yLZD1;
	}
	public String getYLZD2() {
		return YLZD2;
	}
	public void setYLZD2(String yLZD2) {
		YLZD2 = yLZD2;
	}
	public String getYLZD3() {
		return YLZD3;
	}
	public void setYLZD3(String yLZD3) {
		YLZD3 = yLZD3;
	}
	public String getYLZD4() {
		return YLZD4;
	}
	public void setYLZD4(String yLZD4) {
		YLZD4 = yLZD4;
	}

	public String getZHXMBY1() {
		return ZHXMBY1;
	}

	public void setZHXMBY1(String ZHXMBY1) {
		this.ZHXMBY1 = ZHXMBY1;
	}

	public String getZHXMBY2() {
		return ZHXMBY2;
	}

	public void setZHXMBY2(String ZHXMBY2) {
		this.ZHXMBY2 = ZHXMBY2;
	}

	public String getZHXMBY3() {
		return ZHXMBY3;
	}

	public void setZHXMBY3(String ZHXMBY3) {
		this.ZHXMBY3 = ZHXMBY3;
	}

	public String getZHXMBY4() {
		return ZHXMBY4;
	}

	public void setZHXMBY4(String ZHXMBY4) {
		this.ZHXMBY4 = ZHXMBY4;
	}

	public String getZHXMBY5() {
		return ZHXMBY5;
	}

	public void setZHXMBY5(String ZHXMBY5) {
		this.ZHXMBY5 = ZHXMBY5;
	}

	public String getZYKPSL() {
		return ZYKPSL;
	}

	public void setZYKPSL(String ZYKPSL) {
		this.ZYKPSL = ZYKPSL;
	}

	public String getZWKPSL() {
		return ZWKPSL;
	}

	public void setZWKPSL(String ZWKPSL) {
		this.ZWKPSL = ZWKPSL;
	}
}
