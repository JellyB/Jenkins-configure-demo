<%@ page contentType="text/html;charset=UTF-8"%>
<%
	String error = request.getParameter("error");
	String sessionErrorMsg = "";
	if(error==null||"".equals(error)){
		error = session.getAttribute("sessionErrorKey")==null?null:session.getAttribute("sessionErrorKey").toString();
	}
	
	if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
		out.print("{\"success\":false, \"error\":\"session-expire\", \"msg\":\""+sessionErrorMsg+"\"}");
		return;
	}else if ("expire".equals(error) || "timeout".equals(error)){ 
		sessionErrorMsg = "由于长时间未操作，为了您的使用安全请重新登录。";
	}else if("logout".equals(error)){
		sessionErrorMsg = "你所使用的账户已经从系统注销，请你重新登录。";
	}else if("eliminate".equals(error)){
		sessionErrorMsg = "相同账号已在其他地方登录，该会话已过期。";
	}
	if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
		out.print("{\"success\":false, \"error\":\"session-expire\", \"msg\":\""+sessionErrorMsg+"\"}");
		return;
	}else{
%>
<script type="text/javascript">
	alert("<%=sessionErrorMsg%>");
	top.location.href = "${pageContext.request.contextPath}/login.jsp";
</script>
<%}%>