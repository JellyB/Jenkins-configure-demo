(function($) {
	$.fn.wdEdit = function(objs) {
		var _this = this;
		// 默认值处理
		var defaults = {
			id: _this[0].id,
			toolbar: ['both', 'italic', 'underline'],
			width: "100%",
			height: "300"
		};
		var objs = $.extend(defaults, objs);
		// 加载工具条
		var toolDiv = document.createElement('div');
		toolDiv.className = "wd-edittoolbox";
		for (var i = 0; i < objs.toolbar.length; i++) {
			toolDiv.appendChild(addtool(objs.toolbar[i]));
		}
		// 记录当前被选中p
		var selectpinput = document.createElement('input');
		selectpinput.id = "wd-selectinput_" + objs.id;
		toolDiv.appendChild(selectpinput);
		// 记录加载p的计数
		var recordpcount = document.createElement('input');
		recordpcount.id = "wd-recordpcount_" + objs.id;
		recordpcount.value = "0";
		toolDiv.appendChild(recordpcount);
		_this[0].appendChild(toolDiv);

		// 加载文本编辑区
		var editDiv = document.createElement('div');
		editDiv.className = "wd-editeditbox";
		editDiv.style.height = objs.height + "px";
		editDiv.appendChild(addp(objs.id));
		if (objs.width.indexOf('%') == -1) editDiv.style.width = objs.width + "px";
		else editDiv.style.width = objs.width;
		_this[0].appendChild(editDiv);

	};

	// 加载编辑区p
	function addp(parentid) {
		var editp = document.createElement('p');
		editp.className = "wd-editp";
		$(editp).attr("contenteditable", "true");
		editp.id = parentid + $("#wd-recordpcount_" + parentid).val();
		editp.innerHTML = "<br />";
		$(editp).bind('click', function() {
			$("#wd-selectinput_" + parentid).val(this.id);
		});
		$("#wd-recordpcount_" + parentid).val(parseInt($("#wd-recordpcount_" + parentid).val()) + 1); // 设置下一个p的id
		return editp;
	}
	// 加载工具条
	function addtool(toolname) {
		var obj = document.createElement('div');
		switch (toolname) {
			case "both":
				obj.className = "wd-toolbox wd-both";
				obj.title = "加粗";
				$(obj).bind("click", function() {
					//window.getSelection().getRangeAt(0).surroundContents(document.createElement("span"));
					if (window.getSelection) {
						alert(window.getSelection().toString());
					} else if (document.selection && document.selection.createRange) {
						alert(document.selection.createRange().text);
					}
				});

				break;
			case "italic":
				obj.className = "wd-toolbox wd-italic";
				obj.title = "斜体";
				$(obj).bind("click", function() {
					alert(2);
				});
				break;
			case "underline":
				obj.className = "wd-toolbox wd-underline";
				obj.title = "下划线";
				$(obj).bind("click", function() {
					alert(3);
				});
				break;
		}

		return obj;
	}
})(jQuery);