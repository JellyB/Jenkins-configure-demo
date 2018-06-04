$(function() {
	$.get('../../template/cd-fp.html', function(template) {
		var ractive = new Ractive({
			el : '#cdfpbox',
			template : template,
			oncomplete : function() {
				// 加载国际化文件
				loadProperties(ractive, "ptxtgl-cd",
						getLanguageFromCookie("language"));
				var codeclass = new Array("YYXT");
				setCodeMap(codeclass, function() {
					selecttextInfo();
				});
				jQuery("#wd-table").wdGrid({
					url : requestAddressAction + "/module/listResource",
					dataType : "GET",
					postData : $("#searchForm").serializeArray(),
					colNames : [ ractive.get("string_zydm"), ractive.get("string_zymc"), ractive.get("string_gsxt"), ractive.get("string_cjr") ],
					colModel : [ {
						name : 'resourcecode',
						width : 80
					}, {
						name : 'resourcename',
						width : 120
					}, {
						name : 'appkey',
						width : 200,
						codeformat : 'YYXT'
					}, {
						name : 'creatercode',
						width : 100
					} ],
					checkflg : true,
					numberflg : true,
					pager : "#wd-pager",
					checktext : [ 'id' ],
					rowList : [ 10, 20, 30 ],
					pagesize : 10
				});
				$("#zysearch").bind("click", function() {
					$.ajax({
						type : "POST",
						url : requestAddressAction + "/resource/list",
						dataType : "json",
						data : $("#searchForm").serialize(),
						success : function(data) {
							$("#wd-table").setGridParam({
								url : requestAddressAction + "/resource/list",
								postData : $("#searchForm").serializeArray()
							});
						},
						error : function() {
						}
					});
				});
			}
		});
	});

});
function getzyid() {
	var id = $("#wd-table").getAllCheckedId();
	return id;
}
