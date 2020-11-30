/*
**按钮业务定义
*/
$(function () {
    window.common.getNiceScroll("#wrapper");
    queryAllActions();
    submitFormData();
    queryProcessList();
});

function queryProcessList() {//查询业务定义列表
    var code = window.common.getQueryString("code");
    var _url = window.top.contextPath + window.portPath.processActionsPage + code;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
        if (res.exchangeStatus == 1) {
            window.common.drawTemplateHtml("processActions", "#processActionsTb", {ManageData: res});
            if (res.exchangeStatus == 1) {
                window.common.drawTemplateHtml("allTasksList", "#allTasksTb", {ManageData: res});
            }
        }
    });
}

var processActionsFormDialog = null;
function showEditDialog(flag, actionData) {//打开编辑弹窗
    var _title = '';
    if (flag == 'add') {
        _title = '添加按钮';
        $(':input', '#processActionsForm').not(':button,:submit,:reset').val('');
    } else {
        _title = '修改按钮';
        var thisData = JSON.parse(actionData);
        for (var key in thisData) {
            $("#processActionsForm input[name='" + key + "']").val(thisData[key]);
            $("#processActionsForm select[name='" + key + "']").val(thisData[key]);
        }
    }
    processActionsFormDialog = layer.open({
        title: _title,
        type: 1,
        area: [430, 330],
        content: $("#processActionsFormDialog"),
    });
}

function closeEditDialog() {//关闭编辑弹窗
    if (processActionsFormDialog) {
        layer.close(processActionsFormDialog);
        $("#processActionsForm").validate().resetForm();
    } else {
        layer.closeAll();
    }
}

function submitFormData() {//提交表单数据
    $("#processActionsForm").validate({
        rules: {
            taskId: {
                required: true
            },
            actionId: {
                required: true
            },
            orderNumber: {
                required: true,
                checkNum: true
            }
        },
        messages: {
            taskId: {
                required: "必选！"
            },
            actionId: {
                required: "必选！"
            },
            orderNumber: {
                required: "必填项！"
            }
        },
        onsubmit: true,
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form) {
            var paramsObject = $("#processActionsForm").serializeObject();

            paramsObject["taskId"] = $("#taskId").val();
            paramsObject["actionId"] = $("#actionId").val();
            paramsObject['actionName'] = $("#actionName").val();
            paramsObject["orderNumber"] = $("#orderNumber").val();
            paramsObject["id"] = $("#id").val();
            paramsObject["bizCode"] = window.common.getQueryString("code");
            var params = JSON.stringify(paramsObject);
            console.log(params);
            var _url = window.top.contextPath + window.portPath.processActionsSave;
            window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function (res) {
                console.log(res);
                if (res.exchangeStatus == 1) {
                    layer.msg('操作成功', {icon: 1});
                    layer.close(processActionsFormDialog);
                    queryProcessList();
                } else if (res.errorStatus == 300) {
                    layer.msg(res.message, {icon: 2})
                } else {
                    layer.msg("操作失败", {icon: 2})
                }
            });
        }
    });
    //自定义正则表达示验证方法
    $.validator.addMethod("checkNum", function (value, element) {
        var chrnum = /^[0-9]*$/;
        return this.optional(element) || (chrnum.test(value));
    }, "请输入整数");
}

function removeprocessActions(id) {//根据业务id移除数据
    layer.confirm("确认删除按钮吗?", {
        btn: ['取消', '确认']
    }, function (index, layero) {
        layer.close(index);
    }, function (index) {
        var _url = window.top.contextPath + window.portPath.processActionsDelete + "?Id=" + id;
        window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
            if (res.exchangeStatus == 1) {
                layer.msg('删除成功', {icon: 1});
                queryProcessList();
            } else {
                layer.msg(res.message, {icon: 2});
            }
        });
    });
}

function selectChange(even) {//获取下拉框中的actionName
    var selectedActionName = $(even).find("option:selected").text();
    $("input[name='actionName']").val(selectedActionName);
}

function queryAllActions() {
    var _url = window.top.contextPath + window.portPath.commonActionsList;
    window.common.networkConnect(_url, "POST", window.conType.conTyForm, null, function (res) {
        if (res.exchangeStatus == 1) {
            var initSelectData = {
                contextPath: window.top.contextPath,
                ActionsData: res.data,
            };
            window.common.drawTemplateHtml("allActionsList", ".allActionsTb", initSelectData);
        }
    });
}

template.defaults.imports.dateFormat = window.common.dateFormat;//注册过滤器