// JavaScript Document
var WTJ = WTJ || {};
WTJ.msgbox = function(type, msg, sharebox, val) {
	// type=1是失败:提示：没登录，type=2是成功，msg是提示大标题，
	// sharebox=0就是没有分享那个块，sharebox=1是有二标题，sharebox=2是有分享模块, val只手灵感集才调用
	var ticon;// 图标class
	var et = "";// 二标题
	var ets = "";// 分享模块
	switch (type) {
	case 1:
		ticon = 'loseicon';
		et = '<span>请重新试试</span>';
		ets = "";
		break;
	case 2:
		ticon = 'succicon';

		if (sharebox == 0) {
			et = "";
		}
		if (sharebox == 1) {
			et = '已被添加至灵感集 <b>' + val + '</b>';
		}
		if (sharebox == 2) {
			et = '<a href="#">立即查看</a>  |  <a href="#">再搭一张</a>';
			ets = '<p class="share-div">分享到</p><div class="analyse-img-btn"><a href="#" target="_parent" class="sina-wb"></a><a href="#" target="_parent" class="qq-wx"></a><a href="#" target="_parent" class="hb-wz"></a><a href="#" target="_parent" class="qq-gj"></a></div>';
		}
		break;
	}

	var htmlmsgbox = '<div class="addinspiration-bg"></div><div id="msgbox" class="msgbox">\
 <div class="add-succ-box">\
 	<div class="add-succ-head"><i></i></div>\
    <div class="add-succ-main">\
    	<div  class="add-succ-main-center"><i class="'
			+ ticon
			+ '"></i><h3>'
			+ msg
			+ '</h3> <p class="explain-title">'
			+ et
			+ '</p></div>\
    	  <div class="output-btn-div msgbox-btn-div">'
			+ ets
			+ '</div>\
    </div>\
 </div>\
</div>';

	var ml, mt, clearFunc;
	jQuery(htmlmsgbox).appendTo("body");
	// 关闭
	jQuery("#msgbox .add-succ-head i").click(function(e) {
		closemsgbox();
	});

	ml = jQuery("#msgbox").width() / 2;
	mt = jQuery("#msgbox").height() / 2;

	jQuery("#msgbox").css({
		"margin-left" : -ml,
		"margin-top" : -mt
	});
	//jQuery("body").css("overflow","hidden");
	clearFunc = setInterval(function() {
		closemsgbox();
		clearTimeout(clearFunc);
	}, 8000);

	jQuery.Callbacks();
};



WTJ.msgbox2 = function(msg,vid,ojb) {

	var htmlmsgbox = '<div class="addinspiration-bg"></div><div id="msgbox" class="msgbox msgbox2">\
 <div class="add-succ-box">\
 	<div class="add-succ-head"><i></i></div>\
    <div class="add-succ-main">\
    	<div  class="add-succ-main-center"><h3>'+ msg +'</h3></div>\
    	  <div class="msgbox2-btn-div">\
    	  <div class="msgbox2-btn-box"><div class="sure">确定</div><div class="cancel">取消</div>\
    	  </div>\
    </div>\
 </div>\
</div>';

	var ml, mt, clearFunc;
	jQuery(htmlmsgbox).appendTo("body");
	// 关闭
	jQuery("#msgbox .add-succ-head i").click(function(e) {
		closemsgbox();
	});
	// 取消
	jQuery(".msgbox2-btn-box .cancel").click(function(e) {
		closemsgbox();
	});
	// 确定
	jQuery(".msgbox2-btn-box .sure").click(function(e) {
		ojb(vid);
		closemsgbox();

	});

	ml = jQuery("#msgbox").width() / 2;
	mt = jQuery("#msgbox").height() / 2;

	jQuery("#msgbox").css({
		"margin-left" : -ml,
		"margin-top" : -mt
	});
	jQuery("body").css("overflow","hidden");
	//clearFunc = setInterval(function() {
	//	closemsgbox();
	//	clearTimeout(clearFunc);
	//}, 2000);

	//jQuery.Callbacks();
};




//懒加载
function  wtjlazyload(){

	jQuery("img.lazy").lazyload({
		effect : "fadeIn"
  	});

}
//给图片加loading的背景图
function loadImg(customTag){
	jQuery('.lazy-wrap').each(function(){
		var icon = Math.ceil(Math.random() * 3);
		var this_ = jQuery(this);
		var targinTag = customTag || 'lazy';
		var imgTarget = this_.find('.' + targinTag);
		if (this_.attr('class').indexOf('loading') < 0 && imgTarget.length > 0) {
			this_.addClass('loading' + icon);
			imgTarget.load(function(){
				var imgSrc = imgTarget.attr('src');
				if (imgSrc.indexOf('front/grey.png') < 0) {
					this_.removeClass('loading' + icon);
					imgTarget.removeClass(targinTag);
				}
			});
		}
	});
}

// 标签弹出
function taghover() {
	jQuery(".tag-wtj").hover(function(e) {
		var wishBuyBtn = jQuery(this).find(".wishBuy-btn");
		isAddWishBuy(wishBuyBtn); //判断是否加入预购清单
		jQuery(this).find(".tag-content").show();
		var rightxy = jQuery(this).parent().offset().left + jQuery(this).parent().width();
		var topxy=jQuery(this).parent().offset().top;
		var leftpop = jQuery(this).offset().left;
		var toppop = jQuery(this).offset().top;
		if (leftpop + 274 > rightxy) {
			jQuery(this).find(".tag-content").addClass("tag-content-right");
		}
		if(toppop-50<topxy){
			jQuery(this).find(".tag-content").addClass("tag-content-top");
		}
	}, function(e) {
		jQuery(this).find(".tag-content").hide();
	});	
}
//判断是否加入预购清单
function isAddWishBuy(wishBuyBtn, isCall){
	if(!wishBuyBtn )return;

	var lazyWishBuy = wishBuyBtn.hasClass("lazy-wishBuy");
	if(!loginStat && !isCall) return;
	if(!lazyWishBuy) return;

	var type = wishBuyBtn.attr("data-type");
	var tid = wishBuyBtn.attr("data-tid");
	var mid = wishBuyBtn.attr("data-mid") || '';

	if(mid=='' && (typeof(tid) == 'undefined' || tid == '')){
		wishBuyBtn.removeClass("add-collocation-success-btn");
		wishBuyBtn.removeClass("wishbuy_Product_"+tid);
		wishBuyBtn.removeClass("wishBuy-btn");
		wishBuyBtn.addClass("off-shelf-btn");
		wishBuyBtn.html('<i></i>商品已下架');
	}
	if (typeof(type) == 'undefined' || type == '' || typeof(tid) == 'undefined' || tid == '') {
		return;
	}

	var url = '/isAddWishBuy.do?type=' + type + '&tid=' + tid;
	jQuery.ajax({
		url : url,
		type : 'get',
		dataType : 'json',
		success : function(json) {
			if (json.code == 1) {
				var o = json.data;
				var isWishBuy = !!o.isWishBuy;
				var checkStatus = o.checkStatus;
				var item = jQuery('.wishBuy-btn[data-type="' + type + '"][data-tid=' + tid + ']');
				if (isWishBuy) {
					item.addClass("add-collocation-success-btn");
					item.html('<i></i>已加入');
				} else {
					item.removeClass("add-collocation-success-btn");
					item.html('<i></i>加入欲购清单');
				}
				if(type == 'Product' && checkStatus != 'Pass'){
					item.removeClass("add-collocation-success-btn");
					item.removeClass("wishbuy_Product_"+tid);
					item.removeClass("wishBuy-btn");
					item.addClass("off-shelf-btn");
					item.unbind();
					item.html('<i></i>商品已下架');
				}
			}
		}
	});

	wishBuyBtn.removeClass("lazy-wishBuy");
}

