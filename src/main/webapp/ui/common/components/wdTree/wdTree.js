(function($) {
	var mlid = "mlid_";
	var sjmlid = "sjmlid_";
	var mltext = "mltext_";
	var mljdcj = "mljdcj_";
	var mlbj = "mlbj_";
	var mltj = "mltj_";
	var mlsc = "mlsc_";
	var mlss = "mlss_";
	var mlsy = "mlsy_";
	var mlxy = "mlxy_";
	var mlxz = "mlxz_";
	var mlcj = 1;
	var replaceCode = "#";
	var deleteTitle;
	var notUpMoveTitle;
	var notDownMoveTitle;
	var waiting;
	var newleaf;
	var nameEmptyAlert;
	var checkNameLength;
	var checkMaxLevel;
	var cookiename = "language";
	var language;
	var editTip;
	var addTip;
	var deleteTip;
	var upTip;
	var downTip;
	var arr, reg = new RegExp("(^| )" + cookiename + "=([^;]*)(;|$)");
	if (document.cookie.match(reg) == null) {
		language = "zh";
	} else {
		arr = document.cookie.match(reg);
		language = unescape(arr[2]);
	}

	switch (language) {
		case "zh": // 中文
			deleteTitle = "确认要删除么？";
			notUpMoveTitle = "已经是顶级节点，不能再上移";
			notDownMoveTitle = "已经是末级节点，不能再下移";
			waiting = "处理中请等待。。。";
			newleaf = "新节点";
			nameEmptyAlert = "名称不能为空！";
			checkNameLength = "输入内容不能超过{1}个汉字，{0}个字母或数字";
			checkMaxLevel = "最大层级不能超过{0}层";
			editTip = "修改";
			addTip = "新增";
			deleteTip = "删除";
			upTip = "上移";
			downTip = "下移";
			break;
		case "en": // 英文
			deleteTitle = "Are you sure you want to delete?";
			notUpMoveTitle = "It's the top node and cannot move up.";
			notDownMoveTitle = "It's the end node and cannot move down.";
			waiting = "Processing, please wait.";
			newleaf = "New node";
			nameEmptyAlert = "The name should NOT be empty!";
			checkNameLength = "Content should NOT exceed {1} Chinese characters, {0} letters or numbers.";
			checkMaxLevel = "The maximum level should NOT exceed {0}.";
			editTip = "Modify";
			addTip = "Add";
			deleteTip = "Delete";
			upTip = "Move up";
			downTip = "Move down";
			break;
	}
	// 格式化id
	function numformat(num, formatnum) {
		var strings;
		if (num.toString().length < formatnum.length) {
			var temp = formatnum.substring(0, formatnum.length - num.toString().length);
			strings = "" + temp + "" + num;
		} else {
			strings = num;
		}

		return "" + strings;
	}
	// 目录更新
	function mlupdate(obj) {
		$(".wd_zjml_title").removeClass("titleactive");
		$(".wd_zjml_title").attr("contenteditable", "false");

		$('#' + mltext + obj.id.replace(mlbj, '')).addClass("titleactive");
		$('#' + mltext + obj.id.replace(mlbj, '')).attr("contenteditable", "true");


		var len = $('#' + mltext + obj.id.replace(mlbj, ''))[0].innerHTML.length;
		var updateobj = document.getElementById(mltext + obj.id.replace(mlbj, ''));

		$('#' + mltext + obj.id.replace(mlbj, '')).focus();
	}
	// 目录新增
	function mladd(obj, mlcj, data, objs, _this) {
		var uuid = new Date;
		uuid = uuid.getTime();
		if (data != undefined) {
			uuid = data.id;
		}
		var appobj = document.getElementById(mlid + obj.id.replace(mltj, ''));
		var bjmlid = mlid + obj.id.replace(mltj, '');
		var jsondata = {
			id: uuid,
			text: newleaf
		};

		addZjml(appobj, jsondata, bjmlid, mlcj, objs, _this);
	}
	// 目录删除
	function mldelete(obj) {
		var sjobjid = $("#" + obj.id.replace(mlsc, sjmlid)).val();
		var sjspanid = $("#" + obj.id.replace(mlsc, sjmlid)).val().replace(mlid, mlss);
		$("#" + mlid + obj.id.replace(mlsc, '')).remove();
		var iCount = $("#" + sjobjid).find('li').length;
		// 处理节点样式，改变为叶子
		if (iCount == 0) {
			$("#" + sjspanid).removeClass("ssactive");
			$("#" + sjspanid).removeClass("ss");
			$("#" + sjspanid).addClass("leaf");
		}
	}
	// 目录收缩
	function mlshrink(obj) {
		if (obj.className == 'ssactive') {
			$(obj).removeClass('ssactive');
			$(obj).addClass('ss');
			$("#" + mlid + obj.id.replace(mlss, '')).find('li').hide();
		} else {
			$(obj).removeClass('ss');
			$(obj).addClass('ssactive');
			$("#" + mlid + obj.id.replace(mlss, '')).find('li').show();

			var liCount = $("#" + mlid + obj.id.replace(mlss, '')).find('li').length;
			for (var i = 0; i < liCount; i++) {
				if ($("#" + mlid + obj.id.replace(mlss, '')).find('li')[i].firstChild.className == 'ss') {
					$($("#" + mlid + obj.id.replace(mlss, '')).find('li')[i].firstChild).removeClass('ss');
					$($("#" + mlid + obj.id.replace(mlss, '')).find('li')[i].firstChild).addClass('ssactive');
				}
			}
		}
	}
	// 获取上移下移目标位置
	function getindex(obj, flg, mltype, lilength, objs) {
		var index = $(".wd_zjml li").index($("#" + mlid + obj.id.replace(mltype, ''))) + (parseInt(objs.indexflg) * flg); // 待操作控件index
		if (index < lilength) {
			var twosjid = $("#" + sjmlid + obj.id.replace(mltype, '')).val(); // 当前上级id
			var onesjid = $("#" + sjmlid + $(".wd_zjml li").eq(index)[0].id.replace(mlid, '')).val(); //待操作上级id
			if (twosjid != onesjid) {
				objs.indexflg = parseInt(objs.indexflg) + 1;
				getindex(obj, flg, mltype, lilength, objs);
			} else {
				objs.indexflg = index;
			}
		} else {
			objs.indexflg = index;
		}
	}
	// 上移
	function mlup(oneobj, twoobj, objs) {
		$(oneobj).insertAfter($(twoobj));
		objs.indexflg = 1;
	}
	// 下移
	function mldwon(oneobj, twoobj, objs) {
		$(twoobj).insertAfter($(oneobj));
		objs.indexflg = 1;
	}
	// 添加目录结构，处理N级目录
	function addZjml(appobj, jsondata, sjmlids, mlcj, objs, _this) {
		var _objthis = _this;
		var div = document.createElement('div'); // 容器
		div.className = "zjmldiv";
		var li = document.createElement('li');
		li.id = mlid + jsondata.id;
		li.className = mlid + jsondata.id;
		li.style.paddingLeft = parseInt((mlcj - 1) * 10) + "px";
		// 处理收缩
		if (jsondata.children != undefined) {
			var spanss = document.createElement('div');
			spanss.id = mlss + jsondata.id;
			spanss.className = "ssactive";
			$(spanss).bind('click', function() {
				mlshrink(this);
			});
			li.appendChild(spanss);
		} else {
			var spanss = document.createElement('div');
			spanss.id = mlss + jsondata.id;
			spanss.className = "leaf";
			li.appendChild(spanss);
		}
		// 添加checkbox
		if (objs.checkflg) {
			var treeCheck = document.createElement("input");
			if (objs.singleSelectionFlg) {
				treeCheck.type = "radio";
				treeCheck.name = "wdtreecheck" + objs.objid;
			} else {
				treeCheck.type = "checkbox";
			}
			var selecttext = objs.selecttext.split(',');
			var tmpvalue = "";
			for (var i = 0; i < selecttext.length; i++) {
				tmpvalue += selecttext[i] + ":'" + jsondata[selecttext[i]] + "',";
			}
			tmpvalue = tmpvalue.substring(0, tmpvalue.length - 1);
			treeCheck.text = tmpvalue;
			treeCheck.id = mlxz + jsondata.id;
			treeCheck.className = "wdtreecheckbox wdtreecheck" + objs.objid;
			li.appendChild(treeCheck);
		}

		// 处理标题
		var span = document.createElement('label');
		span.name = jsondata.text;
		span.innerHTML = jsondata.text;
		span.title = jsondata.text;
		$(span).attr("for",mlxz + jsondata.id);
		span.style.width = objs.nameMaxViweLength + "px";
		span.className = "wd_zjml_title";
		span.id = mltext + jsondata.id;
		if (jsondata.updateFlag || jsondata.updateFlag == undefined) {
			// 屏蔽回车键
			$(span).bind('keypress', function(event) {
				if (event.keyCode == "13") window.event.returnValue = false;
			});
			$(span).bind('dblclick', function() {
				$(".wd_zjml_title").removeClass("titleactive");
				$(".wd_zjml_title").attr("contenteditable", "false");
				$(this).addClass("titleactive");
				$(this).attr("contenteditable", "true");
				$(this).focus();
				$(span).removeAttr("for");
			});
			$(span).bind('blur', function() {
				if (!objs.token) return false;
				$(span).attr("for",mlxz + jsondata.id);
				objs.token = false;
				var _this = this;
				// 处理输入空
				if (this.innerHTML.replace('<br>', '') == "") { // replace('<br>','') 处理火狐
					var msg = "";
					if (objs.nameRequiredTip != undefined) {
						msg = objs.nameRequiredTip;
					} else {
						msg = nameEmptyAlert;
					}
					if (!objs.bootFlg) {
						error(msg);
					} else {
						$.bootboxAlert('', msg, null, "error");
					}
					objs.token = true;
					return false;
				}

				if (this.innerHTML != this.name) {
					// 处理输入大小
					var lenValue = this.innerHTML.trim();
					var len = 0;
					for (i = 0; i < lenValue.length; i++) {
						if (lenValue.charCodeAt(i) > 256) {
							len += 2;
						} else {
							len++;
						}
					}

					if (len > parseInt(objs.nameMaxLength)) {
						var param = [parseInt(objs.nameMaxLength), (parseInt(objs.nameMaxLength) / 2).toString().split('.')[0]];

						for (var k = 0; k < param.length; k++) {
							checkNameLength = checkNameLength.replace(new RegExp("\\{" + k + "\\}", "g"), param[k]);
						}

						if (!objs.bootFlg) {
							error(checkNameLength);
						} else {
							$.bootboxAlert('', checkNameLength, null, "error");
						}
						objs.token = true;
						return false;
					}
					var updateText = this.innerHTML.trim();
					var postData = {
						"id": this.id.replace(mltext, ''),
						"name": this.innerHTML.trim()
					};
					crossDomainAjax(objs, objs.url.replace(replaceCode, objs.nameUpdateFunctionName), postData, function(data) {
						objs.token = true;
						$(_this)[0].title = updateText;
						$(_this)[0].name = updateText;
						$(_this).html(updateText);
						$(_this).removeClass("titleactive");
						$(".wd_zjml_title").attr("contenteditable", "false");
					});
				} else {
					objs.token = true;
					$(_this).removeClass("titleactive");
					$(".wd_zjml_title").attr("contenteditable", "false");
				}
			})
		}
		li.appendChild(span);
		// 上级目录id
		var inputs = document.createElement('input');
		inputs.value = sjmlids;
		inputs.id = sjmlid + jsondata.id;
		inputs.style.float = "left";
		inputs.style.display = "none";
		li.appendChild(inputs);
		// 是否加载操作按钮
		if (objs.ctrlFlg) {
			// 编辑按钮
			if (jsondata.updateshow || jsondata.updateshow == undefined) {
				if (jsondata.updateFlag || jsondata.updateFlag == undefined) {
					var bjspan = document.createElement('span');
					bjspan.innerHTML = "";
					bjspan.title = editTip;
					bjspan.id = mlbj + jsondata.id;
					bjspan.className = "zjmlctrl zjml-update";
					$(bjspan).bind('click', function() {
						$(span).removeAttr("for");
						mlupdate(this);
					});
					li.appendChild(bjspan);
				}
			}
			// 删除按钮
			if (jsondata.deleteshow || jsondata.deleteshow == undefined) {
				var scspan = document.createElement('span');
				scspan.innerHTML = "";
				scspan.title = deleteTip;
				scspan.id = mlsc + jsondata.id;
				scspan.className = "zjmlctrl zjml-delete";
				$(scspan).bind('click', function() {
					if (!objs.token) return false;
					objs.token = false;
					var _this = this;
					var functionName = "";
					if (objs.deleteCheckFunctionName != undefined) {
						functionName = objs.deleteCheckFunctionName;
					} else {
						functionName = objs.deleteFunctionName;
					}
					// 非boot3框架使用
					if (!objs.bootFlg) {
						confirm(deleteTitle, '', function() {
							var postData = {
								"id": _this.id.replace(mlsc, '')
							};
							crossDomainAjax(objs, objs.url.replace(replaceCode, functionName), postData, function(data) {
								objs.token = true;
								mldelete(_this);
							});
						}, function() {
							objs.token = true;
						})
					}
					// boot3框架使用
					else {
						$.bootboxConfrim(deleteTitle, function() {
							var postData = {
								"id": _this.id.replace(mlsc, '')
							};
							crossDomainAjax(objs, objs.url.replace(replaceCode, functionName), postData, function(data) {
								objs.token = true;
								mldelete(_this);
								document.body.style.overflowY = "scroll";
							});
						}, function() {
							objs.token = true;
						})
					}
				});
				li.appendChild(scspan);
			}
			mlcj = mlcj + 1; // 记录层级

			// 添加按钮
			if (jsondata.addshow || jsondata.addshow == undefined) {
				var tjspan = document.createElement('span');
				tjspan.innerHTML = "";
				tjspan.id = mltj + jsondata.id;
				tjspan.title = addTip;
				tjspan.className = "zjmlctrl zjml-add";
				$(tjspan).bind('click', function() {
					if (!objs.token) return false;
					objs.token = false;
					// 处理最大层级
					if (mlcj > objs.maxLevel) {
						checkMaxLevel = checkMaxLevel.replace(new RegExp("\\{0\\}", "g"), objs.maxLevel);
						if (!objs.bootFlg) {
							alert(checkMaxLevel);
						} else {
							$.bootboxAlert('', checkMaxLevel, null, "error");
						}
						objs.token = true;
						return false;
					}

					var _this = this;

					var sjid = _this.id.replace(mltj, '');
					var postData = {
						"sjid": sjid
					};
					var functionName = "";
					if (objs.addCheckFunctionName != undefined) {
						functionName = objs.addCheckFunctionName;
					} else {
						functionName = objs.addFunctionName;
					}

					crossDomainAjax(objs, objs.url.replace(replaceCode, functionName), postData, function(data) {
						objs.token = true;
						// 处理叶子变节点
						var spansstmp = $("#" + mlss + _this.id.replace(mltj, ''));
						if (spansstmp[0].className == "leaf") {
							spansstmp.removeClass("leaf");
							spansstmp.addClass("ssactive");
							spansstmp.bind('click', function() {
								mlshrink(this);
							});
						}
						mladd(_this, mlcj, data, objs, _objthis);
					});
				});
				li.appendChild(tjspan);
			}

			// 上移按钮
			if (jsondata.moveupshow || jsondata.moveupshow == undefined) {
				var syspan = document.createElement('span');
				syspan.innerHTML = "";
				syspan.id = mlsy + jsondata.id;
				syspan.title = upTip;
				syspan.className = "zjmlctrl zjml-up";
				$(syspan).bind('click', function() {
					if (!objs.token) return false;
					objs.token = false;
					var _this = this;
					getindex(_this, -1, mlsy, $(".wd_zjml li").length, objs); // 获取待处理控件位置

					if (objs.indexflg < 0) {

						if (!objs.bootFlg) {
							alert(notUpMoveTitle);
						} else {
							$.bootboxAlert('', notUpMoveTitle, null, "error");
						}
						objs.token = true;
						objs.indexflg = 1;
						return false;
					}

					var twosjid = $("#" + sjmlid + _this.id.replace(mlsy, '')).val(); // 当前上级id
					var onesjid = $("#" + sjmlid + $(".wd_zjml li").eq(objs.indexflg)[0].id.replace(mlid, '')).val(); //待操作上级id

					var oneobj = $(".wd_zjml li").eq(objs.indexflg)[0].parentElement; // 待操作控件
					var twoobj = $("#" + mlid + _this.id.replace(mlsy, ''))[0].parentElement; // 当前控件
					if (twosjid == onesjid) {
						// 生成遮蔽层
						var shield = document.createElement("DIV");
						shield.id = "shield" + _objthis[0].id;
						shield.className = "zjml_shield";
						_objthis[0].appendChild(shield);

						var texts = document.createElement('span');
						texts.className = "zjml_shield_text";
						texts.id = "shieldtext" + _objthis[0].id;
						texts.innerHTML = waiting;
						_objthis[0].appendChild(texts);

						var postData = {
							"upid": oneobj.firstChild.id.replace(mlid, ''),
							"downid": twoobj.firstChild.id.replace(mlid, '')
						};

						crossDomainAjax(objs, objs.url.replace(replaceCode, objs.upMoveFunctionName), postData, function(data) {
							objs.token = true;
							$("#" + "shield" + _objthis[0].id).remove();
							$("#" + "shieldtext" + _objthis[0].id).remove();
							mlup(oneobj, twoobj, objs);
						});
					} else {
						objs.indexflg = 1;
					}
				});
				li.appendChild(syspan);
			}
			// 下移按钮
			if (jsondata.movedownshow || jsondata.movedownshow == undefined) {
				var xyspan = document.createElement('span');
				xyspan.innerHTML = "";
				xyspan.title = downTip;
				xyspan.id = mlxy + jsondata.id;
				xyspan.className = "zjmlctrl zjml-down";
				$(xyspan).bind('click', function() {
					if (!objs.token) return false;
					objs.token = false;
					var _this = this;
					getindex(_this, 1, mlxy, $(".wd_zjml li").length, objs);

					if (objs.indexflg == $(".wd_zjml li").length) {
						if (!objs.bootFlg) {
							alert(notDownMoveTitle);
						} else {
							$.bootboxAlert('', notDownMoveTitle, null, "error");
						}
						objs.token = true;
						objs.indexflg = 1;
						return false;
					}

					var twosjid = $("#" + sjmlid + _this.id.replace(mlxy, '')).val(); // 当前上级id
					var onesjid = $("#" + sjmlid + $(".wd_zjml li").eq(objs.indexflg)[0].id.replace(mlid, '')).val(); //待操作上级id

					var oneobj = $(".wd_zjml li").eq(objs.indexflg)[0].parentElement; // 待操作控件
					var twoobj = $("#" + mlid + _this.id.replace(mlxy, ''))[0].parentElement; // 当前控件

					if (twosjid == onesjid) {
						// 生成遮蔽层
						var shield = document.createElement("DIV");
						shield.id = "shield" + _objthis[0].id;
						shield.className = "zjml_shield";
						_objthis[0].appendChild(shield);

						var texts = document.createElement('span');
						texts.className = "zjml_shield_text";
						texts.id = "shieldtext" + _objthis[0].id;
						texts.innerHTML = waiting;
						_objthis[0].appendChild(texts);

						var postData = {
							"upid": twoobj.firstChild.id.replace(mlid, ''),
							"downid": oneobj.firstChild.id.replace(mlid, '')
						};

						crossDomainAjax(objs, objs.url.replace(replaceCode, objs.downMoveFunctionName), postData, function(data) {
							objs.token = true;
							$("#" + "shield" + _objthis[0].id).remove();
							$("#" + "shieldtext" + _objthis[0].id).remove();
							mldwon(oneobj, twoobj, objs);
						});
					} else {
						objs.indexflg = 1;
					}
				});
				li.appendChild(xyspan);
			}
		} else {
			mlcj = mlcj + 1; // 记录层级
		}
		if (jsondata.children != undefined) {
			for (var i = 0; i < jsondata.children.length; i++) {
				addZjml(li, jsondata.children[i], li.id, mlcj, objs, _objthis);
			}
		}
		div.appendChild(li);
		appobj.appendChild(div);
	}
	$.fn.wdTree = function(objs) {
		var _this = this;
		// 默认值处理
		var defaults = {
			objid: _this[0].id,
			ctrlFlg: true,
			nameMaxLength: 40,
			nameMaxViweLength: 260,
			maxLevel: 5,
			nameUpdateFunctionName: "nameUpdate", // 修改章节目录名称方法名
			deleteFunctionName: "delete", // 删除章节目录节点
			addFunctionName: "add", // 新增章节目录节点
			upMoveFunctionName: "upMove", // 上移章节目录
			downMoveFunctionName: "downMove", // 下移章节目录
			bootFlg: false,
			indexflg: 1,
			mlcj: 1,
			token: true, // 处理重复提交
			checkflg: true, //是否加checkbox
			singleSelectionFlg: true, //是否单选,默认单选
			selecttext: "id", //选中的值,默认id
			treewidth:1000
		};
		var objs = $.extend(defaults, objs);
		_this[0].style.width = objs.treewidth + "px";
		// 一级目录
		for (var i = 0; i < objs.data.length; i++) {
			addZjml(this[0], objs.data[i], mlid + numformat("0", "00000"), mlcj, objs, _this);
		}

		// 阻止粘贴
		$(".wd_zjml_title").each(function() {
			this.onpaste = function() {
				return false;
			}
		});
	};
	// 发送请求
	function crossDomainAjax(objs, url, postData, successCallback) {
		var uuid = new Date;
		uuid = uuid.getTime();
		$.ajax({
			url: url + "?uuid=" + uuid,
			type: 'POST',
			data: eval('(' + JSON.stringify(postData) + ')'),
			async: false,
			dataType: "json",
			success: function(obj) {
				if (obj["success"]) {
					if (obj["functionName"]) {
						crossDomainAjax(objs, objs.url.replace(replaceCode, obj["functionName"]), postData, successCallback);
					} else {
						successCallback(obj);
					}
				} else {
					if (obj["functionName"]) {
						// 非boot3框架使用
						if (!objs.bootFlg) {
							if (obj["flg"]) {
								error(obj["msg"]);
							} else {
								confirm(obj["msg"], '', function() {
									crossDomainAjax(objs, objs.url.replace(replaceCode, obj["functionName"]), postData, successCallback);
								})
							}
						} else {
							if (obj["flg"]) {
								$.bootboxAlert('', obj["msg"], null, "error");
							} else {
								$.bootboxConfrim(obj["msg"], function() {
									crossDomainAjax(objs, objs.url.replace(replaceCode, obj["functionName"]), postData, successCallback);
								});
							}
						}
					}

					objs.token = true;
				}
			},
			error: function(data) {
				successCallback()
			}
		});
	}
})(jQuery);
// 控件id，是否单选true单选；false多选
function getWdTreeSelectNotText(objid, singleSelectionFlg) {
	var returnstr = "{";
	$(".wdtreecheck" + objid).each(function() {
		if (this.checked) {
			if (singleSelectionFlg) {
				returnstr += this.text;
			} else {
				//returnstr += "{" + this.text + "},";
			}
		}
	});
	
	//if(!singleSelectionFlg) returnstr = returnstr.substring(0,returnstr.length-1);
	
	returnstr += "}";

	return eval("(" + returnstr + ")");
}
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};