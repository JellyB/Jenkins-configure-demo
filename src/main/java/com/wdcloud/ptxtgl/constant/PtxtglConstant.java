package com.wdcloud.ptxtgl.constant;

import com.wdcloud.framework.core.util.PropertyXmlMgr;

public class PtxtglConstant {
	
	public static final String  RETURN_RESULT = "isSuccess";
	
	public static final String  RETURN_MSG = "msg";
	
	/**分页开始**/
	public final static String PAGE_START = "pageStart";
	/**分页每页展示条数**/
	public final static String PAGE_SIZE = "pageSize";
	/**展示列表rows**/
	public final static String RETURN_ROWS = "rows";
	/**展示列表条数records**/
	public final static String RETURN_RECORDS = "records";
	/**结果集**/
	public final static String RETURN_DATA = "data";
	/**Hbase loginid 计数器*/
	public final static String HBASE_INCREMENT_LOGINID = PropertyXmlMgr.getString("CONF_COMMON",
			"HBASE_INCREMENT_LOGINID");
	
	/**查询条件 用户ID 及 归属系统 Appkey 分割符**/
	public final static String USERID_APPKEY_SEPARATOR = PropertyXmlMgr.getString("CONF_COMMON",
			"USERID_APPKEY_SEPARATOR");
	


}