function closemsgbox() {
	jQuery(".addinspiration-bg").remove();
	jQuery("#msgbox").remove();
	jQuery("body").removeAttr("style");
}

// 内容为空 className是class的名字
function isEmpty(className) {
	var empy = '<div class="isemptydiv">暂无内容！</div>';
	jQuery(empy).appendTo("." + className);

}

//处理右键图片另存为
var rightKeyEvent = function () {
	jQuery('.product-img').each(function(){
		var rthis_ = jQuery(this);
		if (rthis_.hasClass('mybuilddet-pj')){
			if (rthis_.siblings('.mybuilddet-pj-pop').length == 0) {
				return;
			}
		}
		rthis_.bind('dragstart', function(){
			return false;
		});
		var aUrl = '';
		var newOpenbox = '';
		var productA = rthis_.children('a');
		if (productA.length != 0){
			aUrl = productA.attr('href');
			newOpenbox = '<div class="rm-row"><a class="new-open" href="" target="_blank">从新窗口打开链接</a></div>'
		}else if(rthis_.hasClass('mybuilddet-pj')){
			var mybuildPob = rthis_.siblings('.mybuilddet-pj-pop').find('.pop-collocation-l');
			aUrl = mybuildPob.attr('href');
		} else {
			aUrl = window.location;
		}
		rthis_.bind('contextmenu', function(e){
			e = e || window.event;
			//阻止冒泡
			e.cancelBubble = true;
			if (e.stopPropagation) {
				e.stopPropagation();
			}
			jQuery('.right-menu').remove();
			var left = e.pageX;
			var top = e.pageY;
			var html = '<div class="right-menu"> '
				+ '<div class="rm-row"><a class="copy-content" href="javascript:;">图片分享</a></div>'
				+ newOpenbox
				+ '</div>';
			jQuery(html).appendTo('body');
			jQuery('.new-open').attr('href', aUrl);
			jQuery('.right-menu').css({
				'left': left + 'px',
				'top': top + 'px'
			});
			jQuery('.right-menu').bind('contextmenu', function(){
				return false;
			});
			var copyContent = jQuery('.copy-content');
			copyContent.bind('click', function(e){
				jQuery('html').css({
					'overflow-y':'hidden'
				});
				var productAddress = dynSite + '/item/';
				var productImg = rthis_.find('img');
				var productTitle = productImg.attr('alt');
				var imgAddress = productImg.attr('src');
				var imgAddressM, imgAddressS ;
				if (imgAddress.indexOf('qty_large') > 0) {
					imgAddressM = imgAddress.replace('qty_large', 'middle');
					imgAddressS = imgAddress.replace('qty_large', 'small');
				}else if (imgAddress.indexOf('large') > 0){
					imgAddressM = imgAddress.replace('large', 'middle');
					imgAddressS = imgAddress.replace('large', 'small');
				}else if (imgAddress.indexOf('middle') > 0){
					imgAddressM = imgAddress;
					imgAddressS = imgAddress.replace('middle', 'small');
				}else{
					imgAddressM = imgAddress.replace('product', 'pic/middle/product');
					imgAddressS = imgAddress.replace('product', 'pic/small/product');
				}
				var bigImg = '<div><a href="' + aUrl + '"><img src="'+imgAddressM+'" /></a></div>'
					+ '<div><a style="text-decoration:none;color:#444;" href="'+ dynSite + '">乌托家</a>-<a style="text-decoration:none;color:#444;"  href="'+productAddress+'">精品家具</a>-<a style="text-decoration:none;color:#444;"  href="'+aUrl+'">'+productTitle+'</a></div>';

				var smallImg ='<div><a href="' + aUrl + '"><img src="'+imgAddressS+'" /></a></div>'
					+ '<div><a style="text-decoration:none;color:#444;" href="'+ dynSite + '">乌托家</a>-<a style="text-decoration:none;color:#444;"  href="'+productAddress+'">精品家具</a>-<a style="text-decoration:none;color:#444;"  href="'+aUrl+'">'+productTitle+'</a></div>';

				if (jQuery('.modal-dialog').length == 0){
					var dialogBox = '<div class="modal-dialog">'
						+ '<div class="dialog-mark"></div>'
						+ '<div class="dialog-box">'
						+ '<div class="modal-header">图片分享</div>'
						+ '<div class="modal-body">'
						+ '<div class="dialogHead">复制此代码嵌入此照片在您的网站：</div>'
						+ '<div class="dialogCaption">大图(500像素):</div>'
						+ '<div class="textarea-box">'
						+ '<textarea onclick="this.select()" readonly="readonly" class="embedPhoto500"></textarea>'
						+ '</div>'
						+ '<div class="dialogCaption">小图(200像素):</div>'
						+ '<div class="textarea-box">'
						+ '<textarea onclick="this.select()" readonly="readonly" class="embedPhoto200"></textarea>'
						+ '</div>'
						+ '<div class="dialogCaption">地址:</div>'
						+ '<div class="textarea-box">'
						+ '<textarea onclick="this.select()" readonly="readonly"  class="shortCode"></textarea>'
						+ '</div>'
						+ '</div>'
						+ '<div class="modal-footer"><input class="hzBtn" value="确定" type="button"></div>'
						+ '</div>'
						+ '</div>';
					jQuery(dialogBox).appendTo('body');
				} else {
					jQuery('.modal-dialog').show();
				}
				var dialogTop = Math.ceil(jQuery('.modal-dialog').height()-jQuery('.dialog-box').height())/2;
				jQuery('.dialog-box').css('margin-top',dialogTop+'px');
				jQuery('.embedPhoto500').text(bigImg);
				jQuery('.embedPhoto200').text(smallImg);
				jQuery('.shortCode').text(aUrl);
				jQuery('.dialog-mark').bind('click', function(){
					jQuery('html').css({
						'overflow-y':''
					});
					jQuery('.modal-dialog').hide();
				});
				jQuery('.hzBtn').bind('click', function(){
					jQuery('html').css({
						'overflow-y':''
					});
					jQuery('.modal-dialog').hide();
				});
				jQuery('.right-menu').hide();
				return false;
			});
			jQuery('.new-open').bind('click', function(e){
				jQuery('.right-menu').hide();
			});
			return false;
		});
	});
};


var dynSite;
var searchUrl;
var searchTimeouts;
// 用户登录状态
var loginStat = false;
var loginIcon = "";
var loginNickName = "";

