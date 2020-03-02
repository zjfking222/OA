package com.jiuyi.action;

import com.jiuyi.util.JiuyiUtil;
import com.jiuyi.util.SAPUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.interfaces.workflow.action.BaseAction;
import weaver.soa.workflow.request.RequestInfo;


/***
 * 请假无账号
 * @author yangc
 *
 */
public class QingJiaWuZhangHao extends BaseAction implements Action{

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
		String functionName="ZHR_OE007_CALU_TIME";//函数的名字
		String message = "";
		
		String sql = "select id from "+tablename+" where requestid="+requestid;
		rs.executeSql(sql);
		rs.next();
		mianid = rs.getInt(1);
		
		try{
			
			myConnection =SAPUtil.getSAPcon();
	        myConnection.connect(); 
			System.out.println("连接SAP成功");
		String sql1 = "select yuangbh,kaisrq,kaissj,jiesrq,jiessj,leix from "+tablename+"_dt1 where mainid="+mianid;
		new BaseBean().writeLog("sql1=="+sql1);
		rs1.executeSql(sql1);
		while(rs1.next()){
	        JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
	        IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);//從這個函數範本獲得該SAP函數的物件
	        JCO.Function bapi = ft.getFunction();
			//用于设置输入参数
			JCO.ParameterList  input=bapi.getImportParameterList();//输入参数和结构处理
			JCO.ParameterList   inputtable= bapi.getTableParameterList();//输入表的处理
			String h_yuangbh = rs1.getString("yuangbh");//员工编号
			String h_ksrq = rs1.getString("kaisrq");//开始日期
			String h_kssj = rs1.getString("kaissj");//开始时间
			String h_jsrq = rs1.getString("jiesrq");//结束日期
			String h_jssj = rs1.getString("jiessj");//结束时间
			String h_leix =JiuyiUtil.getSelectname(rs1.getString("leix"),"8695");//请假类型
			
			
			//判断时间是否输入错误
			new  BaseBean().writeLog("判断时间是否输入错误~"+(h_kssj.length()!=5||h_jssj.length()!=5));
			if(h_kssj.length()!=5||h_jssj.length()!=5){
				requestInfo.getRequestManager().setMessage("111100");//
				requestInfo.getRequestManager().setMessagecontent("提交失败:"+"输入时间有误，请检查");
				return "0";
			}
			//判断日期是否输入错误
			new  BaseBean().writeLog("判断日期是否输入错误~"+(h_ksrq.length()!=8||h_jsrq.length()!=8));
			if(h_ksrq.length()!=8||h_jsrq.length()!=8){
				requestInfo.getRequestManager().setMessage("111100");//
				requestInfo.getRequestManager().setMessagecontent("提交失败:"+"输入日期有误，请检查");
				return "0";
			}
			
			
			new  BaseBean().writeLog("h_yuangbh~"+h_yuangbh);
			new  BaseBean().writeLog("h_ksrq~"+h_ksrq);
			new  BaseBean().writeLog("h_kssj~"+h_kssj);
			new  BaseBean().writeLog("h_jsrq~"+h_jsrq);
			new  BaseBean().writeLog("h_jssj~"+h_jssj);
			new  BaseBean().writeLog("h_leix~"+h_leix);
			
			input.setValue(h_yuangbh,"IM_PERNR");//员工编号
			input.setValue(h_ksrq,"IM_BEGDA");//开始日期
			input.setValue(h_kssj,"IM_BEGUZ");//开始时间
			input.setValue(h_jsrq,"IM_ENDDA");//结束日期
			input.setValue(h_jssj,"IM_ENDUZ");//结束时间
			input.setValue(h_leix,"IM_AWART");//请假类型

			
			myConnection.execute(bapi);
			JCO.ParameterList  outpar = bapi.getExportParameterList();//输出参数和结构处理
	     	JCO.ParameterList  Table00 = bapi.getTableParameterList();//输出表的处理
	     	
			//输出参数
			String shis=outpar.getString("E_STDAZ");//时数
			String nianxjde=outpar.getString("E_ANZHL");//年休假定额
			String shengyxss=outpar.getString("E_QTNEG");//剩余小时数
			String txjde=outpar.getString("E_ANZHL_TIAO");//调休假定额
			String sjts=outpar.getString("E_ZABSJ");//事假天数
			String bjts=outpar.getString("E_ZABBJ");//病假天数
			String gsjts=outpar.getString("E_ZABGSJ");//工伤假天数
			String jxdj=outpar.getString("E_JXDJ");//绩效等级
			
			
			new  BaseBean().writeLog("时数~"+shis);
			new  BaseBean().writeLog("年休假定额~"+nianxjde);
			new  BaseBean().writeLog("剩余小时数~"+shengyxss);
			new  BaseBean().writeLog("调休假定额~"+txjde);
			new  BaseBean().writeLog("事假天数~"+sjts);
			new  BaseBean().writeLog("病假天数~"+bjts);
			new  BaseBean().writeLog("工伤假天数~"+gsjts);
			new BaseBean().writeLog("绩效等级~"+jxdj);
			
			String sql2="update "+tablename+"_dt1 set shis="+shis+",nianxjde="+nianxjde+",shengyxss="+shengyxss+",txjde="+txjde+",sj="+sjts+",bj="+bjts+",gsj="+gsjts+",jxdj='"+jxdj+"' where kaissj='"+h_kssj+"' and kaisrq='"+h_ksrq+"' and yuangbh='"+h_yuangbh+"' and mainid="+mianid;
			new BaseBean().writeLog("sql2=="+sql2);
			rs2.execute(sql2);
			
			if(Double.parseDouble(shengyxss)<=0&&h_leix.equals("4")){
				requestInfo.getRequestManager().setMessage("111100");//
				requestInfo.getRequestManager().setMessagecontent("提交失败:"+"员工编号为:"+h_yuangbh+"的剩余小时数不足,不能请假");
				return "0";
			}
			String E_TYPE=(outpar.getValue("E_TYPE")+"").replace("", "").replace("'", "");
			String E_MESSAGE=(outpar.getValue("E_MESSAGE")+"").replace("", "").replace("'", "");
			new BaseBean().writeLog("M_TYPE:"+E_TYPE+",M_MESSAGE:"+E_MESSAGE);
			if("E".equals(E_TYPE)){
				requestInfo.getRequestManager().setMessage("111100");//
				requestInfo.getRequestManager().setMessagecontent("BPC接口执行失败:"+E_MESSAGE);
				return "0";
			}
		}
		}catch(Exception e){
			message=e.getMessage();
			e.printStackTrace();
			new BaseBean().writeLog("QingJiaWuZhangHao"+e);
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
