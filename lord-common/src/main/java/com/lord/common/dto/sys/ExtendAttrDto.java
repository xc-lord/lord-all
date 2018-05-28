package com.lord.common.dto.sys;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月24日 11:42
 */
public class ExtendAttrDto implements Serializable
{
    /**
     * 属性名
     */
    private String name;

    /**
     * 属性key
     */
    private String dataKey;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 属性类型
     */
    private String inputType;

    /**
     * 属性值的Json
     */
    private JSONObject valJson;

    /**
     * 是否为空
     */
    private Boolean nullable;

    /**
     * 属性ID
     */
    private Long attrId;

    /**
     * 属性值
     */
    private Object val;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDataKey()
    {
        return dataKey;
    }

    public void setDataKey(String dataKey)
    {
        this.dataKey = dataKey;
    }

    public String getDataType()
    {
        return dataType;
    }

    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getInputType()
    {
        return inputType;
    }

    public void setInputType(String inputType)
    {
        this.inputType = inputType;
    }

    public JSONObject getValJson()
    {
        return valJson;
    }

    public void setValJson(JSONObject valJson)
    {
        this.valJson = valJson;
    }

    public Boolean getNullable()
    {
        return nullable;
    }

    public void setNullable(Boolean nullable)
    {
        this.nullable = nullable;
    }

    public Long getAttrId()
    {
        return attrId;
    }

    public void setAttrId(Long attrId)
    {
        this.attrId = attrId;
    }

    public Object getVal()
    {
        return val;
    }

    public void setVal(Object val)
    {
        this.val = val;
    }
}
