package com.lord.biz.service;

import com.lord.biz.BizApplication;
import com.lord.common.dto.Pager;
import com.lord.common.model.mis.MisUser;
import com.lord.common.service.mis.MisUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 功能：测试service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月02日 14:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BizApplication.class)
public class TestMisUserService {

    @Autowired
    private MisUserService misUserService;

    @Test
    public void testSave() {
        MisUser saveObj = new MisUser();
        saveObj.setUsername("admin");
        saveObj.setPassword("123456");
        saveObj.setPhone("18620928924");
        saveObj.setSuperAdmin(true);
        MisUser savedObj = misUserService.saveOrUpdate(saveObj);
        System.out.println("保存的结果：" + savedObj);

        String updatePhone = "18820928924";
        savedObj.setPhone(updatePhone);
        misUserService.saveOrUpdate(savedObj);
        MisUser queryObj = misUserService.getMisUser(savedObj.getId());
        System.out.println("更新后查询的结果：" + savedObj);
        Assert.assertEquals(queryObj.getPhone(), updatePhone);

        Long id = savedObj.getId();
        misUserService.removeMisUser(id);
        Assert.assertEquals( misUserService.getMisUser(id).isRemoved(), true);
        misUserService.updateOrderValue(id, 113L);
        long ordervalue = misUserService.getMisUser(id).getOrderValue();
        Assert.assertEquals(ordervalue, 113L);

        System.out.println("分页查询的结果：");
        Pager<MisUser> pager = misUserService.pageMisUser(null, 1, 10);
        System.out.println(pager);
        for (MisUser misUser : pager.getList()) {
            System.out.println(misUser);
        }
    }
}
