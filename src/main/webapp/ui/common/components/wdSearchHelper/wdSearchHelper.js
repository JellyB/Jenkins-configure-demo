/*multiselect 1.0*/
(function($) {
	$.fn.searchHelper = function(objs) {
		var defaults = {
			multiSelect:false

		};
		var objs = $.extend(defaults, objs);
		this.each(function() {
			//alert()
			var $self = $(this);
			var results = document.createElement("div");
			var $results = $(results);
			$results.addClass("search-helper");
			$self.after($results);
			var active = -1;
			var timeout = null;
			var prev = "";
			var lastKeyPressCode = null;

			$results.css({
				"width":$self.outerWidth() + "px"
			});

			$self.bind("keydown", function(event) {
					var keyCode = event.keyCode ? event.keyCode : event.which;
					lastKeyPressCode = event.keyCode;
					switch (keyCode) {
						case 38:
							moveSelect(-1);
							e.preventDefault();
							break;
						case 40:
							moveSelect(1);
							e.preventDefault();
							break;
					}
				}).focus(function() {
					//$self.show();
				})
				.blur(function() {
					if(!objs.multiSelect){
						hideResults();
					}
					
				}).bind("input", function() {
					onChange();
				}).click(function(){
					if(objs.multiSelect){
						requestData();
					}
			});
			function onChange() {
				if (lastKeyPressCode == 46 || (lastKeyPressCode > 8 && lastKeyPressCode < 32)) return $results.hide();
				var v = $self.html();
				if (v == prev) return;
				prev = v;
				if (v.length >= 1) {
					requestData(v);
				} else {
					//$results.hide();
				}
			}
			function requestData(q) {
				if ((typeof objs.url == "string") && (objs.url.length > 0)) {
					var url = objs.url + "?q=" + encodeURI(q);
					$.get(url, function(data) {
						dataToDom(data);
						$results.show();
					});
				} else {
				}
			}
			function hideResults() {
				if (timeout) clearTimeout(timeout);
				timeout = setTimeout(hideResultsNow, 200);
			}
			function hideResultsNow() {
				if (timeout) clearTimeout(timeout);
				if ($results.is(":visible")) {
					$results.hide();
				}

				var v = $self.html();
				if (v != $self.lastSelected) {
					selectItem(null);
				}
			}
			function dataToDom(data) {
				$results.html("");
				var ul = document.createElement("ul");
				var num = data.data.length;
				for (var i = 0; i < num; i++) {
					var row = data.data[i];
					if (!row) continue;
					var li = document.createElement("li");
					if (objs.formatItem) {
						li.innerHTML = objs.formatItem(row, i, num);
						li.selectValue = row.name;
					} else {
						li.innerHTML = row.name;
						li.selectValue = row.name;
					}
					var extra = null;
					if (row.length > 1) {
						extra = [];
						for (var j = 1; j < row.length; j++) {
							extra[extra.length] = row[j];
						}
					}
					li.extra = extra;
					ul.appendChild(li);
					$(li).hover(
						function() {
							$("li", ul).removeClass("selected");
							$(this).addClass("selected");
							active = $("li", ul).indexOf($(this).get(0));
							$self.focus();

						},
						function() {
							$(this).removeClass("selected");
						}
					).click(function(e) {
						e.preventDefault();
						e.stopPropagation();
						selectItem(this)
					});
				}
				var close = document.createElement("div");
				close.innerHTML = "<span >关闭</span>";
				$(close).addClass("search-close");
				$("span", close).click(function() {
					$results.hide();
				});
				ul.appendChild(close);
				$results.append(ul);
			}

			function selectItem(li) {
				if (!li) {
					li = document.createElement("li");
					li.extra = [];
					li.selectValue = "";
				}
				var v = $.trim(li.selectValue ? li.selectValue : li.innerHTML);
				prev = v;
				$self.lastSelected = v;
				$self.focus();
				if(!objs.multiSelect){
					$self.html(v);

				}else{
					var $div = document.createElement("div");
					$($div).addClass("wd-search-box");
					$($div).css("max-width",$self.width()+"px");
					var $span = document.createElement("span");
					$span.innerHTML=v;
					$div.appendChild($span);
					$self.append($div);
					
					$results.css("top",$self.height()+4+"px")
				}


}
			function moveSelect(step) {
				var lis = $("li", $self);
				if (!lis) return;

				active += step;
				if (active < 0) {
					active = 0;
				} else if (active >= lis.size()) {
					active = lis.size() - 1;
				}
				lis.removeClass("selected");
				$(lis[active]).addClass("selected");
				var li = $("li.selected", $self)[0];
				var v = $.trim(li.selectValue ? li.selectValue : li.innerHTML);
				$self.html(v);
			}
		});
		jQuery.fn.indexOf = function(e) {
			for (var i = 0; i < this.length; i++) {
				if (this[i] == e) return i;
			}
			return -1;
		};

	};
})(jQuery);