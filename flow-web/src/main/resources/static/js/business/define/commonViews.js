/*
**业务定义
*/
$(function(){
    /*
    commonViewsFindOne: '/api/business/commPage/findOne', //通用页面--按主键查询
    commonViewsDelete: '/api/business/commPage/deleteById', //通用页面--按主键删除
    */
    window.common.getNiceScroll("#wrapper");
    submitFormData();
    $('#queryProcessForm').on('submit',function(){
        catalogView();
        queryProcessList();
        return false; //阻止表单默认提交
    })
    $('#commPageDefineSubmitButton').bind("click",function(){
        $("#processPage").val(1);
    });
    $('#commPageDefineSubmitButton').trigger("click");
});
function queryProcessList(){//查询业务定义列表
    var _url = window.top.contextPath + window.portPath.commonViewsPage;
    var queryForm = $("#queryProcessForm").serializeObject();//rows: 20 分页大小,page: 1  当前页
    window.common.networkConnect(_url, "POST", window.conType.conTyJson, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
            window.common.drawTemplateHtml("commViewsTemplate", "#commViewsTb", {listData: res.data});
            var opts = {
                currentPage : res.data.current,
                totalPages:res.data.pages,
                total:res.data.total,
                size:res.data.size,
                callBack : pageCallBack
            };
            $.huasiBackPagination.drawPagination(opts);
        }
    });
}
function pageCallBack(opts){//分页跳转
    $("#processPage").val(opts.currentPage);
    $('#queryProcessForm').trigger("submit");
    return false ;
};
function openCommPageRelationPage(){//打开业务关联界面
    var commPageId = $("#selectedId").val();
    if(!commPageId || commPageId == ""){
        layer.msg('请选择一条数据！',{icon: 5});
        return;
    }
    window.parent.document.getElementById("centerCoverContianer").style.display = 'block';
    window.parent.document.getElementById("centerCoverContianer").setAttribute("src",window.top.contextPath+"/static/page/smartform/admin/commPageRelationView.html?commPageId="+commPageId);
}
function openCommPageDefineDetails(commPageId){//打开详情
    window.parent.document.getElementById("centerContianer").setAttribute("src",window.top.contextPath+"/static/page/smartform/admin/formDefineList.html?commPageId="+commPageId);
}
var commPageDefineLayerDialog = null;
function showEditDialog(flag){//打开编辑弹窗
    var _title = '';
    if(flag == 'add'){
        _title = '新增';
        $(':input','#commPageDefineForm').not(':button,:submit,:reset').val('');
    }else if(flag == 'update'){
        _title = '修改';

        var code = $("#selectedId").val();
        if(!code || code == ""){
            layer.msg('请选择一项进行修改！',{icon: 5});
            return;
        }
        getCommPage(function(listData){
            for(var key in listData){
                $("#commPageDefineForm input[name='"+key+"']").val(listData[key]);
                $("#commPageDefineForm select[name='"+key+"']").val(listData[key]);
            }
        });
    }
    commPageDefineLayerDialog = layer.open({
        title : _title,
        type : 1,
        area:[550,250],
        content : $("#commPageDefineFormDialog")
    });


}
function closeEditDialog(){//关闭编辑弹窗
    if(commPageDefineLayerDialog){
        layer.close(commPageDefineLayerDialog);
    }else{
        layer.closeAll();
    }
}


function submitFormData() {//提交表单数据
    $("#commPageDefineForm").validate({
        rules:{
            name:{
                required:true
            },
            pageUrl:{
                required:true
            },
            catalogCode:{
                required: true
            }
        },
        messages:{
            code:{
                required:"必填项!"
            },
            pageUrl:{
                required:"必填项!"
            },
            catalogCode: {
                required: "必填项!"
            }
        },
        onsubmit:true,
        errorPlacement: function(error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function(form) {
            var paramsObject = $("#commPageDefineForm").serializeObject();
            paramsObject['catalogName'] = $("#catalogName").val();
            var _url = window.top.contextPath + window.portPath.commonViewsSave;

            var params= JSON.stringify(paramsObject);

            window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function(res) {
                if (res.id!='') {
                    layer.msg('操作成功',{icon: 1});
                    layer.close(commPageDefineLayerDialog);
                    queryProcessList();
                }else{
                    layer.msg("操作失败",{icon: 2})
                }
            });
        }

    });
    //自定义正则表达示验证方法
    $.validator.addMethod("checkEnglish",function(value,element){
        var chrnum = /^[a-zA-Z][a-zA-Z0-9_]*$/;
        return this.optional(element) || (chrnum.test(value));
    }, "请输入字母数字和下划线");
    //验证key值是否唯一
    $.validator.addMethod("checkExsitCode",function(value,element){
        return this.optional(element) || (existCode(value));
    }, "业务编码存在，请重输！");
}


