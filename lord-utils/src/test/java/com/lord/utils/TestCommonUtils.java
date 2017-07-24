package com.lord.utils;

import com.lord.utils.constant.RegularExpression;
import org.junit.Test;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月24日 11:00
 */
public class TestCommonUtils
{
    @Test
    public void testDomain()
    {
        String par = RegularExpression.rootDomain;

        System.out.println(CommonUtils.matchedOne("www.baidu.com", par));
        System.out.println(CommonUtils.matchedOne("baidu.com", par));
        System.out.println(CommonUtils.matchedOne("127.0.0.1", par));
        System.out.println(CommonUtils.matchedOne("localhost", par));
        System.out.println(CommonUtils.matchedOne("www.163.com", par));
    }
}
