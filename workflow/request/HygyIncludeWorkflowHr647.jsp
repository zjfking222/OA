<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="weaver.general.*" %>
<%@page import="java.util.*" %>
<%@page import="com.jiuyi.util.JiuyiUtil" %>
<%
//HR流程
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
	String ksrq = map.get("kaisrq").toString();//开始日期
	String kssjxs = map.get("kaissjxs").toString();//开始时间小时
	String kssjfz = map.get("kaissjfz").toString();//开始时间分钟
	String jsrq = map.get("jiesrq").toString();//结束日期
	String jssjxs = map.get("jiessjxs").toString();//结束时间小时
	String jssjfz = map.get("jiessjfz").toString();//结束时间分钟
	String ss = map.get("shis").toString();//时数
	String leix2 = map.get("leix2").toString();//请假类型
	String nianxjde = map.get("nianxjde").toString();//年休假定额
	String shengyxss = map.get("shengyxss").toString();//剩余小时数
%>
<!--<SCRIPT language="javascript" src="/js/weaver.js"></script>-->
<script type="text/javascript">
var ismandStr = "<img src='/images/BacoError.gif' align='absmiddle'>";
var yuangbh='<%=yuangbh%>';//员工编码
var ksrq='<%=ksrq%>';//开始日期
var kssjxs='<%=kssjxs%>';//开始时间小时
var kssjfz='<%=kssjfz%>';//开始时间分钟
var jsrq='<%=jsrq%>';//结束日期
var jssjxs='<%=jssjxs%>';//结束时间小时
var jssjfz='<%=jssjfz%>';//结束时间分钟
var ss='<%=ss%>';//时数
var leix2='<%=leix2%>';//类型
var nianxjde='<%=nianxjde%>';//年休假定额
var shengyxss='<%=shengyxss%>';//剩余小时数
var workflowid='<%=workflowid%>';//工作流id

var requestid='<%=requestid%>';


