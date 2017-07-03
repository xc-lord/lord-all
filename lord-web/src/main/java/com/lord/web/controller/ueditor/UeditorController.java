package com.lord.web.controller.ueditor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能：百度编辑器服务端Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月03日 15:00
 */
@Api(description = "百度编辑器服务端Api")
@RestController
public class UeditorController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "百度编辑器配置", notes = "百度编辑器配置")
    @RequestMapping(value = "/api/ueditor/config", method = {RequestMethod.POST, RequestMethod.GET})
    public String ueditorController(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");

            ServletContext servletContext = request.getSession().getServletContext();
            String rootPath = servletContext.getRealPath("/");

            /*PrintWriter writer = response.getWriter();
            writer.write();
            writer.flush();
            writer.close();*/
            return new ActionEnter(request, rootPath).exec();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "xxxx";
    }

}
