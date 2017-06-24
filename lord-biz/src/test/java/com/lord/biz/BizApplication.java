package com.lord.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 功能：测试业务方法启动Spring Boot
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月02日 14:56
 */
@SpringBootApplication//spring boot启动类注解
@EnableAsync//启用异步方法
@EnableScheduling//启用定时任务
@ComponentScan("com.lord")//spring扫描的包路径
@EntityScan("com.lord.common.model")//jpa实体映射的包路径
@EnableJpaRepositories(basePackages={"com.lord.biz.dao"})//jpa的Dao层的包路径
public class BizApplication {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static void main( String[] args )
    {
        System.out.println( "启动Spring Boot!" );
        SpringApplication.run(BizApplication.class, args);
    }
}
