$(function () {
    window.common.getNiceScroll("#wapper");
    submitFormData();
    $('#queryProcessForm').on('submit',function () {
        queryProcessList();
        return false;//阻止表单默认提交刷新
    })
    $('#businessDefineSubmitButton').bind("click",function(){
        $("#current").val(1);
    });
    $('#businessDefineSubmitButton').trigger("click");
});


function queryProcessList() {
    var _url = window.top.contextPath + window.portPath.testListPage;
    var queryForm = $("#queryProcessForm").serializeObject();
    window.common.networkConnect(_url,"post",window.conType.conTyJson,queryForm,function (res) {
        res.data=res;
       // if (res.exchangeStatus == 1) {
            window.common.drawTemplateHtml("testTemplate", "#testTb", {listData: res.data});
            var opts = {
                currentPage : res.data.current,
                totalPages:res.data.pages,
                total:res.data.total,
                size:res.data.size,
                callBack : pageCallBack
            };
            $.huasiBackPagination.drawPagination(opts);
        //}
    })
}

function pageCallBack(opts){//分页跳转
    $("#current").val(opts.currentPage);
    $('#queryProcessForm').trigger("submit");
    return false ;
};

function openBusinessRelationPage(){//打开业务关联界面
    var businessId = $("#selectedBusinessDefine").val();
    if(!businessId || businessId == ""){
        layer.msg('请选择一条数据！',{icon: 5});
        return;
    }
    window.parent.document.getElementById("centerCoverContianer").style.display = 'block';
    window.parent.document.getElementById("centerCoverContianer").setAttribute("src",window.top.contextPath+"/static/page/smartform/admin/businessRelationView.html?businessId="+businessId);
}
function openBusinessDefineDetails(businessId){//打开详情
    window.parent.document.getElementById("centerContianer").setAttribute("src",window.top.contextPath+"/static/page/smartform/admin/formDefineList.html?businessId="+businessId);
}

var testFormDialog = null;
function submitFormData() {//提交表单数据
    $("#testForm").validate({
        rules:{
            id:{
                required:true
            }
        },
        messages:{
            id:"必填项！",
        },
        onsubmit:true,
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function(form) {
            var paramsObject = $("#testForm").serializeObject();
            var _url = window.top.contextPath;
            _url += window.portPath.testSave;
            // var params = $.param(paramsObject);
            var params = JSON.stringify(paramsObject);
            window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
                res.data = res;
                if (res.data!='') {
                    layer.msg('操作成功', {icon: 1});
                    layer.close(testFormDialog);
                    queryProcessList();
                }else {
                    layer.msg("操作失败",{icon: 2})
                }
            });
        }
    });
}

function addData(){
    var _title = '';
    _title = '新增test';
    testFormDialog = layer.open({
        title : _title,
        type : 1,
        content : $("#testFormDialog"),

    });
}

function closeEditDialog(){//关闭编辑弹窗
    if(testFormDialog){
        layer.close(testFormDialog);
    }else{
        layer.closeAll();
    }
}

template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器