package com.jiuyi.util;



import weaver.general.BaseBean;
/**
 * 获取prop文件值
 * @author SCP
 *
 */
public class PropUtil {
	/**售后不合格处理流程	 */
	public static String AfterSalesDisqualification = new BaseBean().getPropValue("disqualification", "AfterSalesDisqualification");
	/**产品不合格处理流程	 */
	public static String ProductDisqualification = new BaseBean().getPropValue("disqualification", "ProductDisqualification");
	/**原料不合格处理流程	 */
	public static String RawMaterialDisqualification = new BaseBean().getPropValue("disqualification", "RawMaterialDisqualification");
	/**销售发货*/
	public static String SaleAndDelivery = new BaseBean().getPropValue("disqualification", "SaleAndDelivery");
	/**销售冲销*/
	public static String  SellWriteOff = new BaseBean().getPropValue("disqualification", "SellWriteOff");
	/**销售开票*/
	public static String SellTickets = new BaseBean().getPropValue("disqualification", "SellTickets");
	/**物资出门证*/
	public static String Certificate = new BaseBean().getPropValue("disqualification", "Certificate");
	/***/
	public static String EquipmentReceive = new BaseBean().getPropValue("disqualification", "EquipmentReceive");
	/**国内月度需求计划报批流程*/
	public static String Monthlydemandplan = new BaseBean().getPropValue("disqualification", "Monthlydemandplan");
	
}
