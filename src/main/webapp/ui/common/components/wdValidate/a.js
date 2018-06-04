var checkDetailArray = ["xingming:required;validType=Length[1,2]", "mima:required;validType=Length[1,6]", "kcjj:required;validType=Length[1,12]", "max:validType=Max[50]", "min:validType=Min[20]", "future:validType=Future", "past:validType=Past", "onlyNumber:validType=OnlyNumber[10]", "isDouble:validType=IsDouble[5,2,3]"];
var wdvalidateBoxrules = {
		rules: {
			"Length": {
				"message": "输入内容不能超过{2}个汉字，{1}个字母或数字",
				"validator": function(value, param) {
					var len = value.replace(/[^\x00-\xff]/g, '**').length;
					param[2] = (param[1] / 2).toString().split('.')[0];
					return len >= param[0] && len <= param[1]
				}
			},
			"Max": {
				"message": "必须输入数字且不能大于{0}",
				"validator": function(value, param) {
					return parseFloat(value) <= parseFloat(param[0])
				}
			},
			"Min": {
				"message": "必须输入数字且不能小于{0}",
				"validator": function(value, param) {
					return parseFloat(value) >= parseFloat(param[0]);
				}
			},
			"Future": {
				"message": "输入的日期必须晚于当前时间",
				"validator": function(value, param) {
					var today = new Date();
					var myDate = Date.parse(value);
					if (today < myDate) {
						return true;
					} else {
						return false;
					}

				}
			},
			"Past": {
				"message": "输入的日期必须早于当前时间_en_US",
				"validator": function(value) {
					var today = new Date();
					var myDate = Date.parse(value);
					if (today > myDate) {
						return true;
					} else {
						return false;
					}
				}
			},
			"OnlyNumber": {
				"message": "请输入{0}位的正整数",
				"validator": function(value, param) {
					var reg = new RegExp('^(^[0-9]*[1-9][0-9]*$)*$');
					var len = value.trim().length;
					return reg.test(value) && len <= param[0];
				}
			},
			"IsDouble": {
				"message": "仅允许输入数字，且数字最大长度为{0}位；整数{1}位，小数{2}位",
				"validator": function(value, param) {
					var len = value.trim().length;
					var lenAfter = 0;
					var lenBefore = 0;
					if (value != '') {
						var bool = value.indexOf('.') != -1;
						len = bool ? len - 1 : len;
						lenAfter = bool ? (value.substr(value.indexOf('.') + 1)).trim().length : 0;
						lenBefore = bool ? (value.substr(0, value.indexOf('.'))).trim().length : len;
					}
					return len <= param[0] && /(^-?\d+)(\.\d+)?$|(^-?\d+)$/.test(value) && lenAfter <= param[2] && 　lenBefore <= param[1];
				}
			}


		}
	};
	/*
	 function loadjscssfile(filename, filetype) { 
	  
	    if (filetype == "js") { 
	        var fileref = document.createElement('script'); 
	        fileref.setAttribute("type", "text/javascript"); 
	        fileref.setAttribute("src", filename); 
	    } else if (filetype == "css") { 
	  
	        var fileref = document.createElement('link'); 
	        fileref.setAttribute("rel", "stylesheet"); 
	        fileref.setAttribute("type", "text/css"); 
	        fileref.setAttribute("href", filename); 
	    } 
	    if (typeof fileref != "undefined") { 
	        document.getElementsByTagName("head")[0].appendChild(fileref); 
	    } 
	  
	} 
	  
	loadjscssfile('http://www.sjcx.org/css/base.css', 'css'); 
	loadjscssfile('http://www.sjcx.org/js/html5.js', 'js');
	 * */