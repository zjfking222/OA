<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="weaver.general.*" %>
<%@page import="java.util.*" %>
<%@page import="com.jiuyi.util.JiuyiUtil" %>
<%
int formid=Util.getIntValue(request.getParameter("formid"));
Map map = new HashMap();
if(formid!=-1){
map =JiuyiUtil.getFieldId(formid,"0");//主表数据
String WERKS1 =  map.get("werks1").toString();//工厂
String KOSTL1=  map.get("kostl1").toString();//成本中心
String dd=  map.get("dd").toString();//项目
String shenqrxm =  map.get("shenqrxm").toString();//申请人姓名
String wllx =  map.get("wllx").toString();//物料类型
String BSART1 =  map.get("bsart1").toString();//月度采购申请凭证类型
String BSART2=  map.get("bsart2").toString();//零星采购申请凭证类型

String wllxvaule="";

%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">

/*****************主表********************/
var RLTGR = '<%=shenqrxm%>';//申请人姓名
var WERKS1 = '<%=WERKS1%>';//工厂
var KOSTL1 = '<%=KOSTL1%>';//成本中心
var dd = '<%=dd%>';//项目
var wllx = '<%=wllx%>';//物料类型
var BSART1 = '<%=BSART1%>';//月度采购申请凭证类型
var BSART2 = '<%=BSART2%>';///零星采购申请凭证类型 


	
	//添加按钮
	jQuery("button[name='addbutton0']").click(function(){
		var indexnum0 = $("#indexnum0").val();
		if(indexnum0==200){
			alert("明细行不能超过200行");
			jQuery("button[name='addbutton0']").attr("disabled", true);
		}		   		 
	});

	
	//隐藏申请人姓名所在行
	jQuery("td[_fieldlabel='"+<%=shenqrxm%>+"'] ").parent().hide();	
	var rowindex = (jQuery('#indexnum0').val()-1);//行号
	
	jQuery("#field"+<%=BSART1 %>).bindPropertyChange(function(){//月度采购申请凭证类型
		var BSART1vaule = jQuery("#field"+BSART1).val();
		if(BSART1vaule=="0"){
			var wllxvalue = "1WZ";
		}else if(BSART1vaule=="1"){
			var wllxvalue = "2FL";
		}else if(BSART1vaule=="3"){
			var wllxvalue = "7FW";
		}
		setFMVal(wllx,wllxvalue);
		deleteRow(0);
	})
	
	jQuery("#field"+<%=BSART2 %>).bindPropertyChange(function(){//零星采购申请凭证类型 
		var BSART2vaule = jQuery("#field"+BSART2).val();
		if(BSART2vaule=="0"){
			var wllxvalue = "1WZ";
		}else if(BSART2vaule=="1"){
			var wllxvalue = "2FL";
		}else if(BSART2vaule=="3"){
			var wllxvalue = "7FW";
		}
		setFMVal(wllx,wllxvalue);	
		deleteRow(0);
	})
	
	jQuery("#field"+dd).bindPropertyChange(function(){//项目
		deleteRow(0);
	})
	
	jQuery("#field"+KOSTL1).bindPropertyChange(function(){//成本中心
		deleteRow(0);
	})
	
	jQuery("#field"+WERKS1).bindPropertyChange(function(){//工厂
		deleteRow(0);
	})
	

	
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
	function readonly(ids){
		var id = ids.split(",");
		for(var i=0;i<id.length;i++){
			jQuery("#field"+id[i]).attr("readonly","readonly");
		}
	}	
	function mxreadonly(ids){
		var id = ids.split(",");
		var rowindex=jQuery('#indexnum0').val();
		for(var i=0;i<rowindex;i++){
			for(var j=0;j<id.length;j++){
				jQuery("#field"+id[j]+"_"+i).attr("readonly","readonly");
			}
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
<%}%>