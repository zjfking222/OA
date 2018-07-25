<%@page import="com.jiuyi.util.CommonUtil"%>
<%@page import="weaver.general.*"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
//物料停用流程  读取BPC模版接口js 
new BaseBean().writeLog("物料停用流程  读取BPC模版接口js ");
int formid=Util.getIntValue(request.getParameter("formid"));
String nodeid=Util.null2String(request.getParameter("nodeid"));
String requestid=Util.null2String(request.getParameter("requestid"));
String workflowid=Util.null2String(request.getParameter("workflowid"));

HashMap<String, String> map=CommonUtil.getFieldId(formid, "0");//主表
String MATNR =  map.get("matnr");//物料编码

HashMap<String, String> map1 = CommonUtil.getFieldId(formid,"1");//明细1
//String KOINH = map1.get("KOINH");//物料编码
String WERKS = map1.get("werks");//工厂
String LVOWK = map1.get("lvowk");//工厂删除标记

HashMap<String, String> map2 = CommonUtil.getFieldId(formid,"2");//明细2
String LGORT = map2.get("lgort");//库存地点
String LVOLG = map2.get("lvolg");//库存地点删除标记

HashMap<String, String> map3 = CommonUtil.getFieldId(formid,"3");//明细3
String VKORG = map3.get("vkorg");//销售组织
String VTWEG = map3.get("vtweg");//分销渠道
String LVOVK = map3.get("lvovk");//销售删除标记




%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">
var MATNR1 = '<%=MATNR%>';//物料编码
/*****************明细1********************/
//var KOINH = '<%=MATNR%>';//物料编码
var WERKS = '<%=WERKS%>';//工厂
var LVOWK = '<%=LVOWK%>';//工厂删除标记
/*****************明细2********************/
var LGORT = '<%=LGORT%>';//库存地点
var LVOLG = '<%=LVOLG%>';//库存地点删除标记
/*****************明细3********************/
var VKORG = '<%=VKORG%>';//销售组织
var VTWEG = '<%=VTWEG%>';//分销渠道
var LVOVK = '<%=LVOVK%>';//销售删除标记


jQuery(function(){
	//绑定监听
	bindchange();//绑定监听
});

//项目编号绑监听
function bindchange(){
	jQuery("#field"+MATNR1).bindPropertyChange(function(){
		var MATNR = jQuery("#field"+MATNR1).val();
		setTimeout(function(){
			setDetailData(MATNR);
		},500);
	});
}

//添加明细行数据
function setDetailData(MATNR){
	jQuery.ajax({
		url:"/workflow/request/GetSAPDataAjax539.jsp",
		type:"post",
		data:{"MATNR":MATNR},
		async: true,
		success:function(data){
			var mes=eval('('+data+')');
			var dt1Data=mes.dt1;
			setDt1Data(dt1Data);
			var dt2Data=mes.dt2;
			setDt2Data(dt2Data);
			var dt3Data=mes.dt3;
			setDt3Data(dt3Data);
		},
		error:function(e){
			console.log(e);
			alert("错误"+e);
		}
	});	
}

//明细1赋值   开票信息
function setDt1Data(dt1Data){
	var lineData;
	deleteRow(0);
	for(var i=0;i<dt1Data.length;i++){
		lineData=dt1Data[i];
		addRow0(0);
		//setFMVal(KOINH+"_"+i, lineData.KOINH);//物料编码
		setFMVal(WERKS+"_"+i, lineData.WERKS);//工厂
		setFMVal(LVOWK+"_"+i, lineData.LVOWK);//工厂删除标记
	}
} 

//明细2赋值 公司信息信息
function setDt2Data(dt2Data){
	var lineData;
	deleteRow(1);
	for(var i=0;i<dt2Data.length;i++){
		lineData=dt2Data[i];
		addRow1(1);
		setFMVal(LGORT+"_"+i, lineData.LGORT);//库存地点
		setFMVal(LVOLG+"_"+i, lineData.LVOLG);//库存地点删除标记
	}
} 

//明细3赋值 送货信息
function setDt3Data(dt3Data){
	var lineData;
	deleteRow(2);
	for(var i=0;i<dt3Data.length;i++){
		lineData=dt3Data[i];
		addRow2(2);
		setFMVal(VKORG+"_"+i, lineData.VKORG);//销售组织
		setFMVal(VTWEG+"_"+i, lineData.VTWEG);//分销渠道
		setFMVal(LVOVK+"_"+i, lineData.LVOVK);//销售删除标记
	}
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

function deleteRow(groupid){
	if(jQuery('#indexnum'+groupid).val()>0){
		try{
			jQuery("input[type='checkbox'][name='check_node_"+groupid+"']").each(function(){
				jQuery(this).attr({'checked':'checked'});
			});
			delRowFun(groupid,true);
			jQuery('#indexnum'+groupid).val(0);
		}catch(e){}
	}
}
</script>
