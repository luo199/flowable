/*
 * 基本信息  右侧 关联文件弹框
 * */
$(function(){
	getRelavantData();
	window.common.getNiceScroll("#wrapper");
	$("#queryrelavantForm").on('submit',function(){
		 getRelavantData();
		 return false;
	});
});
function getRelavantData(page){// 获取右侧关联的数据
  var queryForm ={};
  if(page){
		queryForm.page = page
	}else{
		queryForm.page = 1
	}
	queryForm.rows = 10;
	queryForm.title = $('.input-inner').val();
	queryForm = $.param(queryForm);
	var _url = window.top.contextPath + window.portPath.getRelatedList;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm,queryForm, function(res) {
		 if (res.exchangeStatus == 1) {
	        	window.common.drawTemplateHtml("relavantTemplate", "#relavantTb", {listData : res.data});
	        	var opts = {
	        			 currentPage :res.data.currentPage ,
	        			 size:res.data.size,
	        			 totalPages:res.data.totalPages,
	        			 total:res.data.total,
	        			 maxPageNumber: 10,
	        			 callBack : pageCallBack
	        	}
	        	$.huasiBackPagination.drawPagination(opts);
	        }
    });
}
var check_val ='';
function getChexkbox(){ //获取所有被被选中的instanceId
	var obj = document.getElementsByName("checkboxTest");
 	obj.forEach(function(item){
 		if(item.checked && check_val.indexOf(item.value) == -1){
 			check_val+=item.value
 			check_val+=',';
 		}else if(!item.checked && check_val.indexOf(item.value) != -1){
 			check_val = check_val.split(",");
 			check_val.forEach(function(it,index){
 				if(item.value == it){
 					check_val.splice(index,1)
 				}
 			})
 			check_val=check_val.join(',');
 		}
 	})
}

function toRelavantList() {// 关联文件
	if (check_val == '') {
		layer.msg('请选择要关联的文件！', {
			icon : 5
		});
		return;
	}
	var params = {};
	var instanceId = window.common.getQueryString("instanceId");// 当前表单的instanceId
	params.instanceId = instanceId;
	check_val = check_val.substring(0, check_val.length - 1)// 截取最后一个逗号
	params.relatedInstanceId = check_val;
	params = $.param(params);
	var _url = window.top.contextPath + window.portPath.toRelatedList;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			layer.msg('操作成功！', {
				icon : 1
			})
			var obj = document.getElementsByName("checkboxTest");
			obj.forEach(function(item) {
				item.checked = false;
			})
			check_val = '';
		}
		window.parent.getRelavantList();
	});
}

function pageCallBack(opts){//分页跳转
	$("#page").val(opts.currentPage);
	getRelavantData(opts.currentPage);
};