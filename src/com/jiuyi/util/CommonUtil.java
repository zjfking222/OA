package com.jiuyi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.hrm.User;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Table;

public class CommonUtil {
	
	/**
	 * 根据
	 * @param gh
	 * @return
	 */
	public static JSONObject getHrmresource(String workcode){
		JSONObject ret=new JSONObject();
		String sql="select * from hrmresource where workcode='"+workcode+"'";
		String oauserid="";
		String oausername="";
		String oadepartmentid="";
		String oasubcompanyid="";
		RecordSet rs=new RecordSet();
		rs.execute(sql);
		if(rs.next()){
			oauserid=rs.getString("id");
			oausername=rs.getString("lastname");
			oadepartmentid=rs.getString("departmentid");
			oasubcompanyid=rs.getString("subcompanyid1");
		}
		ret.put("oauserid", oauserid);//OA人员id
		ret.put("oausername", oausername);//OA人员名称
		ret.put("oadepartmentid", oadepartmentid);//OA人员部门id
		ret.put("oasubcompanyid", oasubcompanyid);//OA人员分部id
		//User u=new User(Integer.parseInt(oauserid));
		//u.getUserSubCompany1();
		return ret;
	} 
	
	/**
	 * 根据流程id获取流程主表名称
	 * @param workflowId
	 * @return
	 */
	public static String getMainTableNameByWorkflowId(String workflowId){
		RecordSet rs=new RecordSet();
		rs.execute("select tablename from workflow_bill where id=(select formid from workflow_base where id="+workflowId+")");
		if(rs.next()){
			return rs.getString("tablename");
		}
		return "0";
	}
	
	/**
	 * 根据流程id获取流程主表名称
	 * @param workflowId
	 * @return
	 */
	public static ArrayList<String> getDetailTableNameByWorkflowId(String workflowId){
		RecordSet rs=new RecordSet();
		ArrayList<String> dtList=new ArrayList<String>();
		rs.execute("select tablename from Workflow_billdetailtable where billid=(select formid from workflow_base where id="+workflowId+")");
		while(rs.next()){
			dtList.add(rs.getString("tablename"));
		}
		return dtList;
	}
	
	/**
	 * 根据流程id获取流程类型名称
	 * @param workflowId
	 * @return
	 */
	public static String getworkflownameByWorkflowId(String workflowId){
		RecordSet rs=new RecordSet();
		rs.execute("select workflowname from workflow_base where id='"+workflowId+"'");
		if(rs.next()){
			return rs.getString("workflowname");
		}
		return "";
	}
	
	
	public static String getUserid(String username){
		String sql="select id from hrmresource where lastname='"+username+"'";
		RecordSet rs=new RecordSet();
		rs.execute(sql);
		if(rs.next()){
			return rs.getString("id");
		}
		return "1";
	}
	
	
	public static String getUseridByWorkcode(String workcode){
		String sql="select id from hrmresource where workcode='"+workcode+"'";
		RecordSet rs=new RecordSet();
		rs.execute(sql);
		if(rs.next()){
			return rs.getString("id");
		}
		return "1";
	}
	
	
	public static String getUseridByWorkcode1(String workcode){
		String sql="select id from hrmresource where workcode like '%"+workcode+"%'";
		RecordSet rs=new RecordSet();
		rs.execute(sql);
		if(rs.next()){
			return rs.getString("id");
		}
		return "1";
	}


	/**
	 * 根据货币的id获取货币的name
	 * @param currencyid
	 * @return
	 */
	public static String getCurrencyNameById(String currencyid){
		RecordSet rs = new RecordSet();
		rs.executeSql("select currencyname from FnaCurrency where id ="+currencyid);
		String s="";
		if(rs.next()){
			s= rs.getString("currencyname");
			return s==null?"":s;
		}else{
			return "";
		}
	}
	
	
	/**
	 * 根据流程workflowid 或者 建模表单模块id 获取流程或建模表单主表名称
	 * @param id 
	 * @param modeOrWf 0代表流程 1代表建模
	 * @return 返回流程或者建模主表名称
	 */
	public static String getTablename(String id,int modeOrWf){
		String sql="";
		if(modeOrWf==0){
			sql="select tablename from workflow_bill where id=(select formid from workflow_base where id="+id+")";			
		}else{
			sql="select tablename from workflow_bill where id=(select formid from modeinfo where id="+id+")";						
		}
		RecordSet rs = new RecordSet();
		rs.execute(sql);
		if(rs.next()){
			return rs.getString("tablename");
		}
		return "";
	}
	
	/**
	 * 根据省份ID获取省份的编码
	 * @param id
	 * @return
	 */
	public static String getProvinceCodeById(String id){
		RecordSet rs=new RecordSet();
		rs.execute("select SFSX from hrmprovince where id='"+id+"'");
		if(rs.next()){
			return rs.getString("SFSX");
		}else{
			return "";
		}
	}
	
	
	//获得tablename
		public static String getTablename(RequestInfo requestInfo){
			String workflowid = requestInfo.getWorkflowid();
			String formid="";
			String sql1="select formid from workflow_base where id="+workflowid;
			System.out.println(sql1);
			RecordSet rs = new RecordSet();
			rs.execute(sql1);
			if(rs.next()){
				formid = rs.getString("formid");
			}
			formid = formid.replaceAll("-", "");
			String tablename="formtable_main_"+formid;
			return tablename;
			
		}
		
		//获得tablename
		public static String getTablenameBywfid(String workflowid){
			String formid="";
			String sql1="select formid from workflow_base where id="+workflowid;
			System.out.println(sql1);
			RecordSet rs = new RecordSet();
			rs.execute(sql1);
			if(rs.next()){
				formid = rs.getString("formid");
			}
			formid = formid.replaceAll("-", "");
			String tablename="formtable_main_"+formid;
			return tablename;
			
		}

