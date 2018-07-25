<%@page import="com.jiuyi.util.CommonUtil"%>
<%@page import="weaver.general.*"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
//客户主数据流程  读取BPC模版接口js 
new BaseBean().writeLog("客户主数据流程  读取BPC模版接口js ");
int formid=Util.getIntValue(request.getParameter("formid"));
String nodeid=Util.null2String(request.getParameter("nodeid"));
String requestid=Util.null2String(request.getParameter("requestid"));
String workflowid=Util.null2String(request.getParameter("workflowid"));

HashMap<String, String> map=CommonUtil.getFieldId(formid, "0");//主表

String  RLTGR=  map.get("rltgr");//业务伙伴分组
String  KUNNR=  map.get("kunnr");//客户编码
String  NAME1=  map.get("name1");//客户名称
String  SORT1=  map.get("sort1");//助记码
String  STREET=  map.get("street");//地址
String  LAND1=  map.get("land1");//国家码
String  REGIO=  map.get("regio");//省份
String  STCD1=  map.get("stcd1");//社会统一信用代码
String  ZSALES=  map.get("zsales");//业务员
String  KUKLA=  map.get("kukla");//行业分类
String  SPERR=  map.get("sperr");//冻结标示
String  EXTENSION2=  map.get("extension2");//主客户编码

String KOINH = map.get("koinh");//开户行名称
String BANKN = map.get("bankn");//开户账号
String TELF1 = map.get("telf1");//开票电话


String ZZSHDZ = map.get("zzshdz");//送货地址
String ZZSHLXR = map.get("zzshlxr");//送货电话
String ZZSHDH = map.get("zzshdh");//送货联系人
String ZZSHYB = map.get("zzshyb");//送货邮编

String ZZJPDZ = map.get("zzjpdz");//寄票地址
String ZZJPLXR = map.get("zzjplxr");//寄票电话
String ZZJPDH = map.get("zzjpdh");//寄票联系人
String ZZJPYB = map.get("zzjpyb");//寄票邮编


HashMap<String, String> map2 = CommonUtil.getFieldId(formid,"1");//明细1
String BUKRS = map2.get("bukrs");//公司代码

%>

<!-- <script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script> -->
<script type="text/javascript">
/*****************主表********************/
var RLTGR = '<%=RLTGR%>';//业务伙伴分组
var KUNNR = '<%=KUNNR%>';//客户编码
var NAME1 = '<%=NAME1%>';//客户名称
var SORT1 = '<%=SORT1%>';//助记码
var STREET = '<%=STREET%>';//地址
var LAND1 = '<%=LAND1%>';//国家码
var REGIO = '<%=REGIO%>';//省份
var STCD1 = '<%=STCD1%>';//社会统一信用代码
var ZSALES = '<%=ZSALES%>';//业务员
var KUKLA = '<%=KUKLA%>';//行业分类
var SPERR = '<%=SPERR%>';//冻结标示
var EXTENSION2 = '<%=EXTENSION2%>';//主客户编码

var KOINH = '<%=KOINH%>';//开户行名称
var BANKN = '<%=BANKN%>';//开户账号
var TELF1 = '<%=TELF1%>';//开票电话

var ZZSHDZ = '<%=ZZSHDZ%>';//送货地址
var ZZSHLXR = '<%=ZZSHLXR%>';//送货电话
var ZZSHDH = '<%=ZZSHDH%>';//送货联系人
var ZZSHYB = '<%=ZZSHYB%>';//送货邮编

var ZZJPDZ = '<%=ZZJPDZ%>';//寄票地址
var ZZJPLXR = '<%=ZZJPLXR%>';//寄票电话
var ZZJPDH = '<%=ZZJPDH%>';//寄票联系人
var ZZJPYB = '<%=ZZJPYB%>';//寄票邮编
/*****************明细1********************/
var BUKRS = '<%=BUKRS%>';//公司代码


jQuery(function(){
	//绑定监听
	bindchange();//绑定监听
});

//项目编号绑监听
function bindchange(){
	jQuery("#field"+KUNNR).bindPropertyChange(function(){
		var KUNNR1 = jQuery("#field"+KUNNR).val();
		setDetailData(KUNNR1);
	});
}

