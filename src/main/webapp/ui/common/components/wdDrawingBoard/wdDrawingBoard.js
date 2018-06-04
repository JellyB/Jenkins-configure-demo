var wdDrawingBoardworkFlg = false;
var wdDrawingBoardworkDefColor = "blue";

document.onmousedown = function() {
	if ($("#wdDrawingBoardmap").length == 0) return;
	document.oncontextmenu = new Function("event.returnValue=false;");
	document.onselectstart = new Function("event.returnValue=false;");
	wdDrawingBoardworkFlg = true;
	document.body.style.cursor = "pointer";
	document.onmousemove = mouseMove;
	document.onmouseup = function() {
		wdDrawingBoardworkFlg = false;
		document.body.style.cursor = "default"
	}
};

function mouseMove(ev) {
	ev = ev || window.event;
	var mousePos = mousePosition(ev);
//	document.getElementById("x").innerHTML = mousePos.x;
//	document.getElementById("y").innerHTML = mousePos.y;
	if (wdDrawingBoardworkFlg) {
		//document.body.scrollTop // 网页被卷去的高(谷歌)
		var node = document.createElement('div');
		node.className = "wdDrawingBoardwork";
		node.style.top = parseInt(mousePos.y-document.body.scrollTop) + "px";
		node.style.left = mousePos.x + "px";
		node.style.backgroundColor = wdDrawingBoardworkDefColor;
//		$(node).bind("mousemove", function() {
//			$(this).blur();
//		})
		document.body.appendChild(node);
	}
}
function mousePosition(ev) {
	return {
		x: ev.clientX + document.body.scrollLeft - document.body.clientLeft,
		y: ev.clientY + document.body.scrollTop - document.body.clientTop
	};
}
function createDrawingBoard() {
	document.body.style.overflow = "hidden";
	// 创建画画板
	var tmpdiv = document.createElement('div');
	tmpdiv.className = "wdDrawingBoardmap";
	tmpdiv.id = "wdDrawingBoardmap";
	document.body.appendChild(tmpdiv);
	// 创建关闭按钮
	var tmpinpute = document.createElement('div');
	tmpinpute.innerHTML = "";
	tmpinpute.title = "关闭";
	tmpinpute.className = "wdDrawingBoardClose";
	tmpinpute.id = "wdDrawingBoardClose";
	document.body.appendChild(tmpinpute);
	$(tmpinpute).bind("click", function() {
		document.oncontextmenu = new Function("event.returnValue=true;");
		document.onselectstart = new Function("event.returnValue=true;");
		document.body.removeChild(document.getElementById("wdDrawingBoardmap"));
		$(".wdDrawingBoardwork").each(function(){
			document.body.removeChild(this);
		});
		document.body.removeChild(document.getElementById("wdDrawingBoardColorBox"));
		document.body.removeChild(document.getElementById("wdDrawingBoardClose"));
		document.body.removeChild(document.getElementById("wdDrawingBoardClear"));
		
		document.body.style.overflow = "auto";
	});
	// 创建清空按钮
	var tmpinputeclear = document.createElement('div');
	tmpinputeclear.innerHTML = "";
	tmpinputeclear.title = "重画";
	tmpinputeclear.className = "wdDrawingBoardClear";
	tmpinputeclear.id = "wdDrawingBoardClear";
	document.body.appendChild(tmpinputeclear);
	$(tmpinputeclear).bind("click", function() {
		$(".wdDrawingBoardwork").each(function(){
			document.body.removeChild(this);
		});
	});
	// 创建样式选择区
	var colordivbox = document.createElement('div');
	colordivbox.className = "wdDrawingBoardColorBox";
	colordivbox.id = "wdDrawingBoardColorBox";
	
	var colordiv1 = document.createElement('div'); // 红色
	colordiv1.className = "wdDrawingBoardColor";
	colordiv1.style.backgroundColor = "red";
	colordiv1.style.border = "2px solid red";
	colordivbox.appendChild(colordiv1);
	$(colordiv1).bind("click",function(){
		wdDrawingBoardworkDefColor = "red";
		$(".wdDrawingBoardColor").each(function(){
			$(this).removeClass("wdDrawingBoardColorSelect");
		});
		$(this).addClass("wdDrawingBoardColorSelect");
	});
	
	var colordiv2 = document.createElement('div'); // 蓝色
	colordiv2.className = "wdDrawingBoardColor wdDrawingBoardColorSelect";
	colordiv2.style.backgroundColor = "blue";
	colordiv2.style.border = "2px solid blue";
	colordivbox.appendChild(colordiv2);
	$(colordiv2).bind("click",function(){
		wdDrawingBoardworkDefColor = "blue";
		$(".wdDrawingBoardColor").each(function(){
			$(this).removeClass("wdDrawingBoardColorSelect");
		});
		$(this).addClass("wdDrawingBoardColorSelect");
	});
	var colordiv3 = document.createElement('div'); // 黄色
	colordiv3.className = "wdDrawingBoardColor";
	colordiv3.style.backgroundColor = "yellow";
	colordiv3.style.border = "2px solid yellow";
	colordivbox.appendChild(colordiv3);
	$(colordiv3).bind("click",function(){
		wdDrawingBoardworkDefColor = "yellow";
		$(".wdDrawingBoardColor").each(function(){
			$(this).removeClass("wdDrawingBoardColorSelect");
		});
		$(this).addClass("wdDrawingBoardColorSelect");
	});
	
	var colordiv4 = document.createElement('div'); // 黄色
	colordiv4.className = "wdDrawingBoardColor";
	colordiv4.style.backgroundColor = "green";
	colordiv4.style.border = "2px solid green";
	colordivbox.appendChild(colordiv4);
	$(colordiv4).bind("click",function(){
		wdDrawingBoardworkDefColor = "green";
		$(".wdDrawingBoardColor").each(function(){
			$(this).removeClass("wdDrawingBoardColorSelect");
		});
		$(this).addClass("wdDrawingBoardColorSelect");
	});
	
	document.body.appendChild(colordivbox);
}