jQuery(function() {

	//处理右键图片另存为
	if (jQuery('.product-img').length != 0) {
		rightKeyEvent();
	}

	jQuery('body').bind('click', function(e){
		if (jQuery('.right-menu').length != 0) {
			jQuery('.right-menu').hide();
		}
	});
	jQuery('body').bind('contextmenu', function(e){
		if (jQuery('.right-menu').length != 0) {
			jQuery('.right-menu').hide();
		}
	});

	//图片懒加载的背景图
	loadImg();
	//图片懒加载
   if(jQuery("img").hasClass("lazy")){
		wtjlazyload();
   }
	dynSite = window.location.host;
	if (dynSite.indexOf("http://") == -1) {
		dynSite = "http://" + dynSite;
	}
	searchUrl = dynSite + "/search.do";
	// 用户登录状态
	loginStat = false;
	loginIcon = "";
	loginNickName = "";

	jQuery.isBlank = function(obj) {
		return (!obj || jQuery.trim(obj) === "");
	};

	// 标签弹出
	taghover();

	// 鼠标经过灵感小图图片弹出效果
	if (jQuery(".ul-inspiration-img").children().hasClass("pop-img-btn")) {
		inspirasmall();
	}
	
	jQuery(window.document).scroll(function(){
			if(jQuery(this).find(".search-main-menu").hasClass("search-main-menu")){
				var topval=jQuery(this).scrollTop();
				if(topval>210){
					jQuery(".search-main-menu").css({"position":"fixed","top":"0px","width":"100%","background-color":"#fff","z-index":"99999"});
				}else{
					jQuery(".search-main-menu").css({"position":"inherit","top":"0px","width":"100%","background-color":"#fff","z-index":"99999"});
					
				}
			}
	});

	// 搜索左边漂浮菜单
	jQuery(".search-main-menu a").click(function(e) {
		jQuery(this).addClass("search-munu-on").siblings().removeClass("search-munu-on");
		var topnum = jQuery(this).index();
		jQuery(".search-box").eq(topnum).show().siblings(".search-box").hide();
		jQuery(document).scrollTop("180"); 
	});
	// 搜索弹出层
	jQuery(".search-btn").click(function(e) {
		jQuery(".screen-all").height(jQuery(document).height()).show();
		jQuery(".search-all-box").show();
		jQuery(".search-ipt").val("");
		jQuery(".search-ipt").focus();
		if(jQuery('#content').length>0){
			jQuery('#content').infinitescroll('pause');
		}
	});
	// 搜索关闭
	jQuery(".header-close-ico").click(function(e) {
		jQuery(".screen-all").hide();
		jQuery(".search-all-box").hide();
		if(jQuery('#content').length>0){
			jQuery('#content').infinitescroll('resume');
		}
	});
	// 搜索智能提示
	jQuery(".search-ipt").keyup(function(e) {
		if (e.keyCode == 13) {
			searchresult();
			return;
		}
		suggest();
		//clearTimeout(searchTimeouts);
		//searchTimeouts = setTimeout('searchresult()', 3000);
	});

	// 关闭智能提示
	jQuery("body").not(".search-result-menu").click(function(e) {
		jQuery(".search-result-menu").hide();
	});
	jQuery(".result-menu-main a").click(function(e) {
		jQuery(".search-result-menu").hide();
		jQuery(".search-main-box").show();
	});

	// 注册页面
	jQuery(".lj-register").click(function(e) {
		jQuery(".login-box, .register2-box").hide();
		jQuery(".register-box").show();
	});
	// 注册页面2
	jQuery(".register-btn").click(function(e) {
		jQuery(".login-box, .register-box").hide();
		jQuery(".register2-box").show();
	});

	// 推荐商品HOVER图片
	goodshover();

	jQuery('.sso_login_btn').click(function() {
		loginedAction();
	});
	
	if(parseInt(jQuery(".links-l").height())<24){
		jQuery('.move-link').hide();
	}
	jQuery('.move-link').click(function() {
		jQuery(this).parent().toggleClass("links-l-all");
		jQuery(this).children("a").toggleClass("down");		
         var scrollHeight = jQuery(document).height();
         jQuery(document).scrollTop(scrollHeight);
	});

	//被推广的用户进入网站，保存Cookies
	var cmpfrom = getUrlQueryParam("cmpfrom");
	var cmpfromCookies = jQuery.cookie('wtj_cmpfrom');
	if (jQuery.isBlank(cmpfromCookies) && !jQuery.isBlank(cmpfrom)) {
		jQuery.cookie('wtj_cmpfrom', cmpfrom, {
			expires : 7,
			path : '/',
			domain: 'wutuojia.com'
			//domain: 'localhost'  //测试环境域名
		});
	}
})

function searchresult() {
	jQuery(".search-main-box").show();
};

/* 注册登录弹出框高和宽 */
function loginlayout() {
	//var loginpopheight = jQuery(window).height();
	//var loginpopwidth = jQuery(window).width() + 20;
	jQuery(".ifranmes, .ifranmes iframe").css({"width":"100%","height":"100%"});

}

function searchresult() {
	var keyword = jQuery(".search-ipt").val();
	search(keyword);
};

// 搜索智能提示
function suggest() {
	var keyword = jQuery.trim(jQuery(".search-ipt").val());
	if (jQuery.isBlank(keyword)) {
		return;
	}

	var params = {
		act : "suggest",
		kw : keyword
	};
	jQuery.get(searchUrl, params, function(data, status) {
		var code = data.code;
		if (code == 1) {
			var suggest = "";
			suggest += getSuggestHtml("灵感", "i", data.data.is);
			suggest += getSuggestHtml("商品", "p", data.data.ps);
			suggest += getSuggestHtml("我搭", "m", data.data.ms);
			jQuery(".result-menu-ul").html(suggest);
			jQuery(".search-result-menu").show();
			// alert(suggest);
		}

		jQuery(".result-menu-main a").on("click", function(e) {
			var type = jQuery(this).attr("st");
			var keyword = jQuery(this).attr("kw");

			search(keyword, type);
		});

	}, "json");
}

function getSuggestHtml(suggestName, suggestType, isuggest) {
	var vhtml = "";
	if (isuggest) {
		vhtml += '<li><dl><dt>' + suggestName + '</dt><dd><div class="result-menu-main">';
		for (var i = 0; i < isuggest.length; i++) {
			vhtml += '<a st="' + suggestType + '" kw="' + isuggest[i] + '" href="javascript:;">' + isuggest[i] + '</a>';
		}
		vhtml += '</div></dd></dl></li>';
	}
	return vhtml;
}

// 搜索
function search(keyword, type) {
	if (jQuery.isBlank(keyword)) {
		return;
	}
	jQuery(".search-result-menu").hide();
	//clearTimeout(searchTimeouts);

	var vInput = jQuery(".search-ipt").val();
	var lastInput = jQuery("#sresultInfo").attr("keyword");
	// 不需要重新搜索
	if (!jQuery.isBlank(lastInput) && lastInput == vInput) {
		return;
	}

	jQuery("#stext_kw").val(keyword);
	jQuery("#stext_st").val(type);
	jQuery("#stext_ui").val(vInput);
	jQuery("#searchBtn").click();
	/*
	 * var params = {act:"ajax", kw:keyword, st:type, ui:vInput};
	 * jQuery.get(searchUrl,params,function(data,status){ //alert("Data: " +
	 * data + "nStatus: " + status);
	 * 
	 * jQuery(".search-main-box").html(data); jQuery(".search-main-box").show();
	 * var lastKw = jQuery("#sresultInfo").attr("keyword");
	 * jQuery(".search-result").html("搜索到 " + lastKw + " 个结果");
	 * jQuery(".search-ipt").val(keyword);
	 * 
	 * });
	 */
}

function loadSBoxData(sbox) {
	var sd = sbox.attr("sd");
	var st = sbox.attr("st");
	if ("y" == sd) {
		// console.log("是否有数据:" + sd);
		return;
	}

	var keyword = jQuery("#sresultInfo").attr("keyword");
	var params = {
		act : "s",
		kw : keyword,
		st : st
	};
	jQuery.get(searchUrl, params, function(data, status) {
		sbox.html(data);
		sbox.attr("sd", "y");
	});
}

