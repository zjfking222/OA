<%@page import="com.jiuyi.util.CommonUtil"%>
<%@page import="weaver.general.*"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
//MM模块报表查询
new BaseBean().writeLog("MM模块报表查询");
int formid=Util.getIntValue(request.getParameter("formid"));

HashMap<String, String> map=CommonUtil.getFieldId(formid, "0");//主表

String  kaisrq=  map.get("kaisrq");//开始日期
String  jiesrq=  map.get("jiesrq");//结束日期


%>

<script type="text/javascript">
/*****************主表********************/
var kaisrq = '<%=kaisrq%>';//开始日期
var jiesrq = '<%=jiesrq%>';//结束日期


jQuery(function(){
	//alert("开始日期="+kaisrq.val());
	//alert("结束日期="+jiesrq.val());
	addbutton();
});

//添加查询按钮
function addbutton(){
	var button = "<button id='btn_cx1'  onclick='btncx();' class='btn_cx e8_btn_top_first' type='button'>查询</button>";
	var $btn_cx=jQuery("#btn_cx");
	$btn_cx.append(button);
}


//按钮单击事件
var btncx =  function(){
	var kaisrq1 = $("#field"+kaisrq+"span").text();
	var jiesrq1 = $("#field"+jiesrq+"span").text();
	window.location.replace("/workflow/request/LogExcel.jsp?kaisrq="+kaisrq1+"&jiesrq="+jiesrq1);
	//alert(2);
	//alert("开始日期="+$("#field"+kaisrq+"span").text());
	//alert("结束日期="+$("#field"+jiesrq+"span").text());
}


function setFMVal(id,v,h){
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

</script>
