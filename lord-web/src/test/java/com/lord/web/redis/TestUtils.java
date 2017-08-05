package com.lord.web.redis;

import com.lord.biz.utils.AdsTemplateUtils;
import com.lord.common.constant.FileType;
import com.lord.utils.CommonUtils;
import org.junit.Test;

/**
 * Created by xiaocheng on 2017/3/28.
 */
public class TestUtils {

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            int num = randomNum(0, 1);
            System.out.println(num);
        }
    }

    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            int count = 5;
            int size = 3;
            int num = randomNum(0, count-size);
            System.out.println(num);
        }
    }

    /**
     * 随机生成一定范围的随机数
     * @param start	开始
     * @param end	结束
     * @return
     */
    public static Integer randomNum(int start, int end) {
        int num = (int)(start+Math.random()*(end-start+1));
        return num;
    }

    @Test
    public void testFileType() {
        System.out.println(FileType.getFileType(".jpeg"));
        System.out.println(FileType.getFileType(".mp4"));
        System.out.println(FileType.getFileType(".mp3"));
        System.out.println(FileType.getFileType(".doc"));
        System.out.println(FileType.getFileType(".zip"));
        System.out.println(FileType.getFileType(".jar"));
    }

    @Test
    public void testClassPath() {
        System.out.println("读取classpath下的文件");
        String filePath = "ueditor-config.json";
        System.out.println(CommonUtils.readClassPathFile(filePath));
    }

    @Test
    public void testReadXml()
    {
        System.out.println("/image/2017/08/05/" + CommonUtils.fillZero(1, 2) + ".jpg");
        String xsdPath = CommonUtils.getClassPathFilePath("page_template.xsd");
        System.out.println("xsdPath = " + xsdPath);

        String xml = CommonUtils.readClassPathFile("page/Example.xml");
        boolean isXml = AdsTemplateUtils.validateXml(xml);
        System.out.println(xml);
        System.out.println("验证结果：" + isXml);
    }
}
