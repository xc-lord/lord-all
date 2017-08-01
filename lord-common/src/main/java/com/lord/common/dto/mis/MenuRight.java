package com.lord.common.dto.mis;

import com.lord.common.model.mis.MisMenuRight;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月01日 18:13
 */
public class MenuRight extends MisMenuRight
{
    private boolean checked;

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
}
