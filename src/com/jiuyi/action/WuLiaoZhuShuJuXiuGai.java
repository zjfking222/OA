package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;

/***
 * 物料主数据修改获取数据
 * @author yangc
 *
 */
public class WuLiaoZhuShuJuXiuGai extends BaseAction implements Action{

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs1 = new RecordSet();
		RecordSet rs2 = new RecordSet();
		int error = 0;
		int mianid=0;
		JCO.Client myConnection =null;
		String requestid = requestInfo.getRequestid();//请求号
		String workflowid = requestInfo.getWorkflowid();//流程id
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		String functionName="ZMM_OE008_MATERIAL_GETINFO";//函数的名字
		String message = "";
		
		String sql = "select id from "+tablename+" where requestid="+requestid;
		rs.executeSql(sql);
		rs.next();
		mianid = rs.getInt(1);
		
		try {
			myConnection =SAPUtil.getSAPcon();
	        myConnection.connect(); 	
			System.out.println("连接SAP成功");
		String sql1 = "select MATNR1,MAKTX,WERKS from "+tablename+"_dt1 where mainid="+mianid;
		new BaseBean().writeLog("sql1=="+sql1);
		rs1.executeSql(sql1);
		while(rs1.next()){
			JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
			IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);//從這個函數範本獲得該SAP函數的物件
			JCO.Function bapi = ft.getFunction();
			//用于设置输入参数
			JCO.ParameterList  input=bapi.getImportParameterList();//输入参数和结构处理
			JCO.ParameterList   inputtable= bapi.getTableParameterList();//输入表的处理
			
			
			String MATNR1 = rs1.getString(1);//物料编码
			String MAKTX = rs1.getString(2);//物料描述
			String WERKS = rs1.getString(3);//工厂
			
			new BaseBean().writeLog("物料编码"+MATNR1);
			new BaseBean().writeLog("物料描述"+MAKTX);
			new BaseBean().writeLog("工厂"+WERKS);
			
			input.setValue(MATNR1, "IM_MATNR");
			//input.setValue(MAKTX, "IM_MAKTX");
			input.setValue(WERKS, "IM_WERKS");
			
			myConnection.execute(bapi);
			JCO.ParameterList  outpar = bapi.getExportParameterList();//输出参数和结构处理
	     	JCO.ParameterList  Table00 = bapi.getTableParameterList();//输出表的处理
	     	JCO.Table  IM_ITEM = Table00.getTable("IM_ITEM");//输出表
	     	String E_TYPE=(outpar.getValue("E_TYPE")+"").replace("", "").replace("'", "");
			String E_MESSAGE=(outpar.getValue("E_MESSAGE")+"").replace("", "").replace("'", "");
			new BaseBean().writeLog("M_TYPE:"+E_TYPE+",M_MESSAGE:"+E_MESSAGE);
			if("E".equals(E_TYPE)){
				requestInfo.getRequestManager().setMessage("111100");//
				requestInfo.getRequestManager().setMessagecontent("BPC接口执行失败:"+E_MESSAGE);
				return "0";
			}else{
				for (int i = 0; i <IM_ITEM.getNumRows(); i++) {
					String MATNR = IM_ITEM.getString("MATNR");//MATNR  物料编号	MATNR==
					String MBRSH = IM_ITEM.getString("MBRSH");//MBRSH  行业领域---
					String MTART = IM_ITEM.getString("MTART");//MTART  物料类型	MTART
					String WERKS1 = IM_ITEM.getString("WERKS");//WERKS	工厂		WERKS==
					String LGORT = IM_ITEM.getString("LGORT");//LGORT	库存地点	LGORT==
					String MAKTX1 = IM_ITEM.getString("MAKTX");//MAKTX	物料描述	MAKTX==
					String MAKTX2 = IM_ITEM.getString("MAKTX2");//MAKTX2	物料描述2	wlms2==
					String MEINS = IM_ITEM.getString("MEINS");//MEINS	基本计量单位	MEINS==
					String MATKL = IM_ITEM.getString("MATKL");//MATKL	物料组	MATKL==
					String BISMT = IM_ITEM.getString("BISMT");//BISMT	旧物料号	BISMT==
					new BaseBean().writeLog("物料编号="+MATNR);
					new BaseBean().writeLog("行业领域="+MBRSH);
					new BaseBean().writeLog("物料类型="+MTART);
					new BaseBean().writeLog("工厂="+WERKS1);
					new BaseBean().writeLog("库存地点="+LGORT);
					new BaseBean().writeLog("物料描述="+MAKTX1);
					new BaseBean().writeLog("物料描述2="+MAKTX2);
					new BaseBean().writeLog("基本计量单位="+MEINS);
					new BaseBean().writeLog("物料组="+MATKL);
					new BaseBean().writeLog("旧物料号="+BISMT);

					String EXTWG = IM_ITEM.getString("EXTWG");//EXTWG	外部物料组	EXTWG==
					String WRKST = IM_ITEM.getString("WRKST");//WRKST	基本物料		WRKST
					String MEINH = IM_ITEM.getString("MEINH");//MEINH	用于显示的计量单位	MEINH	第二计量单位==
					String UMREZ = IM_ITEM.getString("UMREZ");//UMREZ	基本计量单位转换分子	UMREZ	转换关系==
					String UMREN = IM_ITEM.getString("UMREN");//UMREN	转换为基本计量单位的分母	UMREN 基本单位关系==
					String SPART = IM_ITEM.getString("SPART");//SPART	分部		SPART 产品组
					String MTPOS_MARA = IM_ITEM.getString("MTPOS_MARA");//MTPOS_MARA	普通项目类别组---
					String VKORG = IM_ITEM.getString("VKORG");//VKORG	销售组织	VKORG
					String VTWEG = IM_ITEM.getString("VTWEG");//VTWEG	分销渠道	VTWEG
					String DWERK = IM_ITEM.getString("DWERK");//DWERK	交货工厂	DWERK
					new BaseBean().writeLog("外部物料组="+EXTWG);
					new BaseBean().writeLog("基本物料="+WRKST);
					new BaseBean().writeLog("第二计量单位="+MEINH);
					new BaseBean().writeLog("转换关系="+UMREZ);
					new BaseBean().writeLog("基本单位关系="+UMREN);
					new BaseBean().writeLog("产品组="+SPART);
					new BaseBean().writeLog("普通项目类别组="+MTPOS_MARA);
					new BaseBean().writeLog("销售组织="+VKORG);
					new BaseBean().writeLog("分销渠道="+VTWEG);
					new BaseBean().writeLog("交货工厂="+DWERK);

					String TAXM1 = IM_ITEM.getString("TAXM1");//TAXM1	物料的税分类--- 
					String KTGRM = IM_ITEM.getString("KTGRM");//KTGRM	该物料的科目设置组	KTGRM科目设置组
					String MTPOS = IM_ITEM.getString("MTPOS");//MTPOS	来自物料主文件的项目类别组---
					String VERSG = IM_ITEM.getString("VERSG");//VERSG	统计组---
					String KONDM = IM_ITEM.getString("KONDM");//KONDM	物料价格组	KONDM物料定价组
					String TRAGR = IM_ITEM.getString("TRAGR");//TRAGR	运输组---
					String LADGR = IM_ITEM.getString("LADGR");//LADGR	装载组---
					String XCHPF = IM_ITEM.getString("XCHPF");//XCHPF	批次管理需求的标识	XCHPF 批次管理
					String DISMM = IM_ITEM.getString("DISMM");//DISMM	物料需求计划类型	DISMM MRP类型
					String DISPO = IM_ITEM.getString("DISPO");//DISPO	MRP控制者	DISPO 	MRP控制者
					new BaseBean().writeLog("物料的税分类="+TAXM1);
					new BaseBean().writeLog("科目设置组="+KTGRM);
					new BaseBean().writeLog("来自物料主文件的项目类别组="+MTPOS);
					new BaseBean().writeLog("统计组="+VERSG);
					new BaseBean().writeLog("物料定价组="+KONDM);
					new BaseBean().writeLog("运输组="+TRAGR);
					new BaseBean().writeLog("装载组="+LADGR);
					new BaseBean().writeLog("批次管理="+XCHPF);
					new BaseBean().writeLog("MRP类型="+DISMM);
					new BaseBean().writeLog("MRP控制者="+DISPO);

					String DISLS = IM_ITEM.getString("DISLS");//DISLS	批量 (物料计划)	DISLS 批量大小
					String BESKZ = IM_ITEM.getString("BESKZ");//BESKZ	采购类型		BESKZ 采购类型
					String LGPRO = IM_ITEM.getString("LGPRO");//LGPRO	发货存储地点	LGPRO 生产仓储地点
					String LGFSB = IM_ITEM.getString("LGFSB");//LGFSB	外部采购的缺省仓储位置	LGFSB 采购仓储地点
					String FHORI = IM_ITEM.getString("FHORI");//FHORI	浮动排产时间容余码	FHORI 计划边际码
					String MTVFP = IM_ITEM.getString("MTVFP");//MTVFP	可用性检查的检查组---
					String SBDKZ = IM_ITEM.getString("SBDKZ");//SBDKZ	对于独立和集中需求的相关需求标识---
					String FEVOR = IM_ITEM.getString("FEVOR");//FEVOR	生产管理员	FEVOR生产调度员
					String LGPBE = IM_ITEM.getString("LGPBE");//LGPBE	库存仓位	LGPBE 库存仓位
					String IPRKZ = IM_ITEM.getString("IPRKZ");//IPRKZ	货架寿命到期日的期间标识	IPRKZ 货架寿命到期日的期间标识
					new BaseBean().writeLog("批量大小="+DISLS);
					new BaseBean().writeLog("采购类型="+BESKZ);
					new BaseBean().writeLog("生产仓储地点="+LGPRO);
					new BaseBean().writeLog("采购仓储地点="+LGFSB);
					new BaseBean().writeLog("计划边际码="+FHORI);
					new BaseBean().writeLog("可用性检查的检查组="+MTVFP);
					new BaseBean().writeLog("对于独立和集中需求的相关需求标识="+SBDKZ);
					new BaseBean().writeLog("生产调度员="+FEVOR);
					new BaseBean().writeLog("库存仓位="+LGPBE);
					new BaseBean().writeLog("货架寿命到期日的期间标识="+IPRKZ);

					String MHDHB = IM_ITEM.getString("MHDHB");//MHDHB	总货架寿命	MHDHB 总货架寿命
					String MHDRZ = IM_ITEM.getString("MHDRZ");//MHDRZ	最短剩余货架寿命 MHDRZ	 最小剩余货架寿命
					String QMPUR = IM_ITEM.getString("QMPUR");//QMPUR	激活采购中的 QM  QMPUR QM采购激活
					String SSQSS = IM_ITEM.getString("SSQSS");//SSQSS	质检控制码	 SSQSS QM控制码
					String KZDKZ = IM_ITEM.getString("KZDKZ");//KZDKZ	凭证需求标识	KZDKZ 凭证需求
					String BWTTY = IM_ITEM.getString("BWTTY");//BWTTY	评估类别	BWTTY 评估类别
					String BKLAS = IM_ITEM.getString("BKLAS");//BKLAS	评估类	 BKLAS 评估类
					String VPRSV = IM_ITEM.getString("VPRSV");//VPRSV	价格控制指示符	 VPRSV 价格控制
					String MLAST = IM_ITEM.getString("MLAST");//MLAST	物料价格确定: 控制		MLAST 价格确定
					String PEINH = IM_ITEM.getString("PEINH");//PEINH	价格单位	PEINH 价格单位
					new BaseBean().writeLog("总货架寿命="+MHDHB);
					new BaseBean().writeLog("最小剩余货架寿命="+MHDRZ);
					new BaseBean().writeLog("QM采购激活="+QMPUR);
					new BaseBean().writeLog("QM控制码="+SSQSS);
					new BaseBean().writeLog("凭证需求="+KZDKZ);
					new BaseBean().writeLog("评估类别="+BWTTY);
					new BaseBean().writeLog("评估类="+BKLAS);
					new BaseBean().writeLog("价格控制="+VPRSV);
					new BaseBean().writeLog("价格确定="+MLAST);
					new BaseBean().writeLog("价格单位="+PEINH);

					String STPRS = IM_ITEM.getString("STPRS");//STPRS	标准价格	STPRS
					String EKALR = IM_ITEM.getString("EKALR");//EKALR	物料根据数量结构进行成本核算  EKALR 用QS的成本估算
					String HKMAT = IM_ITEM.getString("HKMAT");//HKMAT	物料相关的源	HKMAT 物料来源
					String AWSLS = IM_ITEM.getString("AWSLS");//AWSLS	差异码 AWSLS 
					String LOSGR = IM_ITEM.getString("LOSGR");//LOSGR	批量产品成本核算`	LOSGR 成本核算批量
					String HRKFT = IM_ITEM.getString("HRKFT");//HRKFT	作为成本要素子组的原始组	HRKFT 原始组
					String KOSGR = IM_ITEM.getString("KOSGR");//KOSGR	成本核算间接费用组	KOSGR 间接费分组
					new BaseBean().writeLog("标准价格="+STPRS);
					new BaseBean().writeLog("用QS的成本估算="+EKALR);
					new BaseBean().writeLog("物料来源="+HKMAT);
					new BaseBean().writeLog("差异码="+AWSLS);
					new BaseBean().writeLog("成本核算批量="+LOSGR);
					new BaseBean().writeLog("原始组="+HRKFT);
					new BaseBean().writeLog("间接费分组="+KOSGR);
					
					
					Object MTART1 = 0;
					Object KONDM1 = 0;
					Object XCHPF1 = 0;
					Object BESKZ1 = 0;
					Object FHORI1 = 0;
					Object IPRKZ1 = 0;
					Object QMPUR1 = 0;
					Object SSQSS1 = 0;
					Object KZDKZ1 = 0;
					Object BWTTY1 = 0;
					Object VPRSV1 = 0;
					Object EKALR1 = 0;
					Object HKMAT1 = 0;
					Object AWSLS1 = 0;
					Object TAXM11 =0;
					Object MLAST1 = 0;
					Object FEVOR1="";
					Object WRKST1 = 0;
					//税分类转换1 全税 0   2 半税 专用于硫酸铵 1 
					if(TAXM1.equals("1")){
						TAXM11 = 0;
					}else if(TAXM1.equals("2")){
						TAXM11 = 1;
					}else{
						TAXM11="null";
					}
					
					//MTART 物料类型转换2FL 0   3YCL 1  4BCP 2  5CP 3  1WZ 4  6GZ 5  7FW 6  8STY 7
					if(MTART.equals("2FL")){
						MTART1 = 0;
					}else if(MTART.equals("3YCL")){
						MTART1 = 1;
					}else if(MTART.equals("4BCP")){
						MTART1 = 2;
					}else if(MTART.equals("5CP")){
						MTART1 = 3;
					}else if(MTART.equals("1WZ")){
						MTART1 = 4;
					}else if(MTART.equals("6GZ")){
						MTART1 = 5;
					}else if(MTART.equals("7FW")){
						MTART1 = 6;
					}else if(MTART.equals("8STY")){
						MTART1 = 7;
					}else{
						MTART1 = "null";
					}
					//KONDM  物料定价组转换01  13%退税   0
					if(!KONDM.equals("")){
						KONDM1=0;
					}else{
						KONDM1="null";
					}
					//XCHPF  批次管理转换Check框
					if(XCHPF.equals("X")){
						XCHPF1 = 1;
					}else if(XCHPF.equals("")){
						XCHPF1 = 0;
					}
					//BESKZ  采购类型转换E 0  X 1  F 2
					if(BESKZ.equals("E")){
						BESKZ1 = 0;
					}else if(BESKZ.equals("X")){
						BESKZ1 = 1;
					}else if(BESKZ.equals("F")){
						BESKZ1 = 2;
					}else{
						BESKZ1 ="null";
					}
					//FHORI  计划边际码转换  000 0
					if(FHORI.equals("000")){
						FHORI1 = 0;
					}else{
						FHORI1 = "null";
					}
					//IPRKZ  货架寿命到期日的期间标识转换  D 0 M 1  W 2  Y  3
					if(IPRKZ.equals("D")){
						IPRKZ1 = 0;
					}else if(IPRKZ.equals("M")){
						IPRKZ1 = 1;
					}else if(IPRKZ.equals("W")){
						IPRKZ1 = 2;
					}else if(IPRKZ.equals("Y")){
						IPRKZ1 = 3;
					}else{
						IPRKZ1 = "null";
					}
					//QMPUR  QM采购激活转换 Check框
					if(QMPUR.equals("X")){
						QMPUR1 = 1;
					}else if(QMPUR.equals("")){
						QMPUR1 = 0;
					}
					//SSQSS  QM控制码转换 Z001   交货下达（华友信息记录）   0
					if(!SSQSS.equals("")){
						SSQSS1=0;
					}else{
						SSQSS1="null";
					}
					new BaseBean().writeLog("QM控制码转换="+(!SSQSS.equals("")));
					new BaseBean().writeLog("QM控制码转换=="+(SSQSS.equals("")));
					//KZDKZ  凭证需求转换 Check框
					if(KZDKZ.equals("X")){
						KZDKZ1 = 1;
					}else if(KZDKZ.equals("")){
						KZDKZ1 = 0;
					}
					//BWTTY  评估类别转换 B 0  C 1  H 2  R 3  X 4
					if(BWTTY.equals("B")){
						BWTTY1 = 0;
					}else if(BWTTY.equals("C")){
						BWTTY1 = 1;
					}else if(BWTTY.equals("H")){
						BWTTY1 = 2;
					}else if(BWTTY.equals("R")){
						BWTTY1 = 3;
					}else if(BWTTY.equals("X")){
						BWTTY1 = 4;
					}else{
						BWTTY1 = "null";
					}
					//VPRSV  价格控制转换 S 0  V 1 
					if(VPRSV.equals("S")){
						VPRSV1 = 0;
					}else if(VPRSV.equals("V")){
						VPRSV1 = 1;
					}else{
						VPRSV1 = "null";
					}
					//EKALR  用QS的成本估算转换Check框
					if(EKALR.equals("X")){
						EKALR1 = 1;
					}else if(EKALR.equals("")){
						EKALR1 = 0;
					}
					//HKMAT  物料来源转换Check框
					if(HKMAT.equals("X")){
						HKMAT1 = 1;
					}else if(HKMAT.equals("")){
						HKMAT1 = 0;
					}
					//AWSLS 差异码转换 000001  0  000002 1
					if(AWSLS.equals("000001")){
						AWSLS1 = 0;
					}else if(AWSLS.equals("000002")){
						AWSLS1 = 1;
					}else{
						AWSLS1="null";
					}
					
					//价格确定转换 2-0  3-1
					if(MLAST.equals("2")){
						MLAST1= 0;
					}else if(MLAST.equals("3")){
						MLAST1= 1;
					}else{
						MLAST1= "null";
					}



					//基本物料转换  WRKST1

					if(WRKST.equals("常规物资")){
						WRKST1 = 0 ;
					}else if(WRKST.equals("进料加工")){
						WRKST1 = 1;
					}else if(WRKST.equals("受托加工")){
						WRKST1 = 2;
					}else if(WRKST.equals("一般贸易")){
						WRKST1 = 3;
					}else if(WRKST.equals("自有贸易")){
						WRKST1 = 4;
					}
					
					
					new BaseBean().writeLog("TAXM11="+TAXM11);
					new BaseBean().writeLog("MTART1="+MTART1);
					new BaseBean().writeLog("KONDM1="+KONDM1);
					new BaseBean().writeLog("XCHPF1="+XCHPF1);
					new BaseBean().writeLog("BESKZ1="+BESKZ1);
					new BaseBean().writeLog("FHORI1="+FHORI1);
					new BaseBean().writeLog("IPRKZ1="+IPRKZ1);
					new BaseBean().writeLog("QMPUR1="+QMPUR1);
					new BaseBean().writeLog("SSQSS1="+SSQSS1);
					new BaseBean().writeLog("KZDKZ1="+KZDKZ1);
					new BaseBean().writeLog("BWTTY1="+BWTTY1);
					new BaseBean().writeLog("VPRSV1="+VPRSV1);
					new BaseBean().writeLog("EKALR1="+EKALR1);
					new BaseBean().writeLog("HKMAT1="+HKMAT1);
					new BaseBean().writeLog("AWSLS1="+AWSLS1);
					new BaseBean().writeLog("MLAST1="+MLAST1);
					new BaseBean().writeLog("FEVOR1="+FEVOR1);
					new BaseBean().writeLog("WRKST1="+WRKST1);
					
					String sql2 = "update "+tablename+"_dt1  set MLAST="+MLAST1+",TAXM1="+TAXM11+",MATNR='"+MATNR+
                            "',MTART="+MTART1+",WRKST='"+WRKST1+"',SPART='"+SPART+"',VKORG='"+VKORG+"',VTWEG='"+VTWEG+
                            "',DWERK='"+DWERK+"',KTGRM='"+KTGRM+"',KONDM="+KONDM1+",XCHPF=1,DISMM='"+DISMM+"',DISPO='"+DISPO+
                            "',DISLS='"+DISLS+"',BESKZ="+BESKZ1+",LGPRO='"+LGPRO+"',LGFSB='"+LGFSB+"',FHORI="+FHORI1+
                            ",LGPBE='"+LGPBE+"',IPRKZ="+IPRKZ1+",MHDHB="+MHDHB+",MHDRZ="+MHDRZ+",QMPUR="+QMPUR1+
                            ",SSQSS="+SSQSS1+",KZDKZ="+KZDKZ1+",BWTTY="+BWTTY1+",BKLAS='"+BKLAS+"',VPRSV="+VPRSV1+
                            ",PEINH="+PEINH+",STPRS="+STPRS+",EKALR="+EKALR1+",HKMAT="+HKMAT1+",AWSLS="+AWSLS1+
                            ",LOSGR="+LOSGR+",HRKFT='"+HRKFT+"',KOSGR='"+KOSGR+"' where MATNR1 = "+MATNR+" and  mainid="+mianid;
					new BaseBean().writeLog("sql2="+sql2);
					rs2.executeSql(sql2);
					
				}
			}
	     	
		}
			
			
		}catch(Exception e){
			message=e.getMessage();
			e.printStackTrace();
			new BaseBean().writeLog("WuLiaoZhuShuJuXiuGai"+e);
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
