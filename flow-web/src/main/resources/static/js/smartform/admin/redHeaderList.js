/*
**红头
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	queryAllRedHeaderList();
	/*$('#redHeaderSubmitButton').bind("click",function(){
		$("#redHeaderPage").val(1);
		return;
	});
	$('#redHeaderSubmitButton').trigger("click");*/
	submitRedHeaderData();
});
function queryAllBureaus(bureauGuid, bureauName){//获取局办数据
	var _url = window.top.contextPath + window.portPath.getAllBureausList;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, null, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("allBureausTemplate", ".allBureausTb", {contextPath: window.top.contextPath, redHeaderData: res.data, selectedObj: {bureauGuid: bureauGuid, bureauName: bureauName}});
        }
    });
}
function queryAllDept(bureauGuid, deptGuid, deptName){//获取部门数据
	var _url = window.top.contextPath + window.portPath.getAllDeptList;
	var queryForm = "id=" + bureauGuid;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("allDeptTemplate", ".allDeptTb", {contextPath: window.top.contextPath, redHeaderData: res.data, selectedObj: {deptGuid: deptGuid, deptName: deptName}});
        }
    });
}
function selectChange(even){//
	var selectedText = $(even).find("option:selected").text();
	$(even).siblings("input").val(selectedText);
}
function queryAllRedHeaderList(currentPage){//通过业务ID查询模板列表
	var _url = window.top.contextPath + window.portPath.queryAllRedHeaderList;
	var redHeaderQueryForm = $("#queryRedHeaderForm").serializeObject();
	redHeaderQueryForm.page = currentPage || 1;
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, redHeaderQueryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("redHeader", ".redHeaderTb", {contextPath: window.top.contextPath, redHeaderData: res.data});
        	var opts = {
  				 currentPage : res.data.currentPage + 1,
  				 totalPages:res.data.totalPages,
  				 total:res.data.total,
  				 size:res.data.size,
  				 callBack : redHeaderCallBack
  		 	}
  		   $.huasiBackPagination.drawPagination(opts);
        }
    });
}
function redHeaderCallBack(opts){//分页跳转
	queryAllRedHeaderList(opts.currentPage);
}
var redHeaderLayerDialog = null;
function showRedHeaderEditDialog(flag){//打开编辑弹窗
	var _title = '';
	if(flag == 'add'){
		_title = '新增';
		queryAllBureaus();
		$(":input","#redHeaderForm").not(":button,:submit,:reset").val("");
	}else if(flag == 'update'){
		$(":input","#redHeaderForm").not(":button,:submit,:reset,:hidden").val("");
		_title = '修改';
		var _id = $("#selectedRedHeaderId").val();
		if(!_id || _id == ""){
			layer.msg('请选择一条数据进行修改！',{icon: 5});
			return;
		}
		openRedHeaderDetails();
	}
	redHeaderLayerDialog = layer.open({
		title : _title,
		type : 1,
		area:['680px'],
		content : $("#redHeaderFormDialog")
	});
}
function closeRedHeaderEditDialog(){//关闭编辑弹窗
	if(redHeaderLayerDialog){
		layer.close(redHeaderLayerDialog);
	}else{
		layer.closeAll();
	}
}
function submitRedHeaderData() {//提交表单数据
	$("#redHeaderForm").validate({
		rules:{
			egName:"required"
		},
		messages:{
			egName:"必填项！"
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var redHeaderObj= $("#redHeaderForm").serializeObject();
			var redHeaderStr= $("#redHeaderForm").serialize();
			var _url = window.top.contextPath;
			if(redHeaderObj.templateId){
				_url += window.portPath.updateRedHeader;
			}else{
				_url += window.portPath.saveRedHeader;
			}
			window.common.networkConnect(_url, "POST", window.conType.conTyForm, redHeaderStr, function(res) {
	            if (res.exchangeStatus == 1) {
	            	layer.msg('操作成功',{icon: 1});
		            layer.close(redHeaderLayerDialog);
		            queryAllRedHeaderList();
	            }else{
	            	layer.msg(res.message,{icon: 2})
	            }
	        });
		}
	});
}
function removeRedHeaderById(){//根据模板id移除数据
	var _id = $("#selectedRedHeaderId").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var _url = window.top.contextPath + window.portPath.removeRedHeaderById;
		var _id = $("#selectedRedHeaderId").val();
		var params = "id="+_id;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('操作成功',{icon: 1});
	        	queryAllRedHeaderList();
	        	$("#selectedRedHeaderId").val("");
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function openRedHeaderDetails(){//打开模板详情
	var _id = $("#selectedRedHeaderId").val();
	var _url = window.top.contextPath + window.portPath.getRedHeaderDetatils;
	var params = "id="+_id;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	for(var key in res.data){
				$("#redHeaderForm input[name='"+key+"']").val(res.data[key]);
				$("#redHeaderForm select[name='"+key+"']").val(res.data[key]);
			};
			queryAllBureaus(res.data.bureauGuid, res.data.bureauName);
			queryAllDept(res.data.bureauGuid ,res.data.deptGuid, res.data.deptName);
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function updateTemplateSelection(even, id){//选择数据
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedRedHeaderId").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTemplate']").val("");
		$(even).val("true");
		$("#selectedRedHeaderId").val(id);
	}
}
function openRedHeaderFileInput($index){//点击上传
	$("#redHeaderfileUpload" + $index)[0].click();
};
function uploadRedHeaderFile(tId, even){//上传模板
	var fileData = $(even)[0].files[0];
	var formData = new FormData();
	formData.append("file", fileData);
	formData.append("id", tId);
	var _url = window.top.contextPath + window.portPath.saveTextTemplateContent;
	window.common.networkConnect(_url, "POST", false, formData, function(res) {
        if (res.exchangeStatus == 1) {
        	layer.msg('上传成功',{icon: 1});
        	queryAllRedHeaderList();
        }else{
        	layer.msg(res.message,{icon: 2})
        }
    });
}
function downloadRedHeaderFile(templateId){//下载模板
	var params = "id=" + templateId;
	var _url = window.top.contextPath + window.portPath.getTextTemplateContent;
	window.location.href = _url + "?id=" + templateId;
}
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器