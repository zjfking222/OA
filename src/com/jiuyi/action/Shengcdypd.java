package com.jiuyi.action;

import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;

public class Shengcdypd extends BaseAction implements Action {

    public String execute(RequestInfo requestInfo){
        RecordSet rs=new RecordSet();
        RecordSet rs1 = new RecordSet();
        int error = 0;
        int mianid=0;
        int wuzlx=0;
        JCO.Client myConnection =null;
        String requestid = requestInfo.getRequestid();//请求号
        String workflowid = requestInfo.getWorkflowid();//流程id
        String tablename = SAPUtil.getTablename(requestInfo);//表名
        String message = "";

        String sql = "select id,wuzlx from "+tablename+" where requestid="+requestid;
        rs.execute(sql);
        rs.next();
        mianid = rs.getInt(1);
        wuzlx = rs.getInt(2);
        try {
            String sql1 = "select MTART,FEVOR1,BWTTY from "+tablename+"_dt1 where mainid="+mianid;
            new BaseBean().writeLog("sql1=="+sql1);
            rs1.execute(sql1);
            while(rs1.next()){

                String MTART = rs1.getString(1);//物料类型
                String FEVOR1=rs1.getString(2);//生产调度员
                String BWTTY=rs1.getString(3);//评估类别


                new BaseBean().writeLog("物料类型"+MTART);
                new BaseBean().writeLog("生产调度员"+FEVOR1);
                new BaseBean().writeLog("评估类别"+BWTTY);

                //只有5CP类型才需填写生产调度员
//                if((null != FEVOR1 && !"".equals(FEVOR1)) && !MTART.equals("3")){
//                    requestInfo.getRequestManager().setMessage("111100");//
//                    requestInfo.getRequestManager().setMessagecontent("只有成品才须填写生产调度员字段");
//                    return "0";
//                }
                //非原料、半成品、成品无需填写评估类别
                if(!MTART.equals("1")&&!MTART.equals("2")&&!MTART.equals("3")){
                    if(null != BWTTY && !"".equals(BWTTY)){
                        requestInfo.getRequestManager().setMessage("111100");//
                        requestInfo.getRequestManager().setMessagecontent("非原料、半成品、成品无需填写评估类别");
                        return "0";
                    }
                }
                //控制主表和明细表物料类型一致
                if(wuzlx==15){
                    if(!MTART.equals("3")){
                        requestInfo.getRequestManager().setMessage("111100");//
                        requestInfo.getRequestManager().setMessagecontent("物料类型不一致");
                        return "0";
                    }
                }else if (wuzlx==14){
                    if(!MTART.equals("2")){
                        requestInfo.getRequestManager().setMessage("111100");//
                        requestInfo.getRequestManager().setMessagecontent("物料类型不一致");
                        return "0";
                    }
                }else if (wuzlx==13){
                    if(!MTART.equals("0")){
                        requestInfo.getRequestManager().setMessage("111100");//
                        requestInfo.getRequestManager().setMessagecontent("物料类型不一致");
                        return "0";
                    }
                }else if (wuzlx==12){
                    if(!MTART.equals("1")){
                        requestInfo.getRequestManager().setMessage("111100");//
                        requestInfo.getRequestManager().setMessagecontent("物料类型不一致");
                        return "0";
                    }
                }
            }

        }catch (Exception e){
            message=e.getMessage();
            e.printStackTrace();
            new BaseBean().writeLog("接口执行错误"+e);
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
