(function() {
    'use strict';

    var path = require('path');
    var logger = require('../../lib/logger')(path.basename(__filename));
    var dataUtils = require('../../lib/dataUtils');
    var asyncUtils = require('../../lib/asyncUtils');
    var express = require('express');
    var router = express.Router();

    /* 首页 */
    router.get('/', function (req, res, next) {
        res.redirect('/edu/');
    });

    /* 检查服务是否可用 */
    router.get('/health.do', function (req, res, next) {
        asyncUtils.get({
                url: '/api/health.do',
                req: req
            },
            "json",
            function (err, response) {
                console.info(response);
                res.json(response);
            });
    });

    module.exports = router;
})();