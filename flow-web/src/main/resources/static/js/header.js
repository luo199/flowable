$(function(){
	$("#logo").attr("src", window.top.contextPath+"/img/original/logo.png")
	$("#userName").text(window.top.userName);
	getNavBarList();
	if(window.top.userSex == 1){
		$("#userPhotoImg").attr("src", window.top.contextPath+"/img/original/default-woman.png");
	}else{
		$("#userPhotoImg").attr("src", window.top.contextPath+"/img/original/default-man.png");
	}
});
function getNavBarList(){//获取导航栏菜单列表
	var _url = window.top.contextPath + window.portPath.getNavBarList;
	window.common.networkConnect(_url, "GET", window.conType.conTyForm, null, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("headerNavTemplate", "#headerNavContianer", {list: (res.data && res.data.length>0) ? res.data : []});
        }
    });
}
function headToggle(){//鼠标悬浮显示 改成单击事件 显示/隐藏
	if(window.parent.document.getElementById('headAdmin').style.display == "block"){
		window.parent.document.getElementById('headAdmin').style.display = "none";
	}else{
		window.parent.document.getElementById('headAdmin').style.display = "block";
	}
}