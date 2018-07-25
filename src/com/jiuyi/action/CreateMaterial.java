package com.jiuyi.action;

import com.eosdata.service.EosDataLocator;
import com.eosdata.service.EosDataSoap_PortType;
import com.jiuyi.util.JiuyiUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;
/**
 * 创建US物料主数据
 * @author 李君莹
 *
 */
public class CreateMaterial  extends BaseAction{
	 public String execute(RequestInfo requestInfo) {
		   new BaseBean().writeLog("--------------创建US物料主数据-------------------");
		 	RecordSet rs=new RecordSet();
		 	RecordSet rs1=new RecordSet();
		 	String workflowid=requestInfo.getWorkflowid();
			int error=0;
			int i =0;
			String message = "";
			String requestid = "";
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			 try{
				String tablename=JiuyiUtil.getTablename(requestInfo);
				requestid=requestInfo.getRequestid();
				String sql="select b.* from "+tablename+" a left join "+tablename+"_dt1 b on a.id=b.mainid where a.requestId="+requestid;
				rs.execute(sql);
				while(rs.next()){
					xml+="<item>";
					xml+="<!--物品编码,必填-->  ";
					xml+="<WPH>"+Util.null2String(rs.getString("matnr"))+"</WPH> "; 
					new BaseBean().writeLog("<WPH>"+Util.null2String(rs.getString("matnr"))+"</WPH> ");
					xml+="<!--物品描述,必填-->  ";
					xml+="<DWPMC>"+Util.null2String(rs.getString("MAKTX"))+"</DWPMC>  ";
					new BaseBean().writeLog("<DWPMC>"+Util.null2String(rs.getString("MAKTX"))+"</DWPMC>  ");
					xml+="<!--二级分类-->  ";
					xml+="<EJFL>"+Util.null2String(rs.getString("MATKL"))+"</EJFL>  ";
					new BaseBean().writeLog("<EJFL>"+Util.null2String(rs.getString("MATKL"))+"</EJFL>  ");
					xml+="<!--存货单位-->  ";
					xml+="<CHDW>"+Util.null2String(rs.getString("MEINS"))+"</CHDW>  ";
					new BaseBean().writeLog("<CHDW>"+Util.null2String(rs.getDouble("UMREN")/rs.getDouble("UMREZ"))+"</CHDW>  ");
					xml+="<!--采购单位-->  ";
					xml+="<CGUM>"+Util.null2String(rs.getString("MEINH"))+"</CGUM>  ";
					new BaseBean().writeLog("<CGUM>"+Util.null2String(rs.getString("MEINH"))+"</CGUM>  ");
					xml+="<!--创建用户-->  ";
					xml+="<CJYH>admin</CJYH>  ";
					xml+="<!--创建日期-->  ";
					xml+="<CJRQ>"+TimeUtil.getCurrentDateString()+"</CJRQ>  ";
					new BaseBean().writeLog("<CJRQ>"+TimeUtil.getCurrentDateString()+"</CJRQ>  ");
					xml+="<!--维护用户-->  ";
					xml+="<WHYH>admin</WHYH>  ";
					xml+="<!--维护日期-->  ";
					xml+="<WHRQ>"+TimeUtil.getCurrentDateString()+"</WHRQ>  ";
					new BaseBean().writeLog("<WHRQ>"+TimeUtil.getCurrentDateString()+"</WHRQ>  ");
					xml+="<!--启用-->  ";
					xml+="<QY>N</QY>  ";
					xml+="<!--详细描述-->  ";
					xml+="<WPMC>"+Util.null2String(rs.getString("wlms2"))+"</WPMC>  ";
					new BaseBean().writeLog("<WPMC>"+Util.null2String(rs.getString("wlms2"))+"</WPMC>  ");
					xml+="<!--存货单位转换-->  ";//转换关系
					double UMREZ = rs.getDouble("UMREZ");//转换关系
					double UMREN = rs.getDouble("UMREN");//基本单位关系
					xml+="<CHDWZH>"+Util.null2String(String.format("%.4f", UMREZ/UMREN))+"</CHDWZH> "; 
					xml+="</item>";
					
					new BaseBean().writeLog("xml======++"+i+":"+xml);
					
					EosDataLocator locator = new EosDataLocator();
			        EosDataSoap_PortType service = locator.getEosDataSoap();
			        message = service.materielSave(xml);

					new BaseBean().writeLog("CreateMaterial ++"+i+":"+message);
					xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
					i++;
				}
				
			 }catch(Exception e){
				message=e.getMessage();
				e.printStackTrace();
				new BaseBean().writeLog("CreateMaterial"+e);
				error=1;
			}finally{
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
