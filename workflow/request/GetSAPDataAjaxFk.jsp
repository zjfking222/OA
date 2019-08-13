<%@ page language="java" contentType="text/html; charset=GBK" %>
<%@page import="weaver.general.Util" %>
<%@page import="java.util.*" %>
<%@page import="com.sap.mw.jco.JCO" %>
<%@page import="weaver.hrm.User" %>
<%@page import="weaver.hrm.HrmUserVarify" %>
<%@page import="com.jiuyi.util.CommonUtil" %>
<%@page import="com.sap.mw.jco.IFunctionTemplate" %>
<%@page import="net.sf.json.JSONArray" %>
<%@page import="net.sf.json.JSONObject" %>
<%@page import="java.text.*" %>
<%@page import="weaver.general.BaseBean" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSetDataSource"
             scope="page"/>
<jsp:useBean id="rs1" class="weaver.conn.RecordSet" scope="page"/>
<jsp:useBean id="rs2" class="weaver.conn.RecordSet" scope="page"/>
<jsp:useBean id="basebean" class="weaver.general.BaseBean" scope="page"/>
<jsp:useBean id="CommonUtil" class="com.jiuyi.util.CommonUtil"
             scope="page"/>
<jsp:useBean id="SAPUtil" class="com.jiuyi.util.SAPUtil" scope="page"/>
<jsp:useBean id="BigDecimalUtil" class="com.jiuyi.util.BigDecimalUtil"
             scope="page"/>
