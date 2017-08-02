package com.lord.web.config;

import com.lord.common.service.mis.MisMenuService;
import com.lord.web.handler.AuthAdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 功能：Spring MVC的拦截器配置
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月22日 17:35
 */
@Component
public class MvcWebConfig extends WebMvcConfigurerAdapter
{
    @Autowired
    private MisMenuService misMenuService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        AuthAdminInterceptor authAdminInterceptor = new AuthAdminInterceptor();
        authAdminInterceptor.setMisMenuService(misMenuService);
        registry.addInterceptor(authAdminInterceptor).addPathPatterns("/api/admin/**");
        super.addInterceptors(registry);
    }
}
