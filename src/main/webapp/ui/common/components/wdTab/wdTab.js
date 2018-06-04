(function($) {
	$.extend($.fn, {
		wdTab: function(options) {
			var _this = this;
			var settings = $.extend({
				tabId: "",
				type: "top",
				tabWidth: 0,
				tabHeight: 0,
				tabSpacing: 0,
				tabPadding: 0,
				fontStyle: {
					tabFont: "16px",
					tabColor: "#999999"
				},
				navStyle: {
					navBackground: '',
					showBorder: true,
					navBorder: {
						borderWidth: '1px',
						borderStyle: 'solid',
						borderColor: '#ccc',
						borderPosition: ['bottom']
					}
				},
				tabStyle: {
					tabBackground: '',
					showBorder: false,
					tabBorder: {
						borderWidth: '1px',
						borderStyle: 'solid',
						borderColor: '#ccc',
						borderPosition: ['top', 'right', 'bottom', 'left']
					}
				},
				conStyle: {
					background: '',
					minHeight: '',
					showBorder: false,
					border: {
						borderWidth: '1px',
						borderStyle: 'solid',
						borderColor: '#ccc',
						borderPosition: ['top', 'right', 'bottom', 'left']
					}
				},
				activeTabIndex: 1,
				tabActiveClass: "active",
				showShut: false,
				showIcon: false,
				iconWidth: 0,
				onShow: function() {}
			}, options);

			createMain();

			function createMain() {
				$(_this).addClass("wd-tab-box");
				var wdTabDiv = document.createElement('div');
				wdTabDiv.className = 'wd-tab-navDiv';

				wdTabDiv.id = $(_this).attr("id") + "-navBox";

				var ul = document.createElement("ul");
				ul.className = "wd-tab";
				ul.id = $(_this).attr("id") + "-nav";

				if (settings.fontStyle) {
					if (settings.fontStyle.tabColor) {
						ul.style.color = settings.fontStyle.tabColor;
					}

				}

				if (settings.navStyle) {
					if (settings.navStyle.navBackground) {
						ul.style.background = settings.navStyle.navBackground;
					}
					if (settings.navStyle.showBorder) {
						if (settings.navStyle.navBorder) {
							if (settings.navStyle.navBorder.borderWidth) {
								ul = setTabBorderStyle(ul, settings.navStyle.navBorder)
							}
						}
					}

				}

				wdTabDiv.appendChild(ul);

				var div = document.createElement("div");
				div.className = "wd-tab-con";
				div.id = $(_this).attr("id") + "-container";

				$(_this).append(wdTabDiv);
				$(_this).append(div);

				initTab();
			}
			function initTab() {
				var navDiv = document.getElementById($(_this).attr("id") + "-navBox");
				var ul = document.getElementById($(_this).attr("id") + "-nav");
				var div = document.getElementById($(_this).attr("id") + "-container");
				var activeIndex;
				if ((parseInt(settings.activeTabIndex) - 1) < 0) {
					activeIndex = 0;
				} else {
					activeIndex = parseInt(settings.activeTabIndex) - 1;
				}


				//子元素
				var textWidth, otherWidth, tabBorderWidth; // childDiv,li, 
				for (var i = 0; i < settings.data.length; i++) {
					textWidth = 0;
					otherWidth = 0;
					tabBorderWidth = 0;
					var li = document.createElement("li");
					li.id = settings.data[i].id;
					//设置内边距
					if (settings.tabPadding) {
						li.style.padding = settings.tabPadding + "px";
					} else {
						settings.tabPadding = 10;
					}

					if (settings.fontStyle) {
						if (settings.fontStyle.tabFont) {
							li.style.fontSize = settings.fontStyle.tabFont;
						}
					}
					if (settings.showIcon) {
						icon = document.createElement("span");
						icon.className = "wd-tab-pic";
						if (settings.data[i].iconUrl) {
							icon.style.background = 'url(' + settings.data[i].iconUrl + ') center center no-repeat';
						}
						if (settings.iconWidth) {
							icon.style.width = settings.iconWidth + "px";
							otherWidth += settings.iconWidth + 6;
						} else {
							otherWidth += 22;
						}
						li.appendChild(icon);
					}
					if (settings.showShut) {
						shut = document.createElement("span");
						shut.className = "wd-tab-shut";
						otherWidth += 22;
						li.appendChild(shut);
					}

					if (settings.tabStyle) {
						if (settings.tabStyle.tabBackground) {
							li.style.background = settings.tabStyle.tabBackground;
						}
						if (settings.tabStyle.showBorder) {
							if (settings.tabStyle.tabBorder) {
								if (settings.tabStyle.tabBorder.borderWidth) {
									tabBorderWidth = settings.tabStyle.tabBorder.borderWidth;
									li = setTabBorderStyle(li, settings.tabStyle.tabBorder)
								}
							}
						}
					}

					otherWidth += parseInt(tabBorderWidth.toString().replace('px', '')) * 2;

					if (settings.tabHeight) {
						li.style.height = (settings.tabHeight) + "px";
						li.style.lineHeight = (settings.tabHeight - (settings.tabPadding * 2)) + "px";
					}
					if (settings.tabWidth) {
						if ((settings.tabWidth - otherWidth) <= 30) {
							textWidth = 40;
							li.style.width = (textWidth + otherWidth + (settings.tabPadding * 2)) + "px";
						} else {
							li.style.width = (settings.tabWidth) + "px";
							textWidth = settings.tabWidth - otherWidth;
						}
					} else {
						textWidth = 100;
						li.style.width = (textWidth + otherWidth + (settings.tabPadding * 2)) + "px";
					}

					textSpan = document.createElement("span");
					textSpan.className = "wd-tab-describe";
					textSpan.style.width = textWidth + "px";
					textSpan.innerHTML = settings.data[i].name;

					if (!settings.showIcon && !settings.showShut) {
						textSpan.style.textAlign = 'center';
					}

					//设置活动的tab标签
					if (i == activeIndex) {
						if (settings.tabActiveClass) {
							li.className = settings.tabActiveClass;
						} else {
							li.className = "active";
						}
					}

					//给tab附上动态地址
					if (settings.data[i].url) {
						li.setAttribute('temurl', settings.data[i].url);
					} else {
						li.setAttribute('temurl', "");
					}
					if (settings.data[i].curBackground) {
						li.style.background = settings.data[i].curBackground;
					}

					li.appendChild(textSpan);
					li.title = settings.data[i].name;

					ul.appendChild(li);

					$(li).bind('click', function(e) {
						if (e.target.className == 'wd-tab-shut') {
							var index = $('#' + $(_this).attr("id") + ' li').index($(this));
							$(this).remove();
							var tabNum = $('#' + $(_this).attr("id") + ' li').length;
							if (tabNum == 0) {
								div.innerHTML = "";
							}
							if (activeIndex == index) {
								activeIndex = 0;
								$('#' + $(_this).attr("id") + ' li').eq(activeIndex).trigger('click');
							}
						} else {
							if (settings.tabActiveClass) {
								$('#' + $(_this).attr("id") + ' li').removeClass(settings.tabActiveClass);
								$(this).addClass(settings.tabActiveClass);
							} else {
								$('#' + $(_this).attr("id") + ' li').removeClass("active");
								$(this).addClass("active");
							}

							activeIndex = $('#' + $(_this).attr("id") + ' li').index($(this));
							wdchangeTab($(this).attr('temurl'), activeIndex);
						}
					});

					$(li).hover(
						function() {
							$(this).css('font-weight', 'bold');
						},
						function() {
							$(this).css('font-weight', 'normal');
						});
				}
				var outerWidth = $(ul).children(':first').outerWidth();
				var outerHeight = $(ul).children(':first').outerHeight();

				if (settings.type == "left") {
					navDiv.className += " wd-tab-leftNav";
					ul.className += " wd-tab-left";
					div.className += " wd-tab-leftCon";
					if (settings.tabSpacing == 0) {
						$(div).css('border-left', 'none');
					}
					div.style.marginLeft = outerWidth + settings.tabSpacing + "px";
				} else if (settings.type == "right") {
					ul.className += " wd-tab-right";
					div.className += " wd-tab-rightCon";
					if (settings.tabSpacing == 0) {
						$(div).css('border-right', 'none');
					}
					div.style.right = outerWidth + settings.tabSpacing + "px";
				} else if (settings.type == "bottom") {

				} else {
					ul.className += " wd-tab-top";
					div.className += " wd-tab-topCon";
					if (settings.tabSpacing == 0) {
						//$(ul).find('li').css('border-bottom', 'none');
						//div.css('border-top', 'none');
					}
					div.style.marginTop = settings.tabSpacing + "px";
				}

				if (settings.conStyle) {
					if (settings.conStyle.background) {
						div.style.background = settings.conStyle.background;
					}
					if (settings.conStyle.minHeight) {
						div.style.minHeight = settings.conStyle.minHeight;
					}
					if (settings.conStyle.showBorder) {
						if (settings.conStyle.border) {
							if (settings.conStyle.border.borderWidth) {
								div = setTabBorderStyle(div, settings.conStyle.border);
							}
						}
					}
				}
				wdchangeTab(settings.data[activeIndex].url, activeIndex);
			}
			if (typeof settings.onInitCompleted == 'function') {
				settings.onInitCompleted();
			}

			function wdchangeTab(changeUrl, activeIndex) {
				$.get(changeUrl, function(template) {
					var ractive = new Ractive({
						el: "#" + $(_this).attr("id") + "-container",
						template: template,
						oncomplete: function() {
							if (settings.data[activeIndex].loadfn) {
								if (settings.data[activeIndex].fnParam == undefined || settings.data[activeIndex].fnParam == null) {
									eval(settings.data[activeIndex].loadfn).apply();
								} else {
									eval(settings.data[activeIndex].loadfn).call('', settings.data[activeIndex].fnParam);
								}

							}
							if (typeof settings.onShow == "function") {
								settings.onShow();
							}
						}
					});
				});
			}

			function setTabBorderStyle(obj, borderStyle) {
				var borderPosition = borderStyle.borderPosition;
				var borderStr = borderStyle.borderWidth + " " + borderStyle.borderStyle + " " + borderStyle.borderColor;
				if (borderPosition) {
					for (var i = 0; i < borderPosition.length; i++) {
						switch (borderPosition[i]) {
							case "top":
								obj.style.borderTop = borderStr;
								break;
							case "left":
								obj.style.borderLeft = borderStr;
								break;
							case "right":
								obj.style.borderRight = borderStr;
								break;
							case "bottom":
								obj.style.borderBottom = borderStr;
								break;
						}
					}
				} else {
					obj.style.border = borderStr;
				}
				return obj;
			}
		}
	});
})(jQuery);