/*系统常用语*/

$(function(){
	window.common.getNiceScroll("#wrapper");
	submitFormData();
	$('#usefulExpressForm').on('submit',function(){
		getsystemList();
		return false; //阻止表单默认提交
	})
});
var systemListData = [];
function getsystemList(){// 获取常用语的数据
	var _url = window.top.contextPath + window.portPath.getOftenCommentSignList;
	var queryForm = $("#usefulExpressForm").serialize();
    window.common.networkConnect(_url, "POST", window.conType.conTyForm, queryForm, function(res) {
	    if (res.exchangeStatus == 1) {
	    	window.common.drawTemplateHtml("usefulExpressionsTemplate", "#usefulExpressions", {listData: res.data});
	    	systemListData = res.data;
	    }else{
	        layer.msg(res.message,{icon: 2});
	    }
	});
}
var usefulExpressionsFormDialog = null;
function showEditDialog(flag){//打开编辑弹窗
	var _title = '';
	if(flag == 'add'){
		_title = '新增';
		$(':input','#usefulExpressionsForm').not(':button,:submit,:reset,:hidden').val('');
	}else if(flag == 'update'){
		_title = '修改';
		var _id = $("#selectedusefulDefine").val();
		if(!_id || _id == ""){
			layer.msg('请选择一条数据进行修改！',{icon: 5});
			return;
		}
		getusefulExpressionsDetails();
	}
	usefulExpressionsFormDialog = layer.open({
		title : _title,
		type : 1,
		content : $("#usefulExpressionsFormDialog")
	});
}
function getusefulExpressionsDetails(){//根据业务id获取信息
	
	var _id = $("#selectedusefulDefine").val();
	var _url = window.top.contextPath + window.portPath.getCommentByoftenCommentId;
	var params = "oftenCommentId="+_id;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
    if (res.exchangeStatus == 1) {
      for(var key in res.data){
        		$("#usefulExpressionsForm input[name='"+key+"']").val(res.data[key]);
        	}
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}

function updateSelection(even, id){//更新选择状态
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedusefulDefine").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTest']").val("");
		$(even).val("true");
		$("#selectedusefulDefine").val(id);
	}
}
function submitFormData() {//提交表单数据
	$("#usefulExpressionsForm").validate({
		rules:{
			usefulExpressionName:"required",
		},
		messages:{
			usefulExpressionName:"必填项！",
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var paramsObject = $("#usefulExpressionsForm").serializeObject();
			var _url = window.top.contextPath + window.portPath.saveAndUpdateComment;
            var params = $.param(paramsObject);
			window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	           if (res.exchangeStatus == 1) {
	            	layer.msg('操作成功',{icon: 1});
	                layer.close(usefulExpressionsFormDialog);
	                $("#usefulExpressionsForm input[name=oftenCommentId]").val("");
	                $("#selectedusefulDefine").val("");
	                getsystemList();
	             }else{
	            	layer.msg(res.message,{icon: 2});
	            }
	        });
			
		}
	});
}

function removeBusinessDefineById(){//根据id移除数据
	var _id = $("#selectedusefulDefine").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var _url = window.top.contextPath + window.portPath.delCommentByoftenCommentId;
		var _id = $("#selectedusefulDefine").val();
		var params = "oftenCommentId="+_id;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('操作成功',{icon: 1});
	        	getsystemList();
	        	$("#selectedusefulDefine").val("");
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function closeEditDialog(){//关闭编辑弹窗
	if(usefulExpressionsFormDialog){
		layer.close(usefulExpressionsFormDialog);
	}else{
		layer.closeAll();
	}
}
function sortList(id,direction){ //箭头排序
	var currentId = id,currentIndex = null,params = {};
	for(var i = 0;i<systemListData.length;i++){
		 if(currentId == systemListData[i].oftenCommentId){
			   currentIndex = i;
		   }
	}
	if(direction == 'top'){
		if(currentIndex == 0){
			layer.msg('已经是第一位了！',{icon: 2});
			return;
		}
		params.oftenCommentIdB =systemListData[currentIndex-1].oftenCommentId; 
	}else{
		if(currentIndex ==systemListData.length-1 ){
			layer.msg('已经是最后一位了！',{icon: 2});
			return;
		}
		params.oftenCommentIdB =systemListData[currentIndex+1].oftenCommentId; 
	}
	params.oftenCommentIdA =currentId; 
    params = $.param(params);
	var _url = window.top.contextPath + window.portPath.sortCommentDetailsById;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	layer.msg('操作成功',{icon: 1});
        	getsystemList();
         }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function _init(){
	getsystemList();
}

_init();
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器