/**
 * pagination plugin depends on jquery
 * 
	currentPage : 		当前页码    默认1,
	size :              分页大小,即每页最多显示多少 
	totalPages:			总页码数, 默认1,
	total:				元素总量,默认0,
	maxPageNumber : 	显示页码数量
    hasJump           	是否有跳转框			
    containeID			元素容器ID
    callBack            换页时触发的回调函数
 */

$.huasiBackPagination={
	 opts : {
			 currentPage : 1,
			 size:10,
			 totalPages:1,
			 total:0,
			 maxPageNumber: 10,
			 hasJump:true,
			 containeID:'huasiPagination',
			 callBack : null
	 	},
	 drawPagination : function(options){
	 					var opts = $.huasiBackPagination.opts ;
					 	opts = $.extend(opts,options);
					 	opts.totalPages = Math.ceil(opts.total / opts.size); 					 
						var startPageIndex = (opts.currentPage - 1) / 5;
				    	startPageIndex = parseInt(startPageIndex) * 5;
				    	startPageIndex = Math.max(1, startPageIndex);
						var endPageIndex = Math.min(opts.totalPages, (startPageIndex + opts.maxPageNumber) - 1);
						if(opts.totalPages > opts.maxPageNumber && endPageIndex - startPageIndex < opts.maxPageNumber){
							startPageIndex = endPageIndex - opts.maxPageNumber + 1;
							startPageIndex = Math.max(1, startPageIndex);
						}
						$.huasiBackPagination.opts = opts ;
					 	$.huasiBackPagination.drawHtml(startPageIndex,endPageIndex);
					 	
					},
	 
	drawHtml : function(startPageIndex,endPageIndex){
		var opts = $.huasiBackPagination.opts;
		if(opts.total > 0){
			var html =  '<div class="ng-pagination t-a-c">'
				+			'<div class="font-16 lineblock page-desc">第&nbsp;<span class="theme_color">'+opts.currentPage+'</span>/<span>'+opts.totalPages+'</span>&nbsp;页，共<span>'+opts.total+'</span>条</div>';
				if(opts.totalPages > 1){
					html += 	'<div class="fr"><ul>'
					+ 			'<li><button id="first" '+ (opts.currentPage == 1 ? 'disabled' : '') +'>首页</button></li>'
					+ 			'<li><button id="prev" '+ (opts.currentPage == 1? 'disabled' : '') +'> < </button></li>';
					for( var i=startPageIndex;i<=endPageIndex;i++){
						var pageSel = ''
						if(i == opts.currentPage){
							pageSel = 'active';
						}
						html += '<li class="' + pageSel + '"><button>'+ i +'</button></li>';
					}
					html += '<li><button id="next" '+ (opts.currentPage == opts.totalPages ? 'disabled' : '') +'> > </button></li>'
					+ 			'<li><button id="last" '+ (opts.currentPage == opts.totalPages ? 'disabled' : '') +'>尾页</button></li>'
					+ 		'</ul>';
					if(opts.hasJump){
						html += '<lable>跳转至<input type="text" id="goto" value="" ></label>'
						+'<span class="gopages">GO</span>'	
					}
				}
				html += "</div></div>";
			$("#"+opts.containeID).html(html);
		}else{
			$("#"+opts.containeID).html("");
		}
		$.huasiBackPagination.buttonBind();
		if(opts.hasJump){
			$.huasiBackPagination.gotoBind();
		}
	},
					 
	buttonBind:function (){
		var opts = $.huasiBackPagination.opts ;
		$("li button").bind('click',function(){
			var _this = this;
			switch(_this.getAttribute("id")){
				case 'first':
					opts.currentPage = 1;
					break;
				case 'prev':
					opts.currentPage = opts.currentPage - 1;
					break;
				case 'next':
					opts.currentPage = opts.currentPage + 1;
					break;
				case 'last':
					opts.currentPage = opts.totalPages;
					break;
				default:
					opts.currentPage = _this.innerHTML;
					break;
			}
			if(opts.callBack){
				var needDraw = opts.callBack(opts);
				if(needDraw===true){
					$.huasiBackPagination.drawPagination(opts);
				}
			}
		});
	},
	
	gotoBind : function(){
		var opts = $.huasiBackPagination.opts ;
		$("#goto").bind('keypress', function(event) {
			var _this = this;
			var num = _this.value;
			if(parseInt(num) > opts.totalPages){
				_this.value = opts.totalPages;
	    		return ;
			}
			if(!/^\d+$/.test(num) && num != ""){//页码只能为数值类型
				_this.value = 1;
		        return;
		    }
			if(num == opts.currentPage){//输入的页码与当前页相同
				return;
			}
			if(num == null || num == "" || parseInt(num) == 0){
				num = 1;
			}
			if(num && event.keyCode==13){
				opts.currentPage = parseInt(num);
				if(opts.callBack){
					var needDraw = opts.callBack(opts);
					if(needDraw===true){
						$.huasiBackPagination.drawPagination(opts);
					}
				}
			}
		});
		
		$(".gopages").bind('click',function(){
            var num =$('#goto').val();
			if(parseInt(num) > opts.totalPages){
				$('#goto').val() == opts.totalPages;
	    		return ;
			}
			if(!/^\d+$/.test(num) && num != ""){//页码只能为数值类型
				$('#goto').val() == 1;
		        return;
		    }
			if(num == opts.currentPage){//输入的页码与当前页相同
				return;
			}
			if(num == null || num == "" || parseInt(num) == 0){
				num = 1;
			}
			if(num){
				opts.currentPage = parseInt(num);
				if(opts.callBack){
					var needDraw = opts.callBack(opts);
					if(needDraw===true){
						$.huasiBackPagination.drawPagination(opts);
					}
				}
			}
		});
	}
};

