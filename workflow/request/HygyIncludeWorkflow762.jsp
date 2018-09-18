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
        String lx = map.get("shengcwlkc");//类型
        String wllx = map.get("wllx");//物料类型
//        map1=JiuyiUtil.getFieldId(formid,"1");//明细表数据
        map1 = CommonUtil.getFieldId(formid, "1");//明细1
        String gc = map1.get("werks");//工厂
        String kcdd = map1.get("lgort");//库存地点


%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">
    //MM-生产性物料扩充
    /*****************主表********************/

    var wllx = '<%=wllx%>';//物料类型
    var lx = '<%=lx%>';//类型

    /*****************明细表********************/

    var gc = '<%=gc%>';//工厂
    var kcdd = '<%=kcdd%>';//库存地点


    setFMVal(wllx, "");


    jQuery("#field" +<%=lx%>).bindPropertyChange(function () {
        var lxvaule = jQuery("#field" + lx).val();


        if (lxvaule === "1") {
            var wllxvalue = "5CP";
        } else if (lxvaule === "2") {
            var wllxvalue = "4BCP";
        } else if (lxvaule === "3") {
            var wllxvalue = "2FL";
        } else if (lxvaule === "4") {
            var wllxvalue = "3YCL";
        } else if (lxvaule === "5") {
            var wllxvalue = "8STY";
        }

        setFMVal(wllx, wllxvalue);

    });

    jQuery(".excelDetailTable tr td:nth-child(3) button").live("click", function () {
        var lineId = $(this).attr("id").split("_")[1];
        jQuery("#field12025_"+lineId).bindPropertyChange(function(){
            jQuery("#field9018_" + lineId + "span").text("");
            jQuery("#field9018_" + lineId).val("");
        });
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