		//判断是否为时间类型
		public static String parsedata(Object value){
			if (value instanceof Date) {
				return TimeUtil.getDateString((Date)value);
			}else{
				if(value==null)value="";
				return value+"";
			}
		}
		/**
		 * 根据某个表的某个字段值获取另一个字段值
		 * @param fieldname 所需字段值的字段名
		 * @param tablename 表名
		 * @param byField 条件字段名
		 * @param byValue  条件字段值
		 * @return 返回所需字段值
		 */
		public static String getFieldValueFromTableByfield(String fieldname,String tablename,String byField,String byValue){
			String sql="select "+fieldname+" from "+tablename+" where "+byField+"='"+byValue+"'";
			//System.out.println("获取字段"+sql);
			RecordSet rs=new RecordSet();
			String val="";
			rs.execute(sql);
			if(rs.next()){
				val=rs.getString(fieldname);
			}
			return  val;
		}
		/**
		 * 根据userid 获取 name 有样式
		 * @param userid
		 * @return
		 */
		public static String getLastNameByUserId(String userid){
			RecordSet rs = new RecordSet();
			String result="";
			String lastname = "";
			if(rs.execute("select lastname from hrmresource where id = '"+userid+"'")&&rs.next()){
				lastname=Util.null2String(rs.getString("lastname"));
			}
			//System.out.println(browseid);
			result=lastname;
			return result;
			}
		/**
		 * 根据userid 获取 loginid
		 * @param userid
		 * @return
		 */
		public static String getLoginidByUserId(String userid){
			RecordSet rs = new RecordSet();
			String result="";
			String loginid = "";
			if(rs.execute("select loginid from hrmresource where id = '"+userid+"'")&&rs.next()){
				loginid=Util.null2String(rs.getString("loginid"));
			}
			//System.out.println(browseid);
			result=loginid;
			return result;
			}
		/**
		 * 根据loginid 获取 userid
		 * @param loginid
		 * @return
		 */
		public static String getUserIdByLoginid(String loginid){
			RecordSet rs = new RecordSet();
			String result="";
			String id = "";
			if(rs.execute("select id from hrmresource where loginid = '"+loginid+"'")&&rs.next()){
				id=Util.null2String(rs.getString("id"));
			}
			//System.out.println(browseid);
			result=id;
			return result;
			}
		/**
		 * 国际化语言
		 * @param c
		 * @param langu
		 * @return
		 */
		public static String SystemLangu(Integer c,Integer langu){
			RecordSet rs = new RecordSet();
			String sql = "select lastname from Systemlangu where indexid="+c+" and language="+langu;
			rs.execute(sql);
			rs.next();
			return rs.getString("lastname");
		}
		/**
		 * 根据部门ID获取部门描述
		 * @param id 部门ID
		 * @return 部门描述
		 */
		public static String getDepartmentById(String id,String zlglbfyzl){
			RecordSet rs = new RecordSet();
			String result="";
			rs.executeSql("select * from HrmDepartment where id = "+id);
			if(rs.next()){
				result= rs.getString("departmentname");
			}
			if(result.equals("董事会")){
				result="秘书室";
			}
			if(result.equals("学术组")){
				result="市场部";
			}
			if(result.equals("质量保障部")){
				if(zlglbfyzl.equals("0")){
					result+="-管理";
				}else if(zlglbfyzl.equals("1")){
					result+="-制造";
				}
			}
			return result;
		}
		/**
		 * 根据部门ID获取部门描述
		 * @param id 部门ID
		 * @return 部门描述
		 */
		public static String getDepartById(String id){
			RecordSet rs = new RecordSet();
			String result="";
			rs.executeSql("select * from HrmDepartment where id = "+id);
			if(rs.next()){
				result= rs.getString("departmentname");
			}
			return result;
		}
		
		/**
		 * 截取15位字符串
		 * @param str
		 * @return
		 */
		public static String cutString(String str){
			String strs="";
			strs+="<span onmouseover='showdiv(event,\""+str+"\")' onmouseout='hidediv(event)'>";
			if(str.length()>15){
				strs += str.substring(0, 15);
				strs+="......";
			}else{
				strs+=str;
			}
			strs+="</span>";
			return strs;
		}
		
		/**
		 * 获取城市名称
		 * @param id
		 * @return
		 */
		public static String getCity(int id){
			RecordSet rs = new RecordSet();
			rs.executeSql("select cityname from hrmcity where id="+id);
			rs.next();
			String cityname = rs.getString("cityname");
			return cityname;
		}
		
		/**
		 * 检查流程是否全都归档
		 * @param requestids
		 * @return count
		 */
		public static String getStatus(String requestids){
			RecordSet rs = new RecordSet();
			rs.executeSql("select currentnodetype from workflow_requestbase where requestid in ("+requestids+") and currentnodetype!='3'");
			return ""+rs.getCounts();
		}
		
		/**
		 * 获取下拉框的值
		 * @param id
		 * @return
		 */
		public static String getSelectItem(Integer selectvalue,Integer billid,String fieldname){
			String sql="select * from workflow_selectitem where selectvalue='"+selectvalue+"' and fieldid in (select id from workflow_billfield where billid = '"+billid+"' and fieldname='"+fieldname+"')";
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			rs.next();
			
			return ""+rs.getString("selectname");
		}
		
		/**
		 * 根据user id 获得 lastname
		 * @param id
		 * @return lastname
		 */
		public static String getUserNameById(Integer id){
			String sql="select lastname from hrmresource where id ="+id;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			rs.next();
			return ""+rs.getString("lastname");
		}
		/**
		 * 根据lastname 获得 user id
		 * @param lastname
		 * @return user id
		 */
		public static String getIdByUserName(String username){
			String sql="select id from hrmresource where lastname ='"+username+"'";
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			rs.next();
			return ""+rs.getString("id");
		}
		public static String SystemLanguByHtml(Integer languageid,Integer billid,String fieldname){
			
			String sql="select * from HtmlLabelInfo where indexid in" +
					" (select fieldlabel from workflow_billfield where billid = "+billid+" and fieldname = '"+fieldname+"')" +
					" and languageid = "+languageid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			rs.next();
			
			return ""+rs.getString("labelname");
		}
		/**
		 * 根据user department 获得 所有下级department
		 * @param depid
		 * @return depids
		 */
		public static String getDepidsByDepId(String depid){
			String result=depid;
			while(true){
				String sql="select id from HrmDepartment where supdepid in ("+depid+")";
				RecordSet rs = new RecordSet();
				rs.executeSql(sql);
				if(rs.getCounts()==0){
					break;
				}else{
					depid="";
				}
				while(rs.next()){
					result+=","+rs.getString("id");
					if(depid.equals("")){
						depid=rs.getString("id");
					}else{
						depid+=","+rs.getString("id");
					}
				}
			}
			return result;
		}
		

	    
		/**
		 * 获取对应字段的id
		 * @param formid FormID
		 * @param num 主表 ：0 明细表 1 ：1 以此类推 
		 * @return key,value
		 */
		public static HashMap<String, String> getFieldId(int formid,String num){
			HashMap<String, String> map = new HashMap<String, String>();
			RecordSet rs =  new RecordSet();
			String sql="";
			String tablename = "";
			if("0".equals(num)){//主表
				sql ="select * from workflow_billfield where billid="+formid+" and (detailtable ='' or detailtable is null)";
			}else{//明细
				sql = "select * from Workflow_billdetailtable where billid = "+formid +" and orderid = "+num;
				rs.execute(sql);
				if(rs.next()){
					tablename = rs.getString("tablename");
					sql ="select * from workflow_billfield where billid="+formid+" and detailtable ='"+tablename+"'";
				}
			}
			rs.execute(sql);
			while(rs.next()){
				map.put(Util.null2String(rs.getString("fieldname")).toLowerCase(), Util.null2String(rs.getString("id")));
			}
			return map;
		}



