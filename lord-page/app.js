(function() {
    'use strict';

    var cookieParser = require('cookie-parser');
    var bodyParser = require('body-parser');
    var path = require('path');
    var logger = require('./lib/logger')(path.basename(__filename));
    var ApiUtils = require('./lib/ApiUtils');
    var common = require('./lib/common');
    var template = require('art-template');
    var templateHelper = require('./lib/templateHelper');
    var express = require('express');

    var app = express();

    app.engine('html', require('express-art-template'));
    app.set('view engine', 'html');
    app.set('view options', {
        debug: process.env.NODE_ENV !== 'Production',
        escape: false,  // 是否开启对模板输出语句自动编码功能。为 false 则关闭编码输出功能
        extname: '.html',  // 默认后缀名。如果没有后缀名，则会自动添加 extname
        bail: false,  // bail 如果为 true，编译错误与运行时错误都会抛出异常
        onerror: function(error, options) {
            // 错误事件。仅在 bail 为 false 时生效
            logger.error(error);
        }
    });

    templateHelper.imports(template);

    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({extended: false}));
    app.use(cookieParser());

    //登录拦截器，需要拦截静态页面，需要放在express.static上面
    app.use(function (req, res, next) {
        var url = req.originalUrl;
        var regx = "^/mis/\w*";
        if (url.match(regx) && url != "/mis/login.html") {
            return ApiUtils.doMisAdminAuth(req,res,next);
        }
        next();
    });

    app.use(express.static(path.join(__dirname, 'public')));
    app.use(common.interceptor);

    common.loadRoutes(app);

    module.exports = app;
})();

/*
var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var ViewTemplate = require('./lib/ViewTemplate');
var CommonUtils = require("./lib/CommonUtils");
var ApiUtils = require("./lib/ApiUtils");

var index = require('./routes/index');
var baidu = require('./routes/baidu');
var users = require('./routes/users');

var app = express();

//全局变量
global.app_name = "lord-page-node-js";

// 指定模板引擎
//app.set('views', path.join(__dirname, 'views'));
//app.set('view engine', 'jade');

//使用腾讯的art-template模板引擎
ViewTemplate.init(app);

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));

//app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(cookieParser());

app.use(express.static(path.join(__dirname, 'public')));
//配置路由规则
app.use('/', index);
app.use('/baidu', baidu);
app.use('/users', users);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
    var err = new Error('404 页面不存在');
    err.status = 404;
    next(err);
});

// error handler
app.use(function (err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render('index/error');
});

module.exports = app;
*/
