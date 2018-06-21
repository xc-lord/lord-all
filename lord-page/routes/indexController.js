(function() {
    'use strict';

    var path = require('path');
    var logger = require('../lib/logger')(path.basename(__filename));
    var dataUtils = require('../lib/dataUtils');
    var express = require('express');
    var router = express.Router();

    /* 首页 */
    router.get('/', function (req, res, next) {
        res.redirect('/edu/');
    });


    module.exports = router;
})();