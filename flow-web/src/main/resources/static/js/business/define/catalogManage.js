/*
**模板定义
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	window.common.getNiceScroll("wrapper");
	$('#queryCatalogListForm').on('submit',function(){
		getCataloglist();
		return false; //阻止表单默认提交
	});
	$('#catalogSubmitButton').bind("click",function(){
		$("#catalogPage").val(1);
	});
	$('#catalogSubmitButton').trigger("click");
	submitTextTemplateData();
});
function getCataloglist(){//通过业务ID查询模板列表
	var textTemplateObj= $("#queryCatalogListForm").serializeObject();
	var _url = window.top.contextPath + window.portPath.querycataLogList;
	var queryForm = $("#queryCatalogListForm").serializeObject();
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("catalogList", "#catalogListTb", {catalogData: res.data});
        	var opts = {
   				 currentPage : res.data.current,
   				 totalPages:res.data.pages,
   				 total:res.data.total,
   				 size:res.data.size,
   				 callBack : CatalogPageCallBack
   		 	};
   		   $.huasiBackPagination.drawPagination(opts);
        }
    });
}
function CatalogPageCallBack(opts){//分页跳转
	$("#catalogPage").val(opts.currentPage);
	$('#queryCatalogListForm').trigger("submit");
	return false ;
}
var textTemplateLayerDialog = null;
function showCatalogEditDialog(flag){//打开编辑弹窗
	var _title = '';
	if(flag == 'add'){
		_title = '新增分类';
        $("#cataLogCode").show();
		$(":input","#textTemplateForm").not(":button,:submit,:reset").val("");
	}else if(flag == 'update'){
		$(":input","#textTemplateForm").not(":button,:submit,:reset,:hidden").val("");
		_title = '修改分类';
		var code = $("#selectedCataLogCode").val();
		if(!code || code == ""){
			layer.msg('请选择一条分类进行修改！',{icon: 5});
			return;
		}
		$("#cataLogCode").hide();
		openCataLogDetails();
	}
	textTemplateLayerDialog = layer.open({
		title : _title,
		type : 1,
		content : $("#textTemplateFormDialog")
	});
}
function closeTextTemplateEditDialog(){//关闭编辑弹窗
	if(textTemplateLayerDialog){
		layer.close(textTemplateLayerDialog);
	}else{
		layer.closeAll();
	}
}
function submitTextTemplateData() {//提交表单数据
	$("#textTemplateForm").validate({
		rules:{
            code:{
                required:true,
                checkEnglish:true,
                checkExsitCode:true
            }, name:{
                required:true
            }
		},
		messages:{
            code:{
                required:"必填项!"
            },
            name:{
                required:"必填项!"
            }
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var textTemplateObj= $("#textTemplateForm").serializeObject();
			var _url = window.top.contextPath;
            if($("#cataLogCode").is(":hidden")){//编辑
                _url += window.portPath.updateCataLog;
			}else{
                _url += window.portPath.createCataLog;
			}
            var textTemplateJSON= JSON.stringify(textTemplateObj);
            window.common.networkConnect(_url, "POST", window.conType.conTyJson, textTemplateJSON, function(res) {
	            if (res.exchangeStatus == 1) {
	            	layer.msg('操作成功',{icon: 1});
		            layer.close(textTemplateLayerDialog);
		            getCataloglist();
	            }else{
	            	layer.msg(res.message,{icon: 2})
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
    }, "分类编码存在，请重输！");
}
function existCode(code) {
    var flag = true;
    if($("#cataLogCode").is(":hidden")){//编辑
		return flag;
	}
    $.ajax({
        url:window.top.contextPath + window.portPath.checkCataLogCodeExist+"?code="+code,
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
function existBusinessByCode(code) {
    var flag;
    $.ajax({
        url:window.top.contextPath + window.portPath.checkBusinessByCatacode+"?cataCode="+code,
        async:false,//这里选择异步为false，那么这个程序执行到这里的时候会暂停，等待
        type:"get",
        dataType:"json",
        success:function(res){
            if(res.exchangeStatus == 1){
                flag=res.data;
            }
        }
    });
    return flag;
}
function removeCataLogByCode(){//移除分类
	var code = $("#selectedCataLogCode").val();
    var name = $("#selectedCataLogName").val();
    if(!code || code == ""){
		layer.msg('请选择一条分类进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除分类--'+name+'？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var code = $("#selectedCataLogCode").val();
		if(existBusinessByCode(code)){
            layer.msg("该分类下有业务，删除失败",{icon: 2});
            return;
		}else {
            var _url = window.top.contextPath + window.portPath.updateCataLogStatus+"?code="+code;
            window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
                if (res.exchangeStatus == 1) {
                    layer.msg('删除成功',{icon: 1});
                    getCataloglist();
                    $("#selectedCataLogCode").val("");
                }else{
                    layer.msg(res.message,{icon: 2});
                }
            });
		}

	});
}
function openCataLogDetails(){//打开模板详情
	var code = $("#selectedCataLogCode").val();
	var _url = window.top.contextPath + window.portPath.findCataLogByCode+"?code="+code;
	window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
        if (res.exchangeStatus == 1) {
        	for(var key in res.data){
        		$("#textTemplateForm input[name='"+key+"']").val(res.data[key]);
			}
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function updateTemplateSelection(even, code, name){
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedCataLogCode").val("");
		$("#selectedCataLogName").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTemplate']").val("");
		$(even).val("true");
		$("#selectedCataLogCode").val(code);
        $("#selectedCataLogName").val(name);
	}
}
function openFileInput($index, hasContent){
	if(hasContent == 'true'){
		var laryerIndex = layer.confirm('模板已经存在，确认是否重新上传？', {
			  btn: ['取消', '确认']
			}, function(index, layero){
				layer.close(index);
			}, function(index){
				$("#orgfileUpload" + $index)[0].click(function(event){
					event.stopPropagation();
					event.preventDefault();
				});
			});
	}else{
		$("#orgfileUpload" + $index)[0].click();	
	}
}
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器