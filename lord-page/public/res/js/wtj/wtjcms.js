// JavaScript Document
$(function() {
	
	//二维码点击
	if(jQuery("#apptuiguang2").hasClass("app-small2")){
		jQuery(".app-open").trigger('click');
	}
	
	var widthdow=$(window).width();
	cmsbanner();	
	$(window).resize(function() {	
		var widththis=$(window).width();
		if(widthdow<widththis){		
	 	  window.location.href=window.location.href;
		}
	});

    //咨询列表页图片不变形
	$('.cnsul-concent-img').each(showImg);
    
	//评论弹出回复按钮	
	reply();

		//咨询详情小广告
	 $("#albumbanner").slide({
		titCell:".fCl-nav li",
        mainCell:".bd ul",
        effect:"leftLoop",
        autoPlay:false,
		pnLoop:true,
		delayTime:200
    });

/*文本字数计算截取
if($(".consultation-content-r").hasClass("consultation-content-r")){
	var len;
	var num=90;
	$(".ul-concent-cnsul .cnsul-concent-r p a").each(function(index, element) {
		len= Math.round($(this).text().replace(/[^\x00-\xff]/g, '**').length / 2);
		if (len >= num) {
			$(this).html($(this).text().substring(0, num)+"...<i>［查看全文］</i>");
		   return;
		}else{
			$(this).append("...<i>［查看全文］</i>");
			return;
			}       
    });
}
*/

//咨询列表菜单
$(".special-nav-ul li .special-nav-name").click(function(e) {
	$(this).children("a").click(function(e) {
		e.stopPropagation(); 
    });
	$(this).parent().siblings().removeClass("special-nav-on");
    $(this).parent().toggleClass("special-nav-on");
});

//添加查看全部按钮
if($(".consultation-content-r").hasClass("ency-content-l")){
	
	$(".para").each(function(index, element) {
		 var hrefmy=$(element).find("h2 a").attr("href");
		 if($.isBlank(hrefmy)){
			 return;
		 }
         $(element).find(".para-main p:last").append('<a href="'+hrefmy+'" target="_blank" >［查看全部］</a>');
    });

}
	//百科页
$(".catalog-nav").hover(function(e) {
  $(this).find(".encycl-nav").show();  
	if($("#nav-ul-encycl li").length>8){
		$(".btn-box").show();
	}
	$(".encycl-nav-box").slide({ mainCell:".bd #nav-ul-encycl", effect:"top", autoPlay:false, pnLoop:false, delayTime:0, vis:8});
	
	$("#nav-ul-encycl li").click(function(e) {
		$(this).siblings().removeClass("li-encycl-on");
		$(this).addClass("li-encycl-on");
	});	  
  
}, function(){
  $(this).find(".encycl-nav").hide(); 
});



/*$(document).scroll(function() {

  var wtop=$(this).scrollTop();
  var total=$(".anchor-ency").length;
  var atop = $(".ems-anchor"+i).offset().top;
  var atopnext = $(".ems-anchor"+i+1).offset().top;
  if(wtop>atop>atopnext){
	  $("#nav-ul-encycl li").eq(i-1).addClass("li-encycl-on");
  }

});*/

//其他隐藏和显示
	$(".space-li .style-r-btn .up").bind("click",function(){
		$(this).toggleClass("down");
		var total2=$(this).parents(".space-li").find(".style-dd").find("a").length;
		var rows2=parseInt(total2/16)+1;
		var heightval2=rows2*38;
		if($(this).hasClass("down")){
			$(this).parents("li").find(".style-dd").animate({"height":heightval2},300);
		}else{
			$(this).parents("li").find(".style-dd").animate({"height":"22"},300);
		}

	});

	//点击选择条件
	$('.space-li').each(function(){
		var this_ = $(this);
		if (this_.hasClass('province-li')) {
			this_.find('.style-dd').find('a').click(function(e){
				$(this).toggleClass("space-a-on");
				$('.city-li').show();
			});
			//点击无限按钮
			this_.find('.infinite').click(function(e) {
				$(this).toggleClass("infinite-on");
				$('.city-li').hide();
			});
		}else{
			this_.find('.style-dd').find('a').click(function(e){
				$(this).toggleClass("space-a-on");
			});
			this_.find('.infinite').click(function(e) {
				$(this).toggleClass("infinite-on");
			});
		}
	});


if($("#seo-op-div").hasClass("seo-op-div")){	
	
	var seohtml = '<div id="seo-open-windows">\
	     <div class="seo-all-bg"></div>\
		  <div class="settled-box"> <i class="settled-close"></i>\
		    <div class="settled-from">\
		    <form  id="enterFormId" method="post">\
		     <div class="clearfix in-tlt">申请入驻</div>\
		      <div class="settled-group">\
		        <label>公司名</label>\
		        <input name="name" type="text" class="companyname" placeholder="输入公司名" />\
		        <div class="prompt-box"></div>\
		      </div>\
		      <div class="settled-group">\
		        <label>公司地址</label>\
		        <input name="adress" type="text" placeholder="输入公司地址" />\
		      </div>\
		      <div class="settled-group">\
		        <label>联系人</label>\
		        <input name="people" type="text" placeholder="输入联系人名字" />\
		      </div>\
		      <div class="settled-group">\
		        <label>联系方式</label>\
		        <input name="phone" type="text"  class="contactinfo" placeholder="输入联系方式" />\
		        <div class="prompt-box"></div>\
		      </div>\
		      <a href="javascript:enterSave();" class="settled-sbm-btn" rel="nofollow" >提交</a>\
		      </form>\
		      <p>更多商务洽谈请联系</p>\
		      <p>刘先生：13928897111、林先生：13682281766</p>\
		    </div>\
		    <div class="success-box">\
		    	<div class="success-c">\
		        	<i></i>\
		            <p id="result"></p>\
		        </div>\
		        <span></span>\
		    </div>\
		  </div>\
		</div>';	
	

	//申请入驻
	$(".apply-btn").click(function(e) {
		$(seohtml).appendTo('#seo-op-div');
		$("#seo-open-windows").show();
		var seowidth=($("#seo-open-windows .settled-box").width()+116)/2;
		var seoheight=($("#seo-open-windows .settled-box").height()+40)/2;
		$("#seo-open-windows .settled-box").css({"margin-left":-seowidth,"margin-top":-seoheight});
		//关闭
		$("#seo-open-windows .settled-close").click(function(e) {
			$('#seo-op-div').empty();
		});
		

	
	});
	

	//提交
	/*$(".settled-sbm-btn").click(function(e) {
	    if($(".companyname").val()=="" || $(".contactinfo").val()==""){
			return;
		}else{
			$(".success-box").show();
			$(".settled-from").hide();
			
		clearFunc = setInterval(function() {
			ettledclose();
			clearTimeout(clearFunc);
		}, 4000);
		}
	});*/
}
//公司名鼠标离开触发
$(".companyname").blur(function(e) {
	var pr=$(this).parent().find(".prompt-box");
    if($(this).val()==""){
		pr.show();
		$(this).addClass("errored");
		pr.html("公司名不能为空！");
	}else{
		pr.hide();
	}
});
//联系方式鼠标离开触发
$(".contactinfo").blur(function(e) {
	var pr=$(this).parent().find(".prompt-box");
    if($(this).val()==""){
		pr.show();
		$(this).addClass("errored");
		pr.html("联系方式不能为空！");
	}else{
		pr.hide();
	}
});
//所有INPUT获取焦点触发
$(".settled-from input").focus(function(e){
	var pr=$(this).parent().find(".prompt-box");
	$(this).removeClass("errored");
	pr.hide();
	pr.html("");	
});

})
//咨询列表页图片不变形
function showImg(){
	var scale = 279/174;
	var imgTag = $(this).find('img');
	var realW, realH;

	realW = imgTag.width();
	realH = imgTag.height();
	if (imgTag.get(0).complete) {
		if (realW/realH == scale) {
			imgTag.css({'width': '100%'});
		} else if (realW/realH > scale) {
			var left = -Math.ceil((realW*174/realH - 279)/2);
			imgTag.css({'height': '100%',
				'margin-left': left + 'px'
			});
		} else {
			var top = -Math.ceil((realH*279/realW - 174)/2);
			imgTag.css({'width': '100%',
				'margin-top':  top + 'px'
			});
		}
		imgTag.css('visibility','visible');
	} else {
		imgTag.load(function(){
			realW = imgTag.width();
			realH = imgTag.height();
			if (realW/realH == scale) {
				imgTag.css({'width': '100%'});
			} else if (realW/realH > scale) {
				var left = -Math.ceil((realW*174/realH - 279)/2);
				imgTag.css({'height': '100%',
					'margin-left': left + 'px'
				});
			} else {
				var top = -Math.ceil((realH*279/realW - 174)/2);
				imgTag.css({'width': '100%',
					'margin-top':  top + 'px'
				});
			}
			imgTag.css('visibility','visible');
		});
	}
}

//评论弹出回复按钮	
function reply(){
	$(".discuss-ul li").hover(function(e) {
		   $(this).find(".postitem-actions").css("display","inline"); 
		},function(){
		   $(this).find(".postitem-actions").hide(); 
	});	
		
		//弹出回复框
	$(".postitem-actions").on("click",function(){
		$(this).siblings(".publish-div").slideToggle();
	});
	
	$(".publish-reply-div .publish-btn").on("click",function(){
		$(this).parents(".publish-div").slideUp();
	});	
}


 //cms首页banner动画
function cmsbanner(){
    $("#focusAd").slide({
		titCell:".fCl-nav li",
        mainCell:".bd ul",
        effect:"leftLoop",
        autoPlay:true,
        interTime:5000        
    });	
}