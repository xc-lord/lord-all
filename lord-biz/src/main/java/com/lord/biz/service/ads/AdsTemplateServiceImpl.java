package com.lord.biz.service.ads;

import com.lord.biz.utils.AdsTemplateUtils;
import com.lord.common.constant.ads.AdsSpaceType;
import com.lord.common.model.ads.AdsPage;
import com.lord.common.model.ads.AdsSpace;
import com.lord.common.service.ads.AdsElementService;
import com.lord.common.service.ads.AdsPageService;
import com.lord.common.service.ads.AdsSpaceService;
import com.lord.common.service.ads.AdsTemplateService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Code;
import com.lord.utils.exception.CommonException;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * 功能：广告位模板服务
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月05日 14:28
 */
@Component
public class AdsTemplateServiceImpl implements AdsTemplateService
{
    protected Logger logger = LoggerFactory.getLogger(AdsTemplateServiceImpl.class);

    @Autowired
    private AdsPageService adsPageService;

    @Autowired
    private AdsSpaceService adsSpaceService;

    @Autowired
    private AdsElementService adsElementService;

    private Map<String, String> spaceTypeMap = AdsSpaceType.toNameMap();

    @Override
    public void importData(String xml)
    {
        long start = System.currentTimeMillis();
        Document doc = getDocument(xml);
        Element root = doc.getRootElement();
        Preconditions.checkArgument(root == null, "xml的根结节对象为空！");

        String page_code = AdsTemplateUtils.getNeedElmValue(root, "page_code");
        String page_name = AdsTemplateUtils.getNeedElmValue(root, "page_name");
        AdsPage adsPage = adsPageService.getAndCreate(page_code, page_name);
        Element childrenElm = root.element("children");
        importSpaceData(childrenElm, adsPage, null);

        logger.info(page_name + "导入数据成功，用时：" + (System.currentTimeMillis() - start) + "ms");
    }

    private void importSpaceData(Element childrenElm, AdsPage adsPage, AdsSpace parentSpace)
    {
        if (childrenElm == null)
            return;
        String parentKeyword = adsPage.getPageCode();
        Long parentId = null;
        int parentLevel = 0;
        if (parentSpace != null)
        {
            parentId = parentSpace.getId();
            parentKeyword = parentSpace.getKeyword();
            parentLevel = parentSpace.getLevel();
        }
        long order_value = 1L;
        Iterator<Element> templateList = childrenElm.elementIterator("template");
        while (templateList.hasNext()) {
            Element templateElm = templateList.next();
            String keyword = AdsTemplateUtils.getNeedElmValue(templateElm, "keyword");
            String name = AdsTemplateUtils.getNeedElmValue(templateElm, "name");
            String type = AdsTemplateUtils.getNeedElmValue(templateElm, "type");
            type = spaceTypeMap.get(type);//中文转换
            String intro = AdsTemplateUtils.getElmValue(templateElm, "intro");
            Integer count = AdsTemplateUtils.getElmValueInteger(templateElm, "count");//数量可选
            Integer template_num = AdsTemplateUtils.getElmValueInteger(templateElm, "template_num");//模板数量
            if (template_num == null || template_num < 2) {
                String subKeyword = keyword;
                keyword = parentKeyword + "/" + subKeyword;
                AdsSpace pageObj = new AdsSpace();
                pageObj.setName(name);
                pageObj.setAdsType(type);
                pageObj.setAdsNum(count);
                pageObj.setSubKeyword(subKeyword);
                pageObj.setKeyword(keyword);
                pageObj.setLevel(parentLevel + 1);
                pageObj.setParentId(parentId);
                pageObj.setPageId(adsPage.getId());
                pageObj.setOrderValue(order_value);
                pageObj.setRemark(intro);
                AdsSpace adsSpace = adsSpaceService.getAndCreate(pageObj);//保存数据
                adsElementService.batchCreateElement(adsSpace);//批量创建广告位元素数据，已存在则不创建
                importSpaceData(templateElm.element("children"), adsPage, adsSpace);//导入子节点数据
                order_value++;
                continue;
            }

            for (int i = 1; i <= template_num; i++)
            {
                String subKeyword = keyword + "_" + i;
                keyword = parentKeyword + "/" + subKeyword;

                AdsSpace pageObj = new AdsSpace();
                pageObj.setName(name + "_" + i);
                pageObj.setAdsType(type);
                pageObj.setAdsNum(count);
                pageObj.setSubKeyword(subKeyword);
                pageObj.setKeyword(keyword);
                pageObj.setLevel(parentLevel + 1);
                pageObj.setParentId(parentId);
                pageObj.setPageId(adsPage.getId());
                pageObj.setOrderValue(order_value);
                pageObj.setRemark(intro);
                AdsSpace adsSpace = adsSpaceService.getAndCreate(pageObj);//保存数据
                adsElementService.batchCreateElement(adsSpace);//批量创建广告位元素数据，已存在则不创建
                importSpaceData(templateElm.element("children"), adsPage, adsSpace);//导入子节点数据
                order_value++;
            }
        }
    }

    private Document getDocument(String xml)
    {
        Preconditions.checkArgument(StringUtils.isEmpty(xml), "xml内容不能为空");
        Document doc;
        try
        {
            doc = DocumentHelper.parseText(xml);
        }
        catch (DocumentException e)
        {
            logger.error("xml格式有问题，转换成Document对象失败：" + e.getMessage(), e);
            throw new CommonException(Code.ErrorFormat, "xml格式有问题，转换成Document对象失败");
        }
        Preconditions.checkArgument(doc == null, "xml对象为空！");
        return doc;
    }

}
