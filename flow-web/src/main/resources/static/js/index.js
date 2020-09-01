$(function () {
	$("#mainContainer").load(contextPath + "/static/page/smartform/openForm.html");
});
function logout(){//退出
	top.location.href = window.top.cas_prefix_logout;
}