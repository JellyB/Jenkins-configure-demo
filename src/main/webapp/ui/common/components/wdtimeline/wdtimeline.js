(function($) {
	$.fn.wdtimeline = function(objs) {
		var _this = this;
		var innerHtml = "";
		var iCount = objs.data.length;
		for (var i = 0; i < iCount; i++) {
			innerHtml += setTimeLineInnerHtml(objs.data[i], i, objs);
		}

		_this[0].innerHTML = innerHtml;
	};

	function setTimeLineInnerHtml(obj, i, objs) {
		var innerHtml = "";

		// 第一区域特殊处理
		if (i == 0) {
			innerHtml += "<div class='timelinebox-first'>";
			// 处理上线和内容
			if (obj["topline"] != undefined) {
				innerHtml += "<div class = 'topline timetimelinecolorFF8300'></div>"; // 线

				innerHtml += "<div class='topitembox'>"; // 内容
				innerHtml += "<div class='tx'></div>";
				innerHtml += "<div class='timelinearrow timelinearrowtop-left '></div>";
				innerHtml += "<div class = 'msgitem msgpositiontop timetimelinecolorFF8300'>" + obj["topline"][0]["msg"] + "</div>";
				innerHtml += "</div>";
			}

			// 处理左线和内容
			if (obj["leftline"] != undefined) {
				innerHtml += "<div class='leftline timetimelinecolor6187DC'></div>"; // 线

				innerHtml += "<div class='lefttembox'>"; // 内容
				innerHtml += "<div class='tx'></div>";
				innerHtml += "<div class='timelinearrow timelinearrowleft-right'></div>";
				innerHtml += "<div class='msgitem msgpositionleft timetimelinecolor6187DC'>" + obj["leftline"][0]["msg"] + "</div>";
				innerHtml += "</div>";
			}

			// 处理右线和内容
			if (obj["rightline"] != undefined) {
				innerHtml += "<div class='rightline timetimelinecolor03B8A7'></div>"; // 线

				innerHtml += "<div class='rightitembox'>"; // 内容
				innerHtml += "<div class='tx'></div>";
				innerHtml += "<div class='timelinearrow timelinearrowrigth-left'></div>";
				innerHtml += "<div class='msgitem msgpositionright timetimelinecolor03B8A7'>" + obj["rightline"][0]["msg"] + "</div>";
				innerHtml += "</div>";
			}

			// 处理点
			if (obj["point"] != undefined && obj["point"] == "all") {
				innerHtml += "<div class = 'wdtimepoint timetimelinecolorimgCCCCCC'></div>";
			}

			// 处理下线
			if (obj["bottomline"] != undefined && obj["bottomline"] == true) {
				innerHtml += "<div class = 'bottomline timetimelinecolorCCCCCC'></div>";
			}

			innerHtml += "</div>";
		} else if(i!=objs.data.length-1) {
			// 特殊处理
			if (obj["exception"]) {
				innerHtml += "<div class='timelinebox exceptionposition'>";
				innerHtml += "<div class='timetimelinecolorE4E4E4 exceptionboxposition'>";
				innerHtml += "<span class='exceptiontitle'>" + obj["title"] + "</span>";
				innerHtml += "<div class='exceptionitembox'>";
				innerHtml += "<div class='timelinearrow exceptionarrowposition-top'></div>";
				innerHtml += "<div class='exceptionline-top'></div>";
				innerHtml += "<div class='exceptionitem'>" + obj["item"] + "</div>";
				innerHtml += "<div class='exceptionline-bottom'></div>";
				innerHtml += "<div class='timelinearrow exceptionarrowposition-bottom'></div>";
				innerHtml += "</div>";
				innerHtml += "<div class='timelinearrow exceptionarrowposition-left'></div>";
				innerHtml += "<div class='exceptionitems'>" + obj["msg"] + "</div>";
				if (obj["bottomline"] != undefined && obj["bottomline"] == true) {
					innerHtml += "<div class='bottomline timetimelinecolorCCCCCC exceptiontimelineposition'></div>";
				}
				innerHtml += "</div>";
				innerHtml += "</div>";
			} else { // 正常处理
				innerHtml += "<div class='timelinebox'>";

				// 处理左线和内容
				if (obj["leftline"] != undefined) {
					innerHtml += "<div class='leftline timetimelinecolor6187DC'></div>"; // 线

					innerHtml += "<div class='lefttembox'>"; // 内容
					innerHtml += "<span class='lefttitle'>" + obj["leftline"][0]["title"] + "</span>";
					innerHtml += "<div class='timelinearrow timelinearrowleft-top'></div>";
					innerHtml += "<div class='msgitem timetimelinecolor6187DC msgpositionleft'>" + obj["leftline"][0]["msg"] + "</div>";
					innerHtml += "</div>";
				}

				// 处理右线和内容
				if (obj["rightline"] != undefined) {
					innerHtml += "<div class='rightline timetimelinecolorFF8300'></div>"; // 线

					innerHtml += "<div class='rightitembox'>"; // 内容
					innerHtml += "<span class='lefttitle'>" + obj["rightline"][0]["title"] + "</span>";
					innerHtml += "<div class='timelinearrow timelinearrowright-top'></div>";
					innerHtml += "<div class='msgitem msgpositionright timetimelinecolorFF8300'>" + obj["rightline"][0]["msg"] + "</div>";
					innerHtml += "</div>";
				}

				// 点
				if (obj["point"] != undefined) {
					// 左
					if (obj["point"] == "left") {
						innerHtml += "<div class='wdtimepoint timetimelinecolorimg6187DC'></div>";
					} else if (obj["point"] == "right") { // 右
						innerHtml += "<div class='wdtimepoint timetimelinecolorimgFF8300'></div>";
					}
				}

				// 下线
				if (obj["bottomline"] != undefined && obj["bottomline"] == true) {
					innerHtml += "<div class='bottomline timetimelinecolorCCCCCC'></div>";
				}

				innerHtml += "</div>";
			}
		}else{
			// 末节点处理
			innerHtml += "<div class='timelinebox'>";
			innerHtml += "<div class='wdtimepoint timetimelinecolorimg008000'></div>";
			innerHtml += "<div class='endpointtitle'>" + obj["title"] + "</div>";
			innerHtml += "<div class='endpointmsg'>" + obj["msg"] + "</div>";
			innerHtml += "</div>";
		}

		return innerHtml;
	}
})(jQuery);