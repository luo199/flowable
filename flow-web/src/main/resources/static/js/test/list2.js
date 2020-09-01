$(function () {
    window.common.getNiceScroll("#wapper");
    submitFormData();
    $('#queryProcessForm').on('submit', function () {
        queryProcessList();
        return false;
    })
    $('#businessDefineSubmitButton').bind("click", function () {
        $("#current").val(1);
    });
    $('#businessDefineSubmitButton').trigger("click");
});

function queryProcessList() {
    var _url = window.top.contextPath + window.portPath.testListPage;
    var queryForm = $("#queryProcessForm").serializeObject();
    window.common.networkConnect(_url, "post", window.conType.conTyJson, queryForm, function (res) {
        var rt = res;
        window.common.drawTemplateHtml("testTemplate", "#testTb", {listData: rt});
        var opts = {
            currentPage: rt.current,
            totalPages: rt.pages,
            total: rt.total,
            size: rt.size,
            callBack: pageCallBack
        };
        $.huasiBackPagination.drawPagination(opts);
    })
}

function pageCallBack(opts) {
    $('#current').val(opts.currentPage);
    $('#queryProcessForm').trigger("submit");
    return false;
};

function openBusinessRelationPage() {//打开业务关联界面
    var businessId = $("#selectedBusinessDefine").val();
    if (!businessId || businessId == "") {
        layer.msg('请选择一条数据！', {icon: 5});
        return;
    }
    window.parent.document.getElementById("centerCoverContianer").style.display = 'block';
    window.parent.document.getElementById("centerCoverContianer").setAttribute("src", window.top.contextPath + "/static/page/smartform/admin/businessRelationView.html?businessId=" + businessId);
}

function openBusinessDefineDetails(businessId) {//打开详情
    window.parent.document.getElementById("centerContianer").setAttribute("src", window.top.contextPath + "/static/page/smartform/admin/formDefineList.html?businessId=" + businessId);
}


var testFormDialog = null;

function submitFormData() {
    $("#testForm").validate({
        rules: {id: {required: true}},
        messages: {id: {required: "必须填"}},
        onsubmit: true,
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form) {
            var paramsObject = $("#testForm").serializeObject();
            var _url = window.top.contextPath;
            _url += window.portPath.testSave;
            if (paramsObject.modelType == '') {
                paramsObject.modelType = 0;
            }
            var params = JSON.stringify(paramsObject);
            window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function (res) {
                var rt = res;
                if (rt != '') {
                    layer.msg('操作成功', {icon: 1});
                    layer.close(testFormDialog);
                    queryProcessList();
                } else {
                    layer.msg("操作失败", {icon: 2})
                }
            });
        }
    });
}

function editTestData(flag) {
    var _title = '';
    if (flag == 'add') {
        _title = '新增jia';
        $(':input', '#testForm').not(':button,:submit,:reset').val('');
        testFormDialog = layer.open({
            title: _title,
            type: 1,
            content: $("#testFormDialog"),
        });
    } else if (flag == 'update') {
        var _id = $("#selectedBusinessDefine").val();
        if (!_id || _id == "") {
            layer.msg('选择一条数据修改', {icon: 5});
            return;
        }
        _title = '修改g';
        $(':input', '#testForm').not(':button,:submit,:reset').val('');
        getModelByID(function(listData){
            for(var key in listData){
                $("#businessDefineForm input[name='"+key+"']").val(listData[key]);
            }
        });
        testFormDialog = layer.open({
            title: _title,
            type: 1,
            content: $("#testFormDialog"),
        });

    }
}

function closeEditDialog() {//关闭编辑弹窗
    if (testFormDialog) {
        layer.close(testFormDialog);
    } else {
        layer.closeAll();
    }
}

function updateSelection(even, id, maxVersion, name) {//更新选择状态
    var isCheck = even.checked;
    var _val = $(even).val();
    if (isCheck && _val == "true") {
        $(even).removeAttr("checked");
        $(even).val("");
        $("#selectedBusinessDefine").val("");
        $("#selectedModelName").val("");
    } else {
        $("input[name='checkboxTest']").val("");
        $(even).val("true");
        $("#selectedBusinessDefine").val(id);
        $("#selectedModelName").val(name);
    }
}

function getModelByID(callback) {//根据流程id获取信息
    var _id = $("#selectedBusinessDefine").val();
    var _url = window.top.contextPath + window.portPath.testGetByID + "?id=" + _id;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function (res) {
            res = changeTreeDate(res, "key", "modelKey");
            if (callback) callback(res);
    });
}

function changeTreeDate(zf_jsonObj, newKey, oldKey) {
    var str = JSON.stringify(zf_jsonObj);
    var reg = new RegExp(oldKey, 'g');
    var newStr = str.replace(reg, newKey);
    return JSON.parse(newStr);
}

template.defaults.imports.dateFormat = window.common.dateFormat;//注册过滤器