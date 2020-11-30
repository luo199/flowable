/*
**业务定义
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	submitFormData();
	queryProcessList();
});
function queryProcessList(){//查询业务定义列表
    var code = window.common.getQueryString("code");
	var _url = window.top.contextPath + window.portPath.processLimitPage+code;
	var queryForm = $("#queryProcessForm").serializeObject();//rows: 20 分页大小,page: 1  当前页
	window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("processLimit", "#processLimitTb", {ManageData: res});
        }
    });
}
var processLimitFormDialog = null;
function showEditDialog(){//打开编辑弹窗
	var _title = '';
    var id = $("#selectedId").val();
    if(!id || id == ""){
        layer.msg('请选择一个节点进行配置！',{icon: 5});
        return;
    }
    var timeLimit = $("#timeLimit").val();
    var flowType = $("#flowType").val();
    var taskPMName = $("#taskPMName").val();
	if(!timeLimit || timeLimit==""){
		if(flowType=='PROC'){
            _title = '设置全局时限';
            taskPMName+="(全局)";
		}else {
            _title = '设置节点时限';
		}
		$("#taskName").text(taskPMName);
		$(':input','#processLimitForm').not(':button,:submit,:reset').val('');
	}else{
        _title = '';
        if(flowType=='PROC'){
            _title = '修改全局时限';
            taskPMName+="(全局)";
        }else {
            _title = '修改节点时限';
        }
        $("#taskName").text(taskPMName);
        getProcessLimit(function(listData){
            for(var key in listData){
                $("#processLimitForm input[name='"+key+"']").val(listData[key]);
                $("#processLimitForm select[name='"+key+"']").val(listData[key]);
            }
        });
	}
    processLimitFormDialog = layer.open({
        title : _title,
        type : 1,
        content : $("#processLimitFormDialog")
    });
	

}
function closeEditDialog(){//关闭编辑弹窗
	if(processLimitFormDialog){
		layer.close(processLimitFormDialog);
	}else{
		layer.closeAll();
	}
}


function submitFormData() {//提交表单数据
	$("#processLimitForm").validate({
		rules:{
            limitType:{
                required:true
			},
            timeLimit:{
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
			var paramsObject = $("#processLimitForm").serializeObject();
            paramsObject["taskId"]=$("#selectedId").val();
            paramsObject["bizCode"]=window.common.getQueryString("code");
			var _url = window.top.contextPath + window.portPath.processLimitSave;
            var params= JSON.stringify(paramsObject);
            window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function(res) {
	            if (res.id!='') {
	            	layer.msg('操作成功',{icon: 1});
	                layer.close(processLimitFormDialog);
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


function removeProcessLimit(){//根据业务id移除数据
	var id = $("#selectedId").val();
   var  taskName = $("#taskPMName").val();
    var flowType = $("#flowType").val();
    var timeLimit = $("#timeLimit").val();

    if(flowType=='PROC'){
        taskName+="(全局)";
    }
	if(!id || id == ""){
		layer.msg('请选择一个节点删除时限！',{icon: 5});
		return;
	}
    if(!timeLimit || timeLimit == ""){
        layer.msg('未配置节点时限！',{icon: 5});
        return;
    }
	layer.confirm("确认清除节点-"+taskName+"-时限配置",{
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){

        var id = $("#selectedId").val();
        var _url = window.top.contextPath + window.portPath.processLimitDelete+"?taskId="+id;
		window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('删除成功',{icon: 1});
	        	queryProcessList();
	        	$("#selectedId").val("");
            }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function getProcessLimit(callback){//根据流程id获取信息
	var id = $("#selectedId").val();
    var bizCode = window.common.getQueryString("code");
	var _url = window.top.contextPath + window.portPath.processLimitByprocessId+bizCode+"/"+id;
	window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback) callback(res.data);
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function updateSelection(even,name,flowType,timeLimit, id){//更新选择状态
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedId").val("");
		$("#timeLimit").val("");
		$("#flowType").val("");
		$("#taskPMName").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTest']").val("");
		$(even).val("true");
		$("#selectedId").val(id);
		$("#timeLimit").val(timeLimit);
		$("#flowType").val(flowType);
		$("#taskPMName").val(name);
	}
}

function selectChange(even){//
    var selectedText = $(even).find("option:selected").text();
    $(even).siblings("input").val(selectedText);
}


template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器