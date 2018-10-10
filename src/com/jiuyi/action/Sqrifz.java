package com.jiuyi.action;


import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *申请日期赋值
 * **/
public class Sqrifz extends BaseAction implements Action {
    public String execute (RequestInfo requestInfo){
        int error = 0;
        String message = "";
        String requestid = requestInfo.getRequestid();
        String tablename = JiuyiUtil.getTablename(requestInfo);

        RecordSet rs = new RecordSet();

        new BaseBean().writeLog("申请日期开始");
        try{
//            Calendar calendar = Calendar.getInstance();
//            y = calendar.get(Calendar.YEAR);
//            m = calendar.get(Calendar.MONTH);
//            d = calendar.get(Calendar.DATE);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String time=df.format(new Date());// new Date()为获取当前系统时间
            new BaseBean().writeLog("当前的时间是"+time);


            String sql = " update "+tablename+" set shriq='"+time+"' where requestid="+requestid;

            new BaseBean().writeLog("sql=="+sql);
            rs.execute(sql);
            rs.next();

        }catch (Exception e){
            message=e.getMessage();
            e.printStackTrace();
            new BaseBean().writeLog("Sqrirz"+e);
            error=1;
        }finally {

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