//添加明细行数据
function setDetailData(KUNNR1){
	jQuery.ajax({
		url:"/workflow/request/GetSAPDataAjax540.jsp",
		type:"post",
		data:{"KUNNR1":KUNNR1},
		async: true,
		success:function(data){
			var mes=eval('('+data+')');
			var dt1Data=mes.dt1;
			setDt1Data(dt1Data);
			var dt2Data=mes.dt2;
			setDt2Data(dt2Data);
		},
		error:function(e){
			console.log(e);
			alert("错误"+e);
		}
	});	
}
//主表赋值 
function setDt1Data(dt1Data){
	var lineData;
	deleteRow(0);
	lineData=dt1Data[0];

	if(lineData.RLTGR=="ZHY001"){
		setFMVal(RLTGR,"0" );//行业分类
	}else if(lineData.RLTGR=="ZHY002"){
			setFMVal(RLTGR,"1" );//行业分类
		}else if(lineData.RLTGR=="ZHY003"){
				setFMVal(RLTGR, "2");//行业分类
			}
	
	setFMVal(KUNNR, lineData.KUNNR);//客户编码
	setFMVal(NAME1, lineData.NAME1);//客户名称
	setFMVal(SORT1, lineData.SORT1);//助记码
	setFMVal(STREET, lineData.STREET);//地址
	setFMVal(LAND1, lineData.LAND1);//国家码
	setFMVal(REGIO, lineData.REGIO);//省份
	setFMVal(STCD1, lineData.STCD1);//社会统一信用代码
	setFMVal(ZSALES, lineData.ZSALES);//业务员
	setFMVal(KUKLA, lineData.KUKLA);//行业分类

	if(lineData.SPERR =="X"){
		jQuery("#field"+SPERR).nextAll().addClass("jNiceChecked");//冻结标示
		}else if(lineData.SPERR ==" "){
			jQuery("#field"+SPERR).nextAll().removeClass("jNiceChecked");//冻结标示
			}
	
	setFMVal(EXTENSION2, lineData.EXTENSION2);//主客户编码

	setFMVal(KOINH, lineData.KOINH);//开户行名称
	setFMVal(BANKN, lineData.BANKN);//开户账号
	setFMVal(TELF1, lineData.TELF1);//开票电话
	
	setFMVal(ZZSHDZ, lineData.ZZSHDZ);//送货地址
	setFMVal(ZZSHLXR, lineData.ZZSHLXR);//送货联系人
	setFMVal(ZZSHDH, lineData.ZZSHDH);//送货电话
	setFMVal(ZZSHYB, lineData.ZZSHYB);//送货邮编
	
	setFMVal(ZZJPDZ, lineData.ZZJPDZ);//寄票地址
	setFMVal(ZZJPLXR, lineData.ZZJPLXR);//寄票联系人
	setFMVal(ZZJPDH, lineData.ZZJPDH);//寄票电话
	setFMVal(ZZJPYB, lineData.ZZJPYB);//寄票邮编
}


//明细1赋值 公司信息信息
function setDt2Data(dt2Data){
	var lineData;
	deleteRow(0);
	for(var i=0;i<dt2Data.length;i++){
		lineData=dt2Data[i];
		addRow0(0);
		setFMVal(BUKRS+"_"+i, lineData.BUKRS);//公司代码
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
//删除行方法
function delRowFun(groupid, isfromsap){
	var oTable = jQuery("table#oTable"+groupid);
	var checkObj = oTable.find("input[name='check_node_"+groupid+"']:checked");
	if(isfromsap || checkObj.size()>0){
		if(isfromsap || isdel()){
			var curindex = parseInt($G("nodesnum"+groupid).value);
			var submitdtlStr = $G("submitdtlid"+groupid).value;
			var deldtlStr = $G("deldtlid"+groupid).value;
			checkObj.each(function(){
				var rowIndex = jQuery(this).val();
				var belRow = oTable.find("tr[_target='datarow'][_rowindex='"+rowIndex+"']");
				var keyid = belRow.find("input[name='dtl_id_"+groupid+"_"+rowIndex+"']").val();
				//提交序号串删除对应行号
				var submitdtlArr = submitdtlStr.split(',');
				submitdtlStr = "";
				for(var i=0; i<submitdtlArr.length; i++){
					if(submitdtlArr[i] != rowIndex)
						submitdtlStr += ","+submitdtlArr[i];
				}
				if(submitdtlStr.length > 0 && submitdtlStr.substring(0,1) === ",")
					submitdtlStr = submitdtlStr.substring(1);
				//已有明细主键存隐藏域
				if(keyid != "")
					deldtlStr += ","+keyid;
				//IE下需先销毁附件上传的object对象，才能remove行
				try{
					belRow.find("td[_fieldid][_fieldtype='6_1'],td[_fieldid][_fieldtype='6_2']").each(function(){
						var swfObj = eval("oUpload"+jQuery(this).attr("_fieldid"));
						swfObj.destroy();
					});
				}catch(e){}
				belRow.remove();
				curindex--;
			});
			$G("submitdtlid"+groupid).value = submitdtlStr;
			if(deldtlStr.length >0 && deldtlStr.substring(0,1) === ",")
				deldtlStr = deldtlStr.substring(1);
			$G("deldtlid"+groupid).value = deldtlStr;
			$G("nodesnum"+groupid).value = curindex;
			//序号重排
			oTable.find("input[name='check_node_"+groupid+"']").each(function(index){
				var belRow = oTable.find("tr[_target='datarow'][_rowindex='"+jQuery(this).val()+"']");
				belRow.find("span[name='detailIndexSpan"+groupid+"']").text(index+1);
			});
			oTable.find("input[name='check_all_record']").attr("checked", false);
			//表单设计器，删除行触发公式计算
			triFormula_delRow(groupid);
			try{
				calSum(groupid);
			}catch(e){}
			try{		//自定义函数接口,必须在最后，必须try-catch
				eval("_customDelFun"+groupid+"()");
			}catch(e){}
		}
	}else{
		var language = readCookie("languageidweaver");
		top.Dialog.alert(SystemEnv.getHtmlNoteName(3529, language));
		return;
	}
}
</script>
