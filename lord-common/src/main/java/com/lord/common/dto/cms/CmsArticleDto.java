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
public class CmsArticleDto extends CmsArticle {

    /** 文章的PC内容 */
    private String content;
    /** 文章的移动端内容 */
    private String mcontent;
    /** 文章的关联文章 */
    private List<Long> articleRefIds;
    /** 文章的标签 */
    private List<String> articleTags;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMcontent() {
        return mcontent;
    }

    public void setMcontent(String mcontent) {
        this.mcontent = mcontent;
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
