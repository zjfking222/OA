package com.jiuyi.action;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.JCO;

/**
 * 质量通知单回写
 * @author 李君莹
 *
 */
public class Gxclzt extends BaseAction{
	 public String execute(RequestInfo requestInfo) {
		 	RecordSet rs=new RecordSet();
		 	RecordSet rs1=new RecordSet();
			JCO.Client myConnection =null;
			int error=0;
			String message = "";
			String requestid = "";
			String clzt = "";
			String ydmxId = "";
			int MENGE = 0;
			try{
				String tablename=JiuyiUtil.getTablename(requestInfo);
				requestid=requestInfo.getRequestid();
				String sql = "select b.ydmxId,b.MENGE from "+tablename+" a left join "+tablename+"_dt1 b on a.id=b.mainid where a.requestid='"+requestid+"'";
				rs.execute(sql);
				while(rs.next()){
					ydmxId = Util.null2String(rs.getString("ydmxId"));
					MENGE = rs.getInt("MENGE");
					if(MENGE>0){
						clzt = "3";
					}else{
						clzt = "2";
					}
					sql="update formtable_main_174_Dt1 	clzt = '"+clzt+"' where id='"+ydmxId+"'";
					rs1.execute(sql);
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