<%
    //��Դ�嵥
    response.setHeader("cache-control", "no-cache");
    response.setHeader("pragma", "no-cache");
    response.setHeader("expires", "Mon 1 Jan 1990 00:00:00 GMT");
    JCO.Client myConnection = null;
    try {
        //StringBuffer json = new StringBuffer();

        String sql = "";
        JSONObject dataObj = new JSONObject();
        JSONArray dt1Arr = new JSONArray();
        JSONArray dt2Arr = new JSONArray();
        JSONObject dt1Obj = new JSONObject();
        JSONObject dt2Obj = new JSONObject();
        String operation = Util.null2String(request.getParameter("operation"));
        if (operation.equals("pdfk")) {
            String requestid = Util.null2String(request.getParameter("requestid"));
            String bukrs = Util.null2String(request.getParameter("bukrs"));
            String lifnr = Util.null2String(request.getParameter("lifnr"));
            String tjrq = Util.null2String(request.getParameter("tjrq"));
            String ywyid = Util.null2String(request.getParameter("ywy"));
            String KOINH = "";//�ʻ�����������
            String ZBANKN = "";//�����˻�
            sql = "select*from hrmresource where id = " + ywyid;

            rs.execute(sql);
            rs.next();
            String ywyxm = rs.getString("lastname");
            String workflowid = Util.null2String(request.getParameter("workflowid"));
            String tablename = CommonUtil.getTablenameBywfid(workflowid);
            myConnection = SAPUtil.getSAPcon();
            myConnection.connect();
            //System.out.println("operation:"+operation);
            JCO.Repository myRepository = new JCO.Repository("Repository", myConnection);
            IFunctionTemplate ft = myRepository.getFunctionTemplate("ZFI_OE025_PAY_INBOUND_01");
            JCO.Function function = ft.getFunction();
            JCO.ParameterList input = function.getImportParameterList();//��������ͽṹ����
            JCO.Structure I_INBOUND = input.getStructure("I_INBOUND");
            I_INBOUND.setValue(bukrs, "BUKRS");
            I_INBOUND.setValue(lifnr, "LIFNR");
            myConnection.execute(function);

            JCO.ParameterList outpar = function.getExportParameterList();//��������ͽṹ����
            JCO.ParameterList Table00 = function.getTableParameterList();//�����Ĵ���
            JCO.Table T_OUTBOUND01 = Table00.getTable("T_OUTBOUND01");//��ϸ1��Ϣ
            JCO.Table T_OUTBOUND02 = Table00.getTable("T_OUTBOUND02");//��ϸ2��Ϣ
            new BaseBean().writeLog("T_OUTBOUND01===" + T_OUTBOUND01);
            new BaseBean().writeLog("T_OUTBOUND02===" + T_OUTBOUND02);
            String fpfkfph = "";
            ArrayList<String> belnrlist = new ArrayList<String>();
            new BaseBean().writeLog("T_OUTBOUND01.getNumRows()==========" + T_OUTBOUND01.getNumRows());
            for (int i = 0; i < T_OUTBOUND01.getNumRows(); i++) {
                T_OUTBOUND01.setRow(i);
                KOINH = T_OUTBOUND01.getString("KOINH");
                ZBANKN = T_OUTBOUND01.getString("ZBANKN");
                new BaseBean().writeLog("KOINH===" + KOINH);
                String BELNR = T_OUTBOUND01.getString("BELNR");
                String GJAHR = T_OUTBOUND01.getString("GJAHR");
                double ZFPJE = T_OUTBOUND01.getDouble("ZFPJE");
                dt1Obj.put("BELNR", BELNR);//��Ʊ��
                dt1Obj.put("GJAHR", GJAHR);//���
                dt1Obj.put("ZFBDT", T_OUTBOUND01.getString("ZFBDT"));//��׼��
                dt1Obj.put("ZTERM", T_OUTBOUND01.getString("ZTERM"));//��������
                dt1Obj.put("TEXT1", T_OUTBOUND01.getString("TEXT1"));//������������
                dt1Obj.put("ZDQRI", T_OUTBOUND01.getString("ZDQRI"));//������
                dt1Obj.put("SHKZG", T_OUTBOUND01.getString("SHKZG"));//�����
                dt1Obj.put("EKNAM", T_OUTBOUND01.getString("EKNAM"));//ҵ��Ա
                dt1Obj.put("LFBNR", T_OUTBOUND02.getString("LFBNR"));//Ӧ�����ƾ֤��
                dt1Obj.put("XBLNR", T_OUTBOUND02.getString("XBLNR"));//�ο�ƾ֤���
                String mxywyxm = T_OUTBOUND01.getString("EKNAM");
                dt1Obj.put("ZFPJE", ZFPJE);//��Ʊ���
                String ZDQRI = T_OUTBOUND01.getString("ZDQRI");//������

                double yifje = 0;//
                sql = "select sum(c.sqfkje) as yfkje from " + tablename
                        + " a join workflow_requestbase b on a.requestid=b.requestid and b.currentnodetype='3' left join "
                        + tablename + "_dt1 c on a.id=c.mainid where a.bukrs='" + bukrs + "' and c.belnr='"
                        + BELNR + "' and a.lifnr='" + lifnr + "' and c.gjahr='" + GJAHR + "'";
                rs.executeSql(sql);
                new BaseBean().writeLog("sql=1" + sql);
                if (rs.next()) {
                    yifje = Double.parseDouble(Util.null2o(rs.getString("yfkje")));
                }

                sql = "select sum(c.sqfkje) as yfkje from " + tablename
                        + " a join workflow_requestbase b on a.requestid=b.requestid and b.currentnodetype!='3' left join "
                        + tablename + "_dt1 c on a.id=c.mainid where a.bukrs='" + bukrs + "' and c.belnr='"
                        + BELNR + "' and a.lifnr='" + lifnr + "' and c.gjahr='" + GJAHR + "'";
                new BaseBean().writeLog("sql=2" + sql);
                rs.executeSql(sql);
                double ysqje = 0;//
                if (rs.next()) {
                    ysqje = Double.parseDouble(Util.null2o(rs.getString("yfkje")));
                }

                dt1Obj.put("ysqje", ysqje);//��������
                dt1Obj.put("yifje", yifje);//�Ѹ����
                double yingfje = BigDecimalUtil.sub(ZFPJE, yifje);
                dt1Obj.put("yingfje", yingfje);//Ӧ�����
                dt1Obj.put("sqfkje", yingfje);//���븶����
                String MM = tjrq.replace("-", "").substring(0, 6);
                String mm = ZDQRI.replace("-", "").substring(0, 6);

                new BaseBean().writeLog("tjrq=" + tjrq + " ZDQRI=" + ZDQRI);
                boolean test = (tjrq.compareTo(ZDQRI) > 0 || tjrq.compareTo(ZDQRI) == 0 || mm.equals(MM));
                new BaseBean().writeLog("���������ύ���ڵ��ж�" + test);
                new BaseBean().writeLog("MM=" + MM + " mm=" + mm);
                boolean test2 = yingfje > 0;
                new BaseBean().writeLog("Ӧ��������0" + test2);
                new BaseBean().writeLog("ZFPJE=" + ZFPJE + " ysqje=" + ysqje + "yifje" + yifje);
                boolean test3 = ZFPJE - ysqje - yifje > 0;
                new BaseBean().writeLog("��Ʊ���-Ӧ�����-�Ѹ����>0" + test3);
                boolean test4 = ywyxm.equals(mxywyxm);
                new BaseBean().writeLog("sapҵ��Ա����" + mxywyxm + "oaҵ��Ա����" + ywyxm + "�������" + test4);
                //&& ZFPJE-ysqje-yifje>0
                boolean flag = (tjrq.compareTo(ZDQRI) > 0 || tjrq.compareTo(ZDQRI) == 0 || mm.equals(MM)) && yingfje > 0 && ZFPJE - ysqje - yifje > 0
                        && ywyxm.equals(mxywyxm);
                new BaseBean().writeLog("flag========" + flag);
                if (flag) {
                    new BaseBean().writeLog("д��ķ�Ʊ��" + BELNR);
                    dt1Arr.add(dt1Obj);
                    belnrlist.add(BELNR);
                } else {
                    new BaseBean().writeLog("�ύ�����ڵ�����֮ǰ�ˡ�");
                }
            }
            new BaseBean().writeLog("T_OUTBOUND02.getNumRows()==========" + T_OUTBOUND02.getNumRows());
            for (int i = 0; i < T_OUTBOUND02.getNumRows(); i++) {
                T_OUTBOUND02.setRow(i);
                new BaseBean().writeLog("��ϸ2��Ʊ��=========��" + i + "�� " + T_OUTBOUND02.getString("BELNR"));
                dt2Obj.put("BELNR", T_OUTBOUND02.getString("BELNR"));//��Ʊ��
                dt2Obj.put("BUZEI", T_OUTBOUND02.getString("BUZEI"));//��Ʊ����Ŀ
                dt2Obj.put("GJAHR", T_OUTBOUND02.getString("GJAHR"));//���
                dt2Obj.put("MATNR", T_OUTBOUND02.getString("MATNR"));//����
                dt2Obj.put("MAKTX", T_OUTBOUND02.getString("MAKTX"));//��������
                dt2Obj.put("SHKZG", T_OUTBOUND02.getString("SHKZG"));//�����
                dt2Obj.put("MENGE", T_OUTBOUND02.getString("MENGE"));//����
                dt2Obj.put("MEINS", T_OUTBOUND02.getString("MEINS"));//��λ
                dt2Obj.put("LFBNR", T_OUTBOUND02.getString("LFBNR"));//Ӧ�����ƾ֤��
                dt2Obj.put("XBLNR", T_OUTBOUND02.getString("XBLNR"));//�ο�ƾ֤���
                new BaseBean().writeLog("��Ʊ�ż���=" + Arrays.toString(belnrlist.toArray()));

                for (int m = 0; m < belnrlist.size(); m++) {
                    if ((belnrlist.get(m)).equals(T_OUTBOUND02.getString("BELNR"))) {
                        new BaseBean().writeLog("��Ʊ��д���2" + belnrlist.get(m));
                        dt2Arr.add(dt2Obj);
                    }
                }

            }
            dataObj.put("KOINH", KOINH);
            dataObj.put("ZBANKN", ZBANKN);
            dataObj.put("dt1", dt1Arr);
            dataObj.put("dt2", dt2Arr);
        } else if (operation.equals("htfk")) {
            String bukrs = Util.null2String(request.getParameter("bukrs"));
            String lifnr = Util.null2String(request.getParameter("lifnr"));
            String ebeln = Util.null2String(request.getParameter("ebeln"));
            String ztermoa = Util.null2String(request.getParameter("zterm"));
            String workflowid = Util.null2String(request.getParameter("workflowid"));
            String requestid = Util.null2String(request.getParameter("requestid"));
            String tablename = CommonUtil.getTablenameBywfid(workflowid);
            myConnection = SAPUtil.getSAPcon();
            myConnection.connect();
            //System.out.println("operation:"+operation);
            JCO.Repository myRepository = new JCO.Repository("Repository", myConnection);
            IFunctionTemplate ft = myRepository.getFunctionTemplate("ZFI_OE025_PAY_INBOUND_03");
            JCO.Function function = ft.getFunction();
            JCO.ParameterList input = function.getImportParameterList();//��������ͽṹ����
            JCO.Structure I_INBOUND = input.getStructure("I_INBOUND");
            I_INBOUND.setValue(bukrs, "BUKRS");
            I_INBOUND.setValue(lifnr, "LIFNR");
            I_INBOUND.setValue(ebeln, "EBELN");
            myConnection.execute(function);

            JCO.ParameterList outpar = function.getExportParameterList();//��������ͽṹ����
            JCO.ParameterList Table00 = function.getTableParameterList();//�����Ĵ���

            JCO.Structure E_OUTBOUND = outpar.getStructure("E_OUTBOUND");
            //JCO.Structure E_OUTBOUND2 = outpar.getStructure("E_OUTBOUND2 ");//��ϸ2��Ϣ
            double zhtje = E_OUTBOUND.getDouble("ZHTJE");//�ܺ�ͬ���
            String KOINH = E_OUTBOUND.getString("KOINH");//�ʻ�����������
            String ZBANKN = E_OUTBOUND.getString("ZBANKN");//�����˻�
            String ywy = E_OUTBOUND.getString("EKNAM");//ҵ��Ա����

            new BaseBean().writeLog("ҵ��Ա����=========" + E_OUTBOUND.getString("ZBANKN"));
            double yfkjeoa = 0;
            double syyfjeoa = 0;
            double yifjeoa = 0;
            double ysqje = 0;//
            double sqfkje = 0;//
            JCO.Table T_OUTBOUND = Table00.getTable("T_OUTBOUND");//��ϸ1��Ϣ
            new BaseBean().writeLog("T_OUTBOUND.getNumRows()=========" + T_OUTBOUND.getNumRows());

            for (int i = 0; i < T_OUTBOUND.getNumRows(); i++) {
                T_OUTBOUND.setRow(i);
                String zterm = T_OUTBOUND.getString("ZTERM");//������������

                double yfkje = 0;
                String sratpz = Util.null2String(T_OUTBOUND.getString("RATPZ"));
                if ("".equals(sratpz) || "0".equals(sratpz)) {
                    sql = "select c.ratpz from " + tablename
                            + " a join workflow_requestbase b on a.requestid=b.requestid and b.currentnodetype='3' left join "
                            + tablename + "_dt1 c on a.id=c.mainid where a.bukrs='" + bukrs + "' and a.ebeln='"
                            + ebeln + "' and a.lifnr='" + lifnr + "' and c.zterm='" + zterm + "'";
                    rs.executeSql(sql);

                    if (rs.next()) {
                        sratpz = rs.getString("ratpz");
                    }
                }

                double ratpz = Double.parseDouble(sratpz);
                dt1Obj.put("zterm", zterm);//������������
                dt1Obj.put("text1", T_OUTBOUND.getString("TEXT1"));//������������
                dt1Obj.put("ratpz", sratpz);//��ͬ�������
                sql = "select sum(a.sqfkje) as yfkje from " + tablename
                        + " a join workflow_requestbase b on a.requestid=b.requestid and b.currentnodetype='3' where a.bukrs='"
                        + bukrs + "' and a.ebeln='" + ebeln + "' and a.lifnr='" + lifnr + "' and a.fkjd='"
                        + zterm + "'";
                rs.executeSql(sql);

                new BaseBean().writeLog("sql=3" + sql);
                if (rs.next()) {
                    yfkje = Double.parseDouble(Util.null2o(rs.getString("yfkje")));
                }
                double yfje = BigDecimalUtil.mul(zhtje, ratpz) / 100;//��ͬӦ�����
                dt1Obj.put("yfje", yfje);//��ͬӦ�����
                dt1Obj.put("yfkje", yfkje);//�Ѹ�����
                if (zterm.equals(ztermoa)) {
                    yfkjeoa = yfkje;
                    syyfjeoa = BigDecimalUtil.sub(yfje, yfkje);
                    yifjeoa = yfkje;

                    sql = "select sum(a.sqfkje) as zsqfkje from " + tablename
                            + " a join workflow_requestbase b on a.requestid=b.requestid and b.currentnodetype!='3' left join "
                            + tablename + "_dt1 c on a.id=c.mainid where a.bukrs='" + bukrs + "' and a.ebeln='"
                            + ebeln + "' and c.zterm='" + zterm + "' and a.lifnr='" + lifnr + "' and a.fkjd='" + zterm + "' and a.requestid!='" + requestid + "'";
                    rs.executeSql(sql);
                    new BaseBean().writeLog("sql=4" + sql);
                    if (rs.next()) {
                        ysqje = Double.parseDouble(Util.null2o(rs.getString("zsqfkje")));
                        new BaseBean().writeLog("ysqje" + ysqje);//��������
                    }

                    sqfkje = yfje - yfkje - ysqje;//���븶������ں�ͬӦ������ȥ�Ѹ�����ȥ��������

                }

                dt1Obj.put("syyfje", BigDecimalUtil.sub(yfje, yfkje));//ʣ��Ӧ�����
                String yqdp = "";

                sql = "select yqdp from " + tablename
                        + " a join workflow_requestbase b on a.requestid=b.requestid and b.currentnodetype='3' where a.bukrs='"
                        + bukrs + "' and a.ebeln='" + ebeln + "' and a.lifnr='" + lifnr + "' and a.fkjd='"
                        + zterm + "'";
                rs.executeSql(sql);
                if (rs.next()) {
                    yqdp = rs.getString("yqdp");
                }

                dt1Obj.put("yqdp", yqdp);//Ҫ��Ʊ

                //����׶�ΪYC09-12ʱ��ȡֵ�߼�
                if ("YC09".equals(zterm) || "YC10".equals(zterm) || "YC11".equals(zterm)
                        || "YC12".equals(zterm)) {
                    dt1Obj.put("ratpz", "100.000");//��ͬ�������
                    dt1Obj.put("text1", T_OUTBOUND.getString("TEXT0"));//������������
                    dt1Obj.put("yfje", zhtje);//��ͬӦ�����
                    dt1Obj.put("syyfje", BigDecimalUtil.sub(zhtje, yfkje));//ʣ��Ӧ�����

                    sql = "select sum(a.sqfkje) as zsqfkje from " + tablename
                            + " a join workflow_requestbase b on a.requestid=b.requestid and b.currentnodetype!='3' left join "
                            + tablename + "_dt1 c on a.id=c.mainid where a.bukrs='" + bukrs + "' and a.ebeln='"
                            + ebeln + "' and c.zterm='" + zterm + "' and a.lifnr='" + lifnr + "' and a.fkjd='"
                            + zterm + "' and a.requestid!='" + requestid + "'";
                    rs.executeSql(sql);
                    new BaseBean().writeLog("sql=5" + sql);
                    if (rs.next()) {
                        ysqje = Double.parseDouble(Util.null2o(rs.getString("zsqfkje")));
                        new BaseBean().writeLog("ysqje" + ysqje);//��������
                    }

                    sqfkje = zhtje - yfkje - ysqje;//���븶������ں�ͬӦ������ȥ�Ѹ�����ȥ��������
                }
                dt1Arr.add(dt1Obj);
            }

            dataObj.put("dt1", dt1Arr);
            dataObj.put("sqfkje", sqfkje);//���븶����
            dataObj.put("ysqje", ysqje);//��������
            dataObj.put("zhtje", zhtje);
            dataObj.put("yifjeoa", yifjeoa);
            dataObj.put("yfkjeoa", yfkjeoa);
            dataObj.put("syyfjeoa", syyfjeoa);
            dataObj.put("KOINH", KOINH);
            dataObj.put("ZBANKN", ZBANKN);
            dataObj.put("ywy", ywy);
        }
        out.print(dataObj.toString());
        new BaseBean().writeLog("dataObj.toString()" + dataObj.toString());
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (null != myConnection) {
            SAPUtil.releaseClient(myConnection);
        }
    }
%>