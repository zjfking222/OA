package com.jiuyi.action;
/**
 * 销售发货
 */
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
import com.jiuyi.entity.SaleAndDeliveryMainTable;
import com.jiuyi.entity.SaleAndDeliveryParticulars;
import com.jiuyi.util.CommonUtil;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.JudgeUserId;
import com.jiuyi.util.PropUtil;

public class SaleAndDelivery {
	 public MessageInfo execute(SaleAndDeliveryMainTable sm,SaleAndDeliveryParticulars[] sp) {
		 MessageInfo msg = new MessageInfo();
		 try{ 
			 String loginid = Util.null2String(sm.getERNAM());
			 String userid = JudgeUserId.judegid(loginid);
			 new BaseBean().writeLog("销售发货流程loginid=="+loginid);
			 new BaseBean().writeLog("销售发货流程userid=="+userid);
			 if (!userid.equals("")) {
	             new BaseBean().writeLog("销售发货流程vkbur="+sm.getVKBUR());
	             new BaseBean().writeLog("销售发货流程 workflowid="+PropUtil.SaleAndDelivery);
				 RequestInfo info = new RequestInfo();
				 RecordSet rs = new RecordSet();
	             info.setCreatorid(userid);// 流程发起人
	             info.setWorkflowid(PropUtil.SaleAndDelivery);// 发起销售发货流程
	             info.setRequestlevel("0");// 紧急程度
	             info.setRemindtype("0");// 提醒类型
				 boolean test1=sm.getZCQTS().equals("0");
				 boolean test2="01".equals(sm.getSplx());
				 new BaseBean().writeLog("test1"+test1+"test2"+test2);
	             if(sm.getBURKS().equals("1020")||("01".equals(sm.getSplx())&&sm.getZCQTS().equals("0"))){
	            	 info.setIsNextFlow("0");// 是否提交到下一节点 1提交到下一节点 0停留在创建节点
	             }else{
	            	 info.setIsNextFlow("1");// 是否提交到下一节点 1提交到下一节点 0停留在创建节点
	             }
	             MainTableInfo mti = new MainTableInfo();
	             Property[] p = new Property[44];
	             
	             p[0] = new Property();//公司代码
	             p[0].setName("BURKS");
	             p[0].setValue(Util.null2String(sm.getBURKS()));
	             
	             p[1] = new Property();//公司名称
	             p[1].setName("ZGSDMMS");
	             p[1].setValue(Util.null2String(sm.getZGSDMMS()));
	             
	             p[2] = new Property();//发货单号
	             p[2].setName("VBELN");
	             p[2].setValue(Util.null2String(sm.getVBELN()));
	             
	             p[3] = new Property();//华友合同编号
	             p[3].setName("BSTKD");
	             p[3].setValue(Util.null2String(sm.getBSTKD()));
	             
	             p[4] = new Property();//订单编号
	             p[4].setName("ZVBELN");
	             p[4].setValue(Util.null2String(sm.getZVBELN()));
	             
	             p[5] = new Property();//部门名称
	             p[5].setName("VKBUR");
	             p[5].setValue(Util.null2String(sm.getVKBUR()));
	             
	             p[6] = new Property();//部门描述
	             p[6].setName("ZBMMS");
	             p[6].setValue(Util.null2String(sm.getZBMMS()));
	              
	             p[7] = new Property();//收货方
	             p[7].setName("KUNNR");
	             p[7].setValue(Util.null2String(sm.getKUNNR()));
	             
	             p[8] = new Property();//收货方名称
	             p[8].setName("NAME1");
	             p[8].setValue(Util.null2String(sm.getNAME1()));
	             
	             p[9] = new Property();//下达日期
	             p[9].setName("ERDAT");
	             p[9].setValue(Util.null2String(sm.getERDAT()));
	             
	             p[10] = new Property();//要求发货日期
	             p[10].setName("WADAT");
	             p[10].setValue(Util.null2String(sm.getWADAT()));
	             
	             p[11] = new Property();//付款条款
	             p[11].setName("ZTERM");
	             p[11].setValue(Util.null2String(sm.getZTERM()));
	             
	             p[12] = new Property();//描述
	             p[12].setName("ZTEXT1");
	             p[12].setValue(Util.null2String(sm.getZTEXT1()));
	             
	             p[13] = new Property();//本次发货量--
	             p[13].setName("ZZHJSL");
	             p[13].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZZHJSL()))));
	             
	             p[14] = new Property();//合计金额
	             p[14].setName("ZZHJJE");
	             p[14].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZZHJJE()))));
	             
	             p[15] = new Property();//创建人
	             p[15].setName("ERNAM");
	             p[15].setValue(Util.null2String(sm.getERNAM()));
	             
	             p[16] = new Property();//销售员编码
	             p[16].setName("ZXSYBM");
	             p[16].setValue(Util.null2String(sm.getZXSYBM()));
	             
	             p[17] = new Property();//销售员
	             p[17].setName("ZXSYXM");
	             p[17].setValue(Util.null2String(sm.getZXSYXM()));
	             
	             p[18] = new Property();//送货地址
	             p[18].setName("ZZSHDZ");
	             p[18].setValue(Util.null2String(sm.getZZSHDZ()));
	             
	             p[19] = new Property();//送货电话
	             p[19].setName("ZZSHLXR");
	             p[19].setValue(Util.null2String(sm.getZZSHLXR()));
	             
	             p[20] = new Property();//送货联系人
	             p[20].setName("ZZSHDH");
	             p[20].setValue(Util.null2String(sm.getZZSHDH()));
	             
	             p[21] = new Property();//订单备注
	             p[21].setName("ZCOMMENT1");
	             p[21].setValue(Util.null2String(sm.getZCOMMENT1()));
	             
	             p[22] = new Property();//装运条件
	             p[22].setName("VSART");
	             p[22].setValue(Util.null2String(sm.getVSART()));
	             
	             p[23] = new Property();//装运条件描述
	             p[23].setName("ZZYTJMS");
	             p[23].setValue(Util.null2String(sm.getZZYTJMS()));
	             
	             p[24] = new Property();//审批类型
	             p[24].setName("splx");
	             p[24].setValue("".equals(sm.getSplx())?"''":"01".equals(sm.getSplx())?"0":"1");
	             
	             p[25] = new Property();//申请原因
	             p[25].setName("shenqyy");
	             p[25].setValue(Util.null2String(sm.getShenqyy()));

	             p[26] = new Property();//审批部门
	             p[26].setName("shenpbm");
	             p[26].setValue(Util.null2String(CommonUtil.getShenpbm(sm.getVKBUR())));
	             
	             p[27] = new Property();//逾期天数
	             p[27].setName("yqtsh");
	             if(sm.getZCQTS().equals("")){
	            	 p[27].setValue(""+Util.getIntValue(sm.getZCQTS(),0));
	             }else{
	            	 p[27].setValue(Util.null2String(sm.getZCQTS()));
	             }
	             
	             p[28] = new Property();//所属分部
	             p[28].setName("sshfb");
	             if(Util.null2String(sm.getZBMMS()).equals("营销推广部")){
	            	 p[28].setValue(Util.null2String("0"));
	             }else{
	            	 p[28].setValue(Util.null2String("1"));
	             }
	         	

	             p[29] = new Property();//所属分部
	             p[29].setName("ZXYED");
	             p[29].setValue(Util.null2String(sm.getZXYED()));
	             
	             p[30] = new Property();//所属分部
	             p[30].setName("ZYSYJE");
	             p[30].setValue(Util.null2String(sm.getZYSYJE()));
	             
	    	     p[31] = new Property();//所属分部
	    	     p[31].setName("ZXYCE");
	             p[31].setValue(Util.null2String(sm.getZXYCE()));
	             
	             p[32] = new Property();//逾期货款总额(CNY)--
	             p[32].setName("ZYQHKZE");
	             p[32].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZYQHKZE()))));
	             
	             p[33] = new Property();//合同总量(kg)--
	             p[33].setName("ZHTZL");
	             p[33].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZHTZL()))));
	             
	             p[34] = new Property();//已发货量(不含本次)(kg)--
	             p[34].setName("ZYFHL");
	             p[34].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZYFHL()))));
	             
	             p[35] = new Property();//本次发货后合同余量(kg)--
	             p[35].setName("ZHTYL");
	             p[35].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZHTYL()))));
	             
	             p[36] = new Property();//申请人
	             p[36].setName("sher");
	             p[36].setValue(userid);
	             
	             
	             String sql = "select departmentid from hrmresource where loginid='"+loginid+"'";
	             rs.execute(sql);
	             rs.next();
	             String departmentid = rs.getString(1);
	             new BaseBean().writeLog("departmentid=="+departmentid);
	             p[37] = new Property();//申请人部门
	             p[37].setName("shenbm");
	             p[37].setValue(departmentid);

				 p[38] = new Property();//客户简称
				 p[38].setName("SORT1");
				 p[38].setValue(Util.null2String(sm.getSORT1()));

				 p[39] = new Property();//备用1
				 p[39].setName("ZTDBY1");

