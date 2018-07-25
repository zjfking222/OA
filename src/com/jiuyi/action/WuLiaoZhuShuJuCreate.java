package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;

/**
 * 物料主数据创建  扩充工厂
 * @author yangc
 *
 */
public class WuLiaoZhuShuJuCreate extends BaseAction implements Action{
	@Override
	public String execute(RequestInfo requestInfo) {
		
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		
		JCO.Client myConnection =null;
		String functionName=" ";//函数的名字
		
		
		String sql = "select * from "+tablename+" where requestid="+requestid;
		new BaseAction().writeLog("sql=="+sql);
		rs.executeSql(sql);
		rs.next();
		//获取mainid
		int mainid = rs.getInt("id");
		//销售视图创建标识
		String SALES_VIEW = rs.getString("SALES_VIEW");
		//采购视图创建标识
		String PURCHASE_VIEW = rs.getString("PURCHASE_VIEW");
		//MRP视图创建标识
		String MRP_VIEW = rs.getString("MRP_VIEW");
		//工作计划视图创建标识
		String WORK_SCHED_VIEW = rs.getString("WORK_SCHED_VIEW");
		//库存视图创建标识
		String STORAGE_VIEW = rs.getString("STORAGE_VIEW");
		//质量视图创建标识
		String QUALITY_VIEW = rs.getString("QUALITY_VIEW");
		//财务视图创建标识
		String ACCOUNT_VIEW = rs.getString("ACCOUNT_VIEW");
		
		
		
		String sql1 = "select * from "+tablename+" where mainid="+mainid;
		new BaseAction().writeLog("sql1=="+sql1);
		rs1.executeSql(sql1);
		while(rs1.next()){
			myConnection =SAPUtil.getSAPcon();
	        myConnection.connect(); 
			System.out.println("连接SAP成功");
			
			JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
	        IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);//從這個函數範本獲得該SAP函數的物件
	        JCO.Function bapi = ft.getFunction();
			//用于设置输入参数
			JCO.ParameterList  input=bapi.getImportParameterList();//输入参数和结构处理
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		return Action.SUCCESS;
	}
}	
