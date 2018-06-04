function loadvalidateForm(url) {
	var t = document.createElement('script');
	t.src = url; // 测试地址
	t.type = 'text/javascript';
	t.id = "jy";
	document.getElementsByTagName('head')[0].appendChild(t);

	t.onload = function() {
		automaticCheck();
	};
	// ie处理
	t.onreadystatechange = function() {
		if (this.readyState == "loaded" || this.readyState == "complete") {
			automaticCheck();
		}
	}
}

function validateForm() {
	var returnStr = "";
	var customStr = "";
	for (var i = 0; i < checkDetailArray.length; i++) {
		var temprules = checkDetailArray[i].split(':')[1];
		var tempid = checkDetailArray[i].split(':')[0];
		var tmprul = temprules.split(';');
		var value;
		if ($("#" + tempid).length > 0) {
			value = $("#" + tempid).val();
			for (var j = 0; j < tmprul.length; j++) { // 遍历所有规则
				if (tmprul[j] == "required") { // 必填项校验
					if (value == "") returnStr = returnStr + $("#" + tempid)[0].title + addColon($("#" + tempid)[0].title) + "该输入项为必输项" + "<br/>";
				} else { // 其他校验
					var validType = tmprul[j].split('=')[1];
					var result = /([a-zA-Z_]+)(.*)/.exec(validType); // [0]=Length[1,2];[1]=Length;[2]=[1,2]

					var rule = wdvalidateBoxrules.rules[result[1]];

					if (value && rule) {
						var param = eval(result[2]);
						if (!rule['validator'](value, param)) {
							var message = rule['message'];
							if (param) {
								for (var k = 0; k < param.length; k++) {
									message = message.replace(new RegExp("\\{" + k + "\\}", "g"), param[k]);
								}
							}

							returnStr = returnStr + $("#" + tempid)[0].title + addColon($("#" + tempid)[0].title) + message + "<br/>";
						}
					}
				}
			}
		}
	}
	
	// 页面自定义验证方法
	if (typeof customCheckDate === 'function') {
		customStr = customCheckDate();
	}
	
	if (returnStr != "") {
		if(customStr != "") returnStr = returnStr + customStr;
		top.alert(returnStr);
		return false;
	}

	return true;
}

function addColon(innerhtml) {
	if (innerhtml.indexOf(":") > 0) return "";
	else if (innerhtml.indexOf("：") > 0) return "";
	else return "：";
}

String.prototype.trim = function() {　　
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.ltrim = function() {　　
	return this.replace(/(^\s*)/g, "");
};
String.prototype.rtrim = function() {　　
	return this.replace(/(\s*$)/g, "");
};

function createvaildateTip() {
	var toolTip = $("<div id='multivaildateTip'></div>");
	$(document.body).append(toolTip);
}
function automaticCheck() {
	for (var i = 0; i < checkDetailArray.length; i++) {
		var tempid = checkDetailArray[i].split(':')[0];
		$("#" + tempid).bind('blur', function() {
			var returnStr = "";
			var value = $(this).val();
			for (var j = 0; j < checkDetailArray.length; j++) {
				if (checkDetailArray[j].split(':')[0] == this.id) {
					var temprules = checkDetailArray[j].split(':')[1];
					var tmprul = temprules.split(';');
					for (var k = 0; k < tmprul.length; k++) { // 遍历所有规则
						if (tmprul[k] == "required") { // 必填项校验
							if (value == "") returnStr = returnStr + $(this)[0].title + "：" + "该输入项为必输项";
						} else { // 其他校验
							var validType = tmprul[k].split('=')[1];
							var result = /([a-zA-Z_]+)(.*)/.exec(validType); // [0]=Length[1,2];[1]=Length;[2]=[1,2]
							var rule = wdvalidateBoxrules.rules[result[1]];
							if (value && rule) {
								var param = eval(result[2]);
								if (!rule['validator'](value, param)) {
									var message = rule['message'];
									if (param) {
										for (var l = 0; l < param.length; l++) {
											message = message.replace(new RegExp("\\{" + l + "\\}", "g"), param[l]);
										}
									}
									returnStr = returnStr + $(this)[0].title + "：" + message;
								}
							}
						}
					}
				}
			}

			var $multivaildateTip = $('#multivaildateTip');
			if (returnStr != "") {
				$multivaildateTip.text(returnStr);
				$multivaildateTip.show();
				var winWidth = $(window).width();
				var winHeight = $(window).height();
				var poz = $(this).offset();
				var tipTop = 0;
				if ($multivaildateTip.outerHeight() > poz.top) {
					tipTop = poz.top + $multivaildateTip.outerHeight() + 3;
				} else {
					tipTop = poz.top - $multivaildateTip.outerHeight() - 3;
				}
				var tipLeft = poz.left + $multivaildateTip.outerWidth + 5 > winWidth ?
					poz.left + $(this).outerWidth - $multivaildateTip.outerWidth : poz.left;

				$multivaildateTip.css({
					top: tipTop,
					left: tipLeft,
					width: $(this).width()
				});

				$(this).addClass("validateborder");
			} else {
				$multivaildateTip.hide();
				$(this).removeClass("validateborder");
			}
		})
	}
}

$(document).ready(function() {
	// 初始化提示
	createvaildateTip();
});