(function($){
	var _prefix = "wdMenu_";
	var _this;
	var mlcj = 1;  // 目录层级
	$.fn.setSelMenu = function(objs) {
		if(objs != undefined){
			$(".wd-menu-item-label").parent().removeClass(objs.wd_menu_item_li_active);
		}else{
			$(".wd-menu-item-label").parent().removeClass("wd-menu-item-li-active");
		}
	};
	$.fn.wdMenu = function(objs){
		_this = this;
		var defaults = {
			menu_id: "",
			template_id: "wd_templateId",
			menu_show: true,
			menu_margin: 0,
			menu_expand: false,
			menu_overflowY: "hidden",
			menu_border: "1px solid #CCC",
			menu_level: 2,
			menu_adap: false,  // 菜单是否自适应高度
			menu_width: "200px",
			menu_minHeight: "300px",
			_menuOperaIcon:{  // 菜单下拉图标
				_hide: false,
				width: 15,
				height: 15
			},
			_menuIcon: {   // 菜单的图标
				_show: false,
				width: 15,
				height: 15
			},
			_parentNode: {  // 父级菜单样式
				border: "0",
				background: "#fff",
				color: "#333",
				fontSize: "16px",
				lineHeight: 34,
				padding: 10
			},
			_leafNode: {  // 叶子菜单样式
				background: "#fff",
				color: "#333",
				fontSize: "14px",
				lineHeight: 34,
				padding: "0",  // paddingTop, paddingBottom
				beyondBorder: true,
				margin: "",
				innerPadding: ""
			},
			_className: {
				wd_menu_item_li_active: "wd-menu-item-li-active"
			},
			_data: {
				curMenuId: ""
			},
			afterClick: function(){
				
			}
		};
		var settings = $.extend({}, defaults, objs);
		
		this._initCurMenu = function(curMenuId){
			var _this = $("#"+_prefix+curMenuId);
			if(settings._className.wd_menu_item_li_active != undefined){
				$(_this).parent().addClass(settings._className.wd_menu_item_li_active);
			}else{
				$(_this).parent().addClass(defaults._className.wd_menu_item_li_active);
			}
			if(settings.menu_expand != true && settings.menu_level != 1){
				$(_this).parents(".wd-menu-item").each(function(){
					$(this).show();
				})
			}
		};
		
		// 初始化菜单
		initMenu(_this, settings, defaults, mlcj);
		this._initCurMenu(settings._data.curMenuId);
		
		return this;
	};
	
	// 顶层样式
	function createTopUl(obj, settings, defaults){
		var _offsetTop = $(obj).position().top;
		var ul = document.createElement("ul");
		if(settings.menu_id != undefined)
			ul.id = _prefix + settings.menu_id;
		if(settings.template_id != undefined){
			ul.setAttribute("wd_templateId", settings.template_id);
		}else{
			ul.setAttribute("wd_templateId", defaults.template_id);
		}
		if(settings.menu_border != undefined){
			ul.style.border = settings.menu_border;
		}else{
			ul.style.border = defaults.menu_border;
		}
		
		if(settings.menu_adap || defaults.menu_adap){
			ul.className = "wd-menu-auto";
			if(settings.menu_width != undefined){
				$(obj)[0].style.width = settings.menu_width;
			}else{
				$(obj)[0].style.width = defaults.menu_width;
			}
			if(settings.menum_minHeight != undefined){
				$(obj)[0].style.minHeight = settings.menu_minHeight;
			}else{
				$(obj)[0].style.minHeight = defaults.menu_minHeight;
			}
		}else{
			ul.className = "wd-menu-list";
			
			ul.style.top = _offsetTop + "px";
			if(settings.menu_margin != undefined){
				ul.style.margin = settings.menu_margin;
			}else{
				ul.style.margin = defaults.menu_margin;
			}
			if(settings.menu_overflowY != undefined){
				ul.style.overflowY = settings.menu_overflowY;
			}else{
				ul.style.overflowY = defaults.menu_overflowY;
			}
			if(settings.menu_margin != undefined && settings.menu_margin != 0){
				ul.style.width = $(_this).width() - 2* parseInt(settings.menu_margin) + "px";
			}else{
				if(settings._leafNode.beyondBorder == false){
					ul.style.width = $(_this).width() + "px";
				}else{
					ul.style.width = $(_this).width() + parseInt(1) + "px";
				}
			}
		}
		
		if(settings.menu_show == false){
			ul.style.display = "none";
		}else{
			ul.style.display = "block";
		}
		return ul;
	}
	
	function createParentUl(settings, defaults){
		var sub_ul = document.createElement("ul");
		var sub_li;
		var sub_span;
		sub_ul.className = "wd-menu-item";
		if(settings.menu_expand != true && settings.menu_level != 1){
			sub_ul.style.display = "none";
		}
		if(settings._leafNode.background != undefined){
			sub_ul.style.background = settings._leafNode.background;
		}else{
			sub_ul.style.background = defaults._leafNode.background;
		}
		if(settings._leafNode.padding != undefined){
			sub_ul.style.paddingTop = settings._leafNode.padding;
			sub_ul.style.paddingBottom = settings._leafNode.padding;
		}
		return sub_ul;
	}
	
//	function createParentLi(settings, defaults, data, mlcj){
//		var li = document.createElement("li");
//		li.className = "wd-menu-list-li";
//		if(settings._parentNode.lineHeight != undefined){
//			li.style.lineHeight = settings._parentNode.lineHeight + "px";
//		}else{
//			li.style.lineHeight = defaults._parentNode.lineHeight + "px";
//		}
//		if(settings._parentNode.background != undefined){
//			li.style.background = settings._parentNode.background;
//		}else{
//			li.style.background = defaults._parentNode.background;
//		}
//		if(settings._parentNode.fontSize != undefined){
//			li.style.fontSize = settings._parentNode.fontSize;
//		}else{
//			li.style.fontSize = defaults._parentNode.fontSize;
//		}
//		var div = document.createElement("div");
//		div.id = _prefix + data.id;
//		div.className = "wd-menu-list-handle";
//		if(settings._parentNode.border != undefined){
//			div.style.borderTop = settings._parentNode.border;
//			div.style.borderBottom = settings._parentNode.border;
//		}else{
//			div.style.borderTop = defaults._parentNode.border;
//			div.style.borderBottom = defaults._parentNode.border;
//		}
//		var _lwidth = 0;
//		if(settings._parentNode.padding != undefined){
//			_lwidth = parseInt(settings._parentNode.padding)*2 + parseInt((mlcj - 1) * 10);
//			div.style.paddingLeft = settings._parentNode.padding + parseInt((mlcj - 1) * 10) + "px";
//			div.style.paddingRight = settings._parentNode.padding + "px";
//		}else{
//			_lwidth = parseInt(defaults._parentNode.padding)*2 + parseInt((mlcj - 1) * 10);
//			div.style.paddingLeft = defaults._parentNode.padding + parseInt((mlcj - 1) * 10) + "px";
//			div.style.paddingRight = defaults._parentNode.padding + "px";
//		}
//		$(div).bind("click", function(){
//			bindSubMenu(this, settings, defaults);
//		});
//				
//		// 菜单图标
//		if(settings.menu_expand != true){
//			li.style.cursor = "pointer";
//			if(settings._menuOperaIcon._hide != true){
//				var icon = document.createElement("a");
//				icon.className = "wd_menu-opera-icon wd_menu-icon-down";
//				if(settings._menuOperaIcon.width != undefined){
//					_lwidth = parseInt(_lwidth) + parseInt(settings._menuOperaIcon.width);
//					icon.style.width = settings._menuOperaIcon.width + "px";
//				}else{
//					_lwidth = parseInt(_lwidth) + parseInt(defaults._menuOperaIcon.width);
//					icon.style.width = defaults._menuOperaIcon.width + "px";
//				}
//				var _iconHeight;
//				if(settings._menuOperaIcon.height != undefined){
//					icon.style.height = settings._menuOperaIcon.height + "px";
//					_iconHeight = settings._menuOperaIcon.height;
//				}else{
//					icon.style.height = defaults._menuOperaIcon.height + "px";
//					_iconHeight = defaults._menuOperaIcon.height;
//				}
//				if(settings._parentNode.lineHeight != undefined){
//					icon.style.marginTop = parseInt(parseInt(settings._parentNode.lineHeight) - parseInt(_iconHeight))/2 + "px";
//				}else{
//					icon.style.marginTop = parseInt(parseInt(defaults._parentNode.lineHeight) - parseInt(_iconHeight))/2 + "px";
//				}
//				var name = "";
//				if(data.iconDown){
//					name = data.iconDown;
//					icon.style.background = "url(" + data.iconDown + ") no-repeat center center";
//				}
//				if(data.iconUp){
//					name += ":" + data.iconUp;
//				}
//				icon.name = name;
//				div.appendChild(icon);
//			}
//		}
//		
//		
//		// 自定义图标
//		if (settings._menuIcon._show && data.iconSkin) {
//			var iconSkin = document.createElement("a");
//			iconSkin.className = "wd-menu-list-icon";
//			if(settings._menuIcon.width != undefined){
//				_lwidth = parseInt(_lwidth) + parseInt(settings._menuIcon.width);
//				iconSkin.style.width = settings._menuIcon.width + "px";
//			}else{
//				_lwidth = parseInt(_lwidth) + parseInt(defaults._menuIcon.width);
//				iconSkin.style.width = defaults._menuIcon.width + "px";
//			}
//			var _icoSkinHeight;
//			if(settings._menuIcon.height != undefined){
//				iconSkin.style.height = settings._menuIcon.height + "px";
//				_icoSkinHeight = settings._menuIcon.height + "px";
//			}else{
//				iconSkin.style.height = defaults._menuIcon.height + "px";
//				_icoSkinHeight = defaults._menuIcon.height + "px";
//			}
//			if(settings._parentNode.lineHeight != undefined){
//				iconSkin.style.marginTop = parseInt(parseInt(settings._parentNode.lineHeight) - parseInt(_icoSkinHeight))/2 + "px";
//			}else{
//				iconSkin.style.marginTop = parseInt(parseInt(defaults._parentNode.lineHeight) - parseInt(_icoSkinHeight))/2 + "px";
//			}
//			iconSkin.style.background = "url(" + data.iconSkin + ") no-repeat center center";
//			div.appendChild(iconSkin);
//		}
//				
//		// 菜单描述
//		var span = document.createElement("span");
//		span.className = "wd-menu-list-lable";
//		span.style.width = parseInt($(_this).width()) - parseInt(_lwidth) - parseInt(20) + "px";
//		span.innerHTML = data.name;
//		span.title = data.name;
//		div.appendChild(span);
//		li.appendChild(div);
//		return li;
//	}
		
	// settings:菜单属性
	function initMenu(obj, settings, defaults, mlcj){
		var _this = obj;
		
		// 添加菜单
		if(settings.menu_level == 1){
			var ul = createTopUl(_this, settings, defaults);
			for(var i = 0; i < settings.data.length; i++){
				var mlcj = 1;
				var li = addSubMenu(settings.data[i], settings, defaults, mlcj);
				ul.appendChild(li);
			}
			$(_this).append(ul);
		}else{
			var ul = createTopUl(_this, settings, defaults);
			for(var i = 0; i < settings.data.length; i++){
				var mlcj = 1;
				var sub_li = addSubMenu(settings.data[i], settings, defaults, mlcj, settings.data);
				ul.appendChild(sub_li);
//				var li = createParentLi(settings, defaults, settings.data[i], mlcj);
//				
//				// 二级菜单
//				var children = settings.data[i].children;
//				if(children != undefined && children.length > 0){
//					var sub_ul = createParentUl(settings, defaults);
//					if(settings.menu_level != 1){
//						sub_ul.style.display = "none";
//					}
//					mlcj = mlcj + 1;
//					for(var j = 0; j< children.length; j++){
//						var sub_li = addSubMenu(children[j], settings, defaults, mlcj, settings.data[i]);
//						sub_ul.appendChild(sub_li);
//					}
//					li.appendChild(sub_ul);
//				}
//				ul.appendChild(li);
			}
			$(_this).append(ul);
		}
	}
	
	// 加载子菜单
	function addSubMenu(children, settings, defaults, mlcj, data){
		var sub_li = document.createElement("li");
		if(mlcj == 1 && settings.menu_level != 1){
			sub_li.className = "wd-menu-list-li";
			if(settings._parentNode.background != undefined){
				sub_li.style.background = settings._parentNode.background;
			}else{
				sub_li.style.background = defaults._parentNode.background;
			}
			if(settings._parentNode.color != undefined){
				sub_li.style.color = settings._parentNode.color;
			}else{
				sub_li.style.color = defaults._parentNode.color;
			}
			if(settings._parentNode.fontSize != undefined){
				sub_li.style.fontSize = settings._parentNode.fontSize;
			}else{
				sub_li.style.fontSize = defaults._parentNode.fontSize;
			}
			if(settings._parentNode.lineHeight != undefined){
				sub_li.style.lineHeight = settings._parentNode.lineHeight + "px";
			}else{
				sub_li.style.lineHeight = defaults._parentNode.lineHeight + "px";
			}
		}else{
			sub_li.className = "wd-menu-item-li";
			if(settings._leafNode.background != undefined){
				sub_li.style.background = settings._leafNode.background;
			}else{
				sub_li.style.background = defaults._leafNode.background;
			}
			if(settings._leafNode.color != undefined){
				sub_li.style.color = settings._leafNode.color;
			}else{
				sub_li.style.color = defaults._leafNode.color;
			}
			if(settings._leafNode.fontSize != undefined){
				sub_li.style.fontSize = settings._leafNode.fontSize;
			}else{
				sub_li.style.fontSize = defaults._leafNode.fontSize;
			}
			if(settings._leafNode.lineHeight != undefined){
				sub_li.style.lineHeight = settings._leafNode.lineHeight + "px";
			}else{
				sub_li.style.lineHeight = defaults._leafNode.lineHeight + "px";
			}
		}
		
		if (children.children != undefined && children.children.length > 0) {
			var _div = document.createElement("div");
			_div.id = _prefix + children.id;
			_div.className = "wd-menu-list-handle";
			if(settings._parentNode.border != undefined){
				_div.style.borderTop = settings._parentNode.border;
				_div.style.borderBottom = settings._parentNode.border;
			}else{
				_div.style.borderTop = defaults._parentNode.border;
				_div.style.borderBottom = defaults._parentNode.border;
			}
			var _lwidth = 0;
			if(settings._parentNode.padding != undefined){
				_lwidth = parseInt(settings._parentNode.padding)*2 + parseInt((mlcj - 1) * 10);
				_div.style.paddingLeft = parseInt(settings._parentNode.padding) + parseInt((mlcj - 1) * 10) + "px";
				_div.style.paddingRight = settings._parentNode.padding + "px";
			}else{
				_lwidth = parseInt(defaults._parentNode.padding)*2 + parseInt((mlcj - 1) * 10);
				_div.style.paddingLeft = parseInt(defaults._parentNode.padding) + parseInt((mlcj - 1) * 10) + "px";
				_div.style.paddingRight = defaults._parentNode.padding + "px";
			}
			$(_div).bind("click", function() {
				bindSubMenu(this, settings, defaults);
			});
		
			// 菜单操作图标
			if(settings.menu_expand != true){
				sub_li.style.cursor = "pointer";
				if(settings._menuOperaIcon._hide != true){
					var icon = document.createElement("a");
					icon.className = "wd_menu-opera-icon wd_menu-icon-down";
					if(settings._menuOperaIcon.width != undefined){
						_lwidth = parseInt(_lwidth) + parseInt(settings._menuOperaIcon.width);
						icon.style.width = settings._menuOperaIcon.width + "px";
					}else{
						_lwidth = parseInt(_lwidth) + parseInt(defaults._menuOperaIcon.width);
						icon.style.width = defaults._menuOperaIcon.width + "px";
					}
					var _iconHeight;
					if(settings._menuOperaIcon.height != undefined){
						icon.style.height = settings._menuOperaIcon.height + "px";
						_iconHeight = settings._menuOperaIcon.height;
					}else{
						icon.style.height = defaults._menuOperaIcon.height + "px";
						_iconHeight = defaults._menuOperaIcon.height;
					}
					if(mlcj == 1){
						if(settings._parentNode.lineHeight != undefined){
							icon.style.marginTop = parseInt(parseInt(settings._parentNode.lineHeight) - parseInt(_iconHeight))/2 + "px";
						}else{
							icon.style.marginTop = parseInt(parseInt(defaults._parentNode.lineHeight) - parseInt(_iconHeight))/2 + "px";
						}
					}else{
						if(settings._leafNode.lineHeight != undefined){
							icon.style.marginTop = parseInt(parseInt(settings._leafNode.lineHeight) - parseInt(_iconHeight))/2 + "px";
						}else{
							icon.style.marginTop = parseInt(parseInt(defaults._leafNode.lineHeight) - parseInt(_iconHeight))/2 + "px";
						}
					}
					var name = "";
					if(children.iconDown){
						name = children.iconDown;
						icon.style.background = "url(" + children.iconDown + ") no-repeat center center";
					}
					if(children.iconUp){
						name += ":" + children.iconUp;
					}
					icon.name = name;
					_div.appendChild(icon);
				}
			}
			// 自定义图标
			if (settings._menuIcon._show && children.iconSkin) {
				var iconSkin = document.createElement("a");
				iconSkin.className = "wd-menu-list-icon";
				if(settings._menuIcon.width != undefined){
					_lwidth = parseInt(_lwidth) + parseInt(settings._menuIcon.width);
					iconSkin.style.width = settings._menuIcon.width + "px";
				}else{
					_lwidth = parseInt(_lwidth) + parseInt(defaults._menuIcon.width);
					iconSkin.style.width = defaults._menuIcon.width + "px";
				}
				var _icoSkinHeight;
				if(settings._menuIcon.height != undefined){
					iconSkin.style.height = settings._menuIcon.height + "px";
					_icoSkinHeight = settings._menuIcon.height;
				}else{
					iconSkin.style.height = defaults._menuIcon.height + "px";
					_icoSkinHeight = defaults._menuIcon.height;
				}
				if(mlcj == 1){
					if(settings._parentNode.lineHeight != undefined){
						iconSkin.style.marginTop = parseInt(parseInt(settings._parentNode.lineHeight) - parseInt(_icoSkinHeight))/2 + "px";
					}else{
						iconSkin.style.marginTop = parseInt(parseInt(defaults._parentNode.lineHeight) - parseInt(_icoSkinHeight))/2 + "px";
					}
				}else{
					if(settings._leafNode.lineHeight != undefined){
						iconSkin.style.marginTop = parseInt(parseInt(settings._leafNode.lineHeight) - parseInt(_icoSkinHeight))/2 + "px";
					}else{
						iconSkin.style.marginTop = parseInt(parseInt(defaults._leafNode.lineHeight) - parseInt(_icoSkinHeight))/2 + "px";
					}
				}
				iconSkin.style.background = "url(" + children.iconSkin + ") no-repeat center center";
				_div.appendChild(iconSkin);
			}
		
			// 菜单描述
			var _span = document.createElement("span");
			_span.className = "wd-menu-list-lable";
			_span.innerHTML = children.name;
			_span.title = children.name;
			_span.style.maxWidth = parseInt($(_this).width()) - parseInt(_lwidth) - parseInt(20) + "px";
			_div.appendChild(_span);
			sub_li.appendChild(_div);
		
			mlcj = mlcj + 1;
			// 子菜单
			var _children = children.children;
			if(_children != undefined && _children.length > 0){
				var sub_ul = createParentUl(settings, defaults);
				if(settings.menu_expand != true && settings.menu_level != 1){
					sub_ul.style.display = "none";
				}
				for(var m = 0; m< _children.length; m++){
					var li = addSubMenu(_children[m], settings, defaults, mlcj);
					sub_ul.appendChild(li);
				}
				sub_li.appendChild(sub_ul);
			}
		} else {
			sub_li.style.cursor = "pointer";  // 可操作,添加手样式
			
			var sub_div = document.createElement("div");
			sub_div.className = "wd-menu-item-handle";
			if(settings._leafNode.margin != undefined){
				sub_div.style.margin = settings._leafNode.margin;
			}
			if(settings.menu_level == 1 || (mlcj != 1 && settings._leafNode.innerPadding != undefined)){
				sub_div.style.padding = settings._leafNode.innerPadding;
			}
			var _ppad = 0;
			var _lwidth = 0;
			if(settings._parentNode.padding != undefined){
				_ppad = settings._parentNode.padding;
			}else{
				_ppad = defaults._parentNode.padding;
			}
			if(settings._leafNode.innerPadding == undefined || (mlcj == 1 && settings._leafNode.innerPadding != undefined)){
				_lwidth = parseInt(_ppad) + parseInt((mlcj - 1) * 10);
				sub_div.style.paddingLeft = parseInt(_ppad) + parseInt((mlcj - 1) * 10) + "px";
			}
			
			// 自定义图标
			if (settings._menuIcon._show && children.iconSkin) {
				var iconSkin = document.createElement("a");
				iconSkin.className = "wd-menu-list-icon";
				if(settings._menuIcon.width != undefined){
					_lwidth = parseInt(_lwidth) + parseInt(settings._menuIcon.width);
					iconSkin.style.width = settings._menuIcon.width + "px";
				}else{
					_lwidth = parseInt(_lwidth) + parseInt(defaults._menuIcon.width);
					iconSkin.style.width = defaults._menuIcon.width + "px";
				}
				var _icoSkinHeight;
				if(settings._menuIcon.height != undefined){
					iconSkin.style.height = settings._menuIcon.height + "px";
					_icoSkinHeight = settings._menuIcon.height;
				}else{
					iconSkin.style.height = defaults._menuIcon.height + "px";
					_icoSkinHeight = defaults._menuIcon.height;
				}
				if(mlcj == 1){
					if(settings._parentNode.lineHeight != undefined){
						iconSkin.style.marginTop = parseInt(parseInt(settings._parentNode.lineHeight) - parseInt(_icoSkinHeight))/2 + "px";
					}else{
						iconSkin.style.marginTop = parseInt(parseInt(defaults._parentNode.lineHeight) - parseInt(_icoSkinHeight))/2 + "px";
					}
				}else{
					if(settings._leafNode.lineHeight != undefined){
						iconSkin.style.marginTop = parseInt(parseInt(settings._leafNode.lineHeight) - parseInt(_icoSkinHeight))/2 + "px";
					}else{
						iconSkin.style.marginTop = parseInt(parseInt(defaults._leafNode.lineHeight) - parseInt(_icoSkinHeight))/2 + "px";
					}
				}
				iconSkin.style.background = "url(" + children.iconSkin + ") no-repeat center center";
				sub_div.appendChild(iconSkin);
			}
			
			var sub_span = document.createElement("span");
			sub_span.id = _prefix + children.id;
			sub_span.title = children.name;
			sub_span.setAttribute("url", children.url);
			sub_span.setAttribute("wd_reqType", children.reqType);
			sub_span.setAttribute("wd_headJson_url", children.headJson_url);
			sub_span.className = "wd-menu-item-label";
			
			sub_span.style.maxWidth = parseInt($(_this).width()) - parseInt(_lwidth) - parseInt(20) + "px";
			sub_span.innerHTML = children.name;
			sub_span.title = children.name;
			$(sub_span).bind("click", function() {
				menuSkip(this, settings);
			});
			sub_div.appendChild(sub_span);
			sub_li.appendChild(sub_div);
		}
		return sub_li;
	}
	
	// 父级菜单点击事件
	function bindSubMenu(obj, settings, defaults){
		if(settings.menu_expand != true){
			var _tar = obj;
			if($(_tar).parent().parent().find(".wd-menu-item").length > 0){
				$(_tar).parent().parent().find(".wd-menu-list-handle").each(function() {
					if ($(this).attr("id") != $(_tar).attr("id")) {
						$(this).next(".wd-menu-item").slideUp("100");
						if(settings._menuOperaIcon._hide != true){
							$($(this).children(".wd_menu-opera-icon")[0]).removeClass("wd_menu-icon-up");
							$($(this).children(".wd_menu-opera-icon")[0]).addClass("wd_menu-icon-down");
							
							var name = $(this).children(".wd_menu-opera-icon")[0].name;
							var arr = name.split(':');
							if(arr[0]){
								$(this).children(".wd_menu-opera-icon")[0].style.background = "url(" + arr[0] + ") no-repeat center center";
							}
						}
					}
				})
			} else {
				$(".wd-menu-list-handle").each(function() {
					if ($(this).attr("id") != $(_tar).attr("id")) {
						$(this).next(".wd-menu-item").slideUp("100");
						
						if(settings._menuOperaIcon._hide != true){
							$($(this).children(".wd_menu-opera-icon")[0]).removeClass("wd_menu-icon-up");
							$($(this).children(".wd_menu-opera-icon")[0]).addClass("wd_menu-icon-down");
							
							var name = $(this).children(".wd_menu-opera-icon")[0].name;
							var arr = name.split(':');
							if(arr[0]){
								$(this).children(".wd_menu-opera-icon")[0].style.background = "url(" + arr[0] + ") no-repeat center center";
							}
						}
					}
				})
			}
			
			$(_tar).next(".wd-menu-item").slideToggle("100");
			if(settings._menuOperaIcon._hide != true){
				var name = $(_tar).children(".wd_menu-opera-icon")[0].name;
				var arr = name.split(':');
				if($($(_tar).children(".wd_menu-opera-icon")[0]).hasClass("wd_menu-icon-down")){
					$($(_tar).children(".wd_menu-opera-icon")[0]).removeClass("wd_menu-icon-down");
					$($(_tar).children(".wd_menu-opera-icon")[0]).addClass("wd_menu-icon-up");
					
					if(arr[0]){
						$(_tar).children(".wd_menu-opera-icon")[0].style.background = "";
					}
					if(arr[1]){
						$(_tar).children(".wd_menu-opera-icon")[0].style.background = "url(" + arr[1] + ") no-repeat center center";
					}
				}else{
					$($(_tar).children(".wd_menu-opera-icon")[0]).removeClass("wd_menu-icon-up");
					$($(_tar).children(".wd_menu-opera-icon")[0]).addClass("wd_menu-icon-down");
					
					if(arr[1]){
						$(_tar).children(".wd_menu-opera-icon")[0].style.background = "";
					}
					if(arr[0]){
						$(_tar).children(".wd_menu-opera-icon")[0].style.background = "url(" + arr[0] + ") no-repeat center center";
					}
				}
			}
		}
	}
	
	// 菜单点击事件
	function menuSkip(obj, settings){
		var _tar = obj;
		$(".wd-menu-item-label").parent().removeClass(settings._className.wd_menu_item_li_active);
		$(_tar).parent().addClass(settings._className.wd_menu_item_li_active);
		
		var opts = {
			"menuId": $(_tar).attr("id"),
			"url": $(_tar).attr("url"),
			"reqType": $(_tar).attr("wd_reqType"),
			"headJsonUrl": $(_tar).attr("wd_headJson_url"),
			"templateId": $("#"+_prefix + settings.menu_id).attr("wd_templateId")
		};
		settings.afterClick.call("", opts);
	}
})(jQuery);

