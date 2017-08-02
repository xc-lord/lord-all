package com.lord.biz.service;

import com.lord.common.dto.cat.Category;
import com.lord.common.dto.cat.OptionNode;
import com.lord.common.dto.cat.TreeNode;
import com.lord.common.service.CategoryService;
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
public abstract class CategoryServiceImpl implements CategoryService {

    @Override
    public List<TreeNode> getTreeNodes() {
        List<TreeNode> list = new ArrayList<>();
        List<Category> categoryList = findAllCategory();
        if (categoryList == null || categoryList.size() < 1) {
            return list;
        }

        long rootParentId = 0L;
        Map<Long, List<Category>> parentMap = getParentMap(rootParentId, categoryList);

        List<Category> rootList = parentMap.get(rootParentId);
        for (Category sub : rootList) {
            TreeNode treeNode = setTreeNode(sub, parentMap);
            list.add(treeNode);
        }
        return list;
    }

    @Override
    public List<OptionNode> getOptions() {
        List<OptionNode> list = new ArrayList<>();
        List<Category> categoryList = findAllCategory();
        if (categoryList == null || categoryList.size() < 1) {
            return list;
        }

        long rootParentId = 0L;
        Map<Long, List<Category>> parentMap = getParentMap(rootParentId, categoryList);

        List<Category> rootList = parentMap.get(rootParentId);
        for (Category sub : rootList) {
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
    public void setAddCategory(Category pageObj, Category parent) {
        pageObj.setLeaf(true);//新增节点都是叶子节点
        if (parent == null) {
            setFirstCategory(pageObj);
        } else {
            Preconditions.checkNotNull(parent, "父节点不存在");
            if (StringUtils.isNotEmpty(parent.getParentIds())) {
                pageObj.setParentIds(parent.getParentIds() + parent.getId() + ",");
            } else {
                pageObj.setParentIds(parent.getId() + ",");
            }
            pageObj.setLevel(parent.getLevel() + 1);
            pageObj.setParentId(parent.getId());
            pageObj.setParentName(parent.getName());
        }
    }

    public void updateParents(Category pageObj) {
        List<Long> parentIds = CommonUtils.parseLongList(pageObj.getParentIds(), ",");
        for (Long parentId : parentIds)
        {
            List<Long> chilrenIds = findAllChildrenIdsLike("%" + parentId + ",%");
            String chilrenStr = "";
            for (Long chilrenId : chilrenIds)
            {
                chilrenStr += chilrenId + ",";
            }
            boolean isLeaf = false;
            if (org.apache.commons.lang.StringUtils.isEmpty(chilrenStr))
                isLeaf = true;
            updateChildrenIds(chilrenStr, isLeaf, parentId);
        }
    }

    protected abstract void updateChildrenIds(String chilrenStr, boolean isLeaf, Long parentId);

    protected abstract List<Long> findAllChildrenIdsLike(String parentIds);

    /**
     * 设置更新分类的公共属性
     *
     * @param pageObj 需要保存的对象
     * @param parent  父节点
     * @param dbObj   数据库数据
     */
    public void setUpdateCategory(Category pageObj, Category parent, Category dbObj) {
        pageObj.setParentId(dbObj.getParentId());
        pageObj.setChildrenIds(dbObj.getChildrenIds());
        pageObj.setLeaf(dbObj.isLeaf());
        if (parent != null) {
            pageObj.setLevel(parent.getLevel() + 1);
            pageObj.setParentId(parent.getId());
            pageObj.setParentName(parent.getName());
            if (StringUtils.isNotEmpty(parent.getParentIds())) {
                pageObj.setParentIds(parent.getParentIds() + parent.getId() + ",");
            } else {
                pageObj.setParentIds(parent.getId() + ",");
            }
        } else {
            setFirstCategory(pageObj);
        }
    }

    private void setFirstCategory(Category pageObj) {
        pageObj.setLevel(1);
        pageObj.setParentId(null);
        pageObj.setParentName(null);
        pageObj.setParentIds(null);
    }

    /**
     * 是否需要更新父节点信息
     *
     * @param pageObj 需要保存的对象
     * @param parent  父节点
     */
    public boolean needUpdateParent(Category pageObj, Category parent) {
        if (parent == null) {
            return false;
        }
        String childrenIds = parent.getChildrenIds();
        String idStr = pageObj.getId() + ",";
        if (StringUtils.isEmpty(childrenIds)) {
            parent.setChildrenIds(idStr);
            parent.setLeaf(false);
            return true;
        }
        if (!childrenIds.contains(idStr)) {
            parent.setChildrenIds(childrenIds + idStr);
            parent.setLeaf(false);
            return true;
        }
        return false;
    }

    /**
     * 获得父节点Id对应的所有子节点Map集合
     * @param rootParentId  根节点ID
     * @param categoryList  所有的节点
     * @return Map集合
     */
    public Map<Long, List<Category>> getParentMap(long rootParentId, List<Category> categoryList) {
        Map<Long, List<Category>> parentMap = new HashMap<>();
        for (Category category : categoryList) {
            Long parentId = category.getParentId();
            if (parentId == null) {
                parentId = rootParentId;
            }
            if (parentMap.get(parentId) == null) {
                parentMap.put(parentId, new ArrayList<Category>());
            }
            parentMap.get(parentId).add(category);
        }
        return parentMap;
    }

    public TreeNode setTreeNode(Category sub, Map<Long, List<Category>> parentMap) {
        TreeNode treeNode = parseTreeNode(sub);
        List<Category> subList = parentMap.get(sub.getId());
        if (subList != null && subList.size() > 0) {
            List<TreeNode> sList = new ArrayList<>();
            for (Category category : subList) {
                TreeNode sNode = setTreeNode(category, parentMap);
                sList.add(sNode);
            }
            treeNode.setChildren(sList);
        }
        return treeNode;
    }

    private OptionNode setOptionNode(Category sub, Map<Long, List<Category>> parentMap) {
        OptionNode treeNode = parseOptionNode(sub);
        List<Category> subList = parentMap.get(sub.getId());
        if (subList != null && subList.size() > 0) {
            List<OptionNode> sList = new ArrayList<>();
            for (Category category : subList) {
                OptionNode sNode = setOptionNode(category, parentMap);
                sList.add(sNode);
            }
            treeNode.setChildren(sList);
        }
        return treeNode;
    }

    private OptionNode parseOptionNode(Category sub) {
        OptionNode treeNode = new OptionNode();
        treeNode.setValue(sub.getId() + "");
        treeNode.setLabel(sub.getName());
        return treeNode;
    }

    private TreeNode parseTreeNode(Category sub) {
        TreeNode treeNode = new TreeNode();
        treeNode.setId(sub.getId());
        treeNode.setName(sub.getName());
        treeNode.setLetter(sub.getLetter());
        treeNode.setIcon(sub.getIcon());
        return treeNode;
    }

    protected abstract List<Category> findAllCategory();
}
