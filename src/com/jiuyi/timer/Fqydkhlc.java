package com.jiuyi.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.PropUtil;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.interfaces.schedule.BaseCronJob;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.RequestService;



/**
 * 
 * @author lijunying
 * 发起月度考核流程
 */
public class Fqydkhlc extends BaseCronJob {
	private static BaseBean log=new BaseBean();
	public void execute() {
		log.writeLog("开始发起月度采购申请报批流程");
		String sql = "";
		String BSART = "";//采购凭证类型
		String WERKS1 = "";//工厂
		RecordSet rs = new RecordSet();
		try {
			sql = "select a.BSART,a.WERKS1 from formtable_main_174 a left join formtable_main_174_dt1 b on a.id=b.mainid where b.clzt = '0' group by a.BSART,a.WERKS1";
			rs.execute(sql);
			while(rs.next()){
				BSART = Util.null2String(rs.getString("BSART"));
				WERKS1 = Util.null2String(rs.getString("WERKS1"));
				Fqydkhlc(BSART,WERKS1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.writeLog("发起月度采购申请报批流程报错："+e.getMessage());
		}

	}
	private void Fqydkhlc(String BSART, String WERKS1) throws Exception {
		// TODO Auto-generated method stub
		String tbName = "formtable_main_174 a left join formtable_main_174_dt1 b on a.id=b.mainid";
		String sqlwhere = "where b.clzt = '0' and a.BSART = '"+BSART+"' and a.WERKS1='"+WERKS1+"'";
		int perpage = 950;//每条流程插入950行
		int hs = 0;//现在插进去了几行的记录
		int lcs = 1;//流程数
		int mainid = 0;//明细ID
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		String sql = "select b.*,a.* from "+tbName+" "+sqlwhere+"  order by b.matnr";//数据按照物料分类
		rs.execute(sql);
		log.writeLog("月度采购申请报批流程主表SQL"+sql);
		int i = 0;
		while(rs.next()){
			i++;
			if(hs==0){//发现是第0行，就发起一条新的流程
				RequestInfo info = new RequestInfo();
	            info.setCreatorid("1");// 流程发起人
	            info.setWorkflowid(PropUtil.Monthlydemandplan);// 发起国内月度需求计划报批流程
	            info.setRequestlevel("0");// 紧急程度
	            info.setRemindtype("0");// 提醒类型
	            info.setIsNextFlow("1");// 是否提交到下一节点 1提交到下一节点 0停留在创建节点
	            MainTableInfo mti = new MainTableInfo();
	            Property[] p = new Property[6];
	            
	            p[0] = new Property();//
	            p[0].setName("sqrfb");
	            p[0].setValue(Util.null2String(rs.getString("sqrfb")));
	             
	            p[1] = new Property();//
	            p[1].setName("BSART1");
	            p[1].setValue(Util.null2String(rs.getString("BSART1")));
	             
	            p[2] = new Property();//
	            p[2].setName("WERKS1");
	            p[2].setValue(Util.null2String(rs.getString("WERKS1")));
	             
	            p[3] = new Property();//
	            p[3].setName("gcmc");
	            p[3].setValue(Util.null2String(rs.getString("gcmc")));
	            
	            p[4] = new Property();//
	            p[4].setName("BSART");
	            p[4].setValue(Util.null2String(rs.getString("BSART")));
	        	
	            p[5] = new Property();//
	            p[5].setName("shenqrq");
	            p[5].setValue(Util.null2String(rs.getString("shenqrq")));
	            
	            mti.setProperty(p);
	            info.setMainTableInfo(mti);
	            StringBuffer description = new StringBuffer();
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            description.append("国内月度需求计划报批流程("+lcs+")-").append(sdf.format(new Date()));lcs++;
	            info.setDescription(description.toString());// 流程标题
	            RequestService reqSvc = new RequestService();
	            String reqid = reqSvc.createRequest(info);
	            new BaseBean().writeLog("国内月度需求计划报批流程requestid="+reqid);
	            sql="select id from formtable_main_174 where requestid="+reqid;
	            rs1.execute(sql);
	            if(rs1.next()){
		            mainid = rs1.getInt("id");
	            }
			}
			if(mainid>0){
				String ydmxId = Util.null2String(rs.getString("id"));
				sql="insert into formtable_main_174_Dt1(mainid,KNTTP,"//	科目分配类别
					+"EPSTP,"//	项目类别
					+"MATNR,"//	物料编码
					+"MAKTX,"//	物料描述
					+"MENGE,"//	数量
					+"MEINS,"//	单位
					+"PREIS,"//	价格
					+"WAERS,"//	货币码
					+"MATKL,"//	物料组
					+"WERKS,"//	工厂
					+"NAME1,"//	工厂描述
					+"LGORT,"//	库存地点
					+"NAME2,"//	库存地点描述
					+"KOSTL,"//	成本中心
					+"MCTXT,"//	成本中心描述
					+"EKGRP,"//	采购组
					+"EKNAM,"//	采购组描述
					+"SAKTO,"//	总账科目
					+"TXT20,"//	总账科目描述
					+"ANLN1,"//	资产号
					+"TXT50,"//	资产号描述
					+"AUFNR,"//	订单
					+"KTEXT,"//	项目描述描述
					+"BEDNR,"//	联系方式
					+"AFNAM,"//	申请者
					+"LFDAT,"//	交货日期
					+"TEXT_LINE,"//	备注
					+"gysHzqr,"//	供应商或债权人的帐号
					+"BNFPO,"//	行项目号
					+"cjrq,"//	创建日期"
					+"ly"//	来源"
		+ ") values('"+mainid+"','"+Util.null2String(rs.getString("KNTTP"))+"','"//	科目分配类别
					+Util.null2String(rs.getString("EPSTP"))+"','"//	项目类别
					+Util.null2String(rs.getString("MATNR"))+"','"//	物料编码
					+Util.null2String(rs.getString("MAKTX"))+"','"//	物料描述
					+Double.parseDouble(Util.null2o(rs.getString("MENGE")))+"','"//	数量
					+Util.null2String(rs.getString("MEINS"))+"','"//	单位
					+Double.parseDouble(Util.null2o(rs.getString("PREIS")))+"','"//	价格
					+Util.null2String(rs.getString("WAERS"))+"','"//	货币码
					+Util.null2String(rs.getString("MATKL"))+"','"//	物料组
					+Util.null2String(rs.getString("WERKS"))+"','"//	工厂
					+Util.null2String(rs.getString("NAME1"))+"','"//	工厂描述
					+Util.null2String(rs.getString("LGORT"))+"','"//	库存地点
					+Util.null2String(rs.getString("NAME2"))+"','"//	库存地点描述
					+Util.null2String(rs.getString("KOSTL"))+"','"//	成本中心
					+Util.null2String(rs.getString("MCTXT"))+"','"//	成本中心描述
					+Util.null2String(rs.getString("EKGRP"))+"','"//	采购组
					+Util.null2String(rs.getString("EKNAM"))+"','"//	采购组描述
					+Util.null2String(rs.getString("SAKTO"))+"','"//	总账科目
					+Util.null2String(rs.getString("TXT20"))+"','"//	总账科目描述
					+Util.null2String(rs.getString("ANLN1"))+"','"//	资产号
					+Util.null2String(rs.getString("TXT50"))+"','"//	资产号描述
					+Util.null2String(rs.getString("AUFNR"))+"','"//	订单
					+Util.null2String(rs.getString("KTEXT"))+"','"//	项目描述描述
					+Util.null2String(rs.getString("BEDNR").replaceAll("'","''"))+"','"//	联系方式
					+Util.null2String(rs.getString("AFNAM"))+"','"//	申请者
					+Util.null2String(rs.getString("LFDAT"))+"','"//	交货日期
					+Util.null2String(rs.getString("TEXT_LINE").replaceAll("'","''"))+"','"//	备注
					+Util.null2String(rs.getString("gysHzqr"))+"','"//	供应商或债权人的帐号
					+i+"','"//	行项目号
					+Util.null2String(rs.getString("cjrq"))+"','" //	创建日期
					+Util.null2String(rs.getString("lcbh")+"-"+rs.getString("BNFPO"))+ //	创建日期
					"')";
				new BaseBean().writeLog("国内月度需求计划报批流程,插入明细SQL="+sql);
				rs1.execute(sql);
				sql="update formtable_main_174_Dt1 set	clzt = '1' where id='"+ydmxId+"'";
				rs1.execute(sql);
			}
			hs++;
			if(hs==950){
				hs=0;
				i=0;
			}
		}	
	}
	public static String formatSapNum(String num){
		if(num.endsWith("-")){
			return "-"+num.substring(0,num.length()-1);
		}else{
			return num;
		}
	}
	
	public static String trimStr(String str){
		if(str!=null){
			return str.replaceAll("&nbsp;", " ").replaceAll("&#160;", " ").replaceAll(" ", "");
		}else{
			return "";
		}
	}
	

	
}
