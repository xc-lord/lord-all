/**
 * Created by xiaocheng on 2018/4/28.
 */
//start禁用右键菜单
//<!--
if (window.Event)
    document.captureEvents(Event.MOUSEUP);

function nocontextmenu()
{
    event.cancelBubble = true;
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
        event.cancelBubble = true;
        event.returnValue = false;
        return false;
    }

}

document.oncontextmenu = nocontextmenu; // for IE5+
document.onmousedown = norightclick; // for all others
//-->
//end禁用右键菜单
