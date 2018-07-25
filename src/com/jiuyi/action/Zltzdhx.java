package com.jiuyi.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;
import com.jiuyi.util.CommonUtil;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * 质量通知单回写
 * @author 李君莹
 *
 */
public class Zltzdhx extends BaseAction{
	 public String execute(RequestInfo requestInfo) {
		 	
		 	RecordSet rs=new RecordSet();
			JCO.Client myConnection =null;
			int error=0;
			String message = "";
			String requestid = "";
			String fhxx = "";
			try{
			String tablename=JiuyiUtil.getTablename(requestInfo);
			requestid=requestInfo.getRequestid();
			String functionName="ZQM_OE020_QM01_INBOUND";//函数的名字
			new BaseBean().writeLog("质量通知单回写");
			myConnection =SAPUtil.getSAPcon();
	        myConnection.connect(); 
	        System.out.println("连接SAP成功");
	        new BaseBean().writeLog("连接SAP成功");
	        JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
	        IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);
	        //從這個函數範本獲得該SAP函數的物件
	        JCO.Function bapi = ft.getFunction();
			//用于设置输入参数
			JCO.ParameterList  parameterList=bapi.getImportParameterList();
			JCO.ParameterList   inputtable= bapi.getTableParameterList();//输入表的处理
			JCO.Table  IT_HEADTXT=inputtable.getTable("IT_HEADTXT");
			//JCO.Table  IT_PURCHASE=inputtable.getTable("IT_PURCHASE");
			//JCO.Table  IT_COMPANY=inputtable.getTable("IT_COMPANY");
			//获取输入参数
			//JCO.Structure IS_HEADER = parameterList.getStructure("IS_HEADER");
			//从流程主数据表中查询出结构数据
			String sql="select * from "+tablename+" where requestId="+requestid;
			System.out.println(sql);
			rs.execute(sql);
			String QMNUM="";
			if(rs.next()){
				QMNUM = rs.getString("QMNUM");
			}
			parameterList.setValue(QMNUM,"IM_QMNUM");
//			parameterList.setValue("2", "IM_DOCTYPE");
		    String str = "select (select nodename from workflow_nodebase t2 where id =t1.nodeid) nodename,"
		    		+ "t1.remark,t3.lastname"
		    		+ ",t1.receivedPersons,t1.operatedate,t1.operatetime,t4.departmentname from workflow_requestLog t1 "
		    		+ "left join hrmresource t3 on t3.id = t1.operator "
		    		+ "left join hrmdepartment t4 on t3.departmentid =t4.id where requestid= " + requestid + " order by operatedate,operatetime asc";
		    rs.executeSql(str);
		    while(rs.next()){
		    	fhxx=rs.getString("nodename")+"  "+rs.getString("departmentname")+"/"+rs.getString("lastname")+" "+JiuyiUtil.getRTFContent(rs.getString("remark"))+" "+rs.getString("operatedate")+" "+rs.getString("operatetime");

		    	IT_HEADTXT.appendRow();
		    	IT_HEADTXT.setValue(fhxx,"TDLINE");
		    	new BaseBean().writeLog("fhxx"+fhxx);
		    }
		    int nodeid = requestInfo.getRequestManager().getNodeid();
		    int userid = requestInfo.getRequestManager().getUserId();
		    str="select departmentname from hrmdepartment t4 join hrmresource t3 on t3.departmentid =t4.id where t3.id="+userid;
		    rs.execute(str);
		    rs.next();
		    fhxx = JiuyiUtil.getNodeName(nodeid)+" "+rs.getString("departmentname")+"/"+JiuyiUtil.getLastNameByUserId(""+userid)+"  "+JiuyiUtil.getRTFContent(requestInfo.getRequestManager().getRemark())+" "+requestInfo.getRequestManager().getCurrentDate();

	    	IT_HEADTXT.appendRow();
		    IT_HEADTXT.setValue(fhxx,"TDLINE");
		    //			int hs = fhxx.length()/130;
//			int ys = fhxx.length()%130;
//			if(ys>0){
//				hs++;
//			}
//		    for(int i=0;i<hs-1;i++){
//		    	IT_HEADTXT.appendRow();
//		    	if(i<hs){
//			    	IT_HEADTXT.setValue(fhxx.substring(130*i,130*i+130),"TDLINE");
//		    	}else{
//			    	IT_HEADTXT.setValue(fhxx.substring(130*i,fhxx.length()),"TDLINE");
//		    	}
//			}
		    myConnection.execute(bapi);
			JCO.ParameterList  outs = bapi.getExportParameterList();//输出参数和结构处理
			JCO.ParameterList  outtab = bapi.getTableParameterList();//输出参数和结构处理
			//String rootpath=GCONST.getRootPath()+"shangqilog\\saplogMM_main04data.html";
			//bapi.writeHTML(rootpath);
			String E_TYPE=(outs.getValue("E_TYPE")+"").replace("", "").replace("'", "");
			String E_MESSAGE=(outs.getValue("E_MESSAGE")+"").replace("", "").replace("'", "");
			//String LIFNR=(outs.getValue("LIFNR")+"").replace("", "").replace("'", "");
			
			//message+=" TESTRUN=";
			System.out.println(message);
			if("E".equals(E_TYPE)){
				requestInfo.getRequestManager().setMessage("111100");//提醒信息id
				requestInfo.getRequestManager().setMessagecontent("action执行出错，流程不允许提交到下一节点."+E_MESSAGE);//提醒信息内容

				JCO.Table IT_ERROR = outtab.getTable("IT_ERROR");
				for(int i=0;i<IT_ERROR.getNumRows();i++){
					IT_ERROR.setRow(i);
					sql = " update "+tablename+"_dt1 set type='"+IT_ERROR.getString("TYPE")+"',MESSAGE='"+IT_ERROR.getString("MESSAGE")+"' where id="+IT_ERROR.getString("PROCESSID");
					rs.executeSql(sql);
				}
				return "0";
			}else{//,BLDAT='"+BLDAT+"'
//				sql = " update "+tablename+" set BELNR='"+E_BELNR+"',EMESSAGE='"+E_MESSAGE+"' where requestid="+requestInfo.getRequestid();
//				rs.executeSql(sql);
			}
			}catch(Exception e){
				message=e.getMessage();
				e.printStackTrace();
				new BaseBean().writeLog("Qjpl"+e);
				error=1;
			}finally{
				if(null!=myConnection){
					SAPUtil.releaseClient(myConnection);
				}
			}
			
			if(error==1){
				requestInfo.getRequestManager().setMessage("111100");//提醒信息id
				requestInfo.getRequestManager().setMessagecontent("action执行出错-流程不允许提交到下一节点."+message);//提醒信息内容
				return "0";
			}else{
				return Action.SUCCESS;
			}
	 }
}
