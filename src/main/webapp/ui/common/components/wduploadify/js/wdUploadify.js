(function($) {
	$.extend($.fn, {
		wdUploadify: function(options) {
			var jsArray = [{
				"jsurl": "ui/common/components/wduploadify/js/jquery.uploadify.js",
				"version": "1.0"
			}, {
				"jsurl": "ui/common/components/wduploadify/js/swfobject.js",
				"version": "1.0"
			}];

			var settings = $.extend(true, {
				uploader: 'uploadify.swf', // 上传flash
				buttonImg: '', // 上传按钮
				cancelImg: '', // 删除按钮
				showUrl: '', // 请求图片路径接口
				delUrl: '', // 删除接口
				script: '/upload', // 上传接口
				scriptData: {}, // 上传接口参数
				fileDataName: "files", // 图片流id
				queueID: false, // 展示区id
				fileDesc: '所有文件 (*.*)', // 显示类型
				fileExt: '*.*', // 上传文件可选类型
				hideButton: false,
				removeCompleted: false, // 上传成功是否显示
				sizeLimit: 100 * 1024 * 1024, // 上传文件大小默认限制为4MB
				simUploadLimit: 10, // 上传文件单次限制
				auto: true, // 是否自动提交
				width: 100, // 上传按钮宽度
				height: 40, // 上传按钮高度
				multi: true, // 是否多选
				displayType: false, // 展示形式
				isHeadPortrait : false,
				headPortraitId : "",
				displayPic: {
					picWidth: 80,
					picHeight: 80
				},
				onInit: null, // 初始化数据
				customShow: null, // 自定义展现方式
				initFile: [], // 初始化回调
				param: ["fileId", "fileName", "fileSize", "fileExtName"],
				paramArr: [],
				onComplete: null, // 上传回调
				onAllComplete: null,
				onCancel: null // 删除回调
			}, options);

			var id = jQuery(this).attr('id'); // 上传按钮id

			if (settings.queueID == false) {
				// 创建展示区
//				if (settings.displayType) {
//					var html = '<div id="' + id + '_fileQueue">';
//					html += '<div id="' + id + '_disArea" class="wdUploadify-disArea"></div></div>';
//					jQuery(this).after(html);
//					settings.queueID = id + '_fileQueue';
//				} else {
					jQuery(this).after('<div id="' + id + '_fileQueue"></div>');
					settings.queueID = id + '_fileQueue';
//				}
			} else {
//				if (settings.displayType) {
//					var disAreaDiv = document.createElement('div');
//					disAreaDiv.id = id + '_disArea';
//					disAreaDiv.className = 'wdUploadify-disArea';
//					$('#' + settings.queueID).after(disAreaDiv);
//				}
			}

			addJs(jsArray[0], jsArray, 0, function() {
				$("#" + id).uploadify({
					'uploader': settings.uploader,
					'buttonImg': settings.buttonImg,
					'cancelImg': settings.cancelImg,
					'script': settings.script,
					'scriptData': settings.scriptData,
					'fileDataName': settings.fileDataName,
					'queueID': settings.queueID,
					'fileDesc': settings.fileDesc,
					'fileExt': settings.fileExt,
					'hideButton': settings.hideButton,
					'removeCompleted': settings.removeCompleted,
					'sizeLimit': settings.sizeLimit, // 上传文件大小默认限制为4MB
					'simUploadLimit': settings.simUploadLimit,
					'auto': settings.auto,
					'width': settings.width,
					'height': settings.height,
					'multi': settings.multi,
					onSelect: function(event, queueId, fileObj) {},
					onSelectOnce: function(event, data) {},
					onComplete: function(event, queueId, fileObj, response, data) {
						// fileObj 上传文件属性; response 请求响应
						var obj = $.parseJSON(response);
						if (obj.successFlg == true || obj.successFlg == "true") {
							jQuery("#" + jQuery(event.target).attr('id') + queueId).find('.percentage').text(' - 完成');
							jQuery("#" + jQuery(event.target).attr('id') + queueId).addClass('completed');

							if (settings.onComplete) {
								if(settings.isHeadPortrait){
									$("#" + settings.headPortraitId).attr("src", settings.showUrl + "/" + obj.msgObj[0].fileId);
									$("#" + settings.headPortraitId + "_fileId").val(obj.msgObj[0].fileId);
								}
								// 自定义上传回调
								settings.onComplete(event, queueId, fileObj, response, data);
							} else {
								$.each(obj.msgObj, function() {
									var fileItem = this;
									$('#' + id + queueId).attr('fileId', fileItem.fileId);
									if (settings.displayType) {
										//$('#tburl').val(fileItem.fileId);
										$('#' + id + queueId).hide();
										var tbDiv = document.createElement('div');
										tbDiv.className = 'wdUploadify-dis';
										tbDiv.id = "logo" + queueId;
										if (settings.displayPic) {
											if (settings.displayPic.picWidth) {
												tbDiv.style.width = settings.displayPic.picWidth + "px";
											}
											if (settings.displayPic.picHeight) {
												tbDiv.style.height = settings.displayPic.picHeight + "px";
												tbDiv.style.lineHeight = (settings.displayPic.picHeight - 8) + "px";
											}
										}

										var tbShowImg = document.createElement('img');
										//请求地址
										tbShowImg.src = settings.showUrl + fileItem.fileId;
										tbDiv.appendChild(tbShowImg);

										var tbcancelDiv = document.createElement('div');
										tbcancelDiv.className = 'wdUploadify-cancel';
										tbcancelDiv.style.backgroundColor = 'rgba(0,0,0,0.6)';
										var tbA = document.createElement('a');
										tbA.href = "javascript:jQuery('#" + id + "').uploadifyCancel('" + queueId + "')";
										tbA.className = "wdUploadify-cancelBtn";
										var tbDelImg = document.createElement('img');
										tbDelImg.src = settings.cancelImg;
										tbA.appendChild(tbDelImg);
										tbcancelDiv.appendChild(tbA);

										tbDiv.appendChild(tbcancelDiv);
//										$('#' + id + '_disArea').append(tbDiv);
										$('#' + settings.queueID).append(tbDiv);

										$(tbDiv).hover(
											function() {
												$(tbcancelDiv).show();
											},
											function() {
												$(tbcancelDiv).hide();
											});
									}
									settings.paramArr.push(eval("({'" +
										settings.param[0] + "':'" + fileItem.fileId + "','" +
										settings.param[1] + "':'" + fileItem.fileName + "','" +
										settings.param[2] + "':'" + fileItem.fileSize + "','" +
										settings.param[3] + "':'" + fileItem.fileExtName + "'})"));
								});
							}
						} else {
							$('#' + id + queueId).addClass('uploadifyError');
							$('#' + id + queueId).find('.percentage').text(' - 失败');
						}
						return false;
					},
					onAllComplete: function() {
						// 自定义上传回调
						if (settings.onAllComplete) {
							settings.onAllComplete(event, data);
						}
					},
					onError: function(event, queueId, fileObj, errorObj) {
						if (errorObj.type === "File Size") {
							byteSize = '4';
							jQuery("#" + jQuery(event.target).attr('id') + queueId).find('.percentage').text("所选文件大小超过最大限制(" + byteSize + suffix + ")");
						} else {
							jQuery("#" + jQuery(event.target).attr('id') + queueId).find('.percentage').text(" - " + errorObj.type + " Error");
						}

						jQuery("#" + jQuery(event.target).attr('id') + queueId).find('.uploadifyProgress').hide();
						jQuery("#" + jQuery(event.target).attr('id') + queueId).addClass('uploadifyError');
						return false;
					},
					onCancel: function(event, queueId, fileObj, data) {
						if (settings.onCancel) {
							settings.onCancel(event, queueId, fileObj, data, settings.delUrl);
						} else {
							var result = false;
							var fileId = $('#' + id + queueId).attr('fileId');
							if (fileId != undefined) {
								var opts = {
									url: settings.delUrl,
									dataobj: {
										"fileId": fileId
									},
									redirectUrl: true,
									requestType: "GET",
									successCallback: function(data) { // 成功回调
										result = true;
										if (settings.displayType) {
											$('#logo' + queueId).remove();
										}
										return result;
									},
									errorCallback: function() { // 失败回调
										result = false;
										return result;
									}
								};
								crossDomainAjax(opts);
							}

						}
					}
				});

				if (settings.customShow) {
					settings.customShow(settings.initFile);
				} else {
					initFiles();
				}

				function initFiles() {
					var files = settings.initFile;
					if (settings.simUploadLimit == 1 && !(files instanceof Array)) {
						if (settings.displayType) {
							uploadifyDefaultImg(files);
						} else if(settings.isHeadPortrait){
							// 上传头像、logo
							settings.multi = false;
							$("#" + settings.headPortraitId).attr("src", settings.showUrl + "/" + files[settings.param[0]]);
							var _filePath = document.createElement("input");
							_filePath.type = "hidden";
							_filePath.id = settings.headPortraitId + "_fileId";
							_filePath.value = files[settings.param[0]];
							$('#' + settings.queueID).append(_filePath);
						} else {
							uploadifyDefault(files);
						}
					} else {
						for (var i = 0; i < files.length; i++) {
							if (settings.displayType) {
								uploadifyDefaultImg(files[i]);
							} else {
								uploadifyDefault(files[i]);
							}
						}
					}
				}
			});

			function uploadifyDefaultImg(item) {
				var TBID = "wdyw" + newGuid().toUpperCase();
				var tbDiv = document.createElement('div');
				tbDiv.className = 'wdUploadify-dis';
				tbDiv.id = "logo" + TBID;
				if (settings.displayPic) {
					if (settings.displayPic.picWidth) {
						tbDiv.style.width = settings.displayPic.picWidth + "px";
					}
					if (settings.displayPic.picHeight) {
						tbDiv.style.height = settings.displayPic.picHeight + "px";
						tbDiv.style.lineHeight = (settings.displayPic.picHeight - 8) + "px";
					}
				}
				var tbShowImg = document.createElement('img');
				tbShowImg.src = settings.showUrl + item[settings.param[0]];
				var tbcancelDiv = document.createElement('div');
				tbcancelDiv.className = 'wdUploadify-cancel';
				tbcancelDiv.style.backgroundColor = 'rgba(0,0,0,0.6)';
				var tbA = document.createElement('a');
				tbA.href = "javascript:jQuery('#" + id + "').uploadifyCancel('" + TBID + "')";
				var tbDelImg = document.createElement('img');
				tbDelImg.src = settings.cancelImg;
				tbA.appendChild(tbDelImg);
				tbcancelDiv.appendChild(tbA);
				tbDiv.appendChild(tbShowImg);
				tbDiv.appendChild(tbcancelDiv);
//				$('#' + id + '_disArea').append(tbDiv);
				$('#' + settings.queueID).append(tbDiv);

				$(tbDiv).hover(
					function() {
						$(tbcancelDiv).show();
					},
					function() {
						$(tbcancelDiv).hide();
					});
			}

			function uploadifyDefault(item) {
				var MYID;
				if (item.id != undefined) {
					MyID = item[settings.param[5]];
				} else {
					MYID = "wdyw" + newGuid().toUpperCase();
				}
				var zyDiv = document.createElement('div');
				zyDiv.id = id + MYID;
				zyDiv.className = "uploadifyQueueItem completed";
				zyDiv.fileId = item[settings.param[3]];
				var cancelDiv = document.createElement('div');
				cancelDiv.className = 'cancel';
				var cancelA = document.createElement('a');
				cancelA.href = "javascript:jQuery('#" + id + "').uploadifyCancel('" + MYID + "')";
				var cancelImg = document.createElement('img');
				cancelImg.border = "0";
				cancelImg.src = settings.cancelImg;
				cancelA.appendChild(cancelImg);
				cancelDiv.appendChild(cancelA);
				var nameSpan = document.createElement('span');
				nameSpan.className = "fileName";
				nameSpan.innerHTML = item[settings.param[1]];
				var percentageSpan = document.createElement('span');
				percentageSpan.className = "percentage";
				percentageSpan.innerHTML = " - 完成";
				zyDiv.appendChild(cancelDiv);
				zyDiv.appendChild(nameSpan);
				zyDiv.appendChild(percentageSpan);
				$('#' + settings.queueID).append(zyDiv);
			}

			this.getUploadifyFiles = function() {
				return settings.paramArr;
			};
			return this;
		}
	});
})(jQuery);