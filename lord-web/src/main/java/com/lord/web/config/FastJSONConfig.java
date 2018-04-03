package com.lord.web.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author James
 * @date 2018/1/25 上午11:41
 */
@Configuration
public class FastJSONConfig extends WebMvcConfigurerAdapter
{
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteNullStringAsEmpty,//null转为空字符串
                SerializerFeature.WriteNullNumberAsZero,//空的数值默认为0
                SerializerFeature.WriteDateUseDateFormat//对时间进行格式化输出
        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastConverter.setFastJsonConfig(fastJsonConfig);


        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        //解决Controller的返回值为String时，中文乱码问题
        stringConverter.setDefaultCharset(Charset.forName("UTF-8"));
        //解决视图的返回值类型为String时，返回的string字符串带有双引号问题，先执行字符转换
        converters.add(stringConverter);
        converters.add(fastConverter);
    }
}