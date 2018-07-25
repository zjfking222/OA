package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

public class KeHuZhuShuJuPD implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		int error = 0;
		String NAME1="";
		JCO.Client myConnection =null;
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		String functionName="ZGET_KUNNR";//函数的名字
		
		
		try {
			myConnection =SAPUtil.getSAPcon();
	        myConnection.connect(); 
			System.out.println("连接SAP成功");
			
			JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
	        IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);//從這個函數範本獲得該SAP函數的物件
	        JCO.Function bapi = ft.getFunction();
			//用于设置输入参数
			JCO.ParameterList  input=bapi.getImportParameterList();//输入参数和结构处理
			
			String sql = "select NAME1 from "+tablename+" where requestid="+requestid;
			new BaseBean().writeLog("sql"+sql);
			rs.executeSql(sql);
			rs.next();
			NAME1 = rs.getString("NAME1");
			new BaseBean().writeLog("NAME1="+NAME1);
			
			input.setValue(NAME1, "NAME1");
			
			
			myConnection.execute(bapi);
			JCO.ParameterList  outpar = bapi.getExportParameterList();//输出参数和结构处理
	     	JCO.ParameterList  Table00 = bapi.getTableParameterList();//输出表的处理
	     	JCO.Table  T_KUNNR = Table00.getTable("T_KUNNR");//输出表

			new BaseBean().writeLog("T_KUNNR.getNumRows()="+T_KUNNR.getNumRows());
	     	if(T_KUNNR.getNumRows()!=0){
	     		requestInfo.getRequestManager().setMessage("111100");//
				requestInfo.getRequestManager().setMessagecontent("提交失败:此客户已存在，如需操作请进行修改操作");
				return "0";
	     	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

}
