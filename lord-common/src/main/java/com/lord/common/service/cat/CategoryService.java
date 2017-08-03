package com.lord.common.service.cat;

import com.lord.common.dto.cat.OptionNode;
import com.lord.common.dto.cat.TreeNode;

import java.util.List;

/**
 * 功能：分类数据结构的通用方法
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月05日 16:55
 */
public interface CategoryService {
    /**
     * 获得树形结构的数据
     * @return 树形结构的数据
     */
    List<TreeNode> getTreeNodes();

    /**
     * 获得级联选择器的数据
     * @return 级联选择器的数据
     */
    List<OptionNode> getOptions();
}
