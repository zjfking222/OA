package com.jiuyi.service.impl;

import com.jiuyi.action.SaleAndDelivery;
import com.jiuyi.entity.MessageInfo;
import com.jiuyi.entity.SaleAndDeliveryMainTable;
import com.jiuyi.entity.SaleAndDeliveryParticulars;
import com.jiuyi.service.SaleAndDeliverySevice;

public class SaleAndDeliveryImpl implements SaleAndDeliverySevice{

	public MessageInfo createWorkflow_102(SaleAndDeliveryMainTable sm,SaleAndDeliveryParticulars[] sp) {
		MessageInfo msg = new MessageInfo();
		SaleAndDelivery sd = new SaleAndDelivery();
		msg = sd.execute(sm, sp);
		return msg;
	}

}
