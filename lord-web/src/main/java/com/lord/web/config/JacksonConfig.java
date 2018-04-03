package com.lord.web.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * spring boot 去null值
 * Created by xiaocheng on 2017/3/23.
 */
//@Configuration
public class JacksonConfig {

    /*@Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)*/
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        //去掉响应的json中的null值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //将响应的json中的null值设置为空字符串
        /*objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString("");
            }
        });*/
        return objectMapper;
    }
}
