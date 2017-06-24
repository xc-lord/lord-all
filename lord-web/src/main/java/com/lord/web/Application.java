package com.lord.web;

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
 * Hello world!
 *
 */
@SpringBootApplication//spring boot启动类注解
@EnableAsync//启用异步方法
@EnableScheduling//启用定时任务
@ComponentScan("com.lord")//spring扫描的包路径
@EntityScan("com.lord.common.model")//jpa实体映射的包路径
@EnableJpaRepositories(basePackages={"com.lord.biz.dao"})//jpa的Dao层的包路径
public class Application
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static void main( String[] args )
    {
        System.out.println( "启动Spring Boot!" );
        SpringApplication.run(Application.class, args);
    }

    /*
    GET		/autoconfig		查看自动配置的使用情况							TRUE
    GET		/configprops	显示一个所有@ConfigurationProperties的整理列表	TRUE
    GET		/beans			显示一个应用中所有Spring Beans的完整列表		    TRUE
    GET		/dump			打印线程栈										TRUE
    GET		/env			查看所有环境变量								    TRUE
    GET		/env/{name}		查看具体变量值									TRUE
    GET		/health			查看应用健康指标								    FALSE
    GET		/info			查看应用信息									    FALSE
    GET		/mappings		查看所有url映射									TRUE
    GET		/metrics		查看应用基本指标								    TRUE
    GET		/metrics/{name}	查看具体指标									    TRUE
    POST	/shutdown		允许应用以优雅的方式关闭（默认情况下不启用）	    TRUE
    GET		/trace			查看基本追踪信息								    TRUE
    GET		/loggers		查看日志级别信息								    TRUE
    */
}
