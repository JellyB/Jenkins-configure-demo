var localIp = "http://192.168.8.245";
var appAddress = localIp + ":8080/ptxtgl/"; //前端地址127.0.0.1 、192.168.253.1
var requestAddressAction =  localIp + ":8080/ptxtgl"; // 开发后台测试环境http://192.168.6.158:9001/ptxtgl  / http://172.16.3.114:8084/wdcloud-ptxtgl
var requestAddressFile = "http://192.168.6.100/"; //文件服务
var logoutAddress = localIp + ":8083/admin-login/logout";  //退出地址
var requestType = "post"; //请求类型 app原型；request系统
var mainPageTemplateId = "wd_templateId"; // 菜单切换模板id
var indexAddress=appAddress+"app/jsp/index.html"//本首页地址
var loginAddress = localIp + ":8083/admin-login/app/jsp/main/login.html";//登录地址
$(function() {
    /******************************************************* 获取提示旧版本信息，使用时将注释打开start ************************************/
    //gettipmsg();
    /******************************************************* 获取提示旧版本信息，使用时将注释打开end ************************************/
});
/******************************************************* 获取提示旧版本信息start ************************************/
// 获取提示信息old
function gettipmsg() {
    var language = getLanguageFromCookie("language");
    var json = "{";
    jQuery.i18n.properties({ //加载资浏览器语言对应的资源文件
        name: "common_tip", //资源文件名称
        path: appAddress + "app/i18n/common/", //资源文件路径
        language: language, //语言类型
        mode: 'map', //用Map的方式使用资源文件中的值
        callback: function() { //加载成功后设置显示内容
            for (var key in $.i18n.map) {
                var value = $.i18n.map[key];
                json = json + "'" + key + "':" + "'" + value + "',"
            }
            json = json.substring(0, json.length - 1) + "}";
            if (localStorage.getItem("tip") == null) {
                localStorage.setItem("tip", json);
            } else {
                var tmpjson = eval("(" + json + ")"); // 实时数据
                var tmpstor = eval("(" + localStorage.getItem("tip") + ")"); // 页面缓存

                if (parseInt(tmpjson.string_version) > parseInt(tmpstor.string_version)) {
                    localStorage.setItem("tip", json);
                }
            }
        }
    });
}
// 根据key加载提示信息old
function getTipInfo(key) {
    if (!localStorage.getItem("tip")) gettipmsg();
    var json = eval("(" + localStorage.getItem("tip") + ")");
    return json[key];
}
/******************************************************* 获取提示旧版本信息end ************************************/
/******************************************************* 获取提示新版本信息start ************************************/
// 提示信息路径，回调方法，是否为后台返回方式（可为空）
function getTipMsgFromJs(url, fn, actionFlg) {
    if ($("#wd-tip").length == 0) {
        var t = document.createElement('script');
        var tid = "wd-tip";
        t.src = appAddress + "app/msg/" + url; // 测试地址
        t.type = 'text/javascript';

        //if (actionFlg) tid = "wd-actionTip";

        t.id = tid;
        document.getElementsByTagName('head')[0].appendChild(t);
    }
    t.onload = function() {
        if (fn) fn.call();
    };
    // ie8处理
    t.onreadystatechange = function() {
        if (getBrowserVersionIE8())
            if (fn) fn.call();
    }
}
/******************************************************* 获取提示新版本信息end ************************************/
// 判断浏览器版本是否为ie8
function getBrowserVersionIE8(flg) {
    var browser = navigator.appName;
    var b_version = navigator.appVersion;
    var version = b_version.split(";");
    var trim_Version = version[1].replace(/[ ]/g, "");
    if (browser == "Microsoft Internet Explorer" && trim_Version.toUpperCase().split('.')[0] == "MSIE8") {
        return true;
    } else {
        return false;
    }
}
// 页面跳转方法
// openType打开方式
// targetId目标页面id
// boxId模板id
// headjsurl动态引用json地址
function pageChange(targetId, openType, headjsurl, parameters) {
    var jsarray = "";
    var cssarray = "";
    var url = "";
    var fn = "";
    $.ajax({
        type: "get",
        url: appAddress + headjsurl + "?time=" + new Date().getTime(),
        dataType: "json",
        success: function(data) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].pageid == targetId) {
                    cssarray = data[i].cssurl;
                    url = data[i].pageurl;
                    fn = data[i].pagefun;
                    jsarray = data[i].jsurlarray;
                    break;
                }
            }
            switch (openType) {
                case "template":
                    loadHeadJs(jsarray, cssarray, function() {
                        getTemplate(url, mainPageTemplateId, function() {
                            if (fn != "") {
                                if (parameters) eval(fn).call('',parameters);
                                else eval(fn).apply();
                            }
                        });
                    });
                    break;
                case "open":
                    window.open(data[i].pageurl);
                    break;
                case "location":
                    window.location = data[i].pageurl;
                    break;
            }
        }
    });
}
// 动态加载css
// url需加载css
// cssarray需要加载的集合
// num当前需要加载的位置
function addCss(url, cssarray, num) {
    // 处理重复加载
    if (document.getElementById(url) == null) {
        var t = document.createElement('link');
        t.href = appAddress + url; // 测试地址
        t.rel = 'stylesheet';
        t.className = "wd-headCss";
        t.id = url;
        document.getElementsByTagName('head')[0].appendChild(t);
    }
    num = num + 1;

    if (cssarray[num]) addCss(cssarray[num], cssarray, num);
}

