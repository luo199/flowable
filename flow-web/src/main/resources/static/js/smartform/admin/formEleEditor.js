$(function(){
	var formId = window.common.getQueryString("formId");
	var centerUrl = window.top.contextPath+"/static/page/smartform/admin/formSource.html?formId="+formId;
	var rightUrl = window.top.contextPath+"/static/page/smartform/admin/eleEditor.html?formId="+formId;
    changContainer(centerUrl, rightUrl);
});
function changContainer(centerUrl, rightUrl){
	 $("#centerPanelContainer").load(centerUrl);
	 $("#rightPanelContainer").load(rightUrl);
}
