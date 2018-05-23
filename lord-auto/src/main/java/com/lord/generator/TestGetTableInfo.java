package com.lord.generator;

import cn.org.rapid_framework.generator.provider.db.table.TableFactory;
import cn.org.rapid_framework.generator.provider.db.table.model.Column;
import cn.org.rapid_framework.generator.provider.db.table.model.Table;
import com.alibaba.fastjson.JSON;

import java.util.LinkedHashSet;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月23日 18:13
 */
public class TestGetTableInfo
{
    public static void main(String[] args)
    {
        String tableName = "sys_file";
        Table table = TableFactory.getInstance().getTable(tableName);
        System.out.println(JSON.toJSONString(table));

        System.out.println("SQL查询语句");

        LinkedHashSet<Column> columns = table.getColumns();
        System.out.println("SELECT");
        int i = 1;
        for (Column column : columns)
        {
            String end = ",";
            if(i==columns.size())
                end = "";
            System.out.println(column.getSqlName() + end);
            i++;
        }
        System.out.println("FROM " + tableName);
        System.out.println("LIMIT 10");
    }
}