// 动态加载js
// url需加载js
// jsarray需要加载的集合
// num当前需要加载的位置
// fn回调方法
function addJs(jsobj, jsarray, num, fn) {
    // 处理重复加载
    if (document.getElementById(jsobj.jsurl) == null) {
        var t = document.createElement('script');
        t.src = appAddress + jsobj.jsurl + "?version=" + jsobj.version;
        t.type = 'text/javascript';
        t.className = "wd-headJs";
        t.id = jsobj.jsurl;
        document.getElementsByTagName('head')[0].appendChild(t);
        num = num + 1;
        if (jsarray[num]) {
            t.onload = function() {
                addJs(jsarray[num], jsarray, num, fn);
            };
            // ie8处理
            t.onreadystatechange = function() {
                if (getBrowserVersionIE8())
                    addJs(jsarray[num], jsarray, num, fn);
            }
        } else {
            t.onload = function() {
                if (fn) fn.call();
            };
        }
    } else {
        num = num + 1;
        if (jsarray[num]) {
            addJs(jsarray[num], jsarray, num, fn);
        } else {
            try {
                var browser = navigator.appName;
                var b_version = navigator.appVersion;
                var version = b_version.split(";");
                var trim_Version = version[1].replace(/[ ]/g, "");
                if (trim_Version.toUpperCase().split('.')[0] != "MSIE8") {
                    if (fn) fn.call();
                }
            } catch (e) {
                if (fn) fn.call();
            }
        }
    }
}

// 动态加载js对外接口
// jsurl需加载js文件集合，‘，’分割
// cssurl需要加载css文件集合，‘，’分割
// fn回调方法
function loadHeadJs(jsurl, cssurl, fn) {
    // css处理
    if (cssurl != "") {
        var cssarray = cssurl.split(',');
        addCss(cssarray[0], cssarray, 0);
    }
    // js处理
    if (jsurl.length != 0) {
        addJs(jsurl[0], jsurl, 0, fn);
        if (getBrowserVersionIE8())
            if (fn) fn.call();
    } else {
        if (fn) fn.call();
    }
}
// 获取服务端语言类型，并设置本地cookie
function getLanguageFromCookie(name) {
    var language = getCookie(name);
    if (!language) {
        language = "zh";

        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(language) + ";expires=" + exp.toGMTString();
    }
    return language;
}
// 根据cookie名获取值
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return arr[2];
    else
        return null;
}
// 国际化处理,新版本不再需要国际化部分old
function loadProperties(ractive, pagename, language) {
    jQuery.i18n.properties({ //加载资浏览器语言对应的资源文件
        name: pagename, //资源文件名称
        path: appAddress + "app/i18n/demo/", //资源文件路径
        language: language, //语言类型
        mode: 'map', //用Map的方式使用资源文件中的值
        callback: function() { //加载成功后设置显示内容
            for (var key in $.i18n.map) {
                var value = $.i18n.map[key];
                ractive.set(key, value);
            }
            // 处理title
            var index = location.pathname.lastIndexOf("/");
            var page = location.pathname.substring(index + 1).split(".")[0];
            var key = "i18n_" + page + "_title";
            document.title = $.i18n.prop(key);
        }
    });
}
//获取页面参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
/**
 * Description: 加载缓存
 *    后台返回值格式：{"info":[{"codeclass":"XBDM","version":0,"data":[{"code":"1","codedesc":"男"},{"code":"2","codedesc":"女"}]},{"codeclass":"XXDM","version":0,"data":[{"code":"1","codedesc":"A"},{"code":"2","codedesc":"B"}]}]}
 * @param codeclass_arr 将要加载至缓存的codeclass代码组织成数组形式参数
 */
