/*按钮配置*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	var businessId = window.common.getQueryString("businessId");
	$("#buttonOperatorListForm input[name='businessId']").val(businessId)
	showButtonOperatorView(businessId);
});
function showButtonOperatorView(businessId){
	getButtonOperator(businessId, function(boMsg){
		getButtonResource(businessId, function(msMsg){
			window.common.drawTemplateHtml("buttonOperatorListTemplate", "#buttonOperatorListTb", {boMsg: boMsg,msMsg: msMsg});
		});
	});
}
function getButtonResource(businessId, callback){//获取所有的自定义按钮资源
	var _url = window.top.contextPath + window.portPath.getButtonResource;
	var params = "businessId=" + businessId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback) callback(res.data);
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function getButtonOperator(businessId, callback){//获取所有的自定义按钮资源
	var _url = window.top.contextPath + window.portPath.getButtonOperator;
	var params = "businessId=" + businessId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback) callback(res.data);
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function saveButtonOperator(){//保存按钮定义配置
	var _url = window.top.contextPath + window.portPath.saveButtonOperator;
	var params = $("#buttonOperatorListForm").serialize();
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	layer.msg('操作成功',{icon: 1});
        }else{
        	layer.msg('服务异常',{icon: 2});
        }
    });
}