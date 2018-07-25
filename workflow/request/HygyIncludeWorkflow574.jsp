<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="weaver.general.*"%>
<%@page import="java.util.*"%>
<%@page import="com.jiuyi.util.JiuyiUtil"%>
<%
//采购合同付款流程
	int requestid = Util.getIntValue(request.getParameter("requestid"));//请求id
	int workflowid = Util.getIntValue(request.getParameter("workflowid"));//流程id
	int formid = Util.getIntValue(request.getParameter("formid"));//表单id
	int isbill = Util.getIntValue(request.getParameter("isbill"));//表单类型，1单据，0表单
	int nodeid = Util.getIntValue(request.getParameter("nodeid"));//流程的节点id
	//System.out.println("formid:"+formid);
	Map map = new HashMap();
	if(formid!=-1){
	map =JiuyiUtil.getFieldId(formid,"0");//主表数据
	String bukrs = map.get("bukrs").toString();//公司代码
	String lifnr = map.get("lifnr").toString();//供应商编码
	String ebeln = map.get("ebeln").toString();//采购订单合同号
	String zhtje = map.get("zhtje").toString();//合同金额
	String fkjd = map.get("fkjd").toString();//付款阶段
	String yingfje = map.get("yingfje").toString();//应付金额
	String sqfkje = map.get("sqfkje").toString();//申请付款金额
	String yifje = map.get("yifje").toString();//已付金额
	String syje = map.get("syje").toString();//欠结金额
	String syje2 = map.get("syje2").toString();//欠结金额参考值
	String h_yqdp = map.get("yqdp").toString();//要求到票 
	String fkjdbl = map.get("fkjdbl").toString();//付款阶段比例
	String fkjdms = map.get("fkjdms").toString();//付款阶段描述
	String fktj = map.get("fktj").toString();//付款条件
	String khhzh = map.get("khhzh").toString();//开户行账号
	String khhmc = map.get("khhmc").toString();//开户行名称
	String syyfje1 = map.get("syyfje1").toString();//付款阶段对应的剩余应付金额
	String ywy = map.get("ywyck").toString();//业务员
	String ysqje = map.get("ysqje").toString();//已申请金额
	
	map =JiuyiUtil.getFieldId(formid,"1");//明细表1数据
	String zterm = map.get("zterm").toString();//付款条件编码
	String text0 = map.get("text0").toString();//付款条件描述
	String ratpz = map.get("ratpz").toString();//合同付款比例
	String yfje = map.get("yfje").toString();//合同应付金额
	String yfkje = map.get("yfkje").toString();//已付款金额
	String syyfje = map.get("syyfje").toString();//剩余应付金额
	String yqdp = map.get("yqdp").toString();//要求到票
	
	map =JiuyiUtil.getFieldId(formid,"2");     //明细表2数据
	String rkdh  = map.get("rkdh").toString(); //入库单号
	String rkdhh = map.get("rkdhh").toString();//入库单行号
	String wlbh  = map.get("wlbh").toString(); //物料编号
	String wlms  = map.get("wlms").toString(); //物料描述
	String rksl  = map.get("rksl").toString(); //入库数量
	String rkje  = map.get("rkje").toString(); //入库金额
	String rksj  = map.get("rksj").toString(); //入库时间
	String fph   = map.get("fph").toString();  //发票号
	String fpje  = map.get("fpje").toString(); //发票金额（不含税)
	
%>
<script type="text/javascript">
var ismandStr = "<img src='/images/BacoError.gif' align='absmiddle'>";
var bukrs = '<%=bukrs%>';//公司代码
var lifnr = '<%=lifnr%>';//供应商编码
var ebeln = '<%=ebeln%>';//采购订单合同号
var zhtje = '<%=zhtje%>';//合同金额
var fkjd = '<%=fkjd%>';//付款阶段
var yingfje = '<%=yingfje%>';//应付金额
var sqfkje = '<%=sqfkje%>';//申请付款金额
var yifje = '<%=yifje%>';//已付金额
var syje = '<%=syje%>';//欠结金额
var syje2 = '<%=syje2%>';//欠结金额
var h_yqdp = '<%=h_yqdp%>';//要求到票 
var fkjdbl = '<%=fkjdbl%>';//付款阶段比例
var fkjdms = '<%=fkjdms%>';//付款阶段描述
var fktj = '<%=fktj%>';//付款条件
var khhzh = '<%=khhzh%>';//开户行账号	 
var khhmc = '<%=khhmc%>';//开户行名称
var syyfje1 = '<%=syyfje1%>';//付款阶段对应的剩余应付金额
var ywy = '<%=ywy%>';//剩余金额
var ysqje = '<%=ysqje%>';//已申请金额


var zterm = '<%=zterm%>';//付款条件编码
var text0 = '<%=text0%>';//付款条件描述
var ratpz = '<%=ratpz%>';//合同付款比例
var yfje = '<%=yfje%>';//合同应付金额
var yfkje = '<%=yfkje%>';//已付款金额
var syyfje = '<%=syyfje%>';//剩余应付金额
var yqdp = '<%=yqdp%>';//要求到票


var rkdh  = '<%=rkdh%>';//入库单号     
var rkdhh = '<%=rkdhh%>';//入库单行号    
var wlbh  = '<%=wlbh%>';//物料编号     
var wlms  = '<%=wlms%>';//物料描述     
var rksl  = '<%=rksl%>';//入库数量     
var rkje  = '<%=rkje%>';//入库金额     
var rksj  = '<%=rksj%>';//入库时间     
var fph   = '<%=fph%>';//发票号     
var fpje  = '<%=fpje%>';//发票金额（不含税)



var requestid='<%=requestid%>';
var workflowid='<%=workflowid%>';
var ztermValue = jQuery("#field"+fkjd).val();//付款条件编码
var fktjValue =jQuery("#field"+fktj).val();//付款条件


jQuery(function(){
	jQuery("#field"+bukrs).bindPropertyChange(function() {
		getHtfk();
	});
	jQuery("#field"+lifnr).bindPropertyChange(function() {
		getHtfk();
	});
	jQuery("#field"+ebeln).bindPropertyChange(function() {
		getHtfk();
	});
	jQuery("#field"+fkjd).bindPropertyChange(function() {
		getFkjd();
	});
	jQuery("#field"+sqfkje).bindPropertyChange(function() {
		controlSqfkje();
		//getjekz();
	});
	
	jQuery("#field"+fkjd).bindPropertyChange(function() {
		ztermValue = jQuery("#field"+fkjd).val();//付款条件编码
		fktjValue = jQuery("#field"+fktj).val();//付款条件
		//付款阶段为YC09-12是付款阶段描述取付款条件描述
		if("YC09"==ztermValue||"YC10"==ztermValue||"YC11"==ztermValue||"YC12"==ztermValue){
			setFMVal(fkjdms,fktjValue);//付款条件描述
			setFMVal(fkjdbl,"100.000");//付款阶段比例
		}
		getHtfk();
	});
	
});

/* function getjekz(){

	var sqfk1 = parseFloat(jQuery("#field"+syyfje1).val());//申请付款金额参考
	var sqfk = parseFloat(jQuery("#field"+sqfkje).val());//申请付款金额
	 if (isNaN(sqfk)) {
		 sqfk=0.00;
	    }
	var qiejje = parseFloat(jQuery("#field"+syje).val());//欠结金额
	var leijfkje =parseFloat(jQuery("#field"+yifje).val());//累计付款金额

	setFMVal(yifje, leijfkje-sqfk1+sqfk);//抬头已付金额
	setFMVal(syje, qiejje+sqfk1-sqfk);//抬头剩余金额
} */


function getHtfk(){
	 var bukrsvalue = jQuery("#field"+bukrs).val();
	 var ebelnvalue = jQuery("#field"+ebeln).val();
	 var lifnrvalue = jQuery("#field"+lifnr).val();
     if(bukrsvalue=="" || lifnrvalue=="" || ebelnvalue==""){
     	return;
     }else{
    	    jQuery("#field"+bukrs).bindPropertyChange(function() {
    	    	setFMVal(fkjd,"");//付款阶段
    	    	deleteRow(1);
    	    	deleteRow(2);
    		});
    		jQuery("#field"+lifnr).bindPropertyChange(function() {
    			setFMVal(fkjd,"");//付款阶段
    			deleteRow(1);
    			deleteRow(2);
    		});
    		jQuery("#field"+ebeln).bindPropertyChange(function() {
    			setFMVal(fkjd,"");//付款阶段
    			deleteRow(1);
    			deleteRow(2);
    		});
    		
    	jQuery.ajax({
     		url:"/workflow/request/GetSAPDataAjaxFk.jsp",
   	 		async:false,
   	 		type:"post",
   	 		data:{"operation":"htfk","bukrs":bukrsvalue,"lifnr":lifnrvalue,"ebeln":ebelnvalue,"zterm":ztermValue,"requestid":requestid,"workflowid":workflowid},
   	 		success:function(data){
   	 			eval("var obj="+data);
   	 			setFMVal(zhtje,obj.zhtje);
   	 			//setFMVal(yifje,obj.yfkjeoa);
				setFMVal(sqfkje,(obj.sqfkje).toFixed(2));//申请付款金额
				setFMVal(syyfje1,obj.sqfkje);//
				setFMVal(ysqje,obj.ysqje);//已申请金额
   	 			setFMVal(khhmc,obj.KOINH);

   	 			setFMVal(khhzh,obj.ZBANKN);
   	 			setFMVal(ywy,obj.ywy);
	   	 		var dt1Data=obj.dt1;
				setDt1Data(dt1Data);
     	 	},
     		error:function(data){
   	 			alert("读取出错，请联系系统管理员");
   	 		}
   		}); 
    	
     }
}
//明细1赋值
function setDt1Data(dt1Data){
	var lineData;
	deleteRow(0);
	var yfjett=0;
	var yifjett=0;
	var shenqfkje=0;
	shenqfkje = parseFloat(jQuery('#field'+sqfkje).val());
	for(var i=0;i<dt1Data.length;i++){
		lineData=dt1Data[i];
		addRow0(0);
		var rowindex = (jQuery('#indexnum0').val()-1);//行号
		setFMVal(zterm+"_"+rowindex, lineData.zterm);//付款条件编码
		setFMVal(text0+"_"+rowindex, lineData.text1);//付款条件描述
		setFMVal(ratpz+"_"+rowindex, lineData.ratpz);//合同付款比例
		setFMVal(yfje+"_"+rowindex, lineData.yfje);//合同应付金额
		setFMVal(yfkje+"_"+rowindex, lineData.yfkje);//已付款金额
		setFMVal(syyfje+"_"+rowindex, lineData.syyfje);//剩余应付金额
		setFMVal(yqdp+"_"+rowindex, lineData.yqdp);//要求到票
		
		yfjett= yfjett+lineData.yfje;//应付金额
		yifjett=yifjett+lineData.yfkje;//已付金额
	}
		setFMVal(syje2, (yfjett-yifjett).toFixed(2));//欠结金额参考
		setFMVal(yifje, (yifjett+shenqfkje).toFixed(2));//抬头已付金额
		setFMVal(syje, (yfjett-yifjett-shenqfkje).toFixed(2));//欠结金额
} 
function getFkjd(){
	var fkjdvalue = jQuery("#field"+fkjd).val();
    if(fkjdvalue == "" ){
    	return;
    }else{
		var rowindex = (jQuery('#indexnum0').val()-1);//行号
		for(var i=0;i<=rowindex;i++){
			var ztermvalue = jQuery(zterm+"_"+i).val();//付款条件编码
			if(fkjdvalue == ztermvalue){
				setFMVal(yingfje,jQuery("#field"+yfje+"_"+rowindex).val());//应付金额
				//setFMVal(sqfkje,sqfkje);//申请付款金额
				setFMVal(yifje,jQuery("#field"+yfkje+"_"+rowindex).val());//已付金额
				setFMVal(syje,jQuery("#field"+syyfje+"_"+rowindex).val());//剩余金额
				setFMVal(h_yqdp,jQuery("#field"+yqdp+"_"+rowindex).val());//要求到票
				
			}
		}
   	
    }
}
function controlSqfkje(){//控制申请付款金额
	//申请付款金额
	var sqfkjevalue = jQuery("#field"+sqfkje).val();
	//申请付款金额参照值
	var syyfje1value = jQuery("#field"+syyfje1).val();
	//欠结金额参考值
	var syje2value = jQuery("#field"+syje2).val();
	//欠结金额
	var qjjevalue = syje2value-sqfkjevalue;
	var flag = sqfkjevalue-syyfje1value;
	if(flag>0){
		window.top.Dialog.alert("申请付款金额不能大于该阶段合同金额减去已付金额与已申请金额之和！！");
		setFMVal(sqfkje,"0.00");
	}else{
		setFMVal(syje,qjjevalue.toFixed(2));
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