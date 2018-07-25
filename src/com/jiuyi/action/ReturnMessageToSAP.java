package com.jiuyi.action;

import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;

public class ReturnMessageToSAP extends BaseAction{
	public String execute(RequestInfo requestInfo) {
		JCO.Client myConnection = null;
		String requestid = requestInfo.getRequestid();
		String tablename = JiuyiUtil.getTablename(requestInfo);

		try {
			RecordSet rs = new RecordSet();
			String functionName = "ZQM_OE007_NOTIFICATION";//SAP函数名
			myConnection = SAPUtil.getSAPcon();//获取SAP连接
			myConnection.connect();	
			JCO.Repository myRepository = new JCO.Repository("myRepository", myConnection);
			IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);
			JCO.Function bapi = ft.getFunction();// 创建连接对象
			JCO.ParameterList input = bapi.getImportParameterList();// 输入参数
			String sql = "select PRUEFLOS from "+tablename +" where requestid = '"+requestid+"'";
			rs.execute(sql);
			if(rs.next()){
				input.setValue(rs.getString("PRUEFLOS"), "IM_PRUEFLOS");
			}
			StringBuffer str = new StringBuffer();//审批记录
			sql = "select * from hrmresource where id="+requestInfo.getRequestManager().getUserId();//审批人id
			rs.execute(sql);
			if(rs.next()){
				str.append(rs.getString("lastname"));//审批人
			}
			String remark = Util.null2String(requestInfo.getRequestManager().getRemark());
			String nr="";//获取富文本内容
			if(remark!=null&&remark.length()>0){
				String[] strarr = remark.split(">");
				for (String a : strarr) {
					String substr = a.substring(0,1);
					if(!"<".equals(substr)){
						int index = a.indexOf("<");
						if(index>=0){
							nr = nr+a.substring(0,index);
						}
					}
				}
			}
			str.append(","+nr);
			String sprq = Util.null2String(requestInfo.getRequestManager().getCurrentDate()).replaceAll("-", "");// 审批日期
			str.append(","+sprq);
			myConnection.execute(bapi);// 所有输入数据传输后，执行函数
			JCO.ParameterList outs = bapi.getExportParameterList();// 输出参数与机构集合
			String E_TYPE = outs.getString("E_TYPE");
			String E_MESSAGE = outs.getString("E_MESSAGE");
			if ("E".equals(E_TYPE)) {
				requestInfo.getRequestManager().setMessageid("111100");// 提醒信息id
				requestInfo.getRequestManager().setMessagecontent("执行出错，SAP返回错误信息." + E_MESSAGE);// 提醒信息内容
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
			requestInfo.getRequestManager().setMessageid("111100");//提醒信息id
	    	requestInfo.getRequestManager().setMessagecontent("返回SAP消息失败");//提醒信息内容
			return "0";
		}
		return Action.SUCCESS;
	}
}
