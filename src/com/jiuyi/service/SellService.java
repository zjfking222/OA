package com.jiuyi.service;

import com.jiuyi.entity.MessageInfo;
import com.jiuyi.entity.SellMainTable;
import com.jiuyi.entity.SellParticulars;

public interface SellService {
	public MessageInfo createWorkflow_103(SellMainTable sm,SellParticulars[] sp);
}
