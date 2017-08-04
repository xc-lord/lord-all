package com.lord.biz.service.cat;

import com.lord.common.dto.cat.CategorySimple;
import com.lord.common.dto.cat.OptionNode;
import com.lord.common.dto.cat.TreeNode;
import com.lord.common.service.cat.CategoryService;
import com.lord.common.service.cat.CategorySimpleService;
import com.lord.utils.CommonUtils;
import com.lord.utils.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：分类数据结构的通用方法
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月10日 18:25
 */
public abstract class CategorySimpleServiceImpl implements CategorySimpleService
{
    @Override
    public List<TreeNode> getTreeNodes() {
        List<TreeNode> list = new ArrayList<>();
        List<CategorySimple> categoryList = findAllCategory();
        if (categoryList == null || categoryList.size() < 1) {
            return list;
        }
        long rootParentId = 0L;
        Map<Long, List<CategorySimple>> parentMap = getParentMap(rootParentId, categoryList);

        List<CategorySimple> rootList = parentMap.get(rootParentId);
        for (CategorySimple sub : rootList) {
            TreeNode treeNode = setTreeNode(sub, parentMap);
            list.add(treeNode);
        }
        return list;
    }

    @Override
    public List<OptionNode> getOptions() {
        List<OptionNode> list = new ArrayList<>();
        List<CategorySimple> categoryList = findAllCategory();
        if (categoryList == null || categoryList.size() < 1) {
            return list;
        }

        long rootParentId = 0L;
        Map<Long, List<CategorySimple>> parentMap = getParentMap(rootParentId, categoryList);

        List<CategorySimple> rootList = parentMap.get(rootParentId);
        for (CategorySimple sub : rootList) {
            OptionNode treeNode = setOptionNode(sub, parentMap);
            list.add(treeNode);
        }
        return list;
    }

    /**
     * 设置新增分类的公共属性
     *
     * @param pageObj 需要保存的对象
     * @param parent  父节点
     */
    public void setAddCategory(CategorySimple pageObj, CategorySimple parent) {
        if (parent == null) {
            setFirstCategory(pageObj);
        } else {
            Preconditions.checkNotNull(parent, "父节点不存在");
            pageObj.setLevel(parent.getLevel() + 1);
            pageObj.setParentId(parent.getId());
        }
    }

    /**
     * 设置更新分类的公共属性
     *
     * @param pageObj 需要保存的对象
     * @param parent  父节点
     * @param dbObj   数据库数据
     */
    public void setUpdateCategory(CategorySimple pageObj, CategorySimple parent, CategorySimple dbObj) {
        pageObj.setParentId(dbObj.getParentId());
        if (parent != null) {
            pageObj.setLevel(parent.getLevel() + 1);
            pageObj.setParentId(parent.getId());
        } else {
            setFirstCategory(pageObj);
        }
    }

    private void setFirstCategory(CategorySimple pageObj) {
        pageObj.setLevel(1);
        pageObj.setParentId(null);
    }

    /**
     * 获得父节点Id对应的所有子节点Map集合
     * @param rootParentId  根节点ID
     * @param categoryList  所有的节点
     * @return Map集合
     */
    public Map<Long, List<CategorySimple>> getParentMap(long rootParentId, List<CategorySimple> categoryList) {
        Map<Long, List<CategorySimple>> parentMap = new HashMap<>();
        for (CategorySimple CategorySimple : categoryList) {
            Long parentId = CategorySimple.getParentId();
            if (parentId == null) {
                parentId = rootParentId;
            }
            if (parentMap.get(parentId) == null) {
                parentMap.put(parentId, new ArrayList<CategorySimple>());
            }
            parentMap.get(parentId).add(CategorySimple);
        }
        return parentMap;
    }

    public TreeNode setTreeNode(CategorySimple sub, Map<Long, List<CategorySimple>> parentMap) {
        TreeNode treeNode = parseTreeNode(sub);
        List<CategorySimple> subList = parentMap.get(sub.getId());
        if (subList != null && subList.size() > 0) {
            List<TreeNode> sList = new ArrayList<>();
            for (CategorySimple CategorySimple : subList) {
                TreeNode sNode = setTreeNode(CategorySimple, parentMap);
                sList.add(sNode);
            }
            treeNode.setChildren(sList);
        }
        return treeNode;
    }

    private OptionNode setOptionNode(CategorySimple sub, Map<Long, List<CategorySimple>> parentMap) {
        OptionNode treeNode = parseOptionNode(sub);
        List<CategorySimple> subList = parentMap.get(sub.getId());
        if (subList != null && subList.size() > 0) {
            List<OptionNode> sList = new ArrayList<>();
            for (CategorySimple CategorySimple : subList) {
                OptionNode sNode = setOptionNode(CategorySimple, parentMap);
                sList.add(sNode);
            }
            treeNode.setChildren(sList);
        }
        return treeNode;
    }

    private OptionNode parseOptionNode(CategorySimple sub) {
        OptionNode treeNode = new OptionNode();
        treeNode.setValue(sub.getId() + "");
        treeNode.setLabel(sub.getName());
        return treeNode;
    }

    private TreeNode parseTreeNode(CategorySimple sub) {
        TreeNode treeNode = new TreeNode();
        treeNode.setId(sub.getId());
        treeNode.setName(sub.getName());
        treeNode.setLetter(sub.getAdsType());
        return treeNode;
    }

    protected abstract List<CategorySimple> findAllCategory();
}
