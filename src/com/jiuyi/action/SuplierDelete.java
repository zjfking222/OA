package com.jiuyi.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.eosdata.service.EosDataLocator;
import com.eosdata.service.EosDataSoap_PortType;
import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;
/**
 * 创建US客户主数据
 * @author yangc
 *
 */
public class SuplierDelete  extends BaseAction{
	 public String execute(RequestInfo requestInfo) {
		 	RecordSet rs=new RecordSet();
		 	RecordSet rs1=new RecordSet();
		 	RecordSet rs2=new RecordSet();
		 	String workflowid=requestInfo.getWorkflowid();
			int error=0;
			String message = "";
			String requestid = "";
			String tablename=JiuyiUtil.getTablename(requestInfo);
			requestid=requestInfo.getRequestid();
			
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			
			String sql1 ="select * from "+tablename+" where requestId="+requestid;
			new BaseBean().writeLog("sql1======"+sql1);
			rs2.executeSql(sql1);
			rs2.next();
			String BU_GROUP =rs2.getString("BU_GROUP");
			new BaseBean().writeLog("业务伙伴分组="+BU_GROUP);
			
			if(BU_GROUP.equals("0")){
				try{
					String sql="select a.*,b.* from "+tablename+" a left join "+tablename+"_dt1 b on a.id=b.mainid where a.requestId="+requestid;
					new BaseBean().writeLog("sql======"+sql);
					rs.execute(sql);
					if(rs.next()){
						xml+="<item>";
						xml+="<!--供应商代码,必填-->    ";
						int LIFNR = rs.getInt("LIFNR");  
						xml+="<GYSDM>"+LIFNR+"</GYSDM>   ";
						new BaseBean().writeLog("GYSDM=="+LIFNR);
						xml+="<!--供应商名称,必填-->    ";
						xml+="<GYSMC>"+Util.null2String(rs.getString("NAME1"))+"</GYSMC>    ";
						new BaseBean().writeLog("GYSMC="+Util.null2String(rs.getString("NAME1")));
						xml+="<!--联系人-->    ";
						xml+="<GYSLXR>"+Util.null2String(rs.getString("VERKF"))+"</GYSLXR>    ";
						new BaseBean().writeLog("GYSLXR="+Util.null2String(rs.getString("VERKF")));
						xml+="<!--供应产品类型-->    ";
						String KRAUS = JiuyiUtil.getSelectname(rs.getString("KRAUS"),"9888");//正式系统
//						String KRAUS = JiuyiUtil.getSelectname(rs.getString("KRAUS"),"9888");//测试系统
						xml+="<GYSLX>"+Util.null2String(KRAUS)+"</GYSLX>    ";
						new BaseBean().writeLog("GYSLX="+Util.null2String(KRAUS));
						xml+="<!--统一社会信用代码-->    ";
						xml+="<SH>"+Util.null2String(rs.getString("STENR"))+"</SH>    ";
						new BaseBean().writeLog("SH="+Util.null2String(rs.getString("STENR")));
						xml+="<!--法定代表人-->    ";
						xml+="<FRDB>"+Util.null2String(rs.getString("VERKF_LXR"))+"</FRDB>    ";
						new BaseBean().writeLog("FRDB="+Util.null2String(rs.getString("VERKF_LXR")));
						xml+="<!--付款方式-->    ";
						xml+="<FKFS>"+Util.null2String(rs.getString("ZWELS"))+"</FKFS>    ";
						new BaseBean().writeLog("FKFS="+Util.null2String(rs.getString("ZWELS")));
						xml+="<!--币别码-->    ";
						xml+="<BB>"+Util.null2String(rs.getString("WAERS"))+"</BB>    ";
						new BaseBean().writeLog("BB="+Util.null2String(rs.getString("WAERS")));
						xml+="<!--SWIFT-->    ";
						xml+="<SWI>"+Util.null2String(rs.getString("STENR"))+"</SWI>    ";
						new BaseBean().writeLog("SWI="+Util.null2String(rs.getString("STENR")));
						xml+="<!--帐号-->    ";
						xml+="<ZH>"+Util.null2String(rs.getString("BANKN"))+Util.null2String(rs.getString("BKREF"))+"</ZH>    ";
						new BaseBean().writeLog("ZH="+Util.null2String(rs.getString("BANKN"))+Util.null2String(rs.getString("BKREF")));
						xml+="<!--开户行名称-->    ";
						xml+="<KHHMC>"+Util.null2String(rs.getString("BANKA"))+"</KHHMC>    ";
						new BaseBean().writeLog("KHHMC="+Util.null2String(rs.getString("BANKA")));
						xml+="<!--电话-->    ";
						xml+="<DH>"+Util.null2String(rs.getString("TELF1"))+"</DH>    ";
						new BaseBean().writeLog("DH="+Util.null2String(rs.getString("TELF1")));
						xml+="<!--手机-->    ";
						xml+="<SJ>"+Util.null2String(rs.getString("TELF2"))+"</SJ>    ";
						new BaseBean().writeLog("SJ="+Util.null2String(rs.getString("TELF2")));
						xml+="<!--传真-->    ";
						xml+="<CZ>"+Util.null2String(rs.getString("TELFX"))+"</CZ>    ";
						new BaseBean().writeLog("CZ="+Util.null2String(rs.getString("TELFX")));
						xml+="<!--注册资金-->    ";
						xml+="<ZCZJ>"+Util.null2String(rs.getString("RG"))+"</ZCZJ>    ";
						new BaseBean().writeLog("ZCZJ="+Util.null2String(rs.getString("RG")));
						xml+="<!--主要产品-->   ";
						xml+="<ZYCP>"+Util.null2String(rs.getString("CRC_NUM"))+"</ZYCP>    ";
						new BaseBean().writeLog("ZYCP="+Util.null2String(rs.getString("CRC_NUM")));
						xml+="<!--业务员号，OA端后续增加-->   "; 
						xml+="<YWYH>"+Util.null2String(rs.getString("EKGRP"))+"</YWYH>    ";
						new BaseBean().writeLog("YWYH="+Util.null2String(rs.getString("EKGRP")));
						xml+="<!--业务员-->    ";
						int shenqrid = rs.getInt("shenqr");
						String sql2= "select lastname from hrmresource  where id ="+shenqrid;
						rs1.executeSql(sql2);
						rs1.next();
						String lastname =rs1.getString("lastname");
						xml+="<YWYM>"+Util.null2String(lastname)+"</YWYM>    ";
						new BaseBean().writeLog("YWYM="+Util.null2String(lastname));
						xml+="<!--经营地址-->   ";
						xml+="<JYDZ>"+Util.null2String(rs.getString("STRAS"))+"</JYDZ>    ";
						new BaseBean().writeLog("JYDZ="+Util.null2String(rs.getString("STRAS")));
						xml+="<!--付款条件-->    ";
						xml+="<TKM>"+Util.null2String(rs.getString("ZTERM_GS"))+"</TKM>    ";
						new BaseBean().writeLog("TKM="+Util.null2String(rs.getString("ZTERM_GS")));
						

						xml+="<!--企业类型-->    ";
						String qylx = JiuyiUtil.getSelectname(rs.getString("qylx"),"13311");//正式系统
//						String qylx = JiuyiUtil.getSelectname(rs.getString("qylx"),"13296");//测试系统
						xml+="<QYLX>"+Util.null2String(qylx)+"</QYLX>    ";
						new BaseBean().writeLog("QYLX="+Util.null2String(qylx));
						xml+="<!--纳税人分类-->    ";
						String nsr = JiuyiUtil.getSelectname(rs.getString("nsrfl"),"13310");//正式系统
//						String nsr = JiuyiUtil.getSelectname(rs.getString("nsr"),"13295");//测试系统
						xml+="<NSRFL>"+Util.null2String(nsr)+"</NSRFL>    ";
						new BaseBean().writeLog("NSRFL="+Util.null2String(nsr));
						xml+="<!--是否有历史业务-->    ";
						String lsyw = JiuyiUtil.getSelectname(rs.getString("ywls"),"13313");//正式系统
//						String lsyw = JiuyiUtil.getSelectname(rs.getString("lsyw"),"13297");//测试系统
						xml+="<YWLS>"+Util.null2String(lsyw)+"</YWLS>    ";
						new BaseBean().writeLog("YWLS="+Util.null2String(lsyw));
						xml+="<!--维护用户-->    ";//申请人
						xml+="<WHYH>"+Util.null2String(lastname)+"</WHYH>    ";
						new BaseBean().writeLog("WHYH="+Util.null2String(lastname));
						xml+="<!--维护日期-->    ";//申请日期
						xml+="<WHRQ>"+TimeUtil.getCurrentDateString()+"</WHRQ>    ";
						new BaseBean().writeLog("WHRQ="+TimeUtil.getCurrentDateString());
						xml+="<!--资信审核结果-->    ";
						xml+="<ZXSHJG>"+Util.null2String(rs.getString("zxsh"))+"</ZXSHJG>    ";
						new BaseBean().writeLog("ZXSHJG="+Util.null2String(rs.getString("zxsh")));
						xml+="<!--所属地区-->    ";//地区
						xml+="<DQMC>"+Util.null2String(rs.getString("ORT02"))+"</DQMC>    ";
						new BaseBean().writeLog("DQMC="+Util.null2String(rs.getString("ORT02")));
						xml+="<!--供应商简称-->    ";//搜索词*
						xml+="<GYSJC>"+Util.null2String(rs.getString("SORTL"))+"</GYSJC>    ";
						new BaseBean().writeLog("GYSJC="+Util.null2String(rs.getString("SORTL")));
						xml+="<!--邮编-->    ";
						xml+="<YB>"+Util.null2String(rs.getString("PSTLZ"))+"</YB>    ";
						new BaseBean().writeLog("YB="+Util.null2String(rs.getString("PSTLZ")));
						xml+="<!--E-MAIL-->    ";//E-MAIL
						xml+="<E_mail>"+Util.null2String(rs.getString("SMTP_ADDR"))+"</E_mail>    ";
						new BaseBean().writeLog("E_mail="+Util.null2String(rs.getString("SMTP_ADDR")));
						xml+="<!--资质证书-->    ";
						xml+="<ZZZS>"+Util.null2String(rs.getString("zzzs"))+"</ZZZS>    ";
						new BaseBean().writeLog("ZZZS="+Util.null2String(rs.getString("zzzs")));
						xml+="<!--渠道证明文件-->    ";
						xml+="<QDZMWJ>"+Util.null2String(rs.getString("qdzm"))+"</QDZMWJ>    ";
						new BaseBean().writeLog("QDZMWJ="+Util.null2String(rs.getString("qdzm")));
						xml+="<!--企业网址-->    ";
						xml+="<QYWZ>"+Util.null2String(rs.getString("qywz"))+"</QYWZ>    ";
						new BaseBean().writeLog("QYWZ="+Util.null2String(rs.getString("qywz")));
						xml+="<!--公司成立日期-->    ";
						String gsclsj = rs.getString("gsclsj");//正式机
						if(!gsclsj.equals("")){
							//String clsj = rs.getString("clsj");//测试机
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							Date date = df.parse(gsclsj);
							xml+="<GSCLRQ>"+date.toLocaleString()+"</GSCLRQ>    ";
							new BaseBean().writeLog("GSCLRQ="+date.toLocaleString());
						}
						xml+="<!--合作项目简介-->    ";
						xml+="<HXJJ>"+Util.null2String(rs.getString("hzxmjj"))+"</HXJJ>    ";
						new BaseBean().writeLog("HXJJ="+Util.null2String(rs.getString("hzxmjj")));
						
						xml+="<!--暂停供应商-->    ";
						xml+="<ZTGYSH>Y</ZTGYSH>    ";
						xml+="</item>";
						
						
						
					}
					new BaseBean().writeLog("xml======"+xml);
					EosDataLocator locator = new EosDataLocator();
			        EosDataSoap_PortType service = locator.getEosDataSoap();
			        message = service.supplierSave(xml);
				 }catch(Exception e){
					message=e.getMessage();
					e.printStackTrace();
					new BaseBean().writeLog("cmztj"+e);
					error=1;
				}finally{
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
