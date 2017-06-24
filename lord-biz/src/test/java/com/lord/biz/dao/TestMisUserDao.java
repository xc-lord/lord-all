package com.lord.biz.dao;

import com.alibaba.fastjson.JSON;
import com.lord.biz.BizApplication;
import com.lord.biz.dao.mis.MisUserModelDao;
import com.lord.common.model.mis.MisUserModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaocheng on 2017/3/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BizApplication.class)
public class TestMisUserDao {
    @Autowired
    private MisUserModelDao misUserDao;

    @Test
    public void testMisUserDao() {
        //删除所有数据
        misUserDao.deleteAllInBatch();
        // 创建10条记录
        misUserDao.save(new MisUserModel("AAA", 10));
        misUserDao.save(new MisUserModel("BBB", 20));
        misUserDao.save(new MisUserModel("CCC", 30));
        misUserDao.save(new MisUserModel("DDD", 40));
        misUserDao.save(new MisUserModel("EEE", 50));
        misUserDao.save(new MisUserModel("FFF", 60));
        misUserDao.save(new MisUserModel("GGG", 70));
        misUserDao.save(new MisUserModel("HHH", 80));
        misUserDao.save(new MisUserModel("III", 90));
        misUserDao.save(new MisUserModel("JJJ", 100));
        // 测试findAll, 查询所有记录
        Assert.assertEquals(10, misUserDao.findAll().size());
        // 测试findByName, 查询姓名为FFF的User
        Assert.assertEquals(60, misUserDao.findByUsername("FFF").getSex());
        // 测试findByNameAndAge, 查询姓名为FFF并且年龄为60的User
        Assert.assertEquals("FFF", misUserDao.findByUsername("FFF").getUsername());
        // 测试删除姓名为AAA的User
        misUserDao.delete(misUserDao.findByUsername("AAA"));
        // 测试findAll, 查询所有记录, 验证上面的删除是否成功
        Assert.assertEquals(9, misUserDao.findAll().size());
        System.out.println("测试Dao成功");
    }

    @Test
    public void testQuery() {
        //删除所有数据
        misUserDao.deleteAllInBatch();
        // 创建10条记录
        misUserDao.save(new MisUserModel("AAA", 10));
        misUserDao.save(new MisUserModel("BBB", 20));
        misUserDao.save(new MisUserModel("CCC", 30));
        misUserDao.save(new MisUserModel("DDD", 40));
        misUserDao.save(new MisUserModel("EEE", 50));
        misUserDao.save(new MisUserModel("FFF", 60));
        misUserDao.save(new MisUserModel("GGG", 70));
        misUserDao.save(new MisUserModel("HHH", 80));
        misUserDao.save(new MisUserModel("III", 90));
        misUserDao.save(new MisUserModel("JJJ", 100));

        Page<MisUserModel> pageOne = misUserDao.findAll(new PageRequest(2, 2, new Sort(Sort.Direction.ASC, "id")));
        System.out.println("测试分页查询查询，结果：");
        System.out.println(JSON.toJSONString(pageOne));

        MisUserModel model = new MisUserModel();
        Example<MisUserModel> example = Example.of(model);
        new Sort("");
        Sort sort = new Sort(Sort.Direction.DESC, "age").and (new Sort(Sort.Direction.DESC, "id"));
        Page<MisUserModel> page = misUserDao.findAll(new Specification<MisUserModel>() {
            @Override
            public Predicate toPredicate(Root<MisUserModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(criteriaBuilder.like(root.get("username").as(String.class), "%AAA%"));
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, new PageRequest(0, 5, new Sort(Sort.Direction.DESC, "id")));
        System.out.println("测试Specification查询，结果：");
        System.out.println(JSON.toJSONString(page.getContent()));
    }

    @Test
    @Transactional
    public void testDelete() {
        Long[] ids = new Long[]{1L,24L,456L};
        misUserDao.deleteMisUser(ids);
    }
}
