package com.lord.utils;

import com.lord.utils.exception.CommonException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：加密工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月23日 17:11
 */
public class EncryptUtils {

    public static final String KEY_ALGORITHM = "RSA";
    /** 貌似默认是RSA/NONE/PKCS1Padding，未验证 */
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";

    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    public static final int KEY_SIZE = 2048;
    /** 密码加密添加的字符串 */
    private static final String PASSWORD_KEY = "NzIxZGZiMTc4ZmEzYzYyMmZkNjJhNjI5YTkxYzNjZWI=";

    /**
     * 密码加密结果
     * @param password
     * @return
     */
    public static String passwordEncode(String password) {
        if (StringUtils.isEmpty(password)) {
            return password;
        }
        String str = PASSWORD_KEY + "&" + password + "&" + PASSWORD_KEY;
        return md5Encode(str);
    }

    /**
     * Md5加密
     *
     * @param str 需要加密的字符串
     * @return
     */
    public static String md5Encode(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String md5Encode(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            return DigestUtils.md5Hex(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonException(e.getMessage());
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * base64编码
     *
     * @param str
     * @return String
     */
    public static String base64Encode(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        return base64Encode(str.getBytes());
    }

    /**
     * base64编码
     *
     * @param bstr
     * @return String
     */
    public static String base64Encode(byte[] bstr) {
        return new String(Base64.encodeBase64(bstr));
    }

    /**
     * base64解码
     *
     * @param str
     * @return string
     */
    public static String base64Decode(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        return new String(Base64.decodeBase64(str));
    }

    /**
     * Url编码
     * @param str
     * @return
     */
    public static String urlEncode(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        String requestValue="";
        try {
            requestValue = URLEncoder.encode(str, "UTF-8");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return requestValue;
    }

    /**
     * Url解码
     * @param str
     * @return
     */
    public static String urlDecode(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        String requestValue="";
        try {
            requestValue = URLDecoder.decode(str, "UTF-8");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return requestValue;
    }

    /**
     * 传入文本内容，返回 SHA-1 串
     *
     * @param strText
     * @return
     */
    public static String sha1Encode(String strText)
    {
        return SHA(strText, "SHA-1");
    }

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param strText
     * @return
     */
    public static String sha256Encode(String strText)
    {
        return SHA(strText, "SHA-256");
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param strText
     * @return
     */
    public static String sha512Encode(String strText)
    {
        return SHA(strText, "SHA-512");
    }

    /**
     * 字符串 SHA 加密
     * @param strText
     * @param strType
     * @return
     */
    private static String SHA(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();
                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return strResult;
    }

    /**
     * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
     *
     * @return
     */
    public static Map<String, String> generateRsaKey() {
        try {
            Map<String, byte[]> keyMap = generateRsaKeyBytes();
            Map<String, String> map = new HashMap<>();
            map.put(PUBLIC_KEY, base64Encode(keyMap.get(PUBLIC_KEY)));
            map.put(PRIVATE_KEY, base64Encode(keyMap.get(PRIVATE_KEY)));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
     *
     * @return
     */
    public static Map<String, byte[]> generateRsaKeyBytes() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory
                    .generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA加密，三步走。
     *
     * @param key
     * @param plainText
     * @return
     */
    public static byte[] rsaEncode(PublicKey key, byte[] plainText) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * RSA解密，三步走。
     *
     * @param key
     * @param encodedText
     * @return
     */
    public static String rsaDecode(PrivateKey key, byte[] encodedText) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encodedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
