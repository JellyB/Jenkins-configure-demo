package com.wdcloud.common.constant;

import org.apache.commons.lang.math.NumberUtils;

import com.wdcloud.framework.core.util.PropertyXmlMgr;

public class UicConstants {

	/** 返回结果：成功 */
	public final static String SUCCESS = "1";

	/** 返回结果：失败 */
	public final static String FAILURE = "0";

	/** 返加值：结果 */
	public final static String RETURN_RESULT = "result";

	/** 返加值：是否成功 */
	public final static String RETURN_ISSUCCESS = "isSuccess";

	/** 返加值：数据 */
	public final static String RETURN_DATA = "data";

	/** 返回值：数据总条数 */
	public final static String RETURN_TOTAL = "total";

	/** 返回值：消息 */
	public final static String RETURN_MSG = "msg";
	
	/** 登录方式：昵称 */
	public final static int LOGINTYPE_NICKNAME = 1;

	/** 登录方式：邮箱 */
	public final static int LOGINTYPE_EMAIL = 2;

	/** 登录方式：手机号 */
	public final static int LOGINTYPE_CELLPHONE = 3;

	/** 登录方式：LOGINID */
	public final static int LOGINTYPE_LOGINID = 4;
	
	/** 公用字段： 版本号*/
	public final static String FIELD_VERSIONS = "versions";
	
	/** 公用字段：描述 */
	public final static String FIELD_DESCN = "descn";
	
	/** 公用字段： 操作标识*/
	public final static String FIELD_DELFLAG = "delflag";
	
	/** 公用字段：创建人 */
	public final static String FIELD_CREATERCODE = "creatercode";
	
	/** 公用字段： 创建时间*/
	public final static String FIELD_CREATERTIME = "creatertime";	

	/** 公用字段：操作人 */
	public final static String FIELD_OPERATERCODE = "operatercode";
	
	/** 公用字段： 操作时间*/
	public final static String FIELD_OPERATETIME = "operatetime";

	/** 手机验证码在Session中的键名 */
	public final static String SESSION_CELLPHONECODE = "_cellphone_code_in_session";


	/** 默认手机短信服务 */
	public final static String DEFAULT_SMS_URL = PropertyXmlMgr.getString("CONF_COMMON", "framework.default.sms.url");

	/** 默认手机短信账号 */
	public final static String DEFAULT_SMS_ACCOUNT = PropertyXmlMgr.getString("CONF_COMMON", "framework.default.sms.account");

	/** 默认手机短信账号的密码 */
	public final static String DEFAULT_SMS_PASSWORD = PropertyXmlMgr.getString("CONF_COMMON", "framework.default.sms.account.password");

	/** 默认邮箱 */
	public final static String DEFAULT_EMAIL = PropertyXmlMgr.getString("CONF_COMMON", "framework.default.email");

	/** 默认邮箱密码 */
	public final static String DEFAULT_EMAIL_PASSWORD = PropertyXmlMgr.getString("CONF_COMMON", "framework.default.email.password");

	/** 默认邮件服务器 */
	public final static String DEFAULT_EMAIL_HOST = PropertyXmlMgr.getString("CONF_COMMON", "framework.default.email.host");

	/** 默认访问地址 */
	public final static String DEFAULT_CONTEXTPATH = PropertyXmlMgr.getString("CONF_COMMON", "framework.default.contextpath");
	
	/**
	 * Object转String
	 * @author CHENYB
	 * @param o
	 * @return
	 * @since 2015年8月1日 上午10:33:39
	 */
	public static String getStringFromObject(Object o) {
		if (o == null) {
			return null;
		}
		return o.toString();
	}

	/**
	 * Object转Long
	 * @author CHENYB
	 * @param o
	 * @return
	 * @since 2015年8月1日 上午10:28:10
	 */
	public static Long getLongFromObject(Object o) {
		if (o == null) {
			return null;
		}
		if (NumberUtils.isNumber(o.toString())) {
			return Long.parseLong(o.toString());
		}
		return null;
	}

	/**
	 * Object转Integer
	 * @author CHENYB
	 * @param o
	 * @return
	 * @since 2015年8月13日 下午3:00:13
	 */
	public static Integer getIntegerFromObject(Object o) {
		if (o == null) {
			return null;
		}
		if (NumberUtils.isNumber(o.toString())) {
			return Integer.parseInt(o.toString());
		}
		return null;
	}

}
