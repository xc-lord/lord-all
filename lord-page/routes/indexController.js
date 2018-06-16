(function() {
    'use strict';

    var path = require('path');
    var logger = require('../lib/logger')(path.basename(__filename));
    var dataUtils = require('../lib/dataUtils');
    var express = require('express');
    var router = express.Router();

    /* 首页 */
    router.get('/', function (req, res, next) {
        console.log("req.host = " + req.host);
        console.log("req.port = " + global.serverPort);
        console.log("req.originalUrl = " + req.originalUrl);
        console.log("req.url = " + req.url);
        console.log("req.path = " + req.path);
        var api = {
            districtList: {
                url: '/api/common/listDistrict.do'
            }
        };
        dataUtils.get(req, api, function (err, data) {
            data.title = "我的首页";
            data.keywords = "我的首页";
            data.description = "我的首页";
            res.render('./index/index', data);//渲染页面
        });
    });


    module.exports = router;
})();