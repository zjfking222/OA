<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="weaver.general.*" %>
<%@page import="java.util.*" %>
<%@page import="com.jiuyi.util.JiuyiUtil" %>
<%
//信用证开证申请流程
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
	String wzhth = map.get("gc").toString();//外证合同号
	String nzhth = map.get("gc1").toString();//内证合同号
	String wzgongys = map.get("gongys").toString();//外证供应商
	String nzgongys = map.get("gongys1").toString();//内证供应商
	map =JiuyiUtil.getFieldId(formid,"1");//外证明细表数据
	String wzjslx = map.get("jijia").toString();//外证金属类型
	String wzsl = map.get("shlmt").toString();//外证数量
	map =JiuyiUtil.getFieldId(formid,"2");//内证明细表数据
	String nzjslx = map.get("jijia").toString();//内证金属类型
	String nzsl = map.get("shlmt").toString();//内证数量
%>
<SCRIPT language="javascript" src="/js/weaver_wev8.js"></script>
<script type="text/javascript">
var ismandStr = "<img src='/images/BacoError.gif' align='absmiddle'>";
var wzhth='<%=wzhth%>';//外证合同号
var nzhth='<%=nzhth%>';//内证合同号
var wzgongys='<%=wzgongys%>';//外证供应商
var nzgongys='<%=nzgongys%>';//内证供应商

var wzjslx='<%=wzjslx%>';//外证金属类型
var wzsl='<%=wzsl%>';//外证数量
var nzjslx='<%=nzjslx%>';//内证金属类型
var nzsl='<%=nzsl%>';//内证数量

var requestid='<%=requestid%>';


jQuery(document).ready(function(){
	jQuery("#field"+wzhth).bindPropertyChange(function() {
		getWzsl();
	});
	jQuery("#field"+nzhth).bindPropertyChange(function() {
		getNzsl();
	});
	setInterval(function() { //初始化
		var mxNum_sum = 0;
		//checkCustomize = function (){

			var mxNum =  jQuery("#nodesnum0").val()
			var mxNum2 =  jQuery("#nodesnum1").val()
			var submitdtlid0 = jQuery("#submitdtlid0").val();
		
			var filedIDs = "9258"; //需要复制的字段filedID，只要数字(英文逗号分隔)
			var kzhje1usd = 0;//明细表1的开证总金额USD
			var kzhje1usd2 = 0;//明细表2的开证总金额USD
			var rzxsh = jQuery("#field9369").val();//融资系数
			var kzhzje2usd = 0;//明细表2的开证总金额US
	        var hl = jQuery("#field9361").val();//明细表1的汇率
	        var rzxs = jQuery("#field9362").val();//明细表1的融资系数
	        var hl2 = jQuery("#field9368").val();//明细表2的汇率
	        var rzxs2 = jQuery("#field9369").val();//明细表2的融资系数
			
			for (var i = 0; i < mxNum; i++) {
				var wzjslxvalue = jQuery("#field"+wzjslx+"_" + i).val();//外证金属类型
				var jijia = jQuery("#field9258_" + i).val();//判断计价方式
				var xishu = jQuery("#field9260_" + i).val();//获取系数；
				var gudj = jQuery("#field9261_" + i).val();//获取固定价；
				var jizhj = jQuery("#field9262_" + i).val();//获取基准价；
				var zhk = jQuery("#field9425_" + i).val();//获取折扣；
				var usd = jQuery("#field9265_" + i).val();//获取usd
				var mt = jQuery("#field9259_" + i).val();//获取mt
				var gshj = jQuery("#field9264_" + i).val();//获取公式价
				
				gshj = jizhj*zhk;
				jQuery("#field9264_" + i).val(gshj);
				if (0 == jijia) {
					if(0 != gshj){
					    usd = mt*xishu*gshj;//货值USD=数量(kg)*系数*公式价
					    kzhje1usd = kzhje1usd+usd;
					    jQuery("#field9265_" + i).val(usd);
					    jQuery("#field9266_" + i).val(usd*hl*rzxs);
	                }else{
					    usd = mt*xishu*gudj;//货值USD=数量(kg)*系数*固定价
					    kzhje1usd = kzhje1usd+usd;
					    jQuery("#field9265_" + i).val(usd);
					    jQuery("#field9266_" + i).val(usd*hl*rzxs);
	                }
					
				} else {
	                if(0 != gshj){
					    usd = mt*gshj;
					    kzhje1usd = kzhje1usd+usd;
					    jQuery("#field9265_" + i).val(usd);
					    jQuery("#field9266_" + i).val(usd*hl*rzxs);
	                }else{
					    usd = mt*gudj;
					    kzhje1usd = kzhje1usd+usd;
					    jQuery("#field9265_" + i).val(usd);
					    jQuery("#field9266_" + i).val(usd*hl*rzxs);
	                }
				}
			
			}

			jQuery("#field9439").val(kzhje1usd);
			jQuery("#field9364").val(kzhje1usd*hl*rzxs);
			var kzzjeUSD=jQuery("#field9439").val();
			var kzzjeRMB=jQuery("#field9364").val();
			var hydzzje=jQuery("#field13659").val();
			var kzzjeEUR = jQuery("#field9439").val()/ jQuery("#field13657").val();
			jQuery("#field13655").val(kzzjeEUR);
			var ydzzje=(trim(kzzjeRMB)==0?(1+parseFloat(hydzzje))*kzzjeUSD:(1+parseFloat(hydzzje))*kzzjeRMB);
			jQuery("#field13661").val(ydzzje);
		for (var j = 0; j < mxNum2; j++) {
			var jijia2 = jQuery("#field9337_" + j).val();//判断计价方式
			var mt2 = jQuery("#field9338_" + j).val();//获取mt
			var xishu2 = jQuery("#field9339_" + j).val();//获取系数；
			var gudj2 = jQuery("#field9340_" + j).val();//获取固定价；
			var jizhj2 = jQuery("#field9341_" + j).val();//获取基准价；
			var zhk2 = jQuery("#field9426_" + j).val();//获取折扣；
			var usd2 = jQuery("#field9344_" + j).val();//获取usd
			var gshj2 = jQuery("#field9343_" + j).val();//获取公式价


			gshj2 = jizhj2*zhk2;
			jQuery("#field9343_" + j).val(gshj2);
			if (0 == jijia2) {
				if(0 != gshj2){
					usd2 = mt2*xishu2*gshj2;
					kzhje1usd2 = kzhje1usd2+usd2;
					jQuery("#field9344_" + j).val(usd2);
				    jQuery("#field9345_" + j).val(usd2*hl2*rzxs2);
				}else{
				    usd2 = mt2*xishu2*gudj2;//货值USD=数量(kg)*系数*公式价
				    kzhje1usd2 = kzhje1usd2+usd2;
				    jQuery("#field9344_" + j).val(usd2);
				    jQuery("#field9345_" + j).val(usd2*hl2*rzxs2);
				}
		    } else {
	            if(0 != gshj2){
				    usd2 = mt2*gshj2;
				    kzhje1usd2 = kzhje1usd2+usd2;
				    jQuery("#field9344_" + j).val(usd2);
				    jQuery("#field9345_" + j).val(usd2*hl2*rzxs2);
	             }else{
				    usd2 = mt2*gudj2;
				    kzhje1usd2 = kzhje1usd2+usd2;
				    jQuery("#field9344_" + j).val(usd2);
				    jQuery("#field9345_" + j).val(usd2*hl2*rzxs2);
	             }
			}
		}

		jQuery("#field9440").val(kzhje1usd2*rzxs2);
		jQuery("#field9371").val(kzhje1usd2*hl2*rzxs2);
		var kzzjeRMB2=jQuery("#field9371").val();
        var kzzjeUSD2=jQuery("#field9440").val();
        var hydzzje2=jQuery("#field13660").val();
		var kzzjeEUR2 = jQuery("#field9440").val()/ jQuery("#field13658").val();
		jQuery("#field13656").val(kzzjeEUR2);
		var ydzzje2 = (trim(kzzjeRMB2)==0?(1+parseFloat(hydzzje2))*kzzjeUSD2:(1+parseFloat(hydzzje2))*kzzjeRMB2);
		jQuery("#field13662").val(ydzzje2);
		
		},1000);
	
		//}
});

