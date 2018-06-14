(function() {
    'use strict';

    var cookieParser = require('cookie-parser');
    var bodyParser = require('body-parser');
    var path = require('path');
    var logger = require('./lib/logger')(path.basename(__filename));
    var appInit = require('./lib/appInit');
    var viewTemplate = require('./lib/viewTemplate');
    var express = require('express');

    var app = express();

    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({extended: false}));
    app.use(cookieParser());
    //拦截器配置
    appInit.loadInterceptor(app);
    //静态目录配置
    app.use(express.static(path.join(__dirname, 'public')));
    //使用腾讯的art-template模板引擎
    viewTemplate.init(app);
    //路由配置
    appInit.loadRoutes(app);

    module.exports = app;
})();
