$(function(){
	$.get('../template/head.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-logo-box',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-index", getLanguageFromCookie("language"));
				var myDate = new Date();
				var _year = myDate.getFullYear()+ractive.get("string_year");    //获取完整的年份
				var _month = myDate.getMonth()+1+ractive.get("string_month");       //获取当前月份
				var _data = myDate.getDate()+ractive.get("string_date");        //获取当前日
				$(".hp-logo-date").html(ractive.get("string_time")+"："+_year+_month+_data);
				$(".unickName").html(getCookie("_unick"));
					$(".yhzxTc").click(function(){
						window.location.href = logoutAddress;
					})
			}
		});
	});
	$.get('../template/footer.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-footer',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-index", getLanguageFromCookie("language"));
			}
		});
	});
	$.get('../template/menu.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-menu-box',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-index", getLanguageFromCookie("language"));
				$.ajax({
					type : "POST",
					url : requestAddressAction+ "/menu/getUserMenus",
					dataType : "json",
					traditional: true,
					data : {
					},
					success: function(data){
						if(data){
							if(data.result){
								var _li = "";
								if(data.modulename!=""&&data.modulename!=null){
									if(data.auth && data.display){
										_li +='<span>'+data.modulename+'</span>';
									}
									
								}
								var _len = 0;
								if(data.child){
									_len = data.child.length;
								}
								
								if(_len>0){
									 _li += "<ul>";
									for(var i =0;i<_len;i++){
										var obj = data.child[i];
											if(obj.auth && obj.display){
												_li +='<span>'+obj.modulename+'</span>';
												var _len1=0;
												if(obj.child){
													_len1 = obj.child.length;
												}
												if(_len1>0){
													 _li += "<ul>";
													for(var j =0;j<_len1;j++){
														var obj1 = obj.child[j];
															if(obj1.auth && obj1.display){
																_li+='<li><a href="javascript:void(0);" onclick="menuxz(\''+obj1.modulecode+'\')">'+obj1.modulename+'</a></li>';
															}
														}
													_li+="</ul>";
												}
											}
										}
									_li+="</ul>";
								}
								$(".hp-menu-box ul li").html(_li);
							}else{
								alert(data.msg)
							}
							
						

						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){

					}
				});
			}
		});
	});
});

	function menuxz(type){
		$("#multivaildateTip").hide();
		switch(type){
			case "yhgl":
				/**
			    * 管理员管理
			    */
			 //  $(".hp-nav-box").html('<span>系统管理</span><span>></span><span>管理员管理</span>');
				$.get('../template/gly.html', function(template) {
					var ractive = new Ractive({
						el: '.hp-frameRight',
						template: template,
						oncomplete: function() {
							//加载国际化文件
							loadProperties(ractive, "ptxtgl-index", getLanguageFromCookie("language"));
							glyLoad()
						}
					});
				});

			break;
			case "jsgl":
				/**
			    * 角色管理
			    */
				$.get('../template/js.html', function(template) {
					var ractive = new Ractive({
						el: '.hp-frameRight',
						template: template,
						oncomplete: function() {
							//加载国际化文件
							loadProperties(ractive, "ptxtgl-index", getLanguageFromCookie("language"));
							jsLoad()
						}
					});
				});
			   
			break;
			case "qxgl":
				/**
			    * 权限管理
			    */
				$.get('../template/qx.html', function(template) {
					var ractive = new Ractive({
						el: '.hp-frameRight',
						template: template,
						oncomplete: function() {
							//加载国际化文件
							loadProperties(ractive, "ptxtgl-index", getLanguageFromCookie("language"));
							qxLoad()
						}
					});
				});
			break;
			case "cdgl":
				/**
			    * 菜单管理
			    */
				$.get('../template/cd.html', function(template) {
					var ractive = new Ractive({
						el: '.hp-frameRight',
						template: template,
						oncomplete: function() {
							//加载国际化文件
							loadProperties(ractive, "ptxtgl-index", getLanguageFromCookie("language"));
							cdLoad()
						}
					});
				});
			break;
			case "zygl":
				/**
			    * 资源管理
			    */
				$.get('../template/zy.html', function(template) {
					var ractive = new Ractive({
						el: '.hp-frameRight',
						template: template,
						oncomplete: function() {
							//加载国际化文件
							loadProperties(ractive, "ptxtgl-index", getLanguageFromCookie("language"));
							zyLoad()
						}
					});
				});
			break;
		}
	}
	function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) return decodeURIComponent(arr[2]);
    else
    return null;
} 