// 推荐商品HOVER图片
function goodshover() {
	jQuery(".rec-commodity-ul li").hover(function(e) {
		jQuery(this).find(".rec-commodity-box").show();
		jQuery(this).find(".rec-commodity-pop").delay(500).slideDown();
	}, function(e) {
		jQuery(this).find(".rec-commodity-pop").stop(true, true).slideDown();
		jQuery(this).find(".rec-commodity-box").hide();
		jQuery(this).find(".rec-commodity-pop").hide();
	});
}
/**商品图片，我搭图片切换*/
function switchProductImg(thisObj, isSwitch){
	var imgObj =  jQuery(thisObj).find(".product-img img");
	if(!imgObj) return;
	var data_original = imgObj.attr("data-original");
	var data_targetType = imgObj.attr("data-targetType");
	var data_targetId = imgObj.attr("data-targetId");
	var data_mybuildImg = imgObj.attr("data-mybuildImg");
	if(!data_original || data_original=="" || !data_targetType || data_targetType=="" || !data_targetId || data_targetId==""){
		return;
	}
	if(imgObj.hasClass("switchImg")){
		imgObj.removeClass("switchImg");
		jQuery.ajax({
			type: 'POST',
			url: dynSite + "/diy/switchProductImg.do",
			data: {"id":data_targetId, "productType":data_targetType},
			dataType: 'json',
			async: true,
			contentType:"application/x-www-form-urlencoded; charset=utf-8",
			success: function(data) {
				if(data.code==1 || data.code=="1"){
					data_mybuildImg = data.data.imageUrl;
					imgObj.attr("data-mybuildImg", data_mybuildImg);

					var priceSpan = jQuery(thisObj).find(".resources-price span");
					var productPrice = parseFloat(data.data.price).toFixed(2);
					if(productPrice && !isNaN(productPrice)){
						priceSpan.text(productPrice);
					}
					var priceText = priceSpan.text();
					if(!priceText || priceText==''){
						jQuery(thisObj).find(".resources-price").hide();
					}

					if(isSwitch && !(!data_mybuildImg||data_mybuildImg=="")){
						imgObj.attr("src",data_mybuildImg);
					}else{
						imgObj.attr("src",data_original);
					}
				}
			}
		});
	}else{
		if(isSwitch && !(!data_mybuildImg||data_mybuildImg=="")){
			imgObj.attr("src",data_mybuildImg);
		}else{
			imgObj.attr("src",data_original);
		}
	}

}

//鼠标经过灵感小图图片弹出效果
function inspirasmall(){	
	jQuery(".ul-inspiration-img").hover(function() {
		jQuery(this).find(".pop-img-btn").animate({
			bottom : "0"
		}, 300);
	}, function() {
		jQuery(this).find(".pop-img-btn").stop(true, true);
		jQuery(this).find(".pop-img-btn").animate({
			bottom : "-50"
		}, 300);
	});		
}

// ------------------------------登录 start--------------------------------
/**
 * 登录成功的回调函数
 * 
 * @param nickName
 *            用户昵称
 * @param icon
 *            用户头像
 */
function loginCallback(nickName, icon) {
	loginStat = true;
	loginIcon = nickName;
	loginNickName = icon;

	jQuery(".login-go img").attr("src", icon).attr("alt", nickName);
	jQuery("body").removeAttr("style");
	jQuery(".login-pop-box").slideToggle();
	jQuery(".login-top-close").delay(500).animate({
		top : -80
	});
	jQuery(".login .login-no").hide();
	jQuery(".login .login-succ").show().css("display","inline-block");
	jQuery(".ifranmes").hide();

	jQuery('.sso_login').show();
	jQuery('.sso_nologin').hide();
	jQuery('.sso_login_icon').attr('src', icon).attr('alt', nickName);
	jQuery('.sso_login_nickname').html(nickName);
	if (typeof loginedEvent == "function") {
		try {
			loginedEvent();
		} catch (e) {
			if (typeof console != 'undefined') {
				console.log(e);
			}
		}
	}
}

/**
 * 未登录执行的函数
 */
function notLogin() {
	jQuery('.sso_login').hide();
	jQuery('.sso_nologin').show();

	// 弹出登录框
	jQuery("body").on("click", ".login-no", function(e) {
		createLoginBox();
	});
}

/**
 * 验证登录状态并执行函数
 * 
 * @param funEx
 *            回调函数表达式 function类型已登录则直接执行,未登录弹出登录框,登录完成后不会执行该函数
 *            string表达式类型,如say('hi'),已登录则直接执行,未登录弹出登录框,登录完成自动执行
 */
function loginedAction(funEx) {
	if (loginStat) {
		// 用户已登录,直接执行方法
		if (typeof funEx == "function") {
			funEx();
		} else {
			eval(funEx);
		}
	} else {
		// 用户未登录,先登录,再执行回调方法
		if (typeof funEx == "function") {
			createLoginBox();
		} else {
			createLoginBox(funEx);
		}

	}
}

/**
 * 创建登录框,登录完成后执行回调函数
 * 
 * @param callFunction
 *            回调函数
 */
function createLoginBox(callFunction) {
	try {
		var poploginUrl = dynSite + "/i/sso/popLogin.do";

		if (!jQuery.isBlank(callFunction)) {
			poploginUrl += "?callback=" + encodeURIComponent(callFunction);
		}
		// 删除旧的登录框
		jQuery('.login-ifranmes').remove();

		var htmls = '<div class="ifranmes login-ifranmes" ><div class="ifranme-close"><i></i></div>\
<iframe src="'
				+ poploginUrl
				+ '" allowtransparency="true"  style="background-color=transparent"  title="login" frameborder="0" scrolling="no"></iframe></div>';
		jQuery(htmls).appendTo("body");
		loginlayout();
		jQuery(".ifranmes").show();
		jQuery("body").css("overflow", "hidden");

		// 关闭登录框
		jQuery(".ifranme-close").click(function(e) {
			jQuery(".ifranmes").hide();
			jQuery("body").removeAttr("style");
			jQuery('.login-ifranmes').remove();
		});

		jQuery(window).resize(function() {
			loginlayout();
		});
	} catch (e) {
		console.log(e)
	}
}
// ------------------------------登录 end--------------------------------

// ------------------------------评论 begin--------------------------------

/**
 * 评论样式初始化
 */
WTJ.commentUi = function() {
	// 评论弹出回复按钮
	jQuery(".discuss-ul li").hover(function(e) {
		jQuery(this).find(".postitem-actions").css("display", "inline");
	}, function() {
		jQuery(this).find(".postitem-actions").hide();
	});

	// 弹出回复框
	jQuery(".postitem-actions").on("click", function() {
		jQuery(this).siblings(".publish-div").slideToggle();
	});

	jQuery('.reply_publist_btn').click(function() {
		var target = jQuery(this).attr("data-target");
		loginedAction("WTJ.addComment('" + target + "')");
	});
};
/**
 * 评论切换分页
 */
WTJ.getCommentPage = function(pageNo) {
	pageNo = pageNo || 1;
	var id = jQuery('#commentPage').attr("data-id");
	var type = jQuery('#commentPage').attr("data-type");
	var url = '/comment.do?act=list&type=' + type + '&tid=' + id + '&currentPage=' + pageNo + '&pageSize=6';
	jQuery.ajax({
		url : url,
		dataType : 'html',
		success : function(html) {
			jQuery('#commentPage').html(html);
			var count = jQuery('#commentCount').val();
			count = parseInt(count);
			if (isNaN(count) || count == 0) {
				jQuery('#comment_num').html('');
			} else {
				jQuery('#comment_num').html('(' + count + ')');
			}
			WTJ.commentUi();
		}
	});
};
/**
 * 添加评论
 */
