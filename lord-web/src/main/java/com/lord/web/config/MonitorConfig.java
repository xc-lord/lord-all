package com.lord.web.config;

/**
 * spring boot的监控配置
 * 可访问的地址：
 * http://localhost:8080/logger         动态的修改日志级别
 * http://localhost:8080/health         获取应用的各类健康指标信息
 * http://localhost:8080/metrics        返回当前应用的各类重要度量指标，比如：内存信息、线程信息、垃圾回收信息等
 * http://localhost:8080/autoconfig     应用的自动化配置报告
 * http://localhost:8080/beans          该端点用来获取应用上下文中创建的所有Bean
 * http://localhost:8080/configprops    获取应用中配置的属性信息报告
 * http://localhost:8080/env            获取应用所有可用的环境属性报告
 * http://localhost:8080/mappings       返回所有Spring MVC的控制器映射关系报告
 * http://localhost:8080/info           返回一些应用自定义的信息
 * http://localhost:8080/dump           用来暴露程序运行中的线程信息
 * http://localhost:8080/trace          返回基本的HTTP跟踪信息，始终保留最近的100条请求记录
 * http://localhost:8080/shutdown       关闭应用的端点
 * Created by xiaocheng on 2017/3/24.
 */
/*@Configuration
@Import({ EndpointAutoConfiguration.class,
        PublicMetricsAutoConfiguration.class,
        HealthIndicatorAutoConfiguration.class})*/
public class MonitorConfig {

    /*@Bean
    public EndpointHandlerMapping endpointHandlerMapping(
            Collection<? extends MvcEndpoint> endpoints) {
        return new EndpointHandlerMapping(endpoints);
    }
    @Bean
    public HealthMvcEndpoint healthMvcEndpoint(HealthEndpoint delegate) {
        return new HealthMvcEndpoint(delegate, false);
    }
    @Bean
    public EndpointMvcAdapter metricsEndPoint(MetricsEndpoint delegate) {
        return new EndpointMvcAdapter(delegate);
    }*/
}
