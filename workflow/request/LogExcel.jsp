<%@ page language="java" contentType="application/vnd.ms-excel; charset=gbk" %>
<%@ page import="weaver.systeminfo.*" %>
<%@ page import="weaver.hrm.*" %>
<%@ page import="weaver.general.Util" %>
<%@page import="weaver.general.*" %>
<%@page import="weaver.general.Util"%>

<jsp:useBean id="SysMaintenanceLog" class="weaver.systeminfo.SysMaintenanceLog" scope="page" />
<jsp:useBean id="ResourceComInfo" class="weaver.hrm.resource.ResourceComInfo" scope="page" />
<jsp:useBean id="DepartmentComInfo" class="weaver.hrm.company.DepartmentComInfo" scope="page" />
<jsp:useBean id="companyInfo" class="weaver.hrm.company.CompanyComInfo" scope="page" />
<jsp:useBean id="SubCompanyComInfo" class="weaver.hrm.company.SubCompanyComInfo" scope="page" />
<jsp:useBean id="RecordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="SptmForCowork" class="weaver.splitepage.transform.SptmForCowork" scope="page" />
<jsp:useBean id="SptmForLog" class="weaver.splitepage.transform.SptmForLog" scope="page" />
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
<!--
td{font-size:12px}
.title{font-weight:bold;font-size:20px}
-->
</style>
<%
new BaseBean().writeLog("---------------------MM模块报表查询---------------------------");
String kaisrq = Util.null2String(request.getParameter("kaisrq"));
String jiesrq = Util.null2String(request.getParameter("jiesrq"));
new BaseBean().writeLog("kaisrq:"+kaisrq);
new BaseBean().writeLog("jiesrq:"+jiesrq);


response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-disposition","attachment;filename=logexcel.xls");
User user = HrmUserVarify.getUser (request , response) ;
if(user == null)  return ;
String sql="";
String sql1 = "";
String sqlwhere = Util.null2String(request.getParameter("sqlwhere"));
boolean isLogView = HrmUserVarify.checkUserRight("LogView:View", user);
// 如果是登录日志查看，需要检查是否有登录日志查看的权限 （刘煜修改）
//if( (sqlwhere.equals("")||(sqlwhere.indexOf("operateitem=60")>=0)) && !HrmUserVarify.checkUserRight("LogView:View", user))  {
  //  response.sendRedirect("/notice/noright.jsp") ;
    //return ;
//}
%>
  <table class=ListStyle border="1">

  <tr class=Header>
  <th width=20%>流程编号</th>
  <th width=55%>物料编码</th>
  <th width=60%>物料描述</th>
  <th width=60%>行项目号</th>
  <th width=20%>工厂描述</th>
  <th width=20%>创建日期</th>
  <th width=20%>评估流程追溯号</th>
  <th width=20%>评估流程归档日期</th>
  <th width=20%>评估流程归档时间</th>
  </tr>
   
  
  <%
				String tbName = "formtable_main_174 a left join formtable_main_174_dt1 b on a.id=b.mainid left join workflow_requestbase c on a.requestid = c.requestid";
				sql = "select * from "+tbName+" where c.status='归档' and c.workflowid=560 and (c.createdate between '"+kaisrq+"' and '"+jiesrq+"') order by b.matnr ";//数据按照物料分类
				new BaseBean().writeLog("sql====="+sql); 
				RecordSet.execute(sql);
				while(RecordSet.next()){
					String ly = RecordSet.getString("lcbh")+"-"+RecordSet.getString("BNFPO");
					sql1 = "select * from "+tbName+" where c.workflowid=591 and b.ly='"+ly+"'";
					new BaseBean().writeLog("sql1====="+sql1); 
					rs1.execute(sql1);
					if(rs1.next()){
						
					}else{%>
					  <tr>
					  <td><%out.print(RecordSet.getString("lcbh"));%></td>
					  <td><%out.print(RecordSet.getString("matnr"));%></td>
					  <td><%out.print(RecordSet.getString("maktx"));%></td>
					  <td><%out.print(RecordSet.getString("BNFPO"));%></td>
					  <td><%out.print(RecordSet.getString("name1"));%></td>
					  <td><%out.print(RecordSet.getString("cjrq"));%></td>
					  <td><%out.print(RecordSet.getString("ly"));%></td>
					  <td><%out.print(RecordSet.getString("lastoperatedate"));%></td>
					  <td><%out.print(RecordSet.getString("lastoperatetime"));%></td>
					  </tr>
					<%}
				}
%>

</table>





  



   
