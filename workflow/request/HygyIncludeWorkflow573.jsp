<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="weaver.general.*" %>
<%@page import="java.util.*" %>
<%@page import="com.jiuyi.util.JiuyiUtil" %>
<%
//票到付款申请流程
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
	String tjrq = map.get("tjrq").toString();//提交日期
	String yingfjett = map.get("yingfje").toString();//应付金额
	String sqfkjett = map.get("je").toString();//申请付款金额
	//String yifjett = map.get("yifje").toString();//已付金额
	//String syjett = map.get("syje").toString();//剩余金额
	String khhzh = map.get("khhzh").toString();//开户行账号
	String khhmc = map.get("khhmc").toString();//开户行名称
	String ywy = map.get("ywy").toString();//业务员
	
	map =JiuyiUtil.getFieldId(formid,"1");//明细表1数据
	String belnr_1 = map.get("belnr").toString();//发票号
	String gjahr_1 = map.get("gjahr").toString();//年度
	String zfbdt = map.get("zfbdt").toString();//基准日
	String zterm = map.get("zterm").toString();//付款条件
	String text1 = map.get("text1").toString();//付款条件描述
	String zdqri = map.get("zdqri").toString();//到期日
	String shkzg_1 = map.get("shkzg").toString();//借贷项
	String zfpje = map.get("zfpje").toString();//发票金额
	String yifje = map.get("yifje").toString();//已付金额
	String yingfje = map.get("yingfje").toString();//应付金额

	String ysqje = map.get("ysqje").toString();//已申请金额
	String sqfkje = map.get("sqfkje").toString();//申请付款金额
	String mxywy = map.get("mxywy").toString();//业务员
	//String fkfs = map.get("shlmt").toString();//付款方式
	
	map =JiuyiUtil.getFieldId(formid,"2");//明细表2数据
	String belnr_2 = map.get("belnr").toString();//发票号
	String buzei = map.get("buzei").toString();//发票行项目
	String gjahr_2 = map.get("gjahr").toString();//年度
	String matnr = map.get("matnr").toString();//物料
	String maktx = map.get("maktx").toString();//物料描述
	String shkzg_2 = map.get("shkzg").toString();//借贷项
	String menge = map.get("menge").toString();//数量
	String meins = map.get("meins").toString();//单位
	
%>
<script type="text/javascript">
var ismandStr = "<img src='/images/BacoError.gif' align='absmiddle'>";
var bukrs = '<%=bukrs%>';//公司代码
var lifnr = '<%=lifnr%>';//供应商编码
var tjrq = '<%=tjrq%>';//提交日期
var yingfjett = '<%=yingfjett%>';//应付金额
var sqfkjett = '<%=sqfkjett%>';//申请付款金额
var ywy = '<%=ywy%>';//业务员
var khhzh = '<%=khhzh%>';//开户行账号	 
var khhmc = '<%=khhmc%>';//开户行名称


var belnr_1 = '<%=belnr_1%>';//发票号
var gjahr_1 = '<%=gjahr_1%>';//年度
var zfbdt = '<%=zfbdt%>';//基准日
var zterm = '<%=zterm%>';//付款条件
var text1 = '<%=text1%>';//付款条件描述
var zdqri = '<%=zdqri%>';//到期日
var shkzg_1 = '<%=shkzg_1%>';//借贷项
var zfpje = '<%=zfpje%>';//发票金额
var yifje  = '<%=yifje%>';//已付金额
var yingfje  = '<%=yingfje%>';//应付金额
var sqfkje  = '<%=sqfkje%>';//申请付款金额
var ysqje  = '<%=ysqje%>';//已申请金额
var mxywy  = '<%=mxywy%>';//明细业务员


var belnr_2 = '<%=belnr_2%>';//发票号
var buzei = '<%=buzei%>';//发票行项目
var gjahr_2 = '<%=gjahr_2%>';//年度
var matnr = '<%=matnr%>';//物料
var maktx = '<%=maktx%>';//物料描述
var shkzg_2 = '<%=shkzg_2%>';//借贷项
var menge = '<%=menge%>';//数量
var meins = '<%=meins%>';//单位

var requestid='<%=requestid%>';
var workflowid='<%=workflowid%>';
var row1len = 0;

jQuery(function(){
	jQuery("#field"+bukrs).bindPropertyChange(function() {
		getPdfk();
	});
	jQuery("#field"+lifnr).bindPropertyChange(function() {
		getPdfk();
	});
	jQuery("#field"+ywy).bindPropertyChange(function() {
		getPdfk();
	});
	controlSqfkje();
/* 	$("input[name^='field"+sqfkje+"_']").change(function() {
		
		var attr_name= $(this).attr("name");
		alert("attr_name"+attr_name);
		var attr_last_index=attr_name.substring(attr_name.length-1,attr_name.length);
		alert("attr_last_index"+attr_last_index);
		var indexnum = jQuery(this).attr("name").split("_")[1];
		//var indexnum = 2;
		indexnum = 0;
		controlSqfkje(indexnum);
	}) */
});


