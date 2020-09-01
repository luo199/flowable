
$(function(){
    submitFormElementsData();
	window.common.getNiceScroll(".wrapper");
});

function closeValid(){//关闭右侧验证弹框
	$(".element-validbox").animate({right:'-500px'});
}

function submitFormElementsData() {//提交表单数据
  $("#formElementsForm").validate({
		rules:{
			emptyMsg:{
				 required: function () {
					return $('#isEmpty').val() == "2";
				    },
			},
			lengthMin:{
				 required: function () {
					return $('#isLengthMin').val() == "1";
				    },
			},
			lengthMinMsg:{
				 required: function () {
					return $('#isLengthMin').val() == "1";
				    },
			},
			lengthMax:{
				 required: function () {
					return $('#isLengthMax').val() == "1";
				    },
			},
			lengthMaxMsg:{
				 required: function () {
					return $('#isLengthMax').val() == "1";
				    },
			},
			numericMin:{
				 required: function () {
					return $('#isNumericMin').val() == "1";
				    },
			},
			numericMinMsg:{
				 required: function () {
					return $('#isNumericMin').val() == "1";
				    },
			},
			numericMax:{
				 required: function () {
					return $('#isNumericMax').val() == "1";
				    },
			},
			numericMaxMsg:{
				 required: function () {
					return $('#isNumericMax').val() == "1";
				    },
			},
			regularExpression:{
				 required: function () {
					return $('#sRegularExpression').val() == "1";
				    },
			},
			errorMessage:{
				 required: function () {
					return $('#sRegularExpression').val() == "1";
				    },
			}
		},
		messages:{
			emptyMsg:"必填项！",
			lengthMin: "必填项！",
			lengthMinMsg: "必填项！",
			lengthMax: "必填项！",
			lengthMaxMsg: "必填项！",
			numericMin: "必填项！",
			numericMinMsg: "必填项！",
			numericMax: "必填项！",
			numericMaxMsg: "必填项！",
			regularExpression: "必填项！",
			errorMessage: "必填项！"
		},
		onsubmit:true,
		errorPlacement: function(error, element) {
		    error.appendTo(element.parent());  
		},
		submitHandler: function(form) {
            var _url = window.top.contextPath + window.portPath.saveEleValidForm;
			var formEleValid = $("#formElementsForm").serialize();
			window.common.networkConnect(_url, "POST", window.conType.conTyForm, formEleValid, function(res) {
				if(res.exchangeStatus == 1){
					closeValid();
					layer.msg('操作成功',{icon: 1});
				}else{
	            	layer.msg(res.message,{icon: 2})
	            }
		    });
		}
	});
}

function resetFormElements(){ //重置
	  $("#formElementsForm input:not([type='button'], [type='submit'], [type='hidden'])").val("");
	  var SelectArr = $("select")
	  for (var i = 0; i < SelectArr.length; i++) {
	  SelectArr[i].options[0].selected = true; 
	  }
}