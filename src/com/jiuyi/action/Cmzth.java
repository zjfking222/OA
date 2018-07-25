package com.jiuyi.action;

import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.JCO;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;
/**
 * 出门证退回
 * @author 李君莹
 *
 */
public class Cmzth  extends BaseAction{
	 public String execute(RequestInfo requestInfo) {
		 	RecordSet rs=new RecordSet();
		 	RecordSet rs1=new RecordSet();
			JCO.Client myConnection =null;
			int error=0;
			String message = "";
			String requestid = "";
			String wwcfhd = "";//未完成发货单
			 try{
				String tablename=JiuyiUtil.getTablename(requestInfo);
				requestid=requestInfo.getRequestid();
				String sql="select * from "+tablename+" where requestId="+requestid;
				rs.execute(sql);
				if(rs.next()){
					wwcfhd = rs.getString("wwcfhd");
				}
				
				sql="select b.* from "+tablename+" a left join "+tablename+"_dt1 b on a.id=b.mainid where a.requestId="+requestid;
				rs.execute(sql);
				while(rs.next()){
					double ZZBCFHL = JiuyiUtil.getDoubleFromQfw(rs.getString("ZZBCFHL"));//本次发货数量
					String POSNR = rs.getString("POSNR");
					sql="select ZZLJFHL,ZZWFHL from "+tablename+"_dt1 where  mainid='"+wwcfhd+"' and POSNR='"+POSNR+"'";
					rs1.execute(sql);
					if(rs1.next()){
						String ZZLJFHL = JiuyiUtil.getQfw(JiuyiUtil.getDoubleFromQfw(rs1.getString("ZZLJFHL"))-ZZBCFHL);
						String ZZWFHL = JiuyiUtil.getQfw(JiuyiUtil.getDoubleFromQfw(rs1.getString("ZZWFHL"))+ZZBCFHL);
						sql = "update "+tablename+"_dt1 set ZZLJFHL='"+ZZLJFHL+"',ZZWFHL='"+ZZWFHL+"' where  mainid='"+wwcfhd+"' and POSNR='"+POSNR+"'";
						new BaseBean().writeLog("cmztj"+sql);
						rs1.execute(sql);
					}
				}
			 }catch(Exception e){
				message=e.getMessage();
				e.printStackTrace();
				new BaseBean().writeLog("cmztj"+e);
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
