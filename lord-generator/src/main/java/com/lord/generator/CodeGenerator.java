package com.lord.generator;

import cn.org.rapid_framework.generator.GeneratorFacade;

import java.io.File;

/**
 * 代码生成器
 */
public class CodeGenerator {

    public static void main(String[] args) throws Exception {
        String tableName = "cms_article";
        GeneratorFacade facade = new GeneratorFacade();
        String path = CodeGenerator.class.getClassLoader().getResource("template").getPath();
        facade.getGenerator().addTemplateRootDir(new File(path));
        facade.deleteByTable(tableName);//删除之前生成的文件
        facade.generateByTable(tableName);//重新生成的文件
    }
}
