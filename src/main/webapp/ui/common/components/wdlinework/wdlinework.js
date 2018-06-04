(function($) {
	var defColor = "#0000FF"; // 默认线颜色
	$(function() {
		$(".results").each(function() {
			this.value = "";
		});
	});
	$.fn.wdlinework = function(objs) {
		// 默认值处理
		var defaults = {
			multiselect: false,
			id:this[0].id
		};
		lineWorkInf(this[0].id, objs);
	};

	function createElement(type, id, value) {
		var temp = document.createElement(type);
		temp.id = id;
		temp.innerHTML = value;
		temp.value = value;
		temp.style.display = "none";
		return temp;
	}
	function lineWorkInf(mapId, objs) {
		// 生成隐藏域
		if ($("#" + mapId + "start-x").length == 0) {
			document.getElementById(mapId).appendChild(createElement('span', mapId + "start-x", "0"))
		}
		if ($("#" + mapId + "start-y").length == 0) {
			document.getElementById(mapId).appendChild(createElement('span', mapId + "start-y", "0"))
		}
		if ($("#" + mapId + "end-x").length == 0) {
			document.getElementById(mapId).appendChild(createElement('span', mapId + "end-x", "0"))
		}
		if ($("#" + mapId + "end-y").length == 0) {
			document.getElementById(mapId).appendChild(createElement('span', mapId + "end-y", "0"))
		}
		if ($("#" + mapId + "startId").length == 0) {
			document.getElementById(mapId).appendChild(createElement('span', mapId + "startId", ""))
		}
		if ($("#" + mapId + "endId").length == 0) {
			document.getElementById(mapId).appendChild(createElement('span', mapId + "endId", ""))
		}
		// 生成问题/答案列表
		creataskanswerlist(mapId, objs);
	}
	function creataskanswerlist(mapId, objs) {
		var askLength = objs.data.ask.length;
		var tempdiv;
		var tempspan;
		var tempspan2;
		var tempinput;
		for (var i = 0; i < askLength; i++) {
			tempdiv = document.createElement('div');
			tempspan = document.createElement('span');
			tempspan.id = mapId + objs.data.ask[i].id;
			tempspan.className = "ask";
			tempspan.innerHTML = objs.data.ask[i].value;
			tempdiv.appendChild(tempspan);
			tempinput = document.createElement('input');
			tempinput.id = mapId + objs.data.ask[i].id + "-position";
			tempinput.className = "askposition";
			tempdiv.appendChild(tempinput);
			document.getElementById(objs.askBox).appendChild(tempdiv);

			$(tempspan).bind('click', function() {
				var start_x = $("#" + this.id + "-position").offset().left - $("#" + mapId)[0].offsetLeft;//$("#" + this.id + "-position")[0].offsetLeft;
				$("#" + mapId + "start-x").html(start_x);
				var start_y = $("#" + this.id + "-position")[0].offsetTop;
				var start_y_top = $("#" + mapId)[0].offsetTop;
				var start_y_temp = parseInt($("#" + this.id + "-position")[0].scrollHeight) / 2;
				$("#" + mapId + "start-y").html(parseInt(start_y) - parseInt(start_y_top) + start_y_temp);
				$("#" + mapId + "startId").html(this.id);
			})
		}

		var answerLength = objs.data.answer.length;
		for (var i = 0; i < askLength; i++) {
			tempdiv = document.createElement('div');

			tempinput = document.createElement('input');
			tempinput.id = mapId + objs.data.answer[i].id + "-position";
			tempinput.className = "answerposition";
			tempdiv.appendChild(tempinput);

			tempspan2 = document.createElement('span');
			tempspan2.id = mapId + objs.data.answer[i].id;
			tempspan2.className = "answer";
			tempspan2.innerHTML = objs.data.answer[i].value;
			tempdiv.appendChild(tempspan2);

			tempspan = document.createElement('span');
			tempspan.id = mapId + objs.data.answer[i].id + "-delete";
			tempspan.className = "delete";
			tempspan.innerHTML = "删除";
			tempdiv.appendChild(tempspan);

			tempinput = document.createElement('input');
			tempinput.id = mapId + objs.data.answer[i].id + "-results";
			tempinput.className = "results";
			tempdiv.appendChild(tempinput);
			document.getElementById(objs.answerbox).appendChild(tempdiv);

			$(tempspan2).bind('click', function() {
				// 单选处理
				if(!objs.multiselect){
					if($("#" + this.id + "-results").val() != "") return;
				}
				
				if ($("#" + mapId + "startId").html() == "") return;
				var end_x = $("#" + this.id + "-position")[0].offsetLeft - $("#" + mapId)[0].offsetLeft;
				$("#" + mapId + "end-x").html(end_x);
				var end_y = $("#" + this.id + "-position")[0].offsetTop;
				var end_y_top = $("#" + mapId)[0].offsetTop;
				var end_y_temp = parseInt($("#" + this.id + "-position")[0].scrollHeight) / 2;
				$("#" + mapId + "end-y").html(parseInt(end_y) - parseInt(end_y_top) + end_y_temp);
				var temp = $("#" + this.id + "-results").val();
				$("#" + mapId + "endId").html(this.id);
				var ok = $("#" + mapId + "startId").html() + "," + $("#" + mapId + "endId").html() + ";";
				
				if ($("#" + this.id.split('-')[0] + "-results").val().indexOf(ok) == -1) {
					$("#" + this.id + "-results").val(temp + ok);
					line(parseInt($("#" + mapId + "start-x").html()), parseInt($("#" + mapId + "start-y").html()), parseInt($("#" + mapId + "end-x").html()) - 8, parseInt($("#" + mapId + "end-y").html()), document.getElementById(mapId), $("#" + mapId + "startId").html() + "-" + $("#" + mapId + "endId").html());
					$("#" + mapId + "start-x").html("");
					$("#" + mapId + "start-y").html("");
					$("#" + mapId + "end-x").html("");
					$("#" + mapId + "end-y").html("");
					$("#" + mapId + "startId").html("");
					$("#" + mapId + "endId").html("");
				}
			});

			$(tempspan).bind('click', function() {
				var linelist = $("#" + this.id.split('-')[0] + "-results").val().split(';');
				for (var i = 0; i < linelist.length; i++) {
					deleteline(linelist[i].split(',')[0] + "-" + linelist[i].split(',')[1], document.getElementById(mapId));
				}
				$("#" + this.id.split('-')[0] + "-results").val("");
			});
		}
	}
	$.fn.getAskResults = function(objs){
		var results = "";
		$("#" + this[0].id + " .results").each(function(){
			results = results + this.value + "#";
		});
		
		return results;
	};

	function deleteline(classname, container) {
		$("." + classname).each(function() {
			container.removeChild(this);
		})
	}
	function line(startX, startY, endX, endY, container, classname, color) {
		if (startX == endX) {
			if (startY > endY) {
				var tempY = startY;
				startY = endY;
				endY = tempY;
			}
			for (var k = startY; k < endY; k++) {
				createPoint(container, startX, k, classname, color);
			}
		}
		// y = ax + b   
		var a = (startY - endY) / (startX - endX);
		var b = startY - ((startY - endY) / (startX - endX)) * startX;
		if (Math.abs(startX - endX) > Math.abs(startY - endY)) {
			if (startX > endX) {
				var tempX = endX;
				endX = startX;
				startX = tempX;
			}
			var left = container.style.left;
			var top = container.style.top;
			for (var i = startX; i <= endX; i++) {
				createPoint(container, i, a * i + b, classname, color);
			}
		} else {
			if (startY > endY) {
				var tempY = startY;
				startY = endY;
				endY = tempY;
			}
			for (var j = startY; j <= endY; j++) {
				createPoint(container, (j - b) / a, j, classname, color);
			}
		}
	}
	function createPoint(container, x, y, classname, color) {
		if (!color) color = defColor;
		var node = document.createElement('div');
		node.className = 'line ' + classname;
		node.style.marginTop = y + "px";
		node.style.marginLeft = x + "px";
		node.style.backgroundColor = color;
		container.appendChild(node);
	}
})(jQuery);