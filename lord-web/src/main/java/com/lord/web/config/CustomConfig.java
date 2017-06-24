package com.lord.web.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import javax.servlet.MultipartConfigElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义的Spring配置
 * Created by xiaocheng on 2017/3/24.
 */
@Configuration
public class CustomConfig {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 上传文件大小限制
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("10MB");
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }

    /**
     * sping boot的将字符串的时间转换为java.util.Date
     * @return
     */
    @Bean
    public Converter<String, Date> addDateConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                if (StringUtils.isEmpty(source)) {
                    return null;
                }
                if (StringUtils.isNumeric(source)) {
                    Long time = Long.parseLong(source);
                    return new Date(time.longValue());
                }
                Date date = null;
                try {
                    date = sdf.parse(source);
                } catch (ParseException e) {
                    logger.error("将字符串" + source + "的时间参数转换java.util.Date出错：" + e.getMessage(), e);
                }
                return date;
            }
        };
    }
}