	    /**
	     * 
	     * @param name
	     * @param formid
	     * @param ismain
	     * @param num
	     * @return
	     */
	 	public static String getlabelId(String name,String formid,String ismain ,String num)
	 	{
	 		String id = "";
	 		String sql = "";
	 		if("0".equals(ismain))
	 		{
	 			sql = "select b.id,fieldname,detailtable from workflow_billfield b ,workflow_base a where b.billid=-"+formid+" and a.formid=b.billid and lower(fieldname)='"+name+"'";
	 		}else {
	 			sql = "select b.id,fieldname,detailtable from workflow_billfield b ,workflow_base a where b.billid=-"+formid+" and a.formid=b.billid and detailtable='formtable_main_"+formid+"_dt"+num+"' and lower(fieldname)='"+name+"'";
			}
	 		RecordSet rs = new RecordSet();
	 		rs.execute(sql);
			rs.next();
	 		id = Util.null2String(rs.getString("id"));
	 		return id ;
	 		
	 	}
	 	
	 	/**
	     * 流程转发
	     * @param requestids 多流程
	     * @param forwardoperator 当前操作者
	     * @param recipients 被转发人
	     * @return
	     */
		public static boolean forwardFlow(String requestids,int forwardoperator,String recipients){
			String[] requestid= requestids.split(",");
			try{
			for(int i=0;i<requestid.length;i++){
				weaver.soa.workflow.request.RequestService reqSvc = new weaver.soa.workflow.request.RequestService();
				reqSvc.forwardFlow(Integer.parseInt(requestid[i]) , forwardoperator, recipients, "","");
			}
			}catch(Exception e){
				
			}
			return true;
		}
		
		/**
		 * 根据人员id获取部门id
		 * @param id
		 * @return
		 */
		public static Integer getManagerByUserid(Integer id){
			RecordSet rs = new RecordSet();
			rs.executeSql("select managerid from hrmresource where id = "+id);
			rs.next();
			return rs.getInt("managerid");
		}
		
