// JavaScript Document
jQuery(function() {

    //banner动画	
	var widthdow=jQuery(window).width();
	idxbanner();
	jQuery(window).resize(function() {	
		var widththis=jQuery(window).width();
		if(widthdow<widththis){	
	 	 window.location.href=window.location.href;
		}
	});

    //底部热门品牌
    idxbanner2();


//屏幕改变屏幕被截取的宽度
	jQuery('.tag-wtj').each(function(){
		var pertop = jQuery(this).attr('data-pertop');
		var perleft = jQuery(this).attr('data-perleft');
		var li = jQuery(this).parents('li')[0];
		var top = (548*pertop);
		var left = ((1920*perleft) - ((1920-jQuery(li).width())/2));
		jQuery(this).css('top', top + 'px');
		jQuery(this).css('left', left + 'px');
	});
	
	//标签弹出
    jQuery(".tag-wtj").hover(function(e) {
        jQuery(this).find(".tag-content").show();
    }, function(e) {
        jQuery(this).find(".tag-content").hide();
    });
    //banner菜单
    jQuery(".banner-menu ul li").hover(function(e) {
        jQuery(this).addClass("banner-menu-active");
        jQuery(this).children(".banner-menu-hover").addClass("banner-menu-hover2").stop(true,true).delay(300).show().animate({width:440 }, 300);
    }, function(e) {
        jQuery(this).removeClass("banner-menu-active");
        jQuery(this).children(".banner-menu-hover").removeClass("banner-menu-hover2").stop(true,true).hide().animate({
            width:0
        });
    });
    //我搭切换
    jQuery(".my-take-ul li").hover(function(e) {
        var indexNum = jQuery(this).index();
        var indexNumWork = indexNum + 1;
        var arrow = jQuery(this).width() + 26;
        jQuery(".works").hide();
        jQuery(".works" + indexNumWork).show();
		var arrowmun=indexNum * arrow;
         jQuery(".my-take-arrow").stop(true,true).animate({left:arrowmun},500);
    });
    //推荐商品HOVER图片
    jQuery(".rec-commodity-ul li").hover(function(e) {
        jQuery(this).find(".rec-commodity-box").show();
        jQuery(this).find(".rec-commodity-pop").delay(1000).slideDown();
    }, function(e) {
        jQuery(this).find(".rec-commodity-pop").stop(true, true).slideDown();
        jQuery(this).find(".rec-commodity-box").hide();
        jQuery(this).find(".rec-commodity-pop").hide();
    });

    //二维码点击
    if(jQuery("#apptuiguang2").hasClass("app-small2")){

        if(!jQuery.cookie('sl_app_push_retract')){
            jQuery(".app-open").trigger('click');
        }
	}
    
});

//底部热门品牌
function idxbanner2() {
    jQuery("#topbrands").slide({
        titCell: ".fCl-nav li",
        mainCell: ".bd ul",
        effect: "leftLoop",
        autoPlay: true,
        interTime: 5000,
        endFun: function (i, c) {
            var st = jQuery(document).scrollTop();
            jQuery(document).scrollTop(st - 0.01);
            jQuery(document).scrollTop(st + 0.01);

            jQuery('#topbrands .bd .clone').each(function () {
                var this_ = jQuery(this);
                var cloneImg = this_.find('.lazy');
                cloneImg.each(function(){
                    var parentTag = jQuery(this).parents('.lazy-wrap').removeClass('lazy-wrap');
                    var originalImg = jQuery(this).attr('data-original');
                    jQuery(this).attr('src', originalImg);
                    jQuery(this).removeClass('lazy');
                });

            });
            //jQuery("img.lazy").lazyload();
        }
    });
};
//头部大BANNER
function idxbanner(){
    jQuery("#focusAd").slide({
        mainCell:".bd ul",
        effect:"leftLoop",
        autoPlay:true,
        interTime:5000
    });
}


