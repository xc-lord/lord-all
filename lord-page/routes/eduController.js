(function() {
    'use strict';

    var path = require('path');
    var logger = require('../lib/logger')(path.basename(__filename));
    var dataUtils = require('../lib/dataUtils');
    var express = require('express');
    var router = express.Router();

    /* 首页 */
    router.get('/edu/', function (req, res, next) {
        res.render('./edu/eduIndex', {});
    });

    /* 学校目录 */
    router.get('/edu/school/', function (req, res, next) {
        var api = {
            districtList: {
                url: '/api/edu/listSchool.do'
            }
        };
        dataUtils.get(req, api, function (err, data) {
            res.render('./edu/schoolList', data);//渲染页面
        });
    });

    /* 学校详情 */
    router.get('/edu/school/:schoolId.html', function (req, res, next) {
        var api = {
            schoolData: {
                url: '/api/edu/getSchool.do',
                data:{id: req.params.schoolId}
            }
        };
        dataUtils.get(req, api, function (err, data) {
            res.render('./edu/schoolDetails', data);//渲染页面
        });
    });

    const articleUrl = "/edu/article";

    /* 文章列表 */
    router.get(articleUrl + '/', function (req, res, next) {
        var api = {
            listData: {
                url: '/api/edu/listArticle.do'
            }
        };
        dataUtils.get(req, api, function (err, data) {
            data.articleUrl = articleUrl;
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
            }
        };
        dataUtils.get(req, api, function (err, data) {
            data.articleUrl = articleUrl;
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
            }
        };
        dataUtils.get(req, api, function (err, data) {
            data.articleUrl = articleUrl;
            res.render('./edu/article', data);//渲染页面
        });
    });

    /* 分类文章列表 */
    router.get(articleUrl + '/:catId/', function (req, res, next) {
        var api = {
            listData: {
                url: '/api/edu/listArticle.do',
                data:{catId: req.params.catId}
            }
        };
        dataUtils.get(req, api, function (err, data) {
            data.articleUrl = articleUrl;
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
            }
        };
        dataUtils.get(req, api, function (err, data) {
            data.articleUrl = articleUrl;
            data.pageUrl = data.dynSite + articleUrl + "/" + req.params.catId;
            res.render('./edu/articleList', data);//渲染页面
        });
    });

    module.exports = router;
})();