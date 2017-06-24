package com.lord.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * HttpClient工具类
 *
 * @author xiaocheng
 */
public class HttpUtils {

    protected static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    /**
     * 定义编码格式： UTF-8
     */
    public static final String UTF8 = "UTF-8";
    /**
     * 连接请求参数的字符：&
     */
    private static final String URL_PARAM_CONNECT_FLAG = "&";
    /**
     * 空字符
     */
    private static final String EMPTY = "";

    private static HttpClient clientHttp;
    private static HttpClient clientHttps;

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(1000)//从连接池中获取连接的超时时间
            .setConnectTimeout(3000)//与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间
            .setSocketTimeout(6000)//socket读数据超时时间：从服务器获取响应数据的超时时间
            .build();

    private static HttpClient getHttpClient(String url) {
        if (url.startsWith("https")) {
            return createSSLClient();
        }
        return createHttpClient();
    }

    /**
     * 获取http协议的HttpClient
     * @return
     */
    private static HttpClient createHttpClient() {
        if (clientHttp == null) {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                    .setMaxConnTotal(50)//连接池中最大连接数
                    .setMaxConnPerRoute(10)//分配给同一个route(路由)最大的并发连接数
                    .setDefaultRequestConfig(requestConfig);
            clientHttp = httpClientBuilder.build();
        }
        return clientHttp;
    }

