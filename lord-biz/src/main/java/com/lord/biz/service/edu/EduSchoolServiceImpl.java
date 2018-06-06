package com.lord.biz.service.edu;

import com.lord.biz.dao.edu.EduSchoolDao;
import com.lord.biz.dao.edu.specs.EduSchoolSpecs;
import com.lord.biz.utils.ServiceUtils;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.edu.EduSchool;
import com.lord.common.service.edu.EduSchoolService;
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
 * 学校edu_school的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月06日 10:15:50
 */
@Component
public class EduSchoolServiceImpl implements EduSchoolService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EduSchoolDao eduSchoolDao;

    @Override
    public EduSchool getEduSchool(Long id) {
        return eduSchoolDao.findOne(id);
    }

    @Override
    @Transactional
    public EduSchool saveOrUpdate(EduSchool pageObj, LoginUser loginUser) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "名称[" + pageObj.getName() + "]已经存在");

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            pageObj.setCreator(loginUser.getUserId());
            pageObj.setCreateTime(new Date());
            pageObj.setModifier(loginUser.getUserId());
            pageObj.setUpdateTime(new Date());

            eduSchoolDao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        EduSchool dbObj = eduSchoolDao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        pageObj.setCreator(dbObj.getCreator());
        pageObj.setCreateTime(dbObj.getCreateTime());
        pageObj.setModifier(loginUser.getUserId());
        pageObj.setUpdateTime(new Date());//更新时间

        eduSchoolDao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<EduSchool> pageEduSchool(EduSchool param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return pageEduSchool(param, pagerParam);

    }

    @Override
    public Pager<EduSchool> pageEduSchool(EduSchool param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<EduSchool> pageResult = eduSchoolDao.findAll(EduSchoolSpecs.queryByEduSchool(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<EduSchool> pageEduSchool(EduSchool param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<EduSchool> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            EduSchool obj = eduSchoolDao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = eduSchoolDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = eduSchoolDao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void deleteEduSchool(Long... ids) {
        eduSchoolDao.deleteEduSchool(ids);
    }


    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        EduSchool dbObj = eduSchoolDao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        eduSchoolDao.updateOrderValue(id, orderValue);
    }

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        List<EduSchool> list = eduSchoolDao.findAll(EduSchoolSpecs.queryBy(rowName, rowValue, EduSchool.class));
        if (list == null || list.size() < 1) {
            return false;
        }
        EduSchool dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}