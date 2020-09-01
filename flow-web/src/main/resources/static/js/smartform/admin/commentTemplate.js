/*
**意见类型模板
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	var businessId = window.common.getQueryString("businessId");
	$("#commentTemplateForm input[name='businessId']").val(businessId);
	$("#queryCommentTemplateForm input[name='businessId']").val(businessId);
	$('#queryCommentTemplateForm').on('submit',function(){
		getCommentTemplateByBusinessId();
		return false; //阻止表单默认提交
	})
	$('#commentTemplateSubmitButton').bind("click",function(){
		$("#commentTemplatePage").val(1);
	});
	$('#commentTemplateSubmitButton').trigger("click");
	submitCommentTemplateData();
});
function getCommentTemplateByBusinessId(){//通过业务ID查询模板列表
	var commentTemplateObj= $("#queryCommentTemplateForm").serializeObject();
	var _url = window.top.contextPath + window.portPath.queryCommentTemplateList;
	var queryForm = $("#queryCommentTemplateForm").serializeObject();
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("commentTemplate", "#commentTemplateTb", {commentTemplateData: res.data});
        	var opts = {
   				 currentPage : res.data.currentPage + 1,
   				 totalPages:res.data.totalPages,
   				 total:res.data.total,
   				 size:res.data.size,
   				 callBack : templatePageCallBack
   		 	}
   		   $.huasiBackPagination.drawPagination(opts);
        }
    });
}
function templatePageCallBack(opts){//分页跳转
	$("#commentTemplatePage").val(opts.currentPage);
	$('#queryCommentTemplateForm').trigger("submit");
	return false ;
}
var commentTemplateLayerDialog = null;
function showCommentTemplateEditDialog(flag){//打开编辑弹窗
	var _title = '';
	if(flag == 'add'){
		_title = '新增';
		$(":input","#commentTemplateForm").not(":button,:submit,:reset").val("");
		var businessId = window.common.getQueryString("businessId");
		$("#commentTemplateForm input[name='businessId']").val(businessId);
	}else if(flag == 'update'){
		$(":input","#commentTemplateForm").not(":button,:submit,:reset,:hidden").val("");
		_title = '修改';
		var _id = $("#selectedCommentTemplateId").val();
		if(!_id || _id == ""){
			layer.msg('请选择一条数据进行修改！',{icon: 5});
			return;
		}
		openCommentTemplateDetails();
	}
	commentTemplateLayerDialog = layer.open({
		title : _title,
		type : 1,
		content : $("#commentTemplateFormDialog")
	});
}
function closeCommentTemplateEditDialog(){//关闭编辑弹窗
	if(commentTemplateLayerDialog){
		layer.close(commentTemplateLayerDialog);
	}else{
		layer.closeAll();
	}
}
function submitCommentTemplateData() {//提交表单数据
	$("#commentTemplateForm").validate({
		rules:{
			commentTypeChName:"required",
			commentTypeEgName:"required",
		},
		messages:{
			commentTypeChName:"必填项！",
			commentTypeEgName:"必填项！"
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var commentTemplateObj= $("#commentTemplateForm").serialize();
			var commentTemplateJSON= JSON.stringify(commentTemplateObj);
			var _url = window.top.contextPath;
			if($("#commentTemplateForm").serializeObject().commentTypeId){
				_url += window.portPath.updateCommentTemplate;
			}else{
				_url += window.portPath.saveCommentTemplate;
			}
			window.common.networkConnect(_url, "POST", window.conType.conTyForm, commentTemplateObj, function(res) {
	            if (res.exchangeStatus == 1) {
	            	layer.msg('操作成功',{icon: 1});
		            layer.close(commentTemplateLayerDialog);
		            getCommentTemplateByBusinessId();
	            }else{
	            	layer.msg(res.message,{icon: 2})
	            }
	        });
		}
	});
}
function removeCommentTemplateById(){//根据模板id移除数据
	var _id = $("#selectedCommentTemplateId").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var _url = window.top.contextPath + window.portPath.removeCommentTemplateById;
		var _id = $("#selectedCommentTemplateId").val();
		var params = "id="+_id;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('操作成功',{icon: 1});
	        	getCommentTemplateByBusinessId();
	        	$("#selectedCommentTemplateId").val("");
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function openCommentTemplateDetails(){//打开模板详情
	var _id = $("#selectedCommentTemplateId").val();
	var _url = window.top.contextPath + window.portPath.getCommentTemplateDetatils;
	var params = "id="+_id;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	for(var key in res.data){
				$("#commentTemplateForm input[name='"+key+"'],#commentTemplateForm select[name='"+key+"']").val(res.data[key]);
			};
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function updateTemplateSelection(even, id){
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedCommentTemplateId").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTemplate']").val("");
		$(even).val("true");
		$("#selectedCommentTemplateId").val(id);
	}
}
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器