<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.jiuyi.util.CommonUtil" %>
<%@page import="weaver.general.*" %>
<%@page import="java.util.*" %>
<%@page import="com.jiuyi.util.JiuyiUtil" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%
    int formid = Util.getIntValue(request.getParameter("formid"));
    Map<String, String> map;
    Map<String, String> map1;

    if (formid != -1) {

        map = CommonUtil.getFieldId(formid, "0");//主表数据
        String wlbmz = map.get("wlbm");//物料编码
        String cpmcz = map.get("cpmc");//产品名称
        String ggz = map.get("gg");//规格
        String wlmsz =map.get("wlms");//物料描述
        String myhl=map.get("myhl");//名义汇率
        String gsUSD=map.get("gsdqzdjmy");//公司当期指导价美元
        String gsRMB=map.get("gsdqzdjrmb");//公司当期指导价人民币
        String fgUSD=map.get("fgldspjmy");//分管领导审批价美元
        String fgRMB=map.get("fgldspjrmb");///分管领导审批价人民币
        String cpUSD=map.get("cpnbdjmy");//产品拟报单价美元
        String cpRMB=map.get("cpnbdjrmb");//产品拟报单价人民币
        String xsUSD=map.get("xsfymy");//销售费用美元
        String xsRMB=map.get("xsfyrmb");//销售费用人民币
        String ysUSD=map.get("yjsmy");//佣金+佣金税usd
        String ysRMB=map.get("yjsrmb");//佣金+佣金税rmb
        String sl=map.get("sl");//数量
        String zje=map.get("zje");//总金额
        String bjlb=map.get("bjlb");//报价类别
        String jjUSD=map.get("cpnxsjjmy");//产品拟销售净价usd
        String jjRMB=map.get("cpnxsjjrmb");//产品拟销售净价rmb
        String htRMB=map.get("htcjjrmb");//合同成交价人民币
        String htUSD=map.get("htcjj");//合同成交价美元


        map1 = CommonUtil.getFieldId(formid, "1");//明细1
        String wlbm = map1.get("wlbm");//物料编码
        String cpmc = map1.get("cpmc");//产品名称
        String gg = map1.get("gg");//规格
        String wlms =map1.get("wlms");//物料描述


%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">
    /**
     * SD-客户订单计划  长单报价  报价  佣金申请
     * @author CYN
     *
     */
    /*****************主表********************/
    var wlbmz = '<%=wlbmz%>';//产品名称
    var cpmcz = '<%=cpmcz%>';//产品名称
    var ggz = '<%=ggz%>';//规格
    var wlmsz = '<%=wlmsz%>';//物料描述
    var sl='<%=sl%>';//数量
    var zje='<%=zje%>';//总金额
    var myhl='<%=myhl%>';//名义汇率
    var bjlb='<%=bjlb%>';//报价类别
    var gsUSD='<%=gsUSD%>';//公司当期指导价美元
    var gsRMB='<%=gsRMB%>';//公司当期指导价人民币
    var fgUSD='<%=fgUSD%>';//分管领导审批价美元
    var fgRMB='<%=fgRMB%>';//分管领导审批价人民币
    var cpUSD='<%=cpUSD%>';//产品拟报单价美元
    var cpRMB='<%=cpRMB%>';//产品拟报单价人民币
    var xsUSD='<%=xsUSD%>';//销售费用美元
    var xsRMB='<%=xsRMB%>';//销售费用人民币
    var ysUSD='<%=ysUSD%>';//佣金+佣金税usd
    var ysRMB='<%=ysRMB%>';//佣金+佣金税rmb
    var jjRMB='<%=jjRMB%>';//产品拟销售净价rmb
    var jjUSD='<%=jjUSD%>';//产品拟销售净价USD
    var htRMB='<%=htRMB%>';//合同成交价rmb
    var htUSD='<%=htUSD%>';//合同成交价USD




    /*****************明细表********************/

    var wlbm = '<%=wlbm%>';//产品名称
    var cpmc = '<%=cpmc%>';//产品名称
    var gg = '<%=gg%>';//规格
    var wlms = '<%=wlms%>';//物料描述


    jQuery(function(){
        //绑定监听
        bindchange();//绑定监听
    });


    function bindchange(){
        jQuery("#field"+wlbmz).bindPropertyChange(function(){//物料描述
            // alert("物料描述"+wlmsz);
            var ret=jQuery("#field"+wlmsz).val().split("\\");
            // alert("ret"+ret);
            setFMVal(cpmcz,ret[0]);
            setFMVal(ggz,ret[1]);
        });

        jQuery("#field"+gsRMB).bindPropertyChange(function(){//公司当期指导价人民币
            if (jQuery("#field"+bjlb).val()==1) {//国际
                setFMVal(gsUSD, (jQuery("#field" + gsRMB).val() / jQuery("#field" + myhl).val()).toFixed(2));//公司当期指导价
            }else{
                setFMVal(gsUSD,'0.00');
            }
        });
        jQuery("#field"+fgRMB).bindPropertyChange(function(){//分管领导审批价人民币
            if (jQuery("#field"+bjlb).val()==1) {
                setFMVal(fgUSD, (jQuery("#field" + fgRMB).val() / jQuery("#field" + myhl).val()).toFixed(2));//分管领导审批价
            }else {
                setFMVal(fgUSD,'0.00');
            }
        });
        jQuery("#field"+cpUSD).bindPropertyChange(function(){//产品拟报单价美元
            if (jQuery("#field"+bjlb).val()==0){//国内
                setFMVal(zje,((jQuery("#field"+cpRMB).val()*jQuery("#field"+sl).val())/10000).toFixed(2)+'万元');//国内总金额
            } else if(jQuery("#field"+bjlb).val()==1) {//国际
                setFMVal(zje,((jQuery("#field"+cpUSD).val()*jQuery("#field"+sl).val())/10000).toFixed(2)+'万美元');//国外总金额
                setFMVal(cpRMB, (jQuery("#field" + cpUSD).val() * jQuery("#field" + myhl).val()).toFixed(2));//产品拟报单价
            }
        });
        jQuery("#field"+cpRMB).bindPropertyChange(function(){//产品拟报单价人民币
            if (jQuery("#field"+bjlb).val()==0){
                setFMVal(zje,((jQuery("#field"+cpRMB).val()*jQuery("#field"+sl).val())/10000).toFixed(2)+'万元');//国内总金额
                setFMVal(cpUSD,'0.00');
            } else if(jQuery("#field"+bjlb).val()==1) {
                setFMVal(zje,((jQuery("#field"+cpUSD).val()*jQuery("#field"+sl).val())/10000).toFixed(2)+'万美元');//国外总金额
            }
        });
        jQuery("#field"+xsRMB).bindPropertyChange(function(){//销售费用人民币
            if (jQuery("#field"+bjlb).val()==1) {
                setFMVal(xsUSD, ((jQuery("#field" + xsRMB).val() / jQuery("#field" + myhl).val())).toFixed(2));//销售费用
            }else {
                setFMVal(xsUSD,'0.00');
                setFMVal(jjUSD,'0.00');
                setFMVal(ysUSD,'0.00');
            }
        });

        jQuery("#field"+ysUSD).bindPropertyChange(function(){//佣金+佣金税usd
            setFMVal(ysRMB, ((jQuery("#field" + ysUSD).val() * jQuery("#field" + myhl).val())).toFixed(2));//佣金+佣金税
            var cpusd=jQuery("#field"+cpUSD).val();
            var xsusd=jQuery("#field"+xsUSD).val();
            var ysusd=jQuery("#field"+ysUSD).val();
            setFMVal(jjRMB,((cpusd-xsusd-ysusd)*jQuery("#field"+myhl).val()).toFixed(2));//产品拟销售净价=产品拟报单价-销售费用-(佣金+佣金税)
            setFMVal(jjUSD,(cpusd-xsusd-ysusd).toFixed(2));//产品拟报单价-销售费用-(佣金+佣金税)
        });
        if (jQuery("#field"+bjlb).val()==0) {
            jQuery("#field"+ysRMB).bindPropertyChange(function(){//佣金+佣金税rmb
                var ysrmb=jQuery("#field"+ysRMB).val();
                var cprmb=jQuery("#field"+cpRMB).val();
                var xsrmb=jQuery("#field"+xsRMB).val();
                // alert("cprmb"+cprmb+"xsrmb"+xsrmb+"ysrmb"+ysrmb);
                setFMVal(jjRMB,(cprmb-xsrmb-ysrmb).toFixed(2));//产品拟报单价-销售费用-(佣金+佣金税)
            });
        }
        jQuery("#field"+sl).bindPropertyChange(function(){
            if (jQuery("#field"+bjlb).val()==0){
                setFMVal(zje,((jQuery("#field"+cpRMB).val()*jQuery("#field"+sl).val())/10000).toFixed(2)+'万元');//国内总金额
            } else if(jQuery("#field"+bjlb).val()==1) {
                setFMVal(zje,((jQuery("#field"+cpUSD).val()*jQuery("#field"+sl).val())/10000).toFixed(2)+'万美元');//国外总金额
            }
        });
        jQuery("#field"+bjlb).bindPropertyChange(function(){
            // alert(jQuery("#field"+bjlb).val())
            if (jQuery("#field"+bjlb).val()==0){//国内
                setFMVal(zje,((jQuery("#field"+cpRMB).val()*jQuery("#field"+sl).val())/10000).toFixed(2)+'万元');//国内总金额
                setFMVal(myhl,'0.00');
            } else if(jQuery("#field"+bjlb).val()==1) {//国际
                setFMVal(zje,((jQuery("#field"+cpUSD).val()*jQuery("#field"+sl).val())/10000).toFixed(2)+'万美元');//国外总金额
            }
        });

    }


    jQuery(".excelDetailTable tr td:nth-child(4) button").live("click", function () {//物料描述

        var lineId = $(this).attr("id").split("_")[1];

        if (jQuery("#field"+wlbm +"_"+ lineId).attr("flag") === undefined &&
            jQuery("#field"+wlbm +"_"+ lineId).attr("flag") !== "true") {


            jQuery("#field"+wlms+"_"+ lineId).bindPropertyChange(function () {//物料描述值转变

                if (jQuery("#field" + wlms + "_" + lineId).val() == "")
                    return;

                var ret=jQuery("#field"+wlms +"_"+ lineId).val().split("\\");
                // alert("ret "+ret);

                // jQuery("#field" + cpmc + "_" + lineId + "span").text(ret[0]);//赋值产品名称
                jQuery("#field" + cpmc + "_" + lineId).val(ret[0]);

                // jQuery("#field" + gg + "_" + lineId + "span").text(ret[1]);//赋值规格
                jQuery("#field" + gg + "_" + lineId).val(ret[1]);

            });
            jQuery("#field"+wlms +"_"+ lineId).attr("_flag","true");
        }

    });



    function deleteRow(groupid) {
        if (jQuery('#indexnum' + groupid).val() > 0) {
            try {
                jQuery("input[type='checkbox'][name='check_node_" + groupid + "']").each(function () {
                    jQuery(this).attr({'checked': 'checked'});
                });
                delRowFun(groupid, true);
                jQuery('#indexnum' + groupid).val(0);
            } catch (e) {
            }
        }
    }

    //删除行方法
    function delRowFun(groupid, isfromsap) {
        var oTable = jQuery("table#oTable" + groupid);
        var checkObj = oTable.find("input[name='check_node_" + groupid + "']:checked");
        if (isfromsap || checkObj.size() > 0) {
            if (isfromsap || isdel()) {
                var curindex = parseInt($G("nodesnum" + groupid).value);
                var submitdtlStr = $G("submitdtlid" + groupid).value;
                var deldtlStr = $G("deldtlid" + groupid).value;
                checkObj.each(function () {
                    var rowIndex = jQuery(this).val();
                    var belRow = oTable.find("tr[_target='datarow'][_rowindex='" + rowIndex + "']");
                    var keyid = belRow.find("input[name='dtl_id_" + groupid + "_" + rowIndex + "']").val();
                    //提交序号串删除对应行号
                    var submitdtlArr = submitdtlStr.split(',');
                    submitdtlStr = "";
                    for (var i = 0; i < submitdtlArr.length; i++) {
                        if (submitdtlArr[i] != rowIndex)
                            submitdtlStr += "," + submitdtlArr[i];
                    }
                    if (submitdtlStr.length > 0 && submitdtlStr.substring(0, 1) === ",")
                        submitdtlStr = submitdtlStr.substring(1);
                    //已有明细主键存隐藏域
                    if (keyid != "")
                        deldtlStr += "," + keyid;
                    //IE下需先销毁附件上传的object对象，才能remove行
                    try {
                        belRow.find("td[_fieldid][_fieldtype='6_1'],td[_fieldid][_fieldtype='6_2']").each(function () {
                            var swfObj = eval("oUpload" + jQuery(this).attr("_fieldid"));
                            swfObj.destroy();
                        });
                    } catch (e) {
                    }
                    belRow.remove();
                    curindex--;
                });
                $G("submitdtlid" + groupid).value = submitdtlStr;
                if (deldtlStr.length > 0 && deldtlStr.substring(0, 1) === ",")
                    deldtlStr = deldtlStr.substring(1);
                $G("deldtlid" + groupid).value = deldtlStr;
                $G("nodesnum" + groupid).value = curindex;
                //序号重排
                oTable.find("input[name='check_node_" + groupid + "']").each(function (index) {
                    var belRow = oTable.find("tr[_target='datarow'][_rowindex='" + jQuery(this).val() + "']");
                    belRow.find("span[name='detailIndexSpan" + groupid + "']").text(index + 1);
                });
                oTable.find("input[name='check_all_record']").attr("checked", false);
                //表单设计器，删除行触发公式计算
                triFormula_delRow(groupid);
                try {
                    calSum(groupid);
                } catch (e) {
                }
                try {		//自定义函数接口,必须在最后，必须try-catch
                    eval("_customDelFun" + groupid + "()");
                } catch (e) {
                }
            }
        } else {
            var language = readCookie("languageidweaver");
            top.Dialog.alert(SystemEnv.getHtmlNoteName(3529, language));
            return;
        }
    }

    function setFMVal(id, v, h) {
        var ismandStr = "<img src='/images/BacoError.gif' align='absmiddle'>";
        var x = jQuery('#field' + id);
        if (x.length > 0) {
            x.attr({'value': v});
            if (x.attr('type') == 'hidden' || document.getElementById('field' + id).style.display == 'none') {
                jQuery('#field' + id + 'span').html('');
                if (arguments.length > 2) {
                    jQuery('#field' + id + 'span').html(h);
                } else {
                    jQuery('#field' + id + 'span').html(v);
                }
            } else {
                var viewtype = x.attr('viewtype');
                if (viewtype == 1 && (!v || v == '')) {
                    jQuery('#field' + id + 'span').html(ismandStr);
                } else {
                    jQuery('#field' + id + 'span').html('');
                }
            }
        }
    }

</script>
<%}%>