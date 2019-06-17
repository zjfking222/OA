<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.jiuyi.util.CommonUtil" %>
<%@page import="weaver.general.*" %>
<%@page import="java.util.*" %>
<%
    new BaseBean().writeLog("开始执行jsp");
    int formid = Util.getIntValue(request.getParameter("formid"));
    Map<String, String> map;


    if (formid != -1) {
        map = CommonUtil.getFieldId(formid, "0");//主表数据
        String zbgc = map.get("factory");//主表工厂
        String jksb = map.get("jksb");//进口设备

%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">
    /*****************主表********************/
    /**
     * MM-物料主数据创建
     * @author CYN
     *
     */

    var zbgc = '<%=zbgc%>';//主表工厂
    var jksb = '<%=jksb%>';//进口设备

    jQuery("#field"+jksb).addClass("edesign_hide");

    jQuery("#field" +<%=zbgc%>).bindPropertyChange(function () {
        var gcvaule = jQuery("#field" + zbgc).val();

        if (gcvaule === "4030") {
            jQuery("#field"+jksb+"_tdwrap select").removeClass("edesign_hide");
        } else {
            jQuery("#field"+jksb).addClass("edesign_hide");
        }

    });
</script>
<%}%>