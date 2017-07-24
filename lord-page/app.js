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

app.use('/', index);
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
