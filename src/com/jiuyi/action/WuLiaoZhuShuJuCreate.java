package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;
import com.jiuyi.util.TransUtil;
import com.qiyuesuo.pdf.text.M;
import com.sap.conn.rfc.driver.LG;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

import net.sf.json.JSON;
import org.eclipse.swt.internal.win32.INPUT;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;

/**
 * 物料主数据创建、修改  扩充工厂
 *
 * @author cyn
 */
public class WuLiaoZhuShuJuCreate extends BaseAction implements Action {
    @Override
    public String execute(RequestInfo requestInfo) {

        RecordSet rs = new RecordSet();
        RecordSet rs1 = new RecordSet();
        RecordSet rs2 = new RecordSet();
        int error = 0;
        int mianid = 0;
        String message = "";
        JCO.Client myConnection = null;
        String requestid = requestInfo.getRequestid();//请求号
        String tablename = SAPUtil.getTablename(requestInfo);//表名
        new BaseBean().writeLog("当前的requestid===" + requestid + "表名===" + tablename);
        try {
            new BaseBean().writeLog("进入try");
            myConnection = SAPUtil.getSAPcon();
            myConnection.connect();//连接SAP
            System.out.println("连接SAP成功");
            JCO.Repository myRepository = new JCO.Repository("Repository", myConnection); //只是一個名字
            String functionName = "ZMM_OE008_MATERIAL_INBOUND";//函数的名字
            new BaseBean().writeLog("函数名称" + functionName);
            IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);//從這個函數範本獲得該SAP函數的物件
            JCO.Function bapi = ft.getFunction();
            //用于设置输入参数
            JCO.ParameterList input = bapi.getImportParameterList();//输入参数和结构处理
            JCO.ParameterList inputtable = bapi.getTableParameterList();//输入表的处理

            JCO.Structure IM_HEADER = input.getStructure("IM_HEADER");//输入参数和结构
            JCO.Table IM_ITEM = inputtable.getTable("IM_ITEM");//输入表


            //查询主表
            String sql = "select * from " + tablename + " where requestid=" + requestid;
            new BaseAction().writeLog("sql==" + sql);
            rs.execute(sql);
            rs.next();

            new BaseBean().writeLog("开始获取主表主数据");
            //获取mainid
            int mainid = rs.getInt("id");
            //销售视图创建标识
            String SALES_VIEW = rs.getString("SALES_VIEW");
            new BaseBean().writeLog("SALES_VIEW" + SALES_VIEW);
            //采购视图创建标识
            String PURCHASE_VIEW = rs.getString("PURCHASE_VIEW");
            new BaseBean().writeLog("PURCHASE_VIEW" + PURCHASE_VIEW);
            //MRP视图创建标识
            String MRP_VIEW = rs.getString("MRP_VIEW");
            new BaseBean().writeLog("MRP_VIEW" + MRP_VIEW);
            //工作计划视图创建标识
            String WORK_SCHED_VIEW = "X";
            //库存视图创建标识
            String STORAGE_VIEW = "X";
            //质量视图创建标识
            String QUALITY_VIEW = rs.getString("QUALITY_VIEW");
            new BaseBean().writeLog("QUALITY_VIEW" + QUALITY_VIEW);
            //财务视图创建标识
            String ACCOUNT_VIEW = rs.getString("ACCOUNT_VIEW");
            new BaseBean().writeLog("ACCOUNT_VIEW" + ACCOUNT_VIEW);
            //基本视图创建标识
            String BASIC_VIEW = "X";
            new BaseBean().writeLog("BASIC_VIEW==" + BASIC_VIEW);
            //是否生产
            String SFSC = rs.getString("sfsc");
            new BaseBean().writeLog("SFSC==" + SFSC);
            //请求id
            String REQID = requestid;
            new BaseBean().writeLog("REQID==" + REQID);

            TransUtil transUtil = new TransUtil();
            input.setValue(REQID, "IM_REQID");//请求id
            input.setValue(SFSC, "IM_SFSC");//是否生产性物料

            //视图
            IM_HEADER.setValue(transUtil.getWlty(SALES_VIEW), "SALES_VIEW");//销售视图
            IM_HEADER.setValue(transUtil.getWlty(PURCHASE_VIEW), "PURCHASE_VIEW");//采购视图
            IM_HEADER.setValue(transUtil.getWlty(MRP_VIEW), "MRP_VIEW");//生产视图
            IM_HEADER.setValue(transUtil.getWlty(QUALITY_VIEW), "QUALITY_VIEW");//质量梳头有
            IM_HEADER.setValue(transUtil.getWlty(ACCOUNT_VIEW), "ACCOUNT_VIEW");//财务视图
            IM_HEADER.setValue(BASIC_VIEW, "BASIC_VIEW");//基本视图
            IM_HEADER.setValue(WORK_SCHED_VIEW,"WORK_SCHED_VIEW");//工作计划视图
            IM_HEADER.setValue(STORAGE_VIEW,"STORAGE_VIEW");//库存视图


            //查询明细表
            String sql1 = "select * from " + tablename + "_dt1 where mainid=" + mainid +" and (cjjg !=1 or cjjg is null)";
            new BaseAction().writeLog("sql1==" + sql1);
            rs1.execute(sql1);
            new BaseBean().writeLog("开始获取明细表表主数据");
            while (rs1.next()) {

                String MATNR = rs1.getString("MATNR");//物料编码
                String MAKTX = rs1.getString("MAKTX");//物料描述
                String MTART = rs1.getString("MTART");//物料类型
                String WERKS = rs1.getString("WERKS");//工厂
                String LGORT = rs1.getString("LGORT");//库存
                String MAKTX2 = rs1.getString("wlms2");//物料描述2
                String MEINS = rs1.getString("MEINS");//基本计量单位
                String MATKL = rs1.getString("MATKL");//物料组
                String SPART = rs1.getString("SPART");//产品组
                String EXTWG = rs1.getString("EXTWG");//外部物料组
                String BISMT = rs1.getString("BISMT");//旧物料
                String MEINH = rs1.getString("MEINH");//第二计量单位
                String UMREZ = rs1.getString("UMREZ");//转换关系
                String UMREN = rs1.getString("UMREN");//基本单位关系
                String XCHPF = rs1.getString("XCHPF");//批次管理
                String LGPBE = rs1.getString("LGPBE");//库存仓位
                String DWERK = rs1.getString("DWERK");//交货工厂
                String KTGRM = rs1.getString("KTGRM");//科目设置组
                String KONDM = rs1.getString("KONDM");//物料定价组
                String QMPUR = rs1.getString("QMPUR");//QM采购激活
                String SSQSS = rs1.getString("SSQSS");//QM控制码
                String KZDKZ = rs1.getString("KZDKZ");//凭证需求
                String BKLAS = rs1.getString("BKLAS");//评估类
                String VPRSV = rs1.getString("VPRSV");//价格控制
                String PEINH = rs1.getString("PEINH");//价格单位
                String LOSGR = rs1.getString("LOSGR");//成本核算批量
                String EKALR = rs1.getString("EKALR");//用QS的成本估算
                String HKMAT = rs1.getString("HKMAT");//物料来源
                String DISMM = rs1.getString("DISMM");//MRP类型
                String AWSLS = rs1.getString("AWSLS");//差异码
                String VKORG = rs1.getString("VKORG");//销售组织
                String VTWEG = rs1.getString("VTWEG");//分销渠道
                String WRKST = rs1.getString("WRKST");//基本物料
                String DISPO = rs1.getString("DISPO");//MRP控制者
                String DISLS = rs1.getString("DISLS");//批量大小
                String BESKZ = rs1.getString("BESKZ");//采购类型
                String LGPRO = rs1.getString("LGPRO");//生产仓储地点
                String LGFSB = rs1.getString("LGFSB");//采购仓储地点
                String FHORI = rs1.getString("FHORI");//计划边际码
                String FEVOR = rs1.getString("FEVOR1");//生产调度员
                String IPRKZ = rs1.getString("IPRKZ");//货架寿命到期日的期间标识
                String MHDHB = rs1.getString("MHDHB");//总货架寿命
                String MHDRZ = rs1.getString("MHDRZ");//最小剩余货架寿命
                String BWTTY = rs1.getString("BWTTY");//评估类别
                String HRKFT = rs1.getString("HRKFT");//原始组
                String KOSGR = rs1.getString("KOSGR");//间接费分组
                String MLAST = rs1.getString("MLAST");//价格确定
                String STPRS = rs1.getString("STPRS");//标准价格
                String PRCTR = rs1.getString("PRCTR");//利润中心


                String MBRSH = "C";//
                String MTVFP = "02";//
                String SBDKZ = "2";//
                String TAXM1 = "1";//
                String MTPOS = "NORM";//
                String MTPOS_MARA = "NORM";//
                String VERSG = "1";//
                String TRAGR = "0001";//
                String LADGR = "0001";//


                //明细表赋值
                new BaseBean().writeLog("IM_ITEM开始赋值");
                IM_ITEM.appendRow();
                IM_ITEM.setValue(MBRSH, "MBRSH");//行业部门
                new BaseBean().writeLog("MBRSH===" + IM_ITEM.getString("MBRSH"));
                IM_ITEM.setValue(WERKS, "WERKS");//工厂
                new BaseBean().writeLog("WERKS===" + IM_ITEM.getString("WERKS"));
                IM_ITEM.setValue(LGORT, "LGORT");//库存
                new BaseBean().writeLog("LGORT===" + IM_ITEM.getString("LGORT"));
                IM_ITEM.setValue(MAKTX, "MAKTX");//物料描述
                new BaseBean().writeLog("MAKTX===" + IM_ITEM.getString("MAKTX"));
                IM_ITEM.setValue(MEINS, "MEINS");//基本计量单位
                new BaseBean().writeLog("MEINS===" + IM_ITEM.getString("MEINS"));
                IM_ITEM.setValue(MATKL, "MATKL");//物料组
                new BaseBean().writeLog("MATKL===" + IM_ITEM.getString("MATKL"));
                IM_ITEM.setValue(SPART, "SPART");//产品组
                new BaseBean().writeLog("SPART===" + IM_ITEM.getString("SPART"));
                IM_ITEM.setValue(EXTWG, "EXTWG");//外部物料组
                new BaseBean().writeLog("EXTWG===" + IM_ITEM.getString("EXTWG"));
                IM_ITEM.setValue(BISMT, "BISMT");//旧物料号
                new BaseBean().writeLog("BISMT===" + IM_ITEM.getString("BISMT"));
                IM_ITEM.setValue(MEINH, "MEINH");//第二计量单位
                new BaseBean().writeLog("MEINH===" + IM_ITEM.getString("MEINH"));
                IM_ITEM.setValue(UMREZ, "UMREZ");//转换关系
                new BaseBean().writeLog("UMREZ===" + IM_ITEM.getString("UMREZ"));
                IM_ITEM.setValue(UMREN, "UMREN");//基本单位关系
                new BaseBean().writeLog("UMREN===" + IM_ITEM.getString("UMREN"));
                IM_ITEM.setValue(LGPBE, "LGPBE");//库存仓位
                new BaseBean().writeLog("LGPBE===" + IM_ITEM.getString("LGPBE"));
                IM_ITEM.setValue(DWERK, "DWERK");//交货工厂
                new BaseBean().writeLog("DWERK===" + IM_ITEM.getString("DWERK"));
                IM_ITEM.setValue(KTGRM, "KTGRM");//科目设置组
                new BaseBean().writeLog("KTGRM===" + IM_ITEM.getString("KTGRM"));
                IM_ITEM.setValue(MTPOS, "MTPOS");//
                new BaseBean().writeLog("MTPOS===" + IM_ITEM.getString("MTPOS"));
                IM_ITEM.setValue(MTPOS_MARA, "MTPOS_MARA");//
                new BaseBean().writeLog("MTPOS_MARA===" + IM_ITEM.getString("MTPOS_MARA"));
                IM_ITEM.setValue(VERSG, "VERSG");//
                new BaseBean().writeLog("VERSG===" + IM_ITEM.getString("VERSG"));
                IM_ITEM.setValue(TRAGR, "TRAGR");//
                new BaseBean().writeLog("TRAGR===" + IM_ITEM.getString("TRAGR"));
                IM_ITEM.setValue(LADGR, "LADGR");//
                new BaseBean().writeLog("LADGR===" + IM_ITEM.getString("LADGR"));
                IM_ITEM.setValue(BKLAS, "BKLAS");//评估类
                new BaseBean().writeLog("BKLAS===" + IM_ITEM.getString("BKLAS"));
                IM_ITEM.setValue(PEINH, "PEINH");//价格单位
                new BaseBean().writeLog("PEINH===" + IM_ITEM.getString("PEINH"));
                IM_ITEM.setValue(LOSGR, "LOSGR");//成本核算批量
                new BaseBean().writeLog("LOSGR===" + IM_ITEM.getString("LOSGR"));
                IM_ITEM.setValue(MTVFP, "MTVFP");//
                new BaseBean().writeLog("MTVFP===" + IM_ITEM.getString("MTVFP"));
                IM_ITEM.setValue(DISMM, "DISMM");//MRP类型
                new BaseBean().writeLog("DISMM===" + IM_ITEM.getString("DISMM"));
                IM_ITEM.setValue(VKORG, "VKORG");//销售组织
                new BaseBean().writeLog("VKORG===" + IM_ITEM.getString("VKORG"));
                IM_ITEM.setValue(VTWEG, "VTWEG");//分销渠道
                new BaseBean().writeLog("VTWEG===" + IM_ITEM.getString("VTWEG"));
                IM_ITEM.setValue(DISPO, "DISPO");//MRP控制者
                new BaseBean().writeLog("DISPO===" + IM_ITEM.getString("DISPO"));
                IM_ITEM.setValue(DISLS, "DISLS");//批量大小
                new BaseBean().writeLog("DISLS===" + IM_ITEM.getString("DISLS"));
                IM_ITEM.setValue(LGPRO, "LGPRO");//生产仓储地点
                new BaseBean().writeLog("LGPRO===" + IM_ITEM.getString("LGPRO"));
                IM_ITEM.setValue(LGFSB, "LGFSB");//采购仓储地点
                new BaseBean().writeLog("LGFSB===" + IM_ITEM.getString("LGFSB"));
                IM_ITEM.setValue(SBDKZ, "SBDKZ");//
                new BaseBean().writeLog("SBDKZ===" + IM_ITEM.getString("SBDKZ"));
                IM_ITEM.setValue(FEVOR, "FEVOR");//生产调度员
                new BaseBean().writeLog("FEVOR===" + IM_ITEM.getString("FEVOR"));
                IM_ITEM.setValue(MHDHB, "MHDHB");//总货架寿命
                new BaseBean().writeLog("MHDHB===" + IM_ITEM.getString("MHDHB"));
                IM_ITEM.setValue(MHDRZ, "MHDRZ");//最小剩余货架寿命
                new BaseBean().writeLog("MHDRZ===" + IM_ITEM.getString("MHDRZ"));
                IM_ITEM.setValue(HRKFT, "HRKFT");//原始组
                new BaseBean().writeLog("HRKFT===" + IM_ITEM.getString("HRKFT"));
                IM_ITEM.setValue(KOSGR, "KOSGR");//间接费分组
                new BaseBean().writeLog("KOSGR===" + IM_ITEM.getString("KOSGR"));
                IM_ITEM.setValue(MATNR, "MATNR");//物料编码
                new BaseBean().writeLog("MATNR===" + IM_ITEM.getString("MATNR"));
                IM_ITEM.setValue(MAKTX2, "MAKTX2");//物料描述2
                new BaseBean().writeLog("MAKTX2===" + IM_ITEM.getString("MAKTX2"));
                IM_ITEM.setValue(PRCTR, "PRCTR");//利润中心
                new BaseBean().writeLog("PRCTR===" + IM_ITEM.getString("PRCTR"));
                IM_ITEM.setValue(STPRS, "STPRS");//标准价格
                new BaseBean().writeLog("STPRS===" + IM_ITEM.getString("STPRS"));



                IM_ITEM.setValue(transUtil.getMtart(MTART), "MTART");//物料类型
                new BaseBean().writeLog("MTART===" + IM_ITEM.getString("MTART"));
                IM_ITEM.setValue(transUtil.getWlty(XCHPF), "XCHPF");//批次管理
                new BaseBean().writeLog("XCHPF===" + IM_ITEM.getString("XCHPF"));
                IM_ITEM.setValue(transUtil.getWlty(QMPUR), "QMPUR");//QM采购激活
                new BaseBean().writeLog("QMPUR===" + IM_ITEM.getString("QMPUR"));
                IM_ITEM.setValue(transUtil.getWlty(KZDKZ), "KZDKZ");//凭证需求
                new BaseBean().writeLog("KZDKZ===" + IM_ITEM.getString("KZDKZ"));
                IM_ITEM.setValue(transUtil.getWlty(EKALR), "EKALR");//用QS的成本估算
                new BaseBean().writeLog("EKALR===" + IM_ITEM.getString("EKALR"));
                IM_ITEM.setValue(transUtil.getWlty(HKMAT), "HKMAT");//物料来源
                new BaseBean().writeLog("HKMAT===" + IM_ITEM.getString("HKMAT"));
                IM_ITEM.setValue(transUtil.getKondm(KONDM), "KONDM");//物料定价组
                new BaseBean().writeLog("KONDM===" + IM_ITEM.getString("KONDM"));
                IM_ITEM.setValue(transUtil.getVprsv(VPRSV), "VPRSV");//价格控制
                new BaseBean().writeLog("VPRSV===" + IM_ITEM.getString("VPRSV"));
                IM_ITEM.setValue(transUtil.getAwsls(AWSLS), "AWSLS");//差异码
                new BaseBean().writeLog("AWSLS===" + IM_ITEM.getString("AWSLS"));
                IM_ITEM.setValue(transUtil.getWlzsjJbwl(WRKST), "WRKST");//基本物料
                new BaseBean().writeLog("WRKST===" + IM_ITEM.getString("WRKST"));
                IM_ITEM.setValue(transUtil.getFhori(FHORI), "FHORI");//计划边际码
                new BaseBean().writeLog("FHORI===" + IM_ITEM.getString("FHORI"));
                IM_ITEM.setValue(transUtil.getIprkz(IPRKZ), "IPRKZ");//货架寿命到期日的期间标识
                new BaseBean().writeLog("IPRKZ===" + IM_ITEM.getString("IPRKZ"));
                IM_ITEM.setValue(transUtil.getMlast(MLAST), "MLAST");//价格确定
                new BaseBean().writeLog("MLAST===" + IM_ITEM.getString("MLAST"));
                IM_ITEM.setValue(transUtil.getSsqss(SSQSS), "SSQSS");//QM控制码
                new BaseBean().writeLog("SSQSS===" + IM_ITEM.getString("SSQSS"));
                IM_ITEM.setValue(transUtil.getBeskz(BESKZ), "BESKZ");//采购类型
                new BaseBean().writeLog("BESKZ===" + IM_ITEM.getString("BESKZ"));
                IM_ITEM.setValue(transUtil.getWlzsjSfl(TAXM1), "TAXM1");//
                new BaseBean().writeLog("TAXM1===" + IM_ITEM.getString("TAXM1"));
                IM_ITEM.setValue(transUtil.getBwtty(BWTTY), "BWTTY");//评估类别
                new BaseBean().writeLog("BWTTY===" + IM_ITEM.getString("BWTTY"));
//				new BaseBean().writeLog("IM_ITEM"+IM_ITEM.toString());
            }
            new BaseBean().writeLog("开始执行");
            myConnection.execute(bapi);

            JCO.ParameterList outs = bapi.getExportParameterList();//输出参数和结构处理
            JCO.ParameterList outtab = bapi.getTableParameterList();//输出表
            JCO.Table E_List = outtab.getTable("E_LIST");//报错表
            String E_TYPE = outs.getString("E_TYPE");//返回类型
            String E_MESSAGE = outs.getString("E_MESSAGE");//返回信息
            new BaseBean().writeLog("E_TYPE===" + E_TYPE + "E_MESSAGE==" + E_MESSAGE + "E_List===" + E_List);
            new BaseBean().writeLog("E_LIST的行数===" + E_List.getNumRows());
            String[] e_message = E_MESSAGE.split("~");
            for (int i = 0; i < e_message.length; i++) {
                new BaseBean().writeLog("e_message===" + e_message[i]);
            }
            int num = 0;
            String result="";
            do {
                new BaseBean().writeLog("MATNR===" + E_List.getValue("MATNR"));
                new BaseBean().writeLog("ZTYPE===" + E_List.getValue("ZTYPE"));
                new BaseBean().writeLog("e_message[num]"+e_message[num].substring(6));
                String sql2 = "update "+tablename+"_dt1 set cjjg=1 " +
                        "where MATNR='"+e_message[num].substring(6)+"'";//创建成功=1
                if (E_List.getValue("ZTYPE").equals("E")) {

                    String MATNR=E_List.getString("MATNR");
                    result+="物料编码" + MATNR + "：" + e_message[num]+";";//提醒内容

                }else{
                    new BaseBean().writeLog("sql2==="+sql2);
                    rs2.execute(sql2);
                }
                num++;
            } while (E_List.nextRow());
            if (result!="") {
                requestInfo.getRequestManager().setMessage("111100");//提醒信息id
                requestInfo.getRequestManager().setMessagecontent("SAP端返回信息: " + result);//提醒信息内容
                return "0";
            }
        } catch (Exception e) {
            message = e.getMessage();
            e.printStackTrace();
            new BaseBean().writeLog("WuLiaoZhuShuJuCreate" + e);
            error = 1;
        } finally {
            if (null != myConnection) {
                SAPUtil.releaseClient(myConnection);
            }
        }

        if (error == 1) {
            requestInfo.getRequestManager().setMessage("111100");//提醒信息id
            requestInfo.getRequestManager().setMessagecontent("action执行出错-流程不允许提交到下一节点." + message);//提醒信息内容
            return "0";
        } else {
            return Action.SUCCESS;
        }

    }
}	