function removeCommPageDefineByCode(){//根据业务id移除数据
    var code = $("#selectedId").val();
    if(!code || code == ""){
        layer.msg('请选择一项进行删除！',{icon: 5});
        return;
    }
    layer.confirm("确认删除",{
        btn: ['取消', '确认']
    }, function(index, layero){
        layer.close(index);
    }, function(index){

        var code = $("#selectedId").val();
        var _url = window.top.contextPath + window.portPath.commonViewsDelete+"?id="+code;
        window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
            if (res.exchangeStatus == 1) {
                layer.msg('删除成功',{icon: 1});
                queryProcessList();
                $("#selectedId").val("");
                $("#selectedUrl").val();
            }else{
                layer.msg(res.message,{icon: 2});
            }
        });
    });
}
function getCommPage(callback){//根据流程id获取信息
    var id = $("#selectedId").val();
    var _url = window.top.contextPath + window.portPath.commonViewsFindOne+"?id="+id;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
        if (res.exchangeStatus == 1) {
            if(callback) callback(res.data);
        }else{
            layer.msg(res.message,{icon: 2});
        }
    });
}
function updateSelection(even, id,pageUrl){//更新选择状态
    var isCheck = even.checked;
    var _val = $(even).val();
    if(isCheck && _val == "true"){
        $(even).removeAttr("checked");
        $("#selectedId").val("");
        $("#selectedUrl").val("");
        $(even).val("");
    }else{
        $("input[name='checkboxTest']").val("");
        $(even).val("true");
        $("#selectedId").val(id);
        $("#selectedUrl").val(pageUrl);
    }
}

function selectChange(even){//
    var selectedText = $(even).find("option:selected").text();
    $(even).siblings("input").val(selectedText);
}

var httpStr= new RegExp("http");

function openLookUpPage(){//打开业务分类管理
    var selectedUrl = $("#selectedUrl").val();
    if(!selectedUrl || selectedUrl == ""){
        layer.msg('请选择一项！',{icon: 5});
        return;
    }
    //test方法返回值为(true或者false)
    if(!httpStr.test(selectedUrl)){
        seletedUrl = window.top.contextPath + selectedUrl;
    }
    layer.open({
        title : '类别管理',
        type : 2,
        //offset : '10%',
        area:[900,700],
        maxmin :true,
        content : seletedUrl
    });

    //window.parent.document.getElementById("centerCoverContianer").style.display = 'block';
    // window.parent.document.getElementById("centerCoverContianer").setAttribute("src",window.top.contextPath+"/static/page/business/define/catalogmanage.html");
}

function catalogView() {
    var _url = window.top.contextPath + window.portPath.findChildCataLogList + "?pcodes=BUSINESS_VIEW";
    window.common.networkConnect(_url, "GET", window.conType.conTyForm, null, function (res) {
        if (res.exchangeStatus == 1) {
            var initSelectData = {
                contextPath: window.top.contextPath,
                CatalogVeiws: res.data,
            };
            window.common.drawTemplateHtml("catalogViewList", ".catalogViewTb", initSelectData);
            window.common.drawTemplateHtml("catalogViewListTt", ".catalogViewTt", initSelectData);
        }
    });
}
function selectName(even) {//获取下拉框中的actionName
    var selectedName = $(even).find("option:selected").text();
    $("input[name='catalogName']").val(selectedName);
}


function openCommPageManagePage() {//打开业务配置页面
    var code = $("#selectedId").val();
    if(!code || code == ""){
        layer.msg('请选择一项业务进行配置！',{icon: 5});
        return;
    }

    window.parent.document.getElementById("centerCoverContianer").style.display = 'block';
    window.parent.document.getElementById("centerCoverContianer").setAttribute("src",window.top.contextPath+"/static/page/commPage/define/commPageManageView.html?code="+code);
}
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器