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
 * 请假批量
 * @author 李君莹
 *
 */
public class Jbpl extends BaseAction{
	 public String execute(RequestInfo requestInfo) {
		 	RecordSet rs=new RecordSet();
			JCO.Client myConnection =null;
			int error=0;
			String message="";
			String requestid="";
			try{
			String tablename=JiuyiUtil.getTablename(requestInfo);
			requestid=requestInfo.getRequestid();
			new BaseBean().writeLog("进入加班批量处理"+requestid);
			String functionName="ZHR_OE004_OVERTIME_CRD";//函数的名字
			
			myConnection =SAPUtil.getSAPcon();
	        myConnection.connect(); 
	        System.out.println("连接SAP成功");
	        JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
	        IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);
	        //從這個函數範本獲得該SAP函數的物件
	        JCO.Function bapi = ft.getFunction();
			//用于设置输入参数
			JCO.ParameterList  parameterList=bapi.getImportParameterList();
			JCO.ParameterList   inputtable= bapi.getTableParameterList();//输入表的处理
			JCO.Table  IT_ITEM=inputtable.getTable("IT_ITEM");
			//JCO.Table  IT_PURCHASE=inputtable.getTable("IT_PURCHASE");
			//JCO.Table  IT_COMPANY=inputtable.getTable("IT_COMPANY");
			//获取输入参数
			//JCO.Structure IS_HEADER = parameterList.getStructure("IS_HEADER");
			//从流程主数据表中查询出结构数据
			parameterList.setValue(requestid,"IM_REQUESTID");
			parameterList.setValue("2", "IM_DOCTYPE");
			String sql="select * from "+tablename+" where requestId="+requestid;
			System.out.println(sql);
			rs.execute(sql);
			String id="";
			String USERNAME = "";
			if(rs.next()){
				id = rs.getString("id");
				USERNAME = CommonUtil.getLastNameByUserId(rs.getString("shenqr"));
			}
			sql="select * from "+tablename+"_dt1 where mainid="+id+" and (type='' or type is null) order by id ";
			rs.executeSql(sql);
			String PERNR="";
			String BEGDA="";
			String HOUR_S="";
			String ENDDA="";
			String HOUR_E="";
			String AWART="";
			while(rs.next()){
				IT_ITEM.appendRow();
				String mxid = Util.null2String(rs.getString("id"));
				PERNR=Util.null2String(rs.getString("yuangbh"));//
				BEGDA=Util.null2String(rs.getString("kaisrq"));//
				HOUR_S=Util.null2String(rs.getString("kaissj"));//
				ENDDA=Util.null2String(rs.getString("jiesrq"));//内部订单
				HOUR_E=Util.null2String(rs.getString("jiessj"));//成本中心
				//System.out.println(SGTXT+"--"+BSCHL+"--"+SAKNR+"--"+TXT50+"--"+WRBTR+"--"+ZFBDT);
				
				IT_ITEM.setValue(mxid, "PROCESSID");
				IT_ITEM.setValue(PERNR, "PERNR");
				IT_ITEM.setValue(BEGDA, "BEGDA");
				IT_ITEM.setValue(HOUR_S.split(":")[0], "HOUR_S");
				IT_ITEM.setValue(HOUR_S.split(":")[1], "MINU_S");
				IT_ITEM.setValue(ENDDA, "ENDDA");
				IT_ITEM.setValue(HOUR_E.split(":")[0], "HOUR_E");
				IT_ITEM.setValue(HOUR_E.split(":")[1], "MINU_E");
			}
			
			myConnection.execute(bapi);
			new BaseBean().writeLog("退出加班批量处理"+requestid);
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
				new BaseBean().writeLog("action执行出错，流程不允许提交到下一节点."+E_MESSAGE);
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
