/*
**意见
*/
$(function(){
	window.common.getNiceScroll(".wrapper");
	window.common.getNiceScroll("#wrapper");
	showUserOftenComment();
	var instanceId =  window.common.getQueryString("instanceId");
	$("#userCommentEditForm input[name='instanceId']").val(instanceId);
	var commentTypeId = window.common.getQueryString("commentTypeId");
	$("#userCommentEditForm input[name='commentTypeId']").val(commentTypeId);
	var commentId = window.common.getQueryString("commentId");
	$("#userCommentEditForm input[name='commentId']").val(commentId);
	var formId = $("#formMainContent>div:visible", window.parent.document).attr("form-id");
	$("#userCommentEditForm input[name='formId']").val(formId);
	if(commentId){
		getCommentDetails(commentId);
	}
	if(window.common.getQueryString("isFirstCommentType") == 'true'){//表单的第一个意见类型，显示意见记录
		$("#commentRecord").show();
	}
});
function getCommentDetails(commentId, belongYear){// 通过意见id和年份获取意见详情
	var _url = window.top.contextPath + window.portPath.getCommentDetailsByIdAndYear;
	var queryForm = "commentId=" + commentId; 
	if(belongYear){
		queryForm += "&belongYear="+belongYear;
	}
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	for(var key in res.data){
				$("#userCommentEditForm input[name='"+key+"'] , #userCommentEditForm textarea[name='"+key+"']").val(res.data[key]);
			}
        }
    });
}
function showUserOftenComment(){//获取用户常用意见列表
	var _url = window.top.contextPath + window.portPath.getOftenCommentSignList;
	var queryForm = "commentSign=1";/**commentSign意见标志 1-用户,2-系统*/
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("userOftenCommentTemplate", "#userOftenCommentTb", {listData : res.data});
        }
    });
}
function saveComment(){//保存意见
	var paramsObject = $("#userCommentEditForm").serializeObject();
	if(paramsObject.commentId){
		var _url = window.top.contextPath + window.portPath.updateComment;
	}else{
		var _url = window.top.contextPath + window.portPath.saveComment;
	}
	var commentModel = $.param(paramsObject);
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, commentModel, function(res) {
        if (res.exchangeStatus == 1) {
        	parent.layer.closeAll();
        	getCommentFtl(res.data.instanceId, res.data.commentTypeId)
        }
    });
}
function deleteComment(){//通过意见主键id删除意见
	var _commentId = $("#userCommentEditForm input[name='commentId'").val();
	if(!_commentId || _commentId == ""){
		return;
	}
	layer.confirm('确定删除该意见?', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var _url = window.top.contextPath + window.portPath.deleteComment;
		var params = "commentId="+_commentId;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	parent.layer.closeAll();
	        	getCommentFtl($("#userCommentEditForm input[name='instanceId'").val(), $("#userCommentEditForm input[name='commentTypeId'").val())
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function getCommentFtl(instanceId, commentTypeId){//通过实例id和意见类型的id获取该意见类型下的所有意见
	var _url = window.top.contextPath + window.portPath.getCommentFtl;
	var params = "instanceId=" + instanceId + "&commentTypeId=" + commentTypeId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			$("#" + commentTypeId, window.parent.document).replaceWith(res.data[commentTypeId]);  　　　　　　　　　
		}
	});
}
function createOftenComment(){//存为常用语
	var _url = window.top.contextPath + window.portPath.saveAndUpdateComment;
	var oftenComment = "commentSign=1&commentContent="+$("#editOftenCommentContent").val();
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, oftenComment, function(res) {
		if (res.exchangeStatus == 1) {
			showUserOftenComment();
		}else{
        	layer.msg('服务异常', {icon: 2});
        }
	});
}
function updateOftenComment(commentId, $index){//修改常用语
	var _url = window.top.contextPath + window.portPath.saveAndUpdateComment;
	var oftenComment = "commentSign=1&commentContent=" + $("#userOftenCommentTb>div").eq($index).find("input[name='oftenCommentInput']").val() + "&oftenCommentId=" + commentId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, oftenComment, function(res) {
		if (res.exchangeStatus == 1) {
			showUserOftenComment();
		}else{
        	layer.msg('服务异常', {icon: 2});
        }
	});
}
function showEditOftenComment(i){//显示编辑常用语
	hideEditOftenComment();
	$("#userOftenCommentTb>div").eq(i).find("input[name='oftenCommentInput']").removeClass("noneDisplay");
	$("#userOftenCommentTb>div").eq(i).find("span[data-name='oftenCommentSpan']").addClass("noneDisplay");
	$("#userOftenCommentTb>div").eq(i).find("div[data-name='hideEditBtn']").addClass("noneDisplay");
	$("#userOftenCommentTb>div").eq(i).find("div[data-name='showEditBtn']").removeClass("noneDisplay");
}
function hideEditOftenComment(){//隐藏编辑常用语
	$("#userOftenCommentTb input[name='oftenCommentInput']").addClass("noneDisplay");
	$("#userOftenCommentTb span[data-name='oftenCommentSpan']").removeClass("noneDisplay");
	$("#userOftenCommentTb div[data-name='hideEditBtn']").removeClass("noneDisplay");
	$("#userOftenCommentTb div[data-name='showEditBtn']").addClass("noneDisplay");
}
function deleteOftenComment(commentId, $index){//通过常用语id删除常用语
	var _url = window.top.contextPath + window.portPath.delCommentByoftenCommentId;
	var params = "oftenCommentId="+commentId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			//$("#userOftenCommentTb>div").eq($index).remove();
			showUserOftenComment();
			layer.msg("常用语删除成功！", {icon: 1});
		}else{
        	layer.msg(res.message,{icon: 2});
        }
	});
}
function sortOftenComment(prevCommentId, lastCommentId){//常用语排序，需传两个常用语的id
	var _url = window.top.contextPath + window.portPath.sortCommentDetailsById;
	var params = "oftenCommentIdA="+prevCommentId + "&oftenCommentIdB=" + lastCommentId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			showUserOftenComment();
		}else{
        	layer.msg(res.message,{icon: 2});
        }
	});
}
function appendComment(content){//双击常用语成为填写意见
	$("#editOftenCommentContent").val($("#editOftenCommentContent").val() + content);
}