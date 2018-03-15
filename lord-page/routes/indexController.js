(function() {
    'use strict';

    var path = require('path');
    var logger = require('../lib/logger')(path.basename(__filename));
    var dataFactory = require('../lib/dataFactory');
    var common = require('../lib/common');
    var express = require('express');
    var router = express.Router();

    router.get('/', function(req, res) {
        res.sendFile('/mis/vueMain.html', function(err) {
            if (err) {
                logger.error(err);
                res.status(err.status).end();
            }
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