package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;

import java.util.HashMap;
/***
 * 物料主数据物料类型、物料编码自动赋值
 * @author CYN
 *
 */
public class WuliaozsjKZ extends BaseAction implements Action {
    @Override
    public String execute(RequestInfo requestInfo) {
        new BaseBean().writeLog("开始执行物料主数据物料类型、编码赋值");
        int error=0;
        String message="";
        RecordSet rs = new RecordSet();
        RecordSet rs1 = new RecordSet();
        RecordSet rs2 = new RecordSet();
        RecordSet rs3=new RecordSet();
        String requestid = requestInfo.getRequestid();//请求号
        String tablename = SAPUtil.getTablename(requestInfo);//表名
        try{
            //获取主表的id 工厂编号
            String sql = "select id from "+tablename+" where requestid = "+requestid;
            new BaseBean().writeLog("sql==="+sql);
            rs.execute(sql);
            rs.next();
            //mainid
            int mainid = rs.getInt(1);
            new BaseBean().writeLog("mainid==="+mainid);
            //获取明细表数据
            String sql1 = "select * from " + tablename + "_dt1 where mainid=" + mainid +" and (cjjg !=1 or cjjg is null) and (MATNR is null or MATNR='')";
            new BaseAction().writeLog("sql1==" + sql1);
            rs1.execute(sql1);
            new BaseBean().writeLog("开始获取明细表表主数据");
            while (rs1.next()) {
                //获取明细表评估类
                int pgl = rs1.getInt("BKLAS");
                int mxid=rs1.getInt("id");
                int wllx = 0;
                String cswlbm = "";
                String sqlpgkl="";
//                if (pgl == 3002) {//原材料受托
//                    wllx = 7;
//                    cswlbm = "73050100301";
//                    sqlpgkl="BKLAS=3002";
//                } else
                if (pgl == 3030 || pgl == 3031) {//辅料
                    wllx = 0;
                    cswlbm = "08019900501";
                    sqlpgkl="BKLAS=3030 or BKLAS=3031";
                  }
//                  else if (pgl == 3000) {//原材料
//                    wllx = 1;
//                    cswlbm = "71070106001";
//                    sqlpgkl="BKLAS=3000";
//                } else if (pgl == 3001) {
//                    wllx = 1;
//                    cswlbm = "71010100101";
//                    sqlpgkl="BKLAS=3001";
//                } else if (pgl == 3003) {
//                    wllx = 1;
//                    cswlbm = "71020100001";
//                    sqlpgkl="BKLAS=3001";
//                } else if (pgl == 3004) {
//                    wllx = 1;
//                    cswlbm = "71060100001";
//                    sqlpgkl="BKLAS=3004";
//                }
                  else if (pgl == 5001) {//固资
                    wllx = 5;
                    cswlbm = "23999902001";
                    sqlpgkl="BKLAS=5001";
                }
//                else if (pgl == 7921) {//成品
//                    wllx = 3;
//                    cswlbm = "72010100301";
//                    sqlpgkl="BKLAS=7921";
//                } else if (pgl == 7923) {
//                    wllx = 3;
//                    cswlbm = "72050100301";
//                    sqlpgkl="BKLAS=7923";
//                } else if (pgl == 7924 || pgl==7920) {
//                    wllx = 3;
//                    cswlbm = "76030100501";
//                    sqlpgkl="BKLAS=7924 or BKLAS=7920";
//                }
                else if (pgl == 3042 || pgl == 3043 || pgl == 3046 || pgl == 3047 || pgl == 5002) {//物资
                    wllx = 4;
                    cswlbm = "18999902501";
                    sqlpgkl="BKLAS=3042 or BKLAS=3043 or BKLAS=3046 or BKLAS=3047 or BKLAS=5002";
                } else if (pgl == 3041) {
                    wllx = 4;
                    cswlbm = "16999900901";
                    sqlpgkl="BKLAS=3041";
                } else if (pgl == 3044 || pgl == 3045) {
                    wllx = 4;
                    cswlbm = "48999903001";
                    sqlpgkl="BKLAS=3044 or BKLAS=3045";
                } else if (pgl == 5003 || pgl == 5004 || pgl == 5005 || pgl == 5006 || pgl == 5007 || pgl == 5008 || pgl == 5009 || pgl == 5010 || pgl == 5011) {//服务
                    wllx = 6;
                    cswlbm = "56999900001";
                    sqlpgkl="BKLAS=5003 or BKLAS=5004 or BKLAS=5005 or BKLAS=5006 or BKLAS=5007 or BKLAS=5008 or BKLAS= 5009 or BKLAS=5010 or BKLAS=5011";
                }
//                else if (pgl == 7900 || pgl == 7925 || pgl == 9000 || pgl == 9001 || pgl == 9002 || pgl == 9003 || pgl == 9004 || pgl == 9005 || pgl == 9006 || pgl == 9007 || pgl == 9008 || pgl == 9009 || pgl == 9010) {//半成品
//                    wllx = 2;
//                    cswlbm = "73030100901";
//                    sqlpgkl="BKLAS=7900 or BKLAS=7925 or BKLAS=9000 or BKLAS=9001 or BKLAS=9002 or BKLAS=9003 or BKLAS=9004 or BKLAS=9005 or BKLAS=9006 or BKLAS=9007 or BKLAS=9008 or BKLAS=9009 or BKLAS=9010";
//                } else if (pgl == 7901) {
//                    wllx = 2;
//                    cswlbm = "73050100301";
//                    sqlpgkl="BKLAS=7901";
//                }
                new BaseBean().writeLog("wllx===" + wllx + " cswlbm===" + cswlbm);

                Long result;
                if (cswlbm.length()!=0){
                    Long cswlbmN = Long.parseLong(cswlbm);
                    String nowbmGZ = cswlbm.substring(0, 6);//现有物料编码规则
                    //查询该物料编码规则下评估类最后一条物料编码
                    String sql2 = "select max(MATNR) MATNR from " + tablename + "_dt1 where (" + sqlpgkl + ") and MATNR like'"+nowbmGZ+"%'";
                    new BaseBean().writeLog("sql2==="+sql2);
                    rs2.execute(sql2);
                    rs2.next();
                    String lastMATNR = rs2.getString("MATNR");
                    new BaseBean().writeLog("lastMATNR==="+lastMATNR);
                    if (lastMATNR.length()!=0){
                        Long lastMATNRN=Long.parseLong(lastMATNR);
                        if (lastMATNRN<cswlbmN){
                            new BaseBean().writeLog("初始编码");
                            result=cswlbmN;
                        }else {
                            new BaseBean().writeLog("编码累计");
                            String lastWlbmGZ = lastMATNR.substring(0, 6);
                            result = lastMATNRN;
                            new BaseBean().writeLog("lastWlbmGZ==="+lastWlbmGZ);
                            new BaseBean().writeLog("nowbmGZ==="+nowbmGZ);
                            if (lastWlbmGZ.equals(nowbmGZ)) {
                                result++;
                            } else {
                                result = cswlbmN;
                            }
                        }
                    }else{
                        new BaseBean().writeLog("初始编码");
                        result=cswlbmN;
                    }
                    String resultStr;
                    if (cswlbm.equals("08019900501")){
                        resultStr="0"+result;
                    }else {
                        resultStr=result.toString();
                    }
                    new BaseBean().writeLog("resultStr===" + resultStr);
                    String sql3="update "+tablename+"_dt1 set MTART="+wllx+",MATNR='"+resultStr+"' where mainid = "+mainid+" and id="+mxid;
                    new BaseBean().writeLog("sql3==="+sql3);
                    rs3.execute(sql3);
                    rs3.next();
                }
            }
        }catch (Exception e){
            message = e.getMessage();
            e.printStackTrace();
            new BaseBean().writeLog("WuLiaoZhuShuJuCreate" + e);
            error = 1;
        }
        if (error == 1) {
            requestInfo.getRequestManager().setMessagecontent("action执行出错-流程不允许提交到下一节点." + message);//提醒信息内容
            return "0";
        } else {
            return Action.SUCCESS;
        }
    }
}
