/*详情页*/
$(function(){
	window.common.getNiceScroll(".wrapper");
	var instanceId = window.common.getQueryString("instanceId");
	$("#detailRightContainer").load(window.top.contextPath + "/static/page/officialdoc/detail/detailRight.html?instanceId"+instanceId);
	getInstanceDetails(instanceId, function(msg){
		queryFormList(msg.instanceId, msg.bureauGuid);
	});
});
function getInstanceDetails(instanceId, callback){//获取实例详情
	var _url = window.top.contextPath + window.portPath.getInstanceDetails;
	var params = "instanceId=" + instanceId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			if(callback) callback(res.data);
		}else{
        	layer.msg("服务异常", {icon: 2});
        }
	});
}
function drawFromMainView(listData, len, bureauGuid){//加载中间多表单内容
	if(listData.length > 0){
        if(len == undefined) len = listData.length-1;
        var _html = '<div id="'+listData[len].formEgName+'FormContent" form-id="'+listData[len].formId+'" class="wrapper h100 noneDisplay"><div>';
		$("#formMainContent").prepend(_html);
		if(listData[len].formUrl){
			$("#formMainContent #"+listData[len].formEgName+"FormContent").load(window.top.contextPath + listData[len].formUrl);
			if(len>0){
            	len --;
            	drawFromMainView(listData, len, bureauGuid);
            }
		}else{
			getFormSource(listData[len].formId, function(data){
				$("#formMainContent #"+listData[len].formEgName+"FormContent").html(data);
	            if(len>0){
	            	len --;
	            	drawFromMainView(listData, len, bureauGuid);
	            }
	            return;
	        });
		}
		var instanceId = window.common.getQueryString("instanceId");
		getCommentFtlMap(instanceId, listData[len].formId, "#formMainContent #"+listData[len].formEgName+"FormContent #commentTemplateId");
	}
	if(listData.length>0 && len == 0){
		$("#rightFormDefinedTabTb span[is-first-show=true]").click();//中间部分默认展示第一个表单的内容
		var formIds = _.map(listData, 'formId');//取数组里面的formId单独成一个数组
		getFormDetails(instanceId, bureauGuid, formIds);
		getOperators(instanceId, formIds);
	}
}
function getjudgeOrgDept(bureauGuid, selectedVal, formId){//判断当前人是否是文件法规科
	var _url = window.top.contextPath + window.portPath.getJudgeOrgDept;
    window.common.networkConnect(_url, "POST", window.conType.conTyJson,"", function(res) {
	    if(res.exchangeStatus == 1){
	    	getSelfPerson(res.data, bureauGuid, selectedVal, formId);  
	    }
	});
}
function getSelfPerson(isOrg, bureauGuid, selectedVal, formId){//获取签发人
	var _url = '',params='';
	if(isOrg == true){
	 _url = window.top.contextPath + window.portPath.getDistrictLeader;//获取区领导
	}else{
	 _url = window.top.contextPath + window.portPath.getBureauLeader;//获取局领导
	  params = 'bureauId='+ bureauGuid
	}
	window.common.networkConnect(_url, "POST", window.conType.conTyForm,params, function(res) {
		if(res.exchangeStatus == 1){
			var _html = "";
			res.data.forEach(function(item){
				_html +='<option value="'+item.name+'">'+item.name+'</option>'
			});
			$("#formMainContent div[form-id='"+formId+"'] select[name='qianfaren']").html(_html);
			$("#formMainContent div[form-id='"+formId+"'] select[name='qianfaren']").val(selectedVal);
		}
	});
}
function getFormDetails(instanceId, bureauGuid, formIds){//获取表单详情
	$("#formMainContent input:not([type='button']):not([type='submit']):not([type='hidden']), #formMainContent textarea").val("");
	var _url = window.top.contextPath + window.portPath.getFormDetails;
	var params = "instanceId=" + instanceId + "&formIds=" + formIds;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	for(var formKey in res.data.formMap){
        		var rules = {},messages = {};
        		res.data.formMap[formKey].forEach(function(element, index){
        			$("#formMainContent div[form-id='"+formKey+"'] form *[name='"+element.key+"']").each(function(j,item){
        				if($(this).attr("date-fmt")){//标签上写自定义属性例如date-fmt="yyyy-MM-dd"(针对时间格式的)
            				item.value = (element.value[j] == undefined || element.value[j] == null) ? '' : window.common.dateFormat(element.value[j], $(this).attr("date-fmt"));        					
        				}else{
        					item.value = (element.value[j] == undefined || element.value[j] == null) ? '' : element.value[j];
        				}
        			});
        			$("#formMainContent div[form-id='"+formKey+"'] form *[data-name='"+element.key+"']").each(function(j,item){
        				if($(this).attr("date-fmt")){//标签上写自定义属性例如date-fmt="yyyy-MM-dd"(针对时间格式的)
            				item.textContent = (element.value[j] == undefined || element.value[j] == null) ? '' : window.common.dateFormat(element.value[j], $(this).attr("date-fmt"));        					
        				}else{
        					item.textContent = (element.value[j] == undefined || element.value[j] == null) ? '' : element.value[j];
        				}
        			});
            		if(element.key == 'qianfaren'){
						getjudgeOrgDept(bureauGuid, element.value, formKey);//获取签发人
					}
            	});
        	}
        	setFormListValidator(res.data.formMap);
        }
    });
}
function setFormListValidator(formList){//设置表单验证
	for(var formKey in formList){
		var rules = {},messages = {};
		formList[formKey].forEach(function(element, index){
			rules[element.key] = {},messages[element.key] = {};
			if(element.isEmpty == 2){//是否为空 （1是2否，下同）
				rules[element.key].required = true;
				messages[element.key].required = element.emptyMsg;
			}
			if(element.isLengthMin == 1){//是否有最小长度
				rules[element.key].minlength = element.lengthMin;
				messages[element.key].minlength = element.lengthMinMsg;
			}
			if(element.isLengthMax == 1){//是否有最大长度
				rules[element.key].maxlength = element.lengthMax;
				messages[element.key].maxlength = element.lengthMaxMsg;
			}
			if(element.isNumericMin == 1){//是否不得小于某个数字
				rules[element.key].min = element.numericMin;
				messages[element.key].min = element.numericMinMsg;
			}
			if(element.isNumericMax == 1){//是否不得大于某个数字
				rules[element.key].max = element.numericMax;
				messages[element.key].max = element.numericMaxMsg;
			}
			if(element.sRegularExpression == 1){//是否正则表达式
				addValidatorRules("regularExpression", element.regularExpression, element.errorMessage);
				messages[element.key]["regularExpression"] = true;
			}
		});
		$("#formMainContent div[form-id='"+formKey+"'] form").validate({
			rules: rules,
			messages: messages,
			ignore: "",//不验证的元素
		});
	}
	if ($.validator) {//有name相同字段时，validate只校验表单元素name第一个值是否通过校验,修改property上的validate原型方法,在表单上,相同的name,需要给不同的id
		$.validator.prototype.elements = function() {
            var validator = this,
            rulesCache = {};
            return $([]).add(this.currentForm.elements).filter(":input").not(":submit, :reset, :image, [disabled]").not(this.settings.ignore).filter(function() {
                // 这里加入ID判断
                var elementIdentification = this.id || this.name; ! elementIdentification && validator.settings.debug && window.console && console.error("%o has no id nor name assigned", this);
                if (elementIdentification in rulesCache || !validator.objectLength($(this).rules())) return false;
                rulesCache[elementIdentification] = true;
                return true;
            });
        };
    }
}
function addValidatorRules(name, rule, message){//添加自定义验证规则
	jQuery.validator.addMethod(name, function(value, element) {   
	    var tel = rule;
	    return this.optional(element) || (tel.test(value));
	}, message);
}
function getCommentFtlMap(instanceId, formId ,commentTemplateId){//通过实例id获取该意见类型下的所有意见
	var _url = window.top.contextPath + window.portPath.getCommentFtlMap;
	var params = "instanceId=" + instanceId + "&formId=" + formId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		var _html = '';
		for(var key in res.data){
			_html += res.data[key];
		}
		$(commentTemplateId).html(_html);
	});
}
function getFormSource(formId, callback){//获取表单源码HTML
	var _url = window.top.contextPath + window.portPath.getFormSource;
	var params = "formId=" + formId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
			if(callback) callback(res.data);
		}else{
        	layer.msg("服务异常", {icon: 2});
        }
	});
}
function queryFormList(instanceId, bureauGuid){//通过实例id获取表单
	var _url = window.top.contextPath + window.portPath.queryFormList;
	var params = 'instanceId=' + instanceId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(res.data && res.data.length > 0){
        		window.common.drawTemplateHtml("rightFormDefinedTabTemplate", "#rightFormDefinedTabTb", {listData : res.data});
        	}else{
        		$('#rightFormDefinedTabTb').html("");
        	}
        	drawFromMainView(res.data, res.data.length - 1, bureauGuid);
        }else{
        	layer.msg("服务异常", {icon: 2});
        }
    });
}
function getOperators(instanceId, formIds){//通过(instanceId)实例id和(formIds)多表单的id集合获取表单的操作按钮
	var _url = window.top.contextPath + window.portPath.getOperators;
    var params = "instanceId="+instanceId+"&formIds="+formIds;
    window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("headOperationButtonTemplate", "#headOperationButtonTb", {listData : res.data});
        }else{
        	layer.msg("服务异常", {icon: 2});
        }
    });
}
function officeEdit(attachId, instanceId, callback){//点击编辑附件需要判断附件是否被锁定
    var _url = window.top.contextPath + window.portPath.lockAttachement;
    var params = "lockSource=3&attachmentId="+attachId+"&instanceId="+instanceId;/**lockSource 1-手机 2-pad 3-pc 4-微信 */
    window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback){callback(res.data)}
        }else{
        	layer.msg("服务异常", {icon: 2});
        }
    });
}
function closefjLock(attachId, instanceId){//解锁附件
    var _url = window.top.contextPath + window.portPath.unlockAttachement;
    var params = "lockSource=3&operation=3&attachmentId="+attachId+"&instanceId="+instanceId;/**lockSource 1-手机 2-pad 3-pc 4-微信 operation-加锁-1 数据库解锁-2 点击解锁-3 发送解锁-4 */
    window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	layer.msg("解锁成功");
        }else{
        	layer.msg("服务异常", {icon: 2});
        }
    });
}