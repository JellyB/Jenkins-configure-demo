<%@page import="java.sql.SQLException"%>
<%@page	import="java.io.IOException"%>
<%@page import="com.wdcloud.framework.core.exception.FrameworkException"%>
<%@ page import="org.apache.commons.logging.LogFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setContentType("text/plain;charset=UTF-8");

	if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
		out.print("{\"success\":false,\"msg\":\"提交数据出错！\"}");
		return;
	}
%>

<!--ERROR_MSG_TAG-->
<html>
	<head>
		<title>Error Page</title>
	</head>
	<body style="background:#fff;">
		<div style="background:url(../themes/blue/css/images/error1.jpg)no-repeat; width:668px; height:183px; position:absolute; left:50%; top:50%; margin-left:-334px; margin-top:-91px;"></div>
	</body>
</html>
