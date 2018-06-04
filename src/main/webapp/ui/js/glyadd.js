var id = getQueryString("id");

$(function(){
	loadvalidateForm(requestAddressAction + "/user/validateRule");
	if(id!=null){
		$.get('../../template/gly-edit.html', function(template) {
			var ractive = new Ractive({
				el: '.hp-common-box',
				template: template,
				oncomplete: function() {
					//加载国际化文件
					loadProperties(ractive, "ptxtgl-gly", getLanguageFromCookie("language"));
					$(".zhtd").show();
					//数据请求
					$.ajax({
						type: "POST",
						url: requestAddressAction + "/user/load",
						dataType: "json",
						data:{
							"id": id
						},
						success: function(data) {
							$("#loginid").val(data.loginid);
							$("#loginname").val(data.loginname);
							$("#nickname").val(data.nickname);
							$("#username").val(data.username);
							$("#descn").val(data.descn);
							$("#loginname").val(data.loginname);
						
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {

						}
					});
				}
			});
		});
		
	}else{
		$.get('../../template/gly-add.html', function(template) {
			var ractive = new Ractive({
				el: '.hp-common-box',
				template: template,
				oncomplete: function() {
					//加载国际化文件
					loadProperties(ractive, "ptxtgl-gly", getLanguageFromCookie("language"));
					$(".zhtd").hide();
				}
			});
		});
	}
});
function glyaddfn(){
	return validateForm();
}
