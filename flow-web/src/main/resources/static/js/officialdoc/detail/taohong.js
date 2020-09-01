/*
 * 套红
 * */
$(function(){
	window.common.getNiceScroll(".wrapper");
	getTaohongTemplateData();
	/*$('#queryTaohongForm').on('submit',function(){
		getTaohongTemplateData();
		return false; //阻止表单默认提交
	})
	$('#taohongSubmitButton').bind("click",function(){
		$("#page").val(1);
	});
	$('#taohongSubmitButton').trigger("click");*/
});
function getTaohongTemplateData(){// 获取右侧关联的数据
	var _url = window.opener.window.top.contextPath + window.portPath.queryAllRedHeaderList;
	//var params = "bureauGuid="+window.opener.window.top.bureauId+"&deptGuid="+window.opener.window.top.deptId;
	var redHeaderQueryForm = {};
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, redHeaderQueryForm, function(res) {
		if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("taohongTemplate", "#taohongTb", {listData: res.data});
        	var opts = {
       			 currentPage :res.data.currentPage+1,
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
function pageCallBack(opts){//分页跳转
	$("#page").val(opts.currentPage);
	getRelavantData(opts.currentPage);
};
function saveTaohong(selectedTaohongId, selectedTaohongName){//确定套红
	window.opener.selectedTaohong(selectedTaohongId, selectedTaohongName);
	window.close();
}