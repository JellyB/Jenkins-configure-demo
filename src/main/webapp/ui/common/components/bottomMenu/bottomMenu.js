(function($) {
	// 删除方法
	$.fn.wdBottomMenuDelete = function(objs) {
		objs.boxid = this[0].id; // 记录菜单主体id
		if (objs.menuFlg == 2) deleteSubMenu(objs); // 删除子菜单
		else deleteMainMenu(objs); // 删除主菜单
	};

	// 删除主菜单
	function deleteMainMenu(objs) {
		crossDomainAjax(objs, function() {
			var subMenuCount = $($("#" + objs.id)[0].parentNode.parentNode).find(".wd-menubox").length;
			// 当总数小于最大数时，删除当前选中的，加大增加按钮宽度
			var oldwidth = document.getElementById(objs.boxid + "_" + "bottomaddmenu").style.width.replace('px', '');
			var objwidth = $("#" + objs.id)[0].parentNode.style.width.replace('px', '');

			if (subMenuCount != objs.menuCount) {
				document.getElementById(objs.boxid + "_" + "bottomaddmenu").style.width = parseInt(parseInt(oldwidth) + parseInt(objwidth)) + "px";
				$("#" + objs.id)[0].parentNode.parentNode.removeChild($("#" + objs.id)[0].parentNode);
			} else {
				document.getElementById(objs.boxid + "_" + "bottomaddmenu").style.width = objwidth + "px";
				$("#" + objs.id)[0].parentNode.parentNode.removeChild($("#" + objs.id)[0].parentNode);
				$("#" + objs.boxid + "_bottomaddmenu").show();
			}

			if (objs.deleteCallBack) objs.deleteCallBack.call();
		});

	};

	// 删除子菜单
	function deleteSubMenu(objs) {
		crossDomainAjax(objs, function() {
			var allSubCount = $($("#" + objs.id)[0].parentNode).find(".wd-subMenuAdd").length - 1; // 获取总菜单数
			var deleteName = ""; //需要删除的对象编号
			var addButton = ""; // 添加按钮对象
			$($("#" + objs.id)[0].parentNode).find(".wd-subMenuAdd").each(function() {
				var menuName = this.name; // 获取当前编号
				if (menuName == undefined) addButton = this; // 记录添加按钮
				if (menuName != undefined) {
					if (this.id == objs.id) { // 相等的删除
						deleteName = this.name; // 记录下删除的编号
						$("#" + objs.id)[0].parentNode.removeChild($("#" + this.id)[0]);
						if (allSubCount == objs.menuCount) { // 如果菜单数量是满的，显示添加按钮、恢复添加按钮位置
							var tempaddbottom = parseInt(addButton.style.bottom.replace('px', ''));
							var tempthisaddheight = parseInt(addButton.style.height.replace('px', ''));
							addButton.style.bottom = parseInt(tempaddbottom + tempthisaddheight) + "px";
							addButton.style.display = "block";
						}
					} else { // 不相等的处理位置
						var tempbottom = parseInt(this.style.bottom.replace('px', '')); // 当前菜单巨低距离
						var tempthisheight = parseInt(this.style.height.replace('px', '')); // 当前菜单高度
						if (menuName > deleteName && allSubCount != objs.menuCount && deleteName != "") { // 处理大于删除编号的对象（之上的），并且数量不满的情况
							this.style.bottom = parseInt(tempbottom - tempthisheight) + "px"; // 减高度
						} else if (deleteName == "" && allSubCount == objs.menuCount) { // 处理小于删除编号的对象（之下的），并且数量满的情况
							this.style.bottom = parseInt(tempbottom + tempthisheight) + "px";
						}
					}
				}
			});


			if (objs.deleteCallBack) objs.deleteCallBack.call();
		});
	}
	// 菜单初始化方法
	$.fn.wdBottomMenu = function(objs) {
		var _this = this;
		// 默认值处理
		var defaults = {
			tableKey: "id", // 数据库对应主键名称
			menuName: "menuname", // 数据库菜单名称字段名称
			childMenuNote: "children", // 子菜单名称
			tableMainKey: "parentid", // 上级主键名称
			displayOrder: "displayorder", // 排序字段名称
			replaceCode: "#", // 替换方法名用
			moveSeq: "move_seq", // 移动前序号
			moveToSeq: "moveTo_seq", //移动到序号
			moveId: "moveId", // 当前移动id
			maxMenuCount: 3, // 主菜单最大数量
			childMaxMenuCount: 5, // 子菜单最大数量
			showBoxWidth: 310, // 菜单总宽度
			showMenuWidth: 90, // 主菜单宽度
			showHeight: 60, // 主菜单高度
			showSubHight: 50, // 子菜单高度
			id: this[0].id, // 对象id
			titleWidth: 0, // 菜单标题宽度
			textMaxLength: 5, // 编辑最大文本长度
			frontElement: null, // 前对象
			optButton: null, // 操作按钮对象
			tempSubMenuCount: 1, // 子菜单计数器
			tempMainMenuCount: 1, // 主菜单计数器
			moveFlg: true, // 是否可以移动
			editFlg: false // 是否可编辑
		};
		var objs = $.extend(defaults, objs);
		// 计算菜单标题宽度
		objs.titleWidth = parseInt(objs.showBoxWidth) - (parseInt(objs.showMenuWidth) * parseInt(objs.maxMenuCount));

		var optButton = document.createElement('button');
		optButton.innerHTML = "菜单排序";
		optButton.className = "wd-bottomOpt";
		optButton.id = objs.id + "_optbtn";
		optButton.name = "false";
		objs.optButton = optButton;
		$(optButton).bind('click', function() {
			// 菜单挪动
			if (this.name == "false") {
				$(".wd-subMenuAdd").hide();
				$(".wd-menuedit").hide();
				$(".wd-arrow").hide();
				this.name = "true";
				this.innerHTML = "退出排序";
				document.body.onselectstart = function() {
					return false;
				};
			} else { // 菜单编辑
				$(".wd-subMenuAdd").show();
				if (objs.editFlg) $(".wd-menuedit").show();
				$(".wd-arrow").show();
				this.name = "false";
				this.innerHTML = "菜单排序";
				document.body.onselectstart = null;
			}
		});

		addMainMenu(null, objs);

		// 生成添加按钮
		var divaddmenu = document.createElement('div');
		objs.frontElement.style.width = objs.showMenuWidth + "px";
		divaddmenu.style.width = parseInt(objs.showBoxWidth - objs.titleWidth - objs.showMenuWidth * $(".wd-menubox").length) + "px";
		divaddmenu.style.height = objs.showHeight + "px";
		divaddmenu.style.lineHeight = objs.showHeight + "px";
		divaddmenu.className = "wd-addmenu";
		divaddmenu.id = objs.id + "_" + "bottomaddmenu";
		divaddmenu.innerHTML = "+";
		divaddmenu.title = "添加菜单";
		_this[0].appendChild(divaddmenu);

		var optButtonBox = document.createElement('div');
		optButtonBox.className = "wd-bottomOptBox";

		optButtonBox.appendChild(optButton);

		_this[0].appendChild(optButtonBox);
		// 增加主菜单
		$(divaddmenu).bind('click', function() {
			addMainMenu(this, objs, true);
		});
	};

	function addMenu(obj, objs, data) {
		var divmenu = document.createElement('div');
		divmenu.style.width = objs.showMenuWidth + "px";
		divmenu.style.height = objs.showHeight + "px";
		divmenu.style.lineHeight = objs.showHeight + "px";
		var menutextspan = document.createElement('span');
		menutextspan.innerHTML = (data[objs.menuName]) ? data[objs.menuName] : "菜单名称";
		menutextspan.style.display = "block";
		menutextspan.style.width = (objs.showMenuWidth - 2) + "px";
		menutextspan.style.height = objs.showHeight + "px";
		menutextspan.id = data[objs.tableKey];
		divmenu.appendChild(menutextspan);
		divmenu.className = "wd-menubox";
		divmenu.name = objs.tempMainMenuCount;
		objs.tempMainMenuCount = objs.tempMainMenuCount + 1;
		var editinput = document.createElement('input');
		editinput.style.width = (objs.showMenuWidth - 2) + "px";
		editinput.style.height = (objs.showHeight - 2) + "px";
		editinput.style.lineHeight = (objs.showHeight - 2) + "px";
		editinput.value = (data[objs.menuName]) ? data[objs.menuName] : "菜单名称";
		editinput.maxLength = objs.textMaxLength;
		editinput.className = "wd-menuedit";
		editinput.style.display = "none";
		// 获取焦点显示文本框元素(暂时不用)
		$(menutextspan).bind('click', function() {
			if (objs.editFlg == false) {
				var backItem = {
					menuFlg: 1,
					id: this.id
				};
				if (objs.selectCallBack) objs.selectCallBack.call(null, backItem);
				return;
			}
			if (objs.optButton.name == "true") return;
			if (objs.editFlg == false) return;
			editinput.style.display = "block";
		});
		// 失去焦点后显示div信息(暂时不用)
		$(editinput).bind('blur', function() {
			this.style.display = "none";
			menutextspan.innerHTML = editinput.value;
		});
		divmenu.appendChild(editinput);
		// 绑定移动
		menuMove(divmenu, objs);
		// 确定添加位置
		if ($("#" + objs.id).find(".wd-menubox").length == 0) {
			$("#" + objs.id + "_wd-menutitle").after(divmenu);
		} else {
			var lastCount = $("#" + objs.id).find(".wd-menubox").length;
			$($("#" + objs.id).find(".wd-menubox")[lastCount - 1]).after(divmenu);
		}
		// 添加子菜单
		addSubMenu(divmenu, objs, data[objs.childMenuNote]);
		// 记录当前对象
		objs.frontElement = divmenu;
		// 处理添加按钮宽度
		// 如果当前元素存在，重新计算添加按钮的宽度
		if (obj)
			obj.style.width = parseInt(objs.showBoxWidth - objs.titleWidth - objs.showMenuWidth * $(".wd-menubox").length) + "px";
		// 如果大于指定最大数量隐藏添加按钮
		if ($(".wd-menubox").length == objs.maxMenuCount) obj.style.display = "none";
	}

	// 增加主菜单 flg 判断是否为后续添加的按钮
	function addMainMenu(obj, objs, flg) {
		// 第一次添加时，生成菜单头部分
		if ($("#" + objs.id + "_wd-menutitle").length == 0) {
			var divcode = document.createElement('div');
			divcode.style.width = objs.titleWidth + "px";
			divcode.style.height = objs.showHeight + "px";
			divcode.style.lineHeight = objs.showHeight + "px";
			divcode.className = "wd-menutitle";
			divcode.id = objs.id + "_wd-menutitle";
			document.getElementById(objs.id).appendChild(divcode);
		}
		if (objs.jsondata && !flg) {
			for (var i = 0; i < objs.jsondata.length; i++) {
				addMenu(null, objs, objs.jsondata[i]);
			}
		} else {
			// 拼装请求url
			objs.postUrl = objs.url.replace(objs.replaceCode, objs.addFunction);
			// 添加参数 排序
			var displayOrder = ($("#" + objs.id).find(".wd-menubox").length + 1);
			objs.dataobj[objs.displayOrder] = displayOrder;
			// 生成菜单
			crossDomainAjax(objs, function(data) {
				addMenu(obj, objs, data);
			});
		}
	}
	function addMenuChild(obj, objs, data, _this) {
		var subMenuCount = $(obj).find(".wd-subMenuAdd").length;
		subMenuBottom = objs.showHeight + 10 + subMenuCount * (objs.showSubHight - 1);
		var subMenuBox = document.createElement('div');
		subMenuBox.style.width = (objs.showMenuWidth - 2) + "px";
		subMenuBox.style.height = objs.showSubHight + "px";
		subMenuBox.style.lineHeight = objs.showSubHight + "px";
		subMenuBox.style.bottom = subMenuBottom + "px";
		subMenuBox.id = data[objs.tableKey];
		subMenuBox.name = parseInt(objs.tempSubMenuCount + 1);
		subMenuBox.className = "wd-subMenuAdd wd-subMenu";
		// 点击子菜单调用页面回调方法
		$(subMenuBox).bind('click', function() {
			if (objs.editFlg == false) {
				var backItem = {
					menuFlg: 2, // 2为子菜单
					id: this.id
				};
				if (objs.selectCallBack) objs.selectCallBack.call(null, backItem);

			}
		});
		// 如果需要编辑加载文本框
		if (objs.editFlg == true) {
			var subInput = document.createElement('input');
			subInput.style.width = (objs.showMenuWidth - 4) + "px";
			subInput.style.height = (objs.showSubHight - 2) + "px";
			subInput.style.lineHeight = objs.showSubHight + "px";
			subInput.value = (data[objs.menuName]) ? data[objs.menuName] : "子菜单";
			subInput.maxLength = objs.textMaxLength;
			subInput.className = "wd-subMenuInput";
			subMenuBox.appendChild(subInput);
			subInput.focus();
		} else {
			var subSpan = document.createElement('span');
			subSpan.innerHTML = (data[objs.menuName]) ? data[objs.menuName] : "子菜单";
			subMenuBox.appendChild(subSpan);
		}
		objs.tempSubMenuCount = parseInt(objs.tempSubMenuCount + 1);
		obj.appendChild(subMenuBox);
		// 子菜单大于设定值隐藏添加按钮,移动所有子菜单
		if (subMenuCount == objs.childMaxMenuCount) {
			$(_this).hide();

			$(obj).find(".wd-subMenuAdd").each(function() {
				this.style.bottom = parseInt(this.style.bottom.replace('px', '') - objs.showSubHight) + "px";
			})
		}
	}

	// 添加子菜单
	function addSubMenu(obj, objs, childMenuNote) {
		var subMenuCount = $(obj).find(".wd-subMenuAdd").length;
		var subMenuBottom = 0;
		if (subMenuCount == 0) {
			subMenuBottom = objs.showHeight + 10;
			// 箭头
			var arrow = document.createElement('div');
			arrow.style.bottom = (objs.showHeight + 5) + "px";
			arrow.style.marginLeft = parseInt((objs.showMenuWidth - 2) / 2 - 5) + "px";
			arrow.className = "wd-arrow";
			obj.appendChild(arrow);
		}
		// 添加子菜单按钮
		var subMenuAdd = document.createElement('div');
		subMenuAdd.style.width = (objs.showMenuWidth - 2) + "px";
		subMenuAdd.style.height = objs.showSubHight + "px";
		subMenuAdd.style.lineHeight = objs.showSubHight + "px";
		subMenuAdd.style.bottom = subMenuBottom + "px";
		subMenuAdd.innerHTML = "+";
		subMenuAdd.title = "添加子菜单";
		subMenuAdd.className = "wd-subMenuAdd";
		obj.appendChild(subMenuAdd);

		if (childMenuNote) {
			for (var i = 0; i < childMenuNote.length; i++) {
				addMenuChild(obj, objs, childMenuNote[i], null);
			}
		}

		$(subMenuAdd).bind('click', function() {
			var _this = this;
			// 拼装请求url
			objs.postUrl = objs.url.replace(objs.replaceCode, objs.addFunction);
			// 添加添加父节点id参数
			var parentid = $(obj.parentNode).find('span')[0].id;
			objs.dataobj[objs.tableMainKey] = parentid;
			// 生成菜单
			crossDomainAjax(objs, function(data) {
				addMenuChild(obj, objs, data, _this);
			})
		})
	}
	// 移动
	function menuMove(obj, objs) {
		$(obj).mousedown(function(e) {
			if (objs.optButton.name == "false") return;
			var tempId = $(this).find('span')[0].id;
			var o = {};
			var _this = this; // 当前被移动的
			_this.style.zIndex = 9999;
			var tempCount = $("#" + objs.id).find(".wd-menubox").index(this); // 获取点击点击主菜单的位置
			o.iTop = $(this).position().top - e.pageY;
			o.iLeft = $(this).position().left - objs.showMenuWidth / 2 - e.pageX - (tempCount * objs.showMenuWidth);
			$this = $(this);

			$(document).bind("mousemove", function(e) {
				var iLeft = e.pageX + o.iLeft;
				var iTop = e.pageY + o.iTop;

				$this.css({
					'left': iLeft + "px",
					'top': iTop + "px"
				})
			});

			$(document).bind("mouseup", function() {
				var moveSeq = tempCount;
				var moveToSeq = "";
				var moveId = tempId;
				_this.style.zIndex = 1;
				var targetX = $(_this).position().left;
				var ok = false;
				$(".wd-menubox").each(function() {
					if (this != _this) {
						if ($(this).position().left + 10 < targetX && $(this).position().left + objs.showMenuWidth - 10 > targetX) {
							ok = true;
							if (this.name > _this.name) {
								moveToSeq = $("#" + objs.id).find(".wd-menubox").index(this);
								$(_this).insertAfter($(this));
							} else {
								moveToSeq = $("#" + objs.id).find(".wd-menubox").index(this);
								$(this).insertAfter($(_this));
							}
							$(_this).css({
								'left': "0px",
								'top': "0px"
							});

							var tempname = this.name;
							this.name = _this.name;
							_this.name = tempname;
						}

					}
				});
				if (!ok) {
					$(_this).css({
						'left': "0px",
						'top': "0px"
					});
				}
				$(document).unbind("mousemove");
				$(document).unbind("mouseup");

				// 拼装请求url
				objs.postUrl = objs.url.replace(objs.replaceCode, objs.moveFunction);
				objs.dataobj[objs.moveSeq] = moveSeq;
				objs.dataobj[objs.moveToSeq] = moveToSeq;
				objs.dataobj[objs.moveId] = moveId;
				crossDomainAjax(objs, null);

			});
		});
	}

	// 发送请求
	function crossDomainAjax(objs, successCallback) {
		$.ajax({
			url: objs.postUrl + "?time=" + new Date().getTime(),
			cache: false,
			dataType: 'json',
			type: 'post',
			data: $.param(objs.dataobj),
			async: false, // must be set to false
			success: function(data, success) {
				if (successCallback) successCallback(data);
			},
			error: function() {
				var data = {
					"id": new Date().getTime()
				};
				if (successCallback) successCallback(data);
			}
		});
	}
})(jQuery);