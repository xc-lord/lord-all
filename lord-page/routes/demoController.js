(function() {
    'use strict';

    var path = require('path');
    var logger = require('../lib/logger')(path.basename(__filename));
    var apiUtils = require('../lib/apiUtils');
    var express = require('express');
    var router = express.Router();

    /* 骨架的例子 */
    router.get('/demo/layoutPage.html', function (req, res, next) {
        res.render('./demo/layoutPage', {});
    });

    /* 包含的例子 */
    router.get('/demo/includePage.html', function (req, res, next) {
        res.render('./demo/includePage', {});
    });

    module.exports = router;
})();