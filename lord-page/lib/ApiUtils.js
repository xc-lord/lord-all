var ApiHttpUtils = require('./ApiHttpUtils'),
    async = require('async'),
    CommonUtils = require("./CommonUtils"),
    cache = require('memory-cache'),
    fs = require("fs"),
    path = require("path"),
    logger = CommonUtils.logger;


/*
 批量调用API接口的工具类
 */
var ApiUtils = {
    /*
     * 调用API接口
     * @param req   用户的请求信息
     * @param api
     * @param callback
     * @returns {*}
     */
    get: function (req, api, callback) {
        if (!callback) {
            callback = api;
            api = {};
        }
        var self = this;
        req.data || (req.data = {});
        //在trycatch中要用到
        self.res = req.res;

        var dealArrs = [];
        var data = {};
        var httpMethod;
        var s = new Date().getTime();

        for (var key in api) {
            data[key] = {};
            if (!api[key].url) {
                /*
                 * 若没有 url 字段(或为空)，那直接把api对应的字段赋值到data中。
                 * 在某些时候，需要条件渲染时要用到这个功能。
                 * 例如，根据地址栏URL请求参数，渲染出对应的页面
                 */
                data[key] = api[key];
                continue;
            }

            api[key].type || (api[key].type = "get");
            api[key].data || (api[key].data = {});
            var api_request = null;
            if (api[key].type.toLowerCase() == "get") {
                api_request = self.get;
                httpMethod = ApiHttpUtils.get;
            } else {
                api_request = self.post;
                httpMethod = ApiHttpUtils.post;
            }
            tempObj = {
                'key': key,
                'opt': {url: api[key].url, req: req, data: api[key].data, headers: api[key].headers},
                'fun': httpMethod,
                'dataType': api[key].dataType
            };
            dealArrs.push(tempObj);
            tempObj = {};
        }

        if (dealArrs.length == 0) {
            //请求的API列表为空直接返回
            return self.doRender(callback, null, {hasData:false});
        }

        //批量去请求调用API列表
        async.map(dealArrs, function (item, item_callback) {
            item.dataType = item.dataType || "json";
            var api_url = item.opt.url;
            item.fun(item.opt, item.dataType, function (err, response) {
                item_callback(null, {api_url: api_url, key: item.key, dataType: item.dataType, response: response});
            });

        }, function (err, resArr) {
            var data = {};
            for (var ii = 0; ii < resArr.length; ii++) {
                var modelKey = resArr[ii].key;
                var api_url = resArr[ii].api_url;
                var response = resArr[ii].response;
                if(response.code != 1) {
                    logger.error("API接口" + api_url + " 调用失败：" + response.msg);
                }
                data[modelKey] = response.data;
            }
            data.hasData = true;
            /*logger.debug("接口获取到的数据为：");
            logger.debug(data);*/
            var e = new Date().getTime();
            logger.debug("请求页面：" + req.path + ' 调用api接口获取数据用时: ' + (e - s) + "ms");
            self.doRender(callback, null, data);
            delete resArr;
        });
    },
    /*
     * 渲染页面
     * @param fn    使用回调方法渲染页面
     * @param err   错误信息
     * @param data  渲染页面的数据
     */
    doRender: function (fn, err, data) {
        var self = this;
        try {
            if (fn) {
                var args = [];
                for (var i = 1; i < arguments.length; i++) {
                    args.push(arguments[i]);
                }
                fn.apply(null, args);
            }
        } catch (e) {
            logger.error("调用回调函数，渲染页面时出错：", e);
            logger.info(e.stack);
            try {
                self.res && self.res.render('index/error', {error:e,title:"抱歉，网站出现错误"});
            } catch (e) {
            }
        }
    },
    /**
     * 后台用户是否登录过滤
     */
    doMisAdminAuth:function(req, res, next) {
        if(CommonUtils.isEmpty(req.cookies.MIS_AUTH_SIGN) || CommonUtils.isEmpty(req.cookies.MIS_USER_ID)) {
            return res.redirect("/mis/login.html");
        }
        next();
    }
};

module.exports = ApiUtils;