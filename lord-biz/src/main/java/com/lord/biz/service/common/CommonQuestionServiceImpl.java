package com.lord.biz.service.common;

import com.lord.biz.dao.common.CommonQuestionDao;
import com.lord.biz.dao.common.specs.CommonQuestionSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.common.CommonQuestion;
import com.lord.common.service.common.CommonQuestionService;
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
 * 常见问题common_question的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月16日 18:06:42
 */
@Component
public class CommonQuestionServiceImpl implements CommonQuestionService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommonQuestionDao commonQuestionDao;

    @Override
    public CommonQuestion getCommonQuestion(Long id) {
        return commonQuestionDao.findOne(id);
    }

    @Override
    @Transactional
    public CommonQuestion saveOrUpdate(CommonQuestion pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreateTime(new Date());
            pageObj.setUpdateTime(new Date());

            commonQuestionDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        CommonQuestion dbObj = commonQuestionDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setUpdateTime(new Date());//更新时间

        commonQuestionDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<CommonQuestion> pageCommonQuestion(CommonQuestion param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageCommonQuestion(param, pagerParam);

    }

    @Override
    public Pager<CommonQuestion> pageCommonQuestion(CommonQuestion param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<CommonQuestion> pageResult = commonQuestionDao.findAll(CommonQuestionSpecs.queryByCommonQuestion(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<CommonQuestion> pageCommonQuestion(CommonQuestion param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        PageRequest pageRequest = null;
        if(sort != null) {
            pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort);
        } else {
            pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        }
        Page<CommonQuestion> pageResult = commonQuestionDao.findAll(CommonQuestionSpecs.queryByCommonQuestion(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteCommonQuestion(Long... ids) {
        commonQuestionDao.deleteCommonQuestion(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        CommonQuestion dbObj = commonQuestionDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        commonQuestionDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<CommonQuestion> list = commonQuestionDao.findAll(CommonQuestionSpecs.queryBy(rowName, rowValue, CommonQuestion.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        CommonQuestion dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }

    @Override
    public List<CommonQuestion> listQuestion(String entityCode, Long entityId)
    {
        return commonQuestionDao.listQuestion(entityCode, entityId);
    }
}