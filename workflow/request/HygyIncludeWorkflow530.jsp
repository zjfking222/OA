<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="weaver.general.*"%>
<%@page import="java.util.*"%>
<%@page import="com.jiuyi.util.JiuyiUtil"%>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"> 
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
	
%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">

jQuery(function(){
	var requestid='<%=requestid%>';
   	getHeSuan(requestid);
});




function getHeSuan(requestid){
    	jQuery.ajax({
     		url:"/workflow/request/GetDataAjax530.jsp",
   	 		async:false,
   	 		type:"post",
   	 		data:{"requestid":requestid},
   	 		success:function(data){
	 	   	 	var mes=eval('('+data+')');
				var dt1Data=mes.dt1;

   	 			console.log("数量"+dt1Data[0].FKIMG);
   	 			console.log("最终含税单价"+dt1Data[0].ZZHSDJ);
   	 			console.log("最终不含税单价"+dt1Data[0].ZZBHSDJ);
   	 			console.log("不含税金额"+dt1Data[0].NETWR);
   	 			console.log("含税金额"+dt1Data[0].AMOUNT);
   	 			console.log("税额"+dt1Data[0].MWSBP);
   	 			console.log("海运费"+dt1Data[0].ZYBF);
   	 			console.log("保险费"+dt1Data[0].YLZD1);

				//测试机
   	 			//setFMVal("13412",dt1Data[0].FKIMG);
   	 			//setFMVal("13413",dt1Data[0].ZZHSDJ);
   	 			//setFMVal("13414",dt1Data[0].ZZBHSDJ);
   	 			//setFMVal("13415",dt1Data[0].NETWR);
   	 			//setFMVal("13416",dt1Data[0].AMOUNT);
   	 			//setFMVal("13417",dt1Data[0].MWSBP);
   	 			//setFMVal("13418",dt1Data[0].ZYBF);
   	 			//setFMVal("13419",dt1Data[0].YLZD1);

				//正式机
   	 			setFMVal("13817",dt1Data[0].FKIMG);
   	 			setFMVal("13818",dt1Data[0].ZZHSDJ);
   	 			setFMVal("13819",dt1Data[0].ZZBHSDJ);
   	 			setFMVal("13820",dt1Data[0].NETWR);
   	 			setFMVal("13821",dt1Data[0].AMOUNT);
   	 			setFMVal("13822",dt1Data[0].MWSBP);
   	 			setFMVal("13823",dt1Data[0].ZYBF);
   	 			setFMVal("13824",dt1Data[0].YLZD1);
   	 			
   	 			console.log("最终本位币不含税金额"+dt1Data[0].ZZNETWR);
   	 			console.log("最终本位币含税金额"+dt1Data[0].ZZAMOUNT);
   	 			console.log("最终本位币税额"+dt1Data[0].ZZMWSBP);
   	 			console.log("最终本位币海运费"+dt1Data[0].ZZZYBF);
   	 			console.log("最终本位币保险费"+dt1Data[0].ZZYLZD1);
   	 			console.log("最终本位币含税单价"+dt1Data[0].ZZBWBHSDJ);
   	 			console.log("最终本位币不含税单价"+dt1Data[0].ZZBWBBHSDJ);

   	 			//测试机
   	 			//setFMVal("13420",dt1Data[0].FKIMG);
   	 			//setFMVal("13423",dt1Data[0].ZZNETWR);
   	 			//setFMVal("13424",dt1Data[0].ZZAMOUNT);
   	 			//setFMVal("13425",dt1Data[0].ZZMWSBP);
   	 			//setFMVal("13426",dt1Data[0].ZZZYBF);
   	 			//setFMVal("13427",dt1Data[0].ZZYLZD1);
   	 			//setFMVal("13421",dt1Data[0].ZZBWBHSDJ);
   	 			//setFMVal("13422",dt1Data[0].ZZBWBBHSDJ);

				//正式机
   	 			setFMVal("13825",dt1Data[0].FKIMG);
   	 			setFMVal("13828",dt1Data[0].ZZNETWR);
   	 			setFMVal("13829",dt1Data[0].ZZAMOUNT);
   	 			setFMVal("13830",dt1Data[0].ZZMWSBP);
   	 			setFMVal("13831",dt1Data[0].ZZZYBF);
   	 			setFMVal("13832",dt1Data[0].ZZYLZD1);
   	 			setFMVal("13826",dt1Data[0].ZZBWBHSDJ);
   	 			setFMVal("13827",dt1Data[0].ZZBWBBHSDJ);


   	 			
   	 			
     	 	},
     		error:function(data){
   	 			alert("读取出错，请联系系统管理员");
   	 		}
   		});   	
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
</script>
<%}%>