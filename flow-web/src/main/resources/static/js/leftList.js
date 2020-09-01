/*左侧菜单*/
$(function () {
    window.common.getNiceScroll("#wrapper");
    getLeftMenuList();
});

function getLeftMenuList() {//获取左侧菜单列表
    var _url = window.top.contextPath + window.portPath.getLeftMenuList;
    console.log("url:");
    console.log(_url);
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
        console.log("res");
        console.log(res);
        if (res.exchangeStatus == 1 && res.data && res.data.length > 0) {
            window.common.drawTemplateHtml("leftMenuTemplate", "#leftMenuContianer", {
                contextPath: window.top.contextPath,
                list: (res.data && res.data.length > 0) ? res.data : []
            });
            //changCenterPage(res.data[0].resourceUrl);
        }
    });
}

function changCenterPage(centerUrl, $index) {//切换中间页面
    if (!centerUrl) {
        return;
    }
    var centerCurrentUrl = $("#centerContianer", window.parent.document).attr("src");
    var toUrl = window.top.contextPath + centerUrl;
    $("#centerContianer", window.parent.document).attr("src", toUrl);
    $("#rightContianer", window.parent.document).hide();
    $("#centerContianer.center-contianer", window.parent.parent.document).removeClass("center-contianer").addClass("center-contianer-all");
    setLeftMenuSelectedStyle($index);
}

function setLeftMenuSelectedStyle($index) {//设置左侧菜单选中的样式
    $index = $index || 0;
    var oldSelImgSrc = $("#leftMenuContianer .menu-item-sel img").attr("src");
    if (oldSelImgSrc) {
        var oldBeforeStr = oldSelImgSrc ? oldSelImgSrc.substring(0, (oldSelImgSrc.lastIndexOf("."))) : '';
        var oldAfterStr = oldSelImgSrc ? oldSelImgSrc.substring((oldSelImgSrc.lastIndexOf(".")), oldSelImgSrc.length) : '';
        var tempOldImgSrc = oldBeforeStr.replace("-sel", "") + oldAfterStr;
        $("#leftMenuContianer .menu-item-sel img").attr("src", tempOldImgSrc);
    }
    $("#leftMenuContianer .menu-item-sel").removeClass("menu-item-sel");
    $("#leftMenuContianer .left-menu-item").eq($index).find(".menu-item").addClass("menu-item-sel");
    var newSelImgSrc = $("#leftMenuContianer .left-menu-item").eq($index).find("img").attr("src");
    if (newSelImgSrc) {
        var newBeforeStr = newSelImgSrc.substring(0, (newSelImgSrc.lastIndexOf(".")));
        var newAfterStr = newSelImgSrc.substring((newSelImgSrc.lastIndexOf(".")), newSelImgSrc.length);
        var tempNewImgSrc = newBeforeStr + "-sel" + newAfterStr;
        $("#leftMenuContianer .left-menu-item").eq($index).find("img").attr("src", tempNewImgSrc);
    }
}