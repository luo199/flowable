$(function(){
	getRelavantList();
});

function getRelavantList(){// 获取右侧关联的数据
	var instanceId = window.common.getQueryString("instanceId");//获取父页面的instanceId
    var queryForm = 'instanceId='+instanceId;
    var _url = window.top.contextPath + window.portPath.getRelatedListById;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm,queryForm, function(res) {
		 if (res.exchangeStatus == 1) {
		    window.common.drawTemplateHtml("relavantTemplate", "#related", {listData : res.data});
        	$("#relevantId .data-num").text(res.data.length);//子页面的数据赋值给父页面指定的Id
        }
    });
}

function delRelated(id){//根据 id 删除数据
	layer.confirm('您确定要删除该文件吗？', {
		  btn: ['取消', '确认']
		}, function(index, layero){
			layer.close(index);
		}, function(index){
			var params ={};
			params.relatedId = id
			params = $.param(params);
			var _url = window.top.contextPath + window.portPath.delRelatedList;
			window.common.networkConnect(_url, "POST", window.conType.conTyForm,params, function(res) {
				if (res.exchangeStatus == 1) {
				 	layer.msg('操作成功！',{icon:1});
		        	getRelavantList();
		        }
		    });
		});
}