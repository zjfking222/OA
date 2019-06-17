<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.jiuyi.util.CommonUtil" %>
<%@page import="weaver.general.*" %>
<%@page import="java.util.*" %>
<%@page import="com.jiuyi.util.JiuyiUtil" %>

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
        if(nodeid!=6796&&nodeid!=6802&&nodeid!=6805&&nodeid!=6801) {
            $(".canyin").closest('tr').addClass("edesign_hide");//餐饮
            $(".zhusu").closest('tr').addClass("edesign_hide");//住宿
            $(".paiche").closest('tr').addClass("edesign_hide");//派车
            $(".qita").closest('tr').addClass("edesign_hide");//其他
            $(".canguan").closest('tr').addClass("edesign_hide");//参观
            $(".yinjie").closest('tr').addClass("edesign_hide");//迎接
            $(".huiyi").closest('tr').addClass("edesign_hide");//会议
        }
        if(nodeid==6803){//综合单
            bindchange();//绑定监听
        }
        else {
            moveHide();
        }

    });

    function bindchange(){
        jQuery("#field"+yj).on('change',function(){//迎接
            if($("#field"+yj).is(":checked")){
                $(".yinjie").closest('tr').addClass("edesign_hide");//迎接
            }else {
                $(".yinjie").closest('tr').removeClass("edesign_hide");//迎接
            }
        });
        jQuery("#field"+cg).on('change',function(){//参观
            if($("#field"+cg).is(":checked")){
                $(".canguan").closest('tr').addClass("edesign_hide");//参观
            }else {
                $(".canguan").closest('tr').removeClass("edesign_hide");//参观
            }
        });
        jQuery("#field"+hy).on('change',function(){//会议
            if($("#field"+hy).is(":checked")){
                $(".huiyi").closest('tr').addClass("edesign_hide");//会议
            }else {
                $(".huiyi").closest('tr').removeClass("edesign_hide");//会议
            }
        });
        jQuery("#field"+cy).on('change',function(){//餐饮
            if($("#field"+cy).is(":checked")){
                $(".canyin").closest('tr').addClass("edesign_hide");//餐饮
            }else {
                $(".canyin").closest('tr').removeClass("edesign_hide");//餐饮
            }
        });
        jQuery("#field"+zs).on('change',function(){//住宿
            if($("#field"+zs).is(":checked")){
                $(".zhusu").closest('tr').addClass("edesign_hide");//住宿
            }else {
                $(".zhusu").closest('tr').removeClass("edesign_hide");//住宿
            }

        });
        jQuery("#field"+pc).on('change',function(){//派车
            if($("#field"+pc).is(":checked")){
                $(".paiche").closest('tr').addClass("edesign_hide");//派车
            }else {
                $(".paiche").closest('tr').removeClass("edesign_hide");//派车
            }
        });
        jQuery("#field"+qt).on('change',function(){//其他
            if($("#field"+qt).is(":checked")){
                $(".qita").closest('tr').addClass("edesign_hide");//其他
            }else {
                $(".qita").closest('tr').removeClass("edesign_hide");//其他
            }

        });
    }
    function moveHide() {
        if($("#field"+yj).val()==1){
            $(".yinjie").closest('tr').removeClass("edesign_hide");//迎接
        }
        if($("#field"+cg).val()==1){
            $(".canguan").closest('tr').removeClass("edesign_hide");//参观
        }
        if($("#field"+hy).val()==1){
            $(".huiyi").closest('tr').removeClass("edesign_hide");//会议
        }
        if($("#field"+cy).val()==1){
            $(".canyin").closest('tr').removeClass("edesign_hide");//餐饮
        }
        if($("#field"+zs).val()==1){
            $(".zhusu").closest('tr').removeClass("edesign_hide");//住宿
        }
        if($("#field"+pc).val()==1){
            $(".paiche").closest('tr').removeClass("edesign_hide");//派车
        }
        if($("#field"+qt).val()==1){
            $(".qita").closest('tr').removeClass("edesign_hide");//其他
        }
    }
</script>
<%}%>