<%@page import="com.jiuyi.util.CommonUtil"%>
<%@page import="weaver.hrm.HrmUserVarify"%>
<%@page import="weaver.hrm.User"%>
<%@page import="weaver.general.Util"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%   
User user=HrmUserVarify.getUser(request, response);
int userid=user.getUID();
String name=user.getLastname();
int formid=Util.getIntValue(request.getParameter("formid"));
String nodeid=Util.null2String(request.getParameter("nodeid"));
String nodetype=Util.null2String(request.getParameter("nodetype"));
String requestid=Util.null2String(request.getParameter("requestid"));
String workflowid=Util.null2String(request.getParameter("workflowid"));
HashMap<String, String> map=CommonUtil.getFieldId(formid, "0");//主表
String ksrq =  map.get("ksrq");//开始日期
String jsrq =  map.get("jsrq");//结束日期
String xqzxm =  map.get("xqzxm");//需求者/请求者姓名 
String WERKS =  map.get("werks1");//工厂
String KOSTL =  map.get("kostl1");//成本中心
String MATNR =  map.get("matnr1");//物料编码
String wzlb =  map.get("wzlb");//物资类别


HashMap<String, String> map1 = CommonUtil.getFieldId(formid,"1");//明细1
String KNTTP = map1.get("knttp");//科目分配类别
String EPSTP = map1.get("EPSTP");//项目类别
String MATNR1 = map1.get("matnr");//物料编码
String MAKTX = map1.get("maktx");//物料描述
String MENGE = map1.get("menge");//数量
String MEINS = map1.get("meins");//单位
String PREIS = map1.get("preis");//价格
String WAERS = map1.get("waers");//货币码 
String xj = map1.get("xj");//小计金额
String MATKL = map1.get("matkl");//物料组 
String WERKS1 = map1.get("werks");//工厂
String LGORT = map1.get("lgort");//库存地点
String EKGRP = map1.get("ekgrp");//采购组 
String AFNAM = map1.get("afnam");//需求者/请求者姓名 
String BEDNR = map1.get("bednr");//需求跟踪号
String KOSTL1 = map1.get("kostl");//成本中心
String ANLN1 = map1.get("anln1");//主资产号
String AUFNR = map1.get("aufnr");//订单号 
String LFDAT = map1.get("lfdat");//交货日期
String gysHzqr = map1.get("gysHzqr");//供应商或债权人的帐号
String cjrq = map1.get("cjrq");//创建日期
String kc = map1.get("kc");//库存
String TEXT_LINE = map1.get("text_line");//备注
String ydmxId = map1.get("ydmxId");//月度明细id

%>
<style>
.btn_dqwclxx {
    padding-left: 10px !important;
    padding-right: 10px !important;
    height: 23px;
    line-height: 23px;
    color: #9b5639;
    background-color: #9b5639;
    vertical-align: middle;
    
    margin-left: 0px;
    margin-bottom: 0px;
    width: 80px;
}
</style>

<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">

/*****************主表********************/
var ksrq = '<%=ksrq%>';//开始日期
var jsrq = '<%=jsrq%>';//结束日期
var xqzxm = '<%=xqzxm%>';//需求者/请求者姓名 
var WERKS = '<%=WERKS%>';//工厂
var KOSTL = '<%=KOSTL%>';//成本中心
var MATNR = '<%=MATNR%>';//物料编码
var wzlb = '<%=wzlb%>';//物资类别
/*****************明细1********************/
var KNTTP = '<%=KNTTP%>';//科目分配类别
var EPSTP = '<%=EPSTP%>';//项目类别
var MATNR1 = '<%=MATNR1%>';//物料编码
var MAKTX = '<%=MAKTX%>';//物料描述
var MENGE = '<%=MENGE%>';//数量
var MEINS = '<%=MEINS%>';//单位
var PREIS = '<%=PREIS%>';//价格
var WAERS = '<%=WAERS%>';//货币码 
var xj = '<%=xj%>';//小计金额
var MATKL = '<%=MATKL%>';//物料组 
var WERKS1 = '<%=WERKS1%>';//工厂
var LGORT = '<%=LGORT%>';//库存地点
var EKGRP = '<%=EKGRP%>';//采购组 
var AFNAM = '<%=AFNAM%>';//需求者/请求者姓名 
var BEDNR = '<%=BEDNR%>';//需求跟踪号
var KOSTL1 = '<%=KOSTL1%>';//成本中心
var ANLN1 = '<%=ANLN1%>';//主资产号
var AUFNR = '<%=AUFNR%>';//订单号 
var LFDAT = '<%=LFDAT%>';//交货日期
var gysHzqr = '<%=gysHzqr%>';//供应商或债权人的帐号
var cjrq = '<%=cjrq%>';//创建日期
var kc = '<%=kc%>';//库存
var TEXT_LINE = '<%=TEXT_LINE%>';//备注
var ydmxId = '<%=ydmxId%>';//月度明细id


