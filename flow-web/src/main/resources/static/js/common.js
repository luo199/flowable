/*
**公用方法
*/
window.common = {
    loadScript: function (container, src) {
        var script = document.createElement('script'),
            container = document.getElementById(container);
        script.type = 'text/javascript';
        script.charset = 'UTF-8';
        script.src = src;
        container.parentNode.appendChild(script);
    },
    getNiceScroll: function (container) {//通过container初始化滚动条
        $(container).niceScroll({
            touchbehavior: false,
            cursorcolor: "#ccc",
            cursoropacitymax: 1,
            cursorwidth: 5,
            cursorborder: "none",
            cursorborderradius: "4px",
            background: "none",
            autohidemode: true,
            zindex: 99,
            hidecursordelay: 400, // 设置滚动条淡出的延迟时间（毫秒）
            oneaxismousemode: false,// 当只有水平滚动时可以用鼠标滚轮来滚动，如果设为false则不支持水平滚动，如果设为auto支持双轴滚动
            enablescrollonselection: false, // 当选择文本时激活内容自动滚动
        });
    },
    dateFormat: function (date, format) { //时间格式化
        if (!date) return;
        if (typeof date === "string") {
            var mts = date.match(/(\/Date\((\d+)\)\/)/);
            if (mts && mts.length >= 3) {
                date = parseInt(mts[2]);
            }
        }
        date = new Date(date);
        if (!date || date.toUTCString() == "Invalid Date") {
            return "";
        }

        var map = {
            "M": date.getMonth() + 1, //月份
            "d": date.getDate(), //日
            "h": date.getHours(), //小时
            "m": date.getMinutes(), //分
            "s": date.getSeconds(), //秒
            "q": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds() //毫秒
        };


        format = format.replace(/([yMdhmsqS])+/g, function (all, t) {
            var v = map[t];
            if (v !== undefined) {
                if (all.length > 1) {
                    v = '0' + v;
                    v = v.substr(v.length - 2);
                }
                return v;
            } else if (t === 'y') {
                return (date.getFullYear() + '').substr(4 - all.length);
            }
            return all;
        });
        return format;
    },
    networkConnect: function ($url, $type, $conType, $data, $onComplete) { //封装ajax请求
        if ($conType != false && $data != null && typeof $data === 'object') {
            $data = JSON.stringify($data);
        }
        if ($type == '' || $type == undefined || $type == null) {
            $type = 'POST';
        }
        if ($data && $data != null) {
            $.ajax({
                url: $url,
                type: $type,
                dataType: 'json',
                cache: false,   // 不设置ajax缓存
                processData: false,
                traditional: false,
                contentType: $conType,
                data: $data,
                success: function (data, status) {
                    if ($onComplete) $onComplete(data);
                },
                error: function (xhr, status) {
                    if (xhr.status && xhr.responseJSON && xhr.responseJSON.errorStatus == 2) {
                        top.location.href = window.top.cas_prefix_logout;
                    }
                }
            });
        } else {
            $.ajax({
                url: $url,
                type: $type,
                dataType: 'json',
                cache: false,   // 不设置ajax缓存
                processData: false,
                traditional: false,
                contentType: $conType,
                success: function (data, status) {
                    if ($onComplete) $onComplete(data);
                },
                error: function (xhr, status) {
                    if (xhr.status && xhr.responseJSON && xhr.responseJSON.errorStatus == 2) {
                        top.location.href = window.top.cas_prefix_logout;
                    }
                }
            });
        }

	},
	getQueryString : function(name){//获取地址栏参数
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	},
    drawTemplateHtml:function(templateContainer, showTemplate, data){//绘制模板html
        var _html = template(templateContainer, data);
        $(showTemplate).html(_html);
    }
}
/*-------------jquery 扩展方法----------start-----------------*/
$.fn.serializeObject = function () {//序列化form表单
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] != undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
/*-------------jquery 扩展方法----------end-----------------*/