WTJ.addComment = function(target) {
	var item = jQuery('#' + target);
	var content = item.val();
	var tid = jQuery('#commentPage').attr("data-id");
	var type = jQuery('#commentPage').attr("data-type");
	var rid = item.attr("data-rid");
	var buyPrice = jQuery('#comment_content_buyPrice').val();
	var data = {
		type : type,
		tid : tid,
		content : content
	};
	if (typeof rid != 'undefined' && rid != '') {
		data.rid = rid;
	}
	if (typeof buyPrice != 'undefined' && buyPrice!=''){
		if(!(/^\d+(\.\d{1,2})?$/.test(buyPrice))){
			alert("入手价请输入两位小数点内的数字。");
			return ;
		}
		data.buyPrice = buyPrice;
	}
	console.log(data);
	jQuery.ajax({
		url : dynSite + '/comment.do?act=add',
		type : 'post',
		dataType : 'json',
		data : data,
		success : function(json) {
			if (json.code == 1) {
				item.val('');
				if (typeof rid != 'undefined' && rid != '') {
					item.parents(".publish-div").slideUp();
				}
				WTJ.msgbox(2, '评论成功');
				WTJ.getCommentPage(1);
			} else if (json.code == -2) {
				WTJ.msgbox(1, '请先登录');
			} else {
				WTJ.msgbox(1, json.msg);
			}
		}
	});
};

jQuery(function() {
	jQuery('.comment_publist_btn').click(function() {
		var target = jQuery(this).attr("data-target");
		loginedAction("WTJ.addComment('" + target + "')");
	});
});
// ------------------------------评论 end--------------------------------
// ------------------------------喜欢 begin--------------------------------
// 心 加1动画
WTJ.heartAddOne = function(item, left, top, itemMark) {
	var html = '<span class="addOne" style="left:' + left + "px; top:" + top + 'px"></span>';
	if (itemMark) {
		jQuery(html).appendTo(itemMark.parents('.heart-div'));
	}else {
		jQuery(html).appendTo("body");
	}
	jQuery(".addOne").animate({
		"margin-top" : -15
	}, 100).animate({
		"margin-top" : 0
	}, function() {
		jQuery(this).remove();
		jQuery(item).toggleClass("selected");
	});
};
WTJ.addOrDelMylike = function(type, tid, act, tagMark) {
	if (typeof type == 'undefined' || type == '' || typeof tid == 'undefined' || tid == '') {
		return;
	}
	var item = jQuery('.mylike_' + type + '_' + tid);
	var url = '/' + act + 'Mylike.do?type=' + type + '&tid=' + tid;
	jQuery.ajax({
		url : url,
		type : 'get',
		dataType : 'json',
		success : function(json) {
			if (json.code == 1) {
				var likeNum = json.data.likeNum || 0;
				//if(json.data.likeNum) {
					item.find(".count").html('' + likeNum);
				//}
				if (json.data.isLike) {
					var left = item.find("i").offset().left;
					var top = item.find("i").offset().top;
					var tagItem;
					if (tagMark){
						tagItem = jQuery(tagMark).find('.mylike_' + type + '_' + tid);
						left = tagItem.position().left;
						top = tagItem.position().top;
					}
					var id = item;
					WTJ.heartAddOne(id, left, top, tagItem);
				} else {
					item.removeClass("selected");
				}
			} else {
				alert(json.msg);
			}
		}
	});
};
WTJ.mylikeUi = function() {
	var mylikeMap = {};
	jQuery(".heart-div .heart").each(function() {
		var type = jQuery(this).attr("data-type");
		var tid = jQuery(this).attr("data-tid");
		if (typeof type == 'undefined' || type == '' || typeof tid == 'undefined' || tid == '') {
			return;
		}
		if (typeof mylikeMap[type] == 'undefined') {
			mylikeMap[type] = [ tid ];
		} else {
			mylikeMap[type].push(tid);
		}
	});

	// 点心赞
	jQuery(".heart-div .heart").click(function(e) {
		var type = jQuery(this).attr("data-type");
		var tid = jQuery(this).attr("data-tid");
		if (typeof type == 'undefined' || type == '' || typeof tid == 'undefined' || tid == '') {
			return;
		}
		if (jQuery(this).parents('.smail-top-list').length !=0){
			var tag = '.smail-top-list';
		} else if(jQuery(this).parents('.allbrand-ul-big').length !=0){
			var tag = '.allbrand-ul-big';
		}
		var item = jQuery('.mylike_' + type + '_' + tid);
		var act;
		if (item.hasClass("selected")) {
			act = 'del';
		} else {
			act = 'add';
		}
		if (!act || type == '') {
			return;
		}
		if (tag) {
			loginedAction("WTJ.addOrDelMylike('" + type + "','" + tid + "','" + act + "','" + tag + "')");
		} else {
			loginedAction("WTJ.addOrDelMylike('" + type + "','" + tid + "','" + act + "')");
		}
	});
};
jQuery(function() {
	WTJ.mylikeUi();
});

// ------------------------------喜欢 end--------------------------------

//------------------------------品牌关注 begin----------------------------
WTJ.addOrDelBrandFollowing = function(type, tid, act,tagMark) {
	if (typeof type == 'undefined' || type == '' || typeof tid == 'undefined' || tid == '' || !act || act == '') {
		return;
	}
	var item = jQuery('.follow_' + type + '_' + tid);
	var url = '/' + act + 'Following.do?type=' + type + '&tid=' + tid;
	jQuery.ajax({
		url : url,
		type : 'get',
		dataType : 'json',
		success : function(json) {
			if (json.code == 1) {
				var followNum = json.data.followNum || 0;
				if (json.data.isFollow) {
					var left = item.find("i").offset().left;
					var top = item.find("i").offset().top;
					var tagItem;
					if (tagMark){
						tagItem = jQuery(tagMark).find('.follow_' + type + '_' + tid);
						left = tagItem.position().left;
						top = tagItem.position().top;
					}
					var id = item;
					WTJ.heartAddOne(id, left, top, tagItem);
				} else {
					item.removeClass("selected");
				}
			} else if(json.code == 303){//刷新当前页面
				location.reload();
			} else {
				alert(json.msg);
			}
		}
	});
};


WTJ.followingBrandUi = function() {
	var followingMap = {};
	jQuery(".followBrand").each(function() {
		var type = jQuery(this).attr("data-type");
		var tid = jQuery(this).attr("data-tid");
		if (typeof type == 'undefined' || type == '' || typeof tid == 'undefined' || tid == '') {
			return;
		}
		if (typeof followingMap[type] == 'undefined') {
			followingMap[type] = [ tid ];
		} else {
			followingMap[type].push(tid);
		}
	});
	for ( var type in followingMap) {
		var tids = followingMap[type].join(',');
		var url = '/countFollowing.do?type=' + type + '&tids=' + tids;
		jQuery.ajax({
			url : url,
			type : 'get',
			dataType : 'json',
			success : function(json) {
				if (json.code == 1) {
					for ( var tid in json.data) {
						var o = json.data[tid];
						var isFollow = !!o.isFollow;
						var item = jQuery('.followBrand[data-type="' + o.type + '"][data-tid=' + tid + ']');
						if (isFollow) {
							item.addClass("selected");
						} else {
							item.removeClass("selected");
						}
					}
				}
			}
		});
	}

	jQuery(".followBrand").click(function(e) {
		var type = jQuery(this).attr("data-type");
		var tid = jQuery(this).attr("data-tid");
		if (typeof type == 'undefined' || type == '' || typeof tid == 'undefined' || tid == '') {
			return;
		}
		var item = jQuery('.follow_' + type + '_' + tid);
		if (jQuery(this).parents('.smail-top-list').length !=0){
			var tag = '.smail-top-list';
		} else if(jQuery(this).parents('.allbrand-ul-big').length !=0){
			var tag = '.allbrand-ul-big';
		}
		var act;
		if (item.hasClass("selected")) {
			act = 'del';
		} else {
			act = 'add';
		}
		if(!act || act == ''){
			return;
		}
		loginedAction("WTJ.addOrDelBrandFollowing('" + type + "','" + tid + "','" + act + "','" + tag +  "')");
	});

};
jQuery(function() {
	WTJ.followingBrandUi();
});
//------------------------------品牌关注 end------------------------------

