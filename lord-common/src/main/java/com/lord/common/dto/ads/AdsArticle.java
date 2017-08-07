package com.lord.common.dto.ads;

import com.lord.common.model.ads.AdsElement;
import com.lord.common.model.ads.AdsSpace;

import java.util.List;

/**
 * 功能：广告位信息
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月07日 09:52
 */
public class AdsArticle extends AdsElement
{
    private String desc;

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}
