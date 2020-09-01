/*
**业务定义
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
    queryAllCataLog();
    queryAllProcess();
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
	var _url = window.top.contextPath + window.portPath.businessBaseList;
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
	var businessId = $("#selectedBusinessCode").val();
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
		_title = '新增业务';
       // queryAllCataLog();
       
        $("#businessCode").show();
		$(':input','#businessDefineForm').not(':button,:submit,:reset').val('');
        businessDefineLayerDialog = layer.open({
            title : _title,
            type : 1,
            content : $("#businessDefineFormDialog")
        });
	}else if(flag == 'update'){
        _title = '修改业务';

        var code = $("#selectedBusinessCode").val();
        if(!code || code == ""){
            layer.msg('请选择一项业务进行修改！',{icon: 5});
            return;
        }
        $("#businessCode").hide();
        getBusinessByCode(function(listData){
            for(var key in listData){
                $("#businessDefineForm input[name='"+key+"']").val(listData[key]);
                $("#businessDefineForm select[name='"+key+"']").val(listData[key]);
                //queryAllProcess(listData['processId']);
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
            code:{
                required:true,
                checkEnglish:true,
                checkExsitCode:true
			},
            name:{
                required:true
			},
            cataCode:{
                required:true
			},
            processId:{
                required:true
            },
            remark:{
                required:true
            }
		},
		messages:{
            code:{
                required:"必填项!"
			},
            name:{
                required:"必填项!"
			},
            cataCode:{
                required:"请选择业务分类!"
			},
            processId:{
                required:"请选择关联流程!"
            },
            remark:{
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
            if($("#businessCode").is(":hidden")){//编辑
				/*var updateCode  = paramsObject["code"];
                var trueCode = updateCode.substring(8);
                paramsObject["code"]=trueCode;*/
                _url += window.portPath.updatebusinessBase;
			}else{//新增
                _url += window.portPath.createbusinessBase;
			}
            var params= JSON.stringify(paramsObject);

            window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function(res) {
	            if (res.id!='') {
	            	layer.msg('操作成功',{icon: 1});
	                layer.close(businessDefineLayerDialog);
	            	queryProcessList();
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
    $.validator.addMethod("checkExsitCode",function(value,element){
        return this.optional(element) || (existCode(value));
    }, "业务编码存在，请重输！");
}


function existCode(code) {
    var flag=true;
    if($("#businessCode").is(":hidden")){//编辑
		return flag;
	}
    $.ajax({
        url:window.top.contextPath + window.portPath.checkBusinessCodeExist+"?code="+code,
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
function removeBusinessDefineByCode(){//根据业务id移除数据
	var code = $("#selectedBusinessCode").val();
   var name =  $("#selectedBusinessName").val();
	if(!code || code == ""){
		layer.msg('请选择一项业务进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除业务-'+name+"?", {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){

        var code = $("#selectedBusinessCode").val();
        var _url = window.top.contextPath + window.portPath.updateBusinessStatus+"?code="+code;
		window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('删除成功',{icon: 1});
	        	queryProcessList();
	        	$("#selectedBusinessCode").val("");
          		$("#selectedBusinessName").val();
            }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function getBusinessByCode(callback){//根据流程id获取信息
	var code = $("#selectedBusinessCode").val();
	var _url = window.top.contextPath + window.portPath.findBusinessBaseByCode+"?code="+code;
	window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback) callback(res.data);
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function updateSelection(even, code,name){//更新选择状态
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedBusinessCode").val("");
		$("#selectedBusinessName").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTest']").val("");
		$(even).val("true");
		$("#selectedBusinessCode").val(code);
		$("#selectedBusinessName").val(name);
	}
}
function queryAllCataLog(code, even){//获取业务类别列表
    // name = $(even).find("option:selected").text();
    var _url = window.top.contextPath + window.portPath.cataLogList;
    window.common.networkConnect(_url, "POST", window.conType.conTyForm, null, function(res) {
        if (res.exchangeStatus == 1) {
        	var initSelectData = {contextPath: window.top.contextPath, businessCatalogData: res.data,selectedObj: {code: '', name: name}};
        	
            window.common.drawTemplateHtml("allCataLogList", ".allCataLogTb", initSelectData);
            window.common.drawTemplateHtml("allCataLogListYt", ".allCataLogTt", initSelectData);
        }
    });
}

function queryAllProcess(id, even){//获取激活流程列表
    var name = $(even).find("option:selected").text();
    var _url = window.top.contextPath + window.portPath.ActivateProcessList;
    window.common.networkConnect(_url, "POST", window.conType.conTyForm, null, function(res) {
        if (res.exchangeStatus == 1) {
            window.common.drawTemplateHtml("allActivateProcess", ".allActivateProcessTb", {contextPath: window.top.contextPath, activateProcessData: res.data, selectedObj: {id: id, name: name}});
        }
    });
}
function selectChange(even){//
    var selectedText = $(even).find("option:selected").text();
    $(even).siblings("input").val(selectedText);
}

function openBusinessCatalogPage(){//打开业务分类管理
	layer.open({
        title : '类别管理',
        type : 2,
		//offset : '10%',
		area:[900,700],
        content : window.top.contextPath+"/static/page/business/define/catalogmanage.html"
    });
	
    //window.parent.document.getElementById("centerCoverContianer").style.display = 'block';
  // window.parent.document.getElementById("centerCoverContianer").setAttribute("src",window.top.contextPath+"/static/page/business/define/catalogmanage.html");
}

function openBusinessManagePage() {//打开业务配置页面
    var code = $("#selectedBusinessCode").val();
    if(!code || code == ""){
        layer.msg('请选择一项业务进行配置！',{icon: 5});
        return;
    }

    window.parent.document.getElementById("centerCoverContianer").style.display = 'block';
    window.parent.document.getElementById("centerCoverContianer").setAttribute("src",window.top.contextPath+"/static/page/business/define/businessManageView.html?code="+code);
}
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器