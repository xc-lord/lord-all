package com.lord.common.service.ads;

import com.lord.common.dto.ads.AdsPageInfo;

/**
 * 功能：广告位模板服务
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月05日 14:26
 */
public interface AdsTemplateService
{
    String ADS_ALL_PAGE_CODE = "ADS_ALL_PAGE_CODE_";
    String ADS_ALL_PAGE = "ADS_ALL_PAGE_";
    String ADS_ALL_SPACE = "ADS_ALL_SPACE_";

    void importData(String xml);

    AdsPageInfo getPageInfo(String pageCode);
}
