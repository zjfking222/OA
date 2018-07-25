<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="weaver.general.*" %>
<%@page import="java.util.*" %>
<%@page import="com.jiuyi.util.JiuyiUtil" %>
<%
//HR销假无账号流程
	int requestid = Util.getIntValue(request.getParameter("requestid"));//请求id
	int workflowid = Util.getIntValue(request.getParameter("workflowid"));//流程id
	int formid = Util.getIntValue(request.getParameter("formid"));//表单id
	int isbill = Util.getIntValue(request.getParameter("isbill"));//表单类型，1单据，0表单
	int nodeid = Util.getIntValue(request.getParameter("nodeid"));//流程的节点id
	request.getParameterMap();
	//System.out.println("formid:"+formid);
	Map map = new HashMap();
	if(formid!=-1){
	map =JiuyiUtil.getFieldId(formid,"0");//主表数据
	String yuangbh = map.get("yuangbh").toString();//员工编号
	String xjksrq = map.get("xjksrq").toString();//销假开始日期
	String xjkssj = map.get("xjkssj").toString();//销假开始时间
	String xjjsrq = map.get("xjjsrq").toString();//销假结束日期
	String xjjssj = map.get("xjjssj").toString();//销假结束时间
	String leix = map.get("leix").toString();//销假类型
	String nianxjde = map.get("nianxjde").toString();//年休假定额
	String shengyxss = map.get("shengyxss").toString();//剩余小时数
%>
<!--  <SCRIPT language="javascript" src="/js/weaver.js"></script>-->
<script type="text/javascript">
var ismandStr = "<img src='/images/BacoError.gif' align='absmiddle'>";
var yuangbh='<%=yuangbh%>';//员工编码
var xjksrq='<%=xjksrq%>';//销假开始日期
var xjkssj='<%=xjkssj%>';//销假开始时间
var xjjsrq='<%=xjjsrq%>';//销假结束日期
var xjjssj='<%=xjjssj%>';//销假结束时间
var leix='<%=leix%>';//类型
var nianxjde='<%=nianxjde%>';//年休假定额
var shengyxss='<%=shengyxss%>';//剩余小时数
var workflowid='<%=workflowid%>';//工作流id

var requestid='<%=requestid%>';


jQuery(function(){
	jQuery("#field"+leix).bindPropertyChange(function() {
		getShis();
	});
	jQuery("#field"+yuangbh).bindPropertyChange(function() {
		getShis();
	});
	jQuery("#field"+xjksrq).bindPropertyChange(function() {
		getShis();
	});
	jQuery("#field"+xjkssj).bindPropertyChange(function() {
		getShis();
	});

	jQuery("#field"+xjjsrq).bindPropertyChange(function() {
		getShis();
	});
	jQuery("#field"+xjjssj).bindPropertyChange(function() {
		getShis();
	});
});
function getShis(){
	 var h_yuangbh = jQuery("#field"+yuangbh).val();
	 var h_leix = jQuery("#field"+leix+" option:selected").text();
     var h_xjksrq = jQuery("#field"+xjksrq).val();
     var h_xjkssj = jQuery("#field"+xjkssj).val();
     var h_xjjsrq = jQuery("#field"+xjjsrq).val();
     var h_xjjssj = jQuery("#field"+xjjssj).val();
     
     var h_leix = jQuery("#field"+leix+" option:selected").text();
   	jQuery.ajax({
    		url:"/ajaxOperation/GetTime.jsp",
  	 		async:false,
  	 		type:"post",
  	 		data:{"operation":"time","h_yuangbh":h_yuangbh,"h_ksrq":h_xjksrq,"h_kssj":h_xjkssj,"h_jsrq":h_xjjsrq,"h_jssj":h_xjjssj,"h_leix":h_leix},
  	 		success:function(data){
  	 			eval("var obj="+data);
  				setFMVal(nianxjde,obj.nianxjde);//年休假定额
  				setFMVal(shengyxss,obj.shengyxss);//剩余小时数
    			//alert('欠款结束');
    	 	},
    		error:function(data){
  	 			alert("读取出错，请联系系统管理员");
  	 		}
  		}); 
    	
}

function setFMVal(id,v,h)
		{
			var ismandStr = "<img src='/images/BacoError.gif' align='absmiddle'>";			
			var x= jQuery('#field'+id);
					if(x.length > 0){
						x.attr({'value':v});
						if(x.attr('type') == 'hidden' || document.getElementById('field'+id).style.display == 'none'){
							jQuery('#field'+id+'span').html('');
							if(arguments.length>2){
								jQuery('#field'+id+'span').html(h);
							}else{
								jQuery('#field'+id+'span').html(v);
							}
							
						}else{
							var viewtype = x.attr('viewtype');
							if(viewtype == 1 && (!v || v == '')){
								jQuery('#field'+id+'span').html(ismandStr);
							}else{
								jQuery('#field'+id+'span').html('');
							}
						}
					}	
		}
//不弹出确认框即可删除
function de(){
   return true;
 } 

</script>
<%}%>