// ------------------------------关注 begin--------------------------------

WTJ.addOrDelFollowing = function(type, tid, act) {
	if (typeof type == 'undefined' || type == '' || typeof tid == 'undefined' || tid == '' || !act || act == '') {
		return;
	}
	var item = jQuery('.follow_' + type + '_' + tid);
	var url = '/' + act + 'Following.do?type=' + type + '&tid=' + tid;
	if (jQuery('.countFollow').length > 0) {
		url += '&numFUser=true';
	}
	jQuery.ajax({
		url : url,
		type : 'get',
		dataType : 'json',
		success : function(json) {
			if (json.code == 1) {
				var followNum = json.data.followNum || 0;
				if(json.data.followNum) {
					item.parents('.brand-follow').find(".count").html(followNum + '人关注');
				}
				if (jQuery('.countFollow').length > 0) {
					var countFollow = json.data.countFollow || 0;
					jQuery('.countFollow').html(countFollow);
				}
				if (json.data.isFollow) {
					item.addClass("follow-on");
					item.html('已关注');
				} else {
					item.removeClass("follow-on");
					item.html('关注');
					item.parents('.brand-follow').find(".count").html(followNum + '人关注');
				}
			} else if(json.code == 303){//刷新当前页面
				location.reload();
			} else {
				alert(json.msg);
			}
		}
	});
};
WTJ.followingUi = function() {
	var followingMap = {};
	jQuery(".follow-btn").each(function() {
		var type = jQuery(this).attr("data-type");
		var tid = jQuery(this).attr("data-tid");
		if (typeof type == 'undefined' || type == '' || typeof tid == 'undefined' || tid == '') {
			return;
		}
		if (typeof followingMap[type] == 'undefined') {
			followingMap[type] = [ tid ];
		} else {
			followingMap[type].push(tid);
		}
	});
	for ( var type in followingMap) {
		var tids = followingMap[type].join(',');
		var url = '/countFollowing.do?type=' + type + '&tids=' + tids;
		jQuery.ajax({
			url : url,
			type : 'get',
			dataType : 'json',
			success : function(json) {
				if (json.code == 1) {
					for ( var tid in json.data) {
						var o = json.data[tid];
						var followNum = o.followNum || 0;
						var isFollow = !!o.isFollow;
						var item = jQuery('.follow-btn[data-type="' + o.type + '"][data-tid=' + tid + ']');
						item.parent().find(".count").html(followNum + '人关注');
						if (isFollow) {
							item.addClass("follow-on");
							item.html('已关注');
						} else {
							item.removeClass("follow-on");
							item.html('关注');
						}
					}
				}
			}
		});
	}

	jQuery(".follow-btn").click(function(e) {
		var type = jQuery(this).attr("data-type");
		var tid = jQuery(this).attr("data-tid");
		if (typeof type == 'undefined' || type == '' || typeof tid == 'undefined' || tid == '') {
			return;
		}
		var item = jQuery('.follow_' + type + '_' + tid);
		var act;
		if (item.hasClass("follow-on")) {
			act = 'del';
		} else {
			act = 'add';
		}
		if(!act || act == ''){
			return;
		}
		loginedAction("WTJ.addOrDelFollowing('" + type + "','" + tid + "','" + act + "')");
	});

	// 取消关注
	jQuery(".follow-btn").hover(function() {
		if (jQuery(this).hasClass('follow-on')) {
			jQuery(this).html("取消关注");
		} else {
			jQuery(this).html("关注");
		}
	}, function() {
		if (jQuery(this).hasClass('follow-on')) {
			jQuery(this).html("已关注");
		} else {
			jQuery(this).html("关注");
		}
	});
};
jQuery(function() {
	WTJ.followingUi();
});

// ------------------------------关注 end--------------------------------

// ------------------------------加入欲购清单 begin--------------------------------
WTJ.addOrDelWishBuy = function(type, tid, mid) {
	var item, url, act;
	if (typeof tid != 'undefined' && tid != '' && tid > 0) {
		item = jQuery('.wishbuy_Product_' + tid);
		if (item.hasClass("add-collocation-success-btn")) {
			act = 'del';
		} else {
			act = 'add';
		}
		url = '/' + act + 'WishBuy.do?type=' + type + '&tid=' + tid;
	} else if (typeof mid != 'undefined' && mid != '' && mid > 0) {
		item = jQuery('.wishbuy_Mybuild_' + mid);
		if (item.hasClass("add-collocation-success-btn")) {
			act = 'del';
		} else {
			act = 'add';
		}
		if (act == 'del') {
			return;
		}
		url = '/' + act + 'WishBuy.do?mid=' + mid;
	} else {
		return;
	}
	jQuery.ajax({
		url : url,
		type : 'get',
		dataType : 'json',
		success : function(json) {
			if (json.code == 1) {
				if (typeof tid != 'undefined' && tid != '') {
					if (json.data.isWishBuy) {
						item.addClass("add-collocation-success-btn");
						item.html('<i></i>已加入');
						WTJ.msgbox(2, '成功加入到欲购清单！', 0);
						return;
					} else {
						item.removeClass("add-collocation-success-btn");
						item.html('<i></i>加入欲购清单');
						return;
					}
				} else if (typeof mid != 'undefined' && mid != '') {
					item.addClass("add-collocation-success-btn");
					item.html('<i></i>已加入');
					WTJ.msgbox(2, '成功加入到欲购清单！', 0);
					if (json.data.idsMap) {
						for ( var wtype in json.data.idsMap) {
							var arr = json.data.idsMap[wtype];
							if (arr && arr.length > 0) {
								for (var i = 0, cnt = arr.length; i < cnt; i++) {
									var item0 = jQuery('.wishBuy-btn[data-type="' + wtype + '"][data-tid=' + arr[i]
											+ ']');
									item0.addClass("add-collocation-success-btn");
									item0.html('<i></i>已加入');
								}
							}
						}
					}
					return;
				}
			} else if (json.code == -2) {
				WTJ.msgbox(1, '请先登录');
				return;
			} else {
				if (json.msg) {
					WTJ.msgbox(1, json.msg);
				} else {
					WTJ.msgbox(1, '添加失败');
				}
				if (typeof console != 'undefined') {
					console.log(json.msg);
				}
			}
			return;
		}
	});
};
WTJ.wishBuyUi = function() {
	var wishBuyMap = {};
	jQuery(".wishBuy-btn").each(function() {
		var type = jQuery(this).attr("data-type");
		var tid = jQuery(this).attr("data-tid");
		var callType = jQuery(this).attr("call-type");
		var mid = jQuery(this).attr("data-mid") || '';
		if(mid=='' && (typeof tid == 'undefined' || tid == '')){
			jQuery(this).removeClass("add-collocation-success-btn");
			jQuery(this).removeClass("wishbuy_Product_"+tid);
			jQuery(this).removeClass("wishBuy-btn");
			jQuery(this).addClass("off-shelf-btn");
			jQuery(this).html('<i></i>商品已下架');
		}
		if (typeof type == 'undefined' || type == '' || typeof tid == 'undefined' || tid == '') {
			return;
		}

		if(callType=="isCall"){ //不管登不登陆都调用
			//判断是否加入预购清单,和是否下架
			isAddWishBuy(jQuery(this), true);
		}

		if (typeof wishBuyMap[type] == 'undefined') {
			wishBuyMap[type] = [ tid ];
		} else {
			wishBuyMap[type].push(tid);
		}
	});

	jQuery(".wishBuy-btn").click(function(e) {
		var type = jQuery(this).attr("data-type") || '';
		var tid = jQuery(this).attr("data-tid") || '';
		var mid = jQuery(this).attr("data-mid") || '';
		if (tid != '') {
			tid = parseInt(tid);
		}
		if (mid != '') {
			mid = parseInt(mid);
		}
		loginedAction("WTJ.addOrDelWishBuy('" + type + "','" + tid + "','" + mid + "')");
	});
};
WTJ.lableSearchKeyUi = function() {
	var jsonStr = '';
	jQuery(".searchKey_span").each(function() {
		var tid = jQuery(this).attr("data-tid");
		var pids = jQuery(this).attr("data-pids");
		if (typeof tid == 'undefined' || tid == '' || typeof pids == 'undefined' || pids == '') {
			return;
		}
		if(jsonStr != '')
			jsonStr += ',"'+tid+'":"'+pids+'"';
		else
			jsonStr += '"'+tid+'":"'+pids+'"';
	});
	if(jsonStr == '')
		return;
	jsonStr = '{'+jsonStr+'}';
	var url = '/countlableSearchKey.do';
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : {'jsonStr':jsonStr},
		dataType : 'json',
		success : function(json) {
			if (json.code == 1) {
				for ( var tid in json.data) {
					var count = json.data[tid];
					var item = jQuery('.searchKey_span[data-tid=' + tid + ']');
					if (typeof(count)!='undefined') {
						item.html(count);
					}
				}
			}
		}
	});
	
}
jQuery(function() {
	WTJ.wishBuyUi();
	WTJ.lableSearchKeyUi();
});
// ------------------------------加入欲购清单 end--------------------------------

