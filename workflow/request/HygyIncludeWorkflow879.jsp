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
    int nodeid = Util.getIntValue(request.getParameter("nodeid"));
    Map<String, String> map;

    if (formid != -1) {

        map = CommonUtil.getFieldId(formid, "0");//主表数据
        String yj = map.get("yj");//迎接
        String cg = map.get("cg");//参观
        String hy = map.get("hy7");//会议
        String cy = map.get("cy");//餐饮
        String zs = map.get("zs1");//住宿
        String pc = map.get("pc");//派车
        String qt = map.get("qt6");//其他


%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">
    /**
     * 接待计划审批
     * @author CYN
     *
     */
    /*****************主表********************/
    var yj = '<%=yj%>';//迎接
    var cg = '<%=cg%>';//参观
    var hy = '<%=hy%>';//会议
    var cy = '<%=cy%>';//餐饮
    var zs = '<%=zs%>';//住宿
    var pc = '<%=pc%>';//派车
    var qt = '<%=qt%>';//其他
    var nodeid='<%=nodeid%>';//节点id


    jQuery(function() {
        //绑定监听

        // alert($(".excelMainTable tr").length-2);
        // nodeid!=6394&&nodeid!=6397&&nodeid!=6393&&nodeid!=6388测试机
        //nodeid!=6796&&nodeid!=6802&&nodeid!=6805&&nodeid!=6801正式机
        // alert(nodeid);
        if(nodeid!=6796&&nodeid!=6802&&nodeid!=6805&&nodeid!=6801) {
            $("#detailDiv_4").addClass("edesign_hide");//餐饮需求表
            $("#detailDiv_5").addClass("edesign_hide");//住宿需求表
            $("#detailDiv_2").addClass("edesign_hide");//派车接表
            $("#detailDiv_3").addClass("edesign_hide");//派车送表
            $("#detailDiv_0").addClass("edesign_hide");//礼品表
            for (var i = 12; i < $(".excelMainTable tr").length-2; i++) {
                $(".excelMainTable tr:eq(" + i + ")").addClass("edesign_hide");
            }
        }
        //正式机6803
        //测试机6395
        if(nodeid==6803){

            bindchange();//绑定监听

        }
        else {
            moveHide();
        }

    });


    function bindchange(){
        jQuery("#field"+yj).on('change',function(){//迎接
            if($("#field"+yj).is(":checked")){

                $(".excelMainTable tr:eq(12)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(13)").addClass("edesign_hide");
            }else {
                $(".excelMainTable tr:eq(12)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(13)").removeClass("edesign_hide");
            }
        });
        jQuery("#field"+cg).on('change',function(){//参观
            if($("#field"+cg).is(":checked")){
                $(".excelMainTable tr:eq(14)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(15)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(16)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(17)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(18)").addClass("edesign_hide");
            }else {
                $(".excelMainTable tr:eq(14)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(15)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(16)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(17)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(18)").removeClass("edesign_hide");
            }
        });
        jQuery("#field"+hy).on('change',function(){//会议
            if($("#field"+hy).is(":checked")){
                $(".excelMainTable tr:eq(19)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(20)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(21)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(22)").addClass("edesign_hide");
            }else {
                $(".excelMainTable tr:eq(19)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(20)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(21)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(22)").removeClass("edesign_hide");
            }
        });
        jQuery("#field"+cy).on('change',function(){//餐饮
            if($("#field"+cy).is(":checked")){

                $(".excelMainTable tr:eq(23)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(24)").addClass("edesign_hide");
                $("#detailDiv_4").addClass("edesign_hide");//餐饮需求表

                $(".excelMainTable tr:eq(25)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(26)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(27)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(28)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(29)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(30)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(31)").addClass("edesign_hide");
            }else {

                $(".excelMainTable tr:eq(23)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(24)").removeClass("edesign_hide");
                $("#detailDiv_4").removeClass("edesign_hide");//餐饮需求表
                $("#detailDiv_4 tr").removeClass("edesign_hide");//餐饮需求表

                $(".excelMainTable tr:eq(25)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(26)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(27)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(28)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(29)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(30)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(31)").removeClass("edesign_hide");
            }
        });
        jQuery("#field"+zs).on('change',function(){//住宿
            if($("#field"+zs).is(":checked")){

                $(".excelMainTable tr:eq(32)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(33)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(34)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(35)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(36)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(37)").addClass("edesign_hide");
                $("#detailDiv_5").addClass("edesign_hide");//住宿需求表
            }else {

                $(".excelMainTable tr:eq(32)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(33)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(34)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(35)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(36)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(37)").removeClass("edesign_hide");

                $("#detailDiv_5").removeClass("edesign_hide");//住宿需求表
                $("#detailDiv_5 tr").removeClass("edesign_hide");//住宿需求表

            }

        });
        jQuery("#field"+pc).on('change',function(){//派车
            if($("#field"+pc).is(":checked")){

                $(".excelMainTable tr:eq(38)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(39)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(40)").addClass("edesign_hide");
                $("#detailDiv_2").addClass("edesign_hide");//派车接表

                $(".excelMainTable tr:eq(41)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(42)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(43)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(44)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(45)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(46)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(47)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(48)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(49)").addClass("edesign_hide");
                // $(".excelMainTable tr:eq(50)").addClass("edesign_hide");
                // $(".excelMainTable tr:eq(51)").addClass("edesign_hide");

                $("#detailDiv_3").addClass("edesign_hide");//派车送表
            }else {

                $(".excelMainTable tr:eq(38)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(39)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(40)").removeClass("edesign_hide");
                $("#detailDiv_2").removeClass("edesign_hide");//派车接表
                $("#detailDiv_2 tr").removeClass("edesign_hide");//派车接表

                $(".excelMainTable tr:eq(41)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(42)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(43)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(44)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(45)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(46)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(47)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(48)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(49)").removeClass("edesign_hide");
                // $(".excelMainTable tr:eq(50)").removeClass("edesign_hide");
                // $(".excelMainTable tr:eq(51)").removeClass("edesign_hide");

                $("#detailDiv_3").removeClass("edesign_hide");//派车送表
                $("#detailDiv_3 tr").removeClass("edesign_hide");//派车送表
            }
        });
        jQuery("#field"+qt).on('change',function(){//其他
            if($("#field"+qt).is(":checked")){
                $(".excelMainTable tr:eq(50)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(51)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(52)").addClass("edesign_hide");
                $("#detailDiv_0").addClass("edesign_hide");//礼品表

                $(".excelMainTable tr:eq(53)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(54)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(55)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(56)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(57)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(58)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(59)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(60)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(61)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(62)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(63)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(64)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(65)").addClass("edesign_hide");
                $(".excelMainTable tr:eq(66)").addClass("edesign_hide");
            }else {

                $(".excelMainTable tr:eq(50)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(51)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(52)").removeClass("edesign_hide");
                $("#detailDiv_0").removeClass("edesign_hide");//礼品表
                $("#detailDiv_0 tr").removeClass("edesign_hide");//礼品表

                $(".excelMainTable tr:eq(53)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(54)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(55)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(56)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(57)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(58)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(59)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(60)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(61)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(62)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(63)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(64)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(65)").removeClass("edesign_hide");
                $(".excelMainTable tr:eq(66)").removeClass("edesign_hide");
            }

        });
    }
    function moveHide() {
        if($("#field"+yj).val()==1){
            $(".excelMainTable tr:eq(12)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(13)").removeClass("edesign_hide");
        }
        if($("#field"+cg).val()==1){
            $(".excelMainTable tr:eq(14)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(15)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(16)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(17)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(18)").removeClass("edesign_hide");
        }
        if($("#field"+hy).val()==1){
            $(".excelMainTable tr:eq(19)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(20)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(21)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(22)").removeClass("edesign_hide");
        }
        if($("#field"+cy).val()==1){
            $(".excelMainTable tr:eq(23)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(24)").removeClass("edesign_hide");
            $("#detailDiv_4").removeClass("edesign_hide");//餐饮需求表
            $("#detailDiv_4 tr").removeClass("edesign_hide");//餐饮需求表

            $(".excelMainTable tr:eq(25)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(26)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(27)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(28)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(29)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(30)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(31)").removeClass("edesign_hide");
        }
        if($("#field"+zs).val()==1){

            $(".excelMainTable tr:eq(32)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(33)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(34)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(35)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(36)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(37)").removeClass("edesign_hide");
            $("#detailDiv_5").removeClass("edesign_hide");//住宿需求表
            $("#detailDiv_5 tr").removeClass("edesign_hide");//住宿需求表
        }
        if($("#field"+pc).val()==1){

            $(".excelMainTable tr:eq(38)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(39)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(40)").removeClass("edesign_hide");
            $("#detailDiv_2").removeClass("edesign_hide");//派车接表
            $("#detailDiv_2 tr").removeClass("edesign_hide");//派车接表

            $(".excelMainTable tr:eq(41)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(42)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(43)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(44)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(45)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(46)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(47)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(48)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(49)").removeClass("edesign_hide");
            // $(".excelMainTable tr:eq(50)").removeClass("edesign_hide");
            // $(".excelMainTable tr:eq(51)").removeClass("edesign_hide");

            $("#detailDiv_3").removeClass("edesign_hide");//派车送表
            $("#detailDiv_3 tr").removeClass("edesign_hide");//派车送表
        }
        if($("#field"+qt).val()==1){

            $(".excelMainTable tr:eq(50)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(51)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(52)").removeClass("edesign_hide");
            $("#detailDiv_0").removeClass("edesign_hide");//礼品表
            $("#detailDiv_0 tr").removeClass("edesign_hide");//礼品表

            $(".excelMainTable tr:eq(53)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(54)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(55)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(56)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(57)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(58)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(59)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(60)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(61)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(62)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(63)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(64)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(65)").removeClass("edesign_hide");
            $(".excelMainTable tr:eq(66)").removeClass("edesign_hide");
        }
    }



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