function controlSqfkje(){//控制申请付款金额
	jQuery("input[name ^='field"+sqfkje+"_']").change(function(){
    	//得到最后一位
    	var attr_name=jQuery(this).attr("id");
    	attr_last_index=attr_name.substring(attr_name.length-1,attr_name.length);
    	var price = $(this).val();
    	var fpje = $("#field"+zfpje+"_"+attr_last_index+"").val();   
    	var sin_price = $("#field"+yifje+"_"+attr_last_index+"").val();   
    	var sin1_price = $("#field"+ysqje+"_"+attr_last_index+"").val();   
          var  def = fpje-price - sin_price-sin1_price  ;
     if(def<0){  
    	 setFMVal(sqfkje+"_"+attr_last_index,"0.00");
    	 window.top.Dialog.alert("申请付款金额不能大于发票金额减去已付金额与已申请金额之和！");	
 	}
	});	
}
function getPdfk(){
	 var bukrsvalue = jQuery("#field"+bukrs).val();
	 var lifnrvalue = jQuery("#field"+lifnr).val();
	 var tjrqvalue  = jQuery("#field"+tjrq).val();
	 var ywyvalue  = jQuery("#field"+ywy).val();
	 
     if(bukrsvalue=="" || lifnrvalue=="" || ywyvalue==""){
     	return;
     }else{
    	jQuery.ajax({
     		url:"/workflow/request/GetSAPDataAjaxFk.jsp",
   	 		async:false,
   	 		type:"post",
   	 		data:{"operation":"pdfk","bukrs":bukrsvalue,"lifnr":lifnrvalue,"tjrq":tjrqvalue,"ywy":ywyvalue,"workflowid":workflowid},
   	 		success:function(data){
   	 			eval("var obj="+data);
   	 			setFMVal(khhmc,obj.KOINH);
	 			setFMVal(khhzh,obj.ZBANKN);
	   	 		var dt1Data=obj.dt1;
				setDt1Data(dt1Data);
				var dt2Data=obj.dt2;
				setDt2Data(dt2Data);
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

	var sqfkjettvalue=0;
	var sqfkjemx=0;
	var yingfjettvalue=0;
	var yingfjemx=0;
	var yifjettvalue=0;
	var yifjemx=0;
	for(var i=0;i<dt1Data.length;i++){	
		lineData=dt1Data[i];
		addRow0(0);
		var rowindex = (jQuery('#indexnum0').val()-1);//行号
		setFMVal(belnr_1+"_"+rowindex, lineData.BELNR);//发票号
		setFMVal(gjahr_1+"_"+rowindex, lineData.GJAHR);//年度
		setFMVal(zfbdt+"_"+rowindex, lineData.ZFBDT);//基准日
		setFMVal(zterm+"_"+rowindex, lineData.ZTERM);//付款条件
		setFMVal(text1+"_"+rowindex, lineData.TEXT1);//付款条件描述
		setFMVal(zdqri+"_"+rowindex, lineData.ZDQRI);//到期日
		setFMVal(shkzg_1+"_"+rowindex, lineData.SHKZG);//借贷项
		setFMVal(zfpje+"_"+rowindex, lineData.ZFPJE);//发票金额
		setFMVal(ysqje+"_"+rowindex, lineData.ysqje);//已申请金额
		setFMVal(sqfkje+"_"+rowindex,lineData.ZFPJE- lineData.yifje-lineData.ysqje);//申请付款金额
		setFMVal(yifje+"_"+rowindex, lineData.yifje);//已付金额
		setFMVal(yingfje+"_"+rowindex, lineData.yingfje);//应付金额
		setFMVal(mxywy+"_"+rowindex, lineData.EKNAM);//业务员
		
		sqfkjemx = parseFloat(lineData.ZFPJE- lineData.yifje-lineData.ysqje);
		sqfkjettvalue = parseFloat(sqfkjettvalue);
		sqfkjettvalue = sqfkjettvalue + sqfkjemx;
		
		yingfjemx = parseFloat(lineData.yingfje);
		yingfjettvalue = parseFloat(yingfjettvalue);
		yingfjettvalue= yingfjettvalue+yingfjemx;//应付金额抬头
		
		yifjemx = parseFloat(lineData.yifje);
		yifjettvalue = parseFloat(yifjettvalue);
		yifjettvalue=yifjettvalue+yifjemx;//已付金额抬头
		
		row1len = parseInt(row1len);
		row1len = row1len+1;
		/* jQuery("#field"+sqfkje+"_"+rowindex).bindPropertyChange(function() {
			var indexnum = jQuery(this).attr("name").split("_")[1];
			controlSqfkje(indexnum);
		}) */
	}
	
	setFMVal(sqfkjett, sqfkjettvalue);//申请付款金额
	setFMVal(yingfjett, yingfjettvalue);//抬头应付金额
	//setFMVal(yifjett, yifjettvalue);//抬头已付金额
	controlSqfkje();

	//setFMVal(syjett, yingfjett-yifjett);//抬头剩余金额
} 

//明细2赋值
function setDt2Data(dt2Data){
	var lineData;
	deleteRow(1);
	for(var i=0;i<dt2Data.length;i++){
		lineData=dt2Data[i];
		addRow1(1);
		var rowindex = (jQuery('#indexnum1').val()-1);//行号
		setFMVal(belnr_2+"_"+rowindex, lineData.BELNR);//发票号
		setFMVal(buzei+"_"+rowindex, lineData.BUZEI);//发票行项目
		setFMVal(gjahr_2+"_"+rowindex, lineData.GJAHR);//年度
		setFMVal(matnr+"_"+rowindex, lineData.MATNR);//物料
		setFMVal(maktx+"_"+rowindex, lineData.MAKTX);//物料描述
		setFMVal(shkzg_2+"_"+rowindex, lineData.SHKZG);//借贷项
		setFMVal(menge+"_"+rowindex, lineData.MENGE);//数量
		setFMVal(meins+"_"+rowindex, lineData.MEINS);//单位
	}
} 
function checkJe(){
	var jevalue = "";
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