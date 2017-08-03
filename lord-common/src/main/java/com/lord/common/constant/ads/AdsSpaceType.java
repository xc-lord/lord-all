package com.lord.common.constant.ads;

import com.lord.common.constant.BaseEnumType;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：广告元素类型
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月18日 16:43
 */
public enum AdsSpaceType implements BaseEnumType {
    Model("模板"),
    TextLink("文字链接"),
    ImageLink("图片链接"),
    Article("文章"),
    BigText("大文本"),
    ;

    private String name;

    AdsSpaceType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        AdsSpaceType[] values = AdsSpaceType.values();
        for (AdsSpaceType value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }
}