function getWzsl(){
	 var gc = jQuery("#field"+wzhth).val();//合同号=外证合同号
	 var gongys = jQuery("#field"+wzgongys).val();//供应商=外证供应商
     if(gc==""){
     	return;
     }else{
    	jQuery.ajax({
     		url:"/workflow/request/GetSAPDataAjax567.jsp",
   	 		async:false,
   	 		type:"post",
   	 		data:{"operation":"htxx","gc":gc,"gongys":gongys},
   	 		success:function(data){
   	 			eval("var obj="+data);
   	 			deleteRow(0);
   	 			if(obj.co>0){ //钴
					addRow0(0);//加行
					var rowindex = (jQuery('#indexnum0').val()-1);//行号
					setFMVal(wzjslx+"_"+rowindex,"0");//赋值
					//setFMVal(wzsl+"_"+rowindex,obj.co);
   	 			}
   	 			if(obj.cu>0){ //铜
					addRow0(0);
                    var rowindex = (jQuery('#indexnum0').val()-1);//行号
					setFMVal(wzjslx+"_"+rowindex,"1");
					//setFMVal(wzsl+"_"+rowindex,obj.cu);
   	 			}
   	 			if(obj.ge>0){//锗
   	 				addRow0(0);
                    var rowindex = (jQuery('#indexnum0').val()-1);//行号
					setFMVal(wzjslx+"_"+rowindex,"2");
					//setFMVal(wzsl+"_"+rowindex,obj.ge);
   	 			}
   	 			if(obj.ni>0){ //镍
   	 				addRow0(0);
                    var rowindex = (jQuery('#indexnum0').val()-1);//行号
					setFMVal(wzjslx+"_"+rowindex,"3");
					//setFMVal(wzsl+"_"+rowindex,obj.ni);
   	 			}
                if(obj.li>0){ //锂
                    addRow0(0);
                    var rowindex = (jQuery('#indexnum0').val()-1);//行号
                    setFMVal(wzjslx+"_"+rowindex,"4");
                    //setFMVal(wzsl+"_"+rowindex,obj.ni);
                }
                if(obj.mn>0){ //锰
                    addRow0(0);
                    var rowindex = (jQuery('#indexnum0').val()-1);//行号
                    setFMVal(wzjslx+"_"+rowindex,"5");
                    //setFMVal(wzsl+"_"+rowindex,obj.ni);
                }
   	 			deleteRow(1);
	 			if(obj.co>0){
					addRow1(1);
	               	var rowindex = (jQuery('#indexnum1').val()-1);//行号
					setFMVal(nzjslx+"_"+rowindex,"0");
					//setFMVal(nzsl+"_"+rowindex,obj.co);
	 			}
	 			if(obj.cu>0){
					addRow1(1);
					var rowindex = (jQuery('#indexnum1').val()-1);//行号
					setFMVal(nzjslx+"_"+rowindex,"1");
					//setFMVal(nzsl+"_"+rowindex,obj.cu);
	 			}
	 			if(obj.ge>0){
	 				addRow1(1);
	                var rowindex = (jQuery('#indexnum1').val()-1);//行号
					setFMVal(nzjslx+"_"+rowindex,"2");
					//setFMVal(nzsl+"_"+rowindex,obj.ge);
	 			}
	 			if(obj.ni>0){
	 				addRow1(1);
                var rowindex = (jQuery('#indexnum1').val()-1);//行号
				setFMVal(nzjslx+"_"+rowindex,"3");
				//setFMVal(nzsl+"_"+rowindex,obj.ni);
	 			}
                if(obj.li>0){
                    addRow1(1);
                    var rowindex = (jQuery('#indexnum1').val()-1);//行号
                    setFMVal(nzjslx+"_"+rowindex,"4");
                    //setFMVal(nzsl+"_"+rowindex,obj.ge);
                }
                if(obj.mn>0){
                    addRow1(1);
                    var rowindex = (jQuery('#indexnum1').val()-1);//行号
                    setFMVal(nzjslx+"_"+rowindex,"5");
                    //setFMVal(nzsl+"_"+rowindex,obj.ge);
                }
     	 	},
     		error:function(data){
   	 			alert("读取出错，请联系系统管理员");
   	 		}
   		}); 
    	
     }
}
function getNzsl(){
	var gc = jQuery("#field"+nzhth).val();
	var gongys = jQuery("#field"+nzgongys).val();
    if(gc==""){
    	return;
    }else{
   		jQuery.ajax({
    		url:"/workflow/request/GetSAPDataAjax567.jsp",
  	 		async:false,
  	 		type:"post",
  	 		data:{"operation":"htxx","gc":gc,"gongys":gongys},
  	 		success:function(data){
  	 			eval("var obj="+data);
  	 			deleteRow(1);
  	 			if(obj.co>0){
					addRow1(1);
                   	var rowindex = (jQuery('#indexnum1').val()-1);//行号
					setFMVal(nzjslx+"_"+rowindex,"0");
					//setFMVal(nzsl+"_"+rowindex,obj.co);
  	 			}
  	 			if(obj.cu>0){
					addRow1(1);
					var rowindex = (jQuery('#indexnum1').val()-1);//行号
					setFMVal(nzjslx+"_"+rowindex,"1");
					//setFMVal(nzsl+"_"+rowindex,obj.cu);
  	 			}
  	 			if(obj.ge>0){
  	 				addRow1(1);
                    var rowindex = (jQuery('#indexnum1').val()-1);//行号
					setFMVal(nzjslx+"_"+rowindex,"2");
					//setFMVal(nzsl+"_"+rowindex,obj.ge);
  	 			}
  	 			if(obj.ni>0){
  	 				addRow1(1);
                    var rowindex = (jQuery('#indexnum1').val()-1);//行号
					setFMVal(nzjslx+"_"+rowindex,"3");
					//setFMVal(nzsl+"_"+rowindex,obj.ni);
  	 			}
                if(obj.li>0){
                    addRow1(1);
                    var rowindex = (jQuery('#indexnum1').val()-1);//行号
                    setFMVal(nzjslx+"_"+rowindex,"4");
                    //setFMVal(nzsl+"_"+rowindex,obj.ni);
                }
                if(obj.mn>0){
                    addRow1(1);
                    var rowindex = (jQuery('#indexnum1').val()-1);//行号
                    setFMVal(nzjslx+"_"+rowindex,"5");
                    //setFMVal(nzsl+"_"+rowindex,obj.ni);
                }
    	 	},
    		error:function(data){
  	 			alert("读取出错，请联系系统管理员");
  	 		}
  		}); 
   	
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
</script>
<%}%>