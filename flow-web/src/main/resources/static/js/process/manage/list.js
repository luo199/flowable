/*
**业务定义
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	// submitFormData();
	$('#queryProcessForm').on('submit',function(){
	    queryProcessList();
		return false; //阻止表单默认提交
	})
	$('#businessDefineSubmitButton').bind("click",function(){
		$("#processPageCurrent").val(1);
	});
	$('#businessDefineSubmitButton').trigger("click");
	
});
function queryProcessList(){//查询业务定义列表
	var _url = window.top.contextPath + window.portPath.processManageList;
	var queryForm = $("#queryProcessForm").serializeObject();//rows: 10 分页大小,page: 1  当前页
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("businessTemplate", "#businessTb", {listData: res.data});
        	var opts = {
				 currentPage : res.data.current,
				 totalPages:res.data.pages,
				 total:res.data.total,
				 size:res.data.size,
				 callBack : pageCallBack
		 	}
        	$("#processPageCurrent").val(res.data.current);
        	$("#processPageSize").val(res.data.size);
		   $.huasiBackPagination.drawPagination(opts);
		   uncheck();
        }
    });
}

function uncheck(){
	$("#selectedBusinessDefine").val("");
	$("#btnSuspend").hide(); 
	$("#btnActivate").hide();
}

function pageCallBack(opts){//分页跳转
	$("#processPageCurrent").val(opts.currentPage);
	$('#queryProcessForm').trigger("submit");
	return false ;
};


var uploadBmmnXmlDialog = null;
function uploadBmmnXml(){
	$('.showFile').val('');
	$("#uploadBmmnXml").val('');
	uploadBmmnXmlDialog = layer.open({
		title : 'XML部署',
		type : 1,
		content : $("#uploadBmmnXmlDialog")
	});
} 

function closeUploadDialog(){//关闭导入弹窗
	if(uploadBmmnXmlDialog){
		layer.close(uploadBmmnXmlDialog);
	}else{
		layer.closeAll();
	}
}

$(document).on("change","#uploadBmmnXml",function(){   //显示文件名
	   var filename = $("#uploadBmmnXml")[0].files[0].name;
	   $('.showFile').val(filename)
}); 

$(document).on("click",".uploadFile",function(){  //上传文件
	var filename = $('.showFile').val();
	if(!filename || filename == ""){
        layer.msg('请选择一个文件！',{icon: 5});
        return;
    }
   var _url = window.top.contextPath + window.portPath.processManageXmlDeploy;
   var fileData = $("#uploadBmmnXml")[0].files[0];
   var formData = new FormData();
   formData.append("file", fileData);
   window.common.networkConnect(_url, "POST", false, formData, function(res) {
	   if (res.exchangeStatus==1) {
           layer.msg('部署成功',{icon: 1});
           queryProcessList();
           closeUploadDialog();
       }else{
           layer.msg("部署失败",{icon: 2})
       }
   }); 
}); 

function suspendProcess(){
	var id = $("#selectedBusinessDefine").val();
	var _url = window.top.contextPath + window.portPath.processManageSuspend+"?processDefinitionId="+id;
	window.common.networkConnect(_url, "GET", null, null, function(res) {
		if (res.exchangeStatus==1) {
            layer.msg('流程挂起成功',{icon: 1});
            queryProcessList();
        }else{
            layer.msg("流程挂起失败",{icon: 2})
        }
	});
	$("#btnSuspend").hide();
}

function activateProcess(){
	var id = $("#selectedBusinessDefine").val();
	var _url = window.top.contextPath + window.portPath.processManageActivate+"?processDefinitionId="+id;
	window.common.networkConnect(_url, "GET", null, null, function(res) {
		if (res.exchangeStatus==1) {
            layer.msg('流程激活成功',{icon: 1});
            queryProcessList();
        }else{
            layer.msg("流程激活失败",{icon: 2})
        }
	});
	$("#btnActivate").hide();
}

function deleteDeploy(){
	var _id = $("#selectedBusinessDefine").val();
	if(!_id || _id == ""){
        layer.msg('请选择一项流程进行删除！',{icon: 5});
        return;
    }
	var _url = window.top.contextPath + window.portPath.processManageDelete+"?processDefinitionId="+_id;
	window.common.networkConnect(_url, "GET", null, null, function(res) {
		if (res.exchangeStatus==1) {
            layer.msg('删除成功',{icon: 1});
            queryProcessList();
        }else{
            layer.msg("删除失败",{icon: 2})
        }
	});
}
function updateSelection(even, id,state){//更新选择状态
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedBusinessDefine").val("");
		$(even).val("");
		$("#btnSuspend").hide();
		$("#btnActivate").hide();
	}else{
		$("input[name='checkboxTest']").val("");
		$(even).val("true");
		$("#selectedBusinessDefine").val(id);
		if(state==1){
			$("#btnSuspend").show();
			$("#btnActivate").hide();
		}else{
			$("#btnSuspend").hide();
			$("#btnActivate").show();
		}
	}
}



var imageDialog = null;
function showImageDialog(id){//打卡流程图弹窗
	
	var _url = window.top.contextPath + window.portPath.processManageImage+"?processDefinitionId="+id
	window.common.networkConnect(_url, "GET", null, null, function(res) {
		   if (res.exchangeStatus==1) {
			   /*var imageH = res.data.height;
			   var imageW = res.data.width;*/
			   var windowW = $(window).width();
			   var windowH = $(window).height();
			   /*var diaH = Math.max(imageH,windowH);
			   var diaW = Math.max(imageW,windowW);*/
			   imageDialog = layer.open({
					title : '流程图',
					type : 1,
					content : '<div align="center"><img src="data:image/png;;base64,'+res.data.image+'"/></div>',
					shadeColse: true,
					maxmin :true,
					shade:0.1,
					//offset : '10%',
					area:[windowW,windowH-30]
				});
	       }else{
	           layer.msg("获取流程图失败",{icon: 2})
	       }
	   }); 
	/*var _url = window.top.contextPath + "/api/draw/process/png?processDefinitionId="+id
   var windowW = $(window).width();
   var windowH = $(window).height();
   imageDialog = layer.open({
		title : '流程图',
		type : 2,
		content : _url,
		maxHeight :windowH,
		maxWidth :windowW,
	});*/
}

function closeUploadDialog(){//关闭流程图弹窗
	if(imageDialog){
		layer.close(imageDialog);
	}else{
		layer.closeAll();
	}
}


template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器