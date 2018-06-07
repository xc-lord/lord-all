(function() {
    'use strict';

    var path = require('path');
    var logger = require('../lib/logger')(path.basename(__filename));
    var dataFactory = require('../lib/dataFactory');
    var ApiUtils = require('../lib/ApiUtils');
    var common = require('../lib/common');
    var express = require('express');
    var router = express.Router();

    /*router.get('/', function(req, res) {
        res.sendFile('/mis/vueMain.html', function(err) {
            if (err) {
                logger.error(err);
                res.status(err.status).end();
            }
        });
    });*/

    /* GET home page. */
    router.get('/', function (req, res, next) {
        console.log("req.host = " + req.host);
        console.log("req.port = ");
        console.log("req.originalUrl = " + req.originalUrl);
        console.log("req.url = " + req.url);
        console.log("req.path = " + req.path);
        var api = {
            districtList: {
                url: '/api/common/listDistrict.do'
            }
        };
        ApiUtils.get(req, api, function (err, data) {
            data.title = "我的首页";
            data.keywords = "我的首页";
            data.description = "我的首页";
            console.info(data);
            res.render('./index/index', data);//渲染页面
        });
    });

    // router.get('/', function(req, res) {
    //     var api = {
    //     };

    //     dataFactory.data(req, api, function(err, resource) {
    //         var data = {};

    //         dataFactory.formatData('林氏木业O2O管理后台', data, req, resource);

    //         dataFactory.css(data, []);

    //         dataFactory.js(data, []);

    //         res.render('./index/index.html', data);
    //     });
    // });

    module.exports = router;
})();