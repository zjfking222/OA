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

import com.jiuyi.entity.CertificateMainTable;
import com.jiuyi.entity.CertificateParticulars;
import com.jiuyi.entity.MessageInfo;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.JudgeUserId;
import com.jiuyi.util.PropUtil;
import com.jiuyi.util.SAPUtil;

/**
 * 物资出门证流程
 * @author SCP
 *
 */
public class Certificate {
	 public MessageInfo execute(CertificateMainTable cm,CertificateParticulars[] cp) {
		 MessageInfo msg = new MessageInfo();
		 try{ 
			 String loginid = cm.getERNAM();
			 String userid = JudgeUserId.judegid(loginid);
			 if (!userid.equals("")) {
				 RecordSet rs = new RecordSet();
				 String tablename=JiuyiUtil.getTablenameBywfid(PropUtil.Certificate);
				 String ssgs = "";
				 if(Util.null2String(cm.getVSTEL()).equals("3010")){
	            	 ssgs = "0";//华友钴业（3010）
	             }else if(Util.null2String(cm.getVSTEL()).equals("3030")){
	            	 ssgs = "1";//力科钴镍（3030）	
	             }else if(Util.null2String(cm.getVSTEL()).equals("3020")){
	            	 ssgs = "2";//衢州有色（3020）
	             }else if(Util.null2String(cm.getVSTEL()).equals("4010")){
	            	 ssgs = "3";//新能源科技（4010）
	             }else if(Util.null2String(cm.getVSTEL()).equals("4020")){
	            	 ssgs = "4";//华海科技（4020）
	             }else if(Util.null2String(cm.getVSTEL()).equals("2010")){
	            	 ssgs = "5";//
	             }
				 //仓库操作员  财务销售会计  出门证最终审核人
	             String sql= "select SDMX2,SDMX3,SDMX4 from formtable_main_237_dt1 where SDMX1 = "+ssgs;
	             new BaseBean().writeLog("sql-----"+sql);
	             rs.executeSql(sql);
	             rs.next();
	             String SDMX2=rs.getString(1);//仓库操作员
	             String SDMX3=rs.getString(2);//财务销售会计
	             String SDMX4=rs.getString(3);//出门证最终审核人
	             new BaseBean().writeLog("SDMX2-----"+SDMX2);
	             new BaseBean().writeLog("SDMX3-----"+SDMX3);
	             new BaseBean().writeLog("SDMX4-----"+SDMX4);
	             String mainid = "0";
	             sql = "select id from "+tablename+" where VBELN='"+Util.null2String(cm.getVBELN())+"'";
	             rs.execute(sql);
	             if(rs.next()){
	            	 mainid=rs.getString("id");
	             }else{
	            	 sql = "insert into "+tablename+" (BURKS,ZGSDMMS,KUNNR,NAME1,ZDJLX,ZZCHY,ZZJS,ZZMYSX,ZZCCRQ,ZZCPH"
	 				 		+ ",ERNAM,VBELN,VSTEL,ZFYDMS,OAzd4,OAzd5,OAzd6,OAzd7,OAzd1)values('"
	 						 +Util.null2String(cm.getBURKS())+"','"+Util.null2String(cm.getZGSDMMS())+"','"
	 						 +Util.null2String(cm.getKUNNR())+"','"+Util.null2String(cm.getNAME1())+"','"
	 						 +"0"+"','"+Util.null2String(cm.getZZCHY())+"','"
	 						 +Util.null2String(cm.getZZJS())+"','"+Util.null2String(cm.getZZMYSX())+"','"
	 						 +Util.null2String(cm.getZZCCRQ())+"','"+Util.null2String(cm.getZZCPH())+"','"
	 						 +Util.null2String(cm.getERNAM())+"','"+Util.null2String(cm.getVBELN())+"','"
	 						 +Util.null2String(cm.getVSTEL())+"','"+Util.null2String(cm.getZFYDMS())+"','"
	 						 +ssgs+"','"+Util.null2String(SDMX2)+"','"+Util.null2String(SDMX3)+"','"+Util.null2String(SDMX4)+"','"+userid+"')";
	 				 new BaseBean().writeLog("sql-----"+sql);
	 	             rs.executeSql(sql);
	 	             rs.executeSql("select max(id) as maxid from "+tablename);
	 	             rs.next();
	 	             mainid=rs.getString("maxid");
	             }
	             
				 
	             if (Integer.parseInt(mainid) > 0) { // 主表创建成功
	            	 InsertCertificateMXSql insertMXSql = new InsertCertificateMXSql();
	            	 String m =insertMXSql.inserSql(mainid, JiuyiUtil.getTablenameBywfid(PropUtil.Certificate), cp);
	            	 if("S".equals(m)){
	            		 msg.setMsg("发起流程成功！");
	            		 msg.setRequestid(mainid);
	            		 RequestInfo info = new RequestInfo();
	            		 info.setCreatorid("1");// 流程发起人
	    	             info.setWorkflowid("676");// 发起提醒流程
	    	             info.setRequestlevel("0");// 紧急程度
	    	             info.setRemindtype("0");// 提醒类型
	    	             info.setIsNextFlow("1");// 是否提交到下一节点 1提交到下一节点 0停留在创建节点
	    	             MainTableInfo mti = new MainTableInfo();
	    	             Property[] p = new Property[2];
	    	             
	    	             p[0] = new Property();//申请人
	    	             p[0].setName("txr");
	    	             p[0].setValue(userid);
	    	             
	    	             p[1] = new Property();//申请日期
	    	             p[1].setName("txnr");
	    	             p[1].setValue("你有发货单"+Util.null2String(cm.getVBELN())+"可以开出门证操作了。");

	    	             mti.setProperty(p);
	    	             info.setMainTableInfo(mti);
	    	             info.setDescription("你有发货单"+Util.null2String(cm.getVBELN())+"可以开出门证操作了。");// 流程标题
	    	             RequestService reqSvc = new RequestService();
	    	             reqSvc.createRequest(info);
	            	 }else{
	            		 msg.setMsg("主表创建成功,明细发起失败");
	            		 sql="delete "+tablename+" where id="+mainid;
	            		 rs.executeSql(sql);
	            	 }
	             }          
			 }else {
				 new BaseBean().writeLog("根据loginid找不到相对应的人员");
				 msg.setMsg("根据loginid找不到相对应的人员");
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
			 new BaseBean().writeLog("发起物资出门证流程主表失败");
			 msg.setMsg("发起物资出门证流程主表失败");
		 }
		 return msg;
	 }
}
