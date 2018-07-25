package com.jiuyi.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.jiuyi.base.OA_SAPField;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.weaver.integration.datesource.SAPInterationOutUtil;
import com.weaver.integration.log.LogInfo;

import weaver.conn.RecordSet;
import weaver.formmode.service.FormInfoService;
import weaver.general.BaseBean;
import weaver.general.GCONST;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.workflow.request.RequestInfo;

public class SAPUtil  extends BaseBean{
	
		 private String  sysresult="0";
		/**
		 * 
		 * @return
		 */
		public static JCO.Client  getSAPcon(){
			String datasourceid="1";
			SAPInterationOutUtil sapUtil = new SAPInterationOutUtil();
			JCO.Client  myConnection =null;
			myConnection = (JCO.Client)sapUtil.getConnection(datasourceid, new LogInfo());
			return myConnection;
		}
		/**
		 * 
		 * @return  DEV 200
		 */
		public static JCO.Client  getSAPcon1(){
			String datasourceid="21";
			SAPInterationOutUtil sapUtil = new SAPInterationOutUtil();
			JCO.Client  myConnection =null;
			myConnection = (JCO.Client)sapUtil.getConnection(datasourceid, new LogInfo());
			return myConnection;
		}
		
		/**
		 * 
		 * @return
		 */
		public static JCO.Client  getSAPcon(String  datasourceid){
			
			SAPInterationOutUtil sapUtil = new SAPInterationOutUtil();
			JCO.Client  myConnection =null;
			myConnection = (JCO.Client)sapUtil.getConnection(datasourceid, new LogInfo());
			return myConnection;
		}
		
		/**
		 * 
		 * @return
		 */
		public  static void releaseClient(JCO.Client myConnection){
			if(null!=myConnection){
				JCO.releaseClient(myConnection);
			}
		}
	
		/**
		 * 
		 * @return
		 */
		public static void writeLOG(String s){
			//System.out.println(s);
		}
		/**
		 * 
		 * @return
		 */
		public static void writeLOG(String s,boolean flag){
			new BaseBean().writeLog(s);
		}
	
		public static boolean writeLogToHTML(List li,String filename) {
				boolean flag=false;
				if(null!=li){
					 FileWriter fw=null;
					   try {
						   //�ļ�λ��
						   String rootpath=GCONST.getRootPath()+"shangqi\\"+filename+".html";
						   File file = new File(rootpath);
						   File parent = file.getParentFile(); 
						   if(parent!=null&&!parent.exists()){ 
							   parent.mkdirs(); 
							} 
						   fw= new FileWriter(rootpath, false); //
						   for(int i=0;i<li.size();i++){
							   fw.write(li.get(i)+"<br>");   
						   }
						   fw.write(""+TimeUtil.getTimeString(new Date()));
						   fw.close();
					} catch (IOException e) {
						e.printStackTrace();
						if(null!=fw){
							try {
								fw.close();
							} catch (IOException e1) {
								e.printStackTrace();
							}
						}
					}
				}
				return flag;
		}
		
		public static String getTodayWithLdt() {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(c1.getTime());
		}
		
	
		public   boolean checkSysSap(){
			RecordSet rs=new RecordSet();
			rs.execute("select  count(*) s from shangqisys where cdata='"+TimeUtil.getCurrentDateString()+"'");
			if(rs.next()&&rs.getInt("s")>0){
				return false;
			}
			return true;
		}
		
	
		
		public String parsedata(Object value){
			if (value instanceof Date) {
				return TimeUtil.getDateString((Date)value);
			}else{
				return value+"";
			}
		}

		public String isSysresult() {
			return sysresult;
		}

		public void setSysresult(String sysresult) {
			this.sysresult = sysresult;
		}

