/*
**配置书签
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	queryBookmarkList();
	submitBookmarkData();
});
function queryBookmarkList(){//查询书签列表
	var _url = window.top.contextPath + window.portPath.getBookmarkByTemplateId;
	var queryForm = "templateId=" + window.common.getQueryString("templateId");;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("bookmarkTemplate", "#bookmarkTb", {contextPath: window.top.contextPath, bookmarkData: res.data});
        }
    });
}
var bookmarkLayerDialog = null;
function showBookmarkEditDialog(){//显示书签弹窗
	$(":input","#bookmarkForm").not(":button,:submit,:reset,:hidden").val("");
	var _title = '书签配置';
	var _id = $("#selectedBookmarkId").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行修改！',{icon: 5});
		return;
	}
	bookmarkLayerDialog = layer.open({
		title : _title,
		type : 1,
		content : $("#bookmarkFormDialog")
	});
	queryAllFormList(function(){
		getBookmarkDetails();
	});
}
function closeBookmarkEditDialog(){//关闭编辑弹窗
	if(bookmarkLayerDialog){
		layer.close(bookmarkLayerDialog);
	}else{
		layer.closeAll();
	}
}
function getBookmarkDetails(){//根据业务id获取信息
	var _id = $("#selectedBookmarkId").val();
	var _url = window.top.contextPath + window.portPath.getBookmarkDetails;
	var params = "id="+_id;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	for(var key in res.data){
				$("#bookmarkForm input[name='"+key+"'], #bookmarkForm select[name='"+key+"']").val(res.data[key]);
			};
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function submitBookmarkData(){//提交
	$("#bookmarkForm").validate({
		rules:{
			fieldTab:"required",
			fieldCol:"required"
		},
		messages:{
			fieldTab:"必填项！",
			fieldCol:"必填项！"
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var textTemplateBookmark = $("#bookmarkForm").serialize();
			var _url = window.top.contextPath + window.portPath.updateBookmark;
			window.common.networkConnect(_url, "POST", window.conType.conTyForm, textTemplateBookmark, function(res) {
	            if (res.exchangeStatus == 1) {
	            	layer.msg('操作成功',{icon: 1});
	            	layer.close(bookmarkLayerDialog);
	            	queryBookmarkList();
	            }else{
	            	layer.msg(res.message,{icon: 2})
	            }
	        });
		}
	});
}
function queryAllFormList(callback){//查询所有表单定义表
	var _url = window.top.contextPath + window.portPath.queryAllFormList;
	window.common.networkConnect(_url, "GET", window.conType.conTyForm, null, function(res) {
        if (res.exchangeStatus == 1) {
        	drawBookmarkHtml(res.data, 'allFormTemplate', 'allFormTb');
        	callback();
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function queryFormColList(evt){//通过表单id查询字段列表
	var formId = $(evt).find("option:selected").attr("form-id");
	var _url = window.top.contextPath + window.portPath.getFormElementsByFormId;
	var params = "formId=" + formId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	drawBookmarkHtml(res.data, 'formColTemplate', 'formColTb');
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function updateBookmarkSelection(even, id){//选择某项
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedBookmarkId").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxBookmark']").val("");
		$(even).val("true");
		$("#selectedBookmarkId").val(id);
	}
}