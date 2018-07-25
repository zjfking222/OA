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

import com.jiuyi.entity.EquipmentReceiveMainTable;
import com.jiuyi.entity.EquipmentReceiveParticulars;
import com.jiuyi.entity.MessageInfo;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.JudgeUserId;
import com.jiuyi.util.PropUtil;

/**
 * PM设备领用流程
 * @author SCP
 *
 */
public class EquipmentReceive {
	public MessageInfo execute(EquipmentReceiveMainTable em ,EquipmentReceiveParticulars[] ep){
		BaseBean log = new BaseBean();
		RecordSet rs = new RecordSet();
		MessageInfo msg = new MessageInfo();
		try{ 
			 String loginid = em.getShenqr();
			 String userid = JudgeUserId.judegid(loginid);
			 log.writeLog("发起设备的人员的loginid"+loginid+"/userid:"+userid);
			 log.writeLog("发起设备的人员的loginid"+loginid+"/userid:"+userid);
			 if (!userid.equals("")) {
				 RequestInfo info = new RequestInfo();
	             info.setCreatorid(userid);// 流程发起人
	             info.setWorkflowid(PropUtil.EquipmentReceive);// 发起采购流程
	             info.setRequestlevel("0");// 紧急程度
	             info.setRemindtype("0");// 提醒类型
	             info.setIsNextFlow("0");// 是否提交到下一节点 1提交到下一节点 0停留在创建节点
	             MainTableInfo mti = new MainTableInfo();
	             Property[] p = new Property[16];
	             
	             p[0] = new Property();//申请人
	             p[0].setName("shenqr");
	             p[0].setValue(userid);
	             
	             p[1] = new Property();//申请部门
	             p[1].setName("shenqbm");
	             p[1].setValue(JiuyiUtil.getUserDepartmentByUserId(userid));
	             
	             p[2] = new Property();//申请日期
	             p[2].setName("shenqrq");
	             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	             p[2].setValue(sdf.format(new Date()));
	             

	             p[3] = new Property();//内部订单
	             p[3].setName("AUFNR");
	             p[3].setValue(Util.null2String(ep[0].getAUFNR()));
	             
	             p[4] = new Property();//内部订单描述
	             p[4].setName("KTEXT");
	             p[4].setValue(Util.null2String(ep[0].getKTEXT()));
	             
	             p[5] = new Property();//成本中心
	             p[5].setName("KOSTL");
	             p[5].setValue(Util.null2String(ep[0].getKOSTL()));
	             
	             p[6] = new Property();//成本中心描述
	             p[6].setName("MCTXT");
	             p[6].setValue(Util.null2String(ep[0].getMCTXT()));
	             
	             String YDLX = "-1";
	             if(!"".equals(Util.null2String(ep[0].getAUFNR()))){
	            	 YDLX = "2";
	             }else if(!"".equals(Util.null2String(ep[0].getKOSTL()))){
	            	 YDLX = "1";
	             }
	             p[7] = new Property();//移动类型
	             p[7].setName("YDLX");
	             p[7].setValue(YDLX);

	             p[8] = new Property();//需求日期
	             p[8].setName("BDTER");
	             p[8].setValue(sdf.format(new Date()));

	             p[9] = new Property();
	             p[9].setName("WERKS");
	             p[9].setValue(Util.null2String(ep[0].getWERKS()));;//工厂
	             
	             p[10] = new Property();
	             p[10].setName("NAME1");
	             p[10].setValue(Util.null2String(ep[0].getNAME1()));;//工厂描述
	             
	             p[11] = new Property();
	             p[11].setName("LGORT");
	             p[11].setValue(Util.null2String(ep[0].getLGORT()));;//库存地点
	             
	             p[12] = new Property();
	             p[12].setName("LGOBE");
	             p[12].setValue(Util.null2String(ep[0].getLGOBE()));;//库存地点描述
	             
	             p[13] = new Property();
	             p[13].setName("lx");
	             p[13].setValue(""+Util.getIntValue(em.getLx(),-1));;//类型
	             
	             p[14] = new Property();
	             p[14].setName("sap");
	             p[14].setValue("0");//是否SAP发起
	             
	             String sql = "select locationid from hrmresource where id = "+userid;
	             new BaseBean().writeLog("sql=="+sql);
	             rs.execute(sql);
	             rs.next();
	             
	             
	             p[15] = new Property();
	             p[15].setName("bgdd");
	             p[15].setValue(rs.getString("locationid"));//办公地点
	            
	             
	             
	             mti.setProperty(p);
	             info.setMainTableInfo(mti);
	             StringBuffer description = new StringBuffer();
	             description.append("PM设备领用流程-").append(JiuyiUtil.getLastNameByUserId(userid)).append("-").append(sdf.format(new Date()));
	             info.setDescription(description.toString());// 流程标题
	             RequestService reqSvc = new RequestService();
	             String reqid = reqSvc.createRequest(info);
	             new BaseBean().writeLog("发起的PM设备领取流程requestid="+reqid);
	             if (Integer.parseInt(reqid) > 0) { // 主表创建成功
	            	 InsertEquipmentReceiveMXSql insertMXSql = new InsertEquipmentReceiveMXSql();
	            	 String m = insertMXSql.inserSql(reqid, JiuyiUtil.getTablenameBywfid(PropUtil.EquipmentReceive), ep);
	            	 if("S".equals(m)){
	            		 msg.setMsg("发起流程成功！");
	            		 msg.setRequestid(reqid);
	            	 }else{
	            		 msg.setMsg("主表创建成功,明细发起失败");
	            		 msg.setRequestid(reqid);
	            	 }
	             }else{
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
		}catch(Exception e){
			 e.printStackTrace();
			 new BaseBean().writeLog("发起PM设备领用流程失败"+e.getMessage());
			 msg.setMsg("发起PM设备领用流程失败"+e.getMessage());
		}
		return msg;
	}
}
