package com.jiuyi.service.impl;


import com.jiuyi.action.EquipmentReceive;
import com.jiuyi.entity.EquipmentReceiveMainTable;
import com.jiuyi.entity.EquipmentReceiveParticulars;
import com.jiuyi.entity.MessageInfo;
import com.jiuyi.service.EquipmentReceiveService;

public class EquipmentReceiveImpl implements EquipmentReceiveService{

	public MessageInfo createWorkflow(EquipmentReceiveMainTable em, EquipmentReceiveParticulars[] ep) {
		MessageInfo msg = new MessageInfo();
		msg = new EquipmentReceive().execute(em, ep);
		return msg;
	}

}
