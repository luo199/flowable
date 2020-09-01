/*
**模板定义
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	var businessId = window.common.getQueryString("businessId");
	$("#textTemplateForm input[name='businessId']").val(businessId);
	$("#queryTextTemplateForm input[name='businessId']").val(businessId);
	window.common.getNiceScroll("wrapper");
	
	$('#textTemplateSubmitButton').bind("click",function(){
		$("#formModelPage").val(1);
	});
	$('#textTemplateSubmitButton').trigger("click");
	
	listTasksByBusiness();
});
function listTasksByBusiness(){//通过业务ID查询模板列表
	var textTemplateObj= $("#queryTextTemplateForm").serializeObject();
	var _url = window.top.contextPath + window.portPath.queryTemplateList;
	var queryForm = $("#queryTextTemplateForm").serializeObject();
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("taskListTemplate", "#taskListTable", {taskListData: res.data});
        	/*var opts = {
   				 currentPage : res.data.currentPage + 1,
   				 totalPages:res.data.totalPages,
   				 total:res.data.total,
   				 size:res.data.size,
   				 callBack : templatePageCallBack
   		 	}
   		   $.huasiBackPagination.drawPagination(opts);*/
        }
    });
}
function templatePageCallBack(opts){//分页跳转
	$("#templatePage").val(opts.currentPage);
	$('#queryTextTemplateForm').trigger("submit");
	return false ;
}
var textTemplateLayerDialog = null;
function showTextTemplateEditDialog(flag){//打开编辑弹窗
	var _title = '';
	if(flag == 'add'){
		_title = '新增';
		$(":input","#textTemplateForm").not(":button,:submit,:reset").val("");
		var businessId = window.common.getQueryString("businessId");
		$("#textTemplateForm input[name='businessId']").val(businessId);
	}else if(flag == 'update'){
		$(":input","#textTemplateForm").not(":button,:submit,:reset,:hidden").val("");
		_title = '修改';
		var _id = $("#selectedTextTemplateId").val();
		if(!_id || _id == ""){
			layer.msg('请选择一条数据进行修改！',{icon: 5});
			return;
		}
		openTextTemplateDetails();
	}
	textTemplateLayerDialog = layer.open({
		title : _title,
		type : 1,
		content : $("#textTemplateFormDialog")
	});
}
function closeTextTemplateEditDialog(){//关闭编辑弹窗
	if(textTemplateLayerDialog){
		layer.close(textTemplateLayerDialog);
	}else{
		layer.closeAll();
	}
}
function submitTextTemplateData() {//提交表单数据
	$("#textTemplateForm").validate({
		rules:{
			//businessName:"required"
		},
		messages:{
			//businessName:"必填项！"
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var textTemplateObj= $("#textTemplateForm").serialize();
			var textTemplateJSON= JSON.stringify(textTemplateObj);
			var _url = window.top.contextPath;
			if($("#textTemplateForm").serializeObject().templateId){
				_url += window.portPath.updateTextTemplate;
			}else{
				_url += window.portPath.saveTextTemplate;
			}
			/*var fileData = $("#orgfileUpload")[0].files[0];  
			var formData = new FormData();
			formData.append("file", fileData);
			var textTemplateblobJSON = new Blob([textTemplateJSON], {type: 'application/json'});
			formData.append("textTemplate", textTemplateblobJSON);*/
			window.common.networkConnect(_url, "POST", window.conType.conTyForm, textTemplateObj, function(res) {
	            if (res.exchangeStatus == 1) {
	            	layer.msg('操作成功',{icon: 1});
		            layer.close(textTemplateLayerDialog);
		            getTextTemplateByBusinessId();
	            }else{
	            	layer.msg(res.message,{icon: 2})
	            }
	        });
		}
	});
}
function uploadTemplateFile(templateId, index){//上传模板
	var fileData = $("#orgfileUpload" + index)[0].files[0];
	var formData = new FormData();
	formData.append("file", fileData);
	formData.append("id", templateId);
	var _url = window.top.contextPath + window.portPath.saveTextTemplateContent;
	window.common.networkConnect(_url, "POST", false, formData, function(res) {
        if (res.exchangeStatus == 1) {
        	layer.msg('上传成功',{icon: 1});
        	getTextTemplateByBusinessId();
        }else{
        	layer.msg(res.message,{icon: 2})
        }
    });
}
function downloadFile(templateId){//下载模板
	var params = "id=" + templateId;
	var _url = window.top.contextPath + window.portPath.getTextTemplateContent;
	/*window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		var blob = new Blob([res], {type: "application/octet-stream"});
		var a = document.createElement("a");  // 组装a标签
		a.download = new Date().getTime() + ".doc";// 设置下载文件名
		a.target   = '_blank';
		a.href = URL.createObjectURL(blob);
		document.body.appendChild(a); 
		a.click(); 
		a.remove();
    });*/
	window.location.href = _url + "?id=" + templateId;
}
function toTextTemplateBookmark(){//配置书签
	var templateId = $("#selectedTextTemplateId").val();
	var templatehasContent = $("#selectedTextTemplatehasContent").val();
	if(templateId && templatehasContent == 'true'){
		window.parent.document.getElementById("businessRelationContianer").setAttribute("src",window.top.contextPath+"/static/page/smartform/admin/textTemplateBookmark.html?templateId="+templateId);
	}else if(templateId && templatehasContent == 'false'){
		layer.msg('请先上传模板');
	}else{
		layer.msg('请选择一条数据配置书签');
	}
}
function removeTextTemplateById(){//根据模板id移除数据
	var _id = $("#selectedTextTemplateId").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var _url = window.top.contextPath + window.portPath.removeTextTemplateById;
		var _id = $("#selectedTextTemplateId").val();
		var params = "id="+_id;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('操作成功',{icon: 1});
	        	getTextTemplateByBusinessId();
	        	$("#selectedTextTemplateId").val("");
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function openTextTemplateDetails(){//打开模板详情
	var _id = $("#selectedTextTemplateId").val();
	var _url = window.top.contextPath + window.portPath.getTextTemplateDetatils;
	var params = "id="+_id;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	for(var key in res.data){
				$("#textTemplateForm input[name='"+key+"'],#textTemplateForm select[name='"+key+"']").val(res.data[key]);
			};
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function updateTemplateSelection(even, id, hasContent){
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedTextTemplateId").val("");
		$("#selectedTextTemplatehasContent").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTemplate']").val("");
		$(even).val("true");
		$("#selectedTextTemplateId").val(id);
		$("#selectedTextTemplatehasContent").val(hasContent);
	}
}
function openFileInput($index, hasContent){
	if(hasContent == 'true'){
		var laryerIndex = layer.confirm('模板已经存在，确认是否重新上传？', {
			  btn: ['取消', '确认']
			}, function(index, layero){
				layer.close(index);
			}, function(index){
				$("#orgfileUpload" + $index)[0].click(function(event){
					event.stopPropagation();
					event.preventDefault();
				});
			});
	}else{
		$("#orgfileUpload" + $index)[0].click();	
	}
}
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器