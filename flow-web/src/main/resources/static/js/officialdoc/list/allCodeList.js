/*
 * 系统编号
 * */
$(function(){
	window.common.getNiceScroll("#wrapper");
	$("#queryAllCodeListForm").on('submit',function(){
		getserialNumberList();
		 return false;
	});
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
	queryForm.codeType = $('.input-inner').val();
    var _url = window.top.contextPath + window.portPath.getAdminSerialNumberList;
	window.common.networkConnect(_url, "POST", window.conType.conTyJson,queryForm, function(res) {
		 if (res.exchangeStatus == 1) {
	        	window.common.drawTemplateHtml("allCodeListTemplate", "#allCodeListTb", {listData: res.data});
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
function pageCallBack(opts){//分页跳转
	getserialNumberList(opts.currentPage);
}