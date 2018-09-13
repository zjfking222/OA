package com.jiuyi.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.RequestService;

import com.jiuyi.entity.MessageInfo;
import com.jiuyi.entity.SellMainTable;
import com.jiuyi.entity.SellParticulars;
import com.jiuyi.util.CommonUtil;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.JudgeUserId;
import com.jiuyi.util.PropUtil;


/**
 * 销售开票、冲销  I为开票  O为冲销
 * @author SCP
 *
 */
public class SellTicketsOrWriteOff {
	public MessageInfo execute(SellMainTable st,SellParticulars[] sp) {
		MessageInfo msg = new MessageInfo();
		try{ 
			 String loginid = st.getERNAM();
			 String userid = JudgeUserId.judegid(loginid);
			 new BaseBean().writeLog("发票loginid=="+loginid);
			 new BaseBean().writeLog("发票userid=="+userid);
			 new BaseBean().writeLog("销售开票、冲销loginid=="+loginid);
			 new BaseBean().writeLog("销售开票、冲销userid=="+userid);
			 String ZZZSLX = "";//增值税发票类型
			 if (!userid.equals("")) {
				 RequestInfo info = new RequestInfo();
				 RecordSet rs = new RecordSet();
				 RecordSet rs1 = new RecordSet();
				 String ssgs = "";//所属公司
	             info.setCreatorid(userid);// 流程发起人
	             String workflowid = PropUtil.SellTickets;
	             info.setWorkflowid(workflowid);// 发起销售开票或冲销流程
	             info.setRequestlevel("0");// 紧急程度
	             info.setRemindtype("0");// 提醒类型
				 new BaseBean().writeLog("发票st.getBURKS()=="+st.getBURKS());
	             if(st.getBURKS().equals("1020")){
	            	 info.setIsNextFlow("0");// 是否提交到下一节点 1提交到下一节点 0停留在创建节点
	             }else{
	            	 info.setIsNextFlow("1");// 是否提交到下一节点 1提交到下一节点 0停留在创建节点
	             }
	             
	             MainTableInfo mti = new MainTableInfo();
	             Property[] p = new Property[44];
	             
	             p[0] = new Property();//公司代码
	             p[0].setName("BURKS");
	             p[0].setValue(Util.null2String(st.getBURKS()));
	             
	             p[1] = new Property();//客户编号
	             p[1].setName("KUNNR");
	             p[1].setValue(Util.null2String(st.getKUNNR()));
	             
	             p[2] = new Property();//社会统一信用代码
	             p[2].setName("STCD1");
	             p[2].setValue(Util.null2String(st.getSTCD1()));
	             
	             p[3] = new Property();//客户地址
	             p[3].setName("STREET");
	             p[3].setValue(Util.null2String(st.getSTREET()));
	             
	             p[4] = new Property();//客户名称
	             p[4].setName("NAME1");
	             p[4].setValue(Util.null2String(st.getNAME1()));

	             p[5] = new Property();//开户行名称
	             p[5].setName("KOINH");
	             p[5].setValue(Util.null2String(st.getKOINH()));
	             
	             p[6] = new Property();//开户账号
	             p[6].setName("BANKN");
	             p[6].setValue(Util.null2String(st.getBANKN()));
	             
	             p[7] = new Property();//开票电话
	             p[7].setName("TELF1");
	             p[7].setValue(Util.null2String(st.getTELF1()));
	             
	             p[8] = new Property();//备注
	             p[8].setName("ZCOMMENT1");
	             p[8].setValue(Util.null2String(st.getZCOMMENT1()));
	             
	             p[9] = new Property();//单据日期
	             p[9].setName("ERDAT");
	             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	             p[9].setValue(sdf.format(new Date()));
	             
	             p[10] = new Property();//单据号
	             p[10].setName("VBELN");
	             p[10].setValue(Util.null2String(st.getVBELN()));
	             
	             p[11] = new Property();//申请人
	             p[11].setName("ERNAM");
	             p[11].setValue(Util.null2String(st.getERNAM()));
	             
	             p[12] = new Property();//部门编码
	             p[12].setName("VKBUR");
	             p[12].setValue(Util.null2String(st.getVKBUR()));
	             
	             p[13] = new Property();//申请部门
	             p[13].setName("ZBMMS");
	             p[13].setValue(Util.null2String(st.getZBMMS()));
				 new BaseBean().writeLog("申请部门=="+st.getZBMMS());

	             p[14] = new Property();//审批部门
	             p[14].setName("shenpbm");
	             p[14].setValue(Util.null2String(CommonUtil.getShenpbm(st.getVKBUR())));
	             


	             p[15] = new Property();//审批部门
	             p[15].setName("ZZJPDZ");
	             p[15].setValue(Util.null2String(st.getZZJPDZ()));//寄票地址

	    	     p[16] = new Property();//审批部门
	    	     p[16].setName("ZZJPLXR");
	             p[16].setValue(Util.null2String(st.getZZJPLXR()));//寄票联系人

	             p[17] = new Property();//审批部门
	             p[17].setName("ZZJPLXDH");
	             p[17].setValue(Util.null2String(st.getZZJPLXDH()));//寄票联系电话

	             p[18] = new Property();//审批部门
	             p[18].setName("ZZJPYB");
	             p[18].setValue(Util.null2String(st.getZZJPYB()));//寄票邮编

		    	 p[19] = new Property();//审批部门
		    	 p[19].setName("ZZZSLX");
		    	 if(st.getZZZSLX().equals("01")){
		    		 ZZZSLX = "增值税专用发票";
		    	 }else if(st.getZZZSLX().equals("02")){
		    		 ZZZSLX = "增值税普通发票";
		    	 }
	             p[19].setValue(Util.null2String(ZZZSLX));//增值税发票类型

	             p[20] = new Property();//审批部门
	             p[20].setName("ZZFPLX");
	             p[20].setValue(Util.null2String(st.getZZFPLX()));//发票类型

	             p[21] = new Property();//审批部门
	             p[21].setName("ZZYWLX");
	             p[21].setValue(Util.null2String(st.getZZYWLX()));//业务类型

	             p[22] = new Property();//所属公司
	             p[22].setName("OAzd4");
	             if(Util.null2String(st.getBURKS()).equals("3010")){
	            	 p[22].setValue(Util.null2String("0"));
	            	 ssgs = "0";//华友钴业（3010）;
	             }else if(Util.null2String(st.getBURKS()).equals("3030")){
	            	 p[22].setValue(Util.null2String("1"));
	            	 ssgs = "1";//力科钴镍（3030）;
	             }else if(Util.null2String(st.getBURKS()).equals("3020")){
	            	 p[22].setValue(Util.null2String("2"));
	            	 ssgs = "2";//衢州有色（3020）;
	             }else if(Util.null2String(st.getBURKS()).equals("4010")){
	            	 p[22].setValue(Util.null2String("3"));
	            	 ssgs = "3";//新能源科技（4010）;
	             }else if(Util.null2String(st.getBURKS()).equals("4020")){
	            	 p[22].setValue(Util.null2String("4"));
	            	 ssgs = "4";//华海科技（4020）;
	             }else if(Util.null2String(st.getBURKS()).equals("2010")){
	            	 p[22].setValue(Util.null2String("5"));
	            	 ssgs = "5";//
	             }
	              
	             //仓库操作员  财务销售会计  出门证最终审核人
	             String sql= "select SDMX3,SDMX5 from formtable_main_237_dt1 where SDMX1 = "+ssgs;
	             new BaseBean().writeLog("sql-----"+sql);
	             rs.executeSql(sql);
	             rs.next();
	             String SDMX2=rs.getString(1);//财务销售会计
	             String SDMX3=rs.getString(2);//开票员
	             
	             p[23] = new Property();//财务销售会计
	             p[23].setName("OAzd5");
	             p[23].setValue(Util.null2String(SDMX2));
	             
	             p[24] = new Property();//开票员
	             p[24].setName("OAzd6");
	             p[24].setValue(Util.null2String(SDMX3));
	             
	             p[25] = new Property();//创建人
	             p[25].setName("OAzd1");
	             p[25].setValue(userid);
	             
	             String sql1 = "select departmentid from hrmresource where loginid='"+loginid+"'";
	             rs1.executeSql(sql1);
	             rs1.next();
	             String departmentid = rs1.getString(1);
	             new BaseBean().writeLog("departmentid=="+departmentid);
	             p[26] = new Property();//创建人部门
	             p[26].setName("OAzd2");
	             p[26].setValue(departmentid);
	             
	             p[27] = new Property();//合同号
	             p[27].setName("YLZD1");
	             p[27].setValue(Util.null2String(st.getYLZD1()));
	             
	             p[28] = new Property();//内部发票号
	             p[28].setName("YLZD2");
	             p[28].setValue(Util.null2String(st.getYLZD2()));
	             
	             p[29] = new Property();//帐册号
	             p[29].setName("YLZD3");
	             p[29].setValue(Util.null2String(st.getYLZD3()));
	             
	             p[30] = new Property();//国际贸易条款1
	             p[30].setName("YLZD4");
	             p[30].setValue(Util.null2String(st.getYLZD4()));

	             p[31] =new Property();//客户简称
				 p[31].setName("SORT1");
				 p[31].setValue(Util.null2String(st.getSORT1()));

				 p[32] =new Property();//已申请开票数量
				 p[32].setName("ZYKPSL");
				 p[32].setValue(Util.null2String(st.getSORT1()));

				 p[33] =new Property();//未申请开票数量
				 p[33].setName("ZWKPSL");
				 p[33].setValue(Util.null2String(st.getSORT1()));

				 p[34] =new Property();//抬头备用1
				 p[34].setName("ZTDBY1");
				 p[34].setValue(Util.null2String(st.getSORT1()));

				 p[35] =new Property();//抬头备用2
				 p[35].setName("ZTDBY2");
				 p[35].setValue(Util.null2String(st.getSORT1()));

				 p[36] =new Property();//抬头备用3
				 p[36].setName("ZTDBY3");
				 p[36].setValue(Util.null2String(st.getSORT1()));

				 p[37] =new Property();//抬头备用4
				 p[37].setName("ZTDBY4");
				 p[37].setValue(Util.null2String(st.getSORT1()));

				 p[38] =new Property();//抬头备用5
				 p[38].setName("ZTDBY5");
				 p[38].setValue(Util.null2String(st.getSORT1()));
	             
	             
	             
	             
	             mti.setProperty(p);
	             info.setMainTableInfo(mti);
	             StringBuffer description = new StringBuffer();
	             description.append("销售开票流程-").append(sdf.format(new Date()));
	             info.setDescription(description.toString());// 流程标题
	             RequestService reqSvc = new RequestService();
	             String reqid = reqSvc.createRequest(info);
	             new BaseBean().writeLog("发起的发票流程requestid="+reqid);
	             if (Integer.parseInt(reqid) > 0) { // 主表创建成功
	            	 InsertSellMXSql insertSellMXSql = new InsertSellMXSql();
	            	 String m =insertSellMXSql.inserSql(reqid, JiuyiUtil.getTablenameBywfid(workflowid), sp);
	            	 if("S".equals(m)){
	            		 msg.setMsg("发起流程成功！");
	            		 msg.setRequestid(reqid);
	            	 }else{
	            		 msg.setMsg("主表创建成功,明细发起失败");
	            	 }
	             } else{
	            	 String errorMessage = "";
	            	 switch (Integer.parseInt(reqid)) {
	 				case -2:
	 					  errorMessage="申请人没有创建权限";
	 					break;
	 				case -3:
	 					 errorMessage=" 创建流程id失败";
	 					 break;
	 				case -4:
	 					  errorMessage="主表创建失败";
	 					break;
	 				case -7:
	 					 errorMessage=" 流程下一节点出错 ";
	 					 break;
	 				case -8:
	 					 errorMessage=" 流程节点自动赋值操作错误！ ";
	 					 break;
	 				default:
	 					break;
	 				}
					 msg.setMsg("流程发起失败"+errorMessage);
	        		 msg.setRequestid(reqid);
	             }
			 }else {
				 new BaseBean().writeLog("根据loginid找不到相对应的人员");
				 msg.setMsg("根据loginid找不到相对应的人员");
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
			 new BaseBean().writeLog("发起销售流程主表失败");
			 msg.setMsg("发起销售流程主表失败");
		 }
		 return msg;
	 }
}
