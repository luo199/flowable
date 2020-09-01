/*
 * 文件信息表   编号弹框
 * */
$(function(){
	window.common.getNiceScroll("#wrapper");
	addAndUpdate();
	$('.add-update-box').hide();
	getserialNumberList();
});
function getserialNumberList(page){// 获取编号的数据
  var queryForm ={};
  if(page){
		queryForm.page = page
	}else{
		queryForm.page = 1
	}
	queryForm.rows = 10;
	queryForm.deptId = window.top.bureauId;
    var _url = window.top.contextPath + window.portPath.getSerialNumberList;
	window.common.networkConnect(_url, "POST", window.conType.conTyJson,queryForm, function(res) {
		 if (res.exchangeStatus == 1) {
        	 window.common.drawTemplateHtml("serialNumberTemplate", "#serialNumberTb", {listData: res.data});
        	 var opts = {
        			 currentPage :res.data.currentPage ,
        			 size:res.data.size,
        			 totalPages:res.data.totalPages,
        			 total:res.data.total,
        			 callBack : pageCallBack
        	}
        	$.huasiBackPagination.drawPagination(opts);
         }
    });
}

function removeSerialNumById(){
	 if(check_val==''){
		   layer.msg('请选择一条数据进行删除！',{icon:5});
		   return false;
	 }else{
		 layer.confirm('您确定要删除该文件吗？', {
			  btn: ['取消', '确认']
			}, function(index, layero){
				layer.close(index);
			}, function(index){
				var params ={};
				params.codeId = check_val;
				params = $.param(params);
				var _url = window.top.contextPath + window.portPath.delSerialNumberListById;
				window.common.networkConnect(_url, "POST", window.conType.conTyForm,params, function(res) {
					 if (res.exchangeStatus == 1) {
						 getserialNumberList();
                    }
			    });
			});
	 }
	
}

function addAndUpdate(){ //新增或修改
	$("#add-update-serialnumber").validate({
		rules:{
			codeType: {"required": true,minlength:1},
			prefix: {"required": true,minlength:1},
			yearFormat: {"required": true},
			monthFormat: {"required": true},
			numberFormat: {"required": true}
		},
		messages:{
			codeType: {"required": "必填项！"},
			prefix: {"required": "必填项！"},
			yearFormat: {"required": "必填项！"},
			monthFormat: {"required": "必填项！"},
			numberFormat: {"required": "必填项！"}
		},
	     onsubmit:true,
		 errorPlacement: function(error, element) {  
			    error.appendTo(element.parent());  
			},
		 submitHandler: function(form) {
			 var paramsObject = $("#add-update-serialnumber").serializeObject();
			 if(paramsObject.monthFormat == '无'){
				 paramsObject.monthFormat = '';
			 }
			 paramsObject = $.param(paramsObject)
			 var _url = '';
			 if(status == 'update'){
				 _url = window.top.contextPath + window.portPath.updateSerialNumberList;
			 }else{
				 _url = window.top.contextPath + window.portPath.addSerialNumberList; 
			 }
		      window.common.networkConnect(_url, "POST", window.conType.conTyForm,paramsObject, function(res) {
					 if (res.exchangeStatus == 1) {
						 layer.msg('操作成功',{icon: 1});
						 $('.add-update-box').hide();
						 getserialNumberList();
                     }
			});
		} 
	})
}
	
function closeBox(){ // 关闭下面输入的框
	$('.add-update-box').hide();
}

var check_val ='';
function getChexkbox(){ //获取被选中的Id
 	var obj = document.getElementsByName("checkboxTest");
 	obj.forEach(function(item){
 		if(item.checked){
 			check_val = item.value
 		}
 	})
}
var status ='';
function showSerialbox(flag){ //打开下面输入的框
	status = flag;
	if(flag == 'add'){
	  $(':input','#add-update-serialnumber').not(':button,:submit').val('');
      $('.add-update-box').show();
   }else{
	   if(check_val==''){
		   layer.msg('请选择一条数据进行修改！',{icon:5});
		   return false;
	   }else{
		   var params = {};
		   params.codeId = check_val;
		   params = $.param(params);
		   var _url = window.top.contextPath + window.portPath.getSerialNumberListById;
			window.common.networkConnect(_url, "POST", window.conType.conTyForm,params, function(res) {
				 if (res.exchangeStatus == 1) {
					for(var key in res.data){
						 $(".add-update-box input[name='"+key+"']").val(res.data[key]);
						 $(".add-update-box select[name='"+key+"']  option[value='"+res.data[key]+"']").attr("selected","selected");
	                   }
					$('.add-update-box').show();
					check_val = '';
	            }
		    });
	    }
	  
   }
}

function toParentData(example, codeId, nowValue){
	var numberType = window.common.getQueryString("numberType");
	$(window.parent.document).find("#formMainContent>div:visible form input[name='"+numberType+"']").val(example);//子页面的数据赋值给父页面指定的Id
	$(window.parent.document).find("#formMainContent>div:visible form input[name='"+numberType+"']").attr("code-id", codeId);
	$(window.parent.document).find("#formMainContent>div:visible form input[name='"+numberType+"']").attr("now-value", nowValue);
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}

function pageCallBack(opts){//分页跳转
	getserialNumberList(opts.currentPage);
};