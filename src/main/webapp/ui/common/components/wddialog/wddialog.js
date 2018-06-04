(function($) {
	// ##################################### 全局变量定义，可修改 #####################################start
	var cookiename = "language"; // 国际化cookiename
	// ##################################### 全局变量定义，可修改 #####################################end
	var zindex = document.createElement('input');
	zindex.id = "wddialog-zindex";
	zindex.type = "hidden";
	zindex.value = "10000";
	document.body.appendChild(zindex);
	// ##################################### 国际化处理 #####################################start
	var span1; // 提示
	var span2; // 确 定
	var span3; // 当前处理失败
	var span4; // 当前处理成功
	var span5; // 取 消
	var language;
	var arr, reg = new RegExp("(^| )" + cookiename + "=([^;]*)(;|$)");

	if (document.cookie.match(reg) == null) {
		language = "zh";
	} else {
		arr = document.cookie.match(reg);
		language = unescape(arr[2]);
	}
	//	var language;
	//	if (navigator.browserLanguage) { // ie
	//		language = navigator.browserLanguage.toUpperCase();
	//	} else {
	//		language = navigator.language.toUpperCase();
	//	}
	switch (language) {
		case "zh": // 中文
			span1 = "提示";
			span2 = "确 定";
			span3 = "当前处理失败";
			span4 = "提示";
			span5 = "取 消";
			break;
		case "en": // 英文
			span1 = "Reminder";
			span2 = "OK";
			span3 = "Current process failed";
			span4 = "Current process succeeded";
			span5 = "Cancel";
			break;
	}
	// ##################################### 国际化处理 #####################################end
	// 设置弹出框可移动
	var o = {};

	function FramitemMove(className) {
		$("." + className).mousedown(function(e) {
			o.iTop = $(this).position().top - e.pageY;
			o.iLeft = $(this).position().left - e.pageX;
			$this = $(this);

			$(document).bind("mousemove", function(e) {
				var iLeft = e.pageX + o.iLeft;
				var iTop = e.pageY + o.iTop;

				if (iLeft - 225 < 0) iLeft = 225;
				if (iTop - 75 < 0) {
					iTop = 75;
				}

				$this.css({
					'left': iLeft + "px",
					'top': iTop + "px"
				})
			});

			$(document).bind("mouseup", function() {
				$(document).unbind("mousemove");
				$(document).unbind("mouseup");
			});
		});
	}
	////////////////////////////////////////////////////////////////////////提示框重写
	// txt 显示文本
	// title弹出框标题
	// fn关闭按钮回调函数
	window.alert = function(txt, title, fn, hideimg, buttons) {
		var uuid = new Date().getTime();
		if (!title) {
			title = span1; //"提示";
		}
		var shield = document.createElement("DIV");
		shield.id = "shield" + uuid;
		shield.className = "shield";
		shield.style.zIndex = parseInt(document.getElementById('wddialog-zindex').value) + 1;
		var alertFram = document.createElement("DIV");
		alertFram.id = "alertFram" + uuid;
		alertFram.className = "Framitem";
		alertFram.style.zIndex = parseInt(document.getElementById('wddialog-zindex').value) + 2;
		strHtml = "<ul class='ulstyle'>";
		strHtml += " <li class='lititlestyle'><div class='lititledeteilstyle'>" + title + "</div><div id='wd-alert-X" + uuid + "' class='lititlectrlstyle' onclick='doalertOk(\"" + uuid + "\")'></div></li>";
		strHtml += " <li class='liitemstyle'><div class='txtbox'>";
		if (hideimg) {
			strHtml += " <div class='itemimg itemimgalert' style='display:none'></div>";
			strHtml += " <div class='itemtxt itemtxtalert' style='width:400px;margin:0'>" + txt + "<div><div></li>";
		} else {
			strHtml += " <div class='itemimg itemimgalert'></div>";
			strHtml += " <div class='itemtxt itemtxtalert'>" + txt + "<div><div></li>";
		}
		if (buttons != undefined) {
			strHtml += " <li class='libuttonareastyle'><div class='libuttonareadivboxstyle'>";
			var backgroundClass;
			var styleText = "";
			for (var i = 0; i < buttons.length; i++) {
				if (buttons[i].mainButFlg || buttons[i].mainButFlg == undefined) {
					backgroundClass = "libuttonokstyle";
				} else {
					backgroundClass = "libuttoncanlestyle";
				}
				if (buttons[i].hideflg) {
					styleText = " style='display:none' ";
				}
				strHtml += " <input id='" + buttons[i].buttonId + "' " + styleText + " class='" + backgroundClass + "' type='button' value='" + buttons[i].buttonName + "' onclick='doalertFunction(\"" + uuid + "\",\"" + buttons[i].buttonName + "\")' />";
			}
			strHtml += " <div></li>";
		} else {
			strHtml += " <li class='libuttonareastyle'><div class='libuttonareadivboxstyle'><input id='wd-alert-ok" + uuid + "' class='libuttonokstyle' type='button' value='" + span2 + "' onclick='doalertOk(\"" + uuid + "\")' /><div></li>";

		}
		strHtml += "</ul>";
		alertFram.innerHTML = strHtml;
		document.body.appendChild(alertFram);
		document.body.appendChild(shield);
		if($("#wd-alert-ok" + uuid).length > 0)	$("#wd-alert-ok" + uuid).focus();
		// 文字过多居中显示处理
		if ($(".itemtxtalert")[0].scrollHeight > 50) {
			var tempheight = parseInt($(".itemtxtalert")[0].scrollHeight - 50) / 2;
			if (parseInt($(".itemtxtalert")[0].clientHeight - tempheight) > 0) {
				$(".itemtxtalert")[0].style.marginTop = parseInt($(".itemtxtalert")[0].clientHeight - tempheight) + "px";
			} else {
				$(".itemtxtalert")[0].style.marginTop = "10px";
			}
			if (parseInt($(".itemtxtalert")[0].clientHeight + tempheight) < 100) {
				$(".itemtxtalert")[0].style.height = parseInt($(".itemtxtalert")[0].clientHeight + tempheight) + "px";
			} else {
				$(".itemtxtalert")[0].style.height = "100px";
			}
		}
		document.getElementById('wddialog-zindex').value = parseInt(document.getElementById('wddialog-zindex').value) + 2;

		this.doalertFunction = function(selfuuid, buttonName) {
			for (var i = 0; i < buttons.length; i++) {
				if (buttons[i].buttonName == buttonName) {
					buttons[i].buttonFunction.call();
				}
			}
			document.body.removeChild(document.getElementById("alertFram" + selfuuid));
			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};

		this.doalertOk = function(selfuuid) {
			if (fn) {
				fn.call();
			}
			document.body.removeChild(document.getElementById("alertFram" + selfuuid));
			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};
		document.getElementById("alertFram" + uuid).focus();
		//		document.body.onselectstart = function() {
		//			return false;
		//		};

		//FramitemMove("Framitem");

	};

	////////////////////////////////////////////////////////////////////////提示框重写
	// txt 显示文本
	// title弹出框标题
	// fn关闭按钮回调函数
	window.error = function(txt, title, fn, buttons) {
		var uuid = new Date().getTime();
		if (!title) {
			title = span3; //"当前处理失败";
		}
		var shield = document.createElement("DIV");
		shield.id = "shield" + uuid;
		shield.className = "shield";
		shield.style.zIndex = parseInt(document.getElementById('wddialog-zindex').value) + 1;
		var errorFram = document.createElement("DIV");
		errorFram.id = "errorFram" + uuid;
		errorFram.className = "Framitem";
		errorFram.style.zIndex = parseInt(document.getElementById('wddialog-zindex').value) + 2;
		strHtml = "<ul class='ulstyle'>";
		strHtml += " <li class='lititlestyle'><div class='lititledeteilstyle'>" + title + "</div><div id='wd-error-X" + uuid + "' class='lititlectrlstyle' onclick='doerrorOk(\"" + uuid + "\")'></div></li>";
		strHtml += " <li class='liitemstyle'><div class='txtbox'><div class='itemimg itemimgerror'></div><div class='itemtxt itemtxtalert'>" + txt + "<div><div></li>";

		if (buttons != undefined) {
			strHtml += " <li class='libuttonareastyle'><div class='libuttonareadivboxstyle'>";
			var backgroundClass;
			for (var i = 0; i < buttons.length; i++) {
				if (buttons[i].mainButFlg || buttons[i].mainButFlg == undefined) {
					backgroundClass = "libuttonokstyle";
				} else {
					backgroundClass = "libuttoncanlestyle";
				}
				strHtml += " <input class='" + backgroundClass + "' type='button' value='" + buttons[i].buttonName + "' onclick='doerrorFunction(\"" + uuid + "\",\"" + buttons[i].buttonName + "\")' />";
			}
			strHtml += " <div></li>";
		} else {
			strHtml += " <li class='libuttonareastyle'><div class='libuttonareadivboxstyle'><input id='wd-alert-ok" + uuid + "' class='libuttonokstyle' type='button' value='" + span2 + "' onclick='doerrorOk(\"" + uuid + "\")' /><div></li>";

		}

		strHtml += "</ul>";
		errorFram.innerHTML = strHtml;
		document.body.appendChild(errorFram);
		document.body.appendChild(shield);
		// 文字过多居中显示处理
		if ($(".itemtxtalert")[0].scrollHeight > 50) {
			var tempheight = parseInt($(".itemtxtalert")[0].scrollHeight - 50) / 2;
			if (parseInt($(".itemtxtalert")[0].clientHeight - tempheight) > 0) {
				$(".itemtxtalert")[0].style.marginTop = parseInt($(".itemtxtalert")[0].clientHeight - tempheight) + "px";
			} else {
				$(".itemtxtalert")[0].style.marginTop = "10px";
			}
			if (parseInt($(".itemtxtalert")[0].clientHeight + tempheight) < 100) {
				$(".itemtxtalert")[0].style.height = parseInt($(".itemtxtalert")[0].clientHeight + tempheight) + "px";
			} else {
				$(".itemtxtalert")[0].style.height = "100px";
			}
		}
		document.getElementById('wddialog-zindex').value = parseInt(document.getElementById('wddialog-zindex').value) + 2;

		this.doerrorFunction = function(selfuuid, buttonName) {
			for (var i = 0; i < buttons.length; i++) {
				if (buttons[i].buttonName == buttonName) {
					buttons[i].buttonFunction.call();
				}
			}
			document.body.removeChild(document.getElementById("errorFram" + selfuuid));
			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};

		this.doerrorOk = function(selfuuid) {
			if (fn) {
				fn.call();
			}
			document.body.removeChild(document.getElementById("errorFram" + selfuuid));
			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};

		document.getElementById("errorFram" + uuid).focus();
		//		document.body.onselectstart = function() {
		//			return false;
		//		};

		//FramitemMove("Framitem");
	};

	////////////////////////////////////////////////////////////////////////成功提示框重写
	// txt 显示文本
	// title弹出框标题
	// fn关闭按钮回调函数
	window.success = function(txt, title, fn, buttons,buttonareaflg) {
		var uuid = new Date().getTime();
		if (!title) {
			title = span4; //"当前处理成功";
		}
		var shield = document.createElement("DIV");
		shield.id = "shield" + uuid;
		shield.className = "shield";
		shield.style.zIndex = parseInt(document.getElementById('wddialog-zindex').value) + 1;
		var successFram = document.createElement("DIV");
		successFram.id = "successFram" + uuid;
		successFram.className = "Framitem";
		successFram.style.zIndex = parseInt(document.getElementById('wddialog-zindex').value) + 2;
		strHtml = "<ul class='ulstyle'>";
		strHtml += " <li class='lititlestyle'><div class='lititledeteilstyle'>" + title + "</div><div id='wd-success-X" + uuid + "' class='lititlectrlstyle' onclick='dosuccessOk(\"" + uuid + "\")'></div></li>";
		strHtml += " <li class='liitemstyle'><div class='txtbox'><div class='itemimg itemimgsuccess'></div><div class='itemtxt itemtxtalert'>" + txt + "<div><div></li>";

		if (buttonareaflg != false) {
			if (buttons != undefined) {
				strHtml += " <li class='libuttonareastyle'><div class='libuttonareadivboxstyle'>";
				var backgroundClass;
				for (var i = 0; i < buttons.length; i++) {
					if (buttons[i].mainButFlg || buttons[i].mainButFlg == undefined) {
						backgroundClass = "libuttonokstyle";
					} else {
						backgroundClass = "libuttoncanlestyle";
					}
					strHtml += " <input class='" + backgroundClass + "' type='button' value='" + buttons[i].buttonName + "' onclick='dosuccessFunction(\"" + uuid + "\",\"" + buttons[i].buttonName + "\")' />";
				}
				strHtml += " <div></li>";
			} else {
				strHtml += " <li class='libuttonareastyle'><div class='libuttonareadivboxstyle'><input id='wd-alert-ok" + uuid + "' class='libuttonokstyle' type='button' value='" + span2 + "' onclick='dosuccessOk(\"" + uuid + "\")' /><div></li>";

			}
		}

		strHtml += "</ul>";
		successFram.innerHTML = strHtml;
		document.body.appendChild(successFram);
		document.body.appendChild(shield);
		// 文字过多居中显示处理
		if ($(".itemtxtalert")[0].scrollHeight > 50) {
			var tempheight = parseInt($(".itemtxtalert")[0].scrollHeight - 50) / 2;
			if (parseInt($(".itemtxtalert")[0].clientHeight - tempheight) > 0) {
				$(".itemtxtalert")[0].style.marginTop = parseInt($(".itemtxtalert")[0].clientHeight - tempheight) + "px";
			} else {
				$(".itemtxtalert")[0].style.marginTop = "10px";
			}
			if (parseInt($(".itemtxtalert")[0].clientHeight + tempheight) < 100) {
				$(".itemtxtalert")[0].style.height = parseInt($(".itemtxtalert")[0].clientHeight + tempheight) + "px";
			} else {
				$(".itemtxtalert")[0].style.height = "100px";
			}
		}
		document.getElementById('wddialog-zindex').value = parseInt(document.getElementById('wddialog-zindex').value) + 2;

		this.dosuccessFunction = function(selfuuid, buttonName) {
			for (var i = 0; i < buttons.length; i++) {
				if (buttons[i].buttonName == buttonName) {
					buttons[i].buttonFunction.call();
				}
			}
			document.body.removeChild(document.getElementById("successFram" + selfuuid));
			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};

		this.dosuccessOk = function(selfuuid) {
			if (document.getElementById("successFram" + selfuuid) == null) return;
			if (fn) {
				fn.call();
			}
			if (document.getElementById("successFram" + selfuuid) != null) {
				document.body.removeChild(document.getElementById("successFram" + selfuuid));
				document.body.removeChild(document.getElementById("shield" + selfuuid));
			}
		};
		document.getElementById("successFram" + uuid).focus();
		//		document.body.onselectstart = function() {
		//			return false;
		//		};
		setTimeout(function() {
			dosuccessOk(uuid)
		}, 2000);
		//FramitemMove("Framitem");
	};

	////////////////////////////////////////////////////////////////////////询问框重写
	// txt 显示文本
	// title弹出框标题
	// fnok确定按钮回调函数
	// fncanle取消按钮回调函数
	window.confirm = function(txt, title, fnok, fncanle, buttons) {
		var uuid = new Date().getTime();
		if (!title) {
			title = span1; //"提示";
		}
		var shield = document.createElement("DIV");
		shield.id = "shield" + uuid;
		shield.className = "shield";
		shield.style.zIndex = parseInt(document.getElementById('wddialog-zindex').value) + 1;
		var confirmFram = document.createElement("DIV");
		confirmFram.id = "confirmFram" + uuid;
		confirmFram.className = "Framitem";
		confirmFram.style.zIndex = parseInt(document.getElementById('wddialog-zindex').value) + 2;
		strHtml = "<ul class='ulstyle'>";
		strHtml += " <li class='lititlestyle'><div class='lititledeteilstyle'>" + title + "</div><div id='wd-confirm-X" + uuid + "' class='lititlectrlstyle' onclick='doconfirmCanle(\"" + uuid + "\")'></div></li>";
		strHtml += " <li class='liitemstyle'><div class='txtbox'><div class='itemimg itemimgconfirm'></div><div class='itemtxt itemtxtalert'>" + txt + "<div><div></li>";

		if (buttons != undefined) {
			strHtml += " <li class='libuttonareastyle'><div class='libuttonareadivboxstyle'>";
			var backgroundClass;
			for (var i = 0; i < buttons.length; i++) {
				if (buttons[i].mainButFlg || buttons[i].mainButFlg == undefined) {
					backgroundClass = "libuttonokstyle";
				} else {
					backgroundClass = "libuttoncanlestyle";
				}
				strHtml += " <input class='" + backgroundClass + "' type='button' value='" + buttons[i].buttonName + "' onclick='doconfirmFunction(\"" + uuid + "\",\"" + buttons[i].buttonName + "\")' />";
			}
			strHtml += " <div></li>";
		} else {
			strHtml += " <li class='libuttonareastyle'><div class='libuttonareadivboxstyle'>";
			strHtml += " <input id='wd-confirm-ok" + uuid + "' class='libuttonokstyle' type='button' value='" + span2 + "' onclick='doconfirmOk(\"" + uuid + "\")' />";
			strHtml += " <input id='wd-confirm-cancel" + uuid + "' class='libuttoncanlestyle' type='button' value='" + span5 + "' onclick='doconfirmCanle(\"" + uuid + "\")' />";
			strHtml += " <div></li>";
		}

		strHtml += "</ul>";
		confirmFram.innerHTML = strHtml;
		document.body.appendChild(confirmFram);
		document.body.appendChild(shield);
		// 文字过多居中显示处理
		if ($(".itemtxtalert")[0].scrollHeight > 50) {
			var tempheight = parseInt($(".itemtxtalert")[0].scrollHeight - 50) / 2;
			if (parseInt($(".itemtxtalert")[0].clientHeight - tempheight) > 0) {
				$(".itemtxtalert")[0].style.marginTop = parseInt($(".itemtxtalert")[0].clientHeight - tempheight) + "px";
			} else {
				$(".itemtxtalert")[0].style.marginTop = "10px";
			}
			if (parseInt($(".itemtxtalert")[0].clientHeight + tempheight) < 100) {
				$(".itemtxtalert")[0].style.height = parseInt($(".itemtxtalert")[0].clientHeight + tempheight) + "px";
			} else {
				$(".itemtxtalert")[0].style.height = "100px";
			}
		}
		document.getElementById('wddialog-zindex').value = parseInt(document.getElementById('wddialog-zindex').value) + 2;

		this.doconfirmFunction = function(selfuuid, buttonName) {
			for (var i = 0; i < buttons.length; i++) {
				if (buttons[i].buttonName == buttonName) {
					buttons[i].buttonFunction.call();
				}
			}

			document.body.removeChild(document.getElementById("confirmFram" + selfuuid));
			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};

		this.doconfirmOk = function(selfuuid) {
			if (fnok) {
				fnok.call();
			}
			document.body.removeChild(document.getElementById("confirmFram" + selfuuid));
			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};
		this.doconfirmCanle = function(selfuuid) {
			if (fncanle) {
				fncanle.call();
			}
			document.body.removeChild(document.getElementById("confirmFram" + selfuuid));
			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};
		document.getElementById("confirmFram" + uuid).focus();
		//		document.body.onselectstart = function() {
		//			return false;
		//		};

		//FramitemMove("Framitem");
	};
	////////////////////////////////////////////////////////////////////////询问框重写
	// url 展示页面路径
	// title弹出框标题
	// width弹出框宽度
	// height弹出框高度
	// fnok确定按钮回调函数
	// fncanle取消按钮回调函数
	dialog = function(url, title, width, height, fnok, fncanle, buttons,buttonareaflg) {
		var uuid = new Date().getTime();
		if (!title) {
			title = span1; //"提示";
		}

		if (height > window.screen.availHeight - 40 || !height) {
			height = window.screen.availHeight - 40 - 40;
		}
		if (width > window.document.body.clientWidth || !width) {
			width = window.document.body.clientWidth;
		}
		var shield = document.createElement("DIV");
		shield.id = "shield" + uuid;
		shield.className = "shield";
		shield.style.zIndex = parseInt(document.getElementById('wddialog-zindex').value) + 1;
		var condialog = document.createElement("DIV");
		condialog.className = "condialog";
		condialog.id = "condialog" + uuid;
		condialog.style.position = "fixed";
		condialog.style.left = "50%";
		condialog.style.top = "30px";
		condialog.style.marginLeft = "-" + parseInt(width / 2) + "px";
		condialog.style.width = width + "px";
		condialog.style.height = height + "px";
		condialog.style.background = "#ccc";
		condialog.style.textAlign = "center";
		condialog.style.zIndex = parseInt(document.getElementById('wddialog-zindex').value) + 2;
		strHtml = "<ul class='ulstyle'>";
		strHtml += " <li class='lititlestyle'><div class='lititledeteilstyle'>" + title + "</div><div id='wd-condialog-X" + uuid + "' class='lititlectrlstyle' onclick='dodialogCanle(\"" + uuid + "\")'></div></li>";
		strHtml += " <li class='liitemstyledialog' style='height:" + height + "px'><iframe class='wd-dialog-iframe' src=" + url + " id='ad-main" + uuid + "' name='ad-main' width='100%' height='" + height + "px' border='1px' frameborder='0'></iframe></li>";

		if (buttonareaflg != false) {
			if (buttons != undefined) {
				strHtml += " <li class='libuttonareastyle'><div class='libuttonareadivboxstyle'>";
				var backgroundClass;
				var styleText = "";
				for (var i = 0; i < buttons.length; i++) {
					if (buttons[i].mainButFlg || buttons[i].mainButFlg == undefined) {
						backgroundClass = "libuttonokstyle";
					} else {
						backgroundClass = "libuttoncanlestyle";
					}
					if (buttons[i].hideflg) {
						styleText = " style='display:none' ";
					}
					strHtml += " <input id='" + buttons[i].buttonId + "' " + styleText + " class='wddialogzdybut " + backgroundClass + "' type='button' value='" + buttons[i].buttonName + "' onclick='dodialogFunction(\"" + uuid + "\",\"" + buttons[i].buttonName + "\")' />";
				}
				strHtml += " <div></li>";
			} else {
				strHtml += " <li class='libuttonareastyle'><div class='libuttonareadivboxstyle'>";
				strHtml += " <input id='wd-condialog-ok" + uuid + "' class='libuttonokstyle' type='button' value='" + span2 + "' onclick='dodialogOk(\"" + uuid + "\")' />";
				strHtml += " <input id='wd-condialog-cancel" + uuid + "' class='libuttoncanlestyle' type='button' value='" + span5 + "' onclick='dodialogCanle(\"" + uuid + "\")' />";
				strHtml += " <div></li>";
			}
		}
		strHtml += "</ul>";
		condialog.innerHTML = strHtml;
		document.body.appendChild(condialog);
		document.body.appendChild(shield);
		document.getElementById('wddialog-zindex').value = parseInt(document.getElementById('wddialog-zindex').value) + 2;

		this.dodialogFunction = function(selfuuid, buttonName) {
			for (var i = 0; i < buttons.length; i++) {
				if (buttons[i].buttonName == buttonName) {
					buttons[i].buttonFunction.call();
				}
			}

			//			document.body.removeChild(document.getElementById("condialog" + selfuuid));
			//			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};

		dodialogOk = function(selfuuid) {
			if (fnok) {
				fnok.call();
			}
			//			document.body.removeChild(document.getElementById("condialog" + selfuuid));
			//			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};
		dodialogCanle = function(selfuuid) {
			if (fncanle) {
				fncanle.call();
			}
			document.body.removeChild(document.getElementById("condialog" + selfuuid));
			document.body.removeChild(document.getElementById("shield" + selfuuid));
		};
		document.getElementById("condialog" + uuid).focus();
		//		document.body.onselectstart = function() {
		//			return false;
		//		};

		Win = document.getElementById('ad-main' + uuid).contentWindow; //$("#ad-main" + uuid).contents(); // 获取子页面对象
		WinObj = document.getElementById('ad-main' + uuid).contentWindow; // 获取子页面方法
		// 关闭方法
		Win.Closed = function() {
			var subuuid = this.frameElement.id.split('ad-main')[1];
			document.body.removeChild(document.getElementById("condialog" + subuuid));
			document.body.removeChild(document.getElementById("shield" + subuuid));
		};

		//FramitemMove("condialog");

		return this;
	};
	// 弹出框
	$.fn.dialog = function(objs) {
		var defaults = {
			title: span1
		};
		var objs = $.extend(defaults, objs);
		dialog(objs.url, objs.title, objs.width, objs.height, objs.successFun, objs.cancelFun, objs.buttons, objs.buttonareaflg);
	};
	// 提示
	$.fn.alert = function(objs) {
		var defaults = {
			title: span1,
			hidImg: false
		};
		var objs = $.extend(defaults, objs);
		alert(objs.innerHtml, objs.title, objs.functionName, objs.hidImg, objs.buttons);
	};
	// 错误
	$.fn.error = function(objs) {
		var defaults = {
			title: span3
		};
		var objs = $.extend(defaults, objs);
		error(objs.innerHtml, objs.title, objs.functionName, objs.buttons);
	};
	// 成功
	$.fn.success = function(objs) {
		var defaults = {
			title: span4
		};
		var objs = $.extend(defaults, objs);
		success(objs.innerHtml, objs.title, objs.functionName, objs.buttons, objs.buttonareaflg);
	};
	// 询问
	$.fn.confirm = function(objs) {
		var defaults = {
			title: span1
		};
		var objs = $.extend(defaults, objs);
		confirm(objs.innerHtml, objs.title, objs.okFunction, objs.cancelFunction, objs.buttons);
	};
})(jQuery);

// 关闭弹出框（对外开放）
function wdDialogClosed(flg) {
	var subuuid = $(".wd-" + flg + "-iframe")[0].id.split('ad-main')[1];
	var ctrlName;
	switch (flg) {
		case "dialog":
			ctrlName = "condialog";
			break;
	}
	document.body.removeChild(document.getElementById(ctrlName + subuuid));
	document.body.removeChild(document.getElementById("shield" + subuuid));
}
// 按钮交替显示
function wdDialogButtonChange(btns) {
	var wdshowbuttonId = btns.wdshowbuttonId.split(",");
	var wdhidebuttonid = btns.wdhidebuttonid.split(",");
	$(".wddialogzdybut").hide();
	for (var i = 0; i < wdshowbuttonId.length; i++) {
		$("#" + wdshowbuttonId[i]).show();
	}
	for (var i = 0; i < wdhidebuttonid.length; i++) {
		$("#" + wdhidebuttonid[i]).hide();
	}
}