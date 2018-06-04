$(function(){
	loadvalidateForm(requestAddressAction + "/resource/validateRule");
	var codeclass = new Array("YYXT,RESOURCETYPE");
	setCodeMap(codeclass, function() {
		selecttextInfo();
	});
	var id = getQueryString("id");
	if(id!=null){
		$.get('../../template/zy-add.html', function(template) {
			var ractive = new Ractive({
				el: '.hp-common-box',
				template: template,
				oncomplete: function() {
					//加载国际化文件
					loadProperties(ractive, "ptxtgl-zy", getLanguageFromCookie("language"));
					$("#resourcecode").attr("disabled","disabled");
					$("#code_appkey").attr("disabled","disabled");
					$("#code_appkey").removeClass("WDInput");
					$(".hp-common-box").append("<input type='hidden' id='appkey' />");
					//数据请求
					$.ajax({
						type: "POST",
						url: requestAddressAction + "/resource/load",
						dataType: "json",
						data:{
							"id": id
						},
						success: function(data) {
							$("#resourcename").val(data.resourcename);
							$("#resourcecode").val(data.resourcecode);
							//$("#resourcetype").val(data.resourcetype);
							setWDCode("code_resourcetype", data.resourcetype);//语言
							$("#resourceurl").val(data.resourceurl);
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
		$.get('../../template/zy-add.html', function(template) {
			var ractive = new Ractive({
				el: '.hp-common-box',
				template: template,
				oncomplete: function() {
					//加载国际化文件
					loadProperties(ractive, "ptxtgl-zy", getLanguageFromCookie("language"));
					
				}
			});
		});
	}
});
function zyaddfn(){
	return validateForm();
}
