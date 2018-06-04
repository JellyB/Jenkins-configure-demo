<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
		<title>资源访问出错</title>
<style type="text/css">

/*错误页面样式*/
.error-box {
	margin: 100px auto;
	width: 100%;
	height: 260px;
	border: 0px solid #d8d6d6;
}

.error-box span {
	font-size: 120px;
	font-family: Arial;
	display: block;
	text-align: center;
	margin-top: 30px;
	color: #666;
}

.error-box div {
	text-align: left;
	font-size: 12px;
	width:380px;
	margin:10px auto;
}
</style>

	</head>
	<body>
	<div class="error-box">
		<span>403</span>
		<div>对不起，您不能访问该资源，可能原因如下：</div>
		<div>1、您长时间没有操作，系统自动退出，请重新登陆。</div>
		<div>2、您没有权限访问该资源，如果需要开通访问权限，请联系管理员。</div>
		<div>请点击&nbsp;<a target="_top" class="link-login" href="${pageContext.request.contextPath}/localLogout">登录</a>
		&nbsp;重新进入本系统。</div>
	</div>
	

	</body>
</html>