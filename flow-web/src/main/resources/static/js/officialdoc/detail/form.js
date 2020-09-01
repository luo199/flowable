/*表单*/
$(function(){
	window.common.getNiceScroll(".wrapper");
	var instanceId = window.common.getQueryString("instanceId");
	getInstanceDetails(instanceId);
	$('body').bind('input propertychange', function(evt) {
		var $name = evt.target.name;
		$('body').find("*[data-name='" + $name + "']").text(evt.target.value);
	});
});
function isSetDate(){//onpicked日期填写后的回调事件
	$('body').find("*[data-name='" + this.name + "']").text(this.value);
}
function getInstanceDetails(instanceId){//获取实例详情
	var _url = window.top.contextPath + window.portPath.getInstanceDetails;
	var params = "instanceId=" + instanceId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			setFormHead(res.data.instanceType, res.data.bureauName, res.data.businessName);
		}
	});
}
function saveOperation(){//保存表单
	var validSuccessFlag = true;//表单验证是否通过
	var formList = [];
	$("#formMainContent>div").each(function(index, evt){
		 var runningNumber = $(evt).find("*[data-name='runningNumber']").text();
		 var formObj = $(evt).find("form").serializeObject();
		 if(runningNumber) formObj.runningNumber = runningNumber;
		 formObj.formId = $(evt).attr("form-id");
		 formList.push(formObj);
		 var flag = $(evt).find("form").valid();//检查是否验证通过，注意表单中name不要为空
		 if(flag == false){
			 validSuccessFlag = flag;
		 }
	});
	if(validSuccessFlag == false){//多表单的验证没有成功
		layer.msg('表单没有通过验证', {icon: 2});
		return;
	}
	var codeValue = $("#formMainContent>div:visible form input[has-code='true']").val();
	var attaParam = {};
	attaParam.codeValue = codeValue ? codeValue : '';
	attaParam.codeId = codeValue ? $("#formMainContent>div:visible form input[has-code='true']").attr("code-id") : '';
	attaParam.nowValue = codeValue ? $("#formMainContent>div:visible form input[has-code='true']").attr("now-value") : '';
	var params = "params="+JSON.stringify(formList)+"&instanceId="+window.common.getQueryString("instanceId")+"&attaParam="+JSON.stringify(attaParam);
	var _url = window.top.contextPath + window.portPath.saveFormData;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		 if (res.exchangeStatus == 1) {
        	layer.msg('操作成功',{icon: 1});
		}else{
        	layer.msg(res.message,{icon: 2})
        }
    });
}
function setFormHead(instanceType, bureauName, businessName){//设置表头
	var _html = '';
	var temp1 = businessName == '办文' ? '处理表' : (businessName == '发文' ? '呈批表' : '文件信息表');
	if(parseInt(instanceType) == 1){//管委会文
		_html = '<tr>'+
					'<td height="30" width="65%"><p class="span-bureau-gw3">中共深圳市光明区委员会</p></td>'+
					'<td rowspan="2" height="40" width="35%"><p class="span-bureau1">' + temp1 +'</p></td>'+
				'</tr>'+
				'<tr><td width="65%" height="30"><p class="span-bureau-gw2">深圳市光明区人民政府</p></td></tr>';
		if(businessName == '对外发文'){$("input[name='jinjichengdu']").val("平件");}
	}else if(parseInt(instanceType) == 2){//非管委会文
		_html = '<tr>'+
					'<td height="30" align="center"><p class="span-bureau">' + (window.CONST.orgName + bureauName + temp1) + '</p></td>'+
				'</tr>';
	}
	$("#wjclFormHead tbody").html(_html);
	$("input[name='formdeptname']").val(window.CONST.orgName + bureauName);
}
function openCommentEditDialog(action, isFirstCommentType, p1, p2){//打开意见弹窗
	var params = action == 'add' ? "instanceId="+p1+"&commentTypeId="+p2+"&action="+action+"&isFirstCommentType="+isFirstCommentType : "commentId="+p1+"&action="+action+"&isFirstCommentType="+isFirstCommentType;
	var commentLayerDialog = layer.open({
		title : ['意见填写<br>(注：如有增加附件，请在意见中说明详见附件)', 'text-align: center'],
		type : 2,
		skin: 'comment-popup',
		maxmin : true,
		area: ['70%','70%'],
		content : window.top.contextPath + "/static/page/officialdoc/detail/commentEditDialog.html?"+params
	});
}
function openNumberCardForm(even){//打开办文编号/发文字号弹窗
	var numberType = $(even).siblings("input[has-code='true']").attr("name");
	var commentLayerDialog = layer.open({
		title : ['办文编号格式', 'text-align: center'],
		type : 2,
		skin: 'comment-popup',
		maxmin : true,
		area: ['70%','70%'],
		content : window.top.contextPath + "/static/page/officialdoc/detail/serialNumberDialog.html?numberType="+numberType
	});
}
function showViewOrEditForm(even, showFormId, hideFormId){//显示/隐藏 基本信息/公文办理单
	$(even).siblings('.button-primary').removeClass("button-primary");
	$(even).addClass("button-primary");
	$(even).parent().parent().find("#"+showFormId).show();
	$(even).parent().parent().find("#"+hideFormId).hide();
}

function unitinnerOperation(){// 单位内办理
	var selectPsrsonDialog = layer.open({
		title : ['选择人员', 'text-align: left'],
		type : 2,
		skin: 'comment-popup',
		maxmin : true,
		area: ['90%','80%'],
		content : window.top.contextPath + "/static/page/officialdoc/detail/selPersonDialog.html"
	});
}
function turnoutOperation(){
	layer.msg("转外单位！");
}