// ------------------------------加入灵感集 begin--------------------------------

// 关闭弹出添加灵感集
WTJ.closeinspirat = function() {
	jQuery(".addinspiration-bg").remove();
	jQuery(".addinspiration-pop").remove();
	jQuery(".addinspiration-pop").removeClass("addinspiration-pop-succ");
};
// 加入灵感集提示
WTJ.addInspireGroupTip = function(msg) {
	if (msg && msg != '') {
		var html = msg + '<i></i>';
		if (jQuery('.addinspiration-pop-title').length > 0) {
			jQuery('.addinspiration-pop-title').html(html);
		} else {
			html = '<div class="addinspiration-pop-title" style="top: 0px;">' + html + '</div>';
			jQuery('.addinspiration-main').prepend(html);
		}
	}
	jQuery('.addinspiration-pop-title i').click(function(e) {
		jQuery(this).parents('.addinspiration-pop-title').remove();
	});
};

WTJ.addInspireGroupUi = function() {
	WTJ.addInspireGroupTip();
	// 关闭
	jQuery(".cancel-addinspir-btn, .add-succ-head i").click(function(e) {
		WTJ.closeinspirat();
	});
	// 提交成功
	jQuery(".save-addinspir-btn").click(
			function(e) {
				var pop = jQuery(this).parents('.addinspiration-pop');
				var url, data;
				var iid = parseInt(pop.attr("data-iid") || '');
				var type = (pop.attr("data-itype") || '');
				var gid = parseInt(pop.attr("data-gid") || '');
				if (iid > 0 && !(!type || type=='')) {
					var groupId = '';
					var selBtn = jQuery(pop).find(".select-on");
					if (selBtn.length > 0) {
						groupId = selBtn.attr("data-gid");
					}
					var remark = jQuery(pop).find('.remarks').val();
					url = '/inspire/addInsToGroup.do';
					data = {
						iid : iid,
						groupId : groupId,
						remark : remark,
						groupItemType: type
					};
				} else if (gid > 0) {
					var intro = jQuery(pop).find('.intro').val();
					url = '/inspire/addGroupToGroup.do';
					data = {
						gid : gid,
						intro : intro
					};
				}
				jQuery.ajax({
					type : "POST",
					url : url,
					dataType : "json",
					data : data,
					success : function(json) {
						WTJ.closeinspirat();
						if (json.code == 1) {
							//改变列表中的灵感集个数
							var addInspireGroupBtn = jQuery(".add-inspir-a[data-iid='"+json.data.iid+"'][data-itype='"+json.data.groupItemType+"'] span");
							var inspireGroupNum = addInspireGroupBtn.html();
							if(!inspireGroupNum || inspireGroupNum=='') inspireGroupNum=0;
							addInspireGroupBtn.html(parseInt(''+inspireGroupNum)+1);

							var html = '<a href="/idea/album' + json.data.group_id + '.html" target="_blank">'
									+ json.data.group_name + '</a>';
							WTJ.msgbox(2, '添加成功！', 1, html);
						} else if (json.code == -2) {
							WTJ.msgbox(1, '请先登录');
						} else {
							WTJ.msgbox(1, '添加失败');
							if (typeof console != 'undefined') {
								console.log(json.msg);
							}
						}
					}
				});
			});
	// 选择灵感集按钮切换
	jQuery(".select-box-div").click(function(e) {
		jQuery(this).hide();
		jQuery(".select-box-input").show();
		jQuery(".ul-select").slideDown();

	});
	// 创建
	jQuery(".select-box-input a").click(
			function(e) {
				var groupName = jQuery(this).parents('.addinspiration-pop').find('.groupName').val();
				if (typeof groupName == 'undefined' || groupName == '') {
					WTJ.msgbox(1, '请输入灵感集名称');
					return;
				}
				jQuery.ajax({
					type : "GET",
					url : '/inspire/addGroup.do',
					data : 'groupName=' + groupName,
					dataType : "json",
					success : function(json) {
						if (json.code == 1) {
							jQuery(".ul-select").find('.select-on').removeClass("select-on");
							var html = '<li data-gid="' + json.data.result.id + '" class="select-on" ><div>'
									+ json.data.result.name + '</div><em></em></li>';
							jQuery(".ul-select").append(html);
							jQuery(".select-box-input").hide();
							jQuery(".select-box-div").show();
							jQuery(".select-box-div span").html(json.data.result.name);
							jQuery(".ul-select").slideUp(150);

							jQuery(".ul-select .select-on").click(function(e) {
								jQuery(this).addClass("select-on").siblings().removeClass("select-on");
								jQuery(".select-box-input").hide();
								jQuery(".select-box-div").show();
								jQuery(".select-box-div span").html(jQuery(this).children("div").text());
								jQuery(".ul-select").slideUp(150);
							});

						} else if (json.code == -2) {
							WTJ.msgbox(1, '请先登录');
						} else {
							WTJ.addInspireGroupTip(json.msg);
						}
					}
				});
			});
	// 选择下拉框菜单
	jQuery(".ul-select li").click(function(e) {
		jQuery(this).addClass("select-on").siblings().removeClass("select-on");
		jQuery(".select-box-input").hide();
		jQuery(".select-box-div").show();
		jQuery(".select-box-div span").html(jQuery(this).children("div").text());
		jQuery(".ul-select").slideUp(150);
	});

	// 下拉菜单滚动条
	jQuery(".ul-select").mCustomScrollbar({
		scrollInertia : 0,
		scrollButtons : {
			enable : false,
			scrollType : "continuous",
			scrollSpeed : 80,
			scrollAmount : 100
		}
	});

	// 点击任意地方收起来
	if(jQuery("#select-box").hasClass("select-box")){
		jQuery('body').on('click',function(event) {
					var evt = event.srcElement ? event.srcElement : event.target;
					if (evt.id == 'select-box-div' || evt.id == 'select-box-i' || evt.id == 'establish-input'
							|| evt.id == 'ul-select-id' || evt.id == 'mCSB_5') {
						return;
					} else {
						jQuery("#select-box-div").show();
						jQuery(".select-box-input").hide();
						jQuery(".ul-select").slideUp(150);
					}
		});
	}
	


}

