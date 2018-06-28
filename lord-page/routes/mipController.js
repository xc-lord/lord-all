(function() {
    'use strict';

    var path = require('path');
    var logger = require('../lib/logger')(path.basename(__filename));
    var dataUtils = require('../lib/dataUtils');
    var express = require('express');
    var router = express.Router();

    const siteMapUrl = "/eduMip/siteMap";
    const articleUrl = "/eduMip/article";
    const tagsUrl = "/eduMip/tags";
    const schoolUrl = "/eduMip/school";

    /* 首页 */
    router.get('/eduMip/', function (req, res, next) {
        var api = {
            indexData:{
                url: '/api/ads/getPage.do',
                data:{pageCode:'eduPCIndex'}
            }
        };
        dataUtils.getH5Data(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + articleUrl;
            res.render('./eduMip/mipIndex', data);//渲染页面
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
        dataUtils.getH5Data(req, api, function (err, data) {
            data.siteMapUrl = data.dynSite + siteMapUrl;
            data.articleUrl = data.dynSite + articleUrl;
            data.schoolUrl = data.dynSite + schoolUrl;
            data.pageUrl = data.dynSite + siteMapUrl;
            res.render('./eduMip/mipSiteMap', data);//渲染页面
        });
    });

    /* 学校目录 */
    router.get(schoolUrl + '/', function (req, res, next) {
        var api = {
            districtList: {
                url: '/api/edu/listSchool.do'
            }
        };
        dataUtils.getH5Data(req, api, function (err, data) {
            data.schoolUrl = data.dynSite + schoolUrl;
            data.pageUrl = data.dynSite + schoolUrl;
            res.render('./eduMip/mipSchoolList', data);//渲染页面
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
        dataUtils.getH5Data(req, api, function (err, data) {
            data.schoolUrl = data.dynSite + schoolUrl;
            data.pageUrl = data.dynSite + schoolUrl;
            res.render('./eduMip/mipSchool', data);//渲染页面
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
        dataUtils.getH5Data(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + articleUrl;
            res.render('./eduMip/mipArticleList', data);//渲染页面
        });
    });

    /* 分页文章列表 */
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
        dataUtils.getH5Data(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + articleUrl;
            res.render('./eduMip/mipArticleList', data);//渲染页面
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
        dataUtils.getH5Data(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            res.render('./eduMip/mipArticle', data);//渲染页面
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
        dataUtils.getH5Data(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + articleUrl + "/" + req.params.catId;
            res.render('./eduMip/mipArticleList', data);//渲染页面
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
        dataUtils.getH5Data(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + articleUrl + "/" + req.params.catId;
            res.render('./eduMip/mipArticleList', data);//渲染页面
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
        dataUtils.getH5Data(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + tagsUrl + "/" + req.params.tagsId;
            res.render('./eduMip/mipArticleTags', data);//渲染页面
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
        dataUtils.getH5Data(req, api, function (err, data) {
            data.articleUrl = data.dynSite + articleUrl;
            data.tagsUrl = data.dynSite + tagsUrl;
            data.pageUrl = data.dynSite + tagsUrl + "/" + req.params.tagsId;
            res.render('./eduMip/mipArticleTags', data);//渲染页面
        });
    });

    module.exports = router;
})();