		public String dateformat(String datetime){
			SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
		    Date date;
		    String result="";
			try {
				date = sf.parse(datetime);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				result = sdf.format(date); 
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		   
			return result;
			
		}
		
		public static String unescape(String src) {
			StringBuffer tmp = new StringBuffer();
			tmp.ensureCapacity(src.length());
			int lastPos = 0, pos = 0;
			char ch;
			while (lastPos < src.length()) {
				pos = src.indexOf("%", lastPos);
				if (pos == lastPos) {
					if (src.charAt(pos + 1) == 'u') {
						ch = (char) Integer.parseInt(
								src.substring(pos + 2, pos + 6), 16);
						tmp.append(ch);
						lastPos = pos + 6;
					} else {
						ch = (char) Integer.parseInt(
								src.substring(pos + 1, pos + 3), 16);
						tmp.append(ch);
						lastPos = pos + 3;
					}
				} else {
					if (pos == -1) {
						tmp.append(src.substring(lastPos));
						lastPos = src.length();
					} else {
						tmp.append(src.substring(lastPos, pos));
						lastPos = pos;
					}
				}
			}
			return tmp.toString();
		}
		
		  
		  public static String getTablename(weaver.soa.workflow.request.RequestInfo info)
		  {
		    String workflowid = ""+info.getWorkflowid();
		    String formid = "";
		    String sql1 = "select formid from workflow_base where id=" + workflowid;
		    System.out.println(sql1);
		    RecordSet rs = new RecordSet();
		    rs.execute(sql1);
		    if (rs.next()) {
		      formid = rs.getString("formid");
		    }
		    formid = formid.replaceAll("-", "");
		    String tablename = "formtable_main_" + formid;
		    return tablename;
		  }
		  
		  public static List<String> getTablename_dt(String tablename)
		  {
		    String sql = "select name from sysobjects where name like '%" + 
		      tablename + "_dt%'";
		    RecordSet rs = new RecordSet();
		    List<String> list_dt = new ArrayList();
		    rs.execute(sql);
		    while (rs.next())
		    {
		      rs.getString("name");
		      list_dt.add(rs.getString("name"));
		    }
		    return list_dt;
		  }
		  
		  public static String getTablenameBywfid(String workflowid)
		  {
		    String formid = "";
		    String sql1 = "select formid from workflow_base where id=" + workflowid;
		    
		    RecordSet rs = new RecordSet();
		    rs.execute(sql1);
		    if (rs.next()) {
		      formid = rs.getString("formid");
		    }
		    formid = formid.replaceAll("-", "");
		    String tablename = "formtable_main_" + formid;
		    return tablename;
		  }
		  
		  public static String changeCurrency(String str)
		  {
		    if (str.equals("RMB")) {
		      return "CNY";
		    }
		    return str;
		  }
		  
		  public static Map<String, Object> getFunctionByRFCName(String rfcName)
		  {
		    Map<String, Object> m = new HashMap();
		    try
		    {
		      JCO.Client myConnection = getSAPConnection();
		      JCO.Repository myRepository = new JCO.Repository("Repository", myConnection);
		      IFunctionTemplate ft = myRepository.getFunctionTemplate(rfcName);
		      JCO.Function bapi = ft.getFunction();
		      m.put("conn", myConnection);
		      m.put("bapi", bapi);
		      return m;
		    }
		    catch (Exception e)
		    {
		      e.printStackTrace();
		    }
		    return m;
		  }
		  
		  public static JCO.Client getSAPConnection()
		  {
		    JCO.Client myConnection = null;
		    try
		    {
		      myConnection = getSAPcon();
		      myConnection.connect();
		      return myConnection;
		    }
		    catch (Exception e)
		    {
		      e.printStackTrace();
		    }
		    return myConnection;
		  }
		  
		  public static List<HashMap<String, String>> getSAPImportList(JCO.Function bapi)
		  {
		    List<HashMap<String, String>> importList = new ArrayList();
		    HashMap<String, String> map = new HashMap();
		    JCO.ParameterList importParams = bapi.getImportParameterList();
		    if ((importParams != null) && (importParams.getNumFields() > 0))
		    {
		      JCO.FieldIterator fields = importParams.fields();
		      if (fields != null) {
		        while (fields.hasMoreFields())
		        {
		          map = new HashMap();
		          JCO.Field field = fields.nextField();
		          String paramName = field.getName();
		          paramName = paramName == null ? "" : paramName;
		          String paramDesc = field.getDescription();
		          paramDesc = paramDesc == null ? "" : paramDesc;
		          map.put("paramName", paramName);
		          map.put("paramDesc", paramDesc);
		          if (field.isStructure()) {
		            map.put("paramType", "0");
		          } else if (field.isTable()) {
		            map.put("paramType", "1");
		          } else {
		            map.put("paramType", "2");
		          }
		          importList.add(map);
		        }
		      }
		    }
		    return importList;
		  }
		  
		  public static List<HashMap<String, String>> getSAPExportList(JCO.Function bapi)
		  {
		    List<HashMap<String, String>> exportList = new ArrayList();
		    HashMap<String, String> map = new HashMap();
		    JCO.ParameterList exportParams = bapi.getExportParameterList();
		    if ((exportParams != null) && (exportParams.getNumFields() > 0))
		    {
		      JCO.FieldIterator fields = exportParams.fields();
		      if (fields != null) {
		        while (fields.hasMoreFields())
		        {
		          map = new HashMap();
		          JCO.Field field = fields.nextField();
		          String paramName = field.getName();
		          paramName = paramName == null ? "" : paramName;
		          String paramDesc = field.getDescription();
		          paramDesc = paramDesc == null ? "" : paramDesc;
		          map.put("paramName", paramName);
		          map.put("paramDesc", paramDesc);
		          if (field.isStructure()) {
		            map.put("paramType", "0");
		          } else if (field.isTable()) {
		            map.put("paramType", "1");
		          } else {
		            map.put("paramType", "2");
		          }
		          exportList.add(map);
		        }
		      }
		    }
		    return exportList;
		  }
		  
		  public static List<HashMap<String, String>> getSAPTableList(JCO.Function bapi)
		  {
		    List<HashMap<String, String>> tableList = new ArrayList();
		    HashMap<String, String> map = new HashMap();
		    JCO.ParameterList tableParams = bapi.getTableParameterList();
		    if ((tableParams != null) && (tableParams.getNumFields() > 0))
		    {
		      JCO.FieldIterator fields = tableParams.fields();
		      if (fields != null) {
		        while (fields.hasMoreFields())
		        {
		          map = new HashMap();
		          JCO.Field field = fields.nextField();
		          String paramName = field.getName();
		          paramName = paramName == null ? "" : paramName;
		          String paramDesc = field.getDescription();
		          paramDesc = paramDesc == null ? "" : paramDesc;
		          map.put("paramName", paramName);
		          map.put("paramDesc", paramDesc);
		          map.put("paramType", "1");
		          tableList.add(map);
		        }
		      }
		    }
		    return tableList;
		  }
		  
		  public static List<HashMap<String, String>> getParamListByTableName(JCO.Function bapi, String tableName)
		  {
		    List<HashMap<String, String>> paramList = new ArrayList();
		    HashMap<String, String> map = new HashMap();
		    JCO.Table table = bapi.getTableParameterList().getTable(tableName);
		    if ((table != null) && (table.getNumFields() > 0))
		    {
		      JCO.FieldIterator fields = table.fields();
		      if (fields != null) {
		        while (fields.hasNextFields())
		        {
		          map = new HashMap();
		          JCO.Field field = fields.nextField();
		          String paramName = field.getName();
		          field.getType();
		          paramName = paramName == null ? "" : paramName;
		          String paramDesc = field.getDescription();
		          paramDesc = paramDesc == null ? "" : paramDesc;
		          map.put("paramName", paramName);
		          map.put("paramDesc", paramDesc);
		          if (field.isStructure()) {
		            map.put("paramType", "0");
		          } else if (field.isTable()) {
		            map.put("paramType", "1");
		          } else {
		            map.put("paramType", "2");
		          }
		          paramList.add(map);
		        }
		      }
		    }
		    return paramList;
		  }
		  
		  public static HashMap<String, List<HashMap<String, String>>> getAllTablesData(JCO.Function bapi, List<HashMap<String, String>> tableList, JCO.Client myConnection)
		  {
		    HashMap<String, List<HashMap<String, String>>> tablesData = new HashMap();
		    String tableName = "";
		    for (int i = 0; i < tableList.size(); i++)
		    {
		      tableName = (String)((HashMap)tableList.get(i)).get("paramName");
		      tablesData.put(tableName, getFieldsValueByParamList(bapi, getParamListByTableName(bapi, tableName), tableName, myConnection));
		    }
		    return tablesData;
		  }
		  
		  public static List<HashMap<String, String>> getFieldsValueByParamList(JCO.Function bapi, List<HashMap<String, String>> fieldList, String tableName, JCO.Client myconnection)
		  {
		    myconnection.execute(bapi);
		    JCO.Table table = bapi.getTableParameterList().getTable(tableName);
		    List<HashMap<String, String>> rows = new ArrayList();
		    String paramName = "";
		    HashMap<String, String> map = new HashMap();
		    if (table.getNumRows() > 0) {
		      for (int i = 0; i < table.getNumRows(); i++)
		      {
		        table.setRow(i);
		        map = new HashMap();
		        for (int j = 0; j < fieldList.size(); j++)
		        {
		          paramName = (String)((HashMap)fieldList.get(j)).get("paramName");
		          map.put(paramName, Util.null2String(table.getString(paramName)));
		        }
		        rows.add(map);
		      }
		    }
		    return rows;
		  }
		  
		  public static void getSAPData(String rfcName)
		  {
		    Map<String, Object> m = new HashMap();
		    m = getFunctionByRFCName(rfcName);
		    
		    JCO.Function bapi = (JCO.Function)m.get("bapi");
		    JCO.Client myconnection = (JCO.Client)m.get("conn");
		    
		    List<HashMap<String, String>> importList = getSAPImportList(bapi);
		    
		    List<HashMap<String, String>> exportList = getSAPExportList(bapi);
		    
		    List<HashMap<String, String>> tableList = getSAPTableList(bapi);
		  }
		  
		  public static String getRfcName(String billid)
		  {
		    RecordSet rs = new RecordSet();
		    rs.executeSql("select hsmc from uf_osap where id = " + billid);
		    if (rs.next()) {
		      return rs.getString(1);
		    }
		    return "";
		  }
		  
		  public static String getSapdatasource(String billid)
		  {
		    RecordSet rs = new RecordSet();
		    rs.executeSql("select sapdatasource from uf_osap where id = " + billid);
		    if (rs.next()) {
		      return rs.getString(1);
		    }
		    return "";
		  }
		  
		  public static String getSAPType(String billid)
		  {
		    RecordSet rs = new RecordSet();
		    rs.executeSql("select type from uf_osap where id = " + billid);
		    if (rs.next()) {
		      return rs.getString(1);
		    }
		    return "";
		  }
		  
		  public static String getSAPMessage(String billid)
		  {
		    RecordSet rs = new RecordSet();
		    rs.executeSql("select message from uf_osap where id = " + billid);
		    if (rs.next()) {
		      return rs.getString(1);
		    }
		    return "";
		  }
		  
		  public static Map<String, List<OA_SAPField>> getSAPTableOrStructure(String action, String lx, String inOrout)
		  {
		    Map<String, List<OA_SAPField>> map = new HashMap();
		    RecordSet rs = new RecordSet();
		    String tablename = "";
		    if ("0".equals(inOrout)) {
		      tablename = "uf_OSAP_dt1";
		    } else {
		      tablename = "uf_OSAP_dt2";
		    }
		    String sql = "select LEFT(sapmc,charindex('.',sapmc)),sapmc,bm,oamc,mrz,zhsl from " + tablename + " where mainid in(select id from uf_osap where id = " + action + ") and lx = " + lx + " order by id";
		    rs.executeSql(sql);
		    while (rs.next())
		    {
		      String key = rs.getString(1);
		      key = key.substring(0, key.length() - 1);
		      OA_SAPField osap = new OA_SAPField();
		      String sapmc = rs.getString("sapmc");
		      osap.setSapmc(sapmc.substring(sapmc.indexOf(".") + 1, sapmc.length()));
		      osap.setBm(rs.getString("bm"));
		      osap.setOamc(rs.getString("oamc"));
		      osap.setMrz(rs.getString("mrz"));
		      osap.setZhsl(rs.getString("zhsl"));
		      if (!checkKey(map, key))
		      {
		        List<OA_SAPField> list = new ArrayList();
		        list.add(osap);
		        map.put(key, list);
		      }
		      else
		      {
		        List<OA_SAPField> list = (List)map.get(key);
		        list.add(osap);
		      }
		    }
		    return map;
		  }
		  
		  public static boolean checkKey(Map<String, List<OA_SAPField>> map, String str)
		  {
		    Iterator keys = map.keySet().iterator();
		    while (keys.hasNext())
		    {
		      String key = (String)keys.next();
		      if (str.equals(key)) {
		        return true;
		      }
		    }
		    return false;
		  }
		  
		  public static List<OA_SAPField> getSAPParameter(String action, String inOrout)
		  {
		    RecordSet rs = new RecordSet();
		    String tablename = "";
		    if ("0".equals(inOrout)) {
		      tablename = "uf_OSAP_dt1";
		    } else {
		      tablename = "uf_OSAP_dt2";
		    }
		    List<OA_SAPField> list = new ArrayList();
		    String sql = "select LEFT(sapmc,charindex('.',sapmc)),sapmc,bm,oamc,mrz,zhsl from " + tablename + " where mainid in(select id from uf_osap where id = " + action + ") and lx = 0";
		    rs.executeSql(sql);
		    while (rs.next())
		    {
		      OA_SAPField osap = new OA_SAPField();
		      String sapmc = rs.getString("sapmc");
		      osap.setSapmc(sapmc.substring(sapmc.indexOf(".") + 1, sapmc.length()));
		      osap.setBm(rs.getString("bm"));
		      osap.setOamc(rs.getString("oamc"));
		      osap.setMrz(rs.getString("mrz"));
		      osap.setZhsl(rs.getString("zhsl"));
		      list.add(osap);
		    }
		    return list;
		  }
		  
		  public static String getModeTableBymodeid(String modeid)
		  {
		    RecordSet rs = new RecordSet();
		    FormInfoService formInfoService = new FormInfoService();
		    rs.executeSql("select formid from modeinfo where id=" + modeid);
		    rs.next();
		    int formid = rs.getInt(1);
		    String tablename = formInfoService.getTablenameByFormid(formid);
		    return tablename;
		  }
		
		
}
