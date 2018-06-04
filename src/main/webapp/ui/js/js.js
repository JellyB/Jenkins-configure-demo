var ractiveall;
function jsLoad(){
	/**
    * 角色管理
    */
//	getTemplate("app/template/js-list.html", ".hp-item-box", function() {
//		searhToggle();
//		jsMethod();
//	});
	$.get('../template/js-list.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-item-box',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-js", getLanguageFromCookie("language"));
				searhToggle();
				ractiveall = ractive;
				jsMethod();
			}
		});
	});
}
function jsMethod(){
	var ractive = ractiveall;
	var codeclass = new Array("YYXT");
			setCodeMap(codeclass, function() {
				selecttextInfo();
			});
		$(".btn-jsadd").click(function(){
				var buttons = [{
					"buttonName": ractive.get("string_tj"),
					"buttonFunction": function() {
					var rolename=this.Win.document.getElementById("rolename").value;	//登录名
					var rolecode=this.Win.document.getElementById("rolecode").value;//昵称
					var appkey=this.Win.document.getElementById("appkey").value;//姓名
					var descn=this.Win.document.getElementById("descn").value;//描述
					var flag = 	this.Win.jsaddfn();
					if(flag){
						var opts = {
							requesturl: "/role/save", // 
							dataobj:{
								"rolename":rolename,
								"rolecode":rolecode,
								"appkey":appkey,
								"descn":descn
							},
							successCallback: function(data) { // 成功回调
								if(data.isSuccess){
									success(ractive.get("string_bccg"),"",function(){
										this.Win.Closed();
										$("#wd-table").setGridParam({
											url: requestAddressAction + "/role/list",
											postData: $("#searchForm").serializeArray()
										});	
									},null,false);
								}else{
									alert(data.msg);
								}	
							},
							errorCallback: function() { // 失败回调
								alert(ractive.get("string_qccw"));
							}
						};
						crossDomainAjax(opts);
						
					}
					},
					"mainButFlg": true // 是否主按钮，非必填，默认true
				}, {
					"buttonName": ractive.get("string_qx"),
					"buttonFunction": function() {
						this.Win.Closed();
					}
				}];
				$(".btn-add").dialog({
					url: 'js/jsadd.html',
					title: ractive.get("string_cjjs"), // 非必选
					width: 520,
					height: 320,
					buttons: buttons // 自定义按钮，非必选
				});
			});
		jQuery("#wd-table").wdGrid({
			url: requestAddressAction + "/role/list",
			dataType: "GET",
			postData: $("#searchForm").serializeArray(),
			colNames: [ractive.get("string_jsdm"),ractive.get("string_jsmc"),ractive.get("string_gsxt"),ractive.get("string_cjr"),ractive.get("string_cjsj"),ractive.get("string_cz")],
			colModel: [{
				name: 'rolecode',
				width: 80
			}, {
				name: 'rolename',
				width: 60
			}, {
				name: 'appkey',
				width: 90,
				codeformat: 'YYXT'
			}, {
				name: 'creatercode',
				width: 80
			}, {
				name: 'creatertime',
				width: 90,
				dataformat: 'yyyy-MM-dd'
			}, {
				name: 'cz',
				width: 110,
				innerhtml: function(opts) {
					var str = "";
					str += "<a id='ad-xg' class='' onclick='funjsEdit(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_xg")+"</a>";
					str += "<a id='ad-sc' class='ad-ml10' onclick='funjsDel(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_sc")+"</a>";
					str += "<a id='ad-sc' class='ad-ml10' onclick='funjsFp(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_fpqx")+"</a>";
					return str;
				}
			}],
			checkflg: true,
			numberflg:true,
			pager: "#wd-pager",
			checktext: ['id'],
			rowList: [10, 20, 30],
			pagesize: 10
		});
		$("#jssearch").bind("click", function() {
			$.ajax({
				type: "POST",
				url: requestAddressAction + "/role/list",
				dataType: "json",
				data: $("#searchForm").serialize(),
				success: function(data) {
					$("#wd-table").setGridParam({
						url: requestAddressAction + "/role/list",
						postData: $("#searchForm").serializeArray()
					});
				},
				error: function() {
				}
			});				
		});
		$(".btn-jsdelete").click(function(){
			var str = "";
					str += $("#wd-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funjsDel(str);
					}				
		})
}
function funjsDel(ids) {
	var ractive = ractiveall;
	//弹出框按钮
	var buttons= [{
		"buttonId": "btn1", // 自定义按钮id
		"buttonName": ractive.get("string_qd"), // 显示文本
		"buttonFunction": function() { // 点击后出发事件
			//数据请求
			$.ajax({
				type: "POST",
				url: requestAddressAction + "/role/delete",
				dataType: "json",
				data:{
					"ids": ids
				},
				success: function(data) {
					if(data.isSuccess){
						success(ractive.get("string_sccg"),"",function(){
							$("#wd-table").setGridParam({
								url: requestAddressAction + "/role/list",
								postData: $("#searchForm").serializeArray()
							});	
						},null,false);
					}else{				
						alert(ractive.get("string_scsb"));
					}
				},
				error: function() {
					error(ractive.get("string_scsb"));
				}
			});
		}
	}, {
		"buttonId": "btn2",
		"buttonName": ractive.get("string_qx"),
		"buttonFunction": function() {
		}
	}];		
	//弹出框
	jQuery("#wd-alert-new").alert({
		innerHtml:ractive.get("string_sfqrsc"),
		title: ractive.get("string_scts"), // 非必选
		functionName: function() {
		}, // 非必选 优先
		buttons: buttons // 自定义按钮，非必选
	});

}
//修改
function funjsEdit(id) {
	var ractive = ractiveall;
	var buttons = [{
		"buttonName": ractive.get("string_tj"),
		"buttonFunction": function() {
			var rolename=this.Win.document.getElementById("rolename").value;	//登录名
			var rolecode=this.Win.document.getElementById("rolecode").value;//昵称
			var appkey=this.Win.document.getElementById("appkey").value;//姓名
			var descn=this.Win.document.getElementById("descn").value;//描述
			var flag = 	this.Win.jsaddfn();
			if(flag){
				var opts = {
					requesturl: "/role/update", // 
					dataobj:{
						"id":id,
						"rolename":rolename,
						"rolecode":rolecode,
						"appkey":appkey,
						"descn":descn
					},
					successCallback: function(data) { // 成功回调
						if(data.isSuccess){
							success(ractive.get("string_bccg"),"",function(){
								this.Win.Closed();
								$("#wd-table").setGridParam({
									url: requestAddressAction + "/role/list",
									postData: $("#searchForm").serializeArray()
								});	
							},null,false);
						}else{
							alert(data.msg);
						}	
					},
					errorCallback: function() { // 失败回调
						alert(ractive.get("string_qccw"));
					}
				};
				crossDomainAjax(opts);
				
			}
		},
		"mainButFlg": true // 是否主按钮，非必填，默认true
	}, {
		"buttonName": ractive.get("string_qx"),
		"buttonFunction": function() {
			this.Win.Closed();
		}
	}];
	$(".btn-add").dialog({
		url: 'js/jsadd.html?id='+id,
		title: ractive.get("string_xgjs"), // 非必选
		width: 520,
		height: 340,
		buttons: buttons // 自定义按钮，非必选
	});
}
function funjsFp(roleid){
	$.get('../template/js-fp.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-item-box',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-js", getLanguageFromCookie("language"));
				$("#fpForm #roleid").val(roleid);
				$("#wfpForm #roleid").val(roleid);
				$(".hp-return").show();
				$(".hp-return").unbind("click");
				$(".hp-return").click(function(){
					/**
				    * 管理员管理
				    */
//					getTemplate("app/template/js-list.html", ".hp-item-box", function() {
//						
//					});
					$.get('../template/js-list.html', function(template) {
						var ractive = new Ractive({
							el: '.hp-item-box',
							template: template,
							oncomplete: function() {
								//加载国际化文件
								loadProperties(ractive, "ptxtgl-js", getLanguageFromCookie("language"));
								ractiveall = ractive;
								$(".hp-return").hide();
								searhToggle();
								jsMethod();
							}
						});
					});
				});
				jQuery("#yx-table").wdGrid({
					url: requestAddressAction + "/rolePermission/relatedList",
					dataType: "GET",
					postData: $("#fpForm").serializeArray(),
					colNames: [ractive.get("string_qxdm"),ractive.get("string_qxmc")],
					colModel: [{
						name: 'permissioncode',
						width: 50
					}, {
						name: 'permissionname',
						width: 50
					}],
					checkflg: true,
					simplenav:true,
					pagecountwidth:180,
					pager: "#yx-pager",
					checktext: ['id'],
					rowList: [10, 20, 30],
					pagesize: 10
				});
				jQuery("#wx-table").wdGrid({
					url: requestAddressAction + "/rolePermission/unRelatedList",
					dataType: "GET",
					postData: $("#wfpForm").serializeArray(),
					colNames: [ractive.get("string_qxdm"),ractive.get("string_qxmc")],
					colModel: [{
						name: 'permissioncode',
						width: 50
					}, {
						name: 'permissionname',
						width: 50
					}],
					checkflg: true,
					simplenav:true,
					pagecountwidth:180,
					pager: "#wx-pager",
					checktext: ['id'],
					rowList: [10, 20, 30],
					pagesize: 10
				});
				$("#js-yx").click(function(){
					var str = "";
					str += $("#wx-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funjsyFp(roleid,str);
					}	
				});
				$("#js-wx").click(function(){
					var str = "";
					str += $("#yx-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funjsjcFp(roleid,str);
					}	
				});
				$("#jsfp-search").click(function(){
					$.ajax({
						type: "POST",
						url: requestAddressAction + "/rolePermission/relatedList",
						dataType: "json",
						data: $("#fpForm").serialize(),
						success: function(data) {
							$("#yx-table").setGridParam({
								url: requestAddressAction + "/rolePermission/relatedList",
								postData: $("#fpForm").serializeArray()
							});
						},
						error: function() {
						}
					});
				});
				$("#jswfp-search").click(function(){
					$.ajax({
						type: "POST",
						url: requestAddressAction + "/rolePermission/unRelatedList",
						dataType: "json",
						data: $("#wfpForm").serialize(),
						success: function(data) {
							$("#wx-table").setGridParam({
								url: requestAddressAction + "/rolePermission/unRelatedList",
								postData: $("#wfpForm").serializeArray()
							});
						},
						error: function() {
						}
					});
				})
			}
		});
	});
