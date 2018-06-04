var ractiveall;
function qxLoad(){
	/**
    * 权限管理
    */
	$.get('../template/qx-list.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-item-box',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-qx", getLanguageFromCookie("language"));
				searhToggle();
				qxMethod();
				ractiveall = ractive;
			}
		});
	});
}
function qxMethod(){
	var ractive = ractiveall;
	var codeclass = new Array("YYXT");
			setCodeMap(codeclass, function() {
				selecttextInfo();
			});
		$(".btn-qxadd").click(function(){
				var buttons = [{
					"buttonName": ractive.get("string_tj"),
					"buttonFunction": function() {
						var permissionname=this.Win.document.getElementById("permissionname").value;	//登录名
						var permissioncode=this.Win.document.getElementById("permissioncode").value;//昵称
						var appkey=this.Win.document.getElementById("appkey").value;//姓名
						var descn=this.Win.document.getElementById("descn").value;//描述
						var flag = 	this.Win.qxaddfn();
						if(flag){
							var opts = {
								requesturl: "/permission/save", // 
								dataobj:{
									"permissionname":permissionname,
									"permissioncode":permissioncode,
									"appkey":appkey,
									"descn":descn
								},
								successCallback: function(data) { // 成功回调
									if(data.isSuccess){
										success(ractive.get("string_bccg"),"",function(){
											this.Win.Closed();
											$("#wd-table").setGridParam({
												url: requestAddressAction + "/permission/list",
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
					url: 'qx/qxadd.html',
					title: ractive.get("string_cjqx"), // 非必选
					width: 520,
					height: 320,
					buttons: buttons // 自定义按钮，非必选
				});
			});
		jQuery("#wd-table").wdGrid({
			url: requestAddressAction + "/permission/list",
			dataType: "GET",
			postData: $("#searchForm").serializeArray(),
			colNames: [ractive.get("string_qxdm"),ractive.get("string_qxmc"),ractive.get("string_gsxt"),ractive.get("string_cjr"),ractive.get("string_cjsj"),ractive.get("string_cz")],
			colModel: [{
				name: 'permissioncode',
				width: 80
			}, {
				name: 'permissionname',
				width: 80
			}, {
				name: 'appkey',
				width: 90,
				codeformat: 'YYXT'
			}, {
				name: 'creatercode',
				width: 60
			}, {
				name: 'creatertime',
				width: 90,
				dataformat: 'yyyy-MM-dd'
			}, {
				name: 'cz',
				width: 110,
				innerhtml: function(opts) {
					var str = "";
					str += "<a id='ad-xg' class='' onclick='funqxEdit(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_xg")+"</a>";
					str += "<a id='ad-sc' class='ad-ml10' onclick='funqxDel(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_sc")+"</a>";
					str += "<a id='ad-sc' class='ad-ml10' onclick='funqxFp(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_fpzy")+"</a>";
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
		$("#qxsearch").bind("click", function() {
			$.ajax({
				type: "POST",
				url: requestAddressAction + "/permission/list",
				dataType: "json",
				data: $("#searchForm").serialize(),
				success: function(data) {
					$("#wd-table").setGridParam({
						url: requestAddressAction + "/permission/list",
						postData: $("#searchForm").serializeArray()
					});
				},
				error: function() {
				}
			});				
		});
		$(".btn-qxdelete").click(function(){
			var str = "";
					str += $("#wd-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funqxDel(str);
					}				
		})
}
function funqxDel(ids) {
	var ractive = ractiveall;
	//弹出框按钮
	var buttons= [{
		"buttonId": "btn1", // 自定义按钮id
		"buttonName": ractive.get("string_qd"), // 显示文本
		"buttonFunction": function() { // 点击后出发事件
			//数据请求
			$.ajax({
				type: "POST",
				url: requestAddressAction + "/permission/delete",
				dataType: "json",
				data:{
					"ids": ids
				},
				success: function(data) {
					if(data.isSuccess){
						success(ractive.get("string_sccg"),"",function(){
							$("#wd-table").setGridParam({
								url: requestAddressAction + "/permission/list",
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
		innerHtml:ractive.get("string_qrsfsc"),
		title: ractive.get("string_scts"), // 非必选
		functionName: function() {
		}, // 非必选 优先
		buttons: buttons // 自定义按钮，非必选
	});

}
//修改
function funqxEdit(id) {
	var ractive = ractiveall;
	var buttons = [{
		"buttonName": ractive.get("string_tj"),
		"buttonFunction": function() {
			var permissionname=this.Win.document.getElementById("permissionname").value;	//登录名
			var permissioncode=this.Win.document.getElementById("permissioncode").value;//昵称
			var appkey=this.Win.document.getElementById("appkey").value;//姓名
			var descn=this.Win.document.getElementById("descn").value;//描述
			var flag = 	this.Win.qxaddfn();
			if(flag){
				var opts = {
					requesturl: "/permission/update", // 
					dataobj:{
						"id":id,
						"permissionname":permissionname,
						"permissioncode":permissioncode,
						"appkey":appkey,
						"descn":descn
					},
					successCallback: function(data) { // 成功回调
						if(data.isSuccess){
							success(ractive.get("string_bccg"),"",function(){
								this.Win.Closed();
								$("#wd-table").setGridParam({
									url: requestAddressAction + "/permission/list",
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
	$(".qxbtn-add").dialog({
		url: 'qx/qxadd.html?id='+id,
		title: ractive.get("string_xgqx"), // 非必选
		width: 520,
		height: 320,
		buttons: buttons // 自定义按钮，非必选
	});
}
function funqxFp(permissionid){
	$.get('../template/qx-fp.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-item-box',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-qx", getLanguageFromCookie("language"));
				$("#fpForm #permissionid").val(permissionid);
				$("#wfpForm #permissionid").val(permissionid);
				$(".hp-return").show();
				$(".hp-return").unbind("click");
				$(".hp-return").click(function(){
					/**
				    * 权限管理
				    */
					$.get('../template/qx-list.html', function(template) {
						var ractive = new Ractive({
							el: '.hp-item-box',
							template: template,
							oncomplete: function() {
								//加载国际化文件
								loadProperties(ractive, "ptxtgl-qx", getLanguageFromCookie("language"));
								$(".hp-return").hide();
								searhToggle();
								qxMethod();
								ractiveall = ractive;
							}
						});
					});
					
				});
				jQuery("#yx-table").wdGrid({
					url: requestAddressAction + "/permissionResource/relatedList",
					dataType: "GET",
					postData: $("#fpForm").serializeArray(),
					colNames: [ractive.get("string_zydm"),ractive.get("string_zymc")],
					colModel: [{
						name: 'resourcecode',
						width: 50
					}, {
						name: 'resourcename',
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
					url: requestAddressAction + "/permissionResource/unRelatedList",
					dataType: "GET",
					postData: $("#wfpForm").serializeArray(),
					colNames: [ractive.get("string_zydm"),ractive.get("string_zymc")],
					colModel: [{
						name: 'resourcecode',
						width: 50
					}, {
						name: 'resourcename',
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
				$("#qx-yx").click(function(){
					var str = "";
					str += $("#wx-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funqxyFp(permissionid,str);
					}	
				});
				$("#qx-wx").click(function(){
					var str = "";
					str += $("#yx-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funqxjcFp(permissionid,str);
					}	
				});
				$("#qxfp-search").click(function(){
					$.ajax({
						type: "POST",
						url: requestAddressAction + "/permissionResource/relatedList",
						dataType: "json",
						data: $("#fpForm").serialize(),
						success: function(data) {
							$("#yx-table").setGridParam({
								url: requestAddressAction + "/permissionResource/relatedList",
								postData: $("#fpForm").serializeArray()
							});
						},
						error: function() {
						}
					});
				});
				$("#qxwfp-search").click(function(){
					$.ajax({
						type: "POST",
						url: requestAddressAction + "/permissionResource/unRelatedList",
						dataType: "json",
						data: $("#wfpForm").serialize(),
						success: function(data) {
							$("#wx-table").setGridParam({
								url: requestAddressAction + "/permissionResource/unRelatedList",
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
}
function funqxyFp(permissionid,ids){
	var ractive = ractiveall;
	$.ajax({
		type: "POST",
		url: requestAddressAction + "/permissionResource/related",
		dataType: "json",
		data:{
			"permissionid":permissionid,
			"ids": ids
		},
		success: function(data) {
			if(data.isSuccess){
				success(ractive.get("string_fpcg"),"",function(){
					$("#yx-table").setGridParam({
						url: requestAddressAction + "/permissionResource/relatedList",
						postData: $("#fpForm").serializeArray()
					});
					$("#wx-table").setGridParam({
						url: requestAddressAction + "/permissionResource/unRelatedList",
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
function funqxjcFp(permissionid,ids){
	var ractive = ractiveall;
	$.ajax({
		type: "POST",
		url: requestAddressAction + "/permissionResource/unRelated",
		dataType: "json",
		data:{
			"permissionid":permissionid,
			"ids": ids
		},
		success: function(data) {
			if(data.isSuccess){
				success(ractive.get("string_jcfp"),"",function(){
					$("#yx-table").setGridParam({
						url: requestAddressAction + "/permissionResource/relatedList",
						postData: $("#fpForm").serializeArray()
					});
					$("#wx-table").setGridParam({
						url: requestAddressAction + "/permissionResource/unRelatedList",
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
