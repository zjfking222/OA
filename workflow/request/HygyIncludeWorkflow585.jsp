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
new BaseBean().writeLog("物料主数据停用流程  读取BPC模版接口js ");
int formid=Util.getIntValue(request.getParameter("formid"));
String nodeid=Util.null2String(request.getParameter("nodeid"));
String requestid=Util.null2String(request.getParameter("requestid"));
String workflowid=Util.null2String(request.getParameter("workflowid"));

HashMap<String, String> map=CommonUtil.getFieldId(formid, "0");//主表
String MATNR =  map.get("matnr");//物料编码
String gcbh =  map.get("gcbh");//工厂
String MAKTX =  map.get("maktx");//物料描述


HashMap<String, String> map2 = CommonUtil.getFieldId(formid,"1");//明细1
String WERKS = map2.get("werks");//工厂

HashMap<String, String> map3 = CommonUtil.getFieldId(formid,"3");//明细3
String VKORG = map3.get("vkorg");//销售组织
String VTWEG = map3.get("vtweg");//分销渠道

%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">
/*****************主表********************/
var MATNR = '<%=MATNR%>';//物料编码
var gcbh = '<%=gcbh%>';//工厂
var MAKTX = '<%=MAKTX%>';//物料描述
/*****************明细1********************/
var WERKS = '<%=WERKS%>';//工厂
/*****************明细3********************/
var VKORG = '<%=VKORG%>';//销售组织
var VTWEG = '<%=VTWEG%>';//分销渠道


jQuery(function(){
	//绑定监听
	bindchange();//绑定监听
});

//项目编号绑监听
function bindchange(){
	jQuery("#field"+MATNR).bindPropertyChange(function(){
		var MATNR1 = jQuery("#field"+MATNR).val();
		var gcbh1 = jQuery("#field"+gcbh).val();
		var MAKTX1 = jQuery("#field"+MAKTX).val();
		setDetailData(MATNR1,gcbh1,MAKTX1);
	});
	jQuery("#field"+gcbh).bindPropertyChange(function(){
		var MATNR1 = jQuery("#field"+MATNR).val();
		var gcbh1 = jQuery("#field"+gcbh).val();
		var MAKTX1 = jQuery("#field"+MAKTX).val();
		setDetailData(MATNR1,gcbh1,MAKTX1);
	});
}

//添加明细行数据
function setDetailData(MATNR1,gcbh1,MAKTX1){
	if(gcbh1==''){
		return;
	}else{
		jQuery.ajax({
			url:"/workflow/request/GetSAPDataAjax585.jsp",
			type:"post",
			data:{"MATNR1":MATNR1,"gcbh1":gcbh1,"MAKTX1":MAKTX1},
			async: true,
			success:function(data){
				var mes=eval('('+data+')');
		 		//eval("var obj="+data);
				var dt1Data=mes.dt1;
				setDt1Data(dt1Data);
			},
			error:function(e){
				console.log(e);
				alert("错误"+e);
			}
		});	
	}
	
}

//明细赋值 
function setDt1Data(dt1Data){
	var lineData;
	deleteRow(0);
	for(var i=0;i<dt1Data.length;i++){
		lineData=dt1Data[i];
		addRow0(0);
		/*****************明细1********************/
		setFMVal(WERKS+"_"+i, lineData.WERKS);//工厂
		/*****************明细3********************/
		setFMVal(VKORG+"_"+i, lineData.VKORG);//销售组织
		setFMVal(VTWEG+"_"+i, lineData.VTWEG);//分销渠道
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
