$(function(){
	window.common.getNiceScroll("#wrapper");
	showRoleList();
});

function showRoleList(){
	var settingss = {
			data : {
				simpleData : {
					enable : true, //true 、 false 分别表示 使用 、 不使用 简单数据模式
					idKey : "id", //节点数据中保存唯一标识的属性名称
					pIdKey : "parentID", //节点数据中保存其父节点唯一标识的属性名称  
					rootPId : -1 //用于修正根节点父节点数据，即 pIdKey 指定的属性值
				},
				key : {
					name : "name" //zTree 节点数据保存节点名称的属性名称  默认值："name"
				}
			},
			check : {
				enable : true, //true 、 false 分别表示 显示 、不显示 复选框或单选框
				nocheckInherit : true, //当父节点设置 nocheck = true 时，设置子节点是否自动继承 nocheck = true 
				chkboxType : { "Y" : "ps", "N" : "ps" }
			},
			 callback:{  
                 beforeCheck:true,
                 onCheck:onCheck
	            }
          };
      var _url = window.top.contextPath + window.portPath.getlistAllRoles;
	  window.common.networkConnect(_url, "GET", window.conType.conTyForm,'', function(res) {
	        if (res.exchangeStatus == 1) {
	        	zTreeObj = $.fn.zTree.init($("#treeDemo"), settingss, res.data); //初始化树
                zTreeObj.expandAll(false);   //true 节点全部展开、false节点收缩
           }
	    });
	  
}
function onCheck(e,treeId,treeNode){//获取被选中得角色
    var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
    nodes=treeObj.getCheckedNodes(true),
    names="",ids="";
    for(var i=0;i<nodes.length;i++){
    	names=nodes[i].name ;
    	ids =nodes[i].id; 
   }
    $('.permission-test').val(names)
    $('#permissionForm input[name=roleID]').val(ids);
}  


var permissionFormDialog = null,permissionFormDialogTitle="";
function showEditDialog(flag){//打开编辑弹窗
	var _title = '';
	if(flag == 'add'){
		_title = '新增';
		$(':input','#permissionForm').not(':button,:submit,:reset').val('');
		$('#permissionForm input[name=businessId]').val($('#permissionTable input[name=businessId]').val());
		$('#permissionForm input[name=formId]').val($('#permissionTable input[name=formId]').val());
		$('#permissionForm input[name=elementId]').val($('#permissionTable input[name=elementId]').val());
	}else if(flag == 'update'){
		_title = '修改';
		var _id = $("#selectedFormElement").val();
		if(!_id || _id == ""){
			layer.msg('请选择一条数据进行修改！',{icon: 5});
			return;
		}
		getPermissionDetails(function(listData){
		 for(var key in listData){
				$("#permissionForm input[name='"+key+"']").val(listData[key]);
				$("#permissionForm  select[name='"+key+"']").val(listData[key])
			}
			
		});
	}
	permissionFormDialogTitle =_title; 
	permissionFormDialog = layer.open({
		title : _title,
		type : 1,
		area:["600px","310px"],
		scrollbar: false,
		content : $("#permissionFormDialog")
	});
	
}
function removePermissionById(){//根据业务id移除数据
	var _id = $("#selectedFormElement").val();
	if(!_id || _id == ""){
		layer.msg('请选择一条数据进行删除！',{icon: 5});
		return;
	}
	layer.confirm('确认删除？', {
	  btn: ['取消', '确认']
	}, function(index, layero){
		layer.close(index);
	}, function(index){
		var _url = window.top.contextPath + window.portPath.removePermissionByPermissionId;
		var _id = $("#selectedFormElement").val();
		var params = "permissionId="+_id;
		window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
	        if (res.exchangeStatus == 1) {
	        	getPermissionData($('#permissionTable input[name=elementId]').val())
				layer.msg('操作成功',{icon: 1});
	        	$("#selectedFormElement").val("");
	        }else{
	        	layer.msg(res.message,{icon: 2});
	        }
	    });
	});
}
function getPermissionDetails(callback){//根据业务id获取信息
	var _id = $("#selectedFormElement").val();
	var _url = window.top.contextPath + window.portPath.getPermissionByPermissionId;
	var params = "permissionId="+_id;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
        if (res.exchangeStatus == 1) {
        	if(callback) callback(res.data);
        }else{
        	layer.msg(res.message,{icon: 2});
        }
    });
}

function savePermissionData(){//保存数据
    var params = $("#permissionForm").serializeObject();
	var _url = window.top.contextPath;
	if(permissionFormDialogTitle == "新增"){
		_url += window.portPath.savePermission;
	}else{
		_url += window.portPath.updatePermission;
	}
    window.common.networkConnect(_url, "POST", window.conType.conTyJson, params, function(res) {
		if(res.exchangeStatus == 1){
			layer.close(permissionFormDialog);
			getPermissionData($('#permissionTable input[name=elementId]').val())
			layer.msg('操作成功',{icon: 1});
			showRoleList();
    		var SelectArr = $("select")
    		  for (var i = 0; i < SelectArr.length; i++) {
    		  SelectArr[i].options[0].selected = true; 
    		  }
    	      $("#permissionForm input:not([type='button'], [type='submit'], [type='hidden'])").val("");
    	      $("#selectedFormElement").val("");
	    }else{
		    layer.msg("操作失败");
		}
    });
}
function getPermissionData(elemtId){//打开许可
	var _url = window.top.contextPath + window.portPath.getPermissionByElementId;
	var params = "elementId=" + elemtId;
	window.common.networkConnect(_url, "POST", window.conType.conTyForm, params, function(res) {
		if (res.exchangeStatus == 1) {
	        window.common.drawTemplateHtml("permissionTbTemplate", "#permissionTb", {list : res.data});
	    }
	});
}
function closepermissionFormDialog(){//关闭弹框
	layer.close(permissionFormDialog);
	 $('.permission-test').val("");
}
function closepermission(){
	  $(".permissionbox").animate({right:'-1000px'});
}
function updateSelection(even, id){//更新选择状态
	var isCheck = even.checked;
	var _val = $(even).val();
	if(isCheck && _val == "true"){
		$(even).removeAttr("checked");
		$("#selectedFormElement").val("");
		$(even).val("");
	}else{
		$("input[name='checkboxTest']").val("");
		$(even).val("true");
		$("#selectedFormElement").val(id);
	}
}