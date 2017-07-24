package com.lord.utils;

import org.junit.Test;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月23日 17:16
 */
public class TestEncryptUtils {

    @Test
    public void testPassword() {
        String str = "1234566";
        String encode = EncryptUtils.passwordEncode(str);
        System.out.println("原字符串：" + str);
        System.out.println("加密结果：" + encode);
        System.out.println("加密结果：" + EncryptUtils.base64Encode(encode));
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date(0L));
        System.out.println(new Date(1500000000000L));
        System.out.println(100024706158L/(1000*60*60*24*365));
    }

    @Test
    public void testMd5() {
        String str = "xiaocheng";
        String encode = EncryptUtils.md5Encode(str);
        System.out.println("原字符串：" + str);
        System.out.println("加密结果：" + encode);
        System.out.println("加密结果：" + EncryptUtils.base64Encode(encode));
    }

    @Test
    public void testMd5File() {
        String srcFileName = "D:\\upload\\temp\\src.jpeg";
        String encode = EncryptUtils.md5Encode(new File(srcFileName));
        System.out.println("文件的MD5值为：" + encode);
    }

    @Test
    public void testSha1() {
        String str = "11344343";
        String encode = EncryptUtils.sha1Encode(str);
        System.out.println("原字符串：" + str);
        System.out.println("加密结果：" + encode);
    }

    @Test
    public void testSha256() {
        String str = "11344343";
        String encode = EncryptUtils.sha256Encode(str);
        System.out.println("原字符串：" + str);
        System.out.println("加密结果：" + encode);
    }

    @Test
    public void testBase64() {
        String str = "11344343";
        String encode = EncryptUtils.base64Encode(str);
        System.out.println("原字符串：" + str);
        System.out.println("加密结果：" + encode);
        System.out.println("解密结果：" + EncryptUtils.base64Decode(encode));
    }

    @Test
    public void testUrl() {
        String str = "11344小米";
        String encode = EncryptUtils.urlEncode(str);
        System.out.println("原字符串：" + str);
        System.out.println("加密结果：" + encode);
        System.out.println("解密结果：" + EncryptUtils.urlDecode(encode));
    }

    @Test
    public void testRsa() {
        Map<String, byte[]> keyMap = EncryptUtils.generateRsaKeyBytes();
        System.out.println("公钥：\n" + EncryptUtils.base64Encode(keyMap.get(EncryptUtils.PUBLIC_KEY)));
        System.out.println("私钥：\n" + EncryptUtils.base64Encode(keyMap.get(EncryptUtils.PRIVATE_KEY)));

        // 加密
        PublicKey publicKey = EncryptUtils.restorePublicKey(keyMap.get(EncryptUtils.PUBLIC_KEY));

        String plainText = "MANUTD is the greatest club in the world";
        System.out.println("RSA 原文:\n" + plainText);

        byte[] encodedText = EncryptUtils.rsaEncode(publicKey, plainText.getBytes());
        System.out.println("RSA 加密:\n" + EncryptUtils.base64Encode(encodedText));

        // 解密
        PrivateKey privateKey = EncryptUtils.restorePrivateKey(keyMap.get(EncryptUtils.PRIVATE_KEY));
        System.out.println("RSA 解密:\n"
                + EncryptUtils.rsaDecode(privateKey, encodedText));
    }
}
