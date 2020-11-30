/*
**业务定义
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	// submitFormData();
	queryProcessList();
});
var ManageData= null;
function queryProcessList(){//查询业务定义列表
    var code = window.common.getQueryString("code");
	var _url = window.top.contextPath + window.portPath.processExecutorPage+code;
	var queryForm = $("#queryProcessForm").serializeObject();//rows: 20 分页大小,page: 1  当前页
	window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("processExecutor", "#processExecutorTb", {ManageData: res});
        }
    });
}
var treeDialog;
function showTreeDialog(type){
	var taskId = $("#selectedId").val();
	var code = window.common.getQueryString("code");
	var taskName = $("#taskName").val();
	if(!taskId || taskId == ""){
		layer.msg('请选择一个节点进行配置！',{icon: 5});
		return;
	}
	if(type==1){
		/*treeDialog = layer.open({
			title : "添加人员",
			type : 1,
			area:["600px","500px"],
			scrollbar: true,
			content : $("#treeDialog")
		});*/
		treeDialog = layer.open({
	        title : '给'+taskName+'添加人员',
	        type : 2,
	        //offset : '10%',
	        area:[450,500],
	        maxmin :true,
	        content : "orgTree.html?taskId="+taskId+"&taskName="+taskName+"&code="+code,//"/page/business/define/commonViews.html"
	    });
	}else{
		/*treeDialog = layer.open({
			title : "添加角色",
			type : 1,
			area:["600px","500px"],
			scrollbar: true,
			content : $("#treeDialog")
		});*/
		
		treeDialog = layer.open({
	        title : '给'+taskName+'添加角色',
	        type : 2,
	        //offset : '10%',
	        area:[450,500],
	        maxmin :true,
	        content : "roleTree.html?taskId="+taskId+"&taskName="+taskName,//"/page/business/define/commonViews.html"
	    });
		
		//showRoleList();
	}

}




var processExecutorFormDialog = null;
function showEditDialog(){//打开编辑弹窗
	var _title = '';
    var id = $("#selectedId").val();
    if(!id || id == ""){
        layer.msg('请选择一个节点进行配置！',{icon: 5});
        return;
    }
    var executors = $("#executors").val();
    var flowType = $("#flowType").val();
    var taskPMName = $("#taskPMName").val();
	if(!executors || executors==""){
		if(flowType=='PROC'){
            _title = '设置全局时限';
            taskPMName+="(全局)";
		}else {
            _title = '设置节点时限';
		}
		$("#taskName").text(taskPMName);
		$(':input','#processExecutorForm').not(':button,:submit,:reset').val('');
	}else{
        _title = '';
        if(flowType=='PROC'){
            _title = '修改全局时限';
            taskPMName+="(全局)";
        }else {
            _title = '修改节点时限';
        }
        $("#taskName").text(taskPMName);
        getProcessExecutor(function(listData){
            for(var key in listData){
                $("#processExecutorForm input[name='"+key+"']").val(listData[key]);
                $("#processExecutorForm select[name='"+key+"']").val(listData[key]);
            }
        });
	}
    processExecutorFormDialog = layer.open({
        title : _title,
        type : 1,
        content : $("#processExecutorFormDialog")
    });
	

}
function closeEditDialog(){//关闭编辑弹窗
	if(processExecutorFormDialog){
		layer.close(processExecutorFormDialog);
	}else{
		layer.closeAll();
	}
}
function submitFormData() {//提交表单数据
	$("#processExecutorForm").validate({
		rules:{
			executors:{
                required:true,
                checkNum:true
			}
		},
		messages:{
            code:{
                required:"必填项!"
			},
			pageUrl:{
                required:"必填项!"
			}
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var paramsObject = $("#processExecutorForm").serializeObject();
            paramsObject["taskId"]=$("#selectedId").val();
            paramsObject["bizCode"]=window.common.getQueryString("code");
			var _url = window.top.contextPath + window.portPath.processExecutorSave;
            var params= JSON.stringify(paramsObject);
            window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function(res) {
	            if (res.id!='') {
	            	layer.msg('操作成功',{icon: 1});
	                layer.close(processExecutorFormDialog);
	            	queryProcessList();
                    $("#selectedId").val('');
	            }else{
	            	layer.msg("操作失败",{icon: 2})
	            }
	        });
		}

});
    //自定义正则表达示验证方法
    $.validator.addMethod("checkNum",function(value,element){
        var chrnum = /^[0-9]*$/;
        return this.optional(element) || (chrnum.test(value));
    }, "请输入整数");
}




function getProcessExecutor(callback){//根据流程id获取信息
	var id = $("#selectedId").val();
    var bizCode = window.common.getQueryString("code");
	var _url = window.top.contextPath + window.portPath.processExecutorPage+bizCode+"/"+id;
	window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback) callback(res.data);
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function updateSelection(even,taskId,taskName){//更新选择状态
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedId").val("");
		$("#taskName").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTest']").val("");
		$(even).val("true");
		$("#selectedId").val(taskId);
		$("#taskName").val(taskName);
	}
}

function selectChange(even){//
    var selectedText = $(even).find("option:selected").text();
    $(even).siblings("input").val(selectedText);
}


template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器