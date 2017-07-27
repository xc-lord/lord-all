var express = require('express');
var http_request = require('request');
var logger = require('log4js').getLogger("normal");
var ApiUtils = require('../lib/ApiUtils');
var router = express.Router();

//请求账号信息
var postHeader = {
    "username":"aactporg",
    "token":"da40801d5f8050f6ea4d29f359ac812e",
    "password":"Aa123321"
};

router.get('/getKRByQuery', function (req, res, next) {
    var query = req.query.query;
    if(!query) query = "nodejs";
    var words = query.split("\n");
    if(words.length > 1) {
        query = words[0];
    }
    console.info("拓展的关键字：" + query);
    var url = "https://api.baidu.com/json/sms/v4/KRService/getKRByQuery";
    var params = {
        "body":{"queryType":1,"query":query},
        "header":postHeader
    };
    var options = {
        url: url,
        body: JSON.stringify(params)
    };
    //请求百度的接口
    http_request.post(options, function (error, response, body) {
        var methodMsg = "访问接口: " + url;//调用的方法信息
        if (!response || typeof response == "undefined") {
            logger.error(methodMsg + " 找不到该链接。。。");
            res.end(methodMsg + " 找不到该链接。。。");
        } else if (response.statusCode != 200) {
            logger.error(response.body);
            logger.error(methodMsg + " 响应错误statusCode:" + response.statusCode);
            res.end(methodMsg + " 响应错误statusCode:" + response.statusCode);
        } else {
            logger.info(response.body);
            res.end(response.body);
        }
    });
});

router.get('/getKRFileIdByWords', function (req, res, next) {
    var query = req.query.query;
    if(!query)
        query = ["nodejs"];
    else
        query = query.split("\n");
    console.info("拓展的关键字列表：" + query);
    var url = "https://api.baidu.com/json/sms/v4/KRService/getKRFileIdByWords";
    var params = {
        "body":{"seedWords":query},
        "header":postHeader
    };
    var options = {
        url: url,
        body: JSON.stringify(params)
    };
    //请求百度的接口
    http_request.post(options, function (error, response, body) {
        var methodMsg = "访问接口: " + url;//调用的方法信息
        if (!response || typeof response == "undefined") {
            logger.error(methodMsg + " 找不到该链接。。。");
            res.end(methodMsg + " 找不到该链接。。。");
        } else if (response.statusCode != 200) {
            logger.error(response.body);
            logger.error(methodMsg + " 响应错误statusCode:" + response.statusCode);
            res.end(methodMsg + " 响应错误statusCode:" + response.statusCode);
        } else {
            logger.info(response.body);
            res.end(response.body);
        }
    });
});

router.get('/getFilePath', function (req, res, next) {
    var fileId = req.query.fileId;
    var url = "https://api.baidu.com/json/sms/v4/KRService/getFilePath";
    var params = {
        "body":{"fileId":fileId},
        "header":postHeader
    };
    var options = {
        url: url,
        body: JSON.stringify(params)
    };
    //请求百度的接口
    http_request.post(options, function (error, response, body) {
        var methodMsg = "访问接口: " + url;//调用的方法信息
        if (!response || typeof response == "undefined") {
            logger.error(methodMsg + " 找不到该链接。。。");
            res.end(methodMsg + " 找不到该链接。。。");
        } else if (response.statusCode != 200) {
            logger.error(response.body);
            logger.error(methodMsg + " 响应错误statusCode:" + response.statusCode);
            res.end(methodMsg + " 响应错误statusCode:" + response.statusCode);
        } else {
            logger.info(response.body);
            res.end(response.body);
        }
    });
});

module.exports = router;
