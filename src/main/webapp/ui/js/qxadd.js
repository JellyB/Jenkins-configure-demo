$(function(){
	loadvalidateForm(requestAddressAction + "/permission/validateRule");
	var codeclass = new Array("YYXT");
	setCodeMap(codeclass, function() {
		selecttextInfo();
	});
	var id = getQueryString("id");
	if(id!=null){
		$.get('../../template/qx-add.html', function(template) {
			var ractive = new Ractive({
				el: '.hp-common-box',
				template: template,
				oncomplete: function() {
					//加载国际化文件
					loadProperties(ractive, "ptxtgl-qx", getLanguageFromCookie("language"));
					$("#permissioncode").attr("disabled","disabled");
					$("#code_appkey").attr("disabled","disabled");
					$("#code_appkey").removeClass("WDInput");
					$(".hp-common-box").append("<input type='hidden' id='appkey' />");
					//数据请求
					$.ajax({
						type: "POST",
						url: requestAddressAction + "/permission/load",
						dataType: "json",
						data:{
							"id": id
						},
						success: function(data) {
							$("#permissioncode").val(data.permissioncode);
							$("#permissionname").val(data.permissionname);
							$("#appkey").val(data.appkey);
							setWDCode("code_appkey", data.appkey);//语言
							$("#descn").val(data.descn);
						
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {

						}
					});
				}
			});
		});
		
	}else{
		$.get('../../template/qx-add.html', function(template) {
			var ractive = new Ractive({
				el: '.hp-common-box',
				template: template,
				oncomplete: function() {
					//加载国际化文件
					loadProperties(ractive, "ptxtgl-qx", getLanguageFromCookie("language"));
				}
			});
		});
	}
});

function qxaddfn(){
	return validateForm();
}
