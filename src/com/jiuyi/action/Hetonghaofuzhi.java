package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
/**
 * 合同号赋值
**/
public class Hetonghaofuzhi extends BaseBean implements Action {
    public String execute(RequestInfo requestInfo){
        RecordSet rs = new RecordSet();
        RecordSet rs1 = new RecordSet();
        int error = 0;
        String message = "";

        String requestid = requestInfo.getRequestid();//请求号
        String workflowid = requestInfo.getWorkflowid();//流程id
        String tablename = SAPUtil.getTablename(requestInfo);//表名

        String sql = "select hth2 from "+tablename+" where requestid="+requestid;
        new BaseBean().writeLog("sql==="+sql);
        rs.execute(sql);
        rs.next();
        String hth2=rs.getString(1);
        new BaseBean().writeLog("hth2==="+hth2);
        try {
            String sql2="update "+tablename+" set hth= '"+hth2+"' where requestid="+requestid;
            rs1.execute(sql2);
        }catch (Exception e){
            new BaseBean().writeLog("信用证赋值"+e);
            error=1;
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
