package com.jiuyi.util;

import weaver.conn.RecordSet;

/**
 * 判断人员id是否存在
 * @author SCP
 * @param 登录id
 * @return ""为未找到否则返回userid
 */
public class JudgeUserId {
	public static String judegid(String loginid){
		RecordSet rs = new RecordSet();
		String sql = "select id from hrmresource where loginid = ? and loginid <>'' and loginid is not null";
		rs.executeQuery(sql, loginid);
		if(rs.next()){
			String userid = rs.getString("id");//获取人员id
			return userid;
		}
		return "";
	}
}
