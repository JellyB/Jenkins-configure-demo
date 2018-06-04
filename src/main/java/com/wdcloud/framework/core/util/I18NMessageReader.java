package com.wdcloud.framework.core.util;

import com.wdcloud.ptyhzx.web.i18n.MessageSourceHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NamedThreadLocal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * 针对i18n消息提供的一个消息获取类。提供静态方法。
 * 读取的文件，参见{@link com.jeaw.commons.Constants.MESSAGE_BUNDLE_KEY}
 * 
 *
 */
public class I18NMessageReader {
	private static final ThreadLocal<String> localLanguage = new NamedThreadLocal<String>("Local language");
	
	private static MessageSourceHelper messageSourceHelper;
	
	public void setMessageSourceHelper(MessageSourceHelper messageSourceHelper) {
		this.messageSourceHelper = messageSourceHelper;
	}

	public static String getMessage(String mCode,Object... args) {
		return messageSourceHelper.getMessage(mCode, args);
	}
	
	public static String getMessage(String mCode) {
		return getMessage(mCode, null);
	}
	
	public static String getErrorText(String mCode,Object... args) {
		return messageSourceHelper.getMessage(mCode, args);
	}
	
	public static String getErrorText(String mCode) {
		return getErrorText(mCode, null);
	}
	
	/**
	 * 
	 * Description:获取消息多国语言版本
	 *
	 * @return
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getLanguage(){
		return messageSourceHelper.getLocale().getLanguage();
	}
	
	/**
	 * 
	 * Description:设置消息多国语言版本
	 *
	 * @param language 语言
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static void setLanguage(String language){
		localLanguage.set(language);
	}
	
	/**
	 * 
	 * Description:清楚线程设置语言
	 *
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static void clearLanguage(){
		localLanguage.remove();
	}
	
	/**
	 * 
	 * Description:从cookie中获取语言
	 *
	 * @return
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getLanguageFromCookie(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("language".equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * Description:获取默认语言
	 * @param request 
	 *
	 * @return
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static String getDefaultLanguage(){
		if(DEFAULT_LANGUAGE==null){
			String defaultLanguage = null;
			if(StringUtils.isEmpty(defaultLanguage)){
				defaultLanguage = LocaleContextHolder.getLocale().toString();
			}
			
			DEFAULT_LANGUAGE = defaultLanguage;
		}
		
		return DEFAULT_LANGUAGE;
	}
	
	private static String DEFAULT_LANGUAGE=null;
}
