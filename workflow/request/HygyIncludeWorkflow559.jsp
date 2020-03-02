<%@page import="com.jiuyi.util.CommonUtil"%>
<%@page import="weaver.general.*"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<%
int formid=Util.getIntValue(request.getParameter("formid"));
    HashMap<String, String> map=CommonUtil.getFieldId(formid, "0");//主表数据
    String lx =  map.get("lx");//类型
    String wllx =  map.get("wllx");//物料类型
    String WERKS =  map.get("werks");//工厂
    String KOSTL =  map.get("kostl");//成本中心
    String YDLX =  map.get("ydlx");//移动类型
    String AUFNR =  map.get("aufnr");//内部订单
    String SAP =  map.get("sap");//是否SAP发起

    HashMap<String, String> map1=CommonUtil.getFieldId(formid, "1");//明细数据
    String MATNR=map1.get("matnr");//物料编码
    String LGPBE=map1.get("lgpbe");//仓位

%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">
    //MM-其他领料
    /*****************主表********************/

    var wllx = '<%=wllx%>';//物料类型
    var lx = '<%=lx%>';//类型
    var WERKS = '<%=WERKS%>';//工厂
    var KOSTL = '<%=KOSTL%>';//成本中心
    var YDLX = '<%=YDLX%>';//移动类型
    var AUFNR = '<%=AUFNR%>';//内部订单
    var SAP = '<%=SAP%>';//是否SAP发起

    /*****************明细表********************/
    var MATNR='<%=MATNR%>';//物料编码
    var LGPBE='<%=LGPBE%>';//仓位

    var sfsap = jQuery("#field"+SAP).val();
	//隐藏申请人姓名所在行
	setFMVal(wllx,"1WZ");
	jQuery("#field"+<%=lx%>).bindPropertyChange(function(){
		var lxvaule = jQuery("#field"+lx).val();
		if(lxvaule=="4"){
			var wllxvalue = "1WZ";
		}else {
			var wllxvalue = "6GZ";
		}
		
		setFMVal(wllx,wllxvalue);
	})

	if(sfsap!="0"){
		
	jQuery("#field"+<%=WERKS%>).bindPropertyChange(function(){//工厂
		deleteRow(0);
	})
	
	jQuery("#field"+<%=KOSTL%>).bindPropertyChange(function(){//成本中心
		deleteRow(0);
	})
	
	jQuery("#field"+<%=YDLX%>).bindPropertyChange(function(){//移动类型
		deleteRow(0);
	})
	
	jQuery("#field"+<%=AUFNR%>).bindPropertyChange(function(){//内部订单
		deleteRow(0);
	})
	
	jQuery("#field"+<%=lx%>).bindPropertyChange(function(){//内部订单
		deleteRow(0);
	})
	
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