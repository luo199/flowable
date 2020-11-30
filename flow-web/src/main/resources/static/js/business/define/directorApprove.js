/*
**请休假业务
*/
$(function () {
    window.common.getNiceScroll("#wrapper");
    submitFormData();
    submitProcess();
    $('#queryProcessForm').on('submit', function () {
        queryProcessList();
        return false; //阻止表单默认提交
    })
    $('#commButton').bind("click", function () {
        $("#processPage").val(1);
    });
    $('#commButton').trigger("click");
});

function queryProcessList() {//查询业务定义列表
    var bizCode = window.common.getQueryString("code");
    var _url = window.top.contextPath + window.portPath.holidayManageList + "/" + bizCode + "/" + 1;
    // var queryForm = $("#queryProcessForm").serializeObject();//rows: 20 分页大小,page: 1  当前页
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
        if (res.exchangeStatus == 1) {
            window.common.drawTemplateHtml("baseHolidayTemplate", "#baseHolidayTb", {listData: res.data});
        }
    });
}


function pageCallBack(opts) {//分页跳转
    $("#processPage").val(opts.currentPage);
    $('#queryProcessForm').trigger("submit");
    return false;
};

function submitFormData() {//提交表单数据
    $("#reportForm").validate({
        rules: {
            message: {
                required: true
            },
        },
        messages: {
            message: {
                required: "必填项"
            },
        },
        onsubmit: true,
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form) {
            var paramsObject = $("#reportForm").serializeObject();
            var _url = window.top.contextPath + window.portPath.saveExecutorDetail;
            var params = JSON.stringify(paramsObject);
            window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function (res) {
                if (res.id != '') {
                    layer.msg('操作成功', {icon: 1});
                    layer.close(reportFormDialog);
                    queryProcessList();
                } else {
                    layer.msg("操作失败", {icon: 2})
                }
            });
        }
    });
    //自定义正则表达示验证方法
    $.validator.addMethod("checkNum", function (value, element) {
        var chrnum = /^[0-9]{11}$/;
        return this.optional(element) || (chrnum.test(value));
    }, "请输入11位手机号");
    $.validator.addMethod("checkDate", function (value, element) {
        var chrnum = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
        return this.optional(element) || (chrnum.test(value));
    }, "请输入时间");
}


var baseHolidayFormDialog = null;

function getCommonPage(callback) {//根据流程id获取信息
    var id = $("#selectedId").val();
    var _url = window.top.contextPath + window.portPath.holidayFormFindOne + "?id=" + id;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
        if (res.exchangeStatus == 1) {
            if (callback) callback(res.data);
        } else {
            layer.msg(res.message, {icon: 2});
        }
    });
}

function editHolidayForm() {
    var id = $("#selectedId").val();
    if (!id || id == "") {
        layer.msg('请选择一个节点进行配置！', {icon: 5});
        return;
    }
    getCommonPage(function (listData) {
        for (var key in listData) {
            if (key == 'startHolidayTime' ||
                key == 'workLength' || key == 'endHolidayTime') {
                $("#baseHolidayForm input[name='" + key + "']").val(
                    window.common.dateFormat(listData[key], 'yyyy-MM-dd'));
            } else {
                $("#baseHolidayForm input[name='" + key + "']").val(listData[key]);
            }
            // $("#commActionDefineForm select[name='" + key + "']").val(listData[key]);
        }
    });
    baseHolidayFormDialog = layer.open({
        title: '详细请假表',
        type: 1,
        area: [1300, 700],
        content: $("#baseHolidayFormDialog")
    });
}

function closeHolidayDialog() {//关闭编辑弹窗
    if (baseHolidayFormDialog) {
        layer.close(baseHolidayFormDialog);
        $("#baseHolidayForm").validate().resetForm();
    } else {
        layer.closeAll();
    }
}

var reportFormDialog = null;

function closeReportDialog() {
    if (reportFormDialog) {
        layer.close(reportFormDialog);
        $("#reportFormDialog").validate().resetForm();
    } else {
        layer.closeAll();
    }
}

