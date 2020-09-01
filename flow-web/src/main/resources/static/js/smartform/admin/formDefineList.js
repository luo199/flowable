/*
**表单定义
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	var businessId = window.common.getQueryString("businessId");
	$("#formDefineForm input[name='businessId']").val(businessId);
	$("#queryFormDefineForm input[name='businessId']").val(businessId);
	submitFormData();
	$('#queryFormDefineForm').on('submit',function(){
		queryFormDefineList();
		return false; //阻止表单默认提交
	})
	$('#formDefineSubmitButton').bind("click",function(){
		$("#formDefinePage").val(1);
	});
	$('#formDefineSubmitButton').trigger("click");
});
function queryFormDefineList(){//查询业务定义列表
	var _url = window.top.contextPath + window.portPath.queryFormDefineList;
	var queryForm = $("#queryFormDefineForm").serializeObject();//rows: 20 分页大小,page: 1  当前页
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
            window.common.drawTemplateHtml("formDefineTemplate", "#formDefineTb", {listData: res.data});
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
	$("#formDefinePage").val(opts.currentPage);
	$('#queryFormDefineForm').trigger("submit");
	return false ;
};
function openFormDetails(formId){//打开详情
	$("#businessRelationContianer", window.parent.document).attr("src",window.top.contextPath+"/static/page/smartform/admin/formEleEditor.html?formId="+formId);
}
function openElementValidForm(){
	var formId = $("#selectedFormDefine").val();
	if(formId){
		$("#businessRelationContianer", window.parent.document).attr("src",window.top.contextPath+"/static/page/smartform/admin/elementConfiguration.html?formId="+formId);
	}else{
		layer.msg('请选择一条数据进行元素配置！',{icon: 5});
		return;
	}
	
}
var layerDialog = null;
function showFormEditDialog(flag){//打开编辑弹窗
	var _title = '';
	if(flag == 'add'){
		_title = '新增';
		$(":input","#formDefineForm").not(":button,:submit,:reset").val("");
		var businessId = window.common.getQueryString("businessId");
		$("#formDefineForm input[name='businessId']").val(businessId);
	}else if(flag == 'update'){
		$(":input","#formDefineForm").not(":button,:submit,:reset,:hidden").val("");
		_title = '修改';
		var _id = $("#selectedFormDefine").val();
		if(!_id || _id == ""){
			layer.msg('请选择一条数据进行修改！',{icon: 5});
			return;
		}
		getFormDefineDetails();
	}
	layerDialog = layer.open({
		title : _title,
		type : 1,
		area: '392px',
		content : $("#formDefineFormDialog")
	});
}
function closeEditFormDialog(){//关闭编辑弹窗
	if(layerDialog){
		layer.close(layerDialog);
	}else{
		layer.closeAll();
	}
}

function getopinionList(){// 查询意见列表
	var _url = window.top.contextPath + window.portPath.getopinionListByBusinessId;
	var businessId = window.common.getQueryString("businessId");
	var params = 'businessId='+businessId
	window.common.networkConnect(_url, "POST", window.conType.conTyForm,params, function(res) {
       if(res.exchangeStatus == 1){
    	  if(res.data.length > 0){
    		   var _html = "";
    		   res.data.forEach(function(item){
    			     _html += '<div class="mar-10">'
    		           +'<label for="'+item.commentTypeId+'" class="hs-checkbox">'	
    				   +'<input name="checkboxTest"  id="'+item.commentTypeId+'" data-formId="'+item.formId+'"  value="'+item.commentTypeId+'" type="checkbox" class="checkbox-original">'
    				   +'<span class="checkbox-input">'
    				   +'<span class="checkbox-inner"></span>'
    				   +'</span>'
    				   +'<span class="checkbox-label font-16">'+item.commentTypeChName+'</span>'
    				   +'</label>'
    				   +'</div>'
    		   })  
        	   $('.related-list').html(_html); 
    	  }
    	}
       setSelectStatus()
    });
}

function setSelectStatus(){ //设置被选中的意见 颜色
	 var _id = $("#selectedFormDefine").val();
	 var obj = document.getElementsByName("checkboxTest");
	 for(var i = 0;i<obj.length;i++){
		 if(obj[i].getAttribute("data-formId") == _id){
			 $(obj[i]).prop('checked',true)
		 }
	}
}

var relatedFormDefineDialog = null;
function relatedFormDefineById(){//打开关联意见框
	    var _id = $("#selectedFormDefine").val();
		if(!_id || _id == ""){
			layer.msg('请选择一条数据进行关联！',{icon: 5});
			return;
	    }
		relatedFormDefineDialog = layer.open({
		title : '关联意见',
		type : 1,
		area: '392px',
		content : $("#relatedDialog")
	});
		getopinionList();
}
function closeRelatedDialog(){//关闭关联意见框
	if(relatedFormDefineDialog){
		layer.close(relatedFormDefineDialog);
	}else{
		layer.closeAll();
	}
}

function toRelatedFile(){ //关联文件框  保存
	 var check_val = '';
	 var obj = document.getElementsByName("checkboxTest");
	 for(var i = 0;i<obj.length;i++){
		 if(obj[i].checked){
	 			check_val+=item.value
	 			check_val+=',';
	 		}
	 }
	if (check_val == '') {
		layer.msg('请选择要关联的意见！', {
			icon : 5
		});
		return;
	}else{
		check_val = check_val.substring(0, check_val.length - 1)// 截取最后一个逗号
	}
	var params = {};
	params.formId = $("#selectedFormDefine").val();
	params.commentTypeIds = check_val;
	params = $.param(params);
	var _url = window.top.contextPath + window.portPath.setFormIdByCommentTypeIds;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			layer.msg('操作成功！', {
				icon : 1
			})
		}
		layer.close(relatedFormDefineDialog);
	});
}

function submitFormData() {//提交表单数据
	$("#formDefineForm").validate({
		rules:{
			formEgName:"required",
			formChName:"required",
			formType:"required",
			formOrder:"required",
			//formUrl:"required"
		},
		messages:{
			formEgName:"必填项！",
			formChName:"必填项！",
			formType:"必填项！",
			formOrder:"必填项！",
			//formUrl:"必填项！"
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var params = $("#formDefineForm").serializeObject();
			var _url = window.top.contextPath;
			if(params.formId){
				_url += window.portPath.updateFormDefine;
			}else{
				_url += window.portPath.saveFormDefine;
			}
			/*window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function(res) {
	            if (res.exchangeStatus == 1) {
	            	layer.close(layerDialog);
	            	queryFormDefineList();
	            	layer.msg('操作成功',{icon: 1});
	            }else{
	            	layer.msg(res.message,{icon: 2})
	            }
	        });*/
			$("#formDefineForm").ajaxSubmit({ 
				type : "POST", 
		        url: _url,
		        contentType :window.conType.conTyForm,
		        processData: false,
			    traditional: false,
			    dataType:"json",
		        success: function(data) { 
		        	if(data.exchangeStatus == 1){
		        		layer.close(layerDialog);
		            	queryFormDefineList();
		            	layer.msg('操作成功',{icon: 1});
				    }else{
					    layer.msg("数据获取失败");
					};	
				 },error:function(){
					 
				 }
		    });
		}
	});
}
function removeFormDefineById(){//根据业务id移除数据
	var _id = $("#selectedFormDefine").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var _url = window.top.contextPath + window.portPath.removeFormDefine;
		var _id = $("#selectedFormDefine").val();
		var params = "id="+_id;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	layer.msg('操作成功',{icon: 1});
	        	queryFormDefineList();
	        	$("#selectedFormDefine").val("");
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function getFormDefineDetails(){//根据表单id获取信息
	var _id = $("#selectedFormDefine").val();
	var _url = window.top.contextPath + window.portPath.getFormDefineDetatils;
	var params = "id="+_id;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	for(var key in res.data){
				$("#formDefineForm input[name='"+key+"'], #formDefineForm select[name='"+key+"']").val(res.data[key]);
			};
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}

function updateFormSelection(even, id){//更新选择状态
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedFormDefine").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxForm']").val("");
		$(even).val("true");
		$("#selectedFormDefine").val(id);
	}
}
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器