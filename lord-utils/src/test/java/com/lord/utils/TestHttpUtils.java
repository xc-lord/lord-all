package com.lord.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年06月24日 18:28
 */
public class TestHttpUtils {

    @Test
    public void testHttp() {
        Map<String, String> params = new HashMap<>();
        params.put("a", "323");
        params.put("b", "小学");
        params.put("c", "cool");
        String res = HttpUtils.doGet("http://www.baidu.com/", params);
        System.out.println("响应内容：\n" + res);
    }

    @Test
    public void testHttpParam() throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        params.put("a", "323");
        params.put("b", "小学");
        params.put("c", "cool");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
        }
        System.out.println(URLEncodedUtils.format(nameValuePairs, "UTF-8"));
    }
}
