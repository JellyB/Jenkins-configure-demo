package com.wdcloud.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.wdcloud.framework.core.query.Page;

/** 
 * Description: TODO[描述该类概要功能介绍]
 *
 * @author Administrator
 * @date 2015年9月22日
 * @version 1.0 
 */
public class PageUtil {
	/**
	 * @Method: com.wdcloud.jyx.common.web.action.JyxBaseAction.getPageParameter
	 * @Description: jqGrid列表查询用page对象初始化
	 * @author: 咖啡豆
	 * @date: 2015年6月11日
	 * @version: 1.0
	 * @return
	 *         Page
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static Page getPageForJqGrid(HttpServletRequest request) {

		Page page = new Page();
		int pageNum = 1;
		if (StringUtils.isNotBlank(request.getParameter("page"))) {
			pageNum = Integer.parseInt(request.getParameter("page"));
		}
		if (StringUtils.isNotBlank(request.getParameter("rows"))) {
			page.setPageSize(Integer.parseInt(request.getParameter("rows")));
		}
		page.setStart(pageNum == 1 ? 0 : page.getPageSize() * (pageNum - 1));
		if (StringUtils.isNotBlank(request.getParameter("sidx")) && StringUtils.isNotBlank(request.getParameter("sord"))) {
			String[] sidxs = request.getParameter("sidx").split(",");
			String[] sords = request.getParameter("sord").split(",");
			String orders = "";
			if (sidxs.length == sords.length) {
				for (int i = 0; i < sidxs.length; i++) {
					orders += sidxs[i] + " " + sords[i] + ",";
				}
				page.setOrder(orders.substring(0, orders.length() - 1));
			}
		}

		return page;
	}
}
