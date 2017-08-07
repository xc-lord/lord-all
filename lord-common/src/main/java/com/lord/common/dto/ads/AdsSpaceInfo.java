package com.lord.common.dto.ads;

import com.lord.common.model.ads.AdsSpace;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：广告位信息
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月07日 09:52
 */
public class AdsSpaceInfo<T> implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * 广告位
     */
    private AdsSpace space;
    /**
     * 广告位元素
     */
    private List<T> elements;

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public AdsSpace getSpace()
    {
        return space;
    }

    public void setSpace(AdsSpace space)
    {
        this.space = space;
    }
}
