var ssologinrequestType = "get"; // 请求方式，真实环境下改为post
var SSOLoginUrl = "http://qj.wd.com:8020/WD-UI-FrameWork/webapp/"; // 请求登录地址ip（使用前需调整）
var CheckLoginRequestAddress = SSOLoginUrl + "app/json/demo/ssologin.json"; // 本工程判断是否登录的请求路径（使用前需调整）
//var SSOloginPageAddress = SSOLoginUrl + "app/jsp/demo/ssologin.html"; // 登录页面地址
var SSOloginPageAddress = "http://hd.wd.com:9000/login-server/app/jsp/main/ssologin.html";
var SSOloginPageAddressNoApp = "http://hd.wd.com:9000/login-server/validate/login2";
// 销毁登录框
function closeSSOLoginBox() {
	wd_ssoLoginBackFunction.call();
	document.body.removeChild(document.getElementById("loginshield"));
	document.body.removeChild(document.getElementById("ssoLoginBox"));
}

function onlyCloseSSOLoginBox() {
	document.body.removeChild(document.getElementById("loginshield"));
	document.body.removeChild(document.getElementById("ssoLoginBox"));
}

var wd_ssoLoginBackFunction; // 寄存登录后的操作

function refreshHotSearchCallback(result) {
	if (result.isSuccess)
		if (wd_ssoLoginBackFunction)
			wd_ssoLoginBackFunction.call();
}

function SSOLoginNoApp(fn) {
	wd_ssoLoginBackFunction = fn;
	var temp = new Date().getTime();
	var t = document.createElement('script');
	t.src = SSOloginPageAddressNoApp + "?time="+temp+"&callback=refreshHotSearchCallback"; // 测试地址
	t.type = 'text/javascript';
	document.getElementsByTagName('head')[0].appendChild(t);
}

// 封装页面方法
function SSOLogin(fn) {
	wd_ssoLoginBackFunction = fn;
	var shield = document.createElement("DIV"); // 创建遮蔽层
				shield.id = "loginshield";
				shield.style.position = "fixed";
				shield.style.left = "0px";
				shield.style.top = "0px";
				shield.style.width = "100%";
				shield.style.height = "100%";
				shield.style.backgroundColor = "#333";
				shield.style.textAlign = "center";
				shield.style.opacity = "0.3";
				shield.style.filter = "alpha(opacity=30)";
				shield.style.zIndex = 90;
				document.body.appendChild(shield);

				var loginIframe = document.createElement('iframe');
				loginIframe.id = "ssoLoginBox";
				loginIframe.width = "430px";
				loginIframe.height = "353px";
				loginIframe.border = "0";
				loginIframe.frameBorder = "0";
				loginIframe.style.position = "fixed";
				loginIframe.style.left = "50%";
				loginIframe.style.marginLeft = "-215px";
				loginIframe.style.top = "10%";
				loginIframe.style.zIndex = 91;
				loginIframe.src = SSOloginPageAddress + "?parentLocation=" + window.location.href;
				document.body.appendChild(loginIframe);
}

window.onload = function() {
	var pageflg = window.location.href.split('=')[1];
	if (pageflg == "ssoLoginBack1") parent.parent.closeSSOLoginBox();
	else parent.parent.onlyCloseSSOLoginBox();
};