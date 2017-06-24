var express = require('express');
var ApiUtils = require('../lib/ApiUtils');
var router = express.Router();

/* GET home page. */
router.get('/', function (req, res, next) {
    console.log("req.host = " + req.host);
    console.log("req.port = ");
    console.log("req.originalUrl = " + req.originalUrl);
    console.log("req.url = " + req.url);
    console.log("req.path = " + req.path);
    var api = {
        helloApi: {
            url: '/api/testError'
        }
    };
    ApiUtils.get(req, api, function (err, data) {
        data.title = "我的首页";
        data.keywords = "我的首页";
        data.description = "我的首页";
        console.info(data);
        res.render('index/index', data);//渲染页面
    });
});

router.get('/testTemplate', function (req, res, next) {
    res.render('index/index', {});//渲染页面
});

module.exports = router;