function setCodeMap(codeclass_arr, callback) {
    var url = requestAddressAction + "/code/getCodeMapByCodeClassVersion";
    var param_json = {};
    for (var j = 0; j < codeclass_arr.length; j++) {
        param_json[codeclass_arr[j]] = getCodeClassVersion(codeclass_arr[j]);
    }
    var param_str = JSON.stringify(param_json);
    $.ajax({
        type: "POST",
        url: url,
        data: "clv=" + param_str,
        dataType: "json",
        success: function(data) {
            if (data.info != undefined && data.info != null && data.info.length > 0) {
                for (var i = 0; i < data.info.length; i++) {
                    var codeClass = data.info[i].codeclass;
                    delete data.info[i]["codeclass"];
                    store.set(codeClass, "{\"data\":" + JSON.stringify(data.info[i].data) + "}");
                }
            }
            if (callback != undefined && callback != null) {
                callback();
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {

        }
    });
}

/**
 * Description:获取codeclass当前缓存版本信息
 * @param codeclass
 * @returns
 */
function getCodeClassVersion(codeclass) {
    var codeclass_json = store.get(codeclass);

    if (codeclass_json == undefined) {
        return -1;
    }
    if (typeof codeclass_json == 'string') {
        codeclass_json = eval("(" + codeclass_json + ")");
    }
    if (codeclass_json.data.length == 0) {
        return -1;
    }

    return codeclass_json.version;
}
//生成32位随机码
function newGuid() {
    var guid = "";
    for (var i = 1; i <= 32; i++) {
        var n = Math.floor(Math.random() * 16.0).toString(16);
        guid += n;
    }
    return guid;
}
// 返回顶部
function goTop() {
    $("body,html").animate({
        scrollTop: 0
    }, 500);
    return false;
}
// 共通请求方法(C端)
// url 原型地址
// requesturl 真实开发地址
// dataobj 参数
// requestType请求类型,无特殊情况此值传null
// waitFlg 是否需要显示等待
// successCallback 成功回调
// errorCallback 失败回调
function crossDomainAjaxForC(url, requesturl, dataobj, specialRequestType, waitFlg, successCallback, errorCallback) {
    var activeEle = document.activeElement;
    if (activeEle.nodeName != "BODY")
        activeEle.disabled = true;
    var ajaxtype = "";
    var ajaxurl = "";
    if (requestType == "app") {
        ajaxurl = appAddress + url;
        ajaxtype = "GET";
    } else {
        ajaxurl = requestAddressAction + requesturl;
        ajaxtype = "POST";
    }
    // 处理特殊请求类型
    if (specialRequestType) ajaxtype = specialRequestType.toUpperCase();
    var temp = new Date().getTime();

    if (requestType == "GET") {
        // 处理参数
        if (dataobj != "") ajaxurl = ajaxurl + "?" + dataobj + "&time=" + temp;
        else ajaxurl = ajaxurl + "?time=" + temp;
        $.ajax({
            type: "GET",
            url: ajaxurl,
            data: {},
            processData: false,
            contentType: false,
            success: function(data) {
                if (successCallback) successCallback.call('', data);
            },
            error: function(mess) {
                if (errorCallback) errorCallback.call('', mess);
            }
        });
    } else {
        if (dataobj == undefined) {
            dataobj = "{[]}";
        }

        var dataobj = JSON.stringify(dataobj);
        $.ajax({
            type: "POST",
            url: ajaxurl,
            async: false,
            dataType: 'json',
            data: dataobj,
            crossDomain: true,
            contentType: "application/json",
            success: function(data) {
                if (successCallback) successCallback.call('', data);
            },
            error: function(mess) {
                if (errorCallback) errorCallback.call('', mess);
            }
        });
    }
}
// 共通请求方法
// url 原型地址
// requesturl 真实开发地址
// dataobj 参数
// requestType请求类型,无特殊情况此值传null
// waitFlg 是否需要显示等待
// successCallback 成功回调
// errorCallback 失败回调
var wd_common_token = false;
var wd_common_url = "";

function crossDomainAjax(opts) {


    var defaults = {
        requesturl: null, // 后台请求地址
        dataobj: "{[]}", // 请求参数
        redirectUrl: false, // 例外请求地址
        requestType: null, // 例外请求类型
        waitFlg: false, // 是否需要加载等待动画
        contentType: "application/x-www-form-urlencoded"
    };
    var opts = $.extend(defaults, opts);
    var ajaxtype = "";
    var ajaxurl = "";
    if (opts.redirectUrl) {
        ajaxurl = opts.url;
    } else if (requestType == "app") {
        ajaxurl = appAddress + opts.url;
        ajaxtype = "GET";
    } else {
        ajaxurl = requestAddressAction + opts.requesturl;
        ajaxtype = "POST";
    }
    // 处理特殊请求类型
    if (opts.requestType) ajaxtype = opts.requestType;
    var temp = new Date().getTime();

    var timeOuteSecode = 10000; // 超时设置10s

    if (wd_common_token == true && wd_common_url == ajaxurl) return;
    wd_common_token = true;
    wd_common_url = ajaxurl;

    ////////////////////////////////////
    //	ajaxtype = "POST";
    //	ajaxurl = "http://172.16.3.218:9101/wd-gzh/gzh/01/100002316/01/demo/test";
    //	requestType = "request";
    //	dataobj = {
    //		"id": "fuck",
    //		"shzt": 1
    //	};
    ////////////////////////////////////
    // 添加等待
    if (opts.waitFlg) {
        createWaitDiv("wait.gif", ajaxurl);
    }

    $.ajax({
        type: ajaxtype,
        dataType: 'json',
        url: ajaxurl + "?time=" + temp,
        data: opts.dataobj,
        timeout: timeOuteSecode,
        contentType: opts.contentType,
        success: function(data) {
            if (opts.waitFlg) removeWaitDiv(ajaxurl);
            if (opts.successCallback) opts.successCallback.call('', data);

            wd_common_token = false;
        },
        error: function(XMLHttpRequest, txtStatus, errorThrown) {
            var urlAccess = XMLHttpRequest.getResponseHeader('urlAccess');
            if(urlAccess=="Forbidden"){
                var top = getTopWinow();
                top.location.href =appAddress+"app/jsp/403.html";
            }
            /*if (opts.waitFlg) removeWaitDiv(ajaxurl);*/
            /*createWaitDiv("error.gif", ajaxurl, function() {
             wd_common_token = false;
             crossDomainAjax(opts)
             });*/
            //if (opts.errorCallback) opts.errorCallback.call();
        },
        complete: function(XMLHttpRequest, status) {
            if (opts.waitFlg) removeWaitDiv(ajaxurl);
            var urlAccess = XMLHttpRequest.getResponseHeader('urlAccess');
            if(urlAccess=="Forbidden"){
                var top = getTopWinow();
                top.location.href =appAddress+"app/jsp/403.html";
            }
            if (status == 'timeout') {
                //alert("超时");
                createWaitDiv("time.gif", ajaxurl, function() {
                    wd_common_token = false;
                    crossDomainAjax(opts)
                });
            }
        }
    });
}
// 创建等待层
function createWaitDiv(imgFlg, id, fn) {
    var waitDiv = document.createElement('div');
    waitDiv.id = id;
    waitDiv.zIndex = 99999;
    waitDiv.style.width = "260px";
    waitDiv.style.height = "260px";
    waitDiv.style.textAlign = "center";
    var item = document.createElement('div');
    item.style.background = "url(" + appAddress + "ui/common/images/" + imgFlg + ")";
    item.style.width = "130px";
    item.style.height = "130px";
    item.style.position = "absolute";
    item.style.top = "50%";
    item.style.left = "50%";
    item.style.marginTop = "-65px";
    item.style.marginLeft = "-65px";
    waitDiv.appendChild(item);
    waitDiv.style.position = "fixed";
    waitDiv.style.left = "50%";
    waitDiv.style.top = "50%";
    waitDiv.style.marginTop = "-130px";
    waitDiv.style.marginLeft = "-130px";
    document.body.appendChild(waitDiv);

    var spanTitle = document.createElement('span');
    switch (imgFlg) {
        case "wait.gif":
            spanTitle.innerHTML = "系统正在拼命的加载中！";
            waitDiv.appendChild(spanTitle);
            break;
        case "time.gif":
            spanTitle.innerHTML = "请求超时了！";
            waitDiv.appendChild(spanTitle);
            var testDiv = document.createElement('span');
            testDiv.innerHTML = "再试一次";
            testDiv.style.cursor = "pointer";
            waitDiv.appendChild(testDiv);
            $(testDiv).bind('click', function() {
                if (fn) fn.call();
                removeWaitDiv(id);
            });
            break;
        case "error.gif":
            spanTitle.innerHTML = "请求出错了了！";
            waitDiv.appendChild(spanTitle);
            var testDiv = document.createElement('span');
            testDiv.innerHTML = "再试一次";
            testDiv.style.cursor = "pointer";
            waitDiv.appendChild(testDiv);
            $(testDiv).bind('click', function() {
                if (fn) fn.call();
                removeWaitDiv(id);
            });
            break;
    }
}
// 移除等待层
function removeWaitDiv(id) {
    if (document.getElementById(id))
        document.body.removeChild(document.getElementById(id));
}
// 加载模板
function getTemplate(url, id, fn) {
    $("#multivaildateTip").hide();
    $.get(appAddress + url, function(template) {
        var ractive = new Ractive({
            el: id,
            template: template,
            oncomplete: function() {
                if (fn) fn.call("", ractive);
            }
        });
    });
}
/**
 * Description: 获取codedesc,未找到对应desc返回undefined
 * @param codeclass
 * @param code
 * @returns
 */
function getCodeDescByCodeClass(codeclass, code) {
    if (!code) return code;
    var codes = code.split(",");
    var codeclass_json = store.get(codeclass);
    if (!codeclass_json) return code;
    if (typeof codeclass_json == 'string') {
        codeclass_json = eval("(" + codeclass_json + ")");
    }
    var code_arr = codeclass_json.data;
    if (codeclass_json.data == undefined) {
        code_arr = codeclass_json;
    }
    var codesdesc = "";
    for (var i = 0; i < code_arr.length; i++) {
        for (var j = 0; j < codes.length; j++) {
            if (code_arr[i].code == codes[j].replace(/(^\s*)|(\s*$)/g, "")) {
                codesdesc += code_arr[i].codedesc + ",";
            }
        }
    }
    if (codesdesc != "") {
        return codesdesc.substring(0, codesdesc.length - 1);
    } else {
        return code;
    }
}
// 记录页面跳转信息
var wd_back = [];

function addurl(obj) {
    if (wd_back.length == 0) {
        wd_back[wd_back.length] = obj;
    } else if (wd_back[wd_back.length - 1].url != obj.url) {
        wd_back[wd_back.length] = obj;
    }
}
// 返回操作
function backurl(boxId) {
    var isTrack = wd_back[wd_back.length - 1].isTrack;
    if (isTrack != undefined && isTrack == false) {
        getTemplate(wd_back[wd_back.length - 1].url, boxId, function() {
            var fnName = wd_back[wd_back.length-1].fnName;
            wd_back.pop(wd_back.length - 1);
            eval(fnName).apply();
        });
    } else {
        getTemplate(wd_back[wd_back.length - 2].url, boxId, function() {
            wd_back.pop(wd_back.length - 1);
            eval(wd_back[wd_back.length - 1].fnName).apply();
        });
    }
}
/**
 * Description: 创建瀑布流泳道
 * @param opts.targetId 添加目标元素ID
 * @param opts.roadWidth 泳道宽度
 * @param opts.isShowScroll 是否显示滚动条
 * @param opts.leftPad 目标元素左边距
 * @param opts.rightPad 目标元素右边距
 * @returns 创建泳道元素数组
 */
function createFallsRoad(opts) {
    var leftPad = parseInt(opts.leftPad);
    var rightPad = parseInt(opts.rightPad);
    var roadWidth = parseInt(opts.roadWidth);
    $('#' + opts.targetId).css("padding-left", leftPad + "px");
    $('#' + opts.targetId).css("padding-right", rightPad + "px");
    var conWidth = $('#' + opts.targetId).outerWidth() - leftPad - rightPad;
    if (opts.isShowScroll == undefined || opts.isShowScroll) {
        conWidth = conWidth - 18;
    }
    var roadNum = parseInt(conWidth / roadWidth);
    var roadSpace;
    if (((conWidth % roadWidth) / (roadNum - 1)) < 10) {
        roadNum = roadNum - 1;
        roadSpace = parseInt((roadWidth + parseInt(conWidth % roadWidth)) / (roadNum - 1));
    } else {
        roadNum = parseInt((conWidth / roadWidth).toString().split('.')[0]);
        roadSpace = ((conWidth % roadWidth) / (roadNum - 1)).toString().split('.')[0];
    }
    //var fallsRoadList = new Array();
    for (var r = 0; r < roadNum; r++) {
        var roadDiv = document.createElement("div");
        roadDiv.className = "pull-left";
        roadDiv.style.width = roadWidth + "px";
        roadDiv.id = "wd-fallsRoad" + r;
        if (r != 0) roadDiv.style.marginLeft = roadSpace + "px";
        $('#' + opts.targetId).append(roadDiv);

        //fallsRoadList.push(roadDiv);
    }

    //return fallsRoadList;
}
/**
 *
 * @param {Object} targetId
 * @param {Object} ele
 */
function setDataFallsRoad(targetId, ele) {
    var objList = $("#" + targetId).children("div[id^='wd-fallsRoad']");
    var minHeight = 0;
    var eleIndex = 0;
    $.each(objList, function(a) {
        if (a == 0) {
            minHeight = $(objList[a]).outerHeight();
        } else {
            if (minHeight > $(objList[a]).outerHeight()) {
                minHeight = $(objList[a]).outerHeight();
                eleIndex = a;
            }
        }
    });
    $(objList[eleIndex]).append(ele);

    if ($(objList[eleIndex]).attr('isuser') == undefined) {
        $(objList[eleIndex]).attr('isuser', '1');
    }
}
/**
 *
 * @param {Object} targetId
 */
function delFallsRoad(targetId) {
    var objList = $("#" + targetId).children("div[id^='wd-fallsRoad']");
    var roadWidth = 0;
    var roadSpace = "";
    $.each(objList, function(a) {
        if ($(objList[a]).attr('isuser') == '1') {
            if ($(objList[a]).outerHeight() == 0) {
                if (a == 0) {
                    roadWidth = $(objList[a]).next().width();
                    roadSpace = $(objList[a]).next().css("margin-left");
                    $(objList[a]).next().css("margin-left", "0px");
                    $(objList[a]).remove();

                } else {
                    roadWidth = $(objList[a]).width();
                    roadSpace = $(objList[a]).css("margin-left");
                    $(objList[a]).remove();
                }


                var id = $("#" + targetId).children(":last").attr('id');
                var curIndex = parseInt(id.substr(12, 1)) + 1;

                var roadDiv = document.createElement("div");
                roadDiv.className = "pull-left";
                roadDiv.style.width = roadWidth + "px";
                roadDiv.id = "wd-fallsRoad" + curIndex;
                roadDiv.style.marginLeft = roadSpace;
                $('#' + targetId).append(roadDiv);
                return false;

            }
        }
    });
}
/**
 * 懒加载请求数据
 * @param {Object} mat_opts
 */
function wdLazyLoadData(mat_opts) {
    var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop()); //浏览器的高度加上滚动条的高度

    if ($(document).height() <= totalheight) { //当文档的高度小于或者等于总的高度的时候，开始动态加载数据
        //加载数据
        crossDomainAjax(mat_opts);
    }
}
/**
 *
 */
String.prototype.lengthb = function() {
    var cArr = this.match(/[^\x00-\xff]/ig);
    return this.length + (cArr == null ? 0 : cArr.length);
};
function searhToggle(){
    $(".hp-search-title").click(function(){
        if($(this).find("a").hasClass("hp-up")){
            $(this).find("a").removeClass("hp-up");
        }else{
            $(this).find("a").addClass("hp-up");
        }
        $(this).next(".hp-search-tab-box").slideToggle("slow");

    })
}

/**
 * ajax请求全局设置，如果没有session或者session失效自动跳转
 * loginAddress: 登录地址
 * indexAddress: 登录后的跳转地址
 */
$.ajaxSetup({
    type: 'POST',
    complete: function(xhr,status) {
        var sessionStatus = xhr.getResponseHeader('sessionstatus');
        var urlAccess = xhr.getResponseHeader('urlAccess');
        if(urlAccess=="Forbidden"){
            var top = getTopWinow();
            top.location.href =appAddress+"app/jsp/403.html";
        }
        if(sessionStatus == 'timeout') {
            var top = getTopWinow();
            top.location.href =loginAddress+"?redirectURL="+ indexAddress;
        }
    }
});

/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * @return 当前页面的顶层窗口对象
 */
function getTopWinow(){
    var p = window;
    while(p != p.parent){
        p = p.parent;
    }
    return p;
}

