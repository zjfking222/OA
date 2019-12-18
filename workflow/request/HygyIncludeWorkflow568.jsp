<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.jiuyi.util.CommonUtil" %>
<%@page import="weaver.general.*" %>
<%@page import="java.util.*" %>
<%
    /**
     * FICO-资产主数据维护流程必填控制
     * @author cyn
     *
     * */
%>

<%
    int formid = Util.getIntValue(request.getParameter("formid"));
    Map<String, String> map;
    Map<String, String> map1;

    if (formid != -1) {
        map = CommonUtil.getFieldId(formid, "0");//主表数据
        String xmcg = map.get("sfxmcg");//是否项目采购

        map1 = CommonUtil.getFieldId(formid, "1");//明细1
        String KOSTL = map1.get("kostl");//成本中心
        String AUFNR = map1.get("aufnr");//项目

%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">

    /*****************主表********************/
    var xmcg = '<%=xmcg%>';//是否项目采购

    /*****************明细表********************/

    var KOSTL = '<%=KOSTL%>';//成本中心
    var AUFNR = '<%=AUFNR%>';//项目

    jQuery("#field" +<%=xmcg%>).bindPropertyChange(function () {
        var xmvaule = jQuery("#field" + xmcg).val();
        var indexV= jQuery("#indexnum0").val();
        if(indexV>=0){
            for (indexV;indexV>-1;indexV--) {
                SFXM(xmvaule,indexV);
            }
        }
        jQuery("#indexnum0").bindPropertyChange(function(){
            //获取到明细下标
            var indexV2 = jQuery("#indexnum0").val()-1;
            SFXM(xmvaule,indexV2);
        });
    });
    //是否项目采购必填判断
    function SFXM(xmV,index){
        if (xmV==0){//是否项目采购为是
            //alert("#field"+AUFNR+"_"+index);
            setNeedcheck("field"+AUFNR+"_"+index);//项目必填
            setNotcheck("field"+KOSTL+"_"+index);
        } else if(xmV==1) {
            setNeedcheck("field"+KOSTL+"_"+index);//成本中心必填
            setNotcheck("field"+AUFNR+"_"+index);
        }
    }

    //字段必填
    function setNeedcheck(idStr) {

        var objid = idStr;
        var  needcheck = $("input[name='needcheck']").val();
        var result = (needcheck == "")? idStr: (needcheck + "," + idStr);
        $("input[name='needcheck']").val(result);
        var obj = jQuery("#"+objid);
        var objSpan = jQuery("#"+objid+"spanimg");
        if (obj.val()==''){
            objSpan.html("<img src='/images/BacoError_wev8.gif' align='absmiddle'>");
        }
        var viewtype = obj.attr("viewtype");
        if (viewtype!=""){
            obj.attr("viewtype","1");
        }
    }
    //字段不必填
    function setNotcheck(idStr) {

        var objid = idStr;
        var obj = jQuery("#"+objid);
        var objSpan = jQuery("#"+objid+"spanimg");
        objSpan.html("");
        var viewtype = obj.attr("viewtype");
        if (viewtype!=""){
            obj.attr("viewtype","0");
        }
        var  baseText = $("input[name='needcheck']").val();
        var baseList = baseText.split(',');
        for(var i = baseList.length - 1; i >= 0 ; i--){
            var data = baseList[i].trim();
            if((data == idStr) || (data == ""))
                baseList.splice(i, 1);
        }
        $("input[name='needcheck']").val(baseList.join(','));
    }
</script>
<%}%>