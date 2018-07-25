package com.jiuyi.action;

import weaver.general.BaseBean;

import com.eosdata.service.EosDataLocator;
import com.eosdata.service.EosDataSoap_PortType;

public class Test {
	public static void main(String[] args) {
		int error = 0;
		String message = "";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><item><!--供应商代码,必填-->    <GYSDM>102569</GYSDM>   <!--供应商名称,必填-->    <GYSMC>衢州市惠多利农资连锁有限公司</GYSMC>    <!--联系人-->    <GYSLXR>周新华</GYSLXR>    <!--供应产品类型-->    <GYSLX>服务</GYSLX>    <!--统一社会信用代码-->    <SH>91330800147755889T</SH>    <!--法定代表人-->    <FRDB>杨海果/周新华</FRDB>    <!--付款方式-->    <FKFS>A</FKFS>    <!--币别码-->    <BB>CNY</BB>    <!--SWIFT-->    <SWI>91330800147755889T</SWI>    <!--帐号-->    <ZH>19720201040000146</ZH>    <!--开户行名称-->    <KHHMC>农行衢州南湖支行</KHHMC>    <!--电话-->    <DH>0570-3850673</DH>    <!--手机-->    <SJ>13505701883</SJ>    <!--传真-->    <CZ>0570-3850673</CZ>    <!--注册资金-->    <ZCZJ>5000万元</ZCZJ>    <!--主要产品-->   <ZYCP>仓储、库房租赁</ZYCP>    <!--业务员号，OA端后续增加-->   <YWYH>153</YWYH>    <!--业务员-->    <YWYM>叶拓</YWYM>    <!--经营地址-->   <JYDZ>衢州市三衢路997号</JYDZ>    <!--付款条件-->    <TKM>A000</TKM>    <!--企业类型-->    <QYLX>服务</QYLX>    <!--纳税人分类-->    <NSRFL>一般纳税人</NSRFL>    <!--是否有历史业务-->    <YWLS>否</YWLS>    <!--维护用户-->    <WHYH>叶拓</WHYH>    <!--维护日期-->    <WHRQ>2018-06-04</WHRQ>    <!--资信审核结果-->    <ZXSHJG></ZXSHJG>    <!--所属地区-->    <DQMC>33</DQMC>    <!--供应商简称-->    <GYSJC>惠多利</GYSJC>    <!--邮编-->    <YB>324000</YB>    <!--E-MAIL-->    <E_mail></E_mail>    <!--资质证书-->    <ZZZS>营业执照、房屋产权证</ZZZS>    <!--渠道证明文件-->    <QDZMWJ></QDZMWJ>    <!--企业网址-->    <QYWZ></QYWZ>    <!--公司成立日期-->    <GSCLRQ>2000-9-8 0:00:00</GSCLRQ>    <!--合作项目简介-->    <HXJJ>有色衢州向该公司租赁仓库用于堆存产品</HXJJ>    <!--暂停供应商-->    <ZTGYSH>N</ZTGYSH>    </item>";
		try {

			new BaseBean().writeLog("xml======" + xml);
			EosDataLocator locator = new EosDataLocator();
			EosDataSoap_PortType service = locator.getEosDataSoap();
			message = service.supplierSave(xml);
			System.out.println(message);
		} catch (Exception e) {
			message = e.getMessage();
			e.printStackTrace();
			new BaseBean().writeLog("cmztj" + e);
			error = 1;
			System.out.println("No");
		} finally {
		}

	}
}
