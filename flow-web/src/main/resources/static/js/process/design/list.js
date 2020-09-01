/*
**业务定义
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
    $("#cloneMode").hide();
	submitFormData();
	$('#queryProcessForm').on('submit',function(){
	    queryProcessList();
		return false; //阻止表单默认提交
	})
	$('#businessDefineSubmitButton').bind("click",function(){
		$("#processPage").val(1);
	});
	$('#businessDefineSubmitButton').trigger("click");
});
function queryProcessList(){//查询业务定义列表
	var _url = window.top.contextPath + window.portPath.processDesignList;
	var queryForm = $("#queryProcessForm").serializeObject();//rows: 20 分页大小,page: 1  当前页
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("businessTemplate", "#businessTb", {listData: res.data});
        	var opts = {
				 currentPage : res.data.current,
				 totalPages:res.data.pages,
				 total:res.data.total,
				 size:res.data.size,
				 callBack : pageCallBack
		 	};
		   $.huasiBackPagination.drawPagination(opts);
        }
    });
}
function pageCallBack(opts){//分页跳转
	$("#processPage").val(opts.currentPage);
	$('#queryProcessForm').trigger("submit");
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
		_title = '新增流程';
		$(':input','#businessDefineForm').not(':button,:submit,:reset').val('');
        businessDefineLayerDialog = layer.open({
            title : _title,
            type : 1,
            content : $("#businessDefineFormDialog")
        });
	}else if(flag == 'update'){
		var _id = $("#selectedBusinessDefine").val();
		if(!_id || _id == ""){
			layer.msg('请选择一项流程进行编辑！',{icon: 5});
			return;
		}
        window.open("../../../models/index.html#/editor/"+_id);
    }else if(flag == 'deploy'){
        var _id = $("#selectedBusinessDefine").val();
        if(!_id || _id == ""){
            layer.msg('请选择一项流程进行部署！',{icon: 5});
            return;
        }
        var _url = window.top.contextPath +window.portPath.deployProcessDesign+"?modelId="+_id;
        window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
            if (res.exchangeStatus==1) {
                layer.msg('部署成功',{icon: 1});
                queryProcessList();
            }else{
                layer.msg("部署失败",{icon: 2})
            }
        });
	}else if(flag == 'clone'){
        _title = '复制流程';
        var _id = $("#selectedBusinessDefine").val();
        getModelByID(function(listData){
            for(var key in listData){
                $("#businessDefineForm input[name='"+key+"']").val(listData[key]);
            }
        });
        businessDefineLayerDialog = layer.open({
            title : _title,
            type : 1,
            content : $("#businessDefineFormDialog")
        });
	}

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
			name:{
                required:true
			},
			key:{
                required:true,
                checkEnglish:true,
                checkExsitKey:true
			},
            description:{
                required:true
            }
		},
		messages:{
			name:{
                required:"必填项!"
			},
            key:{
                required:"必填项!"
			},
            description:{
                required:"必填项!"
            }
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var paramsObject = $("#businessDefineForm").serializeObject();
			var _url = window.top.contextPath;
			if(paramsObject.id==''){
                _url += window.portPath.creatProcessDesign;
			}else{
                _url += window.portPath.cloneProcessDesign+paramsObject.id+"/clone";
			}
            if(paramsObject.modelType==''){
                paramsObject.modelType=0;
            }
			//var params = $.param(paramsObject);
            var params= JSON.stringify(paramsObject);
            console.log(params);
			window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function(res) {
	            if (res.id!='') {
	            	layer.msg('操作成功',{icon: 1});
	                layer.close(businessDefineLayerDialog);
	            	queryProcessList();
                    //http://192.168.50.48:8091/flow/models/index.html#/editor/fb7b2818-d309-11ea-a0c7-305a3a544de7
                    window.open("../../../models/index.html#/editor/"+res.id);
	            }else{
	            	layer.msg("操作失败",{icon: 2})
	            }
	        });
		}

	});
    //自定义正则表达示验证方法
    $.validator.addMethod("checkEnglish",function(value,element){
        var chrnum = /^[a-zA-Z][a-zA-Z0-9_]*$/;
        return this.optional(element) || (chrnum.test(value));
    }, "请输入字母数字和下划线");
    //验证key值是否唯一
    $.validator.addMethod("checkExsitKey",function(value,element){
        return this.optional(element) || (existKey(value));
    }, "流程key存在！");
}


function existKey(key) {
    var flag;
    $.ajax({
        url:window.top.contextPath + window.portPath.checkModelKeyExist+"?modelKey="+key,
        async:false,//这里选择异步为false，那么这个程序执行到这里的时候会暂停，等待
        type:"get",
        dataType:"json",
        success:function(res){
            if(res.exchangeStatus == 1){
                flag=!res.data;
            }
        }
    });
    return flag;
}
function removeBusinessDefineById(){//根据业务id移除数据
	var _id = $("#selectedBusinessDefine").val();
   var name =  $("#selectedModelName").val();
	if(!_id || _id == ""){
		layer.msg('请选择一项流程进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除流程-'+name+"?", {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
        var _id = $("#selectedBusinessDefine").val();
        var _url = window.top.contextPath + window.portPath.deleteProcessDesign+"?modelId="+_id;
		window.common.networkConnect(_url, "DELETE", window.conType.conTyJson, null, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('删除成功',{icon: 1});
	        	queryProcessList();
	        	$("#selectedBusinessDefine").val("");
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function getModelByID(callback){//根据流程id获取信息
	var _id = $("#selectedBusinessDefine").val();
	var _url = window.top.contextPath + window.portPath.findModelById+"?id="+_id;
	window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
        if (res.exchangeStatus == 1) {
            res.data= changeTreeDate(res.data,"key","modelKey");
        	if(callback) callback(res.data);
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function updateSelection(even, id,maxVersion,name){//更新选择状态
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedBusinessDefine").val("");
        $("#selectedModelName").val("");
		$(even).val("");
        $("#modelDelete").show();
        $("#cloneMode").hide();
	}else{
		$("input[name='checkboxTest']").val("");
		$(even).val("true");
		$("#selectedBusinessDefine").val(id);
		$("#selectedModelName").val(name);
        $("#cloneMode").show();
		if(maxVersion==null||maxVersion==''){
			$("#modelDelete").show();
		}else {
			//部署的流程无法在流程列表出删除
            $("#modelDelete").hide();
		}
	}
}
function openMenuResourceDialog(){//按钮资源配置
	var _id = $("#selectedBusinessDefine").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行按钮资源配置！',{icon: 5});
		return;
	}
	getModelByID(function(res){
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
	getModelByID(function(res){
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

function changeTreeDate(zf_jsonObj, newKey, oldKey) {
    var str = JSON.stringify(zf_jsonObj);
    var reg = new RegExp(oldKey, 'g');
    var newStr = str.replace(reg, newKey);
    return JSON.parse(newStr);
}
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器