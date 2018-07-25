package com.jiuyi.service;

import com.jiuyi.entity.MessageInfo;
import com.jiuyi.entity.SaleAndDeliveryMainTable;
import com.jiuyi.entity.SaleAndDeliveryParticulars;

public interface SaleAndDeliverySevice {
	public MessageInfo createWorkflow_102(SaleAndDeliveryMainTable sm,SaleAndDeliveryParticulars[] sp);
}
