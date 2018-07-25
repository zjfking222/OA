package com.jiuyi.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import weaver.conn.RecordSet;
import weaver.general.BaseBean;
import weaver.general.TimeUtil;
import weaver.general.Util;
import weaver.soa.workflow.request.RequestInfo;

public class JiuyiUtil {

    // 获得tablename
    public static String getTablename(RequestInfo requestInfo) {
        String workflowid = requestInfo.getWorkflowid();
        String formid = "";
        String sql1 = "select formid from workflow_base where id=" + workflowid;
        System.out.println(sql1);
        RecordSet rs = new RecordSet();
        rs.execute(sql1);
        if (rs.next()) {
            formid = rs.getString("formid");
        }
        formid = formid.replaceAll("-", "");
        String tablename = "formtable_main_" + formid;
        return tablename;

    }

    // 获得tablename
    public static String getTablenameBywfid(String workflowid) {
        String formid = "";
        String sql1 = "select formid from workflow_base where id=" + workflowid;
        System.out.println(sql1);
        RecordSet rs = new RecordSet();
        rs.execute(sql1);
        if (rs.next()) {
            formid = rs.getString("formid");
        }
        formid = formid.replaceAll("-", "");
        String tablename = "formtable_main_" + formid;
        return tablename;

    }

    // 获得workflowname
    public static String getWorkflowname(String workflowid) {
        String sql = "select workflowname from workflow_base whereid=" + workflowid;
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        rs.next();
        String workflowname = rs.getString("workflowname");
        return workflowname;
    }

    // 判断是否为时间类型
    public static String parsedata(Object value) {
        if (value instanceof Date) {
            return TimeUtil.getDateString((Date) value);
        } else {
            if (value == null)
                value = "";
            return value + "";
        }
    }

    /**
     * 根据某个表的某个字段值获取另一个字段值
     * 
     * @param fieldname
     *            所需字段值的字段名
     * @param tablename
     *            表名
     * @param byField
     *            条件字段名
     * @param byValue
     *            条件字段值
     * @return 返回所需字段值
     */
    public static String getFieldValueFromTableByfield(String fieldname, String tablename, String byField, String byValue) {
        String sql = "select " + fieldname + " from " + tablename + " where " + byField + "='" + byValue + "'";
        // System.out.println("获取字段"+sql);
        RecordSet rs = new RecordSet();
        String val = "";
        rs.execute(sql);
        if (rs.next()) {
            val = rs.getString(fieldname);
        }
        return val;
    }