//	getTemplate("app/template/js-fp.html", ".hp-item-box", function() {
//		
//	});
}
function funjsyFp(roleid,ids){
	var ractive = ractiveall;
	$.ajax({
		type: "POST",
		url: requestAddressAction + "/rolePermission/related",
		dataType: "json",
		data:{
			"roleid":roleid,
			"permissionids": ids
		},
		success: function(data) {
			if(data.isSuccess){
				success(ractive.get("string_fpcg"),"",function(){
					$("#yx-table").setGridParam({
						url: requestAddressAction + "/rolePermission/relatedList",
						postData: $("#fpForm").serializeArray()
					});
					$("#wx-table").setGridParam({
						url: requestAddressAction + "/rolePermission/unRelatedList",
						postData: $("#wfpForm").serializeArray()
					});
				},null,false);
				
			}else{				
				alert(ractive.get("string_fpsb"));
			}
		},
		error: function() {
			error(ractive.get("string_fpsb"));
		}
	});
}
function funjsjcFp(roleid,ids){
	var ractive = ractiveall;
	$.ajax({
		type: "POST",
		url: requestAddressAction + "/rolePermission/unRelated",
		dataType: "json",
		data:{
			"roleid":roleid,
			"permissionids": ids
		},
		success: function(data) {
			if(data.isSuccess){
				success(ractive.get("string_jcfp"),"",function(){
					$("#yx-table").setGridParam({
						url: requestAddressAction + "/rolePermission/relatedList",
						postData: $("#fpForm").serializeArray()
					});
					$("#wx-table").setGridParam({
						url: requestAddressAction + "/rolePermission/unRelatedList",
						postData: $("#wfpForm").serializeArray()
					});
				},null,false);
				
			}else{				
				alert(ractive.get("string_jcsb"));
			}
		},
		error: function() {
			error(ractive.get("string_jcsb"));
		}
	});
}
