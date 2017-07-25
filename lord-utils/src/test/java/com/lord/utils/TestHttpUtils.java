package com.lord.utils;

import com.alibaba.fastjson.JSONObject;
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
        System.out.println("响应内容：\n" + res.length());
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

    @Test
    public void testPinYin() {
        String keyword = "长沙";
        Map<String, Integer> pinyinMap = Pinyin4jUtils.getPinyinMap(keyword);
        if (pinyinMap != null) {
            System.out.println(keyword + "的全拼结果为：");
            System.out.println(pinyinMap);
        }
        Map<String, Integer> letterMap = Pinyin4jUtils.getFirstLetterMap(keyword);
        if (letterMap != null) {
            System.out.println(keyword + "的首字母缩写结果为：");
            System.out.println(letterMap);
        }
    }

    @Test
    public void testBaidu()
    {
        JSONObject jsonObject = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("token", "da40801d5f8050f6ea4d29f359ac812e");
        header.put("username", "aactporg");
        header.put("password", "Aa123321");
        JSONObject body = new JSONObject();
        body.put("query", "nodejs");
        body.put("queryType", 1);
        JSONObject filter = new JSONObject();
        filter.put("device", "0");
        //body.put("seedFilter", filter);
        jsonObject.put("header", header);
        jsonObject.put("body", body);
        String json = jsonObject.toJSONString();
        System.out.println(json);
        //json = "{\"header\":{\"target\":null,\"action\":\"API-SDK-JAVA\",\"password\":\"Aa123321\",\"username\":\"aactporg\",\"token\":\"da40801d5f8050f6ea4d29f359ac812e\",\"accessToken\":null,\"account_type\":null},\"body\":{\"device\":null,\"seedFilter\":{\"hotMonth\":null,\"maxNum\":500,\"matchType\":null,\"pvLow\":null,\"pvHigh\":null,\"competeLow\":null,\"negativeWord\":null,\"competeHigh\":null,\"searchRegion\":null,\"regionExtend\":null,\"monthFilter\":null,\"removeDuplicate\":null,\"duplicateUIds\":null},\"seedWord\":\"氯甲酸异丙酯\"}}";
        String res = HttpUtils.doPostString("https://api.baidu.com/json/sms/v4/KRService/getKRByQuery", json);
        System.out.println("响应内容：\n" + res);
    }

    @Test
    public void testBaiduGetKRFileIdByWords()
    {
        JSONObject jsonObject = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("token", "da40801d5f8050f6ea4d29f359ac812e");
        header.put("username", "aactporg");
        header.put("password", "Aa123321");
        JSONObject body = new JSONObject();
        List<String> words = new ArrayList<>();
        words.add("留学");
        words.add("培训师培训");
        words.add("邪恶漫画");
        words.add("nodejs");
        body.put("seedWords", words);
        JSONObject filter = new JSONObject();
        filter.put("device", "0");
        body.put("seedFilter", filter);
        jsonObject.put("header", header);
        jsonObject.put("body", body);
        String json = jsonObject.toJSONString();
        //json = "{\"header\":{\"target\":null,\"action\":\"API-SDK-JAVA\",\"password\":\"Aa123321\",\"username\":\"aactporg\",\"token\":\"da40801d5f8050f6ea4d29f359ac812e\",\"accessToken\":null,\"account_type\":null},\"body\":{\"device\":null,\"seedFilter\":{\"hotMonth\":null,\"maxNum\":500,\"matchType\":null,\"pvLow\":null,\"pvHigh\":null,\"competeLow\":null,\"negativeWord\":null,\"competeHigh\":null,\"searchRegion\":null,\"regionExtend\":null,\"monthFilter\":null,\"removeDuplicate\":null,\"duplicateUIds\":null},\"seedWord\":\"氯甲酸异丙酯\"}}";
        String res = HttpUtils.doPostString("https://api.baidu.com/json/sms/v4/KRService/getKRFileIdByWords", json);
        System.out.println("响应内容：\n" + res);
    }
    @Test
    public void testBaiduGetFileStatus()
    {
        JSONObject jsonObject = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("token", "da40801d5f8050f6ea4d29f359ac812e");
        header.put("username", "aactporg");
        header.put("password", "Aa123321");
        JSONObject body = new JSONObject();
        body.put("fileId", "5db0c6105b798db3cc56555320a1cea2");
        jsonObject.put("header", header);
        jsonObject.put("body", body);
        String json = jsonObject.toJSONString();
        //json = "{\"header\":{\"target\":null,\"action\":\"API-SDK-JAVA\",\"password\":\"Aa123321\",\"username\":\"aactporg\",\"token\":\"da40801d5f8050f6ea4d29f359ac812e\",\"accessToken\":null,\"account_type\":null},\"body\":{\"device\":null,\"seedFilter\":{\"hotMonth\":null,\"maxNum\":500,\"matchType\":null,\"pvLow\":null,\"pvHigh\":null,\"competeLow\":null,\"negativeWord\":null,\"competeHigh\":null,\"searchRegion\":null,\"regionExtend\":null,\"monthFilter\":null,\"removeDuplicate\":null,\"duplicateUIds\":null},\"seedWord\":\"氯甲酸异丙酯\"}}";
        String res = HttpUtils.doPostString("https://api.baidu.com/json/sms/v4/KRService/getFilePath", json);
        System.out.println("响应内容：\n" + res);
    }
}
