package com.lord.biz.service;

import com.alibaba.fastjson.JSON;
import com.lord.biz.BizApplication;
import com.lord.biz.dao.cms.CmsArticleDao;
import com.lord.biz.dao.cms.specs.CmsArticleSpecs;
import com.lord.biz.utils.AdsTemplateUtils;
import com.lord.common.dto.ads.AdsPageInfo;
import com.lord.common.model.cms.CmsArticle;
import com.lord.common.service.ads.AdsTemplateService;
import com.lord.utils.CommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月05日 11:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BizApplication.class)
public class TestAdsTemplateService
{
    @Autowired
    private AdsTemplateService adsTemplateService;

    @Autowired
    private CmsArticleDao cmsArticleDao;

    @Test
    public void testReadXml()
    {
        String xsdPath = CommonUtils.getClassPathFilePath("page_template.xsd");
        System.out.println("xsdPath = " + xsdPath);

        String xml = CommonUtils.readClassPathFile("page/Example.xml");
        boolean isXml = AdsTemplateUtils.validateXml(xsdPath, xml);
        System.out.println(xml);
        System.out.println("验证结果：" + isXml);
    }

    @Test
    public void testImportData() {
        String xml = CommonUtils.readClassPathFile("page/Example.xml");
        adsTemplateService.importData(xml);
    }

    @Test
    public void testGetPageInfo()
    {
        AdsPageInfo pageInfo = adsTemplateService.getPageInfo("WapIndex");
        System.out.println(JSON.toJSONString(pageInfo));
    }

    @Test
    public void testDao() {
        Page<CmsArticle> page = cmsArticleDao.findAll(CmsArticleSpecs.queryAll(), new PageRequest(0, 10));
        List<String> ids = new ArrayList<>();
        for (CmsArticle article : page)
        {
            ids.add(article.getId() + "");
        }
        System.out.println(ids);
    }

}
