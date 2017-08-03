package com.lord.biz.service.ads;

import com.lord.biz.dao.ads.AdsSpaceDao;
import com.lord.biz.dao.ads.specs.AdsSpaceSpecs;
import com.lord.biz.service.cat.CategorySimpleServiceImpl;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.cat.CategorySimple;
import com.lord.common.dto.cat.TreeNode;
import com.lord.common.model.ads.AdsSpace;
import com.lord.common.service.ads.AdsSpaceService;
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
 * 广告位ads_space的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 11:12:10
 */
@Component
public class AdsSpaceServiceImpl extends CategorySimpleServiceImpl implements AdsSpaceService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdsSpaceDao adsSpaceDao;

    @Override
    public AdsSpace getAdsSpace(Long id) {
        return adsSpaceDao.findOne(id);
    }

    @Override
    @Transactional
    public AdsSpace saveOrUpdate(AdsSpace pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "名称[" + pageObj.getName() + "]已经存在");

        //父节点
        AdsSpace parent = null;
        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());
            if (pageObj.getParentId() != null) {
                parent = adsSpaceDao.findOne(pageObj.getParentId());
            }
            super.setAddCategory(pageObj, parent);//设置分类的公共属性
            adsSpaceDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        AdsSpace dbObj = adsSpaceDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间
        if (dbObj.getParentId() != null) {
            parent = adsSpaceDao.findOne(dbObj.getParentId());
        }
        super.setUpdateCategory(pageObj, parent, dbObj);//更新时不能修改的公共属性

        adsSpaceDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<AdsSpace> pageAdsSpace(AdsSpace param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageAdsSpace(param, pagerParam);

    }

    @Override
    public Pager<AdsSpace> pageAdsSpace(AdsSpace param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<AdsSpace> pageResult = adsSpaceDao.findAll(AdsSpaceSpecs.queryByAdsSpace(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<AdsSpace> pageAdsSpace(AdsSpace param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<AdsSpace> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            AdsSpace obj = adsSpaceDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = adsSpaceDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = adsSpaceDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteAdsSpace(Long... ids) {
        adsSpaceDao.deleteAdsSpace(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        AdsSpace dbObj = adsSpaceDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        adsSpaceDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<AdsSpace> list = adsSpaceDao.findAll(AdsSpaceSpecs.queryBy(rowName, rowValue, AdsSpace.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        AdsSpace dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    protected List<CategorySimple> findAllCategory()
    {
        List<AdsSpace> categoryList = adsSpaceDao.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "level"),
                new Sort.Order(Sort.Direction.ASC, "orderValue")));
        List<CategorySimple> categories = new ArrayList<>();
        categories.addAll(categoryList);
        return categories;
    }
}