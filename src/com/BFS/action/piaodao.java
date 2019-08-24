package com.BFS.action;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jiuyi.util.SAPUtil;
import org.artofsolving.jodconverter.document.JsonDocumentFormatRegistry;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.StaticObj;
import weaver.interfaces.datasource.DataSource;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;

/**票到付款至BFS
 * author CYN
 */

public class piaodao implements Action {
    public String execute(RequestInfo requestInfo){
        System.out.println("进入Action requestid="+requestInfo.getRequestid());
        String requestid = requestInfo.getRequestid();//请求ID
        int formid = requestInfo.getRequestManager().getFormid();//表单ID
        //取主表
        String tablename = SAPUtil.getTablename(requestInfo);//表名
        RecordSet rs1 = new RecordSet();
        //查询主表数据
        String sql = "select * from " + tablename + " where requestid=" + requestid;
        new BaseAction().writeLog("sql==" + sql);
        rs1.execute(sql);
        rs1.next();
        new BaseBean().writeLog("开始获取主表主数据");
        String bukrs=rs1.getString("bukrs");//公司代码
        new BaseBean().writeLog ("bukrs"+bukrs);

        String khhzh=rs1.getString("khhzh");//开户行账号
        new BaseBean().writeLog ("khhzh"+khhzh);

        String lifnr=rs1.getString("name1");//供应商
        new BaseBean().writeLog ("lifnr"+lifnr);

        String sqfkje=rs1.getString("je");//申请付款金额
        new BaseBean().writeLog ("sqfkje"+sqfkje);

        String fkfs=rs1.getString("fkfszm");//付款方式
        new BaseBean().writeLog ("fkfs"+fkfs);

        int BFS=0;//BFS开户行
        new BaseBean().writeLog ("BFS"+BFS);

        String lcbm=rs1.getString("lcbh");//流程编号

        String bz=rs1.getString("bz");//备注
        int llh=0;//联行号
        String jhbh=rs1.getString("jhbh");//计划编号

        JSONArray jsonArr= new JSONArray();
        JSONObject jsonObj=new JSONObject();
        JSONObject dataObj=new JSONObject();
        jsonObj.put("SERIAL_NO_ERP",lcbm);
        if("".equals(jhbh)){
            jhbh="0";
        }
        jsonObj.put("DATA_SOURCE",jhbh);
        jsonObj.put("CORP_CODE",bukrs);
        jsonObj.put("OPP_BANK_ACC",khhzh);
        jsonObj.put("OPP_ACC_NAME",lifnr);
        jsonObj.put("OPP_BANK_NAME",BFS);//银行名称
        jsonObj.put("amt",sqfkje);
        jsonObj.put("VOUCHER_TYPE",fkfs);
        jsonObj.put("ISFORINDIVIDUAL",0);
        jsonObj.put("VOUCHER_NO_ERP",lcbm);
        jsonObj.put("PAYER_ACC_NO","00000000000000000");
        jsonObj.put("PAYER_ACC_NAME","");
        jsonObj.put("PAYEE_CODE",llh);
        jsonObj.put("ABS",bz);//备注

        jsonArr.add(jsonObj);
        dataObj.put("ERP_PAY_INFO",jsonArr);
        String dataString=JSONObject.toJSONString(dataObj);

        new BaseBean().writeLog("开始调用付款webservice");

        try{
            new BaseBean().writeLog("传入参数"+dataString);
            HygyPaymentWebService factor=new HygyPaymentWebService();
            HygyPaymentWebServicePortType PaymentPortType=factor.getHygyPaymentWebServiceHttpSoap11Endpoint();
            String result=PaymentPortType.erpPaymentData(dataString);
            JSONObject resultobj= JSONObject.parseObject(result);
            new BaseAction().writeLog("result==" + result);
            JSONArray messageArr=resultobj.getJSONArray("ERP_PAY_INFO");
            String status=messageArr.getJSONObject(0).getString("STATUS");
            new BaseBean().writeLog("STATUS"+status);
            if(status.equals("F")){
                requestInfo.getRequestManager().setMessage("111100");//提醒信息id
                String message=messageArr.getJSONObject(0).getString("MESSAGE");
                requestInfo.getRequestManager().setMessagecontent("传输资金系统出错：" + message);//提醒信息内容
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

}
