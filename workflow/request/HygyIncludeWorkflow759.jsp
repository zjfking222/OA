<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="weaver.general.*" %>
<%@page import="java.util.*" %>
<%@page import="com.jiuyi.util.JiuyiUtil" %>
<%
    int formid=Util.getIntValue(request.getParameter("formid"));
    Map map = new HashMap();
    if(formid!=-1){
        map =JiuyiUtil.getFieldId(formid,"0");//主表数据
        String lx =  map.get("lx").toString();//类型
        String wllx =  map.get("wllx").toString();//物料类型
        String WERKS =  map.get("gc").toString();//工厂

%>
<script type="text/javascript" src="wui/common/jquery/jquery.min_wev8.js"></script>
<script type="text/javascript">
    //MM-车间成品领料
    /*****************主表********************/

    var wllx = '<%=wllx%>';//物料类型
    var lx = '<%=lx%>';//类型
    var WERKS = '<%=WERKS%>';//工厂

    setFMVal(wllx,"5CP");
    jQuery("#field"+<%=lx%>).bindPropertyChange(function(){
        var lxvaule = jQuery("#field"+lx).val();
        if(lxvaule=="0"){
            var wllxvalue = "5CP";
        }else if(lxvaule=="1"){
            var wllxvalue = "4BCP";
        }else if(lxvaule=="2"){
            var wllxvalue = "3YCL";
        }else {
            var wllxvalue = "8STY";
        }

        setFMVal(wllx,wllxvalue);
    })


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

</script>
<%}%>