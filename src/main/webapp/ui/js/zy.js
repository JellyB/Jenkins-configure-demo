var ractiveall;
function zyLoad(){
	/**
    * 资源管理
    */
	$.get('../template/zy-list.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-item-box',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-zy", getLanguageFromCookie("language"));
				searhToggle();
				zyMethod();
				ractiveall = ractive;
			}
		});
	});
}
function zyMethod(){
	var ractive = ractiveall;
	var codeclass = ["RESOURCETYPE","YYXT"];
			setCodeMap(codeclass, function() {
				selecttextInfo();
			});
		$(".btn-zyadd").click(function(){
				var buttons = [{
					"buttonName": ractive.get("string_tj"),
					"buttonFunction": function() {
					var resourcecode=this.Win.document.getElementById("resourcecode").value;	//登录名
					var resourcename=this.Win.document.getElementById("resourcename").value;//昵称
					var resourcetype=this.Win.document.getElementById("resourcetype").value;//昵称
					var resourceurl=this.Win.document.getElementById("resourceurl").value;//昵称
					var appkey=this.Win.document.getElementById("appkey").value;//姓名
					var descn=this.Win.document.getElementById("descn").value;//描述
					var flag = 	this.Win.zyaddfn();
					if(flag){
						var opts = {
							requesturl: "/resource/save", // 
							dataobj:{
								"resourcecode":resourcecode,
								"resourcename":resourcename,
								"resourcetype":resourcetype,
								"resourceurl":resourceurl,
								"appkey":appkey,
								"descn":descn
							},
							successCallback: function(data) { // 成功回调
								if(data.isSuccess){
									success(ractive.get("string_bccg"),"",function(){
										this.Win.Closed();
										$("#wd-table").setGridParam({
											url: requestAddressAction + "/resource/list",
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
					url: 'zy/zyadd.html',
					title: ractive.get("string_cjzy"), // 非必选
					width: 520,
					height: 400,
					buttons: buttons // 自定义按钮，非必选
				});
			});
		jQuery("#wd-table").wdGrid({
			url: requestAddressAction + "/resource/list",
			dataType: "GET",
			postData: $("#searchForm").serializeArray(),
			colNames: [ractive.get("string_zydm"),ractive.get("string_zymc"),ractive.get("string_zylb"),ractive.get("string_zyurl"),ractive.get("string_gsxt"),ractive.get("string_cz")],
			colModel: [{
				name: 'resourcecode',
				width: 80
			}, {
				name: 'resourcename',
				width: 60
			}, {
				name: 'resourcetype',
				width: 80,
				codeformat: 'RESOURCETYPE'
				
			}, {
				name: 'resourceurl',
				width: 80
			}, {
				name: 'appkey',
				width: 90,
				codeformat: 'YYXT'
			}, {
				name: 'cz',
				width: 100,
				innerhtml: function(opts) {
					var str = "";
					str += "<a id='ad-xg' class='' onclick='funzyEdit(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_xg")+"</a>";
					str += "<a id='ad-sc' class='ad-ml10' onclick='funzyDel(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_sc")+"</a>";
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
		$("#zysearch").bind("click", function() {
			$.ajax({
				type: "POST",
				url: requestAddressAction + "/resource/list",
				dataType: "json",
				data: $("#searchForm").serialize(),
				success: function(data) {
					$("#wd-table").setGridParam({
						url: requestAddressAction + "/resource/list",
						postData: $("#searchForm").serializeArray()
					});
				},
				error: function() {
				}
			});				
		});
		$(".btn-zydelete").click(function(){
			var str = "";
					str += $("#wd-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funzyDel(str);
					}				
		})
}
function funzyDel(ids) {
	var ractive = ractiveall;
	//弹出框按钮
	var buttons= [{
		"buttonId": "btn1", // 自定义按钮id
		"buttonName": ractive.get("string_qd"), // 显示文本
		"buttonFunction": function() { // 点击后出发事件
			//数据请求
			$.ajax({
				type: "POST",
				url: requestAddressAction + "/resource/delete",
				dataType: "json",
				data:{
					"ids": ids
				},
				success: function(data) {
					if(data.isSuccess){
						success(ractive.get("string_sccg"),"",function(){
							$("#wd-table").setGridParam({
								url: requestAddressAction + "/resource/list",
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
function funzyEdit(id) {
	var buttons = [{
		"buttonName": ractive.get("string_tj"),
		"buttonFunction": function() {
			var resourcecode=this.Win.document.getElementById("resourcecode").value;	//登录名
			var resourcename=this.Win.document.getElementById("resourcename").value;//昵称
			var resourcetype=this.Win.document.getElementById("resourcetype").value;//昵称
			var resourceurl=this.Win.document.getElementById("resourceurl").value;//昵称
			var appkey=this.Win.document.getElementById("appkey").value;//姓名
			var descn=this.Win.document.getElementById("descn").value;//描述
			var flag = 	this.Win.zyaddfn();
			if(flag){
				var opts = {
					requesturl: "/resource/update", // 
					dataobj:{
						"id":id,
						"resourcecode":resourcecode,
						"resourcename":resourcename,
						"resourcetype":resourcetype,
						"resourceurl":resourceurl,
						"appkey":appkey,
						"descn":descn
					},
					successCallback: function(data) { // 成功回调
						if(data.isSuccess){
							success(ractive.get("string_bccg"),"",function(){
								this.Win.Closed();
								$("#wd-table").setGridParam({
									url: requestAddressAction + "/resource/list",
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
		url: 'zy/zyadd.html?id='+id,
		title: ractive.get("string_xgzy"), // 非必选
		width: 520,
		height: 400,
		buttons: buttons // 自定义按钮，非必选
	});
}
