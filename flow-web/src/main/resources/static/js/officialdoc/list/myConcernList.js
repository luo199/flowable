/*我的关注 列表*/
$(function(){
	window.common.getNiceScroll(".wrapper");
	$("#tagContainer").load(window.top.contextPath + '/static/page/officialdoc/list/tagList.html');
	getConcernList();
});

function getConcernList(page){//获取关注件
	var _url = window.top.contextPath + window.portPath.getConcernList;
	var instanceDisplayQueryForm = {};
	instanceDisplayQueryForm.page = page ? page:1;
	instanceDisplayQueryForm.rows =10;
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, instanceDisplayQueryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	if(res.data){
        		window.common.drawTemplateHtml("docListTemplate", "#docListContianer", {contextPath: window.top.contextPath, listData: res.data});
        		var opts = {
       				 currentPage : res.data.currentPage,
       				 totalPages:res.data.totalPages,
       				 total:res.data.total,
       				 size:res.data.size,
       				 callBack : pageCallBack
       		 	}
       		   $.huasiBackPagination.drawPagination(opts);
        	}
        }
    });
}
function pageCallBack(opts){//分页跳转
	$("#page").val(opts.currentPage);
	getConcernList(opts.currentPage);
};

function removeRecycle(id,totalNum,currentSize,currentPage){ //放至回收站
	var gotoPage = parseInt(totalNum) % parseInt(currentSize) == 1 ? parseInt(currentPage) -1: parseInt(currentPage) 
    layer.confirm('您确定要将该文件放入回收站吗？', {
		  btn: ['取消', '确认']
		}, function(index, layero){
			layer.close(index);
		}, function(index){
			var _url = window.top.contextPath + window.portPath.removerecycleById;
			var params = "instanceId=" + id;
			window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		        if (res.exchangeStatus == 1) {
		        	layer.msg('操作成功',{icon: 1});
		        	getConcernList(gotoPage);
		          }else{
		        	layer.msg(res.message,{icon: 2});
		        }
		    });
		});
  }


function openDetails(instanceId){//打开详情页
	$("#centerContianer", window.parent.document).attr("src", window.top.contextPath + '/static/page/officialdoc/detail/detail.html?instanceId='+instanceId);
}
function openInput(){ //显示输入框
	$('.label-add').hide();
    $('.label-input').show();
}
function showDialog(index){//打开弹框
	$('#label-dialog'+index).show();
	 $('.show-label'+index).addClass('set-label');
}
function closeDIalog(index,direction){//隐藏弹框
	$('#label-dialog'+index).hide();
	if(!direction){
		$('.right-block span').removeClass('set-label');
	}
	
}
var currentInstanceId = '',currentLabelIndex = null;
function openDialog(id, parentIndex) { // 打开标签弹框
    $('#label-dialog'+parentIndex).show();
    $('.show-label'+parentIndex).addClass('set-label');
    currentLabelIndex = parentIndex
    var params = "instanceId=" + id;
	currentInstanceId = id;
	var _url = window.top.contextPath + window.portPath.getLabelListData;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			window.common.drawTemplateHtml("labelListTemplate", "#label-dialog"+parentIndex, {listData: res.data, parentIndex: parentIndex});
		} else {
			layer.msg(res.message, {
				icon : 2
			});
		}
	});
}
function onmouseoutChangeImgSrc(src, elid) {// 鼠标移出 修改img
	setTimeout(function() {
		$('#' + elid).attr('src', src);
	})
}

function setStarStatus(id, direction, data, elid,totalNum,currentSize,currentPage){
	// 点星星
	var hasAtt = 0;
	if (elid == 'change') { // 要添加
		saveCurrentLabelList(direction, id, data,function() {
			$('#'+ elid + data).hide();
			$('#change-sel' + data).show();
		})
	} else if (elid == 'change-sel') { // 要删除
		delCurrentLabelList(hasAtt, id,data,totalNum,currentSize,currentPage, function() {
			$('#'+ elid + data).hide();
			$('#change' + data).show();
		})
	}

}
function toSaveLabel(id, data,hasAtt) { // 点击该标签收藏
    var starStaus = [];// 标签列表被选中的个数的数组
	var obj = document.getElementsByClassName('label-hasatt');
	for(var i =0;i<obj.length;i++){
		if(obj[i].getAttribute("data-hasAtt")!= 0){
			starStaus.push(1)
		}
	}
	var direction="";
	// 标签里面的选项
	if (hasAtt != 0) { // 被选中 要删除
		delCurrentLabelList(hasAtt, id,data, function() {
			if (starStaus.length == 1) {
				$('#change' + data).show();
				$('#change-sel' + data).hide();
			}
		})
	} else { // 未选中 要添加
		saveCurrentLabelList(direction, id,data, function() {
			$('#change' + data).hide();
			$('#change-sel' + data).show();
		})
	}
}
// 取消收藏
function delCurrentLabelList(hasAtt, id,data,totalNum,currentSize,currentPage, fun) {
	var gotoPage = parseInt(totalNum) % parseInt(currentSize) == 1 ? parseInt(currentPage) -1: parseInt(currentPage);
	var params = {};
	if (hasAtt != 0) {
		params.tagId = id;
		params.instanceId = currentInstanceId;
	} else {
		params.instanceId = id;
		params.tagId = '';
	}
	params = $.param(params);
	var _url = window.top.contextPath + window.portPath.delCurrentLabelList;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			layer.msg('操作成功', {
				icon : 1
			});
			$('#label-dialog'+data).hide();
			$('.right-block span').removeClass('set-label');
			getConcernList(gotoPage);
			fun();
		} else {
			layer.msg(res.message, {
				icon : 2
			});
		}
	});
}
// 添加收藏
function saveCurrentLabelList(direction, id,data, fun) {
	var params = {};
	if (direction == 'collect') {
		params.instanceId = id;
		params.tagId = '';
	} else {
		params.tagId = id;
		params.instanceId = currentInstanceId;
	}
	params = $.param(params);
	var _url = window.top.contextPath + window.portPath.saveCurrentLabelList;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			layer.msg('操作成功', {
				icon : 1
			});
			$('#label-dialog'+data).hide();
			fun();
		} else {
			layer.msg(res.message, {
				icon : 2
			});
		}
	});
}

function submitData(e) {// 新建标签
	if (e.keyCode == 13) {
		e.preventDefault();
		var _url = window.top.contextPath + window.portPath.addLabelList;
		var data = $('.label-input').val();
		var params = "tagName=" + data;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
			if (res.exchangeStatus == 1) {
				layer.msg('操作成功', {
					icon : 1
				});
			    $('#label-dialog'+currentLabelIndex).hide();
				$('.label-input').val("");
			} else {
				layer.msg(res.message, {
					icon : 2
				});
			}
		});
	}
}

template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器