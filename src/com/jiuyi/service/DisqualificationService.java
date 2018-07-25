package com.jiuyi.service;

import com.jiuyi.entity.DisqualificationMainTable;
import com.jiuyi.entity.DisqualificationParticulars;
import com.jiuyi.entity.MessageInfo;

/**
 * 不合格处理流程接口
 * @author SCP
 *
 */
public interface DisqualificationService {
	public MessageInfo createWorkflow(DisqualificationMainTable dmt,DisqualificationParticulars[] dp);
}
