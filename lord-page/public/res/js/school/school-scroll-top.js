/**
 * Created by xiaocheng on 2018/4/28.
 */
//返回顶部
function imj2(){
    this.init();
}
imj2.prototype = {
    constructor: imj2,
    init: function(){
        this._initBackTop();
    },
    _initBackTop: function(){
        var $backTop = this.$backTop = $('<div class="m-top-cbbfixed">'+
            '<a class="m-top-weixin m-top-cbbtn"">'+
            '<span class="m-top-weixin-icon"></span><div></div>'+
            '</a>'+
            '<a class="m-top-go m-top-cbbtn">'+
            '<span class="m-top-goicon"></span>'+
            '</a>'+
            '</div>');
        $('body').append($backTop);

        $backTop['click'](function(){
            $("html, body").animate({
                scrollTop: 0
            }, 120);
        });

        var timmer = null;
        $(window).bind("scroll",function() {
            var d = $(document).scrollTop(),
                e = $(window).height();
            0 < d ? $backTop['css']("bottom", "10px") : $backTop['css']("bottom", "-90px");
            clearTimeout(timmer);
            timmer = setTimeout(function() {
                clearTimeout(timmer)
            },100);
        });
    }
}
var imj2 = new imj2();
//end返回顶部
//<!--

if (window.Event)
    document.captureEvents(Event.MOUSEUP);

function nocontextmenu()
{
    event.cancelBubble = true
    event.returnValue = false;

    return false;
}

function norightclick(e)
{
    if (window.Event)
    {
        if (e.which == 2 || e.which == 3)
            return false;
    }
    else
    if (event.button == 2 || event.button == 3)
    {
        event.cancelBubble = true
        event.returnValue = false;
        return false;
    }

}

document.oncontextmenu = nocontextmenu; // for IE5+
document.onmousedown = norightclick; // for all others
//-->