jQuery(function(){
	jQuery("#field"+leix2).bindPropertyChange(function() {
		getShis();
	});
	jQuery("#field"+yuangbh).bindPropertyChange(function() {
		getShis();
	});
	jQuery("#field"+ksrq).bindPropertyChange(function() {
		getShis();
	});
	jQuery("#field"+kssjxs).bindPropertyChange(function() {
	     var h_ksrq = jQuery("#field"+ksrq).val();
	     var h_kssjxs = jQuery("#field"+kssjxs+" option:selected").text();
	     var h_kssjfz = jQuery("#field"+kssjfz+" option:selected").text();
	     if(h_kssjxs==''){
		     h_kssjxs = jQuery("#disfield"+kssjxs+" option:selected").text();
		     h_kssjfz = jQuery("#disfield"+kssjfz+" option:selected").text(); 
	     }
	     var h_jsrq = jQuery("#field"+jsrq).val();
	     var h_jssjxs = jQuery("#field"+jssjxs+" option:selected").text();
	     var h_jssjfz = jQuery("#field"+jssjfz+" option:selected").text();
	     if(h_kssjxs!=''&& h_kssjfz!='' && h_jssjxs!=''&& h_jssjfz!=''){
	    	 if(checkTime(h_ksrq,h_kssjxs,h_kssjfz,h_jsrq,h_jssjxs,h_jssjfz)){
	 			getShis();
	 		}else{
	 			setFMVal(kssjxs,"");
	 		} 
	     }
	});
	jQuery("#field"+kssjfz).bindPropertyChange(function() {
		var h_ksrq = jQuery("#field"+ksrq).val();
	     var h_kssjxs = jQuery("#field"+kssjxs+" option:selected").text();
	     var h_kssjfz = jQuery("#field"+kssjfz+" option:selected").text();
	     if(h_kssjxs==''){
		     h_kssjxs = jQuery("#disfield"+kssjxs+" option:selected").text();
		     h_kssjfz = jQuery("#disfield"+kssjfz+" option:selected").text(); 
	     }
	     var h_jsrq = jQuery("#field"+jsrq).val();
	     var h_jssjxs = jQuery("#field"+jssjxs+" option:selected").text();
	     var h_jssjfz = jQuery("#field"+jssjfz+" option:selected").text();
	     if(h_kssjxs!=''&& h_kssjfz!='' && h_jssjxs!=''&& h_jssjfz!=''){
	    	 if(checkTime(h_ksrq,h_kssjxs,h_kssjfz,h_jsrq,h_jssjxs,h_jssjfz)){
	 			getShis();
	 		}else{
	 			setFMVal(kssjfz,"");
	 		} 
	     }
	});

	jQuery("#field"+jsrq).bindPropertyChange(function() {
		getShis();
	});
	jQuery("#field"+jssjxs).bindPropertyChange(function() {
		var h_ksrq = jQuery("#field"+ksrq).val();
	     var h_kssjxs = jQuery("#field"+kssjxs+" option:selected").text();
	     var h_kssjfz = jQuery("#field"+kssjfz+" option:selected").text();
	     if(h_kssjxs==''){
		     h_kssjxs = jQuery("#disfield"+kssjxs+" option:selected").text();
		     h_kssjfz = jQuery("#disfield"+kssjfz+" option:selected").text(); 
	     }
	     var h_jsrq = jQuery("#field"+jsrq).val();
	     var h_jssjxs = jQuery("#field"+jssjxs+" option:selected").text();
	     var h_jssjfz = jQuery("#field"+jssjfz+" option:selected").text();
	     if(h_kssjxs!=''&& h_kssjfz!='' && h_jssjxs!=''&& h_jssjfz!=''){
	    	 if(checkTime(h_ksrq,h_kssjxs,h_kssjfz,h_jsrq,h_jssjxs,h_jssjfz)){
	 			getShis();
	 		}else{
	 			setFMVal(jssjxs,"");
	 		} 
	     }
	});
	jQuery("#field"+jssjfz).bindPropertyChange(function() {
		var h_ksrq = jQuery("#field"+ksrq).val();
	     var h_kssjxs = jQuery("#field"+kssjxs+" option:selected").text();
	     var h_kssjfz = jQuery("#field"+kssjfz+" option:selected").text();
	     if(h_kssjxs==''){
		     h_kssjxs = jQuery("#disfield"+kssjxs+" option:selected").text();
		     h_kssjfz = jQuery("#disfield"+kssjfz+" option:selected").text(); 
	     }
	     var h_jsrq = jQuery("#field"+jsrq).val();
	     var h_jssjxs = jQuery("#field"+jssjxs+" option:selected").text();
	     var h_jssjfz = jQuery("#field"+jssjfz+" option:selected").text();
	     if(h_kssjxs!=''&& h_kssjfz!='' && h_jssjxs!='' && h_jssjfz!=''){
	    	 if(checkTime(h_ksrq,h_kssjxs,h_kssjfz,h_jsrq,h_jssjxs,h_jssjfz)){
	 			getShis();
	 		}else{
	 			setFMVal(jssjfz,"");
	 		} 
	     }
	});
});
function getShis(){
	 var h_yuangbh = jQuery("#field"+yuangbh).val();
     var h_ksrq = jQuery("#field"+ksrq).val();
     var h_kssj = jQuery("#field"+kssjxs+" option:selected").text()+":"+jQuery("#field"+kssjfz+" option:selected").text();
     if(h_kssj==':'){
    	 h_kssj = jQuery("#disfield"+kssjxs+" option:selected").text()+":"+jQuery("#disfield"+kssjfz+" option:selected").text(); 
     }
     var h_jsrq = jQuery("#field"+jsrq).val();
     var h_jssj = jQuery("#field"+jssjxs+" option:selected").text()+":"+jQuery("#field"+jssjfz+" option:selected").text();
     var h_leix2 = jQuery("#field"+leix2+" option:selected").text();
     if(h_leix2==''){
    	 h_leix2 = jQuery("#disfield"+leix2+" option:selected").text();
     }
     if(h_yuangbh=="" || h_ksrq=="" || h_kssj==":" || h_jsrq=="" || h_jssj==":"|| h_leix2==""){
     	return;
     }else{
    	jQuery.ajax({
     		url:"/ajaxOperation/GetTime.jsp",
   	 		async:false,
   	 		type:"post",
   	 		data:{"operation":"time","h_yuangbh":h_yuangbh,"h_ksrq":h_ksrq,"h_kssj":h_kssj,"h_jsrq":h_jsrq,"h_jssj":h_jssj,"h_leix":h_leix2},
   	 		success:function(data){
   	 			eval("var obj="+data);
   				setFMVal(ss,obj.shis);//时数
   				setFMVal(nianxjde,obj.nianxjde);//年休假定额
   				setFMVal(shengyxss,obj.shengyxss);//剩余小时数
     			//alert('欠款结束');
     	 	},
     		error:function(data){
   	 			alert("读取出错，请联系系统管理员");
   	 		}
   		}); 
    	
     }
}
function checkTime(h_ksrq,h_kssjxs,h_kssjfz,h_jsrq,h_jssjxs,h_jssjfz){
		if(compareTime(h_ksrq+" 13:30:00",h_ksrq+" "+h_kssjxs+":"+h_kssjfz+":00")>0
				&&compareTime(h_ksrq+" 13:30:00",h_ksrq+" "+h_jssjxs+":"+h_jssjfz+":00")>0){//都是下午
			if(!isFifthMutiple(compareTime(h_ksrq+" "+h_kssjxs+":"+h_kssjfz+":00",h_ksrq+" "+h_jssjxs+":"+h_jssjfz+":00"))){
				top.Dialog.alert("时间间隔必须为30的倍数");
				return false;
			}else{
				return true;
			}
		}else if(compareTime(h_ksrq+" 07:59:00",h_ksrq+" "+h_kssjxs+":"+h_kssjfz+":00")>0
				&&compareTime(h_ksrq+" 07:59:00",h_ksrq+" "+h_jssjxs+":"+h_jssjfz+":00")>0){//都是上午
			if(!isFifthMutiple(compareTime(h_ksrq+" "+h_kssjxs+":"+h_kssjfz+":00",h_ksrq+" "+h_jssjxs+":"+h_jssjfz+":00"))){
				top.Dialog.alert("时间间隔必须为30的倍数");
				return false;
			}else{
				return true;
			}
		}else  {
			return true;
		}
}
//时间比较（yyyy-MM-dd HH:mm:ss）
function compareTime(startTime,endTime) {
  var startTimes = startTime.substring(0, 10).split('-');
  var endTimes = endTime.substring(0, 10).split('-');
  startTime = startTimes[1] + '-' + startTimes[2] + '-' + startTimes[0] + ' ' + startTime.substring(10, 19);
  endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + ' ' + endTime.substring(10, 19);
  var thisResult = (Date.parse(endTime) - Date.parse(startTime)) / 60 / 1000;
  return thisResult;
}
function isFifthMutiple(number){
	var isTen=number%30;
		if(isTen==0){
			return true;
		}else{
			return false;
		}
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
//删除明细表
function deleteDetailRow(groupid,isfromsap){
    try{
	var flag = false;
	var ids = document.getElementsByName("check_node_"+groupid);
	for(i=0; i<ids.length; i++) {
		if(ids[i].checked==true) {
			flag = true;
			break;
		}
	}
	if(isfromsap){flag=true;}
    if(flag) {
		if(isfromsap || de()){
		var oTable=$G('oTable' + groupid);
		var len = document.forms[0].elements.length;
		var curindex=parseInt($G("nodesnum"+groupid).value);
		var i=0;
	var thead = jQuery('#oTable'+groupid).find('tr.exceldetailtitle').size();
	if(thead==null||thead==undefined||thead==0) thead=1;
		var rowsum1 = thead-1;
		var objname = "check_node_"+groupid;
		for(i=len-1; i >= 0;i--) {
			if (document.forms[0].elements[i].name==objname){
				rowsum1 += 1;
			}
		}
		for(i=len-1; i>=0; i--) {
			if(document.forms[0].elements[i].name==objname){
				if(document.forms[0].elements[i].checked==true){
					var nodecheckObj;
						var delid;
					try{
						if(jQuery(oTable.rows[rowsum1].cells[0]).find("[name='"+objname+"']").length>0){	
							delid = jQuery(oTable.rows[rowsum1].cells[0]).find("[name='"+objname+"']").eq(0).val(); 
						}
					}catch(e){}
					//记录被删除的旧记录 id串
					if(jQuery(oTable.rows[rowsum1].cells[0]).children().length>0 && jQuery(jQuery(oTable.rows[rowsum1].cells[0]).children()[0]).children().length>1){
						if($G("deldtlid"+groupid).value!=''){
							//老明细
							$G("deldtlid"+groupid).value+=","+jQuery(oTable.rows[rowsum1].cells[0].children[0]).children()[1].value;
						}else{
							//新明细
							$G("deldtlid"+groupid).value=jQuery(oTable.rows[rowsum1].cells[0]).children().eq(0).children()[1].value;
						}
					}
					//从提交序号串中删除被删除的行
					var submitdtlidArray=$G("submitdtlid"+groupid).value.split(',');
					$G("submitdtlid"+groupid).value="";
					var k;
					for(k=0; k<submitdtlidArray.length; k++){
						if(submitdtlidArray[k]!=delid){
							if($G("submitdtlid"+groupid).value==''){
								$G("submitdtlid"+groupid).value = submitdtlidArray[k];
							}else{
								$G("submitdtlid"+groupid).value += ","+submitdtlidArray[k];
							}
						}
					}
					
					oTable.deleteRow(rowsum1);
					curindex--;
				}
				rowsum1--;
			}
		}
		$G("nodesnum"+groupid).value=curindex;
			calSum(groupid);
}
}else{
        top.Dialog.alert('请选择需要删除的数据');
		return;
    }	}catch(e){}
	try{
		var indexNum = jQuery("span[name='detailIndexSpan0']").length;
		for(var k=1; k<=indexNum; k++){
			jQuery("span[name='detailIndexSpan0']").get(k-1).innerHTML = k;
		}
	}catch(e){}
	try{
		if(typeof _customDelFun0 === 'function'){
			_customDelFun0();
		}
	}catch(e){}
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
</script>
<%}%>