package com.jiuyi.service.impl;

import com.jiuyi.action.Disqualification;
import com.jiuyi.entity.DisqualificationMainTable;
import com.jiuyi.entity.DisqualificationParticulars;
import com.jiuyi.entity.MessageInfo;
import com.jiuyi.service.DisqualificationService;
import com.jiuyi.util.PropUtil;

public class DisqualificationImpl implements DisqualificationService{

	public MessageInfo createWorkflow(DisqualificationMainTable dmt,DisqualificationParticulars[] dp) {
		String lx = dmt.getQMART();//发起流程   来料 F2  产品 F3 售后 F1
		MessageInfo msg = new MessageInfo();
		Disqualification df = new Disqualification();
		if("f1".equalsIgnoreCase(lx)){
			msg = df.execute(dmt, dp, PropUtil.AfterSalesDisqualification);
		}else if ("f2".equalsIgnoreCase(lx)){
			msg = df.execute(dmt, dp, PropUtil.RawMaterialDisqualification);
		}else if ("f3".equalsIgnoreCase(lx)){
			msg = df.execute(dmt, dp, PropUtil.ProductDisqualification);
		}else{
			msg.setMsg("发起类型不匹配");
		}
		return msg;
	}
} 
