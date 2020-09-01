/*
**业务定义
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	submitFormData();
	$('#queryBusinessDefineForm').on('submit',function(){
	    queryBusinessDefineList();
		return false; //阻止表单默认提交
	})
	$('#businessDefineSubmitButton').bind("click",function(){
		$("#businessPage").val(1);
	});
	$('#businessDefineSubmitButton').trigger("click");
});
function queryBusinessDefineList(){//查询业务定义列表
	var _url = window.top.contextPath + window.portPath.queryBusinessDefineList;
	var queryForm = $("#queryBusinessDefineForm").serializeObject();//rows: 20 分页大小,page: 1  当前页
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("businessDefineTemplate", "#businessDefineTb", {listData: res.data});
        	var opts = {
				 currentPage : res.data.currentPage + 1,
				 totalPages:res.data.totalPages,
				 total:res.data.total,
				 size:res.data.size,
				 callBack : pageCallBack
		 	}
		   $.huasiBackPagination.drawPagination(opts);
        }
    });
}
function pageCallBack(opts){//分页跳转
	$("#businessPage").val(opts.currentPage);
	$('#queryBusinessDefineForm').trigger("submit");
	return false ;
};
function openBusinessRelationPage(){//打开业务关联界面
	var businessId = $("#selectedBusinessDefine").val();
	if(!businessId || businessId == ""){
		layer.msg('请选择一条数据！',{icon: 5});
		return;
	}
	window.parent.document.getElementById("centerCoverContianer").style.display = 'block';
	window.parent.document.getElementById("centerCoverContianer").setAttribute("src",window.top.contextPath+"/static/page/smartform/admin/businessRelationView.html?businessId="+businessId);
}
function openBusinessDefineDetails(businessId){//打开详情
	window.parent.document.getElementById("centerContianer").setAttribute("src",window.top.contextPath+"/static/page/smartform/admin/formDefineList.html?businessId="+businessId);
}
var businessDefineLayerDialog = null;
function showEditDialog(flag){//打开编辑弹窗
	var _title = '';
	if(flag == 'add'){
		_title = '新增';
		$(':input','#businessDefineForm').not(':button,:submit,:reset').val('');
	}else if(flag == 'update'){
		_title = '修改';
		var _id = $("#selectedBusinessDefine").val();
		if(!_id || _id == ""){
			layer.msg('请选择一条数据进行修改！',{icon: 5});
			return;
		}
		getBusinessDefineDetails(function(listData){
			for(var key in listData){
				$("#businessDefineForm input[name='"+key+"']").val(listData[key]);
			}
		});
	}
	businessDefineLayerDialog = layer.open({
		title : _title,
		type : 1,
		content : $("#businessDefineFormDialog")
	});
}
function closeEditDialog(){//关闭编辑弹窗
	if(businessDefineLayerDialog){
		layer.close(businessDefineLayerDialog);
	}else{
		layer.closeAll();
	}
}
function submitFormData() {//提交表单数据
	$("#businessDefineForm").validate({
		rules:{
			businessName:"required",
		},
		messages:{
			businessName:"必填项！",
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var paramsObject = $("#businessDefineForm").serializeObject();
			var _url = window.top.contextPath;
			if(paramsObject.businessId){
				_url += window.portPath.updateBusinessDefine;
			}else{
				_url += window.portPath.saveBusinessDefine;
			}
			var params = $.param(paramsObject);
			window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	            if (res.exchangeStatus == 1) {
	            	layer.msg('操作成功',{icon: 1});
	                layer.close(businessDefineLayerDialog);
	            	queryBusinessDefineList();
	            }else{
	            	layer.msg(res.message,{icon: 2})
	            }
	        }); 
		}
	});
}
function removeBusinessDefineById(){//根据业务id移除数据
	var _id = $("#selectedBusinessDefine").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var _url = window.top.contextPath + window.portPath.removeBusinessDefine;
		var _id = $("#selectedBusinessDefine").val();
		var params = "id="+_id;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('操作成功',{icon: 1});
	        	queryBusinessDefineList();
	        	$("#selectedBusinessDefine").val("");
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function getBusinessDefineDetails(callback){//根据业务id获取信息
	var _id = $("#selectedBusinessDefine").val();
	var _url = window.top.contextPath + window.portPath.getBusinessDefineDetatils;
	var params = "id="+_id;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback) callback(res.data);
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
		$("#selectedBusinessDefine").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTest']").val("");
		$(even).val("true");
		$("#selectedBusinessDefine").val(id);
	}
}
function openMenuResourceDialog(){//按钮资源配置
	var _id = $("#selectedBusinessDefine").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行按钮资源配置！',{icon: 5});
		return;
	}
	getBusinessDefineDetails(function(res){
		for(var key in res){
			$("#menuResourceForm input[name='"+key+"']").val(res[key]);
		}
		getMenuResourceIds(res.businessButtonResourceId);
		layer.open({
			title : '按钮资源配置',
			type : 1,
			content : $("#menuResourceFormDialog"),
			area: '400px',
			btn: ['取消', '确认'],
			yes: function(index, layero){
				layer.close(index);
			},
			btn2: function(index, layero){
				var paramsObject = $("#menuResourceForm").serializeObject();
				var params = "businessButtonResourceID="+paramsObject.businessButtonResourceID+"&businessId="+_id+"&version="+paramsObject.version;
				var _url = window.top.contextPath + window.portPath.saveMenuResourceId;
				window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
			        if (res.exchangeStatus == 1) {
			        	layer.msg('操作成功',{icon: 1});
			        }else{
			        	layer.msg(res.message,{icon: 2});
			        }
			    });
			}
		});
	});
}
function getMenuResourceIds(businessButtonResourceID){//获取所有的自定义按钮资源
	var _url = window.top.contextPath + window.portPath.getMenuResourceIds;
	window.common.networkConnect(_url, "GET", window.conType.conTyForm, null, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("menuResourceTemplate", "#menuResourceTb", {'listData': res.data, 'businessButtonResourceID':businessButtonResourceID});
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function openMenuFilterDialog(){//按钮资源配置
	var _id = $("#selectedBusinessDefine").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行按钮资源配置！',{icon: 5});
		return;
	}
	getBusinessDefineDetails(function(res){
		for(var key in res){
			$("#menuFilterForm input[name='"+key+"']").val(res[key]);
		}
		getCustomButtonFilterNames(res.buttonFilterBeanName);
		layer.open({
			title : '按钮过滤配置',
			type : 1,
			content : $("#menuFilterFormDialog"),
			area: '400px',
			btn: ['取消', '确认'],
			yes: function(index, layero){
				layer.close(index);
			},
			btn2: function(index, layero){
				var paramsObject = $("#menuFilterForm").serializeObject();
				var params = "buttonFilterBeanName="+paramsObject.buttonFilterBeanName+"&businessId="+_id+"&version="+paramsObject.version;
				var _url = window.top.contextPath + window.portPath.saveButtonFilterBeanName;
				window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
			        if (res.exchangeStatus == 1) {
			        	layer.msg('操作成功',{icon: 1});
			        }else{
			        	layer.msg(res.message,{icon: 2});
			        }
			    });
			}
		});
	});
}
function getCustomButtonFilterNames(buttonFilterBeanName){///获取所有的自定义按钮过滤名称
	var _url = window.top.contextPath + window.portPath.getCustomButtonFilterNames;
	window.common.networkConnect(_url, "GET", window.conType.conTyForm, null, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("menuFilterTemplate", "#menuFilterTb", {'listData': res.data, 'buttonFilterBeanName':buttonFilterBeanName});
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器