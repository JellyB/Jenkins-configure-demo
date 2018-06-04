$(function() {
	loadvalidateForm(requestAddressAction + "/module/validateRule");
	var codeclass = new Array("YYXT");
	setCodeMap(codeclass, function() {
		selecttextInfo();
	});

	var id = getQueryString("id");
	
	if (id != null) {
		$.get('../../template/cd-add.html', function(template) {
			var ractive = new Ractive({
				el: '.hp-common-box',
				template: template,
				oncomplete: function() {
					//加载国际化文件
					loadProperties(ractive, "ptxtgl-cd", getLanguageFromCookie("language"));
					$("input[name='sfCode']").click(function(){
						if($(this).val()==ractive.get("string_shi")){
							$("#code_PARENTID").attr("disabled","disabled");	
						}else{
							$("#code_PARENTID").removeAttr("disabled");
						}
					});
					$("#modulecode").attr("disabled", "disabled");
					$("#code_appkey").attr("disabled","disabled");
					$("#code_appkey").removeClass("WDInput");
					$(".hp-common-box").append("<input type='hidden' id='appkey' />");
					//数据请求
					$.ajax({
						type: "POST",
						url: requestAddressAction + "/module/load",
						dataType: "json",
						data: {
							"id": id
						},
						success: function(data) {
							$("#modulecode").val(data.modulecode);
							$("#modulename").val(data.modulename);
							$("#appkey").val(data.appkey);
							//$("#modulelevel").val(data.modulelevel);
							setWDCode("code_appkey", data.appkey); //
							if(data.parentid){

								 $("input[name='sfCode']").eq(1).attr("checked","checked");
							}else{
								 $("input[name='sfCode']").eq(0).attr("checked","checked");
							}
							setWDCode("code_PARENTID", data.parentid);
							if (data.appkey) {
								var opts = {
									requesturl: "/module/moduleList", // 
									dataobj: {
										"appkey": data.appkey
									},
									successCallback: function(data) { // 成功回调
										store.set('PARENTID', JSON.stringify(data.data));
										selecttextInfo();
										if (data.isSuccess) {


										}
									},
									errorCallback: function() { // 失败回调
										alert(ractive.get("string_qqcw"));
									}
								};
								crossDomainAjax(opts);
							}
							setWDCode("code_PARENTID", data.parentid); //
							$("#descn").val(data.descn);

						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {

						}
					});
				}
			});
		});
		
	}else{
		$.get('../../template/cd-add.html', function(template) {
			var ractive = new Ractive({
				el: '.hp-common-box',
				template: template,
				oncomplete: function() {
					//加载国际化文件
					loadProperties(ractive, "ptxtgl-cd", getLanguageFromCookie("language"));
					$("input[name='sfCode']").click(function(){
						if($(this).val()==ractive.get("string_shi")){
							$("#code_PARENTID").attr("disabled","disabled");	
						}else{
							$("#code_PARENTID").removeAttr("disabled");
						}
					})
				}
			});
		});
	}
});

function cdaddfn() {
	return validateForm();
}


function codechange() {
	var appkey = $("#appkey").val();
	var opts = {
		requesturl: "/module/moduleList", // 
		dataobj: {
			"appkey": appkey
		},
		successCallback: function(data) { // 成功回调
			store.set('PARENTID', JSON.stringify(data.data));
			selecttextInfo();
			if (data.isSuccess) {


			}
		},
		errorCallback: function() { // 失败回调
			
		}
	};
	crossDomainAjax(opts);
}