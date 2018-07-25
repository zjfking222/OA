package com.jiuyi.service.impl;

import com.jiuyi.action.SellTicketsOrWriteOff;
import com.jiuyi.entity.MessageInfo;
import com.jiuyi.entity.SellMainTable;
import com.jiuyi.entity.SellParticulars;
import com.jiuyi.service.SellService;

public class SellImpl implements SellService{

	public MessageInfo createWorkflow_103(SellMainTable sm, SellParticulars[] sp) {
		MessageInfo msg = new MessageInfo();
		msg = new SellTicketsOrWriteOff().execute(sm, sp);
		return msg;
	}

}