    /**
     * 获取SSL验证的HttpClient
     *
     * @return
     */
    private static HttpClient createSSLClient() {
        if (clientHttps == null) {
            SSLContext sslcontext = SSLContexts.createSystemDefault();
            // Create a registry of custom connection socket factories for supported
            // protocol schemes.
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslcontext))
                    .build();
            PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            connMgr.setMaxTotal(50);
            connMgr.setDefaultMaxPerRoute(10);
            HttpClientBuilder httpClientBuilder = HttpClients.custom()
                    //.setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(connMgr)
                    .setDefaultRequestConfig(requestConfig);
            clientHttps = httpClientBuilder.build();
        }
        return clientHttps;
    }

    /**
     * 模拟浏览器post提交
     *
     * @param url
     * @return
     */
    private static HttpPost getPostMethod(String url) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        HttpPost pmethod = new HttpPost(url); // 设置响应头信息
        pmethod.addHeader("Connection", "keep-alive");
        pmethod.addHeader("Accept", "*/*");
        pmethod.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        pmethod.addHeader("Host", "api.mch.weixin.qq.com");
        pmethod.addHeader("X-Requested-With", "XMLHttpRequest");
        pmethod.addHeader("Cache-Control", "max-age=0");
        pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        return pmethod;
    }

    /**
     * 模拟浏览器GET提交
     *
     * @param url
     * @return
     */
    private static HttpGet getGetMethod(String url) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        HttpGet pmethod = new HttpGet(url);
        // 设置响应头信息
        pmethod.addHeader("Connection", "keep-alive");
        pmethod.addHeader("Cache-Control", "max-age=0");
        pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        pmethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8");
        return pmethod;
    }

    /**
     * POST方式提交数据
     *
     * @param url     待请求的URL
     * @param str     要提交的数据,json或xml格式
     * @param resEnc     编码
     * @return 响应结果
     */
    public static String doPostString(String url, String str, String resEnc) {
        String response = EMPTY;
        HttpPost httpPost = null;
        try {
            httpPost = getPostMethod(url);
            // 将XML数据放入httpPost中
            StringEntity entity = new StringEntity(str);
            httpPost.setEntity(entity);
            // 执行请求
            HttpResponse httpResponse = getHttpClient(url).execute(httpPost);
            response = getResponse(url, httpResponse, resEnc);

        } catch (ClientProtocolException e) {
            logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题:" + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("发生网络异常" + e.getMessage(), e);
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
                httpPost = null;
            }
        }
        return response;
    }

    /**
     * POST方式提交数据
     *
     * @param url     待请求的URL
     * @param xmlData 要提交的XML数据
     * @return 响应结果
     */
    public static String doPostXml(String url, String xmlData) {
        return doPostString(url, xmlData, UTF8);
    }

    /**
     * POST方式提交数据
     *
     * @param url     待请求的URL
     * @param str     要提交的数据,json或xml格式
     * @return 响应结果
     */
    public static String doPostString(String url, String str) {
        return doPostString(url, str, UTF8);
    }

    /**
     * POST方式提交数据
     *
     * @param url    待请求的URL
     * @param params 要提交的数据
     * @param resEnc    编码
     * @return 响应结果
     */
    public static String doPost(String url, Map<String, String> params, String resEnc) {
        String response = EMPTY;
        HttpPost postMethod = null;
        try {
            postMethod = getPostMethod(url);
            // 将表单的值放入postMethod中
            // 创建参数队列
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
            }
            //设置参数
            postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            // 执行postMethod
            HttpResponse httpResponse = getHttpClient(url).execute(postMethod);
            response = getResponse(url, httpResponse, resEnc);
        } catch (ClientProtocolException e) {
            logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题:" + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("发生网络异常" + e.getMessage(), e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
                postMethod = null;
            }
        }
        return response;
    }

    /**
     * POST方式提交数据,默认以UTF-8格式提交
     *
     * @param url    待请求的URL
     * @param params 要提交的数据
     * @return 响应结果
     */
    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, UTF8);
    }

    /**
     * GET方式提交数据,默认以UTF-8格式提交
     *
     * @param url 待请求的URL
     * @return 响应结果
     */
    public static String doGet(String url) {
        return doGet(url, new HashMap<String, String>(), UTF8);
    }

    /**
     * GET方式提交数据,默认以UTF-8格式提交
     *
     * @param url    待请求的URL
     * @param params 要提交的数据
     * @return 响应结果
     */
    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, UTF8);
    }

    /**
     * GET方式提交数据
     *
     * @param url    待请求的URL
     * @param params 要提交的数据
     * @param enc    编码
     * @return 响应结果
     */
    public static String doGet(String url, Map<String, String> params, String enc) {
        return doGet(url, params, enc, null);
    }

    /**
     * GET方式提交数据
     *
     * @param url    待请求的URL
     * @param params 要提交的数据
     * @param enc    编码
     * @param resEnc 响应内容的编码
     * @return 响应结果
     */
    public static String doGet(String url, Map<String, String> params, String enc, String resEnc) {
        String response = EMPTY;
        HttpGet getMethod = null;
        StringBuffer strtTotalURL = getTotalUrl(url, params, enc);
        logger.debug("GET请求URL = \n" + strtTotalURL.toString());
        try {
            getMethod = getGetMethod(strtTotalURL.toString());
            getMethod.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + enc);
            // 执行getMethod
            HttpResponse httpResponse = getHttpClient(url).execute(getMethod);
            response = getResponse(url, httpResponse, resEnc);

        } catch (ClientProtocolException e) {
            logger.error("发生致命的异常，可能是协议不对或者返回的内容有问题" + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("发生网络异常" + e.getMessage(), e);
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
                getMethod = null;
            }
        }
        return response;
    }

    private static String getResponse(String url, HttpResponse httpResponse, String resEnc) throws IOException {
        String response = EMPTY;
        if (httpResponse.getEntity().getContentEncoding() != null) {
            String responseCharSet = httpResponse.getEntity().getContentEncoding().getValue();
            response = IOUtils.toString(httpResponse.getEntity().getContent(), responseCharSet);
        } else if (StringUtils.isEmpty(resEnc)) {
            response = IOUtils.toString(httpResponse.getEntity().getContent(), UTF8);
        } else {
            response = IOUtils.toString(httpResponse.getEntity().getContent(), resEnc);
        }
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            logger.warn(url + "响应状态码 = " + statusCode);
            logger.warn(url + "响应内容：\n" + response);
        }
        return response;
    }

    /**
     * 获得完整的Url
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param enc    编码格式
     * @return
     */
    public static StringBuffer getTotalUrl(String url, Map<String, String> params, String enc) {
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        if (strtTotalURL.indexOf("?") == -1) {
            strtTotalURL.append(url).append("?").append(convertMapToUrl(params, enc));
        } else {
            strtTotalURL.append(url).append(URL_PARAM_CONNECT_FLAG).append(convertMapToUrl(params, enc));
        }
        return strtTotalURL;
    }

    /**
     * 获得完整的Url
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     */
    public static StringBuffer getTotalUrl(String url, Map<String, String> params) {
        return getTotalUrl(url, params, UTF8);
    }

    /**
     * 据Map生成URL字符串
     *
     * @param map      Map
     * @param valueEnc URL编码
     * @return URL
     */
    private static String convertMapToUrl(Map<String, String> map, String valueEnc) {
        if (null == map || map.keySet().size() == 0) {
            return (EMPTY);
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (String key : map.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, map.get(key)));
        }
        return URLEncodedUtils.format(nameValuePairs, valueEnc);
    }

}
