/*我的标签 列表*/
$(function(){
	setTagConHeight();
	window.common.getNiceScroll(".wrapper");
	getTagList();
});

function getTagList(){//获取标签列表
	var _url = window.top.contextPath + window.portPath.getTagList;
	var instanceDisplayQueryForm = $("#listSearchForm").serializeObject();
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, instanceDisplayQueryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	if(res.data){
        		drawTagListHtml(res.data);
        	}
        }
    });
}
function drawTagListHtml(listData){//绘制标签列表数据html
	var _val = $("#tagPageTab").val();
	var $tp = _val == 1 ?  'tagManageTemplate' : 'tagListTemplate';
	if(listData){
		window.common.drawTemplateHtml($tp, "#tagListContianer", {contextPath: window.top.contextPath, listData: listData});
	}
}
function openTag(flag){
	if(flag && flag == 1){
		var url = window.top.contextPath + "/static/page/officialdoc/list/tagManage.html";
		$("#tagPageTab").val(1);
	}else{
		var url = window.top.contextPath + "/static/page/officialdoc/list/tagList.html";
		$("#tagPageTab").val(0);
	}
	$("#tagContainer").load(url);
}
function removeByTagId(tagId){//根据标签id移除数据
	layer.confirm('确认删除？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var _url = window.top.contextPath + window.portPath.removeByTagId;
		var params = "tagId=" + tagId;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('操作成功',{icon: 1});
	        	getTagList();
	        	$("#selectedTagId").val("");
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
var tagLayerDialog = null;
function editTagInfo(flag, tagId){//打开编辑弹窗
	var _title = '';
	if(flag == 'add'){
		_title = '新增';
		$(':input','#tagInfoForm').not(':button,:submit,:reset').val('');
	}else if(flag == 'update'){
		_title = '修改';
		getTagInfoDetails(tagId);
	}
	tagLayerDialog = layer.open({
		title : _title,
		type : 1,
		content : $("#tagInfoFormDialog")
	});
	createTagInfo();
}
function closeTagInfo(){//关闭编辑弹窗
	if(tagLayerDialog){
		layer.close(tagLayerDialog);
	}else{
		layer.closeAll();
	}
}
function createTagInfo(){//操作标签
	$("#tagInfoForm").validate({
		rules:{
			tagNumber: {"required": true,maxlength: 5},
			tagName: {"required": true,maxlength: 5},
		},
		messages:{
			tagNumber: {"required": "必填项！", maxlength: "序号长度不能小于 5 个字"},
			tagName: {"required": "必填项！", maxlength: "名称长度不能小于 5 个字"}
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var paramsObject = $("#tagInfoForm").serializeObject();
			var _url = window.top.contextPath;
			if(paramsObject.tagId){
				_url += window.portPath.updateTagInfo;
			}else{
				_url += window.portPath.createTagInfo;
			}
			var params = $.param(paramsObject);
			window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	            if (res.exchangeStatus == 1) {
	            	layer.msg('操作成功',{icon: 1});
	                layer.close(tagLayerDialog);
	                getTagList();
	            }else{
	            	layer.msg(res.message,{icon: 2})
	            }
	        });
		}
	});
}
function getTagInfoDetails(tagId){//根据id 获取数据
   var params = 'tagId='+tagId;
	var _url = window.top.contextPath + window.portPath.getgetTagMsgByTagId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	for(var key in res.data){
        		$('#tagInfoForm').find('input[name='+key+']').val(res.data[key])
        	}
        }else{
        	layer.msg(res.message,{icon: 2})
        }
    });
}
function setTagConHeight(){//设置标签内容高度
	var wHeight =$("#tagListPage").height();
	var titleHeight =$("#tagListTitle").outerHeight();
	var opsHeight =$("#tagListOps").outerHeight();
	$("#tagListMain").height(wHeight- titleHeight - opsHeight + 'px');
}
$( window ).resize(function() {
	setTagConHeight();
});