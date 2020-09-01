$(function(){
	getformEleValidListByBusinessId();
	window.common.getNiceScroll(".wrapper");
	var formId = window.common.getQueryString("formId");
	var rightUrl = window.top.contextPath+"/static/page/smartform/admin/elementValid.html?formId="+formId;
	var permissionUrl = window.top.contextPath+"/static/page/smartform/admin/permission.html?formId="+formId;
	 changContainer(rightUrl,permissionUrl);
});

function changContainer(rightUrl,permissionUrl){
	 $(".element-validbox").load(rightUrl);
	 $(".permissionbox").load(permissionUrl);
}

function getformEleValidListByBusinessId(){
	var _url = window.top.contextPath + window.portPath.getFormElementsByFormId;
	var params = "formId=" +  window.common.getQueryString("formId");
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("formElementTbTemplate", "#formElementTb", {list: res.data});
        }
    });
}

function openelementValidbox(elemtId,businessId,formId){//打开验证规则
	 if($(".element-validbox").css("right") == '-500px'){
	        $(".element-validbox").animate({right:'0px'});
	    }
      var SelectArr = $("select")
	  for (var i = 0; i < SelectArr.length; i++) {
	  SelectArr[i].options[0].selected = true; 
	  }
      $("#formElementsForm input:not([type='button'], [type='submit'], [type='hidden'])").val("");
      $('#formElementsForm input[name=businessId]').val(businessId)
      $('#formElementsForm input[name=formId]').val(formId)
      $('#formElementsForm input[name=elementId]').val(elemtId)
      var _url = window.top.contextPath + window.portPath.getEleValidListByElementId;
      var params = "elementId=" + elemtId;
	  window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	for(var key in res.data[0]){
	        		$("#formElementsForm  input[name='"+key+"']").val(res.data[0][key]);
	        		$("#formElementsForm  select[name='"+key+"']").val(res.data[0][key]);
	        	}
	       
	        }
	    });
}

function openpermissionbox(elemtId,businessId,formId){//打开许可
	if ($(".permissionbox").css("right") == '-1000px') {
		$(".permissionbox").animate({ right : '0px' });
	}
	$('#permissionTable input[name=businessId]').val(businessId);
	$('#permissionTable input[name=formId]').val(formId);
	$('#permissionTable input[name=elementId]').val(elemtId);
	var _url = window.top.contextPath + window.portPath.getPermissionByElementId;
	var params = "elementId=" + elemtId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			window.common.drawTemplateHtml("permissionTbTemplate", "#permissionTb", {list: res.data});
		}
	});
}