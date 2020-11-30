/*
**模板定义
*/
$(function(){
	window.common.getNiceScroll("#wrapper");
	window.common.getNiceScroll("wrapper");
	$('#queryCatalogListForm').on('submit',function(){
		getCataloglist();
		return false; //阻止表单默认提交
	});
	$('#catalogSubmitButton').bind("click",function(){
		$("#catalogPage").val(1);
	});
	$('#catalogSubmitButton').trigger("click");
	submitTextTemplateData();
});
function getCataloglist(){//通过业务ID查询模板列表
	var textTemplateObj= $("#queryCatalogListForm").serializeObject();
	var _url = window.top.contextPath + window.portPath.querycataLogList;
	var queryForm = $("#queryCatalogListForm").serializeObject();
	window.common.networkConnect(_url, "POST", window.conType.conTyJson, queryForm, function(res) {
        if (res.exchangeStatus == 1) {
        	window.common.drawTemplateHtml("catalogList", "#catalogListTb", {catalogData: res.data});
        	var opts = {
   				 currentPage : res.data.current,
   				 totalPages:res.data.pages,
   				 total:res.data.total,
   				 size:res.data.size,
   				 callBack : CatalogPageCallBack
   		 	};
   		   $.huasiBackPagination.drawPagination(opts);
        }
    });
}
function CatalogPageCallBack(opts){//分页跳转
	$("#catalogPage").val(opts.currentPage);
	$('#queryCatalogListForm').trigger("submit");
	return false ;
}
var textTemplateLayerDialog = null;

