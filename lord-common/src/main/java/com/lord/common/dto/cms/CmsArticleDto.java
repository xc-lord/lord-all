package com.lord.common.dto.cms;

import com.lord.common.model.cms.CmsArticle;
import com.lord.common.model.cms.CmsArticleContent;

import java.util.List;

/**
 * 功能：文章保存的对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月07日 17:22
 */
public class CmsArticleDto {
    /** 文章的基础信息 */
    private CmsArticle article;
    /** 文章的内容 */
    private CmsArticleContent content;
    /** 文章的关联文章 */
    private List<Long> articleRefIds;
    /** 文章的标签 */
    private List<String> articleTags;

    public CmsArticle getArticle() {
        return article;
    }

    public void setArticle(CmsArticle article) {
        this.article = article;
    }

    public CmsArticleContent getContent() {
        return content;
    }

    public void setContent(CmsArticleContent content) {
        this.content = content;
    }

    public List<Long> getArticleRefIds() {
        return articleRefIds;
    }

    public void setArticleRefIds(List<Long> articleRefIds) {
        this.articleRefIds = articleRefIds;
    }

    public List<String> getArticleTags() {
        return articleTags;
    }

    public void setArticleTags(List<String> articleTags) {
        this.articleTags = articleTags;
    }
}
