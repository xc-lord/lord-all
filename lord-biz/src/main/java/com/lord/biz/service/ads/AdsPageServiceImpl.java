package com.lord.biz.service.ads;

import com.lord.biz.dao.ads.AdsPageDao;
import com.lord.biz.dao.ads.specs.AdsPageSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.ads.AdsPageQuery;
import com.lord.common.model.ads.AdsPage;
import com.lord.common.service.ads.AdsPageService;
import com.lord.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 页面ads_page的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 09:13:24
 */
@Component
public class AdsPageServiceImpl implements AdsPageService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdsPageDao adsPageDao;

    @Override
    public AdsPage getAdsPage(Long id) {
        return adsPageDao.findOne(id);
    }

    @Override
    @Transactional
    public AdsPage saveOrUpdate(AdsPage pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "名称[" + pageObj.getName() + "]已经存在");
        Preconditions.checkArgument(isExist(pageObj.getId(), "pageCode", pageObj.getName()), "页面编码[" + pageObj.getPageCode() + "]已经存在");

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());

            adsPageDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        AdsPage dbObj = adsPageDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间

        adsPageDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<AdsPage> pageAdsPage(AdsPage param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageAdsPage(param, pagerParam);

    }

    @Override
    public Pager<AdsPage> pageAdsPage(AdsPage param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<AdsPage> pageResult = adsPageDao.findAll(AdsPageSpecs.queryByAdsPage(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<AdsPage> pageAdsPage(AdsPage param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<AdsPage> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            AdsPage obj = adsPageDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = adsPageDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = adsPageDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteAdsPage(Long... ids) {
        adsPageDao.deleteAdsPage(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        AdsPage dbObj = adsPageDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        adsPageDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("pageCode");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<AdsPage> list = adsPageDao.findAll(AdsPageSpecs.queryBy(rowName, rowValue, AdsPage.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        AdsPage dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    public Pager<AdsPage> pageAdsPage(AdsPageQuery query)
    {
        PagerParam pagerParam = new PagerParam(query.getPage(),query.getPageSize());
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<AdsPage> pageResult = adsPageDao.findAll(AdsPageSpecs.queryByAdsPage(query), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }
}