WTJ.addInspireGroup = function(iid, gid, type) {
	var url;
	if (iid != '') {
		url = '/inspire/addInsToGroup.do?iid=' + iid +"&groupItemType="+type;
	} else if (gid != '') {
		url = '/inspire/addGroupToGroup.do?gid=' + gid;
	} else {
		return;
	}
	jQuery.ajax({
		type : "GET",
		cache : false,
		url : url,
		dataType : "html",
		success : function(htmls) {
			jQuery(htmls).appendTo("body");
			WTJ.addInspireGroupUi();
		}
	});
};
jQuery(function() {
	jQuery(".add-inspir-a").click(function(e) {
		var iid = jQuery(this).attr("data-iid");
		var gid = jQuery(this).attr("data-gid");
		var type = jQuery(this).attr("data-itype");
		loginedAction("WTJ.addInspireGroup('" + (iid || '') + "','" + (gid || '')+ "','" + (type || '') + "')");
	});
});

// ------------------------------加入灵感集 end--------------------------------

//-------------------------------右边浮动菜单 start------------------------------
jQuery(function(){
	//右边浮动菜单
	jQuery(window).scroll(function(){
		var whg=300;
		if ( jQuery(window).scrollTop() > whg ){
			jQuery(".wtj-floatingmenu").show(200);
		}else{
			jQuery(".wtj-floatingmenu").hide(200);
		};
	});	
	
	
	jQuery(".floatingmenu-ico1").click(function(e) {
		var obj = jQuery(this).siblings(".qrcode-floatingmenu").show();
		jQuery("body").click(once = function(){
			obj.hide();
			jQuery(this).unbind("click", once);
		});
		return false;
	});
	jQuery(".floatingmenu-ico2").click(function(e) {
		jQuery("body,html").animate({scrollTop:"0px"}, 1000);
	});
	jQuery(".floatingmenu-ico3").click(function(e) {
		jQuery(".view-floatingmenu").show();
	});
	//关闭
	jQuery(".close-view").click(function(e) {
		jQuery(":input[name='content']").val("");
		jQuery(":input[name='contact']").val("");
		jQuery(".view-floatingmenu").hide();
	});
	//提交
	jQuery(".btn-sbm").click(function(e) {
		var contentTextarea = jQuery(":input[name='content']");
		var content = contentTextarea.val();
		if(!content || content==""){
			contentTextarea.css('border', '1px solid #ff0000');
			contentTextarea.focus();
			return;
		}else{
			contentTextarea.css('border', '1px solid #999999');
		}
		jQuery.ajax({
			type: 'POST',
			url : jQuery("#adviceFormId")[0].action,
			data: jQuery("#adviceFormId").serialize(),
			dataType : 'json',
			success : function(json) {
				if (json.code=='1' || json.code==1) {
					jQuery(".succ-view p").text("提交成功！");
				}
//				else{
//					//提交失败
//					jQuery(".succ-view p").text(json.msg);
//				}
				jQuery(".view-floatingmenu").hide();	
				jQuery(":input[name='content']").val("");
				jQuery(":input[name='contact']").val("");
				//提示信息
				jQuery(".succ-view").show();	 
				clearFunc = setTimeout(function() {
					jQuery(".succ-view").hide();
					clearTimeout(clearFunc);
				}, 1000);
			}
		});
	});	
});
//-------------------------------右边浮动菜单 end--------------------------------

//底部二维码条	
jQuery(function(){

/*	if(jQuery.cookie('sl_app_push')){
		jQuery(".app-close").hide();
		jQuery(".app-extension").animate({"left":"-100%"},"fast", function(){
			jQuery(".app-open").animate({"left":"10"},"normal");
			jQuery("#yuding_box").css("visibility","visible");
	    });
	}else{
		jQuery(".app-open").trigger('click');
	}*/
	
jQuery.cookie('sl_app_push', 'true', { expires: 2 });//不显示banner图
	

});

//修改购物车数量
function changeCountCart(){
	if(jQuery(".b-shop-cart").length > 0) {
		jQuery.ajax({
			type: 'GET',
			dataType: 'json',
			url: dynSite + "/cart/countCart.do",
			success: function (data) {
				if (data.code == 1 || data.code == '1') {
					var num = data.data;
					num = (!num || num == '') ? 0 : num;
					if (num > 99) {
						jQuery(".b-shop-cart").html('<span>99</span><i>+</i>');
					} else {
						jQuery(".b-shop-cart").html('<span>' + num + '</span>');
					}
					if (num > 0) {
						jQuery(".b-shop-cart").show();
					} else {
						jQuery(".b-shop-cart").hide();
					}
				}
			}
		});
	}
}

//是否显示购物车
function showCart(){
	if(jQuery(".b-shop-cart").length > 0) {
		jQuery.ajax({
			type: 'GET',
			dataType: 'json',
			url : dynSite + "/user/showCart.do",
			success : function(data) {
				if(data.code==1 || data.code=='1'){
					if(data.data.showCart == 'true' || data.data.showCart == 'TRUE'){
						jQuery(".b-shop-cart").show()
						jQuery(".com-myOrder").show()
						jQuery(".com-com-myAddress").show()
					}
				}
			}
		});
	}
}

jQuery(function() {
	//倒计时
	jQuery(".remain-time").each(function(i){
		var thisObj = jQuery(this);
		//剩余时间 的毫秒值
		var remainTime = thisObj.attr("data-long-time");
		remainTime = Math.floor(remainTime / 1000 );
		var cbfn = thisObj.attr("data-cbfn");
		countdownTimer(remainTime, thisObj, cbfn);
	});
});
//倒计时
function countdownTimer(intDiff, thisObj, cbfn){
	var intervalFn = window.setInterval(function(){
		var day=0, hour=0, minute=0, second=0;//时间默认值
		if(intDiff < 0){
			window.clearInterval(intervalFn);
			if(!cbfn || cbfn=='') return;
			if (typeof cbfn == "function") {
				cbfn();
			} else {
				eval(cbfn);
			}
			return;
		}

		day = Math.floor(intDiff / (60 * 60 * 24));
		hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
		minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
		second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);

		if (hour <= 9) hour = '0' + hour;
		if (minute <= 9) minute = '0' + minute;
		if (second <= 9) second = '0' + second;
		thisObj.find('.t_d').html('<b>'+day+'</b>天');
		thisObj.find('.t_h').html('<b>'+hour+'</b>时');
		thisObj.find('.t_m').html('<b>'+minute+'</b>分');
		thisObj.find('.t_s').html('<b>'+second+'</b>秒');
		intDiff--;
	}, 1000);
}

//获取URL上的请求参数值
function getUrlQueryParam(name)
{
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if(r!=null) return  unescape(r[2]); return null;
}