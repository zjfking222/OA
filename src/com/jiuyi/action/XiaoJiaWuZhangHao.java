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
 * 销假无账号
 * @author yangc
 *
 */
public class XiaoJiaWuZhangHao extends BaseAction implements Action{

	public String execute(RequestInfo requestInfo) {
		RecordSet rs = new RecordSet();
		RecordSet rs2 = new RecordSet();
		int error = 0;
		int mianid=0;
		JCO.Client myConnection =null;
		String requestid = requestInfo.getRequestid();//请求号
		String workflowid = requestInfo.getWorkflowid();//流程id
		String tablename = SAPUtil.getTablename(requestInfo);//表名
		String functionName="ZHR_OE007_CALU_TIME";//函数的名字
		String message = "";
		try{
			
			myConnection =SAPUtil.getSAPcon();
	        myConnection.connect(); 
			System.out.println("连接SAP成功");
	        JCO.Repository myRepository = new JCO.Repository("Repository",myConnection); //只是一個名字
	        IFunctionTemplate ft = myRepository.getFunctionTemplate(functionName);//從這個函數範本獲得該SAP函數的物件
	        JCO.Function bapi = ft.getFunction();
			//用于设置输入参数
			JCO.ParameterList  input=bapi.getImportParameterList();//输入参数和结构处理
			JCO.ParameterList   inputtable= bapi.getTableParameterList();//输入表的处理
			
			String sql = "select yuangbh,xjksrq,xjkssj,xjjsrq,xjjssj,leix from "+tablename+" where requestid="+requestid;
			rs.executeSql(sql);
			if(rs.next()){
				String h_yuangbh = rs.getString("yuangbh");//员工编号
				String h_xjksrq = rs.getString("xjksrq");//销假开始日期
				String h_xjkssj = rs.getString("xjkssj");//销假开始时间
				String h_xjjsrq = rs.getString("xjjsrq");//销假结束日期
				String h_xjjssj = rs.getString("xjjssj");//销假结束时间
				String h_leix =JiuyiUtil.getSelectname(rs.getString("leix"),"12157");//销假类型
				
				new  BaseBean().writeLog("h_yuangbh~"+h_yuangbh);
				new  BaseBean().writeLog("h_xjksrq~"+h_xjksrq);
				new  BaseBean().writeLog("h_xjkssj~"+h_xjkssj);
				new  BaseBean().writeLog("h_xjjsrq~"+h_xjjsrq);
				new  BaseBean().writeLog("h_xjjssj~"+h_xjjssj);
				new  BaseBean().writeLog("h_leix~"+h_leix);
				
				input.setValue(h_yuangbh,"IM_PERNR");
				input.setValue(h_xjksrq,"IM_BEGDA");
				input.setValue(h_xjkssj,"IM_BEGUZ");
				input.setValue(h_xjjsrq,"IM_ENDDA");
				input.setValue(h_xjjssj,"IM_ENDUZ");
				input.setValue(h_leix,"IM_AWART");
	
				
				myConnection.execute(bapi);
				JCO.ParameterList  outpar = bapi.getExportParameterList();//输出参数和结构处理
		     	JCO.ParameterList  Table00 = bapi.getTableParameterList();//输出表的处理
		     	
				//输出参数
				String shis=outpar.getString("E_STDAZ");//时数
				String nianxjde=outpar.getString("E_ANZHL");//年休假定额
				String shengyxss=outpar.getString("E_QTNEG");//剩余小时数
				//String txjde=outpar.getString("E_ANZHL_TIAO");//调休假定额
				
				String sql2="update "+tablename+" set shis="+shis+",nianxjde="+nianxjde+",shengyxss="+shengyxss+" where yuangbh='"+h_yuangbh+"' and requestid="+requestid;
				new BaseBean().writeLog("sql2=="+sql2);
				rs2.executeSql(sql2);
				
				if(Double.parseDouble(shengyxss)<0&&h_leix.equals("4")){
					requestInfo.getRequestManager().setMessage("111100");//
					requestInfo.getRequestManager().setMessagecontent("提交失败:"+"员工编号为:"+h_yuangbh+"的剩余小时数不足");
					return "0";
				}
				
				//判断时间是否输入错误
				if(h_xjkssj.length()!=5||h_xjjssj.length()!=5){
					requestInfo.getRequestManager().setMessage("111100");//
					requestInfo.getRequestManager().setMessagecontent("提交失败:"+"输入时间有误，请检查");
					return "0";
				}
				//判断日期是否输入错误
				if(h_xjksrq.length()!=8||h_xjjsrq.length()!=8){
					requestInfo.getRequestManager().setMessage("111100");//
					requestInfo.getRequestManager().setMessagecontent("提交失败:"+"输入日期有误，请检查");
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
