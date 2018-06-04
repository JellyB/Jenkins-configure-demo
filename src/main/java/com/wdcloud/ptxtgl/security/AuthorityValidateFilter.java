package com.wdcloud.ptxtgl.security;

import com.wdcloud.framework.core.util.PropertyXmlMgr;
import com.wdcloud.framework.web.session.SessionInfoHolder;
import com.wdcloud.ptxtgl.resource.service.ResourceService;
import com.wdcloud.ptxtgl.userRole.service.UserRoleService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorityValidateFilter extends GenericFilterBean {

	private UserRoleService userRoleService;
	private ResourceService resourceService;
	private RedirectStrategy redirectStrategy;
	private SessionInfoHolder sessionInfoHolder;
	private String invalidAccessUrl;

	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	public AuthorityValidateFilter() {
		redirectStrategy = new DefaultRedirectStrategy();
	}

	public void setInvalidAccessUrl(String invalidAccessUrl) {
		this.invalidAccessUrl = invalidAccessUrl;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void setSessionInfoHolder(SessionInfoHolder sessionInfoHolder) {
		this.sessionInfoHolder = sessionInfoHolder;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		final String contextPath = request.getContextPath();
		final String reg = new StringBuilder().append(contextPath).append("(.+?)/").toString();
		String userId = this.sessionInfoHolder.getUserId(request);
		String appkey = PropertyXmlMgr.getString("CONF_COMMON", "PTXTGL_APPKEY");
		String roleCode = PropertyXmlMgr.getString("CONF_COMMON", "ROLE_CODE_ADMINISTRATOR");
		String separator = PropertyXmlMgr.getString("CONF_COMMON", "USERID_APPKEY_SEPARATOR");
		Set<String> roleCodeSet = userRoleService.getRoleCodesByUserIdAndAppkey(userId, appkey);
		if ((roleCodeSet.size() > 0) && roleCodeSet.contains(roleCode)) {
			chain.doFilter(request, response);
			return;
		} else {
			StringBuilder queryKey = new StringBuilder(userId).append(separator).append(appkey);
			Set<String> urlSet = resourceService.getResourceUrlByUserIdandAppkey(queryKey.toString());
			String resourceUrl = "";
			StringBuffer Url = request.getRequestURL();
			logger.info("User Access Resource Url:" + Url);
			//Pattern pattern = Pattern.compile("/ptxtgl(.+?)/");
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(Url.toString());
			if (matcher.find()) {
				resourceUrl = matcher.group(1);
			}
			if(urlAccessControl(resourceUrl)){
				chain.doFilter(request, response);
				return;
			}else{
				boolean access = false;
				if (urlSet.size() > 0) {
					if (urlSet.contains(resourceUrl)) {
						logger.info("Resource Url Access:Permitted" + resourceUrl);
						access = true;
					} else {
						access = false;
					}
				} else {
					access = false;
				}
				if (access) {
					chain.doFilter(request, response);
					return;
				} else {
					logger.error("Resource Url Access:Forbidden" + resourceUrl);
					if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
						response.setHeader("urlAccess", "Forbidden");// 在响应头设置请求状态
						return;
					} else {
						this.redirectStrategy.sendRedirect(request, response, invalidAccessUrl);
						return;
					}
				}
			}			
		}
	}
	
	/**
	 * 获取系统不拦截的访问路径
	 * @author bigd
	 * @param url
	 * @return
	 */
	public boolean urlAccessControl(String url){
		String urlAccessPermitted = PropertyXmlMgr.getString("CONF_SECURITY", "security.local.accessurl");
		List<String> urlAccessList = Arrays.asList(urlAccessPermitted.split(","));
		return urlAccessList.contains(url);
	}
}
