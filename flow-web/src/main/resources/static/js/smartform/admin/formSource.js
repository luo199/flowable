var ckEditor = null;
var isSourceModel = false;
$(function(){
	ckEditor = CKEDITOR.replace("ckeditor", { allowedContent: true, height: 400});//,readOnly:true
	ckEditor.on("instanceReady",function(ev){
		var _this = this;
		getFormSource(function(content){
			_this.setData(content);
			_this.document.on("mouseup", openEditor);
		});
		_this.on("afterCommandExec",function(even){
			if(even.data.name == 'source'){
				isSourceModel = !isSourceModel;
			}
			if(!isSourceModel){//非源码模式下
				setTimeout(function(){
					_this.document.on("mouseup", openEditor);
				}, 300);
			}
		});
		ckEditor.addCommand("save", { modes: {wysiwyg: 0,source: 1}, exec:
			function (editor) {
				submitEditorContent(editor);
		 	}
		});
	});
	submitFormElementsData();
	window.common.getNiceScroll(".wrapper");
});
function submitFormElementsData() {//提交表单数据
	$("#formElementsForm").validate({
		rules:{
			colEgName:"required",
			colChName:"required",
			colType:"required",
			eleEgName:"required",
			eleType:"required",
			eleColTable:"required",
			primaryKey:"required",
			instanceKey:"required"
		},
		messages:{
			colEgName:"必填项！",
			colChName:"必填项！",
			colType:"必填项！",
			eleEgName:"必填项！",
			eleType:"必填项！",
			eleColTable:"必填项！",
			primaryKey:"必填项！",
			instanceKey:"必填项！"
		},
		onsubmit:true,
		errorPlacement: function(error, element) {
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var params = $("#formElementsForm").serializeObject();
			var _url = window.top.contextPath;
			if(params.eleColId){
				_url += window.portPath.updateFormElements;
			}else{
				_url += window.portPath.saveFormElements;
			}
			var params = $("#formElementsForm").serialize();
			window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
				if(res.exchangeStatus == 1){
	        		$("#formElementsForm").hide();
	        		getFormElementsListByFormId();
	            	layer.msg('操作成功',{icon: 1});
			    }else{
				    layer.msg("操作失败");
				}
		    });
		}
	});
}
function getFormSource(callback){//获取编辑器的内容
	var _url = window.top.contextPath + window.portPath.getFormSource;
	var formId = window.common.getQueryString("formId");
	var queryForm = "formId=" + formId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	callback(res.data);
        }else{
		    layer.msg("操作失败", {icon: 2});
		};
    });
}
function submitEditorContent(editor){//保存编辑器内容
	var $html = editor.getData();
	var _url = window.top.contextPath + window.portPath.uploadFormSource;
	var formId = window.common.getQueryString("formId");
	var params = {"formId": formId, "file": $html};
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function(res) {
        if (res.exchangeStatus == 1) {
        	layer.msg("操作成功", {icon: 1});
        }else{
		    layer.msg("操作失败", {icon: 2});
		};
    });
}
function openEditor(){//获取当前编辑器选中的文本
	var  formId = window.common.getQueryString("formId");
	var selectedElement = ckEditor.getSelection().getSelectedElement();
	if(selectedElement && selectedElement.is("input")){
		openFormEditor(formId, selectedElement.getAttribute("name"), selectedElement.getAttribute("type"));
	}else if(selectedElement && selectedElement.is("textarea")){
		openFormEditor(formId, selectedElement.getAttribute("name"), "textarea");
	}else if(selectedElement && selectedElement.is("select")){
		openFormEditor(formId, selectedElement.getAttribute("name"), "select");
	}else{
		return;
	}
}
function openFormEditor(formId, eleEgName,eleType){//传递参数
	var formElementsObj = {"formId": formId, "eleEgName": eleEgName, "eleType": eleType};
	drawFormElements(formElementsObj, 'ckEditor');
	//window.parent.window.frames['rightContianer'].contentWindow.drawFormElements(formElementsObj);
}