		/**
		 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
		 * @param n
		 * @return
		 */
	    public static String digitUppercase(double n){
	        String fraction[] = {"角", "分"};
	        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	        String unit[][] = {{"元", "万", "亿"},
	                     {"", "拾", "佰", "仟"}};
	 
	        String head = n < 0? "负": "";
	        n = Math.abs(n);
	         
	        String s = "";
	        for (int i = 0; i < fraction.length; i++) {
	            s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
	        }
	        if(s.length()<1){
	            s = "整";    
	        }
	        int integerPart = (int)Math.floor(n);
	 
	        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
	            String p ="";
	            for (int j = 0; j < unit[1].length && n > 0; j++) {
	                p = digit[integerPart%10]+unit[1][j] + p;
	                integerPart = integerPart/10;
	            }
	            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
	        }
	        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	    }
	    

	    /**
	     * 字符串去前导零
	     * @param theSapVal
	     * @return
	     */
		public static String replaceSAPPre0(String numStr){
			if(numStr==null){
				return "";
			}
			return numStr.replaceFirst("^0*", "");
		}
		

		
		/**
		 * 通过用户id查找用户部门
		 * @param userid
		 * @return lastname
		 */
		public static  String getUserDepartmentByUserId(String userid){
			RecordSet rs = new RecordSet();
			String departmentid = "";
			if(rs.execute("select departmentid from hrmresource where id = '"+userid+"'")&&rs.next()){
				departmentid=Util.null2String(rs.getString("departmentid"));
			}
			return departmentid;
		}

		/**  
	     * 计算两个日期之间相差的天数  
	     * @param smdate 较小的时间 
	     * @param bdate  较大的时间 
	     * @return 相差天数 
	     * @throws ParseException  
	     */    
	    public static int daysBetween(String sdate,String edate) throws ParseException    
	    {     
	    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        Date smdate=sdf.parse(sdate);  
	        Date bdate=sdf.parse(edate);  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));           
	    }


		/**
		 * 
		 * @param date1 <String>
		 * @param date2 <String>
		 * @return int
		 * @throws ParseException
		 */
		public static int getMonthSpace(String minDate, String maxDate)
				throws ParseException {
			ArrayList<String> result = new ArrayList<String>();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

		    Calendar min = Calendar.getInstance();
		    Calendar max = Calendar.getInstance();

		    min.setTime(sdf.parse(minDate));
		    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		    max.setTime(sdf.parse(maxDate));
		    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		    Calendar curr = min;
		    while (curr.before(max)) {
		     result.add(sdf.format(curr.getTime()));
		     curr.add(Calendar.MONTH, 1);
		    }
			return result.size();
		}
		

		/**
		 * 根据字符串格式获取指定格式的日期
		 * @param dateStr
		 * @return
		 */
		public static String getDate(String dateStr){
		    Calendar c = Calendar.getInstance();
		    SimpleDateFormat sdf=new SimpleDateFormat(dateStr);
		    return sdf.format(c.getTime());
		}
		
		/**
		 * 获取节点名称
		 * @param nodeid
		 * @return nodename
		 */
		public static String getNodeName(int nodeid){
			RecordSet rs = new RecordSet();
			String nodename="";
			if(rs.execute("select nodename from workflow_nodebase where id = '"+nodeid+"'")&&rs.next()){
				nodename=Util.null2String(rs.getString("nodename"));
			}
			return nodename;
		}
		

		
		
		/**
		 * 根据流程requestid获取流程主表名称
		 * @param requestid
		 * @return
		 */
		public static String getMainTableNameByRequestId(String requestid){
			RecordSet rs=new RecordSet();
			rs.execute("select tablename from workflow_bill where id=(select formid from workflow_base where id=(select workflowid from workflow_requestbase where requestid='"+requestid+"'))");
			if(rs.next()){
				return rs.getString("tablename");
			}
			return "";
		}
		
		
		/**
		 * 根据创建人，模块id，数据id对某条数据进行权限重构
		 * @param creator
		 * @param modeid
		 * @param dataid
		 */
		public static void resetPermission(int creator,int modeid,int dataid){
			new ModeRightInfo().rebuildModeDataShareByEdit(creator, modeid, dataid);
		}
		
		/**
		 * 根据关键字，表名称，和表的modeid重构某个人员的表单权限
		 * @param userId 人员id
		 * @param fieldValue 字段值
		 * @param fieldName 字段名称
		 * @param tableName 表名称
		 * @param modeId 表的modeid
		 */
		public static void resetPermission(int userId,String fieldValue,String fieldName,String tableName,int modeId){
			BaseBean base=new BaseBean();
			//获取数据id
			try {
				String dataid=getFieldValueFromTableByfield("id", tableName, fieldName, fieldValue);
				if (dataid!=null||!"".equals(dataid)) {
					//System.out.println("权限重构开始");
					int id=Integer.parseInt((dataid));
					ModeRightInfo modeinfo = new ModeRightInfo();
					modeinfo.setNewRight(true);
					modeinfo.editModeDataShare(userId, modeId,id);
					modeinfo.addDocShare(userId, modeId, id);
					//System.out.println("权限重构结束");
				}
			} catch (Exception e) {
				base.writeLog("权限重构失败："+e);
			}
		}
		
		/**
		 * 对设置的权限列表进行单条数据权限重构
		 * @param createrId 创建人
		 * @param modeId modeid
		 * @param dataId 数据id
		 */
		public static void resetPermissionSingle(int createrId,int modeId,int dataId){
			ModeRightInfo modeinfo = new ModeRightInfo();
			modeinfo.setModeId(modeId);
			modeinfo.addModeRightToDataRight(createrId, modeId, dataId);
			modeinfo.editModeDataShare2(createrId, modeId, dataId);
		}
		
		/**
		 * 对设置的权限列表进行单条数据和数据的全部附件进行权限重构
		 * @param createrId 创建人
		 * @param modeId modeid
		 * @param dataId 数据id
		 */
		public static void resetDataAndAllDocPermissionSingle(int createrId,int modeId,int dataId){
			ModeRightInfo modeinfo = new ModeRightInfo();
			modeinfo.setModeId(modeId);
			modeinfo.addModeRightToDataRight(createrId, modeId, dataId);
			modeinfo.editModeDataShare2(createrId, modeId, dataId);
			modeinfo.addDocShare(createrId, modeId, dataId);
		}
		
		/**
		 * 对设置的权限列表进行单条数据和数据的部分附件进行权限重构，如果附件不需要权限重构则附件参数docStr传入空字符串即可
		 * @param createrId 当前数据的创建人，对应当前表单的modedatacreater
		 * @param modeId modeid  对应模块的modeinfo表的id
		 * @param dataId 表单需要权限重构的数据id
		 * @param docStr 附件id字符串 例 ：12,21,42
		 */
		public static void resetDataAndPartDocPermissionSingle(int createrId,int modeId,int dataId,String docStr){
			ModeRightInfo modeinfo = new ModeRightInfo();
			RecordSet rs=new RecordSet();
			modeinfo.setModeId(modeId);
			modeinfo.addModeRightToDataRight(createrId, modeId, dataId);
			modeinfo.editModeDataShare2(createrId, modeId, dataId);
			docStr=removeDuplicateStr(docStr);
			if(!"".equals(docStr)){
				String[] docArr=removeDuplicateStr(docStr).split(",");
				for (String s : docArr) {
					if(!"".equals(s)){
						rs.execute("delete from DocShare where docid="+s+" and isSecDefaultShare=0 and sharesource="+dataId);
						modeinfo.addDocShareWithMode(Integer.parseInt(s),modeId,createrId,dataId);
					}
				}
			}
		}
		
		

		/**
		 * 根据userId，modeId，数据id,文档id字符串docStr 对某个人员进行设置的权限列表人员进行某条数据的某些文档的权限重构
		 * @param docStr 文档id字符串
		 * @param userId modedatacreater创建人id
		 * @param modeId modeid
		 * @param dataId 数据id
		 */
		public static void resetDocPermissionByDocInfo(String docStr,int userId,int modeId,int dataId){
			RecordSet rs=new RecordSet();
			String[] docArr=removeDuplicateStr(docStr).split(",");
			ModeRightInfo modeRightInfo=new ModeRightInfo();
			modeRightInfo.init();
			modeRightInfo.setModeId(modeId);
			for (String s : docArr) {
				if(!"".equals(s)){
					rs.execute("delete from DocShare where docid="+s+" and isSecDefaultShare=0 and sharesource="+dataId);
					modeRightInfo.addDocShareWithMode(Integer.parseInt(s),modeId,userId,dataId);
				}
			}
		}
		
		
		
		
		
		
		
		/**
		 * 根据userId，modeId，数据id 对某个人员进行设置的权限列表人员进行某条数据的文档的权限重构
		 * @param userId modedatacreater创建人id
		 * @param modeId modeid
		 * @param dataId 数据id
		 */
		public static void resetDocPermission(int userId,int modeId,int dataId){
			ModeRightInfo modeRightInfo=new ModeRightInfo();
			modeRightInfo.init();
			modeRightInfo.setModeId(modeId);
			modeRightInfo.addDocShare(userId, modeId, dataId);
		}
		
		
		/**
		 * 模块数据的文档权限重构
		 * @param modeId
		 */
		public static void resetAllDocPermission(int modeId){
			ModeRightInfo modeRightInfo=new ModeRightInfo();
			modeRightInfo.init();
			modeRightInfo.setModeId(modeId);
			modeRightInfo.resetDocRight();
		}
		
		
		/**
		 * 根据人员id获取人员名称
		 * @param id
		 * @return
		 */
		public static String getLastNameById(String id){
			String sql="select lastname from hrmresource where id='"+id+"'";
			RecordSet rs=new RecordSet();
			rs.execute(sql);
			if (rs.next()) {
				return rs.getString("lastname");
			}
			return "";
		}
		
		/**
		 * 获取指定某个表的指定类型的字段名称和字段id
		 * 根据表名称，表的billid(formid), 表名称，字段的OA的显示类型获取字段的字段名称和字段id
		 * @param billid 表formid
		 * @param tableName 表名
		 * @param fieldHtmlType  字段类型
		 * @return 返回字段名称和字段id的Map(fieldName,fieldId)
		 */
		public static Map<String,String> getFieldIdByFieldHtmlType(String billid,String tableName,String fieldHtmlType){
			BaseBean base=new BaseBean();
			String sql="select t1.* from (select id,fieldName from workflow_billfield where fieldname in (select name from syscolumns where id=object_id('"+tableName+"')) and billid='"+billid+"' ) t1 , workflow_billfield t2 where  t1.id=t2.id and t2.fieldhtmltype='"+fieldHtmlType+"'";
			RecordSet rs=new RecordSet();
			Map<String,String> map=new HashMap<String, String>();
			try {
				rs.execute(sql);
				while (rs.next()) {
					map.put(rs.getString("fieldName"), rs.getString("id"));
				}
				return map;
			} catch (Exception e) {
				base.writeLog("使用com.usas.util:getFieldIdByFieldHtmlType(String billid,String tableName,String fieldHtmlType)获取字段名称和id失败："+e);
			}
			return null;
		}
		
		
		
		
		
		
		
		
		/**
		 * 
		 * @param billid  流程或者建模表单的billid
		 * @param tableName 流程或建模表名
		 * @return 返回封装了key(字段名)和value(字段id)的Map
		 */
		public static Map<String,String> getFieldId(String billid,String tableName){
			BaseBean base=new BaseBean();
			RecordSet rs=new RecordSet();
			String sql="";
			rs.execute("select * from Workflow_billdetailtable where tablename='"+tableName+"'");
			if(rs.next()){
				sql="select id,fieldName from workflow_billfield where billid="+billid+" and detailtable = '"+tableName+"'";
			}else{
				sql="select id,fieldName from workflow_billfield where billid="+billid+" and detailtable = ''";
			}
			Map<String,String> map=new HashMap<String, String>();
			try {
				rs.execute(sql);
				while (rs.next()) {
					map.put(rs.getString("fieldName"), rs.getString("id"));
				}
				return map;
			} catch (Exception e) {
				base.writeLog("使用com.usas.util:getFieldId(String billid,String tableName)获取字段名称和id失败："+e);
			}
			return null;
		}
		
		/**
		 * 根据orderid获取封装了明细表或者主表的字段的id的Map
		 * Workflow_billdetailtable表里面存放着所有表的明细表的名称和orderid
		 * workflow_billfield表存放着所有字段的信息
		 * @param formid
		 * @param num
		 * @return
		 */
		public static Map<String,String> getFieldIdByFormIdAndOrderId(int formid,String orderid){
			Map<String,String> fieldsMap= new HashMap<String, String>();
			RecordSet rs =  new RecordSet();
			String sql="";
			String tablename = "";
			if("0".equals(orderid)){//主表
				tablename="";
			}else{//明细
				sql = "select * from Workflow_billdetailtable where billid = "+formid +" and orderid = "+orderid;
				rs.executeSql(sql);
				while(rs.next()){
					tablename = rs.getString("tablename");
				}
			}
			//System.out.println(tablename);
			sql ="select * from workflow_billfield where billid="+formid+" and detailtable = '"+tablename+"'";
			//System.out.println(sql);
			rs.executeSql(sql);
			while(rs.next()){
				fieldsMap.put(Util.null2String(rs.getString("fieldname")).toLowerCase(), Util.null2String(rs.getString("id")));
			}
			return fieldsMap;
		}
		
		

	    /**
	     * 获取复选框能否被选中     
	     * @param id      
	     * @return     
	     **/    
		public static String getCanCheck(String id){        
			if(Util.getIntValue(id)%2==0) { 
				return "true";//返回true 标识复选框可选      
			}else{
				return "false";//表示复选框不可选，进行隐藏复选框      
			}   
		}   
		
		/** 
		 * 获取能不能进行操作，进行权限判断 
		 * @param id     
		 * @param userid     
		 * @return     
		 **/ 
		public static ArrayList<String> getCanOperation(String id,String userid){    
			ArrayList<String> resultlist = new ArrayList<String>();   
			resultlist.add("true");  //对应第一个操作显示   
			resultlist.add("true");  //对应第二个操作显示，false表示不显示     
			return resultlist;    
		}

		
		/**
		 * 使用TableString传入参数获取，根据RFC函数名称和输入参数list获取RFC执行结果
		 * @param user
		 * @param otherparams 所有的传入参数都以Map<String,String> 参数名称，参数值的方式封装在otherparams
		 * @param request
		 * @param response
		 * @return 
		 */
		public static List<Map<String,String>> getSapDataByTableString(User user,Map<String, String> otherparams, HttpServletRequest request,HttpServletResponse response){
			String[] improtName=otherparams.get("importName").split(",");//tableString传入的参数名称必须一致
			String[] improtVal=otherparams.get("importVal").split(",");
			String RFCName=otherparams.get("RFCName");
			String outTableName=otherparams.get("outTableName");
			String[] outName=otherparams.get("outName").split(",");
			//System.out.println(otherparams.get("importName")+"::"+otherparams.get("importVal")+"：："+RFCName+"::"+outTableName+"::"+otherparams.get("outName"));
			//SAP输出表的行数据List
			Map<String,String> importMap =new HashMap<String, String>();
			for (int i = 0; i < improtName.length; i++) {
				importMap.put(improtName[i], improtVal[i]);
			}
			List<Map<String,String>> outList=new ArrayList<Map<String,String>>();
			outList=getSapData(RFCName, importMap, outTableName, Arrays.asList(outName));
			return outList;
		}
		
		
		
		/**
		 * 根据RFC函数名称和输入参数list获取RFC执行结果集
		 * @param RFCName RFC函数名称
		 * @param importMap    Map<String,String>对象
		 * @param outTableName SAP输出表的表名
		 * @return 返回输出表的List ,封装的是行数据,里面封装了行数据字段值的Map<String,String>对象
		 */
		public static List<Map<String,String>> getSapData(String RFCName,Map<String,String> importMap,String outTableName,List<String> outNameList){
			//System.out.println("连接SAP开始RFC名称："+RFCName+",输入参数列表："+importMap.toString()+",输出表名"+outTableName+",输出数据字段名List:"+outNameList);
			//连接SAP开始RFC名称：ZPS0010,输入参数列表：{I_PSPID=A101604},输出表名ET_PROJECT_COST,输出数据字段名List:[POSID, POST1, USERID, ZYSCB, ZSJCB, ZWGBFB]
			BaseBean base=new BaseBean();//日志对象
			//SAP输出表的行数据List
			List<Map<String,String>> outList=new ArrayList<Map<String,String>>();
			JCO.Client myConnection = null;
			try {
				myConnection = SAPUtil.getSAPcon("3");
				myConnection.connect();// 连接SAP
				//System.out.println("获取SAP链接");
				JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
				IFunctionTemplate ft = myRepository.getFunctionTemplate(RFCName);
				JCO.Function bapi = ft.getFunction();
				JCO.ParameterList  parameterList=bapi.getImportParameterList();
				//迭代器获取Map中的输入参数名称和值
				Iterator<String> it=importMap.keySet().iterator();
				String importName="";
				String importValue="";
				while (it.hasNext()) {
					importName= it.next();
					importValue=importMap.get(importName);
					//System.out.println(importName+":"+importValue);
					//将输入参数和值依次放入输入参数list中
					parameterList.setValue(importValue,importName);
				}
				myConnection.execute(bapi);//执行RFC
				//输出表
				Table outTable = bapi.getTableParameterList().getTable(outTableName);
				//System.out.println("返回消息长度"+outTable.getNumRows()+outTable.getRow());
				if (outTable.getNumRows()> 0) {
					for (int i = 0; i < outTable.getNumRows(); i++) {
						Map<String,String> outMap=new HashMap<String, String>();
						outTable.setRow(i);
						//封装行数据到Map中
						for (String name: outNameList) {
							outTable.getString(name);
							outMap.put(name, outTable.getString(name));
						}
						//将行数据的Map放进List
						outList.add(outMap);
					}
				}
				return outList;
			} catch (Exception e) {
				base.writeLog("获取"+RFCName+"函数的执行结果出错："+e);
			}finally{
				SAPUtil.releaseClient(myConnection);
			}
			return outList;
		}
		/**
		 * 根据RFC函数名称和输入参数list获取指定数据源的RFC执行结果集
		 * @param RFCName RFC函数名称
		 * @param importMap    Map<String,String>对象
		 * @param outTableName SAP输出表的表名
		 * @param sapSourceid sap数据源id
		 * @return 返回输出表的List ,封装的是行数据,里面封装了行数据字段值的Map<String,String>对象
		 */
		public static List<Map<String,String>> getSapData(String RFCName,Map<String,String> importMap,String outTableName,List<String> outNameList,String sapSourceid){
			//System.out.println("连接SAP开始RFC名称："+RFCName+",输入参数列表："+importMap.toString()+",输出表名"+outTableName+",输出数据字段名List:"+outNameList);
			//连接SAP开始RFC名称：ZPS0010,输入参数列表：{I_PSPID=A101604},输出表名ET_PROJECT_COST,输出数据字段名List:[POSID, POST1, USERID, ZYSCB, ZSJCB, ZWGBFB]
			BaseBean base=new BaseBean();//日志对象
			//SAP输出表的行数据List
			List<Map<String,String>> outList=new ArrayList<Map<String,String>>();
			JCO.Client myConnection = null;
			try {
				if(sapSourceid==""||sapSourceid==null){
					myConnection = SAPUtil.getSAPcon();
				}else{
					myConnection = SAPUtil.getSAPcon(sapSourceid);
				}
				myConnection.connect();// 连接SAP
				//System.out.println("获取SAP链接");
				JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
				IFunctionTemplate ft = myRepository.getFunctionTemplate(RFCName);
				JCO.Function bapi = ft.getFunction();
				JCO.ParameterList  parameterList=bapi.getImportParameterList();
				//迭代器获取Map中的输入参数名称和值
				Iterator<String> it=importMap.keySet().iterator();
				String importName="";
				String importValue="";
				while (it.hasNext()) {
					importName= it.next();
					importValue=importMap.get(importName);
					//System.out.println(importName+":"+importValue);
					//将输入参数和值依次放入输入参数list中
					parameterList.setValue(importValue,importName);
				}
				myConnection.execute(bapi);//执行RFC
				//输出表
				Table outTable = bapi.getTableParameterList().getTable(outTableName);
				//System.out.println("返回消息长度"+outTable.getNumRows()+outTable.getRow());
				if (outTable.getNumRows()> 0) {
					for (int i = 0; i < outTable.getNumRows(); i++) {
						Map<String,String> outMap=new HashMap<String, String>();
						outTable.setRow(i);
						//封装行数据到Map中
						for (String name: outNameList) {
							outTable.getString(name);
							outMap.put(name, outTable.getString(name));
						}
						//将行数据的Map放进List
						outList.add(outMap);
					}
				}
				return outList;
			} catch (Exception e) {
				base.writeLog("获取"+RFCName+"函数的执行结果出错："+e);
			}finally{
				SAPUtil.releaseClient(myConnection);
			}
			return outList;
		}
		
		
		/**
		 * 根据RFC函数名称和输入参数list获取RFC执行结果集
		 * @param RFCName RFC函数名称
		 * @param importMap    Map<String,String>对象
		 * @param outTableName SAP输出表的表名
		 * @return 返回输出表的JsonArray,封装的是行数据,里面封装了行数据字段值的JsonObject对象
		 */
		public static JSONArray getSapJsonData(String RFCName,Map<String,String> importMap,String outTableName,List<String> outNameList){
			//System.out.println("连接SAP开始RFC名称："+RFCName+",输入参数列表："+importMap.toString()+",输出表名"+outTableName+",输出数据字段名List:"+outNameList);
			//连接SAP开始RFC名称：ZPS0010,输入参数列表：{I_PSPID=A101604},输出表名ET_PROJECT_COST,输出数据字段名List:[POSID, POST1, USERID, ZYSCB, ZSJCB, ZWGBFB]
			BaseBean base=new BaseBean();//日志对象
			JSONArray outArr=new JSONArray();
			JCO.Client myConnection = null;
			try {
				myConnection = SAPUtil.getSAPcon("3");
				myConnection.connect();// 连接SAP
				//System.out.println("获取SAP链接");
				JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
				IFunctionTemplate ft = myRepository.getFunctionTemplate(RFCName);
				JCO.Function bapi = ft.getFunction();
				JCO.ParameterList  parameterList=bapi.getImportParameterList();
				//迭代器获取Map中的输入参数名称和值
				Iterator<String> it=importMap.keySet().iterator();
				String importName="";
				String importValue="";
				while (it.hasNext()) {
					importName= it.next();
					importValue=importMap.get(importName);
					//System.out.println(importName+":"+importValue);
					//将输入参数和值依次放入输入参数list中
					parameterList.setValue(importValue,importName);
				}
				myConnection.execute(bapi);//执行RFC
				//输出表
				Table outTable = bapi.getTableParameterList().getTable(outTableName);
				//System.out.println("返回消息长度"+outTable.getNumRows()+outTable.getRow());
				if (outTable.getNumRows()> 0) {
					for (int i = 0; i < outTable.getNumRows(); i++) {
						JSONObject outObj=new JSONObject();
						outTable.setRow(i);
						//封装行数据到Map中
						for (String name: outNameList) {
							outTable.getString(name);
							outObj.put(name, outTable.getString(name));
						}
						//将行数据的Map放进List
						outArr.add(outObj);
					}
				}
				return outArr;
			} catch (Exception e) {
				base.writeLog("获取"+RFCName+"函数的执行结果出错："+e);
			}finally{
				SAPUtil.releaseClient(myConnection);
			}
			return outArr;
		}
		
		/**
		 * 根据RFC函数名称和输入参数list获取指定数据源RFC执行结果集
		 * @param RFCName RFC函数名称
		 * @param importMap    Map<String,String>对象
		 * @param outTableName SAP输出表的表名
		 * @param sapSourceid sap数据源id
		 * @return 返回输出表的JsonArray,封装的是行数据,里面封装了行数据字段值的JsonObject对象
		 */
		public static JSONArray getSapJsonData(String RFCName,Map<String,String> importMap,String outTableName,List<String> outNameList,String sapSourceid){
			//System.out.println("连接SAP开始RFC名称："+RFCName+",输入参数列表："+importMap.toString()+",输出表名"+outTableName+",输出数据字段名List:"+outNameList);
			//连接SAP开始RFC名称：ZPS0010,输入参数列表：{I_PSPID=A101604},输出表名ET_PROJECT_COST,输出数据字段名List:[POSID, POST1, USERID, ZYSCB, ZSJCB, ZWGBFB]
			BaseBean base=new BaseBean();//日志对象
			JSONArray outArr=new JSONArray();
			JCO.Client myConnection = null;
			try {
				if(sapSourceid==""||sapSourceid==null){
					myConnection = SAPUtil.getSAPcon("3");
				}else{
					myConnection = SAPUtil.getSAPcon(sapSourceid);
				}
				myConnection.connect();// 连接SAP
				//System.out.println("获取SAP链接");
				JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
				IFunctionTemplate ft = myRepository.getFunctionTemplate(RFCName);
				JCO.Function bapi = ft.getFunction();
				JCO.ParameterList  parameterList=bapi.getImportParameterList();
				//迭代器获取Map中的输入参数名称和值
				Iterator<String> it=importMap.keySet().iterator();
				String importName="";
				String importValue="";
				while (it.hasNext()) {
					importName= it.next();
					importValue=importMap.get(importName);
					//System.out.println(importName+":"+importValue);
					//将输入参数和值依次放入输入参数list中
					parameterList.setValue(importValue,importName);
				}
				myConnection.execute(bapi);//执行RFC
				//输出表
				Table outTable = bapi.getTableParameterList().getTable(outTableName);
				//System.out.println("返回消息长度"+outTable.getNumRows()+outTable.getRow());
				if (outTable.getNumRows()> 0) {
					for (int i = 0; i < outTable.getNumRows(); i++) {
						JSONObject outObj=new JSONObject();
						outTable.setRow(i);
						//封装行数据到Map中
						for (String name: outNameList) {
							outTable.getString(name);
							outObj.put(name, outTable.getString(name));
						}
						//将行数据的Map放进List
						outArr.add(outObj);
					}
				}
				return outArr;
			} catch (Exception e) {
				base.writeLog("获取"+RFCName+"函数的执行结果出错："+e);
			}finally{
				SAPUtil.releaseClient(myConnection);
			}
			return outArr;
		}
		
		
		
		
		
		/**
		 * 根据RFC函数名称和输入参数list获取指定数据源RFC执行结果集
		 * @param RFCName RFC函数名称
		 * @param importMap    Map<String,String>对象
		 * @param outTableName SAP输出表的表名
		 * @param sapSourceid sap数据源id
		 * @return 返回输出表的JsonArray,封装的是行数据,里面封装了行数据字段值的JsonObject对象
		 */
		public static JSONArray getSapJsonData(JSONObject params){
			//System.out.println("连接SAP开始RFC名称："+RFCName+",输入参数列表："+importMap.toString()+",输出表名"+outTableName+",输出数据字段名List:"+outNameList);
			//连接SAP开始RFC名称：ZPS0010,输入参数列表：{I_PSPID=A101604},输出表名ET_PROJECT_COST,输出数据字段名List:[POSID, POST1, USERID, ZYSCB, ZSJCB, ZWGBFB]
			BaseBean log=new BaseBean();//日志对象
			String sapSourceid=params.getString("sapSourceid");
			String RFCName=params.getString("RFCName");
			JSONArray tableArr=params.getJSONArray("tableArr");
			JSONArray outTableNames=params.getJSONArray("outTableNames");
			JSONArray outArr=new JSONArray();
			JCO.Client myConnection = null;
			try {
				if(sapSourceid==""||sapSourceid==null){
					myConnection = SAPUtil.getSAPcon();
				}else{
					myConnection = SAPUtil.getSAPcon(sapSourceid);
				}
				myConnection.connect();// 连接SAP
				JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
				IFunctionTemplate ft = myRepository.getFunctionTemplate(RFCName);
				JCO.Function bapi = ft.getFunction();
/*				JCO.ParameterList  parameterList=bapi.getImportParameterList();
				//迭代器获取Map中的输入参数名称和值
				Iterator<String> it=importMap.keySet().iterator();
				String importName="";
				String importValue="";
				while (it.hasNext()) {
					importName= it.next();
					importValue=importMap.get(importName);
					//System.out.println(importName+":"+importValue);
					//将输入参数和值依次放入输入参数list中
					parameterList.setValue(importValue,importName);
				}*/
				String tablename="";
				JSONObject tableData=null;
				JSONArray rows=null;
				JSONObject row=null;
				Table t=null;
				String key="";
				String v="";
				for (int i = 0; i < tableArr.size(); i++) {
					tableData=tableArr.getJSONObject(i);//获取某个表的json数据
					tablename=tableData.getString("tablename");//获取表名
					t = bapi.getTableParameterList().getTable(tablename);//表对象
					rows=tableData.getJSONArray("rows");//行数据
					for (int j = 0; j < rows.size(); j++) {//循环行数据
						t.appendRow();
						row=rows.getJSONObject(j);
						Iterator<String> it=row.keySet().iterator();
						while (it.hasNext()) {
							key=it.next();
							v=Util.null2String(row.getString(key));
							t.setValue(v, key);
						}
					}
				}
				myConnection.execute(bapi);//执行RFC
				
/*				ParameterList tList= bapi.getTableParameterList();
				for (int i = 0; i < tList.getNumFields(); i++) {
					
				}*/
				Table outTable = null;
/*				for (int i = 0; i < outTableNames.size(); i++) {
					outTable=bapi.getTableParameterList().getTable(outTableNames.getJSONObject(i).getString("tableName"));
					if (outTable.getNumRows()> 0) {
						for (int j = 0; j < outTable.getNumRows(); j++) {
							JSONObject outObj=new JSONObject();
							outTable.setRow(j);
							outTable.getField(j);
							//封装行数据到Map中
							for (String name: outNameList) {
								outTable.getString(name);
								outObj.put(name, outTable.getString(name));
							}
							//将行数据的Map放进List
							outArr.add(outObj);
						}
					}
				}
				//输出表
				Table outTable = bapi.getTableParameterList().getTable(outTableName);
				//System.out.println("返回消息长度"+outTable.getNumRows()+outTable.getRow());
				if (outTable.getNumRows()> 0) {
					for (int i = 0; i < outTable.getNumRows(); i++) {
						JSONObject outObj=new JSONObject();
						outTable.setRow(i);
						//封装行数据到Map中
						for (String name: outNameList) {
							outTable.getString(name);
							outObj.put(name, outTable.getString(name));
						}
						//将行数据的Map放进List
						outArr.add(outObj);
					}
				}*/
				return outArr;
			} catch (Exception e) {
				log.writeLog("获取"+RFCName+"函数的执行结果出错："+e);
			}finally{
				SAPUtil.releaseClient(myConnection);
			}
			return outArr;
		}
		

		
		/**
		 * 获取当前日期yyyy-MM-dd格式
		 * @return
		 */
		public static String getTodayWithLdt(){
			Calendar c1 = Calendar.getInstance();  
	        c1.setTime(new Date()); 
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	        return format.format(c1.getTime());
		}
		
		/**
		 * 获取hh:mm:ss格式的时间
		 * @return
		 */
		public static String getTodayTime(){
			Calendar c1 = Calendar.getInstance();  
	        c1.setTime(new Date()); 
	        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");  
	        return format.format(c1.getTime());
		}
		

		/**
		 * 根据某个字段获取某个表的id 
		 * @param field 条件字段名
		 * @param value	条件字段值
		 * @param tablename 表名
		 * @return 返回数据对应的id
		 */
		public static String getTableIdByField(String field,String value,String tablename){
			String sql="select id from "+tablename+" where "+field+"='"+value+"'";
			//System.out.println("获取主表id："+sql);
			RecordSet rs=new RecordSet();
			String jmid="";
			rs.executeSql(sql);
			if (rs.next()) {
				jmid=rs.getString("id");
			}
			//System.out.println("jmid"+jmid);
			return jmid;
		}

		
		/**
		 * 调用指定的RFC函数，向表中插入数据
		 * @param RFCName SAP函数名称
		 * @param importTableList 表数据
		 * @param importTableName 输入表名称
		 */
		public static void insertSapData(String RFCName,List<Map<String,String>> importTableList,String importTableName){
			BaseBean base=new BaseBean();//日志对象
			JCO.Client myConnection = null;
			try {
				myConnection = SAPUtil.getSAPcon("3");
				myConnection.connect();// 连接SAP
				//System.out.println("获取SAP链接");
				JCO.Repository myRepository = new JCO.Repository("Repository",myConnection);
				IFunctionTemplate ft = myRepository.getFunctionTemplate(RFCName);
				JCO.Function bapi = ft.getFunction();
				//输入表
				JCO.Table importTable = bapi.getTableParameterList().getTable(importTableName);
				Map<String,String> importTableMap=new HashMap<String, String>();
				String importName="";
				String importValue="";
				for (int i = 0; i < importTableList.size(); i++) {
					importTableMap=importTableList.get(i);
					importTable.appendRow();
					Iterator<String> it=importTableMap.keySet().iterator();
					while (it.hasNext()) {
						importName= it.next();
						importValue=importTableMap.get(importName);
						//System.out.println(importName+":"+importValue);
						//将输入参数和值依次放入输入参数list中
						importTable.setValue(importValue,importName);
					}
				}
				myConnection.execute(bapi);
				
			} catch (Exception e) {
				base.writeLog("插入数据："+RFCName+"函数的执行结果出错："+e);
			}finally{
				SAPUtil.releaseClient(myConnection);
			} 
			
		}
		
		
		
		
		
		
		
		
		
		
		public static String getField(String fieldValue){
			String val=("".equals(fieldValue)||fieldValue==null)? "":fieldValue+",";
			return val;
		}
		
		/**
		 * 执行一个sql语句，返回执行结果
		 * @param sql sql语句
		 * @return 返回执行sql是否成功的boolean值
		 */
		public static boolean setFieldValue(String sql){
			RecordSet rs=new RecordSet();
			return rs.executeSql(sql);
		}
		
		
		/**
		 * 去除字符串末尾逗号
		 * @param val
		 * @return
		 */
		public static String getTrimCommaValue(String val){
			if (val.endsWith(",")) {
				return val.substring(0,val.length()-1);
			}
			return val;
		}
		
		
		/**
		 * 去除以逗号分隔的字符串内的重复值,以及空字符串
		 * 例： 1,2,1,21,,,2,3,1,4=> 返回 1,2,21,3,4
		 * @param str
		 * @return
		 */
		public static String removeDuplicateStr(String str){
			String s="";
			if(str!=null && str.length()>0){
				String[] arr=str.split(",");
			 	Set<String> set = new TreeSet<String>();
		        for (String i : arr) {
		            set.add(i);
		        }
		        for (String v : set) {  
		        	if(!"".equals(v)){
		        		s+=v+",";
		        	}
		        }  
			}
			return s.endsWith(",")? s.substring(0,s.length()-1):s;
		}
		
		 /**
		  * 去除字符串的两边空格和最后的逗号
		  * @param str
		  * @return
		  */
		 public static String trimStr(String str){
			 str=str.trim();
			 if(str.endsWith(",")){
				 return str.substring(0,str.length()-1);
			 }else{
				 return str;
			 }
		 }
		
		
		public static String getTime(){
			Calendar c1 = Calendar.getInstance();  
	        c1.setTime(new Date()); 
	        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");  
	        return format.format(c1.getTime());
		}
		/**
		 * java获取根据id获取登录账号
		 */
		public static String getLoginid(String id){
	      String name= "";
	      RecordSet rs= new RecordSet();
	      String sql= "select loginid from hrmresource where id=" +id;
	      rs.execute(sql);
	      rs.next();
	      name=rs.getString("loginid");
	      return name;
		}
		
		
		/**
		 * 根据部门id获取部门名称
		 * @param departmentId
		 * @return
		 */
		public static String getDepartmentNameById(String departmentId){
			 RecordSet rs= new RecordSet();
			 rs.execute("select departmentname from HrmDepartment  where id='"+departmentId+"'");
			 rs.next();
			 return rs.getString("departmentname");
		}
		
		
	
		/**
		 * 根据创建人id，表名，模块id，关键字字段创建一条建模数据，并权限重构
		 * @param tableName
		 * @param creater
		 * @param modeid
		 * @param keyName
		 * @param keyValue
		 * @return
		 */
		public static String createModeDataByTableAndCreater(String tableName,int creater,int modeid,String keyName,String keyValue){
			RecordSet rs=new RecordSet();
			String id="";
			String insertSql="insert into "+tableName+" (formmodeid,modedatacreater,modedatacreatertype,modedatacreatedate,modedatacreatetime,"+keyName+")values('"+modeid+"','"+creater+"','0','"+SAPUtil.getTodayWithLdt()+"','"+CommonUtil.getTime()+"','"+keyValue+"')";
			rs.execute(insertSql);
			id=CommonUtil.getFieldValueFromTableByfield("id", tableName, keyName, keyValue);
			return id;
		}
			
		/**
		 * 获取某个流程节点的操作者
		 * @param nodeid
		 * @param requestid
		 * @param workflowid
		 * @return
		 */
		public static String getNodeOperatorByNodeid(String nodeid,String requestid,String workflowid){
			String operators="";
			RecordSet rs=new RecordSet();
			rs.execute("select t2.id,t2.lastname,t1.nodeid from workflow_currentoperator t1 ,hrmresource t2 where t1.userid=t2.id and t1.workflowid='"+workflowid+"' and t1.requestid='"+requestid+"' and t1.nodeid='"+nodeid+"'");
			while(rs.next()){
				operators+=rs.getString("id")+",";
			}
			return operators.substring(0,operators.length()-1);
		}
		
		/**
		 * 根据流程requestInfo消息对象获取流程主表字段的信息的map集合
		 * @param requestInfo
		 * @return
		 */
		public static HashMap<String,String> getMainTableInfo(RequestInfo requestInfo){
			HashMap<String,String> mainMap = new HashMap<String,String>();
			Property[] properties = requestInfo.getMainTableInfo().getProperty();// 获取表单主字段信息
			for (int i = 0; i < properties.length; i++) {
				String name = properties[i].getName();// 主字段名称
				String value = Util.null2String(properties[i].getValue());// 主字段对应的值
				mainMap.put(name, value);
			}
			return mainMap;
		}

		/**
		 * 获取申请部门
		 * @param workflowId
		 * @return
		 */
		public static String getShenpbm(String value){
			RecordSet rs=new RecordSet();
			rs.execute("select oabm from uf_buoasap where sapbmbh='"+value+"'");
			if(rs.next()){
				return rs.getString("oabm");
			}
			return "0";
		}
		
		
	
}
