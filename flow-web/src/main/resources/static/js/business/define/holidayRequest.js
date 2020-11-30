/*
**请休假业务
*/
$(function () {
    window.common.getNiceScroll("#wrapper");
    submitFormData();
    submitStartProcess();
    $('#queryProcessForm').on('submit', function () {
        // catalogAction();
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
    var _url = window.top.contextPath + window.portPath.holidayManageList + "/" + bizCode+"/"+0;//holidayFormPage;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
        if (res.exchangeStatus == 1) {
            window.common.drawTemplateHtml("baseHolidayTemplate", "#baseHolidayTb", {listData: res.data});
        }
    });
}
       /* var _url = window.top.contextPath + window.portPath.holidayFormPage;
        var queryForm = $("#queryProcessForm").serializeObject();//rows: 20 分页大小,page: 1  当前页
        window.common.networkConnect(_url, "POST", window.conType.conTyJson, queryForm, function (res) {
            if (res.exchangeStatus == 1) {
                window.common.drawTemplateHtml("baseHolidayTemplate", "#baseHolidayTb", {listData: res.data});
            }
        });*/

function pageCallBack(opts) {//分页跳转
    $("#processPage").val(opts.currentPage);
    $('#queryProcessForm').trigger("submit");
    return false;
};

$("#endHolidayTime").on("change", function () {
    var startHolidayTime = $("#startHolidayTime").val();
    var endHolidayTime = $("#endHolidayTime").val();
    var thisTime = Date.now();
    startHolidayTime = Date.parse(startHolidayTime);
    endHolidayTime = Date.parse(endHolidayTime);
    var holidayTime = (endHolidayTime - startHolidayTime) / 1000 / 60 / 60 / 24 + 1;//请假天数
    $("#holidayTime").val(holidayTime);
    $("#shouldHolidayTime").val(holidayTime);//应休

    var hasHolidayTime = Math.floor((thisTime - startHolidayTime) / 1000 / 60 / 60 / 24 + 1);
    if (thisTime > endHolidayTime) {
        $("#hasHolidayTime").val(holidayTime);//已休
    } else {
        $("#hasHolidayTime").val(hasHolidayTime);//已休
    }
});

function submitFormData() {//提交表单数据
    $("#baseHolidayForm").validate({
        rules: {
            name: {
                required: true
            },
            position: {
                required: true
            },
            department: {
                required: true
            },
            phoneNumber: {
                required: true,
                checkNum: true
            },
            holidayType: {
                required: true
            },
            reProcedure: {
                required: true
            },
            workLength: {
                required: true,
                checkDate: true
            },
            leaveShenzhen: {
                required: true
            },
            startHolidayTime: {
                required: true
            },
            endHolidayTime: {
                required: true
            },
        },
        messages: {
            name: {
                required: "必填项"
            },
            position: {
                required: "必填项"
            },
            department: {
                required: "必填项"
            },
            phoneNumber: {
                required: "必填项"
            },
            holidayType: {
                required: "必填项"
            },
            reProcedure: {
                required: "必填项"
            },
            workLength: {
                required: "必填项"
            },
            leaveShenzhen: {
                required: "必填项"
            },
            startHolidayTime: {
                required: "必填项"
            },
            endHolidayTime: {
                required: "必填项"
            },
        },
        onsubmit: true,
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form) {
            var paramsObject = $("#baseHolidayForm").serializeObject();
            paramsObject["bizCode"] = window.common.getQueryString("code");
            var _url = window.top.contextPath + window.portPath.holidayFormSave;
            var params = JSON.stringify(paramsObject);
            window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function (res) {
                if (res.id != '') {
                    layer.msg('操作成功', {icon: 1});
                    layer.close(baseHolidayFormDialog);
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
    var _title = '';
    if (id == "") {
        _title = '新增';
        $(':input', '#baseHolidayForm').not(':button,:submit,:reset').val('');
    } else {
        _title = '修改';
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
    }
    baseHolidayFormDialog = layer.open({
        title: _title,
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

var commFormDialog = null;

function submitStartProcess() {//提交表单数据
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
            var _url = window.top.contextPath + window.portPath.startHolidayProcess;
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

function closeDialog() {//关闭编辑弹窗
    if (commFormDialog) {
        layer.close(commFormDialog);
        $("#commDefineForm").validate().resetForm();
    } else {
        layer.closeAll();
    }
}

function deleteForm() {
    var id = $("#selectedId").val();
    if(!id || id == ""){
        layer.msg('请选择一项进行删除！',{icon: 5});
        return;
    }
    layer.confirm("确认删除",{
        btn: ['取消', '确认']
    }, function(index, layero){
        layer.close(index);
    }, function(index){
        var id = $("#selectedId").val();
        var _url = window.top.contextPath + window.portPath.holidayFormDeleteById+"?id="+id;
        window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
            if (res.exchangeStatus == 1) {
                layer.msg('删除成功',{icon: 1});
                queryProcessList();
                $("#selectedId").val("");
            }else{
                layer.msg(res.message,{icon: 2});
            }
        });
    });
}


function sendToDirector() {
    var bizCode = window.common.getQueryString("code");
    var id = $("#selectedId").val();
    if (!id || id == "") {
        layer.msg('请选择一个节点进行配置！', {icon: 5});
        return;
    }
    console.log(bizCode + "    " + id);
    var _url = window.top.contextPath + window.portPath.getNextExecutor + "?bizCode=" + bizCode + "&id=" + id;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
        if (res.exchangeStatus == 1) {

            window.common.drawTemplateHtml("allExecutorList", ".allExecutorTb", {manageData: res.data});
            console.log(res);
            // layer.msg('操作成功', {icon: 1});
            // queryProcessList();
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
    var _url = window.top.contextPath + window.portPath.holidayFormFindOne + "?id=" + id;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
        if (res.exchangeStatus == 1) {
            if (callback) callback(res.data);
        } else {
            layer.msg(res.message, {icon: 2});
        }
    });
    layer.open({
        title: '类别管理',
        type: 2,
        //offset : '10%',
        area: [900, 700],
        maxmin: true,
        content: "holidayRequest.html",
    });
    //window.parent.document.getElementById("centerCoverContianer").style.display = 'block';
    // window.parent.document.getElementById("centerCoverContianer").setAttribute("src",window.top.contextPath+"/static/page/business/define/catalogmanage.html");
}

function getProcessExecutor(callback) {//根据流程id获取信息
    var id = $("#selectedId").val();
    var bizCode = window.common.getQueryString("code");
    var _url = window.top.contextPath + window.portPath.processExecutorFindOne + bizCode + "/" + id;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
        if (res.exchangeStatus == 1) {
            if (callback) callback(res.data);
        } else {
            layer.msg(res.message, {icon: 2});
        }
    });
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
        // $("#baseHolidayForm").validate().resetForm();
        $("#directorBtn").hide();
        $("#leaderChargeBtn").hide();
    } else {
        $("input[name='checkboxTest']").val("");
        $(even).val("true");
        $("#edit").innerHTML="修改";
        $("#selectedId").val(id);
        if (position == "员工") {
            $("#directorBtn").show();
            $("#leaderChargeBtn").hide();
        } else {
            $("#directorBtn").hide();
            $("#leaderChargeBtn").show();
        }
    }
}


template.defaults.imports.dateFormat = window.common.dateFormat;//注册过滤器