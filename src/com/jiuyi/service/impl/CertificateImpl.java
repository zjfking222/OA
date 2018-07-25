package com.jiuyi.service.impl;

import com.jiuyi.action.Certificate;
import com.jiuyi.entity.CertificateMainTable;
import com.jiuyi.entity.CertificateParticulars;
import com.jiuyi.entity.MessageInfo;
import com.jiuyi.service.CertificateService;

public class CertificateImpl implements CertificateService{

	public MessageInfo createWorkflow_104(CertificateMainTable cm, CertificateParticulars[] cp) {
		MessageInfo msg = new MessageInfo();
		msg = new Certificate().execute(cm, cp);
		return msg;
	}

}
