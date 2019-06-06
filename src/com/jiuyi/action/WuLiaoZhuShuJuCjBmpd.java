package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;

import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;


/**
 * 物料主数据创建编码重复判断
 * @author yangc
 *
 */
public class WuLiaoZhuShuJuCjBmpd  extends BaseAction implements Action {

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		String requestid = requestInfo.getRequestid();//请求号
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		
		JCO.Client myConnection =null;
		String functionName="ZGET_MATNR_CHK";//函数的名字
		
		try {
			myConnection =SAPUtil.getSAPcon();
	        myConnection.connect(); 
			System.out.println("连接SAP成功");
			
			JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
	        IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);//從這個函數範本獲得該SAP函數的物件
	        JCO.Function bapi = ft.getFunction();
			//用于设置输入参数
			JCO.ParameterList  input=bapi.getImportParameterList();//输入参数和结构处理
			
			String sql2 = "select id from "+tablename+" where requestid="+requestid;
			new BaseBean().writeLog("sql2=="+sql2);
			rs2.executeSql(sql2);
			rs2.next();
			int mainid = rs2.getInt("id");
			
			String sql = "select MATNR,MAKTX from "+tablename+"_dt1 where mainid="+mainid+" and (cjjg is null or cjjg='')";
			new BaseBean().writeLog("sql=="+sql);
			rs.executeSql(sql);
			while(rs.next()){
				//获取物料编码
				String MATNR = rs.getString("MATNR");
				new BaseBean().writeLog("MATNR=="+MATNR);
				String MAKTX = rs.getString("MAKTX");
				new BaseBean().writeLog("MAKTX=="+MAKTX);

				//物料编码、描述为输入参数
				input.setValue(MATNR, "IM_MATNR");
				input.setValue(MAKTX, "IM_MAKTX");
				
				
				myConnection.execute(bapi);
				JCO.ParameterList  outpar = bapi.getExportParameterList();//输出参数和结构处理
				
				Object E_FLAG = outpar.getValue("E_FLAG");//获取输出参数
				new BaseBean().writeLog("E_FLAG=="+E_FLAG);
				
				if(E_FLAG.equals("1")){
					requestInfo.getRequestManager().setMessage("111100");//
					requestInfo.getRequestManager().setMessagecontent("提交失败:"+MATNR+"此物料编码已存在，请更换");
					return "0";
				}else if(E_FLAG.equals("2")){
					requestInfo.getRequestManager().setMessage("111100");//
					requestInfo.getRequestManager().setMessagecontent("提交失败:"+MAKTX+"此物料已存在，请更换");
					return "0";
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
		
	}

}
