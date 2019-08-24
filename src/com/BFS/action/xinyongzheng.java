package com.BFS.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiuyi.util.JudgeUserId;
import com.jiuyi.util.SAPUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.hrm.User;
import weaver.interfaces.datasource.DataSource;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
/**信用证至BFS
 * author CYN
 */


public class xinyongzheng implements Action {
    public String execute(RequestInfo requestInfo){
        System.out.println("进入Action requestid="+requestInfo.getRequestid());
        String requestid = requestInfo.getRequestid();//请求ID
        int formid = requestInfo.getRequestManager().getFormid();//表单ID
        int userid = requestInfo.getRequestManager().getUserId();//获取当前操作用户对象
        new BaseBean().writeLog("user==="+userid);

        //取主表
        String tablename = SAPUtil.getTablename(requestInfo);//表名
        RecordSet rs1 = new RecordSet();
        RecordSet rs2 = new RecordSet();
        RecordSet rs3 = new RecordSet();
        //查询主表数据
        String sql = "select * from " + tablename + " where requestid=" + requestid;
        new BaseAction().writeLog("sql==" + sql);
        rs1.execute(sql);
        rs1.next();

        new BaseBean().writeLog("开始获取主表主数据");

        new BaseBean().writeLog("判断信用证类型，0内外证都存在，1内证，2外证");

        int kzlx=rs1.getInt("kzlx");//开证类型
        new BaseBean().writeLog ("kzlx"+kzlx);
        String nzcg=rs1.getString("nzcg");//内证成功
        String wzcg=rs1.getString("wzcg");//外证成功

        //公有
        String hth=rs1.getString("hth");//合同号
        new BaseBean().writeLog ("hth"+hth);

        //外证
        String wxyzbh=rs1.getString("xyzbh");//外信用证编号
        new BaseBean().writeLog ("外信用证编号wxyzbh=="+wxyzbh);

        String wkzrq=rs1.getString("kaizrq");//外证开证日期
        new BaseBean().writeLog ("外证开证日期wkzqrq=="+wkzrq);

        String wdqrq=rs1.getString("wbdqrq");//外证到期日期
        new BaseBean().writeLog ("外证到期日期wdqrq=="+wdqrq);

        String wgc=rs1.getString("gongc");//外开证申请人
        new BaseBean().writeLog ("外工厂gongc=="+wgc);

        String wsyr=rs1.getString("shyr");//外证受益人
        new BaseBean().writeLog ("外证受益人wsyr=="+wsyr);

        String wsyrbm=rs1.getString("gongys");//外证受益人
        new BaseBean().writeLog ("外证受益人wsyr=="+wsyrbm);

        String wyhdm=rs1.getString("wyhdm");//外证银行
        new BaseBean().writeLog ("外证银行wkzh=="+wyhdm);

        String wsxm="0000";//授信种类
        new BaseBean().writeLog ("授信种类wsxm=="+wsxm);

        String whb=rs1.getString("hub");//外货币
        new BaseBean().writeLog ("外货币whb=="+whb);

        String wbb="";
        String wzje="";
        if(whb.equals("CNY")){
            wbb="人民币";
            wzje=rs1.getString("zjermb");//外证总金额
            new BaseBean().writeLog ("外证总金额cny wzje=="+wzje);
        }else if (whb.equals("USD")){
            wbb="美元";
            wzje=rs1.getString("kzzjeusd");//外证总金额
            new BaseBean().writeLog ("外证总金额USD wzje=="+wzje);
        }else if(whb.equals("EUR")){
            wbb="欧元";
            wzje=rs1.getString("kzzjeEUR");//外证总金额
            new BaseBean().writeLog ("外证总金额EUR wzje=="+wzje);
        }else if(whb.equals("RMB")){
            wbb="人民币";
            wzje=rs1.getString("zjermb");//外证总金额
            new BaseBean().writeLog ("外证总金额rmb wzje=="+wzje);
        }

        //内证
        String nxyzbh=rs1.getString("xyzbh1");//内证信用证编号
        new BaseBean().writeLog ("内证信用证编号nxyzbh=="+nxyzbh);

        String nkzrq=rs1.getString("kaizrq1");//内证开证日期
        new BaseBean().writeLog ("内证开证日期nkzqrq=="+nkzrq);

        String ndqrq=rs1.getString("nbdarq");//内证到期日期
        new BaseBean().writeLog ("内证到期日期ndqrq=="+ndqrq);

        String ngc=rs1.getString("gongc1");//外工厂编号
        new BaseBean().writeLog ("外工厂ngc=="+ngc);

        String nyhdm=rs1.getString("nyhdm");//内证银行
        new BaseBean().writeLog ("内证银行nkzh=="+nyhdm);

        String nsxm="0000";//授信种类
        new BaseBean().writeLog ("授信种类nsxm=="+nsxm);

        String nhb=rs1.getString("hub1");//内货币
        new BaseBean().writeLog ("内货币nhb=="+nhb);
        String nzje = "";
        String nbb = "";
        if(nhb.equals("CNY")){
            nbb="人民币";
            nzje=rs1.getString("zjermb2");//内证总金额rmb
            new BaseBean().writeLog ("nzjermb=="+nzje);
        }else if (nhb.equals("USD")){
            nbb="美元";
            nzje=rs1.getString("kzhzongjeusd");//内证总金额usd
            new BaseBean().writeLog ("nzjeusd=="+nzje);
        }else if(nhb.equals("EUR")){
            nbb="欧元";
            nzje=rs1.getString("kzzjeEUR2");//内证总金额eur
            new BaseBean().writeLog ("nzjeeur=="+nzje);
        }else if(nhb.equals("RMB")){
            nbb="人民币";
            nzje=rs1.getString("zjermb2");//内证总金额rmb
            new BaseBean().writeLog ("nzjermb=="+nzje);
        }

        String nsyr=rs1.getString("shyr1");//内证受益人
        new BaseBean().writeLog ("nsyr=="+nsyr);

        String nsyrbm=rs1.getString("gongys1");//内证受益人
        new BaseBean().writeLog ("nsyr=="+nsyrbm);

        JSONArray jsonArrn= new JSONArray();
        JSONObject jsonObjn=new JSONObject();
        JSONObject dataObjn=new JSONObject();
        JSONArray jsonArrw= new JSONArray();
        JSONObject jsonObjw=new JSONObject();
        JSONObject dataObjw=new JSONObject();
        jsonObjn.put("CORP_ID",ngc);
        jsonObjn.put("CODE",nxyzbh);//
        jsonObjn.put("CREDIT_NO",nxyzbh);
        jsonObjn.put("CONTRACT_NO",hth);
        jsonObjn.put("APPLICANT",ngc);
        jsonObjn.put("ISSUING_BANK",nyhdm);//银行名称
        jsonObjn.put("BENEFICIARY",nsyr);
        jsonObjn.put("ITEM_CODE",nsxm);
        jsonObjn.put("USED_MONEY",nzje);
        jsonObjn.put("HANDLING_CHARGE","");//手续费
        jsonObjn.put("CREDIT_MONEY",nzje);
        jsonObjn.put("ISSUING_DATE",nkzrq);
        jsonObjn.put("EXPIRY_DATE",ndqrq);
        jsonObjn.put("ISSUING_CUR_ID",nbb);
        jsonObjn.put("LC_INTER",1);

        jsonArrn.add(jsonObjn);
        dataObjn.put("LC_ENROL_INFO",jsonArrn);
        String dataStringn=JSONObject.toJSONString(dataObjn);

        new BaseBean().writeLog("信用证内证==="+dataStringn);

        jsonObjw.put("CORP_ID",wgc);
        jsonObjw.put("CODE",wxyzbh);//
        jsonObjw.put("CREDIT_NO",wxyzbh);
        jsonObjw.put("CONTRACT_NO",hth);
        jsonObjw.put("APPLICANT",wgc);
        jsonObjw.put("ISSUING_BANK",wyhdm);//银行名称
        jsonObjw.put("BENEFICIARY",wsyr);
        jsonObjw.put("ITEM_CODE",wsxm);
        jsonObjw.put("USED_MONEY",wzje);
        jsonObjw.put("HANDLING_CHARGE","");//手续费
        jsonObjw.put("CREDIT_MONEY",wzje);
        jsonObjw.put("ISSUING_DATE",nkzrq);
        jsonObjw.put("EXPIRY_DATE",wdqrq);
        jsonObjw.put("ISSUING_CUR_ID",wbb);
        jsonObjw.put("LC_INTER",1);

        jsonArrw.add(jsonObjw);
        dataObjw.put("LC_ENROL_INFO",jsonArrw);
        String dataStringw=JSONObject.toJSONString(dataObjw);

        new BaseBean().writeLog("信用证外证==="+dataStringw);

        try{
            if (kzlx==0){
                new BaseBean().writeLog("内外证都存在情况");
                new BaseBean().writeLog("nzcg==="+nzcg+"wzcg==="+wzcg);
                if (!nzcg.equals("S")){
                    JSONArray messageArrn=executeBFS(dataStringn);
                    String statusn=messageArrn.getJSONObject(0).getString("STATUS");
                    new BaseBean().writeLog("STATUSn"+statusn);
                    String sqln="update "+tablename+" set nzcg='S' " +
                            "where requestid='"+requestid+"'";//创建成功=1
                    if (statusn.equals("S")){
                        rs2.execute(sqln);
                    }else {
                        String message=messageArrn.getJSONObject(0).getString("MESSAGE");
                        requestInfo.getRequestManager().setMessagecontent("内证传输出错：" + message);//提醒信息内容
                        return "0";
                    }
                    JSONArray messageArrw=executeBFS(dataStringw);
                    String statusw=messageArrw.getJSONObject(0).getString("STATUS");
                    new BaseBean().writeLog("STATUSw"+statusw);
                    String sqlw="update "+tablename+" set wzcg='S' " +
                            "where requestid='"+requestid+"'";//创建成功=1
                    if (statusw.equals("S")){
                        rs3.execute(sqlw);
                    }else {
                        String message=messageArrw.getJSONObject(0).getString("MESSAGE");
                        requestInfo.getRequestManager().setMessagecontent("外证传输出错：" + message);//提醒信息内容
                        return "0";
                    }

                }else if (!wzcg.equals("S")){
                    JSONArray messageArrw=executeBFS(dataStringw);
                    String statusw=messageArrw.getJSONObject(0).getString("STATUS");
                    new BaseBean().writeLog("STATUSw"+statusw);
                    String sqlw="update "+tablename+" set wzcg='S' " +
                            "where requestid='"+requestid+"'";//创建成功=1
                    if (statusw.equals("S")){
                        rs3.execute(sqlw);
                    }else {
                        String message=messageArrw.getJSONObject(0).getString("MESSAGE");
                        requestInfo.getRequestManager().setMessagecontent("外证传输出错：" + message);//提醒信息内容
                        return "0";
                    }
                }else{
                    requestInfo.getRequestManager().setMessagecontent("原流程已提交成功，请重新申请流程！");//提醒信息内容
                    return "0";
                }
            }
            else if (kzlx==1){//内证
                new BaseBean().writeLog("只有内证存在情况");
                if (!nzcg.equals("S")) {
                    new BaseBean().writeLog("传入内证参数" + dataStringn);
                    JSONArray messageArrn = executeBFS(dataStringn);
                    String statusn = messageArrn.getJSONObject(0).getString("STATUS");
                    new BaseBean().writeLog("STATUSn" + statusn);
                    String sqln = "update " + tablename + " set nzcg='S' " +
                            "where requestid='" + requestid + "'";//创建成功=1
                    if (statusn.equals("S")) {
                        rs2.execute(sqln);
                    } else {
                        String message = messageArrn.getJSONObject(0).getString("MESSAGE");
                        requestInfo.getRequestManager().setMessagecontent("只存在内证，内证传输出错：" + message);//提醒信息内容
                        return "0";
                    }
                }else {
                    requestInfo.getRequestManager().setMessagecontent("原流程已提交成功，请重新申请流程！");//提醒信息内容
                    return "0";
                }
            }else if (kzlx==2){//外证
                new BaseBean().writeLog("只有外证存在情况");
                if (!wzcg.equals("S")){
                    new BaseBean().writeLog("传入外证参数"+dataStringw);
                    JSONArray messageArrw=executeBFS(dataStringw);
                    String statusw=messageArrw.getJSONObject(0).getString("STATUS");
                    new BaseBean().writeLog("STATUSw"+statusw);
                    String sqlw="update "+tablename+" set wzcg='S' " +
                            "where requestid='"+requestid+"'";//创建成功=1
                    if(statusw.equals("S")){
                        rs3.execute(sqlw);
                    }else {
                        String message=messageArrw.getJSONObject(0).getString("MESSAGE");
                        requestInfo.getRequestManager().setMessagecontent("只存在外证，外证传输出错：" + message);//提醒信息内容
                        return "0";
                    }
                }else {
                    requestInfo.getRequestManager().setMessagecontent("原流程已提交成功，请重新申请流程！");//提醒信息内容
                    return "0";
                }

            }else {
                requestInfo.getRequestManager().setMessagecontent("请选择信用证类型");//提醒信息内容
                return "0";
            }


        }catch(Exception e){
            requestInfo.getRequestManager().setMessage("111100");//提醒信息id
            requestInfo.getRequestManager().setMessagecontent("action执行出错-流程不允许提交到下一节点." + e);//提醒信息内容
            return "0";
        }finally{

        }
        return Action.SUCCESS;
    }
    public JSONArray executeBFS(String dataString){
        new BaseBean().writeLog("开始调用信用证webservice");
        //连接BFS数据库
        HygyLCEnrolWebService factor=new HygyLCEnrolWebService();
        HygyLCEnrolWebServicePortType LCEnrolPortType=factor.getHygyLCEnrolWebServiceHttpSoap11Endpoint();
        String result=LCEnrolPortType.erpLCEnrolData(dataString);
        JSONObject resultobj= JSONObject.parseObject(result);
        new BaseAction().writeLog("result==" + result);
        JSONArray messageArr=resultobj.getJSONArray("LC_ENROL_INFO");
        return messageArr;
    }
}
