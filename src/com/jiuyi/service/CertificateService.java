package com.jiuyi.service;

import com.jiuyi.entity.CertificateMainTable;
import com.jiuyi.entity.CertificateParticulars;
import com.jiuyi.entity.MessageInfo;


/**
 * 物资出门证流程
 * @author SCP
 *
 */
public interface CertificateService {
	public MessageInfo createWorkflow_104(CertificateMainTable cm,CertificateParticulars[] sp);
}
