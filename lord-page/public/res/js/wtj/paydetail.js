// JavaScript Document
$(function() {
    //banner动画


	idxbanner();
    $(window).load(function() {
        var imgheight=$(".tv-slideBox li:first img").height();
        $("#focusAd .img-div-slide").height(imgheight);
        $("#focusAd  ul").height(imgheight);
    });

    $(window).resize(function() {
        var imgheights=$(".tv-slideBox li:first img").height();
        $("#focusAd .img-div-slide").height(imgheights);
        $("#focusAd  ul").height(imgheights);
    });

    //评论弹出回复按钮
    reply();

  //头部固定导航条
    if($(".nav-div").hasClass("nav-div")) {
        arrHeight = [];
        $content = $("#jcContent");
        $sideList = $("#jcSide").find("li");
        $topnum = parseInt($("#paydetailframe").offset().top);
        $cont = $(".pay-row").length;
        var wtop = parseInt($(window).scrollTop());
        $conList = $content.children(".pay-row");
        /*var conListLen = $content.children(".pay-row").length;
        for (var h = 0; h < conListLen; h++) {
            arrHeight.push($(".pay-row").eq(h).offset().top - 60);
        }*/
        scolltopmenu();
        //导航栏
        $(window).scroll(function () {
            scolltopmenu();
        });

        $("#jcSide li").click(function (e) {
            $(this).addClass("select").siblings().removeClass("select");
            var sum = $(this).index();
            $(window).scrollTop(arrHeight[sum]);
            return false;
        });
    }
});

$(function(){
    //购买弹出框
    $(".buy-btn-yes").click(function (e) {
        //打开添加地址的弹窗
        var id = $(this).attr("data-id");
        buyDetail('detail',id);
    });
});

//商品购买页
/*function buyDetail(cartType,productId,skuId,num){
	$.ajax({
        type: 'GET',
        data:{
        	'cartType':cartType,
        	'id':productId,
        	'skuId':skuId,
        	'num':num
        	 },
        url : dynSite + "/productSku/buyProduct.do",
        success : function(data) {
        	$(".sales-process-pop").empty();
            $(".sales-process-pop").html(data);
            $(".sales-process-pop").show();
            if($(".sel-condition-sroll").height()>192){
                $(".sel-condition-sroll").css({"overflow":"auto","height":"192"});
            }
        }
    });
}*/

//头部大BANNER
function idxbanner(){
    $("#focusAd").slide({
        mainCell:".bd ul",
        autoPlay:false,
        endFun:function(i,c){
            jQuery("img.lazy").lazyload();
            $('.otherbanner .bigImg .clone').each(function(){
                var this_ = $(this);
                var cloneImg = this_.find('.lazy');
                var originalImg = cloneImg.attr('data-original');
                cloneImg.attr('src', originalImg);
            });
        }
    });
    $(".tv-slideBox .tempWrap ul").height($(".tv-slideBox li").height());
    if($("#focusAd .bd li").length==1){
        $("#focusAd").find(".prev").hide();
        $("#focusAd").find(".next").hide();
    }else{
        $("#focusAd").find(".prev").show();
        $("#focusAd").find(".next").show();
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
//头部导航条

function getScrollTop() {
    var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
    return scrollTop;
}
function scolltopmenu(){
    wtop=parseInt($(window).scrollTop());
    $topnum = parseInt($("#paydetailframe").offset().top);
    if($topnum <  wtop){
        $(".nav-div").animate({top:"0px"}).show();
        arrHeight = [];
        var conListLen = $content.children(".pay-row").length;
        for (var h = 0; h < conListLen; h++) {
            arrHeight.push($(".pay-row").eq(h).offset().top - 60);
        }
        for(var i=0;i<$cont; i++) {
                if (arrHeight[i] < wtop) {
                    if(i+1 ==$cont){
                        $("#jcSide li").eq($cont-1).addClass("select").siblings().removeClass("select");
                        return false;
                    }
                    if (arrHeight[i + 1] > wtop ) {
                        $("#jcSide li").eq(i).addClass("select").siblings().removeClass("select");
                        return false;
                    }
                }

        }
    }else{
        $(".nav-div").hide();
    }
}