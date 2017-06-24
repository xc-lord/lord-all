package com.lord.generator.ftl;

import freemarker.template.*;

import java.util.List;

/**
 * 功能：freemarker 自定义函数 判断表是否包含某个字段
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月20日 12:15
 */
public class FtlHasColumnMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(List args) throws TemplateModelException {
        if (args.size() != 2) {
            throw new TemplateModelException("Wrong arguments");
        }
        SimpleSequence args1 = (SimpleSequence) args.get(0);
        SimpleScalar args2 = (SimpleScalar) args.get(1);
        String columnName = args2.getAsString();
        boolean isContains = false;
        for (int i = 0; i < args1.size(); i++) {
            TemplateModel temp = args1.get(i);
            if(temp.toString().equals(columnName)) {
                isContains = true;
            }
        }
        return isContains;
    }
}
