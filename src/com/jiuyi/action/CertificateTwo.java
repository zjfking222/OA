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
public class CertificateTwo {
	 public MessageInfo execute(CertificateMainTable cm,CertificateParticulars[] cp) {
		 MessageInfo msg = new MessageInfo();
		 try{ 
			 String loginid = cm.getERNAM();
			 String userid = JudgeUserId.judegid(loginid);
			 new BaseBean().writeLog("物资出门证流程loginid"+loginid);
			 new BaseBean().writeLog("物资出门证流程userid"+userid);
			 if (!userid.equals("")) {
				 RequestInfo info = new RequestInfo();
				 RecordSet rs = new RecordSet();
				 String requestid = info.getRequestid();//请求号
				 String tablename = SAPUtil.getTablename(info);//表名
				 String ssgs = "";//所属公司
	             info.setCreatorid(userid);// 流程发起人
	             info.setWorkflowid(PropUtil.Certificate);// 发起采购流程
	             
	             //String sql = "insert into tablename(BURKS,ZGSDMMS,,,,,,,,,,,,,,,,,,,,,,,,,,)";
	             
	             
	             
	             MainTableInfo mti = new MainTableInfo();
	             Property[] p = new Property[20];
	             
	             p[0] = new Property();//
	             p[0].setName("BURKS");
	             p[0].setValue(Util.null2String(cm.getBURKS()));
	             
	             p[1] = new Property();//
	             p[1].setName("ZGSDMMS");
	             p[1].setValue(Util.null2String(cm.getZGSDMMS()));
	             
	             p[2] = new Property();//
	             p[2].setName("");
	             p[2].setValue(Util.null2String(cm.getKUNNR()));
	             
	             p[3] = new Property();//
	             p[3].setName("");
	             p[3].setValue(Util.null2String(cm.getNAME1()));
	             
	             p[4] = new Property();//
	             p[4].setName("");
	             p[4].setValue(Util.null2String("0"));
	             
	             p[5] = new Property();//
	             p[5].setName("	");
	             p[5].setValue(Util.null2String(cm.getZZCHY()));
	             
	             p[6] = new Property();//
	             p[6].setName("");
	             p[6].setValue(Util.null2String(cm.getZZJS()));
	             
	             p[7] = new Property();//
	             p[7].setName("");
	             p[7].setValue(Util.null2String(cm.getZZMYSX()));
	             
	             p[8] = new Property();//
	             p[8].setName("");
	             p[8].setValue(Util.null2String(cm.getZZCCRQ()));
	             
	             p[9] = new Property();//
	             p[9].setName("");
	             p[9].setValue(Util.null2String(cm.getZZCPH()));
	             
	             p[10] = new Property();//
	             p[10].setName("");
	             p[10].setValue(Util.null2String(cm.getERNAM()));
	             
	             p[11] = new Property();//
	             p[11].setName("");
	             p[11].setValue(Util.null2String(cm.getVBELN()));
	             
	             p[12] = new Property();//
	             p[12].setName("");
	             p[12].setValue(Util.null2String(cm.getVSTEL()));
	             
	             p[13] = new Property();//
	             p[13].setName("");
	             p[13].setValue(Util.null2String(cm.getZFYDMS()));

	             p[14] = new Property();//
	             p[14].setName("");
	             if(Util.null2String(cm.getVSTEL()).equals("3010")){
	            	 p[14].setValue(Util.null2String("0"));
	            	 ssgs = "0";//华友钴业（3010）;
	             }else if(Util.null2String(cm.getVSTEL()).equals("3030")){
	            	 p[14].setValue(Util.null2String("1"));
	            	 ssgs = "1";//力科钴镍（3030）;
	             }else if(Util.null2String(cm.getVSTEL()).equals("3020")){
	            	 p[14].setValue(Util.null2String("2"));
	            	 ssgs = "2";//衢州有色（3020）;
	             }else if(Util.null2String(cm.getVSTEL()).equals("4010")){
	            	 p[14].setValue(Util.null2String("3"));
	            	 ssgs = "3";//新能源科技（4010）;
	             }else if(Util.null2String(cm.getVSTEL()).equals("4020")){
	            	 p[14].setValue(Util.null2String("4"));
	            	 ssgs = "4";//华海科技（4020）;
	             }else if(Util.null2String(cm.getVSTEL()).equals("2010")){
	            	 p[14].setValue(Util.null2String("5"));
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
	             
	             p[15] = new Property();//仓库操作员
	             p[15].setName("OAzd5");
	             p[15].setValue(Util.null2String(SDMX2));
	             
	             p[16] = new Property();//财务销售会计
	             p[16].setName("OAzd6");
	             p[16].setValue(Util.null2String(SDMX3));
	             
	             p[17] = new Property();//出门证最终审核人
	             p[17].setName("OAzd7");
	             p[17].setValue(Util.null2String(SDMX4));
	             
	             Date day=new Date();    
	             SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	             System.out.println(df.format(day)); 
	             
	             p[18] = new Property();//创建时间
	             p[18].setName("OAzd3");
	             p[18].setValue(Util.null2String(df.format(day)));
	             
	             p[19] = new Property();//创建人
	             p[19].setName("OAzd1");
	             p[19].setValue(userid);
	             
	             
	             
	             
	            
	            
	             
	             mti.setProperty(p);
	             info.setMainTableInfo(mti);
	             StringBuffer description = new StringBuffer();
	             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	             description.append("物资出门证流程-").append(JiuyiUtil.getLastNameByUserId(userid)).append("-").append(sdf.format(new Date()));
	             info.setDescription(description.toString());// 流程标题
	             RequestService reqSvc = new RequestService();
	             String reqid = reqSvc.createRequest(info);
	             new BaseBean().writeLog("物资出门证流程requestid="+reqid);
	             if (Integer.parseInt(reqid) > 0) { // 主表创建成功
	            	 InsertCertificateMXSql insertMXSql = new InsertCertificateMXSql();
	            	 String m =insertMXSql.inserSql(reqid, JiuyiUtil.getTablenameBywfid(PropUtil.Certificate), cp);
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
			 new BaseBean().writeLog("发起物资出门证流程主表失败");
			 msg.setMsg("发起物资出门证流程主表失败");
		 }
		 return msg;
	 }
}