function executeMessage() {
    var id = $("#selectedId").val();
    if (!id || id == "") {
        layer.msg('请选择一个节点进行配置！', {icon: 5});
        return;
    }
    getCommonPage(function (listData) {
        for (var key in listData) {
            if (key == 'startHolidayTime' ||
                key == 'workLength' || key == 'endHolidayTime') {
                $("#reportForm input[name='" + key + "']").val(
                    window.common.dateFormat(listData[key], 'yyyy-MM-dd'));
            } else {
                $("#reportForm input[name='" + key + "']").val(listData[key]);
            }
            // $("#commActionDefineForm select[name='" + key + "']").val(listData[key]);
        }
    });
    reportFormDialog = layer.open({
        title: "ddde",
        type: 1,
        area: [1300, 700],
        content: $("#reportFormDialog")
    });
}

var commFormDialog = null;
function submitProcess() {//提交表单数据
    $("#commDefineForm").validate({
        rules: {
            executorId: {
                required: true
            },
        },
        messages: {
            executorId: {
                required: "必选"
            },
        },
        onsubmit: true,
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form) {
            var paramsObject = $("#commDefineForm").serializeObject();
            paramsObject["holidayFormId"] = $("#selectedId").val();
            paramsObject["executorId"] =$("#executorId").val();
            var params = JSON.stringify(paramsObject);
            var _url = window.top.contextPath + window.portPath.holidayApproveProcess;
            window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function (res) {
                if (res != '') {
                    layer.msg('操作成功', {icon: 1});
                    layer.close(commFormDialog);
                    queryProcessList();
                } else {
                    layer.msg("操作失败", {icon: 2})
                }
            });
        }
    });
}


function rejectProcess() {
    var id = $("#selectedId").val();
    var _url = window.top.contextPath + window.portPath.holidayRejectProcess+"?id="+id;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
        if (res != '') {
            layer.msg('操作成功', {icon: 1});
            queryProcessList();
        } else {
            layer.msg("操作失败", {icon: 2})
        }
    });
}



function closeDialog() {//关闭编辑弹窗
    if (commFormDialog) {
        layer.close(commFormDialog);
        $("#commDefineForm").validate().resetForm();
    } else {
        layer.closeAll();
    }
}

function sendToManager() {
    var bizCode = window.common.getQueryString("code");
    var id = $("#selectedId").val();
    var _url = window.top.contextPath + window.portPath.getNextExecutor + "?bizCode=" + bizCode + "&id=" + id;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
        if (res.exchangeStatus == 1) {
            window.common.drawTemplateHtml("allExecutorList", ".allExecutorTb", {manageData: res.data});
            // layer.msg('操作成功', {icon: 1});
        } else {
            layer.msg("操作失败", {icon: 2})
        }
    });
    commFormDialog = layer.open({
        title: '选择送交的审批人',
        type: 1,
        area: [430, 330],
        content: $("#commFormDialog")
    });
}

function openLookUpPage() {//打开编辑弹窗
    var id = $("#selectedId").val();
    if (!id || id == "") {
        layer.msg('请选择一个节点进行配置！', {icon: 5});
        return;
    }
    getCommonPage(function (listData) {
        for (var key in listData) {
            if (key == 'startHolidayTime' ||
                key == 'workLength' || key == 'endHolidayTime') {
                $("#baseHolidayForm input[name='" + key + "']").val(
                    window.common.dateFormat(listData[key], 'yyyy-MM-dd'));
            } else {
                $("#baseHolidayForm input[name='" + key + "']").val(listData[key]);
            }
            // $("#commActionDefineForm select[name='" + key + "']").val(listData[key]);
        }
    });
    //window.parent.document.getElementById("centerCoverContianer").style.display = 'block';
    // window.parent.document.getElementById("centerCoverContianer").setAttribute("src",window.top.contextPath+"/static/page/business/define/catalogmanage.html");
}


function selectChange(even) {//获取下拉框中的executorName
    var executorId = $(even).find("option:selected").text();
    $("input[name='executorId']").val(executorId);
}

function updateSelection(even, id, position) {//更新选择状态
    var isCheck = even.checked;
    var _val = $(even).val();
    if (isCheck && _val == "true") {
        $(even).removeAttr("checked");
        $("#selectedId").val("");
        $(even).val("");
        $("#leaderChargeBtn").hide();
        $("#mainLeaderBtn").hide();
        $("#approveBtn").hide();
        $("#rejectBtn").hide();
    } else {
        $("input[name='checkboxTest']").val("");
        $(even).val("true");
        $("#selectedId").val(id);
        $("#approveBtn").show();
        $("#leaderChargeBtn").show();
        $("#rejectBtn").show();
    }
}














template.defaults.imports.dateFormat = window.common.dateFormat;//注册过滤器