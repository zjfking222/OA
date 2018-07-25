package com.jiuyi.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jiuyi.entity.DisqualificationMainTable;
import com.jiuyi.entity.DisqualificationParticulars;
import com.jiuyi.entity.MessageInfo;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.JudgeUserId;

import weaver.general.BaseBean;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.RequestService;

/**
 * 不合格处理流程发起
 * @author SCP
 *
 */
public class Disqualification {
	 public MessageInfo execute(DisqualificationMainTable dfmt,DisqualificationParticulars[] dfp,String workflowid) {
		 MessageInfo msg = new MessageInfo();
		 try{ 
			 String lx = dfmt.getQMART();//发起流程   来料 F2  产品 F3 售后 F1
			 String mingcheng ="";//标题名字
			 String loginid = dfmt.getQMNAM();
			 new BaseBean().writeLog("loginid="+loginid);
			 String userid = JudgeUserId.judegid(loginid);
			 new BaseBean().writeLog("userid="+userid);
			 if (!userid.equals("")) {
				 RequestInfo info = new RequestInfo();
	             info.setCreatorid(userid);// 流程发起人
	             info.setWorkflowid(workflowid);// 发起采购流程
	             info.setRequestlevel("0");// 紧急程度
	             info.setRemindtype("0");// 提醒类型
	             info.setIsNextFlow("0");// 是否提交到下一节点 1提交到下一节点 0停留在创建节点
	             MainTableInfo mti = new MainTableInfo();
	             Property[] p = new Property[5];
	             
	             p[0] = new Property();//申请人
	             p[0].setName("SQR");
	             p[0].setValue(userid);
	             
	             p[1] = new Property();//申请日期
	             p[1].setName("SQRQ");
	             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	             p[1].setValue(sdf.format(new Date()));
	             
	             p[2] = new Property();//检验批号码
	             p[2].setName("PRUEFLOS");
	             p[2].setValue(dfmt.getPRUEFLOS());
	             
	             p[3] = new Property();//通知单编码
	             p[3].setName("QMNUM");
	             p[3].setValue(dfmt.getQMNUM());
	             
	             p[4] = new Property();//通知类型
	             p[4].setName("QMART");
	             p[4].setValue(dfmt.getQMART());
	             
	             
	             mti.setProperty(p);
	             info.setMainTableInfo(mti);
	             StringBuffer description = new StringBuffer();
	             if(lx.equals("f2")){
	            	 mingcheng ="来料";
	             }else if(lx.equals("f3")){
	            	 mingcheng ="产品";
	             }
	             description.append(mingcheng+"不合格处理流程-").append(JiuyiUtil.getLastNameByUserId(userid)).append("-").append(sdf.format(new Date()));
	             info.setDescription(description.toString());// 流程标题
	             RequestService reqSvc = new RequestService();
	             String reqid = reqSvc.createRequest(info);
	             new BaseBean().writeLog("发起的不合格处理流程requestid="+reqid);
	             if (Integer.parseInt(reqid) > 0) { // 主表创建成功
	            	 InsertMXSql insertMXSql = new InsertMXSql();
	            	 String m =insertMXSql.inserSql(reqid, JiuyiUtil.getTablenameBywfid(workflowid), dfp);
	            	 if("S".equals(m)){
	            		 msg.setMsg("发起流程成功！");
	            		 msg.setRequestid(reqid);
	            	 }else{
	            		 msg.setMsg("主表创建成功,明细发起失败");
	            	 }
	             }          
			 }else {
				 new BaseBean().writeLog("根据loginid找不到相对应的人员");
				 msg.setMsg("根据loginid找不到相对应的人员");
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
			 new BaseBean().writeLog("发起不合格处理流程主表失败");
			 msg.setMsg("发起不合格处理流程主表失败");
		 }
		 return msg;
	 }
}