function showCatalogEditDialog(flag){//打开编辑弹窗
	var _title = '';
	if(flag == 'add'){
		_title = '新增分类';
        $("#cataLogCode").show();
        $("#cataLogPcode").hide();
		$(":input","#textTemplateForm").not(":button,:submit,:reset").val("");
        $("#initOrderNumber").val(1);
	}else if(flag == 'update'){
		$(":input","#textTemplateForm").not(":button,:submit,:reset,:hidden").val("");
		_title = '修改分类';
		var code = $("#selectedCataLogCode").val();
		if(!code || code == ""){
			layer.msg('请选择一条分类进行修改！',{icon: 5});
			return;
		}
		$("#cataLogCode").hide();
        $("#cataLogPcode").show();
		openCataLogDetails();
	}
	textTemplateLayerDialog = layer.open({
		title : _title,
		type : 1,
		content : $("#textTemplateFormDialog")
	});
}
function closeTextTemplateEditDialog(){//关闭编辑弹窗
	if(textTemplateLayerDialog){
		layer.close(textTemplateLayerDialog);
	}else{
		layer.closeAll();
	}
}
function submitTextTemplateData() {//提交表单数据
	$("#textTemplateForm").validate({
		rules:{
            code:{
                required:true,
                checkEnglish:true,
                checkExsitCode:true
            }, name:{
                required:true
            },
			pcode:{
            	checkExsitPcoe:true,
                required:true
			},
            orderNumber:{
                required:true,
                checkOrderNumer:true
            }
		},
		messages:{
            code:{
                required:"必填项!"
            },
            name:{
                required:"必填项!"
            },
            pcode:{
                required:"必填项!"
            },
            orderNumber:{
                required:"必填项!"
            }
		},
		onsubmit:true,
		errorPlacement: function(error, element) {  
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
			var textTemplateObj= $("#textTemplateForm").serializeObject();
            var pcode = textTemplateObj['pcode'];
            if(!pcode){
                textTemplateObj['pcode']="0";
			}
			var _url = window.top.contextPath;
            if($("#cataLogCode").is(":hidden")){//编辑
                _url += window.portPath.updateCataLog;
			}else{
                _url += window.portPath.createCataLog;
			}
            var textTemplateJSON= JSON.stringify(textTemplateObj);
            window.common.networkConnect(_url, "POST", window.conType.conTyJson, textTemplateJSON, function(res) {
	            if (res.exchangeStatus == 1) {
	            	layer.msg('操作成功',{icon: 1});
		            layer.close(textTemplateLayerDialog);
		            getCataloglist();
	            }else{
	            	layer.msg(res.message,{icon: 2})
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
    }, "分类编码存在，请重输！");
    //验证key值是否唯一
    $.validator.addMethod("checkExsitPcoe",function(value,element){
        return this.optional(element) || (existPcode(value));
    }, "父节点编码不存在！");
    $.validator.addMethod("checkOrderNumer",function(value,element){
        var chrnum = /^[0-9]*$/;
        return this.optional(element) || (chrnum.test(value));
    }, "排序号必须为整数");
}
function existCode(code) {
    var flag = true;
    if($("#cataLogCode").is(":hidden")){//编辑
		return flag;
	}
    $.ajax({
        url:window.top.contextPath + window.portPath.checkCataLogCodeExist+"?code="+code,
        async:false,//这里选择异步为false，那么这个程序执行到这里的时候会暂停，等待
        type:"get",
        dataType:"json",
        success:function(res){
            if(res.exchangeStatus == 1){
                flag=!res.data;
            }
        }
    });
    return flag;
}
function existPcode(pcode) {
    var flag = true;
    if(!$("#cataLogCode").is(":hidden")){//新增
        return flag;
    }
    $.ajax({
        url:window.top.contextPath + window.portPath.checkCataLogPcodeExist+"?pcode="+pcode,
        async:false,//这里选择异步为false，那么这个程序执行到这里的时候会暂停，等待
        type:"get",
        dataType:"json",
        success:function(res){
            if(res.exchangeStatus == 1){
                flag=res.data;
            }
        }
    });
    return flag;
}
function existBusinessByCode(code) {
    var flag;
    $.ajax({
        url:window.top.contextPath + window.portPath.checkBusinessByCatacode+"?cataCode="+code,
        async:false,//这里选择异步为false，那么这个程序执行到这里的时候会暂停，等待
        type:"get",
        dataType:"json",
        success:function(res){
            if(res.exchangeStatus == 1){
                flag=res.data;
            }
        }
    });
    return flag;
}
function removeCataLogByCode(){//移除分类
	var code = $("#selectedCataLogCode").val();
    var name = $("#selectedCataLogName").val();
    if(!code || code == ""){
		layer.msg('请选择一条分类进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除分类--'+name+'？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var code = $("#selectedCataLogCode").val();
		if(existBusinessByCode(code)){
            layer.msg("该分类下有业务，删除失败",{icon: 2});
            return;
		}else {
            var _url = window.top.contextPath + window.portPath.updateCataLogStatus+"?code="+code;
            window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
                if (res.exchangeStatus == 1) {
                    layer.msg('删除成功',{icon: 1});
                    getCataloglist();
                    $("#selectedCataLogCode").val("");
                }else{
                    layer.msg(res.message,{icon: 2});
                }
            });
		}

	});
}
function openCataLogDetails(){//打开模板详情
	var code = $("#selectedCataLogCode").val();
	var _url = window.top.contextPath + window.portPath.findCataLogByCode+"?code="+code;
	window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
        if (res.exchangeStatus == 1) {
        	for(var key in res.data){
        		$("#textTemplateForm input[name='"+key+"']").val(res.data[key]);
			}
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}
function updateTemplateSelection(even, code, name){
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedCataLogCode").val("");
		$("#selectedCataLogName").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTemplate']").val("");
		$(even).val("true");
		$("#selectedCataLogCode").val(code);
        $("#selectedCataLogName").val(name);
	}
}

function openFileInput($index, hasContent){
	if(hasContent == 'true'){
		var laryerIndex = layer.confirm('模板已经存在，确认是否重新上传？', {
			  btn: ['取消', '确认']
			}, function(index, layero){
				layer.close(index);
			}, function(index){
				$("#orgfileUpload" + $index)[0].click(function(event){
					event.stopPropagation();
					event.preventDefault();
				});
			});
	}else{
		$("#orgfileUpload" + $index)[0].click();	
	}
}
var childClassList = null;
function updateChildClass() {
    var code = $("#selectedCataLogCode").val();
    if(!code || code == ""){
        layer.msg('请选择一条分类进行编辑子节点！',{icon: 5});
        return;
    }
    //$("#childClassListTable").empty("");
    var tr='<tr class="color333 font-16" style="font-size: medium;text-align: center">\n' +
        '<th width="40%">字典编码</th>\n' +
        '<th width="40%">字典名称</th>\n' +
        '<th width="15%">排序号</th>\n' +
        '<th width="5%"></th>\n' +
        '</tr>';
    $("#childClassListTable").html(tr);
    var _url = window.top.contextPath + window.portPath.findChildCataLogList+"?pcodes="+code;
    window.common.networkConnect(_url, "GET", window.conType.conTyJson, null, function(res) {
        if (res.exchangeStatus == 1) {
         if(res.data.length>0){
			for(var i=1;i<=res.data.length;i++){
                var obj = res.data[i-1];
                rowHtml(childClassListTable,obj);
			}

		 }else {
             rowHtml(childClassListTable);
		 }
        }else{
            layer.msg(res.message,{icon: 2});
        }
    });

	var _title ="编辑子节点";
    childClassList = layer.open({
        title : _title,
        type : 1,
        area: ['800px', '500px'],
        content : $("#childClassList"),
        cancel: function(){
            // 右上角关闭事件的逻辑
            getCataloglist();
            $("#selectedCataLogCode").val("");
        }
    });
}
function rowHtml($table,rowData){
    if(!rowData){
        rowData = {};
        rowData.code = '';
        rowData.name = '';
        rowData.orderNumber = 1;
    }
    var newTr = $table.insertRow();
    newTr.innerHTML='<td>\n' +
        '<div class="hs-input" style="width: 97%">\n' +
        '<input required type="text" style="text-align:center;align-self: center;" name="code" value="'+rowData.code+'" autocomplete="off" class="input-inner" maxlength="50">\n' +
        '</div>\n' +
        '</td>\n' +
        '<td>\n' +
        '<div class="hs-input" style="width: 97%;">\n' +
        '<input required type="text"style="text-align:center;align-self: center;" name="name" value="'+rowData.name+'" autocomplete="off" class="input-inner" maxlength="50">\n' +
        '</div>\n' +
        '</td>\n' +
        '<td>\n' +
        '<div class="hs-input" style="width: 97%">\n' +
        '<input required type="number"style="text-align:center;align-self: center;" min=1 max=9 name="orderNumber" value="'+rowData.orderNumber+'" autocomplete="off" class="input-inner" maxlength="50">\n' +
        '</div>\n' +
        '</td>\n' +
        '<td>\n' +
        '<a class="iconfont icon-remove"  onclick="deleRow(this);"></a>\n' +
        '\n' +
        '</td>';
}


function closeChildClassList(){//关闭编辑弹窗
    if(childClassList){
        layer.close(childClassList);
    }else{
        layer.closeAll();
    }
    $("#selectedCataLogCode").val("");
}
function deleRow(node) {
    //通过this找到父级元素节点
    var tr = node.parentNode.parentNode;
    var code = tr.getElementsByTagName("INPUT")[0].value;
    var name = tr.getElementsByTagName("INPUT")[1].value;
    var orderNumber = tr.getElementsByTagName("INPUT")[2].value;
    if(judge(code)||judge(name)){
        layer.confirm('清空数据？', {
            btn: ['取消', '确认']
        }, function(index, layero){
            layer.close(index);
        }, function(index){
            tr.getElementsByTagName("INPUT")[0].value=null;
            tr.getElementsByTagName("INPUT")[1].value=null;
            tr.getElementsByTagName("INPUT")[2].value=null;
        });

    }else {
        //找到表格
        var tbody = tr.parentNode;
        if(tbody.rows.length <= 2){
            layer.msg("至少保留一行编辑栏！",{icon: 2})
        }else {
            //删除行
            tbody.removeChild(tr);
        }
    }
}
var chrnum = /^[a-zA-Z][a-zA-Z0-9_]*$/;
var chrnum3 = /^[0-9]*$/;
function saveTableData() {
    var code = $("#selectedCataLogCode").val();
    var childClassListTable = document.getElementById("childClassListTable");
    var catalogArray=new Array();
    var catalog;
    /*$("#childClassListTable tr:gt(0)").each(function () {
        var ccode = $(this).children().find("input[name='code']").val();
        alert(ccode);
        var name = $(this).children().find("input[name='name']").val();
        alert(name);
        var orderNumber = $(this).children().find("input[name='orderNumber']").val();
        alert(orderNumber);
    });*/

    for (var i=1;i<childClassListTable.rows.length;i++){
        var value1 = childClassListTable.rows[i].cells[0].getElementsByTagName("input")[0].value;//编码

        var value2 = childClassListTable.rows[i].cells[1].getElementsByTagName("input")[0].value;//名称
        var value3 = childClassListTable.rows[i].cells[2].getElementsByTagName("input")[0].value;//排序号

            //校验空白栏
            if(judgeNull(value1)&&judgeNull(value2)&&judgeNull(value3)){
                if (childClassListTable.rows.length>2){
                    layer.msg("请删除多余空白栏！",{icon: 2});
                    return;
                }else {
                }
            }
            //检验信息不完全
            if(judgeNull(value1)||judgeNull(value2)||judgeNull(value3)){
                layer.msg("请完善空白信息！",{icon: 2});
                return;
            }

        if(!chrnum.test(value1)){
            layer.msg(value1+" - 不符合字母数字和下划线组合规范！",{icon: 2});
            return;
        }

        if(!chrnum3.test(value3)){
            layer.msg(value3+" - 排序号只能是数字！",{icon: 2});
            return;
        }
        var getPcode = findPCodeBycode(value1);
        if(getPcode!=null){
            if(getPcode!=code){
                layer.msg(value1+" - 编码重复请重新输入！",{icon: 2});
                return;
            }
        }
        catalog={
            code:value1,
            name:value2,
            orderNumber:value3
        };
        catalogArray[i-1]=catalog;
	}
    for(var i=0;i<catalogArray.length-1;i++){
        var firstCode= catalogArray[i].code;
        for(var m=i+1;m<catalogArray.length;m++){
            var secondCode= catalogArray[m].code;
            if(firstCode==secondCode){
                layer.msg(secondCode+" - 编码重复请重新输入！",{icon: 2});
                return;
            }
        }
    }
    var cataLogManage={
        code:code,
        catalogList:catalogArray
    };
    var catalogJson= JSON.stringify(cataLogManage);
    var _url = window.top.contextPath + window.portPath.saveChildCataLogList;
    window.common.networkConnect(_url, "POST", window.conType.conTyJson, catalogJson, function(res) {
        if (res.exchangeStatus == 1) {
            layer.msg('操作成功',{icon: 1});
            layer.close(childClassList);
            getCataloglist();
            $("#selectedCataLogCode").val("");
        }else{
            layer.msg(res.message,{icon: 2})
        }
    });

}
function findPCodeBycode(code) {
    var flag;
    $.ajax({
        url:window.top.contextPath + window.portPath.findPcodeByCode+"?code="+code,
        async:false,//这里选择异步为false，那么这个程序执行到这里的时候会暂停，等待
        type:"get",
        dataType:"json",
        success:function(res){
            if(res.exchangeStatus == 1){
                    flag=res.data;
            }
        }
    });
    return flag;
}
function judge(value) {
    return value != null && value != "";
}
function judgeNull(value) {
    return value == null || value == "";
}
template.defaults.imports.dateFormat  = window.common.dateFormat;//注册过滤器