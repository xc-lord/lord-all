package com.lord.common.dto.sys;

import com.lord.common.model.sys.SysExtendTemplate;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 19:44
 */
public class ExtendTemplateDto extends SysExtendTemplate
{
    private String columnJsonStr;

    public String getColumnJsonStr()
    {
        return columnJsonStr;
    }

    public void setColumnJsonStr(String columnJsonStr)
    {
        this.columnJsonStr = columnJsonStr;
    }
}
