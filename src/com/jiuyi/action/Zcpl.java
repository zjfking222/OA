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
 * 资产批量
 * @author 李君莹
 *
 */
public class Zcpl extends BaseAction{
	 public String execute(RequestInfo requestInfo) {
		 	RecordSet rs=new RecordSet();
			JCO.Client myConnection =null;
			int error=0;
			String message="";
			String requestid="";
			try{
			String tablename=JiuyiUtil.getTablename(requestInfo);
			requestid=requestInfo.getRequestid();
			String functionName="ZFI_OE026_ASSET_INBOUND";//函数的名字
			
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
			JCO.Table  TAB_ZSFI103=inputtable.getTable("TAB_ZSFI103");
			//获取输入参数
			//JCO.Structure IS_HEADER = parameterList.getStructure("IS_HEADER");
			//从流程主数据表中查询出结构数据
			String sql="select * from "+tablename+" where requestId="+requestid;
			System.out.println(sql);
			rs.execute(sql);
			String id="";
			if(rs.next()){
				id = rs.getString("id");
			}
			sql="select * from "+tablename+"_dt1 where mainid="+id+" and (type='E' or type='' or type is null) order by id ";
			rs.executeSql(sql);
			while(rs.next()){
				TAB_ZSFI103.appendRow();
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("OPTION1")),"OPTION");//标识
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("ID")),"ID");//表id
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("ANLN1")),"ANLN1");//资产号
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("ANLKL")),"ANLKL");//资产分类
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("BUKRS")),"BUKRS");//公司代码
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("TXT50")),"TXT50");//资产描述
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("ANLHTXT")),"ANLHTXT");//资产主号说明
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("MENGE")),"MENGE");//数量
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("MEINS")),"MEINS");//基本计量单位
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("KOSTL")),"KOSTL");//成本中心
				//TAB_ZSFI103.setValue(Util.null2String(rs.getString("MCTXT")),"MCTXT");//成本中心描述

				TAB_ZSFI103.setValue(Util.null2String(rs.getString("AUFNR")),"AUFNR");//内部订单
				//TAB_ZSFI103.setValue(Util.null2String(rs.getString("WERKS")),"WERKS");//工厂
				//TAB_ZSFI103.setValue(Util.null2String(rs.getString("NAME1")),"");//工厂描述
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("KFZKZ")),"INVNR");//车辆的执照牌号
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("AFASL")),"AFASL");//折旧码
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("NDJAR_01")),"NDJAR");//计划年使用期
				TAB_ZSFI103.setValue(Util.null2String(rs.getString("NDPER")),"NDPER");//计划使用期间
				TAB_ZSFI103.setValue("X","XHIST");//资产被历史性管理
				TAB_ZSFI103.setValue("X","INKEN");//库存标识
				TAB_ZSFI103.setValue("X","EQUI_SYNC_AA");//同步资产和设备价格的主数据
			}
			
			myConnection.execute(bapi);
			JCO.ParameterList  outs = bapi.getExportParameterList();//输出参数和结构处理
			JCO.ParameterList  outtab = bapi.getTableParameterList();//输出参数和结构处理
			//String rootpath=GCONST.getRootPath()+"shangqilog\\saplogMM_main04data.html";
			//bapi.writeHTML(rootpath);
			String E_TYPE="S";//(outs.getValue("FLAG")+"").replace("", "").replace("'", "");
			String E_MESSAGE="";//(outs.getValue("E_MESSAGE")+"").replace("", "").replace("'", "");
			//String LIFNR=(outs.getValue("LIFNR")+"").replace("", "").replace("'", "");
			TAB_ZSFI103 = outtab.getTable("TAB_ZSFI103");
			for(int i=0;i<TAB_ZSFI103.getNumRows();i++){
				TAB_ZSFI103.setRow(i);
				sql = " update "+tablename+"_dt1 set ANLN1_N='"+TAB_ZSFI103.getString("ANLN1_N")+"',type='"+TAB_ZSFI103.getString("FLAG")+"' where id="+TAB_ZSFI103.getString("ID");
				rs.executeSql(sql);
				if("E".equals(TAB_ZSFI103.getString("FLAG"))){
					E_TYPE = "E";
					E_MESSAGE+=TAB_ZSFI103.getString("ANLN1_N")+" "+TAB_ZSFI103.getString("MESSAGE");
				}
			}
			//message+=" TESTRUN=";
			System.out.println(message);
			if("E".equals(E_TYPE)){
				requestInfo.getRequestManager().setMessage("111100");//提醒信息id
				requestInfo.getRequestManager().setMessagecontent("action执行出错，流程不允许提交到下一节点."+E_MESSAGE);//提醒信息内容
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
