var ractiveall;
function cdLoad(){
	/**
    * 资源管理
    */
	$.get('../template/cd-list.html', function(template) {
		var ractive = new Ractive({
			el: '.hp-item-box',
			template: template,
			oncomplete: function() {
				//加载国际化文件
				loadProperties(ractive, "ptxtgl-cd", getLanguageFromCookie("language"));
				searhToggle();
				cdMethod();
				ractiveall = ractive;
			}
		});
	});
}
function cdMethod(){
	var ractive = ractiveall;
	var codeclass = new Array("YYXT");
			setCodeMap(codeclass, function() {
				selecttextInfo();
			});
		$(".btn-cdadd").click(function(){
				var buttons = [{
					"buttonName": ractive.get("string_tj"),
					"buttonFunction": function() {
					var modulecode=this.Win.document.getElementById("modulecode").value;	//登录名
					var modulename=this.Win.document.getElementById("modulename").value;//昵称
					//var modulelevel=this.Win.document.getElementById("modulelevel").value;//昵称
					var appkey=this.Win.document.getElementById("appkey").value;//姓名
					var temp = this.Win.document.getElementsByName("sfCode");
					var intHot="";
					for(var i=0;i<temp.length;i++)
					  {
					     if(temp[i].checked){  
					     	intHot = temp[i].value;
					     }
					}
					var parentid=this.Win.document.getElementById("PARENTID").value;//姓名
					var descn=this.Win.document.getElementById("descn").value;//描述
					var flag = 	this.Win.cdaddfn();
					if(flag){
						if(intHot==ractive.get("string_fou")&& !parentid){
							alert(ractive.get("string_qxzfcd"));
							return false;
						}
						var opts = {
							requesturl:  "/module/save", // 
							dataobj:{
								"modulecode":modulecode,
								"modulename":modulename,
								"parentid":parentid,
								"appkey":appkey,
								"descn":descn
							},
							successCallback: function(data) { // 成功回调
								if(data.isSuccess){
									success(ractive.get("string_bccg"),"",function(){
										this.Win.Closed();
										$("#wd-table").setGridParam({
											url: requestAddressAction + "/module/list",
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
					url: 'cd/cdadd.html',
					title: ractive.get("string_cjcd"), // 非必选
					width: 520,
					height: 400,
					buttons: buttons // 自定义按钮，非必选
				});
			});
		jQuery("#wd-table").wdGrid({
			url: requestAddressAction + "/module/list",
			dataType: "GET",
			postData: $("#searchForm").serializeArray(),
			colNames: [ractive.get("string_cddm"),ractive.get("string_cdmc"),ractive.get("string_gsxt"),ractive.get("string_glzy"),ractive.get("string_cz")],
			colModel: [{
				name: 'modulecode',
				width: 80
			}, {
				name: 'modulename',
				width: 60
			}, {
				name: 'appkey',
				width: 90,
				codeformat: 'YYXT'
			}, {
				name: 'resourcename',
				width: 80
			}, {
				name: 'cz',
				width: 100,
				innerhtml: function(opts) {
					var str = "";
					str += "<a id='ad-xg' class='' onclick='funcdEdit(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_xg")+"</a>";
					str += "<a id='ad-sc' class='ad-ml10' onclick='funcdDel(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_sc")+"</a>";
					str += "<a id='ad-sc' class='ad-ml10' onclick='funcdFp(\""+opts.id+"\")' href='javascript:void(0);' title=''>"+ractive.get("string_glzy")+"</a>";
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
		$("#cdsearch").bind("click", function() {
			//数据请求
			$.ajax({
				type: "POST",
				url: requestAddressAction + "/module/list",
				dataType: "json",
				data: $("#searchForm").serialize(),
				success: function(data) {
					$("#wd-table").setGridParam({
						url: requestAddressAction + "/module/list",
						postData: $("#searchForm").serializeArray()
					});
				},
				error: function() {
				}
			});	
		});
		$(".btn-cddelete").click(function(){
			var str = "";
					str += $("#wd-table").getAllCheckedId();
					if(str == ""){
						jQuery("#wd-alert-new").alert({
							innerHtml: ractive.get("string_qxzsj")
						});
					}else{
						funcdDel(str);
					}				
		})
}
function funcdDel(ids) {
	var ractive = ractiveall;
	//弹出框按钮
	var buttons= [{
		"buttonId": "btn1", // 自定义按钮id
		"buttonName": ractive.get("string_qd"), // 显示文本
		"buttonFunction": function() { // 点击后出发事件
			//数据请求
			var opts = {
				requesturl:  "/module/delete", // 
				dataobj:{
					"ids": ids
				},
				successCallback: function(data) { // 成功回调
					if(data.isSuccess){
						success(ractive.get("string_sccg"),"",function(){
							$("#wd-table").setGridParam({
								url: requestAddressAction + "/module/list",
								postData: $("#searchForm").serializeArray()
							});	
						},null,false);
					}else{				
						alert(ractive.get("string_scsb"));
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
function funcdEdit(id) {
	var ractive = ractiveall;
	var buttons = [{
		"buttonName": ractive.get("string_tj"),
		"buttonFunction": function() {
			var modulecode=this.Win.document.getElementById("modulecode").value;	//登录名
			var modulename=this.Win.document.getElementById("modulename").value;//昵称
			//var modulelevel=this.Win.document.getElementById("modulelevel").value;//昵称
			var parentid=this.Win.document.getElementById("PARENTID").value;//姓名
			var appkey=this.Win.document.getElementById("appkey").value;//姓名
			var descn=this.Win.document.getElementById("descn").value;//描述
			var flag = 	this.Win.cdaddfn();
			if(flag){
				//数据请求
				var opts = {
					requesturl:  "/module/update", // 
					dataobj:{
						"id"        :id,
						"modulecode":modulecode,
						"modulename":modulename,
						"parentid":parentid,
						"appkey":appkey,
						"descn":descn
					},
					successCallback: function(data) { // 成功回调
						if(data.isSuccess){
							success(ractive.get("string_bccg"),"",function(){
								this.Win.Closed();
								$("#wd-table").setGridParam({
									url: requestAddressAction + "/module/list",
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
		url: 'cd/cdadd.html?id='+id,
		title: ractive.get("string_xgcd"), // 非必选
		width: 520,
		height: 400,
		buttons: buttons // 自定义按钮，非必选
	});
}
function funcdFp(id){
	var ractive = ractiveall;
	var buttons = [{
		"buttonName": ractive.get("string_tj"),
		"buttonFunction": function() {
			var zyid = this.Win.getzyid();
			
			if(zyid){
				//数据请求
				var opts = {
					requesturl:  "/module/related", // 
					dataobj:{
						"moduleid":id,
						"resourceid":zyid
					},
					successCallback: function(data) { // 成功回调
						if(data.isSuccess){
							success(ractive.get("string_glcg"),"",function(){
								this.Win.Closed();
								$("#wd-table").setGridParam({
									url: requestAddressAction + "/module/list",
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
			}else{
				alert(ractive.get("string_xzzygl"))
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
		url: 'cd/cd-fp.html',
		title: ractive.get("string_glzy"), // 非必选
		width: 920,
		height: 340,
		buttons: buttons // 自定义按钮，非必选
	});
}
