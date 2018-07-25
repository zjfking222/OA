package com.jiuyi.service;

import com.jiuyi.entity.EquipmentReceiveMainTable;
import com.jiuyi.entity.EquipmentReceiveParticulars;
import com.jiuyi.entity.MessageInfo;


public interface EquipmentReceiveService {
	public MessageInfo createWorkflow(EquipmentReceiveMainTable em,EquipmentReceiveParticulars[] ep);
}
