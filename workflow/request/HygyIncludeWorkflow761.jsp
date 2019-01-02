<%@page import="com.jiuyi.util.CommonUtil"%>
<%@page import="weaver.general.*"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
 //信用证修改流程  读取BPC模版接口js

    /**
     * 信用证修改
     * @author CYN
     *
     */
new BaseBean().writeLog("信用证修改流程  读取BPC模版接口js ");
int formid=Util.getIntValue(request.getParameter("formid"));
String nodeid=Util.null2String(request.getParameter("nodeid"));
String requestid=Util.null2String(request.getParameter("requestid"));
String workflowid=Util.null2String(request.getParameter("workflowid"));

    HashMap<String, String> map=CommonUtil.getFieldId(formid, "0");//主表数据

    String hth2=map.get("hth2");//合同号
    String hth=map.get("hth");//SAP合同号
    String fhth =map.get("fhth");//分合同号
    String xyzbh=map.get("xyzbh");//信用证编号外证
    String xyzbh1=map.get ("xyzbh1");//信用证编号内证


    String xyzbm=map.get("xyzbm");//信用证编码

    HashMap<String, String> map1=CommonUtil.getFieldId(formid, "1");//明细表1

    String jijia1 =map1.get("jijia");//计价1
    String shlmt1 =map1.get("shlmt");//数量1
    new BaseBean().writeLog(jijia1);

    HashMap<String, String> map2=CommonUtil.getFieldId(formid, "2");//明细表2

    String jijia2 =map2.get("jijia");//计价2
    String shlmt2 =map2.get("shlmt");//数量2
    new BaseBean().writeLog(jijia2);


    %>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">
    /*****************主表********************/
    var hth2 = '<%=hth2%>';//合同号
    var hth='<%=hth%>';//传SAP的合同号
    var fhth = '<%=fhth%>';//分合同号
    var xyzbh='<%=xyzbh%>';//信用证编号外证
    var xyzbh1='<%=xyzbh1%>';//信用证编号内证


    /*****************明细1********************/
    var jijia1= '<%=jijia1%>';//计价1
    var shlmt1='<%=shlmt1%>';//数量1


    /*****************明细2********************/
    var jijia2= '<%=jijia2%>';//计价2
    var shlmt2='<%=shlmt2%>';//数量2

    jQuery(function(){
        //绑定监听
        bindchange();//绑定监听
    });

    //项目编号绑监听
    function bindchange(){
        jQuery("#field"+hth2).bindPropertyChange(function(){
            var hth1 = jQuery("#field"+hth2).val();
            var fhth1 = jQuery("#field"+fhth).val();
            setDetailData(hth1,fhth1);
        });
    }



    //添加数据
    function setDetailData(hth,fhth){
        if(hth===''||fhth===''){

        }else{
            jQuery.ajax({
                url:"/workflow/request/GetSAPDataAjax761.jsp",
                type:"post",
                data:{"hth":hth,"fhth":fhth},
                async: true,
                success:function(data){

                    var mes=eval('('+data+')');
                    //eval("var obj="+data);
                    var dt1Data=mes.dt1;
                    setDt1Data(dt1Data);

                    var dt2Data=mes.dt2;
                    setDt2Data(dt2Data);
                    // alert(JSON.stringify(dt2Data));
                    var hth2v=jQuery("#field"+hth2).val();

                    jQuery("#field9483span").text(hth2v);//测试机


                },
                error:function(e){
                    console.log(e);
                    alert("错误"+e);
                }
            });
        }

    }

    //明细1赋值 信用证信息
    function setDt1Data(dt1Data){
        var lineData,lineDLength;
        deleteRow(0);
        deleteRow(1);
        // alert(JSON.stringify(dt1Data));
        for(var i=0;i<dt1Data.length;i++){
            lineData=dt1Data[i];
                if(lineData.XYZLX==="1"){
                    addRow0(0);
                    setFMVal(jijia1+"_"+0,0);
                    setFMVal(shlmt1+"_"+0,lineData.CoSL);
                    addRow0(0);
                    setFMVal(jijia1+"_"+1,1);
                    setFMVal(shlmt1+"_"+1,lineData.CuSL);
                    addRow0(0);
                    setFMVal(jijia1+"_"+2,2);
                    setFMVal(shlmt1+"_"+2,lineData.GeSL);
                    addRow0(0);
                    setFMVal(jijia1+"_"+3,3);
                    setFMVal(shlmt1+"_"+3,lineData.NiSL);
                    addRow0(0);
                    setFMVal(jijia1+"_"+4,4);
                    setFMVal(shlmt1+"_"+4,lineData.LiSL);
                    addRow0(0);
                    setFMVal(jijia1+"_"+5,5);
                    setFMVal(shlmt1+"_"+5,lineData.MnSL);

                }else {
                    // alert(JSON.stringify(lineData))
                    addRow1(1);
                    setFMVal(jijia2+"_"+0,0);
                    setFMVal(shlmt2+"_"+0,lineData.CoSL);
                    addRow1(1);
                    setFMVal(jijia2+"_"+1,1);
                    setFMVal(shlmt2+"_"+1,lineData.CuSL);
                    addRow1(1);
                    setFMVal(jijia2+"_"+2,2);
                    setFMVal(shlmt2+"_"+2,lineData.GeSL);
                    addRow1(1);
                    setFMVal(jijia2+"_"+3,3);
                    setFMVal(shlmt2+"_"+3,lineData.NiSL);
                    addRow1(1);
                    setFMVal(jijia2+"_"+4,4);
                    setFMVal(shlmt2+"_"+4,lineData.LiSL);
                    addRow1(1);
                    setFMVal(jijia2+"_"+5,5);
                    setFMVal(shlmt2+"_"+5,lineData.MnSL);
                }
                // alert(jQuery("tr[_target='datarow']").length);//获取明细表行数


        }
    }


    //主表赋值
    function setDt2Data(dt2Data){
        var lineData;

        // alert("输出信用证编码内容"+jQuery("#field"+xyzbm).val());
        for(var i=0;i<dt2Data.length;i++){
            lineData=dt2Data[i];
            // alert("linedata"+lineData.HB);
                if(lineData.XYZLX==="1") {                                   //外证
                    setFMVal(xyzbh, lineData.XYZBH);//信用证编码
                    setFMVal("9372",lineData.KZH);//开证行
                    setFMVal("9496",lineData.KZDW);//开证单位
                    setFMVal("9498",lineData.GYS);//供应商
                    setFMVal("9487",lineData.HB);//货币
                    setFMVal("9376",lineData.XYZXZ);//信用证性质
                    setFMVal("9377",lineData.DQTS);//到期天数
                    setFMVal("9489",lineData.KZRQ);//开证日期
                    setFMVal("10142",lineData.DQRQ);//到期日期
                    if(lineData.HB==="USD"){
                        setFMVal("9361",lineData.HL);//汇率
                        setFMVal("9439",lineData.KZJE);//开证金额
                    }else if(lineData.HB==="EUR"){
                        setFMVal("13657",lineData.HL);//汇率
                        setFMVal("13655",lineData.KZJE);//开证金额
                    }else {
                        setFMVal("9361",lineData.HL);//汇率
                        setFMVal("9364",lineData.KZJE);//开证金额
                    }

                    setFMVal("9353",lineData.KZDWMS);//开证单位描述
                    setFMVal("9354",lineData.GYSMS);//供应商描述
                    setFMVal("9362",lineData.RZXS);//融资系数
                    setFMVal("9509",lineData.JJY);//计价月
                    setFMVal("13682",lineData.SDLF);//收代理方
                }else{                                                        //内证
                    setFMVal(xyzbh1, lineData.XYZBH);//信用证编码
                    setFMVal("9373",lineData.KZH);//开证行
                    setFMVal("9497",lineData.KZDW);//开证单位
                    setFMVal("9499",lineData.GYS);//供应商
                    setFMVal("9488",lineData.HB);//货币
                    setFMVal("9378",lineData.XYZXZ);//信用证性质
                    setFMVal("9379",lineData.DQTS);//到期天数
                    setFMVal("9490",lineData.KZRQ);//开证日期
                    setFMVal("10143",lineData.DQRQ);//到期日期
                    if(lineData.HB==="USD"){
                        setFMVal("9368",lineData.HL);//汇率
                        setFMVal("9440",lineData.KZJE);//开证金额
                    }else if(lineData.HB==="EUR"){
                        setFMVal("13658",lineData.HL);//汇率
                        setFMVal("13656",lineData.KZJE);//开证金额
                    }else{
                        setFMVal("9368",lineData.HL);//汇率
                        setFMVal("9371",lineData.KZJE);//开证金额
                    }
                    setFMVal("9481",lineData.KZDWMS);//开证单位描述
                    setFMVal("9482",lineData.GYSMS);//供应商描述
                    setFMVal("9369",lineData.RZXS);//融资系数
                    setFMVal("9510",lineData.JJY);//计价月
                    setFMVal("13683",lineData.SDLF);//收代理方
                }

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

