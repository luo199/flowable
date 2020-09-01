$(function(){
	window.common.getNiceScroll(".wrapper");
	var instanceId = window.common.getQueryString("instanceId");
	$("#attachmentContent").attr("src",window.top.contextPath + "/static/page/officialdoc/detail/attachment.html?attType=attachment&instanceId="+instanceId);//附件
	$("#beijingAttachmentContent").attr("src",window.top.contextPath + "/static/page/officialdoc/detail/beigjing.html?attType=beijing&instanceId="+instanceId);//相关文件（背景文件）
	$("#relevantContent").load(window.top.contextPath + "/static/page/officialdoc/detail/relevantList.html?instanceId="+instanceId);//关联文件
	getDocDetails(instanceId);
});
function changCenterView(obj, src){//改变详情页中间区域显示内容
	$("#ifrMainContent").show();
	$("#formMainContent").hide();
	if(src){
		var instanceId = window.common.getQueryString("instanceId");
		if(src.indexOf("?") > 0){
			src += "&instanceId="+instanceId;
		}else{
			src += "?instanceId="+instanceId;
		}
		$("#ifrMainContent").attr("src", window.top.contextPath + src);
	}
	setNodeChecked(obj);
}
function changFormTabCenterView(obj){//表单定义---改变详情页中间区域显示内容
	$("#ifrMainContent").hide();
	$("#formMainContent").show();
	$("#formMainContent>div:not(.noneDisplay)").addClass("noneDisplay");
	$("#formMainContent #"+obj+"FormContent").removeClass("noneDisplay");
	setNodeChecked(obj+"FormTab");
}

function toggleBtn(container){ //展开/隐藏 
	 $("#"+container).toggle();
}
var uploadPdfLayerDialog = null;
function addDocument(){
	uploadPdfLayerDialog = layer.open({
		title : '上传PDF',
		type : 1,
		content : $("#uploadPdfDialog")
	});
}
$(document).on("change","#uploadPDF",function(){   //显示文件名
	   var filename = $("#uploadPDF")[0].files[0].name
	   $('.showFile').val(filename)
 }); 

$(document).on("click",".uploadFile",function(){  //上传文件
   var _url = window.top.contextPath + window.portPath.saveOrUpdateDoc;
   var instanceId = window.common.getQueryString("instanceId");
   var fileData = $("#uploadPDF")[0].files[0];
   var formData = new FormData();
   formData.append("docFile", fileData);
   window.common.networkConnect(_url +"?instanceId="+instanceId, "POST", false, formData, function(res) {
		
   }); 
}); 
function setNodeChecked(obj){//设置节点选中效果
	if(obj){
	    $(".parent-node").addClass("noneDisplay");
	    $(".parent-node~span:not(.icon-add)").removeClass("theme-color");
	    $(".parent-node~span .right-title").removeClass("theme-color");
	    $("#"+obj+" .parent-node").removeClass("noneDisplay"); 
	    $("#"+obj+" .parent-node~span").addClass("theme-color");
	    $("#"+obj+" .parent-node~span").find(".right-title").addClass("theme-color");
	    $("div.right-block-sel").removeClass("right-block-sel");
	    $("#"+obj).addClass("right-block-sel");
    }
}
function getDocDetails(instanceId){//通过实例id获取正文详情
	var _url = window.top.contextPath + window.portPath.getDocByInstanceId;
	var params = 'instanceId='+ instanceId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(res.data != null){
        		refreshDocument();
        		$("#isHasDoc").val(res.data.docId);
        		$("#browseDoc").removeAttr("disabled").addClass("pointer");
        	}
        }else{
        	layer.msg("服务异常", {icon: 2});
        }
    });
}
function addZW(flag){//添加或者编辑正文
	lockDoc(function(msg){
		if(msg.LOCK ==0 ){
			layer.confirm('文件进入编辑锁定状态，其他人员无法修改此文件，请修改完成后及时保存并退出', {
			  btn: ['取消', '确认']
			}, function(index, layero){
				unlockDoc();
				layer.close(index);
			}, function(index){
				changCenterView("document", "/static/page/officialdoc/detail/editDocment.html?docId="+$("#isHasDoc").val());
				if(flag == 0){
					refreshDocument();
				}
			});
		}
	});
}
function newWindowEdit(){//新窗口编辑正文
	lockDoc(function(msg){
		if(msg.LOCK ==0 ){
			layer.confirm('文件进入编辑锁定状态，其他人员无法修改此文件，请修改完成后及时保存并退出', {
			  btn: ['取消', '确认']
			}, function(index, layero){
				unlockDoc();
				layer.close(index);
			}, function(index){
				var editUrl = window.top.contextPath + "/static/page/officialdoc/detail/editDocment.html?docId="+$("#isHasDoc").val();
				window.open(editUrl);
			});
		}
	});
}
function lockDoc(callback){//加锁正文
	var _url = window.top.contextPath + window.portPath.lockDoc;
    var instanceId = window.common.getQueryString("instanceId");
    var params = "lockSource=3&instanceId="+instanceId;/**lockSource 1-手机 2-pad 3-pc 4-微信 */
    window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback){callback(res.data)}
        }else{
        	layer.msg("服务异常", {icon: 2});
        }
    });
}
function unlockDoc(flag){//解锁正文
	var _url = window.top.contextPath + window.portPath.unlockDoc;
    var instanceId = window.common.getQueryString("instanceId");
    var params = "lockSource=3&instanceId="+instanceId;/**lockSource 1-手机 2-pad 3-pc 4-微信 */
    window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(flag && flag == 1){//退出编辑并解锁
        		$("#rightFormDefinedTabTb span[is-first-show=true]").click();//中间部分默认展示第一个表单的内容
        	}
        }else{
        	layer.msg("服务异常", {icon: 2});
        }
    });
}
function browseZW(){//预览正文--下载至本地查看
	var _url = window.top.contextPath + window.portPath.downloadDocByDocId + "?fileId="+$("#isHasDoc").val();
    window.location.href= _url;
}
function refreshDocument(){//刷新正文
	$("#addDocumentBtn").hide();
	$("#isNotDoc").hide();
	$("#isNotDoc").parent("button.browse_zw").removeAttr("disabled");
	$("#isNotDoc").parent("button.browse_zw").addClass("pointer");
	$("#isBrowserZw").show();
	$("#editDocId").show();
	$("#newEditDocId").show();
}
var relavantLayerDialog = null;
function openRelatedLayer(flag){//打开关联文件弹窗
	var instanceId = window.common.getQueryString("instanceId");
    relavantLayerDialog = layer.open({
		title : [''],
		type : 2,
		skin: 'related-popup',
		maxmin : true,
		area: ['70%','70%'],
		content : window.top.contextPath + "/static/page/officialdoc/detail/relevantDialog.html?instanceId="+instanceId+"&flag="+flag
	});
    setNodeChecked(flag);
}