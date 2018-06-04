var ractiveall;
function glyLoad(){
	/**
    * 管理员管理
    */
//	getTemplate("app/template/gly-list.html", ".hp-item-box", function() {
//		searhToggle();
//		glyMethod();
//	});
	$.get('../template/gly-list.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-item-box',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-gly", getLanguageFromCookie("language"));
				searhToggle();
				glyMethod(ractive);
				ractiveall = ractive;
			}
		});
	});
}
function glyMethod(ractive){

		$(".btn-add").click(function(){
				var buttons = [{
					"buttonName": ractive.get("string_tj"),
					"buttonFunction": function() {
						var loginname=this.Win.document.getElementById("loginname").value;	//登录名
						var nickname=this.Win.document.getElementById("nickname").value;//昵称
						var username=this.Win.document.getElementById("username").value;//姓名
						var passwd=this.Win.document.getElementById("passwd").value;//密码
						var confirmpasswd=this.Win.document.getElementById("confirmpasswd").value;//确认密码
						var descn=this.Win.document.getElementById("descn").value;//描述
						var flag = 	this.Win.glyaddfn();
						if(flag){
							var opts = {
								requesturl: "/user/save", // 
								dataobj:{
									"loginname":loginname,
									"nickname":nickname,
									"username":username,
									"passwd":passwd,
									"confirmpasswd":confirmpasswd,
									"descn":descn
								},
								successCallback: function(data) { // 成功回调
									if(data.isSuccess){
										success(ractive.get("string_bccg"),"",function(){
											this.Win.Closed();
											$("#wd-table").setGridParam({
												url: requestAddressAction + "/user/list",
												postData: $("#searchForm").serializeArray()
											});	
										},null,false);
									}else{				
										alert(data.msg);
									}
								},
								errorCallback: function() { // 失败回调
									alert(ractive.get("string_qqcw"));
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
					url: 'gly/glyadd.html',
					title: ractive.get("string_cjgly"), // 非必选
					width: 520,
					height: 400,
					buttons: buttons // 自定义按钮，非必选
				});
			});
			$(".btn-czmm").click(function(){
				var str = "";
				str += $("#wd-table").getAllCheckedId();
				if(str == ""){
					jQuery("#wd-alert-new").alert({
						innerHtml: ractive.get("string_qxzsj")
					});
				}else{
				
				var buttons = [{
					"buttonName": ractive.get("string_tj"),
					"buttonFunction": function() {
						var passwd=this.Win.document.getElementById("passwd").value;	//登录名
						var confirmPasswd=this.Win.document.getElementById("confirmPasswd").value;//昵称
						var flag = 	this.Win.glyaddfn();
						if(flag){
							var opts = {
								requesturl: "/user/resetPasswd", // 
								dataobj:{
									"ids":str,
									"passwd":passwd,
									"confirmPasswd":confirmPasswd
								},
								successCallback: function(data) { // 成功回调
									if(data.isSuccess){
										success(ractive.get("string_czmmcg"),"",function(){
											this.Win.Closed();
											$("#wd-table").setGridParam({
												url: requestAddressAction + "/user/list",
												postData: $("#searchForm").serializeArray()
											});	
										},null,false);
									}else{				
										alert(data.msg);
									}
								},
								errorCallback: function() { // 失败回调
									alert(ractive.get("string_qqcw"));
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
				$(".btn-czmm").dialog({
					url: 'gly/glyczmm.html',
					title: ractive.get("string_czmm"), // 非必选
					width: 420,
					height: 140,
					buttons: buttons // 自定义按钮，非必选
				});
				}	
			});

		jQuery("#wd-table").wdGrid({
			url: requestAddressAction + "/user/list",
			dataType: "GET",
			postData: $("#searchForm").serializeArray(),
			colNames: [ractive.get("string_glyxm"),ractive.get("string_glyzh"),ractive.get("string_glydlm"),ractive.get("string_glync"),ractive.get("string_cjr"),ractive.get("string_cjsj"),ractive.get("string_cz")],
			colModel: [{
				name: 'username',
				width: 80
			}, {
				name: 'loginid',
				width: 60
			}, {
				name: 'loginname',
				width: 80
			}, {
				name: 'nickname',
				width: 70
			}, {
				name: 'creatercode',
				width: 80
			}, {
				name: 'creatertime',
				width: 90,
				dataformat: 'yyyy-MM-dd'
			}, {
				name: 'cz',
				width: 190,
				innerhtml: function(opts) {
					var str = "";
					str += "<a id='ad-xg' class='' onclick='funEdit(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_xg")+"</a>";
					str += "<a id='ad-sc' class='ad-ml10' onclick='funDel(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_sc")+"</a>";
					str += "<a id='ad-sc' class='ad-ml10' onclick='funFp(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_fpjs")+"</a>";
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
		$("#search").bind("click", function() {
			$.ajax({
				type: "POST",
				url: requestAddressAction + "/user/list",
				dataType: "json",
				data: $("#searchForm").serialize(),
				success: function(data) {
					$("#wd-table").setGridParam({
						url: requestAddressAction + "/user/list",
						postData: $("#searchForm").serializeArray()
					});
				},
				error: function() {
				}
			});				
		});
		$(".btn-delete").click(function(){
			var str = "";
					str += $("#wd-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funDel(str);
					}				
		})
}

function funDel(ids) {
	var ractive = ractiveall;
	//弹出框按钮
	var buttons= [{
		"buttonId": "btn1", // 自定义按钮id
		"buttonName": ractive.get("string_qd"), // 显示文本
		"buttonFunction": function() { // 点击后出发事件
			//数据请求
			var opts = {
				requesturl: "/user/delete", // 
				dataobj:{
					"ids": ids
				},
				successCallback: function(data) { // 成功回调
					if(data.isSuccess){
						success(ractive.get("string_sccg"),"",function(){
							$("#wd-table").setGridParam({
							url: requestAddressAction + "/user/list",
							postData: $("#searchForm").serializeArray()
						});	
						},null,false);
						
					}else{				
						alert(data.msg);
					}
				},
				errorCallback: function() { // 失败回调
					alert(ractive.get("string_qqcw"));
				}
			};
			crossDomainAjax(opts);
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
function funEdit(id) {
	var ractive = ractiveall;
	var buttons = [{
		"buttonName": ractive.get("string_tj"),
		"buttonFunction": function() {
			var loginname=this.Win.document.getElementById("loginname").value;	//登录名
			var nickname=this.Win.document.getElementById("nickname").value;//昵称
			var username=this.Win.document.getElementById("username").value;//姓名
			var descn=this.Win.document.getElementById("descn").value;//描述
			var flag = 	this.Win.glyaddfn();
			if(flag){
				var opts = {
					requesturl: "/user/update", // 
					dataobj:{
						"id":id,
						"loginname":loginname,
						"nickname":nickname,
						"username":username,
						"descn":descn
					},
					successCallback: function(data) { // 成功回调
						if(data.isSuccess){
							success(ractive.get("string_bccg"),"",function(){
								this.Win.Closed();
								$("#wd-table").setGridParam({
									url: requestAddressAction + "/user/list",
									postData: $("#searchForm").serializeArray()
								});	
							},null,false);
						}else{				
							alert(data.msg);
						}
					},
					errorCallback: function() { // 失败回调
						alert(ractive.get("string_qqcw"));
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
		url: 'gly/glyEdit.html?id='+id,
		title: ractive.get("string_xggly"), // 非必选
		width: 520,
		height: 350,
		buttons: buttons // 自定义按钮，非必选
	});
}
function funFp(userid){
	$.get('../template/gly-fp.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-item-box',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-gly", getLanguageFromCookie("language"));
				$("#fpForm #userid").val(userid);
				$("#wfpForm #userid").val(userid);
				$(".hp-return").show();
				$(".hp-return").unbind("click");
				$(".hp-return").click(function(){
					/**
				    * 管理员管理
				    */
					$.get('../template/gly-list.html', function(template) {
						var ractive = new Ractive({
							el: '.hp-item-box',
							template: template,
							oncomplete: function() {
								//加载国际化文件
								loadProperties(ractive, "ptxtgl-gly", getLanguageFromCookie("language"));
								$(".hp-return").hide();
								searhToggle();
								glyMethod(ractive);
							}
						});
					});
				});
				jQuery("#yx-table").wdGrid({
					url: requestAddressAction + "/userRole/relatedList",
					dataType: "GET",
					postData: $("#fpForm").serializeArray(),
					colNames: [ractive.get("string_jsdm"),ractive.get("stirng_jsmc")],
					colModel: [{
						name: 'rolecode',
						width: 50
					}, {
						name: 'rolename',
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
					url: requestAddressAction + "/userRole/unRelatedList",
					dataType: "GET",
					postData: $("#wfpForm").serializeArray(),
					colNames: [ractive.get("string_jsdm"),ractive.get("stirng_jsmc")],
					colModel: [{
						name: 'rolecode',
						width: 50
					}, {
						name: 'rolename',
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
				$("#gly-yx").click(function(){
					var str = "";
					str += $("#wx-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funyFp(str,userid,ractive);
					}	
				});
				$("#gly-wx").click(function(){
					var str = "";
					str += $("#yx-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funjcFp(str,userid,ractive);
					}	
				});
				$("#fp-search").click(function(){
					$.ajax({
						type: "POST",
						url: requestAddressAction + "/userRole/relatedList",
						dataType: "json",
						data: $("#fpForm").serialize(),
						success: function(data) {
							$("#yx-table").setGridParam({
								url: requestAddressAction + "/userRole/relatedList",
								postData: $("#fpForm").serializeArray()
							});
						},
						error: function() {
						}
					});
				});
				$("#wfp-search").click(function(){
					$.ajax({
						type: "POST",
						url: requestAddressAction + "/userRole/unRelatedList",
						dataType: "json",
						data: $("#wfpForm").serialize(),
						success: function(data) {
							$("#wx-table").setGridParam({
								url: requestAddressAction + "/userRole/unRelatedList",
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
//	getTemplate("app/template/gly-fp.html", ".hp-item-box", function() {
//	
//	});
}
function funyFp(ids,userid){
	var ractive = ractiveall;
	$.ajax({
		type: "POST",
		url: requestAddressAction + "/userRole/related",
		data:{
			"roleids": ids,
			"userid":userid
		},
		success: function(data) {
			if(data.isSuccess){
				success(ractive.get("string_fpcg"),"",function(){
					$("#yx-table").setGridParam({
						url: requestAddressAction + "/userRole/relatedList",
						postData: $("#fpForm").serializeArray()
					});
					$("#wx-table").setGridParam({
						url: requestAddressAction + "/userRole/unRelatedList",
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
function funjcFp(ids,userid){
	var ractive = ractiveall;
	$.ajax({
		type: "POST",
		url: requestAddressAction + "/userRole/unRelated",
		dataType: "json",
		data:{
			"roleids": ids,
			"userid":userid
		},
		success: function(data) {
			if(data.isSuccess){
				success(ractive.get("string_jcfp"),"",function(){
					$("#yx-table").setGridParam({
						url: requestAddressAction + "/userRole/relatedList",
						postData: $("#fpForm").serializeArray()
					});
					$("#wx-table").setGridParam({
						url: requestAddressAction + "/userRole/unRelatedList",
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
