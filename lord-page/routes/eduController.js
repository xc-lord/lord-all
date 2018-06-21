(function() {
    'use strict';

    var path = require('path');
    var logger = require('../lib/logger')(path.basename(__filename));
    var dataUtils = require('../lib/dataUtils');
    var express = require('express');
    var router = express.Router();

    const siteMapUrl = "/edu/siteMap";
    const articleUrl = "/edu/article";
    const tagsUrl = "/edu/tags";
    const schoolUrl = "/edu/school";

    /* 首页 */
    router.get('/edu/', function (req, res, next) {
        var api = {
            indexData:{
                url: '/api/ads/getPage.do',
                data:{pageCode:'eduPCIndex'}
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + articleUrl;
            res.render('./edu/eduIndex', data);//渲染页面
        });
    });

    /* 网站地图 */
    router.get(siteMapUrl + '/', function (req, res, next) {
        var api = {
            districtList: {
                url: '/api/edu/listSchool.do'
            },
            categoryTree: {
                url: '/api/edu/getCategoryTree.do'
            },
            randomArticles: {
                url: '/api/edu/getRandomArticle.do',
                data:{num:10}
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.siteMapUrl = data.dynSite + siteMapUrl;
            data.articleUrl = data.dynSite + articleUrl;
            data.schoolUrl = data.dynSite + schoolUrl;
            data.pageUrl = data.dynSite + siteMapUrl;
            res.render('./edu/siteMap', data);//渲染页面
        });
    });

    /* 学校目录 */
    router.get(schoolUrl + '/', function (req, res, next) {
        var api = {
            districtList: {
                url: '/api/edu/listSchool.do'
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.schoolUrl = data.dynSite + schoolUrl;
            data.pageUrl = data.dynSite + schoolUrl;
            res.render('./edu/schoolList', data);//渲染页面
        });
    });

    /* 学校详情 */
    router.get(schoolUrl + '/:schoolId.html', function (req, res, next) {
        var api = {
            schoolData: {
                url: '/api/edu/getSchool.do',
                data:{id: req.params.schoolId}
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.schoolUrl = data.dynSite + schoolUrl;
            data.pageUrl = data.dynSite + schoolUrl;
            res.render('./edu/schoolDetails', data);//渲染页面
        });
    });

    /* 文章列表 */
    router.get(articleUrl + '/', function (req, res, next) {
        var api = {
            listData: {
                url: '/api/edu/listArticle.do'
            },
            randomArticles: {
                url: '/api/edu/getRandomArticle.do',
                data:{num:10}
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + articleUrl;
            res.render('./edu/articleList', data);//渲染页面
        });
    });

    /* 分类文章列表 */
    router.get(articleUrl + '/list:page.html', function (req, res, next) {
        var api = {
            listData: {
                url: '/api/edu/listArticle.do',
                data:{page:req.params.page}
            },
            randomArticles: {
                url: '/api/edu/getRandomArticle.do',
                data:{num:10}
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + articleUrl;
            res.render('./edu/articleList', data);//渲染页面
        });
    });

    /* 文章详情 */
    router.get(articleUrl + '/:articleId.html', function (req, res, next) {
        var api = {
            articleData: {
                url: '/api/edu/getArticle.do',
                data:{articleId: req.params.articleId}
            },
            randomArticles: {
                url: '/api/edu/getRandomArticle.do',
                data:{num:10}
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            res.render('./edu/article', data);//渲染页面
        });
    });

    /* 分类文章列表 */
    router.get(articleUrl + '/:catId/', function (req, res, next) {
        var api = {
            listData: {
                url: '/api/edu/listArticle.do',
                data:{catId: req.params.catId}
            },
            randomArticles: {
                url: '/api/edu/getRandomArticle.do',
                data:{num:10, catId: req.params.catId}
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + articleUrl + "/" + req.params.catId;
            res.render('./edu/articleList', data);//渲染页面
        });
    });

    /* 分类文章列表 */
    router.get(articleUrl + '/:catId/list:page.html', function (req, res, next) {
        var api = {
            listData: {
                url: '/api/edu/listArticle.do',
                data:{catId: req.params.catId, page:req.params.page}
            },
            randomArticles: {
                url: '/api/edu/getRandomArticle.do',
                data:{num:10, catId: req.params.catId}
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + articleUrl + "/" + req.params.catId;
            res.render('./edu/articleList', data);//渲染页面
        });
    });

    /* 标签详情 */
    router.get(tagsUrl + '/:tagsId/', function (req, res, next) {
        var api = {
            tagsData: {
                url: '/api/edu/getTags.do',
                data:{tagsId: req.params.tagsId}
            },
            randomArticles: {
                url: '/api/edu/getRandomArticle.do',
                data:{num:10}
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + tagsUrl + "/" + req.params.tagsId;
            res.render('./edu/articleTags', data);//渲染页面
        });
    });

    /* 标签详情 */
    router.get(tagsUrl + '/:tagsId/list:page.html', function (req, res, next) {
        var api = {
            tagsData: {
                url: '/api/edu/getTags.do',
                data:{tagsId: req.params.tagsId, page:req.params.page}
            },
            randomArticles: {
                url: '/api/edu/getRandomArticle.do',
                data:{num:10}
            }
        };
        dataUtils.getData(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + tagsUrl + "/" + req.params.tagsId;
            res.render('./edu/articleTags', data);//渲染页面
        });
    });

    module.exports = router;
})();