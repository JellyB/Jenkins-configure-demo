<%@page import="com.wdcloud.framework.web.validation.entity.ValidateResult"%>
<%@page import="com.wdcloud.framework.web.exception.ValidateException"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%
	Exception ex = (Exception)request.getAttribute("exception");
	ValidateException ve = null;
	String message = "";
	boolean canGoto = false;
	if(ex instanceof ValidateException) {
		ve = (ValidateException)ex;
		message = ve.getMessage();
		canGoto = true;
	}
%>
<head>
	<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/ui/common/components/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">
		$(function(){
			if(<%=canGoto%>){
				var spanObj = $("#s");
				var timeout = 5;
				spanObj.text(timeout);
				setInterval(function(){
					if(--timeout==0){
						location = "${pageContext.request.contextPath}<%=ve.getInputView()%>";
					}else{
						spanObj.text(timeout);
					}
				},1*1000);
			}
		});
	</script>
</head>
<body>
	<%=message%>
	<br/>
	页面<span id="s"></span>秒后将自动跳转到编辑页面……
</body>