//				 p[39].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZTDBY1()))));
				 p[39].setValue("0.0");

				 p[40] = new Property();//备用2
				 p[40].setName("ZTDBY2");
//				 p[40].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZTDBY2()))));

				 p[40].setValue("0.0");

				 p[41] = new Property();//备用3
				 p[41].setName("ZTDBY3");
//				 p[41].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZTDBY3()))));

				 p[41].setValue("0.0");

				 p[42] = new Property();//备用4
				 p[42].setName("ZTDBY4");
//				 p[42].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZTDBY4()))));

				 p[42].setValue("0.0");

				 p[43] = new Property();//备用5
				 p[43].setName("ZTDBY5");
//				 p[43].setValue(Util.null2String(JiuyiUtil.getQfw(Double.parseDouble(sm.getZTDBY5()))));

				 p[43].setValue("0.0");

	             
	             
	             mti.setProperty(p);
	             info.setMainTableInfo(mti);
	             StringBuffer description = new StringBuffer();
	             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	             description.append("销售发货流程-").append(JiuyiUtil.getLastNameByUserId(userid)).append("-").append(sdf.format(new Date()));
	             info.setDescription(description.toString());// 流程标题
	             RequestService reqSvc = new RequestService();
	             String reqid = reqSvc.createRequest(info);
	             new BaseBean().writeLog("销售发货流程requestid="+reqid);
	             if (Integer.parseInt(reqid) > 0) { // 主表创建成功
	            	 InsertSaleAndDeliveryMXSql insetmx = new InsertSaleAndDeliveryMXSql();
	            	 String m = insetmx.inserSql(reqid, JiuyiUtil.getTablenameBywfid(PropUtil.SaleAndDelivery), sp);
	            	 if("S".equals(m)){
	            		 msg.setMsg("发起流程成功！");
	            		 msg.setRequestid(reqid);
	            	 }else{
	            		 msg.setMsg("主表创建成功,明细发起失败");
	            		 msg.setRequestid(reqid);
	            	 }
	             } else {
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
			 }else{
				 new BaseBean().writeLog("根据loginid找不到相对应的人员");
				 msg.setMsg("根据loginid找不到相对应的人员");
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
			 new BaseBean().writeLog("发起销售发货流程失败");
			 msg.setMsg("发起销售发货流程失败");
		 }
		 return msg;
	 }
}
