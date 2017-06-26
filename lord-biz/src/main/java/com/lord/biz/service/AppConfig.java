package com.lord.biz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 功能：配置文件
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年06月26日 14:49
 */
@Component
public class AppConfig {
    public static String uploadDir;

    @Value("${lord.upload.dir}")
    public void setUploadDir(String uploadDir) {
        AppConfig.uploadDir = uploadDir;
    }
}