    /**
     * 
     * @Title: getTransVal
     * @Description: 进度汇总表 所有完成率字段按百分比、两位小数显示
     * @author chenjq
     * @param val
     * @return
     * @throws
     */
    public static String getTransVal(String val) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(Double.parseDouble(val) * 100) + "%";
    }

    /**
     * 根据userid 获取 name 有样式
     * 
     * @param userid
     * @return
     */
    public static String getLastNameByUserId(String userid) {
        RecordSet rs = new RecordSet();
        String result = "";
        String lastname = "";
        if (rs.execute("select lastname from hrmresource where id = '" + userid + "'") && rs.next()) {
            lastname = Util.null2String(rs.getString("lastname"));
        }
        // System.out.println(browseid);
        result = lastname;
        return result;
    }

    /**
     * 根据userid 获取 loginid
     * 
     * @param userid
     * @return
     */
    public static String getLoginidByUserId(String userid) {
        RecordSet rs = new RecordSet();
        String result = "";
        String loginid = "";
        if (rs.execute("select loginid from hrmresource where id = '" + userid + "'") && rs.next()) {
            loginid = Util.null2String(rs.getString("loginid"));
        }
        // System.out.println(browseid);
        result = loginid;
        return result;
    }

    /**
     * 根据loginid 获取 userid
     * 
     * @param loginid
     * @return
     */
    public static String getUserIdByLoginid(String loginid) {
        RecordSet rs = new RecordSet();
        String result = "";
        String id = "";
        if (rs.execute("select id from hrmresource where loginid = '" + loginid + "'") && rs.next()) {
            id = Util.null2String(rs.getString("id"));
        }
        // System.out.println(browseid);
        result = id;
        return result;
    }

    // public static String getCellValue(HSSFCell cell)
    // {
    // String cellValue = "";
    // if (cell == null)
    // return "";
    // switch (cell.getCellType()) {
    // case 4:
    // cellValue = String.valueOf(cell.getBooleanCellValue());
    // break;
    // case 0:
    // if (HSSFDateUtil.isCellDateFormatted(cell)) {
    // SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
    // cellValue = String.valueOf(sft.format(cell.getDateCellValue()));
    // } else {
    // cellValue = String.valueOf(new Double(cell.getNumericCellValue()));
    // if (cellValue.endsWith(".0"))
    // cellValue = cellValue.substring(0, cellValue.indexOf("."));
    // }
    // break;
    // case 2:
    // cellValue = cell.getCellFormula();
    // break;
    // case 1:
    // cellValue = cell.getStringCellValue();
    // case 3:
    // }
    //
    // return cellValue;
    // }
    /**
     * 国际化语言
     * 
     * @param c
     * @param langu
     * @return
     */
    public static String SystemLangu(Integer c, Integer langu) {
        RecordSet rs = new RecordSet();
        String sql = "select lastname from Systemlangu where indexid=" + c + " and language=" + langu;
        rs.execute(sql);
        rs.next();
        return rs.getString("lastname");
    }

    /**
     * 根据部门ID获取部门描述
     * 
     * @param id
     *            部门ID
     * @return 部门描述
     */
    public static String getDepartmentById(String id, String zlglbfyzl) {
        RecordSet rs = new RecordSet();
        String result = "";
        rs.executeSql("select * from HrmDepartment where id = " + id);
        if (rs.next()) {
            result = rs.getString("departmentname");
        }
        if (result.equals("董事会")) {
            result = "秘书室";
        }
        if (result.equals("学术组")) {
            result = "市场部";
        }
        if (result.equals("质量保障部")) {
            if (zlglbfyzl.equals("0")) {
                result += "-管理";
            } else if (zlglbfyzl.equals("1")) {
                result += "-制造";
            }
        }
        return result;
    }

    /**
     * 根据部门ID获取部门描述
     * 
     * @param id
     *            部门ID
     * @return 部门描述
     */
    public static String getDepartById(String id) {
        RecordSet rs = new RecordSet();
        String result = "";
        rs.executeSql("select * from HrmDepartment where id = " + id);
        if (rs.next()) {
            result = rs.getString("departmentname");
        }
        return result;
    }

    /**
     * 根据部门描述获取部门ID
     * 
     * @param 部门描述
     * @return id 部门ID
     */
    public static String getIdByDepartment(String departmentname) {
        RecordSet rs = new RecordSet();
        String result = "";
        if (departmentname.contains("成都一")) {
            departmentname = "成都一办事处";
        }
        if (departmentname.contains("成都二")) {
            departmentname = "成都二办事处";
        }
        rs.executeSql("select id from HrmDepartment where departmentname like '%" + departmentname + "%'");
        if (rs.next()) {
            result = rs.getString("id");
        }
        return result;
    }

    /**
     * 截取15位字符串
     * 
     * @param str
     * @return
     */
    public static String cutString(String str) {
        String strs = "";
        strs += "<span onmouseover='showdiv(event,\"" + str + "\")' onmouseout='hidediv(event)'>";
        if (str.length() > 15) {
            strs += str.substring(0, 15);
            strs += "......";
        } else {
            strs += str;
        }
        strs += "</span>";
        return strs;
    }

    /**
     * 获取城市名称
     * 
     * @param id
     * @return
     */
    public static String getCity(int id) {
        RecordSet rs = new RecordSet();
        rs.executeSql("select * from hrmcity where id=" + id);
        rs.next();
        String cityname = rs.getString("cityname");
        return cityname;
    }

    /**
     * 检查流程是否全都归档
     * 
     * @param requestids
     * @return count
     */
    public static String getStatus(String requestids) {
        RecordSet rs = new RecordSet();
        rs.executeSql("select currentnodetype from workflow_requestbase where requestid in (" + requestids + ") and currentnodetype!='3'");
        return "" + rs.getCounts();
    }

    /**
     * 获取下拉框的值
     * 
     * @param id
     * @return
     */
    public static String getSelectItem(Integer selectvalue, Integer billid, String fieldname) {
        String sql = "select * from workflow_selectitem where selectvalue='" + selectvalue + "' and fieldid in (select id from workflow_billfield where billid = '" + billid + "' and fieldname='" + fieldname + "')";
        RecordSet rs = new RecordSet();
        rs.executeSql(sql);
        rs.next();

        return "" + rs.getString("selectname");
    }

    /**
     * 根据user id 获得 lastname
     * 
     * @param id
     * @return lastname
     */
    public static String getUserNameById(Integer id) {
        String sql = "select lastname from hrmresource where id =" + id;
        RecordSet rs = new RecordSet();
        rs.executeSql(sql);
        rs.next();
        return "" + rs.getString("lastname");
    }

    /**
     * 根据lastname 获得 user id
     * 
     * @param lastname
     * @return user id
     */
    public static String getIdByUserName(String username) {
        String sql = "select id from hrmresource where lastname ='" + username + "'";
        RecordSet rs = new RecordSet();
        rs.executeSql(sql);
        rs.next();
        return "" + rs.getString("id");
    }

    public static String SystemLanguByHtml(Integer languageid, Integer billid, String fieldname) {

        String sql = "select * from HtmlLabelInfo where indexid in" + " (select fieldlabel from workflow_billfield where billid = " + billid + " and fieldname = '" + fieldname + "')" + " and languageid = " + languageid;
        RecordSet rs = new RecordSet();
        rs.executeSql(sql);
        rs.next();

        return "" + rs.getString("labelname");
    }

    /**
     * 根据user department 获得 所有下级department
     * 
     * @param depid
     * @return depids
     */
    public static String getDepidsByDepId(String depid) {
        String result = depid;
        while (true) {
            String sql = "select id from HrmDepartment where supdepid in (" + depid + ")";
            RecordSet rs = new RecordSet();
            rs.executeSql(sql);
            if (rs.getCounts() == 0) {
                break;
            } else {
                depid = "";
            }
            while (rs.next()) {
                result += "," + rs.getString("id");
                if (depid.equals("")) {
                    depid = rs.getString("id");
                } else {
                    depid += "," + rs.getString("id");
                }
            }
        }
        return result;
    }

    /**
     * 获取货币
     * 
     * @param id
     * @return
     */
    public static String getCurrency(Integer id) {
        RecordSet rs = new RecordSet();
        rs.executeSql("select currencyname from FnaCurrency where id =" + id);
        rs.next();
        return "" + rs.getString("currencyname");
    }

    /**
     * 根据user id 获得 所有下级
     * 
     * @param userid
     * @return userids
     */
    public static String getUseridsByUserId(String userid) {
        String result = userid;
        while (true) {
            String sql = "select id from Hrmresource where managerid in (" + userid + ")";
            RecordSet rs = new RecordSet();
            rs.executeSql(sql);
            if (rs.getCounts() == 0) {
                break;
            } else {
                userid = "";
            }
            while (rs.next()) {
                String id = rs.getString("id");
                if (!pgWpo(id)) {// 不是WPO
                    result += "," + id;
                    if (userid.equals("")) {
                        userid = id;
                    } else {
                        userid += "," + id;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 判断是否WPO
     * 
     * @param id
     * @return
     */
    public static boolean pgWpo(String userid) {
        RecordSet rs = new RecordSet();
        rs.executeSql("select * from formtable_main_148 where position='WPO' and person='" + userid + "'");
        return rs.next();
    }

    /**
     * 方法描述 : 获取字段的ID 创建者：weitao
     * 
     * @param formid
     *            流程id
     * @param num
     *            明细表
     * @return Map
     */
    public static Map getFieldId(int formid, String num) {

        formid = Math.abs(formid);
        String sql = "";
        if ("0".equals(num)) {
            sql = "select id,fieldname,detailtable from workflow_billfield where billid=-" + formid + " and (detailtable='' or detailtable is null) ";
        } else {
            sql = "select id,fieldname,detailtable from workflow_billfield where billid=-" + formid + " and detailtable='formtable_main_" + formid + "_dt" + num + "'";
        }
        System.out.println(sql);
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        Map array = new HashMap();
        while (rs.next()) {
            array.put(Util.null2String(rs.getString("fieldname")).toLowerCase(), Util.null2String(rs.getString("id")));
        }
        return array;
    }

    public static String getlabelId(String name, String formid, String ismain, String num) {
        String id = "";
        String sql = "";
        if ("0".equals(ismain)) {
            sql = "select b.id,fieldname,detailtable from workflow_billfield b ,workflow_base a where b.billid=-" + formid + " and a.formid=b.billid and lower(fieldname)='" + name + "'";
        } else {
            sql = "select b.id,fieldname,detailtable from workflow_billfield b ,workflow_base a where b.billid=-" + formid + " and a.formid=b.billid and detailtable='formtable_main_" + formid + "_dt" + num + "' and lower(fieldname)='" + name + "'";
        }
        // System.out.println(sql);
        RecordSet rs = new RecordSet();
        rs.execute(sql);
        rs.next();
        id = Util.null2String(rs.getString("id"));
        return id;

    }

    /**
     * 流程转发
     * 
     * @param requestids
     *            多流程
     * @param forwardoperator
     *            当前操作者
     * @param recipients
     *            被转发人
     * @return
     */
    public static boolean forwardFlow(String requestids, int forwardoperator, String recipients) {
        String[] requestid = requestids.split(",");
        try {
            for (int i = 0; i < requestid.length; i++) {
                weaver.soa.workflow.request.RequestService reqSvc = new weaver.soa.workflow.request.RequestService();
                reqSvc.forwardFlow(Integer.parseInt(requestid[i]), forwardoperator, recipients, "", "");
            }
        } catch (Exception e) {

        }
        return true;
    }

    /**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
     */
    public static String digitUppercase(double n) {
        String fraction[] = { "角", "分" };
        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

        String head = n < 0 ? "负" : "";
        n = Math.abs(n);

        String s = "";
        for (int i = 0; i < fraction.length; i++) {
            s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if (s.length() < 1) {
            s = "整";
        }
        int integerPart = (int) Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p = "";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart % 10] + unit[1][j] + p;
                integerPart = integerPart / 10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }

    /*
     * 去除字符串前面无效的0
     */
    public static String replaceSAPPre0(String theSapVal) {
        if (theSapVal == null) {
            return "";
        }

        return theSapVal.replaceFirst("^0*", "");
    }

    /**
     * 流程转发
     * 
     * @param requestids
     *            多流程
     * @param forwardoperator
     *            当前操作者
     * @param recipients
     *            被转发人
     * @return
     */
    public static Integer getManagerByUserid(Integer id) {
        RecordSet rs = new RecordSet();
        rs.executeSql("select managerid from hrmresource where id = " + id);
        rs.next();
        return rs.getInt("managerid");
    }

    /**
     * 通过用户id查找用户部门
     * 
     * @param userid
     * @return lastname
     */
    public static String getUserDepartmentByUserId(String userid) {
        RecordSet rs = new RecordSet();
        String departmentid = "";
        if (rs.execute("select departmentid from hrmresource where id = '" + userid + "'") && rs.next()) {
            departmentid = Util.null2String(rs.getString("departmentid"));
        }
        // System.out.println(browseid);
        return departmentid;
    }

    /**
     * 获取员工备用金余额
     * 
     * @param SAPygbyjye
     * @return ygbyjye
     */
    public static float getYgbyjye(float SAPygbyjye, String requestid, String userid) {
        float ygbyjye = 0.00f;
        RecordSet rs = new RecordSet();
        String sql = "select sum(a.ygbyjye) as ygbyjye from formtable_main_29 a left join workflow_requestbase b on a.requestid=b.requestid where b.currentnodetype='2' and a.sqr='" + userid + "' and a.requestid<>'" + requestid + "'";
        rs.execute(sql);
        rs.next();
        String value = Util.null2String(rs.getString("ygbyjye"));
        if (!value.equals("")) {
            ygbyjye = SAPygbyjye - rs.getFloat("ygbyjye");
        } else {
            ygbyjye = SAPygbyjye;
        }
        return ygbyjye;
    }

    /**
     * 获取员工个人借款余额
     * 
     * @param SAPyggrjkye
     * @return yggrjkye
     */
    public static float getYggrjkye(float SAPyggrjkye, String requestid) {
        float yggrjkye = 0.00f;
        RecordSet rs = new RecordSet();
        String sql = "select sum(a.yggrjkye) as yggrjkye from formtable_main_29 a left join workflow_requestbase b on a.requestid=b.requestid where b.currentnodetype='2' and a.requestid<>'" + requestid + "'";
        rs.execute(sql);
        rs.next();
        String value = Util.null2String(rs.getString("yggrjkye"));
        if (!value.equals("")) {
            yggrjkye = SAPyggrjkye - rs.getFloat("yggrjkye");
        } else {
            yggrjkye = SAPyggrjkye;
        }
        return yggrjkye;
    }

    /**
     * 获取供应商预付款余额
     * 
     * @param SAPgysyfkye
     * @return gysyfkye
     */
    public static float getGysyfkye(float SAPgysyfkye, String requestid, String userid) {
        float gysyfkye = 0.00f;
        RecordSet rs = new RecordSet();
        String sql = "select sum(a.gysyfkye) as gysyfkye from formtable_main_29 a left join workflow_requestbase b on a.requestid=b.requestid where b.currentnodetype='2' and a.sqr='" + userid + "' and a.requestid<>'" + requestid + "'";
        rs.execute(sql);
        rs.next();
        String value = Util.null2String(rs.getString("gysyfkye"));
        if (!value.equals("")) {
            gysyfkye = SAPgysyfkye - rs.getFloat("gysyfkye");
        } else {
            gysyfkye = SAPgysyfkye;
        }
        return gysyfkye;
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param smdate
     *            较小的时间
     * @param bdate
     *            较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String sdate, String edate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date smdate = sdf.parse(sdate);
        Date bdate = sdf.parse(edate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取货币
     * 
     * @param kjkmms
     * @return waers
     */
    public static String getWaers(String kjkmms) {
        String waers = "";
        if (kjkmms.contains("美元户")) {
            waers = "USD";
        } else if (!kjkmms.contains("JPY") && !kjkmms.contains("USD")) {
            waers = "CNY";
        }
        return waers;
    }

    public static boolean isYjs(String bm) {
        boolean isyjs;
        RecordSet rs = new RecordSet();
        String sjbm = "";
        if (bm.equals("29")) {
            isyjs = true;
        } else {
            String sql = "select supdepid from hrmdepartment where id=" + bm;
            rs.execute(sql);
            if (rs.next()) {
                sjbm = rs.getString("supdepid");
                if (sjbm.equals("29")) {
                    isyjs = true;
                } else {
                    isyjs = false;
                }
            } else {
                isyjs = false;
            }
        }

        return isyjs;
    }

    /**
     * 
     * @param date1
     *            <String>
     * @param date2
     *            <String>
     * @return int
     * @throws ParseException
     */
    public static int getMonthSpace(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return result.size();

    }

    /**
     * 获取节点名称
     * 
     * @param nodeid
     * @return nodename
     */
    public static String getNodeName(int nodeid) {
        RecordSet rs = new RecordSet();
        String nodename = "";
        if (rs.execute("select nodename from workflow_nodebase where id = '" + nodeid + "'") && rs.next()) {
            nodename = Util.null2String(rs.getString("nodename"));
        }
        return nodename;
    }

    /**
     * 获取截止时间
     * 
     * @return nodename
     */
    public static int getJzsj() {
        RecordSet rs = new RecordSet();
        String dqmouth = TimeUtil.getToday().split("-")[1];
        int jsrq = 0;
        if (rs.execute("select jsrq from uf_fybxrq where yfsz='" + dqmouth + "'") && rs.next()) {
            jsrq = rs.getInt("jsrq");
        }
        return jsrq;
    }

    public static String getCbzx(String bm, String bukrs) {
        RecordSet rs = new RecordSet();
        String cbzx = "";
        if (rs.execute("select cbzx from uf_bmcbzx where bm='" + bm + "' and gsdm='" + bukrs + "'") && rs.next()) {
            cbzx = rs.getString("cbzx");
        }
        return cbzx;
    }

    public static void updatePosnrXh(String tablename, String setValue, String sqlwhere) {
        RecordSet rs1 = new RecordSet();
        rs1.execute("update " + tablename + " set " + setValue + " " + sqlwhere);
    }
    /**
	 * 获取小拉克的值
	 * @param str
	 * @param fieldid
	 * @return
	 */
	public static String getSelectname(String str,String fieldid){
		RecordSet rs = new RecordSet();
		String sql="select * from workflow_selectitem where fieldid="+fieldid+" and selectvalue="+str;
		rs.executeSql(sql);
		if(rs.next()){
			return rs.getString("selectname");
		}
		return "";
	} 
	
	//获取富文本内容
	public static String getRTFContent(String str){
		if(str!=null&&str.length()>0){
			String[] strarr = str.split(">");
			String nr="";
			for (String a : strarr) {
				String substr = a.substring(0,1);
				if(!"<".equals(substr)){
					int index = a.indexOf("<");
					if(index>=0){
						nr = nr+a.substring(0,index);
					}
				}
			}
			return nr;
		}
		return null;
	}
	 /**
		 * 获取千分位
		 * @param d
		 * @param s
		 * @return
		 */
	public static String getQfw(double d){
	    BigDecimal a=new BigDecimal(d+"");  
	    DecimalFormat df=new DecimalFormat(",###,##0.000"); //保留三位小数  
	    return df.format(a);  
	}
	/**
	 * 千分位转int
	 * @param d
	 * @param s
	 * @return
	 */
	public static double getDoubleFromQfw(String s){
		String d=s.replaceAll(",","");
	    return Double.parseDouble(d);  
	}

	/**
	 * 负数处理
	 * @param d
	 * @param s
	 * @return
	 */
	public static String getRightfs(String s){
		String result=s;
		if(s.length()>1){
			if("-".equals(s.substring(s.length()-1,s.length()))){
				result="-"+s.substring(0,s.length()-1);
			}
		}
	    return result;  
	}
}
