package com.jiuyi.entity;

/**
 * 不合格主表实体类
 * 
 * @author SCP
 * 
 */
public class DisqualificationMainTable {
	private String SQR;// 申请人
	private String SQRQ;// 申请日期
	private String PRUEFLOS;// 检验批号码
	private String QMNUM;// 通知单编码
	private String QMART;// 通知类型
	private String QMNAM;//报告人


	public String getSQR() {
		return SQR;
	}

	public void setSQR(String sQR) {
		SQR = sQR;
	}

	public String getSQRQ() {
		return SQRQ;
	}

	public void setSQRQ(String sQRQ) {
		SQRQ = sQRQ;
	}

	public String getPRUEFLOS() {
		return PRUEFLOS;
	}

	public void setPRUEFLOS(String pRUEFLOS) {
		PRUEFLOS = pRUEFLOS;
	}

	public String getQMNUM() {
		return QMNUM;
	}

	public void setQMNUM(String qMNUM) {
		QMNUM = qMNUM;
	}

	public String getQMART() {
		return QMART;
	}

	public void setQMART(String qMART) {
		QMART = qMART;
	}

	public String getQMNAM() {
		return QMNAM;
	}

	public void setQMNAM(String qMNAM) {
		QMNAM = qMNAM;
	}
	

}
