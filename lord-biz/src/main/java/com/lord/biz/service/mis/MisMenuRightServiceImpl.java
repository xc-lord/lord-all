package com.lord.biz.service.mis;

import com.lord.biz.dao.mis.MisMenuRightDao;
import com.lord.biz.dao.mis.specs.MisMenuRightSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.mis.MisMenuRight;
import com.lord.common.service.mis.MisMenuRightService;
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
 * 后台菜单的具体权限mis_menu_right的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月28日 18:03:31
 */
@Component
public class MisMenuRightServiceImpl implements MisMenuRightService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MisMenuRightDao misMenuRightDao;

    @Override
    public MisMenuRight getMisMenuRight(Long id) {
        return misMenuRightDao.findOne(id);
    }

    @Override
    @Transactional
    public MisMenuRight saveOrUpdate(MisMenuRight pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "名称[" + pageObj.getName() + "]已经存在");

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性

            misMenuRightDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        MisMenuRight dbObj = misMenuRightDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段

        misMenuRightDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<MisMenuRight> pageMisMenuRight(MisMenuRight param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageMisMenuRight(param, pagerParam);

    }

    @Override
    public Pager<MisMenuRight> pageMisMenuRight(MisMenuRight param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<MisMenuRight> pageResult = misMenuRightDao.findAll(MisMenuRightSpecs.queryByMisMenuRight(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<MisMenuRight> pageMisMenuRight(MisMenuRight param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<MisMenuRight> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            MisMenuRight obj = misMenuRightDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = misMenuRightDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = misMenuRightDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteMisMenuRight(Long... ids) {
        misMenuRightDao.deleteMisMenuRight(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        MisMenuRight dbObj = misMenuRightDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        misMenuRightDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<MisMenuRight> list = misMenuRightDao.findAll(MisMenuRightSpecs.queryBy(rowName, rowValue, MisMenuRight.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        MisMenuRight dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}