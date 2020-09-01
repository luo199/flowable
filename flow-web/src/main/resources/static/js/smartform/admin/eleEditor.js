$(function(){
	$("#bottomElesTable").height($("#elesContainer").height() - $("#topElesBtn").height() - 32 +'px');
	window.common.getNiceScroll(".wrapper");
	var formId = window.common.getQueryString("formId");
	$("#queryFormElementsForm input[name='formId']").val(formId);
	getFormElementsListByFormId();
	getJdbcTypesList();
});
function getJdbcTypesList(){//查询数据库字段类别
	var _url = window.top.contextPath + window.portPath.queryJdbcTypesList;
	window.common.networkConnect(_url, "GET", window.conType.conTyForm, null, function(res) {
        if (res.exchangeStatus == 1) {
        	var listData = res.data != null ? res.data : [];
        	window.common.drawTemplateHtml("colTypeTemplate", "#colTypeTb", {listData: listData});
        }
    });
}
function drawFormElements(dataInfo, sourceType){
	var formElementsObj = JSON.parse(JSON.stringify(dataInfo));
	var formId = window.common.getQueryString("formId");
	resetFormElements();
	getFormDetailsByFormId(formId, function(formDetails){
		getFormElementsListByFormId(formElementsObj.eleEgName, function(eleDetails){
			var dataObj = {};
			if(eleDetails && eleDetails.eleColId){//修改
				if(sourceType == 'ckEditor'){//编辑器里面的输入框点击编辑表单元素
					dataObj = eleDetails;
				}else{//表单元素列表点击修改表单元素
					dataObj = formElementsObj;
				}
			}else{
				dataObj.eleEgName = formElementsObj.eleEgName;
				dataObj.eleType = formElementsObj.eleType;
				dataObj.formId = formDetails.formId;
				dataObj.businessId = formDetails.businessId;
				dataObj.eleColTable =  formDetails.formEgName;
				dataObj.primaryKey = 2;//是否主键 1是2否
				dataObj.instanceKey = 2;//是否实例键 1是2否
			}
			for(var key in dataObj){
				$("#formElementsForm input[name='"+key+"'], #formElementsForm select[name='"+key+"']").val(dataObj[key]);
			}
			getBusinessDefineDetails(dataObj.businessId, function(res){
				if(res.businessTabnames){
					var listData = res.businessTabnames.split(",");
					window.common.drawTemplateHtml("eleColTableTemplate", "#eleColTableTb", {listData: listData, eleData: dataObj.eleColTable});
				}
			});//通过获取业务定义详情获取可选的数据库表名
			getFiledsByTablename(dataObj.eleColTable, function(){
				$("#formElementsForm select[name='colEgName']").val(dataObj.colEgName);
			});
		});
	});
	$("#formElementsForm").show();
}
function getBusinessDefineDetails(businessId, callback){
	var _url = window.top.contextPath + window.portPath.getBusinessDefineDetatils;
	var params = "id=" + businessId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback){callback(res.data)};
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function getFormElementsListByFormId(eleEgName, callback){//通过表单ID和元素英文名获取表单元素表
	var _url = window.top.contextPath;
	var params = "formId=" +  window.common.getQueryString("formId");
	if(eleEgName){
		_url += window.portPath.getFormElementsByFormIdAndEleEgName;
		params += "&eleEgName=" + eleEgName;
	}else{
		_url += window.portPath.getFormElementsByFormId;
	}
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	var listData = [];
        	if(eleEgName && eleEgName != null){
        		listData.push(res.data);
        	}else{
        		listData = res.data;
        	}
        	if (Object.prototype.toString.call(callback) == '[object Function]') {
        		callback(res.data);
        	}else{
        		window.common.drawTemplateHtml("formElementTbTemplate", "#formElementTb", {list : listData});
        	}
        }
    });
}
function removeFormElementById(){//根据业务id移除数据
	var _data = $("#selectedFormElement").val();
	var eleObj = JSON.parse(_data);
	var _id = eleObj.eleColId;
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var _url = window.top.contextPath + window.portPath.removeFormElements;
		var params = "id=" + _id;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('操作成功',{icon: 1});
	        	getFormElementsListByFormId();
	        	layer.close(index);
	        	$("#selectedFormElement").val("");
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function showEditFormElement(){//显示修改元素信息的表单
	var _data = $("#selectedFormElement").val();
	var eleObj = JSON.parse(_data);
	if(!_data || _data == ""){
		layer.msg('请选择一条数据进行修改！',{icon: 5});
		return;
	}
	drawFormElements(eleObj);
	$("#formElementsForm").show();
	//getFormDefineDetails(eleObj.eleColId);
}
/*function getFormDefineDetails(eleColId){//根据元素id获取信息
	var _url = window.top.contextPath + window.portPath.getFormElementsDetails;
	var params = "id=" + eleColId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	var FormElementsObj = (res.data != null) ? res.data : {};
        	drawFormElements(FormElementsObj);
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}*/
function updateFormElementsSelection(even, eleStr){//更新选择状态
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedFormElement").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxFormElement']").val("");
		$(even).val("true");
		$("#selectedFormElement").val(eleStr);
	}
	var eleObj = JSON.parse(eleStr);
	(eleObj.formId) ? $("#eleEditorRemoveBtn").show() : $("#eleEditorRemoveBtn").hide();
}
function resetFormElements(){//重置表单元素
	var businessId = $("#formElementsForm input[name='businessId']").val();
	var formId = $("#formElementsForm input[name='formId']").val();
	$(":input","#formElementsForm").not(":button,:submit,:reset").val("");
	$("#formElementsForm input[name='businessId']").val(businessId);
	$("#formElementsForm input[name='formId']").val(formId);
}
function getFormDetailsByFormId(id, callback){//根据表单id获取信息
	var _url = window.top.contextPath + window.portPath.getFormDefineDetatils;
	var params = "id=" + id;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback){callback(res.data)};
        }else{
        	layer.msg(res.message,{icon: 2});
        }       
    });
}
function changeColEgName(even){//根据表名查询表字段
	var colEgName = $(even).val();
	$("#formElementsForm select[name='colEgName']").val(colEgName);
}
function getFiledsByTablename(tableName, callback){//根据表名查询表字段
	var _url = window.top.contextPath + window.portPath.getFiledsByTablename;
	var params = "tableNames=" + tableName;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	var listData = res.data || [];
        	window.common.drawTemplateHtml("colEgNameTemplate", "#colEgNameTb", {listData: listData});
        	if(callback){callback(res.data)};
        }else{
        	layer.msg(res.message,{icon: 2});
        }       
    });
}
$(window).resize(function(){
	$("#bottomElesTable").height($("#elesContainer").height() - $("#topElesBtn").height() - 32 +'px');
});