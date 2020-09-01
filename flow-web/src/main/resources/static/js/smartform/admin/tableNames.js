/*关联库表*/
$(function(){
		getBusinessDefineDetailsById(function(businessDefineDetails){
			getTableNames(businessDefineDetails);
		})
		
	});
	function getTableNames(businessDefineDetails){//查询数据库表名
	var _url = window.top.contextPath + window.portPath.queryTableNamesList;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, null, function(res) {
        if (res.exchangeStatus == 1) {
        	var listData = res.data|| [];
        	var _html = '';
        	if(listData.length>0){
        		for(var i=0,lent=listData.length; i<lent; i++){
        			var isChecked = businessDefineDetails.businessTabnames && (businessDefineDetails.businessTabnames.indexOf(listData[i]) != -1) ? "checked" : "";
        			_html += '<label for="'+listData[i]+'" class="hs-checkbox">'+
				    			'<input '+isChecked+' name="businessTabnames" id="'+listData[i]+'" type="checkbox" class="checkbox-original" value="'+listData[i]+'">'+
				    			'<span class="checkbox-input"><span class="checkbox-inner"></span></span>'+
				    			'<span class="checkbox-label">'+listData[i]+'</span>'+
				    		'</label>';
        		}
        	}
        	$('#businessTabnamesTb').html(_html);
        }
    });
}
function getBusinessDefineDetailsById(callback){//根据业务id获取信息
	var businessId = window.common.getQueryString("businessId");
	var _url = window.top.contextPath + window.portPath.getBusinessDefineDetatils;
	var params = "id=" + businessId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback){
        		for(var key in res.data){
    				$("#tableNamesForm input[name='"+key+"']").val(res.data[key]);
    			}
        		callback(res.data)
        	}
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function submitTableNamesForm() {//提交表单数据
	var paramsObject = $("#tableNamesForm").serializeObject();debugger
	if(Array.isArray(paramsObject.businessTabnames) && paramsObject.businessTabnames.length>0){
		paramsObject.businessTabnames = paramsObject.businessTabnames.join(",");
	}
	var _url = window.top.contextPath + window.portPath.updateBusinessDefine;
	var params = $.param(paramsObject);
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	layer.msg('操作成功',{icon: 1});
        }else{
        	layer.msg(res.message,{icon: 2})
        }
    });
}