jQuery(function(){
	
	addbutton();
	
}

//添加按钮
function addbutton(){
	var button = "<button id='btn_sqbx1' onclick='btndqwclxx();' class='btn_dqwclxx' type='button'>读取未处理信息</button>";
	var $btn_sqbx=jQuery("#btn_sqbx");
	$btn_sqbx.append(button);
}


//按钮单击事件
var btndqwclxx =  function(){
	
	jQuery.ajax({
		url:"/workflow/request/HygyIncludeWorkflow562Operation.jsp",
		type:"post",
		data:{"ksrq":ksrq,"jsrq":jsrq,"xqzxm":xqzxm,"wzlb":wzlb,"WERKS":WERKS,"KOSTL":KOSTL,"MATNR":MATNR},
		async: true,
		success:function(data){
			var mes=eval('('+data+')');
			var dt1Data=mes.dt1;
			setDt1Data(dt1Data);
		},
		error:function(e){
			console.log(e);
			alert("错误"+e);
		}
	});	
}
//明细1赋值   基础建设 
function setDt1Data(dt1Data){
	var lineData;
	deleteRow(0);
	for(var i=0;i<dt1Data.length;i++){
		lineData=dt1Data[i];
		addRow0(0);
		setFMVal(KNTTP+"_"+i, lineData.KNTTP);//科目分配类别
		setFMVal(EPSTP+"_"+i, lineData.EPSTP);//项目类别
		setFMVal(MATNR1+"_"+i, lineData.MATNR);//物料编码
		setFMVal(MAKTX+"_"+i, lineData.MAKTX);//物料描述
		setFMVal(MENGE+"_"+i, lineData.MENGE);//数量
		setFMVal(MEINS+"_"+i, lineData.MEINS);//单位
		setFMVal(PREIS+"_"+i, lineData.PREIS);//价格
		setFMVal(WAERS+"_"+i, lineData.WAERS);//货币码 
		setFMVal(xj+"_"+i, lineData.xj);//小计金额
		setFMVal(MATKL+"_"+i, lineData.MATKL);//物料组 
		setFMVal(WERKS1+"_"+i, lineData.WERKS);//工厂
		setFMVal(LGORT+"_"+i, lineData.LGORT);//库存地点
		setFMVal(EKGRP+"_"+i, lineData.EKGRP);//采购组 
		setFMVal(AFNAM+"_"+i, lineData.AFNAM);//需求者/请求者姓名 
		setFMVal(BEDNR+"_"+i, lineData.BEDNR);//需求跟踪号
		setFMVal(KOSTL1+"_"+i, lineData.KOSTL);//成本中心
		setFMVal(ANLN1+"_"+i, lineData.ANLN1);//主资产号
		setFMVal(AUFNR+"_"+i, lineData.AUFNR);//订单号 
		setFMVal(LFDAT+"_"+i, lineData.LFDAT);//交货日期
		setFMVal(gysHzqr+"_"+i, lineData.gysHzqr);//供应商或债权人的帐号
		setFMVal(cjrq+"_"+i, lineData.cjrq);//创建日期
		setFMVal(kc+"_"+i, lineData.kc);//库存
		setFMVal(TEXT_LINE+"_"+i, lineData.TEXT_LINE);//备注
		setFMVal(ydmxId+"_"+i, lineData.ydmxId);//月度明细id
		
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
